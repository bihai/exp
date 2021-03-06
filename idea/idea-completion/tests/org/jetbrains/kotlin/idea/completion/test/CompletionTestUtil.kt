/*
 * Copyright 2010-2015 JetBrains s.r.o.
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

package org.jetbrains.kotlin.idea.completion.test

import com.intellij.codeInsight.CodeInsightSettings
import com.intellij.codeInsight.lookup.LookupElement
import org.jetbrains.kotlin.idea.project.TargetPlatform
import org.jetbrains.kotlin.test.JetTestUtils
import org.junit.Assert

val COMPLETION_TEST_DATA_BASE_PATH: String
    get() = JetTestUtils.getHomeDirectory() + "/idea/idea-completion/testData"

fun testCompletion(fileText: String, platform: TargetPlatform?, complete: (Int) -> Array<LookupElement>?, defaultInvocationCount: Int = 0) {
    testWithAutoCompleteSetting(fileText) {
        val invocationCount = ExpectedCompletionUtils.getInvocationCount(fileText) ?: defaultInvocationCount
        val items = complete(invocationCount) ?: emptyArray()

        ExpectedCompletionUtils.assertDirectivesValid(fileText)

        val expected = ExpectedCompletionUtils.itemsShouldExist(fileText, platform)
        val unexpected = ExpectedCompletionUtils.itemsShouldAbsent(fileText, platform)
        val itemsNumber = ExpectedCompletionUtils.getExpectedNumber(fileText, platform)
        val nothingElse = ExpectedCompletionUtils.isNothingElseExpected(fileText)

        Assert.assertTrue("Should be some assertions about completion", expected.size() != 0 || unexpected.size() != 0 || itemsNumber != null || nothingElse)
        ExpectedCompletionUtils.assertContainsRenderedItems(expected, items, ExpectedCompletionUtils.isWithOrder(fileText), nothingElse)
        ExpectedCompletionUtils.assertNotContainsRenderedItems(unexpected, items)

        if (itemsNumber != null) {
            val expectedItems = ExpectedCompletionUtils.listToString(ExpectedCompletionUtils.getItemsInformation(items))
            Assert.assertEquals("Invalid number of completion items: ${expectedItems}", itemsNumber, items.size())
        }
    }
}

private fun testWithAutoCompleteSetting(fileText: String, doTest: () -> Unit) {
    val autoComplete = ExpectedCompletionUtils.getAutocompleteSetting(fileText) ?: false

    val settings = CodeInsightSettings.getInstance()
    val oldValue1 = settings.AUTOCOMPLETE_ON_CODE_COMPLETION
    val oldValue2 = settings.AUTOCOMPLETE_ON_SMART_TYPE_COMPLETION
    try {
        settings.AUTOCOMPLETE_ON_CODE_COMPLETION = autoComplete
        settings.AUTOCOMPLETE_ON_SMART_TYPE_COMPLETION = autoComplete
        doTest()
    }
    finally {
        settings.AUTOCOMPLETE_ON_CODE_COMPLETION = oldValue1
        settings.AUTOCOMPLETE_ON_SMART_TYPE_COMPLETION = oldValue2
    }
}
