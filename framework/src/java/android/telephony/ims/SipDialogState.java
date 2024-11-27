package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SipDialogState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.SipDialogState> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.SipDialogState>() { // from class: android.telephony.ims.SipDialogState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SipDialogState createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.SipDialogState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SipDialogState[] newArray(int i) {
            return new android.telephony.ims.SipDialogState[i];
        }
    };
    public static final int STATE_CLOSED = 2;
    public static final int STATE_CONFIRMED = 1;
    public static final int STATE_EARLY = 0;
    private final int mState;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SipDialogStateCode {
    }

    public static final class Builder {
        private int mState;

        public Builder(int i) {
            this.mState = 0;
            this.mState = i;
        }

        public android.telephony.ims.SipDialogState build() {
            return new android.telephony.ims.SipDialogState(this);
        }
    }

    private SipDialogState(android.telephony.ims.SipDialogState.Builder builder) {
        this.mState = builder.mState;
    }

    private SipDialogState(android.os.Parcel parcel) {
        this.mState = parcel.readInt();
    }

    public int getState() {
        return this.mState;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mState);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mState == ((android.telephony.ims.SipDialogState) obj).mState) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mState));
    }
}
