package com.sepim.springboot.utils;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.commands.JedisCommands;
import redis.clients.jedis.params.SetParams;

/**
 * Redis分布式锁
 *
 * @author: SorrowPhage
 * @date: 2023/8/11
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisLockUtils {

    private static final Long RELEASE_SUCCESS = 1L;

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";
    // 当前设置 过期时间单位, EX = seconds; PX = milliseconds
    private static final String SET_WITH_EXPIRE_TIME = "EX";


    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


    private final StringRedisTemplate stringRedisTemplate;


    /**
     * 加锁
     * @param lockKey 加锁键
     * @param id 加锁唯一标识
     * @param seconds 锁过期时间
     * @return
     */
    public boolean tryLock(String lockKey, String id, long seconds) {
        return stringRedisTemplate.execute((RedisCallback<Boolean>) redisConnect -> {
            Object nativeConnection = redisConnect.getNativeConnection();
            RedisSerializer<String> stringRedisSerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
            byte[] keyBytes = stringRedisSerializer.serialize(lockKey);
            byte[] idBytes = stringRedisSerializer.serialize(id);

            // lettuce连接包下 redis 单机模式
            if (nativeConnection instanceof RedisAsyncCommands) {
                RedisAsyncCommands connect = (RedisAsyncCommands) nativeConnection;
                RedisCommands commands = connect.getStatefulConnection().sync();
                String result = commands.set(keyBytes, idBytes, SetArgs.Builder.nx().ex(seconds));
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                }
            }

            // lettuce连接包下 redis 集群模式
            if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                RedisAdvancedClusterAsyncCommands connection = (RedisAdvancedClusterAsyncCommands) nativeConnection;
                RedisAdvancedClusterCommands commands = connection.getStatefulConnection().sync();
                String result = commands.set(keyBytes, idBytes, SetArgs.Builder.nx().ex(seconds));
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                }
            }

            if (nativeConnection instanceof JedisCommands) {
                JedisCommands jedis = (JedisCommands) nativeConnection;
                String result = jedis.set(new String(keyBytes), new String(idBytes), SetParams.setParams().nx().ex((int) seconds));
                //String result = jedis.set(lockKey, clientId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, seconds);
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                }
            }
            return false;
        });
    }


    /**
     * 与 tryLock 相对应，用作释放锁
     *
     * @param lockKey
     * @param clientId
     * @return
     */
    public boolean releaseLock(String lockKey, String clientId) {
        DefaultRedisScript<Integer> redisScript = new DefaultRedisScript<>();
        //使用lua脚本可以保证redis的原子性
        redisScript.setScriptText(RELEASE_LOCK_SCRIPT);
        redisScript.setResultType(Integer.class);

        Object execute = stringRedisTemplate.execute((RedisConnection connection) -> connection.eval(
                RELEASE_LOCK_SCRIPT.getBytes(),
                ReturnType.INTEGER,
                1,
                lockKey.getBytes(),
                clientId.getBytes()));
        if (RELEASE_SUCCESS.equals(execute)) {
            return true;
        }
        return false;
    }


}
