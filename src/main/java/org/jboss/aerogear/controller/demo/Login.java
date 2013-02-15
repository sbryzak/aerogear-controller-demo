/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.aerogear.controller.demo;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.aerogear.controller.demo.model.AeroGearUser;
import org.jboss.aerogear.security.otp.api.Base32;
import org.picketlink.Identity;
import org.picketlink.Identity.AuthenticationResult;
import org.picketlink.credential.internal.DefaultLoginCredentials;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.Attribute;
import org.picketlink.idm.model.User;

@Stateless
public class Login {

    private static final Logger LOGGER = Logger.getLogger(Login.class.getSimpleName());
    private static final String IDM_SECRET_ATTRIBUTE = "serial";

    @Inject
    private Identity identity;

    @Inject
    private DefaultLoginCredentials credentials;

    @Inject
    private IdentityManager identityManager;

    public void index() {
        LOGGER.info("Login page!");
    }

    public AeroGearUser login(AeroGearUser user) {
        credentials.setUserId(user.getUsername());
        credentials.setCredential(new Password(user.getPassword()));
        if (AuthenticationResult.SUCCESS == identity.login()) {

            updateAttributes();

            return user;
        } else {
            throw new RuntimeException("Authentication failed!");
        }
        
    }

    /**
     * Update custom attributes to alread logged in user
     * On AeroGear we make use of this feature to store OTP secrets for example
     */
    private void updateAttributes() {
        if (identity.isLoggedIn()) {

            User picketLinkUser = identity.getUser();

            Attribute<String> secret = picketLinkUser.getAttribute(IDM_SECRET_ATTRIBUTE);

            if (secret == null) {
                secret = new Attribute<String>(IDM_SECRET_ATTRIBUTE, Base32.random());
                picketLinkUser.setAttribute(secret);
                this.identityManager.update(picketLinkUser);
            }
        } else {
            throw new RuntimeException("Can't update attributes!");
        }
    }

    public void logout() {
        LOGGER.info("User logout!");
        identity.logout();
    }
}
