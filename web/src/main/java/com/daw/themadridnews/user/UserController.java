package com.daw.themadridnews.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserComponent userComponent;

	@RequestMapping("/user-settings")
	public String userSettings(Model model, HttpServletRequest request) {
		userComponent.checkRolesAndName(model, request);
		User user =userComponent.getLoggedUser();
		model.addAttribute("user_name", user.getName());
		model.addAttribute("user_lastName", user.getLastName());
		model.addAttribute("user_alias", user.getAlias());
		model.addAttribute("user_alias", user.getAlias());
		model.addAttribute("user_city", user.getCity());
		model.addAttribute("user_phone", user.getPhoneNumber());
		model.addAttribute("user_description", user.getDescription());
		model.addAttribute("user_url", user.getPersonalWeb());
		return "user-settings";
	}

	@RequestMapping("/#")
	public String userSettingsSave(Model model, HttpServletRequest request) {
		User user =userComponent.getLoggedUser();

		return userSettings(model, request);
	}

	@RequestMapping("/user_sign")
	public String user_sign(Model model, User user){
                userRepository.save(user);
                User user1 = userRepository.findByName("Pepe");

                System.out.println(user.getName());

		return "user_sign";
	}
}