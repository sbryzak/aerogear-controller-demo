package org.jboss.aerogear.controller.demo.spi;

import javax.inject.Inject;

import org.picketlink.Identity;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.internal.DefaultLoginCredentials;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Credentials;
import org.picketlink.idm.credential.internal.Password;
import org.picketlink.idm.credential.internal.UsernamePasswordCredentials;
import org.picketlink.idm.model.SimpleUser;
import org.picketlink.idm.model.User;

public class AeroGearAuthenticator extends BaseAuthenticator {
    @Inject @Aerogear IdentityManager identityManager;
    @Inject DefaultLoginCredentials credentials;
    
    @Override
    public void authenticate() {
        if (credentials.getPassword() == null) {
            setStatus(AuthenticationStatus.FAILURE);
            return;
        }
        
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(credentials.getUserId(), 
                (Password) credentials.getCredential());
        identityManager.validateCredentials(creds);
        
        
        if (Credentials.Status.VALID.equals(creds.getStatus())) {
            setStatus(AuthenticationStatus.SUCCESS);
            setUser((User) credentials.getValidatedAgent());
        } else {
            setStatus(AuthenticationStatus.FAILURE);
        }
        
        
        
        
    }
}
