package com.android.server.permission.jarjar.kotlin.jvm.internal;

/* loaded from: classes2.dex */
public class Intrinsics {
    private Intrinsics() {
    }

    public static void checkNotNull(java.lang.Object object) {
        if (object == null) {
            throwJavaNpe();
        }
    }

    public static void checkNotNull(java.lang.Object object, java.lang.String message) {
        if (object == null) {
            throwJavaNpe(message);
        }
    }

    public static void throwJavaNpe() {
        throw ((java.lang.NullPointerException) sanitizeStackTrace(new java.lang.NullPointerException()));
    }

    public static void throwJavaNpe(java.lang.String message) {
        throw ((java.lang.NullPointerException) sanitizeStackTrace(new java.lang.NullPointerException(message)));
    }

    public static void throwUninitializedProperty(java.lang.String message) {
        throw ((com.android.server.permission.jarjar.kotlin.UninitializedPropertyAccessException) sanitizeStackTrace(new com.android.server.permission.jarjar.kotlin.UninitializedPropertyAccessException(message)));
    }

    public static void throwUninitializedPropertyAccessException(java.lang.String propertyName) {
        throwUninitializedProperty("lateinit property " + propertyName + " has not been initialized");
    }

    public static void checkNotNullExpressionValue(java.lang.Object value, java.lang.String expression) {
        if (value == null) {
            throw ((java.lang.NullPointerException) sanitizeStackTrace(new java.lang.NullPointerException(expression + " must not be null")));
        }
    }

    public static void checkNotNullParameter(java.lang.Object value, java.lang.String paramName) {
        if (value == null) {
            throwParameterIsNullNPE(paramName);
        }
    }

    private static void throwParameterIsNullNPE(java.lang.String paramName) {
        throw ((java.lang.NullPointerException) sanitizeStackTrace(new java.lang.NullPointerException(createParameterIsNullExceptionMessage(paramName))));
    }

    private static java.lang.String createParameterIsNullExceptionMessage(java.lang.String paramName) {
        java.lang.StackTraceElement[] stackTraceElements = java.lang.Thread.currentThread().getStackTrace();
        java.lang.String thisClassName = com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.class.getName();
        int i = 0;
        while (!stackTraceElements[i].getClassName().equals(thisClassName)) {
            i++;
        }
        while (stackTraceElements[i].getClassName().equals(thisClassName)) {
            i++;
        }
        java.lang.StackTraceElement caller = stackTraceElements[i];
        java.lang.String className = caller.getClassName();
        java.lang.String methodName = caller.getMethodName();
        return "Parameter specified as non-null is null: method " + className + "." + methodName + ", parameter " + paramName;
    }

    public static boolean areEqual(java.lang.Object first, java.lang.Object second) {
        return first == null ? second == null : first.equals(second);
    }

    private static <T extends java.lang.Throwable> T sanitizeStackTrace(T t) {
        return (T) sanitizeStackTrace(t, com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.class.getName());
    }

    static <T extends java.lang.Throwable> T sanitizeStackTrace(T throwable, java.lang.String classNameToDrop) {
        java.lang.StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        int lastIntrinsic = -1;
        for (int i = 0; i < size; i++) {
            if (classNameToDrop.equals(stackTrace[i].getClassName())) {
                lastIntrinsic = i;
            }
        }
        int i2 = lastIntrinsic + 1;
        java.lang.StackTraceElement[] newStackTrace = (java.lang.StackTraceElement[]) java.util.Arrays.copyOfRange(stackTrace, i2, size);
        throwable.setStackTrace(newStackTrace);
        return throwable;
    }
}
