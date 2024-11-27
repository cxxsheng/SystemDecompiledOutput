package com.android.server.wm;

/* loaded from: classes3.dex */
class UnknownAppVisibilityController {
    private static final java.lang.String TAG = "WindowManager";
    private static final int UNKNOWN_STATE_WAITING_RELAYOUT = 2;
    private static final int UNKNOWN_STATE_WAITING_RESUME = 1;
    private static final int UNKNOWN_STATE_WAITING_VISIBILITY_UPDATE = 3;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private final com.android.server.wm.WindowManagerService mService;
    private final android.util.ArrayMap<com.android.server.wm.ActivityRecord, java.lang.Integer> mUnknownApps = new android.util.ArrayMap<>();

    UnknownAppVisibilityController(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
    }

    boolean allResolved() {
        return this.mUnknownApps.isEmpty();
    }

    boolean isVisibilityUnknown(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mUnknownApps.isEmpty()) {
            return false;
        }
        return this.mUnknownApps.containsKey(activityRecord);
    }

    void clear() {
        this.mUnknownApps.clear();
    }

    java.lang.String getDebugMessage() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int size = this.mUnknownApps.size() - 1; size >= 0; size--) {
            sb.append("app=");
            sb.append(this.mUnknownApps.keyAt(size));
            sb.append(" state=");
            sb.append(this.mUnknownApps.valueAt(size));
            if (size != 0) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    void appRemovedOrHidden(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mUnknownApps.isEmpty()) {
            return;
        }
        this.mUnknownApps.remove(activityRecord);
    }

    void notifyLaunched(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        if (!activityRecord.mLaunchTaskBehind) {
            this.mUnknownApps.put(activityRecord, 1);
        } else {
            this.mUnknownApps.put(activityRecord, 2);
        }
    }

    void notifyAppResumedFinished(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        java.lang.Integer num;
        if (!this.mUnknownApps.isEmpty() && (num = this.mUnknownApps.get(activityRecord)) != null && num.intValue() == 1) {
            this.mUnknownApps.put(activityRecord, 2);
        }
    }

    void notifyRelayouted(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        java.lang.Integer num;
        if (this.mUnknownApps.isEmpty() || (num = this.mUnknownApps.get(activityRecord)) == null) {
            return;
        }
        if (num.intValue() == 2 || activityRecord.mStartingWindow != null) {
            this.mUnknownApps.put(activityRecord, 3);
            this.mDisplayContent.notifyKeyguardFlagsChanged();
            notifyVisibilitiesUpdated();
        }
    }

    private void notifyVisibilitiesUpdated() {
        boolean z = false;
        for (int size = this.mUnknownApps.size() - 1; size >= 0; size--) {
            if (this.mUnknownApps.valueAt(size).intValue() == 3) {
                this.mUnknownApps.removeAt(size);
                z = true;
            }
        }
        if (z) {
            this.mService.mWindowPlacerLocked.performSurfacePlacement();
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        if (this.mUnknownApps.isEmpty()) {
            return;
        }
        printWriter.println(str + "Unknown visibilities:");
        for (int size = this.mUnknownApps.size() + (-1); size >= 0; size += -1) {
            printWriter.println(str + "  app=" + this.mUnknownApps.keyAt(size) + " state=" + this.mUnknownApps.valueAt(size));
        }
    }
}
