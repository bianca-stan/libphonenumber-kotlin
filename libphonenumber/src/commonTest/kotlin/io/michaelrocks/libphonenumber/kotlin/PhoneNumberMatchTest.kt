/*
 * Copyright (C) 2011 The Libphonenumber Authors
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
package io.michaelrocks.libphonenumber.kotlin

import io.michaelrocks.libphonenumber.kotlin.Phonenumber.PhoneNumber
import kotlin.test.*

/**
 * Tests for [PhoneNumberMatch].
 */
class PhoneNumberMatchTest {
    /**
     * Tests the value type semantics. Equality and hash code must be based on the covered range and
     * corresponding phone number. Range and number correctness are tested by
     * [PhoneNumberMatcherTest].
     */
    @Test
    fun testValueTypeSemantics() {
        val number = PhoneNumber()
        val match1 = PhoneNumberMatch(10, "1 800 234 45 67", number)
        val match2 = PhoneNumberMatch(10, "1 800 234 45 67", number)
        assertEquals(match1, match2)
        assertEquals(match1.hashCode(), match2.hashCode())
        assertEquals(match1.start(), match2.start())
        assertEquals(match1.end(), match2.end())
        assertEquals(match1.number(), match2.number())
        assertEquals(match1.rawString(), match2.rawString())
        assertEquals("1 800 234 45 67", match1.rawString())
    }

    /**
     * Tests the value type semantics for matches with a null number.
     */
    @Throws(Exception::class)
    fun testIllegalArguments() {
        try {
            PhoneNumberMatch(-110, "1 800 234 45 67", PhoneNumber())
            fail()
        } catch (e: IllegalArgumentException) { /* success */
        }
        try {
            PhoneNumberMatch(10, "1 800 234 45 67", null)
            fail()
        } catch (e: NullPointerException) { /* success */
        }
        try {
            PhoneNumberMatch(10, null, PhoneNumber())
            fail()
        } catch (e: NullPointerException) { /* success */
        }
        try {
            PhoneNumberMatch(10, null, null)
            fail()
        } catch (e: NullPointerException) { /* success */
        }
    }
}
