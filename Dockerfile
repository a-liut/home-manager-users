FROM openjdk:8-jre-alpine

ENV APPLICATION_USER ktor
RUN adduser -D -g '' $APPLICATION_USER

# Create app directory
RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

# Install app dependencies
COPY ./build/libs/homemanager-users.jar /app/homemanager-users.jar
WORKDIR /app

#Expose port and start application
EXPOSE 8080

CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:InitialRAMFraction=2", "-XX:MinRAMFraction=2", "-XX:MaxRAMFraction=2", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "homemanager-users.jar"]