/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

/**
 *
 * @author os828
 */
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

       Scanner input = new Scanner(System.in);
        Login login = new Login();

        //Call exiting methods
        login.registerUser();
        boolean loggedIn= false;
        
        // Get user details
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        login.setFirstName(firstName);
        login.setLastName(lastName);

        // USERNAME LOOP
        String username;
        String usernameMessage;
        do {
            System.out.print("Enter username: ");
            username = input.nextLine();
            usernameMessage = login.registerUserName(username);
            System.out.println(usernameMessage);
        } while (!login.checkUserName(username));

        // PASSWORD LOOP
        String password;
        String passwordMessage;
        do {
            System.out.print("Enter password: ");
            password = input.nextLine();
            passwordMessage = login.registerPassword(password);
            System.out.println(passwordMessage);
        } while (!login.checkPasswordComplexity(password));

        // CELL PHONE LOOP
        String cell;
        String cellMessage;
        do {
            System.out.print("Enter cell phone number (with international code): ");
            cell = input.nextLine();
            cellMessage = login.registerCellPhoneNumber(cell);
            System.out.println(cellMessage);
        } while (!login.checkCellPhoneNumber(cell));

        // LOGIN SECTION
        System.out.println("\n--- LOGIN ---");

        System.out.print("Enter username: ");
        String loginUser = input.nextLine();

        System.out.print("Enter password: ");
        String loginPass = input.nextLine();

        System.out.println(login.returnLoginStatus(loginUser, loginPass));
        
        //Print out the correct login message
        String loginMessage = login.returningLoginStatus(loggedIn);
        System.out.println(loginMessage);  // capital 'M' fixed → was 'LoginMessage'
    input.close();
    }
}
