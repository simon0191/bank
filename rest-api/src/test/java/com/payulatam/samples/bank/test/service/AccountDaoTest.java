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
import com.payulatam.samples.bank.service.IAccountDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-test.xml")
public class AccountDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private IAccountDao accountDao;

	@Autowired
	private GigaSpace gigaSpace;

	@BeforeMethod
	public void beforeMethod() {
		EasyMock.resetToNice(gigaSpace);
	}

	@Test
	public void createValidAccount() {
		Client owner = new Client();
		owner.setId("id");

		EasyMock.expect(gigaSpace.readById(Client.class, owner.getId())).andReturn(owner);
		EasyMock.expect(
				gigaSpace.write(EasyMock.isA(Account.class), EasyMock.isA(WriteModifiers.class)))
				.andReturn(null);
		EasyMock.replay(gigaSpace);

		Account result = accountDao.create(owner.getId());
		EasyMock.verify(gigaSpace);

		Assert.assertEquals(result.getClientId(), owner.getId(), "Client id mismatch");
		Assert.assertEquals(result.getBalance(), BigDecimal.ZERO,
				"Account initial balance should be 0");

	}
	@Test(expectedExceptions=NoSuchElementException.class)
	public void createAccountInvalidClientId() {
		Client owner = new Client();
		owner.setId("NonExistentId");

		EasyMock.expect(gigaSpace.readById(Client.class, owner.getId())).andReturn(null);
		EasyMock.replay(gigaSpace);

		accountDao.create(owner.getId());
		EasyMock.verify(gigaSpace);
	}
	
	@Test
	public void deleteValidAccount() {
		Account account = new Account();
		account.setId("id");

		EasyMock.expect(gigaSpace.takeById(Account.class, account.getId())).andReturn(account);
		EasyMock.replay(gigaSpace);

		Account result = accountDao.delete(account.getId());
		EasyMock.verify(gigaSpace);

		Assert.assertEquals(result.getId(),account.getId(), "Account id mismatch");
	}
	
	@Test(expectedExceptions=NoSuchElementException.class)
	public void deleteAccountInvalidId() {
		Account account = new Account();
		account.setId("nonExistentId");

		EasyMock.expect(gigaSpace.takeById(Account.class, account.getId())).andReturn(null);
		EasyMock.replay(gigaSpace);

		accountDao.delete(account.getId());
		EasyMock.verify(gigaSpace);
	}
	@Test
	public void searchAcountById() {
		Account account = new Account();
		account.setId("id");
		account.setBalance(BigDecimal.ZERO);
		account.setClientId("clientIf");
		

		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(account);
		EasyMock.replay(gigaSpace);

		Account result = accountDao.searchById(account.getId());
		EasyMock.verify(gigaSpace);

		Assert.assertEquals(result,account, "Account mismatch");
		
	}
	@Test(expectedExceptions=NoSuchElementException.class)
	public void searchAcountByNonExistentId() {
		Account account = new Account();
		account.setId("id");
		account.setBalance(BigDecimal.ZERO);
		account.setClientId("clientIf");
		
		EasyMock.expect(gigaSpace.readById(Account.class, account.getId())).andReturn(null);
		EasyMock.replay(gigaSpace);

		accountDao.searchById(account.getId());
		EasyMock.verify(gigaSpace);
	}
	
	@Test
	public void searchAcountsByClientId() {
		Client owner = new Client();
		owner.setId("owner");
	
		Account[] accounts = new Account[5]; 
		
		EasyMock.expect(gigaSpace.readById(Client.class,owner.getId())).andReturn(owner);
		EasyMock.expect(gigaSpace.readMultiple(EasyMock.isA(ISpaceQuery.class))).andReturn(accounts);
		EasyMock.replay(gigaSpace);

		List<Account> result = accountDao.searchAccountsByClientId(owner.getId());
		EasyMock.verify(gigaSpace);

		Assert.assertEquals(result.size(), accounts.length);
		
	}
	@Test(expectedExceptions=NoSuchElementException.class)
	public void searchAcountsByNonExistentClientId() {
		Client owner = new Client();
		owner.setId("nonExistentId");
		
		EasyMock.expect(gigaSpace.readById(Client.class,owner.getId())).andReturn(null);
		EasyMock.replay(gigaSpace);

		List<Account> result = accountDao.searchAccountsByClientId(owner.getId());
		EasyMock.verify(gigaSpace);
		
	}
	
}
