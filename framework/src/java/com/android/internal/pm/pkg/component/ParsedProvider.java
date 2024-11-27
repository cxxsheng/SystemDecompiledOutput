package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedProvider extends com.android.internal.pm.pkg.component.ParsedMainComponent {
    java.lang.String getAuthority();

    int getInitOrder();

    java.util.List<android.content.pm.PathPermission> getPathPermissions();

    java.lang.String getReadPermission();

    java.util.List<android.os.PatternMatcher> getUriPermissionPatterns();

    java.lang.String getWritePermission();

    boolean isForceUriPermissions();

    boolean isGrantUriPermissions();

    boolean isMultiProcess();

    boolean isSyncable();
}
