package com.android.server.wm;

/* loaded from: classes3.dex */
final class VrController {
    private static final int FLAG_NON_VR_MODE = 0;
    private static final int FLAG_PERSISTENT_VR_MODE = 2;
    private static final int FLAG_VR_MODE = 1;
    private static int[] ORIG_ENUMS = {0, 1, 2};
    private static int[] PROTO_ENUMS = {0, 1, 2};
    private static final java.lang.String TAG = "VrController";
    private final java.lang.Object mGlobalAmLock;
    com.android.server.vr.VrManagerInternal mVrService;
    private volatile int mVrState = 0;
    private int mVrRenderThreadTid = 0;
    private final android.service.vr.IPersistentVrStateCallbacks mPersistentVrModeListener = new android.service.vr.IPersistentVrStateCallbacks.Stub() { // from class: com.android.server.wm.VrController.1
        public void onPersistentVrStateChanged(boolean z) {
            synchronized (com.android.server.wm.VrController.this.mGlobalAmLock) {
                try {
                    if (z) {
                        com.android.server.wm.VrController.this.setVrRenderThreadLocked(0, 3, true);
                        com.android.server.wm.VrController.this.mVrState |= 2;
                    } else {
                        com.android.server.wm.VrController.this.setPersistentVrRenderThreadLocked(0, true);
                        com.android.server.wm.VrController.this.mVrState &= -3;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };

    public VrController(java.lang.Object obj) {
        this.mGlobalAmLock = obj;
    }

    public void onSystemReady() {
        com.android.server.vr.VrManagerInternal vrManagerInternal = (com.android.server.vr.VrManagerInternal) com.android.server.LocalServices.getService(com.android.server.vr.VrManagerInternal.class);
        if (vrManagerInternal != null) {
            this.mVrService = vrManagerInternal;
            vrManagerInternal.addPersistentVrModeStateListener(this.mPersistentVrModeListener);
        }
    }

    boolean isInterestingToSchedGroup() {
        return (this.mVrState & 3) != 0;
    }

    public void onTopProcChangedLocked(com.android.server.wm.WindowProcessController windowProcessController) {
        int currentSchedulingGroup = windowProcessController.getCurrentSchedulingGroup();
        if (currentSchedulingGroup == 3) {
            setVrRenderThreadLocked(windowProcessController.mVrThreadTid, currentSchedulingGroup, true);
        } else if (windowProcessController.mVrThreadTid == this.mVrRenderThreadTid) {
            clearVrRenderThreadLocked(true);
        }
    }

    public boolean onVrModeChanged(com.android.server.wm.ActivityRecord activityRecord) {
        boolean z;
        android.content.ComponentName componentName;
        int i;
        android.content.ComponentName componentName2;
        boolean changeVrModeLocked;
        int i2;
        com.android.server.vr.VrManagerInternal vrManagerInternal = this.mVrService;
        if (vrManagerInternal == null) {
            return false;
        }
        synchronized (this.mGlobalAmLock) {
            try {
                z = activityRecord.requestedVrComponent != null;
                componentName = activityRecord.requestedVrComponent;
                i = activityRecord.mUserId;
                componentName2 = activityRecord.info.getComponentName();
                changeVrModeLocked = changeVrModeLocked(z, activityRecord.app);
                if (activityRecord.app == null) {
                    i2 = -1;
                } else {
                    i2 = activityRecord.app.getPid();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        vrManagerInternal.setVrMode(z, componentName, i, i2, componentName2);
        return changeVrModeLocked;
    }

    public void setVrThreadLocked(int i, int i2, com.android.server.wm.WindowProcessController windowProcessController) {
        if (hasPersistentVrFlagSet()) {
            android.util.Slog.w(TAG, "VR thread cannot be set in persistent VR mode!");
            return;
        }
        if (windowProcessController == null) {
            android.util.Slog.w(TAG, "Persistent VR thread not set, calling process doesn't exist!");
            return;
        }
        if (i != 0) {
            enforceThreadInProcess(i, i2);
        }
        if (!inVrMode()) {
            android.util.Slog.w(TAG, "VR thread cannot be set when not in VR mode!");
        } else {
            setVrRenderThreadLocked(i, windowProcessController.getCurrentSchedulingGroup(), false);
        }
        if (i <= 0) {
            i = 0;
        }
        windowProcessController.mVrThreadTid = i;
    }

    public void setPersistentVrThreadLocked(int i, int i2, com.android.server.wm.WindowProcessController windowProcessController) {
        if (!hasPersistentVrFlagSet()) {
            android.util.Slog.w(TAG, "Persistent VR thread may only be set in persistent VR mode!");
        } else {
            if (windowProcessController == null) {
                android.util.Slog.w(TAG, "Persistent VR thread not set, calling process doesn't exist!");
                return;
            }
            if (i != 0) {
                enforceThreadInProcess(i, i2);
            }
            setPersistentVrRenderThreadLocked(i, false);
        }
    }

    public boolean shouldDisableNonVrUiLocked() {
        return this.mVrState != 0;
    }

    private boolean changeVrModeLocked(boolean z, com.android.server.wm.WindowProcessController windowProcessController) {
        int i = this.mVrState;
        if (z) {
            this.mVrState |= 1;
        } else {
            this.mVrState &= -2;
        }
        boolean z2 = i != this.mVrState;
        if (z2) {
            if (windowProcessController != null) {
                if (windowProcessController.mVrThreadTid > 0) {
                    setVrRenderThreadLocked(windowProcessController.mVrThreadTid, windowProcessController.getCurrentSchedulingGroup(), false);
                }
            } else {
                clearVrRenderThreadLocked(false);
            }
        }
        return z2;
    }

    private int updateVrRenderThreadLocked(int i, boolean z) {
        if (this.mVrRenderThreadTid == i) {
            return this.mVrRenderThreadTid;
        }
        if (this.mVrRenderThreadTid > 0) {
            com.android.server.am.ActivityManagerService.scheduleAsRegularPriority(this.mVrRenderThreadTid, z);
            this.mVrRenderThreadTid = 0;
        }
        if (i > 0) {
            this.mVrRenderThreadTid = i;
            com.android.server.am.ActivityManagerService.scheduleAsFifoPriority(this.mVrRenderThreadTid, z);
        }
        return this.mVrRenderThreadTid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setPersistentVrRenderThreadLocked(int i, boolean z) {
        if (!hasPersistentVrFlagSet()) {
            if (!z) {
                android.util.Slog.w(TAG, "Failed to set persistent VR thread, system not in persistent VR mode.");
            }
            return this.mVrRenderThreadTid;
        }
        return updateVrRenderThreadLocked(i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setVrRenderThreadLocked(int i, int i2, boolean z) {
        java.lang.String str;
        boolean inVrMode = inVrMode();
        boolean hasPersistentVrFlagSet = hasPersistentVrFlagSet();
        if (!inVrMode || hasPersistentVrFlagSet || i2 != 3) {
            if (!z) {
                if (!inVrMode) {
                    str = "system not in VR mode.";
                } else if (!hasPersistentVrFlagSet) {
                    str = "caller is not the current top application.";
                } else {
                    str = "system in persistent VR mode.";
                }
                android.util.Slog.w(TAG, "Failed to set VR thread, " + str);
            }
            return this.mVrRenderThreadTid;
        }
        return updateVrRenderThreadLocked(i, z);
    }

    private void clearVrRenderThreadLocked(boolean z) {
        updateVrRenderThreadLocked(0, z);
    }

    private void enforceThreadInProcess(int i, int i2) {
        if (!android.os.Process.isThreadInProcess(i2, i)) {
            throw new java.lang.IllegalArgumentException("VR thread does not belong to process");
        }
    }

    private boolean inVrMode() {
        return (this.mVrState & 1) != 0;
    }

    private boolean hasPersistentVrFlagSet() {
        return (this.mVrState & 2) != 0;
    }

    public java.lang.String toString() {
        return java.lang.String.format("[VrState=0x%x,VrRenderThreadTid=%d]", java.lang.Integer.valueOf(this.mVrState), java.lang.Integer.valueOf(this.mVrRenderThreadTid));
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        android.util.proto.ProtoUtils.writeBitWiseFlagsToProtoEnum(protoOutputStream, 2259152797697L, this.mVrState, ORIG_ENUMS, PROTO_ENUMS);
        protoOutputStream.write(1120986464258L, this.mVrRenderThreadTid);
        protoOutputStream.end(start);
    }
}
