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
    git clone https://github.com/bmhm/jameica-meta ${TARGETDIR}/jameica-meta
    cd "${TARGETDIR}/jameica-meta"
    git submodule init
    git submodule update
    mvn install
    cd ..
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
