package com.actvc.client.entities;

import java.io.Serializable;
import java.util.Date;

import com.google.code.twig.annotation.Id;

public class TGCR implements Serializable {
	// grade change request

	@Id
	private Long id;

	private Long riderId;

	private int number;

	private String lastName;

	private String firstName;

	private Date dob;

	private String currentGrade;

	private int currentSubgrade;

	private String currentCriteriumGrade;

	private String newGrade;

	private Date submitDate;

	private String handicapperEmail;

	private int newSubgrade;

	private String newCriteriumGrade;

	public TGCR() {

	}

	public TGCR(Long iderId, int number, String lastName, String firstName,
			Date dob, String currentGrade, int currentSubgrade,
			String currentCriteriumGrade, String newGrade, int newSubgrade,
			String newCriteriumGrade, Date submitDate) {
		super();
		setRiderId(iderId);
		setNumber(number);
		setLastName(lastName);
		setFirstName(firstName);
		setDob(dob);
		setCurrentGrade(currentGrade);
		setCurrentSubgrade(currentSubgrade);
		setCurrentCriteriumGrade(currentCriteriumGrade);
		setNewGrade(newGrade);
		setNewSubgrade(newSubgrade);
		setNewCriteriumGrade(newCriteriumGrade);
		setSubmitDate(submitDate);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getHandicapperEmail() {
		return handicapperEmail;
	}

	public void setHandicapperEmail(String handicapperEmail) {
		this.handicapperEmail = handicapperEmail;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public String getCurrentGrade() {
		return currentGrade;
	}

	public void setCurrentGrade(String currentGrade) {
		this.currentGrade = currentGrade.toUpperCase();
	}

	public String getNewGrade() {
		return newGrade;
	}

	public void setNewGrade(String newGrade) {
		this.newGrade = newGrade.toUpperCase();
	}

	public Long getId() {
		return id;
	}

	public void setRiderId(Long riderId) {
		this.riderId = riderId;
	}

	public Long getRiderId() {
		return riderId;
	}

	public void setNewSubgrade(int newSubgrade) {
		this.newSubgrade = newSubgrade;
	}

	public int getNewSubgrade() {
		return newSubgrade;
	}

	public void setNewCriteriumGrade(String newCriteriumGrade) {
		this.newCriteriumGrade = newCriteriumGrade;
	}

	public String getNewCriteriumGrade() {
		return newCriteriumGrade;
	}

	public int getCurrentSubgrade() {
		return currentSubgrade;
	}

	public void setCurrentSubgrade(int currentSubgrade) {
		this.currentSubgrade = currentSubgrade;
	}

	public String getCurrentCriteriumGrade() {
		return currentCriteriumGrade;
	}

	public void setCurrentCriteriumGrade(String currentCriteriumGrade) {
		this.currentCriteriumGrade = currentCriteriumGrade == null ? ""
				: currentCriteriumGrade.toUpperCase();
	}
}
