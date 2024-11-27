package android.util;

/* loaded from: classes3.dex */
public class LogPrinter implements android.util.Printer {
    private final int mBuffer;
    private final int mPriority;
    private final java.lang.String mTag;

    public LogPrinter(int i, java.lang.String str) {
        this.mPriority = i;
        this.mTag = str;
        this.mBuffer = 0;
    }

    public LogPrinter(int i, java.lang.String str, int i2) {
        this.mPriority = i;
        this.mTag = str;
        this.mBuffer = i2;
    }

    @Override // android.util.Printer
    public void println(java.lang.String str) {
        android.util.Log.println_native(this.mBuffer, this.mPriority, this.mTag, str);
    }
}
