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

    private List<String> factList;

    @Override
    public void init() {
        factList = new ArrayList<>();
        factList.add("On my mom's side of the family, I'm the oldest of my generation.");
        factList.add("I have two younger sisters.");
        factList.add("I once won a jigsaw puzzle competition.");
        factList.add("After graduating, I want to volunteer as CS instructor along with my full-time job.");
        factList.add("My entire extended family immigrated to the US.");
        factList.add("I am the oldest of all of my immediate cousins.");
        factList.add("My first large team coding project was done with an international team.");
        factList.add("I'm interested in K-Pop.");
        factList.add("I grew up playing with my younger cousins on a regular basis.");

    }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String randomFact = factList.get((int) (Math.random() * factList.size()));
    response.setContentType("text/html;");
    response.getWriter().println(randomFact);
  }
}
