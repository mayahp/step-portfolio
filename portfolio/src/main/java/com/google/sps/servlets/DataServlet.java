// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
 
package com.google.sps.servlets;
 
import com.google.sps.data.CommentStore;
import com.google.sps.data.Comment;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
 
/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
 
    private CommentStore commentStore = new CommentStore();
 
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
 
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery comments = datastore.prepare(query);
 
        List<Comment> commentList = new ArrayList<>();
        for (Entity entity : comments.asIterable()) {
            long timestamp = (long) entity.getProperty("timestamp");
            String name = (String) entity.getProperty("name");
            String textContent = (String) entity.getProperty("textContent");
 
            Comment newComment = new Comment(timestamp, name, textContent);
            commentList.add(newComment);
        }
 
        Gson gson = new Gson();
 
        response.setContentType("application/json");
        response.getWriter().println(gson.toJson(commentList));
    }
 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = getParameter(request, "name-input", "");
        String textContent = getParameter(request, "comment-input", "");
        long timestamp = System.currentTimeMillis();
 
        Entity commentEntity = new Entity("Comment");
        commentEntity.setProperty("name", name);
        commentEntity.setProperty("textContent", textContent);
        commentEntity.setProperty("timestamp", timestamp);
 
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);
 
        response.sendRedirect("/index.html"); 
    }
 
    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
 
