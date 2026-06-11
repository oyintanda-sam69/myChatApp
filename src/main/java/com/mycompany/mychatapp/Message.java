/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author os828
 */
public class Message {

    // ===================== Fields =====================

    private int    messageNumber;
    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;

    // ===================== Part 3: Five Parallel Arrays =====================

    private static List<String> sentMessages       = new ArrayList<>();

    private static List<String> disregardedMessages = new ArrayList<>();

    private static List<String> storedMessages     = new ArrayList<>();

    private static List<String> messageHashes      = new ArrayList<>();

    private static List<String> messageIDs         = new ArrayList<>();

    private static List<String> recipientList      = new ArrayList<>();

    private static int totalMessagesSent = 0;

    // ===================== Constructor =====================

    // Creates a new Message and auto-generates its ID and hash.
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.messageText   = messageText;
        this.messageID     = generateMessageID();
        this.messageHash   = generateMessageHash();
    }

    // ===================== ID & Hash Generation =====================

    public String generateMessageID() {
        long number = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(number);
    }

    public String generateMessageHash() {
        // First two digits of the message ID
        String idPrefix = messageID != null ? messageID.substring(0, 2) : "00";

        // Split message text into words, strip punctuation, uppercase
        String[] words = messageText != null
                ? messageText.trim().split("\\s+")
                : new String[]{"X"};

        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        String lastWord  = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "").toUpperCase();

        // Avoid duplicate when there is only one word
        String combined = words.length == 1 ? firstWord : firstWord + lastWord;

        return idPrefix + ":" + messageNumber + ":" + combined;
    }

    // ===================== Validation =====================

    public boolean checkMessageID(String id) {
        return id != null && id.length() == 10;
    }

    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber != null
            && cellNumber.startsWith("+27")
            && cellNumber.length() == 12;
    }

    public String checkMessageLength() {
        if (messageText == null || messageText.isEmpty()) {
            return "Message cannot be empty.";
        }
        if (messageText.length() > 250) {
            return "Message exceeds 250 characters by "
                + (messageText.length() - 250) + " characters.";
        }
        return "Message ready to send.";
    }
 // ===================== Part 3: sentMessage() =====================

    public String sentMessage(int option) {
        switch (option) {
            case 1 -> {
                sentMessages.add(messageText);
                messageHashes.add(messageHash);
                messageIDs.add(messageID);
                recipientList.add(recipient);
                totalMessagesSent++;
                return "Message successfully sent.";
            }
            case 2 -> {
                disregardedMessages.add(messageText);
                return "Message disregarded.";
            }
            case 3 -> {
                storeMessage();
                messageHashes.add(messageHash);
                messageIDs.add(messageID);
                recipientList.add(recipient);
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid option. Please select 1, 2, or 3.";
            }
        }
    }

    public void storeMessage() {
        try (java.io.FileWriter fw = new java.io.FileWriter("messages.json", true)) {
            fw.write(toJson() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Could not save message to file: " + e.getMessage());
        }
    }

    // ===================== Part 3: Load Stored Messages =====================

    public static void loadStoredMessages() {
        storedMessages.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                // Extract the value of "messageText":"..." manually
                String key = "\"messageText\":\"";
                int start = line.indexOf(key);
                if (start != -1) {
                    start += key.length();
                    int end = line.indexOf("\"", start);
                    if (end != -1) {
                        String text = line.substring(start, end);
                        storedMessages.add(text);
                    }
                }
            }
        } catch (IOException e) {
            // File does not exist yet – this is expected on first run
        }
    }

    // ===================== Part 3: Longest Message =====================

    public String displayLongestMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }
        String longest = "";
        for (String msg : storedMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest;
    }

    // ===================== Part 3: Search by ID =====================

    public String searchByMessageID(String id) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                // Use sentMessages if index is in range, otherwise check storedMessages
                if (i < sentMessages.size()) {
                    return sentMessages.get(i);
                }
            }
        }
        return "Message not found.";
    }

    // ===================== Part 3: Search by Recipient =====================

    public String searchByRecipient(String recipient) {
        StringBuilder results = new StringBuilder();
        for (int i = 0; i < recipientList.size(); i++) {
            if (recipientList.get(i).equals(recipient)) {
                if (results.length() > 0) {
                    results.append("\n");
                }
                results.append(sentMessages.get(i));
            }
        }
        if (results.length() == 0) {
            return "No messages found for recipient: " + recipient;
        }
        return results.toString();
    }

    // ===================== Part 3: Delete by Hash =====================

    public String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                String deletedText = i < sentMessages.size() ? sentMessages.get(i) : "(stored)";
                messageHashes.remove(i);
                if (i < sentMessages.size())  sentMessages.remove(i);
                if (i < messageIDs.size())    messageIDs.remove(i);
                if (i < recipientList.size()) recipientList.remove(i);
                return "Message: " + deletedText + " successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    // ===================== Part 3: Display Report =====================

    public static String printMessages() {
        StringBuilder report = new StringBuilder();
        report.append("=== Message Report ===\n");
        if (sentMessages.isEmpty()) {
            report.append("No sent messages to display.\n");
            return report.toString();
        }
        for (int i = 0; i < sentMessages.size(); i++) {
            report.append("──────────────────────────────\n");
            report.append("  Hash      : ").append(i < messageHashes.size()  ? messageHashes.get(i)  : "N/A").append("\n");
            report.append("  Recipient : ").append(i < recipientList.size()  ? recipientList.get(i)  : "N/A").append("\n");
            report.append("  Message   : ").append(sentMessages.get(i)).append("\n");
        }
        report.append("──────────────────────────────\n");
        return report.toString();
    }

    // ===================== Part 3: Total Messages Counter =====================

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // ===================== Array Accessors (for tests) =====================

    /** Returns the sentMessages list. */
    public static List<String> getSentMessagesList()        { return sentMessages; }

    /** Returns the disregardedMessages list. */
    public static List<String> getDisregardedMessages()     { return disregardedMessages; }

    /** Returns the storedMessages list. */
    public static List<String> getStoredMessages()          { return storedMessages; }

    /** Returns the messageHashes list. */
    public static List<String> getMessageHashes()           { return messageHashes; }

    /** Returns the messageIDs list. */
    public static List<String> getMessageIDs()              { return messageIDs; }

    /** Returns the recipientList. */
    public static List<String> getRecipientList()           { return recipientList; }

    public static void clearAllArrays() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        recipientList.clear();
        totalMessagesSent = 0;
    }

    // ===================== Legacy Support =====================

    public void markAsSent() {
        sentMessages.add(messageText);
        messageHashes.add(messageHash);
        messageIDs.add(messageID);
        recipientList.add(recipient);
        totalMessagesSent++;
    }

    public static List<String> getSentMessages() {
        return sentMessages;
    }

    public static void clearSentMessages() {
        clearAllArrays();
    }

    // ===================== Getters / Setters =====================

    /** @return the message sequence number */
    public int    getMessageNumber() { return messageNumber; }

    public String getRecipient()     { return recipient;     }
    
    public String getMessageID()     { return messageID;     }

    public String getMessageHash()   {
        return messageHash;  
    }
    
    public String getMessageText()   { 
        return messageText;   }
    
    public void setMessageText(String newText) {
        this.messageText = newText;
        this.messageHash = generateMessageHash();
    }

    // ========================== Display ======================================

    public String printMessage() {
        return "┌─────────────────────────────────────┐\n"
             + "  Message #"  + messageNumber + "\n"
             + "  ID        : " + messageID    + "\n"
             + "  Hash      : " + messageHash  + "\n"
             + "  Recipient : " + recipient    + "\n"
             + "  Message   : " + messageText  + "\n"
             + "└─────────────────────────────────────┘";
    }

    public String toJson() {
        return "{"
             + "\"messageNumber\":"  + messageNumber                 + ","
             + "\"recipient\":\""    + escapeJson(recipient)         + "\","
             + "\"messageText\":\""  + escapeJson(messageText)       + "\","
             + "\"messageID\":\""    + messageID                     + "\","
             + "\"messageHash\":\""  + messageHash                   + "\""
             + "}";
    }

    private String escapeJson(String s) {
        return s == null ? "" : s.replace("\\", "\\\\")
                                 .replace("\"", "\\\"")
                                 .replace("\n", "\\n")
                                 .replace("\r", "\\r");
    }
}
