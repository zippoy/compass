package org.compass.core.lucene.engine.transaction.serializable;

import java.io.IOException;

import org.compass.core.engine.SearchEngineException;
import org.compass.core.lucene.engine.transaction.readcommitted.ReadCommittedTransaction;

/**
 * @author kimchy
 */
public class SerializableTransaction extends ReadCommittedTransaction {

    public void begin() throws SearchEngineException {
        super.begin();
        for (String subIndex : indexManager.getStore().getSubIndexes()) {
            try {
                openIndexWriterIfNeeded(subIndex);
            } catch (IOException e) {
                throw new SearchEngineException("Failed to open index writer for sub index [" + subIndex + "]", e);
            }
        }
    }
}
