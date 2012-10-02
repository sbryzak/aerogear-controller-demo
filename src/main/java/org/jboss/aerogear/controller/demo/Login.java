package org.jboss.aerogear.controller.demo;

import org.jboss.aerogear.controller.demo.model.User;
import org.jboss.aerogear.security.dsl.AuthenticationManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class Login {

    @Inject
    private AuthenticationManager authenticationManager;

    public void index() {
        System.out.println("Login page!");
    }

    public User login(User user) {

        System.out.println(user.getId());
        System.out.println(user.getPassword());

        boolean logged = authenticationManager.login(user.getId(), user.getPassword());
        System.out.println("Logged? " + logged);
        return user;
    }

    public void logout() {
        System.out.println("User logout!");
    }
}
