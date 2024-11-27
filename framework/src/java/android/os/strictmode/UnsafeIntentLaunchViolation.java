package android.os.strictmode;

/* loaded from: classes3.dex */
public final class UnsafeIntentLaunchViolation extends android.os.strictmode.Violation {
    private transient android.content.Intent mIntent;

    public UnsafeIntentLaunchViolation(android.content.Intent intent) {
        super("Launch of unsafe intent: " + intent);
        this.mIntent = (android.content.Intent) java.util.Objects.requireNonNull(intent);
    }

    public UnsafeIntentLaunchViolation(android.content.Intent intent, java.lang.String str) {
        super(str);
        this.mIntent = (android.content.Intent) java.util.Objects.requireNonNull(intent);
    }

    public android.content.Intent getIntent() {
        return this.mIntent;
    }
}
