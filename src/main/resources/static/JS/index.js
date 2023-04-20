const optionsBtn = document.getElementById('options');
const searchBtn = document.getElementById('chercher');

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
    var time = hours + ":" + minutes;
    document.getElementById('hour').value = time;
    console.log(time);
}

optionsBtn.addEventListener('click', (e) => {
    let parent = e.target.parentNode.parentNode;
    Array.from(e.target.parentNode.parentNode.classList).find((element) => {
        if (element !== "slide-up") {
            parent.classList.add('slide-up')
        } else {
            searchBtn.parentNode.classList.add('slide-up')
            parent.classList.remove('slide-up')
        }
    });
});

searchBtn.addEventListener('click', (e) => {
    let parent = e.target.parentNode;
    Array.from(e.target.parentNode.classList).find((element) => {
        if (element !== "slide-up") {
            parent.classList.add('slide-up')
        } else {
            optionsBtn.parentNode.parentNode.classList.add('slide-up')
            parent.classList.remove('slide-up')
        }
    });
});

// ... code d'initialisation de la carte et ajout du marqueur ...

// Gestionnaire d'événements pour supprimer le marqueur lors du clic sur le bouton
document.getElementById("remove-marker").addEventListener("click", function () {
    if (originMarker) {
        originMarker.remove();
        originMarker = null;
    }
    if (destinationMarker) {
        destinationMarker.remove();
        destinationMarker = null;
    }
});

// Gerer erreur saisie
const showValueButton = document.getElementById('search-btn');
const errorSig = document.getElementById('errorSig');

showValueButton.addEventListener('click', function () {

    var origine = document.getElementById('origine');
    var destination = document.getElementById('destination');
    var orig = origine.value;
    var dest = destination.value;

    if (orig != "" && dest != "") {
        // console.log("origine : " + orig + " destination : " + dest);

    } else {
        var afficher_message = document.getElementById('chercher')
        var messageDiv = document.createElement("div");
        messageDiv.setAttribute("id", "errorSig");
        messageDiv.innerHTML = "Veuillez remplir les champs";
        afficher_message.appendChild(messageDiv);

        setTimeout(function () {
            afficher_message.removeChild(messageDiv);
        }, 3000);
    }


});


let originMarker = null;
let destinationMarker = null;


$(function () {
    $("#origine, #destination").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "/autocomplete",
                dataType: "json",
                data: {
                    term: request.term
                },
                success: function (data) {
                    var res = data.map(function (item) {
                        var parts = item.split(';');
                        return {
                            label: parts[0],
                            value: item
                        };
                    });
                    response(res);
                }
            });
        },
        minLength: 1,
        select: function (event, ui) {
            //When clicking on a suggestion, fill the input with the suggestion
            this.value = ui.item.value.toString().split(";")[0];

            // Récupérer les coordonnées de la station sélectionnée
            let stationInfo = ui.item.value.split(";");
            let stationName = stationInfo[0];
            let stationLatitude = parseFloat(stationInfo[2]);
            let stationLongitude = parseFloat(stationInfo[1]);

            // Vérifier si l'élément sélectionné est "origine" ou "destination"
            if (this.id === "origine") {
                // Supprimer le marqueur précédent s'il existe
                if (originMarker) {
                    originMarker.remove();
                }

                // Créer un nouveau marqueur d'origine aux coordonnées spécifiées
                originMarker = L.marker([stationLatitude, stationLongitude]).addTo(map);
                originMarker.bindPopup(`<b>Origine: ${stationName}</b>`).openPopup();
            } else if (this.id === "destination") {
                // Supprimer le marqueur précédent s'il existe
                if (destinationMarker) {
                    destinationMarker.remove();
                }

                // Créer un nouveau marqueur de destination aux coordonnées spécifiées
                destinationMarker = L.marker([stationLatitude, stationLongitude]).addTo(map);
                destinationMarker.bindPopup(`<b>Destination: ${stationName}</b>`).openPopup();
            }

            // Empêcher la valeur sélectionnée de s'afficher dans le champ de saisie
            event.preventDefault();

            if (originMarker && destinationMarker) {
                // Obtenir les coordonnées des deux marqueurs
                let originLatLng = originMarker.getLatLng();
                let destinationLatLng = destinationMarker.getLatLng();

                // Créer un groupe de points pour les deux marqueurs
                let markersGroup = new L.LatLngBounds([originLatLng, destinationLatLng]);

                // Adapter le zoom et le centrage de la carte pour afficher les deux marqueurs
                map.fitBounds(markersGroup);
            }
        }
    });
});

var map = L.map('map').setView([48.858093, 2.294694], 15);
// Définir les limites de la carte
var bounds = L.latLngBounds(
    L.latLng(48.8156, 2.2242), // Coin inférieur gauche
    L.latLng(48.9022, 2.4699)  // Coin supérieur droit
);
map.setMaxBounds(bounds);

L.tileLayer('tiles/{z}/{x}/{y}.png', {
    minZoom: 12,
    maxZoom: 16,
    attribution: 'Map data © <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors'
}).addTo(map);


// Créer une fonction pour ajouter des marqueurs et des lignes rouges
function addStationMarkersAndLinesByList(trajets) {
    trajets.forEach(trajet => {
        let lineColor;
        switch (trajet[0]) {
            case "1":
                const div = document.getElementsByClassName("ligne1");

                const style = window.getComputedStyle(div[0]);
                const backgroundColor = style.backgroundColor;
                lineColor = backgroundColor;
                break;
            case "2":
                lineColor = "blue";
                break;
            // Ajouter d'autres couleurs pour les autres numéros de ligne
            default:
                lineColor = "black";
        }

        trajet[1].forEach(sectionTransport => {
            let lineCoordinates = [];
            for (let i = 0; i < sectionTransport.length; i++) {
                let station = sectionTransport[i];

                let marker = L.marker([station.latitude, station.longitude]).addTo(map);
                marker.bindPopup(`<b>${station.nom}</b>`).openPopup();
                lineCoordinates.push([station.latitude, station.longitude]);
            }

            // Ajouter une ligne de la couleur spécifiée reliant les marqueurs
            let coloredLine = L.polyline(lineCoordinates, {color: lineColor}).addTo(map);
        });
    });
}

// Appeler la fonction pour ajouter des marqueurs et des lignes aux trajets
// addStationMarkersAndLinesByList(listeTrajet);
