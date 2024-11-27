package android.media.tv.tuner;

/* loaded from: classes2.dex */
public final class TunerUtils {
    public static int getFilterSubtype(int i, int i2) {
        if (i == 1) {
            switch (i2) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 4;
                case 4:
                    return 5;
                case 6:
                    return 7;
                case 7:
                    return 3;
                case 8:
                    return 6;
                case 9:
                    return 8;
            }
        }
        if (i == 2) {
            switch (i2) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 4;
                case 4:
                    return 5;
                case 5:
                    return 7;
                case 6:
                    return 6;
                case 10:
                    return 3;
            }
        }
        if (i == 4) {
            switch (i2) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 11:
                    return 2;
                case 12:
                    return 3;
                case 13:
                    return 4;
                case 14:
                    return 5;
            }
        }
        if (i == 8) {
            switch (i2) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 14:
                    return 3;
                case 15:
                    return 2;
            }
        }
        if (i == 16) {
            switch (i2) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 14:
                    return 3;
                case 16:
                    return 2;
            }
        }
        throw new java.lang.IllegalArgumentException("Invalid filter types. Main type=" + i + ", subtype=" + i2);
    }

    public static void throwExceptionForResult(int i, java.lang.String str) {
        if (str == null) {
            str = "";
        }
        switch (i) {
            case 0:
                return;
            case 1:
                throw new java.lang.IllegalStateException("Invalid state: resource unavailable. " + str);
            case 2:
                throw new java.lang.IllegalStateException("Invalid state: not initialized. " + str);
            case 3:
                throw new java.lang.IllegalStateException(str);
            case 4:
                throw new java.lang.IllegalArgumentException(str);
            case 5:
                throw new java.lang.OutOfMemoryError(str);
            case 6:
                throw new java.lang.RuntimeException("Unknown error" + str);
            default:
                throw new java.lang.RuntimeException("Unexpected result " + i + ".  " + str);
        }
    }

    public static void checkResourceState(java.lang.String str, boolean z) {
        if (z) {
            throw new java.lang.IllegalStateException(str + " has been closed");
        }
    }

    public static void checkResourceAccessible(java.lang.String str, boolean z) {
        if (!z) {
            throw new java.lang.IllegalStateException(str + " is inaccessible");
        }
    }

    private TunerUtils() {
    }
}
