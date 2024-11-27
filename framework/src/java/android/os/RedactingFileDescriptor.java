package android.os;

/* loaded from: classes3.dex */
public class RedactingFileDescriptor {
    private static final boolean DEBUG = true;
    private static final java.lang.String TAG = "RedactingFileDescriptor";
    private final android.os.ProxyFileDescriptorCallback mCallback = new android.os.ProxyFileDescriptorCallback() { // from class: android.os.RedactingFileDescriptor.1
        @Override // android.os.ProxyFileDescriptorCallback
        public long onGetSize() throws android.system.ErrnoException {
            return android.system.Os.fstat(android.os.RedactingFileDescriptor.this.mInner).st_size;
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public int onRead(long j, int i, byte[] bArr) throws android.system.ErrnoException {
            int pread;
            android.os.RedactingFileDescriptor.AnonymousClass1 anonymousClass1 = this;
            long j2 = j;
            int i2 = i;
            byte b = 0;
            int i3 = 0;
            while (i3 < i2) {
                try {
                    pread = android.system.Os.pread(android.os.RedactingFileDescriptor.this.mInner, bArr, i3, i2 - i3, j2 + i3);
                } catch (java.io.InterruptedIOException e) {
                    i3 += e.bytesTransferred;
                }
                if (pread == 0) {
                    break;
                }
                i3 += pread;
            }
            long[] jArr = android.os.RedactingFileDescriptor.this.mRedactRanges;
            int i4 = 0;
            while (i4 < jArr.length) {
                long max = java.lang.Math.max(j2, jArr[i4]);
                long min = java.lang.Math.min(i2 + j2, jArr[i4 + 1]);
                long j3 = max;
                while (j3 < min) {
                    bArr[(int) (j3 - j2)] = b;
                    j3++;
                    i4 = i4;
                }
                int i5 = i4;
                long[] jArr2 = android.os.RedactingFileDescriptor.this.mFreeOffsets;
                int length = jArr2.length;
                int i6 = b;
                while (i6 < length) {
                    int i7 = i3;
                    long j4 = jArr2[i6];
                    long max2 = java.lang.Math.max(j4, max);
                    long min2 = java.lang.Math.min(j4 + 4, min);
                    while (max2 < min2) {
                        bArr[(int) (max2 - j2)] = (byte) "free".charAt((int) (max2 - j4));
                        max2++;
                        j2 = j;
                        jArr = jArr;
                    }
                    i6++;
                    j2 = j;
                    i3 = i7;
                    jArr = jArr;
                }
                i4 = i5 + 2;
                anonymousClass1 = this;
                j2 = j;
                i2 = i;
                b = 0;
            }
            return i3;
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public int onWrite(long j, int i, byte[] bArr) throws android.system.ErrnoException {
            int pwrite;
            int i2 = 0;
            while (i2 < i) {
                try {
                    pwrite = android.system.Os.pwrite(android.os.RedactingFileDescriptor.this.mInner, bArr, i2, i - i2, j + i2);
                } catch (java.io.InterruptedIOException e) {
                    i2 += e.bytesTransferred;
                }
                if (pwrite == 0) {
                    break;
                }
                i2 += pwrite;
            }
            android.os.RedactingFileDescriptor.this.mRedactRanges = android.os.RedactingFileDescriptor.removeRange(android.os.RedactingFileDescriptor.this.mRedactRanges, j, i2 + j);
            return i2;
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public void onFsync() throws android.system.ErrnoException {
            android.system.Os.fsync(android.os.RedactingFileDescriptor.this.mInner);
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public void onRelease() {
            android.util.Slog.v(android.os.RedactingFileDescriptor.TAG, "onRelease()");
            libcore.io.IoUtils.closeQuietly(android.os.RedactingFileDescriptor.this.mInner);
        }
    };
    private volatile long[] mFreeOffsets;
    private java.io.FileDescriptor mInner;
    private android.os.ParcelFileDescriptor mOuter;
    private volatile long[] mRedactRanges;

    private RedactingFileDescriptor(android.content.Context context, java.io.File file, int i, long[] jArr, long[] jArr2) throws java.io.IOException {
        this.mInner = null;
        this.mOuter = null;
        this.mRedactRanges = checkRangesArgument(jArr);
        this.mFreeOffsets = jArr2;
        try {
            try {
                this.mInner = android.system.Os.open(file.getAbsolutePath(), android.os.FileUtils.translateModePfdToPosix(i), 0);
                this.mOuter = ((android.os.storage.StorageManager) context.getSystemService(android.os.storage.StorageManager.class)).openProxyFileDescriptor(i, this.mCallback);
            } catch (android.system.ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } catch (java.io.IOException e2) {
            libcore.io.IoUtils.closeQuietly(this.mInner);
            libcore.io.IoUtils.closeQuietly(this.mOuter);
            throw e2;
        }
    }

    private static long[] checkRangesArgument(long[] jArr) {
        if (jArr.length % 2 != 0) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < jArr.length - 1; i += 2) {
            if (jArr[i] > jArr[i + 1]) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        return jArr;
    }

    public static android.os.ParcelFileDescriptor open(android.content.Context context, java.io.File file, int i, long[] jArr, long[] jArr2) throws java.io.IOException {
        return new android.os.RedactingFileDescriptor(context, file, i, jArr, jArr2).mOuter;
    }

    public static long[] removeRange(long[] jArr, long j, long j2) {
        if (j == j2) {
            return jArr;
        }
        if (j > j2) {
            throw new java.lang.IllegalArgumentException();
        }
        long[] jArr2 = libcore.util.EmptyArray.LONG;
        for (int i = 0; i < jArr.length; i += 2) {
            if (j > jArr[i] || j2 < jArr[i + 1]) {
                if (j >= jArr[i]) {
                    int i2 = i + 1;
                    if (j2 <= jArr[i2]) {
                        jArr2 = java.util.Arrays.copyOf(jArr2, jArr2.length + 4);
                        jArr2[jArr2.length - 4] = jArr[i];
                        jArr2[jArr2.length - 3] = j;
                        jArr2[jArr2.length - 2] = j2;
                        jArr2[jArr2.length - 1] = jArr[i2];
                    }
                }
                jArr2 = java.util.Arrays.copyOf(jArr2, jArr2.length + 2);
                if (j2 >= jArr[i] && j2 <= jArr[i + 1]) {
                    jArr2[jArr2.length - 2] = java.lang.Math.max(jArr[i], j2);
                } else {
                    jArr2[jArr2.length - 2] = jArr[i];
                }
                if (j >= jArr[i]) {
                    int i3 = i + 1;
                    if (j <= jArr[i3]) {
                        jArr2[jArr2.length - 1] = java.lang.Math.min(jArr[i3], j);
                    }
                }
                jArr2[jArr2.length - 1] = jArr[i + 1];
            }
        }
        return jArr2;
    }
}
