package android.os.strictmode;

/* loaded from: classes3.dex */
public final class ImplicitDirectBootViolation extends android.os.strictmode.Violation {
    public ImplicitDirectBootViolation() {
        super("Implicitly relying on automatic Direct Boot filtering; request explicit filtering with PackageManager.MATCH_DIRECT_BOOT flags");
    }
}
