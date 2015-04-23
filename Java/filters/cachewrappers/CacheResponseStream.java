//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-02-23
//Revisions
//------------------------------------------------------------------------------
//$Log: CacheResponseStream.java,v $
//Revision 1.1.2.1  2005/02/23 16:30:35  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package filters.cachewrappers;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tcs
 *
 */
public class CacheResponseStream extends ServletOutputStream {

	protected boolean closed = false;
	protected HttpServletResponse response = null;
	protected ServletOutputStream output = null;
	protected OutputStream cache = null;

	/**
	 * @param response
	 * @param cache
	 * @throws IOException
	 */
	public CacheResponseStream(HttpServletResponse response, OutputStream cache)
			throws IOException {
		super();
		closed = false;
		this.response = response;
		this.cache = cache;
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		if (closed) {
			throw new IOException("This output stream has already been closed");
		}
		cache.close();
		closed = true;
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		if (closed) {
			throw new IOException("Cannot flush a closed output stream");
		}
		cache.flush();
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		}
		cache.write((byte) b);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte b[], int off, int len) throws IOException {
		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		}
		cache.write(b, off, len);
	}

	/**
	 * @return
	 */
	public boolean closed() {
		return (this.closed);
	}

	/**
	 * 
	 */
	public void reset() {
		// noop
	}
}