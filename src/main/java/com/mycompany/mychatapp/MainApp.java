/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.util.Scanner;

/**
 *
 * @author os828
 */
public class MainApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== Welcome to QuickChat Registration ===\n");

        // ── Collect first and last name ──────────────────────────────────────
        System.out.print("Enter first name: ");
        login.setFirstName(input.nextLine());

        System.out.print("Enter last name: ");
        login.setLastName(input.nextLine());

        // ── Username loop (repeat until valid) ───────────────────────────────
        String username;
        do {
            System.out.print("Enter username (must contain _ and be ≤5 chars): ");
            username = input.nextLine();
            System.out.println(login.registerUserName(username));
        } while (!login.checkUserName(username));

        // ── Password loop (repeat until valid) ───────────────────────────────
        String password;
        do {
            System.out.print("Enter password (8+ chars, uppercase, digit, special): ");
            password = input.nextLine();
            System.out.println(login.registerPassword(password));
        } while (!login.checkPasswordComplexity(password));

        // ── Cell phone loop (repeat until valid) ─────────────────────────────
        String cell;
        do {
            System.out.print("Enter cell phone number (with international code e.g. +27...): ");
            cell = input.nextLine();
            System.out.println(login.registerCellPhoneNumber(cell));
        } while (!login.checkCellPhoneNumber(cell));

        // ── Login section ─────────────────────────────────────────────────────
        System.out.println("\n=== Login ===");

        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter username: ");
            String loginUser = input.nextLine();

            System.out.print("Enter password: ");
            String loginPass = input.nextLine();

            String status = login.returnLoginStatus(loginUser, loginPass);
            System.out.println(status);

            if (login.loginUser(loginUser, loginPass)) {
                loggedIn = true;
            }
        }

        // ── Part 3: Load previously stored messages from JSON ─────────────────
        Message.loadStoredMessages();

        input.close();

        // ── Hand off to the main chat menu ────────────────────────────────────
    
    }
}
