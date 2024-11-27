package com.android.server.credentials;

/* loaded from: classes.dex */
public class CredentialManagerUi {
    private static final java.lang.String TAG = "CredentialManagerUi";

    @android.annotation.NonNull
    private final com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback mCallbacks;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private final java.util.Set<android.content.ComponentName> mEnabledProviders;

    @android.annotation.NonNull
    private final android.os.ResultReceiver mResultReceiver = new android.os.ResultReceiver(new android.os.Handler(android.os.Looper.getMainLooper())) { // from class: com.android.server.credentials.CredentialManagerUi.1
        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, android.os.Bundle bundle) {
            com.android.server.credentials.CredentialManagerUi.this.handleUiResult(i, bundle);
        }
    };
    private com.android.server.credentials.CredentialManagerUi.UiStatus mStatus = com.android.server.credentials.CredentialManagerUi.UiStatus.IN_PROGRESS;
    private final int mUserId;

    public interface CredentialManagerUiCallback {
        void onUiCancellation(boolean z, android.os.ResultReceiver resultReceiver);

        void onUiSelection(android.credentials.selection.UserSelectionDialogResult userSelectionDialogResult, android.os.ResultReceiver resultReceiver);

        void onUiSelectorInvocationFailure();
    }

    enum UiStatus {
        IN_PROGRESS,
        USER_INTERACTION,
        NOT_STARTED,
        TERMINATED
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUiResult(int i, android.os.Bundle bundle) {
        switch (i) {
            case 0:
                this.mStatus = com.android.server.credentials.CredentialManagerUi.UiStatus.TERMINATED;
                this.mCallbacks.onUiCancellation(true, (android.os.ResultReceiver) bundle.getParcelable("android.credentials.selection.extra.FINAL_RESPONSE_RECEIVER", android.os.ResultReceiver.class));
                break;
            case 1:
                this.mStatus = com.android.server.credentials.CredentialManagerUi.UiStatus.TERMINATED;
                this.mCallbacks.onUiCancellation(false, (android.os.ResultReceiver) bundle.getParcelable("android.credentials.selection.extra.FINAL_RESPONSE_RECEIVER", android.os.ResultReceiver.class));
                break;
            case 2:
                this.mStatus = com.android.server.credentials.CredentialManagerUi.UiStatus.IN_PROGRESS;
                android.credentials.selection.UserSelectionDialogResult fromResultData = android.credentials.selection.UserSelectionDialogResult.fromResultData(bundle);
                if (fromResultData != null) {
                    this.mCallbacks.onUiSelection(fromResultData, (android.os.ResultReceiver) bundle.getParcelable("android.credentials.selection.extra.FINAL_RESPONSE_RECEIVER", android.os.ResultReceiver.class));
                    break;
                }
                break;
            case 3:
                this.mStatus = com.android.server.credentials.CredentialManagerUi.UiStatus.TERMINATED;
                this.mCallbacks.onUiSelectorInvocationFailure();
                break;
            default:
                this.mStatus = com.android.server.credentials.CredentialManagerUi.UiStatus.IN_PROGRESS;
                this.mCallbacks.onUiSelectorInvocationFailure();
                break;
        }
    }

    public android.content.Intent createCancelIntent(android.os.IBinder iBinder, java.lang.String str) {
        return android.credentials.selection.IntentFactory.createCancelUiIntent(iBinder, true, str);
    }

    public CredentialManagerUi(android.content.Context context, int i, com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback credentialManagerUiCallback, java.util.Set<android.content.ComponentName> set) {
        this.mContext = context;
        this.mUserId = i;
        this.mCallbacks = credentialManagerUiCallback;
        this.mEnabledProviders = set;
    }

    public void setStatus(com.android.server.credentials.CredentialManagerUi.UiStatus uiStatus) {
        this.mStatus = uiStatus;
    }

    public com.android.server.credentials.CredentialManagerUi.UiStatus getStatus() {
        return this.mStatus;
    }

    public android.app.PendingIntent createPendingIntent(android.credentials.selection.RequestInfo requestInfo, java.util.ArrayList<android.credentials.selection.ProviderData> arrayList) {
        android.content.Intent createCredentialSelectorIntent = android.credentials.selection.IntentFactory.createCredentialSelectorIntent(this.mContext, requestInfo, arrayList, new java.util.ArrayList(android.service.credentials.CredentialProviderInfoFactory.getCredentialProviderServices(this.mContext, this.mUserId, 2, this.mEnabledProviders, new java.util.HashSet()).stream().filter(new java.util.function.Predicate() { // from class: com.android.server.credentials.CredentialManagerUi$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$createPendingIntent$0;
                lambda$createPendingIntent$0 = com.android.server.credentials.CredentialManagerUi.lambda$createPendingIntent$0((android.credentials.CredentialProviderInfo) obj);
                return lambda$createPendingIntent$0;
            }
        }).map(new java.util.function.Function() { // from class: com.android.server.credentials.CredentialManagerUi$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.credentials.selection.DisabledProviderData lambda$createPendingIntent$1;
                lambda$createPendingIntent$1 = com.android.server.credentials.CredentialManagerUi.lambda$createPendingIntent$1((android.credentials.CredentialProviderInfo) obj);
                return lambda$createPendingIntent$1;
            }
        }).toList()), this.mResultReceiver);
        createCredentialSelectorIntent.setAction(java.util.UUID.randomUUID().toString());
        return android.app.PendingIntent.getActivityAsUser(this.mContext, 0, createCredentialSelectorIntent, 33554432, null, android.os.UserHandle.of(this.mUserId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$createPendingIntent$0(android.credentials.CredentialProviderInfo credentialProviderInfo) {
        return !credentialProviderInfo.isEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.credentials.selection.DisabledProviderData lambda$createPendingIntent$1(android.credentials.CredentialProviderInfo credentialProviderInfo) {
        return new android.credentials.selection.DisabledProviderData(credentialProviderInfo.getComponentName().flattenToString());
    }

    public android.content.Intent createIntentForAutofill(android.credentials.selection.RequestInfo requestInfo) {
        return android.credentials.selection.IntentFactory.createCredentialSelectorIntentForAutofill(this.mContext, requestInfo, new java.util.ArrayList(), this.mResultReceiver);
    }
}
