package com.sample.redisandkafka.kafka;

import com.sample.redisandkafka.SampleEvent;
import com.sample.redisandkafka.config.SampleOutputSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(SampleOutputSource.class)
@Slf4j
public class SampleKafkaSourceProducer {

  @Autowired
  private SampleOutputSource sampleOutputSource;

  public boolean produceSampleEvent() {
    SampleEvent sampleEvent = SampleEvent.newBuilder().setField("value").build();

    try {
      sampleOutputSource.sampleMessageChannel().send(MessageBuilder.withPayload(sampleEvent).build());
    } catch(Exception ex) {
      log.error("Error while producing SampleEvent: {}", ex.toString());
      return false;
    }
    return true;
  }

}
