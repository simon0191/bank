package com.payulatam.samples.bank.test.service.integration;

import java.util.NoSuchElementException;

import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gigaspaces.client.WriteModifiers;
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.service.IClientService;
import com.payulatam.samples.bank.test.utils.Fixtures;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/integrationTestContext.xml")
public class ClientServiceIntegrationTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private IClientService clientDAO;

	@Autowired
	private GigaSpace gigaSpace;

	@BeforeMethod
	public void beforeMethod() {
		gigaSpace.clear(null);
	}

	public void afterClass() {
		gigaSpace.clear(null);
	}

	@Test(groups={"createClient"})
	public void createClientAssignsId() {
		Client client = Fixtures.standardClient();
		Client result = clientDAO.create(client.getName(), client.getAddress(),
				client.getTelephone());
		Assert.assertNotNull(result.getId());
	}

	@Test(groups={"createClient"})
	public void createClientDoesNotModifyAttributes() {
		Client expected = Fixtures.standardClient();
		Client result = clientDAO.create(expected.getName(), expected.getAddress(),
				expected.getTelephone());
		expected.setId(result.getId());
		Assert.assertEquals(result, expected);
	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void updateNonExistingClientThrowsException() {
		Client client = Fixtures.standardClient();
		client.setId("nonExistentId");
		clientDAO.update(client.getId(), client.getName(), client.getAddress(),
				client.getTelephone());
	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void deleteNonExistentClientThowsException() {
		clientDAO.delete("nonExistentId");
	}
	@Test
	public void searchByIdReturnsRightClient() {
		Client expected = Fixtures.standardClient();
		gigaSpace.write(expected);
		
		Client result = clientDAO.searchById(expected.getId());
		
		Assert.assertEquals(result, expected);

		
	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void searchByNonExistentIdThrowsException() {
		clientDAO.searchById("NonExistentId");
	}

}
