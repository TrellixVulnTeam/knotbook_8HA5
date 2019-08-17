# Knotbook 
![Knotbook Icon](assets/knot-small.png)

|Platform|Build Status|
|----|----|
|Windows|[![Build Status](https://dev.azure.com/yuliu2016/knotbook/_apis/build/status/yuliu2016.knotbook?branchName=master&jobName=Windows)](https://dev.azure.com/yuliu2016/knotbook/_build/latest?definitionId=1&branchName=master)|
|Mac OS|[![Build Status](https://dev.azure.com/yuliu2016/knotbook/_apis/build/status/yuliu2016.knotbook?branchName=master&jobName=macOS)](https://dev.azure.com/yuliu2016/knotbook/_build/latest?definitionId=1&branchName=master)|
|Linux|[![Build Status](https://dev.azure.com/yuliu2016/knotbook/_apis/build/status/yuliu2016.knotbook?branchName=master&jobName=Linux)](https://dev.azure.com/yuliu2016/knotbook/_build/latest?definitionId=1&branchName=master)|

**Knotbook** is a **JavaFX** data viewing/analysis app for FRC 2020, successor
to the [2019 app](https://github.com/Team865/Restructured-Tables).
It is written with JDK 12 with modules and uses OpenJFX 12. Instead of of using
launch4j to generate executables, it uses jlink to create a complete runtime.

To build: `gradlew build`

To run application: `gradlew run`

To generate a runtime image: `gradlew jlink` or `gradlew jlinkZip`

#### Components

**KnotTable**: 
ControlsFX SpreadsheetView is not supported since JDK 9.
This project uses a custom table Control that uses a custom 
VirtualFlow implementation. 
See [VirtualGrid.kt](core/table/src/main/java/knotbook/core/table/VirtualGrid.kt)
for details on how it works.

**Camera**:
A JavaFX image property to display the webcam and read QR codes. See
[KnotCamera.java](core/camera/src/main/java/knotbook/core/camera/KnotCamera.java)

#### Special Notes: 
The webcam-capture library is unable to load the native files in the modules in development mode,
likely due to Java 9+ modularization. As a workaround, we need to find `OpenIMAJGrabber.dll`
(or another extension for another system) and put it on the PATH so that BridJ can find it.
It seems to work in the jlink image


#### Useful Library & Links:

[paour/natorder](https://github.com/paour/natorder) - Natural Order Comparator