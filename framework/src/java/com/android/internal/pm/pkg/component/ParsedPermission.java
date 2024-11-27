package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedPermission extends com.android.internal.pm.pkg.component.ParsedComponent {
    java.lang.String getBackgroundPermission();

    java.lang.String getGroup();

    java.util.Set<java.lang.String> getKnownCerts();

    com.android.internal.pm.pkg.component.ParsedPermissionGroup getParsedPermissionGroup();

    int getProtectionLevel();

    int getRequestRes();

    boolean isTree();
}
