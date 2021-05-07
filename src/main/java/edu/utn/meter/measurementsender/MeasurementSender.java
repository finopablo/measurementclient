package edu.utn.meter.measurementsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class MeasurementSender {

    @Value("${meter.last}")
    float lastMeasurement;

    @Value("${meter.serialNumber}")
    String serial;

    @Value("${meter.password}")
    String password ;

    @Autowired
    MeasurementClient client;

    @Scheduled(fixedDelay = 1000L)
    public void sendMeasurement() {
        MeasurementDto dto = MeasurementDto.builder().serialNumber(serial).date(LocalDateTime.now().toString()).password(password).value(lastMeasurement).build();
        this.lastMeasurement = lastMeasurement + new Random().nextFloat();
        client.create(dto);
    }
}
