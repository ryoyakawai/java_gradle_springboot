FROM openjdk:8

ENV USER_GROUP springboot
ENV WORKING_DIR /home/$USER_GROUP
ENV ARTIFACT $WORKING_DIR/app/build/libs/helloworld-0.0.1.war

## Set ARGs and ENVs
ARG SPRING_SYSTEM_PROPERTY_OPTIONS
ARG APP_TARGET_PORT
ARG MYSQL_SERVER_HOST_NAME
ARG MYSQL_ROOT_PASSWORD
ARG MYSQL_DATABASE
ARG MYSQL_PORT
ARG MYSQL_USER
ARG MYSQL_PASSWORD

ENV SPRING_SYSTEM_PROPERTY_OPTIONS $SPRING_SYSTEM_PROPERTY_OPTIONS
ENV APP_TARGET_PORT $APP_TARGET_PORT
ENV MYSQL_SERVER_HOST_NAME $MYSQL_SERVER_HOST_NAME
ENV MYSQL_DATABASE $MYSQL_DATABASE
ENV MYSQL_PORT $MYSQL_PORT
ENV MYSQL_USER $MYSQL_USER
ENV MYSQL_PASSWORD $MYSQL_PASSWORD

### Update Package ###
RUN apt-get update \
 && apt-get install --yes --no-install-recommends \
 bash unzip wget git \
 && rm -rf /var/lib/apt/lists/*

### Add GROUP and USER ###
RUN set -o errexit -o nounset \
 && echo "Adding app user and group" \
 && groupadd --system --gid 1000 $USER_GROUP \
 && useradd --system --gid $USER_GROUP --uid 1000 --shell /bin/bash --create-home $USER_GROUP \
 && mkdir $WORKING_DIR/.gradle \
 && chown --recursive $USER_GROUP:$USER_GROUP $WORKING_DIR
WORKDIR $WORKING_DIR

### BUILD and RUN ###
COPY [ "build.gradle", "gradle", "gradlew", "gradlew.bat", "settings.gradle", "$WORKING_DIR/app/" ]
COPY src $WORKING_DIR/app/src/
COPY gradle $WORKING_DIR/app/gradle/

RUN cd $WORKING_DIR/app && ./gradlew build
EXPOSE $APP_TARGET_PORT

ENTRYPOINT ["sh", "-c", "java ${SPRING_SYSTEM_PROPERTY_OPTIONS} -jar ${ARTIFACT}"]
