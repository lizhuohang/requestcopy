package com.lzh.monitor.requestcopy.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lizhuohang on 30/11/2016.
 */
public class RedisUtil {
    static JedisPool pool = null;
    private static final transient Logger logger = Logger.getLogger(RedisUtil.class);
    static {
        try {
            if (null == pool) {
                String host = Setting.getString("REDIS_HOST");
                int port = Setting.getInt("REDIS_PORT" , 6379);
                GenericObjectPoolConfig config = new GenericObjectPoolConfig();
                config.setMaxTotal(200);
				pool = new JedisPool(config, host, port, 1000*60*5 );

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    public static boolean sismember(String key,String member){
        boolean onError = false;
        int retry = 5;
        boolean r = true;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();

                r = jedis.sismember(key, member);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }
    public static long sdiffstore(String dstKey, String... keys) {
        boolean onError = false;
        int retry = 5;
        long r = 0;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();

                r = jedis.sdiffstore(dstKey, keys);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    public static String get(String key) throws Exception {
        boolean onError = false;
        int retry = 5;
        String r = null;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.get(key);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    public static long del(String key) {
        boolean onError = false;
        int retry = 5;
        long r = 0;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();

                r = jedis.del(key);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    public static long sunionstore(String dstKey, String... keys) {
        boolean onError = false;
        int retry = 5;
        long r = 0;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.sunionstore(dstKey, keys);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    public static Set<String> smembers(String key) {
        boolean onError = false;
        int retry = 5;
        Set<String> r = null;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.smembers(key);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    public static long sadd(String key, String... members) throws Exception {
        boolean onError = false;
        int retry = 5;
        long r = 0;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.sadd(key, members);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    public static long srem(String key, String... members) throws Exception {
        boolean onError = false;
        int retry = 5;
        long r = 0;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.srem(key, members);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    public static long scard(String key) throws Exception {
        boolean onError = false;
        int retry = 5;
        long r = 0;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.scard(key);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    public static String srandmember(String key) throws Exception {
        boolean onError = false;
        int retry = 5;
        String r = null;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.srandmember(key);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    public static long incr(String key) throws Exception {
        int retry = 5;
        long r = 0;
        boolean onError = false;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.incr(key);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }

        return r;
    }

    public static long decr(String key) throws Exception {
        boolean onError = false;
        int retry = 5;
        long r = 0;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.decr(key);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }

        return r;
    }

    public static long expire(String key, int seconds) throws Exception {
        boolean onError = false;
        int retry = 5;
        long r = 0;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.expire(key, seconds);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }

        return r;
    }


    /**
     * 向redis的key集合放元素
     * @param key
     * @param imeis
     * @param number		一次放的个数，<=5000
     * @return
     */
    public static void sadd(String key, Set<String> imeis,int number) {
        //int step = 5;
        List<String> tempImeis = new ArrayList<String>(imeis);
        for (int i = 0; tempImeis.size() > 0;) {
            int toIndex = i + number;

            if (toIndex > tempImeis.size()) {
                toIndex = tempImeis.size();
            }
            List<String> temp = tempImeis.subList(i, toIndex);

            try {
                sadd(key, temp.toArray(new String[] {}));
            } catch (Exception e) {
                e.printStackTrace();
            }
            temp.clear();
            temp = null;
        }
    }


    /**
     * 从某个集合随机取数据
     * @param key
     * @param count
     * @return
     */
    public static Set<String> srandmember(String key,int count)   throws Exception {
        boolean onError = false;
        int retry = 5;
        Set<String> r = new HashSet<String>();
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r.addAll(jedis.srandmember(key, count));
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    e.printStackTrace();
            } finally {
                if(onError){
                    pool.returnBrokenResource(jedis);
                }else{
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }



    /**
     * 从多个集合随机取数据
     * @param sourceKeys
     * @param count
     * @return
     */
    public static Set<String> srandmember(int count, String ...sourceKeys)  throws Exception {

        Set<String> r =null;
        if(sourceKeys.length==1){
            r = srandmember(sourceKeys[0],count);
        }else{
            //集合合并
            String tmpKey="srandmember-"+System.currentTimeMillis();
            sunionstore(tmpKey,sourceKeys);

            //取数据
            r = srandmember(tmpKey,count);

            //删除临时集合
            del(tmpKey);
        }


        return r;
    }

    public static boolean exists(String key) throws Exception {
        boolean onError = false;
        int retry = 5;
        boolean r = true;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.exists(key);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }

        return r;
    }

    public static long incrBy(String key, long integer) throws Exception {
        int retry = 5;
        long r = 0;
        boolean onError = false;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.incrBy(key, integer);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }

        return r;
    }


    public static String set(String key,String val) throws Exception {
        int retry = 5;
        String r = "";
        boolean onError = false;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.set(key, val);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }

        return r;
    }

    /**
     * @auther lizhuohang@58peilian.com
     *
     * @param key 文字key
     * @param score key对应的分数
     * @param member member名称
     * @return 增加member的个数
     * @throws Exception
     */
    public static long zadd(String key,Double score ,String member) throws Exception {
        int retry = 5;
        long r = 0;
        boolean onError = false;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.zadd(key, score, member);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }
    /**
     *
     * @param key 分数存放的key
     * @param start 开始的元素，而不是分数
     * @param end 结束元素，-1为最后一个，-2为倒数第二个
     * @return 对应的member
     * @throws Exception
     */
    public static Set<String> zrange(String key,long start ,long end) throws Exception {
        int retry = 5;
        Set<String> r = new HashSet<String>();
        boolean onError = false;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.zrange(key, start, end);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    /**
     *
     * @param key 键
     * @param member
     * @return 返回member对应key中的值
     * @throws Exception
     */
    public static Double zscore(String key,String member) throws Exception {
        int retry = 5;
        Double r = 0d;
        boolean onError = false;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.zscore(key, member);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    /**
     *
     * @param key
     * @param start
     * @param end
     * @return 返回有续集key中指定范围[通过索引start stop,而不是分数]的member，返回member根据score按降序排列
     * @throws Exception
     */
    public static Set<String> zrevrange(String key,long start ,long end) throws Exception {
        int retry = 5;
        Set<String> r = new HashSet<String>();
        boolean onError = false;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.zrevrange(key, start, end);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    /**
     *
     * @param key
     * @param start
     * @param end
     * @return 返回有续集key中指定范围[通过索引,而不是分数]的member，返回member根据score按降序排列，返回结果带有score
     * @throws Exception
     */
    public static Set<Tuple> zrevrangewithscore(String key, long start , long end) throws Exception {
        int retry = 5;
        Set<Tuple> r = new HashSet<Tuple>();
        boolean onError = false;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.zrevrangeWithScores(key, start, end);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    /**
     *
     * @param key
     * @param member
     * @param incr 加的值
     * @return 返回增加后的score
     * @throws Exception
     */
    public static Double zincrby(String key,String member,Double incr) throws Exception {
        int retry = 5;
        Double r = 0d;
        boolean onError = false;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.zincrby(key, incr, member);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }

    /**
     *
     * @param key
     * @param member
     * @return 根据score从高到低排序，返回member在有序集key中的index
     * @throws Exception
     */
    public static Long zrevrank(String key,String member) throws Exception {
        int retry = 5;
        Long r = 0l;
        boolean onError = false;
        while (retry-- > 0) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                r = jedis.zrevrank(key, member);
                break;
            } catch (Exception e) {
                onError = true;
                if (retry == 0)
                    throw e;
            } finally {
                if (onError) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
                onError = false;
            }
        }
        return r;
    }
}
