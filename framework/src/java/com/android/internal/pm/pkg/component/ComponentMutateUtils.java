package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ComponentMutateUtils {
    public static void setMaxAspectRatio(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, int i, float f) {
        ((com.android.internal.pm.pkg.component.ParsedActivityImpl) parsedActivity).setMaxAspectRatio(i, f);
    }

    public static void setMinAspectRatio(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, int i, float f) {
        ((com.android.internal.pm.pkg.component.ParsedActivityImpl) parsedActivity).setMinAspectRatio(i, f);
    }

    public static void setSupportsSizeChanges(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, boolean z) {
        ((com.android.internal.pm.pkg.component.ParsedActivityImpl) parsedActivity).setSupportsSizeChanges(z);
    }

    public static void setResizeMode(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, int i) {
        ((com.android.internal.pm.pkg.component.ParsedActivityImpl) parsedActivity).setResizeMode(i);
    }

    public static void setExactFlags(com.android.internal.pm.pkg.component.ParsedComponent parsedComponent, int i) {
        ((com.android.internal.pm.pkg.component.ParsedComponentImpl) parsedComponent).setFlags(i);
    }

    public static void setEnabled(com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, boolean z) {
        ((com.android.internal.pm.pkg.component.ParsedMainComponentImpl) parsedMainComponent).setEnabled(z);
    }

    public static void setPackageName(com.android.internal.pm.pkg.component.ParsedComponent parsedComponent, java.lang.String str) {
        ((com.android.internal.pm.pkg.component.ParsedComponentImpl) parsedComponent).setPackageName(str);
    }

    public static void setDirectBootAware(com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, boolean z) {
        ((com.android.internal.pm.pkg.component.ParsedMainComponentImpl) parsedMainComponent).setDirectBootAware(z);
    }

    public static void setExported(com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, boolean z) {
        ((com.android.internal.pm.pkg.component.ParsedMainComponentImpl) parsedMainComponent).setExported(z);
    }

    public static void setAuthority(com.android.internal.pm.pkg.component.ParsedProvider parsedProvider, java.lang.String str) {
        ((com.android.internal.pm.pkg.component.ParsedProviderImpl) parsedProvider).setAuthority(str);
    }

    public static void setSyncable(com.android.internal.pm.pkg.component.ParsedProvider parsedProvider, boolean z) {
        ((com.android.internal.pm.pkg.component.ParsedProviderImpl) parsedProvider).setSyncable(z);
    }

    public static void setProtectionLevel(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission, int i) {
        ((com.android.internal.pm.pkg.component.ParsedPermissionImpl) parsedPermission).setProtectionLevel(i);
    }

    public static void setParsedPermissionGroup(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission, com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup) {
        ((com.android.internal.pm.pkg.component.ParsedPermissionImpl) parsedPermission).setParsedPermissionGroup(parsedPermissionGroup);
    }

    public static void setPriority(com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup, int i) {
        ((com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl) parsedPermissionGroup).setPriority(i);
    }

    public static void addStateFrom(com.android.internal.pm.pkg.component.ParsedProcess parsedProcess, com.android.internal.pm.pkg.component.ParsedProcess parsedProcess2) {
        ((com.android.internal.pm.pkg.component.ParsedProcessImpl) parsedProcess).addStateFrom(parsedProcess2);
    }
}
