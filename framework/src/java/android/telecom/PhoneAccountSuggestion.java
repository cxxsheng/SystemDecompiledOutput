package android.telecom;

/* loaded from: classes3.dex */
public final class PhoneAccountSuggestion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.PhoneAccountSuggestion> CREATOR = new android.os.Parcelable.Creator<android.telecom.PhoneAccountSuggestion>() { // from class: android.telecom.PhoneAccountSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.PhoneAccountSuggestion createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.PhoneAccountSuggestion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.PhoneAccountSuggestion[] newArray(int i) {
            return new android.telecom.PhoneAccountSuggestion[i];
        }
    };
    public static final int REASON_FREQUENT = 2;
    public static final int REASON_INTRA_CARRIER = 1;
    public static final int REASON_NONE = 0;
    public static final int REASON_OTHER = 4;
    public static final int REASON_USER_SET = 3;
    private android.telecom.PhoneAccountHandle mHandle;
    private int mReason;
    private boolean mShouldAutoSelect;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SuggestionReason {
    }

    public PhoneAccountSuggestion(android.telecom.PhoneAccountHandle phoneAccountHandle, int i, boolean z) {
        this.mHandle = phoneAccountHandle;
        this.mReason = i;
        this.mShouldAutoSelect = z;
    }

    private PhoneAccountSuggestion(android.os.Parcel parcel) {
        this.mHandle = (android.telecom.PhoneAccountHandle) parcel.readParcelable(android.telecom.PhoneAccountHandle.class.getClassLoader(), android.telecom.PhoneAccountHandle.class);
        this.mReason = parcel.readInt();
        this.mShouldAutoSelect = parcel.readByte() != 0;
    }

    public android.telecom.PhoneAccountHandle getPhoneAccountHandle() {
        return this.mHandle;
    }

    public int getReason() {
        return this.mReason;
    }

    public boolean shouldAutoSelect() {
        return this.mShouldAutoSelect;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mHandle, i);
        parcel.writeInt(this.mReason);
        parcel.writeByte(this.mShouldAutoSelect ? (byte) 1 : (byte) 0);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telecom.PhoneAccountSuggestion phoneAccountSuggestion = (android.telecom.PhoneAccountSuggestion) obj;
        if (this.mReason == phoneAccountSuggestion.mReason && this.mShouldAutoSelect == phoneAccountSuggestion.mShouldAutoSelect && java.util.Objects.equals(this.mHandle, phoneAccountSuggestion.mHandle)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mHandle, java.lang.Integer.valueOf(this.mReason), java.lang.Boolean.valueOf(this.mShouldAutoSelect));
    }
}
