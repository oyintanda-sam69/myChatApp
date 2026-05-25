/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


/**
 *
 * @author os828
 */
public class Message {
    private int    messageNumber;
    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;

    // Static list shared across all instances (for "sent" storage)
    private static java.util.List<Message> sentMessages = new java.util.ArrayList<>();

    // ==============================Constructor================================
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.messageText   = messageText;
        this.messageID     = generateMessageID();
        this.messageHash   = generateMessageHash();
    }

    // ======================ID & Hash generation===============================

    public String generateMessageID() {
        long number = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L; // always 10 digits
        return String.valueOf(number);
    }

    //Creates a short SHA-256 hash from recipient + messageText.
    //Returns the first 8 hex characters (e.g. "a3f92b1c").
     
    public String generateMessageHash() {
        try {
            String input = recipient + messageText;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.substring(0, 8); // short hash
        } catch (Exception e) {
            return "00000000";
        }
    }

    // ==========================Validation===============================

    //Returns true if the message ID is exactly 10 characters
    public boolean checkMessageID(String id) {
        return id != null && id.length() == 10;
    }

    //Returns true if the cell number starts with +27 and is 12 characters long.
    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber != null
                && cellNumber.startsWith("+27")
                && cellNumber.length() == 12;
    }

    //Checks that the message text is between 1 and 250 characters.
     
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

    // =================Static list helpe=======================================

    public void markAsSent() {
        sentMessages.add(this);
    }

    public static java.util.List<Message> getSentMessages() {
        return sentMessages;
    }

    public static void clearSentMessages() {
        sentMessages.clear();
    }


    public int    getMessageNumber() { return messageNumber; }
    public String getRecipient()     { return recipient;     }
    public String getMessageID()     { return messageID;     }
    public String getMessageHash()   { return messageHash;   }
    public String getMessageText()   { return messageText;   }

    public void setMessageText(String newText) {
        this.messageText = newText;
        this.messageHash = generateMessageHash();
    }

    // ============================Display======================================

    public String printMessage() {
        return "┌─────────────────────────────────────┐\n"
             + "  Message #" + messageNumber + "\n"
             + "  Recipient : " + recipient   + "\n"
             + "  Message   : " + messageText + "\n"
             + "  ID        : " + messageID   + "\n"
             + "  Hash      : " + messageHash + "\n"
             + "└─────────────────────────────────────┘";
    }

    public String toJson() {
        return "{"
             + "\"messageNumber\":" + messageNumber + ","
             + "\"recipient\":\""   + escapeJson(recipient)    + "\","
             + "\"messageText\":\"" + escapeJson(messageText)  + "\","
             + "\"messageID\":\""   + messageID   + "\","
             + "\"messageHash\":\"" + messageHash + "\""
             + "}";
    }

    private String escapeJson(String s) {
        return s == null ? "" : s.replace("\\", "\\\\")
                                 .replace("\"", "\\\"")
                                 .replace("\n", "\\n")
                                 .replace("\r", "\\r");
    }
    
}
