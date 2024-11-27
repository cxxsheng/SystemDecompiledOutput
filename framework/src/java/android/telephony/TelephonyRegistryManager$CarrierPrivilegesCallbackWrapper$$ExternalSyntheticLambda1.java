package android.telephony;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda1 implements java.util.function.Supplier {
    public final /* synthetic */ java.lang.ref.WeakReference f$0;

    public /* synthetic */ TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda1(java.lang.ref.WeakReference weakReference) {
        this.f$0 = weakReference;
    }

    @Override // java.util.function.Supplier
    public final java.lang.Object get() {
        return (android.telephony.TelephonyManager.CarrierPrivilegesCallback) this.f$0.get();
    }
}
