package demo.jms_sevices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.model.User;
import demo.service.ServerException;
import demo.service.UserServiceImpl;

@Component
public class RequestHandler {
	
	
	@Autowired
	UserServiceImpl userService;
	
	public RequestHandler(){
	}
	
	public String handle(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> model = new HashMap<String, Object>();
		   
	    try {
			model = mapper.readValue(json, 
					new TypeReference<HashMap<String, Object>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		} 
	    
	    Map<String, Object> response = new HashMap<String, Object>();
	    try {
			switch ((String)model.get("method")) {
			case "get":
				response.put("users", userService.getAllUsers());
				break;
			case "delete":
				userService.deleteUser((Integer)model.get("userId"));
				break;
			case "save":
				userService.saveUser(mapper.convertValue(model.get("user"), User.class));
				break;
			}
			response.put("status", true);
		
	    } catch (ServerException e) {
	    	//commit error
			e.printStackTrace();
			response.put("status", false);
			response.put("message", e.getMessage());
		}
	    
	    //generate response
	    try {
			return mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    return "server error";
	    
	}

}
