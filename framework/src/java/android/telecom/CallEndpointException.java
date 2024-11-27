package android.telecom;

/* loaded from: classes3.dex */
public final class CallEndpointException extends java.lang.RuntimeException implements android.os.Parcelable {
    public static final java.lang.String CHANGE_ERROR = "ChangeErrorKey";
    public static final android.os.Parcelable.Creator<android.telecom.CallEndpointException> CREATOR = new android.os.Parcelable.Creator<android.telecom.CallEndpointException>() { // from class: android.telecom.CallEndpointException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.CallEndpointException createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.CallEndpointException(parcel.readString8(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.CallEndpointException[] newArray(int i) {
            return new android.telecom.CallEndpointException[i];
        }
    };
    public static final int ERROR_ANOTHER_REQUEST = 3;
    public static final int ERROR_ENDPOINT_DOES_NOT_EXIST = 1;
    public static final int ERROR_REQUEST_TIME_OUT = 2;
    public static final int ERROR_UNSPECIFIED = 4;
    private int mCode;
    private final java.lang.String mMessage;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallEndpointErrorCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mMessage);
        parcel.writeInt(this.mCode);
    }

    public CallEndpointException(java.lang.String str, int i) {
        super(getMessage(str, i));
        this.mCode = 4;
        this.mCode = i;
        this.mMessage = str;
    }

    public int getCode() {
        return this.mCode;
    }

    private static java.lang.String getMessage(java.lang.String str, int i) {
        if (!android.text.TextUtils.isEmpty(str)) {
            return str + " (code: " + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
        return "code: " + i;
    }
}
