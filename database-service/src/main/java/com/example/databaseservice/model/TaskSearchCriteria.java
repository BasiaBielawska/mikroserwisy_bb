package com.example.databaseservice.model;

import java.time.Instant;

public class TaskSearchCriteria {

    private String description;
    private boolean done;
    private Instant deadlineFrom;
    private Instant deadlineTo;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Instant getDeadlineFrom() {
        return deadlineFrom;
    }

    public void setDeadlineFrom(Instant deadlineFrom) {
        this.deadlineFrom = deadlineFrom;
    }

    public Instant getDeadlineTo() {
        return deadlineTo;
    }

    public void setDeadlineTo(Instant deadlineTo) {
        this.deadlineTo = deadlineTo;
    }
}
