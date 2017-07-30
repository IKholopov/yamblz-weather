package com.example.toor.yamblzweather.common;

import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class AnswerWithSelf implements Answer<Object> {
    private final Answer<Object> delegate = new ReturnsEmptyValues();
    private final Class<?> selfClass;

    public AnswerWithSelf(Class<?> selfClass) {
        this.selfClass = selfClass;
    }

    public Object answer(InvocationOnMock invocation) throws Throwable {
        Class<?> returnType = invocation.getMethod().getReturnType();
        if (returnType == selfClass) {
            return invocation.getMock();
        } else {
            return delegate.answer(invocation);
        }
    }
}
