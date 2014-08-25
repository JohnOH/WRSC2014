package mmcom;

import java.io.*;
import java.util.*;

import gnu.io.*;

public class COMPort extends Observable implements SerialPortEventListener, BoatConnectionInterface, BoatComConstants {
	private String portName;
	private CommPortIdentifier portIdentifier;
	private SerialPort serialPort;
	private InputStream inStream;
	private OutputStream outStream;
	private WriteThread writeThread;
	private volatile boolean isConnected = false;
	private final Object writeSignal = new Object();

	public COMPort(String portName) throws NoSuchPortException {
		this.portName = portName;
		portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
	}

	/* (non-Javadoc)
	 * @see de.uniluebeck.robsail.worldserver.client.BoatConnectionInterface#connect()
	 */
	public boolean connect() {
		try {
			serialPort = (SerialPort) portIdentifier.open("Communicator", 2000);
			inStream = serialPort.getInputStream();
			outStream = serialPort.getOutputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			serialPort.setDTR(true);
			serialPort.setRTS(true);
			isConnected = true;
			return true;
		} catch (PortInUseException e) {
			System.out.println("Cannot open port " + portName +
                                ". If connection to boat was lost, restart.");
			return false;
		} catch (IOException e) {
			System.out.println("IO-streams for port " + portName +
                                " could not be opened.");
			return false;
		} catch (TooManyListenersException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		// case SerialPortEvent.BI:
		// case SerialPortEvent.OE:
		// case SerialPortEvent.FE:
		// case SerialPortEvent.PE:
		// case SerialPortEvent.CD:
		// case SerialPortEvent.CTS:
		// case SerialPortEvent.DSR:
		// case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			ArrayList<Byte> byteBuffer = new ArrayList<Byte>(20);
			int c;
			int length = 0;
			try {
				while ((c = inStream.read()) > -1) {
					if (byteBuffer.size() == 0) { // wenn Buffer leer ist, beginnt eine neue Nachricht
						length = c;			// das erste byte ist die Laenge der Nachricht
					}
					byteBuffer.add(Byte.valueOf((byte) c)); // Jedes byte wird gebuffert
					if (byteBuffer.size() >= length + MSG_OVERHEAD) {
						if (byteBuffer.get(length + MSG_OVERHEAD - 1) == MSG_SEPERATOR) {
							byte[] readBytes = new byte[length + MSG_OVERHEAD];
							for (int i = 0; i < readBytes.length; i++) {
								readBytes[i] = byteBuffer.get(0).byteValue();
								byteBuffer.remove(0);
							}
							//System.out.println(Arrays.toString(readBytes));
							setChanged();
							notifyObservers(readBytes);
						} else { // falls Nachrichtentrennzeichen falsch
							byteBuffer.remove(0);
							length = byteBuffer.get(0); // interpretiere das neue erste byte als Laenge der Nachricht
						}
					}
				}
			} catch (IOException e) {
				// hier etwas tun?
			}
			break;
		}
	}

	/* (non-Javadoc)
	 * @see de.uniluebeck.robsail.worldserver.client.BoatConnectionInterface#writeToStream(byte[])
	 */
	public synchronized void writeToStream(final byte[] msg) throws IOException {
		if (writeThread == null || !writeThread.isAlive()) {
			writeThread = new WriteThread();
			writeThread.start();
		}
		int queueSize = writeThread.writeMsg(msg);
		if (queueSize >= 20) {
			writeThread.stopThread();
			throw new IOException(
					"<html>Fehler bei der �bertragung der Befehle,<br>evtl. ist die EVA-Frequenz zu hoch eingestellt");
		}
	}

	public static ArrayList<String> getCOMPorts() {
		String portName;
		ArrayList<String> availablePorts = new ArrayList<String>();
		for (int i = 1; i < 51; i++) {
			try {
				portName = "COM" + i;
				CommPortIdentifier.getPortIdentifier(portName);
				availablePorts.add(portName);
			} catch (NoSuchPortException e) {
				// dann halt nicht
			}
		}
		return availablePorts;
	}

	/* (non-Javadoc)
	 * @see de.uniluebeck.robsail.worldserver.client.BoatConnectionInterface#isConnected()
	 */
	public boolean isConnected() {
		return isConnected;
	}

	/* (non-Javadoc)
	 * @see de.uniluebeck.robsail.worldserver.client.BoatConnectionInterface#disconnect()
	 */
	public synchronized void disconnect() {
		if (writeThread != null) {
			writeThread.stopThread();
			writeThread.interrupt();
		}
		writeThread = null;
		if (serialPort != null) {
			try {
				inStream.close();
				outStream.close();
				inStream = null;
				outStream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			Thread closeThread = new Thread(new Runnable() {
				public void run() {
					SerialPort port = serialPort;
					serialPort = null;
					port.close();
				}
			});
			closeThread.setDaemon(true);
			closeThread.start();
		}
		isConnected = false;
	}

	private class WriteThread extends Thread {
		private ArrayList<byte[]> messages;
		private volatile boolean stop;

		public WriteThread() {
			super("Communicator.WriteThread");
			messages = new ArrayList<byte[]>(20);
			stop = false;
			setDaemon(true);
		}

		public int writeMsg(byte[] msg) {
			int size;
			synchronized (messages) {
				messages.add(msg);
				size = messages.size();
			}
			synchronized (writeSignal) {
				writeSignal.notify();
			}
			return size;
		}

		public void stopThread() {
			stop = true;
		}

		@Override
		public void run() {
			byte[] msg;
			stop = false;
			while (!stop && !isInterrupted()) {
				msg = null;
				synchronized (messages) {
					if (!messages.isEmpty()) {
						msg = messages.get(0);
						messages.remove(0);
					}
				}
				if (msg != null) {
					try {
						//System.out.println("Sending: "+Arrays.toString(msg));
						outStream.write(msg);
						outStream.flush();
					} catch (IOException e) {
						// jeah, aehm...
						break;
					}
				} else {
					try {
						synchronized (writeSignal) {
							writeSignal.wait(1000);
						}
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		}
	}
}
