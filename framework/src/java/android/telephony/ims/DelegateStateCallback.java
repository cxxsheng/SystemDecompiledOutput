package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface DelegateStateCallback {
    void onConfigurationChanged(android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration);

    void onCreated(android.telephony.ims.stub.SipDelegate sipDelegate, java.util.Set<android.telephony.ims.FeatureTagState> set);

    void onDestroyed(int i);

    void onFeatureTagRegistrationChanged(android.telephony.ims.DelegateRegistrationState delegateRegistrationState);

    @java.lang.Deprecated
    void onImsConfigurationChanged(android.telephony.ims.SipDelegateImsConfiguration sipDelegateImsConfiguration);
}
