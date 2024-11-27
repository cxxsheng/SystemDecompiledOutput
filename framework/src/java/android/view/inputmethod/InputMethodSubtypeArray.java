package android.view.inputmethod;

/* loaded from: classes4.dex */
public class InputMethodSubtypeArray {
    private static final java.lang.String TAG = "InputMethodSubtypeArray";
    private volatile byte[] mCompressedData;
    private final int mCount;
    private volatile int mDecompressedSize;
    private volatile android.view.inputmethod.InputMethodSubtype[] mInstance;
    private final java.lang.Object mLockObject = new java.lang.Object();

    public InputMethodSubtypeArray(java.util.List<android.view.inputmethod.InputMethodSubtype> list) {
        if (list == null) {
            this.mCount = 0;
        } else {
            this.mCount = list.size();
            this.mInstance = (android.view.inputmethod.InputMethodSubtype[]) list.toArray(new android.view.inputmethod.InputMethodSubtype[this.mCount]);
        }
    }

    public InputMethodSubtypeArray(android.os.Parcel parcel) {
        this.mCount = parcel.readInt();
        if (this.mCount < 0) {
            throw new android.os.BadParcelableException("mCount must be non-negative.");
        }
        if (this.mCount > 0) {
            this.mDecompressedSize = parcel.readInt();
            this.mCompressedData = parcel.createByteArray();
        }
    }

    public void writeToParcel(android.os.Parcel parcel) {
        int length;
        if (this.mCount == 0) {
            parcel.writeInt(this.mCount);
            return;
        }
        byte[] bArr = this.mCompressedData;
        int i = this.mDecompressedSize;
        if (bArr == null && i == 0) {
            synchronized (this.mLockObject) {
                bArr = this.mCompressedData;
                i = this.mDecompressedSize;
                if (bArr == null && i == 0) {
                    byte[] marshall = marshall(this.mInstance);
                    byte[] compress = compress(marshall);
                    if (compress == null) {
                        android.util.Slog.i(TAG, "Failed to compress data.");
                        length = -1;
                    } else {
                        length = marshall.length;
                    }
                    this.mDecompressedSize = length;
                    this.mCompressedData = compress;
                    i = length;
                    bArr = compress;
                }
            }
        }
        if (bArr != null && i > 0) {
            parcel.writeInt(this.mCount);
            parcel.writeInt(i);
            parcel.writeByteArray(bArr);
        } else {
            android.util.Slog.i(TAG, "Unexpected state. Behaving as an empty array.");
            parcel.writeInt(0);
        }
    }

    public android.view.inputmethod.InputMethodSubtype get(int i) {
        if (i < 0 || this.mCount <= i) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr = this.mInstance;
        if (inputMethodSubtypeArr == null) {
            synchronized (this.mLockObject) {
                inputMethodSubtypeArr = this.mInstance;
                if (inputMethodSubtypeArr == null) {
                    byte[] decompress = decompress(this.mCompressedData, this.mDecompressedSize);
                    this.mCompressedData = null;
                    this.mDecompressedSize = 0;
                    if (decompress != null) {
                        inputMethodSubtypeArr = unmarshall(decompress);
                    } else {
                        android.util.Slog.e(TAG, "Failed to decompress data. Returns null as fallback.");
                        inputMethodSubtypeArr = new android.view.inputmethod.InputMethodSubtype[this.mCount];
                    }
                    this.mInstance = inputMethodSubtypeArr;
                }
            }
        }
        return inputMethodSubtypeArr[i];
    }

    public int getCount() {
        return this.mCount;
    }

    private static byte[] marshall(android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr) {
        android.os.Parcel parcel;
        try {
            parcel = android.os.Parcel.obtain();
        } catch (java.lang.Throwable th) {
            th = th;
            parcel = null;
        }
        try {
            parcel.writeTypedArray(inputMethodSubtypeArr, 0);
            byte[] marshall = parcel.marshall();
            if (parcel != null) {
                parcel.recycle();
            }
            return marshall;
        } catch (java.lang.Throwable th2) {
            th = th2;
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
    }

    private static android.view.inputmethod.InputMethodSubtype[] unmarshall(byte[] bArr) {
        android.os.Parcel parcel;
        try {
            parcel = android.os.Parcel.obtain();
        } catch (java.lang.Throwable th) {
            th = th;
            parcel = null;
        }
        try {
            parcel.unmarshall(bArr, 0, bArr.length);
            parcel.setDataPosition(0);
            android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr = (android.view.inputmethod.InputMethodSubtype[]) parcel.createTypedArray(android.view.inputmethod.InputMethodSubtype.CREATOR);
            if (parcel != null) {
                parcel.recycle();
            }
            return inputMethodSubtypeArr;
        } catch (java.lang.Throwable th2) {
            th = th2;
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
    }

    private static byte[] compress(byte[] bArr) {
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            try {
                java.util.zip.GZIPOutputStream gZIPOutputStream = new java.util.zip.GZIPOutputStream(byteArrayOutputStream);
                try {
                    gZIPOutputStream.write(bArr);
                    gZIPOutputStream.finish();
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    gZIPOutputStream.close();
                    byteArrayOutputStream.close();
                    return byteArray;
                } finally {
                }
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to compress the data.", e);
            return null;
        }
    }

    private static byte[] decompress(byte[] bArr, int i) {
        try {
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            try {
                java.util.zip.GZIPInputStream gZIPInputStream = new java.util.zip.GZIPInputStream(byteArrayInputStream);
                try {
                    byte[] bArr2 = new byte[i];
                    int i2 = 0;
                    while (i2 < i) {
                        int read = gZIPInputStream.read(bArr2, i2, i - i2);
                        if (read < 0) {
                            break;
                        }
                        i2 += read;
                    }
                    if (i != i2) {
                        gZIPInputStream.close();
                        byteArrayInputStream.close();
                        return null;
                    }
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return bArr2;
                } finally {
                }
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to decompress the data.", e);
            return null;
        }
    }
}
