package com.android.server.audio;

/* loaded from: classes.dex */
public class HardeningEnforcer {
    private static final boolean DEBUG = false;
    private static final int LOG_NB_EVENTS = 20;
    public static final int METHOD_AUDIO_MANAGER_ADJUST_STREAM_VOLUME = 103;
    public static final int METHOD_AUDIO_MANAGER_ADJUST_SUGGESTED_STREAM_VOLUME = 102;
    public static final int METHOD_AUDIO_MANAGER_ADJUST_VOLUME = 101;
    public static final int METHOD_AUDIO_MANAGER_REQUEST_AUDIO_FOCUS = 300;
    public static final int METHOD_AUDIO_MANAGER_SET_RINGER_MODE = 200;
    public static final int METHOD_AUDIO_MANAGER_SET_STREAM_VOLUME = 100;
    private static final java.lang.String TAG = "AS.HardeningEnforcer";
    final android.app.ActivityManager mActivityManager;
    final android.app.AppOpsManager mAppOps;
    final android.content.Context mContext;
    final com.android.server.utils.EventLogger mEventLogger = new com.android.server.utils.EventLogger(20, "Hardening enforcement");
    final boolean mIsAutomotive;
    final android.content.pm.PackageManager mPackageManager;

    public HardeningEnforcer(android.content.Context context, boolean z, android.app.AppOpsManager appOpsManager, android.content.pm.PackageManager packageManager) {
        this.mContext = context;
        this.mIsAutomotive = z;
        this.mAppOps = appOpsManager;
        this.mActivityManager = (android.app.ActivityManager) context.getSystemService(android.app.ActivityManager.class);
        this.mPackageManager = packageManager;
    }

    protected void dump(java.io.PrintWriter printWriter) {
        this.mEventLogger.dump(printWriter);
    }

    protected boolean blockVolumeMethod(int i) {
        if (!this.mIsAutomotive || !android.media.audio.Flags.autoPublicVolumeApiHardening() || this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED") == 0 || android.os.Binder.getCallingUid() < 10000) {
            return false;
        }
        android.util.Slog.e(TAG, "Preventing volume method " + i + " for " + getPackNameForUid(android.os.Binder.getCallingUid()));
        return true;
    }

    protected boolean blockFocusMethod(int i, int i2, @android.annotation.NonNull java.lang.String str, int i3, @android.annotation.NonNull java.lang.String str2) {
        if (str2.isEmpty()) {
            str2 = getPackNameForUid(i);
        }
        if (checkAppOp(32, i, str2)) {
            return false;
        }
        this.mEventLogger.enqueueAndSlog("Focus request DENIED for uid:" + i + " clientId:" + str + " req:" + i3 + " procState:" + this.mActivityManager.getUidProcessState(i), 0, TAG);
        return true;
    }

    private java.lang.String getPackNameForUid(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid != null && packagesForUid.length != 0 && !android.text.TextUtils.isEmpty(packagesForUid[0])) {
                return packagesForUid[0];
            }
            return "[" + i + "]";
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean checkAppOp(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        if (this.mAppOps.checkOpNoThrow(i, i2, str) != 0) {
            return false;
        }
        return true;
    }
}
