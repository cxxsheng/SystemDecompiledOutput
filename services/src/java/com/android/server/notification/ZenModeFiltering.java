package com.android.server.notification;

/* loaded from: classes2.dex */
public class ZenModeFiltering {
    private static final boolean DEBUG = com.android.server.notification.ZenModeHelper.DEBUG;
    static final com.android.server.notification.ZenModeFiltering.RepeatCallers REPEAT_CALLERS = new com.android.server.notification.ZenModeFiltering.RepeatCallers();
    private static final java.lang.String TAG = "ZenModeHelper";
    private final android.content.Context mContext;
    private android.content.ComponentName mDefaultPhoneApp;
    private final com.android.internal.util.NotificationMessagingUtil mMessagingUtil;

    public ZenModeFiltering(android.content.Context context) {
        this.mContext = context;
        this.mMessagingUtil = new com.android.internal.util.NotificationMessagingUtil(this.mContext, (java.lang.Object) null);
    }

    public ZenModeFiltering(android.content.Context context, com.android.internal.util.NotificationMessagingUtil notificationMessagingUtil) {
        this.mContext = context;
        this.mMessagingUtil = notificationMessagingUtil;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("mDefaultPhoneApp=");
        printWriter.println(this.mDefaultPhoneApp);
        printWriter.print(str);
        printWriter.print("RepeatCallers.mThresholdMinutes=");
        printWriter.println(REPEAT_CALLERS.mThresholdMinutes);
        synchronized (REPEAT_CALLERS) {
            try {
                if (!REPEAT_CALLERS.mTelCalls.isEmpty()) {
                    printWriter.print(str);
                    printWriter.println("RepeatCallers.mTelCalls=");
                    for (int i = 0; i < REPEAT_CALLERS.mTelCalls.size(); i++) {
                        printWriter.print(str);
                        printWriter.print("  ");
                        printWriter.print((java.lang.String) REPEAT_CALLERS.mTelCalls.keyAt(i));
                        printWriter.print(" at ");
                        printWriter.println(ts(((java.lang.Long) REPEAT_CALLERS.mTelCalls.valueAt(i)).longValue()));
                    }
                }
                if (!REPEAT_CALLERS.mOtherCalls.isEmpty()) {
                    printWriter.print(str);
                    printWriter.println("RepeatCallers.mOtherCalls=");
                    for (int i2 = 0; i2 < REPEAT_CALLERS.mOtherCalls.size(); i2++) {
                        printWriter.print(str);
                        printWriter.print("  ");
                        printWriter.print((java.lang.String) REPEAT_CALLERS.mOtherCalls.keyAt(i2));
                        printWriter.print(" at ");
                        printWriter.println(ts(((java.lang.Long) REPEAT_CALLERS.mOtherCalls.valueAt(i2)).longValue()));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static java.lang.String ts(long j) {
        return new java.util.Date(j) + " (" + j + ")";
    }

    public static boolean matchesCallFilter(android.content.Context context, int i, android.app.NotificationManager.Policy policy, android.os.UserHandle userHandle, android.os.Bundle bundle, com.android.server.notification.ValidateNotificationPeople validateNotificationPeople, int i2, float f, int i3) {
        if (i == 2) {
            com.android.server.notification.ZenLog.traceMatchesCallFilter(false, "no interruptions", i3);
            return false;
        }
        if (i == 3) {
            com.android.server.notification.ZenLog.traceMatchesCallFilter(false, "alarms only", i3);
            return false;
        }
        if (i == 1) {
            if (policy.allowRepeatCallers() && REPEAT_CALLERS.isRepeat(context, bundle, null)) {
                com.android.server.notification.ZenLog.traceMatchesCallFilter(true, "repeat caller", i3);
                return true;
            }
            if (!policy.allowCalls()) {
                com.android.server.notification.ZenLog.traceMatchesCallFilter(false, "calls not allowed", i3);
                return false;
            }
            if (validateNotificationPeople != null) {
                float contactAffinity = validateNotificationPeople.getContactAffinity(userHandle, bundle, i2, f);
                boolean audienceMatches = audienceMatches(policy.allowCallsFrom(), contactAffinity);
                com.android.server.notification.ZenLog.traceMatchesCallFilter(audienceMatches, "contact affinity " + contactAffinity, i3);
                return audienceMatches;
            }
        }
        com.android.server.notification.ZenLog.traceMatchesCallFilter(true, "no restrictions", i3);
        return true;
    }

    private static android.os.Bundle extras(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord == null || notificationRecord.getSbn() == null || notificationRecord.getSbn().getNotification() == null) {
            return null;
        }
        return notificationRecord.getSbn().getNotification().extras;
    }

    protected void recordCall(com.android.server.notification.NotificationRecord notificationRecord) {
        REPEAT_CALLERS.recordCall(this.mContext, extras(notificationRecord), notificationRecord.getPhoneNumbers());
    }

    private boolean canRecordBypassDnd(com.android.server.notification.NotificationRecord notificationRecord, android.app.NotificationManager.Policy policy) {
        boolean z = notificationRecord.getPackagePriority() == 2;
        if (android.app.Flags.modesApi()) {
            return z && policy.allowPriorityChannels();
        }
        return z;
    }

    public boolean shouldIntercept(int i, android.app.NotificationManager.Policy policy, com.android.server.notification.NotificationRecord notificationRecord) {
        if (i == 0) {
            return false;
        }
        if (isCritical(notificationRecord)) {
            maybeLogInterceptDecision(notificationRecord, false, "criticalNotification");
            return false;
        }
        if (android.app.NotificationManager.Policy.areAllVisualEffectsSuppressed(policy.suppressedVisualEffects) && com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(notificationRecord.getSbn().getPackageName()) && 48 == notificationRecord.getSbn().getId()) {
            maybeLogInterceptDecision(notificationRecord, false, "systemDndChangedNotification");
            return false;
        }
        switch (i) {
            case 1:
                if (canRecordBypassDnd(notificationRecord, policy)) {
                    maybeLogInterceptDecision(notificationRecord, false, "priorityApp");
                    break;
                } else if (isAlarm(notificationRecord)) {
                    if (policy.allowAlarms()) {
                        maybeLogInterceptDecision(notificationRecord, false, "allowedAlarm");
                        break;
                    } else {
                        maybeLogInterceptDecision(notificationRecord, true, "!allowAlarms");
                        break;
                    }
                } else if (isEvent(notificationRecord)) {
                    if (policy.allowEvents()) {
                        maybeLogInterceptDecision(notificationRecord, false, "allowedEvent");
                        break;
                    } else {
                        maybeLogInterceptDecision(notificationRecord, true, "!allowEvents");
                        break;
                    }
                } else if (isReminder(notificationRecord)) {
                    if (policy.allowReminders()) {
                        maybeLogInterceptDecision(notificationRecord, false, "allowedReminder");
                        break;
                    } else {
                        maybeLogInterceptDecision(notificationRecord, true, "!allowReminders");
                        break;
                    }
                } else if (isMedia(notificationRecord)) {
                    if (policy.allowMedia()) {
                        maybeLogInterceptDecision(notificationRecord, false, "allowedMedia");
                        break;
                    } else {
                        maybeLogInterceptDecision(notificationRecord, true, "!allowMedia");
                        break;
                    }
                } else if (isSystem(notificationRecord)) {
                    if (policy.allowSystem()) {
                        maybeLogInterceptDecision(notificationRecord, false, "allowedSystem");
                        break;
                    } else {
                        maybeLogInterceptDecision(notificationRecord, true, "!allowSystem");
                        break;
                    }
                } else {
                    if (isConversation(notificationRecord) && policy.allowConversations()) {
                        if (policy.priorityConversationSenders == 1) {
                            maybeLogInterceptDecision(notificationRecord, false, "conversationAnyone");
                            break;
                        } else if (policy.priorityConversationSenders == 2 && notificationRecord.getChannel().isImportantConversation()) {
                            maybeLogInterceptDecision(notificationRecord, false, "conversationMatches");
                            break;
                        }
                    }
                    if (isCall(notificationRecord)) {
                        if (policy.allowRepeatCallers() && REPEAT_CALLERS.isRepeat(this.mContext, extras(notificationRecord), notificationRecord.getPhoneNumbers())) {
                            maybeLogInterceptDecision(notificationRecord, false, "repeatCaller");
                            break;
                        } else if (!policy.allowCalls()) {
                            maybeLogInterceptDecision(notificationRecord, true, "!allowCalls");
                            break;
                        } else {
                            break;
                        }
                    } else if (!isMessage(notificationRecord)) {
                        maybeLogInterceptDecision(notificationRecord, true, "!priority");
                        break;
                    } else if (!policy.allowMessages()) {
                        maybeLogInterceptDecision(notificationRecord, true, "!allowMessages");
                        break;
                    } else {
                        break;
                    }
                }
                break;
            case 2:
                maybeLogInterceptDecision(notificationRecord, true, "none");
                break;
            case 3:
                if (isAlarm(notificationRecord)) {
                    maybeLogInterceptDecision(notificationRecord, false, com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
                    break;
                } else {
                    maybeLogInterceptDecision(notificationRecord, true, "alarmsOnly");
                    break;
                }
            default:
                maybeLogInterceptDecision(notificationRecord, false, "unknownZenMode");
                break;
        }
        return false;
    }

    private static void maybeLogInterceptDecision(com.android.server.notification.NotificationRecord notificationRecord, boolean z, java.lang.String str) {
        boolean isIntercepted = notificationRecord.isIntercepted();
        if (notificationRecord.hasInterceptBeenSet() && isIntercepted == z) {
            return;
        }
        if (!notificationRecord.hasInterceptBeenSet()) {
            str = "new:" + str;
        } else if (isIntercepted != z) {
            str = "updated:" + str;
        }
        if (z) {
            com.android.server.notification.ZenLog.traceIntercepted(notificationRecord, str);
        } else {
            com.android.server.notification.ZenLog.traceNotIntercepted(notificationRecord, str);
        }
    }

    private boolean isCritical(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.getCriticality() < 2;
    }

    private static boolean shouldInterceptAudience(int i, com.android.server.notification.NotificationRecord notificationRecord) {
        float contactAffinity = notificationRecord.getContactAffinity();
        if (!audienceMatches(i, contactAffinity)) {
            maybeLogInterceptDecision(notificationRecord, true, "!audienceMatches,affinity=" + contactAffinity);
            return true;
        }
        maybeLogInterceptDecision(notificationRecord, false, "affinity=" + contactAffinity);
        return false;
    }

    protected static boolean isAlarm(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.isCategory(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM) || notificationRecord.isAudioAttributesUsage(4);
    }

    private static boolean isEvent(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.isCategory("event");
    }

    private static boolean isReminder(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.isCategory("reminder");
    }

    public boolean isCall(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord != null && (isDefaultPhoneApp(notificationRecord.getSbn().getPackageName()) || notificationRecord.isCategory("call"));
    }

    public boolean isMedia(com.android.server.notification.NotificationRecord notificationRecord) {
        android.media.AudioAttributes audioAttributes = notificationRecord.getAudioAttributes();
        return audioAttributes != null && android.media.AudioAttributes.SUPPRESSIBLE_USAGES.get(audioAttributes.getUsage()) == 5;
    }

    public boolean isSystem(com.android.server.notification.NotificationRecord notificationRecord) {
        android.media.AudioAttributes audioAttributes = notificationRecord.getAudioAttributes();
        return audioAttributes != null && android.media.AudioAttributes.SUPPRESSIBLE_USAGES.get(audioAttributes.getUsage()) == 6;
    }

    private boolean isDefaultPhoneApp(java.lang.String str) {
        if (this.mDefaultPhoneApp == null) {
            android.telecom.TelecomManager telecomManager = (android.telecom.TelecomManager) this.mContext.getSystemService("telecom");
            this.mDefaultPhoneApp = telecomManager != null ? telecomManager.getDefaultPhoneApp() : null;
            if (DEBUG) {
                android.util.Slog.d(TAG, "Default phone app: " + this.mDefaultPhoneApp);
            }
        }
        return (str == null || this.mDefaultPhoneApp == null || !str.equals(this.mDefaultPhoneApp.getPackageName())) ? false : true;
    }

    protected boolean isMessage(com.android.server.notification.NotificationRecord notificationRecord) {
        return this.mMessagingUtil.isMessaging(notificationRecord.getSbn());
    }

    protected boolean isConversation(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.isConversation();
    }

    private static boolean audienceMatches(int i, float f) {
        switch (i) {
            case 0:
                break;
            case 1:
                if (f >= 0.5f) {
                    break;
                }
                break;
            case 2:
                if (f >= 1.0f) {
                    break;
                }
                break;
            default:
                android.util.Slog.w(TAG, "Encountered unknown source: " + i);
                break;
        }
        return true;
    }

    protected void cleanUpCallersAfter(long j) {
        REPEAT_CALLERS.cleanUpCallsAfter(j);
    }

    private static class RepeatCallers {
        private final android.util.ArrayMap<java.lang.String, java.lang.Long> mOtherCalls;
        private final android.util.ArrayMap<java.lang.String, java.lang.Long> mTelCalls;
        private int mThresholdMinutes;

        private RepeatCallers() {
            this.mTelCalls = new android.util.ArrayMap<>();
            this.mOtherCalls = new android.util.ArrayMap<>();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void recordCall(android.content.Context context, android.os.Bundle bundle, android.util.ArraySet<java.lang.String> arraySet) {
            setThresholdMinutes(context);
            if (this.mThresholdMinutes <= 0 || bundle == null) {
                return;
            }
            java.lang.String[] extraPeople = com.android.server.notification.ValidateNotificationPeople.getExtraPeople(bundle);
            if (extraPeople == null || extraPeople.length == 0) {
                return;
            }
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            cleanUp(this.mTelCalls, currentTimeMillis);
            cleanUp(this.mOtherCalls, currentTimeMillis);
            recordCallers(extraPeople, arraySet, currentTimeMillis);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized boolean isRepeat(android.content.Context context, android.os.Bundle bundle, android.util.ArraySet<java.lang.String> arraySet) {
            setThresholdMinutes(context);
            if (this.mThresholdMinutes <= 0 || bundle == null) {
                return false;
            }
            java.lang.String[] extraPeople = com.android.server.notification.ValidateNotificationPeople.getExtraPeople(bundle);
            if (extraPeople == null || extraPeople.length == 0) {
                return false;
            }
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            cleanUp(this.mTelCalls, currentTimeMillis);
            cleanUp(this.mOtherCalls, currentTimeMillis);
            return checkCallers(context, extraPeople, arraySet);
        }

        private synchronized void cleanUp(android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap, long j) {
            try {
                for (int size = arrayMap.size() - 1; size >= 0; size--) {
                    long longValue = arrayMap.valueAt(size).longValue();
                    if (longValue > j || j - longValue > this.mThresholdMinutes * 1000 * 60) {
                        arrayMap.removeAt(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void cleanUpCallsAfter(long j) {
            try {
                for (int size = this.mTelCalls.size() - 1; size >= 0; size--) {
                    if (this.mTelCalls.valueAt(size).longValue() > j) {
                        this.mTelCalls.removeAt(size);
                    }
                }
                for (int size2 = this.mOtherCalls.size() - 1; size2 >= 0; size2--) {
                    if (this.mOtherCalls.valueAt(size2).longValue() > j) {
                        this.mOtherCalls.removeAt(size2);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        private void setThresholdMinutes(android.content.Context context) {
            if (this.mThresholdMinutes <= 0) {
                this.mThresholdMinutes = context.getResources().getInteger(android.R.integer.config_vibratorControlServiceDumpAggregationTimeMillisLimit);
            }
        }

        private synchronized void recordCallers(java.lang.String[] strArr, android.util.ArraySet<java.lang.String> arraySet, long j) {
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            for (java.lang.String str : strArr) {
                try {
                    if (str != null) {
                        android.net.Uri parse = android.net.Uri.parse(str);
                        if ("tel".equals(parse.getScheme())) {
                            java.lang.String decode = android.net.Uri.decode(parse.getSchemeSpecificPart());
                            if (decode != null) {
                                this.mTelCalls.put(decode, java.lang.Long.valueOf(j));
                                z = true;
                                z2 = true;
                            }
                        } else {
                            this.mOtherCalls.put(str, java.lang.Long.valueOf(j));
                            z = true;
                            z3 = true;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (arraySet != null) {
                java.util.Iterator<java.lang.String> it = arraySet.iterator();
                while (it.hasNext()) {
                    java.lang.String next = it.next();
                    if (next != null) {
                        this.mTelCalls.put(next, java.lang.Long.valueOf(j));
                        z = true;
                        z2 = true;
                    }
                }
            }
            if (z) {
                com.android.server.notification.ZenLog.traceRecordCaller(z2, z3);
            }
        }

        private synchronized boolean checkForNumber(java.lang.String str, java.lang.String str2) {
            if (this.mTelCalls.containsKey(str)) {
                return true;
            }
            java.lang.String decode = android.net.Uri.decode(str);
            if (decode != null) {
                java.util.Iterator<java.lang.String> it = this.mTelCalls.keySet().iterator();
                while (it.hasNext()) {
                    if (android.telephony.PhoneNumberUtils.areSamePhoneNumber(decode, it.next(), str2)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private synchronized boolean checkCallers(android.content.Context context, java.lang.String[] strArr, android.util.ArraySet<java.lang.String> arraySet) {
            boolean z;
            try {
                java.lang.String networkCountryIso = ((android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class)).getNetworkCountryIso();
                z = false;
                boolean z2 = false;
                boolean z3 = false;
                for (java.lang.String str : strArr) {
                    if (str != null) {
                        android.net.Uri parse = android.net.Uri.parse(str);
                        if ("tel".equals(parse.getScheme())) {
                            if (checkForNumber(parse.getSchemeSpecificPart(), networkCountryIso)) {
                                z = true;
                            }
                            z2 = true;
                        } else if (!this.mOtherCalls.containsKey(str)) {
                            z3 = true;
                        } else {
                            z = true;
                            z3 = true;
                        }
                    }
                }
                if (arraySet != null) {
                    java.util.Iterator<java.lang.String> it = arraySet.iterator();
                    while (it.hasNext()) {
                        if (checkForNumber(it.next(), networkCountryIso)) {
                            z = true;
                        }
                        z2 = true;
                    }
                }
                com.android.server.notification.ZenLog.traceCheckRepeatCaller(z, z2, z3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
            return z;
        }
    }
}
