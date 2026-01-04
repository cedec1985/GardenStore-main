package app.MyBackendApp.main.bll.impl;

import java.util.List;
import app.MyBackendApp.main.domain.entities.Produit;

public interface ProduitService {
    Produit getOne(Long id);
    List<Produit> getAll(Produit produit);
    Produit add(Produit produit);
    Produit update(Long id, Produit produit);
    boolean delete(Long id);
}
