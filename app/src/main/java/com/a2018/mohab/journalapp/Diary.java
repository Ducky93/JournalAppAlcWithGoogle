package com.a2018.mohab.journalapp;

class Diary {
    public String name;
    public String diaryId;

    public Diary() {
    }

    Diary(String name, String diaryId) {
        this.name = name;
        this.diaryId = diaryId;
    }
    public String getName() {
        return name;
    }

    public String getDiaryId() {
        return diaryId;
    }
}
