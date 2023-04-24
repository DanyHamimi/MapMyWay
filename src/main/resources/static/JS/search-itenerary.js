var itenaries;
const DEPART = "Début";
const FIN = "Arrivée";
const LIGNE = "ligne";
const WALK_CLASS_HTML = '<img class="marcher" src="../css/image/marche.png" alt="">'

var stationsByLocalisation = new Map();
var itineraryLayer = L.layerGroup();
var time

window.addEventListener('load', function () {
    fillCurrentHour();
});

function fillCurrentHour() {
    var date = new Date();
    var hours = date.getHours();
    var minutes = date.getMinutes();
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    time = hours + ":" + minutes;
    document.getElementById('hour').value = time;
}

$(document).ready(function () {

    $('#search-btn').click(function () {
        var origine = $('#origine').val();
        var destination = $('#destination').val();
        var timeValue = $('#hour').val();
        var data = {
            origin: origine,
            destination: destination,
            time: timeValue
        };


        // Envoyer les données au backend via une requête AJAX
        $.ajax({
            type: 'GET',
            url: '/iteneray',
            data: data,
            success: function (response) {
                // Traitement de la réponse du backend en cas de succès
                console.log(response)
                itenaries = response
                for (let index = 0; index < itenaries.length; index++) {
                    buildTraject(index + 1);
                }
                pingLocalizations(1);
            },
            error: function (xhr, status, error) {
                // Traitement de la réponse du backend en cas d'erreur
                console.log("failed")
                console.log(xhr.responseText);
                // Faire quelque chose en cas d'erreur
            }
        });
    });

    function addTrajectDetails(index) {
        var trajectDetails = document.getElementById("details" + index);
        var itenerary = itenaries[index - 1];
        var stationsNames = [];
        var detailsHtml = '<div class="detailsTrajet">'

        itenerary.forEach(section => {
            if (section.ligne != null) {
                stationsNames.push(section.depart.nomLieu + ";" + section.ligne.nomLigne.split(' ')[0] + ";" + section.depart.horaireDePassage);
            } else {
                if (section.arrivee.nomLieu === FIN) {
                    stationsNames.push(section.depart.nomLieu + " Arrivée");
                }
            }
        })

        var oldNumLigne = stationsNames[0].split(';')[1];
        var currentDiv = '<div class="groupe groupe' + oldNumLigne + '">';
        stationsNames.forEach(station => {

            var numLigne = station.split(';')[1];
            var stationName = station.split(';')[0];

            if (numLigne !== oldNumLigne) {
                // Fermer le div précédent s'il existe
                if (currentDiv !== '') {
                    currentDiv += '</div>';
                }
                // Créer un nouveau div pour le nouvel oldNumLigne
                currentDiv += '<div class="groupe groupe' + numLigne + '">';
            }

            var horaireDePassage = station.split(';')[2]
            if (horaireDePassage != null) {
                var heure = horaireDePassage.split(":")[0]
                var min = horaireDePassage.split(":")[1]
                currentDiv += '<div class="station nomStation' + numLigne + '">' + heure + "h" + min + " : " + stationName + '</div>';

            } else
                currentDiv += '<div class="station nomStation' + numLigne + '">' + stationName + '</div>';

            oldNumLigne = numLigne;

        });

        if (currentDiv !== '') {
            currentDiv += '</div>';
        }
        detailsHtml += currentDiv;
        trajectDetails.innerHTML = (detailsHtml)
    }

    function buildTraject(index) {
        // récupérer la indexième classe resultat
        var trajectResult = document.querySelectorAll('.resultat')[index - 1];
        // déclarer un set de noom de ligne
        var linesNames = new Set();
        var itenerary = itenaries[index - 1];
        var htmlContent = '<div class="testAffichage"><div class="ligne-container">';
        itenerary.forEach((section, i) => {
            if (section.depart.nomLieu !== DEPART && section.arrivee.nomLieu !== FIN) {
                if (section.ligne != null) {
                    linesNames.add(section.ligne.nomLigne.split(' ')[0]);
                } else {
                    linesNames.add('sectionMarche');
                }
            } else if (section.distance !== 0) {
                linesNames.add('sectionMarche');
            }
        });
        linesNames.forEach(line => {
            if (line === 'sectionMarche') {
                htmlContent += WALK_CLASS_HTML;
            } else {
                htmlContent += '<img src="../css/image/M' + line + '.png" alt="ligne ' + line + '" class="ligne" style="height: 30px;">';
            }
        })
        console.log("traject duration ===> " + buildDuration(index))
        htmlContent += '</div><div class="dureeTrajet">' + buildDuration(index) + '</div></div>'

        htmlContent += '<div class="details" id="details' + index + '"style="display: none"></div>'
        trajectResult.innerHTML = (htmlContent)
        addTrajectDetails(index);
    }


    function buildDuration(index) {
        var itenerary = itenaries[index - 1];
        var horaire_depart = parseISO8601Time(itenerary[0].depart.horaireDePassage);
        var horaire_arrivee = parseISO8601Time(itenerary[itenerary.length - 1].arrivee.horaireDePassage);
        var traject_duration = new Date(Math.abs(horaire_arrivee - horaire_depart));
        return traject_duration.getUTCHours() != 0 ? traject_duration.getUTCHours() + " h " + traject_duration.getUTCMinutes() + ' min' : traject_duration.getUTCMinutes() + ' min';
    }

    let markersAndLinesGroup = L.layerGroup().addTo(map);

    function parseISO8601Time(timeString) {
        const [hours, minutes, seconds] = timeString.split(':').map(Number);
        const date = new Date();
        date.setHours(hours);
        date.setMinutes(minutes);
        date.setSeconds(seconds);
        return date;
    }

    function parseISO8601Duration(durationString) {
        const durationRegex = /P((\d+)Y)?((\d+)M)?((\d+)D)?T?((\d+)H)?((\d+)M)?((\d+)S)?/;
        const matches = durationString.match(durationRegex);
        const years = matches[2] ? parseInt(matches[2]) : 0;
        const months = matches[4] ? parseInt(matches[4]) : 0;
        const days = matches[6] ? parseInt(matches[6]) : 0;
        const hours = matches[8] ? parseInt(matches[8]) : 0;
        const minutes = matches[10] ? parseInt(matches[10]) : 0;
        const seconds = matches[12] ? parseInt(matches[12]) : 0;
        const totalSeconds =
            years * 31536000 +
            months * 2592000 +
            days * 86400 +
            hours * 3600 +
            minutes * 60 +
            seconds;
        return totalSeconds;
    }

    function durationMinutes(durationString) {
        return Math.ceil(parseISO8601Duration(durationString) / 60);
    }


})
// Declare global arrays to hold markers and polylines
var itineraryMarkers = [];
var itineraryPolylines = [];

