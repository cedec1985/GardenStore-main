package app.MyBackendApp.main.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.MyBackendApp.main.api.models.dtos.ClientDTO;
import app.MyBackendApp.main.api.models.form.ClientForm;
import app.MyBackendApp.main.bll.impl.AuthRequest;
import app.MyBackendApp.main.bll.impl.AuthResponse;
import app.MyBackendApp.main.bll.impl.ClientService;
import app.MyBackendApp.main.domain.entities.Client;
import app.MyBackendApp.main.utils.JwtTokenUtil;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/client")
@CrossOrigin("*")

public class ClientController {
    private final ClientService clientService;
    final
    AuthenticationManager authManager;
    final
    JwtTokenUtil jwtUtil;

    public ClientController(ClientService clientService, AuthenticationManager authManager, JwtTokenUtil jwtUtil) {
        this.clientService = clientService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }
    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getAll(Client client) {
        List<Client> users = clientService.getAll(client);
        List<ClientDTO> dtos = users.stream()
                .map(ClientDTO::fromEntity)
                .toList();
        return ResponseEntity.ok( dtos );
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getOne(@PathVariable Long id) {
        Client user = clientService.getOne(id);
        ClientDTO dto = ClientDTO.fromEntity(user);
        return ResponseEntity.ok(dto);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/insert")
    public ResponseEntity<ClientDTO> create(@RequestBody @Valid ClientForm form){
        Client user = clientService.create( form.ToEntity());
        ClientDTO dto = ClientDTO.fromEntity(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody @Valid ClientForm form) {
       return ResponseEntity.ok(
                    ClientDTO.fromEntity(clientService.update(id) )
            );
    }
   @DeleteMapping("/{id}")
    public ResponseEntity<ClientDTO> delete(@PathVariable Long id) {
        if (clientService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping(value = "/login", produces ="application/json")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getMail(), request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            Client user = (Client) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getMail(), user.getPassword(),accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
