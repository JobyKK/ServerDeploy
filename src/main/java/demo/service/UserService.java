package demo.service;

import java.util.List;

import demo.model.User;

public interface UserService {

	List<User> getAllUsers() throws ServerException;

	String updateUser(User user) throws ServerException;

	String saveUser(User user) throws ServerException;

	String deleteUser(Integer id) throws ServerException;


	
}
