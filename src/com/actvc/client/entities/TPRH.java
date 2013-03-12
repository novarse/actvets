package com.actvc.client.entities;

import java.io.Serializable;
import java.util.Date;

import com.actvc.client.Gender;
import com.google.code.twig.annotation.Id;

public class TPRH implements Serializable, Person, TEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Long eventId;

	private Date date;

	private String raceGrade;

	private int place;

	private int overTheLine;

	private String time;

	private int points;

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

	private String dobStr;

	private String email;

	private boolean firstAid;

	private String emergencyContact;

	private String phoneEmergencyContact;

	private String phoneEmergencyContact2;

	private boolean isDirector;

	private String uppercaseLastName;

	private String uppercaseFirstName;

	private String AVCCNumber;

	public TPRH() {

	}

	public Long getpKeyID() {
		return id;
	}

	public void setpKeyID(Long pKeyID) {
		this.id = pKeyID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRaceGrade() {
		return raceGrade.trim().toUpperCase();
	}

	public void setRaceGrade(String raceGrade) {
		this.raceGrade = raceGrade;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public int getOverTheLine() {
		return overTheLine;
	}

	public void setOverTheLine(int overTheLine) {
		this.overTheLine = overTheLine;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = capitalizeFirst(lastName);
		this.uppercaseLastName = lastName.toUpperCase();
	}

	public void setFirstName(String firstName) {
		this.firstName = capitalizeFirst(firstName);
		this.uppercaseFirstName = firstName.toUpperCase();
	}

	@Override
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade.trim().toUpperCase();
	}

	@Override
	public int getSubGrade() {
		return this.subGrade;
	}

	public void setSubGrade(int subGrade) {
		this.subGrade = subGrade;
	}

	@Override
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String getAVCCNumber() {
		return AVCCNumber;
	}

	public void setAVCCNumber(String aVCCNumber) {
		AVCCNumber = aVCCNumber.trim().toUpperCase();
	}

	@Override
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	@Override
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Override
	public String getPhoneHome() {
		return phoneHome;
	}

	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	@Override
	public String getPhoneWorkOrMobile() {
		return phoneWorkOrMobile;
	}

	public void setPhoneWorkOrMobile(String phoneWorkOrMobile) {
		this.phoneWorkOrMobile = phoneWorkOrMobile;
	}

	@Override
	public void setDob(Object dob) {
		this.dobStr = (String) dob;
	}

	@Override
	public String getDob() {
		return this.dobStr;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean isFirstAid() {
		return firstAid;
	}

	public void setFirstAid(boolean firstAid) {
		this.firstAid = firstAid;
	}

	@Override
	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	@Override
	public String getPhoneEmergencyContact() {
		return phoneEmergencyContact;
	}

	public void setPhoneEmergencyContact(String phoneEmergencyContact) {
		this.phoneEmergencyContact = phoneEmergencyContact;
	}

	public void setPhoneEmergencyContact2(String phoneEmergencyContact2) {
		this.phoneEmergencyContact2 = phoneEmergencyContact2;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@Override
	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public String getPhoneEmergencyContact2() {
		return this.phoneEmergencyContact2;
	}

	@Override
	public String toExportForm() {
		return null;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public boolean isDirector() {
		return this.isDirector;
	}

	@Override
	public String getCriteriumGrade() {
		return criteriumGrade;
	}

	public void setCriteriumGrade(String criteriumGrade) {
		this.criteriumGrade = criteriumGrade.trim().toUpperCase();
	}

	public String getUppercaseLastName() {
		return uppercaseLastName;
	}

	public String getUppercaseFirstName() {
		return uppercaseFirstName;
	}

	private String capitalizeFirst(String text) {
		return (text.length() > 0 ? Character.toUpperCase(text.charAt(0))
				+ text.substring(1).toLowerCase() : text);
	}

}
