package com.actvc.common;

import junit.framework.Assert;

import org.junit.Test;

import com.actvc.client.common.MyUtils;

/**
 * Contains tests for Util methods.
 * 
 * @author stephen
 * 
 */
public class MyUtilsTest {

	/**
	 * Tests getDateStr. Check that passing a null value returns an empty
	 * string.
	 */
	@Test
	public void testGetDateStr() {
		Assert.assertEquals("", MyUtils.getDateStr(null));
	}

	/**
	 * Tests returning of the correct entity class when given the entity "id".
	 */
	@Test
	public void testGetEntityClass() {
		// TEntity
	}
}
