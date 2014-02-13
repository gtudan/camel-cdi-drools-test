package de.cofinpro.camel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
@XmlRootElement
public class Person {

    private int age;
    private boolean canDrink;

    public boolean canDrink() {
        return age > 16;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCanDrink(boolean canDrink) {
        this.canDrink = canDrink;
    }
}
