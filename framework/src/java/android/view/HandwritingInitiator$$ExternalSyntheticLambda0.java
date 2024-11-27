package android.view;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class HandwritingInitiator$$ExternalSyntheticLambda0 implements java.util.concurrent.Executor {
    public final /* synthetic */ android.view.View f$0;

    public /* synthetic */ HandwritingInitiator$$ExternalSyntheticLambda0(android.view.View view) {
        this.f$0 = view;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(java.lang.Runnable runnable) {
        this.f$0.post(runnable);
    }
}
