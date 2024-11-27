package com.android.internal.util;

/* loaded from: classes5.dex */
public class NotificationMessagingUtil {
    private static final java.lang.String DEFAULT_SMS_APP_SETTING = "sms_default_application";
    private final android.content.Context mContext;
    private final android.util.SparseArray<java.lang.String> mDefaultSmsApp = new android.util.SparseArray<>();
    private final android.database.ContentObserver mSmsContentObserver = new android.database.ContentObserver(new android.os.Handler(android.os.Looper.getMainLooper())) { // from class: com.android.internal.util.NotificationMessagingUtil.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z, java.util.Collection<android.net.Uri> collection, int i, int i2) {
            if (collection.contains(android.provider.Settings.Secure.getUriFor("sms_default_application"))) {
                com.android.internal.util.NotificationMessagingUtil.this.cacheDefaultSmsApp(i2);
            }
        }
    };
    private final java.lang.Object mStateLock;

    public NotificationMessagingUtil(android.content.Context context, java.lang.Object obj) {
        this.mContext = context;
        this.mStateLock = obj == null ? new java.lang.Object() : obj;
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("sms_default_application"), false, this.mSmsContentObserver);
    }

    public boolean isImportantMessaging(android.service.notification.StatusBarNotification statusBarNotification, int i) {
        if (i < 2) {
            return false;
        }
        return hasMessagingStyle(statusBarNotification) || (isCategoryMessage(statusBarNotification) && isDefaultMessagingApp(statusBarNotification));
    }

    public boolean isMessaging(android.service.notification.StatusBarNotification statusBarNotification) {
        return hasMessagingStyle(statusBarNotification) || isDefaultMessagingApp(statusBarNotification) || isCategoryMessage(statusBarNotification);
    }

    private boolean isDefaultMessagingApp(android.service.notification.StatusBarNotification statusBarNotification) {
        boolean equals;
        int userId = statusBarNotification.getUserId();
        if (userId == -10000 || userId == -1) {
            return false;
        }
        synchronized (this.mStateLock) {
            if (this.mDefaultSmsApp.get(userId) == null) {
                cacheDefaultSmsApp(userId);
            }
            equals = java.util.Objects.equals(this.mDefaultSmsApp.get(userId), statusBarNotification.getPackageName());
        }
        return equals;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cacheDefaultSmsApp(int i) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "sms_default_application", i);
        synchronized (this.mStateLock) {
            this.mDefaultSmsApp.put(i, stringForUser);
        }
    }

    private boolean hasMessagingStyle(android.service.notification.StatusBarNotification statusBarNotification) {
        return statusBarNotification.getNotification().isStyle(android.app.Notification.MessagingStyle.class);
    }

    private boolean isCategoryMessage(android.service.notification.StatusBarNotification statusBarNotification) {
        return android.app.Notification.CATEGORY_MESSAGE.equals(statusBarNotification.getNotification().category);
    }
}
