#!/bin/bash

day="$1"

if [ -d "src" ]; then
  true # do nothing here. everything is fine
else
  echo "Please go to the root folder of the project and run the script from there.
In the directory should be a folder called \"src\"."
  exit
fi

cp "src/DayTEMPLATE.kt" "src/Day$day.kt"
touch "src/Day$day.txt"
touch "src/Day${day}_test.txt"
