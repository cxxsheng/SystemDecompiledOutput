package android.util;

/* loaded from: classes3.dex */
public final class MemoryIntArray implements android.os.Parcelable, java.io.Closeable {
    public static final android.os.Parcelable.Creator<android.util.MemoryIntArray> CREATOR = new android.os.Parcelable.Creator<android.util.MemoryIntArray>() { // from class: android.util.MemoryIntArray.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.util.MemoryIntArray createFromParcel(android.os.Parcel parcel) {
            try {
                return new android.util.MemoryIntArray(parcel);
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("Error unparceling MemoryIntArray");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.util.MemoryIntArray[] newArray(int i) {
            return new android.util.MemoryIntArray[i];
        }
    };
    private static final int MAX_SIZE = 1024;
    private static final java.lang.String TAG = "MemoryIntArray";
    private final dalvik.system.CloseGuard mCloseGuard;
    private int mFd;
    private final boolean mIsOwner;
    private final long mMemoryAddr;
    private final int mSize;

    private native void nativeClose(int i, long j, boolean z);

    private native int nativeCreate(java.lang.String str, int i);

    private native int nativeGet(int i, long j, int i2);

    private native long nativeOpen(int i, boolean z);

    private native void nativeSet(int i, long j, int i2, int i3);

    private native int nativeSize(int i);

    public MemoryIntArray(int i) throws java.io.IOException {
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mFd = -1;
        if (i > 1024) {
            throw new java.lang.IllegalArgumentException("Max size is 1024");
        }
        this.mIsOwner = true;
        this.mFd = nativeCreate(java.util.UUID.randomUUID().toString(), i);
        this.mMemoryAddr = nativeOpen(this.mFd, this.mIsOwner);
        this.mSize = nativeSize(this.mFd);
        this.mCloseGuard.open("MemoryIntArray.close");
    }

    private MemoryIntArray(android.os.Parcel parcel) throws java.io.IOException {
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mFd = -1;
        this.mIsOwner = false;
        android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readParcelable(null, android.os.ParcelFileDescriptor.class);
        if (parcelFileDescriptor == null) {
            throw new java.io.IOException("No backing file descriptor");
        }
        this.mFd = parcelFileDescriptor.detachFd();
        this.mMemoryAddr = nativeOpen(this.mFd, this.mIsOwner);
        this.mSize = nativeSize(this.mFd);
        this.mCloseGuard.open("MemoryIntArray.close");
    }

    public boolean isWritable() {
        enforceNotClosed();
        return this.mIsOwner;
    }

    public int get(int i) throws java.io.IOException {
        enforceNotClosed();
        enforceValidIndex(i);
        return nativeGet(this.mFd, this.mMemoryAddr, i);
    }

    public void set(int i, int i2) throws java.io.IOException {
        enforceNotClosed();
        enforceWritable();
        enforceValidIndex(i);
        nativeSet(this.mFd, this.mMemoryAddr, i, i2);
    }

    public int size() {
        enforceNotClosed();
        return this.mSize;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        if (!isClosed()) {
            nativeClose(this.mFd, this.mMemoryAddr, this.mIsOwner);
            this.mFd = -1;
            this.mCloseGuard.close();
        }
    }

    public boolean isClosed() {
        return this.mFd == -1;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            libcore.io.IoUtils.closeQuietly(this);
        } finally {
            super.finalize();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        try {
            android.os.ParcelFileDescriptor fromFd = android.os.ParcelFileDescriptor.fromFd(this.mFd);
            try {
                parcel.writeParcelable(fromFd, i);
                if (fromFd != null) {
                    fromFd.close();
                }
            } finally {
            }
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass() || this.mFd != ((android.util.MemoryIntArray) obj).mFd) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.mFd;
    }

    private void enforceNotClosed() {
        if (isClosed()) {
            throw new java.lang.IllegalStateException("cannot interact with a closed instance");
        }
    }

    private void enforceValidIndex(int i) {
        if (i < 0 || i > this.mSize - 1) {
            throw new java.lang.IndexOutOfBoundsException(i + " not between 0 and " + (this.mSize - 1));
        }
    }

    private void enforceWritable() {
        if (!isWritable()) {
            throw new java.lang.UnsupportedOperationException("array is not writable");
        }
    }

    public static int getMaxSize() {
        return 1024;
    }
}
