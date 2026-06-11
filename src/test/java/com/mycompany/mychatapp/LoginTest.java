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
public class LoginTest {
      Login login = new Login();

    // ============================================================
    // USERNAME TESTS
    // ============================================================

    @Test
    public void testValidUsername() {
        assertTrue(login.checkUserName("ab_cd"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(login.checkUserName("abcdef")); // no underscore + too long
    }

    // ============================================================
    // PASSWORD TESTS
    // ============================================================

    @Test
    public void testValidPassword() {
        assertTrue(login.checkPasswordComplexity("Abc@1234"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(login.checkPasswordComplexity("password")); // no caps, number, special
    }

    // ============================================================
    // CELL PHONE TESTS
    // ============================================================

    @Test
    public void testValidCellPhone() {
        assertTrue(login.checkCellPhoneNumber("+27783091277"));
    }

    @Test
    public void testInvalidCellPhone() {
        assertFalse(login.checkCellPhoneNumber("0783091277")); // no international code
    }

    // ============================================================
    // REGISTER USER TESTS (IMPORTANT FIX HERE)
    // ============================================================

    @Test
    public void testRegisterUserSuccess() {
        String result = login.registerUser("ab_cd", "Abc@1234");
        assertEquals("The two above conditions have been met, and the user has been registered successfully.", result);
    }

    @Test
    public void testRegisterUserInvalidUsername() {
        String result = login.registerUser("abcd", "Abc@1234");
        assertEquals("The username is incorrectly formatted.", result);
    }

    @Test
    public void testRegisterUserInvalidPassword() {
        String result = login.registerUser("ab_cd", "password");
        assertEquals("The password does not meet the complexity requirements.", result);
    }

    // ============================================================
    // LOGIN TESTS
    // ============================================================

    @Test
    public void testLoginSuccess() {
        login.registerUser("ab_cd", "Abc@1234");
        assertTrue(login.loginUser("ab_cd", "Abc@1234"));
    }

    @Test
    public void testLoginFail() {
        login.registerUser("ab_cd", "Abc@1234");
        assertFalse(login.loginUser("ab_cd", "WrongPass1!"));
    }

    // ============================================================
    // LOGIN STATUS TEST
    // ============================================================

    @Test
    public void testReturnLoginStatusSuccess() {
        login.registerUser("ab_cd", "Abc@1234");
        login.setFirstName("John");
        login.setLastName("Doe");

        String message = login.returnLoginStatus("ab_cd", "Abc@1234");

        assertTrue(message.contains("Welcome John, Doe"));
    }

    @Test
    public void testReturnLoginStatusFail() {
        login.registerUser("ab_cd", "Abc@1234");

        String message = login.returnLoginStatus("ab_cd", "wrong");

        assertEquals("Username or password incorrect, please try again.", message);
    }
   
}
