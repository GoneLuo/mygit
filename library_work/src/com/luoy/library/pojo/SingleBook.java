package com.luoy.library.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 单本图书详情表
 * @author ying luo
 *
 */
@Entity
@Table(name = "single_book")
public class SingleBook {
	
	@Id
	@Column(name = "id")
	@GenericGenerator(name = "uuidGen", strategy = "uuid")
	@GeneratedValue(generator = "uuidGen")
	private String id;

	@Column(name = "barcode")
	private String barcode;
	
	@Column(name = "is_borrowed")
	private String isBorrowed;

	@Column(name = "is_collected")
	private String isCollected;
	
	@Column(name = "is_return")
	private String isReturn; //是否逾期未还【暂时不使用】
	
	@Column(name = "book_set_id")
	private String bookSetId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getIsBorrowed() {
		return isBorrowed;
	}

	public void setIsBorrowed(String isBorrowed) {
		this.isBorrowed = isBorrowed;
	}

	public String getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(String isCollected) {
		this.isCollected = isCollected;
	}

	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	public String getBookSetId() {
		return bookSetId;
	}

	public void setBookSetId(String bookSetId) {
		this.bookSetId = bookSetId;
	}
}
