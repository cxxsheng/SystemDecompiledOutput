package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface SipDelegate {
    void cleanupSession(java.lang.String str);

    void notifyMessageReceiveError(java.lang.String str, int i);

    void notifyMessageReceived(java.lang.String str);

    void sendMessage(android.telephony.ims.SipMessage sipMessage, long j);
}
