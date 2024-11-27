package android.database;

/* loaded from: classes.dex */
public class CursorWindow extends android.database.sqlite.SQLiteClosable implements android.os.Parcelable {
    private static final java.lang.String STATS_TAG = "CursorWindowStats";
    private final dalvik.system.CloseGuard mCloseGuard;
    private final java.lang.String mName;
    private int mStartPos;
    public long mWindowPtr;
    private static int sCursorWindowSize = -1;
    public static final android.os.Parcelable.Creator<android.database.CursorWindow> CREATOR = new android.os.Parcelable.Creator<android.database.CursorWindow>() { // from class: android.database.CursorWindow.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.database.CursorWindow createFromParcel(android.os.Parcel parcel) {
            return new android.database.CursorWindow(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.database.CursorWindow[] newArray(int i) {
            return new android.database.CursorWindow[i];
        }
    };

    @dalvik.annotation.optimization.FastNative
    private static native boolean nativeAllocRow(long j);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeClear(long j);

    private static native void nativeCopyStringToBuffer(long j, int i, int i2, android.database.CharArrayBuffer charArrayBuffer);

    private static native long nativeCreate(java.lang.String str, int i);

    private static native long nativeCreateFromParcel(android.os.Parcel parcel);

    private static native void nativeDispose(long j);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeFreeLastRow(long j);

    private static native byte[] nativeGetBlob(long j, int i, int i2);

    @dalvik.annotation.optimization.FastNative
    private static native double nativeGetDouble(long j, int i, int i2);

    @dalvik.annotation.optimization.FastNative
    private static native long nativeGetLong(long j, int i, int i2);

    private static native java.lang.String nativeGetName(long j);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeGetNumRows(long j);

    private static native java.lang.String nativeGetString(long j, int i, int i2);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeGetType(long j, int i, int i2);

    private static native boolean nativePutBlob(long j, byte[] bArr, int i, int i2);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nativePutDouble(long j, double d, int i, int i2);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nativePutLong(long j, long j2, int i, int i2);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nativePutNull(long j, int i, int i2);

    private static native boolean nativePutString(long j, java.lang.String str, int i, int i2);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nativeSetNumColumns(long j, int i);

    private static native void nativeWriteToParcel(long j, android.os.Parcel parcel);

    public CursorWindow(java.lang.String str) {
        this(str, getCursorWindowSize());
    }

    public CursorWindow(java.lang.String str, long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("Window size cannot be less than 0");
        }
        this.mStartPos = 0;
        this.mName = (str == null || str.length() == 0) ? "<unnamed>" : str;
        this.mWindowPtr = nativeCreate(this.mName, (int) j);
        if (this.mWindowPtr == 0) {
            throw new java.lang.AssertionError();
        }
        this.mCloseGuard = createCloseGuard();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @java.lang.Deprecated
    public CursorWindow(boolean z) {
        this((java.lang.String) null);
    }

    private CursorWindow(android.os.Parcel parcel) {
        this.mStartPos = parcel.readInt();
        this.mWindowPtr = nativeCreateFromParcel(parcel);
        if (this.mWindowPtr == 0) {
            throw new java.lang.AssertionError();
        }
        this.mName = nativeGetName(this.mWindowPtr);
        this.mCloseGuard = createCloseGuard();
    }

    private dalvik.system.CloseGuard createCloseGuard() {
        dalvik.system.CloseGuard closeGuard = dalvik.system.CloseGuard.get();
        closeGuard.open("CursorWindow.close");
        return closeGuard;
    }

    private dalvik.system.CloseGuard createCloseGuard$ravenwood() {
        return null;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            dispose();
        } finally {
            super.finalize();
        }
    }

