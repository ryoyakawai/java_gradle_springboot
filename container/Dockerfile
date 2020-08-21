FROM openjdk:8

ENV USER_GROUP springboot
ENV WORKING_DIR /home/$USER_GROUP

RUN apt-get update \
 && apt-get install --yes --no-install-recommends \
 bash unzip wget git \
 && rm -rf /var/lib/apt/lists/*

RUN set -o errexit -o nounset \
 && echo "Adding app user and group" \
 && groupadd --system --gid 1000 $USER_GROUP \
 && useradd --system --gid $USER_GROUP --uid 1000 --shell /bin/bash --create-home $USER_GROUP \
 && mkdir $WORKING_DIR/.gradle \
 && chown --recursive $USER_GROUP:$USER_GROUP $WORKING_DIR

WORKDIR $WORKING_DIR

### BUILD and RUN ###

COPY ./app $WORKING_DIR/app
RUN cd $WORKING_DIR/app && ./gradlew build
ENV ARTIFACT $WORKING_DIR/app/build/libs/helloworld-0.0.1.war

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar ${ARTIFACT}"]
