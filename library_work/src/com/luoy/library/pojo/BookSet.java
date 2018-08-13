package com.luoy.library.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 图书表（一种图书，并非单本）
 * @author ying luo
 *
 */
@Entity
@Table(name = "book_set")
public class BookSet {

	@Id
	@Column(name = "id")
	@GenericGenerator(name = "uuidGen", strategy = "uuid")
	@GeneratedValue(generator = "uuidGen")
	private String id;

	@Column(name = "book_name")
	private String bookName;
	
	@Column(name = "isbn")
	private String isbn;

	@Column(name = "author")
	private String author;
	
	@Column(name = "translator")
	private String translator;
	
	@Column(name = "press")
	private String press;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "cover_url")
	private String coverUrl;
	
	@Column(name = "sum")
	private Integer sum;
	
	@Column(name = "left_count")
	private Integer leftCount;
	
	@Column(name = "publish_time")
	private Date publishTime;
	
	@Column(name = "borrowed_count")
	private Integer borrowedCount;
	
	@Column(name = "cur_borrow_count")
	private Integer curBorrowCount;
	
	@Column(name = "summary")
	private String summary;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "sort_id")
	private String sortId;

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
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

	public Integer getBorrowedCount() {
		return borrowedCount;
	}

	public void setBorrowedCount(Integer borrowedCount) {
		this.borrowedCount = borrowedCount;
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

}
