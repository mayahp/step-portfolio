package com.google.sps.data;

// A user that logs in.
public final class User {
    public final long timestamp;
    public final String username;
    public final String url;

    public User(long timestamp, String username, String url) {
        this.timestamp = timestamp;
        this.username = username;
        this.url = url;
    }
}