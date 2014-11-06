package com.mocking.dynamic.proxies;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

public class SubClassMocker {

    public static Object createMock(Class clazz) throws Exception {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(clazz);
        MethodHandler handler = new MethodHandler()
        {
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable
            {
                if(thisMethod.getName().equalsIgnoreCase("getDefaultArea"))
                    System.out.print("Handling this method :" + thisMethod);

                return proceed.invoke(self, args);
            }
        };
        return factory.create(new Class[0], new Object[0], handler);
    }
}
