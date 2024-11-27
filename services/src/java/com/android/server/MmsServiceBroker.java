package com.android.server;

/* loaded from: classes.dex */
public class MmsServiceBroker extends com.android.server.SystemService {
    private static final int MSG_TRY_CONNECTING = 1;
    private static final long RETRY_DELAY_ON_DISCONNECTION_MS = 3000;
    private static final long SERVICE_CONNECTION_WAIT_TIME_MS = 4000;
    private static final java.lang.String TAG = "MmsServiceBroker";
    private volatile android.app.AppOpsManager mAppOpsManager;
    private android.content.ServiceConnection mConnection;
    private final android.os.Handler mConnectionHandler;
    private android.content.Context mContext;
    private volatile android.content.pm.PackageManager mPackageManager;
    private volatile com.android.internal.telephony.IMms mService;
    private final com.android.internal.telephony.IMms mServiceStubForFailure;
    private volatile android.telephony.TelephonyManager mTelephonyManager;
    private static final android.content.ComponentName MMS_SERVICE_COMPONENT = new android.content.ComponentName("com.android.mms.service", "com.android.mms.service.MmsService");
    private static final android.net.Uri FAKE_SMS_SENT_URI = android.net.Uri.parse("content://sms/sent/0");
    private static final android.net.Uri FAKE_MMS_SENT_URI = android.net.Uri.parse("content://mms/sent/0");
    private static final android.net.Uri FAKE_SMS_DRAFT_URI = android.net.Uri.parse("content://sms/draft/0");
    private static final android.net.Uri FAKE_MMS_DRAFT_URI = android.net.Uri.parse("content://mms/draft/0");

