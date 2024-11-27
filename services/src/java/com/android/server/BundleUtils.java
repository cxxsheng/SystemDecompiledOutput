package com.android.server;

/* loaded from: classes.dex */
public final class BundleUtils {
    private BundleUtils() {
    }

    public static boolean isEmpty(@android.annotation.Nullable android.os.Bundle bundle) {
        return bundle == null || bundle.size() == 0;
    }

    @android.annotation.NonNull
    public static android.os.Bundle clone(@android.annotation.Nullable android.os.Bundle bundle) {
        return bundle != null ? new android.os.Bundle(bundle) : new android.os.Bundle();
    }
}
