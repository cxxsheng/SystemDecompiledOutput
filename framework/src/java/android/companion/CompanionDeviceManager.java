package android.companion;

/* loaded from: classes.dex */
public final class CompanionDeviceManager {
    private static final int ASSOCIATION_TAG_LENGTH_LIMIT = 1024;
    private static final boolean DEBUG = false;
    public static final java.lang.String EXTRA_ASSOCIATION = "android.companion.extra.ASSOCIATION";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_DEVICE = "android.companion.extra.DEVICE";
    public static final int FLAG_CALL_METADATA = 1;
    private static final java.lang.String LOG_TAG = "CDM_CompanionDeviceManager";
    public static final int MESSAGE_ONEWAY_FROM_WEARABLE = 1131446919;
    public static final int MESSAGE_ONEWAY_PING = 1132491640;
    public static final int MESSAGE_ONEWAY_TO_WEARABLE = 1132755335;
    public static final int MESSAGE_REQUEST_CONTEXT_SYNC = 1667729539;
    public static final int MESSAGE_REQUEST_PERMISSION_RESTORE = 1669491075;
    public static final int MESSAGE_REQUEST_PING = 1669362552;
    public static final int MESSAGE_REQUEST_REMOTE_AUTHENTICATION = 1669494629;
    public static final java.lang.String REASON_CANCELED = "canceled";
    public static final java.lang.String REASON_DISCOVERY_TIMEOUT = "discovery_timeout";
    public static final java.lang.String REASON_INTERNAL_ERROR = "internal_error";
    public static final java.lang.String REASON_USER_REJECTED = "user_rejected";
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_DISCOVERY_TIMEOUT = 2;
    public static final int RESULT_INTERNAL_ERROR = 3;
    public static final int RESULT_OK = -1;
    public static final int RESULT_USER_REJECTED = 1;
    private android.content.Context mContext;
    private final android.companion.ICompanionDeviceManager mService;
    private final java.util.ArrayList<android.companion.CompanionDeviceManager.OnAssociationsChangedListenerProxy> mListeners = new java.util.ArrayList<>();
    private final android.util.SparseArray<android.companion.CompanionDeviceManager.Transport> mTransports = new android.util.SparseArray<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataSyncTypes {
    }

    @android.annotation.SystemApi
    public interface OnAssociationsChangedListener {
        void onAssociationsChanged(java.util.List<android.companion.AssociationInfo> list);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    public static abstract class Callback {
        public abstract void onFailure(java.lang.CharSequence charSequence);

        @java.lang.Deprecated
        public void onDeviceFound(android.content.IntentSender intentSender) {
        }

        public void onAssociationPending(android.content.IntentSender intentSender) {
            onDeviceFound(intentSender);
        }

        public void onAssociationCreated(android.companion.AssociationInfo associationInfo) {
        }
    }

    public CompanionDeviceManager(android.companion.ICompanionDeviceManager iCompanionDeviceManager, android.content.Context context) {
        this.mService = iCompanionDeviceManager;
        this.mContext = context;
    }

