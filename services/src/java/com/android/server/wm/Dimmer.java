package com.android.server.wm;

/* loaded from: classes3.dex */
public abstract class Dimmer {
    static final boolean DIMMER_REFACTOR = com.android.window.flags.Flags.introduceSmootherDimmer();
    protected final com.android.server.wm.WindowContainer mHost;

    protected abstract void adjustAppearance(com.android.server.wm.WindowContainer windowContainer, float f, int i);

    protected abstract void adjustRelativeLayer(com.android.server.wm.WindowContainer windowContainer, int i);

    abstract void dontAnimateExit();

    abstract android.graphics.Rect getDimBounds();

    @com.android.internal.annotations.VisibleForTesting
    abstract android.view.SurfaceControl getDimLayer();

    abstract void resetDimStates();

    abstract boolean updateDims(android.view.SurfaceControl.Transaction transaction);

    protected Dimmer(com.android.server.wm.WindowContainer windowContainer) {
        this.mHost = windowContainer;
    }

    static com.android.server.wm.Dimmer create(com.android.server.wm.WindowContainer windowContainer) {
        return DIMMER_REFACTOR ? new com.android.server.wm.SmoothDimmer(windowContainer) : new com.android.server.wm.LegacyDimmer(windowContainer);
    }

    @android.annotation.NonNull
    com.android.server.wm.WindowContainer<?> getHost() {
        return this.mHost;
    }
}
