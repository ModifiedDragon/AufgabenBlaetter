package de.nscr.gui;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
public class QueueInputStream extends InputStream {
    private final BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();
    private byte[] current = null;
    private int pos = 0;

    /**
     *
     * @param s
     */
    public void addInput(String s) {
        try {
            byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
            queue.add(bytes);
        } catch (Exception ex) {
            System.out.println("ERROR in addInput(): " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     *
     * @return
     * @throws IOException
     */
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
        int result = current[pos++] & 0xFF;
        if (pos >= current.length) {  // Optional: Reset after full read
            current = null;
        }
        return result;
    }
}
