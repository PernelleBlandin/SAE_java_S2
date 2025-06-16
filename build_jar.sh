# Arrêt en cas d'erreur du script
set -e

mvn clean package

cp ./target/sae-1.0-SNAPSHOT-jar-with-dependencies.jar ./ExecutableTerminal.jar

