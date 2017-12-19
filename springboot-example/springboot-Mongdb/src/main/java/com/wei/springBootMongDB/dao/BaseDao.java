package com.wei.springBootMongDB.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Creation of a Common Base Repository interfaces, all of Repository need to extend this Interface.
 * 创建一个公共接口
 * @author Wei WANG
 *
 * @param <T>
 */
public interface BaseDao<T> {

	/**
	 * save entity
	 * @param T entity
	 * @return T entity
	 */
	public T save(T entity);
	
	/**
	 * insert batch entities
	 * @param List<T> entities
	 */
	public void insert(List<T> entities);
	
	/**
	 * update entity
	 * @param T entity
	 */
	public void update(T entity);
	
	/**
	 * update batch entities
	 * @param List<T> entities
	 */
	public void update(List<T> entities);
	
	/**
	 * update by query and update
	 * @param org.springframework.data.mongodb.core.query.Query
	 * @param org.springframework.data.mongodb.core.query.Update
	 */
	public void update(Query query, Update update);
	
	
	/**
	 * load entity by id
	 * @param org.bson.types.ObjectId  id
	 * @return T entity
	 */
	public T load(ObjectId id);
	
	/**
	 * find one entity
	 * @param Query query
	 * @return T entity
	 */
	public T findOne(Query query);
	
	/**
	 * find one entity by id
	 * @param org.bson.types.ObjectId  id
	 * @param String collectionName
	 * @return T entity
	 */
	public T findById(ObjectId id, String collectionName);
	
	/**
	 * find the entity (or entities) by query
	 * @param org.springframework.data.mongodb.core.query.Query query
	 * @return List<T> entities
	 */
	public List<T> find(Query query);
	
	/**
	 * find all entities in this collection.
	 * @return List<T> entities
	 */
	public List<T> findAll();
	
	/**
	 * Get the number of items in this collection by query.
	 * @param org.springframework.data.mongodb.core.query.Query query
	 * @return long sum
	 */
	public long count(Query query);
	
	/**
	 * Get the number of items in this collection
	 * @return long sum
	 */
	public long count();
	
	/**
	 * remove entity by query
	 * @param org.springframework.data.mongodb.core.query.Query query
	 */
	public void remove(Query query);
	
	/**
	 * remove entity by id
	 * @param org.bson.types.ObjectId  id
	 */
	public void remove(ObjectId id);
	
	/**
	 * remove entity
	 * @param T entiy
	 */
	public void remove(T entity);
	
	/**
	 * remove batch entities by id
	 * @param List<ObjectId> ids
	 */
	public void removes(List<ObjectId> ids);
	
	/**
	 * remove all the entities / remove this collection
	 */
	public void removeAll();
}
