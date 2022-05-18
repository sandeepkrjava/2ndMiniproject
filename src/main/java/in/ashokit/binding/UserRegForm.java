package in.ashokit.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserRegForm {

	private String fName;

	private String lName;

	private String email;

	private Long mobile;

	private LocalDate dob;

	private String gender;

	private Integer cityId;

	private Integer stateId;

	private Integer countryId;

}
