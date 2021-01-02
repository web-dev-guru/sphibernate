package com.demo.controller;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    @Test(expected=IllegalArgumentException.class)
 public void checkConstructor(){
        Throwable exception = assertThrows(
                IllegalArgumentException.class, () -> {

                }
        );

        assertEquals("Username cannot be blank", exception.getMessage());

 }
}