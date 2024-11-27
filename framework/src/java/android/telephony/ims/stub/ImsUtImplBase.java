package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class ImsUtImplBase {
    public static final int CALL_BARRING_ALL = 7;
    public static final int CALL_BARRING_ALL_INCOMING = 1;
    public static final int CALL_BARRING_ALL_OUTGOING = 2;
    public static final int CALL_BARRING_ANONYMOUS_INCOMING = 6;
    public static final int CALL_BARRING_INCOMING_ALL_SERVICES = 9;
    public static final int CALL_BARRING_OUTGOING_ALL_SERVICES = 8;
    public static final int CALL_BARRING_OUTGOING_INTL = 3;
    public static final int CALL_BARRING_OUTGOING_INTL_EXCL_HOME = 4;
    public static final int CALL_BARRING_SPECIFIC_INCOMING_CALLS = 10;
    public static final int CALL_BLOCKING_INCOMING_WHEN_ROAMING = 5;
    public static final int INVALID_RESULT = -1;
    private static final java.lang.String TAG = "ImsUtImplBase";
    private java.util.concurrent.Executor mExecutor = new android.app.PendingIntent$$ExternalSyntheticLambda0();
    private final com.android.ims.internal.IImsUt.Stub mServiceImpl = new android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallBarringMode {
    }

    /* renamed from: android.telephony.ims.stub.ImsUtImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.ims.internal.IImsUt.Stub {
        private final java.lang.Object mLock = new java.lang.Object();
        private android.telephony.ims.ImsUtListener mUtListener;

        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$close$0() {
            android.telephony.ims.stub.ImsUtImplBase.this.close();
        }

        @Override // com.android.ims.internal.IImsUt
        public void close() throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$close$0();
                }
            }, "close");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$queryCallBarring$1(int i) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.queryCallBarring(i));
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallBarring(final int i) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda9
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$queryCallBarring$1;
                    lambda$queryCallBarring$1 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$queryCallBarring$1(i);
                    return lambda$queryCallBarring$1;
                }
            }, "queryCallBarring")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$queryCallForward$2(int i, java.lang.String str) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.queryCallForward(i, str));
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallForward(final int i, final java.lang.String str) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$queryCallForward$2;
                    lambda$queryCallForward$2 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$queryCallForward$2(i, str);
                    return lambda$queryCallForward$2;
                }
            }, "queryCallForward")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$queryCallWaiting$3() {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.queryCallWaiting());
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallWaiting() throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$queryCallWaiting$3;
                    lambda$queryCallWaiting$3 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$queryCallWaiting$3();
                    return lambda$queryCallWaiting$3;
                }
            }, "queryCallWaiting")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$queryCLIR$4() {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.queryCLIR());
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCLIR() throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$queryCLIR$4;
                    lambda$queryCLIR$4 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$queryCLIR$4();
                    return lambda$queryCLIR$4;
                }
            }, "queryCLIR")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$queryCLIP$5() {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.queryCLIP());
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCLIP() throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$queryCLIP$5;
                    lambda$queryCLIP$5 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$queryCLIP$5();
                    return lambda$queryCLIP$5;
                }
            }, "queryCLIP")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$queryCOLR$6() {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.queryCOLR());
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCOLR() throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda10
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$queryCOLR$6;
                    lambda$queryCOLR$6 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$queryCOLR$6();
                    return lambda$queryCOLR$6;
                }
            }, "queryCOLR")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$queryCOLP$7() {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.queryCOLP());
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCOLP() throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$queryCOLP$7;
                    lambda$queryCOLP$7 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$queryCOLP$7();
                    return lambda$queryCOLP$7;
                }
            }, "queryCOLP")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$transact$8(android.os.Bundle bundle) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.transact(bundle));
        }

        @Override // com.android.ims.internal.IImsUt
        public int transact(final android.os.Bundle bundle) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda14
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$transact$8;
                    lambda$transact$8 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$transact$8(bundle);
                    return lambda$transact$8;
                }
            }, "transact")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$updateCallBarring$9(int i, int i2, java.lang.String[] strArr) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.updateCallBarring(i, i2, strArr));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallBarring(final int i, final int i2, final java.lang.String[] strArr) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$updateCallBarring$9;
                    lambda$updateCallBarring$9 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$updateCallBarring$9(i, i2, strArr);
                    return lambda$updateCallBarring$9;
                }
            }, "updateCallBarring")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$updateCallForward$10(int i, int i2, java.lang.String str, int i3, int i4) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.updateCallForward(i, i2, str, i3, i4));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallForward(final int i, final int i2, final java.lang.String str, final int i3, final int i4) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda15
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$updateCallForward$10;
                    lambda$updateCallForward$10 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$updateCallForward$10(i, i2, str, i3, i4);
                    return lambda$updateCallForward$10;
                }
            }, "updateCallForward")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$updateCallWaiting$11(boolean z, int i) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.updateCallWaiting(z, i));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallWaiting(final boolean z, final int i) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda16
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$updateCallWaiting$11;
                    lambda$updateCallWaiting$11 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$updateCallWaiting$11(z, i);
                    return lambda$updateCallWaiting$11;
                }
            }, "updateCallWaiting")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$updateCLIR$12(int i) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.updateCLIR(i));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCLIR(final int i) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$updateCLIR$12;
                    lambda$updateCLIR$12 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$updateCLIR$12(i);
                    return lambda$updateCLIR$12;
                }
            }, "updateCLIR")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$updateCLIP$13(boolean z) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.updateCLIP(z));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCLIP(final boolean z) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda21
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$updateCLIP$13;
                    lambda$updateCLIP$13 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$updateCLIP$13(z);
                    return lambda$updateCLIP$13;
                }
            }, "updateCLIP")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$updateCOLR$14(int i) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.updateCOLR(i));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCOLR(final int i) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda11
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$updateCOLR$14;
                    lambda$updateCOLR$14 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$updateCOLR$14(i);
                    return lambda$updateCOLR$14;
                }
            }, "updateCOLR")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$updateCOLP$15(boolean z) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.updateCOLP(z));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCOLP(final boolean z) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda12
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$updateCOLP$15;
                    lambda$updateCOLP$15 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$updateCOLP$15(z);
                    return lambda$updateCOLP$15;
                }
            }, "updateCOLP")).intValue();
        }

        @Override // com.android.ims.internal.IImsUt
        public void setListener(final com.android.ims.internal.IImsUtListener iImsUtListener) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$setListener$16(iImsUtListener);
                }
            }, "setListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$16(com.android.ims.internal.IImsUtListener iImsUtListener) {
            if (this.mUtListener != null && !this.mUtListener.getListenerInterface().asBinder().isBinderAlive()) {
                android.util.Log.w(android.telephony.ims.stub.ImsUtImplBase.TAG, "setListener: discarding dead Binder");
                this.mUtListener = null;
            }
            if (this.mUtListener != null && iImsUtListener != null && java.util.Objects.equals(this.mUtListener.getListenerInterface().asBinder(), iImsUtListener.asBinder())) {
                return;
            }
            if (iImsUtListener == null) {
                this.mUtListener = null;
            } else if (iImsUtListener != null && this.mUtListener == null) {
                this.mUtListener = new android.telephony.ims.ImsUtListener(iImsUtListener);
            } else {
                android.util.Log.w(android.telephony.ims.stub.ImsUtImplBase.TAG, "setListener is being called when there is already an active listener");
                this.mUtListener = new android.telephony.ims.ImsUtListener(iImsUtListener);
            }
            android.telephony.ims.stub.ImsUtImplBase.this.setListener(this.mUtListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$queryCallBarringForServiceClass$17(int i, int i2) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.queryCallBarringForServiceClass(i, i2));
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallBarringForServiceClass(final int i, final int i2) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda20
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$queryCallBarringForServiceClass$17;
                    lambda$queryCallBarringForServiceClass$17 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$queryCallBarringForServiceClass$17(i, i2);
                    return lambda$queryCallBarringForServiceClass$17;
                }
            }, "queryCallBarringForServiceClass")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$updateCallBarringForServiceClass$18(int i, int i2, java.lang.String[] strArr, int i3) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.updateCallBarringForServiceClass(i, i2, strArr, i3));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallBarringForServiceClass(final int i, final int i2, final java.lang.String[] strArr, final int i3) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda17
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$updateCallBarringForServiceClass$18;
                    lambda$updateCallBarringForServiceClass$18 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$updateCallBarringForServiceClass$18(i, i2, strArr, i3);
                    return lambda$updateCallBarringForServiceClass$18;
                }
            }, "updateCallBarringForServiceClass")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$updateCallBarringWithPassword$19(int i, int i2, java.lang.String[] strArr, int i3, java.lang.String str) {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsUtImplBase.this.updateCallBarringWithPassword(i, i2, strArr, i3, str));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallBarringWithPassword(final int i, final int i2, final java.lang.String[] strArr, final int i3, final java.lang.String str) throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$updateCallBarringWithPassword$19;
                    lambda$updateCallBarringWithPassword$19 = android.telephony.ims.stub.ImsUtImplBase.AnonymousClass1.this.lambda$updateCallBarringWithPassword$19(i, i2, strArr, i3, str);
                    return lambda$updateCallBarringWithPassword$19;
                }
            }, "updateCallBarringWithPassword")).intValue();
        }

        private void executeMethodAsync(final java.lang.Runnable runnable, java.lang.String str) throws android.os.RemoteException {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, android.telephony.ims.stub.ImsUtImplBase.this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsUtImplBase.TAG, "ImsUtImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final java.util.function.Supplier<T> supplier, java.lang.String str) throws android.os.RemoteException {
            try {
                return (T) java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda19
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity((java.util.function.Supplier<java.lang.Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, android.telephony.ims.stub.ImsUtImplBase.this.mExecutor).get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsUtImplBase.TAG, "ImsUtImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }
    }

    public void close() {
    }

    public int queryCallBarring(int i) {
        return -1;
    }

    public int queryCallBarringForServiceClass(int i, int i2) {
        return -1;
    }

    public int queryCallForward(int i, java.lang.String str) {
        return -1;
    }

    public int queryCallWaiting() {
        return -1;
    }

    public int queryCLIR() {
        return queryClir();
    }

    public int queryCLIP() {
        return queryClip();
    }

    public int queryCOLR() {
        return queryColr();
    }

    public int queryCOLP() {
        return queryColp();
    }

    public int queryClir() {
        return -1;
    }

    public int queryClip() {
        return -1;
    }

    public int queryColr() {
        return -1;
    }

    public int queryColp() {
        return -1;
    }

    public int transact(android.os.Bundle bundle) {
        return -1;
    }

    public int updateCallBarring(int i, int i2, java.lang.String[] strArr) {
        return -1;
    }

    public int updateCallBarringForServiceClass(int i, int i2, java.lang.String[] strArr, int i3) {
        return -1;
    }

    public int updateCallBarringWithPassword(int i, int i2, java.lang.String[] strArr, int i3, java.lang.String str) {
        return -1;
    }

    public int updateCallForward(int i, int i2, java.lang.String str, int i3, int i4) {
        return 0;
    }

    public int updateCallWaiting(boolean z, int i) {
        return -1;
    }

    public int updateCLIR(int i) {
        return updateClir(i);
    }

    public int updateCLIP(boolean z) {
        return updateClip(z);
    }

    public int updateCOLR(int i) {
        return updateColr(i);
    }

    public int updateCOLP(boolean z) {
        return updateColp(z);
    }

    public int updateClir(int i) {
        return -1;
    }

    public int updateClip(boolean z) {
        return -1;
    }

    public int updateColr(int i) {
        return -1;
    }

    public int updateColp(boolean z) {
        return -1;
    }

    public void setListener(android.telephony.ims.ImsUtListener imsUtListener) {
    }

    public com.android.ims.internal.IImsUt getInterface() {
        return this.mServiceImpl;
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }
}
