package sn.architech.etatcivil.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void testSetLogin() {
        User user = new User();
        user.setLogin("Login");
        assertEquals(User_.LOGIN, user.getLogin());
    }

    @Test
    public void testEquals() {
        User user = new User();
        user.setLastName("Doe");
        user.setLogin("Login");
        user.setPassword("iloveyou");
        user.setEmail("jane.doe@example.org");
        user.setActivationKey("Activation Key");
        user.setLastModifiedDate(null);
        user.setFirstName("Jane");
        user.setCreatedDate(null);
        user.setActivated(true);
        user.setResetDate(null);
        user.setLangKey("Lang Key");
        user.setAuthorities(new HashSet<Authority>());
        user.setImageUrl("https://example.org/example");
        user.setId(123L);
        user.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setResetKey("Reset Key");
        assertFalse(user.equals(null));
    }

    @Test
    public void testEquals2() {
        User user = new User();
        user.setLastName("Doe");
        user.setLogin("Login");
        user.setPassword("iloveyou");
        user.setEmail("jane.doe@example.org");
        user.setActivationKey("Activation Key");
        user.setLastModifiedDate(null);
        user.setFirstName("Jane");
        user.setCreatedDate(null);
        user.setActivated(true);
        user.setResetDate(null);
        user.setLangKey("Lang Key");
        user.setAuthorities(new HashSet<Authority>());
        user.setImageUrl("https://example.org/example");
        user.setId(123L);
        user.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setResetKey("Reset Key");
        assertFalse(user.equals("Different type to User"));
    }

    @Test
    public void testEquals3() {
        User user = new User();
        user.setLastName("Doe");
        user.setLogin("Login");
        user.setPassword("iloveyou");
        user.setEmail("jane.doe@example.org");
        user.setActivationKey("Activation Key");
        user.setLastModifiedDate(null);
        user.setFirstName("Jane");
        user.setCreatedDate(null);
        user.setActivated(true);
        user.setResetDate(null);
        user.setLangKey("Lang Key");
        user.setAuthorities(new HashSet<Authority>());
        user.setImageUrl("https://example.org/example");
        user.setId(123L);
        user.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setResetKey("Reset Key");
        assertTrue(user.equals(user));
        int expectedHashCodeResult = user.hashCode();
        assertEquals(expectedHashCodeResult, user.hashCode());
    }

    @Test
    public void testEquals4() {
        User user = new User();
        user.setLastName("Doe");
        user.setLogin("Login");
        user.setPassword("iloveyou");
        user.setEmail("jane.doe@example.org");
        user.setActivationKey("Activation Key");
        user.setLastModifiedDate(null);
        user.setFirstName("Jane");
        user.setCreatedDate(null);
        user.setActivated(true);
        user.setResetDate(null);
        user.setLangKey("Lang Key");
        user.setAuthorities(new HashSet<Authority>());
        user.setImageUrl("https://example.org/example");
        user.setId(123L);
        user.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setResetKey("Reset Key");

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setLogin("Login");
        user1.setPassword("iloveyou");
        user1.setEmail("jane.doe@example.org");
        user1.setActivationKey("Activation Key");
        user1.setLastModifiedDate(null);
        user1.setFirstName("Jane");
        user1.setCreatedDate(null);
        user1.setActivated(true);
        user1.setResetDate(null);
        user1.setLangKey("Lang Key");
        user1.setAuthorities(new HashSet<Authority>());
        user1.setImageUrl("https://example.org/example");
        user1.setId(123L);
        user1.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user1.setResetKey("Reset Key");
        assertTrue(user.equals(user1));
        int expectedHashCodeResult = user.hashCode();
        assertEquals(expectedHashCodeResult, user1.hashCode());
    }

    @Test
    public void testConstructor() {
        User actualUser = new User();
        actualUser.setActivated(true);
        actualUser.setActivationKey("Activation Key");
        HashSet<Authority> authoritySet = new HashSet<Authority>();
        actualUser.setAuthorities(authoritySet);
        actualUser.setEmail("jane.doe@example.org");
        actualUser.setFirstName("Jane");
        actualUser.setId(123L);
        actualUser.setImageUrl("https://example.org/example");
        actualUser.setLangKey("Lang Key");
        actualUser.setLastName("Doe");
        actualUser.setPassword("iloveyou");
        actualUser.setResetDate(null);
        actualUser.setResetKey("Reset Key");
        assertEquals("Activation Key", actualUser.getActivationKey());
        assertSame(authoritySet, actualUser.getAuthorities());
        assertEquals("jane.doe@example.org", actualUser.getEmail());
        assertEquals("Jane", actualUser.getFirstName());
        assertEquals(123L, actualUser.getId().longValue());
        assertEquals("https://example.org/example", actualUser.getImageUrl());
        assertEquals("Lang Key", actualUser.getLangKey());
        assertEquals("Doe", actualUser.getLastName());
        assertNull(actualUser.getLogin());
        assertEquals("iloveyou", actualUser.getPassword());
        assertNull(actualUser.getResetDate());
        assertEquals("Reset Key", actualUser.getResetKey());
        assertTrue(actualUser.isActivated());
        assertEquals(
            "User{login='null', firstName='Jane', lastName='Doe', email='jane.doe@example.org', imageUrl='https:/"
                + "/example.org/example', activated='true', langKey='Lang Key', activationKey='Activation Key'}",
            actualUser.toString());
    }
}

