package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SipMessage implements android.os.Parcelable {
    private static final java.lang.String CRLF = "\r\n";
    private final java.lang.String mCallIdParam;
    private final byte[] mContent;
    private final java.lang.String mHeaderSection;
    private final java.lang.String mStartLine;
    private final java.lang.String mViaBranchParam;
    private static final boolean IS_DEBUGGING = android.os.Build.IS_ENG;
    public static final android.os.Parcelable.Creator<android.telephony.ims.SipMessage> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.SipMessage>() { // from class: android.telephony.ims.SipMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SipMessage createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.SipMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SipMessage[] newArray(int i) {
            return new android.telephony.ims.SipMessage[i];
        }
    };

    public SipMessage(java.lang.String str, java.lang.String str2, byte[] bArr) {
        java.util.Objects.requireNonNull(str, "Required parameter is null: startLine");
        java.util.Objects.requireNonNull(str2, "Required parameter is null: headerSection");
        java.util.Objects.requireNonNull(bArr, "Required parameter is null: content");
        this.mStartLine = str;
        this.mHeaderSection = str2;
        this.mContent = bArr;
        this.mViaBranchParam = com.android.internal.telephony.SipMessageParsingUtils.getTransactionId(this.mHeaderSection);
        if (android.text.TextUtils.isEmpty(this.mViaBranchParam)) {
            throw new java.lang.IllegalArgumentException("header section MUST contain a branch parameter inside of the Via header.");
        }
        this.mCallIdParam = com.android.internal.telephony.SipMessageParsingUtils.getCallId(this.mHeaderSection);
    }

    private SipMessage(android.os.Parcel parcel) {
        this.mStartLine = parcel.readString();
        this.mHeaderSection = parcel.readString();
        this.mContent = new byte[parcel.readInt()];
        parcel.readByteArray(this.mContent);
        this.mViaBranchParam = parcel.readString();
        this.mCallIdParam = parcel.readString();
    }

    public java.lang.String getStartLine() {
        return this.mStartLine;
    }

    public java.lang.String getHeaderSection() {
        return this.mHeaderSection;
    }

    public byte[] getContent() {
        return this.mContent;
    }

    public java.lang.String getViaBranchParameter() {
        return this.mViaBranchParam;
    }

    public java.lang.String getCallIdParameter() {
        return this.mCallIdParam;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mStartLine);
        parcel.writeString(this.mHeaderSection);
        parcel.writeInt(this.mContent.length);
        parcel.writeByteArray(this.mContent);
        parcel.writeString(this.mViaBranchParam);
        parcel.writeString(this.mCallIdParam);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("StartLine: [");
        if (IS_DEBUGGING) {
            sb.append(this.mStartLine);
        } else {
            sb.append(sanitizeStartLineRequest(this.mStartLine));
        }
        sb.append("], Header: [");
        if (IS_DEBUGGING) {
            sb.append(this.mHeaderSection);
        } else {
            sb.append("***");
        }
        sb.append("], Content: ");
        sb.append(getContent().length == 0 ? "[NONE]" : "[NOT SHOWN]");
        return sb.toString();
    }

    private java.lang.String sanitizeStartLineRequest(java.lang.String str) {
        if (!com.android.internal.telephony.SipMessageParsingUtils.isSipRequest(str)) {
            return str;
        }
        java.lang.String[] split = str.split(" ");
        return split[0] + " <Request-URI> " + split[2];
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.SipMessage sipMessage = (android.telephony.ims.SipMessage) obj;
        if (this.mStartLine.equals(sipMessage.mStartLine) && this.mHeaderSection.equals(sipMessage.mHeaderSection) && java.util.Arrays.equals(this.mContent, sipMessage.mContent)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (java.util.Objects.hash(this.mStartLine, this.mHeaderSection) * 31) + java.util.Arrays.hashCode(this.mContent);
    }

    public byte[] toEncodedMessage() {
        byte[] bytes = (this.mStartLine + this.mHeaderSection + CRLF).getBytes(java.nio.charset.StandardCharsets.UTF_8);
        byte[] bArr = new byte[bytes.length + this.mContent.length];
        java.lang.System.arraycopy(bytes, 0, bArr, 0, bytes.length);
        java.lang.System.arraycopy(this.mContent, 0, bArr, bytes.length, this.mContent.length);
        return bArr;
    }
}
