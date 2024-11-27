package com.android.server.usage;

/* loaded from: classes2.dex */
public class IntervalStats {
    public static final int CURRENT_MAJOR_VERSION = 1;
    public static final int CURRENT_MINOR_VERSION = 1;
    private static final java.lang.String TAG = "IntervalStats";
    public android.content.res.Configuration activeConfiguration;
    public long beginTime;
    public long endTime;
    public long lastTimeSaved;
    public int majorVersion = 1;
    public int minorVersion = 1;
    public final com.android.server.usage.IntervalStats.EventTracker interactiveTracker = new com.android.server.usage.IntervalStats.EventTracker();
    public final com.android.server.usage.IntervalStats.EventTracker nonInteractiveTracker = new com.android.server.usage.IntervalStats.EventTracker();
    public final com.android.server.usage.IntervalStats.EventTracker keyguardShownTracker = new com.android.server.usage.IntervalStats.EventTracker();
    public final com.android.server.usage.IntervalStats.EventTracker keyguardHiddenTracker = new com.android.server.usage.IntervalStats.EventTracker();
    public final android.util.ArrayMap<java.lang.String, android.app.usage.UsageStats> packageStats = new android.util.ArrayMap<>();
    public final android.util.SparseArray<android.app.usage.UsageStats> packageStatsObfuscated = new android.util.SparseArray<>();
    public final android.util.ArrayMap<android.content.res.Configuration, android.app.usage.ConfigurationStats> configurations = new android.util.ArrayMap<>();
    public final android.app.usage.EventList events = new android.app.usage.EventList();
    public final android.util.ArraySet<java.lang.String> mStringCache = new android.util.ArraySet<>();

    public static final class EventTracker {
        public int count;
        public long curStartTime;
        public long duration;
        public long lastEventTime;

        public void commitTime(long j) {
            if (this.curStartTime != 0) {
                this.duration += j - this.curStartTime;
                this.curStartTime = 0L;
            }
        }

        public void update(long j) {
            if (this.curStartTime == 0) {
                this.count++;
            }
            commitTime(j);
            this.curStartTime = j;
            this.lastEventTime = j;
        }

        void addToEventStats(java.util.List<android.app.usage.EventStats> list, int i, long j, long j2) {
            if (this.count != 0 || this.duration != 0) {
                android.app.usage.EventStats eventStats = new android.app.usage.EventStats();
                eventStats.mEventType = i;
                eventStats.mCount = this.count;
                eventStats.mTotalTime = this.duration;
                eventStats.mLastEventTime = this.lastEventTime;
                eventStats.mBeginTimeStamp = j;
                eventStats.mEndTimeStamp = j2;
                list.add(eventStats);
            }
        }
    }

    android.app.usage.UsageStats getOrCreateUsageStats(java.lang.String str) {
        android.app.usage.UsageStats usageStats = this.packageStats.get(str);
        if (usageStats == null) {
            android.app.usage.UsageStats usageStats2 = new android.app.usage.UsageStats();
            usageStats2.mPackageName = getCachedStringRef(str);
            usageStats2.mBeginTimeStamp = this.beginTime;
            usageStats2.mEndTimeStamp = this.endTime;
            this.packageStats.put(usageStats2.mPackageName, usageStats2);
            return usageStats2;
        }
        return usageStats;
    }

    android.app.usage.ConfigurationStats getOrCreateConfigurationStats(android.content.res.Configuration configuration) {
        android.app.usage.ConfigurationStats configurationStats = this.configurations.get(configuration);
        if (configurationStats == null) {
            android.app.usage.ConfigurationStats configurationStats2 = new android.app.usage.ConfigurationStats();
            configurationStats2.mBeginTimeStamp = this.beginTime;
            configurationStats2.mEndTimeStamp = this.endTime;
            configurationStats2.mConfiguration = configuration;
            this.configurations.put(configuration, configurationStats2);
            return configurationStats2;
        }
        return configurationStats;
    }

