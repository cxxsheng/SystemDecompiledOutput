package android.mtp;

/* loaded from: classes2.dex */
public final class MtpObjectInfo {
    private int mAssociationDesc;
    private int mAssociationType;
    private int mCompressedSize;
    private long mDateCreated;
    private long mDateModified;
    private int mFormat;
    private int mHandle;
    private int mImagePixDepth;
    private int mImagePixHeight;
    private int mImagePixWidth;
    private java.lang.String mKeywords;
    private java.lang.String mName;
    private int mParent;
    private int mProtectionStatus;
    private int mSequenceNumber;
    private int mStorageId;
    private int mThumbCompressedSize;
    private int mThumbFormat;
    private int mThumbPixHeight;
    private int mThumbPixWidth;

    private MtpObjectInfo() {
        this.mName = "";
        this.mKeywords = "";
    }

    public final int getObjectHandle() {
        return this.mHandle;
    }

    public final int getStorageId() {
        return this.mStorageId;
    }

    public final int getFormat() {
        return this.mFormat;
    }

    public final int getProtectionStatus() {
        return this.mProtectionStatus;
    }

    public final int getCompressedSize() {
        com.android.internal.util.Preconditions.checkState(this.mCompressedSize >= 0);
        return this.mCompressedSize;
    }

    public final long getCompressedSizeLong() {
        return uint32ToLong(this.mCompressedSize);
    }

    public final int getThumbFormat() {
        return this.mThumbFormat;
    }

    public final int getThumbCompressedSize() {
        com.android.internal.util.Preconditions.checkState(this.mThumbCompressedSize >= 0);
        return this.mThumbCompressedSize;
    }

    public final long getThumbCompressedSizeLong() {
        return uint32ToLong(this.mThumbCompressedSize);
    }

    public final int getThumbPixWidth() {
        com.android.internal.util.Preconditions.checkState(this.mThumbPixWidth >= 0);
        return this.mThumbPixWidth;
    }

    public final long getThumbPixWidthLong() {
        return uint32ToLong(this.mThumbPixWidth);
    }

    public final int getThumbPixHeight() {
        com.android.internal.util.Preconditions.checkState(this.mThumbPixHeight >= 0);
        return this.mThumbPixHeight;
    }

    public final long getThumbPixHeightLong() {
        return uint32ToLong(this.mThumbPixHeight);
    }

    public final int getImagePixWidth() {
        com.android.internal.util.Preconditions.checkState(this.mImagePixWidth >= 0);
        return this.mImagePixWidth;
    }

    public final long getImagePixWidthLong() {
        return uint32ToLong(this.mImagePixWidth);
    }

    public final int getImagePixHeight() {
        com.android.internal.util.Preconditions.checkState(this.mImagePixHeight >= 0);
        return this.mImagePixHeight;
    }

    public final long getImagePixHeightLong() {
        return uint32ToLong(this.mImagePixHeight);
    }

    public final int getImagePixDepth() {
        com.android.internal.util.Preconditions.checkState(this.mImagePixDepth >= 0);
        return this.mImagePixDepth;
    }

    public final long getImagePixDepthLong() {
        return uint32ToLong(this.mImagePixDepth);
    }

    public final int getParent() {
        return this.mParent;
    }

    public final int getAssociationType() {
        return this.mAssociationType;
    }

    public final int getAssociationDesc() {
        return this.mAssociationDesc;
    }

    public final int getSequenceNumber() {
        com.android.internal.util.Preconditions.checkState(this.mSequenceNumber >= 0);
        return this.mSequenceNumber;
    }

    public final long getSequenceNumberLong() {
        return uint32ToLong(this.mSequenceNumber);
    }

    public final java.lang.String getName() {
        return this.mName;
    }

    public final long getDateCreated() {
        return this.mDateCreated;
    }

    public final long getDateModified() {
        return this.mDateModified;
    }

    public final java.lang.String getKeywords() {
        return this.mKeywords;
    }

    public static class Builder {
        private android.mtp.MtpObjectInfo mObjectInfo = new android.mtp.MtpObjectInfo();

        public Builder() {
            this.mObjectInfo.mHandle = -1;
        }

