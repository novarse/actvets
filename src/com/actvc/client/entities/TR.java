package com.actvc.client.entities;

import java.io.Serializable;
import java.util.Date;

import com.actvc.client.Gender;
import com.actvc.client.util.Utils;
import com.google.code.twig.annotation.Id;

public class TR implements Serializable, Person, TEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String lastName;

	private String firstName;

	private boolean isActive;

	private Gender gender;

	private String grade;

	private int subGrade;

	private String criteriumGrade;

	private int number;

	private String street;

	private String suburb;

	private String state;

	private String postcode;

	private String phoneHome;

	private String phoneWorkOrMobile;

	private Date dob;

	private String email;

	private boolean firstAid;

	private String emergencyContact;

	private String phoneEmergencyContact;

	private String phoneEmergencyContact2;

	private boolean isDirector;

	private String uppercaseLastName;

	private String uppercaseFirstName;

	private String AVCCNumber;

	public TR() {

	}

	@Override
	public String toExportForm() {
		return getId() + TAB + getFirstName() + TAB + isActive() + TAB
				+ getStreet() + TAB + getSuburb() + TAB + getState() + TAB
				+ getPostcode() + TAB + getPhoneHome() + TAB
				+ getPhoneWorkOrMobile() + TAB
				+ Utils.formatDateForExport(getDob()) + TAB + getGender() + TAB
				+ getEmail() + TAB + isFirstAid() + TAB + getEmergencyContact()
				+ TAB + getPhoneEmergencyContact() + TAB
				+ getPhoneEmergencyContact2() + TAB + getNumber() + TAB + grade
				+ TAB + getSubGrade() + TAB + getCriteriumGrade() + TAB
				+ isDirector() + TAB + getAVCCNumber() + TAB + getLastName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setFirstName(java.lang.String)
	 */
	@Override
	public void setFirstName(String firstName) {
		this.firstName = capitalizeFirst(firstName.trim(), true);
		this.uppercaseFirstName = firstName.toUpperCase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getFirstName()
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setLastName(java.lang.String)
	 */
	@Override
	public void setLastName(String lastName) {
		this.lastName = capitalizeFirst(lastName.trim(), false);
		this.uppercaseLastName = lastName.toUpperCase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getLastName()
	 */
	@Override
	public String getLastName() {
		return lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setGrade(java.lang.String)
	 */
	@Override
	public void setGrade(String grade) {
		this.grade = grade.trim().toUpperCase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getGrade()
	 */
	@Override
	public String getGrade() {
		return grade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setNumber(int)
	 */
	@Override
	public void setNumber(int raceNumber) {
		this.number = raceNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getNumber()
	 */
	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public void setAVCCNumber(String aVCCNumber) {
		AVCCNumber = aVCCNumber.trim().toUpperCase();
	}

	@Override
	public String getAVCCNumber() {
		return AVCCNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getPhoneHome()
	 */
	@Override
	public String getPhoneHome() {
		return phoneHome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setPhoneHome(java.lang.String)
	 */
	@Override
	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getStreet()
	 */
	@Override
	public String getStreet() {
		return street;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setStreet(java.lang.String)
	 */
	@Override
	public void setStreet(String street) {
		this.street = street;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getSuburb()
	 */
	@Override
	public String getSuburb() {
		return suburb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setSuburb(java.lang.String)
	 */
	@Override
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getState()
	 */
	@Override
	public String getState() {
		return state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setState(java.lang.String)
	 */
	@Override
	public void setState(String state) {
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getPostcode()
	 */
	@Override
	public String getPostcode() {
		return postcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setPostcode(java.lang.String)
	 */
	@Override
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getPhoneWorkOrMobile()
	 */
	@Override
	public String getPhoneWorkOrMobile() {
		return phoneWorkOrMobile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.actvc.client.entities.Person#setPhoneWorkOrMobile(java.lang.String)
	 */
	@Override
	public void setPhoneWorkOrMobile(String phoneWorkOrMobile) {
		this.phoneWorkOrMobile = phoneWorkOrMobile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getDob()
	 */
	@Override
	public Date getDob() {
		return dob;
	}

	@Override
	public void setDob(Object dob) {
		this.dob = (Date) dob;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#isFirstAid()
	 */
	@Override
	public boolean isFirstAid() {
		return firstAid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#setFirstAid(boolean)
	 */
	@Override
	public void setFirstAid(boolean firstAid) {
		this.firstAid = firstAid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getEmergencyContact()
	 */
	@Override
	public String getEmergencyContact() {
		return emergencyContact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.actvc.client.entities.Person#setEmergencyContact(java.lang.String)
	 */
	@Override
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actvc.client.entities.Person#getPhoneEmergencyContact()
	 */
	@Override
	public String getPhoneEmergencyContact() {
		return phoneEmergencyContact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.actvc.client.entities.Person#setPhoneEmergencyContact(java.lang.String
	 * )
	 */
	@Override
	public void setPhoneEmergencyContact(String phoneEmergencyContact) {
		this.phoneEmergencyContact = phoneEmergencyContact;
	}

	private String capitalizeFirst(String text, boolean onlyFirst) {
		if (onlyFirst) {
			return (text.length() > 0 ? Character.toUpperCase(text.charAt(0))
					+ text.substring(1).toLowerCase() : text);
		} else {
			return (text.length() > 0 ? Character.toUpperCase(text.charAt(0))
					+ text.substring(1) : text);
		}
	}

	@Override
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public String getPhoneEmergencyContact2() {
		return phoneEmergencyContact2;
	}

	@Override
	public void setPhoneEmergencyContact2(String phoneEmergencyContact2) {
		this.phoneEmergencyContact2 = phoneEmergencyContact2;
	}

	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	public void setDirector(boolean isDirector) {
		this.isDirector = isDirector;
	}

	@Override
	public boolean isDirector() {
		return isDirector;
	}

	@Override
	public void setSubGrade(int subGrade) {
		this.subGrade = subGrade;
	}

	@Override
	public int getSubGrade() {
		return subGrade;
	}

	@Override
	public String getCriteriumGrade() {
		return criteriumGrade;
	}

	@Override
	public void setCriteriumGrade(String criteriumGrade) {
		this.criteriumGrade = criteriumGrade.trim().toUpperCase();
	}

	public String getUppercaseLastName() {
		return uppercaseLastName;
	}

	public String getUppercaseFirstName() {
		return uppercaseFirstName;
	}

	@Override
	public String toString() {
		return "TR [id=" + id + ", lastName=" + lastName + ", firstName="
				+ firstName + ", isActive=" + isActive + ", gender=" + gender
				+ ", grade=" + grade + ", subGrade=" + subGrade
				+ ", criteriumGrade=" + criteriumGrade + ", number=" + number
				+ ", street=" + street + ", suburb=" + suburb + ", state="
				+ state + ", postcode=" + postcode + ", phoneHome=" + phoneHome
				+ ", phoneWorkOrMobile=" + phoneWorkOrMobile + ", dob=" + dob
				+ ", email=" + email + ", firstAid=" + firstAid
				+ ", emergencyContact=" + emergencyContact
				+ ", phoneEmergencyContact=" + phoneEmergencyContact
				+ ", phoneEmergencyContact2=" + phoneEmergencyContact2
				+ ", isDirector=" + isDirector + ", uppercaseLastName="
				+ uppercaseLastName + ", uppercaseFirstName="
				+ uppercaseFirstName + ", AVCCNumber=" + AVCCNumber + "]";
	}

}
