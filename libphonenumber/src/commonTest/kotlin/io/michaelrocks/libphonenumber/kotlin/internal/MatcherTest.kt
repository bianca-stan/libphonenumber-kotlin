/*
 * Copyright (C) 2017 The Libphonenumber Authors
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
package io.michaelrocks.libphonenumber.kotlin.internal

import io.michaelrocks.libphonenumber.kotlin.Phonemetadata.PhoneNumberDesc
import io.michaelrocks.libphonenumber.kotlin.Phonemetadata.PhoneNumberDesc.Companion.newBuilder
import io.michaelrocks.libphonenumber.kotlin.internal.RegexBasedMatcher.Companion.create
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests that all implementations of `MatcherApi` are consistent.
 */
class MatcherTest {

    @Test
    fun testRegexBasedMatcher() {
        checkMatcherBehavesAsExpected(create())
    }

    private fun checkMatcherBehavesAsExpected(matcher: MatcherApi) {
        var desc = createDesc("")
        // Test if there is no matcher data.
        assertInvalid(matcher, "1", desc)
        desc = createDesc("9\\d{2}")
        assertInvalid(matcher, "91", desc)
        assertInvalid(matcher, "81", desc)
        assertMatched(matcher, "911", desc)
        assertInvalid(matcher, "811", desc)
        assertTooLong(matcher, "9111", desc)
        assertInvalid(matcher, "8111", desc)
        desc = createDesc("\\d{1,2}")
        assertMatched(matcher, "2", desc)
        assertMatched(matcher, "20", desc)
        desc = createDesc("20?")
        assertMatched(matcher, "2", desc)
        assertMatched(matcher, "20", desc)
        desc = createDesc("2|20")
        assertMatched(matcher, "2", desc)
        // Subtle case where lookingAt() and matches() result in different end()s.
        assertMatched(matcher, "20", desc)
    }

    // Helper method to set national number fields in the PhoneNumberDesc proto. Empty fields won't be
    // set.
    private fun createDesc(nationalNumberPattern: String): PhoneNumberDesc {
        val desc = newBuilder()
        if (nationalNumberPattern.isNotEmpty()) {
            desc.setNationalNumberPattern(nationalNumberPattern)
        }
        return desc.build()
    }

    private fun assertMatched(matcher: MatcherApi, number: String, desc: PhoneNumberDesc) {
        assertTrue(
            matcher.matchNationalNumber(number, desc, false),
            "$number should have matched ${toString(desc)}."
        )
        assertTrue(
            matcher.matchNationalNumber(number, desc, true),
            "$number should have matched ${toString(desc)}."
        )
    }

    private fun assertInvalid(matcher: MatcherApi, number: String, desc: PhoneNumberDesc) {
        assertFalse(
            matcher.matchNationalNumber(number, desc, false),
            "$number should not have matched ${toString(desc)}."
        )
        assertFalse(
            matcher.matchNationalNumber(number, desc, true),
            "$number should not have matched ${toString(desc)}."
        )
    }

    private fun assertTooLong(matcher: MatcherApi, number: String, desc: PhoneNumberDesc) {
        assertFalse(
            matcher.matchNationalNumber(number, desc, false),
            "$number should have been too long for ${toString(desc)}."
        )
        assertTrue(
            matcher.matchNationalNumber(number, desc, true),
            "$number should have been too long for ${toString(desc)}."
        )
    }

    private fun toString(desc: PhoneNumberDesc): String {
        val strBuilder = StringBuilder("pattern: ")
        if (desc.hasNationalNumberPattern()) {
            strBuilder.append(desc.nationalNumberPattern)
        } else {
            strBuilder.append("none")
        }
        return strBuilder.toString()
    }
}
