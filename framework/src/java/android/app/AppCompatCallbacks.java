package android.app;

/* loaded from: classes.dex */
public final class AppCompatCallbacks implements android.compat.Compatibility.BehaviorChangeDelegate {
    private final com.android.internal.compat.ChangeReporter mChangeReporter;
    private final long[] mDisabledChanges;

    public static void install(long[] jArr) {
        android.compat.Compatibility.setBehaviorChangeDelegate(new android.app.AppCompatCallbacks(jArr));
    }

    private AppCompatCallbacks(long[] jArr) {
        this.mDisabledChanges = java.util.Arrays.copyOf(jArr, jArr.length);
        java.util.Arrays.sort(this.mDisabledChanges);
        this.mChangeReporter = new com.android.internal.compat.ChangeReporter(1);
    }

    public void onChangeReported(long j) {
        reportChange(j, 3);
    }

    public boolean isChangeEnabled(long j) {
        if (java.util.Arrays.binarySearch(this.mDisabledChanges, j) < 0) {
            reportChange(j, 1);
            return true;
        }
        reportChange(j, 2);
        return false;
    }

    private void reportChange(long j, int i) {
        this.mChangeReporter.reportChange(android.os.Process.myUid(), j, i);
    }
}
