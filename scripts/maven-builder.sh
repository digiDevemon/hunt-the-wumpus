#!/usr/bin/env bash

function maven_command() {
    $(maven_docker_run_with_args "") "$@"
}

function maven_docker_run_with_args() {
    mkdir -p ~/.m2

    # maven:3.8.2-openjdk-17-slim
    echo "docker run \
        $* \
        --rm \
        -t $(tty &>/dev/null && echo -i) \
        -u $(id -u):$(id -g) \
        -w ${PWD} \
        -v ${PWD}:${PWD} \
        -v ${HOME}/.m2:/tmp/.m2 \
        -e MAVEN_CONFIG=/tmp/.m2 \
        maven@sha256:a8dbb0c806d0eddd61c2a61cede819fdb828cf82cd86ec2b375d8caa29d36551 \
        mvn \
        -Duser.home=/tmp"
}
