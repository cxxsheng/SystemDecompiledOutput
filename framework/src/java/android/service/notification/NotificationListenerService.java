package android.service.notification;

/* loaded from: classes3.dex */
public abstract class NotificationListenerService extends android.app.Service {
    public static final java.lang.String ACTION_SETTINGS_HOME = "android.service.notification.action.SETTINGS_HOME";
    public static final int FLAG_FILTER_TYPE_ALERTING = 2;
    public static final int FLAG_FILTER_TYPE_CONVERSATIONS = 1;
    public static final int FLAG_FILTER_TYPE_ONGOING = 8;
    public static final int FLAG_FILTER_TYPE_SILENT = 4;
    public static final int HINT_HOST_DISABLE_CALL_EFFECTS = 4;
    public static final int HINT_HOST_DISABLE_EFFECTS = 1;
    public static final int HINT_HOST_DISABLE_NOTIFICATION_EFFECTS = 2;
    public static final int INTERRUPTION_FILTER_ALARMS = 4;
    public static final int INTERRUPTION_FILTER_ALL = 1;
    public static final int INTERRUPTION_FILTER_NONE = 3;
    public static final int INTERRUPTION_FILTER_PRIORITY = 2;
    public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
    public static final java.lang.String META_DATA_DEFAULT_AUTOBIND = "android.service.notification.default_autobind_listenerservice";
    public static final java.lang.String META_DATA_DEFAULT_FILTER_TYPES = "android.service.notification.default_filter_types";
    public static final java.lang.String META_DATA_DISABLED_FILTER_TYPES = "android.service.notification.disabled_filter_types";
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_ADDED = 1;
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_DELETED = 3;
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_UPDATED = 2;
    public static final int REASON_APP_CANCEL = 8;
    public static final int REASON_APP_CANCEL_ALL = 9;
    public static final int REASON_ASSISTANT_CANCEL = 22;
    public static final int REASON_CANCEL = 2;
    public static final int REASON_CANCEL_ALL = 3;
    public static final int REASON_CHANNEL_BANNED = 17;
    public static final int REASON_CHANNEL_REMOVED = 20;
    public static final int REASON_CLEAR_DATA = 21;
    public static final int REASON_CLICK = 1;
    public static final int REASON_ERROR = 4;
    public static final int REASON_GROUP_OPTIMIZATION = 13;
    public static final int REASON_GROUP_SUMMARY_CANCELED = 12;
    public static final int REASON_LISTENER_CANCEL = 10;
    public static final int REASON_LISTENER_CANCEL_ALL = 11;
    public static final int REASON_LOCKDOWN = 23;
    public static final int REASON_PACKAGE_BANNED = 7;
    public static final int REASON_PACKAGE_CHANGED = 5;
    public static final int REASON_PACKAGE_SUSPENDED = 14;
    public static final int REASON_PROFILE_TURNED_OFF = 15;
    public static final int REASON_SNOOZED = 18;
    public static final int REASON_TIMEOUT = 19;
    public static final int REASON_UNAUTOBUNDLED = 16;
    public static final int REASON_USER_STOPPED = 6;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.notification.NotificationListenerService";

    @java.lang.Deprecated
    public static final int SUPPRESSED_EFFECT_SCREEN_OFF = 1;

    @java.lang.Deprecated
    public static final int SUPPRESSED_EFFECT_SCREEN_ON = 2;

    @android.annotation.SystemApi
    public static final int TRIM_FULL = 0;

    @android.annotation.SystemApi
    public static final int TRIM_LIGHT = 1;
    protected int mCurrentUser;
    private android.os.Handler mHandler;
    protected android.app.INotificationManager mNoMan;
    private android.service.notification.NotificationListenerService.RankingMap mRankingMap;
    protected android.content.Context mSystemContext;
    private final java.lang.String TAG = getClass().getSimpleName();
    private final java.lang.Object mLock = new java.lang.Object();
    protected android.service.notification.NotificationListenerService.NotificationListenerWrapper mWrapper = null;
    private boolean isConnected = false;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ChannelOrGroupModificationTypes {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NotificationCancelReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NotificationFilterTypes {
    }

    @Override // android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
        this.mHandler = new android.service.notification.NotificationListenerService.MyHandler(getMainLooper());
    }

    public void onNotificationPosted(android.service.notification.StatusBarNotification statusBarNotification) {
    }

    public void onNotificationPosted(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap) {
        onNotificationPosted(statusBarNotification);
    }

    public void onNotificationRemoved(android.service.notification.StatusBarNotification statusBarNotification) {
    }

    public void onNotificationRemoved(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap) {
        onNotificationRemoved(statusBarNotification);
    }

    public void onNotificationRemoved(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap, int i) {
        onNotificationRemoved(statusBarNotification, rankingMap);
    }

    @android.annotation.SystemApi
    public void onNotificationRemoved(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap, android.service.notification.NotificationStats notificationStats, int i) {
        onNotificationRemoved(statusBarNotification, rankingMap, i);
    }

    public void onListenerConnected() {
    }

    public void onListenerDisconnected() {
    }

    public void onNotificationRankingUpdate(android.service.notification.NotificationListenerService.RankingMap rankingMap) {
    }

    public void onListenerHintsChanged(int i) {
    }

    public void onSilentStatusBarIconsVisibilityChanged(boolean z) {
    }

    public void onNotificationChannelModified(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel, int i) {
    }

    public void onNotificationChannelGroupModified(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup, int i) {
    }

    public void onInterruptionFilterChanged(int i) {
    }

    protected final android.app.INotificationManager getNotificationInterface() {
        if (this.mNoMan == null) {
            this.mNoMan = android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification"));
        }
        return this.mNoMan;
    }

