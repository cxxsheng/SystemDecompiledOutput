package com.android.internal.compat;

/* loaded from: classes4.dex */
public class AndroidBuildClassifier {
    public boolean isDebuggableBuild() {
        return android.os.Build.IS_DEBUGGABLE;
    }

    public boolean isFinalBuild() {
        return "REL".equals(android.os.Build.VERSION.CODENAME);
    }

    public int platformTargetSdk() {
        if (isFinalBuild()) {
            return android.os.Build.VERSION.SDK_INT;
        }
        return 10000;
    }
}
