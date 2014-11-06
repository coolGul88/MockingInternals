package com.mocking.framework.test;

import com.mocking.framework.examples.FinalModifierClass;
import com.mocking.framework.examples.HelloClass;
import com.mocking.framework.examples.NativeModifierClass;
import com.mocking.framework.internals.MockFinal;
import com.mocking.framework.internals.PreMockJUnit4ClassRunner;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;


@MockFinal({FinalModifierClass.class, HelloClass.class, NativeModifierClass.class})
@RunWith(PreMockJUnit4ClassRunner.class)
public class ExampleTest {

    @Mock
    FinalModifierClass finalModifierClass;

    @Mock
    HelloClass helloClass;

    @Mock
    NativeModifierClass nativeModifierClass;

    @Test
    public void testIt() {
        Mockito.when(finalModifierClass.finalMethod()).thenReturn("mock result from final method");
        Mockito.when(nativeModifierClass.nativeMethod()).thenReturn("mock result from native method");
        Mockito.when(helloClass.method()).thenReturn("PreMock does not get in the way");

        Assert.assertEquals("mock result from final method", finalModifierClass.finalMethod());
        Assert.assertEquals("mock result from native method", nativeModifierClass.nativeMethod());
        Assert.assertEquals("PreMock does not get in the way", helloClass.method());
    }
}