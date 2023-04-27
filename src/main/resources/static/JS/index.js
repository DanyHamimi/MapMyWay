const optionsBtn = document.getElementById('options');
const searchBtn = document.getElementById('chercher');
let lastClickedValue = 'origine';

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


// Gerer erreur saisie
const showValueButton = document.getElementById('search-btn');
const errorSig = document.getElementById('errorSig');
const div = document.getElementById('liste');

// showValueButton.addEventListener('click', function () {
//
//     var origine = document.getElementById('origine');
//     var destination = document.getElementById('destination');
//     var orig = origine.value;
//     var dest = destination.value;
//
//     if (orig != "" && dest != "") {
//         // console.log("origine : " + orig + " destination : " + dest);
//         if (div.style.display === 'none') {
//             div.style.display = 'block';
//         }
//
//     }
// });


let originMarker = null;
let destinationMarker = null;


$(function () {
    $("#origine, #destination, #rechercher-input-horaires").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "/autocomplete",
                dataType: "json",
                data: {
                    term: request.term.toLowerCase()
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

// Variable pour stocker la dernière valeur cliquée ("origine" ou "destination")

// Fonction pour mettre à jour la variable "lastClickedValue" en fonction de l'ID du champ de saisie cliqué
function updateLastClickedValue(inputId) {
    lastClickedValue = (inputId === 'origine') ? 'origine' : 'destination';
}

// Ajouter un listener au champ de saisie "origine" pour mettre à jour "lastClickedValue"
document.getElementById('origine').addEventListener('click', () => {
    updateLastClickedValue('origine');
});

// Ajouter un listener au champ de saisie "destination" pour mettre à jour "lastClickedValue"
document.getElementById('destination').addEventListener('click', () => {
    updateLastClickedValue('destination');
});

// Fonction pour ajouter un marqueur sur la carte Leaflet lorsque l'utilisateur clique
function onMapClick(e) {
    // Ajouter un marqueur à l'emplacement cliqué en fonction de la dernière valeur cliquée ("origine" ou "destination")
    if (lastClickedValue === 'origine') {
        if (originMarker) {
            originMarker.remove();
        }
        originMarker = L.marker(e.latlng).addTo(map);
        originMarker.bindPopup(`<b>Origine</b>`).openPopup();
        document.getElementById('origine').value = `${e.latlng.lat}, ${e.latlng.lng}`;
    } else if (lastClickedValue === 'destination') {
        if (destinationMarker) {
            destinationMarker.remove();
        }
        destinationMarker = L.marker(e.latlng).addTo(map);
        destinationMarker.bindPopup(`<b>Destination</b>`).openPopup();
        document.getElementById('destination').value = `${e.latlng.lat}, ${e.latlng.lng}`;
    }
}

map.on('click', onMapClick);
L.tileLayer('tiles/{z}/{x}/{y}.png', {
    minZoom: 12,
    maxZoom: 16,
    attribution: 'Map data © <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors'
}).addTo(map);

// Code JavaScript pour gérer l'ouverture et la fermeture de la fenêtre modale
const modal = document.getElementById('modal');
const btn = document.getElementById('rechercher-btn');
const span = document.getElementById('rechercher-annuler');
const submit = document.getElementById('rechercher-submit');

submit.onclick = function() {
    $.ajax({
        url: "/schedules",
        dataType: "json",
        data: {
            station: document.getElementById('rechercher-input-horaires').value,
        },
    }).done(function (data) {
        let keys = Object.keys(data);

        // Clear existing radio buttons and list items
        $("#modal-content").html('');
        $('#vers').html("<div class='vers'>Vers : </div>")

        for(let i = 0; i < keys.length; i++) {
            let station = keys[i];
            let schedules = data[station];

            // Create a radio button for each station
            let radioButton = $("<input>").attr({
                type: "radio",
                name: "station",
                id: "station-" + i,
                value: station
            });

            // Add a label for the radio button
            let label = $("<label>").attr("for", "station-" + i).text(station.split(";")[1]);
            //set the radio button image
            let imageUrl = "../css/image/M"+station.split(";")[0]+".png";
            label.css({
                "background-image": "url(" + imageUrl + ")",
                "background-size": "16px 16px", // Modifiez ces valeurs pour ajuster la taille de l'image
                "padding-left": "25px", // Ajustez la valeur pour positionner correctement le texte
                "background-repeat": "no-repeat",
                "min-width": "max-content"
            });
            // Append the radio button and label to the modal content
            //$("#modal-content").html().css({'border' : '1px solid rgb(145 134 134 / 70%)'})
            $("#modal-content").css("border","1px solid rgb(145 134 134 / 70%)")

            $("#modal-content").append(radioButton, label);

            // Event listener for the radio button click
            radioButton.on("click", function() {
                // Clear existing list items
                $("#modal ul").html('');


                // Create an unordered list for schedules
                //let ul = $("<ul class='ul_lignes'>");
                $('#ul_lignes').html('')
                let imageUrl = "../css/image/M"+station.split(";")[0]+".png";
                for (let j = 0; j < schedules.length; j++) {
                    let schedule = schedules[j];
                    let li = $("<li>").html(schedule).css({
                        "list-style-type": "none",
                        "background-image": "url(" + imageUrl + ")",
                        "background-size": "16px 16px", // Modifiez ces valeurs pour ajuster la taille de l'image
                        "padding-left": "25px", // Ajustez la valeur pour positionner correctement le texte
                        "background-repeat": "no-repeat",
                        "background-position": "left center"
                    });
                    $('#ul_lignes').append(li);
                }

                // Append the unordered list to the modal content
                //$("#modal").append(ul)
            });
        }
    });
}
btn.onclick = function() {
    modal.style.display = "block";
}

span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        //modal.style.display = "none";
    }
}
// Créer une fonction pour ajouter des marqueurs et des lignes rouges


// Appeler la fonction pour ajouter des marqueurs et des lignes aux trajets
// addStationMarkersAndLinesByList(listeTrajet);
