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
    // Test data
    private Message msg1;
    private Message msg2;
    private Message msg3;
    private Message msg4;
    private Message msg5;

    @BeforeEach
    public void setUp() {
        // Always start from a clean state
        Message.clearAllArrays();

        msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg3 = new Message(3, "+27834484567", "Yohoo, I am on my way.");
        msg4 = new Message(4, "0838884567",   "It is dinner time!");
        msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
    }

    @AfterEach
    public void tearDown() {
        Message.clearAllArrays();
    }

    // ════════════════════════════════════════════════════════════════════════
    // ── PART 2 TESTS ────────────────────────────────────────────────────────
    // ════════════════════════════════════════════════════════════════════════

    /** Message ID must be exactly 10 digits. */
    @Test
    public void testMessageIDLength() {
        assertEquals(10, msg1.getMessageID().length());
    }

    /** A correctly formatted +27 number must pass. */
    @Test
    public void testValidRecipientCell() {
        assertTrue(msg1.checkRecipientCell("+27831234567"));
    }

    /** A number without an international code must fail. */
    @Test
    public void testInvalidRecipientCell() {
        assertFalse(msg4.checkRecipientCell("0838884567"));
    }

    /** A message within 250 characters must be ready to send. */
    @Test
    public void testMessageLengthValid() {
        assertEquals("Message ready to send.", msg1.checkMessageLength());
    }

    /** A message over 250 characters must fail the length check. */
    @Test
    public void testMessageLengthTooLong() {
        Message longMsg = new Message(1, "+27831234567", "A".repeat(251));
        assertNotEquals("Message ready to send.", longMsg.checkMessageLength());
    }

    @Test
    public void testMessageHashNotEmpty() {
        assertNotNull(msg1.getMessageHash());
        assertFalse(msg1.getMessageHash().isEmpty());
    }

    @Test
    public void testPrintMessageContainsRecipient() {
        assertTrue(msg1.printMessage().contains("+27834557896"));
    }

    @Test
    public void testClearSentMessages() {
        msg1.markAsSent();
        Message.clearSentMessages();
        assertEquals(0, Message.getSentMessages().size());
    }

    // ========================== PART 3 TESTS =================================

    @Test
    public void testSentMessagesArray_correctlyPopulated() {
        msg1.sentMessage(1); // Send
        msg4.sentMessage(1); // Send

        java.util.List<String> sent = Message.getSentMessagesList();
        assertTrue(sent.contains("Did you get the cake?"),
                "sentMessages should contain message 1");
        assertTrue(sent.contains("It is dinner time!"),
                "sentMessages should contain message 4");
    }

    @Test
    public void testDisplayLongestMessage_returnsCorrectMessage() {
        // Simulate what loadStoredMessages() does
        Message.getStoredMessages().add("Where are you? You are late! I have asked you to be on time.");
        Message.getStoredMessages().add("Yohoo, I am on my way.");
        Message.getStoredMessages().add("Ok, I am leaving without you.");

        String longest = msg1.displayLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByMessageID_returnsCorrectMessage() {
        msg4.sentMessage(1); // adds to sentMessages and messageIDs
        String id = msg4.getMessageID();

        String result = msg4.searchByMessageID(id);
        assertEquals("It is dinner time!", result);
    }

    @Test
    public void testSearchByRecipient_returnsAllMatchingMessages() {
        msg2.sentMessage(1); // sent to +27838884567
        msg5.sentMessage(1); // sent to +27838884567

        String result = msg2.searchByRecipient("+27838884567");

        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."),
                "Result should contain message 2");
        assertTrue(result.contains("Ok, I am leaving without you."),
                "Result should contain message 5");
    }

    
    @Test
    public void testDeleteByHash_removesCorrectMessage() {
        msg2.sentMessage(1);
        String hash = msg2.getMessageHash();

        String result = msg2.deleteByHash(hash);
        assertEquals(
            "Message: Where are you? You are late! I have asked you to be on time. successfully deleted.",
            result
        );
    }

    @Test
    public void testDisplayReport_containsRequiredFields() {
        msg1.sentMessage(1);
        msg4.sentMessage(1);

        String report = Message.printMessages();

        assertTrue(report.contains(msg1.getMessageHash()),    "Report must contain msg1 hash");
        assertTrue(report.contains("+27834557896"),            "Report must contain msg1 recipient");
        assertTrue(report.contains("Did you get the cake?"),  "Report must contain msg1 text");

        assertTrue(report.contains(msg4.getMessageHash()),    "Report must contain msg4 hash");
        assertTrue(report.contains("It is dinner time!"),     "Report must contain msg4 text");
    }
    
}
