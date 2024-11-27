package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class MagnificationProcessor {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "MagnificationProcessor";
    private final com.android.server.accessibility.magnification.MagnificationController mController;

    public MagnificationProcessor(com.android.server.accessibility.magnification.MagnificationController magnificationController) {
        this.mController = magnificationController;
    }

    @android.annotation.NonNull
    public android.accessibilityservice.MagnificationConfig getMagnificationConfig(int i) {
        int controllingMode = getControllingMode(i);
        android.accessibilityservice.MagnificationConfig.Builder builder = new android.accessibilityservice.MagnificationConfig.Builder();
        if (controllingMode == 1) {
            com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController = this.mController.getFullScreenMagnificationController();
            builder.setMode(controllingMode).setActivated(this.mController.isActivated(i, 1)).setScale(fullScreenMagnificationController.getScale(i)).setCenterX(fullScreenMagnificationController.getCenterX(i)).setCenterY(fullScreenMagnificationController.getCenterY(i));
        } else if (controllingMode == 2) {
            com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager = this.mController.getMagnificationConnectionManager();
            builder.setMode(controllingMode).setActivated(this.mController.isActivated(i, 2)).setScale(magnificationConnectionManager.getScale(i)).setCenterX(magnificationConnectionManager.getCenterX(i)).setCenterY(magnificationConnectionManager.getCenterY(i));
        } else {
            builder.setActivated(false);
        }
        return builder.build();
    }

    public boolean setMagnificationConfig(int i, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig, boolean z, int i2) {
        if (transitionModeIfNeeded(i, magnificationConfig, z, i2)) {
            return true;
        }
        int mode = magnificationConfig.getMode();
        if (mode == 0) {
            mode = getControllingMode(i);
        }
        boolean isActivated = magnificationConfig.isActivated();
        if (mode == 1) {
            if (isActivated) {
                return setScaleAndCenterForFullScreenMagnification(i, magnificationConfig.getScale(), magnificationConfig.getCenterX(), magnificationConfig.getCenterY(), z, i2);
            }
            return resetFullscreenMagnification(i, z);
        }
        if (mode != 2) {
            return false;
        }
        if (isActivated) {
            return this.mController.getMagnificationConnectionManager().enableWindowMagnification(i, magnificationConfig.getScale(), magnificationConfig.getCenterX(), magnificationConfig.getCenterY(), z ? android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null, i2);
        }
        return this.mController.getMagnificationConnectionManager().disableWindowMagnification(i, false);
    }

    private boolean setScaleAndCenterForFullScreenMagnification(int i, float f, float f2, float f3, boolean z, int i2) {
        if (!isRegistered(i)) {
            register(i);
        }
        return this.mController.getFullScreenMagnificationController().setScaleAndCenter(i, f, f2, f3, z, i2);
    }

    private boolean transitionModeIfNeeded(int i, android.accessibilityservice.MagnificationConfig magnificationConfig, boolean z, int i2) {
        int controllingMode = getControllingMode(i);
        if (magnificationConfig.getMode() == 0) {
            return false;
        }
        if (controllingMode == magnificationConfig.getMode() && !this.mController.hasDisableMagnificationCallback(i)) {
            return false;
        }
        this.mController.transitionMagnificationConfigMode(i, magnificationConfig, z, i2);
        return true;
    }

    public float getScale(int i) {
        return this.mController.getFullScreenMagnificationController().getScale(i);
    }

    public float getCenterX(int i, boolean z) {
        boolean registerDisplayMagnificationIfNeeded = registerDisplayMagnificationIfNeeded(i, z);
        try {
            return this.mController.getFullScreenMagnificationController().getCenterX(i);
        } finally {
            if (registerDisplayMagnificationIfNeeded) {
                unregister(i);
            }
        }
    }

    public float getCenterY(int i, boolean z) {
        boolean registerDisplayMagnificationIfNeeded = registerDisplayMagnificationIfNeeded(i, z);
        try {
            return this.mController.getFullScreenMagnificationController().getCenterY(i);
        } finally {
            if (registerDisplayMagnificationIfNeeded) {
                unregister(i);
            }
        }
    }

    public void getCurrentMagnificationRegion(int i, @android.annotation.NonNull android.graphics.Region region, boolean z) {
        int controllingMode = getControllingMode(i);
        if (controllingMode == 1) {
            getFullscreenMagnificationRegion(i, region, z);
        } else if (controllingMode == 2) {
            this.mController.getMagnificationConnectionManager().getMagnificationSourceBounds(i, region);
        }
    }

    public void getFullscreenMagnificationRegion(int i, @android.annotation.NonNull android.graphics.Region region, boolean z) {
        boolean registerDisplayMagnificationIfNeeded = registerDisplayMagnificationIfNeeded(i, z);
        try {
            this.mController.getFullScreenMagnificationController().getMagnificationRegion(i, region);
        } finally {
            if (registerDisplayMagnificationIfNeeded) {
                unregister(i);
            }
        }
    }

    public boolean resetCurrentMagnification(int i, boolean z) {
        int controllingMode = getControllingMode(i);
        if (controllingMode == 1) {
            return this.mController.getFullScreenMagnificationController().reset(i, z);
        }
        if (controllingMode == 2) {
            return this.mController.getMagnificationConnectionManager().disableWindowMagnification(i, false, z ? android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null);
        }
        return false;
    }

    public boolean resetFullscreenMagnification(int i, boolean z) {
        return this.mController.getFullScreenMagnificationController().reset(i, z);
    }

    public void resetAllIfNeeded(int i) {
        this.mController.getFullScreenMagnificationController().resetAllIfNeeded(i);
        this.mController.getMagnificationConnectionManager().resetAllIfNeeded(i);
    }

    public boolean isMagnifying(int i) {
        int controllingMode = getControllingMode(i);
        if (controllingMode == 1) {
            return this.mController.getFullScreenMagnificationController().isActivated(i);
        }
        if (controllingMode == 2) {
            return this.mController.getMagnificationConnectionManager().isWindowMagnifierEnabled(i);
        }
        return false;
    }

    public int getControllingMode(int i) {
        if (this.mController.isActivated(i, 2)) {
            return 2;
        }
        return (!this.mController.isActivated(i, 1) && this.mController.getLastMagnificationActivatedMode(i) == 2) ? 2 : 1;
    }

    private boolean registerDisplayMagnificationIfNeeded(int i, boolean z) {
        if (!isRegistered(i) && z) {
            register(i);
            return true;
        }
        return false;
    }

    private boolean isRegistered(int i) {
        return this.mController.getFullScreenMagnificationController().isRegistered(i);
    }

    private void register(int i) {
        this.mController.getFullScreenMagnificationController().register(i);
    }

    private void unregister(int i) {
        this.mController.getFullScreenMagnificationController().unregister(i);
    }

    public void dump(java.io.PrintWriter printWriter, java.util.ArrayList<android.view.Display> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            int displayId = arrayList.get(i).getDisplayId();
            android.accessibilityservice.MagnificationConfig magnificationConfig = getMagnificationConfig(displayId);
            printWriter.println("Magnifier on display#" + displayId);
            printWriter.append((java.lang.CharSequence) ("    " + magnificationConfig)).println();
            android.graphics.Region region = new android.graphics.Region();
            getCurrentMagnificationRegion(displayId, region, true);
            if (!region.isEmpty()) {
                printWriter.append("    Magnification region=").append((java.lang.CharSequence) region.toString()).println();
            }
            printWriter.append((java.lang.CharSequence) ("    IdOfLastServiceToMagnify=" + getIdOfLastServiceToMagnify(magnificationConfig.getMode(), displayId))).println();
            dumpTrackingTypingFocusEnabledState(printWriter, displayId, magnificationConfig.getMode());
        }
        printWriter.append((java.lang.CharSequence) ("    SupportWindowMagnification=" + this.mController.supportWindowMagnification())).println();
        printWriter.append((java.lang.CharSequence) ("    WindowMagnificationConnectionState=" + this.mController.getMagnificationConnectionManager().getConnectionState())).println();
    }

    private int getIdOfLastServiceToMagnify(int i, int i2) {
        if (i == 1) {
            return this.mController.getFullScreenMagnificationController().getIdOfLastServiceToMagnify(i2);
        }
        return this.mController.getMagnificationConnectionManager().getIdOfLastServiceToMagnify(i2);
    }

    private void dumpTrackingTypingFocusEnabledState(java.io.PrintWriter printWriter, int i, int i2) {
        if (i2 == 2) {
            printWriter.append((java.lang.CharSequence) ("    TrackingTypingFocusEnabled=" + this.mController.getMagnificationConnectionManager().isTrackingTypingFocusEnabled(i))).println();
        }
    }
}
