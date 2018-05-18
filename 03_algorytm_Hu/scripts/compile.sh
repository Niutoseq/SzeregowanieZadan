#!/bin/bash
echo "Compiling files..."

classDirectory=class
graphicDirectory=graphic
graphicInDir=$graphicDirectory/in
graphicOutDir=$graphicDirectory/out

if [ ! -e $classDirectory ]; then
  mkdir $classDirectory
fi

if [ ! -e $graphicDirectory ]; then
  mkdir $graphicDirectory
fi

if [ ! -e $graphicInDir ]; then
  mkdir $graphicInDir
fi

if [ ! -e $graphicOutDir ]; then
  mkdir $graphicOutDir
fi

javac -d class main/App.java main/Task.java main/TaskManager.java main/Machine.java main/MachineManager.java main/Graphic.java
echo "Done!"
