#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "$0")" &> /dev/null && pwd)

INPUT_FILE="$SCRIPT_DIR/input.txt"
OUTPUT_FILE="$SCRIPT_DIR/output.txt"

SUCCESS=true

cat "$INPUT_FILE" | java -cp bin main.App > "$OUTPUT_FILE"

if grep -q "\\[ERROR\\]" "$OUTPUT_FILE"; then
	echo "Output still has an [ERROR] in it (severe logging message)."
	SUCCESS=false
else
:
fi

TESTED_LINE="Snowplower1_1 with GravelSpreader1_1 cleans Lane1_1 for 25$"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Cleaner1 received 25$"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Snowplower1_1 used 100 gravel"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Lane1_1 has gravel"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Snowplower1_1 has 100 gravel"
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
