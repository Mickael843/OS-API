package br.com.osworksapi.api.domain.service;

import br.com.osworksapi.api.domain.model.Client;

public interface CrudClientService {

    Client save(Client client);

    void deleteById(Long id);
}
