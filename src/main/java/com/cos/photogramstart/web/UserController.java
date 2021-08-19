package com.cos.photogramstart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
	
	
	//사람마다 다를수 있으니 
	//  "/user/profile"로 매핑받지 말고
	//  "/user/{id}" 로 매핑받는다
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id) {
		return "user/profile";
		
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id) {
		return "user/update";
		
	}
}
