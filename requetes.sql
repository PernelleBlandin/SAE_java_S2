-- Ajouts de livres tests

SELECT idauteur, nomauteur, anneenais, anneedeces, idedit, nomedit, iddewey, nomclass, isbn, titre, nbpages, datepubli, prix
FROM LIVRE NATURAL JOIN ECRIRE NATURAL JOIN AUTEUR NATURAL JOIN EDITER NATURAL JOIN EDITEUR NATURAL JOIN THEMES NATURAL JOIN CLASSIFICATION
ORDER BY isbn;