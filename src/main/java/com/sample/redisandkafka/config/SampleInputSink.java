
package com.sample.redisandkafka.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface SampleInputSink {

  String SAMPLE = "sampleInput";

  @Input(SAMPLE)
  MessageChannel sampleMessageChannel();
}
