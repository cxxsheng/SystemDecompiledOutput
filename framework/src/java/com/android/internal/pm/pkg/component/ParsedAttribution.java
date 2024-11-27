package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedAttribution {
    public static final int MAX_ATTRIBUTION_TAG_LEN = 50;

    java.util.List<java.lang.String> getInheritFrom();

    int getLabel();

    java.lang.String getTag();
}
