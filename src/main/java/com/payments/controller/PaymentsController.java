package com.payments.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payments.model.Transaction;
import com.payments.repo.PaymentsRepository;
import com.payments.repo.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class PaymentsController {

	@Autowired
	private PaymentsRepository paymentsRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = "/")
	@ApiOperation(value = "This API is to get Swagger UI")
	public void swaggerHome(HttpServletResponse response) throws IOException {
		response.sendRedirect("swagger-ui.html");
	}

	@GetMapping(value = "/all", produces = "application/json")
	@ApiOperation(value = "This API returns all the transactions available in the system")
	public List<Transaction> getAll() {
		List<Transaction> list = paymentsRepository.findAll();
		list.forEach(x -> {
			x.setCreatedBy(userRepository.findById(x.getCreatedBy()));
		});
		return list;
	}

	@PostMapping(value = "/transaction", produces = "application/json")
	@ApiOperation(value = "adds transaction")
	public ResponseEntity<String> insertTransaction(
			@ApiParam(value = "Station attributes") @RequestBody Transaction station) {
		int result = 0;
		try {
			result = paymentsRepository.insert(station);
		} catch (Exception e) {
			log.error("failed to insert station ", e);
		}
		if (result > 0) {
			return new ResponseEntity<String>("Transaction inserted successfully", HttpStatus.OK);
		} else {

			return new ResponseEntity<String>("Failed to insert Transaction", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/transaction", produces = "application/json")
	@ApiOperation(value = "updates transaction")
	public ResponseEntity<String> updateStation(
			@ApiParam(value = "transaction attributes") @RequestBody Transaction station) {
		int result = paymentsRepository.update(station);
		if (result > 0) {
			return new ResponseEntity<String>("Transaction updated successfully", HttpStatus.OK);
		} else {

			return new ResponseEntity<String>("Failed to update Transaction", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/transaction", produces = "application/json")
	@ApiOperation(value = "This API can be used to delete a station taking station id as input")
	public ResponseEntity<String> deleteStation(
			@ApiParam(value = "transaction Id") @RequestParam("transactionId") String transactionId) {

		int result = paymentsRepository.deleteStationById(transactionId);
		if (result > 0) {
			return new ResponseEntity<String>("Transaction deleted successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Transaction deletion failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/transaction", produces = "application/json")
	@ApiOperation(value = "This API is to get transaction by useremail")
	public List<Transaction> findByEmail(
			@ApiParam(value = "created by email") @RequestParam("createdby") String email) {
		List<Transaction> list = paymentsRepository.findByEmail(email);
		list.forEach(x -> {
			x.setCreatedBy(userRepository.findById(x.getCreatedBy()));
		});
		return list;
	}

	@GetMapping(value = "/transaction-type", produces = "application/json")
	@ApiOperation(value = "This API is to get transaction by useremail")
	public List<Transaction> findByEmailNType(
			@ApiParam(value = "created by email") @RequestParam("createdby") String email,
			@ApiParam(value = "type of transaction") @RequestParam("type") String type) {
		List<Transaction> list = paymentsRepository.findByEmailNType(email, type);
		list.forEach(x -> {
			x.setCreatedBy(userRepository.findById(x.getCreatedBy()));
		});
		return list;
	}

	@GetMapping(value = "/transaction-date", produces = "application/json")
	@ApiOperation(value = "This API is to get transaction by useremail")
	public List<Transaction> findByDate(@ApiParam(value = "date ") @RequestParam("date") String date) {
		List<Transaction> list = paymentsRepository.findAll();
		List<Transaction> newList = new ArrayList<>();

		for (Transaction tx : list) {
			SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.ENGLISH);
			Date parsedDate=null;
			try {
				parsedDate = sdf.parse(tx.getCreatedDate().toString());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (parsedDate.equals(sdf1.parse(date))) {
					newList.add(tx);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return newList;

	}

}
