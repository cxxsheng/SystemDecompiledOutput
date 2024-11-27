package android.util;

/* loaded from: classes3.dex */
public interface Dumpable {
    void dump(java.io.PrintWriter printWriter, java.lang.String[] strArr);

    default java.lang.String getDumpableName() {
        return getClass().getName();
    }
}
