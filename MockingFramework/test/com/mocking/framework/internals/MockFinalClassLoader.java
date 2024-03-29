package com.mocking.framework.internals;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class MockFinalClassLoader extends ClassLoader {

    private static Set<String> classes = Collections.emptySet();

    private ClassPool pool;

    public static void setClasses(Set<String> set) {
        classes = new HashSet<String>(set);
    }

    public MockFinalClassLoader(ClassLoader parent) {
        super(parent);
        pool = ClassPool.getDefault();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        // no mater what, do not allow certain classes to be loaded by this
        // class loader. change this as you see fit (and are able to).
        if (classes.contains(name)) {
            // only load the classes specified with the class loader,
            // otherwise leave it up to the parent.
            return findClass(name);
        } else {
            return super.loadClass(name);
        }
    }


    public Class<?> findClass(String name) throws ClassNotFoundException {

        try {
            CtClass cc = pool.get(name);

            // remove final modifier from the class
            if (Modifier.isFinal(cc.getModifiers())) {
                cc.setModifiers(Modifier.PUBLIC);
            }

            // remove final modifiers from all methods
            CtMethod[] methods = cc.getDeclaredMethods();
            for (CtMethod method : methods) {
                int modifiers = method.getModifiers();
                if (Modifier.isFinal(modifiers)) {
                    method.setModifiers(modifiers & ~Modifier.FINAL);
                }
            }

            byte[] b = cc.toBytecode();

            Class<?> result = defineClass(name, b, 0, b.length);

            return result;
        } catch (NotFoundException e) {
            throw new ClassNotFoundException();
        } catch (IOException e) {
            throw new ClassNotFoundException();
        } catch (CannotCompileException e) {
            throw new ClassNotFoundException();
        }
    }

}