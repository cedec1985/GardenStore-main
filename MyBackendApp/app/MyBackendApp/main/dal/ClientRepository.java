package app.MyBackendApp.main.dal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ch.qos.logback.core.net.server.Client;
import jakarta.validation.constraints.NotNull;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

   @Query("select c from Client c where c.nom = :nom")
   Optional<app.MyBackendApp.main.domain.entities.Client> findClientByUsername(String nom);

   @NotNull
   @Query("select c from Client c where c = c")
   List<app.MyBackendApp.main.domain.entities.Client> findAll(app.MyBackendApp.main.domain.entities.Client client);
   
   @Modifying
   @Query("select c from Client c where c.id =:id")
   Client findClientById(long id);
   Client update (long id);
   Client delete (app.MyBackendApp.main.domain.entities.Client client);
}