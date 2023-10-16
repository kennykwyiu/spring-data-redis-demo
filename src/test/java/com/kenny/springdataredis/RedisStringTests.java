package com.kenny.springdataredis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenny.springdataredis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;

@SpringBootTest
class RedisStringTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testString() {
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set("name", "Cherry Wong");
        Object name = valueOperations.get("name");
        System.out.println("name = " + name);
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSaveUser() throws JsonProcessingException {
        // Create an instance of User
        User originalUser = new User("Tiger Gor", 21);

        // Serialize the User object to JSON
        String serializedUser = mapper.writeValueAsString(originalUser);

        // Store the serialized User in Redis
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set("user:200", serializedUser);

        // Retrieve the serialized User from Redis
        String retrievedUserJson = valueOps.get("user:200");

        // Deserialize the User from JSON
        User deserializedUser = mapper.readValue(retrievedUserJson, User.class);

        // Print the deserialized User
        System.out.println("Deserialized User: " + deserializedUser);
    }

    @Test
    void    testHash() {
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
         hashOperations.put("user:400", "name", "ar4gor");
         hashOperations.put("user:400", "age", "21");

        Map<Object, Object> entries = hashOperations.entries("user:400");
        System.out.println("entries = " + entries);
    }

}
