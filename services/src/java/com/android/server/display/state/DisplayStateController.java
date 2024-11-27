package com.android.server.display.state;

/* loaded from: classes.dex */
public class DisplayStateController {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private com.android.server.display.DisplayPowerProximityStateController mDisplayPowerProximityStateController;
    private boolean mPerformScreenOffTransition = false;
    private int mDozeStateOverride = 0;

    public DisplayStateController(com.android.server.display.DisplayPowerProximityStateController displayPowerProximityStateController) {
        this.mDisplayPowerProximityStateController = displayPowerProximityStateController;
    }

    public int updateDisplayState(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, boolean z, boolean z2) {
        int i;
        this.mPerformScreenOffTransition = false;
        switch (displayPowerRequest.policy) {
            case 0:
                this.mPerformScreenOffTransition = true;
                i = 1;
                break;
            case 1:
                if (this.mDozeStateOverride != 0) {
                    i = this.mDozeStateOverride;
                    break;
                } else if (displayPowerRequest.dozeScreenState != 0) {
                    i = displayPowerRequest.dozeScreenState;
                    break;
                } else {
                    i = 3;
                    break;
                }
            default:
                i = 2;
                break;
        }
        this.mDisplayPowerProximityStateController.updateProximityState(displayPowerRequest, i);
        if (!z || z2 || this.mDisplayPowerProximityStateController.isScreenOffBecauseOfProximity()) {
            return 1;
        }
        return i;
    }

    public void overrideDozeScreenState(int i) {
        this.mDozeStateOverride = i;
    }

    public boolean shouldPerformScreenOffTransition() {
        return this.mPerformScreenOffTransition;
    }

    public void dumpsys(java.io.PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("DisplayStateController:");
        printWriter.println("  mPerformScreenOffTransition:" + this.mPerformScreenOffTransition);
        printWriter.println("  mDozeStateOverride=" + this.mDozeStateOverride);
        java.io.PrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, " ");
        if (this.mDisplayPowerProximityStateController != null) {
            this.mDisplayPowerProximityStateController.dumpLocal(indentingPrintWriter);
        }
    }
}
