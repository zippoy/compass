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

package org.compass.core.json.impl.converter;

import org.compass.core.converter.ConversionException;
import org.compass.core.converter.json.JsonContentConverter;
import org.compass.core.json.AliasedJsonObject;
import org.compass.core.json.JsonObject;
import org.compass.core.json.impl.AliasedJSONObject;
import org.compass.core.json.impl.JSONTokener;

/**
 * @author kimchy
 */
public class JSONContentConverterImpl implements JsonContentConverter {

    public String toJSON(JsonObject jsonObject) throws ConversionException {
        return jsonObject.toString();
    }

    public AliasedJsonObject fromJSON(String alias, String json) throws ConversionException {
        return new AliasedJSONObject(alias, new JSONTokener(json));
    }
}