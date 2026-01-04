package app.MyBackendApp.main.api.models.dtos;

import java.time.LocalDate;
import app.MyBackendApp.main.domain.entities.Commande;

public record CommandeDTO (
    Long id,
    Integer montant,
    LocalDate dateCommande,
    Integer quantite,
    Integer nCommande
)
    {
    public static CommandeDTO fromEntity(Commande commande) {
        if(commande==null)
            return null;
        return new CommandeDTO(
                commande.getId(),
                commande.getMontant(),
                commande.getDateCommande(),
                commande.getQuantite(),
                commande.getNCommande()
            );

    }
}