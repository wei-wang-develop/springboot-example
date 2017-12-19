package com.wei.springbootRedis.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.cypher.query.Pagination;
import org.neo4j.ogm.cypher.query.SortOrder;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * 分页查询设计
 * @descriptor 对于新型的Neo4j 数据库来说，由于它的资源库遵循了JPA 的规范标准来设计，
 * 在分页查询方面有的地方还不是很完善，所以在分页查询中，设计了一个服务类来处
 * 理，以下代码 所示。其中，使用 Class<T> 传入调用的实体对象，使用 Pageable
 * 传入页数设定和排序字段设定的参数，使用 Filters 传入查询的一些节点属性设定的参数。
 * 
 * @author Wei WANG
 */
@Service
public class PageService<T> {
	
	@Autowired
	private Session session;
	
	//分页 Pageable pageable, List<T> results 页数设定和排序字段设定
	public Page<T> findAll(Class<T> clazz, Pageable pageable, Filters filters){

        Collection<T> data = this.session.loadAll(clazz, filters, 
        				convert(pageable.getSort()), 
        				new Pagination(pageable.getPageNumber(), pageable.getPageSize()), 1);

        return updatePage(pageable, new ArrayList<T>(data));

    }
	
	private Page<T> updatePage(Pageable pageable, List<T> results){
		int pageSize =  pageable.getPageSize();
		int pageOffset = pageable.getOffset();
		int total = pageOffset + results.size() + (results.size()== pageSize? pageSize:0);
		return new PageImpl<>(results, pageable, (long)total);
	}
	
	private SortOrder convert(Sort sort) {
		SortOrder sortOrder = new SortOrder();
		if(sort != null) {
			Iterator<?> var3 = sort.iterator();
			while(var3.hasNext()) {
				Sort.Order order = (Sort.Order)var3.next();
                if(order.isAscending()) {
                    sortOrder.add(new String[]{order.getProperty()});
                } else {
                    sortOrder.add(SortOrder.Direction.DESC, new String[]{order.getProperty()});
                }
			}
		}
		return sortOrder;
	}

}
