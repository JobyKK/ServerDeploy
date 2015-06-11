package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import demo.model.User;
import demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() throws ServerException{
		return userRepository.findAll();
	}
	
	@Override
	public String updateUser(User user) throws ServerException{
		try {
			userRepository.save(user);
		} catch(UnexpectedRollbackException ex) {
			throw new ServerException(ex);
		}
		return "OK";
	}
	
	@Override
	public String saveUser(User user) throws ServerException{
		try {
			userRepository.save(user);
		} catch(UnexpectedRollbackException ex) {
			throw new ServerException(ex);
		}
		return "OK";
	}
	
	@Override
	public String deleteUser(Integer id) throws ServerException{
		
		try {
			userRepository.delete(id);
		} catch(UnexpectedRollbackException ex) {
			//if(ex.getMostSpecificCause())
			throw new ServerException("there is no user", ex);
		}
		return "OK";
	}
	
}
