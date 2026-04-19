#!/bin/bash

javac -d bin src/entities/*.java src/equipment/*.java src/equipment/heads/*.java src/main/*.java src/playground/*.java src/user/*.java

SCRIPT_DIR=$(cd -- "$(dirname -- "$0")" &> /dev/null && pwd)

SUCCESS_COUNT=0
FAILED_COUNT=0
TOTAL_TESTS=0


TEST_FOLDERS=("ValamiBeszedesNev" "MasikBeszedesNev")

echo -e "Running all tests\n"

for FOLDER in "${TEST_FOLDERS[@]}"
do
    ((TOTAL_TESTS++))
    echo -n "Running $FOLDER: "

    # Itt hívjuk meg az előzőleg megírt teszt scriptet
    # Feltételezzük, hogy a teszt script elfogad egy bemeneti fájlt paraméterként
    # és 0-val tér vissza ha OK, 1-el ha HIBA
    $SCRIPT_DIR/$FOLDER/runtest.sh > /dev/null 2>&1

    # Megnézzük a kilépési kódot
    if [ $? -eq 0 ]; then
        echo "OK"
        ((SUCCESS_COUNT++))
    else
        echo "ERR"
        ((FAILED_COUNT++))
    fi
done

echo -e "\nPassed $SUCCESS_COUNT/$FAILED_COUNT tests."

# Ha volt akár egy hiba is, a fő script is hibával térjen vissza
if [ $FAILED_COUNT -eq 0 ]; then
    exit 0
else
    exit 1
fi
