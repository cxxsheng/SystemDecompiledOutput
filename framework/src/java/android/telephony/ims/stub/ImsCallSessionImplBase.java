package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class ImsCallSessionImplBase implements java.lang.AutoCloseable {
    private static final java.lang.String LOG_TAG = "ImsCallSessionImplBase";
    public static final int MEDIA_STREAM_DIRECTION_DOWNLINK = 2;
    public static final int MEDIA_STREAM_DIRECTION_UPLINK = 1;
    public static final int MEDIA_STREAM_TYPE_AUDIO = 1;
    public static final int MEDIA_STREAM_TYPE_VIDEO = 2;
    public static final int USSD_MODE_NOTIFY = 0;
    public static final int USSD_MODE_REQUEST = 1;
    private java.util.concurrent.Executor mExecutor = new android.app.PendingIntent$$ExternalSyntheticLambda0();
    private com.android.ims.internal.IImsCallSession mServiceImpl = new android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MediaStreamDirection {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MediaStreamType {
    }

    public static class State {
        public static final int ESTABLISHED = 4;
        public static final int ESTABLISHING = 3;
        public static final int IDLE = 0;
        public static final int INITIATED = 1;
        public static final int INVALID = -1;
        public static final int NEGOTIATING = 2;
        public static final int REESTABLISHING = 6;
        public static final int RENEGOTIATING = 5;
        public static final int TERMINATED = 8;
        public static final int TERMINATING = 7;

        public static java.lang.String toString(int i) {
            switch (i) {
                case 0:
                    return "IDLE";
                case 1:
                    return "INITIATED";
                case 2:
                    return "NEGOTIATING";
                case 3:
                    return "ESTABLISHING";
                case 4:
                    return "ESTABLISHED";
                case 5:
                    return "RENEGOTIATING";
                case 6:
                    return "REESTABLISHING";
                case 7:
                    return "TERMINATING";
                case 8:
                    return "TERMINATED";
                default:
                    return "UNKNOWN";
            }
        }

        private State() {
        }
    }

    /* renamed from: android.telephony.ims.stub.ImsCallSessionImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.ims.internal.IImsCallSession.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$close$0() {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.close();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void close() {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$close$0();
                }
            }, "close");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.String lambda$getCallId$1() {
            return android.telephony.ims.stub.ImsCallSessionImplBase.this.getCallId();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public java.lang.String getCallId() {
            return (java.lang.String) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getCallId$1;
                    lambda$getCallId$1 = android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$getCallId$1();
                    return lambda$getCallId$1;
                }
            }, "getCallId");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.ImsCallProfile lambda$getCallProfile$2() {
            return android.telephony.ims.stub.ImsCallSessionImplBase.this.getCallProfile();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public android.telephony.ims.ImsCallProfile getCallProfile() {
            return (android.telephony.ims.ImsCallProfile) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda9
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.telephony.ims.ImsCallProfile lambda$getCallProfile$2;
                    lambda$getCallProfile$2 = android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$getCallProfile$2();
                    return lambda$getCallProfile$2;
                }
            }, "getCallProfile");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.ImsCallProfile lambda$getLocalCallProfile$3() {
            return android.telephony.ims.stub.ImsCallSessionImplBase.this.getLocalCallProfile();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public android.telephony.ims.ImsCallProfile getLocalCallProfile() {
            return (android.telephony.ims.ImsCallProfile) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.telephony.ims.ImsCallProfile lambda$getLocalCallProfile$3;
                    lambda$getLocalCallProfile$3 = android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$getLocalCallProfile$3();
                    return lambda$getLocalCallProfile$3;
                }
            }, "getLocalCallProfile");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.ImsCallProfile lambda$getRemoteCallProfile$4() {
            return android.telephony.ims.stub.ImsCallSessionImplBase.this.getRemoteCallProfile();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public android.telephony.ims.ImsCallProfile getRemoteCallProfile() {
            return (android.telephony.ims.ImsCallProfile) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda27
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.telephony.ims.ImsCallProfile lambda$getRemoteCallProfile$4;
                    lambda$getRemoteCallProfile$4 = android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$getRemoteCallProfile$4();
                    return lambda$getRemoteCallProfile$4;
                }
            }, "getRemoteCallProfile");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.String lambda$getProperty$5(java.lang.String str) {
            return android.telephony.ims.stub.ImsCallSessionImplBase.this.getProperty(str);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public java.lang.String getProperty(final java.lang.String str) {
            return (java.lang.String) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda32
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getProperty$5;
                    lambda$getProperty$5 = android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$getProperty$5(str);
                    return lambda$getProperty$5;
                }
            }, "getProperty");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$getState$6() {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsCallSessionImplBase.this.getState());
        }

        @Override // com.android.ims.internal.IImsCallSession
        public int getState() {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda25
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$getState$6;
                    lambda$getState$6 = android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$getState$6();
                    return lambda$getState$6;
                }
            }, "getState")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Boolean lambda$isInCall$7() {
            return java.lang.Boolean.valueOf(android.telephony.ims.stub.ImsCallSessionImplBase.this.isInCall());
        }

        @Override // com.android.ims.internal.IImsCallSession
        public boolean isInCall() {
            return ((java.lang.Boolean) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda33
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Boolean lambda$isInCall$7;
                    lambda$isInCall$7 = android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$isInCall$7();
                    return lambda$isInCall$7;
                }
            }, "isInCall")).booleanValue();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) {
            final android.telephony.ims.ImsCallSessionListener imsCallSessionListener = new android.telephony.ims.ImsCallSessionListener(iImsCallSessionListener);
            imsCallSessionListener.setDefaultExecutor(android.telephony.ims.stub.ImsCallSessionImplBase.this.mExecutor);
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda37
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$setListener$8(imsCallSessionListener);
                }
            }, "setListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$8(android.telephony.ims.ImsCallSessionListener imsCallSessionListener) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.setListener(imsCallSessionListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setMute$9(boolean z) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.setMute(z);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void setMute(final boolean z) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$setMute$9(z);
                }
            }, "setMute");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$10(java.lang.String str, android.telephony.ims.ImsCallProfile imsCallProfile) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.start(str, imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void start(final java.lang.String str, final android.telephony.ims.ImsCallProfile imsCallProfile) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$start$10(str, imsCallProfile);
                }
            }, "start");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startConference$11(java.lang.String[] strArr, android.telephony.ims.ImsCallProfile imsCallProfile) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.startConference(strArr, imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void startConference(final java.lang.String[] strArr, final android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$startConference$11(strArr, imsCallProfile);
                }
            }, "startConference");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$accept$12(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.accept(i, imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void accept(final int i, final android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$accept$12(i, imsStreamMediaProfile);
                }
            }, "accept");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$deflect$13(java.lang.String str) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.deflect(str);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void deflect(final java.lang.String str) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$deflect$13(str);
                }
            }, "deflect");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$reject$14(int i) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.reject(i);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void reject(final int i) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$reject$14(i);
                }
            }, "reject");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$transfer$15(java.lang.String str, boolean z) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.transfer(str, z);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void transfer(final java.lang.String str, final boolean z) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$transfer$15(str, z);
                }
            }, "transfer");
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void consultativeTransfer(final com.android.ims.internal.IImsCallSession iImsCallSession) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$consultativeTransfer$16(iImsCallSession);
                }
            }, "consultativeTransfer");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$consultativeTransfer$16(com.android.ims.internal.IImsCallSession iImsCallSession) {
            android.telephony.ims.stub.ImsCallSessionImplBase imsCallSessionImplBase = new android.telephony.ims.stub.ImsCallSessionImplBase();
            imsCallSessionImplBase.setServiceImpl(iImsCallSession);
            android.telephony.ims.stub.ImsCallSessionImplBase.this.transfer(imsCallSessionImplBase);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$terminate$17(int i) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.terminate(i);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void terminate(final int i) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$terminate$17(i);
                }
            }, "terminate");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$hold$18(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.hold(imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void hold(final android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$hold$18(imsStreamMediaProfile);
                }
            }, "hold");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resume$19(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.resume(imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void resume(final android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$resume$19(imsStreamMediaProfile);
                }
            }, android.media.tv.interactive.TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_RESUME);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$merge$20() {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.merge();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void merge() {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$merge$20();
                }
            }, "merge");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$update$21(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.update(i, imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void update(final int i, final android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$update$21(i, imsStreamMediaProfile);
                }
            }, "update");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$extendToConference$22(java.lang.String[] strArr) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.extendToConference(strArr);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void extendToConference(final java.lang.String[] strArr) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$extendToConference$22(strArr);
                }
            }, "extendToConference");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$inviteParticipants$23(java.lang.String[] strArr) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.inviteParticipants(strArr);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void inviteParticipants(final java.lang.String[] strArr) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$inviteParticipants$23(strArr);
                }
            }, "inviteParticipants");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeParticipants$24(java.lang.String[] strArr) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.removeParticipants(strArr);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void removeParticipants(final java.lang.String[] strArr) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$removeParticipants$24(strArr);
                }
            }, "removeParticipants");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendDtmf$25(char c, android.os.Message message) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.sendDtmf(c, message);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendDtmf(final char c, final android.os.Message message) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$sendDtmf$25(c, message);
                }
            }, "sendDtmf");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startDtmf$26(char c) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.startDtmf(c);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void startDtmf(final char c) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$startDtmf$26(c);
                }
            }, "startDtmf");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopDtmf$27() {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.stopDtmf();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void stopDtmf() {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$stopDtmf$27();
                }
            }, "stopDtmf");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendUssd$28(java.lang.String str) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.sendUssd(str);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendUssd(final java.lang.String str) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$sendUssd$28(str);
                }
            }, "sendUssd");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.ims.internal.IImsVideoCallProvider lambda$getVideoCallProvider$29() {
            return android.telephony.ims.stub.ImsCallSessionImplBase.this.getVideoCallProvider();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public com.android.ims.internal.IImsVideoCallProvider getVideoCallProvider() {
            return (com.android.ims.internal.IImsVideoCallProvider) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.ims.internal.IImsVideoCallProvider lambda$getVideoCallProvider$29;
                    lambda$getVideoCallProvider$29 = android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$getVideoCallProvider$29();
                    return lambda$getVideoCallProvider$29;
                }
            }, "getVideoCallProvider");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Boolean lambda$isMultiparty$30() {
            return java.lang.Boolean.valueOf(android.telephony.ims.stub.ImsCallSessionImplBase.this.isMultiparty());
        }

        @Override // com.android.ims.internal.IImsCallSession
        public boolean isMultiparty() {
            return ((java.lang.Boolean) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda26
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Boolean lambda$isMultiparty$30;
                    lambda$isMultiparty$30 = android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$isMultiparty$30();
                    return lambda$isMultiparty$30;
                }
            }, "isMultiparty")).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendRttModifyRequest$31(android.telephony.ims.ImsCallProfile imsCallProfile) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.sendRttModifyRequest(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttModifyRequest(final android.telephony.ims.ImsCallProfile imsCallProfile) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$sendRttModifyRequest$31(imsCallProfile);
                }
            }, "sendRttModifyRequest");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendRttModifyResponse$32(boolean z) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.sendRttModifyResponse(z);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttModifyResponse(final boolean z) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$sendRttModifyResponse$32(z);
                }
            }, "sendRttModifyResponse");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendRttMessage$33(java.lang.String str) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.sendRttMessage(str);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttMessage(final java.lang.String str) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$sendRttMessage$33(str);
                }
            }, "sendRttMessage");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendRtpHeaderExtensions$34(java.util.List list) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.sendRtpHeaderExtensions(new android.util.ArraySet(list));
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRtpHeaderExtensions(final java.util.List<android.telephony.ims.RtpHeaderExtension> list) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$sendRtpHeaderExtensions$34(list);
                }
            }, "sendRtpHeaderExtensions");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionNotifyAnbr$35(int i, int i2, int i3) {
            android.telephony.ims.stub.ImsCallSessionImplBase.this.callSessionNotifyAnbr(i, i2, i3);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void callSessionNotifyAnbr(final int i, final int i2, final int i3) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsCallSessionImplBase.AnonymousClass1.this.lambda$callSessionNotifyAnbr$35(i, i2, i3);
                }
            }, "callSessionNotifyAnbr");
        }

        private void executeMethodAsync(final java.lang.Runnable runnable, java.lang.String str) {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda36
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, android.telephony.ims.stub.ImsCallSessionImplBase.this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsCallSessionImplBase.LOG_TAG, "ImsCallSessionImplBase Binder - " + str + " exception: " + e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final java.util.function.Supplier<T> supplier, java.lang.String str) {
            try {
                return (T) java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda22
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity((java.util.function.Supplier<java.lang.Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, android.telephony.ims.stub.ImsCallSessionImplBase.this.mExecutor).get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsCallSessionImplBase.LOG_TAG, "ImsCallSessionImplBase Binder - " + str + " exception: " + e.getMessage());
                return null;
            }
        }
    }

    public final void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) throws android.os.RemoteException {
        setListener(new android.telephony.ims.ImsCallSessionListener(iImsCallSessionListener));
    }

    @java.lang.Deprecated
    public void setListener(android.telephony.ims.ImsCallSessionListener imsCallSessionListener) {
    }

    @Override // java.lang.AutoCloseable
    public void close() {
    }

    public java.lang.String getCallId() {
        return null;
    }

    public android.telephony.ims.ImsCallProfile getCallProfile() {
        return null;
    }

    public android.telephony.ims.ImsCallProfile getLocalCallProfile() {
        return null;
    }

    public android.telephony.ims.ImsCallProfile getRemoteCallProfile() {
        return null;
    }

    public java.lang.String getProperty(java.lang.String str) {
        return null;
    }

    public int getState() {
        return -1;
    }

    public boolean isInCall() {
        return false;
    }

    public void setMute(boolean z) {
    }

    public void start(java.lang.String str, android.telephony.ims.ImsCallProfile imsCallProfile) {
    }

    public void startConference(java.lang.String[] strArr, android.telephony.ims.ImsCallProfile imsCallProfile) {
    }

    public void accept(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public void deflect(java.lang.String str) {
    }

    public void reject(int i) {
    }

    public void transfer(java.lang.String str, boolean z) {
    }

    public void transfer(android.telephony.ims.stub.ImsCallSessionImplBase imsCallSessionImplBase) {
    }

    public void terminate(int i) {
    }

    public void hold(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public void resume(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public void merge() {
    }

    public void update(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public void extendToConference(java.lang.String[] strArr) {
    }

    public void inviteParticipants(java.lang.String[] strArr) {
    }

    public void removeParticipants(java.lang.String[] strArr) {
    }

    public void sendDtmf(char c, android.os.Message message) {
    }

    public void startDtmf(char c) {
    }

    public void stopDtmf() {
    }

    public void sendUssd(java.lang.String str) {
    }

    public com.android.ims.internal.IImsVideoCallProvider getVideoCallProvider() {
        android.telephony.ims.ImsVideoCallProvider imsVideoCallProvider = getImsVideoCallProvider();
        if (imsVideoCallProvider != null) {
            return imsVideoCallProvider.getInterface();
        }
        return null;
    }

    public android.telephony.ims.ImsVideoCallProvider getImsVideoCallProvider() {
        return null;
    }

    public boolean isMultiparty() {
        return false;
    }

    public void sendRttModifyRequest(android.telephony.ims.ImsCallProfile imsCallProfile) {
    }

    public void sendRttModifyResponse(boolean z) {
    }

    public void sendRttMessage(java.lang.String str) {
    }

    public void sendRtpHeaderExtensions(java.util.Set<android.telephony.ims.RtpHeaderExtension> set) {
    }

    public void callSessionNotifyAnbr(int i, int i2, int i3) {
        android.util.Log.i(LOG_TAG, "ImsCallSessionImplBase callSessionNotifyAnbr - mediaType: " + i);
    }

    public com.android.ims.internal.IImsCallSession getServiceImpl() {
        return this.mServiceImpl;
    }

    public void setServiceImpl(com.android.ims.internal.IImsCallSession iImsCallSession) {
        this.mServiceImpl = iImsCallSession;
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }
}
