# SAE Java - Groupe 31

Blandin Pernelle, Gautier--Gocko Erwan, Demirtay Emrecan, Hachelef Asma

[Lien du répertoire GitHub](https://github.com/PernelleBlandin/SAE_java_S2)

## Démarrage de l'application

### Lancement de l'exécutable

Pour lancer l'application, exécutez simplement le script `./start_jar.sh`.
Ce script lance l'exécutable de notre application : `Executable.jar`.

#### Connexion de la base de données

⚠️ Notre application nécessite une base de données correctement configurée pour fonctionner.
Par défaut, l'application se connecte au serveur MariaDB de l'IUT.
Si l'application est exécutée à l'IUT, aucune configuration supplémentaire n'est nécessaire.

Pour une utilisation hors de l'IUT, il est possible d'exécuter l'application en spécifiant des paramètres de connexion personnalisés.

Pour cela, ajoutez les arguments suivants dans la commande du script :
- bd-host : Adresse IP de la BD, avec le port.
- bd-base : Nom de la base de données
- bd-login : Nom d'utilisateur
- bd-password : Mot de passe

Exemple : `./start_jar.sh --bd-host localhost:3306 --bd-base Librairie --bd-login root --bd-password root_mdp`

// TODO: Modifier cela au dernier moment ; éviter de leak la BD publique sur GitHub...
Une base de données hébergée sur Internet par nos soins est également disponible pour faciliter les tests.
Il est possible de lancer l'application avec celle-ci avec : `./start_jar.sh <>`

Si [Docker](https://www.docker.com) est installé sur l'ordinateur, il est possible de lancer une base de données préconfigurée avec `docker compose up -d` dans le dossier courant.
Ensuite, vous pouvez démarrer l'application avec la commande `./start_jar.sh --bd-host localhost:3306 --bd-base Librairie --bd-login root --bd-password root_mdp`.

Une fois les tests finis, il est possible ensuite de supprimer l'ensemble de la base de données lancée avec Docker avec :
- Arrêt de container : `docker compose down`
- Supprimer le volume de données : `docker volume rm s2-sae-java_mariadb_data`
