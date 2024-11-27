package org.apache.commons.math.exception.util;

/* loaded from: classes3.dex */
public class ArgUtils {
    private ArgUtils() {
    }

    public static java.lang.Object[] flatten(java.lang.Object[] objArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (objArr != null) {
            for (java.lang.Object obj : objArr) {
                if (obj instanceof java.lang.Object[]) {
                    for (java.lang.Object obj2 : flatten((java.lang.Object[]) obj)) {
                        arrayList.add(obj2);
                    }
                } else {
                    arrayList.add(obj);
                }
            }
        }
        return arrayList.toArray();
    }
}
