package com.sohu.cms.cloud.cloud.redis;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis服务
 * @author xiaoliu208902
 * 2021/9/9 2:18 下午
 */
@Repository
public class BaseRedisService<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate<String, T> redisTemplate;

    private ValueOperations<String, T> valOps() {
        return redisTemplate.opsForValue();
    }

    private ZSetOperations<String, T> zSetOperations() {
        return redisTemplate.opsForZSet();
    }

    private ListOperations<String, T> listOperations() {
        return redisTemplate.opsForList();
    }

    private SetOperations<String, T> setOperations() {
        return redisTemplate.opsForSet();
    }

    private HashOperations<String, String, T> hashMapOperations() {
        return redisTemplate.opsForHash();
    }

    public void set(String key, T obj) {
        try {
            valOps().set(key, obj);
        } catch (Exception e) {
            logger.error("error when store values to redis for key: " + key, e);
        }
    }

    public void setExpire(String key, T obj, int seconds) {
        try {
            valOps().set(key, obj, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("error when store values to redis for key: " + key, e);
        }
    }

    public T get(String key) {
        try {
            return valOps().get(key);
        } catch (Exception e) {
            logger.error("error when query value from redis for key: " + key, e);
            return null;
        }
    }

    public List<T> multiGet(List<String> keys) {
        try {
            return Objects.requireNonNull(valOps().multiGet(keys)).stream().filter(Objects::nonNull).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("error when multi get values from redis: " + keys, e);
        }
        return new ArrayList<>();
    }


    public Long hashIncr(String key, String field, long delta) {
        try {
            return hashMapOperations().increment(key, field, delta);
        } catch (Exception e) {
            logger.error("error when incr hash field: key {} field {} delta {}", key, field, delta, e);
            return null;
        }
    }


    public T getHashVal(String key, String field) {
        try {
            return hashMapOperations().get(key, field);
        } catch (Exception e) {
            logger.error("error when incr hash field: key {} field {} ", key, field, e);
            return null;
        }

    }

    public void putHashValues(String key, Map<String, T> field) {
        try {
            hashMapOperations().putAll(key, field);
        } catch (Exception e) {
            logger.error("error when put hash values: key {} field {} ", key, field, e);
        }
    }

    public void putHashValue(String key, String field, T value) {
        try {
            hashMapOperations().put(key, field, value);
        } catch (Exception e) {
            logger.error("error when put hash value: key {} field {} value {}", key, field, value, e);
        }
    }

    @Async
    public void multiSet(Map<String, T> map, int seconds) {
        try {
            valOps().multiSet(map);
            map.keySet().forEach(key -> redisTemplate.expire(key, seconds, TimeUnit.SECONDS));
        } catch (Exception e) {
            logger.error("error when multi set values from redis: " + map, e);
        }
    }

    protected T queryHashField(String key, String field) {
        try {
            return hashMapOperations().get(key, field);
        } catch (Exception e) {
            logger.error("[error when query hash]: key={}, field={}, cause={}", key, field, e.getMessage());
            return null;
        }
    }

    public void delHashField(String key, String field) {
        try {
            hashMapOperations().delete(key, field);
        } catch (Exception e) {
            logger.error("error when del field from redis for key: " + key, e);
        }
    }

    /**** LIST ****/
    public void rightPush(String key, T obj) {
        try {
            listOperations().rightPush(key, obj);
        } catch (Exception e) {
            logger.error(String.format("error when store list to redis for key: %s, obj: %s", key, obj), e);
        }
    }

    public void leftPush(String key, T obj) {
        try {
            listOperations().leftPush(key, obj);
        } catch (Exception e) {
            logger.error(String.format("error when store list to redis for key: %s, obj: %s", key, obj), e);
        }
    }

    public void storeList(String key, List<T> list) {
        try {
            for (T t : list) {
                listOperations().rightPush(key, t);
            }
        } catch (Exception e) {
            logger.error("error when store list to redis for key: " + key, e);
        }
    }

    public void deleteAllFromList(String key, T value) {
        try {
            listOperations().remove(key, 0, value);
        } catch (Exception e) {
            logger.error("error when store list to redis for key: " + key, e);
        }
    }

    public void storeSet(String key, T obj) {
        try {
            setOperations().add(key, obj);
        } catch (Exception e) {
            logger.error("error when store set for key: " + key, e);
        }
    }

    public void deleteFromSet(String key, T obj) {
        try {
            setOperations().remove(key, obj);
        } catch (Exception e) {
            logger.error(String.format("error when delete from set for key: %s, obj: %s", key, obj), e);
        }
    }

    public boolean isSetMember(String key, T value) {
        try {
            return setOperations().isMember(key, value);
        } catch (Exception e) {
            logger.error("error when check set member for key: " + key, e);
            return false;
        }
    }


    public Long getSetSize(String key) {
        try {
            return setOperations().size(key);
        } catch (Exception e) {
            logger.error("error when get set size for key: " + key, e);
            return null;
        }
    }


    public Set<T> getMembers(String key) {
        try {
            return setOperations().members(key);
        } catch (Exception e) {
            logger.error("error when get set size for key: " + key, e);
            return Sets.newHashSet();
        }
    }

    /**** ZSET ****/
    public void deleteFromZset(String key, T obj) {
        try {
            zSetOperations().remove(key, obj);
        } catch (Exception e) {
            logger.error(String.format("error when delete from zset for key: %s, obj: %s", key, obj), e);
        }
    }

    /**
     * Store the list in sorted set
     */
    public void storeZset(String key, List<T> list) {
        try {
            for (int i = 0, length = list.size(); i < length; i++) {
                double score = Instant.now().toEpochMilli() + 100.0 * i;
                zSetOperations().add(key, list.get(i), score);
            }
        } catch (Exception e) {
            logger.error("error when store zset to redis for key: " + key, e);
        }
    }

    public void storeZset(String key, List<T> values, List<Double> scores) {
        try {
            for (int i = 0; i < values.size(); i++) {
                T value = values.get(i);
                Double score = scores.get(i);
                zSetOperations().add(key, value, score);
            }
        } catch (Exception e) {
            logger.error("error when store zset for key: " + key, e);
        }
    }

    public void storeZsetSimple(String key, T value, Double score) {
        try {
            zSetOperations().add(key, value, score);
        } catch (Exception e) {
            logger.error("error when store zset for key: " + key, e);
        }
    }

    public void storeZset(String key, List<T> values, List<Double> scores, int maxSize) {
        try {
            for (int i = 0; i < values.size(); i++) {
                T value = values.get(i);
                Double score = scores.get(i);
                zSetOperations().add(key, value, score);
            }
            long size = zSetOperations().zCard(key);
            if (size > maxSize) {
                zSetOperations().removeRange(key, maxSize - 1, size - 1);
            }
        } catch (Exception e) {
            logger.error("error when store zset for key: " + key, e);
        }
    }

    /**
     * increase key
     */
    public void increment(String key, long delta) {
        try {
            valOps().increment(key, delta);
        } catch (Exception e) {
            logger.error("error when increase redis key: " + key, e);
        }
    }

    /**
     * increase key
     */
    public Long increase(String key) {
        try {
            return valOps().increment(key, 1);
        } catch (Exception e) {
            logger.error("error when increase redis key: " + key, e);
            return null;
        }
    }

    /**
     * decrease key
     */
    public Long decrease(String key) {
        try {
            return valOps().increment(key, -1);
        } catch (Exception e) {
            logger.error("error when increase redis key: " + key, e);
            return null;
        }
    }

    public T getSet(String key, T value) {
        try {
            return valOps().getAndSet(key, value);
        } catch (Exception e) {
            logger.error("error getSet redis key: " + key, e);
            return null;
        }
    }

    /**
     * get list of object from list
     */
    public List<T> queryFromList(String key) {
        return queryFromList(key, 0, -1);
    }

    /**
     * get list of object from list with start and end
     */
    public List<T> queryFromList(String key, int start, int end) {
        try {
            return listOperations().range(key, start, end);
        } catch (Exception e) {
            logger.error("error when query list from redis for key: " + key, e);
            return null;
        }
    }

    /**
     * Get list of objects from sorted set
     */
    public Set<T> queryAllFromZset(String key) {
        return queryFromZset(key, 0, -1);
    }

    /**
     * Get list of objects from sorted set with start and end
     */
    public Set<T> queryFromZset(String key, int start, int end) {
        try {
            return zSetOperations().range(key, start, end);
        } catch (Exception e) {
            logger.error("error when query zset from redis for key: " + key, e);
            return null;
        }
    }

    public void zincr(String key, T value, double delta) {
        try {
            zSetOperations().incrementScore(key, value, delta);
        } catch (Exception e) {
            logger.error("error when incr zset from redis for key: " + key, e);
        }
    }

    /**
     * Get list of objects from sorted set with start and end
     */
    public Set<ZSetOperations.TypedTuple<T>> queryRevrangeWithScoreFromZset(String key, long start, long end) {
        try {
            return zSetOperations().reverseRangeWithScores(key, start, end);
        } catch (Exception e) {
            logger.error("error when query zset from redis for key: " + key, e);
            return null;
        }
    }

    /**
     * Get list of objects from sorted set with start and end
     */
    public Long reverseRank(String key, T value) {
        try {
            return zSetOperations().reverseRank(key, value);
        } catch (Exception e) {
            logger.error("error when query zset from redis for key: " + key, e);
            return Long.MAX_VALUE;
        }
    }

    /**
     * Get list of objects from sorted set with start and end
     */
    public Long getZcard(String key) {
        try {
            return zSetOperations().zCard(key);
        } catch (Exception e) {
            logger.error("error when query zset from redis for key: " + key, e);
            return 0L;
        }
    }

    /**
     * whether the key exists
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("error when check exists from redis for key: " + key, e);
            return false;
        }

    }

    /**
     * Delete the key from redis.
     */
    public void deleteKey(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error(String.format("error when delete key: %s from redis", key), e);
        }
    }

    /**
     * Delete the key from redis.
     */
    public void deleteKeys(List<String> keys) {
        try {
            redisTemplate.delete(keys);
        } catch (Exception e) {
            logger.error("error when delete keys from redis", e);
        }
    }

    /**
     * set the key an expire timeout.
     *
     * @param key
     * @param timeout
     * @param timeUnit valid TimeUnit
     */
    public void expireKey(String key, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.expire(key, timeout, timeUnit);
        } catch (Exception e) {
            logger.error(String.format("error when expire key: %s from redis", key), e);
        }
    }

    /**
     * Set the key an expire timeout.
     *
     * @param key
     * @param timeout
     */
    public void expireKey(String key, long timeout) {
        try {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(String.format("error when expire key: %s from redis", key), e);
        }
    }

    /**
     * Set the key expire at date.
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        try {
            expireKey(key, date.getTime() - Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error(String.format("error when expire key: %s from redis", key), e);
        }
    }

    /**
     * delete pattern
     *
     * @param pattern scan 0 match *pattern* count 100
     */
    public Long deleteByPattern(String pattern, Integer count) {
        Long delete = 0L;
        try {
            Set<String> keys = Sets.newHashSet();
            redisTemplate.execute((RedisCallback<String>) connection -> {
                Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match("*" + pattern + "*")
                        .count(count).build());
                while (cursor.hasNext()) {
                    String key = new String(cursor.next());
                    keys.add(key);
                }
                try {
                    cursor.close();
                } catch (IOException e) {
                    logger.error("mp.rec.gambling.web.cache.BaseRedisDao.deletePattern", e);
                }
                return null;
            });
            delete = redisTemplate.delete(keys);
        } catch (Exception e) {
            logger.error(String.format("error when delete pattern: %s from redis", pattern), e);
        }
        return delete;
    }


    public Long bitCount(final String key) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    public Long bitCount(String key, int start, int end) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        byte[][] bytes = new byte[desKey.length][];
        for (int i = 0; i < desKey.length; i++) {
            bytes[i] = desKey[i].getBytes();
        }
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
    }

    public boolean setbit(String key, long offset, boolean value) {
        try {
            return redisTemplate.opsForValue().setBit(key, offset, value);
        } catch (Exception e) {
            logger.error("redis setbit error", e);
            return false;
        }
    }

    public boolean getBit(String key, long offset) {
        try {
            return redisTemplate.opsForValue().getBit(key, offset);
        } catch (Exception e) {
            logger.error("redis getBit error", e);
            return false;
        }
    }

    /**
     * 获取increase后的值
     *
     * @param key redis-key
     * @return increase后的值
     */
    public Long getIncrValue(String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            try {
                byte[] serialize = serializer.serialize(key);
                if (Objects.isNull(serialize)) {
                    return NumberUtils.LONG_ZERO;
                }
                byte[] rowVal = connection.get(serialize);
                String deserialize = serializer.deserialize(rowVal);
                if (StringUtils.isBlank(deserialize)) {
                    return NumberUtils.LONG_ZERO;
                }
                return Long.parseLong(deserialize);
            } catch (Exception e) {
                logger.error("error method getIncrValue", e);
            }
            return NumberUtils.LONG_ZERO;
        });
    }

    /**
     * 获取increase后的值
     *
     * @param key redis-key
     * @return increase后的值
     */
    public Long getHashIncrValue(String key, String field) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            try {
                byte[] serialize = serializer.serialize(key);
                byte[] serializeField = serializer.serialize(field);
                if (Objects.isNull(serialize) || Objects.isNull(serializeField)) {
                    return NumberUtils.LONG_ZERO;
                }
                byte[] rowVal = connection.hGet(serialize, serializeField);
                Object deserialize = serializer.deserialize(rowVal);
                if (Objects.isNull(deserialize)) {
                    return NumberUtils.LONG_ZERO;
                }
                return Long.parseLong((String) deserialize);
            } catch (Exception e) {
                logger.error("error method getIncrHashValue, key {} field {}", key, field, e);
            }
            return NumberUtils.LONG_ZERO;
        });
    }

}
