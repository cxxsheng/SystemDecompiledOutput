package com.android.internal.widget;

/* loaded from: classes5.dex */
public final class InlinePresentationStyleUtils {
    public static boolean bundleEquals(android.os.Bundle bundle, android.os.Bundle bundle2) {
        boolean equals;
        if (bundle == bundle2) {
            return true;
        }
        if (bundle == null || bundle2 == null || bundle.size() != bundle2.size()) {
            return false;
        }
        for (java.lang.String str : bundle.keySet()) {
            java.lang.Object obj = bundle.get(str);
            java.lang.Object obj2 = bundle2.get(str);
            if ((obj instanceof android.os.Bundle) && (obj2 instanceof android.os.Bundle)) {
                equals = bundleEquals((android.os.Bundle) obj, (android.os.Bundle) obj2);
            } else {
                equals = java.util.Objects.equals(obj, obj2);
            }
            if (!equals) {
                return false;
            }
        }
        return true;
    }

    public static void filterContentTypes(android.os.Bundle bundle) {
        if (bundle == null) {
            return;
        }
        for (java.lang.String str : bundle.keySet()) {
            java.lang.Object obj = bundle.get(str);
            if (obj instanceof android.os.Bundle) {
                filterContentTypes((android.os.Bundle) obj);
            } else if (obj instanceof android.os.IBinder) {
                bundle.remove(str);
            }
        }
    }

    private InlinePresentationStyleUtils() {
    }
}