    private void dispose() {
        if (this.mCloseGuard != null) {
            this.mCloseGuard.close();
        }
        if (this.mWindowPtr != 0) {
            nativeDispose(this.mWindowPtr);
            this.mWindowPtr = 0L;
        }
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public void clear() {
        acquireReference();
        try {
            this.mStartPos = 0;
            nativeClear(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public int getStartPosition() {
        return this.mStartPos;
    }

    public void setStartPosition(int i) {
        this.mStartPos = i;
    }

    public int getNumRows() {
        acquireReference();
        try {
            return nativeGetNumRows(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public boolean setNumColumns(int i) {
        acquireReference();
        try {
            return nativeSetNumColumns(this.mWindowPtr, i);
        } finally {
            releaseReference();
        }
    }

    public boolean allocRow() {
        acquireReference();
        try {
            return nativeAllocRow(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public void freeLastRow() {
        acquireReference();
        try {
            nativeFreeLastRow(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    @java.lang.Deprecated
    public boolean isNull(int i, int i2) {
        return getType(i, i2) == 0;
    }

    @java.lang.Deprecated
    public boolean isBlob(int i, int i2) {
        int type = getType(i, i2);
        return type == 4 || type == 0;
    }

    @java.lang.Deprecated
    public boolean isLong(int i, int i2) {
        return getType(i, i2) == 1;
    }

    @java.lang.Deprecated
    public boolean isFloat(int i, int i2) {
        return getType(i, i2) == 2;
    }

    @java.lang.Deprecated
    public boolean isString(int i, int i2) {
        int type = getType(i, i2);
        return type == 3 || type == 0;
    }

    public int getType(int i, int i2) {
        acquireReference();
        try {
            return nativeGetType(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public byte[] getBlob(int i, int i2) {
        acquireReference();
        try {
            return nativeGetBlob(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public java.lang.String getString(int i, int i2) {
        acquireReference();
        try {
            return nativeGetString(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public void copyStringToBuffer(int i, int i2, android.database.CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer == null) {
            throw new java.lang.IllegalArgumentException("CharArrayBuffer should not be null");
        }
        acquireReference();
        try {
            nativeCopyStringToBuffer(this.mWindowPtr, i - this.mStartPos, i2, charArrayBuffer);
        } finally {
            releaseReference();
        }
    }

    public long getLong(int i, int i2) {
        acquireReference();
        try {
            return nativeGetLong(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public double getDouble(int i, int i2) {
        acquireReference();
        try {
            return nativeGetDouble(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public short getShort(int i, int i2) {
        return (short) getLong(i, i2);
    }

    public int getInt(int i, int i2) {
        return (int) getLong(i, i2);
    }

    public float getFloat(int i, int i2) {
        return (float) getDouble(i, i2);
    }

    public boolean putBlob(byte[] bArr, int i, int i2) {
        acquireReference();
        try {
            return nativePutBlob(this.mWindowPtr, bArr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public boolean putString(java.lang.String str, int i, int i2) {
        acquireReference();
        try {
            return nativePutString(this.mWindowPtr, str, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public boolean putLong(long j, int i, int i2) {
        acquireReference();
        try {
            return nativePutLong(this.mWindowPtr, j, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public boolean putDouble(double d, int i, int i2) {
        acquireReference();
        try {
            return nativePutDouble(this.mWindowPtr, d, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public boolean putNull(int i, int i2) {
        acquireReference();
        try {
            return nativePutNull(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public static android.database.CursorWindow newFromParcel(android.os.Parcel parcel) {
        return CREATOR.createFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        acquireReference();
        try {
            parcel.writeInt(this.mStartPos);
            nativeWriteToParcel(this.mWindowPtr, parcel);
            releaseReference();
            if ((i & 1) != 0) {
            }
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.sqlite.SQLiteClosable
    protected void onAllReferencesReleased() {
        dispose();
    }

    private static int getCursorWindowSize() {
        if (sCursorWindowSize < 0) {
            sCursorWindowSize = android.content.res.Resources.getSystem().getInteger(com.android.internal.R.integer.config_cursorWindowSize) * 1024;
        }
        return sCursorWindowSize;
    }

    private static int getCursorWindowSize$ravenwood() {
        return 1024;
    }

    public java.lang.String toString() {
        return getName() + " {" + java.lang.Long.toHexString(this.mWindowPtr) + "}";
    }
}
