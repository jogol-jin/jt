//package redis;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.junit.Test;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPoolConfig;
//import redis.clients.jedis.JedisShardInfo;
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPool;
//
//public class TestJedis {
//	@Test
//	public void test01(){
//		//创建jedis对象
//		Jedis jedis = new Jedis("192.168.102.100", 6379);
//		String a = jedis.get("a");
//		System.out.println(a);
//		
//		jedis.close();
//	}
//	@Test
//	public void testJedis(){
//		Jedis jedis = new Jedis("192.168.102.101", 6379);
//		jedis.select(15);
//		String a = jedis.get("name16620");
////		Set<String> keys = jedis.keys("a");
//		System.out.println(a);
//	}
//	
//	@Test
//	public void shard(){
//		JedisPoolConfig poolConfig = new JedisPoolConfig();
//		poolConfig.setMaxTotal(200);
//		
//		
//		List<JedisShardInfo> shards =  new ArrayList<JedisShardInfo>();
//		JedisShardInfo info1 = new JedisShardInfo("192.168.102.100", 6379);
//		shards.add(info1);
//		JedisShardInfo info2 = new JedisShardInfo("192.168.102.100", 6380);
//		shards.add(info2);
//		JedisShardInfo info3 = new JedisShardInfo("192.168.102.101", 6379);
//		shards.add(info3);
//		
//		ShardedJedisPool pool = new ShardedJedisPool(poolConfig, shards);
//		
//		ShardedJedis jedis = pool.getResource();
//		jedis.set("a", "我的aa");
//		for(int i=0; i<100000; i++){
//			jedis.set("name" + i, "我的name");
//		}
////		jedis.set("name11", "我的name");
////		String a = jedis.get("a");
////		System.out.println(a);
//	}
//	@Test
//	public void MyPool(){
//		JedisPoolConfig poolConfig = new JedisPoolConfig();
//		poolConfig.setMaxTotal(200);
//		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
//		JedisShardInfo info1 = new JedisShardInfo("192.168.102.100", 6379);
//		shards.add(info1);
//		ShardedJedisPool pool = new ShardedJedisPool(poolConfig, shards);
//		ShardedJedis jedis = pool.getResource();
//		String a = jedis.lindex("a", 15);
////		String a = jedis.get("a");
////		String b = jedis.lpop("a");
//		
//		System.out.println(a);
//	}
//}
