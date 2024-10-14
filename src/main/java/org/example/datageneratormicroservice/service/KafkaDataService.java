package org.example.datageneratormicroservice.service;

import org.example.datageneratormicroservice.model.Data;

public interface KafkaDataService {
    void send(Data data);
}
