package com.example.ip.repository;

import com.example.ip.model.Ip;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IpRepository extends MongoRepository<Ip,String> {

}
