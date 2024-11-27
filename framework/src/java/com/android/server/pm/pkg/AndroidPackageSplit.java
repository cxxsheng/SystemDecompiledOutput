package com.android.server.pm.pkg;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes5.dex */
public interface AndroidPackageSplit {
    java.lang.String getClassLoaderName();

    java.util.List<com.android.server.pm.pkg.AndroidPackageSplit> getDependencies();

    java.lang.String getName();

    java.lang.String getPath();

    int getRevisionCode();

    boolean isHasCode();
}
