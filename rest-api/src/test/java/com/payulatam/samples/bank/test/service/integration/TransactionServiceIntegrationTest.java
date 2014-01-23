package com.payulatam.samples.bank.test.service.integration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.gigaspaces.client.WriteModifiers;
import com.gigaspaces.query.ISpaceQuery;
import com.j_spaces.core.client.SQLQuery;
import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.TransactionType;
import com.payulatam.samples.bank.service.ITransactionService;
import com.payulatam.samples.bank.test.utils.Fixtures;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/integrationTestContext.xml")
public class TransactionServiceIntegrationTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private ITransactionService transactionService;

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

//	@Test
//	public void createDebitTransactionModifiesAccount() {
//		Account a = Fixtures.standardAccount("clientId",BigDecimal.TEN);
//		Transaction t = Fixtures.debitTransaction(a.getId(),BigDecimal.ONE);
//		
//		Transaction result = transactionService.create("accountId", TransactionType.DEBIT, value);
//
//		Assert.assertEquals(account.getBalance(), expectedBalance);
//	}
//
//	@Test
//	public void createCreditTransaction() {
//		BigDecimal value = new BigDecimal("100");
//		BigDecimal expectedBalance = BigDecimal.ZERO;
//
//		Account account = new Account();
//		account.setId("accountId");
//		account.setBalance(value);
//
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(account);
//		EasyMock.expect(
//				gigaSpace.write(EasyMock.isA(Transaction.class), EasyMock.isA(WriteModifiers.class)))
//				.andReturn(null);
//		EasyMock.expect(
//				gigaSpace.write(EasyMock.isA(Account.class), EasyMock.isA(WriteModifiers.class)))
//				.andReturn(null);
//		EasyMock.replay(gigaSpace);
//
//		Transaction result = transactionService.create("accountId", TransactionType.CREDIT, value);
//		EasyMock.verify(gigaSpace);
//
//		Assert.assertEquals(account.getBalance(), expectedBalance);
//		Assert.assertEquals(result.getAccountId(), account.getId());
//	}
//
//	@Test(expectedExceptions = IllegalStateException.class)
//	public void createInvalidCreditTransaction() {
//		BigDecimal value = new BigDecimal("50.00001");
//		BigDecimal expectedBalance = new BigDecimal("50");
//
//		Account account = new Account();
//		account.setId("accountId");
//		account.setBalance(expectedBalance);
//
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(account);
//		EasyMock.replay(gigaSpace);
//
//		Transaction result = transactionService.create("accountId", TransactionType.CREDIT, value);
//		EasyMock.verify(gigaSpace);
//
//		Assert.assertEquals(account.getBalance(), expectedBalance);
//		Assert.assertEquals(result.getAccountId(), null);
//	}
//
//	@Test(expectedExceptions = IllegalArgumentException.class)
//	public void createInvalidNegativeValueTransaction() {
//		BigDecimal value = new BigDecimal("-50");
//		BigDecimal expectedBalance = new BigDecimal("5000");
//
//		Account account = new Account();
//		account.setId("accountId");
//		account.setBalance(expectedBalance);
//
//		Transaction result = transactionService.create("accountId", TransactionType.CREDIT, value);
//
//		Assert.assertEquals(account.getBalance(), expectedBalance);
//		Assert.assertEquals(result.getAccountId(), null);
//	}
//
//	@Test(expectedExceptions = IllegalArgumentException.class)
//	public void createInvalidNullTypeTransaction() {
//		BigDecimal value = new BigDecimal("50");
//		BigDecimal expectedBalance = new BigDecimal("5000");
//
//		Account account = new Account();
//		account.setId("accountId");
//		account.setBalance(expectedBalance);
//
//		Transaction result = transactionService.create("accountId", null, value);
//
//		Assert.assertEquals(account.getBalance(), expectedBalance);
//		Assert.assertEquals(result.getAccountId(), null);
//	}
//
//	@Test
//	public void searchByNumber() {
//		Transaction expected = new Transaction();
//		expected.setAccountId("id");
//
//		EasyMock.expect(gigaSpace.readById(Transaction.class, expected.getId()))
//				.andReturn(expected);
//		EasyMock.replay(gigaSpace);
//		Transaction result = transactionService.searchByNumber(expected.getId());
//		EasyMock.verify(gigaSpace);
//		Assert.assertEquals(result, expected);
//
//	}
//
//	@Test(expectedExceptions = NoSuchElementException.class)
//	public void searchByNonExistentNumber() {
//		Transaction expected = new Transaction();
//		expected.setAccountId("id");
//
//		EasyMock.expect(gigaSpace.readById(Transaction.class, expected.getId())).andReturn(null);
//		EasyMock.replay(gigaSpace);
//		Transaction result = transactionService.searchByNumber(expected.getId());
//		EasyMock.verify(gigaSpace);
//	}
//
//	@Test
//	public void searchByAccountId() {
//		Account account = new Account();
//		account.setId("accountId");
//
//		Transaction expected = new Transaction();
//		expected.setAccountId(account.getId());
//
//		Transaction[] trans = new Transaction[5];
//
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(account);
//		EasyMock.expect(gigaSpace.readMultiple(EasyMock.isA(ISpaceQuery.class))).andReturn(trans);
//		EasyMock.replay(gigaSpace);
//
//		List<Transaction> result = transactionService.searchByAccountId(account.getId());
//		EasyMock.verify(gigaSpace);
//
//		Assert.assertEquals(result.size(), trans.length);
//	}
//
//	@Test(expectedExceptions = NoSuchElementException.class)
//	public void searchByNonExistentAccountId() {
//		Account account = new Account();
//		account.setId("accountId");
//
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(null);
//		EasyMock.replay(gigaSpace);
//
//		transactionService.searchByAccountId(account.getId());
//		EasyMock.verify(gigaSpace);
//	}
//
//	@Test
//	public void searchByClientId() {
//		Client owner = new Client();
//		owner.setId("ownerId");
//		Account account = new Account();
//		account.setId("accountId");
//		Account[] accounts = new Account[5];
//		for (int i = 0; i < accounts.length; ++i) {
//			accounts[i] = new Account();
//			accounts[i].setId(account.getId());
//		}
//		Transaction[] transactions = new Transaction[5];
//
//		EasyMock.expect(gigaSpace.readById(Client.class, owner.getId())).andReturn(owner)
//				.anyTimes();
//		EasyMock.expect(gigaSpace.readMultiple(EasyMock.isA((new SQLQuery<Account>()).getClass())))
//				.andReturn(accounts).once();
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(account)
//				.anyTimes();
//		EasyMock.expect(
//				gigaSpace.readMultiple(EasyMock.isA((new SQLQuery<Transaction>()).getClass())))
//				.andReturn(transactions).anyTimes();
//
//		EasyMock.replay(gigaSpace);
//		Assert.assertNotNull(transactionService, "TransactionService is null");
//		List<Transaction> result = transactionService.searchByClientId(owner.getId());
//		EasyMock.verify(gigaSpace);
//
//		Assert.assertEquals(result.size(), transactions.length * accounts.length);
//
//	}
//
//	@Test(expectedExceptions = NoSuchElementException.class)
//	public void searchByNonExistentCientId() {
//		Client owner = new Client();
//		owner.setId("ownerId");
//
//		EasyMock.expect(gigaSpace.readById(Client.class, owner.getId())).andReturn(null);
//		EasyMock.replay(gigaSpace);
//
//		transactionService.searchByClientId(owner.getId());
//		EasyMock.verify(gigaSpace);
//
//	}
//
//	@Test
//	public void searchByDateBetweenAndAccount() {
//		Date startDate = (new GregorianCalendar(2013, GregorianCalendar.APRIL, 1)).getTime();
//		Date endDate = (new GregorianCalendar(2013, GregorianCalendar.JUNE, 1)).getTime();
//		Account account = new Account();
//		account.setId("accountId");
//
//		Transaction[] transactions = new Transaction[5];
//
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(account);
//		EasyMock.expect(gigaSpace.readMultiple(EasyMock.isA(ISpaceQuery.class))).andReturn(
//				transactions);
//		EasyMock.replay(gigaSpace);
//
//		List<Transaction> result = transactionService.searchByDateBetweenAndAccount(startDate, endDate,
//				account.getId());
//		EasyMock.verify(gigaSpace);
//
//		Assert.assertEquals(result.size(), transactions.length);
//	}
//
//	@Test(expectedExceptions = NoSuchElementException.class)
//	public void searchByDateBetweenAndNonExistentAccount() {
//		Date startDate = (new GregorianCalendar(2013, GregorianCalendar.APRIL, 1)).getTime();
//		Date endDate = (new GregorianCalendar(2013, GregorianCalendar.JUNE, 1)).getTime();
//		Account account = new Account();
//		account.setId("NonExistentAccountId");
//
//		Transaction[] transactions = new Transaction[5];
//
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(null);
//		EasyMock.replay(gigaSpace);
//
//		List<Transaction> result = transactionService.searchByDateBetweenAndAccount(startDate, endDate,
//				account.getId());
//		EasyMock.verify(gigaSpace);
//	}
//
//	@Test(expectedExceptions = IllegalArgumentException.class)
//	public void searchByIllegalDateRangeAndAccount() {
//		Date startDate = (new GregorianCalendar(2013, GregorianCalendar.APRIL, 2)).getTime();
//		Date endDate = (new GregorianCalendar(2013, GregorianCalendar.APRIL, 1)).getTime();
//		Account account = new Account();
//		account.setId("accountId");
//
//		Transaction[] transactions = new Transaction[5];
//
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(account);
//		EasyMock.replay(gigaSpace);
//
//		List<Transaction> result = transactionService.searchByDateBetweenAndAccount(startDate, endDate,
//				account.getId());
//		EasyMock.verify(gigaSpace);
//	}
//
//	@Test
//	public void searchBySameDateRangeAndAccount() {
//		Date startDate = (new GregorianCalendar(2013, GregorianCalendar.APRIL, 1)).getTime();
//		Date endDate = (new GregorianCalendar(2013, GregorianCalendar.APRIL, 1)).getTime();
//		Account account = new Account();
//		account.setId("accountId");
//
//		Transaction[] transactions = new Transaction[5];
//
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(account);
//		EasyMock.expect(gigaSpace.readMultiple(EasyMock.isA(ISpaceQuery.class))).andReturn(
//				transactions);
//		EasyMock.replay(gigaSpace);
//
//		List<Transaction> result = transactionService.searchByDateBetweenAndAccount(startDate, endDate,
//				account.getId());
//		EasyMock.verify(gigaSpace);
//
//		Assert.assertEquals(result.size(), transactions.length);
//	}
//
//	@Test
//	public void searchByDateBetweenAndClient() {
//
//		Date startDate = (new GregorianCalendar(2013, GregorianCalendar.APRIL, 1)).getTime();
//		Date endDate = (new GregorianCalendar(2013, GregorianCalendar.JUNE, 1)).getTime();
//		Client owner = new Client();
//		owner.setId("ownerId");
//		Account account = new Account();
//		account.setId("accountId");
//		Account[] accounts = new Account[5];
//		for (int i = 0; i < accounts.length; ++i) {
//			accounts[i] = new Account();
//			accounts[i].setId(account.getId());
//		}
//		Transaction[] transactions = new Transaction[5];
//
//		EasyMock.expect(gigaSpace.readById(Client.class, owner.getId())).andReturn(owner)
//				.anyTimes();
//		EasyMock.expect(gigaSpace.readMultiple(EasyMock.isA((new SQLQuery<Account>()).getClass())))
//				.andReturn(accounts).once();
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(account)
//				.anyTimes();
//		EasyMock.expect(
//				gigaSpace.readMultiple(EasyMock.isA((new SQLQuery<Transaction>()).getClass())))
//				.andReturn(transactions).anyTimes();
//
//		EasyMock.replay(gigaSpace);
//
//		List<Transaction> result = transactionService.searchByDateBetweenAndClient(startDate, endDate,
//				owner.getId());
//		EasyMock.verify(gigaSpace);
//		Assert.assertEquals(result.size(), transactions.length * accounts.length);
//	}
//	
//	@Test(expectedExceptions = IllegalArgumentException.class)
//	public void searchByIllegalDateRangeAndClient() {
//		Date startDate = (new GregorianCalendar(2013, GregorianCalendar.APRIL, 2)).getTime();
//		Date endDate = (new GregorianCalendar(2013, GregorianCalendar.APRIL, 1)).getTime();
//		Account account = new Account();
//		account.setId("accountId");
//
//		Transaction[] transactions = new Transaction[5];
//
//		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(account);
//		EasyMock.replay(gigaSpace);
//
//		List<Transaction> result = transactionService.searchByDateBetweenAndAccount(startDate, endDate,
//				account.getId());
//		EasyMock.verify(gigaSpace);
//	}
}
