package com.luoy.library.common.vo;

import java.util.Date;

import javax.persistence.Column;

import com.luoy.library.pojo.BookSet;
import com.luoy.library.pojo.Sort;

/**
 * 图书vo
 * @author ying luo
 * @createDate 2018年4月9日
 */
public class BookSetVo {
	
	/**
	 * BookSet
	 */
	private String id;
	private String bookName;
	private String isbn;
	private String author;
	private String translator;
	private String press;
	private double price;
	private String coverUrl;
	private Integer sum;
	private Integer leftCount;
	private Integer borrowedCount;
	private Date publishTime;
	private Integer curBorrowCount;
	private String summary;
	private String status;
	private String sortId;
	
	/**
	 * Sort
	 */
	private String sortName;
	
	public BookSetVo(){}
	
	public BookSetVo(BookSet bookSet, Sort sort) {
		if (bookSet != null) {
			this.id = bookSet.getId();
			this.bookName = bookSet.getBookName();
			this.isbn = bookSet.getIsbn();
			this.author = bookSet.getAuthor();
			this.translator = bookSet.getTranslator();
			this.press = bookSet.getPress();
			this.price = bookSet.getPrice();
			this.coverUrl = bookSet.getCoverUrl();
			this.sum = bookSet.getSum();
			this.leftCount = bookSet.getLeftCount();
			this.borrowedCount = bookSet.getBorrowedCount();
			this.publishTime = bookSet.getPublishTime();
			this.curBorrowCount = bookSet.getCurBorrowCount();
			this.summary = bookSet.getSummary();
			this.status = bookSet.getStatus();
			this.sortId = bookSet.getSortId();
		}
		
		if (sort != null) {
			this.sortName = sort.getSortName();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTranslator() {
		return translator;
	}

	public void setTranslator(String translator) {
		this.translator = translator;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public double getPrice() {
		return price;
	}

	public Integer getBorrowedCount() {
		return borrowedCount;
	}

	public void setBorrowedCount(Integer borrowedCount) {
		this.borrowedCount = borrowedCount;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getCurBorrowCount() {
		return curBorrowCount;
	}

	public void setCurBorrowCount(Integer curBorrowCount) {
		this.curBorrowCount = curBorrowCount;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	

}
