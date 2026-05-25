/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.util.regex.Pattern;

/**
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
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public String registerUserName(String username) {
        if (checkUserName(username)) {
            this.username = username;
            return "Username successfully captured.";
        } else {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
    }

    // ================= PASSWORD =================
    public boolean checkPasswordComplexity(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";
        return Pattern.matches(regex, password);
    }

    public String registerPassword(String password) {
        if (checkPasswordComplexity(password)) {
            this.password = password;
            return "Password successfully captured.";
        } else {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
    }

    // ================= CELL PHONE =================
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        // Must start with + and include country code, total digits after + max 10
        String regex = "^\\+\\d{1,3}\\d{1,10}$";
        return Pattern.matches(regex, cellPhoneNumber);
    }

    public String registerCellPhoneNumber(String cellPhoneNumber) {
        if (checkCellPhoneNumber(cellPhoneNumber)) {
            this.cellPhoneNumber = cellPhoneNumber;
            return "Cell phone number successfully added.";
        } else {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
    }

    // ================= REGISTER USER (REQUIRED METHOD) =================
    public String registerUser(String username, String password) {

        if (!checkUserName(username)) {
            return "The username is incorrectly formatted.";
        }

        if (!checkPasswordComplexity(password)) {
            return "The password does not meet the complexity requirements.";
        }

        // Store values only if both are correct
        this.username = username;
        this.password = password;

        return "The two above conditions have been met, and the user has been registered successfully.";
    }

    // ================= USER DETAILS =================
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // ================= LOGIN =================
    public boolean loginUser(String username, String password) {
        return this.username != null &&
               this.password != null &&
               this.username.equals(username) &&
               this.password.equals(password);
    }

    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    String returningLoginStatus(boolean loggedIn) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void registerUser() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
