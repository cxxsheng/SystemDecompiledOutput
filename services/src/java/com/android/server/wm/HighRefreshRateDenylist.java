package com.android.server.wm;

/* loaded from: classes3.dex */
class HighRefreshRateDenylist {

    @android.annotation.NonNull
    private final java.lang.String[] mDefaultDenylist;
    private final android.util.ArraySet<java.lang.String> mDenylistedPackages = new android.util.ArraySet<>();
    private final java.lang.Object mLock = new java.lang.Object();

    static com.android.server.wm.HighRefreshRateDenylist create(@android.annotation.NonNull android.content.res.Resources resources) {
        return new com.android.server.wm.HighRefreshRateDenylist(resources, android.provider.DeviceConfigInterface.REAL);
    }

    @com.android.internal.annotations.VisibleForTesting
    HighRefreshRateDenylist(android.content.res.Resources resources, android.provider.DeviceConfigInterface deviceConfigInterface) {
        this.mDefaultDenylist = resources.getStringArray(android.R.array.config_hideWhenDisabled_packageNames);
        deviceConfigInterface.addOnPropertiesChangedListener("display_manager", com.android.internal.os.BackgroundThread.getExecutor(), new com.android.server.wm.HighRefreshRateDenylist.OnPropertiesChangedListener());
        updateDenylist(deviceConfigInterface.getProperty("display_manager", "high_refresh_rate_blacklist"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDenylist(@android.annotation.Nullable java.lang.String str) {
        synchronized (this.mLock) {
            try {
                this.mDenylistedPackages.clear();
                int i = 0;
                if (str != null) {
                    java.lang.String[] split = str.split(",");
                    int length = split.length;
                    while (i < length) {
                        java.lang.String trim = split[i].trim();
                        if (!trim.isEmpty()) {
                            this.mDenylistedPackages.add(trim);
                        }
                        i++;
                    }
                } else {
                    java.lang.String[] strArr = this.mDefaultDenylist;
                    int length2 = strArr.length;
                    while (i < length2) {
                        this.mDenylistedPackages.add(strArr[i]);
                        i++;
                    }
                }
            } finally {
            }
        }
    }

    boolean isDenylisted(java.lang.String str) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mDenylistedPackages.contains(str);
        }
        return contains;
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("High Refresh Rate Denylist");
        printWriter.println("  Packages:");
        synchronized (this.mLock) {
            try {
                java.util.Iterator<java.lang.String> it = this.mDenylistedPackages.iterator();
                while (it.hasNext()) {
                    printWriter.println("    " + it.next());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class OnPropertiesChangedListener implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        private OnPropertiesChangedListener() {
        }

        public void onPropertiesChanged(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains("high_refresh_rate_blacklist")) {
                com.android.server.wm.HighRefreshRateDenylist.this.updateDenylist(properties.getString("high_refresh_rate_blacklist", (java.lang.String) null));
            }
        }
    }
}
