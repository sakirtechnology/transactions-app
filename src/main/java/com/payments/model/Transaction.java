package com.payments.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Transaction {
	private String transactionId;
	private String type;
	private String createdBy;
	private Date createdDate;
}
