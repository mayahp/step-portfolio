package com.google.sps.data;

// A comment that a user makes.
public final class Comment {
    public final long timestamp;
    public final String name;
    public final String textContent;

    public Comment(long timestamp, String name, String textContent) {
        this.timestamp = timestamp;
        this.name = name;
        this.textContent = textContent;
    }
}