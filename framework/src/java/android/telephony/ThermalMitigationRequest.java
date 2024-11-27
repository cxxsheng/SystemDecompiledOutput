package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ThermalMitigationRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ThermalMitigationRequest> CREATOR = new android.os.Parcelable.Creator<android.telephony.ThermalMitigationRequest>() { // from class: android.telephony.ThermalMitigationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ThermalMitigationRequest createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ThermalMitigationRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ThermalMitigationRequest[] newArray(int i) {
            return new android.telephony.ThermalMitigationRequest[i];
        }
    };

    @android.annotation.SystemApi
    public static final int THERMAL_MITIGATION_ACTION_DATA_THROTTLING = 0;

    @android.annotation.SystemApi
    public static final int THERMAL_MITIGATION_ACTION_RADIO_OFF = 2;

    @android.annotation.SystemApi
    public static final int THERMAL_MITIGATION_ACTION_VOICE_ONLY = 1;
    private android.telephony.DataThrottlingRequest mDataThrottlingRequest;
    private int mThermalMitigationAction;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ThermalMitigationAction {
    }

    private ThermalMitigationRequest(int i, android.telephony.DataThrottlingRequest dataThrottlingRequest) {
        this.mThermalMitigationAction = i;
        this.mDataThrottlingRequest = dataThrottlingRequest;
    }

    private ThermalMitigationRequest(android.os.Parcel parcel) {
        this.mThermalMitigationAction = parcel.readInt();
        this.mDataThrottlingRequest = (android.telephony.DataThrottlingRequest) parcel.readParcelable(android.telephony.DataThrottlingRequest.class.getClassLoader(), android.telephony.DataThrottlingRequest.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mThermalMitigationAction);
        parcel.writeParcelable(this.mDataThrottlingRequest, 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "[ThermalMitigationRequest , thermalMitigationAction=" + this.mThermalMitigationAction + ", dataThrottlingRequest=" + this.mDataThrottlingRequest + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public int getThermalMitigationAction() {
        return this.mThermalMitigationAction;
    }

    public android.telephony.DataThrottlingRequest getDataThrottlingRequest() {
        return this.mDataThrottlingRequest;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private android.telephony.DataThrottlingRequest mDataThrottlingRequest;
        private int mThermalMitigationAction = -1;

        public android.telephony.ThermalMitigationRequest.Builder setThermalMitigationAction(int i) {
            this.mThermalMitigationAction = i;
            return this;
        }

        public android.telephony.ThermalMitigationRequest.Builder setDataThrottlingRequest(android.telephony.DataThrottlingRequest dataThrottlingRequest) {
            this.mDataThrottlingRequest = dataThrottlingRequest;
            return this;
        }

        public android.telephony.ThermalMitigationRequest build() {
            if (this.mThermalMitigationAction < 0) {
                throw new java.lang.IllegalArgumentException("thermalMitigationAction was  not set");
            }
            if (this.mThermalMitigationAction == 0) {
                if (this.mDataThrottlingRequest == null) {
                    throw new java.lang.IllegalArgumentException("dataThrottlingRequest  cannot be null for THERMAL_MITIGATION_ACTION_DATA_THROTTLING");
                }
            } else if (this.mDataThrottlingRequest != null) {
                throw new java.lang.IllegalArgumentException("dataThrottlingRequest must be null for THERMAL_MITIGATION_ACTION_VOICE_ONLY and THERMAL_MITIGATION_ACTION_RADIO_OFF");
            }
            return new android.telephony.ThermalMitigationRequest(this.mThermalMitigationAction, this.mDataThrottlingRequest);
        }
    }
}
