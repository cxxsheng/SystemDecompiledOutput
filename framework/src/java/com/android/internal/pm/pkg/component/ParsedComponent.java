package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedComponent {
    int getBanner();

    android.content.ComponentName getComponentName();

    int getDescriptionRes();

    int getFlags();

    int getIcon();

    java.util.List<com.android.internal.pm.pkg.component.ParsedIntentInfo> getIntents();

    int getLabelRes();

    int getLogo();

    android.os.Bundle getMetaData();

    java.lang.String getName();

    java.lang.CharSequence getNonLocalizedLabel();

    java.lang.String getPackageName();

    java.util.Map<java.lang.String, android.content.pm.PackageManager.Property> getProperties();
}
