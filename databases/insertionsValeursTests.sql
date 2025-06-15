USE `Librairie`;

-- les magasins
insert into MAGASIN(idmag, nommag, villemag) values
       (1,'La librairie parisienne','Paris'),
       (2,'Cap au Sud','Marseille'),
       (3,'Ty Li-Breizh-rie','Rennes'),
       (4,'L''européenne','Strasbourg'),
       (7,'Loire et livres','Orléans');

-- La classification
insert into CLASSIFICATION(iddewey, nomclass) values
	('000', 'Informatique, généralités'),
	('010', 'Bibliographie'),
	('020', 'Bibliothéconomie et sciences de l''information'),
	('030', 'Encyclopédies générales'),
	('040', 'Non attribué'),
	('050', 'Publications en série'),
	('060', 'Organisations et muséologie'),
	('070', 'Journalisme, médias'),
	('080', 'Collections générales'),
	('090', 'Manuscrits et livres rares'),
	('100', 'Philosophie et psychologie'),
	('110', 'Métaphysique'),
	('120', 'épistémologie, causalité et libre arbitre'),
	('130', 'Parapsychologie et occultisme'),
	('140', 'écoles philosophiques'),
	('150', 'Psychologie'),
	('160', 'Logique'),
	('170', 'éthique'),
	('180', 'Philosophie ancienne, médiévale et orientale'),
	('190', 'Philosophie moderne et occidentale'),
	('200', 'Religion'),
	('210', 'Philosophie et théorie de la religion'),
	('220', 'Bible'),
	('230', 'Christianisme et théologie chrétienne'),
	('240', 'Morale chrétienne et dévotion'),
	('250', 'église et pastorale'),
	('260', 'Théologie sociale et ecclésiologie'),
	('270', 'Histoire de l''église chrétienne'),
	('280', 'Dénominations chrétiennes'),
	('290', 'Autres religions'),
	('300', 'Sciences sociales'),
	('310', 'Statistiques'),
	('320', 'Sciences politiques'),
	('330', 'économie'),
	('340', 'Droit'),
	('350', 'Administration publique'),
	('360', 'Problèmes sociaux et services sociaux'),
	('370', 'éducation'),
	('380', 'Commerce, communications, transports'),
	('390', 'Usages, folklore et étiquette'),
	('400', 'Langues'),
	('410', 'Linguistique'),
	('420', 'Anglais et vie de la langue'),
	('430', 'Langues germaniques'),
	('440', 'Langues romanes (français, espagnol...)'),
	('450', 'Italien, roumain'),
	('460', 'Espagnol, portugais'),
	('470', 'Latin'),
	('480', 'Langues grecques'),
	('490', 'Autres langues'),
	('500', 'Sciences naturelles et mathématiques'),
	('510', 'Mathématiques'),
	('520', 'Astronomie'),
	('530', 'Physique'),
	('540', 'Chimie'),
	('550', 'Sciences de la Terre'),
	('560', 'Paléontologie'),
	('570', 'Biologie'),
	('580', 'Botanique'),
	('590', 'Zoologie'),
	('600', 'Technologie et sciences appliquées'),
	('610', 'Médecine'),
	('620', 'Ingénierie'),
	('630', 'Agriculture'),
	('640', 'Maison et vie domestique'),
	('650', 'Gestion et administration'),
	('660', 'Chimie industrielle'),
	('670', 'Manufactures'),
	('680', 'Industries diverses'),
	('690', 'Bâtiment'),
	('700', 'Arts et loisirs'),
	('710', 'Urbanisme et aménagement du paysage'),
	('720', 'Architecture'),
	('730', 'Sculpture'),
	('740', 'Arts décoratifs'),
	('750', 'Peinture'),
	('760', 'Arts graphiques'),
	('770', 'Photographie'),
	('780', 'Musique'),
	('790', 'Loisirs et sports'),
	('800', 'Littérature'),
	('810', 'Littérature américaine'),
	('820', 'Littérature anglaise'),
	('830', 'Littérature germanique'),
	('840', 'Littérature française'),
	('850', 'Littérature italienne, espagnole, portugaise'),
	('860', 'Littérature espagnole, portugaise'),
	('870', 'Littérature latine'),
	('880', 'Littérature grecque'),
	('890', 'Autres littératures'),
	('900', 'Histoire et géographie'),
	('910', 'Géographie et voyages'),
	('920', 'Biographies et généalogie'),
	('930', 'Histoire ancienne'),
	('940', 'Histoire de l''Europe'),
	('950', 'Histoire de l''Asie'),
	('960', 'Histoire de l''Afrique'),
	('970', 'Histoire de l''Amérique du Nord'),
	('980', 'Histoire de l''Amérique du Sud'),
	('990', 'Histoire d''autres parties du monde');

