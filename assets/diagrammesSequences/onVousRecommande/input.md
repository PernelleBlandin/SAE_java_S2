<!-- https://www.websequencediagrams.com -->

title onVousRecommande(client: Client)

ChaineLibrairie->Client: getMagasin()
Client-->ChaineLibrairie: magasinClient

ChaineLibrairie->LivreBD: obtenirLivreEnStockMagasin(magasinClient)
LivreBD-->ChaineLibrairie: listeLivresMagasin

ChaineLibrairie->Client: getCommandes()
Client-->ChaineLibrairie: commandesClient

ChaineLibrairie->Client: getPanier()
Client-->ChaineLibrairie: panierClient

ChaineLibrairie->Panier: getDetailLivres()
Panier-->ChaineLibrairie: detailPanierClient

alt pas de commandes et panier vide
    ChaineLibrairie->ChaineLibrairie: getLivresTriesParVentes(listeLivresMagasin)
    ChaineLibrairie-->ChaineLibrairie: return livresTriesParVentes
else
    ChaineLibrairie->Client: getLivresNonAchetes(listeLivresMagasin)
    Client-->ChaineLibrairie: livresNonAchetes
    
    ChaineLibrairie->ChaineLibrairie: getLivresTriesParVentes(livresNonAchetes)
    ChaineLibrairie-->ChaineLibrairie: livresRecommendes
    
    ChaineLibrairie->ClientBD: obtenirClientsAyantLivresCommuns(client.getId())
    ClientBD-->ChaineLibrairie: clientsCommuns
    
    loop pour chaque client commun
        ChaineLibrairie->Client: getLivresAchetes()
        Client-->ChaineLibrairie: livresAchetesParAutreClient
        
        ChaineLibrairie->Client: getDetailCommandes()
        Client-->ChaineLibrairie: detailCommandes
        
        loop pour chaque DetailCommande
            ChaineLibrairie->DetailLivre: getLivre()
            DetailLivre-->ChaineLibrairie: livre
            
            alt livre dans livresRecommendes
                alt livre dans livresAchetesParAutreClient
                    note over ChaineLibrairie: +3 points de recommandation
                else livre dans le panier de l'autre client
                    note over ChaineLibrairie: +2 points de recommandation
                end
            end
        end
    end
    
    ChaineLibrairie->Client: getClassifications()
    Client-->ChaineLibrairie: classificationsClient
    
    loop pour chaque livre recommandé
        ChaineLibrairie->Livre: getClassifications()
        Livre-->ChaineLibrairie: classifications
        
        alt classifications dans classificationsClient
            note over ChaineLibrairie: +1 point de recommandation
        end
    end
    
    ChaineLibrairie->ComparatorLivreRecommandation: new ComparatorLivreRecommandation(recommendationsLivres)
    ComparatorLivreRecommandation-->ChaineLibrairie: comparatorRecommendation
    ChaineLibrairie->Collections: sort(livresRecommendes, comparatorRecommendation)
    ChaineLibrairie-->ChaineLibrairie: return livresRecommendes
end