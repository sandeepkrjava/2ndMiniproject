package in.ashokit.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.UnlockAccForm;
import in.ashokit.binding.UserRegForm;
import in.ashokit.entity.CityMasterEntity;
import in.ashokit.entity.CountryMasterEntity;
import in.ashokit.entity.StateMasterEntity;
import in.ashokit.entity.UserDtlsEntity;
import in.ashokit.repository.CityMasterRepo;
import in.ashokit.repository.CountryMasterRepo;
import in.ashokit.repository.StateMasterRepo;
import in.ashokit.repository.UserDtlsRepo;
import in.ashokit.utils.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserDtlsRepo userRepo;

	@Autowired
	private CityMasterRepo cityRepo;

	@Autowired
	private CountryMasterRepo countryRepo;

	@Autowired
	private StateMasterRepo stateRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String login(LoginForm loginForm) {
		UserDtlsEntity entity = userRepo.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());

		if (entity == null) {
			return "not a valid record found";
		}
		if (entity != null && entity.getAccStatus().equalsIgnoreCase("locked")) {
			return "your acc is locked till now";
		}
		return "succesfully login account";
	}

	@Override
	public String emailCheck(String email) {

		UserDtlsEntity entity = userRepo.findByEmail(email);
		if (entity == null) {
			return "email is unique";
		}
		return "already this email is exists";
	}

	@Override
	public String unlockAcc(UnlockAccForm unlockAccform) {

		if (!unlockAccform.getNewPassword().equals(unlockAccform.getConfirmNewPassword())) {
			return "password and confirm password should be same";
		}
		UserDtlsEntity entity = userRepo.findByEmailAndPassword(unlockAccform.getEmail(),
				unlockAccform.getTempPassword());

		if (entity == null) {
			return "incorrect temp password";
		}
		entity.setPassword(unlockAccform.getNewPassword());
		entity.setAccStatus("unlocked");
		return "success ";
	}

	@Override
	public String forgetPwd(String email) {
		UserDtlsEntity entity = userRepo.findByEmail(email);
		if (entity == null) {
			return "invalid email";
		}
		String fileName = "RecoverpasswordBodyTemplate.txt";
		String body = readMailBody(fileName, entity);
		String subject = "recover password ......AshokiIt";
		boolean isSent = emailUtils.sendEmail(email, subject, body);
		if (isSent) {
			return "password sent to registered email";
		}
		return "error occured";
	}

	@Override
	public String registerUser(UserRegForm userform) {

		UserDtlsEntity entity = new UserDtlsEntity();

		BeanUtils.copyProperties(userform, entity);
		entity.setAccStatus("locked");

		entity.setPassword(generateRandomPassword());

		UserDtlsEntity savedEntity = userRepo.save(entity);

		String email = userform.getEmail();
		String subject = "user Regsitration.........AshokIT";
		String fileName = "UnlockAccountBodyTemplate.txt";
		String body = readMailBody(fileName, entity);

		boolean isSent = emailUtils.sendEmail(email, subject, body);

		if (savedEntity.getUserId() != null && isSent) {
			return "succesfully message sent";
		}
		return "failure";
	}

	@Override
	public Map<Integer, String> loadCountry() {

		List<CountryMasterEntity> country = countryRepo.findAll();

		Map<Integer, String> countryMap = new HashMap<>();
		for (CountryMasterEntity entity : country) {
			countryMap.put(entity.getCountryId(), entity.getCountryName());
		}

		return countryMap;
	}

	@Override
	public Map<Integer, String> loadState(Integer countryId) {

		List<StateMasterEntity> states = stateRepo.findByCountryId(countryId);
		Map<Integer, String> stateMap = new HashMap<>();

		for (StateMasterEntity state : states) {
			stateMap.put(state.getStateId(), state.getStateName());
		}
		return stateMap;
	}

	@Override
	public Map<Integer, String> loadCity(Integer stateId) {

		List<CityMasterEntity> cities = cityRepo.findByStateId(stateId);

		Map<Integer, String> cityMap = new HashMap<>();

		for (CityMasterEntity city : cities) {
			cityMap.put(city.getCityId(), city.getCityName());
		}

		return cityMap;
	}

	public String generateRandomPassword() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	private String readMailBody(String fileName, UserDtlsEntity entity) {
		String mailbody = null;
		try {

			StringBuffer sb = new StringBuffer();

			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();

			while (line != null) { // reading line by line data
				sb.append(line);
				line = br.readLine();
			}
			mailbody = sb.toString();
			mailbody = mailbody.replace("{FNAME}", entity.getFName());
			mailbody = mailbody.replace("{LNAME}", entity.getLName());
			mailbody = mailbody.replace("{TEMP-PASSWORD}", entity.getPassword());

			mailbody = mailbody.replace("{PWD}", entity.getPassword());

			br.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return mailbody;
	}
}
