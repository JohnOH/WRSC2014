package worldserver.io;

import java.io.*;
import java.util.*;

public class WorldLogger extends Thread {
	
	private ObjectOutputStream out;
	PrintStream otherOut = null;
	private String logFile;
	public static long loggedObjects = 0;
        public final int FLUSH_CNT = 100;
        public final int REOPEN_CNT = 10000;
        public volatile boolean stop = false;
        protected Vector<Serializable> buffer = null;

	public WorldLogger() {
		ObjectOutputStream stream = null;
		logFile = "log/WorldLog " + new Date().toString().replace(':', '_').replace(' ', '_');
		try {
			stream = new ObjectOutputStream(new FileOutputStream(logFile + "_0.log", false));
			stream.writeObject("+++++++ Logging started at "+new Date().toString());
			loggedObjects++;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out = stream;
		}
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					out.writeObject("+++++++ Logging ended at "+new Date().toString());
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}));
                this.buffer = new Vector<Serializable>();
	}
	
	public WorldLogger(PrintStream textualOut) {
		this();
		otherOut = textualOut;
		otherOut.println("+++++++ Logging started at "+new Date().toString());
	}

        public void run() {
            while (!stop) {
                writeBuffer();
            }
        }

        public synchronized void log(Serializable record) {
            this.buffer.add(record);
            this.notify();
        }

        protected synchronized void writeBuffer() {
            while (getBufferSize() < 1) {
                try {
                    wait();
                } catch (InterruptedException ie) {
                }
            }
            while (getBufferSize() > 0) {
                logRecord(getRecord());
            }
        }

        protected synchronized int getBufferSize() {
            return this.buffer.size();
        }

        protected synchronized Serializable getRecord() {
            return this.buffer.remove(0);
        }

	protected void logRecord(Serializable record) {
		try {
                    if (out == null)
                        try {
                            out = new ObjectOutputStream(new FileOutputStream(logFile + "_" + (loggedObjects / REOPEN_CNT) + ".log", false));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
			out.writeObject(record);
//                    if (flushCnt++ > FLUSH_CNT) {
//                        out.flush();
//                        flushCnt = 0;
//                    }
			loggedObjects++;
			if (loggedObjects % FLUSH_CNT == 0) {
                                out.flush();
                                System.out.println("# objects: " + loggedObjects);
                                out.reset();
			}
			if (loggedObjects % REOPEN_CNT == 0) {
                                out.close();
                                try {
                                    out = new ObjectOutputStream(new FileOutputStream(logFile + "_" + (loggedObjects/REOPEN_CNT) + ".log", false));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
			}
			if (otherOut != null) {
				otherOut.println(record.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Object> readLog(File logFile) throws FileNotFoundException, IOException {
		List<Object> objects = new ArrayList<Object>((int) (logFile.length()/150));
		// 150 byte ist in etwa die Gr��e eines SailBoat Objekts
		FileInputStream fis = new FileInputStream(logFile);
		ObjectInputStream in = new ObjectInputStream(fis);
		Object readObject;
		try {
			while ((readObject = in.readObject()) != null) {
				objects.add(readObject);
				if (readObject instanceof String) {
					String string = (String) readObject;
					if (string.contains("ended")) {
						in = new ObjectInputStream(fis);
					}
				}
			}
		} catch (EOFException eof) {
			// end of stream reached
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
		return objects;
	}
	
//	public static void main(String[] args) throws FileNotFoundException, IOException {
//		long start = System.currentTimeMillis();
//		Object obj = readLog(new File("log/WorldLog Fri Aug 13 12-59-33 CEST 2010.txt"));
//		System.out.println("done after " + (System.currentTimeMillis()-start) +" ms");
//	}
}
