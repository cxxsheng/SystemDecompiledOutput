package android.telecom;

/* loaded from: classes3.dex */
public final class CallEndpoint implements android.os.Parcelable {
    private static final java.lang.String CALLENDPOINT_NAME_ID_NULL_ERROR = "CallEndpoint name cannot be null.";
    public static final android.os.Parcelable.Creator<android.telecom.CallEndpoint> CREATOR = new android.os.Parcelable.Creator<android.telecom.CallEndpoint>() { // from class: android.telecom.CallEndpoint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.CallEndpoint createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.CallEndpoint(parcel.readCharSequence(), parcel.readInt(), android.os.ParcelUuid.CREATOR.createFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.CallEndpoint[] newArray(int i) {
            return new android.telecom.CallEndpoint[i];
        }
    };
    public static final int ENDPOINT_OPERATION_FAILED = 1;
    public static final int ENDPOINT_OPERATION_SUCCESS = 0;
    public static final int TYPE_BLUETOOTH = 2;
    public static final int TYPE_EARPIECE = 1;
    public static final int TYPE_SPEAKER = 4;
    public static final int TYPE_STREAMING = 5;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_WIRED_HEADSET = 3;
    private final android.os.ParcelUuid mIdentifier;
    private final java.lang.CharSequence mName;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EndpointType {
    }

    public CallEndpoint(java.lang.CharSequence charSequence, int i, android.os.ParcelUuid parcelUuid) {
        if (charSequence == null || parcelUuid == null) {
            throw new java.lang.IllegalArgumentException(CALLENDPOINT_NAME_ID_NULL_ERROR);
        }
        this.mName = charSequence;
        this.mType = i;
        this.mIdentifier = parcelUuid;
    }

    public CallEndpoint(java.lang.CharSequence charSequence, int i) {
        this(charSequence, i, new android.os.ParcelUuid(java.util.UUID.randomUUID()));
    }

    public CallEndpoint(android.telecom.CallEndpoint callEndpoint) {
        if (callEndpoint.getEndpointName() == null || callEndpoint.getIdentifier() == null) {
            throw new java.lang.IllegalArgumentException(CALLENDPOINT_NAME_ID_NULL_ERROR);
        }
        this.mName = callEndpoint.getEndpointName();
        this.mType = callEndpoint.getEndpointType();
        this.mIdentifier = callEndpoint.getIdentifier();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telecom.CallEndpoint)) {
            return false;
        }
        android.telecom.CallEndpoint callEndpoint = (android.telecom.CallEndpoint) obj;
        return java.util.Objects.equals(getEndpointName(), callEndpoint.getEndpointName()) && getEndpointType() == callEndpoint.getEndpointType() && getIdentifier().equals(callEndpoint.getIdentifier());
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mName, java.lang.Integer.valueOf(this.mType), this.mIdentifier);
    }

    public java.lang.String toString() {
        return android.text.TextUtils.formatSimple("[CallEndpoint Name: %s, Type: %s, Identifier: %s]", this.mName.toString(), endpointTypeToString(this.mType), this.mIdentifier.toString());
    }

    public java.lang.CharSequence getEndpointName() {
        return this.mName;
    }

    public int getEndpointType() {
        return this.mType;
    }

    public android.os.ParcelUuid getIdentifier() {
        return this.mIdentifier;
    }

    public static java.lang.String endpointTypeToString(int i) {
        switch (i) {
            case 1:
                return "EARPIECE";
            case 2:
                return "BLUETOOTH";
            case 3:
                return "WIRED_HEADSET";
            case 4:
                return "SPEAKER";
            case 5:
                return "EXTERNAL";
            default:
                return "UNKNOWN (" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mName);
        parcel.writeInt(this.mType);
        this.mIdentifier.writeToParcel(parcel, i);
    }
}
