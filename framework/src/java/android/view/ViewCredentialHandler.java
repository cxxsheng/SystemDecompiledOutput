package android.view;

/* loaded from: classes4.dex */
public class ViewCredentialHandler {
    private android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> mCallback;
    private android.credentials.GetCredentialRequest mRequest;

    ViewCredentialHandler(android.credentials.GetCredentialRequest getCredentialRequest, android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver) {
        this.mRequest = getCredentialRequest;
        this.mCallback = outcomeReceiver;
    }

    public android.credentials.GetCredentialRequest getRequest() {
        return this.mRequest;
    }

    public android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> getCallback() {
        return this.mCallback;
    }
}
