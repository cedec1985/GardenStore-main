package app.MyBackendApp.main.bll.impl;

import java.util.List;

import app.MyBackendApp.main.domain.entities.Client;


public interface ClientService {

    Client getOne(Long id);
    List<Client> getAll(Client client);
    Client create(Client toCreate);
    Client update(Long id);
    boolean delete(Long id);

}

