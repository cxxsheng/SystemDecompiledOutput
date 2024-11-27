package com.android.server.stats.pull;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class StatsPullAtomService$$ExternalSyntheticLambda0 implements java.util.function.Consumer {
    public final /* synthetic */ java.util.concurrent.CompletableFuture f$0;

    @Override // java.util.function.Consumer
    public final void accept(java.lang.Object obj) {
        this.f$0.complete((android.app.AppOpsManager.HistoricalOps) obj);
    }
}
