package com.netcracker.edu.backend.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "lesson_time", schema = "project")
public class LessonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "begin_time")
    private Timestamp begin;

    @Column(name = "end_time")
    private Timestamp end;

    public LessonTime() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getBegin() {
        return begin;
    }

    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonTime that = (LessonTime) o;
        return id == that.id &&
                begin.equals(that.begin) &&
                end.equals(that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, begin, end);
    }
}
