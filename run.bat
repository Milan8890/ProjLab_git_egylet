@REM javac -d bin src/entities/*.java
@REM javac -d bin src/equipment/*.java
@REM javac -d bin src/main/*.java
@REM javac -d bin src/playground/*.java
@REM javac -d bin src/user/*.java

@REM java -cp bin main.App



javac -d bin src/entities/*.java src/equipment/*.java src/equipment/heads/*.java src/main/*.java src/playground/*.java src/user/*.java

java -cp bin main.App