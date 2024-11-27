package android.telecom;

/* loaded from: classes3.dex */
public final class CallAttributes implements android.os.Parcelable {
    public static final int AUDIO_CALL = 1;
    public static final java.lang.String CALLER_PID_KEY = "CallerPid";
    public static final java.lang.String CALLER_UID_KEY = "CallerUid";
    public static final java.lang.String CALL_CAPABILITIES_KEY = "TelecomCapabilities";
    public static final android.os.Parcelable.Creator<android.telecom.CallAttributes> CREATOR = new android.os.Parcelable.Creator<android.telecom.CallAttributes>() { // from class: android.telecom.CallAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.CallAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.CallAttributes((android.telecom.PhoneAccountHandle) parcel.readParcelable(getClass().getClassLoader(), android.telecom.PhoneAccountHandle.class), parcel.readCharSequence(), (android.net.Uri) parcel.readParcelable(getClass().getClassLoader(), android.net.Uri.class), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.CallAttributes[] newArray(int i) {
            return new android.telecom.CallAttributes[i];
        }
    };
    public static final int DIRECTION_INCOMING = 1;
    public static final int DIRECTION_OUTGOING = 2;
    public static final java.lang.String DISPLAY_NAME_KEY = "DisplayName";
    public static final int SUPPORTS_SET_INACTIVE = 2;
    public static final int SUPPORTS_STREAM = 4;
    public static final int SUPPORTS_TRANSFER = 8;
    public static final int SUPPORTS_VIDEO_CALLING = 16;
    public static final int VIDEO_CALL = 2;
    private final android.net.Uri mAddress;
    private final int mCallCapabilities;
    private final int mCallType;
    private final int mDirection;
    private final java.lang.CharSequence mDisplayName;
    private final android.telecom.PhoneAccountHandle mPhoneAccountHandle;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallCapability {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    private CallAttributes(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.CharSequence charSequence, android.net.Uri uri, int i, int i2, int i3) {
        this.mPhoneAccountHandle = phoneAccountHandle;
        this.mDisplayName = charSequence;
        this.mAddress = uri;
        this.mDirection = i;
        this.mCallType = i2;
        this.mCallCapabilities = i3;
    }

    public static final class Builder {
        private final android.net.Uri mAddress;
        private final int mDirection;
        private final java.lang.CharSequence mDisplayName;
        private final android.telecom.PhoneAccountHandle mPhoneAccountHandle;
        private int mCallType = 1;
        private int mCallCapabilities = 2;

        public Builder(android.telecom.PhoneAccountHandle phoneAccountHandle, int i, java.lang.CharSequence charSequence, android.net.Uri uri) {
            if (!isInRange(1, 2, i)) {
                throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("CallDirection=[%d] is invalid. CallDirections value should be within [%d, %d]", java.lang.Integer.valueOf(i), 1, 2));
            }
            java.util.Objects.requireNonNull(phoneAccountHandle);
            java.util.Objects.requireNonNull(charSequence);
            java.util.Objects.requireNonNull(uri);
            this.mPhoneAccountHandle = phoneAccountHandle;
            this.mDirection = i;
            this.mDisplayName = charSequence;
            this.mAddress = uri;
        }

        public android.telecom.CallAttributes.Builder setCallType(int i) {
            if (!isInRange(1, 2, i)) {
                throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("CallType=[%d] is invalid. CallTypes value should be within [%d, %d]", java.lang.Integer.valueOf(i), 1, 2));
            }
            this.mCallType = i;
            return this;
        }

        public android.telecom.CallAttributes.Builder setCallCapabilities(int i) {
            this.mCallCapabilities = i;
            return this;
        }

        public android.telecom.CallAttributes build() {
            return new android.telecom.CallAttributes(this.mPhoneAccountHandle, this.mDisplayName, this.mAddress, this.mDirection, this.mCallType, this.mCallCapabilities);
        }

        private boolean isInRange(int i, int i2, int i3) {
            return i3 >= i && i3 <= i2;
        }
    }

    public android.telecom.PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccountHandle;
    }

    public java.lang.CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public android.net.Uri getAddress() {
        return this.mAddress;
    }

    public int getDirection() {
        return this.mDirection;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public int getCallCapabilities() {
        return this.mCallCapabilities;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mPhoneAccountHandle, i);
        parcel.writeCharSequence(this.mDisplayName);
        parcel.writeParcelable(this.mAddress, i);
        parcel.writeInt(this.mDirection);
        parcel.writeInt(this.mCallType);
        parcel.writeInt(this.mCallCapabilities);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{ CallAttributes: [phoneAccountHandle: ").append(this.mPhoneAccountHandle).append("], [contactName: ").append(android.telecom.Log.pii(this.mDisplayName)).append("], [address=").append(android.telecom.Log.pii(this.mAddress)).append("], [direction=").append(this.mDirection).append("], [callType=").append(this.mCallType).append("], [mCallCapabilities=").append(this.mCallCapabilities).append("]  }");
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        android.telecom.CallAttributes callAttributes = (android.telecom.CallAttributes) obj;
        return this.mDirection == callAttributes.mDirection && this.mCallType == callAttributes.mCallType && this.mCallCapabilities == callAttributes.mCallCapabilities && java.util.Objects.equals(this.mPhoneAccountHandle, callAttributes.mPhoneAccountHandle) && java.util.Objects.equals(this.mAddress, callAttributes.mAddress) && java.util.Objects.equals(this.mDisplayName, callAttributes.mDisplayName);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPhoneAccountHandle, this.mAddress, this.mDisplayName, java.lang.Integer.valueOf(this.mDirection), java.lang.Integer.valueOf(this.mCallType), java.lang.Integer.valueOf(this.mCallCapabilities));
    }
}
