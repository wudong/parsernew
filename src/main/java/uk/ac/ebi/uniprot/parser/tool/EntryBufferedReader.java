package uk.ac.ebi.uniprot.parser.tool;

import com.google.common.base.Preconditions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Reading Entry using memory mapped file..
 * <p/>
 * <p/>
 * User: wudong, Date: 07/05/2013, Time: 11:27
 */
public class EntryBufferedReader {

	private long entryStartPosition = 0;
	private long entryEndPosition = 0;

	private long bufferStartPosition = 0;

	private final FileChannel channel;
	private final byte[] entryEnding = "//\n".getBytes();
	private ByteBuffer buffer;
	private boolean getFromFile = true;
	final private int size = 1024 * 1024 * (1024 * 2 - 10);
	//2G-10M, to make it less than Integer.Max_value while still a page aligned size

	public EntryBufferedReader(String fileName) throws FileNotFoundException {
		final RandomAccessFile channel1 = new RandomAccessFile(fileName, "r");
		channel = channel1.getChannel();
	}

	private long timeUserdInAllocateBuffer = 0;
	private long totalTimeUsedScanning = 0;

	/**
	 * Time used are measured in Nano seconds.
	 * @return
	 */
	public long getTimeUserdInAllocateBuffer() {
		return timeUserdInAllocateBuffer/(1000*1000*1000);
	}

	/**
	 * Time used are measured in Nano seconds.
	 * @return
	 */
	public long getTotalTimeUsedScanning() {
		return totalTimeUsedScanning / (1000*1000*1000);
	}

	//create buffer.
	private boolean reNewBuffer() throws IOException {
		if (entryEndPosition >= channel.size()) return false;
		else {
			final long currentTime = System.nanoTime();
			bufferStartPosition = entryEndPosition;
			entryStartPosition = entryEndPosition;

			final long remainingLength = channel.size() - bufferStartPosition;
			int buffersize = (int) (remainingLength >= size ? size : remainingLength);

			if (buffer != null) {
				buffer = null;
				System.gc();
			}

			buffer = channel.map(FileChannel.MapMode.READ_ONLY, bufferStartPosition, buffersize);
			getFromFile = false;

			timeUserdInAllocateBuffer += (System.nanoTime() - currentTime);
			return true;
		}
	}

	public void reset() {
		entryStartPosition = 0;
		entryEndPosition = 0;
		bufferStartPosition = 0;
		getFromFile = true;
		timeUserdInAllocateBuffer = 0;
		totalTimeUsedScanning = 0;
	}

	/**
	 * Return false while cannot find the ending. which means the buffer need reread.
	 *
	 * @param buffer
	 * @return
	 */
	private boolean seekingEntryEndInBuffer(ByteBuffer buffer) {
		out:
		while (buffer.remaining() >= entryEnding.length) {
			for (int j = 0; j < entryEnding.length; j++) {
				final byte b = buffer.get();
				if (b != entryEnding[j]) {
					continue out;
				}
			}
			entryEndPosition = buffer.position() + bufferStartPosition;
			return true;
		}
		return false;
	}

	public String next() throws IOException {
		final long currentTime = System.nanoTime();

		if (getFromFile) {
			boolean b = reNewBuffer();
			if (!b) return null;
		}

		final boolean b = seekingEntryEndInBuffer(buffer);
		//not enough
		if (!b) {
			final boolean b2 = reNewBuffer();
			if (!b2) return null;

			final boolean b1 = seekingEntryEndInBuffer(buffer);
			Preconditions.checkState(b1, "if exception happened here, the size is setting incorrectly");
		}

		final byte[] bytes = new byte[(int) (entryEndPosition - entryStartPosition)];
		buffer.position((int) (entryStartPosition - bufferStartPosition));
		buffer.get(bytes);

		entryStartPosition = entryEndPosition;

		this.totalTimeUsedScanning += (System.nanoTime() - currentTime);

		return new String(bytes);
	}
}
