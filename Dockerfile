## https://github.com/keeganwitt/docker-gradle/blob/bc176d51b9bb8ddb692660189db41706453c18be/jdk14/Dockerfile
FROM adoptopenjdk:14-jdk-hotspot

ENV GRADLE_HOME /opt/gradle
ENV WORKING_DIR /home/gradle

RUN set -o errexit -o nounset \
 && echo "Adding gradle user and group" \
 && groupadd --system --gid 1000 gradle \
 && useradd --system --gid gradle --uid 1000 --shell /bin/bash --create-home gradle \
 && mkdir /home/gradle/.gradle \
 && chown --recursive gradle:gradle /home/gradle \
 \
 && echo "Symlinking root Gradle cache to gradle Gradle cache" \
 && ln -s /home/gradle/.gradle /root/.gradle

VOLUME /home/gradle/.gradle

WORKDIR $WORKING_DIR

RUN apt-get update \
 && apt-get install --yes --no-install-recommends \
 fontconfig \
 unzip \
 wget \
 \
 bzr \
 git \
 git-lfs \
 mercurial \
 openssh-client \
 subversion \
 && rm -rf /var/lib/apt/lists/*

ENV GRADLE_VERSION 6.6
ARG GRADLE_DOWNLOAD_SHA256=e6f83508f0970452f56197f610d13c5f593baaf43c0e3c6a571e5967be754025
RUN set -o errexit -o nounset \
 && echo "Downloading Gradle" \
 && wget --no-verbose --output-document=gradle.zip "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" \
 \
 && echo "Checking download hash" \
 && echo "${GRADLE_DOWNLOAD_SHA256} *gradle.zip" | sha256sum --check - \
 \
 && echo "Installing Gradle" \
 && unzip gradle.zip \
 && rm gradle.zip \
 && mv "gradle-${GRADLE_VERSION}" "${GRADLE_HOME}/" \
 && ln --symbolic "${GRADLE_HOME}/bin/gradle" /usr/bin/gradle \
 \
 && echo "Testing Gradle installation" \
 && gradle --version

#######

EXPOSE 8080

COPY ./app $WORKING_DIR/app
WORKDIR $WORKING_DIR/app

RUN gradle clean
RUN gradle --version && java --version
RUN pwd && ls -al && gradle task
RUN ls -al
RUN echo $ARTIFACT_NAME
RUN gradle build

########
########

WORKDIR $WORKING_DIR
ENV ARTIFACT_NAME $WORKING_DIR/app/build/libs/helloworld-0.0.1.war
RUN cd $WORKING_DIR && ls -al
RUN ls -al $ARTIFACT_NAME
RUN echo $ARTIFACT_NAME

ENTRYPOINT ["java", "-jar", "/home/gradle/app/build/libs/helloworld-0.0.1.war"]


