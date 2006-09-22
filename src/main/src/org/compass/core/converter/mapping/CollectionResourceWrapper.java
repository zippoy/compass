/*
 * Copyright 2004-2006 the original author or authors.
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

package org.compass.core.converter.mapping;

import java.io.Reader;
import java.util.HashMap;

import org.compass.core.Property;
import org.compass.core.Resource;
import org.compass.core.converter.ConversionException;
import org.compass.core.engine.SearchEngineException;

/**
 * @author kimchy
 */
public class CollectionResourceWrapper implements Resource {

    public class PropertiesWrapper {

        public String name;

        public Property[] properties;

        public int counter;

        public int hashCode() {
            return name.hashCode();
        }

        public boolean equals(Object object) {
            PropertiesWrapper copy = (PropertiesWrapper) object;
            return name.equals(copy.name);
        }
    }

    private Resource resource;

    private HashMap propertiesMap = new HashMap();

    public CollectionResourceWrapper(Resource resource) {
        this.resource = resource;
    }

    public String get(String name) {
        return computeProperty(name).getStringValue();
    }

    public Object getObject(String name) {
        return computeProperty(name).getObjectValue();
    }

    public String[] getValues(String name) {
        return resource.getValues(name);
    }

    public String getAlias() {
        return resource.getAlias();
    }

    public String getId() {
        throw new ConversionException("should not be called");
    }

    public String[] getIds() {
        throw new ConversionException("should not be called");
    }

    public Property getIdProperty() {
        throw new ConversionException("should not be called");
    }

    public Property[] getIdProperties() {
        throw new ConversionException("should not be called");
    }

    public Resource addProperty(String name, Object value) throws SearchEngineException {
        throw new ConversionException("should not be called");
    }

    public Resource addProperty(String name, Reader value) throws SearchEngineException {
        throw new ConversionException("should not be called");
    }

    public Resource addProperty(Property property) {
        throw new ConversionException("should not be called");
    }

    public Resource removeProperty(String name) {
        throw new ConversionException("should not be called");
    }

    public Resource removeProperties(String name) {
        throw new ConversionException("should not be called");
    }

    public Property getProperty(String name) {
        return computeProperty(name);
    }

    private Property computeProperty(String name) {
        PropertiesWrapper wrapper = (PropertiesWrapper) propertiesMap.get(name);
        if (wrapper == null) {
            wrapper = new PropertiesWrapper();
            wrapper.name = name;
            wrapper.properties = getProperties(name);
            propertiesMap.put(name, wrapper);
        }

        if (wrapper.properties.length == 0) {
            return null;
        }

        if (wrapper.counter >= wrapper.properties.length) {
            // we are asking for data that was not marshalled, return null
            return null;
        }
        return wrapper.properties[wrapper.counter++];
    }

    public void rollbackGetProperty(String name) {
        PropertiesWrapper wrapper = (PropertiesWrapper) propertiesMap.get(name);
        if (wrapper == null) {
            return;
        }
        if (wrapper.properties.length == 0) {
            return;
        }
        wrapper.counter--;
    }

    public Property[] getProperties(String name) {
        return resource.getProperties(name);
    }

    public Property[] getProperties() {
        return null;
    }

    public float getBoost() {
        return resource.getBoost();
    }

    public Resource setBoost(float boost) {
        resource.setBoost(boost);
        return this;
    }

    public void copy(Resource resource) {
        this.resource.copy(resource);
    }

}
