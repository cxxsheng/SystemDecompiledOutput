package android.telephony.ims;

/* loaded from: classes3.dex */
public final class SipDetails implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.SipDetails> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.SipDetails>() { // from class: android.telephony.ims.SipDetails.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SipDetails createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.SipDetails(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SipDetails[] newArray(int i) {
            return new android.telephony.ims.SipDetails[i];
        }
    };
    public static final int METHOD_PUBLISH = 2;
    public static final int METHOD_REGISTER = 1;
    public static final int METHOD_SUBSCRIBE = 3;
    public static final int METHOD_UNKNOWN = 0;
    private final java.lang.String mCallId;
    private final int mCseq;
    private final int mMethod;
    private final int mReasonHeaderCause;
    private final java.lang.String mReasonHeaderText;
    private final int mResponseCode;
    private final java.lang.String mResponsePhrase;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Method {
    }

    public static final class Builder {
        private java.lang.String mCallId;
        private int mMethod;
        private int mCseq = 0;
        private int mResponseCode = 0;
        private java.lang.String mResponsePhrase = "";
        private int mReasonHeaderCause = 0;
        private java.lang.String mReasonHeaderText = "";

        public Builder(int i) {
            this.mMethod = i;
        }

        public android.telephony.ims.SipDetails.Builder setCSeq(int i) {
            this.mCseq = i;
            return this;
        }

        public android.telephony.ims.SipDetails.Builder setSipResponseCode(int i, java.lang.String str) {
            this.mResponseCode = i;
            this.mResponsePhrase = str;
            return this;
        }

        public android.telephony.ims.SipDetails.Builder setSipResponseReasonHeader(int i, java.lang.String str) {
            this.mReasonHeaderCause = i;
            this.mReasonHeaderText = str;
            return this;
        }

        public android.telephony.ims.SipDetails.Builder setCallId(java.lang.String str) {
            this.mCallId = str;
            return this;
        }

        public android.telephony.ims.SipDetails build() {
            return new android.telephony.ims.SipDetails(this);
        }
    }

    private SipDetails(android.telephony.ims.SipDetails.Builder builder) {
        this.mMethod = builder.mMethod;
        this.mCseq = builder.mCseq;
        this.mResponseCode = builder.mResponseCode;
        this.mResponsePhrase = builder.mResponsePhrase;
        this.mReasonHeaderCause = builder.mReasonHeaderCause;
        this.mReasonHeaderText = builder.mReasonHeaderText;
        this.mCallId = builder.mCallId;
    }

    public int getMethod() {
        return this.mMethod;
    }

    public int getCSeq() {
        return this.mCseq;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    public java.lang.String getResponsePhrase() {
        return this.mResponsePhrase;
    }

    public int getReasonHeaderCause() {
        return this.mReasonHeaderCause;
    }

    public java.lang.String getReasonHeaderText() {
        return this.mReasonHeaderText;
    }

    public java.lang.String getCallId() {
        return this.mCallId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mMethod);
        parcel.writeInt(this.mCseq);
        parcel.writeInt(this.mResponseCode);
        parcel.writeString(this.mResponsePhrase);
        parcel.writeInt(this.mReasonHeaderCause);
        parcel.writeString(this.mReasonHeaderText);
        parcel.writeString(this.mCallId);
    }

    private SipDetails(android.os.Parcel parcel) {
        this.mMethod = parcel.readInt();
        this.mCseq = parcel.readInt();
        this.mResponseCode = parcel.readInt();
        this.mResponsePhrase = parcel.readString();
        this.mReasonHeaderCause = parcel.readInt();
        this.mReasonHeaderText = parcel.readString();
        this.mCallId = parcel.readString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.SipDetails sipDetails = (android.telephony.ims.SipDetails) obj;
        if (this.mMethod == sipDetails.mMethod && this.mCseq == sipDetails.mCseq && this.mResponseCode == sipDetails.mResponseCode && android.text.TextUtils.equals(this.mResponsePhrase, sipDetails.mResponsePhrase) && this.mReasonHeaderCause == sipDetails.mReasonHeaderCause && android.text.TextUtils.equals(this.mReasonHeaderText, sipDetails.mReasonHeaderText) && android.text.TextUtils.equals(this.mCallId, sipDetails.mCallId)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mMethod), java.lang.Integer.valueOf(this.mCseq), java.lang.Integer.valueOf(this.mResponseCode), this.mResponsePhrase, java.lang.Integer.valueOf(this.mReasonHeaderCause), this.mReasonHeaderText, this.mCallId);
    }

    public java.lang.String toString() {
        return "SipDetails { methodType= " + this.mMethod + ", cSeq=" + this.mCseq + ", ResponseCode=" + this.mResponseCode + ", ResponseCPhrase=" + this.mResponsePhrase + ", ReasonHeaderCause=" + this.mReasonHeaderCause + ", ReasonHeaderText=" + this.mReasonHeaderText + ", callId=" + this.mCallId + "}";
    }
}
