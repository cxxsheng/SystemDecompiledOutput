package com.android.server.credentials;

/* loaded from: classes.dex */
public class PendingIntentResultHandler {
    public static boolean isValidResponse(android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        return providerPendingIntentResponse.getResultCode() == -1;
    }

    public static boolean isCancelledResponse(android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        return providerPendingIntentResponse.getResultCode() == 0;
    }

    public static android.service.credentials.BeginGetCredentialResponse extractResponseContent(android.content.Intent intent) {
        if (intent == null) {
            return null;
        }
        return (android.service.credentials.BeginGetCredentialResponse) intent.getParcelableExtra("android.service.credentials.extra.BEGIN_GET_CREDENTIAL_RESPONSE", android.service.credentials.BeginGetCredentialResponse.class);
    }

    public static android.credentials.CreateCredentialResponse extractCreateCredentialResponse(android.content.Intent intent) {
        if (intent == null) {
            return null;
        }
        return (android.credentials.CreateCredentialResponse) intent.getParcelableExtra("android.service.credentials.extra.CREATE_CREDENTIAL_RESPONSE", android.credentials.CreateCredentialResponse.class);
    }

    public static android.credentials.GetCredentialResponse extractGetCredentialResponse(android.content.Intent intent) {
        if (intent == null) {
            return null;
        }
        return (android.credentials.GetCredentialResponse) intent.getParcelableExtra("android.service.credentials.extra.GET_CREDENTIAL_RESPONSE", android.credentials.GetCredentialResponse.class);
    }

    public static android.credentials.CreateCredentialException extractCreateCredentialException(android.content.Intent intent) {
        if (intent == null) {
            return null;
        }
        return (android.credentials.CreateCredentialException) intent.getSerializableExtra("android.service.credentials.extra.CREATE_CREDENTIAL_EXCEPTION", android.credentials.CreateCredentialException.class);
    }

    public static android.credentials.GetCredentialException extractGetCredentialException(android.content.Intent intent) {
        if (intent == null) {
            return null;
        }
        return (android.credentials.GetCredentialException) intent.getSerializableExtra("android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION", android.credentials.GetCredentialException.class);
    }
}
