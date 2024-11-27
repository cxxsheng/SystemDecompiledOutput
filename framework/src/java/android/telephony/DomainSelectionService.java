package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class DomainSelectionService extends android.app.Service {
    private static final java.lang.String LOG_TAG = "DomainSelectionService";
    public static final int SCAN_TYPE_FULL_SERVICE = 2;
    public static final int SCAN_TYPE_LIMITED_SERVICE = 1;
    public static final int SCAN_TYPE_NO_PREFERENCE = 0;
    public static final int SELECTOR_TYPE_CALLING = 1;
    public static final int SELECTOR_TYPE_SMS = 2;
    public static final java.lang.String SERVICE_INTERFACE = "android.telephony.DomainSelectionService";
    private java.util.concurrent.Executor mExecutor;
    private final java.lang.Object mExecutorLock = new java.lang.Object();
    private final android.os.IBinder mDomainSelectionServiceController = new android.telephony.DomainSelectionService.AnonymousClass1();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EmergencyScanType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SelectorType {
    }

    public abstract void onDomainSelection(android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes, android.telephony.TransportSelectorCallback transportSelectorCallback);

    public static final class SelectionAttributes implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.telephony.DomainSelectionService.SelectionAttributes> CREATOR = new android.os.Parcelable.Creator<android.telephony.DomainSelectionService.SelectionAttributes>() { // from class: android.telephony.DomainSelectionService.SelectionAttributes.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.DomainSelectionService.SelectionAttributes createFromParcel(android.os.Parcel parcel) {
                return new android.telephony.DomainSelectionService.SelectionAttributes(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.DomainSelectionService.SelectionAttributes[] newArray(int i) {
                return new android.telephony.DomainSelectionService.SelectionAttributes[i];
            }
        };
        private static final java.lang.String TAG = "SelectionAttributes";
        private android.net.Uri mAddress;
        private java.lang.String mCallId;
        private int mCause;
        private android.telephony.EmergencyRegistrationResult mEmergencyRegistrationResult;
        private android.telephony.ims.ImsReasonInfo mImsReasonInfo;
        private boolean mIsEmergency;
        private boolean mIsExitedFromAirplaneMode;
        private boolean mIsTestEmergencyNumber;
        private boolean mIsVideoCall;
        private int mSelectorType;
        private int mSlotIndex;
        private int mSubId;

        private SelectionAttributes(int i, int i2, java.lang.String str, android.net.Uri uri, int i3, boolean z, boolean z2, boolean z3, boolean z4, android.telephony.ims.ImsReasonInfo imsReasonInfo, int i4, android.telephony.EmergencyRegistrationResult emergencyRegistrationResult) {
            this.mSlotIndex = i;
            this.mSubId = i2;
            this.mCallId = str;
            this.mAddress = uri;
            this.mSelectorType = i3;
            this.mIsVideoCall = z;
            this.mIsEmergency = z2;
            this.mIsTestEmergencyNumber = z3;
            this.mIsExitedFromAirplaneMode = z4;
            this.mImsReasonInfo = imsReasonInfo;
            this.mCause = i4;
            this.mEmergencyRegistrationResult = emergencyRegistrationResult;
        }

        public SelectionAttributes(android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes) {
            this.mSlotIndex = selectionAttributes.mSlotIndex;
            this.mSubId = selectionAttributes.mSubId;
            this.mCallId = selectionAttributes.mCallId;
            this.mAddress = selectionAttributes.mAddress;
            this.mSelectorType = selectionAttributes.mSelectorType;
            this.mIsEmergency = selectionAttributes.mIsEmergency;
            this.mIsTestEmergencyNumber = selectionAttributes.mIsTestEmergencyNumber;
            this.mIsExitedFromAirplaneMode = selectionAttributes.mIsExitedFromAirplaneMode;
            this.mImsReasonInfo = selectionAttributes.mImsReasonInfo;
            this.mCause = selectionAttributes.mCause;
            this.mEmergencyRegistrationResult = selectionAttributes.mEmergencyRegistrationResult;
        }

        private SelectionAttributes(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }

        public int getSlotIndex() {
            return this.mSlotIndex;
        }

        public int getSubscriptionId() {
            return this.mSubId;
        }

        public java.lang.String getCallId() {
            return this.mCallId;
        }

        public android.net.Uri getAddress() {
            return this.mAddress;
        }

        public int getSelectorType() {
            return this.mSelectorType;
        }

        public boolean isVideoCall() {
            return this.mIsVideoCall;
        }

        public boolean isEmergency() {
            return this.mIsEmergency;
        }

        public boolean isTestEmergencyNumber() {
            return this.mIsTestEmergencyNumber;
        }

        public boolean isExitedFromAirplaneMode() {
            return this.mIsExitedFromAirplaneMode;
        }

        public android.telephony.ims.ImsReasonInfo getPsDisconnectCause() {
            return this.mImsReasonInfo;
        }

        public int getCsDisconnectCause() {
            return this.mCause;
        }

        public android.telephony.EmergencyRegistrationResult getEmergencyRegistrationResult() {
            return this.mEmergencyRegistrationResult;
        }

        public java.lang.String toString() {
            return "{ slotIndex=" + this.mSlotIndex + ", subId=" + this.mSubId + ", callId=" + this.mCallId + ", address=" + (android.os.Build.IS_DEBUGGABLE ? this.mAddress : "***") + ", type=" + this.mSelectorType + ", videoCall=" + this.mIsVideoCall + ", emergency=" + this.mIsEmergency + ", isTest=" + this.mIsTestEmergencyNumber + ", airplaneMode=" + this.mIsExitedFromAirplaneMode + ", reasonInfo=" + this.mImsReasonInfo + ", cause=" + this.mCause + ", regResult=" + this.mEmergencyRegistrationResult + " }";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes = (android.telephony.DomainSelectionService.SelectionAttributes) obj;
            if (this.mSlotIndex == selectionAttributes.mSlotIndex && this.mSubId == selectionAttributes.mSubId && android.text.TextUtils.equals(this.mCallId, selectionAttributes.mCallId) && equalsHandlesNulls(this.mAddress, selectionAttributes.mAddress) && this.mSelectorType == selectionAttributes.mSelectorType && this.mIsVideoCall == selectionAttributes.mIsVideoCall && this.mIsEmergency == selectionAttributes.mIsEmergency && this.mIsTestEmergencyNumber == selectionAttributes.mIsTestEmergencyNumber && this.mIsExitedFromAirplaneMode == selectionAttributes.mIsExitedFromAirplaneMode && equalsHandlesNulls(this.mImsReasonInfo, selectionAttributes.mImsReasonInfo) && this.mCause == selectionAttributes.mCause && equalsHandlesNulls(this.mEmergencyRegistrationResult, selectionAttributes.mEmergencyRegistrationResult)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mCallId, this.mAddress, this.mImsReasonInfo, java.lang.Boolean.valueOf(this.mIsVideoCall), java.lang.Boolean.valueOf(this.mIsEmergency), java.lang.Boolean.valueOf(this.mIsTestEmergencyNumber), java.lang.Boolean.valueOf(this.mIsExitedFromAirplaneMode), this.mEmergencyRegistrationResult, java.lang.Integer.valueOf(this.mSlotIndex), java.lang.Integer.valueOf(this.mSubId), java.lang.Integer.valueOf(this.mSelectorType), java.lang.Integer.valueOf(this.mCause));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mSlotIndex);
            parcel.writeInt(this.mSubId);
            parcel.writeString8(this.mCallId);
            parcel.writeParcelable(this.mAddress, 0);
            parcel.writeInt(this.mSelectorType);
            parcel.writeBoolean(this.mIsVideoCall);
            parcel.writeBoolean(this.mIsEmergency);
            parcel.writeBoolean(this.mIsTestEmergencyNumber);
            parcel.writeBoolean(this.mIsExitedFromAirplaneMode);
            parcel.writeParcelable(this.mImsReasonInfo, 0);
            parcel.writeInt(this.mCause);
            parcel.writeParcelable(this.mEmergencyRegistrationResult, 0);
        }

        private void readFromParcel(android.os.Parcel parcel) {
            this.mSlotIndex = parcel.readInt();
            this.mSubId = parcel.readInt();
            this.mCallId = parcel.readString8();
            this.mAddress = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
            this.mSelectorType = parcel.readInt();
            this.mIsVideoCall = parcel.readBoolean();
            this.mIsEmergency = parcel.readBoolean();
            this.mIsTestEmergencyNumber = parcel.readBoolean();
            this.mIsExitedFromAirplaneMode = parcel.readBoolean();
            this.mImsReasonInfo = (android.telephony.ims.ImsReasonInfo) parcel.readParcelable(android.telephony.ims.ImsReasonInfo.class.getClassLoader(), android.telephony.ims.ImsReasonInfo.class);
            this.mCause = parcel.readInt();
            this.mEmergencyRegistrationResult = (android.telephony.EmergencyRegistrationResult) parcel.readParcelable(android.telephony.EmergencyRegistrationResult.class.getClassLoader(), android.telephony.EmergencyRegistrationResult.class);
        }

        private static boolean equalsHandlesNulls(java.lang.Object obj, java.lang.Object obj2) {
            return obj == null ? obj2 == null : obj.equals(obj2);
        }

        public static final class Builder {
            private android.net.Uri mAddress;
            private java.lang.String mCallId;
            private int mCause;
            private android.telephony.EmergencyRegistrationResult mEmergencyRegistrationResult;
            private android.telephony.ims.ImsReasonInfo mImsReasonInfo;
            private boolean mIsEmergency;
            private boolean mIsExitedFromAirplaneMode;
            private boolean mIsTestEmergencyNumber;
            private boolean mIsVideoCall;
            private final int mSelectorType;
            private final int mSlotIndex;
            private final int mSubId;

            public Builder(int i, int i2, int i3) {
                this.mSlotIndex = i;
                this.mSubId = i2;
                this.mSelectorType = i3;
            }

            public android.telephony.DomainSelectionService.SelectionAttributes.Builder setCallId(java.lang.String str) {
                this.mCallId = str;
                return this;
            }

            public android.telephony.DomainSelectionService.SelectionAttributes.Builder setAddress(android.net.Uri uri) {
                this.mAddress = uri;
                return this;
            }

            public android.telephony.DomainSelectionService.SelectionAttributes.Builder setVideoCall(boolean z) {
                this.mIsVideoCall = z;
                return this;
            }

            public android.telephony.DomainSelectionService.SelectionAttributes.Builder setEmergency(boolean z) {
                this.mIsEmergency = z;
                return this;
            }

            public android.telephony.DomainSelectionService.SelectionAttributes.Builder setTestEmergencyNumber(boolean z) {
                this.mIsTestEmergencyNumber = z;
                return this;
            }

            public android.telephony.DomainSelectionService.SelectionAttributes.Builder setExitedFromAirplaneMode(boolean z) {
                this.mIsExitedFromAirplaneMode = z;
                return this;
            }

            public android.telephony.DomainSelectionService.SelectionAttributes.Builder setPsDisconnectCause(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
                this.mImsReasonInfo = imsReasonInfo;
                return this;
            }

            public android.telephony.DomainSelectionService.SelectionAttributes.Builder setCsDisconnectCause(int i) {
                this.mCause = i;
                return this;
            }

            public android.telephony.DomainSelectionService.SelectionAttributes.Builder setEmergencyRegistrationResult(android.telephony.EmergencyRegistrationResult emergencyRegistrationResult) {
                this.mEmergencyRegistrationResult = emergencyRegistrationResult;
                return this;
            }

            public android.telephony.DomainSelectionService.SelectionAttributes build() {
                return new android.telephony.DomainSelectionService.SelectionAttributes(this.mSlotIndex, this.mSubId, this.mCallId, this.mAddress, this.mSelectorType, this.mIsVideoCall, this.mIsEmergency, this.mIsTestEmergencyNumber, this.mIsExitedFromAirplaneMode, this.mImsReasonInfo, this.mCause, this.mEmergencyRegistrationResult);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class TransportSelectorCallbackWrapper implements android.telephony.TransportSelectorCallback {
        private static final java.lang.String TAG = "TransportSelectorCallbackWrapper";
        private final com.android.internal.telephony.ITransportSelectorCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private android.telephony.DomainSelectionService.TransportSelectorCallbackWrapper.ITransportSelectorResultCallbackAdapter mResultCallback;
        private android.telephony.DomainSelectionService.DomainSelectorWrapper mSelectorWrapper;

        TransportSelectorCallbackWrapper(com.android.internal.telephony.ITransportSelectorCallback iTransportSelectorCallback, java.util.concurrent.Executor executor) {
            this.mCallback = iTransportSelectorCallback;
            this.mExecutor = executor;
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onCreated(android.telephony.DomainSelector domainSelector) {
            try {
                this.mSelectorWrapper = android.telephony.DomainSelectionService.this.new DomainSelectorWrapper(domainSelector, this.mExecutor);
                this.mCallback.onCreated(this.mSelectorWrapper.getCallbackBinder());
            } catch (java.lang.Exception e) {
                com.android.telephony.Rlog.e(TAG, "onCreated e=" + e);
            }
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onWlanSelected(boolean z) {
            try {
                this.mCallback.onWlanSelected(z);
            } catch (java.lang.Exception e) {
                com.android.telephony.Rlog.e(TAG, "onWlanSelected e=" + e);
            }
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onWwanSelected(final java.util.function.Consumer<android.telephony.WwanSelectorCallback> consumer) {
            try {
                this.mResultCallback = new android.telephony.DomainSelectionService.TransportSelectorCallbackWrapper.ITransportSelectorResultCallbackAdapter(consumer, this.mExecutor);
                this.mCallback.onWwanSelectedAsync(this.mResultCallback);
            } catch (java.lang.Exception e) {
                com.android.telephony.Rlog.e(TAG, "onWwanSelected e=" + e);
                android.telephony.DomainSelectionService.this.executeMethodAsyncNoException(this.mExecutor, new java.lang.Runnable() { // from class: android.telephony.DomainSelectionService$TransportSelectorCallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(null);
                    }
                }, TAG, "onWwanSelectedAsync-Exception");
            }
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onSelectionTerminated(int i) {
            try {
                this.mCallback.onSelectionTerminated(i);
                this.mSelectorWrapper = null;
            } catch (java.lang.Exception e) {
                com.android.telephony.Rlog.e(TAG, "onSelectionTerminated e=" + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class ITransportSelectorResultCallbackAdapter extends com.android.internal.telephony.ITransportSelectorResultCallback.Stub {
            private final java.util.function.Consumer<android.telephony.WwanSelectorCallback> mConsumer;
            private final java.util.concurrent.Executor mExecutor;

            ITransportSelectorResultCallbackAdapter(java.util.function.Consumer<android.telephony.WwanSelectorCallback> consumer, java.util.concurrent.Executor executor) {
                this.mConsumer = consumer;
                this.mExecutor = executor;
            }

            @Override // com.android.internal.telephony.ITransportSelectorResultCallback
            public void onCompleted(com.android.internal.telephony.IWwanSelectorCallback iWwanSelectorCallback) {
                if (this.mConsumer == null) {
                    return;
                }
                final android.telephony.DomainSelectionService.WwanSelectorCallbackWrapper wwanSelectorCallbackWrapper = android.telephony.DomainSelectionService.this.new WwanSelectorCallbackWrapper(iWwanSelectorCallback, this.mExecutor);
                android.telephony.DomainSelectionService.this.executeMethodAsyncNoException(this.mExecutor, new java.lang.Runnable() { // from class: android.telephony.DomainSelectionService$TransportSelectorCallbackWrapper$ITransportSelectorResultCallbackAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.DomainSelectionService.TransportSelectorCallbackWrapper.ITransportSelectorResultCallbackAdapter.this.lambda$onCompleted$0(wwanSelectorCallbackWrapper);
                    }
                }, android.telephony.DomainSelectionService.TransportSelectorCallbackWrapper.TAG, "onWwanSelectedAsync-Completed");
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onCompleted$0(android.telephony.WwanSelectorCallback wwanSelectorCallback) {
                this.mConsumer.accept(wwanSelectorCallback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DomainSelectorWrapper {
        private static final java.lang.String TAG = "DomainSelectorWrapper";
        private com.android.internal.telephony.IDomainSelector mCallbackBinder;

        DomainSelectorWrapper(android.telephony.DomainSelector domainSelector, java.util.concurrent.Executor executor) {
            this.mCallbackBinder = new android.telephony.DomainSelectionService.DomainSelectorWrapper.IDomainSelectorAdapter(domainSelector, executor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        class IDomainSelectorAdapter extends com.android.internal.telephony.IDomainSelector.Stub {
            private final java.lang.ref.WeakReference<android.telephony.DomainSelector> mDomainSelectorWeakRef;
            private final java.util.concurrent.Executor mExecutor;

            IDomainSelectorAdapter(android.telephony.DomainSelector domainSelector, java.util.concurrent.Executor executor) {
                this.mDomainSelectorWeakRef = new java.lang.ref.WeakReference<>(domainSelector);
                this.mExecutor = executor;
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void reselectDomain(final android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes) {
                final android.telephony.DomainSelector domainSelector = this.mDomainSelectorWeakRef.get();
                if (domainSelector == null) {
                    return;
                }
                android.telephony.DomainSelectionService.this.executeMethodAsyncNoException(this.mExecutor, new java.lang.Runnable() { // from class: android.telephony.DomainSelectionService$DomainSelectorWrapper$IDomainSelectorAdapter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.DomainSelector.this.reselectDomain(selectionAttributes);
                    }
                }, android.telephony.DomainSelectionService.DomainSelectorWrapper.TAG, "reselectDomain");
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void finishSelection() {
                final android.telephony.DomainSelector domainSelector = this.mDomainSelectorWeakRef.get();
                if (domainSelector == null) {
                    return;
                }
                android.telephony.DomainSelectionService.this.executeMethodAsyncNoException(this.mExecutor, new java.lang.Runnable() { // from class: android.telephony.DomainSelectionService$DomainSelectorWrapper$IDomainSelectorAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.DomainSelector.this.finishSelection();
                    }
                }, android.telephony.DomainSelectionService.DomainSelectorWrapper.TAG, "finishSelection");
            }
        }

        public com.android.internal.telephony.IDomainSelector getCallbackBinder() {
            return this.mCallbackBinder;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class WwanSelectorCallbackWrapper implements android.telephony.WwanSelectorCallback, android.os.CancellationSignal.OnCancelListener {
        private static final java.lang.String TAG = "WwanSelectorCallbackWrapper";
        private final com.android.internal.telephony.IWwanSelectorCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private android.telephony.DomainSelectionService.WwanSelectorCallbackWrapper.IWwanSelectorResultCallbackAdapter mResultCallback;

        WwanSelectorCallbackWrapper(com.android.internal.telephony.IWwanSelectorCallback iWwanSelectorCallback, java.util.concurrent.Executor executor) {
            this.mCallback = iWwanSelectorCallback;
            this.mExecutor = executor;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            try {
                this.mCallback.onCancel();
            } catch (java.lang.Exception e) {
                com.android.telephony.Rlog.e(TAG, "onCancel e=" + e);
            }
        }

        @Override // android.telephony.WwanSelectorCallback
        public void onRequestEmergencyNetworkScan(java.util.List<java.lang.Integer> list, int i, boolean z, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<android.telephony.EmergencyRegistrationResult> consumer) {
            if (cancellationSignal != null) {
                try {
                    cancellationSignal.setOnCancelListener(this);
                } catch (java.lang.Exception e) {
                    com.android.telephony.Rlog.e(TAG, "onRequestEmergencyNetworkScan e=" + e);
                    return;
                }
            }
            this.mResultCallback = new android.telephony.DomainSelectionService.WwanSelectorCallbackWrapper.IWwanSelectorResultCallbackAdapter(consumer, this.mExecutor);
            this.mCallback.onRequestEmergencyNetworkScan(list.stream().mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray(), i, z, this.mResultCallback);
        }

        @Override // android.telephony.WwanSelectorCallback
        public void onDomainSelected(int i, boolean z) {
            try {
                this.mCallback.onDomainSelected(i, z);
            } catch (java.lang.Exception e) {
                com.android.telephony.Rlog.e(TAG, "onDomainSelected e=" + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class IWwanSelectorResultCallbackAdapter extends com.android.internal.telephony.IWwanSelectorResultCallback.Stub {
            private final java.util.function.Consumer<android.telephony.EmergencyRegistrationResult> mConsumer;
            private final java.util.concurrent.Executor mExecutor;

            IWwanSelectorResultCallbackAdapter(java.util.function.Consumer<android.telephony.EmergencyRegistrationResult> consumer, java.util.concurrent.Executor executor) {
                this.mConsumer = consumer;
                this.mExecutor = executor;
            }

            @Override // com.android.internal.telephony.IWwanSelectorResultCallback
            public void onComplete(final android.telephony.EmergencyRegistrationResult emergencyRegistrationResult) {
                if (this.mConsumer == null) {
                    return;
                }
                android.telephony.DomainSelectionService.this.executeMethodAsyncNoException(this.mExecutor, new java.lang.Runnable() { // from class: android.telephony.DomainSelectionService$WwanSelectorCallbackWrapper$IWwanSelectorResultCallbackAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.DomainSelectionService.WwanSelectorCallbackWrapper.IWwanSelectorResultCallbackAdapter.this.lambda$onComplete$0(emergencyRegistrationResult);
                    }
                }, android.telephony.DomainSelectionService.WwanSelectorCallbackWrapper.TAG, "onScanComplete");
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onComplete$0(android.telephony.EmergencyRegistrationResult emergencyRegistrationResult) {
                this.mConsumer.accept(emergencyRegistrationResult);
            }
        }
    }

    public void onServiceStateUpdated(int i, int i2, android.telephony.ServiceState serviceState) {
    }

    public void onBarringInfoUpdated(int i, int i2, android.telephony.BarringInfo barringInfo) {
    }

    /* renamed from: android.telephony.DomainSelectionService$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.telephony.IDomainSelectionServiceController.Stub {
        AnonymousClass1() {
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void selectDomain(final android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes, final com.android.internal.telephony.ITransportSelectorCallback iTransportSelectorCallback) throws android.os.RemoteException {
            android.telephony.DomainSelectionService.executeMethodAsync(android.telephony.DomainSelectionService.this.getCachedExecutor(), new java.lang.Runnable() { // from class: android.telephony.DomainSelectionService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.DomainSelectionService.AnonymousClass1.this.lambda$selectDomain$0(selectionAttributes, iTransportSelectorCallback);
                }
            }, android.telephony.DomainSelectionService.LOG_TAG, "onDomainSelection");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$selectDomain$0(android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes, com.android.internal.telephony.ITransportSelectorCallback iTransportSelectorCallback) {
            android.telephony.DomainSelectionService.this.onDomainSelection(selectionAttributes, android.telephony.DomainSelectionService.this.new TransportSelectorCallbackWrapper(iTransportSelectorCallback, android.telephony.DomainSelectionService.this.getCachedExecutor()));
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateServiceState(final int i, final int i2, final android.telephony.ServiceState serviceState) {
            android.telephony.DomainSelectionService.this.executeMethodAsyncNoException(android.telephony.DomainSelectionService.this.getCachedExecutor(), new java.lang.Runnable() { // from class: android.telephony.DomainSelectionService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.DomainSelectionService.AnonymousClass1.this.lambda$updateServiceState$1(i, i2, serviceState);
                }
            }, android.telephony.DomainSelectionService.LOG_TAG, "onServiceStateUpdated");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateServiceState$1(int i, int i2, android.telephony.ServiceState serviceState) {
            android.telephony.DomainSelectionService.this.onServiceStateUpdated(i, i2, serviceState);
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateBarringInfo(final int i, final int i2, final android.telephony.BarringInfo barringInfo) {
            android.telephony.DomainSelectionService.this.executeMethodAsyncNoException(android.telephony.DomainSelectionService.this.getCachedExecutor(), new java.lang.Runnable() { // from class: android.telephony.DomainSelectionService$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.DomainSelectionService.AnonymousClass1.this.lambda$updateBarringInfo$2(i, i2, barringInfo);
                }
            }, android.telephony.DomainSelectionService.LOG_TAG, "onBarringInfoUpdated");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateBarringInfo$2(int i, int i2, android.telephony.BarringInfo barringInfo) {
            android.telephony.DomainSelectionService.this.onBarringInfoUpdated(i, i2, barringInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void executeMethodAsync(java.util.concurrent.Executor executor, final java.lang.Runnable runnable, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        try {
            java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.DomainSelectionService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                }
            }, executor).join();
        } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
            com.android.telephony.Rlog.w(str, "Binder - " + str2 + " exception: " + e.getMessage());
            throw new android.os.RemoteException(e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeMethodAsyncNoException(java.util.concurrent.Executor executor, final java.lang.Runnable runnable, java.lang.String str, java.lang.String str2) {
        try {
            java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.DomainSelectionService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                }
            }, executor);
        } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
            com.android.telephony.Rlog.w(str, "Binder - " + str2 + " exception: " + e.getMessage());
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (intent == null || !SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        android.util.Log.i(LOG_TAG, "DomainSelectionService Bound.");
        return this.mDomainSelectionServiceController;
    }

    public java.util.concurrent.Executor getCreateExecutor() {
        return new android.app.PendingIntent$$ExternalSyntheticLambda0();
    }

    public final java.util.concurrent.Executor getCachedExecutor() {
        java.util.concurrent.Executor executor;
        synchronized (this.mExecutorLock) {
            if (this.mExecutor == null) {
                java.util.concurrent.Executor createExecutor = getCreateExecutor();
                if (createExecutor == null) {
                    createExecutor = new android.app.PendingIntent$$ExternalSyntheticLambda0();
                }
                this.mExecutor = createExecutor;
            }
            executor = this.mExecutor;
        }
        return executor;
    }

    public static java.lang.String getDomainName(int i) {
        return android.telephony.NetworkRegistrationInfo.domainToString(i);
    }
}
