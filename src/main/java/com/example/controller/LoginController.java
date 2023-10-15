package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/loginForm")
	public String getLogin() {
		return "loginForm";
	}
}

//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.entity.User;
//import com.example.service.LoginUserService;
//
//@Controller
//@RequestMapping("loginForm")
//public class LoginController {
//
//    private final LoginUserService loginUserService;
//
//    @Autowired
//    public LoginController(LoginUserService loginUserService) {
//        this.loginUserService = loginUserService;
//    }
//
//    @GetMapping
//    public String getLogin(Model model) {
//        List<User> user = this.loginUserService.loadUserByUsername(email);
//        model.addAttribute("users", user);
//        return "loginForm";
//    }
//}