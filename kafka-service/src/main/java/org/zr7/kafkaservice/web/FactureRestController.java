package org.zr7.kafkaservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;
import org.zr7.kafkaservice.entities.FactureEntity;

import java.util.Random;

@RestController
public class FactureRestController {

    @Autowired
    private StreamBridge streamBridge;


    @GetMapping(path = "/publish/{topic}/{name}/{id}")
    public FactureEntity publish(@PathVariable String topic, @PathVariable String name, @PathVariable Long id){
        FactureEntity factureEntity = new FactureEntity(null, id, name, new Random().nextInt(6000));
        streamBridge.send(topic, factureEntity);

        return factureEntity;
    }

}
