package android.os;

/* loaded from: classes3.dex */
public class PooledStringReader {
    private final android.os.Parcel mIn;
    private final java.lang.String[] mPool;

    public PooledStringReader(android.os.Parcel parcel) {
        this.mIn = parcel;
        this.mPool = new java.lang.String[parcel.readInt()];
    }

    public int getStringCount() {
        return this.mPool.length;
    }

    public java.lang.String readString() {
        int readInt = this.mIn.readInt();
        if (readInt >= 0) {
            return this.mPool[readInt];
        }
        java.lang.String readString = this.mIn.readString();
        this.mPool[(-readInt) - 1] = readString;
        return readString;
    }
}
