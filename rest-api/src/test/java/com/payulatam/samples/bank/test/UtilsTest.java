package com.payulatam.samples.bank.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.payulatam.samples.bank.service.Utils;

public class UtilsTest {


	private Utils utils;

	@BeforeTest
	public void setup() {
		utils = new Utils();
	}
	@Test
	public void validateInvalidPhoneNumber() {
		boolean result = utils.validateTelephone("adsf");
		Assert.assertFalse(result);
	}

	@Test
	public void validatePhoneNumberWithSpaces() {
		Assert.assertFalse(utils.validateTelephone("123 456 4"));
	}

	@Test
	public void validateEmptyPhoneNumber() {
		Assert.assertFalse(utils.validateTelephone(""));
	}

	@Test
	public void createValidateEmptyName() {
		Assert.assertFalse(utils.validateName(""));
	}

	@Test
	public void createValidateNameWithOutSpaces() {
		Assert.assertTrue(utils.validateName("SimonSantiago"));
	}

	@Test
	public void createValidateNameWithSpaces() {
		Assert.assertTrue(utils.validateName("Simon Santiago Soriano"));
	}
}
