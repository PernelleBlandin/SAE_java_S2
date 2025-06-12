<!-- https://www.websequencediagrams.com -->

title exporterFactures(mois: int, annee: int)

ChaineLibrairie->CommandeBD: getCommandesIterator(mois, annee)
CommandeBD-->ChaineLibrairie: commandesIterator

alt !commandesIterator.next()
    ChaineLibrairie-->ChaineLibrairie: throw new PasDeCommandeException()
else
    note over ChaineLibrairie: Création du dossier ./factures/<annee>-<mois>
    
    loop commandesIterator.next()
        ChaineLibrairie->commandesIterator: getInt("numcom")
        commandesIterator-->ChaineLibrairie: numCom
        
        alt curNumCom == null || !curNumCom.equals(numCom)
            alt curNumCom != null
                ChaineLibrairie->ChaineLibrairie: genererFacturePDF(filePath, curCustomersInfos, curTitle, curSubTitle, detailCommande, curDetailLivres)
            end
            
            ChaineLibrairie->commandesIterator: getString("nomcli")
            commandesIterator-->ChaineLibrairie: nomcli
            ChaineLibrairie->commandesIterator: getString("prenomcli")
            commandesIterator-->ChaineLibrairie: prenomcli
            ChaineLibrairie->commandesIterator: getString("adressecli")
            commandesIterator-->ChaineLibrairie: adressecli
            ChaineLibrairie->commandesIterator: getString("codepostal")
            commandesIterator-->ChaineLibrairie: codepostal
            ChaineLibrairie->commandesIterator: getString("villecli")
            commandesIterator-->ChaineLibrairie: villecli
            
            ChaineLibrairie->commandesIterator: getDate("datecom")
            commandesIterator-->ChaineLibrairie: datecom
            
            ChaineLibrairie->commandesIterator: getString("enligne")
            commandesIterator-->ChaineLibrairie: enligne
            ChaineLibrairie->commandesIterator: getString("livraison")
            commandesIterator-->ChaineLibrairie: livraison
            
            ChaineLibrairie->commandesIterator: getString("nommag")
            commandesIterator-->ChaineLibrairie: nomMag
            
            note over ChaineLibrairie: curDetailLivres = new ArrayList<>()
        end
        
        ChaineLibrairie->commandesIterator: getString("isbn")
        commandesIterator-->ChaineLibrairie: isbnLivre
        ChaineLibrairie->commandesIterator: getString("titre")
        commandesIterator-->ChaineLibrairie: titreLivre
        ChaineLibrairie->Livre: new Livre(isbnLivre, titreLivre)
        Livre-->ChaineLibrairie: livre
        
        ChaineLibrairie->commandesIterator: getInt("qte")
        commandesIterator-->ChaineLibrairie: quantite
        ChaineLibrairie->commandesIterator: getDouble("prixvente")
        commandesIterator-->ChaineLibrairie: prixvente
        
        ChaineLibrairie->DetailLivre: new DetailLivre(livre, curDetailLivres.size() + 1, quantite, prixvente)
        DetailLivre-->ChaineLibrairie: detailLivre
        
        note over ChaineLibrairie: curDetailLivres.add(detailLivre)
    end
    
    note over ChaineLibrairie: Facture de la dernière commande
    ChaineLibrairie->ChaineLibrairie: genererFacturePDF(filePath, curCustomersInfos, curTitle, curSubTitle, detailCommande, curDetailLivres)
end