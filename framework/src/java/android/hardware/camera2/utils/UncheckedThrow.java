package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class UncheckedThrow {
    public static void throwAnyException(java.lang.Exception exc) {
        throwAnyImpl(exc);
    }

    public static void throwAnyException(java.lang.Throwable th) {
        throwAnyImpl(th);
    }

    private static <T extends java.lang.Throwable> void throwAnyImpl(java.lang.Throwable th) throws java.lang.Throwable {
        throw th;
    }
}
