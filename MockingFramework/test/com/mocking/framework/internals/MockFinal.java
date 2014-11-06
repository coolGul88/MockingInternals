package com.mocking.framework.internals;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MockFinal {
    Class<?>[] value();
}