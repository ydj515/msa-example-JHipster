package com.example.gateway.web.rest;

import com.example.gateway.broker.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/gateway-kafka")
public class GatewayKafkaResource {

    private static final String PRODUCER_BINDING_NAME = "binding-out-0";

    private static final Logger LOG = LoggerFactory.getLogger(GatewayKafkaResource.class);
    private final KafkaConsumer kafkaConsumer;
    private final StreamBridge streamBridge;

    public GatewayKafkaResource(StreamBridge streamBridge, KafkaConsumer kafkaConsumer) {
        this.streamBridge = streamBridge;
        this.kafkaConsumer = kafkaConsumer;
    }

    @PostMapping("/publish")
    public Mono<ResponseEntity<Void>> publish(@RequestParam("message") String message) {
        LOG.debug("REST request the message : {} to send to Kafka topic", message);
        streamBridge.send(PRODUCER_BINDING_NAME, message);
        return Mono.just(ResponseEntity.noContent().build());
    }

    @GetMapping("/consume")
    public Flux<String> consume() {
        LOG.debug("REST request to consume records from Kafka topics");
        return this.kafkaConsumer.getFlux();
    }
}
