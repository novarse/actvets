package com.actvc.client.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginInfo implements IsSerializable {

	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String emailAddress;
	private String nickname;
	private String name;
	private boolean roleSuperUser;
	private boolean roleMembershipTreasurer;
	private boolean roleResults;
	private boolean roleHandicapper;
	private boolean roleRaceDirector;
	private boolean roleCommittee;
	private boolean roleSystem;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public LoginInfo() {

	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isRoleSuperUser() {
		return roleSuperUser;
	}

	public void setRoleSuperUser(boolean roleSuperUser) {
		this.roleSuperUser = roleSuperUser;
	}

	public boolean isRoleMembershipTreasurer() {
		return roleMembershipTreasurer;
	}

	public void setRoleMembershipTreasurer(boolean roleMembershipTreasurer) {
		this.roleMembershipTreasurer = roleMembershipTreasurer;
	}

	public boolean isRoleResults() {
		return roleResults;
	}

	public void setRoleResults(boolean roleResults) {
		this.roleResults = roleResults;
	}

	public boolean isRoleHandicapper() {
		return roleHandicapper;
	}

	public void setRoleHandicapper(boolean roleHandicapper) {
		this.roleHandicapper = roleHandicapper;
	}

	public boolean isRoleRaceDirector() {
		return roleRaceDirector;
	}

	public void setRoleRaceDirector(boolean roleRaceDirector) {
		this.roleRaceDirector = roleRaceDirector;
	}

	public boolean isRoleCommittee() {
		return roleCommittee;
	}

	public void setRoleCommittee(boolean roleCommittee) {
		this.roleCommittee = roleCommittee;
	}

	public void setRoleSystem(boolean roleSystem) {
		this.roleSystem = roleSystem;
	}

	public boolean isRoleSystem() {
		return roleSystem;
	}
}
