package android.telephony;

/* loaded from: classes3.dex */
public final class SignalStrengthUpdateRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.SignalStrengthUpdateRequest> CREATOR = new android.os.Parcelable.Creator<android.telephony.SignalStrengthUpdateRequest>() { // from class: android.telephony.SignalStrengthUpdateRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SignalStrengthUpdateRequest createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.SignalStrengthUpdateRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SignalStrengthUpdateRequest[] newArray(int i) {
            return new android.telephony.SignalStrengthUpdateRequest[i];
        }
    };
    private final boolean mIsReportingRequestedWhileIdle;
    private final boolean mIsSystemThresholdReportingRequestedWhileIdle;
    private final android.os.IBinder mLiveToken;
    private final java.util.List<android.telephony.SignalThresholdInfo> mSignalThresholdInfos;

    private SignalStrengthUpdateRequest(java.util.List<android.telephony.SignalThresholdInfo> list, boolean z, boolean z2) {
        validate(list, z2);
        this.mSignalThresholdInfos = list;
        this.mIsReportingRequestedWhileIdle = z;
        this.mIsSystemThresholdReportingRequestedWhileIdle = z2;
        this.mLiveToken = new android.os.Binder();
    }

    public static final class Builder {
        private java.util.List<android.telephony.SignalThresholdInfo> mSignalThresholdInfos = null;
        private boolean mIsReportingRequestedWhileIdle = false;
        private boolean mIsSystemThresholdReportingRequestedWhileIdle = false;

        public android.telephony.SignalStrengthUpdateRequest.Builder setSignalThresholdInfos(java.util.Collection<android.telephony.SignalThresholdInfo> collection) {
            java.util.Objects.requireNonNull(collection, "SignalThresholdInfo collection must not be null");
            java.util.Iterator<android.telephony.SignalThresholdInfo> it = collection.iterator();
            while (it.hasNext()) {
                java.util.Objects.requireNonNull(it.next(), "SignalThresholdInfo in the collection must not be null");
            }
            this.mSignalThresholdInfos = new java.util.ArrayList(collection);
            this.mSignalThresholdInfos.sort(java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: android.telephony.SignalStrengthUpdateRequest$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    return ((android.telephony.SignalThresholdInfo) obj).getRadioAccessNetworkType();
                }
            }).thenComparing(new java.util.function.Function() { // from class: android.telephony.SignalStrengthUpdateRequest$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return java.lang.Integer.valueOf(((android.telephony.SignalThresholdInfo) obj).getSignalMeasurementType());
                }
            }));
            return this;
        }

        public android.telephony.SignalStrengthUpdateRequest.Builder setReportingRequestedWhileIdle(boolean z) {
            this.mIsReportingRequestedWhileIdle = z;
            return this;
        }

        @android.annotation.SystemApi
        public android.telephony.SignalStrengthUpdateRequest.Builder setSystemThresholdReportingRequestedWhileIdle(boolean z) {
            this.mIsSystemThresholdReportingRequestedWhileIdle = z;
            return this;
        }

        public android.telephony.SignalStrengthUpdateRequest build() {
            return new android.telephony.SignalStrengthUpdateRequest(this.mSignalThresholdInfos, this.mIsReportingRequestedWhileIdle, this.mIsSystemThresholdReportingRequestedWhileIdle);
        }
    }

    private SignalStrengthUpdateRequest(android.os.Parcel parcel) {
        this.mSignalThresholdInfos = parcel.createTypedArrayList(android.telephony.SignalThresholdInfo.CREATOR);
        this.mIsReportingRequestedWhileIdle = parcel.readBoolean();
        this.mIsSystemThresholdReportingRequestedWhileIdle = parcel.readBoolean();
        this.mLiveToken = parcel.readStrongBinder();
    }

    public java.util.Collection<android.telephony.SignalThresholdInfo> getSignalThresholdInfos() {
        return java.util.Collections.unmodifiableList(this.mSignalThresholdInfos);
    }

    public boolean isReportingRequestedWhileIdle() {
        return this.mIsReportingRequestedWhileIdle;
    }

    @android.annotation.SystemApi
    public boolean isSystemThresholdReportingRequestedWhileIdle() {
        return this.mIsSystemThresholdReportingRequestedWhileIdle;
    }

    public android.os.IBinder getLiveToken() {
        return this.mLiveToken;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mSignalThresholdInfos);
        parcel.writeBoolean(this.mIsReportingRequestedWhileIdle);
        parcel.writeBoolean(this.mIsSystemThresholdReportingRequestedWhileIdle);
        parcel.writeStrongBinder(this.mLiveToken);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.SignalStrengthUpdateRequest)) {
            return false;
        }
        android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest = (android.telephony.SignalStrengthUpdateRequest) obj;
        return this.mSignalThresholdInfos.equals(signalStrengthUpdateRequest.mSignalThresholdInfos) && this.mIsReportingRequestedWhileIdle == signalStrengthUpdateRequest.mIsReportingRequestedWhileIdle && this.mIsSystemThresholdReportingRequestedWhileIdle == signalStrengthUpdateRequest.mIsSystemThresholdReportingRequestedWhileIdle;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mSignalThresholdInfos, java.lang.Boolean.valueOf(this.mIsReportingRequestedWhileIdle), java.lang.Boolean.valueOf(this.mIsSystemThresholdReportingRequestedWhileIdle));
    }

    public java.lang.String toString() {
        return "SignalStrengthUpdateRequest{mSignalThresholdInfos=" + this.mSignalThresholdInfos + " mIsReportingRequestedWhileIdle=" + this.mIsReportingRequestedWhileIdle + " mIsSystemThresholdReportingRequestedWhileIdle=" + this.mIsSystemThresholdReportingRequestedWhileIdle + " mLiveToken" + this.mLiveToken + "}";
    }

    private static void validate(java.util.Collection<android.telephony.SignalThresholdInfo> collection, boolean z) {
        if (collection == null || (collection.isEmpty() && !z)) {
            throw new java.lang.IllegalArgumentException("SignalThresholdInfo collection is null or empty");
        }
        java.util.HashMap hashMap = new java.util.HashMap(collection.size());
        for (android.telephony.SignalThresholdInfo signalThresholdInfo : collection) {
            int radioAccessNetworkType = signalThresholdInfo.getRadioAccessNetworkType();
            int signalMeasurementType = signalThresholdInfo.getSignalMeasurementType();
            hashMap.putIfAbsent(java.lang.Integer.valueOf(radioAccessNetworkType), new java.util.HashSet());
            if (!((java.util.Set) hashMap.get(java.lang.Integer.valueOf(radioAccessNetworkType))).add(java.lang.Integer.valueOf(signalMeasurementType))) {
                throw new java.lang.IllegalArgumentException("SignalMeasurementType " + signalMeasurementType + " for RAN " + radioAccessNetworkType + " is not unique");
            }
        }
    }
}
