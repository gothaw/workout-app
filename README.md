# workout-app

Desktop application build in JavaFX. The app is based on the pomodoro app built for Intermediate Java Course at Team TreeHouse online training programme. The purpose of the project was to familiarize with basis of JavaFx and using dependency management tool (Gradle) to build and deploy the app. The code was initially developed in java-tth repo but was moved to separate repository to finish the application and finalize documentation and testing.

### App Description

The project is a single stage application allows for [interval training](https://en.wikipedia.org/wiki/Interval_training) for break and workout times specified by the user. It counts through break and workout times unless paused or reset.

### Built With

Java 14, JavaFX 14, FXML, CSS

### Deployment

The project was built using Gradle 6.3 and using [JLink](https://github.com/beryx/badass-jlink-plugin) plugin deploy the application - jpackage and application image.

To build the application - fat jar creation:

```groovy
gradle build
```

Application image creation:

```groovy
gradle jlink
```

To run the application image:

```
build\image\bin\java.exe -m com.radsoltan/com.radsoltan.EntryPoint
```

Installation file creation (uses incubator modules jdk.incubator.jpackage):

```groovy
gradle jpackage
```

### Authors

Radoslaw Soltan

### Acknowledgment 

The app is heavily influenced by the pomodoro app developed for JavaFX course at [Team TreeHouse](https://teamtreehouse.com) online training programme. It is not for commercial use and was developed just to practice JavaFX and build tools.