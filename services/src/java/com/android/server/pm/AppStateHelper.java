package com.android.server.pm;

/* loaded from: classes2.dex */
public class AppStateHelper {
    private static final long ACTIVE_NETWORK_DURATION_MILLIS = java.util.concurrent.TimeUnit.MINUTES.toMillis(10);
    private final android.content.Context mContext;

    public AppStateHelper(android.content.Context context) {
        this.mContext = context;
    }

    private static boolean isPackageLoaded(android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo, java.lang.String str) {
        return com.android.internal.util.ArrayUtils.contains(runningAppProcessInfo.pkgList, str) || com.android.internal.util.ArrayUtils.contains(runningAppProcessInfo.pkgDeps, str);
    }

    private int getImportance(java.lang.String str) {
        return ((android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class)).getPackageImportance(str);
    }

    private boolean hasAudioFocus(java.lang.String str) {
        try {
            java.util.List focusStack = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio")).getFocusStack();
            int size = focusStack.size();
            return android.text.TextUtils.equals(str, size > 0 ? ((android.media.AudioFocusInfo) focusStack.get(size - 1)).getPackageName() : null);
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    private boolean hasVoiceCall() {
        try {
            int mode = ((android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class)).getMode();
            return mode == 2 || mode == 3;
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    private boolean isRecordingAudio(java.lang.String str) {
        try {
            java.util.Iterator<android.media.AudioRecordingConfiguration> it = ((android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class)).getActiveRecordingConfigurations().iterator();
            while (it.hasNext()) {
                if (android.text.TextUtils.equals(it.next().getClientPackageName(), str)) {
                    return true;
                }
            }
            return false;
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    private boolean isAppForeground(java.lang.String str) {
        return getImportance(str) <= 125;
    }

    public boolean isAppTopVisible(java.lang.String str) {
        return getImportance(str) <= 100;
    }

    private boolean hasActiveAudio(java.lang.String str) {
        return hasAudioFocus(str) || isRecordingAudio(str);
    }

    private boolean hasActiveNetwork(java.util.List<java.lang.String> list, int i) {
        android.content.pm.IPackageManager packageManager = android.app.ActivityThread.getPackageManager();
        android.app.usage.NetworkStatsManager networkStatsManager = (android.app.usage.NetworkStatsManager) this.mContext.getSystemService(android.app.usage.NetworkStatsManager.class);
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        try {
            android.app.usage.NetworkStats querySummary = networkStatsManager.querySummary(i, null, currentTimeMillis - ACTIVE_NETWORK_DURATION_MILLIS, currentTimeMillis);
            try {
                android.app.usage.NetworkStats.Bucket bucket = new android.app.usage.NetworkStats.Bucket();
                while (querySummary.hasNextBucket()) {
                    querySummary.getNextBucket(bucket);
                    if (list.contains(packageManager.getNameForUid(bucket.getUid())) && (bucket.getRxPackets() > 0 || bucket.getTxPackets() > 0)) {
                        querySummary.close();
                        return true;
                    }
                }
                querySummary.close();
                return false;
            } finally {
            }
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    private static boolean containsAny(java.lang.String[] strArr, java.util.List<java.lang.String> list) {
        int length = strArr.length;
        int size = list.size();
        int i = 0;
        int i2 = 0;
        while (i < length && i2 < size) {
            int compareTo = strArr[i].compareTo(list.get(i2));
            if (compareTo == 0) {
                return true;
            }
            if (compareTo < 0) {
                i++;
            } else {
                i2++;
            }
        }
        return false;
    }

    private void addLibraryDependency(final android.util.ArraySet<java.lang.String> arraySet, java.util.List<java.lang.String> list) {
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        final java.util.ArrayList arrayList2 = new java.util.ArrayList();
        final java.util.ArrayList arrayList3 = new java.util.ArrayList();
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            com.android.server.pm.pkg.AndroidPackage androidPackage = packageManagerInternal.getAndroidPackage(it.next());
            if (androidPackage != null) {
                arrayList.addAll(androidPackage.getLibraryNames());
                java.lang.String staticSharedLibraryName = androidPackage.getStaticSharedLibraryName();
                if (staticSharedLibraryName != null) {
                    arrayList2.add(staticSharedLibraryName);
                }
                java.lang.String sdkLibraryName = androidPackage.getSdkLibraryName();
                if (sdkLibraryName != null) {
                    arrayList3.add(sdkLibraryName);
                }
            }
        }
        if (arrayList.isEmpty() && arrayList2.isEmpty() && arrayList3.isEmpty()) {
            return;
        }
        java.util.Collections.sort(arrayList);
        java.util.Collections.sort(arrayList3);
        java.util.Collections.sort(arrayList2);
        packageManagerInternal.forEachPackageState(new java.util.function.Consumer() { // from class: com.android.server.pm.AppStateHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.AppStateHelper.lambda$addLibraryDependency$0(arrayList, arrayList2, arrayList3, arraySet, (com.android.server.pm.pkg.PackageStateInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addLibraryDependency$0(java.util.ArrayList arrayList, java.util.ArrayList arrayList2, java.util.ArrayList arrayList3, android.util.ArraySet arraySet, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
        if (pkg == null) {
            return;
        }
        if (containsAny(pkg.getUsesLibrariesSorted(), arrayList) || containsAny(pkg.getUsesOptionalLibrariesSorted(), arrayList) || containsAny(pkg.getUsesStaticLibrariesSorted(), arrayList2) || containsAny(pkg.getUsesSdkLibrariesSorted(), arrayList3)) {
            arraySet.add(pkg.getPackageName());
        }
    }

    private boolean hasActiveNetwork(java.util.List<java.lang.String> list) {
        if (hasActiveNetwork(list, 1) || hasActiveNetwork(list, 0)) {
            return true;
        }
        return false;
    }

    public boolean hasInteractingApp(java.util.List<java.lang.String> list) {
        for (java.lang.String str : list) {
            if (hasActiveAudio(str) || isAppTopVisible(str)) {
                return true;
            }
        }
        return hasActiveNetwork(list);
    }

    public boolean hasForegroundApp(java.util.List<java.lang.String> list) {
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            if (isAppForeground(it.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasTopVisibleApp(java.util.List<java.lang.String> list) {
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            if (isAppTopVisible(it.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean isInCall() {
        if (android.os.SystemProperties.getBoolean("debug.pm.gentle_update_test.is_in_call", false)) {
            return true;
        }
        return ((android.telecom.TelecomManager) this.mContext.getSystemService(android.telecom.TelecomManager.class)).isInCall() || hasVoiceCall();
    }

    public java.util.List<java.lang.String> getDependencyPackages(java.util.List<java.lang.String> list) {
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        for (android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class)).getRunningAppProcesses()) {
            java.util.Iterator<java.lang.String> it = list.iterator();
            while (it.hasNext()) {
                if (isPackageLoaded(runningAppProcessInfo, it.next())) {
                    for (java.lang.String str : runningAppProcessInfo.pkgList) {
                        arraySet.add(str);
                    }
                }
            }
        }
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        java.util.Iterator<java.lang.String> it2 = list.iterator();
        while (it2.hasNext()) {
            arraySet.addAll(activityManagerInternal.getClientPackages(it2.next()));
        }
        addLibraryDependency(arraySet, list);
        return new java.util.ArrayList(arraySet);
    }
}
