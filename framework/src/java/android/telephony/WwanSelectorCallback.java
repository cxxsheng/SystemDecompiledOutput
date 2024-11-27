package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface WwanSelectorCallback {
    void onDomainSelected(int i, boolean z);

    void onRequestEmergencyNetworkScan(java.util.List<java.lang.Integer> list, int i, boolean z, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<android.telephony.EmergencyRegistrationResult> consumer);
}
