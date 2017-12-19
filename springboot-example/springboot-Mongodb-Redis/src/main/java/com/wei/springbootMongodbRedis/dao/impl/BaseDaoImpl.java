package com.wei.springbootMongodbRedis.dao.impl;

import java.lang.reflect.Field;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.wei.springbootMongodbRedis.dao.BaseDao;
import com.wei.springbootMongodbRedis.utils.ReflectionUtils;

public class BaseDaoImpl<T> implements BaseDao<T> {

	/**
	 * Get the class object
	 * @return clazz
	 */
	protected Class<T> getEntityClazz(){
		return ReflectionUtils.getSuperClassGenricType(getClass());
	}
	
	
	/**
	 * MongoDB operations
	 */
	//protected MongoOperations operations;
	@Autowired
	@Qualifier("mongoTemplate")
	protected MongoTemplate operations;
	
	
	
	/**
	 * injection MongoTemplate bean by annotation @Resource
	 * @param MongoOperations operations must not be {@literal null} or empty.
	 */
	/*
	@Resource(name = "mongoTemplate")
	public void setMongoOperations(MongoTemplate operations) {
		Assert.notNull(operations);
		this.operations = operations;
	}*/
	
	/**
	 * Get MongOperations implementation by MongoTempletate
	 * @return MongoOperations 
	 */
	/*
	public MongoOperations getMongoOperations(){
		return operations;
	}*/
	
	/**
	 * return list of the entities by query.
	 * @param query
	 * @return 
	 */
	public List<T> list(Query query) {
		return this.operations.find(query, this.getEntityClazz());
	}


	/**
	 * save entity
	 * @param entity
	 * @return entity
	 */
	public T save(T entity) {
		operations.insert(entity);
		return entity;
	}

	/**
	 * insert batch entities
	 * @param entities
	 */
	public void insert(List<T> entities) {
		entities.forEach(entity -> this.save(entity));
	}


	/**
	 * update entity
	 * @param entity
	 */
	public void update(T entity) {
		try{
			// obtain id (type ObijectId) by Class object
			ObjectId id = (ObjectId) this.getEntityClazz()
					.getMethod("getId").invoke(entity);
			Update update = new Update();
			
			//obtain an array of Field objects 
			Field[] fields = this.getEntityClazz().getDeclaredFields();
			for(Field field :fields){
				String fieldName = field.getName();
				if (fieldName != null && !"id".equals(fieldName)) {
					update.set(fieldName, ReflectionUtils.getProperty(entity, fieldName));
							//BeanUtils.getNestedProperty(entity, fieldName));
							//BeanUtils.getProperty(entity, fieldName));
				}
			}
			
			// update entity
			this.operations.updateFirst( new Query(Criteria.where("id").is(id)), 
										update,
										this.getEntityClazz()
										);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	
	
	/**
	 * update batch entities
	 * @param entities
	 */
	public void update(List<T> entities){
		entities.forEach(entity -> this.update(entity));
	}
	
	/**
	 * update by query and update
	 * @param query
	 * @param update
	 */
	public void update(Query query, Update update){
		operations.findAndModify(query, update, this.getEntityClazz());
	}

	/**
	 * load entity by id
	 * @param id
	 * @return entity
	 */
	public T load(ObjectId id) {
		return operations.findById(id, this.getEntityClazz());
	}

	/**
	 * find one entity
	 * @param query
	 * @return entity
	 */
	
	public T findOne(Query query) {
		return operations.findOne(query, this.getEntityClazz());
	}
	/*
	public Optional<T> findOne(Query query){
		T value = operations.findOne(query, this.getEntityClazz());
		return Optional.ofNullable(value);
		//return operations.findOne(query, this.getEntityClazz());
	}*/

	/**
	 * find one entity by id
	 * @param id
	 * @param collectionName
	 * @return entity
	 */
	public T findById(ObjectId id, String collectionName) {
		return operations.findById(id, this.getEntityClazz(), collectionName);
	}

	/**
	 * find the entity (or entities) by query
	 * @param query
	 * @return entities
	 */
	public List<T> find(Query query) {
		return operations.find(query, this.getEntityClazz());
	}

	/**
	 * find all entities in this collection.
	 * @return entities
	 */
	public List<T> findAll() {
		return operations.findAll(this.getEntityClazz());
	}

	/**
	 * Get the number of items in this collection by query.
	 * @param  query
	 * @return sum
	 */
	public long count(Query query) {
		return operations.count(query, this.getEntityClazz());
	}

	/**
	 * Get the number of items in this collection
	 * @return sum
	 */
	public long count() {
		return this.count(new Query(Criteria.where("").is("")));
	}

	/**
	 * remove entity by query
	 * @param query
	 */
	public void remove(Query query) {
		operations.remove(query, this.getEntityClazz());
	}

	/**
	 * remove entity by id
	 * @param  id
	 */
	public void remove(ObjectId id) {
		operations.remove(new Query(Criteria.where("id").is(id)), 
				this.getEntityClazz());
	}

	/**
	 * remove entity
	 * @param entiy
	 */
	public void remove(T entity) {
		operations.remove(entity);
	}

	/**
	 * remove batch entities by id
	 * @param ids
	 */
	public void removes(List<ObjectId> ids) {
		//List<ObjectId> listId = entites.stream().map(e -> e.getId()).collect(Collectors.toList());
		ids.stream().forEach(id -> this.remove(id));
	}

	/**
	 * remove all the entities / remove this collection
	 */
	public void removeAll() {
		operations.dropCollection(this.getEntityClazz());
	}


}
