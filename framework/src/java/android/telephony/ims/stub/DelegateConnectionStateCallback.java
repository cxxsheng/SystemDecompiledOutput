package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface DelegateConnectionStateCallback {
    void onConfigurationChanged(android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration);

    void onCreated(android.telephony.ims.SipDelegateConnection sipDelegateConnection);

    void onDestroyed(int i);

    void onFeatureTagStatusChanged(android.telephony.ims.DelegateRegistrationState delegateRegistrationState, java.util.Set<android.telephony.ims.FeatureTagState> set);

    @java.lang.Deprecated
    default void onImsConfigurationChanged(android.telephony.ims.SipDelegateImsConfiguration sipDelegateImsConfiguration) {
        onConfigurationChanged(sipDelegateImsConfiguration.toNewConfig());
    }
}
