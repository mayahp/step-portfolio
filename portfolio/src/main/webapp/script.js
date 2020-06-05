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

function updateFactContainerWithRandomFact() {
    console.log('Getting a random fact.');
    const facts = ['On my mom\'s side of the family, I\'m the oldest of my generation.',
    'I have two younger sisters.', 'I once won a jigsaw puzzle competition.',
    'After graduating, I want to volunteer as CS instructor along with my full-time job.',
    'My entire extended family immigrated to the US.',
    'I am the oldest of all of my immediate cousins.',
    'My first large team coding project was done with an international team.',
    'I\'m interested in K-Pop.', 'I grew up playing with my younger cousins on a regular basis.'];

    const fact = facts[Math.floor(Math.random() * facts.length)];
    const factContainer = document.getElementById('fact-container');
    factContainer.innerText = fact; 
}

function getComments() {
    // Build the list of comment entries.
    fetch('/data')
        .then(response => response.json())
        .then((comments) => {
            const commentListElement = document.getElementById('comment-container');
            comments.forEach((comment) => {
                commentListElement.appendChild(createCommentElement(comment));
            });
    });    
}

function createCommentElement(comment) {
    const commentElement = document.createElement('li');
    commentElement.className = 'comment';

    const name = comment.name;
    const text = comment.textContent;
    console.log(typeof text);
    const fullComment = name + ': ' + text;

    commentElement.innerText = fullComment;
    return commentElement;
}

function handleResponse(response) {
    console.log('Handling the response.');
    const factTextPromise = response.text();
    factTextPromise.then(addFactText);
}

function addFactText(fact) {
    console.log('Adding fact to dom: ' + fact);
    const factContainer = document.getElementById('fact-container');
    factContainer.innerText = fact;
}