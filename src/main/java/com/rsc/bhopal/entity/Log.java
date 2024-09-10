package com.rsc.bhopal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "RSC_TS_ACTIVITY_LOG")
public class Log {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ACTION_AT")
	private Date actionAt;

	@Column(name = "ACTION_BY")
	private Long actionBy;

	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "PAYLOAD")
	private String payload;

	@Column(name = "STATUS")
	private Boolean status;
}
