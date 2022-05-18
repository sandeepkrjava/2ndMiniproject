package in.ashokit.service;

import java.util.Map;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.UnlockAccForm;
import in.ashokit.binding.UserRegForm;


public interface UserMgmtService {

	public String login(LoginForm loginForm);

	public String emailCheck(String email);

	public String unlockAcc(UnlockAccForm unlockform);

	public String forgetPwd(String email);

	public String registerUser(UserRegForm userform);

	public Map<Integer, String> loadCountry();

	public Map<Integer, String> loadState(Integer countryId);

	public Map<Integer, String> loadCity(Integer stateId);

}