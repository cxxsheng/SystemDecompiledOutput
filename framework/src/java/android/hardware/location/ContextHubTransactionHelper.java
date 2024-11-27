package android.hardware.location;

/* loaded from: classes2.dex */
public class ContextHubTransactionHelper {
    private static final java.lang.String TAG = "ContextHubTransactionHelper";

    public static android.hardware.location.IContextHubTransactionCallback createNanoAppQueryCallback(final android.hardware.location.ContextHubTransaction<java.util.List<android.hardware.location.NanoAppState>> contextHubTransaction) {
        java.util.Objects.requireNonNull(contextHubTransaction, "transaction cannot be null");
        return new android.hardware.location.IContextHubTransactionCallback.Stub() { // from class: android.hardware.location.ContextHubTransactionHelper.1
            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int i, java.util.List<android.hardware.location.NanoAppState> list) {
                android.hardware.location.ContextHubTransaction.this.setResponse(new android.hardware.location.ContextHubTransaction.Response(i, list));
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int i) {
                android.util.Log.e(android.hardware.location.ContextHubTransactionHelper.TAG, "Received a non-query callback on a query request");
                android.hardware.location.ContextHubTransaction.this.setResponse(new android.hardware.location.ContextHubTransaction.Response(7, null));
            }
        };
    }

    public static android.hardware.location.IContextHubTransactionCallback createTransactionCallback(final android.hardware.location.ContextHubTransaction<java.lang.Void> contextHubTransaction) {
        java.util.Objects.requireNonNull(contextHubTransaction, "transaction cannot be null");
        return new android.hardware.location.IContextHubTransactionCallback.Stub() { // from class: android.hardware.location.ContextHubTransactionHelper.2
            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int i, java.util.List<android.hardware.location.NanoAppState> list) {
                android.util.Log.e(android.hardware.location.ContextHubTransactionHelper.TAG, "Received a query callback on a non-query request");
                android.hardware.location.ContextHubTransaction.this.setResponse(new android.hardware.location.ContextHubTransaction.Response(7, null));
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int i) {
                android.hardware.location.ContextHubTransaction.this.setResponse(new android.hardware.location.ContextHubTransaction.Response(i, null));
            }
        };
    }
}
