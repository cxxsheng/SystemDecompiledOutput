package android.app.blob;

/* loaded from: classes.dex */
public final class BlobHandle implements android.os.Parcelable {
    public static final java.lang.String ALGO_SHA_256 = "SHA-256";
    private static final int LIMIT_BLOB_LABEL_LENGTH = 100;
    private static final int LIMIT_BLOB_TAG_LENGTH = 128;
    public final java.lang.String algorithm;
    public final byte[] digest;
    public final long expiryTimeMillis;
    public final java.lang.CharSequence label;
    public final java.lang.String tag;
    private static final java.lang.String[] SUPPORTED_ALGOS = {"SHA-256"};
    public static final android.os.Parcelable.Creator<android.app.blob.BlobHandle> CREATOR = new android.os.Parcelable.Creator<android.app.blob.BlobHandle>() { // from class: android.app.blob.BlobHandle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.blob.BlobHandle createFromParcel(android.os.Parcel parcel) {
            return new android.app.blob.BlobHandle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.blob.BlobHandle[] newArray(int i) {
            return new android.app.blob.BlobHandle[i];
        }
    };

    private BlobHandle(java.lang.String str, byte[] bArr, java.lang.CharSequence charSequence, long j, java.lang.String str2) {
        this.algorithm = str;
        this.digest = bArr;
        this.label = charSequence;
        this.expiryTimeMillis = j;
        this.tag = str2;
    }

    private BlobHandle(android.os.Parcel parcel) {
        this.algorithm = parcel.readString();
        this.digest = parcel.createByteArray();
        this.label = parcel.readCharSequence();
        this.expiryTimeMillis = parcel.readLong();
        this.tag = parcel.readString();
    }

    public static android.app.blob.BlobHandle create(java.lang.String str, byte[] bArr, java.lang.CharSequence charSequence, long j, java.lang.String str2) {
        android.app.blob.BlobHandle blobHandle = new android.app.blob.BlobHandle(str, bArr, charSequence, j, str2);
        blobHandle.assertIsValid();
        return blobHandle;
    }

    public static android.app.blob.BlobHandle createWithSha256(byte[] bArr, java.lang.CharSequence charSequence, long j, java.lang.String str) {
        return create("SHA-256", bArr, charSequence, j, str);
    }

    public byte[] getSha256Digest() {
        return this.digest;
    }

    public java.lang.CharSequence getLabel() {
        return this.label;
    }

    public long getExpiryTimeMillis() {
        return this.expiryTimeMillis;
    }

    public java.lang.String getTag() {
        return this.tag;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.algorithm);
        parcel.writeByteArray(this.digest);
        parcel.writeCharSequence(this.label);
        parcel.writeLong(this.expiryTimeMillis);
        parcel.writeString(this.tag);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.app.blob.BlobHandle)) {
            return false;
        }
        android.app.blob.BlobHandle blobHandle = (android.app.blob.BlobHandle) obj;
        if (this.algorithm.equals(blobHandle.algorithm) && java.util.Arrays.equals(this.digest, blobHandle.digest) && this.label.toString().equals(blobHandle.label.toString()) && this.expiryTimeMillis == blobHandle.expiryTimeMillis && this.tag.equals(blobHandle.tag)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.algorithm, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.digest)), this.label, java.lang.Long.valueOf(this.expiryTimeMillis), this.tag);
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter, boolean z) {
        if (z) {
            indentingPrintWriter.println("algo: " + this.algorithm);
            indentingPrintWriter.println("digest: " + (z ? encodeDigest(this.digest) : safeDigest(this.digest)));
            indentingPrintWriter.println("label: " + ((java.lang.Object) this.label));
            indentingPrintWriter.println("expiryMs: " + this.expiryTimeMillis);
            indentingPrintWriter.println("tag: " + this.tag);
            return;
        }
        indentingPrintWriter.println(toString());
    }

    public void assertIsValid() {
        com.android.internal.util.Preconditions.checkArgumentIsSupported(SUPPORTED_ALGOS, this.algorithm);
        com.android.internal.util.Preconditions.checkByteArrayNotEmpty(this.digest, com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.DIGEST);
        com.android.internal.util.Preconditions.checkStringNotEmpty(this.label, "label must not be null");
        com.android.internal.util.Preconditions.checkArgument(this.label.length() <= 100, "label too long");
        com.android.internal.util.Preconditions.checkArgumentNonnegative(this.expiryTimeMillis, "expiryTimeMillis must not be negative");
        com.android.internal.util.Preconditions.checkStringNotEmpty(this.tag, "tag must not be null");
        com.android.internal.util.Preconditions.checkArgument(this.tag.length() <= 128, "tag too long");
    }

    public java.lang.String toString() {
        return "BlobHandle {algo:" + this.algorithm + ",digest:" + safeDigest(this.digest) + ",label:" + ((java.lang.Object) this.label) + ",expiryMs:" + this.expiryTimeMillis + ",tag:" + this.tag + "}";
    }

    public static java.lang.String safeDigest(byte[] bArr) {
        java.lang.String encodeDigest = encodeDigest(bArr);
        return encodeDigest.substring(0, 2) + ".." + encodeDigest.substring(encodeDigest.length() - 2);
    }

    private static java.lang.String encodeDigest(byte[] bArr) {
        return android.util.Base64.encodeToString(bArr, 2);
    }

    public boolean isExpired() {
        return this.expiryTimeMillis != 0 && this.expiryTimeMillis < java.lang.System.currentTimeMillis();
    }

    public void writeToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        com.android.internal.util.XmlUtils.writeStringAttribute(xmlSerializer, android.app.blob.XmlTags.ATTR_ALGO, this.algorithm);
        com.android.internal.util.XmlUtils.writeByteArrayAttribute(xmlSerializer, android.app.blob.XmlTags.ATTR_DIGEST, this.digest);
        com.android.internal.util.XmlUtils.writeStringAttribute(xmlSerializer, android.app.blob.XmlTags.ATTR_LABEL, this.label);
        com.android.internal.util.XmlUtils.writeLongAttribute(xmlSerializer, android.app.blob.XmlTags.ATTR_EXPIRY_TIME, this.expiryTimeMillis);
        com.android.internal.util.XmlUtils.writeStringAttribute(xmlSerializer, android.app.blob.XmlTags.ATTR_TAG, this.tag);
    }

    public static android.app.blob.BlobHandle createFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException {
        return create(com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, android.app.blob.XmlTags.ATTR_ALGO), com.android.internal.util.XmlUtils.readByteArrayAttribute(xmlPullParser, android.app.blob.XmlTags.ATTR_DIGEST), com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, android.app.blob.XmlTags.ATTR_LABEL), com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, android.app.blob.XmlTags.ATTR_EXPIRY_TIME), com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, android.app.blob.XmlTags.ATTR_TAG));
    }
}
