package com.sample.redisandkafka.kafka;

import com.sample.redisandkafka.SampleEvent;
import com.sample.redisandkafka.config.SampleInputSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(SampleInputSink.class)
@Slf4j
public class SampleKafkaSinkConsumer {

  @StreamListener(SampleInputSink.SAMPLE)
  public void processSampleEvent(SampleEvent sampleEvent) {
    log.info("Recieved sample event: {}", sampleEvent);
  }

}
