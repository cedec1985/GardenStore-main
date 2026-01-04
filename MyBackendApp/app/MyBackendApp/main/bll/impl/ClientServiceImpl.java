package app.MyBackendApp.main.bll.impl;

import app.MyBackendApp.main.dal.ClientRepository;
import app.MyBackendApp.main.domain.entities.Client;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getOne(Long id) {
        return (Client) clientRepository.findClientById(id);
        
    }

    @Override
    public List<Client> getAll(Client client) {
        return clientRepository.findAll(client);
    }

    @Override
    public Client create(Client toCreate) {
        return toCreate;
        
    }
    @Override
    public Client update(Long id) {
        Client client = (Client) clientRepository.findClientById(id);
        client.setId(id);
        return client;
    }

    @Override
    public boolean delete(Long id) {
        Client client = (Client) clientRepository.findClientById(id);
        client.setId(id);
        clientRepository.delete(client);
        return true;
    }

    
}