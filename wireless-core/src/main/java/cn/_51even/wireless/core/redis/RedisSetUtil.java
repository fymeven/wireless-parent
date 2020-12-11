package cn._51even.wireless.core.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@ConditionalOnClass(name = "org.springframework.data.redis.core.RedisTemplate")
@Component
public class RedisSetUtil {

    private static RedisTemplate redisTemplate;

    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSetUtil.redisTemplate = redisTemplate;
    }

    /**
     * 添加元素
     * @param key
     * @param value
     */
    public static void add(String key, Object value) {
        Long add = redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 移除元素
     * @param key
     * @param value
     */
    public static void remove(String key, Object value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 检查是否包含
     * @param key
     * @param value
     * @return
     */
    public static boolean contains(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取set列表
     * @param key
     * @return
     */
    public static Set<String> values(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取set长度
     * @param key
     * @return
     */
    public static int size(String key) {
        return redisTemplate.opsForSet().members(key).size();
    }

    /**
     * 返回多个集合的并集  sunion
     *
     * @param key1
     * @param key2
     * @return
     */
    public static Set<String> union(String key1, String key2) {
        return redisTemplate.opsForSet().union(key1, key2);
    }

    /**
     * 返回多个集合的交集 sinter
     *
     * @param key1
     * @param key2
     * @return
     */
    public static Set<String> intersect(String key1, String key2) {
        return redisTemplate.opsForSet().intersect(key1, key2);
    }

    /**
     * 返回集合key1中存在，但是key2中不存在的数据集合  sdiff
     *
     * @param key1
     * @param key2
     * @return
     */
    public static Set<String> diff(String key1, String key2) {
        return redisTemplate.opsForSet().difference(key1, key2);
    }

}
