package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedActivity extends com.android.internal.pm.pkg.component.ParsedMainComponent {
    int getColorMode();

    int getConfigChanges();

    int getDocumentLaunchMode();

    java.util.Set<java.lang.String> getKnownActivityEmbeddingCerts();

    int getLaunchMode();

    int getLockTaskLaunchMode();

    float getMaxAspectRatio();

    int getMaxRecents();

    float getMinAspectRatio();

    java.lang.String getParentActivityName();

    java.lang.String getPermission();

    int getPersistableMode();

    int getPrivateFlags();

    java.lang.String getRequestedVrComponent();

    int getRequireContentUriPermissionFromCaller();

    java.lang.String getRequiredDisplayCategory();

    int getResizeMode();

    int getRotationAnimation();

    int getScreenOrientation();

    int getSoftInputMode();

    java.lang.String getTargetActivity();

    java.lang.String getTaskAffinity();

    int getTheme();

    int getUiOptions();

    android.content.pm.ActivityInfo.WindowLayout getWindowLayout();

    boolean isSupportsSizeChanges();
}
