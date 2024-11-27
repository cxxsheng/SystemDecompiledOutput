package com.android.server.wm;

/* loaded from: classes3.dex */
public interface ConfigurationContainerListener {
    default void onRequestedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
    }

    default void onMergedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
    }
}
