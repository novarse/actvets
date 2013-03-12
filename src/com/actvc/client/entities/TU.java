package com.actvc.client.entities;

import java.io.Serializable;

import com.google.code.twig.annotation.Id;

public class TU implements Serializable, TEntity {
	// twig user

	@Id
	private String email;

	private String name;

	private boolean roleSuperUser = false;

	private boolean roleMembershipTreasurer = false;

	private boolean roleResults = false;

	private boolean roleHandicapper = false;

	private boolean roleRaceDirector = false;

	private boolean roleCommittee = false;

	private boolean roleSystem = false;

	public TU() {
	}

	public TU(String email, String name) {
		this.setEmail(email);
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setRoleSuperUser(boolean isRoleSuperUser) {
		this.roleSuperUser = isRoleSuperUser;
	}

	public boolean isRoleSuperUser() {
		return roleSuperUser;
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

	public boolean isRoleSystem() {
		return roleSystem;
	}

	public void setRoleSystem(boolean roleSystem) {
		this.roleSystem = roleSystem;
	}

	@Override
	public String toExportForm() {
		return getEmail() + TAB + getName() + TAB + isRoleSuperUser() + TAB
				+ isRoleMembershipTreasurer() + TAB + isRoleResults() + TAB
				+ isRoleHandicapper() + TAB + isRoleRaceDirector() + TAB
				+ roleCommittee + TAB + isRoleSystem();
	}

}
