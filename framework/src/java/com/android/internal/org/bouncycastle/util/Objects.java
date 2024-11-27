package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public class Objects {
    public static boolean areEqual(java.lang.Object obj, java.lang.Object obj2) {
        return obj == obj2 || !(obj == null || obj2 == null || !obj.equals(obj2));
    }

    public static int hashCode(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }
}
