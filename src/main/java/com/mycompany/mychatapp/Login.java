/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.util.regex.Pattern;

/**
 * Login class handles user registration and authentication for QuickChat.
 * Covers username validation, password complexity, cell number format,
 * and login verification.
 *
 * @author os828
 */
public class Login {

    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    // ================= USERNAME =================

    /**
     * Checks that the username contains an underscore and is at most 5 characters.
     *
     * @param username the username to validate
     * @return true if valid, false otherwise
     */
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    /**
     * Registers the username if it passes validation.
     *
     * @param username the username to register
     * @return success or failure message
     */
    public String registerUserName(String username) {
        if (checkUserName(username)) {
            this.username = username;
            return "Username successfully captured.";
        } else {
            return "Username is not correctly formatted; please ensure that your username contains "
                 + "an underscore and is no more than five characters in length.";
        }
    }

    // ================= PASSWORD =================

    /**
     * Checks that the password is at least 8 characters with one uppercase letter,
     * one digit, and one special character.
     *
     * @param password the password to validate
     * @return true if valid, false otherwise
     */
    public boolean checkPasswordComplexity(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";
        return Pattern.matches(regex, password);
    }

    /**
     * Registers the password if it meets complexity requirements.
     *
     * @param password the password to register
     * @return success or failure message
     */
    public String registerPassword(String password) {
        if (checkPasswordComplexity(password)) {
            this.password = password;
            return "Password successfully captured.";
        } else {
            return "Password is not correctly formatted; please ensure that the password contains "
                 + "at least eight characters, a capital letter, a number, and a special character.";
        }
    }

    // ================= CELL PHONE =================

    /**
     * Checks that the cell number starts with + and has a valid international format.
     *
     * @param cellPhoneNumber the cell number to validate
     * @return true if valid, false otherwise
     */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        String regex = "^\\+\\d{1,3}\\d{1,10}$";
        return Pattern.matches(regex, cellPhoneNumber);
    }

    /**
     * Registers the cell phone number if it passes validation.
     *
     * @param cellPhoneNumber the cell number to register
     * @return success or failure message
     */
    public String registerCellPhoneNumber(String cellPhoneNumber) {
        if (checkCellPhoneNumber(cellPhoneNumber)) {
            this.cellPhoneNumber = cellPhoneNumber;
            return "Cell phone number successfully added.";
        } else {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
    }

    // ================= REGISTER USER =================

    /**
     * Registers a user by validating both username and password together.
     *
     * @param username the chosen username
     * @param password the chosen password
     * @return a message indicating success or which field failed
     */
    public String registerUser(String username, String password) {
        if (!checkUserName(username)) {
            return "The username is incorrectly formatted.";
        }
        if (!checkPasswordComplexity(password)) {
            return "The password does not meet the complexity requirements.";
        }
        this.username = username;
        this.password = password;
        return "The two above conditions have been met, and the user has been registered successfully.";
    }

    // ================= USER DETAILS =================

    /**
     * Sets the user's first name.
     *
     * @param firstName the first name to store
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName the last name to store
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the user's first name.
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the user's last name.
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    // ================= LOGIN =================

    /**
     * Checks credentials against stored username and password.
     *
     * @param username the username entered at login
     * @param password the password entered at login
     * @return true if credentials match, false otherwise
     */
    public boolean loginUser(String username, String password) {
        return this.username != null
            && this.password != null
            && this.username.equals(username)
            && this.password.equals(password);
    }

    /**
     * Returns a welcome message on successful login or an error on failure.
     *
     * @param username the username entered at login
     * @param password the password entered at login
     * @return login status message
     */
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
