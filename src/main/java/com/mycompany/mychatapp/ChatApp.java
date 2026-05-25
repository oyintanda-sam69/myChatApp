/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author os828
 */
public class ChatApp {
     //===============================Storage====================================
    private static final List<Message> messageStore = new ArrayList<>();
    private static int messageCounter = 0; // loop counter / message number

    //=========================Entry point======================================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        printWelcome();

        boolean running = true;

        //=============Main while loop (menu)===================================
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> sendMessage(scanner);
                case "2" -> displayAllMessages();
                case "3" -> searchMessage(scanner);
                case "4" -> saveMessagesToJson();
                case "5" -> {
                    System.out.println("\n  Goodbye! Have a great day.\n");
                    running = false;
                }
                default  -> System.out.println("\n Invalid option. Please enter 1–5.\n");
            }
        }

        scanner.close();
    }

    //=========================Welcome banner===================================
    private static void printWelcome() {
        System.out.println("Welcome to QuickChat");
        System.out.println("Fast, simple messaging for you!");
        System.out.println();
    }

    //==========================Numeric menu====================================
    private static void printMenu() {
        System.out.println("──────────────────────────────────────");
        System.out.println("  MENU");
        System.out.println("  1) Send a Message");
        System.out.println("  2) Show All Messages");
        System.out.println("  3) Search Message by ID");
        System.out.println("  4) Save Messages to JSON");
        System.out.println("  5) Quit");
        System.out.println("──────────────────────────────────────");
        System.out.print("Enter choice: ");
    }

    //==================Option 1: Send a message================================
    private static void sendMessage(Scanner scanner) {
        System.out.println("\n── New Message ──────────────────────");

        // Recipient validation loop
        String recipient;
        while (true) {
            System.out.print("Recipient cell (+27XXXXXXXXX): ");
            recipient = scanner.nextLine().trim();
            Message temp = new Message(0, recipient, "");
            if (temp.checkRecipientCell(recipient)) {
                break;
            }
            System.out.println("Invalid number. Must start with +27 and be 12 characters.");
        }

        // Message text validation loop
        String text;
        while (true) {
            System.out.print("Message (max 250 chars): ");
            text = scanner.nextLine();
            messageCounter++; // increment loop counter
            Message candidate = new Message(messageCounter, recipient, text);
            String lengthCheck = candidate.checkMessageLength();
            if ("Message ready to send.".equals(lengthCheck)) {
                // Valid – store it
                messageStore.add(candidate);
                candidate.markAsSent();

                System.out.println("\n" + lengthCheck);
                System.out.println(candidate.printMessage());
                break;
            } else {
                System.out.println("" + lengthCheck);
                messageCounter--; // don't count rejected messages
            }
        }

        System.out.println();
    }

    //==============Option 2: Display all messages (for loop)===================
    private static void displayAllMessages() {
        System.out.println("\n── All Messages ─────────────────────");
        if (messageStore.isEmpty()) {
            System.out.println("  No messages yet.\n");
            return;
        }
        // for loop over stored messages
        for (int i = 0; i < messageStore.size(); i++) {
            System.out.println(messageStore.get(i).printMessage());
        }
        System.out.println("  Total: " + messageStore.size() + " message(s).\n");
    }

    //===============Option 3: Search by message ID=============================
    private static void searchMessage(Scanner scanner) {
        System.out.println("\n── Search Message ───────────────────");
        System.out.print("Enter Message ID: ");
        String searchID = scanner.nextLine().trim();

        boolean found = false;
        for (Message m : messageStore) {
            if (m.getMessageID().equals(searchID)) {
                System.out.println("\n Message found:");
                System.out.println(m.printMessage());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No message found with ID: " + searchID);
        }
        System.out.println();
    }

    //===================Option 4: Save to JSON=================================
    private static void saveMessagesToJson() {
        if (messageStore.isEmpty()) {
            System.out.println("\n  No messages to save.\n");
            return;
        }

        String filename = "messages.json";

        // Build JSON array using a StringBuilder
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < messageStore.size(); i++) {
            sb.append("  ").append(messageStore.get(i).toJson());
            if (i < messageStore.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]");

        // Write to file
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(sb.toString());
            System.out.println("\n" + messageStore.size()
                    + " message(s) saved to \"" + filename + "\".\n");
        } catch (IOException e) {
            System.out.println(" Failed to save file: " + e.getMessage() + "\n");
        }
    }
}
