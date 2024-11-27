package android.os;

/* loaded from: classes3.dex */
public class PooledStringWriter {
    private int mNext;
    private final android.os.Parcel mOut;
    private final java.util.HashMap<java.lang.String, java.lang.Integer> mPool = new java.util.HashMap<>();
    private int mStart;

    public PooledStringWriter(android.os.Parcel parcel) {
        this.mOut = parcel;
        this.mStart = parcel.dataPosition();
        parcel.writeInt(0);
    }

    public void writeString(java.lang.String str) {
        java.lang.Integer num = this.mPool.get(str);
        if (num != null) {
            this.mOut.writeInt(num.intValue());
            return;
        }
        this.mPool.put(str, java.lang.Integer.valueOf(this.mNext));
        this.mOut.writeInt(-(this.mNext + 1));
        this.mOut.writeString(str);
        this.mNext++;
    }

    public int getStringCount() {
        return this.mPool.size();
    }

    public void finish() {
        int dataPosition = this.mOut.dataPosition();
        this.mOut.setDataPosition(this.mStart);
        this.mOut.writeInt(this.mNext);
        this.mOut.setDataPosition(dataPosition);
    }
}
