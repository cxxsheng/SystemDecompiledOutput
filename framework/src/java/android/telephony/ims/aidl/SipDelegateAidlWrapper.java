package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public class SipDelegateAidlWrapper implements android.telephony.ims.DelegateStateCallback, android.telephony.ims.DelegateMessageCallback {
    private static final java.lang.String LOG_TAG = "SipDelegateAW";
    private volatile android.telephony.ims.stub.SipDelegate mDelegate;
    private final android.telephony.ims.aidl.ISipDelegate.Stub mDelegateBinder = new android.telephony.ims.aidl.SipDelegateAidlWrapper.AnonymousClass1();
    private final java.util.concurrent.Executor mExecutor;
    private final android.telephony.ims.aidl.ISipDelegateMessageCallback mMessageBinder;
    private final android.telephony.ims.aidl.ISipDelegateStateCallback mStateBinder;

    /* renamed from: android.telephony.ims.aidl.SipDelegateAidlWrapper$1, reason: invalid class name */
    class AnonymousClass1 extends android.telephony.ims.aidl.ISipDelegate.Stub {
        AnonymousClass1() {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void sendMessage(final android.telephony.ims.SipMessage sipMessage, final long j) {
            final android.telephony.ims.stub.SipDelegate sipDelegate = android.telephony.ims.aidl.SipDelegateAidlWrapper.this.mDelegate;
            if (sipDelegate == null) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateAidlWrapper$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.stub.SipDelegate.this.sendMessage(sipMessage, j);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void notifyMessageReceived(final java.lang.String str) {
            final android.telephony.ims.stub.SipDelegate sipDelegate = android.telephony.ims.aidl.SipDelegateAidlWrapper.this.mDelegate;
            if (sipDelegate == null) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateAidlWrapper$1$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.stub.SipDelegate.this.notifyMessageReceived(str);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void notifyMessageReceiveError(final java.lang.String str, final int i) {
            final android.telephony.ims.stub.SipDelegate sipDelegate = android.telephony.ims.aidl.SipDelegateAidlWrapper.this.mDelegate;
            if (sipDelegate == null) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateAidlWrapper$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.stub.SipDelegate.this.notifyMessageReceiveError(str, i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void cleanupSession(final java.lang.String str) {
            final android.telephony.ims.stub.SipDelegate sipDelegate = android.telephony.ims.aidl.SipDelegateAidlWrapper.this.mDelegate;
            if (sipDelegate == null) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.aidl.SipDelegateAidlWrapper.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateAidlWrapper$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.stub.SipDelegate.this.cleanupSession(str);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public SipDelegateAidlWrapper(java.util.concurrent.Executor executor, android.telephony.ims.aidl.ISipDelegateStateCallback iSipDelegateStateCallback, android.telephony.ims.aidl.ISipDelegateMessageCallback iSipDelegateMessageCallback) {
        this.mExecutor = executor;
        this.mStateBinder = iSipDelegateStateCallback;
        this.mMessageBinder = iSipDelegateMessageCallback;
    }

    @Override // android.telephony.ims.DelegateMessageCallback
    public void onMessageReceived(android.telephony.ims.SipMessage sipMessage) {
        try {
            this.mMessageBinder.onMessageReceived(sipMessage);
        } catch (android.os.RemoteException e) {
            if (this.mDelegate != null) {
                notifyLocalMessageFailedToBeReceived(sipMessage, 1);
            }
        }
    }

    @Override // android.telephony.ims.DelegateMessageCallback
    public void onMessageSent(java.lang.String str) {
        try {
            this.mMessageBinder.onMessageSent(str);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telephony.ims.DelegateMessageCallback
    public void onMessageSendFailure(java.lang.String str, int i) {
        try {
            this.mMessageBinder.onMessageSendFailure(str, i);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telephony.ims.DelegateStateCallback
    public void onCreated(android.telephony.ims.stub.SipDelegate sipDelegate, java.util.Set<android.telephony.ims.FeatureTagState> set) {
        this.mDelegate = sipDelegate;
        if (set == null) {
            set = java.util.Collections.emptySet();
        }
        try {
            this.mStateBinder.onCreated(this.mDelegateBinder, new java.util.ArrayList(set));
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telephony.ims.DelegateStateCallback
    public void onFeatureTagRegistrationChanged(android.telephony.ims.DelegateRegistrationState delegateRegistrationState) {
        try {
            this.mStateBinder.onFeatureTagRegistrationChanged(delegateRegistrationState);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telephony.ims.DelegateStateCallback
    public void onImsConfigurationChanged(android.telephony.ims.SipDelegateImsConfiguration sipDelegateImsConfiguration) {
        try {
            this.mStateBinder.onImsConfigurationChanged(sipDelegateImsConfiguration);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telephony.ims.DelegateStateCallback
    public void onConfigurationChanged(android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration) {
        try {
            this.mStateBinder.onConfigurationChanged(sipDelegateConfiguration);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telephony.ims.DelegateStateCallback
    public void onDestroyed(int i) {
        this.mDelegate = null;
        try {
            this.mStateBinder.onDestroyed(i);
        } catch (android.os.RemoteException e) {
        }
    }

    public android.telephony.ims.stub.SipDelegate getDelegate() {
        return this.mDelegate;
    }

    public android.telephony.ims.aidl.ISipDelegate getDelegateBinder() {
        return this.mDelegateBinder;
    }

    public android.telephony.ims.aidl.ISipDelegateStateCallback getStateCallbackBinder() {
        return this.mStateBinder;
    }

    private void notifyLocalMessageFailedToBeReceived(android.telephony.ims.SipMessage sipMessage, final int i) {
        final java.lang.String viaBranchParameter = sipMessage.getViaBranchParameter();
        final android.telephony.ims.stub.SipDelegate sipDelegate = this.mDelegate;
        if (sipDelegate != null) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.aidl.SipDelegateAidlWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.SipDelegate.this.notifyMessageReceiveError(viaBranchParameter, i);
                }
            });
        }
    }
}
