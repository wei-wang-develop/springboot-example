package com.wei.springBootMongDB.dao2;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.wei.springBootMongDB.domain.Email;
import com.wei.springBootMongDB.domain.User;

/**
 * UserRepository interface.
 * Create UserRepository interface to access {@link User}'s
 * 
 * @author Wei WANG
 * @description: MongoRepository extends PagingAndSortingRepository extends Repository.
 * 要使用 Repository 的功能，先继承 MongoRepository<T, TD> 接口，
 * 其中 T 为仓库保存的 bean 类，TD为该 bean 的唯一标识的类型，一般为ObjectId。
 * 之后在service中注入该接口就可以使用，无需实现里面的方法，spring会根据定义的规则自动生成。
 * 
 * MongoRepository与HibernateTemplete相似，提供一些基本的方法，实现的方法有:
 * findOne(),save(),count(),findAll(),findAll(Pageable)，
 * delete(),deleteAll(),基本就这几个.
 *
 */
public interface UserRepository extends MongoRepository<User, ObjectId>{

	User findByUserName(String userName);
	User findByAge(Long age);
	User findByEmailAddress(Email email);
	User findByBirthday(LocalDate birthday);
	
	// MongoRepository 的 save()兼具插入和更新的功能
	
}
