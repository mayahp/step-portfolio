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
var map;
var upenn;
var currentInfoWindow;

function start() {
    getComments();
    initMap();
}

function initMap() {
    upenn = {
        lat: 39.9522229,
        lng: -75.1954024
    };
    const hill = {
        lat: 39.9530122,
        lng: -75.1928656
    };
    const zeta = {
        lat: 39.9543422,
        lng: -75.2060464
    };
    const eng = {
        lat: 39.9517975,
        lng: -75.1931415
    };
    const sobol = {
        lat: 39.9510881,
        lng: -75.2009277
    };
    const love = {
        lat: 39.9517666,
        lng: -75.1962875
    };

    map = new google.maps.Map(
        document.getElementById('map'), {
            center: upenn,
            zoom: 16,
            styles: [{
                    elementType: 'geometry',
                    stylers: [{
                        color: '#242f3e'
                    }]
                },
                {
                    elementType: 'labels.text.stroke',
                    stylers: [{
                        color: '#242f3e'
                    }]
                },
                {
                    elementType: 'labels.text.fill',
                    stylers: [{
                        color: '#746855'
                    }]
                },
                {
                    featureType: 'administrative.locality',
                    elementType: 'labels.text.fill',
                    stylers: [{
                        color: '#d59563'
                    }]
                },
                {
                    featureType: 'poi',
                    elementType: 'labels.text.fill',
                    stylers: [{
                        color: '#d59563'
                    }]
                },
                {
                    featureType: 'poi.park',
                    elementType: 'geometry',
                    stylers: [{
                        color: '#263c3f'
                    }]
                },
                {
                    featureType: 'poi.park',
                    elementType: 'labels.text.fill',
                    stylers: [{
                        color: '#6b9a76'
                    }]
                },
                {
                    featureType: 'road',
                    elementType: 'geometry',
                    stylers: [{
                        color: '#38414e'
                    }]
                },
                {
                    featureType: 'road',
                    elementType: 'geometry.stroke',
                    stylers: [{
                        color: '#212a37'
                    }]
                },
                {
                    featureType: 'road',
                    elementType: 'labels.text.fill',
                    stylers: [{
                        color: '#9ca5b3'
                    }]
                },
                {
                    featureType: 'road.highway',
                    elementType: 'geometry',
                    stylers: [{
                        color: '#746855'
                    }]
                },
                {
                    featureType: 'road.highway',
                    elementType: 'geometry.stroke',
                    stylers: [{
                        color: '#1f2835'
                    }]
                },
                {
                    featureType: 'road.highway',
                    elementType: 'labels.text.fill',
                    stylers: [{
                        color: '#f3d19c'
                    }]
                },
                {
                    featureType: 'transit',
                    elementType: 'geometry',
                    stylers: [{
                        color: '#2f3948'
                    }]
                },
                {
                    featureType: 'transit.station',
                    elementType: 'labels.text.fill',
                    stylers: [{
                        color: '#d59563'
                    }]
                },
                {
                    featureType: 'water',
                    elementType: 'geometry',
                    stylers: [{
                        color: '#17263c'
                    }]
                },
                {
                    featureType: 'water',
                    elementType: 'labels.text.fill',
                    stylers: [{
                        color: '#515c6d'
                    }]
                },
                {
                    featureType: 'water',
                    elementType: 'labels.text.stroke',
                    stylers: [{
                        color: '#17263c'
                    }]
                }
            ]
        });

    const hillMarker = createMapMarker(hill, 'Hill College House', 'I lived in Hill College House in freshman year.', 'hill.jpg');
    const zetaMarker = createMapMarker(zeta, 'Zeta Tau Alpha Sorority', 'My sorority\'s house.  I joined Zeta Tau Alpha in January 2020.', 'lin.jpg');
    const engMarker = createMapMarker(eng, 'School of Engineering and Applied Sciences', 'I spend most of my time in the Engineering buildings.', 'eng.jpg');
    const sobolMarker = createMapMarker(sobol, 'SoBol', 'I love getting acai bowls from SoBol.', 'acai.jpg');
    const loveMarker = createMapMarker(love, 'Love Sculpture', 'The famous Love Sculpture is one of my favorite places on campus.', 'love.jpg');
}

function createMapMarker(place, title, text, img) {
    const marker = new google.maps.Marker({
        position: place,
        map: map,
        title: title
    });

    const infoWindow = new google.maps.InfoWindow({
        content: '<p>' + text + '</p>' + '<img src="images/' + img + '"/>' + '<br/>' + '<button onClick="panToCenter()">Back to Center</button>'
    });

    marker.addListener('click', function () {
        currentInfoWindow = infoWindow;
        map.setZoom(20);
        map.setCenter(marker.getPosition());
        infoWindow.open(map, marker);
    });
}

function panToCenter() {
    currentInfoWindow.close();
    map.setZoom(16);
    map.setCenter(upenn);
}

function updateFactContainerWithRandomFact() {
    console.log('Getting a random fact.');
    const facts = ['On my mom\'s side of the family, I\'m the oldest of my generation.',
        'I have two younger sisters.', 'I once won a jigsaw puzzle competition.',
        'After graduating, I want to volunteer as CS instructor along with my full-time job.',
        'My entire extended family immigrated to the US.',
        'I am the oldest of all of my immediate cousins.',
        'My first large team coding project was done with an international team.',
        'I\'m interested in K-Pop.', 'I grew up playing with my younger cousins on a regular basis.'
    ];

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