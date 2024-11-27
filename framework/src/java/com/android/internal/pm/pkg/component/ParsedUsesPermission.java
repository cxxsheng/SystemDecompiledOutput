package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedUsesPermission {
    public static final int FLAG_NEVER_FOR_LOCATION = 65536;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UsesPermissionFlags {
    }

    java.lang.String getName();

    int getUsesPermissionFlags();
}
