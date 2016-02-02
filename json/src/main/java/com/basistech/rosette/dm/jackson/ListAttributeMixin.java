/*
* Copyright 2014 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.basistech.rosette.dm.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * {@link com.basistech.rosette.dm.ListAttribute}.
 */
@JsonSerialize(using = ListAttributeSerializer.class)
@JsonDeserialize(using = ListAttributeDeserializer.class)
public abstract class ListAttributeMixin {

    // this is only used by the serializer, never let it get processed automatically.
    @JsonIgnore
    abstract String getItemJsonKey();

    @JsonIgnore
    public abstract boolean isEmpty();

}
