package com.leigq.quartz.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * 包装响应对象
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/24 10:50
 */
public class WrapperResponse extends HttpServletResponseWrapper {
	private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	private PrintWriter pwrite;

	WrapperResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() {
		// 将数据写到 byte 中
		return new MyServletOutputStream(bytes);
	}

	/**
	 * 重写父类的 getWriter() 方法，将响应数据缓存在 PrintWriter 中
	 */
	@Override
	public PrintWriter getWriter() {
		try {
			pwrite = new PrintWriter(new OutputStreamWriter(bytes, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pwrite;
	}

	/**
	 * 获取缓存在 PrintWriter 中的响应数据
	 */
	byte[] getBytes() {
		if (null != pwrite) {
			pwrite.close();
			return bytes.toByteArray();
		}

		if (null != bytes) {
			try {
				bytes.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		assert bytes != null;
		return bytes.toByteArray();
	}


	class MyServletOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream ostream;

		MyServletOutputStream(ByteArrayOutputStream ostream) {
			this.ostream = ostream;
		}

		@Override
		public void write(int b) {
			// 将数据写到 stream　中
			ostream.write(b);
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {

		}

	}
}