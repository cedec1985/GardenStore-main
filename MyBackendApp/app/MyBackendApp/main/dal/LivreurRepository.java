package app.MyBackendApp.main.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import app.MyBackendApp.main.domain.entities.Livreur;

@Repository
public interface LivreurRepository extends JpaRepository<Livreur, Long> {
  @Modifying
  @Query("select l from Livreur l where l.id =:id")
  Livreur findLivreurById(long id);
  Livreur update (long id);
  Livreur delete (long id);
  List<Livreur> findAll(Livreur livreur);
}