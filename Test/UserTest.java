/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import org.junit.Before;
import org.junit.Test;
import Backend.*;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        // Using an anonymous subclass to instantiate the abstract User class for testing
        user = new User("testUser", "password123", "John Doe", "Male", 30, "john.doe@example.com", LocalDate.of(1994, 5, 15)) {};
    }

    @Test
    public void testUsername() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testName() {
        assertEquals("John Doe", user.getName());
    }

    @Test
    public void testAge() {
        assertEquals(30, user.getAge());
    }

    @Test
    public void testEmail() {
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    public void testDOB() {
        assertEquals(LocalDate.of(1994, 5, 15), user.getDOB());
    }

    @Test
    public void testGender() {
        assertEquals("Male", user.getGender());
    }

    @Test
    public void testSetUsername() {
        user.setUsername("newUser");
        assertEquals("newUser", user.getUsername());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newPassword123");
        assertEquals("newPassword123", user.getPassword());
    }

    @Test
    public void testSetName() {
        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName());
    }

    @Test
    public void testSetAge() {
        user.setAge(25);
        assertEquals(25, user.getAge());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", user.getEmail());
    }

    @Test
    public void testSetDOB() {
        user.setDOB(LocalDate.of(1995, 7, 20));
        assertEquals(LocalDate.of(1995, 7, 20), user.getDOB());
    }

    @Test
    public void testSetGender() {
        user.setGender("Female");
        assertEquals("Female", user.getGender());
    }
}
