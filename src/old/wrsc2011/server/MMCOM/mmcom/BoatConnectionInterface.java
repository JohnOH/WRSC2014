package mmcom;

import java.io.IOException;
import java.util.Observer;

public interface BoatConnectionInterface {

	public abstract boolean connect();

	public abstract void writeToStream(final byte[] msg) throws IOException;
	
	public abstract void addObserver(Observer o);

	public abstract boolean isConnected();

	public abstract void disconnect();

}