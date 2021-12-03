package com.github.sjlian014.jlmsclient.model;

public class Semester {

    public enum SemesterType {
        FALL, SPRING, SUMMER;
    }

    private SemesterType semester;
    private int year;

    protected Semester() {}

    public Semester(SemesterType semester, int year) {
        this.semester = semester;
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public SemesterType getSemester() {
        return semester;
    }

    public void setSemester(SemesterType semester) {
        this.semester = semester;
    }

}
