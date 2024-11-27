package com.android.internal.pm.parsing.pkg;

/* loaded from: classes5.dex */
public interface AndroidPackageHidden {
    java.lang.String getPrimaryCpuAbi();

    java.lang.String getSecondaryCpuAbi();

    @java.lang.Deprecated
    int getVersionCode();

    int getVersionCodeMajor();

    boolean isOdm();

    boolean isOem();

    boolean isPrivileged();

    boolean isProduct();

    boolean isSystem();

    boolean isSystemExt();

    boolean isVendor();

    android.content.pm.ApplicationInfo toAppInfoWithoutState();
}
