package in.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;


@Entity
@Data
@Table(name="USER_DTLS")
public class UserDtlsEntity {
	
	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(name="FIRST_NAME")
	private String fName;
	
	@Column(name="LAST_NAME")
	private String lName;
	
	@Column(name="USER_EMAIL", unique=true)
	private String email;
	
	@Column(name="USER_PWD")
	private String password;
	
	@Column(name="USER_MOBILE")
	private Long userMobile;
	
	@Column(name="DOB")
	private LocalDate dob;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="CITY_ID")
	private Integer cityId;
	
	@Column(name="STATE_ID")
	private Integer stateId;
	
	@Column(name="COUNTRY_ID")
	private Integer countryId;
	
	@Column(name="ACC_STATUS")
	private String accStatus;
	
	@Column(name="CREATED_DATE",updatable=false)
	@CreationTimestamp
	private LocalDate createdDate;
	
	@Column(name="UPDATED_DATE",insertable=false)
    @UpdateTimestamp
	private LocalDate updatedDate;
}
