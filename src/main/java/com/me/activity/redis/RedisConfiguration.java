package com.me.activity.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * @program: activity
 * @description: redis配置
 * @author: lic
 * @create: 2020-01-09 17:21
 **/
@Configuration
@ConfigurationProperties(prefix = "custom.redis")
@Getter
@Setter
public class RedisConfiguration {
    private String host;
    private int port;
    private int timeout;//毫秒
    private String password;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;//毫秒
    private int dataBase;//数据库

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //最大空闲数
        jedisPoolConfig.setMaxIdle(this.getPoolMaxIdle());
        //连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(this.getPoolMaxTotal());
        //最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(this.getPoolMaxWait());
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(300000);
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(10);
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        //是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(true);
        //在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig){
        int db = this.getDataBase();
        db = (db >0 && db < 16 ) ? db : Protocol.DEFAULT_DATABASE;
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, this.getHost(),
                this.getPort(), this.getTimeout(), this.getPassword(),db);
        return jedisPool;
    }
}
