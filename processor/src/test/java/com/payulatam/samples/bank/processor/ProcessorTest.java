package com.payulatam.samples.bank.processor;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.Data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * A simple unit test that verifies the Processor processData method actually processes
 * the Data object.
 */
public class ProcessorTest {

    @Test
    public void verifyProcessedFlag() {
        Processor processor = new Processor();
        Client client= new Client();
        client.setName("Simon");
        Client result = processor.processData(client);
        assertEquals("verify that the data object was processed", "Santiago", result.getName());
        assertEquals("verify the ID was not changed", client.getId(), result.getId());
    }
}
