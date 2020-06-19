package br.com.osworksapi.api.domain.service.serviceImpl;

import br.com.osworksapi.api.domain.exception.BusinessException;
import br.com.osworksapi.api.domain.service.CrudClientService;
import br.com.osworksapi.api.domain.model.Client;
import br.com.osworksapi.api.domain.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrudClientServiceImpl implements CrudClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        Client existingClient = clientRepository.findByEmail(client.getEmail()).orElse(null);

        if (existingClient != null && !existingClient.equals(client)) {
            throw new BusinessException("There is already a customer registered with this email");
        }

        return clientRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
