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

TESTED_LINE="Cleaner1 deducted 3000$ successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="HeadInventory1_1 bought HeadListing1_1_BRE successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Cleaner1 deducted 1000$ successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="HeadInventory1_1 bought HeadListing1_1_SWE successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Cleaner1 deducted 6000$ successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="HeadInventory1_1 bought HeadListing1_1_DRA successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Cleaner1 deducted 4000$ successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="HeadInventory1_1 bought HeadListing1_1_GRA successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Cleaner1 deducted 5000$ successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="HeadInventory1_1 bought HeadListing1_1_SAL successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Cleaner1 deducted 25$ successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Snowplower1_1 bought bio successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Cleaner1 deducted 25$ successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Snowplower1_1 bought salt successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Cleaner1 deducted 25$ successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="Snowplower1_1 bought gravel successfully"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO HeadInventory1_1 has head Ejector1_1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO HeadInventory1_1 has head Breaker1_1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO HeadInventory1_1 has head Sweeper1_1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO HeadInventory1_1 has head Dragon1_1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO HeadInventory1_1 has head GravelSpreader1_1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO HeadInventory1_1 has head SaltSpreader1_1"
if grep -q "$TESTED_LINE" "$OUTPUT_FILE"; then
:
else
	SUCCESS=false
	echo "Didn't find line when it should be present: $TESTED_LINE"
fi

TESTED_LINE="INFO Cleaner1 has 80925\\$"
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
