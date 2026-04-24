package com.test;


import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {
    @BeforeMethod
    public void beforeMethod(Method m ){
        System.out.println("methodName"+ m.getName());
        System.out.println("Thread Id"+ Thread.currentThread().getId());

    }
}
