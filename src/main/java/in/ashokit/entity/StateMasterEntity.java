package in.ashokit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "STATE_MASTER")
@Data
public class StateMasterEntity {

	@Id
	@Column(name = "STATE_ID")
	private Integer stateId;

	@Column(name = "COUNTRY_ID")
	private Integer countryId;

	@Column(name = "STATE_NAME")
	private String stateName;

}
