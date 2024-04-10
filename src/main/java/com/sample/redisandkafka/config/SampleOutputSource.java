package com.sample.redisandkafka.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SampleOutputSource {
  String SAMPLE = "sampleOutput";

  @Output(SAMPLE)
  MessageChannel sampleMessageChannel();
}
