package in.ashokit.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.binding.UserRegForm;
import in.ashokit.service.UserMgmtService;

@RestController
public class RegistrationRestController {

	@Autowired
	private UserMgmtService service;

	@GetMapping("/email/{email}")
	public String emailCheck(@PathVariable("email") String email) {
		return service.emailCheck(email);

	}

	@GetMapping("/country")
	public Map<Integer, String> getCountry() {
		return service.loadCountry();
	}

	@GetMapping("/state/{countryId}")
	public Map<Integer, String> getState(@PathVariable("countryId") Integer countryId) {
		return service.loadState(countryId);
	}

	@GetMapping("/city/{stateId}")
	public Map<Integer, String> getCity(@PathVariable("stateId") Integer stateId){
		
	
		return service.loadCity(stateId);
	}
	
	@PostMapping("/user")
	public String userReg(@RequestBody  UserRegForm form) {
		return service.registerUser(form);
	}
}

