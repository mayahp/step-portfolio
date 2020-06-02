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

    private List<String> songs;

    @Override
    public void init() {
        songs = new ArrayList<>();
        songs.add("134340 by BTS");
        songs.add("Boy With Luv by BTS");
        songs.add("Tempo by EXO");
        songs.add("Style by Taylor Swift");
        songs.add("Without You by Oh Wonder");
        songs.add("High on Humans by Oh Wonder");
        songs.add("Fresh Static Snow by Porter Robinson");
        songs.add("Divinity by Porter Robinson");
        songs.add("Technicolor by Madeon");
        songs.add("Pop Culture by Madeon");
        songs.add("Icarus by Madeon");
    }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String song = songs.get((int) (Math.random() * songs.size()));
    response.setContentType("text/html;");
    response.getWriter().println(song);
  }
}
