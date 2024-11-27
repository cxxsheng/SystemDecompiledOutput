package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ImsExternalCallState implements android.os.Parcelable {
    public static final int CALL_STATE_CONFIRMED = 1;
    public static final int CALL_STATE_TERMINATED = 2;
    public static final android.os.Parcelable.Creator<android.telephony.ims.ImsExternalCallState> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.ImsExternalCallState>() { // from class: android.telephony.ims.ImsExternalCallState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsExternalCallState createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.ImsExternalCallState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsExternalCallState[] newArray(int i) {
            return new android.telephony.ims.ImsExternalCallState[i];
        }
    };
    private static final java.lang.String TAG = "ImsExternalCallState";
    private android.net.Uri mAddress;
    private int mCallId;
    private int mCallState;
    private int mCallType;
    private boolean mIsHeld;
    private boolean mIsPullable;
    private android.net.Uri mLocalAddress;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ExternalCallState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ExternalCallType {
    }

    public ImsExternalCallState() {
    }

    public ImsExternalCallState(int i, android.net.Uri uri, boolean z, int i2, int i3, boolean z2) {
        this.mCallId = i;
        this.mAddress = uri;
        this.mIsPullable = z;
        this.mCallState = i2;
        this.mCallType = i3;
        this.mIsHeld = z2;
        com.android.telephony.Rlog.d(TAG, "ImsExternalCallState = " + this);
    }

    public ImsExternalCallState(int i, android.net.Uri uri, android.net.Uri uri2, boolean z, int i2, int i3, boolean z2) {
        this.mCallId = i;
        this.mAddress = uri;
        this.mLocalAddress = uri2;
        this.mIsPullable = z;
        this.mCallState = i2;
        this.mCallType = i3;
        this.mIsHeld = z2;
        com.android.telephony.Rlog.d(TAG, "ImsExternalCallState = " + this);
    }

    public ImsExternalCallState(java.lang.String str, android.net.Uri uri, android.net.Uri uri2, boolean z, int i, int i2, boolean z2) {
        this.mCallId = getIdForString(str);
        this.mAddress = uri;
        this.mLocalAddress = uri2;
        this.mIsPullable = z;
        this.mCallState = i;
        this.mCallType = i2;
        this.mIsHeld = z2;
        com.android.telephony.Rlog.d(TAG, "ImsExternalCallState = " + this);
    }

    public ImsExternalCallState(android.os.Parcel parcel) {
        this.mCallId = parcel.readInt();
        java.lang.ClassLoader classLoader = android.telephony.ims.ImsExternalCallState.class.getClassLoader();
        this.mAddress = (android.net.Uri) parcel.readParcelable(classLoader, android.net.Uri.class);
        this.mLocalAddress = (android.net.Uri) parcel.readParcelable(classLoader, android.net.Uri.class);
        this.mIsPullable = parcel.readInt() != 0;
        this.mCallState = parcel.readInt();
        this.mCallType = parcel.readInt();
        this.mIsHeld = parcel.readInt() != 0;
        com.android.telephony.Rlog.d(TAG, "ImsExternalCallState const = " + this);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCallId);
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeParcelable(this.mLocalAddress, 0);
        parcel.writeInt(this.mIsPullable ? 1 : 0);
        parcel.writeInt(this.mCallState);
        parcel.writeInt(this.mCallType);
        parcel.writeInt(this.mIsHeld ? 1 : 0);
        com.android.telephony.Rlog.d(TAG, "ImsExternalCallState writeToParcel = " + parcel.toString());
    }

    public int getCallId() {
        return this.mCallId;
    }

    public android.net.Uri getAddress() {
        return this.mAddress;
    }

    public android.net.Uri getLocalAddress() {
        return this.mLocalAddress;
    }

    public boolean isCallPullable() {
        return this.mIsPullable;
    }

    public int getCallState() {
        return this.mCallState;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public boolean isCallHeld() {
        return this.mIsHeld;
    }

    public java.lang.String toString() {
        return "ImsExternalCallState { mCallId = " + this.mCallId + ", mAddress = " + com.android.telephony.Rlog.pii(TAG, this.mAddress) + ", mLocalAddress = " + com.android.telephony.Rlog.pii(TAG, this.mLocalAddress) + ", mIsPullable = " + this.mIsPullable + ", mCallState = " + this.mCallState + ", mCallType = " + this.mCallType + ", mIsHeld = " + this.mIsHeld + "}";
    }

    private int getIdForString(java.lang.String str) {
        try {
            return java.lang.Integer.parseInt(str);
        } catch (java.lang.NumberFormatException e) {
            return str.hashCode();
        }
    }
}
