/*
package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest{

    @Test
    void shouldAuthenticateUserWithCorrectLoginAndPassword() {
        IUserRepository userRepository = new JsonUserRepository();
        Authentication authentication = new Authentication(userRepository);

        User user = authentication.login("admin", "admin123"); // zmieniona nazwa na login

        assertNotNull(user);
        assertEquals("admin", user.getLogin());
    }

    @Test
    void shouldNotAuthenticateUserWithWrongPassword() {
        IUserRepository userRepository = new JsonUserRepository();
        Authentication authentication = new Authentication(userRepository);

        User user = authentication.login("admin", "zlehaslo");

        assertNull(user);
    }

    @Test
    void shouldNotAuthenticateNonExistingUser() {
        IUserRepository userRepository = new JsonUserRepository();
        Authentication authentication = new Authentication(userRepository);

        User user = authentication.login("brak", "admin123");

        assertNull(user);
    }

    @Test
    void hashPasswordShouldReturnSameHashForSameInput() {
        String hash1 = Authentication.hashPassword("admin123");
        String hash2 = Authentication.hashPassword("admin123");

        assertEquals(hash1, hash2);
    }
}
*/