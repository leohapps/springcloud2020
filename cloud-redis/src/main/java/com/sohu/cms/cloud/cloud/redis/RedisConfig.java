package com.sohu.cms.cloud.cloud.redis;

import com.google.common.collect.Lists;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huaili
 * @date 2019/7/11 16:58
 */
@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class RedisConfig {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        redisClusterConfiguration.setPassword(redisProperties.getPassword());
        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(ClusterTopologyRefreshOptions.builder().enableAllAdaptiveRefreshTriggers().build())
                .build();
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration
                .builder().clientOptions(clusterClientOptions).build();
        return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
    }

    @Bean
    public <K, T> RedisTemplate<K, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<K, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Redisson客户端
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        ClusterServersConfig serversConfig = config.useClusterServers();
        serversConfig.setPassword(redisProperties.getPassword());
        redisProperties.getCluster().getNodes().forEach((node) -> serversConfig.addNodeAddress("redis://" + node));
        return Redisson.create(config);
    }

    /**
     * Lettuce-RedisCluster客户端
     */
    @Bean
    public RedisClusterClient redisClusterClient() {
        List<String> nodes = redisProperties.getCluster().getNodes();
        List<RedisURI> list = Lists.newArrayList();
        nodes.forEach(node -> {
            RedisURI.Builder builder = RedisURI.builder().withPassword(redisProperties.getPassword());
            String[] split = node.split(":");
            builder.withHost(split[0]).withPort(Integer.parseInt(split[1]));
            list.add(builder.build());
        });
        return RedisClusterClient.create(list);
    }

}
