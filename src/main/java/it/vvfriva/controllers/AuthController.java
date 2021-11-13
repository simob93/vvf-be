package it.vvfriva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.LoginModel;
import it.vvfriva.models.LoginResponse;
import it.vvfriva.services.AuthService;


@Controller
@RequestMapping(path = "/auth")
public class AuthController {
	@Autowired AuthService authService;

	@PostMapping("/login")
	public @ResponseBody JsonResponse<LoginResponse> doLogin(@RequestBody LoginModel loginParam) {
		return authService.doLogin(loginParam);
	}	
}
