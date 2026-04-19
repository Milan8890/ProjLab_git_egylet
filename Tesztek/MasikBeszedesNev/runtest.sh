#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "$0")" &> /dev/null && pwd)

INPUT_FILE="$SCRIPT_DIR/input.txt"
OUTPUT_FILE="$SCRIPT_DIR/output.txt"

SUCCESS=true

cat "$INPUT_FILE" | java -cp bin main.App > "$OUTPUT_FILE"

if grep -q "A bemenet: Zsa" "$OUTPUT_FILE"; then
    SUCCESS=true
else
    SUCCESS=false
fi

if [ "$SUCCESS" = true ]; then
    echo "Success"
    exit 0
else
    echo "Error"
    exit 1
fi

