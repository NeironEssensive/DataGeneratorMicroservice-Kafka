package org.example.datageneratormicroservice.service;

import lombok.RequiredArgsConstructor;
import org.example.datageneratormicroservice.model.Data;
import org.example.datageneratormicroservice.model.test.DataTestOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TestDataServiceImpl implements TestDataService {
    private final ScheduledExecutorService executorService =
            Executors.newSingleThreadScheduledExecutor();
    private final KafkaDataService kafkaDataService;

    @Override
    public void sendMessages(DataTestOptions testOptions) {
        if (testOptions.getMeasurementTypes().length > 0) {
            executorService.scheduleAtFixedRate(
                    () -> {
                        Data data = new Data();
                        data.setSensorId(
                                (long) getRandomNumber(1, 10)
                        );
                        data.setMeasurement(
                                getRandomNumber(15, 100)
                        );
                        data.setMeasurementType(
                                getRandomMeasurementType(
                                        testOptions.getMeasurementTypes()
                                )
                        );
                        data.setTimestamp(
                                LocalDateTime.now()
                        );
                    },
                    0,
                    testOptions.getDelayInSeconds(),
                    TimeUnit.SECONDS
            );
        }
    }

    private double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    private Data.MeasurementType getRandomMeasurementType(Data.MeasurementType[] measurementTypes) {
        int randomTypeId = (int) (Math.random() * (measurementTypes.length));
        return measurementTypes[randomTypeId];
    }
}