-- les clients
insert into CLIENT (idcli, nomcli, prenomcli, adressecli, codepostal, villecli, idmag) values
       (1, 'Rodriguez', 'Fatima', '188 chemin de la Forêt', '45000', 'Orléans', 7),
       (2, 'Garcia', 'Hugo', '167 avenue de la Forêt', '06000', 'Nice', 2),
       (3, 'Martin', 'Julie', '133 boulevard de l''Université', '45000', 'Orléans', 7),
       (4, 'Dubois', 'Lucas', '45 place de la Paix', '34000', 'Montpellier', 1),
       (5, 'Guichon', 'Julien', '2 rue de la Tour Eiffel', '75000', 'Rennes', 1);

-- les éditeurs
insert into EDITEUR(nomedit,idedit) values
	('Dunod', 1),
    ('Simplement 2', 2),
    ('Lgf 2', 3),
    ('Flammarion', 6);

-- Les auteurs
insert into AUTEUR(idauteur, nomauteur,anneenais,anneedeces) values
	('OL7572575A', 'Philippe Chéreau', 1968, NULL),
	('OL7572575B', 'Christophe Agius', 1980, NULL),
	('OL7572551D', 'Xavier Niel', 1967, NULL),
	('OL1871013A', 'Claude Servi', NULL, NULL),
    ('OL6835078A', 'Guillaume Musso', 1974, NULL);

-- Les livres
insert into LIVRE(isbn, titre, nbpages, datepubli, prix) values
	('9782205054750', 'Simplement 2: 25 ans de commentaires', 335, 2025, 24.99),
	('9780446570992', 'Une sacrée envie de foutre le bordel', NULL, 2024, 9.99),
	('9780340932056', 'Réseaux & Télécom', 405, 2013, 46.99),
	('9780768939866', 'Angélique', 213, 2023, 11.99),
    ('9780768939812', 'Skidamarink', 313, 2024, 13.99);

-- Les thèmes des livres
insert into THEMES(isbn,iddewey) values
    ('9782205054750', '790'),
    ('9780446570992', '000'),
    ('9780340932056', '000'),
    ('9780768939866', '840'),
    ('9780768939812', '840');

-- Les éditeurs des livres
insert into EDITER(isbn,idedit) values
	('9782205054750', 2),
	('9780446570992', 6),
	('9780340932056', 1),
	('9780768939866', 3),
    ('9780768939812', 3);

-- Les auteurs des livres
insert into ECRIRE(isbn,idauteur) values
	('9782205054750', 'OL7572575A'),
    ('9782205054750', 'OL7572575B'),
	('9780446570992', 'OL7572551D'),
	('9780340932056', 'OL1871013A'),
	('9780768939866', 'OL6835078A'),
	('9780768939812', 'OL6835078A');

-- les stocks de livres
insert into POSSEDER(idmag, isbn, qte) values
	(7, '9782205054750', 2),
	(4, '9780446570992', 7),
	(1, '9780340932056', 10),
	(4, '9780768939866', 10),
	(7, '9780768939866', 1),
	(2, '9782205054750', 9);

-- Les commandes
insert into COMMANDE(numcom, datecom, enligne, livraison, idcli, idmag) values
	(1,str_to_date('01/09/2024','%d/%m/%Y'),'O','M',2,2),
    (2,str_to_date('11/11/2024','%d/%m/%Y'),'N','M',3,2),
    (3,str_to_date('21/12/2024','%d/%m/%Y'),'N','M',3,7),
    (4,str_to_date('24/12/2024','%d/%m/%Y'),'N','C',5,1);

-- Detail Commandes

insert into DETAILCOMMANDE(numcom, numlig, isbn, qte, prixvente) values
	(1,1,9780768939866,2, 11.99),
    (2,1,9780768939866,3, 11.99),
    (3,1,9780340932056,2, 46.99),
    (4,1,9782205054750,1, 24.99);

-- Les vendeurs

INSERT INTO VENDEUR(idvendeur, nomvendeur, prenomvendeur, idmag) VALUES 
	(1, 'Guernon', 'Hortense', 1),
	(2, 'Tollmache', 'Vincent', 1),
	(3, 'Routhier', 'Delphine', 2),
	(4, 'Casgrain', 'Charlotte', 2),
	(5, 'Boutot', 'Christien', 2),
	(6, 'Dupuis', 'Claire', 3),
	(7, 'Chalifour', 'Paul', 4),
	(8, 'Desforges', 'Heloise', 4),
	(9, 'Courtois', 'Gérard', 7),
	(10, 'Ruel', 'Solaine', 7);

-- Les paniers

INSERT INTO PANIER(idpanier, idcli, idmag) VALUES 
    (1, 1, 7),
    (2, 2, 2),
    (3, 3, 7),
    (4, 4, 1),
    (5, 5, 1);

INSERT INTO DETAILPANIER(idpanier, numlig, qte, prixvente, isbn) VALUES
    (3, 1, 1, 9.99, '9780446570992'),
	(4, 1, 1, 11.99, '9780446570992');
