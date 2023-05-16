
# Docker Client Using Docker-Java API
# Openfabric-test 

To run the application from compiled jar from root folder follow these steps:


## .env file
Place the .env file on .\openfabric-api\build\libs\ with Database Address, port, username and password<br>

## Run the jar
<b>To run the jar file from root folder use the following command:</b><br><br>
<i>java -jar .\openfabric-api\build\libs\openfabric-api-1.0.0-SNAPSHOT.jar</i>

## API entry point 
http://localhost:8080/swagger-ui/

## Building Fat or Shadow Jar File
<b>On root folder run following command and check .\openfabric-api\build\libs\ for output</b><br><br>
<i>gradle shadowJar</i> <b>(with Gradle)</b><br>
<i>./gradlew shadowJar</i> <b>(with Gradle Wrapper on Linux)<br> 
<i>.\gradle shadowJar</i> <b>(with Gradle Wrapper on Windows(Powershell))</b><br>
