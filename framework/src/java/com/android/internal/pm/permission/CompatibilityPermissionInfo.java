package com.android.internal.pm.permission;

/* loaded from: classes5.dex */
public class CompatibilityPermissionInfo {
    public static final com.android.internal.pm.permission.CompatibilityPermissionInfo[] COMPAT_PERMS = {new com.android.internal.pm.permission.CompatibilityPermissionInfo(android.Manifest.permission.POST_NOTIFICATIONS, 33), new com.android.internal.pm.permission.CompatibilityPermissionInfo(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, 4), new com.android.internal.pm.permission.CompatibilityPermissionInfo(android.Manifest.permission.READ_PHONE_STATE, 4)};
    private final java.lang.String mName;
    private final int mSdkVersion;

    public CompatibilityPermissionInfo(java.lang.String str, int i) {
        this.mName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mName);
        this.mSdkVersion = i;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getSdkVersion() {
        return this.mSdkVersion;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
