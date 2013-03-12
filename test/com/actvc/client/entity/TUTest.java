package com.actvc.client.entity;

import junit.framework.Assert;

import org.junit.Test;

import com.actvc.client.entities.TU;

/**
 * Tests for TU.
 * 
 * @author stephen
 * 
 */
public class TUTest {

	private static final String TO_EXPORT_FORM_STR = "email	name	true	false	true	false	true	false	false";

	/**
	 * <p>
	 * Tests the output of toExportForm get the order correct.
	 * </p>
	 * <p>
	 * Poor test though. Just tests if correct number of fields are output
	 * </p>
	 */
	@Test
	public void testToExportForm() {
		TU user = new TU();
		user.setEmail("email");
		user.setName("name");
		user.setRoleSuperUser(true);
		user.setRoleMembershipTreasurer(false);
		user.setRoleResults(true);
		user.setRoleHandicapper(false);
		user.setRoleRaceDirector(true);
		user.setRoleCommittee(false);
		Assert.assertEquals(TO_EXPORT_FORM_STR, user.toExportForm());
	}

}
