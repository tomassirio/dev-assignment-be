#!/bin/bash

AIRPORT_API="http://localhost:8080/v1/airports"
FLIGHT_API="http://localhost:8080/v1/flights"
PASSENGER_API="http://localhost:8080/v1/passengers"

AIRPORT_CODES=("Pikc" "Char" "Buls" "Jigp" "Volt" "Clef" "Geng" "Rydn" "Lapr" "Arcn")
AIRPORT_NAMES=("Ardougne" "Brimhaven" "Camelot" "Catherby" "Dorgesh-Kaan" "Draynor Manor" "Edgeville" "Falador" "Gnome Stronghold" "Karamja" "Lumbridge" "Morytania" "Port Sarim" "Varrock")
COUNTRIES=("Misland" "Promenea" "Sosaria" "Azeroth" "Hyboria" "Abeir-Toril" "Britannia" "Neverland" "Hoen" "Camazotz" "Narnia" "Zyberia" "Krynn" "Flatland")

FLIGHT_CODES=("Digm1" "Gumr2" "Pamp3" "Flig4" "Worm5" "Glid6" "Aero7" "Strm8")
NAMES=("Jim Halpert" "Pam Beesly" "Roy Anderson" "Dwight Schrute" "Michael Scott" "Toby Flenderson" "Andy Bernard" "Erin Hannon")

num_codes=${#AIRPORT_CODES[@]}
num_names=${#NAMES[@]}
num_flight_codes=${#FLIGHT_CODES[@]}

for i in $(seq 1 10)
do
    curl -X POST $AIRPORT_API \
    -H "Content-type: application/json" \
    -d "{\"code\":\"${AIRPORT_CODES[$((i-1%num_codes))]}\",\"name\":\"${AIRPORT_NAMES[$((i-1%10))]}\",\"country\":\"${COUNTRIES[$((i-1%10))]}\"}"
done

echo
echo "Airports Done"
echo

for i in $(seq 1 8)
do
    departure_time=$(date -d "+${i} hour" +"%Y-%m-%dT%H:%M:%S")
    arrival_time=$(date -d "+$(($i+2)) hour" +"%Y-%m-%dT%H:%M:%S")

    originAirportCode=${AIRPORT_CODES[$RANDOM % ${#AIRPORT_CODES[@]}]}
    destinationAirportCode=${AIRPORT_CODES[$RANDOM % ${#AIRPORT_CODES[@]}]}

    while [ $originAirportCode == $destinationAirportCode ]
    do
        destinationAirportCode=${AIRPORT_CODES[$RANDOM % ${#AIRPORT_CODES[@]}]}
    done

    flightCode=${FLIGHT_CODES[$((i-1))]}

    curl -s -X POST $FLIGHT_API \
    -H "Content-type: application/json" \
    -d "{\"code\":\"$flightCode\",\"originAirportCode\":\"$originAirportCode\",\"destinationAirportCode\":\"$destinationAirportCode\",\"departureTime\":\"$departure_time\",\"arrivalTime\":\"$arrival_time\"}"
done

echo
echo "Flights Done"
echo

for i in $(seq 1 10000)
do
    name=${NAMES[$RANDOM % ${#NAMES[@]}]}
    flightCode=${FLIGHT_CODES[$RANDOM % ${#FLIGHT_CODES[@]}]}

    curl -X POST $PASSENGER_API \
    -H "Content-type: application/json" \
    -d "{\"name\":\"$name\",\"flightCode\":\"$flightCode\"}"
done

echo
echo "Passengers Done"

echo
echo "All Done"