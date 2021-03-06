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

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.testFramework.LightProjectDescriptor
import org.jetbrains.kotlin.idea.test.JdkAndMockLibraryProjectDescriptor
import org.jetbrains.kotlin.idea.test.PluginTestCaseBase
import org.jetbrains.kotlin.idea.project.TargetPlatform

public abstract class AbstractCompiledKotlinInJavaCompletionTest : JetFixtureCompletionBaseTestCase() {
    override fun getPlatform() = TargetPlatform.JVM

    override fun getProjectDescriptor() = JdkAndMockLibraryProjectDescriptor(COMPLETION_TEST_DATA_BASE_PATH + "/injava/mockLib", false)

    override fun complete(invocationCount: Int) = myFixture.complete(CompletionType.BASIC, invocationCount)
}
