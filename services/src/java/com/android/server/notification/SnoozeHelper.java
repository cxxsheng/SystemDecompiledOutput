package com.android.server.notification;

/* loaded from: classes2.dex */
public final class SnoozeHelper {
    static final int CONCURRENT_SNOOZE_LIMIT = 500;
    static final java.lang.String EXTRA_KEY = "key";
    private static final java.lang.String EXTRA_USER_ID = "userId";
    private static final java.lang.String INDENT = "    ";
    static final int MAX_STRING_LENGTH = 1000;
    private static final java.lang.String REPOST_SCHEME = "repost";
    private static final int REQUEST_CODE_REPOST = 1;
    private static final java.lang.String XML_SNOOZED_NOTIFICATION = "notification";
    private static final java.lang.String XML_SNOOZED_NOTIFICATION_CONTEXT = "context";
    private static final java.lang.String XML_SNOOZED_NOTIFICATION_CONTEXT_ID = "id";
    private static final java.lang.String XML_SNOOZED_NOTIFICATION_KEY = "key";
    private static final java.lang.String XML_SNOOZED_NOTIFICATION_TIME = "time";
    public static final int XML_SNOOZED_NOTIFICATION_VERSION = 1;
    private static final java.lang.String XML_SNOOZED_NOTIFICATION_VERSION_LABEL = "version";
    protected static final java.lang.String XML_TAG_NAME = "snoozed-notifications";
    private android.app.AlarmManager mAm;
    private com.android.server.notification.SnoozeHelper.Callback mCallback;
    private final android.content.Context mContext;
    private final com.android.server.notification.ManagedServices.UserProfiles mUserProfiles;
    private static final java.lang.String TAG = "SnoozeHelper";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.lang.String REPOST_ACTION = com.android.server.notification.SnoozeHelper.class.getSimpleName() + ".EVALUATE";
    private android.util.ArrayMap<java.lang.String, com.android.server.notification.NotificationRecord> mSnoozedNotifications = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, java.lang.Long> mPersistedSnoozedNotifications = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, java.lang.String> mPersistedSnoozedNotificationsWithContext = new android.util.ArrayMap<>();
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.notification.SnoozeHelper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (com.android.server.notification.SnoozeHelper.DEBUG) {
                android.util.Slog.d(com.android.server.notification.SnoozeHelper.TAG, "Reposting notification");
            }
            if (com.android.server.notification.SnoozeHelper.REPOST_ACTION.equals(intent.getAction())) {
                com.android.server.notification.SnoozeHelper.this.repost(intent.getStringExtra("key"), intent.getIntExtra("userId", 0), false);
            }
        }
    };

    protected interface Callback {
        void repost(int i, com.android.server.notification.NotificationRecord notificationRecord, boolean z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface Inserter<T> {
        void insert(T t) throws java.io.IOException;
    }

    public SnoozeHelper(android.content.Context context, com.android.server.notification.SnoozeHelper.Callback callback, com.android.server.notification.ManagedServices.UserProfiles userProfiles) {
        this.mContext = context;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter(REPOST_ACTION);
        intentFilter.addDataScheme(REPOST_SCHEME);
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, 2);
        this.mAm = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        this.mCallback = callback;
        this.mUserProfiles = userProfiles;
    }

    protected boolean canSnooze(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mSnoozedNotifications.size() + i <= 500 && this.mPersistedSnoozedNotifications.size() + this.mPersistedSnoozedNotificationsWithContext.size() + i <= 500) {
                    return true;
                }
                return false;
            } finally {
            }
        }
    }

    @android.annotation.NonNull
    protected java.lang.Long getSnoozeTimeForUnpostedNotification(int i, java.lang.String str, java.lang.String str2) {
        java.lang.Long l;
        synchronized (this.mLock) {
            l = this.mPersistedSnoozedNotifications.get(getTrimmedString(str2));
        }
        if (l == null) {
            return 0L;
        }
        return l;
    }

    protected java.lang.String getSnoozeContextForUnpostedNotification(int i, java.lang.String str, java.lang.String str2) {
        java.lang.String str3;
        synchronized (this.mLock) {
            str3 = this.mPersistedSnoozedNotificationsWithContext.get(getTrimmedString(str2));
        }
        return str3;
    }

    protected boolean isSnoozed(int i, java.lang.String str, java.lang.String str2) {
        boolean containsKey;
        synchronized (this.mLock) {
            containsKey = this.mSnoozedNotifications.containsKey(str2);
        }
        return containsKey;
    }

    protected java.util.Collection<com.android.server.notification.NotificationRecord> getSnoozed(int i, java.lang.String str) {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                arrayList = new java.util.ArrayList();
                for (com.android.server.notification.NotificationRecord notificationRecord : this.mSnoozedNotifications.values()) {
                    if (notificationRecord.getUserId() == i && notificationRecord.getSbn().getPackageName().equals(str)) {
                        arrayList.add(notificationRecord);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    java.util.ArrayList<com.android.server.notification.NotificationRecord> getNotifications(java.lang.String str, java.lang.String str2, java.lang.Integer num) {
        java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList = new java.util.ArrayList<>();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mSnoozedNotifications.size(); i++) {
                try {
                    com.android.server.notification.NotificationRecord valueAt = this.mSnoozedNotifications.valueAt(i);
                    if (valueAt.getSbn().getPackageName().equals(str) && valueAt.getUserId() == num.intValue() && java.util.Objects.equals(valueAt.getSbn().getGroup(), str2)) {
                        arrayList.add(valueAt);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    protected com.android.server.notification.NotificationRecord getNotification(java.lang.String str) {
        com.android.server.notification.NotificationRecord notificationRecord;
        synchronized (this.mLock) {
            notificationRecord = this.mSnoozedNotifications.get(str);
        }
        return notificationRecord;
    }

    @android.annotation.NonNull
    protected java.util.List<com.android.server.notification.NotificationRecord> getSnoozed() {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new java.util.ArrayList();
            arrayList.addAll(this.mSnoozedNotifications.values());
        }
        return arrayList;
    }

    protected void snooze(com.android.server.notification.NotificationRecord notificationRecord, long j) {
        java.lang.String key = notificationRecord.getKey();
        snooze(notificationRecord);
        scheduleRepost(key, j);
        java.lang.Long valueOf = java.lang.Long.valueOf(java.lang.System.currentTimeMillis() + j);
        synchronized (this.mLock) {
            this.mPersistedSnoozedNotifications.put(getTrimmedString(key), valueOf);
        }
    }

    protected void snooze(com.android.server.notification.NotificationRecord notificationRecord, java.lang.String str) {
        if (str != null) {
            synchronized (this.mLock) {
                this.mPersistedSnoozedNotificationsWithContext.put(getTrimmedString(notificationRecord.getKey()), getTrimmedString(str));
            }
        }
        snooze(notificationRecord);
    }

    private void snooze(com.android.server.notification.NotificationRecord notificationRecord) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Snoozing " + notificationRecord.getKey());
        }
        synchronized (this.mLock) {
            this.mSnoozedNotifications.put(notificationRecord.getKey(), notificationRecord);
        }
    }

    private java.lang.String getTrimmedString(java.lang.String str) {
        if (str != null && str.length() > 1000) {
            return str.substring(0, 1000);
        }
        return str;
    }

    protected boolean cancel(int i, java.lang.String str, java.lang.String str2, int i2) {
        synchronized (this.mLock) {
            try {
                for (java.util.Map.Entry<java.lang.String, com.android.server.notification.NotificationRecord> entry : this.mSnoozedNotifications.entrySet()) {
                    android.service.notification.StatusBarNotification sbn = entry.getValue().getSbn();
                    if (sbn.getPackageName().equals(str) && sbn.getUserId() == i && java.util.Objects.equals(sbn.getTag(), str2) && sbn.getId() == i2) {
                        entry.getValue().isCanceled = true;
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void cancel(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mSnoozedNotifications.size() == 0) {
                    return;
                }
                android.util.IntArray intArray = new android.util.IntArray();
                intArray.add(i);
                if (z) {
                    intArray = this.mUserProfiles.getCurrentProfileIds();
                }
                for (com.android.server.notification.NotificationRecord notificationRecord : this.mSnoozedNotifications.values()) {
                    if (intArray.binarySearch(notificationRecord.getUserId()) >= 0) {
                        notificationRecord.isCanceled = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected boolean cancel(int i, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                int size = this.mSnoozedNotifications.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.notification.NotificationRecord valueAt = this.mSnoozedNotifications.valueAt(i2);
                    if (valueAt.getSbn().getPackageName().equals(str) && valueAt.getUserId() == i) {
                        valueAt.isCanceled = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return true;
    }

    protected void update(int i, com.android.server.notification.NotificationRecord notificationRecord) {
        synchronized (this.mLock) {
            try {
                if (this.mSnoozedNotifications.containsKey(notificationRecord.getKey())) {
                    this.mSnoozedNotifications.put(notificationRecord.getKey(), notificationRecord);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void repostAll(android.util.IntArray intArray) {
        synchronized (this.mLock) {
            try {
                for (com.android.server.notification.NotificationRecord notificationRecord : getSnoozed()) {
                    if (intArray.binarySearch(notificationRecord.getUserId()) >= 0) {
                        repost(notificationRecord.getKey(), notificationRecord.getUserId(), false);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void repost(java.lang.String str, boolean z) {
        synchronized (this.mLock) {
            try {
                com.android.server.notification.NotificationRecord notificationRecord = this.mSnoozedNotifications.get(str);
                if (notificationRecord != null) {
                    repost(str, notificationRecord.getUserId(), z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void repost(java.lang.String str, int i, boolean z) {
        com.android.server.notification.NotificationRecord remove;
        java.lang.String trimmedString = getTrimmedString(str);
        synchronized (this.mLock) {
            this.mPersistedSnoozedNotifications.remove(trimmedString);
            this.mPersistedSnoozedNotificationsWithContext.remove(trimmedString);
            remove = this.mSnoozedNotifications.remove(str);
        }
        if (remove != null && !remove.isCanceled) {
            this.mAm.cancel(createPendingIntent(remove.getKey()));
            com.android.internal.logging.MetricsLogger.action(remove.getLogMaker().setCategory(831).setType(1));
            this.mCallback.repost(remove.getUserId(), remove, z);
        }
    }

    protected void repostGroupSummary(java.lang.String str, int i, java.lang.String str2) {
        java.lang.String str3;
        synchronized (this.mLock) {
            try {
                int size = this.mSnoozedNotifications.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        str3 = null;
                        break;
                    }
                    com.android.server.notification.NotificationRecord valueAt = this.mSnoozedNotifications.valueAt(i2);
                    if (!valueAt.getSbn().getPackageName().equals(str) || valueAt.getUserId() != i || !valueAt.getSbn().isGroup() || !valueAt.getNotification().isGroupSummary() || !str2.equals(valueAt.getGroupKey())) {
                        i2++;
                    } else {
                        str3 = valueAt.getKey();
                        break;
                    }
                }
                if (str3 != null) {
                    final com.android.server.notification.NotificationRecord remove = this.mSnoozedNotifications.remove(str3);
                    java.lang.String trimmedString = getTrimmedString(str3);
                    this.mPersistedSnoozedNotificationsWithContext.remove(trimmedString);
                    this.mPersistedSnoozedNotifications.remove(trimmedString);
                    if (remove != null && !remove.isCanceled) {
                        new java.lang.Runnable() { // from class: com.android.server.notification.SnoozeHelper$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.notification.SnoozeHelper.this.lambda$repostGroupSummary$0(remove);
                            }
                        }.run();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$repostGroupSummary$0(com.android.server.notification.NotificationRecord notificationRecord) {
        com.android.internal.logging.MetricsLogger.action(notificationRecord.getLogMaker().setCategory(831).setType(1));
        this.mCallback.repost(notificationRecord.getUserId(), notificationRecord, false);
    }

    protected void clearData(int i, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mSnoozedNotifications.size() - 1; size >= 0; size--) {
                    final com.android.server.notification.NotificationRecord valueAt = this.mSnoozedNotifications.valueAt(size);
                    if (valueAt.getUserId() == i && valueAt.getSbn().getPackageName().equals(str)) {
                        this.mSnoozedNotifications.removeAt(size);
                        java.lang.String trimmedString = getTrimmedString(valueAt.getKey());
                        this.mPersistedSnoozedNotificationsWithContext.remove(trimmedString);
                        this.mPersistedSnoozedNotifications.remove(trimmedString);
                        new java.lang.Runnable() { // from class: com.android.server.notification.SnoozeHelper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.notification.SnoozeHelper.this.lambda$clearData$1(valueAt);
                            }
                        }.run();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearData$1(com.android.server.notification.NotificationRecord notificationRecord) {
        this.mAm.cancel(createPendingIntent(notificationRecord.getKey()));
        com.android.internal.logging.MetricsLogger.action(notificationRecord.getLogMaker().setCategory(831).setType(5));
    }

    protected void clearData(int i) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mSnoozedNotifications.size() - 1; size >= 0; size--) {
                    final com.android.server.notification.NotificationRecord valueAt = this.mSnoozedNotifications.valueAt(size);
                    if (valueAt.getUserId() == i) {
                        this.mSnoozedNotifications.removeAt(size);
                        java.lang.String trimmedString = getTrimmedString(valueAt.getKey());
                        this.mPersistedSnoozedNotificationsWithContext.remove(trimmedString);
                        this.mPersistedSnoozedNotifications.remove(trimmedString);
                        new java.lang.Runnable() { // from class: com.android.server.notification.SnoozeHelper$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.notification.SnoozeHelper.this.lambda$clearData$2(valueAt);
                            }
                        }.run();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearData$2(com.android.server.notification.NotificationRecord notificationRecord) {
        this.mAm.cancel(createPendingIntent(notificationRecord.getKey()));
        com.android.internal.logging.MetricsLogger.action(notificationRecord.getLogMaker().setCategory(831).setType(5));
    }

    private android.app.PendingIntent createPendingIntent(java.lang.String str) {
        return android.app.PendingIntent.getBroadcast(this.mContext, 1, new android.content.Intent(REPOST_ACTION).setPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME).setData(new android.net.Uri.Builder().scheme(REPOST_SCHEME).appendPath(str).build()).addFlags(268435456).putExtra("key", str), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
    }

    public void scheduleRepostsForPersistedNotifications(long j) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mPersistedSnoozedNotifications.size(); i++) {
                try {
                    java.lang.String keyAt = this.mPersistedSnoozedNotifications.keyAt(i);
                    java.lang.Long valueAt = this.mPersistedSnoozedNotifications.valueAt(i);
                    if (valueAt != null && valueAt.longValue() > j) {
                        scheduleRepostAtTime(keyAt, valueAt.longValue());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void scheduleRepost(java.lang.String str, long j) {
        scheduleRepostAtTime(str, java.lang.System.currentTimeMillis() + j);
    }

    private void scheduleRepostAtTime(final java.lang.String str, final long j) {
        new java.lang.Runnable() { // from class: com.android.server.notification.SnoozeHelper$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.notification.SnoozeHelper.this.lambda$scheduleRepostAtTime$3(str, j);
            }
        }.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRepostAtTime$3(java.lang.String str, long j) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.PendingIntent createPendingIntent = createPendingIntent(str);
            this.mAm.cancel(createPendingIntent);
            if (DEBUG) {
                android.util.Slog.d(TAG, "Scheduling evaluate for " + new java.util.Date(j));
            }
            this.mAm.setExactAndAllowWhileIdle(0, j, createPendingIntent);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void dump(java.io.PrintWriter printWriter, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("\n  Snoozed notifications:");
                for (java.lang.String str : this.mSnoozedNotifications.keySet()) {
                    printWriter.print(INDENT);
                    printWriter.println("key: " + str);
                }
                printWriter.println("\n Pending snoozed notifications");
                for (java.lang.String str2 : this.mPersistedSnoozedNotifications.keySet()) {
                    printWriter.print(INDENT);
                    printWriter.println("key: " + str2 + " until: " + this.mPersistedSnoozedNotifications.get(str2));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void writeXml(final com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        synchronized (this.mLock) {
            final long currentTimeMillis = java.lang.System.currentTimeMillis();
            typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_NAME);
            writeXml(typedXmlSerializer, this.mPersistedSnoozedNotifications, XML_SNOOZED_NOTIFICATION, new com.android.server.notification.SnoozeHelper.Inserter() { // from class: com.android.server.notification.SnoozeHelper$$ExternalSyntheticLambda2
                @Override // com.android.server.notification.SnoozeHelper.Inserter
                public final void insert(java.lang.Object obj) {
                    com.android.server.notification.SnoozeHelper.lambda$writeXml$4(currentTimeMillis, typedXmlSerializer, (java.lang.Long) obj);
                }
            });
            writeXml(typedXmlSerializer, this.mPersistedSnoozedNotificationsWithContext, XML_SNOOZED_NOTIFICATION_CONTEXT, new com.android.server.notification.SnoozeHelper.Inserter() { // from class: com.android.server.notification.SnoozeHelper$$ExternalSyntheticLambda3
                @Override // com.android.server.notification.SnoozeHelper.Inserter
                public final void insert(java.lang.Object obj) {
                    typedXmlSerializer.attribute(null, com.android.server.notification.SnoozeHelper.XML_SNOOZED_NOTIFICATION_CONTEXT_ID, (java.lang.String) obj);
                }
            });
            typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_NAME);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$writeXml$4(long j, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.Long l) throws java.io.IOException {
        if (l.longValue() < j) {
            return;
        }
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_SNOOZED_NOTIFICATION_TIME, l.longValue());
    }

    private <T> void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, android.util.ArrayMap<java.lang.String, T> arrayMap, java.lang.String str, com.android.server.notification.SnoozeHelper.Inserter<T> inserter) throws java.io.IOException {
        for (int i = 0; i < arrayMap.size(); i++) {
            java.lang.String keyAt = arrayMap.keyAt(i);
            T valueAt = arrayMap.valueAt(i);
            typedXmlSerializer.startTag((java.lang.String) null, str);
            inserter.insert(valueAt);
            typedXmlSerializer.attributeInt((java.lang.String) null, XML_SNOOZED_NOTIFICATION_VERSION_LABEL, 1);
            typedXmlSerializer.attribute((java.lang.String) null, "key", keyAt);
            typedXmlSerializer.endTag((java.lang.String) null, str);
        }
    }

    protected void readXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, long j) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                java.lang.String name = typedXmlPullParser.getName();
                if (next != 3 || !XML_TAG_NAME.equals(name)) {
                    if (next == 2 && (XML_SNOOZED_NOTIFICATION.equals(name) || name.equals(XML_SNOOZED_NOTIFICATION_CONTEXT))) {
                        if (typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_SNOOZED_NOTIFICATION_VERSION_LABEL, -1) == 1) {
                            try {
                                java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "key");
                                if (name.equals(XML_SNOOZED_NOTIFICATION)) {
                                    java.lang.Long valueOf = java.lang.Long.valueOf(typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_SNOOZED_NOTIFICATION_TIME, 0L));
                                    if (valueOf.longValue() > j) {
                                        synchronized (this.mLock) {
                                            this.mPersistedSnoozedNotifications.put(attributeValue, valueOf);
                                        }
                                    }
                                }
                                if (name.equals(XML_SNOOZED_NOTIFICATION_CONTEXT)) {
                                    java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, XML_SNOOZED_NOTIFICATION_CONTEXT_ID);
                                    synchronized (this.mLock) {
                                        this.mPersistedSnoozedNotificationsWithContext.put(attributeValue, attributeValue2);
                                    }
                                }
                            } catch (java.lang.Exception e) {
                                android.util.Slog.e(TAG, "Exception in reading snooze data from policy xml", e);
                            }
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

    @com.android.internal.annotations.VisibleForTesting
    void setAlarmManager(android.app.AlarmManager alarmManager) {
        this.mAm = alarmManager;
    }
}