    android.app.usage.UsageEvents.Event buildEvent(java.lang.String str, java.lang.String str2) {
        android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event();
        event.mPackage = getCachedStringRef(str);
        if (str2 != null) {
            event.mClass = getCachedStringRef(str2);
        }
        return event;
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0142, code lost:
    
        switch(r0.mEventType) {
            case 5: goto L35;
            case 8: goto L32;
            case 12: goto L29;
            case 30: goto L26;
            default: goto L38;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0148, code lost:
    
        if (r0.mLocusId != null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x014a, code lost:
    
        r0.mLocusId = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x014f, code lost:
    
        if (r0.mNotificationChannelId != null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0151, code lost:
    
        r0.mNotificationChannelId = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0156, code lost:
    
        if (r0.mShortcutId != null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0158, code lost:
    
        r0.mShortcutId = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x015d, code lost:
    
        if (r0.mConfiguration != null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x015f, code lost:
    
        r0.mConfiguration = new android.content.res.Configuration();
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0166, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    android.app.usage.UsageEvents.Event buildEvent(android.util.proto.ProtoInputStream protoInputStream, java.util.List<java.lang.String> list) throws java.io.IOException {
        android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event();
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    event.mPackage = getCachedStringRef(protoInputStream.readString(1138166333441L));
                    break;
                case 2:
                    event.mPackage = getCachedStringRef(list.get(protoInputStream.readInt(1120986464258L) - 1));
                    break;
                case 3:
                    event.mClass = getCachedStringRef(protoInputStream.readString(1138166333443L));
                    break;
                case 4:
                    event.mClass = getCachedStringRef(list.get(protoInputStream.readInt(1120986464260L) - 1));
                    break;
                case 5:
                    event.mTimeStamp = this.beginTime + protoInputStream.readLong(1112396529669L);
                    break;
                case 6:
                    event.mFlags = protoInputStream.readInt(1120986464262L);
                    break;
                case 7:
                    event.mEventType = protoInputStream.readInt(1120986464263L);
                    break;
                case 8:
                    event.mConfiguration = new android.content.res.Configuration();
                    event.mConfiguration.readFromProto(protoInputStream, 1146756268040L);
                    break;
                case 9:
                    event.mShortcutId = protoInputStream.readString(1138166333449L).intern();
                    break;
                case 11:
                    event.mBucketAndReason = protoInputStream.readInt(1120986464267L);
                    break;
                case 12:
                    event.mNotificationChannelId = protoInputStream.readString(1138166333452L);
                    break;
                case 13:
                    event.mNotificationChannelId = getCachedStringRef(list.get(protoInputStream.readInt(1120986464269L) - 1));
                    break;
                case 14:
                    event.mInstanceId = protoInputStream.readInt(1120986464270L);
                    break;
                case 15:
                    event.mTaskRootPackage = getCachedStringRef(list.get(protoInputStream.readInt(1120986464271L) - 1));
                    break;
                case 16:
                    event.mTaskRootClass = getCachedStringRef(list.get(protoInputStream.readInt(1120986464272L) - 1));
                    break;
                case 17:
                    event.mLocusId = getCachedStringRef(list.get(protoInputStream.readInt(1120986464273L) - 1));
                    break;
            }
        }
    }

    private boolean isStatefulEvent(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 26:
                return true;
            default:
                return false;
        }
    }

    private boolean isUserVisibleEvent(int i) {
        return (i == 6 || i == 11) ? false : true;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void update(java.lang.String str, java.lang.String str2, long j, int i, int i2) {
        if (i == 26 || i == 25) {
            int size = this.packageStats.size();
            for (int i3 = 0; i3 < size; i3++) {
                this.packageStats.valueAt(i3).update(null, j, i, i2);
            }
        } else {
            getOrCreateUsageStats(str).update(str2, j, i, i2);
        }
        if (j > this.endTime) {
            this.endTime = j;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void addEvent(android.app.usage.UsageEvents.Event event) {
        event.mPackage = getCachedStringRef(event.mPackage);
        if (event.mClass != null) {
            event.mClass = getCachedStringRef(event.mClass);
        }
        if (event.mTaskRootPackage != null) {
            event.mTaskRootPackage = getCachedStringRef(event.mTaskRootPackage);
        }
        if (event.mTaskRootClass != null) {
            event.mTaskRootClass = getCachedStringRef(event.mTaskRootClass);
        }
        if (event.mEventType == 12) {
            event.mNotificationChannelId = getCachedStringRef(event.mNotificationChannelId);
        }
        this.events.insert(event);
        if (event.mTimeStamp > this.endTime) {
            this.endTime = event.mTimeStamp;
        }
    }

    void updateChooserCounts(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.util.ArrayMap arrayMap;
        android.app.usage.UsageStats orCreateUsageStats = getOrCreateUsageStats(str);
        if (orCreateUsageStats.mChooserCounts == null) {
            orCreateUsageStats.mChooserCounts = new android.util.ArrayMap();
        }
        int indexOfKey = orCreateUsageStats.mChooserCounts.indexOfKey(str3);
        if (indexOfKey < 0) {
            arrayMap = new android.util.ArrayMap();
            orCreateUsageStats.mChooserCounts.put(str3, arrayMap);
        } else {
            arrayMap = (android.util.ArrayMap) orCreateUsageStats.mChooserCounts.valueAt(indexOfKey);
        }
        arrayMap.put(str2, java.lang.Integer.valueOf(((java.lang.Integer) arrayMap.getOrDefault(str2, 0)).intValue() + 1));
    }

    void updateConfigurationStats(android.content.res.Configuration configuration, long j) {
        if (this.activeConfiguration != null) {
            android.app.usage.ConfigurationStats configurationStats = this.configurations.get(this.activeConfiguration);
            configurationStats.mTotalTimeActive += j - configurationStats.mLastTimeActive;
            configurationStats.mLastTimeActive = j - 1;
        }
        if (configuration != null) {
            android.app.usage.ConfigurationStats orCreateConfigurationStats = getOrCreateConfigurationStats(configuration);
            orCreateConfigurationStats.mLastTimeActive = j;
            orCreateConfigurationStats.mActivationCount++;
            this.activeConfiguration = orCreateConfigurationStats.mConfiguration;
        }
        if (j > this.endTime) {
            this.endTime = j;
        }
    }

    void incrementAppLaunchCount(java.lang.String str) {
        getOrCreateUsageStats(str).mAppLaunchCount++;
    }

    void commitTime(long j) {
        this.interactiveTracker.commitTime(j);
        this.nonInteractiveTracker.commitTime(j);
        this.keyguardShownTracker.commitTime(j);
        this.keyguardHiddenTracker.commitTime(j);
    }

    void updateScreenInteractive(long j) {
        this.interactiveTracker.update(j);
        this.nonInteractiveTracker.commitTime(j);
    }

    void updateScreenNonInteractive(long j) {
        this.nonInteractiveTracker.update(j);
        this.interactiveTracker.commitTime(j);
    }

    void updateKeyguardShown(long j) {
        this.keyguardShownTracker.update(j);
        this.keyguardHiddenTracker.commitTime(j);
    }

    void updateKeyguardHidden(long j) {
        this.keyguardHiddenTracker.update(j);
        this.keyguardShownTracker.commitTime(j);
    }

    void addEventStatsTo(java.util.List<android.app.usage.EventStats> list) {
        this.interactiveTracker.addToEventStats(list, 15, this.beginTime, this.endTime);
        this.nonInteractiveTracker.addToEventStats(list, 16, this.beginTime, this.endTime);
        this.keyguardShownTracker.addToEventStats(list, 17, this.beginTime, this.endTime);
        this.keyguardHiddenTracker.addToEventStats(list, 18, this.beginTime, this.endTime);
    }

    private java.lang.String getCachedStringRef(java.lang.String str) {
        int indexOf = this.mStringCache.indexOf(str);
        if (indexOf < 0) {
            this.mStringCache.add(str);
            return str;
        }
        return this.mStringCache.valueAt(indexOf);
    }

    void upgradeIfNeeded() {
        if (this.majorVersion >= 1) {
            return;
        }
        this.majorVersion = 1;
    }

    private boolean deobfuscateUsageStats(com.android.server.usage.PackagesTokenData packagesTokenData) {
        com.android.server.usage.PackagesTokenData packagesTokenData2 = packagesTokenData;
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int size = this.packageStatsObfuscated.size();
        int i = 0;
        boolean z = false;
        while (i < size) {
            int keyAt = this.packageStatsObfuscated.keyAt(i);
            android.app.usage.UsageStats valueAt = this.packageStatsObfuscated.valueAt(i);
            valueAt.mPackageName = packagesTokenData2.getPackageString(keyAt);
            if (valueAt.mPackageName == null) {
                arraySet.add(java.lang.Integer.valueOf(keyAt));
                z = true;
            } else {
                int size2 = valueAt.mChooserCountsObfuscated.size();
                int i2 = 0;
                while (i2 < size2) {
                    android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                    java.lang.String string = packagesTokenData2.getString(keyAt, valueAt.mChooserCountsObfuscated.keyAt(i2));
                    if (string != null) {
                        android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) valueAt.mChooserCountsObfuscated.valueAt(i2);
                        int size3 = sparseIntArray.size();
                        int i3 = 0;
                        while (i3 < size3) {
                            java.lang.String string2 = packagesTokenData2.getString(keyAt, sparseIntArray.keyAt(i3));
                            if (string2 != null) {
                                arrayMap.put(string2, java.lang.Integer.valueOf(sparseIntArray.valueAt(i3)));
                            }
                            i3++;
                            packagesTokenData2 = packagesTokenData;
                        }
                        valueAt.mChooserCounts.put(string, arrayMap);
                    }
                    i2++;
                    packagesTokenData2 = packagesTokenData;
                }
                this.packageStats.put(valueAt.mPackageName, valueAt);
            }
            i++;
            packagesTokenData2 = packagesTokenData;
        }
        if (z) {
            android.util.Slog.d(TAG, "Unable to parse usage stats packages: " + java.util.Arrays.toString(arraySet.toArray()));
        }
        return z;
    }

    private boolean deobfuscateEvents(com.android.server.usage.PackagesTokenData packagesTokenData) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        boolean z = false;
        for (int size = this.events.size() - 1; size >= 0; size--) {
            android.app.usage.UsageEvents.Event event = this.events.get(size);
            int i = event.mPackageToken;
            event.mPackage = packagesTokenData.getPackageString(i);
            if (event.mPackage == null) {
                arraySet.add(java.lang.Integer.valueOf(i));
                this.events.remove(size);
                z = true;
            } else {
                if (event.mClassToken != -1) {
                    event.mClass = packagesTokenData.getString(i, event.mClassToken);
                }
                if (event.mTaskRootPackageToken != -1) {
                    event.mTaskRootPackage = packagesTokenData.getString(i, event.mTaskRootPackageToken);
                }
                if (event.mTaskRootClassToken != -1) {
                    event.mTaskRootClass = packagesTokenData.getString(i, event.mTaskRootClassToken);
                }
                switch (event.mEventType) {
                    case 5:
                        if (event.mConfiguration == null) {
                            event.mConfiguration = new android.content.res.Configuration();
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (event.mUserInteractionExtrasToken != null) {
                            java.lang.String string = packagesTokenData.getString(i, event.mUserInteractionExtrasToken.mCategoryToken);
                            java.lang.String string2 = packagesTokenData.getString(i, event.mUserInteractionExtrasToken.mActionToken);
                            if (android.text.TextUtils.isEmpty(string) || android.text.TextUtils.isEmpty(string2)) {
                                this.events.remove(size);
                                z = true;
                                break;
                            } else {
                                event.mExtras = new android.os.PersistableBundle();
                                event.mExtras.putString("android.app.usage.extra.EVENT_CATEGORY", string);
                                event.mExtras.putString("android.app.usage.extra.EVENT_ACTION", string2);
                                event.mUserInteractionExtrasToken = null;
                                break;
                            }
                        } else {
                            break;
                        }
                    case 8:
                        event.mShortcutId = packagesTokenData.getString(i, event.mShortcutIdToken);
                        if (event.mShortcutId == null) {
                            android.util.Slog.v(TAG, "Unable to parse shortcut " + event.mShortcutIdToken + " for package " + i);
                            this.events.remove(size);
                            z = true;
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        event.mNotificationChannelId = packagesTokenData.getString(i, event.mNotificationChannelIdToken);
                        if (event.mNotificationChannelId == null) {
                            android.util.Slog.v(TAG, "Unable to parse notification channel " + event.mNotificationChannelIdToken + " for package " + i);
                            this.events.remove(size);
                            z = true;
                            break;
                        } else {
                            break;
                        }
                    case 30:
                        event.mLocusId = packagesTokenData.getString(i, event.mLocusIdToken);
                        if (event.mLocusId == null) {
                            android.util.Slog.v(TAG, "Unable to parse locus " + event.mLocusIdToken + " for package " + i);
                            this.events.remove(size);
                            z = true;
                            break;
                        } else {
                            break;
                        }
                }
            }
        }
        if (z) {
            android.util.Slog.d(TAG, "Unable to parse event packages: " + java.util.Arrays.toString(arraySet.toArray()));
        }
        return z;
    }

    public boolean deobfuscateData(com.android.server.usage.PackagesTokenData packagesTokenData) {
        return deobfuscateUsageStats(packagesTokenData) || deobfuscateEvents(packagesTokenData);
    }

    private void obfuscateUsageStatsData(com.android.server.usage.PackagesTokenData packagesTokenData) {
        int packageTokenOrAdd;
        int size = this.packageStats.size();
        for (int i = 0; i < size; i++) {
            java.lang.String keyAt = this.packageStats.keyAt(i);
            android.app.usage.UsageStats valueAt = this.packageStats.valueAt(i);
            if (valueAt != null && (packageTokenOrAdd = packagesTokenData.getPackageTokenOrAdd(keyAt, valueAt.mEndTimeStamp)) != -1) {
                valueAt.mPackageToken = packageTokenOrAdd;
                int size2 = valueAt.mChooserCounts.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    java.lang.String str = (java.lang.String) valueAt.mChooserCounts.keyAt(i2);
                    android.util.ArrayMap arrayMap = (android.util.ArrayMap) valueAt.mChooserCounts.valueAt(i2);
                    if (arrayMap != null) {
                        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
                        int size3 = arrayMap.size();
                        for (int i3 = 0; i3 < size3; i3++) {
                            sparseIntArray.put(packagesTokenData.getTokenOrAdd(packageTokenOrAdd, keyAt, (java.lang.String) arrayMap.keyAt(i3)), ((java.lang.Integer) arrayMap.valueAt(i3)).intValue());
                        }
                        valueAt.mChooserCountsObfuscated.put(packagesTokenData.getTokenOrAdd(packageTokenOrAdd, keyAt, str), sparseIntArray);
                    }
                }
                this.packageStatsObfuscated.put(packageTokenOrAdd, valueAt);
            }
        }
    }

    private void obfuscateEventsData(com.android.server.usage.PackagesTokenData packagesTokenData) {
        for (int size = this.events.size() - 1; size >= 0; size--) {
            android.app.usage.UsageEvents.Event event = this.events.get(size);
            if (event != null) {
                int packageTokenOrAdd = packagesTokenData.getPackageTokenOrAdd(event.mPackage, event.mTimeStamp);
                if (packageTokenOrAdd == -1) {
                    this.events.remove(size);
                } else {
                    event.mPackageToken = packageTokenOrAdd;
                    if (!android.text.TextUtils.isEmpty(event.mClass)) {
                        event.mClassToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mClass);
                    }
                    if (!android.text.TextUtils.isEmpty(event.mTaskRootPackage)) {
                        event.mTaskRootPackageToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mTaskRootPackage);
                    }
                    if (!android.text.TextUtils.isEmpty(event.mTaskRootClass)) {
                        event.mTaskRootClassToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mTaskRootClass);
                    }
                    switch (event.mEventType) {
                        case 7:
                            if (event.mExtras != null && event.mExtras.size() != 0) {
                                java.lang.String string = event.mExtras.getString("android.app.usage.extra.EVENT_CATEGORY");
                                java.lang.String string2 = event.mExtras.getString("android.app.usage.extra.EVENT_ACTION");
                                if (!android.text.TextUtils.isEmpty(string) && !android.text.TextUtils.isEmpty(string2)) {
                                    event.mUserInteractionExtrasToken = new android.app.usage.UsageEvents.Event.UserInteractionEventExtrasToken();
                                    event.mUserInteractionExtrasToken.mCategoryToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, string);
                                    event.mUserInteractionExtrasToken.mActionToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, string2);
                                    break;
                                }
                            }
                            break;
                        case 8:
                            if (android.text.TextUtils.isEmpty(event.mShortcutId)) {
                                break;
                            } else {
                                event.mShortcutIdToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mShortcutId);
                                break;
                            }
                        case 12:
                            if (android.text.TextUtils.isEmpty(event.mNotificationChannelId)) {
                                break;
                            } else {
                                event.mNotificationChannelIdToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mNotificationChannelId);
                                break;
                            }
                        case 30:
                            if (android.text.TextUtils.isEmpty(event.mLocusId)) {
                                break;
                            } else {
                                event.mLocusIdToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mLocusId);
                                break;
                            }
                    }
                }
            }
        }
    }

    public void obfuscateData(com.android.server.usage.PackagesTokenData packagesTokenData) {
        obfuscateUsageStatsData(packagesTokenData);
        obfuscateEventsData(packagesTokenData);
    }
}
