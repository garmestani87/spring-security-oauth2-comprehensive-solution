package com.garm.security.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CustomServletOutputStream extends ServletOutputStream {

    private final OutputStream outputStream;
    private final ByteArrayOutputStream copy;
    private long counter;
    private boolean written;

    public CustomServletOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream();
        this.counter = 0;
        written = false;
    }

    private void writeBuf() throws IOException {
        outputStream.write(copy.toByteArray());
        outputStream.flush();
        written = true;
    }

    @Override
    public void write(final int bytes) throws IOException {
        int buffer_size = 1024 * 1024 * 1024;
        if (counter > buffer_size) {
            outputStream.write(bytes);
        } else {
            copy.write(bytes);
            if (counter == buffer_size) {
                writeBuf();
            }
            counter++;
        }
    }

    public void reallyFlush() throws IOException {
        if (!written) {
            writeBuf();
        }
    }

    public boolean isCommitted() {
        return written;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(final WriteListener writeListener) {
    }
}