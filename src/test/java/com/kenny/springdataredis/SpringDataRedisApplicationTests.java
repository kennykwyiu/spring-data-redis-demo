package com.kenny.springdataredis;

import com.kenny.springdataredis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class SpringDataRedisApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void testString() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name", "Cherry Wong");
        Object name = valueOperations.get("name");
        System.out.println("name = " + name);
    }

    @Test
    void testSaveUser() {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("user:100", new User("Tiger Gor", 21));
        User user = (User) opsForValue.get("user:100");
        System.out.println("user = " + user);

    }

}
