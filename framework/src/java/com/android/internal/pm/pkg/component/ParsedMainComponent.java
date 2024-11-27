package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedMainComponent extends com.android.internal.pm.pkg.component.ParsedComponent {
    java.lang.String[] getAttributionTags();

    java.lang.String getClassName();

    int getOrder();

    java.lang.String getProcessName();

    java.lang.String getSplitName();

    boolean isDirectBootAware();

    boolean isEnabled();

    boolean isExported();
}
