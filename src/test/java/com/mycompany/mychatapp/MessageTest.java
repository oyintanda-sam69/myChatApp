/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mychatapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author os828
 */
public class MessageTest {
     // Test that message ID is 10 characters long
    @Test
    public void testMessageIDLength() {
        Message msg = new Message(1, "+27831234567", "Hello World");
        assertEquals(10, msg.getMessageID().length());
    }

    // Test that a valid cell number passes
    @Test
    public void testValidRecipientCell() {
        Message msg = new Message(1, "+27831234567", "Hello");
        assertTrue(msg.checkRecipientCell("+27831234567"));
    }

    // Test that an invalid cell number fails
    @Test
    public void testInvalidRecipientCell() {
        Message msg = new Message(1, "0831234567", "Hello");
        assertFalse(msg.checkRecipientCell("0831234567"));
    }

    // Test that a short message passes the length check
    @Test
    public void testMessageLengthValid() {
        Message msg = new Message(1, "+27831234567", "Hi there!");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    // Test that a message over 250 characters fails
    @Test
    public void testMessageLengthTooLong() {
        String longText = "A".repeat(251);
        Message msg = new Message(1, "+27831234567", longText);
        assertNotEquals("Message ready to send.", msg.checkMessageLength());
    }

    // Test that message hash is not null or empty
    @Test
    public void testMessageHashNotEmpty() {
        Message msg = new Message(1, "+27831234567", "Hello");
        assertNotNull(msg.getMessageHash());
        assertFalse(msg.getMessageHash().isEmpty());
    }

    // Test that printMessage contains the recipient
    @Test
    public void testPrintMessageContainsRecipient() {
        Message msg = new Message(1, "+27831234567", "Hello");
        assertTrue(msg.printMessage().contains("+27831234567"));
    }

    // Test clearSentMessages clears the list
    @Test
    public void testClearSentMessages() {
        Message msg = new Message(1, "+27831234567", "Hello");
        msg.markAsSent();
        Message.clearSentMessages();
        assertEquals(0, Message.getSentMessages().size());
    }
}
