package com.example.piepaas_api.controller;

import com.example.piepaas_api.entity.Client;
import com.example.piepaas_api.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("clients")
    ResponseEntity<List<Client>> findAll(){
        return new ResponseEntity<>(clientService.findAllClients(), HttpStatus.OK);
    }
    @PostMapping("clients")
    ResponseEntity<Client> newClient(@RequestBody Client client){
        return new ResponseEntity<>(clientService.saveClient(client), HttpStatus.OK);
    }
    @GetMapping("clients/{id}")
    ResponseEntity<Client> findById(@PathVariable Long id){
        return new ResponseEntity<>(clientService.findClientById(id), HttpStatus.OK);
    }
    @PutMapping("clients/{id}")
    ResponseEntity<Client> updateClient(@RequestBody Client newClient,@PathVariable Long id){
        return new ResponseEntity<>(clientService.updateClient(newClient,id), HttpStatus.OK);
    }
    @DeleteMapping("clients/{id}")
    ResponseEntity deleteById(@PathVariable Long id){
        return new ResponseEntity<>( HttpStatus.OK);
    }

}
