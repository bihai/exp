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

package org.jetbrains.kotlin.idea.util.psiModificationUtil

import com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.calls.callUtil.getResolvedCall
import org.jetbrains.kotlin.resolve.calls.callUtil.getValueArgumentsInParentheses
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch

fun JetFunctionLiteralArgument.moveInsideParentheses(bindingContext: BindingContext): JetCallExpression {
    return moveInsideParenthesesAndReplaceWith(this.getArgumentExpression(), bindingContext)
}

fun JetFunctionLiteralArgument.getFunctionLiteralArgumentName(bindingContext: BindingContext): String? {
    val callExpression = getParent() as JetCallExpression
    val resolvedCall = callExpression.getResolvedCall(bindingContext)
    return (resolvedCall?.getArgumentMapping(this) as? ArgumentMatch)?.valueParameter?.getName()?.toString()
}

fun JetFunctionLiteralArgument.moveInsideParenthesesAndReplaceWith(
        replacement: JetExpression,
        bindingContext: BindingContext
): JetCallExpression = moveInsideParenthesesAndReplaceWith(replacement, getFunctionLiteralArgumentName(bindingContext))

fun JetFunctionLiteralArgument.moveInsideParenthesesAndReplaceWith(
        replacement: JetExpression,
        functionLiteralArgumentName: String?
): JetCallExpression {
    val oldCallExpression = getParent() as JetCallExpression
    val newCallExpression = oldCallExpression.copy() as JetCallExpression

    val psiFactory = JetPsiFactory(getProject())
    val argument = if (newCallExpression.getValueArgumentsInParentheses().any { it.getArgumentName() != null }) {
        psiFactory.createArgument(replacement, functionLiteralArgumentName)
    }
    else {
        psiFactory.createArgument(replacement)
    }

    val functionLiteralArgument = newCallExpression.getFunctionLiteralArguments().firstOrNull()!!
    val valueArgumentList = newCallExpression.getValueArgumentList() ?: psiFactory.createCallArguments("()")

    valueArgumentList.addArgument(argument)

    (functionLiteralArgument.getPrevSibling() as? PsiWhiteSpace)?.delete()
    if (newCallExpression.getValueArgumentList() != null) {
        functionLiteralArgument.delete()
    }
    else {
        functionLiteralArgument.replace(valueArgumentList)
    }
    return oldCallExpression.replace(newCallExpression) as JetCallExpression
}

fun JetCallExpression.moveFunctionLiteralOutsideParentheses() {
    assert(getFunctionLiteralArguments().isEmpty())
    val argumentList = getValueArgumentList()!!
    val argument = argumentList.getArguments().last()
    val expression = argument.getArgumentExpression()!!
    assert(expression.unpackFunctionLiteral() != null)

    val dummyCall = JetPsiFactory(this).createExpressionByPattern("foo()$0:'{}'", expression) as JetCallExpression
    val functionLiteralArgument = dummyCall.getFunctionLiteralArguments().single()
    this.add(functionLiteralArgument)
    if (argumentList.getArguments().size() > 1) {
        argumentList.removeArgument(argument)
    }
    else {
        argumentList.delete()
    }
}