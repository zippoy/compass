/*
 * Copyright 2004-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.compass.gps.device.jpa.embedded.hibernate;

import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.compass.core.CompassSession;
import org.compass.core.CompassTransaction;
import org.compass.gps.device.jpa.AbstractSimpleJpaGpsDeviceTests;
import org.compass.gps.device.jpa.model.Simple;
import org.hibernate.ejb.HibernatePersistence;

/**
 * Performs JPA tests using Hibernate specific support.
 *
 * @author kimchy
 */
public class EmbeddedHibernateSimpleJpaGpsDeviceTests extends AbstractSimpleJpaGpsDeviceTests {

    protected void setUpCompass() {
        compass = HibernateJpaHelper.getCompass(entityManagerFactory);
        assertNotNull(compass);
    }

    protected void setUpGps() {
        compassGps = HibernateJpaHelper.getCompassGps(entityManagerFactory);
        assertNotNull(compass);
    }

    protected EntityManagerFactory doSetUpEntityManagerFactory() {
        return new HibernatePersistence().createEntityManagerFactory("embeddedhibernate", new HashMap());
    }

    public void testRollbackTransaction() throws Exception {
        compassGps.index();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        // insert a new one
        Simple simple = new Simple();
        simple.setId(4);
        simple.setValue("value4");
        entityManager.persist(simple);

        entityTransaction.rollback();
        entityManager.close();

        CompassSession sess = compass.openSession();
        CompassTransaction tr = sess.beginTransaction();

        simple = sess.get(Simple.class, 4);
        assertNull(simple);

        tr.commit();
        sess.close();
    }

}