package com.android.server.accessibility;

/* loaded from: classes.dex */
public class PolicyWarningUIController {
    private static final java.lang.String EXTRA_TIME_FOR_LOGGING = "start_time_to_log_a11y_tool";
    private static final int SEND_NOTIFICATION_DELAY_HOURS = 24;
    private final android.app.AlarmManager mAlarmManager;
    private final android.content.Context mContext;
    private final android.util.ArraySet<android.content.ComponentName> mEnabledA11yServices = new android.util.ArraySet<>();
    private final android.os.Handler mMainHandler;
    private final com.android.server.accessibility.PolicyWarningUIController.NotificationController mNotificationController;
    private static final java.lang.String TAG = com.android.server.accessibility.PolicyWarningUIController.class.getSimpleName();

    @com.android.internal.annotations.VisibleForTesting
    protected static final java.lang.String ACTION_SEND_NOTIFICATION = TAG + ".ACTION_SEND_NOTIFICATION";

    @com.android.internal.annotations.VisibleForTesting
    protected static final java.lang.String ACTION_A11Y_SETTINGS = TAG + ".ACTION_A11Y_SETTINGS";

    @com.android.internal.annotations.VisibleForTesting
    protected static final java.lang.String ACTION_DISMISS_NOTIFICATION = TAG + ".ACTION_DISMISS_NOTIFICATION";

    public PolicyWarningUIController(@android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.content.Context context, com.android.server.accessibility.PolicyWarningUIController.NotificationController notificationController) {
        this.mMainHandler = handler;
        this.mContext = context;
        this.mNotificationController = notificationController;
        this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(ACTION_SEND_NOTIFICATION);
        intentFilter.addAction(ACTION_A11Y_SETTINGS);
        intentFilter.addAction(ACTION_DISMISS_NOTIFICATION);
        this.mContext.registerReceiver(this.mNotificationController, intentFilter, "android.permission.MANAGE_ACCESSIBILITY", this.mMainHandler, 2);
    }

