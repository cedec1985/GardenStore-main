package app.MyBackendApp.main.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import app.MyBackendApp.main.api.validation.constraints.Email;
import app.MyBackendApp.main.api.validation.constraints.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;


@Data
@Getter
@Setter
@Entity
@Table(name = "client")
public class Client implements UserDetails {

    @Transient
    private Client client;
    public Client() { }
    public Client(Client client) {
        this.client = client;
    }

    public Client(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false,insertable = false, updatable = false)
    private Long id;

  
    @NotBlank
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Embedded
    private Addresse addresse;

    @Password
    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @Email
    @NotBlank
    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "telephone", nullable = false)
    private Integer telephone;

    public Client(String mail) {
        this.mail = mail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));    // rôle employé
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));   // rôle administrateur
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return mail;
    }

    public Client getClient() {
        return client;
}
    public String getMail() {
        return mail;
    }

    public Addresse getAddresse() {
        return addresse;
    }

    public void setAddresse(Addresse addresse) {
        this.addresse = addresse;
    }
}