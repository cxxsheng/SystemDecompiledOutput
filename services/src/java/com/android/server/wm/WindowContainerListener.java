package com.android.server.wm;

/* loaded from: classes3.dex */
interface WindowContainerListener extends com.android.server.wm.ConfigurationContainerListener {
    default void onDisplayChanged(com.android.server.wm.DisplayContent displayContent) {
    }

    default void onRemoved() {
    }

    default void onVisibleRequestedChanged(boolean z) {
    }
}
