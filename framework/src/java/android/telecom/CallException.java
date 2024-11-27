package android.telecom;

/* loaded from: classes3.dex */
public final class CallException extends java.lang.RuntimeException implements android.os.Parcelable {
    public static final int CODE_CALL_CANNOT_BE_SET_TO_ACTIVE = 4;
    public static final int CODE_CALL_IS_NOT_BEING_TRACKED = 3;
    public static final int CODE_CALL_NOT_PERMITTED_AT_PRESENT_TIME = 5;
    public static final int CODE_CANNOT_HOLD_CURRENT_ACTIVE_CALL = 2;
    public static final int CODE_ERROR_UNKNOWN = 1;
    public static final int CODE_OPERATION_TIMED_OUT = 6;
    public static final android.os.Parcelable.Creator<android.telecom.CallException> CREATOR = new android.os.Parcelable.Creator<android.telecom.CallException>() { // from class: android.telecom.CallException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.CallException createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.CallException(parcel.readString8(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.CallException[] newArray(int i) {
            return new android.telecom.CallException[i];
        }
    };
    public static final java.lang.String TRANSACTION_EXCEPTION_KEY = "TelecomTransactionalExceptionKey";
    private int mCode;
    private final java.lang.String mMessage;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallErrorCode {
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

    public CallException(java.lang.String str, int i) {
        super(getMessage(str, i));
        this.mCode = 1;
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
