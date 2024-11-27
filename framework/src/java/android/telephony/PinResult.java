package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class PinResult implements android.os.Parcelable {
    public static final int PIN_RESULT_TYPE_ABORTED = 3;
    public static final int PIN_RESULT_TYPE_FAILURE = 2;
    public static final int PIN_RESULT_TYPE_INCORRECT = 1;
    public static final int PIN_RESULT_TYPE_SUCCESS = 0;
    private final int mAttemptsRemaining;
    private final int mResult;
    private static final android.telephony.PinResult sFailedResult = new android.telephony.PinResult(2, -1);
    public static final android.os.Parcelable.Creator<android.telephony.PinResult> CREATOR = new android.os.Parcelable.Creator<android.telephony.PinResult>() { // from class: android.telephony.PinResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.PinResult createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.PinResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.PinResult[] newArray(int i) {
            return new android.telephony.PinResult[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PinResultType {
    }

    public int getResult() {
        return this.mResult;
    }

    public int getAttemptsRemaining() {
        return this.mAttemptsRemaining;
    }

    public static android.telephony.PinResult getDefaultFailedResult() {
        return sFailedResult;
    }

    public PinResult(int i, int i2) {
        this.mResult = i;
        this.mAttemptsRemaining = i2;
    }

    private PinResult(android.os.Parcel parcel) {
        this.mResult = parcel.readInt();
        this.mAttemptsRemaining = parcel.readInt();
    }

    public java.lang.String toString() {
        return "result: " + getResult() + ", attempts remaining: " + getAttemptsRemaining();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mResult);
        parcel.writeInt(this.mAttemptsRemaining);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mAttemptsRemaining), java.lang.Integer.valueOf(this.mResult));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.PinResult pinResult = (android.telephony.PinResult) obj;
        if (this.mResult == pinResult.mResult && this.mAttemptsRemaining == pinResult.mAttemptsRemaining) {
            return true;
        }
        return false;
    }
}
