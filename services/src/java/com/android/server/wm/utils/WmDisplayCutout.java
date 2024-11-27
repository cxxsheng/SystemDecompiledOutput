package com.android.server.wm.utils;

/* loaded from: classes3.dex */
public class WmDisplayCutout {
    public static final com.android.server.wm.utils.WmDisplayCutout NO_CUTOUT = new com.android.server.wm.utils.WmDisplayCutout(android.view.DisplayCutout.NO_CUTOUT, null);
    private final android.util.Size mFrameSize;
    private final android.view.DisplayCutout mInner;

    public WmDisplayCutout(android.view.DisplayCutout displayCutout, android.util.Size size) {
        this.mInner = displayCutout;
        this.mFrameSize = size;
    }

    public static com.android.server.wm.utils.WmDisplayCutout computeSafeInsets(android.view.DisplayCutout displayCutout, int i, int i2) {
        if (displayCutout == android.view.DisplayCutout.NO_CUTOUT) {
            return NO_CUTOUT;
        }
        return new com.android.server.wm.utils.WmDisplayCutout(displayCutout.replaceSafeInsets(android.view.DisplayCutout.computeSafeInsets(i, i2, displayCutout)), new android.util.Size(i, i2));
    }

    public com.android.server.wm.utils.WmDisplayCutout computeSafeInsets(int i, int i2) {
        return computeSafeInsets(this.mInner, i, i2);
    }

    public android.view.DisplayCutout getDisplayCutout() {
        return this.mInner;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.server.wm.utils.WmDisplayCutout)) {
            return false;
        }
        com.android.server.wm.utils.WmDisplayCutout wmDisplayCutout = (com.android.server.wm.utils.WmDisplayCutout) obj;
        return java.util.Objects.equals(this.mInner, wmDisplayCutout.mInner) && java.util.Objects.equals(this.mFrameSize, wmDisplayCutout.mFrameSize);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mInner, this.mFrameSize);
    }

    public java.lang.String toString() {
        return "WmDisplayCutout{" + this.mInner + ", mFrameSize=" + this.mFrameSize + '}';
    }
}