function pingLocalizations(index) {
    // Remove previous markers and polylines from the map
    itineraryMarkers.forEach(marker => marker.remove());
    itineraryPolylines.forEach(polyline => polyline.remove());

    // Clear the arrays for the new itinerary
    itineraryMarkers = [];
    itineraryPolylines = [];

    var itenerary = itenaries[index - 1];

    if (itenerary === undefined)
        return;
    var prevValues = null;
    itenerary.forEach(section => {
        let lineColor;
        let lignetmp;
        // Check if section.ligne exists
        if (section.ligne == null) {
            lignetmp = {nomLigne: "sectionMarche"};
        } else {
            lignetmp = section.ligne.nomLigne.split(' ')[0];
        }

        let longitude = section.depart.localisation.latitude;
        let latitude = section.depart.localisation.longitude;
        let nom_station = section.depart.nomLieu;
        const marker = L.marker([latitude, longitude]).addTo(map)
            .bindPopup(`<b>${nom_station}</b>`).openPopup();
        itineraryMarkers.push(marker);

        if (prevValues) {
            let polyline;
            if (prevValues.ligne.nomLigne === 'sectionMarche') {
                lineColor = 'black';
                polyline = L.polyline([prevValues.marker.getLatLng(), marker.getLatLng()], {
                    color: 'black',
                    weight: 8,
                    dashArray: [10, 20]
                }).addTo(map);
            } else {
                const style = window.getComputedStyle(document.documentElement);
                lineColor = style.getPropertyValue('--ligne' + prevValues.ligne);
                polyline = L.polyline([prevValues.marker.getLatLng(), marker.getLatLng()], {
                    color: lineColor,
                    weight: 8
                }).addTo(map);
            }

            itineraryPolylines.push(polyline);
        }

        if (section.arrivee.nomLieu !== FIN) {
            prevValues = {marker: marker, ligne: lignetmp};
        }
    });
}

function AfficheDetails(num) {
    var message = document.getElementById("details" + num);

    var allDetailsDivs = document.querySelectorAll(".details");
    for (var i = 0; i < allDetailsDivs.length; i++) {
        if (allDetailsDivs[i].style.display === "block" && allDetailsDivs[i] !== message) {
            allDetailsDivs[i].style.display = "none";
        }
    }

    if (message.style.display === "none") {
        message.style.display = "block"; // Show the message
        pingLocalizations(num);
    } else {
        message.style.display = "none"; // Hide the message
    }
}
