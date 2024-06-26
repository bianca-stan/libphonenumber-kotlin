/*
 * Copyright (C) 2022 The Libphonenumber Authors
 * Copyright (C) 2022 Michael Rozumyanskiy
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
package io.michaelrocks.libphonenumber.kotlin.metadata.source

import io.michaelrocks.libphonenumber.kotlin.Phonemetadata.PhoneMetadata

/**
 * A source of phone metadata for non-geographical entities.
 *
 *
 * Non-geographical entities are phone number ranges that have a country calling code, but either
 * do not belong to an actual country (some international services), or belong to a region which has
 * a different country calling code from the country it is part of. Examples of such ranges are
 * those starting with:
 *
 *
 *  * 800 - country code assigned to the Universal International Freephone Service
 *  * 808 - country code assigned to the International Shared Cost Service
 *  * 870 - country code assigned to the Pitcairn Islands
 *  * ...
 *
 */
interface NonGeographicalEntityMetadataSource {
    /**
     * Gets phone metadata for a non-geographical entity.
     *
     * @param countryCallingCode the country calling code.
     * @return the phone metadata for that entity, or null if there is none.
     * @throws IllegalArgumentException if provided `countryCallingCode` does not belong to a
     * non-geographical entity
     */
    fun getMetadataForNonGeographicalRegion(countryCallingCode: Int): PhoneMetadata?
}
