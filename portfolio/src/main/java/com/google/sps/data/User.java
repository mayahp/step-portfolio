package com.google.sps.data;

// A user that logs in.
public final class User {
    public final long timestamp;
    public final String username;
    public final String buttonRedirectURL;

    public User(long timestamp, String username, String buttonRedirectURL) {
        this.timestamp = timestamp;
        this.username = username;
        this.buttonRedirectURL = buttonRedirectURL;
    }
}