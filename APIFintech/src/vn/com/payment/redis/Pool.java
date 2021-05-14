package vn.com.payment.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import vn.com.payment.config.MainCfg;

public class Pool {
	static JedisPool pool = null;

	public static Jedis getConnection() throws Exception {
		Exception e1=null;
		boolean flag=false;
//		System.out.println("Redis host: "+ MainCfg.host);

		int counter = 0;
		Jedis jedis = null;
		if (pool == null) {
			JedisPoolConfig jedisconf = new JedisPoolConfig();
//			jedisconf.setMaxActive(MainCfg.max_connect);
			jedisconf.setMaxIdle(MainCfg.max_idle);
			jedisconf.setMinIdle(MainCfg.min_idle);
			jedisconf.setTestOnBorrow(MainCfg.testOnBorrow);
			jedisconf.setTestOnReturn(true);
//			jedisconf.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
//			System.out.println("Redis host: "+ MainCfg.host);

			if(MainCfg.isAuth==1){
				pool = new JedisPool(jedisconf, MainCfg.host,MainCfg.port, MainCfg.redispool_connecttimeout,MainCfg.pass);
			}else{
				pool = new JedisPool(jedisconf, MainCfg.host,MainCfg.port, MainCfg.redispool_connecttimeout);
			}
		}
//		while (counter < 10) {
//			try {
//				jedis = pool.getResource();
//				jedis.select(MainCfg.index);
//			} catch (Exception e) {
//				if (counter==8) {
//					throw e;
//				}
//			}
//			if (jedis != null)
//				return jedis;
//			try {Thread.sleep(50);} catch (InterruptedException e) {}
//			counter++;
//		}
		
		
		for (int i = 0; i < 10; i++) {
			try {
				jedis = pool.getResource();
				jedis.select(MainCfg.index);//test thay ok trong [0,15]
				flag=true;
				break;//thoat khoi vong for
			} catch (Exception e) {
				e1=e;
			}
			try {Thread.sleep(10);} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (flag==false) throw e1;
		return jedis;
	}

	
	
	public static void ReleaseConnection(Jedis jedis) throws Exception {
		if (jedis != null)
			try {
//				 jedis.disconnect();
//				jedis.quit();
				pool.returnResource(jedis);
			} catch (Exception e) {
				throw e;
			}
	}

	public void shutdown() {
		pool.destroy();
	}
	
	public static void main(String[] args) {
		try {
			Jedis j1 =getConnection();
//			Jedis j2 =getConnection();
//			Jedis j3 =getConnection();
//			Jedis j4 =getConnection();
//			Jedis j5 =getConnection();
//			Jedis j6 =getConnection();
//			Jedis j7 =getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
