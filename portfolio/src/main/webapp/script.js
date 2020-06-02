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

/**
 * Adds a random greeting to the page.
 */
function addRandomSong() {
  const songs =
      ['134340 by BTS', '0 Mile by NCT 127', 'Ghungroo by Arjit Singh and Shilpa Rao', 'Kangna by Dr Zeus', 'New Romantics by Taylor Swift', 'Beat Of My Drum by POWERS', 'Without You by Oh Wonder', 'High On Humans by Oh Wonder', 'Fresh Static Snow by Porter Robinson', 'Icarus by Madeon'];

  // Pick a random greeting.
  const song = songs[Math.floor(Math.random() * songs.length)];

  // Add it to the page.
  const songContainer = document.getElementById('song-container');
  songContainer.innerText = song;
}

function getRandomSong() {
    console.log('Getting a random song.');

    // The fetch() function returns a Promise
    const responsePromise = fetch('/data');

    // When the request is complete, pass the response into handleResponse().
    responsePromise.then(handleResponse);
}

function handleResponse(response) {
    console.log('Handling the response.');

    const textPromise = response.text();

    textPromise.then(addSongToDom);
}

function addSongToDom(song) {
    console.log('Adding song to dom: ' + song);

    const songContainer = document.getElementById('song-container');
    songContainer.innerText = song;
}
