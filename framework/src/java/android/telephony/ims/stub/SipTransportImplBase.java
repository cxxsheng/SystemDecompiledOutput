package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class SipTransportImplBase {
    private static final java.lang.String LOG_TAG = "SipTransportIB";
    private java.util.concurrent.Executor mBinderExecutor;
    private final android.os.IBinder.DeathRecipient mDeathRecipient = new android.telephony.ims.stub.SipTransportImplBase.AnonymousClass1();
    private final android.telephony.ims.aidl.ISipTransport.Stub mSipTransportImpl = new android.telephony.ims.stub.SipTransportImplBase.AnonymousClass2();
    private final java.util.ArrayList<android.telephony.ims.aidl.SipDelegateAidlWrapper> mDelegates = new java.util.ArrayList<>();

    /* renamed from: android.telephony.ims.stub.SipTransportImplBase$1, reason: invalid class name */
    class AnonymousClass1 implements android.os.IBinder.DeathRecipient {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$0() {
            android.telephony.ims.stub.SipTransportImplBase.this.binderDiedInternal(null);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.telephony.ims.stub.SipTransportImplBase.this.mBinderExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.stub.SipTransportImplBase$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.SipTransportImplBase.AnonymousClass1.this.lambda$binderDied$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$1(android.os.IBinder iBinder) {
            android.telephony.ims.stub.SipTransportImplBase.this.binderDiedInternal(iBinder);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied(final android.os.IBinder iBinder) {
            android.telephony.ims.stub.SipTransportImplBase.this.mBinderExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.stub.SipTransportImplBase$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.SipTransportImplBase.AnonymousClass1.this.lambda$binderDied$1(iBinder);
                }
            });
        }
    }

    /* renamed from: android.telephony.ims.stub.SipTransportImplBase$2, reason: invalid class name */
    class AnonymousClass2 extends android.telephony.ims.aidl.ISipTransport.Stub {
        AnonymousClass2() {
        }

        @Override // android.telephony.ims.aidl.ISipTransport
        public void createSipDelegate(final int i, final android.telephony.ims.DelegateRequest delegateRequest, final android.telephony.ims.aidl.ISipDelegateStateCallback iSipDelegateStateCallback, final android.telephony.ims.aidl.ISipDelegateMessageCallback iSipDelegateMessageCallback) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.stub.SipTransportImplBase.this.mBinderExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.stub.SipTransportImplBase$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.stub.SipTransportImplBase.AnonymousClass2.this.lambda$createSipDelegate$0(i, delegateRequest, iSipDelegateStateCallback, iSipDelegateMessageCallback);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createSipDelegate$0(int i, android.telephony.ims.DelegateRequest delegateRequest, android.telephony.ims.aidl.ISipDelegateStateCallback iSipDelegateStateCallback, android.telephony.ims.aidl.ISipDelegateMessageCallback iSipDelegateMessageCallback) {
            android.telephony.ims.stub.SipTransportImplBase.this.createSipDelegateInternal(i, delegateRequest, iSipDelegateStateCallback, iSipDelegateMessageCallback);
        }

        @Override // android.telephony.ims.aidl.ISipTransport
        public void destroySipDelegate(final android.telephony.ims.aidl.ISipDelegate iSipDelegate, final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.telephony.ims.stub.SipTransportImplBase.this.mBinderExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.stub.SipTransportImplBase$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.stub.SipTransportImplBase.AnonymousClass2.this.lambda$destroySipDelegate$1(iSipDelegate, i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$destroySipDelegate$1(android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i) {
            android.telephony.ims.stub.SipTransportImplBase.this.destroySipDelegateInternal(iSipDelegate, i);
        }
    }

    public SipTransportImplBase() {
    }

    public SipTransportImplBase(java.util.concurrent.Executor executor) {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor must not be null");
        }
        this.mBinderExecutor = executor;
    }

    public void createSipDelegate(int i, android.telephony.ims.DelegateRequest delegateRequest, android.telephony.ims.DelegateStateCallback delegateStateCallback, android.telephony.ims.DelegateMessageCallback delegateMessageCallback) {
        throw new java.lang.UnsupportedOperationException("createSipDelegate not implemented!");
    }

    public void destroySipDelegate(android.telephony.ims.stub.SipDelegate sipDelegate, int i) {
        throw new java.lang.UnsupportedOperationException("destroySipDelegate not implemented!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createSipDelegateInternal(int i, android.telephony.ims.DelegateRequest delegateRequest, android.telephony.ims.aidl.ISipDelegateStateCallback iSipDelegateStateCallback, android.telephony.ims.aidl.ISipDelegateMessageCallback iSipDelegateMessageCallback) {
        android.telephony.ims.aidl.SipDelegateAidlWrapper sipDelegateAidlWrapper = new android.telephony.ims.aidl.SipDelegateAidlWrapper(this.mBinderExecutor, iSipDelegateStateCallback, iSipDelegateMessageCallback);
        this.mDelegates.add(sipDelegateAidlWrapper);
        linkDeathRecipient(sipDelegateAidlWrapper);
        createSipDelegate(i, delegateRequest, sipDelegateAidlWrapper, sipDelegateAidlWrapper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroySipDelegateInternal(android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i) {
        android.telephony.ims.aidl.SipDelegateAidlWrapper sipDelegateAidlWrapper;
        java.util.Iterator<android.telephony.ims.aidl.SipDelegateAidlWrapper> it = this.mDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                sipDelegateAidlWrapper = null;
                break;
            } else {
                sipDelegateAidlWrapper = it.next();
                if (java.util.Objects.equals(iSipDelegate, sipDelegateAidlWrapper.getDelegateBinder())) {
                    break;
                }
            }
        }
        if (sipDelegateAidlWrapper != null) {
            unlinkDeathRecipient(sipDelegateAidlWrapper);
            this.mDelegates.remove(sipDelegateAidlWrapper);
            destroySipDelegate(sipDelegateAidlWrapper.getDelegate(), i);
            return;
        }
        android.util.Log.w(LOG_TAG, "destroySipDelegateInternal, could not findSipDelegate corresponding to " + iSipDelegate);
    }

    private void linkDeathRecipient(android.telephony.ims.aidl.SipDelegateAidlWrapper sipDelegateAidlWrapper) {
        try {
            sipDelegateAidlWrapper.getStateCallbackBinder().asBinder().linkToDeath(this.mDeathRecipient, 0);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "linkDeathRecipient, remote process already died, cleaning up.");
            this.mDeathRecipient.binderDied(sipDelegateAidlWrapper.getStateCallbackBinder().asBinder());
        }
    }

    private void unlinkDeathRecipient(android.telephony.ims.aidl.SipDelegateAidlWrapper sipDelegateAidlWrapper) {
        try {
            sipDelegateAidlWrapper.getStateCallbackBinder().asBinder().unlinkToDeath(this.mDeathRecipient, 0);
        } catch (java.util.NoSuchElementException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void binderDiedInternal(android.os.IBinder iBinder) {
        java.util.Iterator<android.telephony.ims.aidl.SipDelegateAidlWrapper> it = this.mDelegates.iterator();
        while (it.hasNext()) {
            android.telephony.ims.aidl.SipDelegateAidlWrapper next = it.next();
            if (iBinder == null || next.getStateCallbackBinder().asBinder().equals(iBinder)) {
                android.util.Log.w(LOG_TAG, "Binder death detected for " + next + ", calling destroy and removing.");
                this.mDelegates.remove(next);
                destroySipDelegate(next.getDelegate(), 1);
                return;
            }
        }
        android.util.Log.w(LOG_TAG, "Binder death detected for IBinder " + iBinder + ", but couldn't find matching SipDelegate");
    }

    public android.telephony.ims.aidl.ISipTransport getBinder() {
        return this.mSipTransportImpl;
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        if (this.mBinderExecutor == null) {
            this.mBinderExecutor = executor;
        }
    }
}
