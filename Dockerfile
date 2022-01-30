FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
ARG CONFIG_FILE=config.properties
COPY ${JAR_FILE} application.jar
COPY ${CONFIG_FILE} application.properties
ENTRYPOINT ["java", "-jar", "application.jar", "-Xmx300m -Xss512k -XX:CICompilerCount=2"]
EXPOSE 8080