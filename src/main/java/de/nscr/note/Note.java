package de.nscr.note;

import java.time.LocalDate;

public class Note {
    private String key;
    private String note;
    private LocalDate eDate;
    private LocalDate fDate;

    public Note(String key, String note, LocalDate eDate, LocalDate fDate) {
        this.key = key;
        this.note = note;
        this.eDate = eDate;
        this.fDate = fDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getfDate() {
        return fDate;
    }

    public void setfDate(LocalDate fDate) {
        this.fDate = fDate;
    }

    public LocalDate geteDate() {
        return eDate;
    }

    public void seteDate(LocalDate eDate) {
        this.eDate = eDate;
    }

    @Override
    public String toString() {
        return "Note{" +
                "key='" + key + '\'' +
                ", note='" + note + '\'' +
                ", eDate=" + eDate +
                ", fDate=" + fDate +
                '}';
    }
}
