package it.vvfriva.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.entity.Person;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.services.PersonService;
/**
 * 
 * @author simone
 *
 */
@Controller
@RequestMapping(path="/person")
public class PersonController {
	
	
	@Autowired PersonService personService;
	
	@GetMapping(path="/listbyarea")
	public @ResponseBody JsonResponse<List<Person>> listByArea(@RequestParam String area) {
		return personService.listByArea(area);
	}
	
	@GetMapping(path="/getby")
	public @ResponseBody JsonResponse<List<Person>> getBy(@RequestParam Integer idArea) {
		return personService.getBy(idArea);
	}
	
	@PostMapping(path = "/new")
	public @ResponseBody JsonResponse<Person> save(@RequestBody Person person) {
		return personService.save(person);
	}
	
	@PostMapping(path = "/update")
	public @ResponseBody JsonResponse<Person> update(@RequestBody Person person) {
		return personService.update(person);
	}
	
	@GetMapping(path = "/{id}/delete")
	public @ResponseBody JsonResponse<Person> update(@PathVariable Integer id) {
		return personService.delete(id);
	}
	
	
}
