package com.android.server;

/* loaded from: classes.dex */
public class MasterClearReceiver extends android.content.BroadcastReceiver {
    private static final java.lang.String TAG = "MasterClear";
    private boolean mShowWipeProgress = true;
    private boolean mWipeEsims;
    private boolean mWipeExternalStorage;

    @Override // android.content.BroadcastReceiver
    public void onReceive(final android.content.Context context, android.content.Intent intent) {
        if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE") && !"google.com".equals(intent.getStringExtra("from"))) {
            android.util.Slog.w(TAG, "Ignoring master clear request -- not from trusted server.");
            return;
        }
        if ("android.intent.action.MASTER_CLEAR".equals(intent.getAction())) {
            android.util.Slog.w(TAG, "The request uses the deprecated Intent#ACTION_MASTER_CLEAR, Intent#ACTION_FACTORY_RESET should be used instead.");
        }
        if (intent.hasExtra("android.intent.extra.FORCE_MASTER_CLEAR")) {
            android.util.Slog.w(TAG, "The request uses the deprecated Intent#EXTRA_FORCE_MASTER_CLEAR, Intent#EXTRA_FORCE_FACTORY_RESET should be used instead.");
        }
        java.lang.String string = context.getString(android.R.string.config_dozeTapSensorType);
        if ("android.intent.action.FACTORY_RESET".equals(intent.getAction()) && !android.text.TextUtils.isEmpty(string)) {
            android.util.Slog.i(TAG, "Re-directing intent to " + string);
            intent.setPackage(string).setComponent(null);
            context.sendBroadcastAsUser(intent, android.os.UserHandle.SYSTEM);
            return;
        }
        final java.lang.String stringExtra = intent.getStringExtra("android.intent.extra.REASON");
        this.mShowWipeProgress = intent.getBooleanExtra("android.intent.extra.SHOW_WIPE_PROGRESS", true);
        final boolean booleanExtra = intent.getBooleanExtra("shutdown", false);
        this.mWipeExternalStorage = intent.getBooleanExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", false);
        this.mWipeEsims = intent.getBooleanExtra("com.android.internal.intent.extra.WIPE_ESIMS", false);
        final boolean z = intent.getBooleanExtra("android.intent.extra.FORCE_MASTER_CLEAR", false) || intent.getBooleanExtra("android.intent.extra.FORCE_FACTORY_RESET", false);
        final boolean booleanExtra2 = intent.getBooleanExtra("keep_memtag_mode", false);
        final int sendingUserId = getSendingUserId();
        if (sendingUserId != 0 && !android.os.UserManager.isHeadlessSystemUserMode()) {
            com.android.server.utils.Slogf.w(TAG, "ACTION_FACTORY_RESET received on a non-system user %d, WIPING THE USER!!", java.lang.Integer.valueOf(sendingUserId));
            if (!((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.MasterClearReceiver$$ExternalSyntheticLambda0
                public final java.lang.Object getOrThrow() {
                    java.lang.Boolean lambda$onReceive$0;
                    lambda$onReceive$0 = com.android.server.MasterClearReceiver.this.lambda$onReceive$0(context, sendingUserId, stringExtra);
                    return lambda$onReceive$0;
                }
            })).booleanValue()) {
                com.android.server.utils.Slogf.e(TAG, "Failed to wipe user %d", java.lang.Integer.valueOf(sendingUserId));
                return;
            }
            return;
        }
        android.util.Slog.w(TAG, "!!! FACTORY RESET !!!");
        java.lang.Thread thread = new java.lang.Thread("Reboot") { // from class: com.android.server.MasterClearReceiver.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    android.util.Slog.i(com.android.server.MasterClearReceiver.TAG, "Calling RecoverySystem.rebootWipeUserData(context, shutdown=" + booleanExtra + ", reason=" + stringExtra + ", forceWipe=" + z + ", wipeEsims=" + com.android.server.MasterClearReceiver.this.mWipeEsims + ", keepMemtagMode=" + booleanExtra2 + ")");
                    android.os.RecoverySystem.rebootWipeUserData(context, booleanExtra, stringExtra, z, com.android.server.MasterClearReceiver.this.mWipeEsims, booleanExtra2);
                    android.util.Slog.wtf(com.android.server.MasterClearReceiver.TAG, "Still running after master clear?!");
                } catch (java.io.IOException e) {
                    android.util.Slog.e(com.android.server.MasterClearReceiver.TAG, "Can't perform master clear/factory reset", e);
                } catch (java.lang.SecurityException e2) {
                    android.util.Slog.e(com.android.server.MasterClearReceiver.TAG, "Can't perform master clear/factory reset", e2);
                }
            }
        };
        if (this.mWipeExternalStorage) {
            android.util.Slog.i(TAG, "Wiping external storage on async task");
            new com.android.server.MasterClearReceiver.WipeDataTask(context, thread).execute(new java.lang.Void[0]);
            return;
        }
        android.util.Slog.i(TAG, "NOT wiping external storage; starting thread " + thread.getName());
        thread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$onReceive$0(android.content.Context context, int i, java.lang.String str) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(wipeUser(context, i, str));
    }

    private boolean wipeUser(android.content.Context context, int i, java.lang.String str) {
        android.os.UserManager userManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        if (!android.os.UserManager.isRemoveResultSuccessful(userManager.removeUserWhenPossible(android.os.UserHandle.of(i), false))) {
            com.android.server.utils.Slogf.e(TAG, "Can't remove user %d", java.lang.Integer.valueOf(i));
            return false;
        }
        if (getCurrentForegroundUserId() == i) {
            try {
                if (!android.app.ActivityManager.getService().switchUser(0)) {
                    com.android.server.utils.Slogf.w(TAG, "Can't switch from current user %d, user will get removed when it is stopped.", java.lang.Integer.valueOf(i));
                }
            } catch (android.os.RemoteException e) {
                com.android.server.utils.Slogf.w(TAG, "Can't switch from current user %d, user will get removed when it is stopped.", java.lang.Integer.valueOf(i));
            }
        }
        if (userManager.isManagedProfile(i)) {
            sendWipeProfileNotification(context, str);
            return true;
        }
        return true;
    }

    private void sendWipeProfileNotification(android.content.Context context, java.lang.String str) {
        ((android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class)).notify(1001, new android.app.Notification.Builder(context, com.android.internal.notification.SystemNotificationChannels.DEVICE_ADMIN).setSmallIcon(android.R.drawable.stat_sys_warning).setContentTitle(getWorkProfileDeletedTitle(context)).setContentText(str).setColor(context.getColor(android.R.color.system_notification_accent_color)).setStyle(new android.app.Notification.BigTextStyle().bigText(str)).build());
    }

    private java.lang.String getWorkProfileDeletedTitle(final android.content.Context context) {
        return ((android.app.admin.DevicePolicyManager) context.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString("Core.WORK_PROFILE_DELETED_TITLE", new java.util.function.Supplier() { // from class: com.android.server.MasterClearReceiver$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getWorkProfileDeletedTitle$1;
                lambda$getWorkProfileDeletedTitle$1 = com.android.server.MasterClearReceiver.lambda$getWorkProfileDeletedTitle$1(context);
                return lambda$getWorkProfileDeletedTitle$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$getWorkProfileDeletedTitle$1(android.content.Context context) {
        return context.getString(android.R.string.whichEditApplicationLabel);
    }

    private int getCurrentForegroundUserId() {
        try {
            return android.app.ActivityManager.getCurrentUser();
        } catch (java.lang.Exception e) {
            com.android.server.utils.Slogf.e(TAG, "Can't get current user", e);
            return com.android.server.am.ProcessList.INVALID_ADJ;
        }
    }

    private class WipeDataTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void> {
        private final java.lang.Thread mChainedTask;
        private final android.content.Context mContext;
        private final android.app.ProgressDialog mProgressDialog;

        public WipeDataTask(android.content.Context context, java.lang.Thread thread) {
            android.app.ProgressDialog progressDialog;
            this.mContext = context;
            this.mChainedTask = thread;
            if (com.android.server.MasterClearReceiver.this.mShowWipeProgress) {
                progressDialog = new android.app.ProgressDialog(context, android.R.style.Theme.DeviceDefault.Settings.SearchBar);
            } else {
                progressDialog = null;
            }
            this.mProgressDialog = progressDialog;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            if (this.mProgressDialog != null) {
                this.mProgressDialog.setIndeterminate(true);
                this.mProgressDialog.getWindow().setType(2003);
                this.mProgressDialog.setMessage(this.mContext.getText(android.R.string.policylab_wipeData_secondaryUser));
                this.mProgressDialog.show();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public java.lang.Void doInBackground(java.lang.Void... voidArr) {
            android.util.Slog.w(com.android.server.MasterClearReceiver.TAG, "Wiping adoptable disks");
            if (com.android.server.MasterClearReceiver.this.mWipeExternalStorage) {
                ((android.os.storage.StorageManager) this.mContext.getSystemService("storage")).wipeAdoptableDisks();
                return null;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(java.lang.Void r1) {
            if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
                this.mProgressDialog.dismiss();
            }
            this.mChainedTask.start();
        }
    }
}