    public void associate(android.companion.AssociationRequest associationRequest, android.companion.CompanionDeviceManager.Callback callback, android.os.Handler handler) {
        if (checkFeaturePresent()) {
            java.util.Objects.requireNonNull(associationRequest, "Request cannot be null");
            java.util.Objects.requireNonNull(callback, "Callback cannot be null");
            try {
                this.mService.associate(associationRequest, new android.companion.CompanionDeviceManager.AssociationRequestCallbackProxy(android.os.Handler.mainIfNull(handler), callback), this.mContext.getOpPackageName(), this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void associate(android.companion.AssociationRequest associationRequest, java.util.concurrent.Executor executor, android.companion.CompanionDeviceManager.Callback callback) {
        if (checkFeaturePresent()) {
            java.util.Objects.requireNonNull(associationRequest, "Request cannot be null");
            java.util.Objects.requireNonNull(executor, "Executor cannot be null");
            java.util.Objects.requireNonNull(callback, "Callback cannot be null");
            try {
                this.mService.associate(associationRequest, new android.companion.CompanionDeviceManager.AssociationRequestCallbackProxy(executor, callback), this.mContext.getOpPackageName(), this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.content.IntentSender buildAssociationCancellationIntent() {
        if (!checkFeaturePresent()) {
            return null;
        }
        try {
            return this.mService.buildAssociationCancellationIntent(this.mContext.getOpPackageName(), this.mContext.getUserId()).getIntentSender();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableSystemDataSyncForTypes(int i, int i2) {
        if (!checkFeaturePresent()) {
            return;
        }
        try {
            this.mService.enableSystemDataSync(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableSystemDataSyncForTypes(int i, int i2) {
        if (!checkFeaturePresent()) {
            return;
        }
        try {
            this.mService.disableSystemDataSync(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enablePermissionsSync(int i) {
        try {
            this.mService.enablePermissionsSync(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disablePermissionsSync(int i) {
        try {
            this.mService.disablePermissionsSync(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.companion.datatransfer.PermissionSyncRequest getPermissionSyncRequest(int i) {
        try {
            return this.mService.getPermissionSyncRequest(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public java.util.List<java.lang.String> getAssociations() {
        return com.android.internal.util.CollectionUtils.mapNotNull(getMyAssociations(), new java.util.function.Function() { // from class: android.companion.CompanionDeviceManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.companion.CompanionDeviceManager.lambda$getAssociations$0((android.companion.AssociationInfo) obj);
            }
        });
    }

    static /* synthetic */ java.lang.String lambda$getAssociations$0(android.companion.AssociationInfo associationInfo) {
        if (associationInfo.isSelfManaged()) {
            return null;
        }
        return associationInfo.getDeviceMacAddressAsString();
    }

    public java.util.List<android.companion.AssociationInfo> getMyAssociations() {
        if (!checkFeaturePresent()) {
            return java.util.Collections.emptyList();
        }
        try {
            return this.mService.getAssociations(this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void disassociate(java.lang.String str) {
        if (checkFeaturePresent()) {
            try {
                this.mService.legacyDisassociate(str, this.mContext.getOpPackageName(), this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void disassociate(int i) {
        if (checkFeaturePresent()) {
            try {
                this.mService.disassociate(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void requestNotificationAccess(android.content.ComponentName componentName) {
        if (!checkFeaturePresent()) {
            return;
        }
        try {
            android.content.IntentSender intentSender = this.mService.requestNotificationAccess(componentName, this.mContext.getUserId()).getIntentSender();
            if (intentSender == null) {
                return;
            }
            this.mContext.startIntentSender(intentSender, null, 0, 0, 0, android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
        } catch (android.content.IntentSender.SendIntentException e) {
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean hasNotificationAccess(android.content.ComponentName componentName) {
        if (!checkFeaturePresent()) {
            return false;
        }
        try {
            return this.mService.hasNotificationAccess(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isDeviceAssociatedForWifiConnection(java.lang.String str, android.net.MacAddress macAddress, android.os.UserHandle userHandle) {
        if (!checkFeaturePresent()) {
            return false;
        }
        java.util.Objects.requireNonNull(str, "package name cannot be null");
        java.util.Objects.requireNonNull(macAddress, "mac address cannot be null");
        java.util.Objects.requireNonNull(userHandle, "user cannot be null");
        try {
            return this.mService.isDeviceAssociatedForWifiConnection(str, macAddress.toString(), userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.companion.AssociationInfo> getAllAssociations() {
        return getAllAssociations(this.mContext.getUserId());
    }

    public java.util.List<android.companion.AssociationInfo> getAllAssociations(int i) {
        if (!checkFeaturePresent()) {
            return java.util.Collections.emptyList();
        }
        try {
            return this.mService.getAllAssociationsForUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void addOnAssociationsChangedListener(java.util.concurrent.Executor executor, android.companion.CompanionDeviceManager.OnAssociationsChangedListener onAssociationsChangedListener) {
        addOnAssociationsChangedListener(executor, onAssociationsChangedListener, this.mContext.getUserId());
    }

    public void addOnAssociationsChangedListener(java.util.concurrent.Executor executor, android.companion.CompanionDeviceManager.OnAssociationsChangedListener onAssociationsChangedListener, int i) {
        if (checkFeaturePresent()) {
            synchronized (this.mListeners) {
                android.companion.CompanionDeviceManager.OnAssociationsChangedListenerProxy onAssociationsChangedListenerProxy = new android.companion.CompanionDeviceManager.OnAssociationsChangedListenerProxy(executor, onAssociationsChangedListener);
                try {
                    this.mService.addOnAssociationsChangedListener(onAssociationsChangedListenerProxy, i);
                    this.mListeners.add(onAssociationsChangedListenerProxy);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @android.annotation.SystemApi
    public void removeOnAssociationsChangedListener(android.companion.CompanionDeviceManager.OnAssociationsChangedListener onAssociationsChangedListener) {
        if (checkFeaturePresent()) {
            synchronized (this.mListeners) {
                java.util.Iterator<android.companion.CompanionDeviceManager.OnAssociationsChangedListenerProxy> it = this.mListeners.iterator();
                while (it.hasNext()) {
                    android.companion.CompanionDeviceManager.OnAssociationsChangedListenerProxy next = it.next();
                    if (next.mListener == onAssociationsChangedListener) {
                        try {
                            this.mService.removeOnAssociationsChangedListener(next, this.mContext.getUserId());
                            it.remove();
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }
            }
        }
    }

    public void addOnTransportsChangedListener(java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.List<android.companion.AssociationInfo>> consumer) {
        try {
            this.mService.addOnTransportsChangedListener(new android.companion.CompanionDeviceManager.OnTransportsChangedListenerProxy(executor, consumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeOnTransportsChangedListener(java.util.function.Consumer<java.util.List<android.companion.AssociationInfo>> consumer) {
        try {
            this.mService.removeOnTransportsChangedListener(new android.companion.CompanionDeviceManager.OnTransportsChangedListenerProxy(null, consumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendMessage(int i, byte[] bArr, int[] iArr) {
        try {
            this.mService.sendMessage(i, bArr, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOnMessageReceivedListener(java.util.concurrent.Executor executor, int i, java.util.function.BiConsumer<java.lang.Integer, byte[]> biConsumer) {
        try {
            this.mService.addOnMessageReceivedListener(i, new android.companion.CompanionDeviceManager.OnMessageReceivedListenerProxy(executor, biConsumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeOnMessageReceivedListener(int i, java.util.function.BiConsumer<java.lang.Integer, byte[]> biConsumer) {
        try {
            this.mService.removeOnMessageReceivedListener(i, new android.companion.CompanionDeviceManager.OnMessageReceivedListenerProxy(null, biConsumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean canPairWithoutPrompt(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        if (!checkFeaturePresent()) {
            return false;
        }
        java.util.Objects.requireNonNull(str, "package name cannot be null");
        java.util.Objects.requireNonNull(str2, "device mac address cannot be null");
        java.util.Objects.requireNonNull(userHandle, "user handle cannot be null");
        try {
            return this.mService.canPairWithoutPrompt(str, str2, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startObservingDevicePresence(java.lang.String str) throws android.companion.DeviceNotAssociatedException {
        if (!checkFeaturePresent()) {
            return;
        }
        java.util.Objects.requireNonNull(str, "address cannot be null");
        try {
            this.mService.registerDevicePresenceListenerService(str, this.mContext.getOpPackageName(), this.mContext.getUserId());
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
            if (activityManagerInternal != null) {
                activityManagerInternal.logFgsApiBegin(9, callingUid, callingPid);
            }
        } catch (android.os.RemoteException e) {
            android.util.ExceptionUtils.propagateIfInstanceOf(e.getCause(), android.companion.DeviceNotAssociatedException.class);
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopObservingDevicePresence(java.lang.String str) throws android.companion.DeviceNotAssociatedException {
        if (!checkFeaturePresent()) {
            return;
        }
        java.util.Objects.requireNonNull(str, "address cannot be null");
        try {
            this.mService.unregisterDevicePresenceListenerService(str, this.mContext.getPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            android.util.ExceptionUtils.propagateIfInstanceOf(e.getCause(), android.companion.DeviceNotAssociatedException.class);
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        if (activityManagerInternal != null) {
            activityManagerInternal.logFgsApiEnd(9, callingUid, callingPid);
        }
    }

    public void startObservingDevicePresence(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest) {
        java.util.Objects.requireNonNull(observingDevicePresenceRequest, "request cannot be null");
        try {
            this.mService.startObservingDevicePresence(observingDevicePresenceRequest, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopObservingDevicePresence(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest) {
        java.util.Objects.requireNonNull(observingDevicePresenceRequest, "request cannot be null");
        try {
            this.mService.stopObservingDevicePresence(observingDevicePresenceRequest, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void dispatchMessage(int i, int i2, byte[] bArr) throws android.companion.DeviceNotAssociatedException {
        android.util.Log.w(LOG_TAG, "dispatchMessage replaced by attachSystemDataTransport");
    }

    public void attachSystemDataTransport(int i, java.io.InputStream inputStream, java.io.OutputStream outputStream) throws android.companion.DeviceNotAssociatedException {
        synchronized (this.mTransports) {
            if (this.mTransports.contains(i)) {
                detachSystemDataTransport(i);
            }
            try {
                android.companion.CompanionDeviceManager.Transport transport = new android.companion.CompanionDeviceManager.Transport(i, inputStream, outputStream);
                this.mTransports.put(i, transport);
                transport.start();
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException("Failed to attach transport", e);
            }
        }
    }

    public void detachSystemDataTransport(int i) throws android.companion.DeviceNotAssociatedException {
        synchronized (this.mTransports) {
            android.companion.CompanionDeviceManager.Transport transport = this.mTransports.get(i);
            if (transport != null) {
                this.mTransports.delete(i);
                transport.stop();
            }
        }
    }

    @android.annotation.SystemApi
    public void associate(java.lang.String str, android.net.MacAddress macAddress, byte[] bArr) {
        if (!checkFeaturePresent()) {
            return;
        }
        java.util.Objects.requireNonNull(str, "package name cannot be null");
        java.util.Objects.requireNonNull(macAddress, "mac address cannot be null");
        try {
            this.mService.createAssociation(str, macAddress.toString(), android.os.Process.myUserHandle().getIdentifier(), bArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void notifyDeviceAppeared(int i) {
        try {
            this.mService.notifyDeviceAppeared(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void notifyDeviceDisappeared(int i) {
        try {
            this.mService.notifyDeviceDisappeared(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.IntentSender buildPermissionTransferUserConsentIntent(int i) throws android.companion.DeviceNotAssociatedException {
        try {
            android.app.PendingIntent buildPermissionTransferUserConsentIntent = this.mService.buildPermissionTransferUserConsentIntent(this.mContext.getOpPackageName(), this.mContext.getUserId(), i);
            if (buildPermissionTransferUserConsentIntent == null) {
                return null;
            }
            return buildPermissionTransferUserConsentIntent.getIntentSender();
        } catch (android.os.RemoteException e) {
            android.util.ExceptionUtils.propagateIfInstanceOf(e.getCause(), android.companion.DeviceNotAssociatedException.class);
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPermissionTransferUserConsented(int i) {
        try {
            return this.mService.isPermissionTransferUserConsented(this.mContext.getOpPackageName(), this.mContext.getUserId(), i);
        } catch (android.os.RemoteException e) {
            android.util.ExceptionUtils.propagateIfInstanceOf(e.getCause(), android.companion.DeviceNotAssociatedException.class);
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void startSystemDataTransfer(int i) throws android.companion.DeviceNotAssociatedException {
        try {
            this.mService.startSystemDataTransfer(this.mContext.getOpPackageName(), this.mContext.getUserId(), i, null);
        } catch (android.os.RemoteException e) {
            android.util.ExceptionUtils.propagateIfInstanceOf(e.getCause(), android.companion.DeviceNotAssociatedException.class);
            throw e.rethrowFromSystemServer();
        }
    }

    public void startSystemDataTransfer(int i, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.companion.CompanionException> outcomeReceiver) throws android.companion.DeviceNotAssociatedException {
        try {
            this.mService.startSystemDataTransfer(this.mContext.getOpPackageName(), this.mContext.getUserId(), i, new android.companion.CompanionDeviceManager.SystemDataTransferCallbackProxy(executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            android.util.ExceptionUtils.propagateIfInstanceOf(e.getCause(), android.companion.DeviceNotAssociatedException.class);
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCompanionApplicationBound() {
        try {
            return this.mService.isCompanionApplicationBound(this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableSecureTransport(boolean z) {
        try {
            this.mService.enableSecureTransport(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAssociationTag(int i, java.lang.String str) {
        java.util.Objects.requireNonNull(str, "tag cannot be null");
        if (str.length() > 1024) {
            throw new java.lang.IllegalArgumentException("Length of the tag must be at most1024 characters");
        }
        try {
            this.mService.setAssociationTag(i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearAssociationTag(int i) {
        try {
            this.mService.clearAssociationTag(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean checkFeaturePresent() {
        return this.mService != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AssociationRequestCallbackProxy extends android.companion.IAssociationRequestCallback.Stub {
        private final android.companion.CompanionDeviceManager.Callback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final android.os.Handler mHandler;

        private AssociationRequestCallbackProxy(java.util.concurrent.Executor executor, android.companion.CompanionDeviceManager.Callback callback) {
            this.mExecutor = executor;
            this.mHandler = null;
            this.mCallback = callback;
        }

        private AssociationRequestCallbackProxy(android.os.Handler handler, android.companion.CompanionDeviceManager.Callback callback) {
            this.mHandler = handler;
            this.mExecutor = null;
            this.mCallback = callback;
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onAssociationPending(android.app.PendingIntent pendingIntent) {
            final android.companion.CompanionDeviceManager.Callback callback = this.mCallback;
            java.util.Objects.requireNonNull(callback);
            execute(new java.util.function.Consumer() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.companion.CompanionDeviceManager.Callback.this.onAssociationPending((android.content.IntentSender) obj);
                }
            }, pendingIntent.getIntentSender());
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onAssociationCreated(android.companion.AssociationInfo associationInfo) {
            final android.companion.CompanionDeviceManager.Callback callback = this.mCallback;
            java.util.Objects.requireNonNull(callback);
            execute(new java.util.function.Consumer() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.companion.CompanionDeviceManager.Callback.this.onAssociationCreated((android.companion.AssociationInfo) obj);
                }
            }, associationInfo);
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onFailure(java.lang.CharSequence charSequence) throws android.os.RemoteException {
            final android.companion.CompanionDeviceManager.Callback callback = this.mCallback;
            java.util.Objects.requireNonNull(callback);
            execute(new java.util.function.Consumer() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.companion.CompanionDeviceManager.Callback.this.onFailure((java.lang.CharSequence) obj);
                }
            }, charSequence);
        }

        private <T> void execute(final java.util.function.Consumer<T> consumer, final T t) {
            if (this.mExecutor != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(t);
                    }
                });
            } else {
                this.mHandler.post(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(t);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnAssociationsChangedListenerProxy extends android.companion.IOnAssociationsChangedListener.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final android.companion.CompanionDeviceManager.OnAssociationsChangedListener mListener;

        private OnAssociationsChangedListenerProxy(java.util.concurrent.Executor executor, android.companion.CompanionDeviceManager.OnAssociationsChangedListener onAssociationsChangedListener) {
            this.mExecutor = executor;
            this.mListener = onAssociationsChangedListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAssociationsChanged$0(java.util.List list) {
            this.mListener.onAssociationsChanged(list);
        }

        @Override // android.companion.IOnAssociationsChangedListener
        public void onAssociationsChanged(final java.util.List<android.companion.AssociationInfo> list) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceManager$OnAssociationsChangedListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.CompanionDeviceManager.OnAssociationsChangedListenerProxy.this.lambda$onAssociationsChanged$0(list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnTransportsChangedListenerProxy extends android.companion.IOnTransportsChangedListener.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final java.util.function.Consumer<java.util.List<android.companion.AssociationInfo>> mListener;

        private OnTransportsChangedListenerProxy(java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.List<android.companion.AssociationInfo>> consumer) {
            this.mExecutor = executor;
            this.mListener = consumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTransportsChanged$0(java.util.List list) {
            this.mListener.accept(list);
        }

        @Override // android.companion.IOnTransportsChangedListener
        public void onTransportsChanged(final java.util.List<android.companion.AssociationInfo> list) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceManager$OnTransportsChangedListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.CompanionDeviceManager.OnTransportsChangedListenerProxy.this.lambda$onTransportsChanged$0(list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnMessageReceivedListenerProxy extends android.companion.IOnMessageReceivedListener.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final java.util.function.BiConsumer<java.lang.Integer, byte[]> mListener;

        private OnMessageReceivedListenerProxy(java.util.concurrent.Executor executor, java.util.function.BiConsumer<java.lang.Integer, byte[]> biConsumer) {
            this.mExecutor = executor;
            this.mListener = biConsumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageReceived$0(int i, byte[] bArr) {
            this.mListener.accept(java.lang.Integer.valueOf(i), bArr);
        }

        @Override // android.companion.IOnMessageReceivedListener
        public void onMessageReceived(final int i, final byte[] bArr) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceManager$OnMessageReceivedListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.CompanionDeviceManager.OnMessageReceivedListenerProxy.this.lambda$onMessageReceived$0(i, bArr);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SystemDataTransferCallbackProxy extends android.companion.ISystemDataTransferCallback.Stub {
        private final android.os.OutcomeReceiver<java.lang.Void, android.companion.CompanionException> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        private SystemDataTransferCallbackProxy(java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.companion.CompanionException> outcomeReceiver) {
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0() {
            this.mCallback.onResult(null);
        }

        @Override // android.companion.ISystemDataTransferCallback
        public void onResult() {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceManager$SystemDataTransferCallbackProxy$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.CompanionDeviceManager.SystemDataTransferCallbackProxy.this.lambda$onResult$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(java.lang.String str) {
            this.mCallback.onError(new android.companion.CompanionException(str));
        }

        @Override // android.companion.ISystemDataTransferCallback
        public void onError(final java.lang.String str) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceManager$SystemDataTransferCallbackProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.CompanionDeviceManager.SystemDataTransferCallbackProxy.this.lambda$onError$1(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Transport {
        private final int mAssociationId;
        private java.io.InputStream mLocalIn;
        private java.io.OutputStream mLocalOut;
        private final java.io.InputStream mRemoteIn;
        private final java.io.OutputStream mRemoteOut;
        private volatile boolean mStopped;

        public Transport(int i, java.io.InputStream inputStream, java.io.OutputStream outputStream) {
            this.mAssociationId = i;
            this.mRemoteIn = inputStream;
            this.mRemoteOut = outputStream;
        }

        public void start() throws java.io.IOException {
            android.os.ParcelFileDescriptor[] createSocketPair = android.os.ParcelFileDescriptor.createSocketPair();
            android.os.ParcelFileDescriptor parcelFileDescriptor = createSocketPair[0];
            android.os.ParcelFileDescriptor parcelFileDescriptor2 = createSocketPair[1];
            this.mLocalIn = new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
            this.mLocalOut = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
            try {
                android.companion.CompanionDeviceManager.this.mService.attachSystemDataTransport(android.companion.CompanionDeviceManager.this.mContext.getPackageName(), android.companion.CompanionDeviceManager.this.mContext.getUserId(), this.mAssociationId, parcelFileDescriptor2);
                new java.lang.Thread(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceManager$Transport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.companion.CompanionDeviceManager.Transport.this.lambda$start$0();
                    }
                }).start();
                new java.lang.Thread(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceManager$Transport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.companion.CompanionDeviceManager.Transport.this.lambda$start$1();
                    }
                }).start();
            } catch (android.os.RemoteException e) {
                throw new java.io.IOException("Failed to configure transport", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$0() {
            try {
                copyWithFlushing(this.mLocalIn, this.mRemoteOut);
            } catch (java.io.IOException e) {
                if (!this.mStopped) {
                    android.util.Log.w(android.companion.CompanionDeviceManager.LOG_TAG, "Trouble during outgoing transport", e);
                    stop();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$1() {
            try {
                copyWithFlushing(this.mRemoteIn, this.mLocalOut);
            } catch (java.io.IOException e) {
                if (!this.mStopped) {
                    android.util.Log.w(android.companion.CompanionDeviceManager.LOG_TAG, "Trouble during incoming transport", e);
                    stop();
                }
            }
        }

        public void stop() {
            this.mStopped = true;
            try {
                android.companion.CompanionDeviceManager.this.mService.detachSystemDataTransport(android.companion.CompanionDeviceManager.this.mContext.getPackageName(), android.companion.CompanionDeviceManager.this.mContext.getUserId(), this.mAssociationId);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.companion.CompanionDeviceManager.LOG_TAG, "Failed to detach transport", e);
            }
            libcore.io.IoUtils.closeQuietly(this.mRemoteIn);
            libcore.io.IoUtils.closeQuietly(this.mRemoteOut);
            libcore.io.IoUtils.closeQuietly(this.mLocalIn);
            libcore.io.IoUtils.closeQuietly(this.mLocalOut);
        }

        private void copyWithFlushing(java.io.InputStream inputStream, java.io.OutputStream outputStream) throws java.io.IOException {
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    outputStream.write(bArr, 0, read);
                    outputStream.flush();
                } else {
                    return;
                }
            }
        }
    }
}
