package com.example.camel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
@XmlRootElement
public class Person {

    private int age;
    private boolean allowedToDrink;

    public boolean isAllowedToDrink() {
        return allowedToDrink;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAllowedToDrink(boolean allowedToDrink) {
        this.allowedToDrink = allowedToDrink;
    }
}
