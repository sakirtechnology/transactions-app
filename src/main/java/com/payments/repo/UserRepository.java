package com.payments.repo;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.payments.model.User;

@Mapper
public interface UserRepository {

	@Select("select * from user")
	public List<User> findAll();

	@Insert("insert into user(email, accountno, phonenumber) " + " values (#{email}, #{accountno}, #{phonenumber})")
	public int insert(User user);

	@Select("select email from user where userid=#{userid}")
	public String findById(String userid);

}
