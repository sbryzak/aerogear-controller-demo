package org.jboss.aerogear.controller.demo.spi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.picketlink.idm.DefaultIdentityCache;
import org.picketlink.idm.IdGenerator;
import org.picketlink.idm.IdentityCache;
import org.picketlink.idm.credential.internal.DefaultCredentialHandlerFactory;
import org.picketlink.idm.credential.spi.CredentialHandlerFactory;
import org.picketlink.idm.event.EventBridge;
import org.picketlink.idm.internal.DefaultIdGenerator;
import org.picketlink.idm.internal.DefaultIdentityStoreInvocationContextFactory;
import org.picketlink.idm.jpa.internal.JPAIdentityStore;
import org.picketlink.idm.spi.IdentityStore;
import org.picketlink.idm.spi.IdentityStoreInvocationContext;
import org.picketlink.idm.spi.IdentityStoreInvocationContextFactory;
import org.picketlink.idm.spi.PartitionStore;

public class AerogearInvocationContextFactory implements IdentityStoreInvocationContextFactory {

    private EventBridge eventBridge;
    private CredentialHandlerFactory credentialHandlerFactory;
    private IdentityCache identityCache;
    private IdGenerator idGenerator;

    // FIXME Bad!! we can't do this, this class is multi-threaded!
    private EntityManager entityManager;

    public static DefaultIdentityStoreInvocationContextFactory DEFAULT = new DefaultIdentityStoreInvocationContextFactory(null, new DefaultCredentialHandlerFactory());

    public AerogearInvocationContextFactory(EntityManager entityManager){
        this.entityManager = entityManager;

        this.eventBridge = new EventBridge() {

            @Override
            public void raiseEvent(Object event) {
                // by default do nothing
            }
        };
        this.credentialHandlerFactory = new DefaultCredentialHandlerFactory();
        this.identityCache = new DefaultIdentityCache();
        this.idGenerator = new DefaultIdGenerator();
    }

    @Override
    public IdentityStoreInvocationContext createContext() {
        return new IdentityStoreInvocationContext(this.identityCache, eventBridge, credentialHandlerFactory, idGenerator);
    }

    @Override
    public void initContextForStore(IdentityStoreInvocationContext ctx, IdentityStore<?> store) {
        if (store instanceof JPAIdentityStore) {
            if (!ctx.isParameterSet(JPAIdentityStore.INVOCATION_CTX_ENTITY_MANAGER)) {
                ctx.setParameter(JPAIdentityStore.INVOCATION_CTX_ENTITY_MANAGER, getEntityManager());
            }
        }
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public void setEntityManager(EntityManager em){
        this.entityManager = em;
    }

    @Override
    public void initContextForStore(IdentityStoreInvocationContext ctx, PartitionStore store) {
        if (store instanceof JPAIdentityStore) {
            if (!ctx.isParameterSet(JPAIdentityStore.INVOCATION_CTX_ENTITY_MANAGER)) {
                ctx.setParameter(JPAIdentityStore.INVOCATION_CTX_ENTITY_MANAGER, getEntityManager());
            }
        }
    }

}
