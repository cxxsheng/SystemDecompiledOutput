package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class DataThrottlingRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.DataThrottlingRequest> CREATOR = new android.os.Parcelable.Creator<android.telephony.DataThrottlingRequest>() { // from class: android.telephony.DataThrottlingRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.DataThrottlingRequest createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.DataThrottlingRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.DataThrottlingRequest[] newArray(int i) {
            return new android.telephony.DataThrottlingRequest[i];
        }
    };

    @android.annotation.SystemApi
    public static final int DATA_THROTTLING_ACTION_HOLD = 3;

    @android.annotation.SystemApi
    public static final int DATA_THROTTLING_ACTION_NO_DATA_THROTTLING = 0;

    @android.annotation.SystemApi
    public static final int DATA_THROTTLING_ACTION_THROTTLE_PRIMARY_CARRIER = 2;

    @android.annotation.SystemApi
    public static final int DATA_THROTTLING_ACTION_THROTTLE_SECONDARY_CARRIER = 1;
    private long mCompletionDurationMillis;
    private int mDataThrottlingAction;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataThrottlingAction {
    }

    private DataThrottlingRequest(int i, long j) {
        this.mDataThrottlingAction = i;
        this.mCompletionDurationMillis = j;
    }

    private DataThrottlingRequest(android.os.Parcel parcel) {
        this.mDataThrottlingAction = parcel.readInt();
        this.mCompletionDurationMillis = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDataThrottlingAction);
        parcel.writeLong(this.mCompletionDurationMillis);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "[DataThrottlingRequest , DataThrottlingAction=" + this.mDataThrottlingAction + ", completionDurationMillis=" + this.mCompletionDurationMillis + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public int getDataThrottlingAction() {
        return this.mDataThrottlingAction;
    }

    public long getCompletionDurationMillis() {
        return this.mCompletionDurationMillis;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private long mCompletionDurationMillis;
        private int mDataThrottlingAction;

        public android.telephony.DataThrottlingRequest.Builder setDataThrottlingAction(int i) {
            this.mDataThrottlingAction = i;
            return this;
        }

        public android.telephony.DataThrottlingRequest.Builder setCompletionDurationMillis(long j) {
            this.mCompletionDurationMillis = j;
            return this;
        }

        public android.telephony.DataThrottlingRequest build() {
            if (this.mCompletionDurationMillis < 0) {
                throw new java.lang.IllegalArgumentException("completionDurationMillis cannot be a negative number");
            }
            if (this.mDataThrottlingAction == 3 && this.mCompletionDurationMillis != 0) {
                throw new java.lang.IllegalArgumentException("completionDurationMillis must be 0 for DataThrottlingRequest.DATA_THROTTLING_ACTION_HOLD");
            }
            return new android.telephony.DataThrottlingRequest(this.mDataThrottlingAction, this.mCompletionDurationMillis);
        }
    }
}
