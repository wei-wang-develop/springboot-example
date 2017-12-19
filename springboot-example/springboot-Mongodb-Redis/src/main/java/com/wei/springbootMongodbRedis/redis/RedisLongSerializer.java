package com.wei.springbootMongodbRedis.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class RedisLongSerializer implements RedisSerializer<Long>{

	@Override
	public byte[] serialize(Long aLong) throws SerializationException {
		if(aLong == null){
			return new byte[0];
		}
		return aLong.toString().getBytes();
	}

	@Override
	public Long deserialize(byte[] bytes) throws SerializationException {
		if(bytes == null || bytes.length ==0){
			return null;
		}
		return Long.parseLong(new String(bytes));
	}

}
