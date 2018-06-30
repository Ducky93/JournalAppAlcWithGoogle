package com.a2018.mohab.journalapp;

public class DiaryEntry {
    private String entryId;
    private String title;
    private String description;

    public String getEntryId() {
        return entryId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public DiaryEntry() {

    }

    DiaryEntry(String entryId, String title, String description) {
        this.entryId = entryId;
        this.title = title;
        this.description = description;
    }
}
