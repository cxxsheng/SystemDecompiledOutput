package com.android.server.usage;

/* loaded from: classes2.dex */
final class UsageStatsXmlV1 {
    private static final java.lang.String ACTIVE_ATTR = "active";
    private static final java.lang.String APP_LAUNCH_COUNT_ATTR = "appLaunchCount";
    private static final java.lang.String CATEGORY_TAG = "category";
    private static final java.lang.String CHOOSER_COUNT_TAG = "chosen_action";
    private static final java.lang.String CLASS_ATTR = "class";
    private static final java.lang.String CONFIGURATIONS_TAG = "configurations";
    private static final java.lang.String CONFIG_TAG = "config";
    private static final java.lang.String COUNT = "count";
    private static final java.lang.String COUNT_ATTR = "count";
    private static final java.lang.String END_TIME_ATTR = "endTime";
    private static final java.lang.String EVENT_LOG_TAG = "event-log";
    private static final java.lang.String EVENT_TAG = "event";
    private static final java.lang.String FLAGS_ATTR = "flags";
    private static final java.lang.String INSTANCE_ID_ATTR = "instanceId";
    private static final java.lang.String INTERACTIVE_TAG = "interactive";
    private static final java.lang.String KEYGUARD_HIDDEN_TAG = "keyguard-hidden";
    private static final java.lang.String KEYGUARD_SHOWN_TAG = "keyguard-shown";
    private static final java.lang.String LAST_EVENT_ATTR = "lastEvent";
    private static final java.lang.String LAST_TIME_ACTIVE_ATTR = "lastTimeActive";
    private static final java.lang.String LAST_TIME_SERVICE_USED_ATTR = "lastTimeServiceUsed";
    private static final java.lang.String LAST_TIME_VISIBLE_ATTR = "lastTimeVisible";
    private static final java.lang.String MAJOR_VERSION_ATTR = "majorVersion";
    private static final java.lang.String MINOR_VERSION_ATTR = "minorVersion";
    private static final java.lang.String NAME = "name";
    private static final java.lang.String NON_INTERACTIVE_TAG = "non-interactive";
    private static final java.lang.String NOTIFICATION_CHANNEL_ATTR = "notificationChannel";
    private static final java.lang.String PACKAGES_TAG = "packages";
    private static final java.lang.String PACKAGE_ATTR = "package";
    private static final java.lang.String PACKAGE_TAG = "package";
    private static final java.lang.String SHORTCUT_ID_ATTR = "shortcutId";
    private static final java.lang.String STANDBY_BUCKET_ATTR = "standbyBucket";
    private static final java.lang.String TAG = "UsageStatsXmlV1";
    private static final java.lang.String TIME_ATTR = "time";
    private static final java.lang.String TOTAL_TIME_ACTIVE_ATTR = "timeActive";
    private static final java.lang.String TOTAL_TIME_SERVICE_USED_ATTR = "timeServiceUsed";
    private static final java.lang.String TOTAL_TIME_VISIBLE_ATTR = "timeVisible";
    private static final java.lang.String TYPE_ATTR = "type";

