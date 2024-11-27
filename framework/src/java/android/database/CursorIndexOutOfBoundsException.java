package android.database;

/* loaded from: classes.dex */
public class CursorIndexOutOfBoundsException extends java.lang.IndexOutOfBoundsException {
    public CursorIndexOutOfBoundsException(int i, int i2) {
        super("Index " + i + " requested, with a size of " + i2);
    }

    public CursorIndexOutOfBoundsException(java.lang.String str) {
        super(str);
    }
}
