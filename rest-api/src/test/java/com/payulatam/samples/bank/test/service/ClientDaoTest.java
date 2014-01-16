package com.payulatam.samples.bank.test.service;

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
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.service.ClientDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-test.xml")
public class ClientDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private ClientDao clientDAO;

	@Autowired
	private GigaSpace gigaSpace;

	@BeforeMethod
	public void beforeMethod() {
		EasyMock.resetToNice(gigaSpace);
	}

	@Test
	public void createValidClient() {
		Client client = new Client();
		client.setName("Simon");
		client.setAddress("cra 45");
		client.setTelephone("123456");

		EasyMock.expect(
				gigaSpace.write(EasyMock.isA(Client.class), EasyMock.isA(WriteModifiers.class)))
				.andReturn(null);
		EasyMock.replay(gigaSpace);

		Client result = clientDAO.create(client.getName(), client.getAddress(),
				client.getTelephone());
		EasyMock.verify(gigaSpace);

		Assert.assertEquals(result, client);

	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void updateNonExistingClient() {
		Client client = new Client();
		client.setId("nonExistentId");
		client.setName("Simon");
		client.setAddress("cra 45");
		client.setTelephone("123456");

		EasyMock.expect(gigaSpace.readById(Client.class, client.getId())).andReturn(null);
		EasyMock.replay(gigaSpace);

		clientDAO.update(client.getId(), client.getName(), client.getAddress(),
				client.getTelephone());
		EasyMock.verify(gigaSpace);
	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void deleteNonExistentClient() {

		EasyMock.expect(gigaSpace.takeById(Client.class, "nonExistentId")).andReturn(null);
		EasyMock.replay(gigaSpace);

		clientDAO.delete("nonExistentId");
		EasyMock.verify(gigaSpace);
	}
	@Test
	public void searchById() {
		Client expected = new Client();
		expected.setId("id");
		
		EasyMock.expect(gigaSpace.readById(Client.class, expected.getId())).andReturn(expected);
		EasyMock.replay(gigaSpace);

		Client result = clientDAO.searchById(expected.getId());
		Assert.assertEquals(result, expected);
		
		EasyMock.verify(gigaSpace);
	}
	@Test(expectedExceptions=NoSuchElementException.class)
	public void searchByNonExistentId() {
		Client expected = new Client();
		expected.setId("nonExistentId");
		
		EasyMock.expect(gigaSpace.readById(Client.class, expected.getId())).andReturn(null);
		EasyMock.replay(gigaSpace);

		clientDAO.searchById(expected.getId());
		EasyMock.verify(gigaSpace);
	}

}
