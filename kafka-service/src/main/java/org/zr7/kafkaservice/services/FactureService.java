package org.zr7.kafkaservice.services;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.zr7.kafkaservice.entities.FactureEntity;
import org.zr7.kafkaservice.repository.FactureEntityRepository;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class FactureService {

    @Autowired
    private FactureEntityRepository factureEntityRepository;

    @Bean
    public Consumer<FactureEntity> factureDbConsumer(){
        return (input)->{
            System.out.println(input.toString());
            FactureEntity factureEntity = new FactureEntity(null, input.getFactureID(),
                    input.getClientName(), input.getFacturePrice());
            factureEntityRepository.save(factureEntity);
            System.out.println("************* Saved *************");
        };
    }

    //@Bean
    public Consumer<FactureEntity> factureConsumer(){
        return (input)->{
            System.out.println("******************************************");
            System.out.println(input.getFactureID());
            System.out.println(input.getClientName());
            System.out.println(input.getFacturePrice());
        };
    }

    @Bean
    public Supplier<FactureEntity> factureSupplier(){
        return ()-> new FactureEntity(
                null,
                1L,
                Math.random() > 0.5?"zaki":"jamal",
                new Random().nextInt(5000)
        );
    }

    //@Bean
    public Function<KStream<String,FactureEntity>,KStream<String,Double>> KStreamFunctionClientFacture(){

        return (input)->input

                .map((k,v)->new KeyValue<>(v.getClientName(),v.getFacturePrice()))
                .groupBy((k,v)->k, Grouped.with(Serdes.String(),Serdes.Double()))
                .reduce((c1,c2)->c1+c2)
                .toStream()
                .map((k,v)->new KeyValue<>("total des factures de Client:" + k + "  est  = ",v));


    }

    //@Bean
    public Function<KStream<String, FactureEntity>, KStream<String, Long>> kStreamFunction(){
        return (input)->{
            return input
                    .map((k,v)->new KeyValue<>(v.getClientName(), v.getFacturePrice()))
                    .groupBy((k,v)->k, Grouped.with(Serdes.String(), Serdes.Double()))
                    .count()
                    .toStream();
        };
    }

}
