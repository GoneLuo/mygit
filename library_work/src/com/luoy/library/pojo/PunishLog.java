package com.luoy.library.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 处罚记录表
 * @author ying luo
 *
 */
@Entity
@Table(name = "punish_log")
public class PunishLog {

	@Id
	@Column(name = "id")
	@GenericGenerator(name = "uuidGen", strategy = "uuid")
	@GeneratedValue(generator = "uuidGen")
	private String id;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "over_day")
	private Integer overDay;

	@Column(name = "sum_punish_money")
	private double sumPunishMoney;
	
	@Column(name = "borrow_log_id")
	private String borrowLogLd;
	
	@Column(name = "debt")
	private double debt;
	
	@Column(name = "punish_type")
	private String punishType;
	
	@Column(name = "has_return_debt")
	private String hasReturnDebt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHasReturnDebt() {
		return hasReturnDebt;
	}

	public void setHasReturnDebt(String hasReturnDebt) {
		this.hasReturnDebt = hasReturnDebt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getOverDay() {
		return overDay;
	}

	public void setOverDay(Integer overDay) {
		this.overDay = overDay;
	}

	public double getSumPunishMoney() {
		return sumPunishMoney;
	}

	public void setSumPunishMoney(double sumPunishMoney) {
		this.sumPunishMoney = sumPunishMoney;
	}


	public String getBorrowLogLd() {
		return borrowLogLd;
	}

	public void setBorrowLogLd(String borrowLogLd) {
		this.borrowLogLd = borrowLogLd;
	}

	public double getDebt() {
		return debt;
	}

	public void setDebt(double debt) {
		this.debt = debt;
	}

	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}
}
