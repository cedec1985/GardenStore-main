package app.MyBackendApp.main.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import app.MyBackendApp.main.domain.entities.Produit;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

  @Modifying
  @Query("select p from Produit p where p.id =:id")
  Produit findProduitById(long id);
  Produit update (long id);
  Produit delete (long id);

}
