package com.payulatam.samples.bank.test.service.integration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.service.IAccountService;
import com.payulatam.samples.bank.service.IClientService;
import com.payulatam.samples.bank.test.utils.Fixtures;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/integrationTestContext.xml")
public class AccountServiceIntegrationTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private IAccountService accountService;

	@Autowired
	private GigaSpace gigaSpace;

	@BeforeMethod
	public void beforeMethod() {
		gigaSpace.clear(null);
	}

	@AfterClass
	public void afterClass() {
		gigaSpace.clear(null);
	}

	@Test
	public void createAccountCreatesAccountWithItsOwnerId() {
		Client owner = Fixtures.standardClient();
		gigaSpace.write(owner);
		Account result = accountService.create(owner.getId());
		Assert.assertEquals(result.getClientId(), owner.getId(), "Client id mismatch");
	}
	
	@Test
	public void createAccountAssignsId() {
		Client owner = Fixtures.standardClient();
		gigaSpace.write(owner);
		Account result = accountService.create(owner.getId());
		Assert.assertNotNull(result.getId(), "AccountId should not be null");
	}

	@Test
	public void createAccountCreatesAccountWithBalanceEqual0() {
		Client owner = Fixtures.standardClient();
		gigaSpace.write(owner);

		Account result = accountService.create(owner.getId());
		Assert.assertEquals(result.getBalance(), BigDecimal.ZERO,
				"Account initial balance should be 0");
	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void createAccountWithNonExistentClientThrowsException() {
		accountService.create("nonExistentId");
	}
	@Test
	public void createAccountWithNonExistentClientIsNotCreated() {
		Account account = Account.builder().clientId("nonExistentId").build();

		try {
			accountService.create(account.getClientId());
		} catch (NoSuchElementException e) {

		}

		Account result = gigaSpace.read(account);
		Assert.assertNull(result);
	}

	@Test
	public void deleteAccountReturnsDeletedAccount() {
		Account expected = Account.builder().id("accountId").build();
		gigaSpace.write(expected);

		Account result = accountService.delete(expected.getId());

		Assert.assertEquals(result.getId(), expected.getId(), "Account id mismatch");
	}

	@Test
	public void deleteAccountDeletesAccountInGigaSpace() {
		Account expected = Account.builder().id("accountId").build();
		gigaSpace.write(expected);

		accountService.delete(expected.getId());
		Account result = gigaSpace.read(expected);
		Assert.assertNull(result, "Account should be null since it was deleted");
	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void deleteNonExistentAccountThrowsException() {
		Account account = Account.builder().id("nonExistentId").build();
		accountService.delete(account.getId());
	}
	
	@Test
	public void deleteAccountDeletesItsTransactions() {
		Client c = Fixtures.standardClient();
		gigaSpace.write(c);
		c = gigaSpace.read(c);
		
		Account a = Fixtures.standardAccount(c.getId());
		gigaSpace.write(a);
		a = gigaSpace.read(a);
		
		List<Transaction> ts = Fixtures.validTransactions(a.getId());
		
		gigaSpace.writeMultiple(ts.toArray());
		
		accountService.delete(a.getId());
		
		Transaction template = Transaction.builder().accountId(a.getId()).build();
		Assert.assertEquals(gigaSpace.readMultiple(template).length,0);
		
	}

	@Test
	public void searchAcountByIdReturnsRightAccount() {
		Account expected = Account.builder().id("id").build();
		gigaSpace.write(expected);
		Account result = accountService.searchById(expected.getId());
		Assert.assertEquals(result, expected, "Account mismatch");
	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void searchAcountByNonExistentIdThrowsException() {
		accountService.searchById("nonExistentId");
	}

	@Test
	public void searchAcountsByClientIdReturnsRightAccounts() {
		Client owner = Fixtures.standardClient();
		gigaSpace.write(owner);
		owner = gigaSpace.read(owner);

		Client otherOwner = Fixtures.standardClient();
		gigaSpace.write(otherOwner);
		otherOwner = gigaSpace.read(otherOwner);

		Account expected = Fixtures.standardAccount(owner.getId());
		Account notExpected = Fixtures.standardAccount(otherOwner.getId());

		gigaSpace.writeMultiple(new Account[] { expected, notExpected, expected, expected,
				notExpected });

		List<Account> result = accountService.searchAccountsByClientId(owner.getId());
		for (Account a : result) {
			if (!a.getClientId().equals(expected.getClientId())) {
				Assert.fail("Expected clientId: "+owner.getId()+", actual clientId: "+a.getClientId());
			}
		}
		Assert.assertEquals(result.size(), 3);
	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void searchAcountsByNonExistentClientIdThrowsException() {
		accountService.searchAccountsByClientId("nonExistentId");
	}

}
