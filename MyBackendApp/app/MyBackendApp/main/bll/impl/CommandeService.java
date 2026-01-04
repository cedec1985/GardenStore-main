package app.MyBackendApp.main.bll.impl;

import app.MyBackendApp.main.domain.entities.Commande;
import java.util.List;

public interface CommandeService {

    Commande register(Commande commande);
    List<Commande> getAll(Commande commande);
    Commande getOne(Long id);
    boolean delete (Long id);
    Commande update(Long id);
}