package com.scramcode.djinn.test;

import org.junit.Test;

import com.scramcode.djinn.db.mgmt.ConnectionManager;

public class DatabaseTest {

	@Test
	public void testLoadDatabase() throws Exception {
		ConnectionManager.getInstance().resetDatabase();
	}
}
