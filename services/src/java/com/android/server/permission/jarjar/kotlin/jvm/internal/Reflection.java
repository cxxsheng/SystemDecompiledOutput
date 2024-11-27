package com.android.server.permission.jarjar.kotlin.jvm.internal;

/* loaded from: classes2.dex */
public class Reflection {
    private static final com.android.server.permission.jarjar.kotlin.reflect.KClass[] EMPTY_K_CLASS_ARRAY;
    private static final com.android.server.permission.jarjar.kotlin.jvm.internal.ReflectionFactory factory;

    static {
        com.android.server.permission.jarjar.kotlin.jvm.internal.ReflectionFactory impl;
        try {
            java.lang.Class<?> implClass = java.lang.Class.forName("com.android.server.permission.jarjar.kotlin.reflect.jvm.internal.ReflectionFactoryImpl");
            impl = (com.android.server.permission.jarjar.kotlin.jvm.internal.ReflectionFactory) implClass.newInstance();
        } catch (java.lang.ClassCastException e) {
            impl = null;
        } catch (java.lang.ClassNotFoundException e2) {
            impl = null;
        } catch (java.lang.IllegalAccessException e3) {
            impl = null;
        } catch (java.lang.InstantiationException e4) {
            impl = null;
        }
        factory = impl != null ? impl : new com.android.server.permission.jarjar.kotlin.jvm.internal.ReflectionFactory();
        EMPTY_K_CLASS_ARRAY = new com.android.server.permission.jarjar.kotlin.reflect.KClass[0];
    }

    public static java.lang.String renderLambdaToString(com.android.server.permission.jarjar.kotlin.jvm.internal.Lambda lambda) {
        return factory.renderLambdaToString(lambda);
    }
}
