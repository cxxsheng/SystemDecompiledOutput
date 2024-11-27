package com.android.server.soundtrigger;

/* loaded from: classes2.dex */
public class PhoneCallStateHandler {
    private final com.android.server.soundtrigger.PhoneCallStateHandler.Callback mCallback;
    private final android.telephony.SubscriptionManager mSubscriptionManager;
    private final android.telephony.TelephonyManager mTelephonyManager;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<com.android.server.soundtrigger.PhoneCallStateHandler.MyCallStateListener> mListenerList = new java.util.ArrayList();
    private final java.util.concurrent.atomic.AtomicBoolean mIsPhoneCallOngoing = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.util.concurrent.ExecutorService mExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();

    public interface Callback {
        void onPhoneCallStateChanged(boolean z);
    }

    public PhoneCallStateHandler(android.telephony.SubscriptionManager subscriptionManager, android.telephony.TelephonyManager telephonyManager, com.android.server.soundtrigger.PhoneCallStateHandler.Callback callback) {
        java.util.Objects.requireNonNull(subscriptionManager);
        this.mSubscriptionManager = subscriptionManager.createForAllUserProfiles();
        java.util.Objects.requireNonNull(telephonyManager);
        this.mTelephonyManager = telephonyManager;
        java.util.Objects.requireNonNull(callback);
        this.mCallback = callback;
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mExecutor, new android.telephony.SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler.1
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public void onSubscriptionsChanged() {
                com.android.server.soundtrigger.PhoneCallStateHandler.this.updateTelephonyListeners();
            }

            public void onAddListenerFailed() {
                android.util.Slog.wtf("SoundTriggerPhoneCallStateHandler", "Failed to add a telephony listener");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class MyCallStateListener extends android.telephony.TelephonyCallback implements android.telephony.TelephonyCallback.CallStateListener {
        final android.telephony.TelephonyManager mTelephonyManagerForSubId;

        MyCallStateListener(android.telephony.TelephonyManager telephonyManager) {
            this.mTelephonyManagerForSubId = telephonyManager;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$cleanup$0() {
            this.mTelephonyManagerForSubId.unregisterTelephonyCallback(this);
        }

        void cleanup() {
            com.android.server.soundtrigger.PhoneCallStateHandler.this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$MyCallStateListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger.PhoneCallStateHandler.MyCallStateListener.this.lambda$cleanup$0();
                }
            });
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public void onCallStateChanged(int i) {
            com.android.server.soundtrigger.PhoneCallStateHandler.this.updateCallStatus();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCallStatus() {
        boolean checkCallStatus = checkCallStatus();
        if (this.mIsPhoneCallOngoing.compareAndSet(!checkCallStatus, checkCallStatus)) {
            this.mCallback.onPhoneCallStateChanged(checkCallStatus);
        }
    }

    private boolean checkCallStatus() {
        java.util.List<android.telephony.SubscriptionInfo> activeSubscriptionInfoList = this.mSubscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            return false;
        }
        if (!com.android.internal.telephony.flags.Flags.enforceTelephonyFeatureMapping()) {
            return activeSubscriptionInfoList.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$checkCallStatus$0;
                    lambda$checkCallStatus$0 = com.android.server.soundtrigger.PhoneCallStateHandler.lambda$checkCallStatus$0((android.telephony.SubscriptionInfo) obj);
                    return lambda$checkCallStatus$0;
                }
            }).anyMatch(new java.util.function.Predicate() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$checkCallStatus$1;
                    lambda$checkCallStatus$1 = com.android.server.soundtrigger.PhoneCallStateHandler.this.lambda$checkCallStatus$1((android.telephony.SubscriptionInfo) obj);
                    return lambda$checkCallStatus$1;
                }
            });
        }
        return activeSubscriptionInfoList.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$checkCallStatus$2;
                lambda$checkCallStatus$2 = com.android.server.soundtrigger.PhoneCallStateHandler.lambda$checkCallStatus$2((android.telephony.SubscriptionInfo) obj);
                return lambda$checkCallStatus$2;
            }
        }).anyMatch(new java.util.function.Predicate() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$checkCallStatus$3;
                lambda$checkCallStatus$3 = com.android.server.soundtrigger.PhoneCallStateHandler.this.lambda$checkCallStatus$3((android.telephony.SubscriptionInfo) obj);
                return lambda$checkCallStatus$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$checkCallStatus$0(android.telephony.SubscriptionInfo subscriptionInfo) {
        return subscriptionInfo.getSubscriptionId() != -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkCallStatus$1(android.telephony.SubscriptionInfo subscriptionInfo) {
        return isCallOngoingFromState(this.mTelephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId()).getCallStateForSubscription());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$checkCallStatus$2(android.telephony.SubscriptionInfo subscriptionInfo) {
        return subscriptionInfo.getSubscriptionId() != -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkCallStatus$3(android.telephony.SubscriptionInfo subscriptionInfo) {
        try {
            return isCallOngoingFromState(this.mTelephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId()).getCallStateForSubscription());
        } catch (java.lang.UnsupportedOperationException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTelephonyListeners() {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.server.soundtrigger.PhoneCallStateHandler.MyCallStateListener> it = this.mListenerList.iterator();
                while (it.hasNext()) {
                    it.next().cleanup();
                }
                this.mListenerList.clear();
                java.util.List<android.telephony.SubscriptionInfo> activeSubscriptionInfoList = this.mSubscriptionManager.getActiveSubscriptionInfoList();
                if (activeSubscriptionInfoList == null) {
                    return;
                }
                activeSubscriptionInfoList.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$updateTelephonyListeners$4;
                        lambda$updateTelephonyListeners$4 = com.android.server.soundtrigger.PhoneCallStateHandler.lambda$updateTelephonyListeners$4((android.telephony.SubscriptionInfo) obj);
                        return lambda$updateTelephonyListeners$4;
                    }
                }).map(new java.util.function.Function() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        android.telephony.TelephonyManager lambda$updateTelephonyListeners$5;
                        lambda$updateTelephonyListeners$5 = com.android.server.soundtrigger.PhoneCallStateHandler.this.lambda$updateTelephonyListeners$5((android.telephony.SubscriptionInfo) obj);
                        return lambda$updateTelephonyListeners$5;
                    }
                }).forEach(new java.util.function.Consumer() { // from class: com.android.server.soundtrigger.PhoneCallStateHandler$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.soundtrigger.PhoneCallStateHandler.this.lambda$updateTelephonyListeners$6((android.telephony.TelephonyManager) obj);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateTelephonyListeners$4(android.telephony.SubscriptionInfo subscriptionInfo) {
        return subscriptionInfo.getSubscriptionId() != -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.telephony.TelephonyManager lambda$updateTelephonyListeners$5(android.telephony.SubscriptionInfo subscriptionInfo) {
        return this.mTelephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTelephonyListeners$6(android.telephony.TelephonyManager telephonyManager) {
        synchronized (this.mLock) {
            com.android.server.soundtrigger.PhoneCallStateHandler.MyCallStateListener myCallStateListener = new com.android.server.soundtrigger.PhoneCallStateHandler.MyCallStateListener(telephonyManager);
            this.mListenerList.add(myCallStateListener);
            telephonyManager.registerTelephonyCallback(this.mExecutor, myCallStateListener);
        }
    }

    private static boolean isCallOngoingFromState(int i) {
        switch (i) {
            case 0:
            case 1:
                return false;
            case 2:
                return true;
            default:
                throw new java.lang.IllegalStateException("Received unexpected call state from Telephony Manager: " + i);
        }
    }
}