    @java.lang.Deprecated
    public final void cancelNotification(java.lang.String str, java.lang.String str2, int i) {
        if (isBound()) {
            try {
                getNotificationInterface().cancelNotificationFromListener(this.mWrapper, str, str2, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void cancelNotification(java.lang.String str) {
        if (isBound()) {
            try {
                getNotificationInterface().cancelNotificationsFromListener(this.mWrapper, new java.lang.String[]{str});
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void cancelAllNotifications() {
        cancelNotifications(null);
    }

    public final void cancelNotifications(java.lang.String[] strArr) {
        if (isBound()) {
            try {
                getNotificationInterface().cancelNotificationsFromListener(this.mWrapper, strArr);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    @android.annotation.SystemApi
    public final void snoozeNotification(java.lang.String str, java.lang.String str2) {
        if (isBound()) {
            try {
                getNotificationInterface().snoozeNotificationUntilContextFromListener(this.mWrapper, str, str2);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void snoozeNotification(java.lang.String str, long j) {
        if (isBound()) {
            try {
                getNotificationInterface().snoozeNotificationUntilFromListener(this.mWrapper, str, j);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void migrateNotificationFilter(int i, java.util.List<java.lang.String> list) {
        if (isBound()) {
            try {
                getNotificationInterface().migrateNotificationFilter(this.mWrapper, i, list);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void setNotificationsShown(java.lang.String[] strArr) {
        if (isBound()) {
            try {
                getNotificationInterface().setNotificationsShownFromListener(this.mWrapper, strArr);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void updateNotificationChannel(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel) {
        if (isBound()) {
            try {
                getNotificationInterface().updateNotificationChannelFromPrivilegedListener(this.mWrapper, str, userHandle, notificationChannel);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public final java.util.List<android.app.NotificationChannel> getNotificationChannels(java.lang.String str, android.os.UserHandle userHandle) {
        if (!isBound()) {
            return null;
        }
        try {
            return getNotificationInterface().getNotificationChannelsFromPrivilegedListener(this.mWrapper, str, userHandle).getList();
        } catch (android.os.RemoteException e) {
            android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public final java.util.List<android.app.NotificationChannelGroup> getNotificationChannelGroups(java.lang.String str, android.os.UserHandle userHandle) {
        if (!isBound()) {
            return null;
        }
        try {
            return getNotificationInterface().getNotificationChannelGroupsFromPrivilegedListener(this.mWrapper, str, userHandle).getList();
        } catch (android.os.RemoteException e) {
            android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public final void setOnNotificationPostedTrim(int i) {
        if (isBound()) {
            try {
                getNotificationInterface().setOnNotificationPostedTrimFromListener(this.mWrapper, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public android.service.notification.StatusBarNotification[] getActiveNotifications() {
        android.service.notification.StatusBarNotification[] activeNotifications = getActiveNotifications(null, 0);
        return activeNotifications != null ? activeNotifications : new android.service.notification.StatusBarNotification[0];
    }

    public final android.service.notification.StatusBarNotification[] getSnoozedNotifications() {
        try {
            return cleanUpNotificationList(getNotificationInterface().getSnoozedNotificationsFromListener(this.mWrapper, 0));
        } catch (android.os.RemoteException e) {
            android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            return null;
        }
    }

    @android.annotation.SystemApi
    public android.service.notification.StatusBarNotification[] getActiveNotifications(int i) {
        android.service.notification.StatusBarNotification[] activeNotifications = getActiveNotifications(null, i);
        return activeNotifications != null ? activeNotifications : new android.service.notification.StatusBarNotification[0];
    }

    public android.service.notification.StatusBarNotification[] getActiveNotifications(java.lang.String[] strArr) {
        android.service.notification.StatusBarNotification[] activeNotifications = getActiveNotifications(strArr, 0);
        return activeNotifications != null ? activeNotifications : new android.service.notification.StatusBarNotification[0];
    }

    @android.annotation.SystemApi
    public android.service.notification.StatusBarNotification[] getActiveNotifications(java.lang.String[] strArr, int i) {
        if (!isBound()) {
            return null;
        }
        try {
            return cleanUpNotificationList(getNotificationInterface().getActiveNotificationsFromListener(this.mWrapper, strArr, i));
        } catch (android.os.BadParcelableException | android.os.RemoteException e) {
            android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            return null;
        }
    }

    private android.service.notification.StatusBarNotification[] cleanUpNotificationList(android.content.pm.ParceledListSlice<android.service.notification.StatusBarNotification> parceledListSlice) {
        if (parceledListSlice == null || parceledListSlice.getList() == null) {
            return new android.service.notification.StatusBarNotification[0];
        }
        java.util.List list = parceledListSlice.getList();
        int size = list.size();
        java.util.ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            android.service.notification.StatusBarNotification statusBarNotification = (android.service.notification.StatusBarNotification) list.get(i);
            android.app.Notification notification = statusBarNotification.getNotification();
            try {
                createLegacyIconExtras(notification);
                maybePopulateRemoteViews(notification);
                maybePopulatePeople(notification);
            } catch (java.lang.IllegalArgumentException e) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList(size);
                }
                arrayList.add(statusBarNotification);
                android.util.Log.w(this.TAG, "get(Active/Snoozed)Notifications: can't rebuild notification from " + statusBarNotification.getPackageName());
            }
        }
        if (arrayList != null) {
            list.removeAll(arrayList);
        }
        return (android.service.notification.StatusBarNotification[]) list.toArray(new android.service.notification.StatusBarNotification[list.size()]);
    }

    public final int getCurrentListenerHints() {
        if (!isBound()) {
            return 0;
        }
        try {
            return getNotificationInterface().getHintsFromListener(this.mWrapper);
        } catch (android.os.RemoteException e) {
            android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            return 0;
        }
    }

    public final int getCurrentInterruptionFilter() {
        if (!isBound()) {
            return 0;
        }
        try {
            return getNotificationInterface().getInterruptionFilterFromListener(this.mWrapper);
        } catch (android.os.RemoteException e) {
            android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            return 0;
        }
    }

    public final void clearRequestedListenerHints() {
        if (isBound()) {
            try {
                getNotificationInterface().clearRequestedListenerHints(this.mWrapper);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void requestListenerHints(int i) {
        if (isBound()) {
            try {
                getNotificationInterface().requestHintsFromListener(this.mWrapper, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void requestInterruptionFilter(int i) {
        if (isBound()) {
            try {
                getNotificationInterface().requestInterruptionFilterFromListener(this.mWrapper, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public android.service.notification.NotificationListenerService.RankingMap getCurrentRanking() {
        android.service.notification.NotificationListenerService.RankingMap rankingMap;
        synchronized (this.mLock) {
            rankingMap = this.mRankingMap;
        }
        return rankingMap;
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (this.mWrapper == null) {
            this.mWrapper = new android.service.notification.NotificationListenerService.NotificationListenerWrapper();
        }
        return this.mWrapper;
    }

    protected boolean isBound() {
        if (this.mWrapper == null) {
            android.util.Log.w(this.TAG, "Notification listener service not yet bound.");
            return false;
        }
        return true;
    }

    @Override // android.app.Service
    public void onDestroy() {
        onListenerDisconnected();
        super.onDestroy();
    }

    @android.annotation.SystemApi
    public void registerAsSystemService(android.content.Context context, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        if (this.mWrapper == null) {
            this.mWrapper = new android.service.notification.NotificationListenerService.NotificationListenerWrapper();
        }
        this.mSystemContext = context;
        android.app.INotificationManager notificationInterface = getNotificationInterface();
        this.mHandler = new android.service.notification.NotificationListenerService.MyHandler(context.getMainLooper());
        this.mCurrentUser = i;
        notificationInterface.registerListener(this.mWrapper, componentName, i);
    }

    @android.annotation.SystemApi
    public void unregisterAsSystemService() throws android.os.RemoteException {
        if (this.mWrapper != null) {
            getNotificationInterface().unregisterListener(this.mWrapper, this.mCurrentUser);
        }
    }

    public static void requestRebind(android.content.ComponentName componentName) {
        try {
            android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification")).requestBindListener(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void requestUnbind(android.content.ComponentName componentName) {
        try {
            android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification")).requestUnbindListenerComponent(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void requestUnbind() {
        if (this.mWrapper != null) {
            try {
                getNotificationInterface().requestUnbindListener(this.mWrapper);
                this.isConnected = false;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public final void createLegacyIconExtras(android.app.Notification notification) {
        android.graphics.drawable.Drawable loadDrawable;
        if (getContext().getApplicationInfo().targetSdkVersion < 23) {
            android.graphics.drawable.Icon smallIcon = notification.getSmallIcon();
            android.graphics.drawable.Icon largeIcon = notification.getLargeIcon();
            if (smallIcon != null && smallIcon.getType() == 2) {
                notification.extras.putInt(android.app.Notification.EXTRA_SMALL_ICON, smallIcon.getResId());
                notification.icon = smallIcon.getResId();
            }
            if (largeIcon != null && (loadDrawable = largeIcon.loadDrawable(getContext())) != null && (loadDrawable instanceof android.graphics.drawable.BitmapDrawable)) {
                android.graphics.Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) loadDrawable).getBitmap();
                notification.extras.putParcelable(android.app.Notification.EXTRA_LARGE_ICON, bitmap);
                notification.largeIcon = bitmap;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybePopulateRemoteViews(android.app.Notification notification) {
        if (getContext().getApplicationInfo().targetSdkVersion < 24) {
            android.app.Notification.Builder recoverBuilder = android.app.Notification.Builder.recoverBuilder(getContext(), notification);
            android.widget.RemoteViews createContentView = recoverBuilder.createContentView();
            android.widget.RemoteViews createBigContentView = recoverBuilder.createBigContentView();
            android.widget.RemoteViews createHeadsUpContentView = recoverBuilder.createHeadsUpContentView();
            notification.contentView = createContentView;
            notification.bigContentView = createBigContentView;
            notification.headsUpContentView = createHeadsUpContentView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybePopulatePeople(android.app.Notification notification) {
        java.util.ArrayList parcelableArrayList;
        if (getContext().getApplicationInfo().targetSdkVersion < 28 && (parcelableArrayList = notification.extras.getParcelableArrayList(android.app.Notification.EXTRA_PEOPLE_LIST, android.app.Person.class)) != null && !parcelableArrayList.isEmpty()) {
            int size = parcelableArrayList.size();
            java.lang.String[] strArr = new java.lang.String[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = ((android.app.Person) parcelableArrayList.get(i)).resolveToLegacyUri();
            }
            notification.extras.putStringArray(android.app.Notification.EXTRA_PEOPLE, strArr);
        }
    }

    protected class NotificationListenerWrapper extends android.service.notification.INotificationListener.Stub {
        protected NotificationListenerWrapper() {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationPosted(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) {
            try {
                android.service.notification.StatusBarNotification statusBarNotification = iStatusBarNotificationHolder.get();
                if (statusBarNotification == null) {
                    android.util.Log.w(android.service.notification.NotificationListenerService.this.TAG, "onNotificationPosted: Error receiving StatusBarNotification");
                    return;
                }
                try {
                    android.service.notification.NotificationListenerService.this.createLegacyIconExtras(statusBarNotification.getNotification());
                    android.service.notification.NotificationListenerService.this.maybePopulateRemoteViews(statusBarNotification.getNotification());
                    android.service.notification.NotificationListenerService.this.maybePopulatePeople(statusBarNotification.getNotification());
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Log.w(android.service.notification.NotificationListenerService.this.TAG, "onNotificationPosted: can't rebuild notification from " + statusBarNotification.getPackageName());
                    statusBarNotification = null;
                }
                synchronized (android.service.notification.NotificationListenerService.this.mLock) {
                    android.service.notification.NotificationListenerService.this.applyUpdateLocked(notificationRankingUpdate);
                    if (statusBarNotification != null) {
                        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                        obtain.arg1 = statusBarNotification;
                        obtain.arg2 = android.service.notification.NotificationListenerService.this.mRankingMap;
                        android.service.notification.NotificationListenerService.this.mHandler.obtainMessage(1, obtain).sendToTarget();
                    } else {
                        android.service.notification.NotificationListenerService.this.mHandler.obtainMessage(4, android.service.notification.NotificationListenerService.this.mRankingMap).sendToTarget();
                    }
                }
            } catch (android.os.RemoteException e2) {
                android.util.Log.w(android.service.notification.NotificationListenerService.this.TAG, "onNotificationPosted: Error receiving StatusBarNotification", e2);
            }
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationRemoved(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.service.notification.NotificationRankingUpdate notificationRankingUpdate, android.service.notification.NotificationStats notificationStats, int i) {
            try {
                android.service.notification.StatusBarNotification statusBarNotification = iStatusBarNotificationHolder.get();
                if (statusBarNotification == null) {
                    android.util.Log.w(android.service.notification.NotificationListenerService.this.TAG, "onNotificationRemoved: Error receiving StatusBarNotification");
                    return;
                }
                synchronized (android.service.notification.NotificationListenerService.this.mLock) {
                    android.service.notification.NotificationListenerService.this.applyUpdateLocked(notificationRankingUpdate);
                    com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                    obtain.arg1 = statusBarNotification;
                    obtain.arg2 = android.service.notification.NotificationListenerService.this.mRankingMap;
                    obtain.arg3 = java.lang.Integer.valueOf(i);
                    obtain.arg4 = notificationStats;
                    android.service.notification.NotificationListenerService.this.mHandler.obtainMessage(2, obtain).sendToTarget();
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.service.notification.NotificationListenerService.this.TAG, "onNotificationRemoved: Error receiving StatusBarNotification", e);
            }
        }

        @Override // android.service.notification.INotificationListener
        public void onListenerConnected(android.service.notification.NotificationRankingUpdate notificationRankingUpdate) {
            synchronized (android.service.notification.NotificationListenerService.this.mLock) {
                android.service.notification.NotificationListenerService.this.applyUpdateLocked(notificationRankingUpdate);
            }
            android.service.notification.NotificationListenerService.this.isConnected = true;
            android.service.notification.NotificationListenerService.this.mHandler.obtainMessage(3).sendToTarget();
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationRankingUpdate(android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException {
            synchronized (android.service.notification.NotificationListenerService.this.mLock) {
                android.service.notification.NotificationListenerService.this.applyUpdateLocked(notificationRankingUpdate);
                android.service.notification.NotificationListenerService.this.mHandler.obtainMessage(4, android.service.notification.NotificationListenerService.this.mRankingMap).sendToTarget();
            }
        }

        @Override // android.service.notification.INotificationListener
        public void onListenerHintsChanged(int i) throws android.os.RemoteException {
            android.service.notification.NotificationListenerService.this.mHandler.obtainMessage(5, i, 0).sendToTarget();
        }

        @Override // android.service.notification.INotificationListener
        public void onInterruptionFilterChanged(int i) throws android.os.RemoteException {
            android.service.notification.NotificationListenerService.this.mHandler.obtainMessage(6, i, 0).sendToTarget();
        }

        public void onNotificationEnqueuedWithChannel(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.app.NotificationChannel notificationChannel, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) throws android.os.RemoteException {
        }

        public void onNotificationsSeen(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        public void onPanelRevealed(int i) throws android.os.RemoteException {
        }

        public void onPanelHidden() throws android.os.RemoteException {
        }

        public void onNotificationVisibilityChanged(java.lang.String str, boolean z) {
        }

        public void onNotificationSnoozedUntilContext(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, java.lang.String str) throws android.os.RemoteException {
        }

        public void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2) {
        }

        public void onNotificationDirectReply(java.lang.String str) {
        }

        public void onSuggestedReplySent(java.lang.String str, java.lang.CharSequence charSequence, int i) {
        }

        public void onActionClicked(java.lang.String str, android.app.Notification.Action action, int i) {
        }

        public void onNotificationClicked(java.lang.String str) {
        }

        public void onAllowedAdjustmentsChanged() {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationChannelModification(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel, int i) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = userHandle;
            obtain.arg3 = notificationChannel;
            obtain.arg4 = java.lang.Integer.valueOf(i);
            android.service.notification.NotificationListenerService.this.mHandler.obtainMessage(7, obtain).sendToTarget();
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationChannelGroupModification(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup, int i) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = userHandle;
            obtain.arg3 = notificationChannelGroup;
            obtain.arg4 = java.lang.Integer.valueOf(i);
            android.service.notification.NotificationListenerService.this.mHandler.obtainMessage(8, obtain).sendToTarget();
        }

        @Override // android.service.notification.INotificationListener
        public void onStatusBarIconsBehaviorChanged(boolean z) {
            android.service.notification.NotificationListenerService.this.mHandler.obtainMessage(9, java.lang.Boolean.valueOf(z)).sendToTarget();
        }

        public void onNotificationFeedbackReceived(java.lang.String str, android.service.notification.NotificationRankingUpdate notificationRankingUpdate, android.os.Bundle bundle) {
        }
    }

    public final void applyUpdateLocked(android.service.notification.NotificationRankingUpdate notificationRankingUpdate) {
        this.mRankingMap = notificationRankingUpdate.getRankingMap();
    }

    protected android.content.Context getContext() {
        if (this.mSystemContext != null) {
            return this.mSystemContext;
        }
        return this;
    }

    public static class Ranking {
        private static final int PARCEL_VERSION = 2;
        public static final int RANKING_DEMOTED = -1;
        public static final int RANKING_PROMOTED = 1;
        public static final int RANKING_UNCHANGED = 0;
        public static final int USER_SENTIMENT_NEGATIVE = -1;
        public static final int USER_SENTIMENT_NEUTRAL = 0;
        public static final int USER_SENTIMENT_POSITIVE = 1;
        public static final int VISIBILITY_NO_OVERRIDE = -1000;
        private boolean mCanBubble;
        private android.app.NotificationChannel mChannel;
        private boolean mHidden;
        private int mImportance;
        private java.lang.CharSequence mImportanceExplanation;
        private boolean mIsAmbient;
        private boolean mIsBubble;
        private boolean mIsConversation;
        private boolean mIsTextChanged;
        private java.lang.String mKey;
        private long mLastAudiblyAlertedMs;
        private boolean mMatchesInterruptionFilter;
        private boolean mNoisy;
        private java.lang.String mOverrideGroupKey;
        private java.util.ArrayList<java.lang.String> mOverridePeople;
        private int mProposedImportance;
        private int mRank;
        private int mRankingAdjustment;
        private float mRankingScore;
        private boolean mSensitiveContent;
        private android.content.pm.ShortcutInfo mShortcutInfo;
        private boolean mShowBadge;
        private java.util.ArrayList<android.app.Notification.Action> mSmartActions;
        private java.util.ArrayList<java.lang.CharSequence> mSmartReplies;
        private java.util.ArrayList<android.service.notification.SnoozeCriterion> mSnoozeCriteria;
        private int mSuppressedVisualEffects;
        private int mUserSentiment;
        private int mVisibilityOverride;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface RankingAdjustment {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface UserSentiment {
        }

        public Ranking() {
            this.mRank = -1;
            this.mUserSentiment = 0;
        }

        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.dataPosition();
            parcel.writeInt(2);
            parcel.writeString(this.mKey);
            parcel.writeInt(this.mRank);
            parcel.writeBoolean(this.mIsAmbient);
            parcel.writeBoolean(this.mMatchesInterruptionFilter);
            parcel.writeInt(this.mVisibilityOverride);
            parcel.writeInt(this.mSuppressedVisualEffects);
            parcel.writeInt(this.mImportance);
            parcel.writeCharSequence(this.mImportanceExplanation);
            parcel.writeFloat(this.mRankingScore);
            parcel.writeString(this.mOverrideGroupKey);
            parcel.writeParcelable(this.mChannel, i);
            parcel.writeStringList(this.mOverridePeople);
            parcel.writeTypedList(this.mSnoozeCriteria, i);
            parcel.writeBoolean(this.mShowBadge);
            parcel.writeInt(this.mUserSentiment);
            parcel.writeBoolean(this.mHidden);
            parcel.writeLong(this.mLastAudiblyAlertedMs);
            parcel.writeBoolean(this.mNoisy);
            parcel.writeTypedList(this.mSmartActions, i);
            parcel.writeCharSequenceList(this.mSmartReplies);
            parcel.writeBoolean(this.mCanBubble);
            parcel.writeBoolean(this.mIsTextChanged);
            parcel.writeBoolean(this.mIsConversation);
            parcel.writeParcelable(this.mShortcutInfo, i);
            parcel.writeInt(this.mRankingAdjustment);
            parcel.writeBoolean(this.mIsBubble);
            parcel.writeInt(this.mProposedImportance);
            parcel.writeBoolean(this.mSensitiveContent);
        }

        public Ranking(android.os.Parcel parcel) {
            this.mRank = -1;
            this.mUserSentiment = 0;
            java.lang.ClassLoader classLoader = getClass().getClassLoader();
            int readInt = parcel.readInt();
            if (readInt != 2) {
                throw new java.lang.IllegalArgumentException("malformed Ranking parcel: " + parcel + " version " + readInt + ", expected 2");
            }
            this.mKey = parcel.readString();
            this.mRank = parcel.readInt();
            this.mIsAmbient = parcel.readBoolean();
            this.mMatchesInterruptionFilter = parcel.readBoolean();
            this.mVisibilityOverride = parcel.readInt();
            this.mSuppressedVisualEffects = parcel.readInt();
            this.mImportance = parcel.readInt();
            this.mImportanceExplanation = parcel.readCharSequence();
            this.mRankingScore = parcel.readFloat();
            this.mOverrideGroupKey = parcel.readString();
            this.mChannel = (android.app.NotificationChannel) parcel.readParcelable(classLoader, android.app.NotificationChannel.class);
            this.mOverridePeople = parcel.createStringArrayList();
            this.mSnoozeCriteria = parcel.createTypedArrayList(android.service.notification.SnoozeCriterion.CREATOR);
            this.mShowBadge = parcel.readBoolean();
            this.mUserSentiment = parcel.readInt();
            this.mHidden = parcel.readBoolean();
            this.mLastAudiblyAlertedMs = parcel.readLong();
            this.mNoisy = parcel.readBoolean();
            this.mSmartActions = parcel.createTypedArrayList(android.app.Notification.Action.CREATOR);
            this.mSmartReplies = parcel.readCharSequenceList();
            this.mCanBubble = parcel.readBoolean();
            this.mIsTextChanged = parcel.readBoolean();
            this.mIsConversation = parcel.readBoolean();
            this.mShortcutInfo = (android.content.pm.ShortcutInfo) parcel.readParcelable(classLoader, android.content.pm.ShortcutInfo.class);
            this.mRankingAdjustment = parcel.readInt();
            this.mIsBubble = parcel.readBoolean();
            this.mProposedImportance = parcel.readInt();
            this.mSensitiveContent = parcel.readBoolean();
        }

        public java.lang.String getKey() {
            return this.mKey;
        }

        public int getRank() {
            return this.mRank;
        }

        public boolean isAmbient() {
            return this.mIsAmbient;
        }

        public int getLockscreenVisibilityOverride() {
            return this.mVisibilityOverride;
        }

        public int getSuppressedVisualEffects() {
            return this.mSuppressedVisualEffects;
        }

        public boolean matchesInterruptionFilter() {
            return this.mMatchesInterruptionFilter;
        }

        public int getImportance() {
            return this.mImportance;
        }

        public java.lang.CharSequence getImportanceExplanation() {
            return this.mImportanceExplanation;
        }

        public float getRankingScore() {
            return this.mRankingScore;
        }

        @android.annotation.SystemApi
        public int getProposedImportance() {
            return this.mProposedImportance;
        }

        @android.annotation.SystemApi
        public boolean hasSensitiveContent() {
            return this.mSensitiveContent;
        }

        public java.lang.String getOverrideGroupKey() {
            return this.mOverrideGroupKey;
        }

        public android.app.NotificationChannel getChannel() {
            return this.mChannel;
        }

        public int getUserSentiment() {
            return this.mUserSentiment;
        }

        @android.annotation.SystemApi
        public java.util.List<java.lang.String> getAdditionalPeople() {
            return this.mOverridePeople;
        }

        @android.annotation.SystemApi
        public java.util.List<android.service.notification.SnoozeCriterion> getSnoozeCriteria() {
            return this.mSnoozeCriteria;
        }

        public java.util.List<android.app.Notification.Action> getSmartActions() {
            return this.mSmartActions == null ? java.util.Collections.emptyList() : this.mSmartActions;
        }

        public void setSmartActions(java.util.ArrayList<android.app.Notification.Action> arrayList) {
            this.mSmartActions = arrayList;
        }

        public java.util.List<java.lang.CharSequence> getSmartReplies() {
            return this.mSmartReplies == null ? java.util.Collections.emptyList() : this.mSmartReplies;
        }

        public boolean canShowBadge() {
            return this.mShowBadge;
        }

        public boolean isSuspended() {
            return this.mHidden;
        }

        public long getLastAudiblyAlertedMillis() {
            return this.mLastAudiblyAlertedMs;
        }

        public boolean canBubble() {
            return this.mCanBubble;
        }

        public boolean isTextChanged() {
            return this.mIsTextChanged;
        }

        public boolean isNoisy() {
            return this.mNoisy;
        }

        public boolean isConversation() {
            return this.mIsConversation;
        }

        public boolean isBubble() {
            return this.mIsBubble;
        }

        public android.content.pm.ShortcutInfo getConversationShortcutInfo() {
            return this.mShortcutInfo;
        }

        public int getRankingAdjustment() {
            return this.mRankingAdjustment;
        }

        public void populate(java.lang.String str, int i, boolean z, int i2, int i3, int i4, java.lang.CharSequence charSequence, java.lang.String str2, android.app.NotificationChannel notificationChannel, java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<android.service.notification.SnoozeCriterion> arrayList2, boolean z2, int i5, boolean z3, long j, boolean z4, java.util.ArrayList<android.app.Notification.Action> arrayList3, java.util.ArrayList<java.lang.CharSequence> arrayList4, boolean z5, boolean z6, boolean z7, android.content.pm.ShortcutInfo shortcutInfo, int i6, boolean z8, int i7, boolean z9) {
            this.mKey = str;
            this.mRank = i;
            this.mIsAmbient = i4 < 2;
            this.mMatchesInterruptionFilter = z;
            this.mVisibilityOverride = i2;
            this.mSuppressedVisualEffects = i3;
            this.mImportance = i4;
            this.mImportanceExplanation = charSequence;
            this.mOverrideGroupKey = str2;
            this.mChannel = notificationChannel;
            this.mOverridePeople = arrayList;
            this.mSnoozeCriteria = arrayList2;
            this.mShowBadge = z2;
            this.mUserSentiment = i5;
            this.mHidden = z3;
            this.mLastAudiblyAlertedMs = j;
            this.mNoisy = z4;
            this.mSmartActions = arrayList3;
            this.mSmartReplies = arrayList4;
            this.mCanBubble = z5;
            this.mIsTextChanged = z6;
            this.mIsConversation = z7;
            this.mShortcutInfo = shortcutInfo;
            this.mRankingAdjustment = i6;
            this.mIsBubble = z8;
            this.mProposedImportance = i7;
            this.mSensitiveContent = z9;
        }

        public android.service.notification.NotificationListenerService.Ranking withAudiblyAlertedInfo(android.service.notification.NotificationListenerService.Ranking ranking) {
            if (ranking != null && ranking.mLastAudiblyAlertedMs > 0 && this.mLastAudiblyAlertedMs <= 0) {
                this.mLastAudiblyAlertedMs = ranking.mLastAudiblyAlertedMs;
            }
            return this;
        }

        public void populate(android.service.notification.NotificationListenerService.Ranking ranking) {
            populate(ranking.mKey, ranking.mRank, ranking.mMatchesInterruptionFilter, ranking.mVisibilityOverride, ranking.mSuppressedVisualEffects, ranking.mImportance, ranking.mImportanceExplanation, ranking.mOverrideGroupKey, ranking.mChannel, ranking.mOverridePeople, ranking.mSnoozeCriteria, ranking.mShowBadge, ranking.mUserSentiment, ranking.mHidden, ranking.mLastAudiblyAlertedMs, ranking.mNoisy, ranking.mSmartActions, ranking.mSmartReplies, ranking.mCanBubble, ranking.mIsTextChanged, ranking.mIsConversation, ranking.mShortcutInfo, ranking.mRankingAdjustment, ranking.mIsBubble, ranking.mProposedImportance, ranking.mSensitiveContent);
        }

        public static java.lang.String importanceToString(int i) {
            switch (i) {
                case -1000:
                    return "UNSPECIFIED";
                case 0:
                    return android.security.keystore.KeyProperties.DIGEST_NONE;
                case 1:
                    return "MIN";
                case 2:
                    return "LOW";
                case 3:
                    return "DEFAULT";
                case 4:
                case 5:
                    return "HIGH";
                default:
                    return "UNKNOWN(" + java.lang.String.valueOf(i) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            }
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.service.notification.NotificationListenerService.Ranking ranking = (android.service.notification.NotificationListenerService.Ranking) obj;
            if (java.util.Objects.equals(this.mKey, ranking.mKey) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mRank), java.lang.Integer.valueOf(ranking.mRank)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mMatchesInterruptionFilter), java.lang.Boolean.valueOf(ranking.mMatchesInterruptionFilter)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mVisibilityOverride), java.lang.Integer.valueOf(ranking.mVisibilityOverride)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mSuppressedVisualEffects), java.lang.Integer.valueOf(ranking.mSuppressedVisualEffects)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mImportance), java.lang.Integer.valueOf(ranking.mImportance)) && java.util.Objects.equals(this.mImportanceExplanation, ranking.mImportanceExplanation) && java.util.Objects.equals(this.mOverrideGroupKey, ranking.mOverrideGroupKey) && java.util.Objects.equals(this.mChannel, ranking.mChannel) && java.util.Objects.equals(this.mOverridePeople, ranking.mOverridePeople) && java.util.Objects.equals(this.mSnoozeCriteria, ranking.mSnoozeCriteria) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mShowBadge), java.lang.Boolean.valueOf(ranking.mShowBadge)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mUserSentiment), java.lang.Integer.valueOf(ranking.mUserSentiment)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mHidden), java.lang.Boolean.valueOf(ranking.mHidden)) && java.util.Objects.equals(java.lang.Long.valueOf(this.mLastAudiblyAlertedMs), java.lang.Long.valueOf(ranking.mLastAudiblyAlertedMs)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mNoisy), java.lang.Boolean.valueOf(ranking.mNoisy))) {
                if ((this.mSmartActions == null ? 0 : this.mSmartActions.size()) == (ranking.mSmartActions == null ? 0 : ranking.mSmartActions.size()) && java.util.Objects.equals(this.mSmartReplies, ranking.mSmartReplies) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mCanBubble), java.lang.Boolean.valueOf(ranking.mCanBubble)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mIsTextChanged), java.lang.Boolean.valueOf(ranking.mIsTextChanged)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mIsConversation), java.lang.Boolean.valueOf(ranking.mIsConversation))) {
                    if (java.util.Objects.equals(this.mShortcutInfo == null ? 0 : this.mShortcutInfo.getId(), ranking.mShortcutInfo == null ? 0 : ranking.mShortcutInfo.getId()) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mRankingAdjustment), java.lang.Integer.valueOf(ranking.mRankingAdjustment)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mIsBubble), java.lang.Boolean.valueOf(ranking.mIsBubble)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mProposedImportance), java.lang.Integer.valueOf(ranking.mProposedImportance)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mSensitiveContent), java.lang.Boolean.valueOf(ranking.mSensitiveContent))) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static class RankingMap implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.service.notification.NotificationListenerService.RankingMap> CREATOR = new android.os.Parcelable.Creator<android.service.notification.NotificationListenerService.RankingMap>() { // from class: android.service.notification.NotificationListenerService.RankingMap.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.service.notification.NotificationListenerService.RankingMap createFromParcel(android.os.Parcel parcel) {
                return new android.service.notification.NotificationListenerService.RankingMap(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.service.notification.NotificationListenerService.RankingMap[] newArray(int i) {
                return new android.service.notification.NotificationListenerService.RankingMap[i];
            }
        };
        private java.util.ArrayList<java.lang.String> mOrderedKeys;
        private android.util.ArrayMap<java.lang.String, android.service.notification.NotificationListenerService.Ranking> mRankings;

        public RankingMap(android.service.notification.NotificationListenerService.Ranking[] rankingArr) {
            this.mOrderedKeys = new java.util.ArrayList<>();
            this.mRankings = new android.util.ArrayMap<>();
            for (int i = 0; i < rankingArr.length; i++) {
                java.lang.String key = rankingArr[i].getKey();
                this.mOrderedKeys.add(key);
                this.mRankings.put(key, rankingArr[i]);
            }
        }

        private RankingMap(android.os.Parcel parcel) {
            this.mOrderedKeys = new java.util.ArrayList<>();
            this.mRankings = new android.util.ArrayMap<>();
            getClass().getClassLoader();
            int readInt = parcel.readInt();
            this.mOrderedKeys.ensureCapacity(readInt);
            this.mRankings.ensureCapacity(readInt);
            for (int i = 0; i < readInt; i++) {
                android.service.notification.NotificationListenerService.Ranking ranking = new android.service.notification.NotificationListenerService.Ranking(parcel);
                java.lang.String key = ranking.getKey();
                this.mOrderedKeys.add(key);
                this.mRankings.put(key, ranking);
            }
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.service.notification.NotificationListenerService.RankingMap rankingMap = (android.service.notification.NotificationListenerService.RankingMap) obj;
            if (this.mOrderedKeys.equals(rankingMap.mOrderedKeys) && this.mRankings.equals(rankingMap.mRankings)) {
                return true;
            }
            return false;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            int size = this.mOrderedKeys.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                this.mRankings.get(this.mOrderedKeys.get(i2)).writeToParcel(parcel, i);
            }
        }

        public java.lang.String[] getOrderedKeys() {
            return (java.lang.String[]) this.mOrderedKeys.toArray(new java.lang.String[0]);
        }

        public boolean getRanking(java.lang.String str, android.service.notification.NotificationListenerService.Ranking ranking) {
            if (this.mRankings.containsKey(str)) {
                ranking.populate(this.mRankings.get(str));
                return true;
            }
            return false;
        }

        public android.service.notification.NotificationListenerService.Ranking getRawRankingObject(java.lang.String str) {
            return this.mRankings.get(str);
        }
    }

    private final class MyHandler extends android.os.Handler {
        public static final int MSG_ON_INTERRUPTION_FILTER_CHANGED = 6;
        public static final int MSG_ON_LISTENER_CONNECTED = 3;
        public static final int MSG_ON_LISTENER_HINTS_CHANGED = 5;
        public static final int MSG_ON_NOTIFICATION_CHANNEL_GROUP_MODIFIED = 8;
        public static final int MSG_ON_NOTIFICATION_CHANNEL_MODIFIED = 7;
        public static final int MSG_ON_NOTIFICATION_POSTED = 1;
        public static final int MSG_ON_NOTIFICATION_RANKING_UPDATE = 4;
        public static final int MSG_ON_NOTIFICATION_REMOVED = 2;
        public static final int MSG_ON_STATUS_BAR_ICON_BEHAVIOR_CHANGED = 9;

        public MyHandler(android.os.Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (!android.service.notification.NotificationListenerService.this.isConnected) {
            }
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.service.notification.StatusBarNotification statusBarNotification = (android.service.notification.StatusBarNotification) someArgs.arg1;
                    android.service.notification.NotificationListenerService.RankingMap rankingMap = (android.service.notification.NotificationListenerService.RankingMap) someArgs.arg2;
                    someArgs.recycle();
                    android.service.notification.NotificationListenerService.this.onNotificationPosted(statusBarNotification, rankingMap);
                    break;
                case 2:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    android.service.notification.StatusBarNotification statusBarNotification2 = (android.service.notification.StatusBarNotification) someArgs2.arg1;
                    android.service.notification.NotificationListenerService.RankingMap rankingMap2 = (android.service.notification.NotificationListenerService.RankingMap) someArgs2.arg2;
                    int intValue = ((java.lang.Integer) someArgs2.arg3).intValue();
                    android.service.notification.NotificationStats notificationStats = (android.service.notification.NotificationStats) someArgs2.arg4;
                    someArgs2.recycle();
                    android.service.notification.NotificationListenerService.this.onNotificationRemoved(statusBarNotification2, rankingMap2, notificationStats, intValue);
                    break;
                case 3:
                    android.service.notification.NotificationListenerService.this.onListenerConnected();
                    break;
                case 4:
                    android.service.notification.NotificationListenerService.this.onNotificationRankingUpdate((android.service.notification.NotificationListenerService.RankingMap) message.obj);
                    break;
                case 5:
                    android.service.notification.NotificationListenerService.this.onListenerHintsChanged(message.arg1);
                    break;
                case 6:
                    android.service.notification.NotificationListenerService.this.onInterruptionFilterChanged(message.arg1);
                    break;
                case 7:
                    com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str = (java.lang.String) someArgs3.arg1;
                    android.os.UserHandle userHandle = (android.os.UserHandle) someArgs3.arg2;
                    android.app.NotificationChannel notificationChannel = (android.app.NotificationChannel) someArgs3.arg3;
                    int intValue2 = ((java.lang.Integer) someArgs3.arg4).intValue();
                    someArgs3.recycle();
                    android.service.notification.NotificationListenerService.this.onNotificationChannelModified(str, userHandle, notificationChannel, intValue2);
                    break;
                case 8:
                    com.android.internal.os.SomeArgs someArgs4 = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str2 = (java.lang.String) someArgs4.arg1;
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) someArgs4.arg2;
                    android.app.NotificationChannelGroup notificationChannelGroup = (android.app.NotificationChannelGroup) someArgs4.arg3;
                    int intValue3 = ((java.lang.Integer) someArgs4.arg4).intValue();
                    someArgs4.recycle();
                    android.service.notification.NotificationListenerService.this.onNotificationChannelGroupModified(str2, userHandle2, notificationChannelGroup, intValue3);
                    break;
                case 9:
                    android.service.notification.NotificationListenerService.this.onSilentStatusBarIconsVisibilityChanged(((java.lang.Boolean) message.obj).booleanValue());
                    break;
            }
        }
    }
}
