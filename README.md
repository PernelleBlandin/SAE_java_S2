# SAE Java - Groupe G34

Blandin Pernelle, Gautier--Gocko Erwan, Demirtay Emrecan, Hachelef Asma

[Lien du répertoire GitHub](https://github.com/PernelleBlandin/SAE_java_S2)

## Lancement de notre travail

### Utilisation de l'exécutable

Il est possible de lancer notre programme directement depuis l'exécutable donné dans le rendu, en utilisant le script `./start_jar_ihm.sh`.

Uniquement le lancement de l'application y est assuré. La génération de la documentation et le lancement de tests unitaires sont assurés par Maven.

### Utilisation de Maven

Nous avons utilisé Maven pour cette SAE, permettant de simplifier l'installation des différentes dépendances (MariaDB, JUnit, etc.) sur les différents ordinateurs de notre groupe. S'il est installé, il est possible de lancer l'application avec le script `./start_maven_ihm.sh`.

Ce script s'occupe du lancement du programme, mais aussi de la vérification des tests unitaires, de la compilation et de la génération de la documentation.

### Connexion à la base de données

⚠️ Notre application nécessite une base de données correctement configurée pour fonctionner. Par défaut, l'application se connecte au serveur MariaDB de l'IUT.
Si l'application est exécutée à l'IUT, aucun argument supplémentaire dans la commande de lancement n'est nécessaire.

Pour une utilisation hors de l'IUT, il est possible d'exécuter l'application en spécifiant des paramètres de connexion personnalisés. Cela fonctionne pour les scripts `start_jar_ihm.sh` et `start_maven_ihm.sh`.

Pour cela, ajoutez les arguments suivants dans la commande du script :
- bd-host : Adresse IP de la BD, avec le port.
- bd-base : Nom de la base de données
- bd-login : Nom d'utilisateur
- bd-password : Mot de passe

Exemple : `./start_jar_ihm.sh --bd-host localhost:3306 --bd-base Librairie --bd-login root --bd-password root_mdp`

Si [Docker](https://www.docker.com) est installé sur l'ordinateur, il est possible de lancer une base de données préconfigurée avec `docker compose up -d` dans le dossier courant.
Ensuite, vous pouvez démarrer l'application avec la commande `./start_jar_ihm.sh --bd-host localhost:3306 --bd-base Librairie --bd-login root --bd-password root_mdp`.

Une fois les tests finis, il est possible ensuite de supprimer l'ensemble de la base de données lancée avec Docker avec :
- Arrêt de container : `docker compose down`
- Suppression du volume de données : `docker volume rm s2-sae-java_mariadb_data`
