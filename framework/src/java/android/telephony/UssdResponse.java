package android.telephony;

/* loaded from: classes3.dex */
public final class UssdResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.UssdResponse> CREATOR = new android.os.Parcelable.Creator<android.telephony.UssdResponse>() { // from class: android.telephony.UssdResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UssdResponse createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.UssdResponse(parcel.readString(), android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UssdResponse[] newArray(int i) {
            return new android.telephony.UssdResponse[i];
        }
    };
    private java.lang.CharSequence mReturnMessage;
    private java.lang.String mUssdRequest;

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mUssdRequest);
        android.text.TextUtils.writeToParcel(this.mReturnMessage, parcel, 0);
    }

    public java.lang.String getUssdRequest() {
        return this.mUssdRequest;
    }

    public java.lang.CharSequence getReturnMessage() {
        return this.mReturnMessage;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UssdResponse(java.lang.String str, java.lang.CharSequence charSequence) {
        this.mUssdRequest = str;
        this.mReturnMessage = charSequence;
    }
}
