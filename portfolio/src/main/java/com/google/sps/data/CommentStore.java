/**
    Class representing the list of comments stored on the site.
*/

package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

public class CommentStore {
    // List comments with the commentor's name + comment.
    private final List<String> comments = new ArrayList<>();

    public void addComment(String name, String text) {
        String comment = name + ": " + text;
        comments.add(comment);
    }
}