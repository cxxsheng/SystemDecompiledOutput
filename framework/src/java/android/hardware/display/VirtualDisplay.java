package android.hardware.display;

/* loaded from: classes2.dex */
public final class VirtualDisplay {
    private final android.view.Display mDisplay;
    private final android.hardware.display.DisplayManagerGlobal mGlobal;
    private android.view.Surface mSurface;
    private android.hardware.display.IVirtualDisplayCallback mToken;

    VirtualDisplay(android.hardware.display.DisplayManagerGlobal displayManagerGlobal, android.view.Display display, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.view.Surface surface) {
        this.mGlobal = displayManagerGlobal;
        this.mDisplay = display;
        this.mToken = iVirtualDisplayCallback;
        this.mSurface = surface;
    }

    public android.view.Display getDisplay() {
        return this.mDisplay;
    }

    public android.view.Surface getSurface() {
        return this.mSurface;
    }

    public android.hardware.display.IVirtualDisplayCallback getToken() {
        return this.mToken;
    }

    public void setSurface(android.view.Surface surface) {
        if (this.mSurface != surface) {
            this.mGlobal.setVirtualDisplaySurface(this.mToken, surface);
            this.mSurface = surface;
        }
    }

    public void resize(int i, int i2, int i3) {
        this.mGlobal.resizeVirtualDisplay(this.mToken, i, i2, i3);
    }

    public void release() {
        if (this.mToken != null) {
            this.mGlobal.releaseVirtualDisplay(this.mToken);
            this.mToken = null;
        }
    }

    public void setDisplayState(boolean z) {
        if (this.mToken != null) {
            this.mGlobal.setVirtualDisplayState(this.mToken, z);
        }
    }

    public java.lang.String toString() {
        return "VirtualDisplay{display=" + this.mDisplay + ", token=" + this.mToken + ", surface=" + this.mSurface + "}";
    }

    public static abstract class Callback {
        public void onPaused() {
        }

        public void onResumed() {
        }

        public void onStopped() {
        }
    }
}
