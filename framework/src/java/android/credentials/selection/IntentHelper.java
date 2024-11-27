package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class IntentHelper {
    public static android.credentials.selection.CancelSelectionRequest extractCancelUiRequest(android.content.Intent intent) {
        return (android.credentials.selection.CancelSelectionRequest) intent.getParcelableExtra(android.credentials.selection.CancelSelectionRequest.EXTRA_CANCEL_UI_REQUEST, android.credentials.selection.CancelSelectionRequest.class);
    }

    public static android.credentials.selection.RequestInfo extractRequestInfo(android.content.Intent intent) {
        return (android.credentials.selection.RequestInfo) intent.getParcelableExtra(android.credentials.selection.RequestInfo.EXTRA_REQUEST_INFO, android.credentials.selection.RequestInfo.class);
    }

    public static java.util.List<android.credentials.selection.GetCredentialProviderInfo> extractGetCredentialProviderInfoList(android.content.Intent intent) {
        java.util.ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(android.credentials.selection.ProviderData.EXTRA_ENABLED_PROVIDER_DATA_LIST, android.credentials.selection.GetCredentialProviderData.class);
        return parcelableArrayListExtra == null ? java.util.Collections.emptyList() : parcelableArrayListExtra.stream().map(new java.util.function.Function() { // from class: android.credentials.selection.IntentHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.credentials.selection.GetCredentialProviderData) obj).toGetCredentialProviderInfo();
            }
        }).toList();
    }

    public static java.util.List<android.credentials.selection.CreateCredentialProviderInfo> extractCreateCredentialProviderInfoList(android.content.Intent intent) {
        java.util.ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(android.credentials.selection.ProviderData.EXTRA_ENABLED_PROVIDER_DATA_LIST, android.credentials.selection.CreateCredentialProviderData.class);
        return parcelableArrayListExtra == null ? java.util.Collections.emptyList() : parcelableArrayListExtra.stream().map(new java.util.function.Function() { // from class: android.credentials.selection.IntentHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.credentials.selection.CreateCredentialProviderData) obj).toCreateCredentialProviderInfo();
            }
        }).toList();
    }

    public static java.util.List<android.credentials.selection.DisabledProviderInfo> extractDisabledProviderInfoList(android.content.Intent intent) {
        java.util.ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(android.credentials.selection.ProviderData.EXTRA_DISABLED_PROVIDER_DATA_LIST, android.credentials.selection.DisabledProviderData.class);
        return parcelableArrayListExtra == null ? java.util.Collections.emptyList() : parcelableArrayListExtra.stream().map(new java.util.function.Function() { // from class: android.credentials.selection.IntentHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.credentials.selection.DisabledProviderData) obj).toDisabledProviderInfo();
            }
        }).toList();
    }

    public static android.os.ResultReceiver extractResultReceiver(android.content.Intent intent) {
        return (android.os.ResultReceiver) intent.getParcelableExtra(android.credentials.selection.Constants.EXTRA_RESULT_RECEIVER, android.os.ResultReceiver.class);
    }

    private IntentHelper() {
    }
}
