package br.com.osworksapi.api.controller;

import br.com.osworksapi.api.domain.service.CrudClientService;
import br.com.osworksapi.api.domain.model.Client;
import br.com.osworksapi.api.domain.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CrudClientService crudClientService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientRepository.findAll();

            return ResponseEntity.ok(clients);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/{clientId}", produces = "application/json")
    public ResponseEntity<Client> getAllClient(@PathVariable Long clientId) {
        try {
            Client client = clientRepository.findById(clientId).orElse(null);

            if (client != null) {
                return ResponseEntity.ok(client);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Client> saveClient(@Valid @RequestBody Client client) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(crudClientService.save(client));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) {
        try {

            if (!clientRepository.existsById(client.getId()) || client.getId() == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(crudClientService.save(client));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/{clientId}", produces = "application/json")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        try {

            if (!clientRepository.existsById(clientId)) {
                return ResponseEntity.notFound().build();
            }

            crudClientService.deleteById(clientId);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
