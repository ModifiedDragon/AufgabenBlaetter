package de.nscr.gui;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
public class SchlangenEingabe extends InputStream {
    private final BlockingQueue<byte[]> schlange = new LinkedBlockingQueue<>();
    private byte[] jetziger = null;
    private int position = 0;

    /**
     * Dazu da, die Eingaben aus dem GUI zu übertragen auf das Background thread Aufgaben
     * @param string Eingabe aus dem GUI vom Benutzer
     */
    public void inputEinfuegen(String string) {
        try {
            byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
            schlange.add(bytes);
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
        while (jetziger == null || position >= jetziger.length) {
            try {
                jetziger = schlange.take();
                position = 0;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IOException("Unterbrochen", ex);
            }
        }
        int ergebnis = jetziger[position++] & 0xFF;
        if (position >= jetziger.length) {
            jetziger = null;
        }
        return ergebnis;
    }
}
