package org.jboss.aerogear.controller.demo;

import org.jboss.aerogear.controller.demo.model.AeroGearUser;

import javax.ejb.Stateless;

@Stateless
public class Register {

    public static final String DEFAULT_ROLE = "admin";

    public void index() {
        System.out.println("Login page!");
    }

    public AeroGearUser register(AeroGearUser user) {
        return user;
    }
}
