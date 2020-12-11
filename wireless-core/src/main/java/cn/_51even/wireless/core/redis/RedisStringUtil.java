package cn._51even.wireless.core.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis string数据结构 工具类
 * Created by fymeven on 2017/7/23.
 */
@ConditionalOnClass(name = "org.springframework.data.redis.core.StringRedisTemplate")
@Component
public final class RedisStringUtil {

    private static final Logger logger =LoggerFactory.getLogger(RedisStringUtil.class);

    private static StringRedisTemplate stringRedisTemplate;

    @Resource
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisStringUtil.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public static void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public static void removePattern(final String pattern) {
        Set<String> keys = stringRedisTemplate.keys(pattern);
        if (keys.size() > 0)
            stringRedisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public static boolean remove(final String key) {
        if (exists(key)) {
            return stringRedisTemplate.delete(key);
        }else {
            logger.info("redis key not exist:{}",key);
            return false;
        }
    }

    /**
     * 获取表达式key的size
     * @param pattern
     * @return
     */
    public static int getSize(String pattern){
        Set<String> keys = stringRedisTemplate.keys(pattern);
        return keys.size();
    }

    /**
     * 获取表达式key的集合
     * @param pattern
     * @return
     */
    public static Set getKeys(String pattern){
        Set keys = stringRedisTemplate.keys(pattern);
        return keys;
    }

    /**
     * 获取表达式value的集合
     * @param pattern
     * @return
     */
    public static Set getValues(String pattern) {
        Set values=new HashSet();
        Set keys = getKeys(pattern);
        for (Object key : keys) {
            Object value = get((String) key);
            values.add(value);
        }
        return values;
    }


    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public static boolean exists(final String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public static Object get(final String key) {
        Object result = null;
        ValueOperations<String, String> operations = stringRedisTemplate
                .opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate
                    .opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, String value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate
                    .opsForValue();
            operations.set(key, value);
            stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean expire(final String key, Long expireTime){
        return  stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

}