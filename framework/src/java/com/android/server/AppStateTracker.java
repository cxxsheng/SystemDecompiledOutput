package com.android.server;

/* loaded from: classes5.dex */
public interface AppStateTracker {
    public static final java.lang.String TAG = "AppStateTracker";

    public interface BackgroundRestrictedAppListener {
        void updateBackgroundRestrictedForUidPackage(int i, java.lang.String str, boolean z);
    }

    void addBackgroundRestrictedAppListener(com.android.server.AppStateTracker.BackgroundRestrictedAppListener backgroundRestrictedAppListener);

    boolean isAppBackgroundRestricted(int i, java.lang.String str);
}
