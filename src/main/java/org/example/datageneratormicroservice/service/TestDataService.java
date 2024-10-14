package org.example.datageneratormicroservice.service;

import org.example.datageneratormicroservice.model.test.DataTestOptions;
import org.example.datageneratormicroservice.web.dto.DataTestOptionsDto;

public interface TestDataService {
    void sendMessages(DataTestOptions testOptions);
}
