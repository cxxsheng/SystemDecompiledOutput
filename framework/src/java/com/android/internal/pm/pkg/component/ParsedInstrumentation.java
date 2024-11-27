package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedInstrumentation extends com.android.internal.pm.pkg.component.ParsedComponent {
    java.lang.String getTargetPackage();

    java.lang.String getTargetProcesses();

    boolean isFunctionalTest();

    boolean isHandleProfiling();
}
