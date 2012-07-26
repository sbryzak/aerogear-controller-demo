package org.jboss.aerogear.controller.demo;

import org.jboss.aerogear.controller.demo.model.UserLogin;
import org.jboss.aerogear.security.idm.authentication.AuthenticatorManager;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class Login {

    @Inject
    private AuthenticatorManager authenticatorManager;

    public void index() {
        System.out.println("hello from security");
    }

    public UserLogin welcome(UserLogin userLogin) {

        authenticatorManager.login(userLogin.getUsername(), userLogin.getPassword());
        return userLogin;
    }

}
