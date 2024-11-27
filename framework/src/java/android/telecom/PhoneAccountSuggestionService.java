package android.telecom;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class PhoneAccountSuggestionService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.telecom.PhoneAccountSuggestionService";
    private com.android.internal.telecom.IPhoneAccountSuggestionService mInterface = new com.android.internal.telecom.IPhoneAccountSuggestionService.Stub() { // from class: android.telecom.PhoneAccountSuggestionService.1
        @Override // com.android.internal.telecom.IPhoneAccountSuggestionService
        public void onAccountSuggestionRequest(com.android.internal.telecom.IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, java.lang.String str) {
            android.telecom.PhoneAccountSuggestionService.this.mCallbackMap.put(str, iPhoneAccountSuggestionCallback);
            android.telecom.PhoneAccountSuggestionService.this.onAccountSuggestionRequest(str);
        }
    };
    private final java.util.Map<java.lang.String, com.android.internal.telecom.IPhoneAccountSuggestionCallback> mCallbackMap = new java.util.HashMap();

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mInterface.asBinder();
    }

    public void onAccountSuggestionRequest(java.lang.String str) {
    }

    public final void suggestPhoneAccounts(java.lang.String str, java.util.List<android.telecom.PhoneAccountSuggestion> list) {
        com.android.internal.telecom.IPhoneAccountSuggestionCallback remove = this.mCallbackMap.remove(str);
        if (remove == null) {
            android.telecom.Log.w(this, "No suggestions requested for the number %s", android.telecom.Log.pii(str));
            return;
        }
        try {
            remove.suggestPhoneAccounts(str, list);
        } catch (android.os.RemoteException e) {
            android.telecom.Log.w(this, "Remote exception calling suggestPhoneAccounts", new java.lang.Object[0]);
        }
    }
}
