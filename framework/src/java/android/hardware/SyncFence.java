package android.hardware;

/* loaded from: classes.dex */
public final class SyncFence implements java.lang.AutoCloseable, android.os.Parcelable {
    public static final long SIGNAL_TIME_INVALID = -1;
    public static final long SIGNAL_TIME_PENDING = Long.MAX_VALUE;
    private final java.lang.Runnable mCloser;
    private long mNativePtr;
    private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createNonmalloced(android.hardware.SyncFence.class.getClassLoader(), nGetDestructor(), 4);
    public static final android.os.Parcelable.Creator<android.hardware.SyncFence> CREATOR = new android.os.Parcelable.Creator<android.hardware.SyncFence>() { // from class: android.hardware.SyncFence.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.SyncFence createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.SyncFence(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.SyncFence[] newArray(int i) {
            return new android.hardware.SyncFence[i];
        }
    };

    private static native long nCreate(int i);

    private static native long nGetDestructor();

    private static native int nGetFd(long j);

    private static native long nGetSignalTime(long j);

    private static native void nIncRef(long j);

    private static native boolean nIsValid(long j);

    private static native boolean nWait(long j, long j2);

    private SyncFence(int i) {
        this.mNativePtr = nCreate(i);
        this.mCloser = sRegistry.registerNativeAllocation(this, this.mNativePtr);
    }

    private SyncFence(android.os.Parcel parcel) {
        java.io.FileDescriptor fileDescriptor;
        if (!parcel.readBoolean()) {
            fileDescriptor = null;
        } else {
            fileDescriptor = parcel.readRawFileDescriptor();
        }
        if (fileDescriptor != null) {
            this.mNativePtr = nCreate(fileDescriptor.getInt$());
            this.mCloser = sRegistry.registerNativeAllocation(this, this.mNativePtr);
        } else {
            this.mCloser = new java.lang.Runnable() { // from class: android.hardware.SyncFence$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.SyncFence.lambda$new$0();
                }
            };
        }
    }

    static /* synthetic */ void lambda$new$0() {
    }

    public SyncFence(long j) {
        this.mNativePtr = j;
        if (j != 0) {
            this.mCloser = sRegistry.registerNativeAllocation(this, this.mNativePtr);
        } else {
            this.mCloser = new java.lang.Runnable() { // from class: android.hardware.SyncFence$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.SyncFence.lambda$new$1();
                }
            };
        }
    }

    static /* synthetic */ void lambda$new$1() {
    }

    public SyncFence(android.hardware.SyncFence syncFence) {
        this(syncFence.mNativePtr);
        if (this.mNativePtr != 0) {
            nIncRef(this.mNativePtr);
        }
    }

    private SyncFence() {
        this.mCloser = new java.lang.Runnable() { // from class: android.hardware.SyncFence$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.SyncFence.lambda$new$2();
            }
        };
    }

    static /* synthetic */ void lambda$new$2() {
    }

    public static android.hardware.SyncFence createEmpty() {
        return new android.hardware.SyncFence();
    }

    public static android.hardware.SyncFence create(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        return new android.hardware.SyncFence(parcelFileDescriptor.detachFd());
    }

    public static android.hardware.SyncFence adopt(int i) {
        return new android.hardware.SyncFence(i);
    }

    public android.os.ParcelFileDescriptor getFdDup() throws java.io.IOException {
        android.os.ParcelFileDescriptor fromFd;
        synchronized (this.mCloser) {
            int nGetFd = this.mNativePtr != 0 ? nGetFd(this.mNativePtr) : -1;
            if (nGetFd == -1) {
                throw new java.lang.IllegalStateException("Cannot dup the FD of an invalid SyncFence");
            }
            fromFd = android.os.ParcelFileDescriptor.fromFd(nGetFd);
        }
        return fromFd;
    }

    public boolean isValid() {
        boolean z;
        synchronized (this.mCloser) {
            z = this.mNativePtr != 0 && nIsValid(this.mNativePtr);
        }
        return z;
    }

    public boolean await(java.time.Duration duration) {
        long nanos;
        if (duration.isNegative()) {
            nanos = -1;
        } else {
            nanos = duration.toNanos();
        }
        return await(nanos);
    }

    public boolean awaitForever() {
        return await(-1L);
    }

    private boolean await(long j) {
        boolean z;
        synchronized (this.mCloser) {
            z = this.mNativePtr != 0 && nWait(this.mNativePtr, j);
        }
        return z;
    }

    public long getSignalTime() {
        long nGetSignalTime;
        synchronized (this.mCloser) {
            nGetSignalTime = this.mNativePtr != 0 ? nGetSignalTime(this.mNativePtr) : -1L;
        }
        return nGetSignalTime;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mCloser) {
            if (this.mNativePtr == 0) {
                return;
            }
            this.mNativePtr = 0L;
            this.mCloser.run();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 1;
    }

    public java.lang.Object getLock() {
        return this.mCloser;
    }

    public long getNativeFence() {
        return this.mNativePtr;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        synchronized (this.mCloser) {
            int nGetFd = this.mNativePtr != 0 ? nGetFd(this.mNativePtr) : -1;
            if (nGetFd == -1) {
                parcel.writeBoolean(false);
            } else {
                parcel.writeBoolean(true);
                java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
                fileDescriptor.setInt$(nGetFd);
                parcel.writeFileDescriptor(fileDescriptor);
            }
        }
    }
}
