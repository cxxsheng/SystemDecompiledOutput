package com.android.server.role;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public interface RoleServicePlatformHelper {
    @android.annotation.NonNull
    java.lang.String computePackageStateHash(int i);

    @android.annotation.NonNull
    java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getLegacyRoleState(int i);
}
