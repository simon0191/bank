package com.payulatam.samples.bank.processor;

import java.util.logging.Logger;

import org.openspaces.events.adapter.SpaceDataEvent;

import com.payulatam.samples.bank.common.Client;

/**
 * The processor simulates work done no un-processed Data object. The processData
 * accepts a Data object, simulate work by sleeping, and then sets the processed
 * flag to true and returns the processed Data.
 */
public class Processor {

    Logger log= Logger.getLogger(this.getClass().getName());

    private long workDuration = 100;

    /**
     * Sets the simulated work duration (in milliseconds). Defaut to 100.
     */
    public void setWorkDuration(long workDuration) {
        this.workDuration = workDuration;
    }

    /**
     * Process the given Data object and returning the processed Data.
     *
     * Can be invoked using OpenSpaces Events when a matching event
     * occurs.
     */
    @SpaceDataEvent
    public Client processData(Client client) {
        // sleep to simulate some work
        try {
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {
            // do nothing
        }
        client.setName("Santiago");
        log.info(" ------ PROCESSED : " + client);
        return client;
    }

}
