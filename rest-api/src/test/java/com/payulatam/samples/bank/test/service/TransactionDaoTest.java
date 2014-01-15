package com.payulatam.samples.bank.test.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.easymock.EasyMock;
import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gigaspaces.client.WriteModifiers;
import com.gigaspaces.query.ISpaceQuery;
import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.TransactionType;
import com.payulatam.samples.bank.service.TransactionDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-test.xml")
public class TransactionDaoTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private TransactionDao transactionDao;

	private GigaSpace gigaSpace;

	@BeforeClass
	public void beforeClass() {
		gigaSpace = EasyMock.createNiceMock(GigaSpace.class);
		transactionDao.setGigaSpace(gigaSpace);
	}

	@BeforeMethod
	public void beforeMethod() {
		EasyMock.resetToNice(gigaSpace);
	}
	
	@Test
	public void createDebitTransaction() {
		BigDecimal value = new BigDecimal("100");
		BigDecimal expectedBalance = new BigDecimal("200");
		
		Account account = new Account();
		account.setId("accountId");
		account.setBalance(value);		
		
		EasyMock.expect(gigaSpace.readById(Account.class,account.getId())).andReturn(account);
		EasyMock.expect(gigaSpace.write(EasyMock.isA(Transaction.class),EasyMock.isA(WriteModifiers.class))).andReturn(null);
		EasyMock.expect(gigaSpace.write(EasyMock.isA(Account.class),EasyMock.isA(WriteModifiers.class))).andReturn(null);
		EasyMock.replay(gigaSpace);
		
		Transaction result = transactionDao.create("accountId",TransactionType.DEBIT,value);
		EasyMock.verify(gigaSpace);
		
		Assert.assertEquals(account.getBalance(), expectedBalance);
		Assert.assertEquals(result.getAccountId(), account.getId());
		
	}
	
	@Test
	public void createCreditTransaction() {
		BigDecimal value = new BigDecimal("100");
		BigDecimal expectedBalance = BigDecimal.ZERO;
		
		Account account = new Account();
		account.setId("accountId");
		account.setBalance(value);		
		
		EasyMock.expect(gigaSpace.readById(Account.class,account.getId())).andReturn(account);
		EasyMock.expect(gigaSpace.write(EasyMock.isA(Transaction.class),EasyMock.isA(WriteModifiers.class))).andReturn(null);
		EasyMock.expect(gigaSpace.write(EasyMock.isA(Account.class),EasyMock.isA(WriteModifiers.class))).andReturn(null);
		EasyMock.replay(gigaSpace);
		
		Transaction result = transactionDao.create("accountId",TransactionType.CREDIT,value);
		EasyMock.verify(gigaSpace);
		
		Assert.assertEquals(account.getBalance(), expectedBalance);
		Assert.assertEquals(result.getAccountId(), account.getId());
	}
	
	@Test(expectedExceptions=IllegalStateException.class)
	public void createInvalidCreditTransaction() {
		BigDecimal value = new BigDecimal("50.00001");
		BigDecimal expectedBalance = new BigDecimal("50");
		
		Account account = new Account();
		account.setId("accountId");
		account.setBalance(expectedBalance);		
		
		EasyMock.expect(gigaSpace.readById(Account.class,account.getId())).andReturn(account);
		EasyMock.replay(gigaSpace);
		
		Transaction result = transactionDao.create("accountId",TransactionType.CREDIT,value);
		EasyMock.verify(gigaSpace);
		
		Assert.assertEquals(account.getBalance(), expectedBalance);
		Assert.assertEquals(result.getAccountId(), null);
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void createInvalidNegativeValueTransaction() {
		BigDecimal value = new BigDecimal("-50");
		BigDecimal expectedBalance = new BigDecimal("5000");
		
		Account account = new Account();
		account.setId("accountId");
		account.setBalance(expectedBalance);		
		
		Transaction result = transactionDao.create("accountId",TransactionType.CREDIT,value);
		
		Assert.assertEquals(account.getBalance(), expectedBalance);
		Assert.assertEquals(result.getAccountId(), null);
	}
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void createInvalidNullTypeTransaction() {
		BigDecimal value = new BigDecimal("50");
		BigDecimal expectedBalance = new BigDecimal("5000");
		
		Account account = new Account();
		account.setId("accountId");
		account.setBalance(expectedBalance);		
		
		Transaction result = transactionDao.create("accountId",null,value);
		
		Assert.assertEquals(account.getBalance(), expectedBalance);
		Assert.assertEquals(result.getAccountId(), null);
	}
	
	@Test
	public void searchByNumber() {
		Transaction expected = new Transaction();
		expected.setAccountId("id");
		
		EasyMock.expect(gigaSpace.readById(Transaction.class,expected.getId())).andReturn(expected);
		EasyMock.replay(gigaSpace);
		Transaction result = transactionDao.searchByNumber(expected.getId());
		EasyMock.verify(gigaSpace);
		Assert.assertEquals(result, expected);
		
	}
	@Test(expectedExceptions=NoSuchElementException.class)
	public void searchByNonExistentNumber() {
		Transaction expected = new Transaction();
		expected.setAccountId("id");
		
		EasyMock.expect(gigaSpace.readById(Transaction.class,expected.getId())).andReturn(null);
		EasyMock.replay(gigaSpace);
		Transaction result = transactionDao.searchByNumber(expected.getId());
		EasyMock.verify(gigaSpace);
	}
	
	@Test
	public void searchByAccountId() {
		Account account = new Account();
		account.setId("accountId");
		
		Transaction expected = new Transaction();
		expected.setAccountId(account.getId());
		
		Transaction[] trans = new Transaction[5];
		
		EasyMock.expect(gigaSpace.readById(Account.class,account.getId())).andReturn(account);
		EasyMock.expect(gigaSpace.readMultiple(EasyMock.isA(ISpaceQuery.class))).andReturn(trans);
		EasyMock.replay(gigaSpace);
		
		List<Transaction> result = transactionDao.searchByAccountId(account.getId());
		EasyMock.verify(gigaSpace);
		
		Assert.assertEquals(result.size(), trans.length);
	}
	@Test(expectedExceptions=NoSuchElementException.class)
	public void searchByNonExistentAccountId() {
		Account account = new Account();
		account.setId("accountId");
		
		EasyMock.expect(gigaSpace.readById(Account.class,account.getId())).andReturn(null);
		EasyMock.replay(gigaSpace);
		
		transactionDao.searchByAccountId(account.getId());
		EasyMock.verify(gigaSpace);
	}
	
	@Test
	public void searchByClientId() {
		Client owner = new Client();
		owner.setId("ownerId");
		Transaction[] transactions = new Transaction[5];
		
		
		EasyMock.expect(gigaSpace.readById(Client.class,owner.getId())).andReturn(null);
		
		EasyMock.replay(gigaSpace);
		
		
		List<Transaction> result = transactionDao.searchByClientId(owner.getId());
		EasyMock.verify(gigaSpace);
		
		Assert.assertEquals(result.size(), transactions.length);
//				
//		Transaction expected = new Transaction();
//		expected.setAccountId(account.getId());
//		
//		Transaction[] trans = new Transaction[5];
//		
//		EasyMock.expect(gigaSpace.readById(Account.class,account.getId())).andReturn(account);
//		EasyMock.expect(gigaSpace.readMultiple(EasyMock.isA(ISpaceQuery.class))).andReturn(trans);
//		EasyMock.replay(gigaSpace);
//		
//		List<Transaction> result = transactionDao.searchByAccountId(account.getId());
//		EasyMock.verify(gigaSpace);
//		
//		Assert.assertEquals(result.size(), trans.length);
		Assert.assertEquals(null,"asdf");
	}
	@Test(expectedExceptions=NoSuchElementException.class)
	public void searchByNonExistentCientId() {
		Client owner = new Client();
		owner.setId("ownerId");
		
		EasyMock.expect(gigaSpace.readById(Client.class,owner.getId())).andReturn(null);
		EasyMock.replay(gigaSpace);
		
		transactionDao.searchByClientId(owner.getId());
		EasyMock.verify(gigaSpace);
		
	}
}
