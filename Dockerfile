FROM openjdk
ADD target/libraryProject-0.0.1-SNAPSHOT.jar libraryProject-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar" , "libraryProject-0.0.1-SNAPSHOT.jar"]