    private static void loadUsageStats(org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.server.usage.IntervalStats intervalStats) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
        if (attributeValue == null) {
            throw new java.net.ProtocolException("no package attribute present");
        }
        android.app.usage.UsageStats orCreateUsageStats = intervalStats.getOrCreateUsageStats(attributeValue);
        orCreateUsageStats.mLastTimeUsed = intervalStats.beginTime + com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, LAST_TIME_ACTIVE_ATTR);
        try {
            orCreateUsageStats.mLastTimeVisible = intervalStats.beginTime + com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, LAST_TIME_VISIBLE_ATTR);
        } catch (java.io.IOException e) {
            android.util.Log.i(TAG, "Failed to parse mLastTimeVisible");
        }
        try {
            orCreateUsageStats.mLastTimeForegroundServiceUsed = intervalStats.beginTime + com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, LAST_TIME_SERVICE_USED_ATTR);
        } catch (java.io.IOException e2) {
            android.util.Log.i(TAG, "Failed to parse mLastTimeForegroundServiceUsed");
        }
        orCreateUsageStats.mTotalTimeInForeground = com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, TOTAL_TIME_ACTIVE_ATTR);
        try {
            orCreateUsageStats.mTotalTimeVisible = com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, TOTAL_TIME_VISIBLE_ATTR);
        } catch (java.io.IOException e3) {
            android.util.Log.i(TAG, "Failed to parse mTotalTimeVisible");
        }
        try {
            orCreateUsageStats.mTotalTimeForegroundServiceUsed = com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, TOTAL_TIME_SERVICE_USED_ATTR);
        } catch (java.io.IOException e4) {
            android.util.Log.i(TAG, "Failed to parse mTotalTimeForegroundServiceUsed");
        }
        orCreateUsageStats.mLastEvent = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, LAST_EVENT_ATTR);
        orCreateUsageStats.mAppLaunchCount = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, APP_LAUNCH_COUNT_ATTR, 0);
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                java.lang.String name = xmlPullParser.getName();
                if (next != 3 || !name.equals(com.android.server.pm.Settings.ATTR_PACKAGE)) {
                    if (next == 2 && name.equals(CHOOSER_COUNT_TAG)) {
                        loadChooserCounts(xmlPullParser, orCreateUsageStats, com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, "name"));
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private static void loadCountAndTime(org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.server.usage.IntervalStats.EventTracker eventTracker) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        eventTracker.count = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, 0);
        eventTracker.duration = com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, TIME_ATTR, 0L);
        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
    }

    private static void loadChooserCounts(org.xmlpull.v1.XmlPullParser xmlPullParser, android.app.usage.UsageStats usageStats, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (str == null) {
            return;
        }
        if (usageStats.mChooserCounts == null) {
            usageStats.mChooserCounts = new android.util.ArrayMap();
        }
        if (!usageStats.mChooserCounts.containsKey(str)) {
            usageStats.mChooserCounts.put(str, new android.util.ArrayMap());
        }
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                java.lang.String name = xmlPullParser.getName();
                if (next != 3 || !name.equals(CHOOSER_COUNT_TAG)) {
                    if (next == 2 && name.equals(CATEGORY_TAG)) {
                        ((android.util.ArrayMap) usageStats.mChooserCounts.get(str)).put(com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, "name"), java.lang.Integer.valueOf(com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT)));
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private static void loadConfigStats(org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.server.usage.IntervalStats intervalStats) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        android.content.res.Configuration.readXmlAttrs(xmlPullParser, configuration);
        android.app.usage.ConfigurationStats orCreateConfigurationStats = intervalStats.getOrCreateConfigurationStats(configuration);
        orCreateConfigurationStats.mLastTimeActive = intervalStats.beginTime + com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, LAST_TIME_ACTIVE_ATTR);
        orCreateConfigurationStats.mTotalTimeActive = com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, TOTAL_TIME_ACTIVE_ATTR);
        orCreateConfigurationStats.mActivationCount = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT);
        if (com.android.internal.util.XmlUtils.readBooleanAttribute(xmlPullParser, "active")) {
            intervalStats.activeConfiguration = orCreateConfigurationStats.mConfiguration;
        }
    }

    private static void loadEvent(org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.server.usage.IntervalStats intervalStats) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, com.android.server.pm.Settings.ATTR_PACKAGE);
        if (readStringAttribute == null) {
            throw new java.net.ProtocolException("no package attribute present");
        }
        android.app.usage.UsageEvents.Event buildEvent = intervalStats.buildEvent(readStringAttribute, com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, CLASS_ATTR));
        buildEvent.mFlags = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, FLAGS_ATTR, 0);
        buildEvent.mTimeStamp = intervalStats.beginTime + com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, TIME_ATTR);
        buildEvent.mEventType = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "type");
        try {
            buildEvent.mInstanceId = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, INSTANCE_ID_ATTR);
        } catch (java.io.IOException e) {
            android.util.Log.i(TAG, "Failed to parse mInstanceId");
        }
        switch (buildEvent.mEventType) {
            case 5:
                buildEvent.mConfiguration = new android.content.res.Configuration();
                android.content.res.Configuration.readXmlAttrs(xmlPullParser, buildEvent.mConfiguration);
                break;
            case 8:
                java.lang.String readStringAttribute2 = com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, SHORTCUT_ID_ATTR);
                buildEvent.mShortcutId = readStringAttribute2 != null ? readStringAttribute2.intern() : null;
                break;
            case 11:
                buildEvent.mBucketAndReason = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, STANDBY_BUCKET_ATTR, 0);
                break;
            case 12:
                java.lang.String readStringAttribute3 = com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, NOTIFICATION_CHANNEL_ATTR);
                buildEvent.mNotificationChannelId = readStringAttribute3 != null ? readStringAttribute3.intern() : null;
                break;
        }
        intervalStats.addEvent(buildEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0094, code lost:
    
        if (r1.equals(com.android.server.usage.UsageStatsXmlV1.NON_INTERACTIVE_TAG) != false) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void read(org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.server.usage.IntervalStats intervalStats) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        intervalStats.packageStats.clear();
        intervalStats.configurations.clear();
        intervalStats.activeConfiguration = null;
        intervalStats.events.clear();
        intervalStats.endTime = intervalStats.beginTime + com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, END_TIME_ATTR);
        try {
            intervalStats.majorVersion = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, MAJOR_VERSION_ATTR);
        } catch (java.io.IOException e) {
            android.util.Log.i(TAG, "Failed to parse majorVersion");
        }
        try {
            intervalStats.minorVersion = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, MINOR_VERSION_ATTR);
        } catch (java.io.IOException e2) {
            android.util.Log.i(TAG, "Failed to parse minorVersion");
        }
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            char c = 1;
            if (next != 1) {
                if (next != 3 || xmlPullParser.getDepth() > depth) {
                    if (next == 2) {
                        java.lang.String name = xmlPullParser.getName();
                        switch (name.hashCode()) {
                            case -1354792126:
                                if (name.equals(CONFIG_TAG)) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1169351247:
                                if (name.equals(KEYGUARD_HIDDEN_TAG)) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -807157790:
                                break;
                            case -807062458:
                                if (name.equals(com.android.server.pm.Settings.ATTR_PACKAGE)) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 96891546:
                                if (name.equals(EVENT_TAG)) {
                                    c = 6;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 526608426:
                                if (name.equals(KEYGUARD_SHOWN_TAG)) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1844104930:
                                if (name.equals(INTERACTIVE_TAG)) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                loadCountAndTime(xmlPullParser, intervalStats.interactiveTracker);
                                break;
                            case 1:
                                loadCountAndTime(xmlPullParser, intervalStats.nonInteractiveTracker);
                                break;
                            case 2:
                                loadCountAndTime(xmlPullParser, intervalStats.keyguardShownTracker);
                                break;
                            case 3:
                                loadCountAndTime(xmlPullParser, intervalStats.keyguardHiddenTracker);
                                break;
                            case 4:
                                loadUsageStats(xmlPullParser, intervalStats);
                                break;
                            case 5:
                                loadConfigStats(xmlPullParser, intervalStats);
                                break;
                            case 6:
                                loadEvent(xmlPullParser, intervalStats);
                                break;
                        }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private UsageStatsXmlV1() {
    }
}
