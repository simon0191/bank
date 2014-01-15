package com.payulatam.samples.bank.test;

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
import com.payulatam.samples.bank.service.ClientDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-test.xml")
public class ClientDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private ClientDAO clientDAO;

	private GigaSpace gigaSpace;

	@BeforeClass
	public void beforeClass() {
		gigaSpace = EasyMock.createNiceMock(GigaSpace.class);
		clientDAO.setGigaSpace(gigaSpace);
	}

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
	public void updateNotExistingClient() {
		Client client = new Client();
		client.setId("idNoExistente");
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
	public void deleteNotExistingClient() {

		EasyMock.expect(gigaSpace.takeById(Client.class, "idNoExistente")).andReturn(null);
		EasyMock.replay(gigaSpace);

		clientDAO.delete("idNoExistente");
		EasyMock.verify(gigaSpace);
	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void searchById() {
		Client expected = new Client();
		expected.setId("id");
		
		EasyMock.expect(gigaSpace.readById(Client.class, expected.getId())).andReturn(expected);
		EasyMock.replay(gigaSpace);

		Client result = clientDAO.searchById(expected.getId());
		Assert.assertEquals(result, expected);
		
		EasyMock.verify(gigaSpace);
	}

}
