#!/usr/bin/env bash
set -o errexit
set -o pipefail

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

pushd "${CURRENT_DIR}"/..

    # shellcheck source=scripts/maven-builder.sh
    source scripts/maven-builder.sh

    maven_command                                           \
        test                                               \

popd
