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

TESTED_LINE="Snowplower1_1 slip check on Lane1_1: doesn’t have gravel, slipping"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
	SUCCESS=false
	echo "Found line when it should be absent: $TESTED_LINE"
else
:
fi

TESTED_LINE="Snowplower1_1 slipping on Road1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
	SUCCESS=false
	echo "Found line when it should be absent: $TESTED_LINE"
else
:
fi

if [ "$SUCCESS" = true ]; then
    echo "Success"
    exit 0
else
    echo "Error"
    exit 1
fi
