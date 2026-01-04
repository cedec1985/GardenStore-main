package app.MyBackendApp.main.api.models.form;

import app.MyBackendApp.main.domain.entities.Commande;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


public record CommandeForm(

        @NotNull
        Long id,
        @NotNull Integer quantite,
        @NotNull
        Integer n_commande,
        @NotNull
        LocalDate date_commande,
        @NotNull
        Integer montant

        ) {

    public Commande toEntity(){
        Commande commande = new Commande();
        commande.setId(id);
        commande.setQuantite(quantite);
        commande.setNCommande(n_commande);
        commande.setDateCommande(date_commande);
        commande.setMontant(montant);
        return commande;
    }
}
