#!/bin/bash

javac -d bin src/entities/*.java src/equipment/*.java src/equipment/heads/*.java src/main/*.java src/playground/*.java src/user/*.java

SCRIPT_DIR=$(cd -- "$(dirname -- "$0")" &> /dev/null && pwd)

SUCCESS_COUNT=0
FAILED_COUNT=0
TOTAL_TESTS=0


TEST_FOLDERS=("01PlowerChangeHead"
"02CrossingInit"    
"03RoadInit"
"04PlowerInit"
"05CarInit"
"06BusInit"
"07VehicleExetendPath"
"08VehicleExetendPathFail"
"09VehcileDeletePath"
"10DragonHeadLeaveCrossing"
"11DragonHeadLeaveCrossingFailNoBio"
"12SaltHeadLeaveCrossing"
"13SaltHeadLeaveCrossingFailNoSalt"
"14SaltHeadLeaveCrossingFailLaneHasSalt"
"15VehicleMove"
"16VehicleFollowPath"
"17VehicleStayInCrossing"
"18VehicleLeavesCrossing"
"19CarPath"
"20SweeperCleanInside"
"21SweeperCleanOutside"
"22SweeperCleanGravelInside"
"23EjectorClean"
"24EjectorCleanGravel"
"25BreakerClean"
"26SaltSpreaderClean"
"27GravelSpreaderClean"
"28DragonClean"
"29BusReachCorssing"
"30VehicleStuckSnow"
"31CarSwitchLaneTooMuchSnow"
"32PlowerNotStuckInSnow"
"33VehicleSlip"
"34PlowerSlip"
"35VehicleCrash"
"36VehicleStartAfterCrashBP"
"37VehicleStartAfterCrashC"
"38TrampleSnowBC"
"39TrampleSnowP"
"40SaltMeltIceSnow"
"41SaltDies"
"42GravelPreveltSlip"
"43GravelPreveltSlipFail"
"44Snowing"
"45SaltPreventSnowFall"
"46SnowingTunnel"
"47BuyPlowerEverything"
"48BuyPlowerBreaker"
"49BuyPlowerBreakerFail"
"50BuyDragon"
"51BuyDragonFailNoMoney"
"52BuyDragonFailNotCrossing"
"53BuyMaterial"
"54BuyMaterialFailNoMoney"
"55BuyGravelFailNoSpace"
"56BuyMaterialFailNotCrossing")

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
