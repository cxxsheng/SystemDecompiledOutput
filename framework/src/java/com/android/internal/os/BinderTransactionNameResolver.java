package com.android.internal.os;

/* loaded from: classes4.dex */
public class BinderTransactionNameResolver {
    private static final java.lang.reflect.Method NO_GET_DEFAULT_TRANSACTION_NAME_METHOD;
    private final java.util.HashMap<java.lang.Class<? extends android.os.Binder>, java.lang.reflect.Method> mGetDefaultTransactionNameMethods = new java.util.HashMap<>();

    public static java.lang.String noDefaultTransactionName(int i) {
        return java.lang.String.valueOf(i);
    }

    static {
        try {
            NO_GET_DEFAULT_TRANSACTION_NAME_METHOD = com.android.internal.os.BinderTransactionNameResolver.class.getMethod("noDefaultTransactionName", java.lang.Integer.TYPE);
        } catch (java.lang.NoSuchMethodException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public java.lang.String getMethodName(java.lang.Class<? extends android.os.Binder> cls, int i) {
        java.lang.reflect.Method method = this.mGetDefaultTransactionNameMethods.get(cls);
        if (method == null) {
            try {
                method = cls.getMethod("getDefaultTransactionName", java.lang.Integer.TYPE);
            } catch (java.lang.NoSuchMethodException e) {
                method = NO_GET_DEFAULT_TRANSACTION_NAME_METHOD;
            }
            if (method.getReturnType() != java.lang.String.class || !java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
                method = NO_GET_DEFAULT_TRANSACTION_NAME_METHOD;
            }
            this.mGetDefaultTransactionNameMethods.put(cls, method);
        }
        try {
            return (java.lang.String) method.invoke(null, java.lang.Integer.valueOf(i));
        } catch (java.lang.IllegalAccessException | java.lang.reflect.InvocationTargetException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }
}
