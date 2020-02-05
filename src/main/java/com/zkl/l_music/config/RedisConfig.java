package com.zkl.l_music.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

//    /**
//     * redis配置属性读取
//     */
//    @Value("${spring.redis.host}")
//    private  String host;
//    @Value("${spring.redis.port}")
//    private  int port;
//    @Value("${spring.redis.database}")
//    private  int database;
//    @Value("${spring.redis.jedis.pool.max-idle}")
//    private int maxIdle;
//    @Value("${spring.redis.jedis.pool.max-wait}")
//    private long maxWaitMillis;
//    @Value("${spring.redis.jedis.pool.max-active}")
//    private int maxActive;
//
//    /**
//     * JedisPool配置
//     * @return
//     */
//    @Bean
//    public JedisPoolConfig jedisPoolConfig(){
//        log.info("初始化JedisPoolConfig");
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(maxActive);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        return jedisPoolConfig;
//    }
//
//    /**
//     * 注入RedisConnectionFactory
//     * @param jedisPoolConfig
//     * @return
//     */
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
//        log.info("初始化JedisConnectFactory");
//        //jedisConnectionFactory配置host,database,password参数
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setDatabase(database);
//        //jedisConnectionFactory配置JedisPoolConfig
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        jedisPoolBuilder.poolConfig(jedisPoolConfig);
//        return new JedisConnectionFactory(redisStandaloneConfiguration);
//    }
//
//    /**
//     * 采用redisCacheManager作为缓存管理器
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
//        return redisCacheManager;
//    }
//
//    /**
//     * 生成key的策略:根据类名+方法名+所有参数的值生成唯一的一个key
//     * @return
//     */
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        return (Object target, Method method, Object... params) -> {
//            StringBuilder sb = new StringBuilder();
//            sb.append(target.getClass().getName());
//            sb.append(method.getName());
//            for (Object obj : params) {
//                sb.append(obj.toString());
//            }
//            return sb.toString();
//        };
//    }

    /**
     * 重新实现RedisTemplate：解决序列化问题
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        //设置任何字段可见
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        log.info("objecMapper{}:",om);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash的key采用String序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value序列化采用Jackson序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的value采用Jackson序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        template.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

}