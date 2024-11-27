package com.android.server.notification;

/* loaded from: classes2.dex */
class NotificationComparator implements java.util.Comparator<com.android.server.notification.NotificationRecord> {
    private final android.content.Context mContext;
    private java.lang.String mDefaultPhoneApp;
    private final com.android.internal.util.NotificationMessagingUtil mMessagingUtil;
    public final java.lang.Object mStateLock = new java.lang.Object();
    private final android.content.BroadcastReceiver mPhoneAppBroadcastReceiver = new com.android.server.notification.NotificationComparator.AnonymousClass1();

    public NotificationComparator(android.content.Context context) {
        this.mContext = context;
        this.mContext.registerReceiver(this.mPhoneAppBroadcastReceiver, new android.content.IntentFilter("android.telecom.action.DEFAULT_DIALER_CHANGED"));
        this.mMessagingUtil = new com.android.internal.util.NotificationMessagingUtil(this.mContext, this.mStateLock);
    }

    @Override // java.util.Comparator
    public int compare(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationRecord notificationRecord2) {
        int importance = notificationRecord.getImportance();
        int importance2 = notificationRecord2.getImportance();
        boolean z = importance >= 3;
        boolean z2 = importance2 >= 3;
        if (z != z2) {
            return java.lang.Boolean.compare(z, z2) * (-1);
        }
        if (notificationRecord.getRankingScore() != notificationRecord2.getRankingScore()) {
            return java.lang.Float.compare(notificationRecord.getRankingScore(), notificationRecord2.getRankingScore()) * (-1);
        }
        boolean isImportantColorized = isImportantColorized(notificationRecord);
        boolean isImportantColorized2 = isImportantColorized(notificationRecord2);
        if (isImportantColorized != isImportantColorized2) {
            return java.lang.Boolean.compare(isImportantColorized, isImportantColorized2) * (-1);
        }
        boolean isImportantOngoing = isImportantOngoing(notificationRecord);
        boolean isImportantOngoing2 = isImportantOngoing(notificationRecord2);
        if (isImportantOngoing != isImportantOngoing2) {
            return java.lang.Boolean.compare(isImportantOngoing, isImportantOngoing2) * (-1);
        }
        boolean isImportantMessaging = isImportantMessaging(notificationRecord);
        boolean isImportantMessaging2 = isImportantMessaging(notificationRecord2);
        if (isImportantMessaging != isImportantMessaging2) {
            return java.lang.Boolean.compare(isImportantMessaging, isImportantMessaging2) * (-1);
        }
        boolean isImportantPeople = isImportantPeople(notificationRecord);
        boolean isImportantPeople2 = isImportantPeople(notificationRecord2);
        int compare = java.lang.Float.compare(notificationRecord.getContactAffinity(), notificationRecord2.getContactAffinity());
        if (isImportantPeople && isImportantPeople2) {
            if (compare != 0) {
                return compare * (-1);
            }
        } else if (isImportantPeople != isImportantPeople2) {
            return java.lang.Boolean.compare(isImportantPeople, isImportantPeople2) * (-1);
        }
        boolean isSystemMax = isSystemMax(notificationRecord);
        boolean isSystemMax2 = isSystemMax(notificationRecord2);
        if (isSystemMax != isSystemMax2) {
            return java.lang.Boolean.compare(isSystemMax, isSystemMax2) * (-1);
        }
        if (importance != importance2) {
            return java.lang.Integer.compare(importance, importance2) * (-1);
        }
        if (compare != 0) {
            return compare * (-1);
        }
        int packagePriority = notificationRecord.getPackagePriority();
        int packagePriority2 = notificationRecord2.getPackagePriority();
        if (packagePriority != packagePriority2) {
            return java.lang.Integer.compare(packagePriority, packagePriority2) * (-1);
        }
        int i = notificationRecord.getSbn().getNotification().priority;
        int i2 = notificationRecord2.getSbn().getNotification().priority;
        if (i != i2) {
            return java.lang.Integer.compare(i, i2) * (-1);
        }
        return java.lang.Long.compare(notificationRecord.getRankingTimeMs(), notificationRecord2.getRankingTimeMs()) * (-1);
    }

    private boolean isImportantColorized(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord.getImportance() < 2) {
            return false;
        }
        return notificationRecord.getNotification().isColorized();
    }

    private boolean isImportantOngoing(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord.getImportance() < 2) {
            return false;
        }
        if (isCallStyle(notificationRecord)) {
            return true;
        }
        if (notificationRecord.getNotification().isFgsOrUij()) {
            return isCallCategory(notificationRecord) || isMediaNotification(notificationRecord);
        }
        return false;
    }

    protected boolean isImportantPeople(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.getImportance() >= 2 && notificationRecord.getContactAffinity() > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    protected boolean isImportantMessaging(com.android.server.notification.NotificationRecord notificationRecord) {
        return this.mMessagingUtil.isImportantMessaging(notificationRecord.getSbn(), notificationRecord.getImportance());
    }

    protected boolean isSystemMax(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord.getImportance() < 4) {
            return false;
        }
        java.lang.String packageName = notificationRecord.getSbn().getPackageName();
        return com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(packageName) || "com.android.systemui".equals(packageName);
    }

    private boolean isMediaNotification(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.getNotification().isMediaNotification();
    }

    private boolean isCallCategory(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.isCategory("call") && isDefaultPhoneApp(notificationRecord.getSbn().getPackageName());
    }

    private boolean isCallStyle(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.getNotification().isStyle(android.app.Notification.CallStyle.class);
    }

    private boolean isDefaultPhoneApp(java.lang.String str) {
        if (this.mDefaultPhoneApp == null) {
            android.telecom.TelecomManager telecomManager = (android.telecom.TelecomManager) this.mContext.getSystemService("telecom");
            this.mDefaultPhoneApp = telecomManager != null ? telecomManager.getDefaultDialerPackage() : null;
        }
        return java.util.Objects.equals(str, this.mDefaultPhoneApp);
    }

    /* renamed from: com.android.server.notification.NotificationComparator$1, reason: invalid class name */
    class AnonymousClass1 extends android.content.BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, final android.content.Intent intent) {
            com.android.internal.os.BackgroundThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationComparator$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.notification.NotificationComparator.AnonymousClass1.this.lambda$onReceive$0(intent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(android.content.Intent intent) {
            synchronized (com.android.server.notification.NotificationComparator.this.mStateLock) {
                com.android.server.notification.NotificationComparator.this.mDefaultPhoneApp = intent.getStringExtra("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME");
            }
        }
    }
}