    public void onSwitchUser(int i, java.util.Set<android.content.ComponentName> set) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.PolicyWarningUIController$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.accessibility.PolicyWarningUIController.this.onSwitchUserInternal(((java.lang.Integer) obj).intValue(), (java.util.Set) obj2);
            }
        }, java.lang.Integer.valueOf(i), set));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSwitchUserInternal(int i, java.util.Set<android.content.ComponentName> set) {
        this.mEnabledA11yServices.clear();
        this.mEnabledA11yServices.addAll(set);
        this.mNotificationController.onSwitchUser(i);
    }

    public void onEnabledServicesChanged(int i, java.util.Set<android.content.ComponentName> set) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.PolicyWarningUIController$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.accessibility.PolicyWarningUIController.this.onEnabledServicesChangedInternal(((java.lang.Integer) obj).intValue(), (java.util.Set) obj2);
            }
        }, java.lang.Integer.valueOf(i), set));
    }

    void onEnabledServicesChangedInternal(int i, java.util.Set<android.content.ComponentName> set) {
        android.util.ArraySet arraySet = new android.util.ArraySet((android.util.ArraySet) this.mEnabledA11yServices);
        arraySet.removeAll(set);
        this.mEnabledA11yServices.clear();
        this.mEnabledA11yServices.addAll(set);
        android.os.Handler handler = this.mMainHandler;
        final com.android.server.accessibility.PolicyWarningUIController.NotificationController notificationController = this.mNotificationController;
        java.util.Objects.requireNonNull(notificationController);
        handler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.PolicyWarningUIController$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.accessibility.PolicyWarningUIController.NotificationController.this.onServicesDisabled(((java.lang.Integer) obj).intValue(), (android.util.ArraySet) obj2);
            }
        }, java.lang.Integer.valueOf(i), arraySet));
    }

    public void onNonA11yCategoryServiceBound(int i, android.content.ComponentName componentName) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.PolicyWarningUIController$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.accessibility.PolicyWarningUIController.this.setAlarm(((java.lang.Integer) obj).intValue(), (android.content.ComponentName) obj2);
            }
        }, java.lang.Integer.valueOf(i), componentName));
    }

    public void onNonA11yCategoryServiceUnbound(int i, android.content.ComponentName componentName) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.PolicyWarningUIController$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.accessibility.PolicyWarningUIController.this.cancelAlarm(((java.lang.Integer) obj).intValue(), (android.content.ComponentName) obj2);
            }
        }, java.lang.Integer.valueOf(i), componentName));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAlarm(int i, android.content.ComponentName componentName) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.add(10, 24);
        this.mAlarmManager.set(0, calendar.getTimeInMillis(), createPendingIntent(this.mContext, i, ACTION_SEND_NOTIFICATION, componentName));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAlarm(int i, android.content.ComponentName componentName) {
        this.mAlarmManager.cancel(createPendingIntent(this.mContext, i, ACTION_SEND_NOTIFICATION, componentName));
    }

    protected static android.app.PendingIntent createPendingIntent(android.content.Context context, int i, java.lang.String str, android.content.ComponentName componentName) {
        return android.app.PendingIntent.getBroadcast(context, 0, createIntent(context, i, str, componentName), 67108864);
    }

    protected static android.content.Intent createIntent(android.content.Context context, int i, java.lang.String str, android.content.ComponentName componentName) {
        android.content.Intent intent = new android.content.Intent(str);
        intent.setPackage(context.getPackageName()).setIdentifier(componentName.flattenToShortString()).putExtra("android.intent.extra.COMPONENT_NAME", componentName).putExtra("android.intent.extra.USER_ID", i).putExtra("android.intent.extra.TIME", android.os.SystemClock.elapsedRealtime());
        return intent;
    }

    public void enableSendingNonA11yToolNotification(boolean z) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.accessibility.PolicyWarningUIController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.accessibility.PolicyWarningUIController.this.enableSendingNonA11yToolNotificationInternal(((java.lang.Boolean) obj).booleanValue());
            }
        }, java.lang.Boolean.valueOf(z)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableSendingNonA11yToolNotificationInternal(boolean z) {
        this.mNotificationController.setSendingNotification(z);
    }

    public static class NotificationController extends android.content.BroadcastReceiver {
        private static final char RECORD_SEPARATOR = ':';
        private final android.content.Context mContext;
        private int mCurrentUserId;
        private final android.app.NotificationManager mNotificationManager;
        private boolean mSendNotification;
        private final android.util.ArraySet<android.content.ComponentName> mNotifiedA11yServices = new android.util.ArraySet<>();
        private final java.util.List<android.content.ComponentName> mSentA11yServiceNotification = new java.util.ArrayList();

        public NotificationController(android.content.Context context) {
            this.mContext = context;
            this.mNotificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            android.content.ComponentName componentName = (android.content.ComponentName) intent.getParcelableExtra("android.intent.extra.COMPONENT_NAME", android.content.ComponentName.class);
            if (android.text.TextUtils.isEmpty(action) || componentName == null) {
                return;
            }
            long longExtra = intent.getLongExtra("android.intent.extra.TIME", 0L);
            long elapsedRealtime = longExtra > 0 ? android.os.SystemClock.elapsedRealtime() - longExtra : 0L;
            int intExtra = intent.getIntExtra("android.intent.extra.USER_ID", 0);
            if (com.android.server.accessibility.PolicyWarningUIController.ACTION_SEND_NOTIFICATION.equals(action)) {
                if (trySendNotification(intExtra, componentName)) {
                    com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logNonA11yToolServiceWarningReported(componentName.getPackageName(), com.android.internal.accessibility.util.AccessibilityStatsLogUtils.ACCESSIBILITY_PRIVACY_WARNING_STATUS_SHOWN, elapsedRealtime);
                }
            } else {
                if (com.android.server.accessibility.PolicyWarningUIController.ACTION_A11Y_SETTINGS.equals(action)) {
                    if (tryLaunchSettings(intExtra, componentName)) {
                        com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logNonA11yToolServiceWarningReported(componentName.getPackageName(), com.android.internal.accessibility.util.AccessibilityStatsLogUtils.ACCESSIBILITY_PRIVACY_WARNING_STATUS_CLICKED, elapsedRealtime);
                    }
                    this.mNotificationManager.cancel(componentName.flattenToShortString(), 1005);
                    this.mSentA11yServiceNotification.remove(componentName);
                    onNotificationCanceled(intExtra, componentName);
                    return;
                }
                if (com.android.server.accessibility.PolicyWarningUIController.ACTION_DISMISS_NOTIFICATION.equals(action)) {
                    this.mSentA11yServiceNotification.remove(componentName);
                    onNotificationCanceled(intExtra, componentName);
                }
            }
        }

        protected void onSwitchUser(int i) {
            cancelSentNotifications();
            this.mNotifiedA11yServices.clear();
            this.mCurrentUserId = i;
            this.mNotifiedA11yServices.addAll((android.util.ArraySet<? extends android.content.ComponentName>) readNotifiedServiceList(i));
        }

        protected void onServicesDisabled(int i, android.util.ArraySet<android.content.ComponentName> arraySet) {
            if (this.mNotifiedA11yServices.removeAll((android.util.ArraySet<? extends android.content.ComponentName>) arraySet)) {
                writeNotifiedServiceList(i, this.mNotifiedA11yServices);
            }
        }

        private boolean trySendNotification(int i, android.content.ComponentName componentName) {
            if (i != this.mCurrentUserId || !this.mSendNotification) {
                return false;
            }
            java.util.List<android.accessibilityservice.AccessibilityServiceInfo> enabledServiceInfos = getEnabledServiceInfos();
            int i2 = 0;
            while (true) {
                if (i2 >= enabledServiceInfos.size()) {
                    break;
                }
                android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = enabledServiceInfos.get(i2);
                if (!componentName.flattenToShortString().equals(accessibilityServiceInfo.getComponentName().flattenToShortString())) {
                    i2++;
                } else if (!accessibilityServiceInfo.isAccessibilityTool() && !this.mNotifiedA11yServices.contains(componentName)) {
                    java.lang.CharSequence loadLabel = accessibilityServiceInfo.getResolveInfo().serviceInfo.loadLabel(this.mContext.getPackageManager());
                    android.graphics.drawable.Drawable loadIcon = accessibilityServiceInfo.getResolveInfo().loadIcon(this.mContext.getPackageManager());
                    int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(android.R.dimen.app_icon_size);
                    sendNotification(i, componentName, loadLabel, com.android.internal.util.ImageUtils.buildScaledBitmap(loadIcon, dimensionPixelSize, dimensionPixelSize));
                    return true;
                }
            }
            return false;
        }

        private boolean tryLaunchSettings(int i, android.content.ComponentName componentName) {
            if (i != this.mCurrentUserId) {
                return false;
            }
            android.content.Intent intent = new android.content.Intent("android.settings.ACCESSIBILITY_DETAILS_SETTINGS");
            intent.addFlags(268468224);
            intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName.flattenToShortString());
            intent.putExtra(com.android.server.accessibility.PolicyWarningUIController.EXTRA_TIME_FOR_LOGGING, android.os.SystemClock.elapsedRealtime());
            this.mContext.startActivityAsUser(intent, android.app.ActivityOptions.makeBasic().setLaunchDisplayId(this.mContext.getDisplayId()).toBundle(), android.os.UserHandle.of(i));
            ((android.app.StatusBarManager) this.mContext.getSystemService(android.app.StatusBarManager.class)).collapsePanels();
            return true;
        }

        protected void onNotificationCanceled(int i, android.content.ComponentName componentName) {
            if (i == this.mCurrentUserId && this.mNotifiedA11yServices.add(componentName)) {
                writeNotifiedServiceList(i, this.mNotifiedA11yServices);
            }
        }

        private void sendNotification(int i, android.content.ComponentName componentName, java.lang.CharSequence charSequence, android.graphics.Bitmap bitmap) {
            android.app.Notification.Builder builder = new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.ACCESSIBILITY_SECURITY_POLICY);
            builder.setSmallIcon(android.R.drawable.ic_ab_back_material_settings).setContentTitle(this.mContext.getString(android.R.string.usb_contaminant_detected_message)).setContentText(this.mContext.getString(android.R.string.usb_charging_notification_title, charSequence)).setStyle(new android.app.Notification.BigTextStyle().bigText(this.mContext.getString(android.R.string.usb_charging_notification_title, charSequence))).setTicker(this.mContext.getString(android.R.string.usb_contaminant_detected_message)).setOnlyAlertOnce(true).setDeleteIntent(com.android.server.accessibility.PolicyWarningUIController.createPendingIntent(this.mContext, i, com.android.server.accessibility.PolicyWarningUIController.ACTION_DISMISS_NOTIFICATION, componentName)).setContentIntent(com.android.server.accessibility.PolicyWarningUIController.createPendingIntent(this.mContext, i, com.android.server.accessibility.PolicyWarningUIController.ACTION_A11Y_SETTINGS, componentName));
            if (bitmap != null) {
                builder.setLargeIcon(bitmap);
            }
            this.mNotificationManager.notify(componentName.flattenToShortString(), 1005, builder.build());
            this.mSentA11yServiceNotification.add(componentName);
        }

        private android.util.ArraySet<android.content.ComponentName> readNotifiedServiceList(int i) {
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "notified_non_accessibility_category_services", i);
            if (android.text.TextUtils.isEmpty(stringForUser)) {
                return new android.util.ArraySet<>();
            }
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(RECORD_SEPARATOR);
            simpleStringSplitter.setString(stringForUser);
            android.util.ArraySet<android.content.ComponentName> arraySet = new android.util.ArraySet<>();
            java.util.Iterator it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString((java.lang.String) it.next());
                if (unflattenFromString != null) {
                    arraySet.add(unflattenFromString);
                }
            }
            return arraySet;
        }

        private void writeNotifiedServiceList(int i, android.util.ArraySet<android.content.ComponentName> arraySet) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (int i2 = 0; i2 < arraySet.size(); i2++) {
                if (i2 > 0) {
                    sb.append(RECORD_SEPARATOR);
                }
                sb.append(arraySet.valueAt(i2).flattenToShortString());
            }
            android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "notified_non_accessibility_category_services", sb.toString(), i);
        }

        @com.android.internal.annotations.VisibleForTesting
        protected java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getEnabledServiceInfos() {
            return android.view.accessibility.AccessibilityManager.getInstance(this.mContext).getEnabledAccessibilityServiceList(-1);
        }

        private void cancelSentNotifications() {
            this.mSentA11yServiceNotification.forEach(new java.util.function.Consumer() { // from class: com.android.server.accessibility.PolicyWarningUIController$NotificationController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.accessibility.PolicyWarningUIController.NotificationController.this.lambda$cancelSentNotifications$0((android.content.ComponentName) obj);
                }
            });
            this.mSentA11yServiceNotification.clear();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$cancelSentNotifications$0(android.content.ComponentName componentName) {
            this.mNotificationManager.cancel(componentName.flattenToShortString(), 1005);
        }

        void setSendingNotification(boolean z) {
            this.mSendNotification = z;
        }
    }
}
