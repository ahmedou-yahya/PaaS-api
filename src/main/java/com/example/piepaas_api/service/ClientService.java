package com.example.piepaas_api.service;
import com.example.piepaas_api.entity.Client;
import com.example.piepaas_api.entity.Project;
import com.example.piepaas_api.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAllClients(){
            return clientRepository.findAll();
    }

    public Client findClientById(Long id){
        return clientRepository.findById(id).orElseGet(null);
    }
    public Client saveClient(Client client){
        return clientRepository.save(client);
    }
    public Client updateClient(Client newClient, Long id){
        return clientRepository.findById(id)
                .map(client -> {
                    client.setName(newClient.getName());
                    client.setEmail(newClient.getEmail());
                    client.setUser(newClient.getUser());
                    return clientRepository.save(client);
                })
                .orElseGet(() -> {
                    newClient.setId(id);
                    return clientRepository.save(newClient);
                });
    }
    public void deleteProjectById(Long id){
        clientRepository.deleteById(id);
    }
}
