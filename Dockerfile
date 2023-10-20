FROM twgorg/do-base-image-java-11:81854b481378262595760b5cc5c0ee98d7741395

ARG artifactId
ARG artifactVersion

LABEL artifactId="${artifactId}"
LABEL artifactVersion="${artifactVersion}"

COPY target/${artifactId}-${artifactVersion}.jar /app/app.jar
RUN echo "${artifactId}-${artifactVersion}"

ENV JDK_JAVA_OPTIONS='-XX:+PrintFlagsFinal -XX:MinRAMPercentage=50 -XX:InitialRAMPercentage=25 -XX:MaxRAMPercentage=50'
USER application:application

CMD ["java", "-jar", "/app/app.jar"]
