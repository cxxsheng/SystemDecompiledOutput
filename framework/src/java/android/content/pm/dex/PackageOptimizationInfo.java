package android.content.pm.dex;

/* loaded from: classes.dex */
public class PackageOptimizationInfo {
    private final int mCompilationFilter;
    private final int mCompilationReason;

    public PackageOptimizationInfo(int i, int i2) {
        this.mCompilationReason = i2;
        this.mCompilationFilter = i;
    }

    public int getCompilationReason() {
        return this.mCompilationReason;
    }

    public int getCompilationFilter() {
        return this.mCompilationFilter;
    }

    public static android.content.pm.dex.PackageOptimizationInfo createWithNoInfo() {
        return new android.content.pm.dex.PackageOptimizationInfo(-1, -1);
    }
}
