#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "$0")" &> /dev/null && pwd)

INPUT_FILE="$SCRIPT_DIR/input.txt"
OUTPUT_FILE="$SCRIPT_DIR/output.txt"

SUCCESS=true

cat "$INPUT_FILE" | java -cp bin main.App > "$OUTPUT_FILE"

if grep -q "[ERROR]" "$OUTPUT_FILE"; then
	echo "Output still has an [ERROR] in it (severe logging message)."
	SUCCESS=false
else
:
fi

TESTED_LINE="INFO Cleaner1 has Snowplower1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Cleaner1 has Snowplower2"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Snowplower1_1 owner is Cleaner1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Snowplower1_1 last crossing is Crossing1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Snowplower1_1 current lane is null"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO HeadInventory1_1 has head Breaker"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Snowplower1_2 owner is Cleaner1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Snowplower1_2 last crossing is Crossing1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Snowplower1_2 current lane is null"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO HeadInventory1_2 has head Ejector"
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
