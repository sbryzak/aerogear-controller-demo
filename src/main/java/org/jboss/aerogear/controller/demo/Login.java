package org.jboss.aerogear.controller.demo;

import org.jboss.aerogear.controller.demo.model.AeroGearUser;

import javax.ejb.Stateless;

@Stateless
public class Login {

    public void index() {
        System.out.println("Login page!");
    }

    public AeroGearUser login(AeroGearUser user) {

        return user;
    }

    public void logout() {
        System.out.println("User logout!");
    }
}
