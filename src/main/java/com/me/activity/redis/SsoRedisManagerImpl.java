package com.me.activity.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: me
 * @description: redisManager实现
 * @author: lic
 * @create: 2019-12-02 14:53
 **/
@Service
public class SsoRedisManagerImpl implements SsoRedisManager {
    private static final Logger logger= LoggerFactory.getLogger(SsoRedisManagerImpl.class);
    @Autowired
    private JedisPool jedisPool;
    /*默认每次迭代返回的数量*/
    protected static final int DEFAULT_COUNT = 100;

    private int count = DEFAULT_COUNT;

    private Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     * get value from redis
     * @param key key
     * @return value
     */
    public byte[] get(byte[] key) {
        if (key == null) {
            return null;
        }
        byte[] value = null;
        Jedis jedis = getJedis();
        try {
            value = jedis.get(key);
        } finally {
            returnToPool(jedis);
        }
        return value;
    }

    /**
     * set
     * @param key key
     * @param value value
     * @param expireTime expire time
     * @return value
     */
    public byte[] set(byte[] key, byte[] value, int expireTime) {
        if (key == null) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
            if (expireTime > 0) {
                jedis.expire(key, expireTime);
            }
        } finally {
            returnToPool(jedis);
        }
        return value;
    }

    /**
     * Delete a key-value pair.
     * @param key key
     */
    public void del(byte[] key) {
        if (key == null) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * Return the size of redis db.
     * @param pattern key pattern
     * @return key-value size
     */
    public Long dbSize(byte[] pattern) {
        long dbSize = 0L;
        Jedis jedis = getJedis();
        try {
            ScanParams params = new ScanParams();
            params.count(count);
            params.match(pattern);
            byte[] cursor = ScanParams.SCAN_POINTER_START_BINARY;
            ScanResult<byte[]> scanResult;
            do {
                scanResult = jedis.scan(cursor, params);
                List<byte[]> results = scanResult.getResult();
                for (byte[] result : results) {
                    dbSize++;
                }
                cursor = scanResult.getCursorAsBytes();
            } while (scanResult.getStringCursor().compareTo(ScanParams.SCAN_POINTER_START) > 0);
        } finally {
            returnToPool(jedis);
        }
        return dbSize;
    }

    /**
     * Return all the keys of Redis db. Filtered by pattern.
     * @param pattern key pattern
     * @return key set
     */
    public Set<byte[]> keys(byte[] pattern) {
        Set<byte[]> keys = new HashSet<byte[]>();
        Jedis jedis = getJedis();

        try {
            ScanParams params = new ScanParams();
            params.count(count);
            params.match(pattern);
            byte[] cursor = ScanParams.SCAN_POINTER_START_BINARY;
            ScanResult<byte[]> scanResult;
            do {
                scanResult = jedis.scan(cursor, params);
                keys.addAll(scanResult.getResult());
                cursor = scanResult.getCursorAsBytes();
            } while (scanResult.getStringCursor().compareTo(ScanParams.SCAN_POINTER_START) > 0);
        } finally {
            returnToPool(jedis);
        }
        return keys;

    }

    public Set<String> keys(String pattern){
        Set<String> keys=new HashSet<>();
        Jedis jedis = getJedis();
        try {
            ScanParams sp = new ScanParams();
            sp.match(pattern);
            sp.count(count);
            String cursor = "0";
            do{
                ScanResult<String> ret = jedis.scan(cursor, sp);
                List<String> result = ret.getResult();
                if(!CollectionUtils.isEmpty(result))
                    keys.addAll(result);
                //再处理cursor
                cursor = ret.getStringCursor();
            }while(!cursor.equals("0"));
            return keys;
        }catch (Exception e){
            logger.error("RedisManager keys(String pattern) 失败;异常信息:{}",e);
        }finally {
            returnToPool(jedis);
        }
        return keys;
    }

    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
