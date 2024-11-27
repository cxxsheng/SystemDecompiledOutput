package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class UserSelectionResult {
    private final java.lang.String mEntryKey;
    private final java.lang.String mEntrySubkey;
    private final java.lang.String mProviderId;
    private android.credentials.selection.ProviderPendingIntentResponse mProviderPendingIntentResponse;

    public static void sendUserSelectionResult(android.os.ResultReceiver resultReceiver, android.credentials.selection.UserSelectionResult userSelectionResult) {
        android.credentials.selection.UserSelectionDialogResult userSelectionDialogResult = userSelectionResult.toUserSelectionDialogResult();
        android.os.Bundle bundle = new android.os.Bundle();
        android.credentials.selection.UserSelectionDialogResult.addToBundle(userSelectionDialogResult, bundle);
        resultReceiver.send(2, bundle);
    }

    public UserSelectionResult(java.lang.String str, java.lang.String str2, java.lang.String str3, android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        this.mProviderId = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        this.mEntryKey = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str2);
        this.mEntrySubkey = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str3);
        this.mProviderPendingIntentResponse = providerPendingIntentResponse;
    }

    public java.lang.String getProviderId() {
        return this.mProviderId;
    }

    public java.lang.String getEntryKey() {
        return this.mEntryKey;
    }

    public java.lang.String getEntrySubkey() {
        return this.mEntrySubkey;
    }

    public android.credentials.selection.ProviderPendingIntentResponse getPendingIntentProviderResponse() {
        return this.mProviderPendingIntentResponse;
    }

    android.credentials.selection.UserSelectionDialogResult toUserSelectionDialogResult() {
        return new android.credentials.selection.UserSelectionDialogResult(null, this.mProviderId, this.mEntryKey, this.mEntrySubkey, this.mProviderPendingIntentResponse);
    }
}
