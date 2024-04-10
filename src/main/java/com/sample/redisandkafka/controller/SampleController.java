package com.sample.redisandkafka.controller;

import com.sample.redisandkafka.kafka.SampleKafkaSourceProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    private SampleKafkaSourceProducer sampleKafkaSourceProducer;

    @GetMapping("/send")
    public boolean sendSampleEvent() {
       return sampleKafkaSourceProducer.produceSampleEvent();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @Cacheable("sample-cache")
    @GetMapping("/hello/{name}")
    public String sampleCache(@PathVariable(value = "name") String name) {
        return "Hello " + name;
    }
}
