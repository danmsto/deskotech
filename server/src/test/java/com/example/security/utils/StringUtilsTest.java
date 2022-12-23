package com.example.security.utils;

import org.junit.jupiter.api.Test;

import static com.example.security.utils.StringUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void isNullReturnsTrue() {
        assertTrue(isNullOrEmpty(null));
    }

    @Test
    void isEmptyReturnsTrue() {
        assertTrue(isNullOrEmpty(""));
    }

    @Test
    void isNotEmptyReturnsFalse() {
        assertFalse(isNullOrEmpty("I have some length"));
    }

}