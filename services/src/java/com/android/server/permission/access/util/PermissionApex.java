package com.android.server.permission.access.util;

/* compiled from: PermissionApex.kt */
/* loaded from: classes2.dex */
public final class PermissionApex {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.util.PermissionApex INSTANCE = new com.android.server.permission.access.util.PermissionApex();

    private PermissionApex() {
    }

    @org.jetbrains.annotations.NotNull
    public final java.io.File getSystemDataDirectory() {
        return getApexEnvironment().getDeviceProtectedDataDir();
    }

    @org.jetbrains.annotations.NotNull
    public final java.io.File getUserDataDirectory(int userId) {
        return getApexEnvironment().getDeviceProtectedDataDirForUser(android.os.UserHandle.of(userId));
    }

    private final android.content.ApexEnvironment getApexEnvironment() {
        return android.content.ApexEnvironment.getApexEnvironment("com.android.permission");
    }
}
