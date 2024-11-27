package com.android.server.appop;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda3 implements com.android.internal.util.function.QuadFunction {
    public final /* synthetic */ com.android.server.appop.AppOpsService f$0;

    public /* synthetic */ AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda3(com.android.server.appop.AppOpsService appOpsService) {
        this.f$0 = appOpsService;
    }

    public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
        java.lang.Void finishProxyOperationImpl;
        finishProxyOperationImpl = this.f$0.finishProxyOperationImpl((android.os.IBinder) obj, ((java.lang.Integer) obj2).intValue(), (android.content.AttributionSource) obj3, ((java.lang.Boolean) obj4).booleanValue());
        return finishProxyOperationImpl;
    }
}
