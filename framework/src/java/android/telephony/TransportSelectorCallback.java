package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface TransportSelectorCallback {
    void onCreated(android.telephony.DomainSelector domainSelector);

    void onSelectionTerminated(int i);

    void onWlanSelected(boolean z);

    void onWwanSelected(java.util.function.Consumer<android.telephony.WwanSelectorCallback> consumer);
}
