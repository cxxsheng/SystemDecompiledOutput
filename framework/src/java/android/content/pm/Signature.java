package android.content.pm;

/* loaded from: classes.dex */
public class Signature implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.Signature> CREATOR = new android.os.Parcelable.Creator<android.content.pm.Signature>() { // from class: android.content.pm.Signature.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.Signature createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.Signature(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.Signature[] newArray(int i) {
            return new android.content.pm.Signature[i];
        }
    };
    private java.security.cert.Certificate[] mCertificateChain;
    private int mFlags;
    private int mHashCode;
    private boolean mHaveHashCode;
    private final byte[] mSignature;
    private java.lang.ref.SoftReference<java.lang.String> mStringRef;

    public Signature(byte[] bArr) {
        this.mSignature = (byte[]) bArr.clone();
        this.mCertificateChain = null;
    }

    public Signature(java.security.cert.Certificate[] certificateArr) throws java.security.cert.CertificateEncodingException {
        this.mSignature = certificateArr[0].getEncoded();
        if (certificateArr.length > 1) {
            this.mCertificateChain = (java.security.cert.Certificate[]) java.util.Arrays.copyOfRange(certificateArr, 1, certificateArr.length);
        }
    }

    private static final int parseHexDigit(int i) {
        if (48 <= i && i <= 57) {
            return i - 48;
        }
        if (97 <= i && i <= 102) {
            return (i - 97) + 10;
        }
        if (65 <= i && i <= 70) {
            return (i - 65) + 10;
        }
        throw new java.lang.IllegalArgumentException("Invalid character " + i + " in hex string");
    }

    public Signature(java.lang.String str) {
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        if (length % 2 != 0) {
            throw new java.lang.IllegalArgumentException("text size " + length + " is not even");
        }
        byte[] bArr = new byte[length / 2];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i + 1;
            bArr[i2] = (byte) ((parseHexDigit(bytes[i]) << 4) | parseHexDigit(bytes[i3]));
            i = i3 + 1;
            i2++;
        }
        this.mSignature = bArr;
    }

    public Signature(android.content.pm.Signature signature) {
        this.mSignature = (byte[]) signature.mSignature.clone();
        java.security.cert.Certificate[] certificateArr = signature.mCertificateChain;
        if (certificateArr != null && certificateArr.length > 1) {
            this.mCertificateChain = (java.security.cert.Certificate[]) java.util.Arrays.copyOfRange(certificateArr, 1, certificateArr.length);
        }
        this.mFlags = signature.mFlags;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public char[] toChars() {
        return toChars(null, null);
    }

    public char[] toChars(char[] cArr, int[] iArr) {
        byte[] bArr = this.mSignature;
        int length = bArr.length;
        int i = length * 2;
        if (cArr == null || i > cArr.length) {
            cArr = new char[i];
        }
        for (int i2 = 0; i2 < length; i2++) {
            byte b = bArr[i2];
            int i3 = (b >> 4) & 15;
            int i4 = i2 * 2;
            cArr[i4] = (char) (i3 >= 10 ? (i3 + 97) - 10 : i3 + 48);
            int i5 = b & 15;
            cArr[i4 + 1] = (char) (i5 >= 10 ? (i5 + 97) - 10 : i5 + 48);
        }
        if (iArr != null) {
            iArr[0] = length;
        }
        return cArr;
    }

    public java.lang.String toCharsString() {
        java.lang.String str = this.mStringRef == null ? null : this.mStringRef.get();
        if (str != null) {
            return str;
        }
        java.lang.String str2 = new java.lang.String(toChars());
        this.mStringRef = new java.lang.ref.SoftReference<>(str2);
        return str2;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[this.mSignature.length];
        java.lang.System.arraycopy(this.mSignature, 0, bArr, 0, this.mSignature.length);
        return bArr;
    }

    public java.security.PublicKey getPublicKey() throws java.security.cert.CertificateException {
        return java.security.cert.CertificateFactory.getInstance("X.509").generateCertificate(new java.io.ByteArrayInputStream(this.mSignature)).getPublicKey();
    }

    public android.content.pm.Signature[] getChainSignatures() throws java.security.cert.CertificateEncodingException {
        if (this.mCertificateChain == null) {
            return new android.content.pm.Signature[]{this};
        }
        int i = 1;
        android.content.pm.Signature[] signatureArr = new android.content.pm.Signature[this.mCertificateChain.length + 1];
        int i2 = 0;
        signatureArr[0] = this;
        java.security.cert.Certificate[] certificateArr = this.mCertificateChain;
        int length = certificateArr.length;
        while (i2 < length) {
            signatureArr[i] = new android.content.pm.Signature(certificateArr[i2].getEncoded());
            i2++;
            i++;
        }
        return signatureArr;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj != null) {
            try {
                android.content.pm.Signature signature = (android.content.pm.Signature) obj;
                if (this != signature) {
                    if (!java.util.Arrays.equals(this.mSignature, signature.mSignature)) {
                        return false;
                    }
                }
                return true;
            } catch (java.lang.ClassCastException e) {
            }
        }
        return false;
    }

    public int hashCode() {
        if (this.mHaveHashCode) {
            return this.mHashCode;
        }
        this.mHashCode = java.util.Arrays.hashCode(this.mSignature);
        this.mHaveHashCode = true;
        return this.mHashCode;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByteArray(this.mSignature);
    }

    public void writeToXmlAttributeBytesHex(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        typedXmlSerializer.attributeBytesHex(str, str2, this.mSignature);
    }

    private Signature(android.os.Parcel parcel) {
        this.mSignature = parcel.createByteArray();
    }

    public static boolean areExactMatch(android.content.pm.SigningDetails signingDetails, android.content.pm.SigningDetails signingDetails2) {
        return areExactArraysMatch(signingDetails.getSignatures(), signingDetails2.getSignatures());
    }

    public static boolean areExactMatch(android.content.pm.SigningDetails signingDetails, android.content.pm.Signature[] signatureArr) {
        return areExactArraysMatch(signingDetails.getSignatures(), signatureArr);
    }

    static boolean areExactArraysMatch(android.content.pm.Signature[] signatureArr, android.content.pm.Signature[] signatureArr2) {
        return com.android.internal.util.ArrayUtils.size(signatureArr) == com.android.internal.util.ArrayUtils.size(signatureArr2) && com.android.internal.util.ArrayUtils.containsAll(signatureArr, signatureArr2) && com.android.internal.util.ArrayUtils.containsAll(signatureArr2, signatureArr);
    }

    public static boolean areEffectiveMatch(android.content.pm.SigningDetails signingDetails, android.content.pm.SigningDetails signingDetails2) throws java.security.cert.CertificateException {
        return areEffectiveArraysMatch(signingDetails.getSignatures(), signingDetails2.getSignatures());
    }

    static boolean areEffectiveArraysMatch(android.content.pm.Signature[] signatureArr, android.content.pm.Signature[] signatureArr2) throws java.security.cert.CertificateException {
        java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
        android.content.pm.Signature[] signatureArr3 = new android.content.pm.Signature[signatureArr.length];
        for (int i = 0; i < signatureArr.length; i++) {
            signatureArr3[i] = bounce(certificateFactory, signatureArr[i]);
        }
        android.content.pm.Signature[] signatureArr4 = new android.content.pm.Signature[signatureArr2.length];
        for (int i2 = 0; i2 < signatureArr2.length; i2++) {
            signatureArr4[i2] = bounce(certificateFactory, signatureArr2[i2]);
        }
        return areExactArraysMatch(signatureArr3, signatureArr4);
    }

    public static boolean areEffectiveMatch(android.content.pm.Signature signature, android.content.pm.Signature signature2) throws java.security.cert.CertificateException {
        java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
        return bounce(certificateFactory, signature).equals(bounce(certificateFactory, signature2));
    }

    public static android.content.pm.Signature bounce(java.security.cert.CertificateFactory certificateFactory, android.content.pm.Signature signature) throws java.security.cert.CertificateException {
        android.content.pm.Signature signature2 = new android.content.pm.Signature(((java.security.cert.X509Certificate) certificateFactory.generateCertificate(new java.io.ByteArrayInputStream(signature.mSignature))).getEncoded());
        if (java.lang.Math.abs(signature2.mSignature.length - signature.mSignature.length) > 2) {
            throw new java.security.cert.CertificateException("Bounced cert length looks fishy; before " + signature.mSignature.length + ", after " + signature2.mSignature.length);
        }
        return signature2;
    }
}
