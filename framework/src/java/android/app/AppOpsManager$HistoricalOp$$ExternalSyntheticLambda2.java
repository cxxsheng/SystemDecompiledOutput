package android.app;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsManager$HistoricalOp$$ExternalSyntheticLambda2 implements java.util.function.Supplier {
    public final /* synthetic */ android.app.AppOpsManager.HistoricalOp f$0;

    public /* synthetic */ AppOpsManager$HistoricalOp$$ExternalSyntheticLambda2(android.app.AppOpsManager.HistoricalOp historicalOp) {
        this.f$0 = historicalOp;
    }

    @Override // java.util.function.Supplier
    public final java.lang.Object get() {
        android.util.LongSparseLongArray orCreateAccessDuration;
        orCreateAccessDuration = this.f$0.getOrCreateAccessDuration();
        return orCreateAccessDuration;
    }
}