package com.android.server.pm;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda15 implements com.android.server.pm.ApkChecksums.Injector.Producer {
    public final /* synthetic */ com.android.server.pm.PackageManagerServiceInjector f$0;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda15(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector) {
        this.f$0 = packageManagerServiceInjector;
    }

    @Override // com.android.server.pm.ApkChecksums.Injector.Producer
    public final java.lang.Object produce() {
        return this.f$0.getIncrementalManager();
    }
}
