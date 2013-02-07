package org.jboss.aerogear.controller.demo.spi;

import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.idm.model.SimpleUser;

public class AeroGearAuthenticator extends BaseAuthenticator {

    @Override
    public void authenticate() {
        setUser(new SimpleUser("john"));
        setStatus(AuthenticationStatus.SUCCESS);
    }
}
