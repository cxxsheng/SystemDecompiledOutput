package android.app.job;

/* loaded from: classes.dex */
public final class JobWorkItem implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.job.JobWorkItem> CREATOR = new android.os.Parcelable.Creator<android.app.job.JobWorkItem>() { // from class: android.app.job.JobWorkItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.job.JobWorkItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.job.JobWorkItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.job.JobWorkItem[] newArray(int i) {
            return new android.app.job.JobWorkItem[i];
        }
    };
    int mDeliveryCount;
    private final android.os.PersistableBundle mExtras;
    java.lang.Object mGrants;
    final android.content.Intent mIntent;
    private final long mMinimumChunkBytes;
    private final long mNetworkDownloadBytes;
    private final long mNetworkUploadBytes;
    int mWorkId;

    public JobWorkItem(android.content.Intent intent) {
        this(intent, -1L, -1L);
    }

    public JobWorkItem(android.content.Intent intent, long j, long j2) {
        this(intent, j, j2, -1L);
    }

    public JobWorkItem(android.content.Intent intent, long j, long j2, long j3) {
        this.mExtras = android.os.PersistableBundle.EMPTY;
        this.mIntent = intent;
        this.mNetworkDownloadBytes = j;
        this.mNetworkUploadBytes = j2;
        this.mMinimumChunkBytes = j3;
        enforceValidity(android.compat.Compatibility.isChangeEnabled(android.app.job.JobInfo.REJECT_NEGATIVE_NETWORK_ESTIMATES));
    }

    private JobWorkItem(android.app.job.JobWorkItem.Builder builder) {
        this.mDeliveryCount = builder.mDeliveryCount;
        this.mExtras = builder.mExtras.deepCopy();
        this.mIntent = builder.mIntent;
        this.mNetworkDownloadBytes = builder.mNetworkDownloadBytes;
        this.mNetworkUploadBytes = builder.mNetworkUploadBytes;
        this.mMinimumChunkBytes = builder.mMinimumNetworkChunkBytes;
    }

    public android.os.PersistableBundle getExtras() {
        return this.mExtras;
    }

    public android.content.Intent getIntent() {
        return this.mIntent;
    }

    public long getEstimatedNetworkDownloadBytes() {
        return this.mNetworkDownloadBytes;
    }

    public long getEstimatedNetworkUploadBytes() {
        return this.mNetworkUploadBytes;
    }

    public long getMinimumNetworkChunkBytes() {
        return this.mMinimumChunkBytes;
    }

    public int getDeliveryCount() {
        return this.mDeliveryCount;
    }

    public void bumpDeliveryCount() {
        this.mDeliveryCount++;
    }

    public void setWorkId(int i) {
        this.mWorkId = i;
    }

    public int getWorkId() {
        return this.mWorkId;
    }

    public void setGrants(java.lang.Object obj) {
        this.mGrants = obj;
    }

    public java.lang.Object getGrants() {
        return this.mGrants;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        sb.append("JobWorkItem{id=");
        sb.append(this.mWorkId);
        sb.append(" intent=");
        sb.append(this.mIntent);
        sb.append(" extras=");
        sb.append(this.mExtras);
        if (this.mNetworkDownloadBytes != -1) {
            sb.append(" downloadBytes=");
            sb.append(this.mNetworkDownloadBytes);
        }
        if (this.mNetworkUploadBytes != -1) {
            sb.append(" uploadBytes=");
            sb.append(this.mNetworkUploadBytes);
        }
        if (this.mMinimumChunkBytes != -1) {
            sb.append(" minimumChunkBytes=");
            sb.append(this.mMinimumChunkBytes);
        }
        if (this.mDeliveryCount != 0) {
            sb.append(" dcount=");
            sb.append(this.mDeliveryCount);
        }
        sb.append("}");
        return sb.toString();
    }

    public static final class Builder {
        private int mDeliveryCount;
        private android.content.Intent mIntent;
        private android.os.PersistableBundle mExtras = android.os.PersistableBundle.EMPTY;
        private long mNetworkDownloadBytes = -1;
        private long mNetworkUploadBytes = -1;
        private long mMinimumNetworkChunkBytes = -1;

        public android.app.job.JobWorkItem.Builder setDeliveryCount(int i) {
            this.mDeliveryCount = i;
            return this;
        }

        public android.app.job.JobWorkItem.Builder setExtras(android.os.PersistableBundle persistableBundle) {
            if (persistableBundle == null) {
                throw new java.lang.IllegalArgumentException("extras cannot be null");
            }
            this.mExtras = persistableBundle;
            return this;
        }

        public android.app.job.JobWorkItem.Builder setIntent(android.content.Intent intent) {
            this.mIntent = intent;
            return this;
        }

        public android.app.job.JobWorkItem.Builder setEstimatedNetworkBytes(long j, long j2) {
            if (j != -1 && j < 0) {
                throw new java.lang.IllegalArgumentException("Invalid network download bytes: " + j);
            }
            if (j2 != -1 && j2 < 0) {
                throw new java.lang.IllegalArgumentException("Invalid network upload bytes: " + j2);
            }
            this.mNetworkDownloadBytes = j;
            this.mNetworkUploadBytes = j2;
            return this;
        }

        public android.app.job.JobWorkItem.Builder setMinimumNetworkChunkBytes(long j) {
            if (j != -1 && j <= 0) {
                throw new java.lang.IllegalArgumentException("Minimum chunk size must be positive");
            }
            this.mMinimumNetworkChunkBytes = j;
            return this;
        }

        public android.app.job.JobWorkItem build() {
            return build(android.compat.Compatibility.isChangeEnabled(android.app.job.JobInfo.REJECT_NEGATIVE_NETWORK_ESTIMATES));
        }

        public android.app.job.JobWorkItem build(boolean z) {
            android.app.job.JobWorkItem jobWorkItem = new android.app.job.JobWorkItem(this);
            jobWorkItem.enforceValidity(z);
            return jobWorkItem;
        }
    }

    public void enforceValidity(boolean z) {
        long j;
        if (z) {
            if (this.mNetworkUploadBytes != -1 && this.mNetworkUploadBytes < 0) {
                throw new java.lang.IllegalArgumentException("Invalid network upload bytes: " + this.mNetworkUploadBytes);
            }
            if (this.mNetworkDownloadBytes != -1 && this.mNetworkDownloadBytes < 0) {
                throw new java.lang.IllegalArgumentException("Invalid network download bytes: " + this.mNetworkDownloadBytes);
            }
        }
        if (this.mNetworkUploadBytes != -1) {
            j = this.mNetworkUploadBytes + (this.mNetworkDownloadBytes == -1 ? 0L : this.mNetworkDownloadBytes);
        } else {
            j = this.mNetworkDownloadBytes;
        }
        if (this.mMinimumChunkBytes != -1 && j != -1 && this.mMinimumChunkBytes > j) {
            throw new java.lang.IllegalArgumentException("Minimum chunk size can't be greater than estimated network usage");
        }
        if (this.mMinimumChunkBytes != -1 && this.mMinimumChunkBytes <= 0) {
            throw new java.lang.IllegalArgumentException("Minimum chunk size must be positive");
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mIntent != null) {
            parcel.writeInt(1);
            this.mIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writePersistableBundle(this.mExtras);
        parcel.writeLong(this.mNetworkDownloadBytes);
        parcel.writeLong(this.mNetworkUploadBytes);
        parcel.writeLong(this.mMinimumChunkBytes);
        parcel.writeInt(this.mDeliveryCount);
        parcel.writeInt(this.mWorkId);
    }

    JobWorkItem(android.os.Parcel parcel) {
        if (parcel.readInt() != 0) {
            this.mIntent = android.content.Intent.CREATOR.createFromParcel(parcel);
        } else {
            this.mIntent = null;
        }
        android.os.PersistableBundle readPersistableBundle = parcel.readPersistableBundle();
        this.mExtras = readPersistableBundle == null ? android.os.PersistableBundle.EMPTY : readPersistableBundle;
        this.mNetworkDownloadBytes = parcel.readLong();
        this.mNetworkUploadBytes = parcel.readLong();
        this.mMinimumChunkBytes = parcel.readLong();
        this.mDeliveryCount = parcel.readInt();
        this.mWorkId = parcel.readInt();
    }
}
