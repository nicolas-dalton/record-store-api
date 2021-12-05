package com.company.recordstoreapi.model;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class Record {
    @NotEmpty(message= "Artist must not be empty")
    private String artist;
    @NotEmpty(message= "Album must not be empty")
    private String album;
    @NotEmpty(message= "Year must not be empty")
    private String year;
    private int id;

    public Record() { }

    public Record(String artist, String album, String year, int id) {
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return getId() == record.getId() &&
                Objects.equals(getArtist(), record.getArtist()) &&
                Objects.equals(getAlbum(), record.getAlbum()) &&
                Objects.equals(getYear(), record.getYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArtist(), getAlbum(), getYear(), getId());
    }
}
