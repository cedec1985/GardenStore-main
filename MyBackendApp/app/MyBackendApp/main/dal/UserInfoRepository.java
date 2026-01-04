package app.MyBackendApp.main.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import app.MyBackendApp.main.domain.entities.Client;


public interface UserInfoRepository extends JpaRepository<Client, Long> {

    @Query("select Client c from Client where c.mail = :mail")
    Client findByUsername(String mail);

}
