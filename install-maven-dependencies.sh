#!/bin/bash
set -euo pipefail

BASEDIR="$(pwd)"
TARGETDIR="${BASEDIR}/target"

cleanup() {
    rm -fr "${TARGETDIR}/jameica-meta"
}

check_target_dir() {
    if  [ ! -e "${BASEDIR}/pom.xml" ]; then
        echo "Konnte BASEDIR nicht bestimmen [${BASEDIR}]." >&2
        exit 1
    fi
    if [ ! -e "${TARGETDIR}" ]; then
        mkdir -p "${TARGETDIR}"
    fi
    if [ ! -e "${TARGETDIR}" ]; then
        echo "Konnte TARGETDIR nicht bestimmen [${TARGETDIR}]." >&2
        exit 1
    fi

}

install_jameica() {
    # Gibt leider kein Buildscript :-(
    # git clone "https://github.com/willuhn/jameica.git" "${JAMEICADIR}"
    git clone --recursive https://github.com/bmhm/jameica-meta ${TARGETDIR}/jameica-meta

    echo "cd to ${TARGETDIR}/jameica-meta"
    cd "${TARGETDIR}/jameica-meta"

    echo "mvn install -B"
    mvn install -B -Pdoclint-java8-disable

    cd ..

    echo "rm -fr ${TARGETDIR}/jameica-meta"
    rm -fr "${TARGETDIR}/jameica-meta"
}

check_target_dir
cleanup

echo "Installing Jameica..." >&2
sleep 2
install_jameica

echo "Cleanup" >&2
cleanup
echo "All done!" >&2
sleep 1

exit 0
