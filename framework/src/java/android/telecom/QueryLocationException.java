package android.telecom;

/* loaded from: classes3.dex */
public final class QueryLocationException extends java.lang.RuntimeException implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.QueryLocationException> CREATOR = new android.os.Parcelable.Creator<android.telecom.QueryLocationException>() { // from class: android.telecom.QueryLocationException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.QueryLocationException createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.QueryLocationException(parcel.readString8(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.QueryLocationException[] newArray(int i) {
            return new android.telecom.QueryLocationException[i];
        }
    };
    public static final int ERROR_NOT_ALLOWED_FOR_NON_EMERGENCY_CONNECTIONS = 4;
    public static final int ERROR_NOT_PERMITTED = 3;
    public static final int ERROR_PREVIOUS_REQUEST_EXISTS = 2;
    public static final int ERROR_REQUEST_TIME_OUT = 1;
    public static final int ERROR_SERVICE_UNAVAILABLE = 5;
    public static final int ERROR_UNSPECIFIED = 6;
    public static final java.lang.String QUERY_LOCATION_ERROR = "QueryLocationErrorKey";
    private int mCode;
    private final java.lang.String mMessage;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface QueryLocationErrorCode {
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

    public QueryLocationException(java.lang.String str) {
        super(getMessage(str, 6));
        this.mCode = 6;
        this.mMessage = str;
    }

    public QueryLocationException(java.lang.String str, int i) {
        super(getMessage(str, i));
        this.mCode = 6;
        this.mCode = i;
        this.mMessage = str;
    }

    public QueryLocationException(java.lang.String str, int i, java.lang.Throwable th) {
        super(getMessage(str, i), th);
        this.mCode = 6;
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
