package demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	List<User> findAll();
	
}
