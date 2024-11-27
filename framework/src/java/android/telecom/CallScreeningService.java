package android.telecom;

/* loaded from: classes3.dex */
public abstract class CallScreeningService extends android.app.Service {
    private static final int MSG_SCREEN_CALL = 1;
    public static final java.lang.String SERVICE_INTERFACE = "android.telecom.CallScreeningService";
    private com.android.internal.telecom.ICallScreeningAdapter mCallScreeningAdapter;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.telecom.CallScreeningService.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        try {
                            android.telecom.CallScreeningService.this.mCallScreeningAdapter = (com.android.internal.telecom.ICallScreeningAdapter) someArgs.arg1;
                            android.telecom.Call.Details createFromParcelableCall = android.telecom.Call.Details.createFromParcelableCall((android.telecom.ParcelableCall) someArgs.arg2);
                            android.telecom.CallScreeningService.this.onScreenCall(createFromParcelableCall);
                            if (createFromParcelableCall.getCallDirection() == 1) {
                                android.telecom.CallScreeningService.this.mCallScreeningAdapter.onScreeningResponse(createFromParcelableCall.getTelecomCallId(), new android.content.ComponentName(android.telecom.CallScreeningService.this.getPackageName(), getClass().getName()), null);
                            }
                        } catch (android.os.RemoteException e) {
                            android.telecom.Log.w(this, "Exception when screening call: " + e, new java.lang.Object[0]);
                        }
                        return;
                    } finally {
                        someArgs.recycle();
                    }
                default:
                    return;
            }
        }
    };

    public abstract void onScreenCall(android.telecom.Call.Details details);

    private final class CallScreeningBinder extends com.android.internal.telecom.ICallScreeningService.Stub {
        private CallScreeningBinder() {
        }

        @Override // com.android.internal.telecom.ICallScreeningService
        public void screenCall(com.android.internal.telecom.ICallScreeningAdapter iCallScreeningAdapter, android.telecom.ParcelableCall parcelableCall) {
            android.telecom.Log.v(this, "screenCall", new java.lang.Object[0]);
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = iCallScreeningAdapter;
            obtain.arg2 = parcelableCall;
            android.telecom.CallScreeningService.this.mHandler.obtainMessage(1, obtain).sendToTarget();
        }
    }

    public static class ParcelableCallResponse implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.telecom.CallScreeningService.ParcelableCallResponse> CREATOR = new android.os.Parcelable.Creator<android.telecom.CallScreeningService.ParcelableCallResponse>() { // from class: android.telecom.CallScreeningService.ParcelableCallResponse.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telecom.CallScreeningService.ParcelableCallResponse createFromParcel(android.os.Parcel parcel) {
                return new android.telecom.CallScreeningService.ParcelableCallResponse(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telecom.CallScreeningService.ParcelableCallResponse[] newArray(int i) {
                return new android.telecom.CallScreeningService.ParcelableCallResponse[i];
            }
        };
        private final int mCallComposerAttachmentsToShow;
        private final boolean mShouldDisallowCall;
        private final boolean mShouldRejectCall;
        private final boolean mShouldScreenCallViaAudioProcessing;
        private final boolean mShouldSilenceCall;
        private final boolean mShouldSkipCallLog;
        private final boolean mShouldSkipNotification;

        private ParcelableCallResponse(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i) {
            this.mShouldDisallowCall = z;
            this.mShouldRejectCall = z2;
            this.mShouldSilenceCall = z3;
            this.mShouldSkipCallLog = z4;
            this.mShouldSkipNotification = z5;
            this.mShouldScreenCallViaAudioProcessing = z6;
            this.mCallComposerAttachmentsToShow = i;
        }

        protected ParcelableCallResponse(android.os.Parcel parcel) {
            this.mShouldDisallowCall = parcel.readBoolean();
            this.mShouldRejectCall = parcel.readBoolean();
            this.mShouldSilenceCall = parcel.readBoolean();
            this.mShouldSkipCallLog = parcel.readBoolean();
            this.mShouldSkipNotification = parcel.readBoolean();
            this.mShouldScreenCallViaAudioProcessing = parcel.readBoolean();
            this.mCallComposerAttachmentsToShow = parcel.readInt();
        }

        public android.telecom.CallScreeningService.CallResponse toCallResponse() {
            return new android.telecom.CallScreeningService.CallResponse.Builder().setDisallowCall(this.mShouldDisallowCall).setRejectCall(this.mShouldRejectCall).setSilenceCall(this.mShouldSilenceCall).setSkipCallLog(this.mShouldSkipCallLog).setSkipNotification(this.mShouldSkipNotification).setShouldScreenCallViaAudioProcessing(this.mShouldScreenCallViaAudioProcessing).setCallComposerAttachmentsToShow(this.mCallComposerAttachmentsToShow).build();
        }

        public boolean shouldDisallowCall() {
            return this.mShouldDisallowCall;
        }

        public boolean shouldRejectCall() {
            return this.mShouldRejectCall;
        }

        public boolean shouldSilenceCall() {
            return this.mShouldSilenceCall;
        }

        public boolean shouldSkipCallLog() {
            return this.mShouldSkipCallLog;
        }

        public boolean shouldSkipNotification() {
            return this.mShouldSkipNotification;
        }

        public boolean shouldScreenCallViaAudioProcessing() {
            return this.mShouldScreenCallViaAudioProcessing;
        }

        public int getCallComposerAttachmentsToShow() {
            return this.mCallComposerAttachmentsToShow;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeBoolean(this.mShouldDisallowCall);
            parcel.writeBoolean(this.mShouldRejectCall);
            parcel.writeBoolean(this.mShouldSilenceCall);
            parcel.writeBoolean(this.mShouldSkipCallLog);
            parcel.writeBoolean(this.mShouldSkipNotification);
            parcel.writeBoolean(this.mShouldScreenCallViaAudioProcessing);
            parcel.writeInt(this.mCallComposerAttachmentsToShow);
        }
    }

    public static class CallResponse {
        public static final int CALL_COMPOSER_ATTACHMENT_LOCATION = 2;
        public static final int CALL_COMPOSER_ATTACHMENT_PICTURE = 1;
        public static final int CALL_COMPOSER_ATTACHMENT_PRIORITY = 8;
        public static final int CALL_COMPOSER_ATTACHMENT_SUBJECT = 4;
        private static final int NUM_CALL_COMPOSER_ATTACHMENT_TYPES = 4;
        private final int mCallComposerAttachmentsToShow;
        private final boolean mShouldDisallowCall;
        private final boolean mShouldRejectCall;
        private final boolean mShouldScreenCallViaAudioProcessing;
        private final boolean mShouldSilenceCall;
        private final boolean mShouldSkipCallLog;
        private final boolean mShouldSkipNotification;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface CallComposerAttachmentType {
        }

        private CallResponse(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i) {
            if (!z && (z2 || z4 || z5)) {
                throw new java.lang.IllegalStateException("Invalid response state for allowed call.");
            }
            if (z && z6) {
                throw new java.lang.IllegalStateException("Invalid response state for allowed call.");
            }
            this.mShouldDisallowCall = z;
            this.mShouldRejectCall = z2;
            this.mShouldSkipCallLog = z4;
            this.mShouldSkipNotification = z5;
            this.mShouldSilenceCall = z3;
            this.mShouldScreenCallViaAudioProcessing = z6;
            this.mCallComposerAttachmentsToShow = i;
        }

        public boolean getDisallowCall() {
            return this.mShouldDisallowCall;
        }

        public boolean getRejectCall() {
            return this.mShouldRejectCall;
        }

        public boolean getSilenceCall() {
            return this.mShouldSilenceCall;
        }

        public boolean getSkipCallLog() {
            return this.mShouldSkipCallLog;
        }

        public boolean getSkipNotification() {
            return this.mShouldSkipNotification;
        }

        public boolean getShouldScreenCallViaAudioProcessing() {
            return this.mShouldScreenCallViaAudioProcessing;
        }

        public int getCallComposerAttachmentsToShow() {
            return this.mCallComposerAttachmentsToShow;
        }

        public android.telecom.CallScreeningService.ParcelableCallResponse toParcelable() {
            return new android.telecom.CallScreeningService.ParcelableCallResponse(this.mShouldDisallowCall, this.mShouldRejectCall, this.mShouldSilenceCall, this.mShouldSkipCallLog, this.mShouldSkipNotification, this.mShouldScreenCallViaAudioProcessing, this.mCallComposerAttachmentsToShow);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.telecom.CallScreeningService.CallResponse callResponse = (android.telecom.CallScreeningService.CallResponse) obj;
            if (this.mShouldDisallowCall == callResponse.mShouldDisallowCall && this.mShouldRejectCall == callResponse.mShouldRejectCall && this.mShouldSilenceCall == callResponse.mShouldSilenceCall && this.mShouldSkipCallLog == callResponse.mShouldSkipCallLog && this.mShouldSkipNotification == callResponse.mShouldSkipNotification && this.mShouldScreenCallViaAudioProcessing == callResponse.mShouldScreenCallViaAudioProcessing && this.mCallComposerAttachmentsToShow == callResponse.mCallComposerAttachmentsToShow) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mShouldDisallowCall), java.lang.Boolean.valueOf(this.mShouldRejectCall), java.lang.Boolean.valueOf(this.mShouldSilenceCall), java.lang.Boolean.valueOf(this.mShouldSkipCallLog), java.lang.Boolean.valueOf(this.mShouldSkipNotification), java.lang.Boolean.valueOf(this.mShouldScreenCallViaAudioProcessing), java.lang.Integer.valueOf(this.mCallComposerAttachmentsToShow));
        }

        public static class Builder {
            private int mCallComposerAttachmentsToShow = -1;
            private boolean mShouldDisallowCall;
            private boolean mShouldRejectCall;
            private boolean mShouldScreenCallViaAudioProcessing;
            private boolean mShouldSilenceCall;
            private boolean mShouldSkipCallLog;
            private boolean mShouldSkipNotification;

            public android.telecom.CallScreeningService.CallResponse.Builder setDisallowCall(boolean z) {
                this.mShouldDisallowCall = z;
                return this;
            }

            public android.telecom.CallScreeningService.CallResponse.Builder setRejectCall(boolean z) {
                this.mShouldRejectCall = z;
                return this;
            }

            public android.telecom.CallScreeningService.CallResponse.Builder setSilenceCall(boolean z) {
                this.mShouldSilenceCall = z;
                return this;
            }

            public android.telecom.CallScreeningService.CallResponse.Builder setSkipCallLog(boolean z) {
                this.mShouldSkipCallLog = z;
                return this;
            }

            public android.telecom.CallScreeningService.CallResponse.Builder setSkipNotification(boolean z) {
                this.mShouldSkipNotification = z;
                return this;
            }

            @android.annotation.SystemApi
            public android.telecom.CallScreeningService.CallResponse.Builder setShouldScreenCallViaAudioProcessing(boolean z) {
                this.mShouldScreenCallViaAudioProcessing = z;
                return this;
            }

            public android.telecom.CallScreeningService.CallResponse.Builder setCallComposerAttachmentsToShow(int i) {
                if (i < 0) {
                    return this;
                }
                if ((i & 16) != 0) {
                    throw new java.lang.IllegalArgumentException("Attachment types must match the ones defined in CallResponse");
                }
                this.mCallComposerAttachmentsToShow = i;
                return this;
            }

            public android.telecom.CallScreeningService.CallResponse build() {
                return new android.telecom.CallScreeningService.CallResponse(this.mShouldDisallowCall, this.mShouldRejectCall, this.mShouldSilenceCall, this.mShouldSkipCallLog, this.mShouldSkipNotification, this.mShouldScreenCallViaAudioProcessing, this.mCallComposerAttachmentsToShow);
            }
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        android.telecom.Log.v(this, "onBind", new java.lang.Object[0]);
        return new android.telecom.CallScreeningService.CallScreeningBinder();
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        android.telecom.Log.v(this, "onUnbind", new java.lang.Object[0]);
        return false;
    }

    public final void respondToCall(android.telecom.Call.Details details, android.telecom.CallScreeningService.CallResponse callResponse) {
        try {
            this.mCallScreeningAdapter.onScreeningResponse(details.getTelecomCallId(), new android.content.ComponentName(getPackageName(), getClass().getName()), callResponse.toParcelable());
        } catch (android.os.RemoteException e) {
            android.telecom.Log.e(this, e, "Got remote exception when returning response", new java.lang.Object[0]);
        }
    }
}
