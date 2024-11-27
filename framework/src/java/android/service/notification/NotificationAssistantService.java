package android.service.notification;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class NotificationAssistantService extends android.service.notification.NotificationListenerService {
    public static final java.lang.String ACTION_NOTIFICATION_ASSISTANT_DETAIL_SETTINGS = "android.service.notification.action.NOTIFICATION_ASSISTANT_DETAIL_SETTINGS";
    public static final java.lang.String FEEDBACK_RATING = "feedback.rating";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.notification.NotificationAssistantService";
    public static final int SOURCE_FROM_APP = 0;
    public static final int SOURCE_FROM_ASSISTANT = 1;
    private static final java.lang.String TAG = "NotificationAssistants";
    protected android.os.Handler mHandler;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Source {
    }

    public abstract android.service.notification.Adjustment onNotificationEnqueued(android.service.notification.StatusBarNotification statusBarNotification);

    public abstract void onNotificationSnoozedUntilContext(android.service.notification.StatusBarNotification statusBarNotification, java.lang.String str);

    @Override // android.service.notification.NotificationListenerService, android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
        this.mHandler = new android.service.notification.NotificationAssistantService.MyHandler(getContext().getMainLooper());
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (this.mWrapper == null) {
            this.mWrapper = new android.service.notification.NotificationAssistantService.NotificationAssistantServiceWrapper();
        }
        return this.mWrapper;
    }

    public android.service.notification.Adjustment onNotificationEnqueued(android.service.notification.StatusBarNotification statusBarNotification, android.app.NotificationChannel notificationChannel) {
        return onNotificationEnqueued(statusBarNotification);
    }

    public android.service.notification.Adjustment onNotificationEnqueued(android.service.notification.StatusBarNotification statusBarNotification, android.app.NotificationChannel notificationChannel, android.service.notification.NotificationListenerService.RankingMap rankingMap) {
        return onNotificationEnqueued(statusBarNotification, notificationChannel);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap, android.service.notification.NotificationStats notificationStats, int i) {
        onNotificationRemoved(statusBarNotification, rankingMap, i);
    }

    public void onNotificationsSeen(java.util.List<java.lang.String> list) {
    }

    public void onPanelRevealed(int i) {
    }

    public void onPanelHidden() {
    }

    public void onNotificationVisibilityChanged(java.lang.String str, boolean z) {
    }

    public void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2) {
    }

    public void onNotificationDirectReplied(java.lang.String str) {
    }

    public void onSuggestedReplySent(java.lang.String str, java.lang.CharSequence charSequence, int i) {
    }

    public void onActionInvoked(java.lang.String str, android.app.Notification.Action action, int i) {
    }

    public void onNotificationClicked(java.lang.String str) {
    }

    @java.lang.Deprecated
    public void onAllowedAdjustmentsChanged() {
    }

    public void onNotificationFeedbackReceived(java.lang.String str, android.service.notification.NotificationListenerService.RankingMap rankingMap, android.os.Bundle bundle) {
    }

    public final void adjustNotification(android.service.notification.Adjustment adjustment) {
        if (isBound()) {
            try {
                setAdjustmentIssuer(adjustment);
                getNotificationInterface().applyEnqueuedAdjustmentFromAssistant(this.mWrapper, adjustment);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(TAG, "Unable to contact notification manager", e);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public final void adjustNotifications(java.util.List<android.service.notification.Adjustment> list) {
        if (isBound()) {
            try {
                java.util.Iterator<android.service.notification.Adjustment> it = list.iterator();
                while (it.hasNext()) {
                    setAdjustmentIssuer(it.next());
                }
                getNotificationInterface().applyAdjustmentsFromAssistant(this.mWrapper, list);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(TAG, "Unable to contact notification manager", e);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public final void unsnoozeNotification(java.lang.String str) {
        if (isBound()) {
            try {
                getNotificationInterface().unsnoozeNotificationFromAssistant(this.mWrapper, str);
            } catch (android.os.RemoteException e) {
                android.util.Log.v(TAG, "Unable to contact notification manager", e);
            }
        }
    }

    private class NotificationAssistantServiceWrapper extends android.service.notification.NotificationListenerService.NotificationListenerWrapper {
        private NotificationAssistantServiceWrapper() {
            super();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationEnqueuedWithChannel(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, android.app.NotificationChannel notificationChannel, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) {
            try {
                android.service.notification.StatusBarNotification statusBarNotification = iStatusBarNotificationHolder.get();
                if (statusBarNotification == null) {
                    android.util.Log.w(android.service.notification.NotificationAssistantService.TAG, "onNotificationEnqueuedWithChannel: Error receiving StatusBarNotification");
                    return;
                }
                android.service.notification.NotificationAssistantService.this.applyUpdateLocked(notificationRankingUpdate);
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = statusBarNotification;
                obtain.arg2 = notificationChannel;
                obtain.arg3 = android.service.notification.NotificationAssistantService.this.getCurrentRanking();
                android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(1, obtain).sendToTarget();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.service.notification.NotificationAssistantService.TAG, "onNotificationEnqueued: Error receiving StatusBarNotification", e);
            }
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationSnoozedUntilContext(android.service.notification.IStatusBarNotificationHolder iStatusBarNotificationHolder, java.lang.String str) {
            try {
                android.service.notification.StatusBarNotification statusBarNotification = iStatusBarNotificationHolder.get();
                if (statusBarNotification == null) {
                    android.util.Log.w(android.service.notification.NotificationAssistantService.TAG, "onNotificationSnoozed: Error receiving StatusBarNotification");
                    return;
                }
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = statusBarNotification;
                obtain.arg2 = str;
                android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(2, obtain).sendToTarget();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.service.notification.NotificationAssistantService.TAG, "onNotificationSnoozed: Error receiving StatusBarNotification", e);
            }
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationsSeen(java.util.List<java.lang.String> list) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = list;
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(3, obtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onPanelRevealed(int i) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argi1 = i;
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(9, obtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onPanelHidden() {
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(10, com.android.internal.os.SomeArgs.obtain()).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationVisibilityChanged(java.lang.String str, boolean z) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.argi1 = z ? 1 : 0;
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(11, obtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.argi1 = z ? 1 : 0;
            obtain.argi2 = z2 ? 1 : 0;
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(4, obtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationDirectReply(java.lang.String str) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(5, obtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onSuggestedReplySent(java.lang.String str, java.lang.CharSequence charSequence, int i) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = charSequence;
            obtain.argi2 = i;
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(6, obtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onActionClicked(java.lang.String str, android.app.Notification.Action action, int i) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = action;
            obtain.argi2 = i;
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(7, obtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationClicked(java.lang.String str) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(12, obtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onAllowedAdjustmentsChanged() {
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(8).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationFeedbackReceived(java.lang.String str, android.service.notification.NotificationRankingUpdate notificationRankingUpdate, android.os.Bundle bundle) {
            android.service.notification.NotificationAssistantService.this.applyUpdateLocked(notificationRankingUpdate);
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = android.service.notification.NotificationAssistantService.this.getCurrentRanking();
            obtain.arg3 = bundle;
            android.service.notification.NotificationAssistantService.this.mHandler.obtainMessage(13, obtain).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAdjustmentIssuer(android.service.notification.Adjustment adjustment) {
        if (adjustment != null) {
            adjustment.setIssuer(getOpPackageName() + "/" + getClass().getName());
        }
    }

    private final class MyHandler extends android.os.Handler {
        public static final int MSG_ON_ACTION_INVOKED = 7;
        public static final int MSG_ON_ALLOWED_ADJUSTMENTS_CHANGED = 8;
        public static final int MSG_ON_NOTIFICATIONS_SEEN = 3;
        public static final int MSG_ON_NOTIFICATION_CLICKED = 12;
        public static final int MSG_ON_NOTIFICATION_DIRECT_REPLY_SENT = 5;
        public static final int MSG_ON_NOTIFICATION_ENQUEUED = 1;
        public static final int MSG_ON_NOTIFICATION_EXPANSION_CHANGED = 4;
        public static final int MSG_ON_NOTIFICATION_FEEDBACK_RECEIVED = 13;
        public static final int MSG_ON_NOTIFICATION_SNOOZED = 2;
        public static final int MSG_ON_NOTIFICATION_VISIBILITY_CHANGED = 11;
        public static final int MSG_ON_PANEL_HIDDEN = 10;
        public static final int MSG_ON_PANEL_REVEALED = 9;
        public static final int MSG_ON_SUGGESTED_REPLY_SENT = 6;

        public MyHandler(android.os.Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            boolean z;
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.service.notification.StatusBarNotification statusBarNotification = (android.service.notification.StatusBarNotification) someArgs.arg1;
                    android.app.NotificationChannel notificationChannel = (android.app.NotificationChannel) someArgs.arg2;
                    android.service.notification.NotificationListenerService.RankingMap rankingMap = (android.service.notification.NotificationListenerService.RankingMap) someArgs.arg3;
                    someArgs.recycle();
                    android.service.notification.Adjustment onNotificationEnqueued = android.service.notification.NotificationAssistantService.this.onNotificationEnqueued(statusBarNotification, notificationChannel, rankingMap);
                    android.service.notification.NotificationAssistantService.this.setAdjustmentIssuer(onNotificationEnqueued);
                    if (onNotificationEnqueued != null) {
                        if (!android.service.notification.NotificationAssistantService.this.isBound()) {
                            android.util.Log.w(android.service.notification.NotificationAssistantService.TAG, "MSG_ON_NOTIFICATION_ENQUEUED: service not bound, skip.");
                            return;
                        }
                        try {
                            android.service.notification.NotificationAssistantService.this.getNotificationInterface().applyEnqueuedAdjustmentFromAssistant(android.service.notification.NotificationAssistantService.this.mWrapper, onNotificationEnqueued);
                            return;
                        } catch (android.os.RemoteException e) {
                            android.util.Log.v(android.service.notification.NotificationAssistantService.TAG, "Unable to contact notification manager", e);
                            throw e.rethrowFromSystemServer();
                        } catch (java.lang.SecurityException e2) {
                            android.util.Log.w(android.service.notification.NotificationAssistantService.TAG, "Enqueue adjustment failed; no longer connected", e2);
                            return;
                        }
                    }
                    return;
                case 2:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    android.service.notification.StatusBarNotification statusBarNotification2 = (android.service.notification.StatusBarNotification) someArgs2.arg1;
                    java.lang.String str = (java.lang.String) someArgs2.arg2;
                    someArgs2.recycle();
                    android.service.notification.NotificationAssistantService.this.onNotificationSnoozedUntilContext(statusBarNotification2, str);
                    return;
                case 3:
                    com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                    java.util.List<java.lang.String> list = (java.util.List) someArgs3.arg1;
                    someArgs3.recycle();
                    android.service.notification.NotificationAssistantService.this.onNotificationsSeen(list);
                    return;
                case 4:
                    com.android.internal.os.SomeArgs someArgs4 = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str2 = (java.lang.String) someArgs4.arg1;
                    boolean z2 = someArgs4.argi1 == 1;
                    z = someArgs4.argi2 == 1;
                    someArgs4.recycle();
                    android.service.notification.NotificationAssistantService.this.onNotificationExpansionChanged(str2, z2, z);
                    return;
                case 5:
                    com.android.internal.os.SomeArgs someArgs5 = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str3 = (java.lang.String) someArgs5.arg1;
                    someArgs5.recycle();
                    android.service.notification.NotificationAssistantService.this.onNotificationDirectReplied(str3);
                    return;
                case 6:
                    com.android.internal.os.SomeArgs someArgs6 = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str4 = (java.lang.String) someArgs6.arg1;
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) someArgs6.arg2;
                    int i = someArgs6.argi2;
                    someArgs6.recycle();
                    android.service.notification.NotificationAssistantService.this.onSuggestedReplySent(str4, charSequence, i);
                    return;
                case 7:
                    com.android.internal.os.SomeArgs someArgs7 = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str5 = (java.lang.String) someArgs7.arg1;
                    android.app.Notification.Action action = (android.app.Notification.Action) someArgs7.arg2;
                    int i2 = someArgs7.argi2;
                    someArgs7.recycle();
                    android.service.notification.NotificationAssistantService.this.onActionInvoked(str5, action, i2);
                    return;
                case 8:
                    android.service.notification.NotificationAssistantService.this.onAllowedAdjustmentsChanged();
                    return;
                case 9:
                    com.android.internal.os.SomeArgs someArgs8 = (com.android.internal.os.SomeArgs) message.obj;
                    int i3 = someArgs8.argi1;
                    someArgs8.recycle();
                    android.service.notification.NotificationAssistantService.this.onPanelRevealed(i3);
                    return;
                case 10:
                    android.service.notification.NotificationAssistantService.this.onPanelHidden();
                    return;
                case 11:
                    com.android.internal.os.SomeArgs someArgs9 = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str6 = (java.lang.String) someArgs9.arg1;
                    z = someArgs9.argi1 == 1;
                    someArgs9.recycle();
                    android.service.notification.NotificationAssistantService.this.onNotificationVisibilityChanged(str6, z);
                    return;
                case 12:
                    com.android.internal.os.SomeArgs someArgs10 = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str7 = (java.lang.String) someArgs10.arg1;
                    someArgs10.recycle();
                    android.service.notification.NotificationAssistantService.this.onNotificationClicked(str7);
                    return;
                case 13:
                    com.android.internal.os.SomeArgs someArgs11 = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str8 = (java.lang.String) someArgs11.arg1;
                    android.service.notification.NotificationListenerService.RankingMap rankingMap2 = (android.service.notification.NotificationListenerService.RankingMap) someArgs11.arg2;
                    android.os.Bundle bundle = (android.os.Bundle) someArgs11.arg3;
                    someArgs11.recycle();
                    android.service.notification.NotificationAssistantService.this.onNotificationFeedbackReceived(str8, rankingMap2, bundle);
                    return;
                default:
                    return;
            }
        }
    }
}
