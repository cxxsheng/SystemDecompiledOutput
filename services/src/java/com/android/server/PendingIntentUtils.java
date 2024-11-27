package com.android.server;

/* loaded from: classes.dex */
public class PendingIntentUtils {
    private PendingIntentUtils() {
    }

    public static android.os.Bundle createDontSendToRestrictedAppsBundle(@android.annotation.Nullable android.os.Bundle bundle) {
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setDontSendToRestrictedApps(true);
        makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
        if (bundle == null) {
            return makeBasic.toBundle();
        }
        bundle.putAll(makeBasic.toBundle());
        return bundle;
    }
}
