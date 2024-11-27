package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public class SipDelegateConnectionAidlWrapper implements android.telephony.ims.SipDelegateConnection, android.os.IBinder.DeathRecipient {
    private static final java.lang.String LOG_TAG = "SipDelegateCAW";
    private final java.util.concurrent.Executor mExecutor;
    private final android.telephony.ims.stub.DelegateConnectionMessageCallback mMessageCallback;
    private final android.telephony.ims.stub.DelegateConnectionStateCallback mStateCallback;
    private final android.telephony.ims.aidl.ISipDelegateConnectionStateCallback.Stub mStateBinder = new android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.AnonymousClass1();
    private final android.telephony.ims.aidl.ISipDelegateMessageCallback.Stub mMessageBinder = new android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.AnonymousClass2();
    private final java.util.concurrent.atomic.AtomicReference<android.telephony.ims.aidl.ISipDelegate> mDelegateBinder = new java.util.concurrent.atomic.AtomicReference<>();

    /* renamed from: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1, reason: invalid class name */
    class AnonymousClass1 extends android.telephony.ims.aidl.ISipDelegateConnectionStateCallback.Stub {
        AnonymousClass1() {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onCreated(android.telephony.ims.aidl.ISipDelegate iSipDelegate) {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.associateSipDelegate(iSipDelegate);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.AnonymousClass1.this.lambda$onCreated$0();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreated$0() {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mStateCallback.onCreated(android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this);
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onFeatureTagStatusChanged(final android.telephony.ims.DelegateRegistrationState delegateRegistrationState, final java.util.List<android.telephony.ims.FeatureTagState> list) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.AnonymousClass1.this.lambda$onFeatureTagStatusChanged$1(delegateRegistrationState, list);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFeatureTagStatusChanged$1(android.telephony.ims.DelegateRegistrationState delegateRegistrationState, java.util.List list) {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mStateCallback.onFeatureTagStatusChanged(delegateRegistrationState, new android.util.ArraySet(list));
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onImsConfigurationChanged(final android.telephony.ims.SipDelegateImsConfiguration sipDelegateImsConfiguration) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.AnonymousClass1.this.lambda$onImsConfigurationChanged$2(sipDelegateImsConfiguration);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImsConfigurationChanged$2(android.telephony.ims.SipDelegateImsConfiguration sipDelegateImsConfiguration) {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mStateCallback.onImsConfigurationChanged(sipDelegateImsConfiguration);
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onConfigurationChanged(final android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.AnonymousClass1.this.lambda$onConfigurationChanged$3(sipDelegateConfiguration);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigurationChanged$3(android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration) {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mStateCallback.onConfigurationChanged(sipDelegateConfiguration);
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onDestroyed(final int i) {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.invalidateSipDelegateBinder();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.AnonymousClass1.this.lambda$onDestroyed$4(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDestroyed$4(int i) {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mStateCallback.onDestroyed(i);
        }
    }

    /* renamed from: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$2, reason: invalid class name */
    class AnonymousClass2 extends android.telephony.ims.aidl.ISipDelegateMessageCallback.Stub {
        AnonymousClass2() {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
        public void onMessageReceived(final android.telephony.ims.SipMessage sipMessage) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.AnonymousClass2.this.lambda$onMessageReceived$0(sipMessage);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageReceived$0(android.telephony.ims.SipMessage sipMessage) {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mMessageCallback.onMessageReceived(sipMessage);
        }

        @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
        public void onMessageSent(final java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$2$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.AnonymousClass2.this.lambda$onMessageSent$1(str);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageSent$1(java.lang.String str) {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mMessageCallback.onMessageSent(str);
        }

        @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
        public void onMessageSendFailure(final java.lang.String str, final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.AnonymousClass2.this.lambda$onMessageSendFailure$2(str, i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageSendFailure$2(java.lang.String str, int i) {
            android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.mMessageCallback.onMessageSendFailure(str, i);
        }
    }

    public SipDelegateConnectionAidlWrapper(java.util.concurrent.Executor executor, android.telephony.ims.stub.DelegateConnectionStateCallback delegateConnectionStateCallback, android.telephony.ims.stub.DelegateConnectionMessageCallback delegateConnectionMessageCallback) {
        this.mExecutor = executor;
        this.mStateCallback = delegateConnectionStateCallback;
        this.mMessageCallback = delegateConnectionMessageCallback;
    }

    @Override // android.telephony.ims.SipDelegateConnection
    public void sendMessage(android.telephony.ims.SipMessage sipMessage, long j) {
        try {
            android.telephony.ims.aidl.ISipDelegate sipDelegateBinder = getSipDelegateBinder();
            if (sipDelegateBinder == null) {
                notifyLocalMessageFailedToSend(sipMessage, 2);
            } else {
                sipDelegateBinder.sendMessage(sipMessage, j);
            }
        } catch (android.os.RemoteException e) {
            notifyLocalMessageFailedToSend(sipMessage, 1);
        }
    }

    @Override // android.telephony.ims.SipDelegateConnection
    public void notifyMessageReceived(java.lang.String str) {
        try {
            android.telephony.ims.aidl.ISipDelegate sipDelegateBinder = getSipDelegateBinder();
            if (sipDelegateBinder == null) {
                return;
            }
            sipDelegateBinder.notifyMessageReceived(str);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telephony.ims.SipDelegateConnection
    public void notifyMessageReceiveError(java.lang.String str, int i) {
        try {
            android.telephony.ims.aidl.ISipDelegate sipDelegateBinder = getSipDelegateBinder();
            if (sipDelegateBinder == null) {
                return;
            }
            sipDelegateBinder.notifyMessageReceiveError(str, i);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telephony.ims.SipDelegateConnection
    public void cleanupSession(java.lang.String str) {
        try {
            android.telephony.ims.aidl.ISipDelegate sipDelegateBinder = getSipDelegateBinder();
            if (sipDelegateBinder == null) {
                return;
            }
            sipDelegateBinder.cleanupSession(str);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        invalidateSipDelegateBinder();
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.lambda$binderDied$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderDied$0() {
        this.mStateCallback.onDestroyed(1);
    }

    public android.telephony.ims.aidl.ISipDelegateConnectionStateCallback getStateCallbackBinder() {
        return this.mStateBinder;
    }

    public android.telephony.ims.aidl.ISipDelegateMessageCallback getMessageCallbackBinder() {
        return this.mMessageBinder;
    }

    public android.telephony.ims.aidl.ISipDelegate getSipDelegateBinder() {
        return this.mDelegateBinder.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void associateSipDelegate(android.telephony.ims.aidl.ISipDelegate iSipDelegate) {
        if (iSipDelegate != null) {
            try {
                iSipDelegate.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                iSipDelegate = null;
            }
        }
        this.mDelegateBinder.set(iSipDelegate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateSipDelegateBinder() {
        android.telephony.ims.aidl.ISipDelegate andUpdate = this.mDelegateBinder.getAndUpdate(new java.util.function.UnaryOperator() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.lambda$invalidateSipDelegateBinder$1((android.telephony.ims.aidl.ISipDelegate) obj);
            }
        });
        if (andUpdate != null) {
            try {
                andUpdate.asBinder().unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
                android.util.Log.i(LOG_TAG, "invalidateSipDelegateBinder: " + e);
            }
        }
    }

    static /* synthetic */ android.telephony.ims.aidl.ISipDelegate lambda$invalidateSipDelegateBinder$1(android.telephony.ims.aidl.ISipDelegate iSipDelegate) {
        return null;
    }

    private void notifyLocalMessageFailedToSend(android.telephony.ims.SipMessage sipMessage, final int i) {
        final java.lang.String viaBranchParameter = sipMessage.getViaBranchParameter();
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper.this.lambda$notifyLocalMessageFailedToSend$2(viaBranchParameter, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyLocalMessageFailedToSend$2(java.lang.String str, int i) {
        this.mMessageCallback.onMessageSendFailure(str, i);
    }
}
