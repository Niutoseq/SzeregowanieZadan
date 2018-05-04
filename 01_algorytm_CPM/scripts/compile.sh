#!/bin/bash
echo "Compiling files..."
classDirectory=class
if [ ! -e $classDirectory ]; then
  mkdir $classDirectory
fi
javac -d class main/App.java main/Task.java main/TaskManager.java main/Machine.java main/MachineManager.java
echo "Done!"
