package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public class ClassUtil {
    public static java.lang.Class loadClass(java.lang.Class cls, final java.lang.String str) {
        try {
            java.lang.ClassLoader classLoader = cls.getClassLoader();
            if (classLoader != null) {
                return classLoader.loadClass(str);
            }
            return (java.lang.Class) java.security.AccessController.doPrivileged(new java.security.PrivilegedAction() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil.1
                @Override // java.security.PrivilegedAction
                public java.lang.Object run() {
                    try {
                        return java.lang.Class.forName(str);
                    } catch (java.lang.Exception e) {
                        return null;
                    }
                }
            });
        } catch (java.lang.ClassNotFoundException e) {
            return null;
        }
    }
}
