package android.os;

/* loaded from: classes3.dex */
public final class ThreadLocalWorkSource {
    public static final int UID_NONE = -1;
    private static final java.lang.ThreadLocal<int[]> sWorkSourceUid = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: android.os.ThreadLocalWorkSource$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return android.os.ThreadLocalWorkSource.lambda$static$0();
        }
    });

    static /* synthetic */ int[] lambda$static$0() {
        return new int[]{-1};
    }

    public static int getUid() {
        return sWorkSourceUid.get()[0];
    }

    public static long setUid(int i) {
        long token = getToken();
        sWorkSourceUid.get()[0] = i;
        return token;
    }

    public static void restore(long j) {
        sWorkSourceUid.get()[0] = parseUidFromToken(j);
    }

    public static long clear() {
        return setUid(-1);
    }

    private static int parseUidFromToken(long j) {
        return (int) j;
    }

    private static long getToken() {
        return sWorkSourceUid.get()[0];
    }

    private ThreadLocalWorkSource() {
    }
}
