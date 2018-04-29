package com.myretail.api.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.myretail.api.entity.Product;


public interface ProductRepository extends CassandraRepository<Product> 
{		 
    @Query("Select * from Product where id=?0")
    Product findByProductId(int productId);
}
