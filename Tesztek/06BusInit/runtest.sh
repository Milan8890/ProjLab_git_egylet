#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "$0")" &> /dev/null && pwd)

INPUT_FILE="$SCRIPT_DIR/input.txt"
OUTPUT_FILE="$SCRIPT_DIR/output.txt"

SUCCESS=true

cat "$INPUT_FILE" | java -cp bin graphics.NewMain > "$OUTPUT_FILE"

if grep -q "\\[ERROR\\]" "$OUTPUT_FILE"; then
	echo "Output still has an [ERROR] in it (severe logging message)."
	SUCCESS=false
else
:
fi

# TESTED_LINE="INFO Bus1 last crossing is Crossing1"
# if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
# :
# else
# 	SUCCESS=false
# 	echo "Didn't find line when it should be present: $TESTED_LINE"
# fi

if grep -q "INFO Bus1 last crossing is Crossing1" "$OUTPUT_FILE"; then
	STATION_A="Crossing1"
	STATION_B="Crossing2"
elif grep -q "INFO Bus1 last crossing is Crossing2" "$OUTPUT_FILE"; then
	STATION_A="Crossing2"
	STATION_B="Crossing1"
else
	SUCCESS=false
	echo "Didn't find Bus1 last crossing as Crossing1 or Crossing2"
fi

TESTED_LINE="INFO Bus1 current lane is null"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Bus1 driver is BusDriver1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

# TESTED_LINE="INFO Bus1 station A is Crossing1"
# if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
# :
# else
# 	SUCCESS=false
# 	echo "Didn't find line when it should be present: $TESTED_LINE"
# fi

TESTED_LINE="INFO Bus1 station A is $STATION_A"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

# TESTED_LINE="INFO Bus1 station B is Crossing2"
# if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
# :
# else
# 	SUCCESS=false
# 	echo "Didn't find line when it should be present: $TESTED_LINE"
# fi

TESTED_LINE="INFO Bus1 station B is $STATION_B"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Bus1 current destination is B"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

if [ "$SUCCESS" = true ]; then
    echo "Success"
    exit 0
else
    echo "Error"
    exit 1
fi