        public Builder(android.mtp.MtpObjectInfo mtpObjectInfo) {
            this.mObjectInfo.mHandle = -1;
            this.mObjectInfo.mAssociationDesc = mtpObjectInfo.mAssociationDesc;
            this.mObjectInfo.mAssociationType = mtpObjectInfo.mAssociationType;
            this.mObjectInfo.mCompressedSize = mtpObjectInfo.mCompressedSize;
            this.mObjectInfo.mDateCreated = mtpObjectInfo.mDateCreated;
            this.mObjectInfo.mDateModified = mtpObjectInfo.mDateModified;
            this.mObjectInfo.mFormat = mtpObjectInfo.mFormat;
            this.mObjectInfo.mImagePixDepth = mtpObjectInfo.mImagePixDepth;
            this.mObjectInfo.mImagePixHeight = mtpObjectInfo.mImagePixHeight;
            this.mObjectInfo.mImagePixWidth = mtpObjectInfo.mImagePixWidth;
            this.mObjectInfo.mKeywords = mtpObjectInfo.mKeywords;
            this.mObjectInfo.mName = mtpObjectInfo.mName;
            this.mObjectInfo.mParent = mtpObjectInfo.mParent;
            this.mObjectInfo.mProtectionStatus = mtpObjectInfo.mProtectionStatus;
            this.mObjectInfo.mSequenceNumber = mtpObjectInfo.mSequenceNumber;
            this.mObjectInfo.mStorageId = mtpObjectInfo.mStorageId;
            this.mObjectInfo.mThumbCompressedSize = mtpObjectInfo.mThumbCompressedSize;
            this.mObjectInfo.mThumbFormat = mtpObjectInfo.mThumbFormat;
            this.mObjectInfo.mThumbPixHeight = mtpObjectInfo.mThumbPixHeight;
            this.mObjectInfo.mThumbPixWidth = mtpObjectInfo.mThumbPixWidth;
        }

        public android.mtp.MtpObjectInfo.Builder setObjectHandle(int i) {
            this.mObjectInfo.mHandle = i;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setAssociationDesc(int i) {
            this.mObjectInfo.mAssociationDesc = i;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setAssociationType(int i) {
            this.mObjectInfo.mAssociationType = i;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setCompressedSize(long j) {
            this.mObjectInfo.mCompressedSize = android.mtp.MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setDateCreated(long j) {
            this.mObjectInfo.mDateCreated = j;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setDateModified(long j) {
            this.mObjectInfo.mDateModified = j;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setFormat(int i) {
            this.mObjectInfo.mFormat = i;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setImagePixDepth(long j) {
            this.mObjectInfo.mImagePixDepth = android.mtp.MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setImagePixHeight(long j) {
            this.mObjectInfo.mImagePixHeight = android.mtp.MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setImagePixWidth(long j) {
            this.mObjectInfo.mImagePixWidth = android.mtp.MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setKeywords(java.lang.String str) {
            if (dalvik.system.VMRuntime.getRuntime().getTargetSdkVersion() > 25) {
                com.android.internal.util.Preconditions.checkNotNull(str);
            } else if (str == null) {
                str = "";
            }
            this.mObjectInfo.mKeywords = str;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setName(java.lang.String str) {
            com.android.internal.util.Preconditions.checkNotNull(str);
            this.mObjectInfo.mName = str;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setParent(int i) {
            this.mObjectInfo.mParent = i;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setProtectionStatus(int i) {
            this.mObjectInfo.mProtectionStatus = i;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setSequenceNumber(long j) {
            this.mObjectInfo.mSequenceNumber = android.mtp.MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setStorageId(int i) {
            this.mObjectInfo.mStorageId = i;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setThumbCompressedSize(long j) {
            this.mObjectInfo.mThumbCompressedSize = android.mtp.MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setThumbFormat(int i) {
            this.mObjectInfo.mThumbFormat = i;
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setThumbPixHeight(long j) {
            this.mObjectInfo.mThumbPixHeight = android.mtp.MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public android.mtp.MtpObjectInfo.Builder setThumbPixWidth(long j) {
            this.mObjectInfo.mThumbPixWidth = android.mtp.MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public android.mtp.MtpObjectInfo build() {
            android.mtp.MtpObjectInfo mtpObjectInfo = this.mObjectInfo;
            this.mObjectInfo = null;
            return mtpObjectInfo;
        }
    }

    private static long uint32ToLong(int i) {
        return i < 0 ? i + 4294967296L : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int longToUint32(long j, java.lang.String str) {
        com.android.internal.util.Preconditions.checkArgumentInRange(j, 0L, 4294967295L, str);
        return (int) j;
    }
}
