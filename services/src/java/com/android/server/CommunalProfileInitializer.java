package com.android.server;

/* loaded from: classes.dex */
public class CommunalProfileInitializer {
    private static final java.lang.String TAG = com.android.server.CommunalProfileInitializer.class.getSimpleName();
    private final com.android.server.am.ActivityManagerService mAms;
    private com.android.server.pm.UserManagerInternal mUmi = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);

    public CommunalProfileInitializer(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mAms = activityManagerService;
    }

    public void init(com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        com.android.server.utils.Slogf.i(TAG, "init())");
        timingsTraceAndSlog.traceBegin("createCommunalProfileIfNeeded");
        createCommunalProfileIfNeeded();
        timingsTraceAndSlog.traceEnd();
    }

    private void createCommunalProfileIfNeeded() {
        int communalProfileId = this.mUmi.getCommunalProfileId();
        if (communalProfileId != -10000) {
            com.android.server.utils.Slogf.d(TAG, "Found existing Communal Profile, userId=%d", java.lang.Integer.valueOf(communalProfileId));
            return;
        }
        com.android.server.utils.Slogf.d(TAG, "Creating a new Communal Profile");
        try {
            com.android.server.utils.Slogf.i(TAG, "Successfully created Communal Profile, userId=%d", java.lang.Integer.valueOf(this.mUmi.createUserEvenWhenDisallowed(null, "android.os.usertype.profile.COMMUNAL", 0, null, null).id));
        } catch (android.os.UserManager.CheckedUserOperationException e) {
            com.android.server.utils.Slogf.wtf(TAG, "Communal Profile creation failed", (java.lang.Throwable) e);
        }
    }

    static void removeCommunalProfileIfPresent() {
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        int communalProfileId = userManagerInternal.getCommunalProfileId();
        if (communalProfileId == -10000) {
            return;
        }
        com.android.server.utils.Slogf.d(TAG, "Removing existing Communal Profile, userId=%d", java.lang.Integer.valueOf(communalProfileId));
        if (!userManagerInternal.removeUserEvenWhenDisallowed(communalProfileId)) {
            com.android.server.utils.Slogf.e(TAG, "Failed to remove Communal Profile, userId=%d", java.lang.Integer.valueOf(communalProfileId));
        }
    }
}
