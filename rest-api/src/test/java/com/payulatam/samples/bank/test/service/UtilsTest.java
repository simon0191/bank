package com.payulatam.samples.bank.test.service;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.payulatam.samples.bank.utils.Utils;

public class UtilsTest {

	@Test
	public void validateInvalidPhoneNumber() {
		boolean result = Utils.validateTelephone("adsf");
		Assert.assertFalse(result);
	}

	@Test
	public void validatePhoneNumberWithSpaces() {
		Assert.assertFalse(Utils.validateTelephone("123 456 4"));
	}

	@Test
	public void validateEmptyPhoneNumber() {
		Assert.assertFalse(Utils.validateTelephone(""));
	}

	@Test
	public void createValidateEmptyName() {
		Assert.assertFalse(Utils.validateName(""));
	}

	@Test
	public void createValidateNameWithOutSpaces() {
		Assert.assertTrue(Utils.validateName("SimonSantiago"));
	}

	@Test
	public void createValidateNameWithSpaces() {
		Assert.assertTrue(Utils.validateName("Simon Santiago Soriano"));
	}
}
