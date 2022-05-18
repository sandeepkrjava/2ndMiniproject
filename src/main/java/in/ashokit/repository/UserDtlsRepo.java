package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer>{

	public UserDtlsEntity findByEmailAndPassword(String email , String password);


     public UserDtlsEntity findByEmail(String email);
}
