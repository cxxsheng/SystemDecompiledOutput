package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
public class ContextHubClientBroker extends android.hardware.location.IContextHubClient.Stub implements android.os.IBinder.DeathRecipient, android.app.AppOpsManager.OnOpChangedListener, android.app.PendingIntent.OnFinished {
    private static final int AUTHORIZATION_UNKNOWN = -1;
    private static final long CHANGE_ID_AUTH_STATE_DENIED = 181350407;
    private static final java.lang.String RECEIVE_MSG_NOTE = "NanoappMessageDelivery ";
    private static final java.lang.String TAG = "ContextHubClientBroker";
    private static final long WAKELOCK_TIMEOUT_MILLIS = 5000;
    private final android.app.AppOpsManager mAppOpsManager;
    private final android.hardware.location.ContextHubInfo mAttachedContextHubInfo;

    @android.annotation.Nullable
    private java.lang.String mAttributionTag;
    private final com.android.server.location.contexthub.ContextHubClientManager mClientManager;
    private final android.content.Context mContext;
    private android.hardware.location.IContextHubClientCallback mContextHubClientCallback;
    private final com.android.server.location.contexthub.IContextHubWrapper mContextHubProxy;
    private final java.util.Set<java.lang.Long> mForceDeniedNapps;
    private final short mHostEndPointId;
    private final java.util.concurrent.atomic.AtomicBoolean mIsPendingIntentCancelled;
    private final java.util.concurrent.atomic.AtomicBoolean mIsPermQueryIssued;
    private java.util.concurrent.atomic.AtomicBoolean mIsWakelockUsable;
    private final java.util.Map<java.lang.Long, java.lang.Integer> mMessageChannelNanoappIdMap;
    private final java.util.Map<java.lang.Long, com.android.server.location.contexthub.AuthStateDenialTimer> mNappToAuthTimerMap;
    private final java.lang.String mPackage;
    private final com.android.server.location.contexthub.ContextHubClientBroker.PendingIntentRequest mPendingIntentRequest;
    private final int mPid;
    private final android.hardware.location.IContextHubTransactionCallback mQueryPermsCallback;
    private boolean mRegistered;
    private final com.android.server.location.contexthub.ContextHubTransactionManager mTransactionManager;
    private final int mUid;

    @com.android.internal.annotations.GuardedBy({"mWakeLock"})
    private final android.os.PowerManager.WakeLock mWakeLock;

    @com.android.internal.annotations.VisibleForTesting
    interface CallbackConsumer {
        void accept(android.hardware.location.IContextHubClientCallback iContextHubClientCallback) throws android.os.RemoteException;
    }

    private static class PendingIntentRequest {
        private long mNanoAppId;
        private android.app.PendingIntent mPendingIntent;
        private boolean mValid;

        PendingIntentRequest() {
            this.mValid = false;
        }

        PendingIntentRequest(android.app.PendingIntent pendingIntent, long j) {
            this.mValid = false;
            this.mPendingIntent = pendingIntent;
            this.mNanoAppId = j;
            this.mValid = true;
        }

        public long getNanoAppId() {
            return this.mNanoAppId;
        }

        public android.app.PendingIntent getPendingIntent() {
            return this.mPendingIntent;
        }

        public boolean hasPendingIntent() {
            return this.mPendingIntent != null;
        }

        public void clear() {
            this.mPendingIntent = null;
        }

        public boolean isValid() {
            return this.mValid;
        }
    }

