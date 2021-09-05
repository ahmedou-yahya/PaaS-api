package com.example.piepaas_api.repository;

import com.example.piepaas_api.entity.Client;
import com.example.piepaas_api.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {


}
