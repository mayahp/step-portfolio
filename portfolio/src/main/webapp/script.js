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

const COMMENT_ERROR_CODE = -1;

function start() {
    getComments();
    initMap();
}

function initMap() {
    console.log('wtf');
    const map = new google.maps.Map(
        document.getElementById('map'),
        {center: {
            lat: 40.3079168, 
            lng: -74.1408768
        }, 
        zoom: 16,
        styles: [
            {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
            {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
            {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
            {
                featureType: 'administrative.locality',
                elementType: 'labels.text.fill',
                stylers: [{color: '#d59563'}]
            },
            {
                featureType: 'poi',
                elementType: 'labels.text.fill',
                stylers: [{color: '#d59563'}]
            },
            {
                featureType: 'poi.park',
                elementType: 'geometry',
                stylers: [{color: '#263c3f'}]
            },
            {
                featureType: 'poi.park',
                elementType: 'labels.text.fill',
                stylers: [{color: '#6b9a76'}]
            },
            {
                featureType: 'road',
                elementType: 'geometry',
                stylers: [{color: '#38414e'}]
            },
            {
                featureType: 'road',
                elementType: 'geometry.stroke',
                stylers: [{color: '#212a37'}]
            },
            {
                featureType: 'road',
                elementType: 'labels.text.fill',
                stylers: [{color: '#9ca5b3'}]
            },
            {
                featureType: 'road.highway',
                elementType: 'geometry',
                stylers: [{color: '#746855'}]
            },
            {
                featureType: 'road.highway',
                elementType: 'geometry.stroke',
                stylers: [{color: '#1f2835'}]
            },
            {
                featureType: 'road.highway',
                elementType: 'labels.text.fill',
                stylers: [{color: '#f3d19c'}]
            },
            {
                featureType: 'transit',
                elementType: 'geometry',
                stylers: [{color: '#2f3948'}]
            },
            {
                featureType: 'transit.station',
                elementType: 'labels.text.fill',
                stylers: [{color: '#d59563'}]
            },
            {
                featureType: 'water',
                elementType: 'geometry',
                stylers: [{color: '#17263c'}]
            },
            {
                featureType: 'water',
                elementType: 'labels.text.fill',
                stylers: [{color: '#515c6d'}]
            },
            {
                featureType: 'water',
                elementType: 'labels.text.stroke',
                stylers: [{color: '#17263c'}]
            }
          ]
    });
}

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
            var commentCount = 0;
            comments.forEach((comment) => {
                if (comment.timestamp == COMMENT_ERROR_CODE) {
                    document.getElementById('error-message').textContent = "There are only " + commentCount + " comments.";
                } else {
                    commentListElement.appendChild(createCommentElement(comment));
                    commentCount++;
                }
            });
    });    
}

function createCommentElement(comment) {
    const commentElement = document.createElement('li');
    commentElement.className = 'comment';

    const name = comment.name;
    const text = comment.textContent;
    const fullComment = name + ': ' + text;

    commentElement.innerText = fullComment;
    return commentElement;
}