package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface DelegateConnectionMessageCallback {
    void onMessageReceived(android.telephony.ims.SipMessage sipMessage);

    void onMessageSendFailure(java.lang.String str, int i);

    void onMessageSent(java.lang.String str);
}
