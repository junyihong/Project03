package com.project.backend.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	
	//이 코드는 Spring Boot에서 Redis를 사용하기 위한 기본 설정을 하는 클래스

 @Bean
 public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory) {
     RedisMessageListenerContainer container = new RedisMessageListenerContainer();
     container.setConnectionFactory(connectionFactory);
     return container;
 }


 
 @Bean
 public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
     RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
     redisTemplate.setConnectionFactory(connectionFactory);
     redisTemplate.setKeySerializer(new StringRedisSerializer());
     redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
     return redisTemplate;
 }
 
 
}