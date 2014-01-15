package com.payulatam.samples.bank.test;

import static org.testng.AssertJUnit.assertNotNull;

import org.easymock.EasyMock;
import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.service.ClientDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-test.xml")
public class ClientDaoTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private ClientDAO clientDAO;
	
	private GigaSpace gigaSpace;

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void createValidateInvalidPhoneNumber() {
		Client client = new Client();
		client.setName("Simon");
		client.setAddress("");
		client.setTelephone("asdfa");
		
		EasyMock.expect(gigaSpace.write(client)).andReturn(null);
		EasyMock.replay(gigaSpace);
		
		Client result = clientDAO.create(client.getName(),client.getAddress(),client.getTelephone());
		EasyMock.verify(gigaSpace);
	}
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void createValidatePhoneNumberWithSpaces() {
		Client client = new Client();
		client.setName("Simon");
		client.setAddress("");
		client.setTelephone("123 456 4");
		
		EasyMock.expect(gigaSpace.write(client)).andReturn(null);
		EasyMock.replay(gigaSpace);
		
		Client result = clientDAO.create(client.getName(),client.getAddress(),client.getTelephone());
		EasyMock.verify(gigaSpace);
	}
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void createValidateEmptyPhoneNumber() {
		Client client = new Client();
		client.setName("Simon");
		client.setAddress("");
		client.setTelephone("");
		
		EasyMock.expect(gigaSpace.write(client)).andReturn(null);
		EasyMock.replay(gigaSpace);
		
		Client result = clientDAO.create(client.getName(),client.getAddress(),client.getTelephone());
		EasyMock.verify(gigaSpace);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void createValidateEmptyName() {
		Client client = new Client();
		client.setName("");
		client.setAddress("");
		client.setTelephone("123456");
		
		EasyMock.expect(gigaSpace.write(client)).andReturn(null);
		EasyMock.replay(gigaSpace);
		
		Client result = clientDAO.create(client.getName(),client.getAddress(),client.getTelephone());
		EasyMock.verify(gigaSpace);
	}
	
	@Test
	public void createValidateNameWithOutSpaces() {
		Client client = new Client();
		client.setName("SimonSantiagoSoriano");
		client.setAddress("");
		client.setTelephone("123456");
		
		Client result = clientDAO.create(client.getName(),client.getAddress(),client.getTelephone());
	}
	@Test
	public void createValidateNameWithSpaces() {
		Client client = new Client();
		client.setName("Simon Santiago Soriano");
		client.setAddress("");
		client.setTelephone("123456");
		Client result = clientDAO.create(client.getName(),client.getAddress(),client.getTelephone());
	}
	
	@BeforeMethod
	public void beforeMethod() {		
		gigaSpace = EasyMock.createNiceMock(GigaSpace.class);
		clientDAO.setGigaSpace(gigaSpace);
	}

	@BeforeClass
	public void beforeClass() {
	}

	@BeforeTest
	public void beforeTest() {
	}

	@BeforeSuite
	public void beforeSuite() {
	}

}