    private ContextHubClientBroker(android.content.Context context, com.android.server.location.contexthub.IContextHubWrapper iContextHubWrapper, com.android.server.location.contexthub.ContextHubClientManager contextHubClientManager, android.hardware.location.ContextHubInfo contextHubInfo, short s, android.hardware.location.IContextHubClientCallback iContextHubClientCallback, java.lang.String str, com.android.server.location.contexthub.ContextHubTransactionManager contextHubTransactionManager, android.app.PendingIntent pendingIntent, long j, java.lang.String str2) {
        this.mRegistered = true;
        this.mIsWakelockUsable = new java.util.concurrent.atomic.AtomicBoolean(true);
        this.mIsPendingIntentCancelled = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mIsPermQueryIssued = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mMessageChannelNanoappIdMap = new java.util.concurrent.ConcurrentHashMap();
        this.mForceDeniedNapps = new java.util.HashSet();
        this.mNappToAuthTimerMap = new java.util.concurrent.ConcurrentHashMap();
        this.mQueryPermsCallback = new android.hardware.location.IContextHubTransactionCallback.Stub() { // from class: com.android.server.location.contexthub.ContextHubClientBroker.1
            public void onTransactionComplete(int i) {
            }

            public void onQueryResponse(int i, java.util.List<android.hardware.location.NanoAppState> list) {
                com.android.server.location.contexthub.ContextHubClientBroker.this.mIsPermQueryIssued.set(false);
                if (i != 0 && list != null) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubClientBroker.TAG, "Permissions query failed, but still received nanoapp state");
                    return;
                }
                if (list != null) {
                    for (android.hardware.location.NanoAppState nanoAppState : list) {
                        if (com.android.server.location.contexthub.ContextHubClientBroker.this.mMessageChannelNanoappIdMap.containsKey(java.lang.Long.valueOf(nanoAppState.getNanoAppId()))) {
                            com.android.server.location.contexthub.ContextHubClientBroker.this.updateNanoAppAuthState(nanoAppState.getNanoAppId(), nanoAppState.getNanoAppPermissions(), false);
                        }
                    }
                }
            }
        };
        this.mContext = context;
        this.mContextHubProxy = iContextHubWrapper;
        this.mClientManager = contextHubClientManager;
        this.mAttachedContextHubInfo = contextHubInfo;
        this.mHostEndPointId = s;
        this.mContextHubClientCallback = iContextHubClientCallback;
        if (pendingIntent == null) {
            this.mPendingIntentRequest = new com.android.server.location.contexthub.ContextHubClientBroker.PendingIntentRequest();
        } else {
            this.mPendingIntentRequest = new com.android.server.location.contexthub.ContextHubClientBroker.PendingIntentRequest(pendingIntent, j);
        }
        if (str2 == null) {
            java.lang.String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(android.os.Binder.getCallingUid());
            if (packagesForUid != null && packagesForUid.length > 0) {
                str2 = packagesForUid[0];
            }
            android.util.Log.e(TAG, "createClient: Provided package name null. Using first package name " + str2);
        }
        this.mPackage = str2;
        this.mAttributionTag = str;
        this.mTransactionManager = contextHubTransactionManager;
        this.mPid = android.os.Binder.getCallingPid();
        this.mUid = android.os.Binder.getCallingUid();
        this.mAppOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService(android.os.PowerManager.class)).newWakeLock(1, TAG);
        this.mWakeLock.setWorkSource(new android.os.WorkSource(this.mUid, this.mPackage));
        this.mWakeLock.setReferenceCounted(true);
        startMonitoringOpChanges();
        sendHostEndpointConnectedEvent();
    }

    ContextHubClientBroker(android.content.Context context, com.android.server.location.contexthub.IContextHubWrapper iContextHubWrapper, com.android.server.location.contexthub.ContextHubClientManager contextHubClientManager, android.hardware.location.ContextHubInfo contextHubInfo, short s, android.hardware.location.IContextHubClientCallback iContextHubClientCallback, java.lang.String str, com.android.server.location.contexthub.ContextHubTransactionManager contextHubTransactionManager, java.lang.String str2) {
        this(context, iContextHubWrapper, contextHubClientManager, contextHubInfo, s, iContextHubClientCallback, str, contextHubTransactionManager, null, 0L, str2);
    }

    ContextHubClientBroker(android.content.Context context, com.android.server.location.contexthub.IContextHubWrapper iContextHubWrapper, com.android.server.location.contexthub.ContextHubClientManager contextHubClientManager, android.hardware.location.ContextHubInfo contextHubInfo, short s, android.app.PendingIntent pendingIntent, long j, java.lang.String str, com.android.server.location.contexthub.ContextHubTransactionManager contextHubTransactionManager) {
        this(context, iContextHubWrapper, contextHubClientManager, contextHubInfo, s, null, str, contextHubTransactionManager, pendingIntent, j, pendingIntent.getCreatorPackage());
    }

    private void startMonitoringOpChanges() {
        this.mAppOpsManager.startWatchingMode(-1, this.mPackage, this);
    }

    public int sendMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage) {
        return doSendMessageToNanoApp(nanoAppMessage, null);
    }

    public int sendReliableMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback) {
        return doSendMessageToNanoApp(nanoAppMessage, iContextHubTransactionCallback);
    }

    private int doSendMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage, @android.annotation.Nullable android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback) {
        int i;
        com.android.server.location.contexthub.ContextHubServiceUtil.checkPermissions(this.mContext);
        if (isRegistered()) {
            int intValue = this.mMessageChannelNanoappIdMap.getOrDefault(java.lang.Long.valueOf(nanoAppMessage.getNanoAppId()), -1).intValue();
            if (intValue == 0) {
                if (!android.compat.Compatibility.isChangeEnabled(CHANGE_ID_AUTH_STATE_DENIED)) {
                    return 1;
                }
                throw new java.lang.SecurityException("Client doesn't have valid permissions to send message to " + nanoAppMessage.getNanoAppId());
            }
            if (intValue == -1) {
                checkNanoappPermsAsync();
            }
            if (!android.chre.flags.Flags.reliableMessageImplementation() || iContextHubTransactionCallback == null) {
                try {
                    i = this.mContextHubProxy.sendMessageToContextHub(this.mHostEndPointId, this.mAttachedContextHubInfo.getId(), nanoAppMessage);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "RemoteException in sendMessageToNanoApp (target hub ID = " + this.mAttachedContextHubInfo.getId() + ")", e);
                    i = 1;
                }
            } else {
                try {
                    this.mTransactionManager.addTransaction(this.mTransactionManager.createMessageTransaction(this.mHostEndPointId, this.mAttachedContextHubInfo.getId(), nanoAppMessage, iContextHubTransactionCallback, getPackageName()));
                    i = 0;
                } catch (java.lang.IllegalStateException e2) {
                    android.util.Log.e(TAG, "Unable to add a transaction in sendMessageToNanoApp (target hub ID = " + this.mAttachedContextHubInfo.getId() + ")", e2);
                    i = 7;
                }
            }
            com.android.server.location.contexthub.ContextHubEventLogger.getInstance().logMessageToNanoapp(this.mAttachedContextHubInfo.getId(), nanoAppMessage, i == 0);
            return i;
        }
        android.util.Log.e(TAG, java.lang.String.format("Failed to send message (connection closed): hostEndpointId= %1$d payload %2$s", java.lang.Short.valueOf(this.mHostEndPointId), java.util.Base64.getEncoder().encodeToString(nanoAppMessage.getMessageBody())));
        return 1;
    }

    public void close() {
        synchronized (this) {
            this.mPendingIntentRequest.clear();
        }
        onClientExit();
    }

    public int getId() {
        return this.mHostEndPointId;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        onClientExit();
    }

    @Override // android.app.AppOpsManager.OnOpChangedListener
    public void onOpChanged(java.lang.String str, java.lang.String str2) {
        if (str2.equals(this.mPackage) && !this.mMessageChannelNanoappIdMap.isEmpty()) {
            checkNanoappPermsAsync();
        }
    }

    java.lang.String getPackageName() {
        return this.mPackage;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isWakelockUsable() {
        boolean z;
        synchronized (this.mWakeLock) {
            z = this.mIsWakelockUsable.get();
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.PowerManager.WakeLock getWakeLock() {
        android.os.PowerManager.WakeLock wakeLock;
        synchronized (this.mWakeLock) {
            wakeLock = this.mWakeLock;
        }
        return wakeLock;
    }

    void setAttributionTag(java.lang.String str) {
        this.mAttributionTag = str;
    }

    java.lang.String getAttributionTag() {
        return this.mAttributionTag;
    }

    int getAttachedContextHubId() {
        return this.mAttachedContextHubInfo.getId();
    }

    short getHostEndPointId() {
        return this.mHostEndPointId;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public byte sendMessageToClient(final android.hardware.location.NanoAppMessage nanoAppMessage, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2) {
        final long nanoAppId = nanoAppMessage.getNanoAppId();
        int updateNanoAppAuthState = updateNanoAppAuthState(nanoAppId, list, false);
        if (updateNanoAppAuthState == 1 && !list2.isEmpty()) {
            android.util.Log.e(TAG, "Dropping message from " + java.lang.Long.toHexString(nanoAppId) + ". " + this.mPackage + " in grace period and napp msg has permissions");
            return (byte) 3;
        }
        if (updateNanoAppAuthState != 0) {
            if (notePermissions(list2, RECEIVE_MSG_NOTE + nanoAppId)) {
                byte invokeCallback = invokeCallback(new com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda1
                    @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
                    public final void accept(android.hardware.location.IContextHubClientCallback iContextHubClientCallback) {
                        iContextHubClientCallback.onMessageFromNanoApp(nanoAppMessage);
                    }
                });
                if (invokeCallback != 0) {
                    return invokeCallback;
                }
                java.util.function.Supplier<android.content.Intent> supplier = new java.util.function.Supplier() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        android.content.Intent lambda$sendMessageToClient$1;
                        lambda$sendMessageToClient$1 = com.android.server.location.contexthub.ContextHubClientBroker.this.lambda$sendMessageToClient$1(nanoAppId, nanoAppMessage);
                        return lambda$sendMessageToClient$1;
                    }
                };
                java.util.function.Consumer<java.lang.Byte> consumer = new java.util.function.Consumer() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.location.contexthub.ContextHubClientBroker.this.lambda$sendMessageToClient$2(nanoAppMessage, (java.lang.Byte) obj);
                    }
                };
                if (!android.chre.flags.Flags.reliableMessageImplementation() || !nanoAppMessage.isReliable()) {
                    consumer = null;
                }
                return sendPendingIntent(supplier, nanoAppId, consumer);
            }
        }
        android.util.Log.e(TAG, "Dropping message from " + java.lang.Long.toHexString(nanoAppId) + ". " + this.mPackage + " doesn't have permission");
        return (byte) 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.Intent lambda$sendMessageToClient$1(long j, android.hardware.location.NanoAppMessage nanoAppMessage) {
        return createIntent(5, j).putExtra("android.hardware.location.extra.MESSAGE", (android.os.Parcelable) nanoAppMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendMessageToClient$2(android.hardware.location.NanoAppMessage nanoAppMessage, java.lang.Byte b) {
        sendMessageDeliveryStatusToContextHub(nanoAppMessage.getMessageSequenceNumber(), b.byteValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onNanoAppLoaded(final long j) {
        checkNanoappPermsAsync();
        invokeCallback(new com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda4
            @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
            public final void accept(android.hardware.location.IContextHubClientCallback iContextHubClientCallback) {
                iContextHubClientCallback.onNanoAppLoaded(j);
            }
        });
        sendPendingIntent(new java.util.function.Supplier() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.Intent lambda$onNanoAppLoaded$4;
                lambda$onNanoAppLoaded$4 = com.android.server.location.contexthub.ContextHubClientBroker.this.lambda$onNanoAppLoaded$4(j);
                return lambda$onNanoAppLoaded$4;
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.Intent lambda$onNanoAppLoaded$4(long j) {
        return createIntent(0, j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onNanoAppUnloaded(final long j) {
        invokeCallback(new com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda14
            @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
            public final void accept(android.hardware.location.IContextHubClientCallback iContextHubClientCallback) {
                iContextHubClientCallback.onNanoAppUnloaded(j);
            }
        });
        sendPendingIntent(new java.util.function.Supplier() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda15
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.Intent lambda$onNanoAppUnloaded$6;
                lambda$onNanoAppUnloaded$6 = com.android.server.location.contexthub.ContextHubClientBroker.this.lambda$onNanoAppUnloaded$6(j);
                return lambda$onNanoAppUnloaded$6;
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.Intent lambda$onNanoAppUnloaded$6(long j) {
        return createIntent(1, j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onHubReset() {
        invokeCallback(new com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda7
            @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
            public final void accept(android.hardware.location.IContextHubClientCallback iContextHubClientCallback) {
                iContextHubClientCallback.onHubReset();
            }
        });
        sendPendingIntent(new java.util.function.Supplier() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.Intent lambda$onHubReset$7;
                lambda$onHubReset$7 = com.android.server.location.contexthub.ContextHubClientBroker.this.lambda$onHubReset$7();
                return lambda$onHubReset$7;
            }
        });
        sendHostEndpointConnectedEvent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.Intent lambda$onHubReset$7() {
        return createIntent(6);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onNanoAppAborted(final long j, final int i) {
        invokeCallback(new com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda11
            @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
            public final void accept(android.hardware.location.IContextHubClientCallback iContextHubClientCallback) {
                iContextHubClientCallback.onNanoAppAborted(j, i);
            }
        });
        sendPendingIntent(new java.util.function.Supplier() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda12
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.Intent lambda$onNanoAppAborted$9;
                lambda$onNanoAppAborted$9 = com.android.server.location.contexthub.ContextHubClientBroker.this.lambda$onNanoAppAborted$9(j, i);
                return lambda$onNanoAppAborted$9;
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.Intent lambda$onNanoAppAborted$9(long j, int i) {
        return createIntent(4, j).putExtra("android.hardware.location.extra.NANOAPP_ABORT_CODE", i);
    }

    boolean hasPendingIntent(android.app.PendingIntent pendingIntent, long j) {
        android.app.PendingIntent pendingIntent2;
        long nanoAppId;
        synchronized (this) {
            pendingIntent2 = this.mPendingIntentRequest.getPendingIntent();
            nanoAppId = this.mPendingIntentRequest.getNanoAppId();
        }
        return pendingIntent2 != null && pendingIntent2.equals(pendingIntent) && nanoAppId == j;
    }

    void attachDeathRecipient() throws android.os.RemoteException {
        if (this.mContextHubClientCallback != null) {
            this.mContextHubClientCallback.asBinder().linkToDeath(this, 0);
        }
    }

    boolean hasPermissions(java.util.List<java.lang.String> list) {
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            if (this.mContext.checkPermission(it.next(), this.mPid, this.mUid) != 0) {
                return false;
            }
        }
        return true;
    }

    boolean notePermissions(java.util.List<java.lang.String> list, java.lang.String str) {
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            int permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(it.next());
            if (permissionToOpCode != -1) {
                try {
                    if (this.mAppOpsManager.noteOp(permissionToOpCode, this.mUid, this.mPackage, this.mAttributionTag, str) != 0) {
                        return false;
                    }
                } catch (java.lang.SecurityException e) {
                    android.util.Log.e(TAG, "SecurityException: noteOp for pkg " + this.mPackage + " opcode " + permissionToOpCode + ": " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    boolean isPendingIntentCancelled() {
        return this.mIsPendingIntentCancelled.get();
    }

    void handleAuthStateTimerExpiry(long j) {
        com.android.server.location.contexthub.AuthStateDenialTimer remove;
        synchronized (this.mMessageChannelNanoappIdMap) {
            remove = this.mNappToAuthTimerMap.remove(java.lang.Long.valueOf(j));
        }
        if (remove != null) {
            updateNanoAppAuthState(j, java.util.Collections.emptyList(), true);
        }
    }

    private void checkNanoappPermsAsync() {
        if (!this.mIsPermQueryIssued.getAndSet(true)) {
            this.mTransactionManager.addTransaction(this.mTransactionManager.createQueryTransaction(this.mAttachedContextHubInfo.getId(), this.mQueryPermsCallback, this.mPackage));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int updateNanoAppAuthState(long j, java.util.List<java.lang.String> list, boolean z) {
        return updateNanoAppAuthState(j, list, z, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x008e A[Catch: all -> 0x002f, TryCatch #0 {all -> 0x002f, blocks: (B:4:0x0003, B:6:0x0020, B:9:0x0037, B:15:0x0063, B:17:0x0071, B:20:0x008e, B:21:0x009b, B:28:0x0077, B:37:0x0057), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int updateNanoAppAuthState(long j, java.util.List<java.lang.String> list, boolean z, boolean z2) {
        int intValue;
        int i;
        synchronized (this.mMessageChannelNanoappIdMap) {
            try {
                boolean hasPermissions = hasPermissions(list);
                intValue = this.mMessageChannelNanoappIdMap.getOrDefault(java.lang.Long.valueOf(j), -1).intValue();
                if (intValue == -1) {
                    this.mMessageChannelNanoappIdMap.put(java.lang.Long.valueOf(j), 2);
                    intValue = 2;
                }
                i = 0;
                if (z2 || this.mForceDeniedNapps.contains(java.lang.Long.valueOf(j))) {
                    this.mForceDeniedNapps.add(java.lang.Long.valueOf(j));
                } else if (z) {
                    if (intValue == 1) {
                    }
                    i = intValue;
                } else if (intValue == 2 && !hasPermissions) {
                    i = 1;
                } else {
                    if (intValue != 2 && hasPermissions) {
                        i = 2;
                    }
                    i = intValue;
                }
                if (i == 1) {
                    if (intValue == 2) {
                        com.android.server.location.contexthub.AuthStateDenialTimer authStateDenialTimer = new com.android.server.location.contexthub.AuthStateDenialTimer(this, j, android.os.Looper.getMainLooper());
                        this.mNappToAuthTimerMap.put(java.lang.Long.valueOf(j), authStateDenialTimer);
                        authStateDenialTimer.start();
                        if (intValue != i) {
                            this.mMessageChannelNanoappIdMap.put(java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
                        }
                    }
                } else {
                    com.android.server.location.contexthub.AuthStateDenialTimer remove = this.mNappToAuthTimerMap.remove(java.lang.Long.valueOf(j));
                    if (remove != null) {
                        remove.cancel();
                    }
                }
                if (intValue != i) {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (intValue != i) {
            sendAuthStateCallback(j, i);
        }
        return i;
    }

    private void sendAuthStateCallback(final long j, final int i) {
        invokeCallback(new com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda9
            @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
            public final void accept(android.hardware.location.IContextHubClientCallback iContextHubClientCallback) {
                iContextHubClientCallback.onClientAuthorizationChanged(j, i);
            }
        });
        sendPendingIntent(new java.util.function.Supplier() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda10
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.Intent lambda$sendAuthStateCallback$11;
                lambda$sendAuthStateCallback$11 = com.android.server.location.contexthub.ContextHubClientBroker.this.lambda$sendAuthStateCallback$11(j, i);
                return lambda$sendAuthStateCallback$11;
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.Intent lambda$sendAuthStateCallback$11(long j, int i) {
        return createIntent(7, j).putExtra("android.hardware.location.extra.CLIENT_AUTHORIZATION_STATE", i);
    }

    private synchronized byte invokeCallback(com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer callbackConsumer) {
        if (this.mContextHubClientCallback != null) {
            try {
                acquireWakeLock();
                callbackConsumer.accept(this.mContextHubClientCallback);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException while invoking client callback (host endpoint ID = " + ((int) this.mHostEndPointId) + ")", e);
                return (byte) 2;
            }
        }
        return (byte) 0;
    }

    private android.content.Intent createIntent(int i) {
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.hardware.location.extra.EVENT_TYPE", i);
        intent.putExtra("android.hardware.location.extra.CONTEXT_HUB_INFO", (android.os.Parcelable) this.mAttachedContextHubInfo);
        return intent;
    }

    private android.content.Intent createIntent(int i, long j) {
        android.content.Intent createIntent = createIntent(i);
        createIntent.putExtra("android.hardware.location.extra.NANOAPP_ID", j);
        return createIntent;
    }

    private synchronized byte sendPendingIntent(java.util.function.Supplier<android.content.Intent> supplier) {
        if (!this.mPendingIntentRequest.hasPendingIntent()) {
            return (byte) 0;
        }
        return doSendPendingIntent(this.mPendingIntentRequest.getPendingIntent(), supplier.get(), this);
    }

    private synchronized byte sendPendingIntent(java.util.function.Supplier<android.content.Intent> supplier, long j) {
        return sendPendingIntent(supplier, j, null);
    }

    private synchronized byte sendPendingIntent(java.util.function.Supplier<android.content.Intent> supplier, long j, final java.util.function.Consumer<java.lang.Byte> consumer) {
        if (!this.mPendingIntentRequest.hasPendingIntent() || this.mPendingIntentRequest.getNanoAppId() != j) {
            return (byte) 0;
        }
        return doSendPendingIntent(this.mPendingIntentRequest.getPendingIntent(), supplier.get(), new android.app.PendingIntent.OnFinished() { // from class: com.android.server.location.contexthub.ContextHubClientBroker.2
            @Override // android.app.PendingIntent.OnFinished
            public void onSendFinished(android.app.PendingIntent pendingIntent, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle) {
                byte b;
                if (consumer != null) {
                    java.util.function.Consumer consumer2 = consumer;
                    if (i == 0) {
                        b = 0;
                    } else {
                        b = 1;
                    }
                    consumer2.accept(java.lang.Byte.valueOf(b));
                }
                this.onSendFinished(pendingIntent, intent, i, str, bundle);
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    byte doSendPendingIntent(android.app.PendingIntent pendingIntent, android.content.Intent intent, android.app.PendingIntent.OnFinished onFinished) {
        try {
            acquireWakeLock();
            pendingIntent.send(this.mContext, 0, intent, onFinished, null, "android.permission.ACCESS_CONTEXT_HUB", null);
            return (byte) 0;
        } catch (android.app.PendingIntent.CanceledException e) {
            this.mIsPendingIntentCancelled.set(true);
            android.util.Log.w(TAG, "PendingIntent has been canceled, unregistering from client (host endpoint ID " + ((int) this.mHostEndPointId) + ")");
            close();
            return (byte) 2;
        }
    }

    private synchronized boolean isRegistered() {
        return this.mRegistered;
    }

    private synchronized void onClientExit() {
        try {
            if (this.mContextHubClientCallback != null) {
                this.mContextHubClientCallback.asBinder().unlinkToDeath(this, 0);
                this.mContextHubClientCallback = null;
            }
            if (!this.mPendingIntentRequest.hasPendingIntent() && this.mRegistered) {
                this.mClientManager.unregisterClient(this.mHostEndPointId);
                this.mRegistered = false;
                this.mAppOpsManager.stopWatchingMode(this);
                this.mContextHubProxy.onHostEndpointDisconnected(this.mHostEndPointId);
                releaseWakeLockOnExit();
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private java.lang.String authStateToString(int i) {
        switch (i) {
            case 0:
                return "DENIED";
            case 1:
                return "DENIED_GRACE_PERIOD";
            case 2:
                return "GRANTED";
            default:
                return "UNKNOWN";
        }
    }

    private void sendHostEndpointConnectedEvent() {
        int i;
        android.hardware.contexthub.HostEndpointInfo hostEndpointInfo = new android.hardware.contexthub.HostEndpointInfo();
        hostEndpointInfo.hostEndpointId = (char) this.mHostEndPointId;
        hostEndpointInfo.packageName = this.mPackage;
        hostEndpointInfo.attributionTag = this.mAttributionTag;
        if (this.mUid == 1000) {
            i = 1;
        } else {
            i = 2;
        }
        hostEndpointInfo.type = i;
        this.mContextHubProxy.onHostEndpointConnected(hostEndpointInfo);
    }

    void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1120986464257L, (int) getHostEndPointId());
        protoOutputStream.write(1120986464258L, getAttachedContextHubId());
        protoOutputStream.write(1138166333443L, this.mPackage);
        if (this.mPendingIntentRequest.isValid()) {
            protoOutputStream.write(1133871366149L, true);
            protoOutputStream.write(1112396529668L, this.mPendingIntentRequest.getNanoAppId());
        }
        protoOutputStream.write(1133871366150L, this.mPendingIntentRequest.hasPendingIntent());
        protoOutputStream.write(1133871366151L, isPendingIntentCancelled());
        protoOutputStream.write(1133871366152L, this.mRegistered);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("[ContextHubClient ");
        sb.append("endpointID: ");
        sb.append((int) getHostEndPointId());
        sb.append(", ");
        sb.append("contextHub: ");
        sb.append(getAttachedContextHubId());
        sb.append(", ");
        if (this.mAttributionTag != null) {
            sb.append("attributionTag: ");
            sb.append(getAttributionTag());
            sb.append(", ");
        }
        if (this.mPendingIntentRequest.isValid()) {
            sb.append("intentCreatorPackage: ");
            sb.append(this.mPackage);
            sb.append(", ");
            sb.append("nanoAppId: 0x");
            sb.append(java.lang.Long.toHexString(this.mPendingIntentRequest.getNanoAppId()));
        } else {
            sb.append("package: ");
            sb.append(this.mPackage);
        }
        if (this.mMessageChannelNanoappIdMap.size() > 0) {
            sb.append(" messageChannelNanoappSet: (");
            java.util.Iterator<java.util.Map.Entry<java.lang.Long, java.lang.Integer>> it = this.mMessageChannelNanoappIdMap.entrySet().iterator();
            while (it.hasNext()) {
                java.util.Map.Entry<java.lang.Long, java.lang.Integer> next = it.next();
                sb.append("0x");
                sb.append(java.lang.Long.toHexString(next.getKey().longValue()));
                sb.append(" auth state: ");
                sb.append(authStateToString(next.getValue().intValue()));
                if (it.hasNext()) {
                    sb.append(",");
                }
            }
            sb.append(")");
        }
        synchronized (this.mWakeLock) {
            sb.append("wakelock: ");
            sb.append(this.mWakeLock);
        }
        sb.append("]");
        return sb.toString();
    }

    public void callbackFinished() {
        releaseWakeLock();
    }

    public void reliableMessageCallbackFinished(int i, byte b) {
        sendMessageDeliveryStatusToContextHub(i, b);
        callbackFinished();
    }

    @Override // android.app.PendingIntent.OnFinished
    public void onSendFinished(android.app.PendingIntent pendingIntent, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle) {
        releaseWakeLock();
    }

    private void acquireWakeLock() {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda13
            public final void runOrThrow() {
                com.android.server.location.contexthub.ContextHubClientBroker.this.lambda$acquireWakeLock$12();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$acquireWakeLock$12() throws java.lang.Exception {
        if (this.mIsWakelockUsable.get()) {
            this.mWakeLock.acquire(WAKELOCK_TIMEOUT_MILLIS);
        }
    }

    private void releaseWakeLock() {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda6
            public final void runOrThrow() {
                com.android.server.location.contexthub.ContextHubClientBroker.this.lambda$releaseWakeLock$13();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseWakeLock$13() throws java.lang.Exception {
        if (this.mWakeLock.isHeld()) {
            try {
                this.mWakeLock.release();
            } catch (java.lang.RuntimeException e) {
                android.util.Log.e(TAG, "Releasing the wakelock fails - ", e);
            }
        }
    }

    private void releaseWakeLockOnExit() {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.location.contexthub.ContextHubClientBroker.this.lambda$releaseWakeLockOnExit$14();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseWakeLockOnExit$14() throws java.lang.Exception {
        this.mIsWakelockUsable.set(false);
        while (this.mWakeLock.isHeld()) {
            try {
                this.mWakeLock.release();
            } catch (java.lang.RuntimeException e) {
                android.util.Log.e(TAG, "Releasing the wakelock for all acquisitions fails - ", e);
                return;
            }
        }
    }

    private void sendMessageDeliveryStatusToContextHub(int i, byte b) {
        if (!android.chre.flags.Flags.reliableMessageImplementation()) {
            return;
        }
        android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus = new android.hardware.contexthub.MessageDeliveryStatus();
        messageDeliveryStatus.messageSequenceNumber = i;
        messageDeliveryStatus.errorCode = b;
        if (this.mContextHubProxy.sendMessageDeliveryStatusToContextHub(this.mAttachedContextHubInfo.getId(), messageDeliveryStatus) != 0) {
            android.util.Log.e(TAG, "Failed to send the reliable message status");
        }
    }
}
