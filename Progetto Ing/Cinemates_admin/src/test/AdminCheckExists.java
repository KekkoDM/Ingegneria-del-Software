package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.Controller;

class AdminCheckExists {

	@Test
	void checkExistsAdminOk() {
		Controller ctr = new Controller();
		assertTrue(ctr.adminDAO.checkExists("ingsw", "admin123", ctr.admin));
	}

	@Test
	void checkExistsAdminUsernameOkPasswordNo() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("ingsw", "password", ctr.admin));
	}
	
	@Test
	void checkExistsAdminUsernameNoPasswordOk() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("ingegneria", "admin123", ctr.admin));
	}
	
	@Test
	void checkExistsAdminUsernameNoPasswordNo() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("no", "no", ctr.admin));
	}
	
	@Test
	void checkExistsAdminUsernameVuotoPasswordVuoto() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("", "", ctr.admin));
	}
	
	@Test
	void checkExistsAdminUsernameOkPasswordVuoto() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("ingsw", "", ctr.admin));
	}
	
	@Test
	void checkExistsAdminUsernameVuotoPasswordOk() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("", "admin123", ctr.admin));
	}
	
	@Test
	void checkExistsAdminUsernameNoPasswordVuoto() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("no", "", ctr.admin));
	}
	@Test
	void checkExistsAdminUsernameVuotoPasswordNo() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("", "no", ctr.admin));
	}
	
	@Test
	void checkExistsAdminUsernameOkPasswordOkAdminNull() {
		Controller ctr = new Controller();
		assertTrue(ctr.adminDAO.checkExists("ingsw", "admin123", null));
	}
	
	@Test
	void checkExistsAdminUsernameVuotoPasswordVuotoAdminNull() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("", "", null));
	}
	
	@Test
	void checkExistsAdminUsernameOkPasswordVuotoAdminNull() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("ingsw", "", null));
	}
	
	@Test
	void checkExistsAdminUsernameVuotoPasswordOkAdminNull() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("", "admin123", null));
	}
	
	@Test
	void checkExistsAdminUsernameOkPasswordNoAdminNull() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("ingsw", "no", null));
	}
	
	@Test
	void checkExistsAdminUsernameNoPasswordOkAdminNull() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("no", "admin123", null));
	}
	
	@Test
	void checkExistsAdminUsernameNoPasswordVuotoAdminNull() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("no", "", null));
	}
	
	
	@Test
	void checkExistsAdminUsernameVuotoPasswordNoAdminNull() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("", "no", null));
	}
	
	@Test
	void checkExistsAdminUsernameNoPasswordNoAdminNull() {
		Controller ctr = new Controller();
		assertFalse(ctr.adminDAO.checkExists("no", "no", null));
	}
	
}
