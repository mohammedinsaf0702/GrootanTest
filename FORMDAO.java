package com.example.demo;

import java.util.List;

import org.springframework.data.domain.Example;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;




@RepositoryRestResource(collectionResourceRel = "Forms", path="Forms")
public interface FORMDAO extends MongoRepository<FormPOJO,Integer>
{


	@Override
	default long count() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}




