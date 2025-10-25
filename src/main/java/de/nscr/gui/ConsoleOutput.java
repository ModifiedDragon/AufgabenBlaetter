package de.nscr.gui;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ConsoleOutput extends OutputStream {
    private final StringBuilder buffer = new StringBuilder();

    @Override
    public synchronized void write(int b) {
        buffer.append((char) b);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) {
        buffer.append(new String(b, off, len, StandardCharsets.UTF_8));
    }

    public synchronized String getAndClear() {
        String out = buffer.toString();
        buffer.setLength(0);
        return out;
    }
}
