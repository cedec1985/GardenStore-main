package app.MyBackendApp.main.bll.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import app.MyBackendApp.main.dal.CommandeRepository;
import app.MyBackendApp.main.domain.entities.Commande;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CommandeServiceImpl implements CommandeService {
    private final CommandeRepository commandeRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }
     @SuppressWarnings("null")
    @Override
    public Commande register(Commande commande) {
        commandeRepository.findById(commande.getId()).orElseThrow(() -> new RuntimeException("aucun client trouvé avec cet ID"));
        return commandeRepository.save(commande);
    }
    
    @Override
    public List<Commande> getAll(Commande commande) {
        return commandeRepository.findAll(commande);
    }

    @SuppressWarnings("null")
    @Override
    public Commande getOne(Long id) {
        return commandeRepository.findById(id).orElseThrow(()->new RuntimeException("aucune commnde trouvée avec cet ID"));
    }
    @SuppressWarnings("null")
    @Override
    public boolean delete(Long id) {
        Commande commande = getOne(id);
        commandeRepository.delete(commande);
        return true;
    }

    @Override
    public Commande update(Long id) {
       Commande commande = commandeRepository.findCommandeById(id);
       commande.setId(id);
        return commandeRepository.save(commande);
    }
}