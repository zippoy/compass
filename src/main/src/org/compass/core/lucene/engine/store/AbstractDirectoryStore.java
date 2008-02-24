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

package org.compass.core.lucene.engine.store;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.store.Directory;
import org.compass.core.engine.SearchEngine;
import org.compass.core.engine.SearchEngineException;
import org.compass.core.engine.event.SearchEngineEventManager;

/**
 * Base class for different {@link org.compass.core.lucene.engine.store.DirectoryStore} implementations. 
 *
 * @author kimchy
 */
public abstract class AbstractDirectoryStore implements DirectoryStore {

    protected final transient Log log = LogFactory.getLog(getClass());

    public void closeDirectory(Directory dir, String subContext, String subIndex) throws SearchEngineException {
        try {
            dir.close();
        } catch (IOException e) {
            log.warn("Failed to close direcrtory for sub context [" + subContext + "] and sub index [" + subIndex + "]");
        }
    }

    public Boolean indexExists(Directory dir) throws SearchEngineException {
        return null;
    }

    public void deleteIndex(Directory dir, String subContext, String subIndex) throws SearchEngineException {
    }

    public void cleanIndex(Directory dir, String subContext, String subIndex) throws SearchEngineException {
    }

    public void performScheduledTasks(Directory dir, String subContext, String subIndex) throws SearchEngineException {
    }

    public void registerEventListeners(SearchEngine searchEngine, SearchEngineEventManager eventManager) {
    }

    public CopyFromHolder beforeCopyFrom(String subContext, Directory[] dirs) throws SearchEngineException {
        return new CopyFromHolder();
    }

    public void afterSuccessfulCopyFrom(String subContext, CopyFromHolder holder) throws SearchEngineException {
    }

    public void afterFailedCopyFrom(String subContext, CopyFromHolder holder) throws SearchEngineException {
    }

    public void close() {
    }
}