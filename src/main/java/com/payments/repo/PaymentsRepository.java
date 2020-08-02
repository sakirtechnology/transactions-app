package com.payments.repo;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.payments.model.Transaction;

@Mapper
public interface PaymentsRepository {

	@Select("select * from transaction")
	public List<Transaction> findAll();

	@Insert("insert into transaction(transactionId, type, createdDate, createdBy) "
			+ " values (#{transactionId}, #{type}, #{createdDate}, select userid from User where email=#{createdBy})")
	public int insert(Transaction station);

	@Update("update station set name=#{name}, "
			+ " hdenabled=#{hdEnabled}, callsign=#{callSign} where stationid=#{stationId}")
	public int update(Transaction station);

	@Select("select * from transaction where createdBy = select userid from user where email=#{email}")
	public List<Transaction> findByEmail(String email);

	@Delete("delete from transaction where transactionid = #{id}")
	public int deleteStationById(String id);

	@Select("select * from transaction where createdBy = select userid from user where email=#{email} and type=#{type}")
	public List<Transaction> findByEmailNType(String email, String type);

}
