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
        document.getElementById('liste').style.display = "block"

        let dataToSend = getFormData();
        if (dataToSend === null) {
            return;
        }
        let urlQuery = dataToSend[0];
        let data = dataToSend[1];

        // Envoyer les données au backend via une requête AJAX
        $.ajax({
            type: 'GET',
            url: urlQuery,
            data: data,
            success: function (response) {
                // Traitement de la réponse du backend en cas de succès
                console.log(response)
                itenaries = response

                if (itenaries.length === 0) {
                    alert("Station inexistantes dans le réseau de transport")
                }
                //hide all det from previous search
                for (var i = 1; i <= 5; i++) {
                    document.getElementById("det" + i).style.display = "none";
                }
                for (let index = 0; index < itenaries.length; index++) {
                    buildTraject(index + 1);
                    displayTraject(index + 1);
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
        // si l'horaire d'arrivée est inférieur à l'horaire de départ, on ajoute 1 jour à l'horaire d'arrivée (on arrive le lendemain)
        var traject_duration = horaire_arrivee > horaire_depart ? new Date(Math.abs(horaire_arrivee - horaire_depart)) : new Date(Math.abs(horaire_arrivee.setDate(horaire_arrivee.getDate() + 1) - horaire_depart));
        return traject_duration.getUTCHours() !== 0 ? traject_duration.getUTCHours() + " h " + traject_duration.getUTCMinutes() + ' min' : traject_duration.getUTCMinutes() + ' min';
    }

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
    console.log("LITINERARY")
    console.log(itenerary);
    if(itenerary.length == 1) {
        let polyline = L.polyline([
            [itenerary[0].depart.localisation.latitude, itenerary[0].depart.localisation.longitude],
            [itenerary[0].arrivee.localisation.latitude, itenerary[0].arrivee.localisation.longitude]
        ],{color: 'black',
            weight: 8,
            dashArray: [10, 20]
        }).addTo(map);
        itineraryPolylines.push(polyline);
        return;
    }
    let i = 0
    itenerary.forEach(section => {
        let lineColor;
        let lignetmp;
        //check if name == depart and name == arrivee
        if (section.depart.nomLieu === "Départ" && section.arrivee.nomLieu === "Arrivée") {
            if(prevValues){
                let latitude = section.depart.localisation.latitude;
                let longitude = section.depart.localisation.longitude;
                let nom_station = section.depart.nomLieu;
                const marker = L.marker([latitude, longitude]).addTo(map)
                    .bindPopup(`<b>${nom_station}</b>`).openPopup();
                itineraryMarkers.push(marker);
                const style = window.getComputedStyle(document.documentElement);
                lineColor = style.getPropertyValue('--ligne' + prevValues.ligne);
                polyline = L.polyline([prevValues.marker.getLatLng(), marker.getLatLng()], {
                    color: lineColor,
                    weight: 8
                }).addTo(map);
                itineraryPolylines.push(polyline);
            }

            //do a line between depart and arrivee as pointillés
            polyline = L.polyline([
                [section.depart.localisation.latitude, section.depart.localisation.longitude],
                [section.arrivee.localisation.latitude, section.arrivee.localisation.longitude]
            ],{color: 'black',
                weight: 8,
                dashArray: [10, 20]
            }).addTo(map);
            itineraryPolylines.push(polyline);
            prevValues = null;
        }

        else{
            // Check if section.ligne exists
            if (section.ligne == null) {
                lignetmp = {nomLigne: "sectionMarche"};
            } else {
                lignetmp = section.ligne.nomLigne.split(' ')[0];
            }

            let latitude = section.depart.localisation.latitude;
            let longitude = section.depart.localisation.longitude;
            let nom_station = section.depart.nomLieu;
            const marker = L.marker([latitude, longitude]).addTo(map)
                .bindPopup(`<b>${nom_station}</b>`).openPopup();
            itineraryMarkers.push(marker);


            if (prevValues) {
                i = i + 1;
                console.log("a la "+ i +" itération mon ancienne ligne est " + prevValues.ligne.nomLigne)
                console.log("Je rentre ici pour la "+ i +" fois avec station comme départ" + section.depart.nomLieu + " et station comme arrivée" + section.arrivee.nomLieu)
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

            let polyline2;
            if (section.arrivee.nomLieu !== FIN) {
                prevValues = {marker: marker, ligne: lignetmp};
            } else {
                if (section.ligne == null) { //Donc si il faut marcher à la fin
                    let latitude = section.arrivee.localisation.latitude;
                    let longitude = section.arrivee.localisation.longitude;
                    let nom_station = "Arrivée";
                    const markerARR = L.marker([latitude, longitude]).addTo(map)
                        .bindPopup(`<b>${nom_station}</b>`).openPopup();
                    itineraryMarkers.push(markerARR);
                    //Now draw the polyline between marker and markerARR
                    polyline2 = L.polyline([marker.getLatLng(), markerARR.getLatLng()], {
                        color: 'black',
                        weight: 8,
                        dashArray: [10, 20]
                    }).addTo(map);
                    itineraryPolylines.push(polyline2);
                }
            }
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

function displayTraject(idTraject) {
    let name = "det" + idTraject.toString();
    document.getElementById(name).style.display = "block";
}

function isEmpty(field) {
    if (field === '' || field === null || field === undefined) {

        var afficher_message = document.getElementById('chercher')
        var messageDiv = document.createElement("div");
        messageDiv.setAttribute("id", "errorSig");
        messageDiv.innerHTML = "Veuillez remplir les champs";
        afficher_message.appendChild(messageDiv);

        setTimeout(function () {
            afficher_message.removeChild(messageDiv);
        }, 3000);

        return true;
    }
    return false;
}

function checkEmptyAndAlert(field, message) {
    if (field === '' || field === null || field === undefined) {
        alert('Veuillez remplir le champ ' + message);
        return true;
    }
    return false;
}


// fonction qui permet de recuperer les données à envoyer à l'API selon l'option de trajet selectionnée
function getFormData() {

    var origine = $('#origine').val();
    var destination = $('#destination').val();
    var timeValue = $('#hour').val();

    if (isEmpty(origine) || isEmpty(destination) || isEmpty(timeValue)) {
        console.log("origine ou destination vide")
        return;
    }

    var selectedOption = $('input[name="typetrajet"]:checked').val();
    var data = {}
    var url = ''
    if (selectedOption === 'lazy0') {

        url = 'itinerary/optimal'
        data = {
            "origin": origine,
            "destination": destination,
            "time": timeValue
        }
    } else if (selectedOption === 'lazy1') {
        var distanceMax = $('#lazy_distance').val();
        if (checkEmptyAndAlert(distanceMax, 'distance maximale')) {
            return null;
        }
        url = 'itinerary/lazy'
        data = {
            "origin": origine,
            "destination": destination,
            "time": timeValue,
            "distanceMax": distanceMax
        }
    } else if (selectedOption === 'sport0') {
        url = 'itinerary/fullSport'
        data = {
            "origin": origine,
            "destination": destination,
            "time": timeValue
        }
    } else if (selectedOption === 'sport1') {
        var distanceMin = $('#sport_distance').val();
        if (checkEmptyAndAlert(distanceMin, 'distance minimale')) {
            return null;
        }
        url = 'itinerary/sport/distance'
        data = {
            "origin": origine,
            "destination": destination,
            "time": timeValue,
            "distanceMin": distanceMin
        }
    } else if (selectedOption === 'sport2') {
        var walkingTimeMax = $('#sport_minutes').val();
        if (checkEmptyAndAlert(walkingTimeMax, 'temps de marche')) {
            return null;
        }
        url = 'itinerary/sport/time'
        data = {
            "origin": origine,
            "destination": destination,
            "time": timeValue,
            "walkingTimeMax": walkingTimeMax
        }
    }
    return [url, data]
}
