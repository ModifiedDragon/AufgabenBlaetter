package de.nscr.gui;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class QueueInputStream extends InputStream {
    private final BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();
    private byte[] current = null;
    private int pos = 0;

    // GUI calls this to add input (include newline)
    public void addInput(String s) {
        queue.add(s.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public int read() throws IOException {
        while (current == null || pos >= current.length) {
            try {
                current = queue.take(); // blocks until input available
                pos = 0;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted", ex);
            }
        }
        return current[pos++] & 0xFF;
    }
}