    public MmsServiceBroker(android.content.Context context) {
        super(context);
        this.mAppOpsManager = null;
        this.mPackageManager = null;
        this.mTelephonyManager = null;
        this.mConnectionHandler = new android.os.Handler() { // from class: com.android.server.MmsServiceBroker.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.server.MmsServiceBroker.this.tryConnecting();
                        break;
                    default:
                        android.util.Slog.e(com.android.server.MmsServiceBroker.TAG, "Unknown message");
                        break;
                }
            }
        };
        this.mConnection = new android.content.ServiceConnection() { // from class: com.android.server.MmsServiceBroker.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                android.util.Slog.i(com.android.server.MmsServiceBroker.TAG, "MmsService connected");
                synchronized (com.android.server.MmsServiceBroker.this) {
                    com.android.server.MmsServiceBroker.this.mService = com.android.internal.telephony.IMms.Stub.asInterface(android.os.Binder.allowBlocking(iBinder));
                    com.android.server.MmsServiceBroker.this.notifyAll();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                android.util.Slog.i(com.android.server.MmsServiceBroker.TAG, "MmsService unexpectedly disconnected");
                synchronized (com.android.server.MmsServiceBroker.this) {
                    com.android.server.MmsServiceBroker.this.mService = null;
                    com.android.server.MmsServiceBroker.this.notifyAll();
                }
                com.android.server.MmsServiceBroker.this.mConnectionHandler.sendMessageDelayed(com.android.server.MmsServiceBroker.this.mConnectionHandler.obtainMessage(1), 3000L);
            }
        };
        this.mServiceStubForFailure = new com.android.internal.telephony.IMms() { // from class: com.android.server.MmsServiceBroker.3
            public android.os.IBinder asBinder() {
                return null;
            }

            public void sendMessage(int i, java.lang.String str, android.net.Uri uri, java.lang.String str2, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j, java.lang.String str3) throws android.os.RemoteException {
                returnPendingIntentWithError(pendingIntent);
            }

            public void downloadMessage(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j, java.lang.String str3) throws android.os.RemoteException {
                returnPendingIntentWithError(pendingIntent);
            }

            public android.net.Uri importTextMessage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, long j, boolean z, boolean z2) throws android.os.RemoteException {
                return null;
            }

            public android.net.Uri importMultimediaMessage(java.lang.String str, android.net.Uri uri, java.lang.String str2, long j, boolean z, boolean z2) throws android.os.RemoteException {
                return null;
            }

            public boolean deleteStoredMessage(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
                return false;
            }

            public boolean deleteStoredConversation(java.lang.String str, long j) throws android.os.RemoteException {
                return false;
            }

            public boolean updateStoredMessageStatus(java.lang.String str, android.net.Uri uri, android.content.ContentValues contentValues) throws android.os.RemoteException {
                return false;
            }

            public boolean archiveStoredConversation(java.lang.String str, long j, boolean z) throws android.os.RemoteException {
                return false;
            }

            public android.net.Uri addTextMessageDraft(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                return null;
            }

            public android.net.Uri addMultimediaMessageDraft(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
                return null;
            }

            public void sendStoredMessage(int i, java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                returnPendingIntentWithError(pendingIntent);
            }

            public void setAutoPersisting(java.lang.String str, boolean z) throws android.os.RemoteException {
            }

            public boolean getAutoPersisting() throws android.os.RemoteException {
                return false;
            }

            private void returnPendingIntentWithError(android.app.PendingIntent pendingIntent) {
                try {
                    pendingIntent.send(com.android.server.MmsServiceBroker.this.mContext, 1, (android.content.Intent) null);
                } catch (android.app.PendingIntent.CanceledException e) {
                    android.util.Slog.e(com.android.server.MmsServiceBroker.TAG, "Failed to return pending intent result", e);
                }
            }
        };
        this.mContext = context;
        this.mService = null;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("imms", new com.android.server.MmsServiceBroker.BinderService());
    }

    public void systemRunning() {
        android.util.Slog.i(TAG, "Delay connecting to MmsService until an API is called");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryConnecting() {
        android.util.Slog.i(TAG, "Connecting to MmsService");
        synchronized (this) {
            try {
                if (this.mService != null) {
                    android.util.Slog.d(TAG, "Already connected");
                    return;
                }
                android.content.Intent intent = new android.content.Intent();
                intent.setComponent(MMS_SERVICE_COMPONENT);
                try {
                    if (!this.mContext.bindService(intent, this.mConnection, 1)) {
                        android.util.Slog.e(TAG, "Failed to bind to MmsService");
                    }
                } catch (java.lang.SecurityException e) {
                    android.util.Slog.e(TAG, "Forbidden to bind to MmsService", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private com.android.internal.telephony.IMms getOrConnectService() {
        synchronized (this) {
            try {
                if (this.mService != null) {
                    return this.mService;
                }
                android.util.Slog.w(TAG, "MmsService not connected. Try connecting...");
                this.mConnectionHandler.sendMessage(this.mConnectionHandler.obtainMessage(1));
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                long j = elapsedRealtime + SERVICE_CONNECTION_WAIT_TIME_MS;
                for (long j2 = SERVICE_CONNECTION_WAIT_TIME_MS; j2 > 0; j2 = j - android.os.SystemClock.elapsedRealtime()) {
                    try {
                        wait(j2);
                    } catch (java.lang.InterruptedException e) {
                        android.util.Slog.w(TAG, "Connection wait interrupted", e);
                    }
                    if (this.mService != null) {
                        return this.mService;
                    }
                }
                android.util.Slog.e(TAG, "Can not connect to MmsService (timed out)");
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.internal.telephony.IMms getServiceGuarded() {
        com.android.internal.telephony.IMms orConnectService = getOrConnectService();
        if (orConnectService != null) {
            return orConnectService;
        }
        return this.mServiceStubForFailure;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.AppOpsManager getAppOpsManager() {
        if (this.mAppOpsManager == null) {
            this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService("appops");
        }
        return this.mAppOpsManager;
    }

    private android.content.pm.PackageManager getPackageManager() {
        if (this.mPackageManager == null) {
            this.mPackageManager = this.mContext.getPackageManager();
        }
        return this.mPackageManager;
    }

    private android.telephony.TelephonyManager getTelephonyManager() {
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PHONE);
        }
        return this.mTelephonyManager;
    }

    private java.lang.String getCallingPackageName() {
        java.lang.String[] packagesForUid = getPackageManager().getPackagesForUid(android.os.Binder.getCallingUid());
        if (packagesForUid != null && packagesForUid.length > 0) {
            return packagesForUid[0];
        }
        return "unknown";
    }

    private final class BinderService extends com.android.internal.telephony.IMms.Stub {
        private static final java.lang.String PHONE_PACKAGE_NAME = "com.android.phone";

        private BinderService() {
        }

        public void sendMessage(int i, java.lang.String str, android.net.Uri uri, java.lang.String str2, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j, java.lang.String str3) throws android.os.RemoteException {
            android.util.Slog.d(com.android.server.MmsServiceBroker.TAG, "sendMessage() by " + str);
            com.android.server.MmsServiceBroker.this.mContext.enforceCallingPermission("android.permission.SEND_SMS", "Send MMS message");
            if (!com.android.internal.telephony.TelephonyPermissions.checkSubscriptionAssociatedWithUser(com.android.server.MmsServiceBroker.this.mContext, i, android.os.Binder.getCallingUserHandle()) && com.android.server.MmsServiceBroker.this.isActiveSubId(i)) {
                com.android.internal.telephony.util.TelephonyUtils.showSwitchToManagedProfileDialogIfAppropriate(com.android.server.MmsServiceBroker.this.mContext, i, android.os.Binder.getCallingUid(), str);
                return;
            }
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(20, android.os.Binder.getCallingUid(), str, str3, (java.lang.String) null) != 0) {
                android.util.Slog.e(com.android.server.MmsServiceBroker.TAG, str + " is not allowed to call sendMessage()");
                return;
            }
            com.android.server.MmsServiceBroker.this.getServiceGuarded().sendMessage(i, str, adjustUriForUserAndGrantPermission(uri, "android.service.carrier.CarrierMessagingService", 1, i), str2, bundle, pendingIntent, j, str3);
        }

        public void downloadMessage(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j, java.lang.String str3) throws android.os.RemoteException {
            android.util.Slog.d(com.android.server.MmsServiceBroker.TAG, "downloadMessage() by " + str);
            com.android.server.MmsServiceBroker.this.mContext.enforceCallingPermission("android.permission.RECEIVE_MMS", "Download MMS message");
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(18, android.os.Binder.getCallingUid(), str, str3, (java.lang.String) null) != 0) {
                android.util.Slog.e(com.android.server.MmsServiceBroker.TAG, str + " is not allowed to call downloadMessage()");
                return;
            }
            com.android.server.MmsServiceBroker.this.getServiceGuarded().downloadMessage(i, str, str2, adjustUriForUserAndGrantPermission(uri, "android.service.carrier.CarrierMessagingService", 3, i), bundle, pendingIntent, j, str3);
        }

        public android.net.Uri importTextMessage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, long j, boolean z, boolean z2) throws android.os.RemoteException {
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(15, android.os.Binder.getCallingUid(), str, (java.lang.String) null, (java.lang.String) null) != 0) {
                return com.android.server.MmsServiceBroker.FAKE_SMS_SENT_URI;
            }
            return com.android.server.MmsServiceBroker.this.getServiceGuarded().importTextMessage(str, str2, i, str3, j, z, z2);
        }

        public android.net.Uri importMultimediaMessage(java.lang.String str, android.net.Uri uri, java.lang.String str2, long j, boolean z, boolean z2) throws android.os.RemoteException {
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(15, android.os.Binder.getCallingUid(), str, (java.lang.String) null, (java.lang.String) null) != 0) {
                return com.android.server.MmsServiceBroker.FAKE_MMS_SENT_URI;
            }
            return com.android.server.MmsServiceBroker.this.getServiceGuarded().importMultimediaMessage(str, uri, str2, j, z, z2);
        }

        public boolean deleteStoredMessage(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(15, android.os.Binder.getCallingUid(), str, (java.lang.String) null, (java.lang.String) null) != 0) {
                return false;
            }
            return com.android.server.MmsServiceBroker.this.getServiceGuarded().deleteStoredMessage(str, uri);
        }

        public boolean deleteStoredConversation(java.lang.String str, long j) throws android.os.RemoteException {
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(15, android.os.Binder.getCallingUid(), str, (java.lang.String) null, (java.lang.String) null) != 0) {
                return false;
            }
            return com.android.server.MmsServiceBroker.this.getServiceGuarded().deleteStoredConversation(str, j);
        }

        public boolean updateStoredMessageStatus(java.lang.String str, android.net.Uri uri, android.content.ContentValues contentValues) throws android.os.RemoteException {
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(15, android.os.Binder.getCallingUid(), str, (java.lang.String) null, (java.lang.String) null) != 0) {
                return false;
            }
            return com.android.server.MmsServiceBroker.this.getServiceGuarded().updateStoredMessageStatus(str, uri, contentValues);
        }

        public boolean archiveStoredConversation(java.lang.String str, long j, boolean z) throws android.os.RemoteException {
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(15, android.os.Binder.getCallingUid(), str, (java.lang.String) null, (java.lang.String) null) != 0) {
                return false;
            }
            return com.android.server.MmsServiceBroker.this.getServiceGuarded().archiveStoredConversation(str, j, z);
        }

        public android.net.Uri addTextMessageDraft(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(15, android.os.Binder.getCallingUid(), str, (java.lang.String) null, (java.lang.String) null) != 0) {
                return com.android.server.MmsServiceBroker.FAKE_SMS_DRAFT_URI;
            }
            return com.android.server.MmsServiceBroker.this.getServiceGuarded().addTextMessageDraft(str, str2, str3);
        }

        public android.net.Uri addMultimediaMessageDraft(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(15, android.os.Binder.getCallingUid(), str, (java.lang.String) null, (java.lang.String) null) != 0) {
                return com.android.server.MmsServiceBroker.FAKE_MMS_DRAFT_URI;
            }
            return com.android.server.MmsServiceBroker.this.getServiceGuarded().addMultimediaMessageDraft(str, uri);
        }

        public void sendStoredMessage(int i, java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(20, android.os.Binder.getCallingUid(), str, (java.lang.String) null, (java.lang.String) null) != 0) {
                return;
            }
            com.android.server.MmsServiceBroker.this.getServiceGuarded().sendStoredMessage(i, str, uri, bundle, pendingIntent);
        }

        public void setAutoPersisting(java.lang.String str, boolean z) throws android.os.RemoteException {
            if (com.android.server.MmsServiceBroker.this.getAppOpsManager().noteOp(15, android.os.Binder.getCallingUid(), str, (java.lang.String) null, (java.lang.String) null) != 0) {
                return;
            }
            com.android.server.MmsServiceBroker.this.getServiceGuarded().setAutoPersisting(str, z);
        }

        public boolean getAutoPersisting() throws android.os.RemoteException {
            return com.android.server.MmsServiceBroker.this.getServiceGuarded().getAutoPersisting();
        }

        private android.net.Uri adjustUriForUserAndGrantPermission(android.net.Uri uri, java.lang.String str, int i, int i2) {
            android.content.Intent intent = new android.content.Intent();
            intent.setData(uri);
            intent.setFlags(i);
            int callingUid = android.os.Binder.getCallingUid();
            int callingUserId = android.os.UserHandle.getCallingUserId();
            if (callingUserId != 0) {
                uri = android.content.ContentProvider.maybeAddUserId(uri, callingUserId);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.uri.UriGrantsManagerInternal uriGrantsManagerInternal = (com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class);
                uriGrantsManagerInternal.grantUriPermissionUncheckedFromIntent(uriGrantsManagerInternal.checkGrantUriPermissionFromIntent(intent, callingUid, PHONE_PACKAGE_NAME, 0), null);
                java.util.List carrierPackageNamesForIntentAndPhone = ((android.telephony.TelephonyManager) com.android.server.MmsServiceBroker.this.mContext.getSystemService(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PHONE)).getCarrierPackageNamesForIntentAndPhone(new android.content.Intent(str), com.android.server.MmsServiceBroker.this.getPhoneIdFromSubId(i2));
                if (carrierPackageNamesForIntentAndPhone != null && carrierPackageNamesForIntentAndPhone.size() == 1) {
                    uriGrantsManagerInternal.grantUriPermissionUncheckedFromIntent(uriGrantsManagerInternal.checkGrantUriPermissionFromIntent(intent, callingUid, (java.lang.String) carrierPackageNamesForIntentAndPhone.get(0), 0), null);
                }
                return uri;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isActiveSubId(int i) {
        boolean z;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class);
            if (subscriptionManager != null) {
                if (subscriptionManager.isActiveSubscriptionId(i)) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPhoneIdFromSubId(int i) {
        android.telephony.SubscriptionInfo activeSubscriptionInfo;
        android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
        if (subscriptionManager == null || (activeSubscriptionInfo = subscriptionManager.getActiveSubscriptionInfo(i)) == null) {
            return -1;
        }
        return activeSubscriptionInfo.getSimSlotIndex();
    }
}
