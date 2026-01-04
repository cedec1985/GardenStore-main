package app.MyBackendApp.main.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.MyBackendApp.main.api.models.dtos.CommandeDTO;
import app.MyBackendApp.main.api.models.form.CommandeForm;
import app.MyBackendApp.main.bll.impl.CommandeService;
import app.MyBackendApp.main.domain.entities.Commande;
import jakarta.validation.Valid;

@RestController
@RequestMapping("commande")
@CrossOrigin("*")
@Valid

public class CommandeController {
    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public ResponseEntity<List<CommandeDTO>> getAll(Commande commande) {
        List<Commande> commandes = commandeService.getAll(commande);
        List<CommandeDTO> dtos = commandes.stream()
                .map(CommandeDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeDTO> getOne(@PathVariable Long id) {
        Commande commande = commandeService.getOne(id);
        CommandeDTO dto = CommandeDTO.fromEntity(commande);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/register/commande")
    public ResponseEntity<CommandeDTO> register(@RequestBody @Valid CommandeForm form) {
        Commande commande = form.toEntity();
        commande = commandeService.register(commande);
        CommandeDTO dto = CommandeDTO.fromEntity(commande);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommandeDTO> update(@PathVariable Long id) {
        Commande commande = commandeService.getOne(id);
        Commande com = commandeService.update(commande.getId());
        return ResponseEntity.ok(CommandeDTO.fromEntity(com));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CommandeDTO> delete(@PathVariable Long id) {
        if (commandeService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}