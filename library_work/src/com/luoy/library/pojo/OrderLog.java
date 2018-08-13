package com.luoy.library.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 预约记录表
 * @author ying luo
 *
 */
@Entity
@Table(name = "order_log")
public class OrderLog {

	@Id
	@Column(name = "id")
	@GenericGenerator(name = "uuidGen", strategy = "uuid")
	@GeneratedValue(generator = "uuidGen")
	private String id;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "book_set_id")
	private String bookSetId;

	@Column(name = "order_time")
	private Date orderTime;
	
	@Column(name = "is_success")
	private String isSuccess;
}
