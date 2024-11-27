package android.service.autofill;

/* loaded from: classes3.dex */
final class SavedDatasetsInfoCallbackImpl implements android.service.autofill.SavedDatasetsInfoCallback {
    private static final java.lang.String TAG = "AutofillService";
    private final com.android.internal.os.IResultReceiver mReceiver;
    private final java.lang.String mType;

    SavedDatasetsInfoCallbackImpl(com.android.internal.os.IResultReceiver iResultReceiver, java.lang.String str) {
        this.mReceiver = (com.android.internal.os.IResultReceiver) java.util.Objects.requireNonNull(iResultReceiver);
        this.mType = (java.lang.String) java.util.Objects.requireNonNull(str);
    }

    @Override // android.service.autofill.SavedDatasetsInfoCallback
    public void onSuccess(java.util.Set<android.service.autofill.SavedDatasetsInfo> set) {
        java.util.Objects.requireNonNull(set);
        if (set.isEmpty()) {
            send(1, null);
            return;
        }
        int i = -1;
        for (android.service.autofill.SavedDatasetsInfo savedDatasetsInfo : set) {
            if (this.mType.equals(savedDatasetsInfo.getType())) {
                i = savedDatasetsInfo.getCount();
            }
        }
        if (i < 0) {
            send(1, null);
            return;
        }
        android.os.Bundle bundle = new android.os.Bundle(1);
        bundle.putInt("result", i);
        send(0, bundle);
    }

    @Override // android.service.autofill.SavedDatasetsInfoCallback
    public void onError(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 2, "error");
        android.os.Bundle bundle = new android.os.Bundle(1);
        bundle.putInt("error", i);
        send(1, bundle);
    }

    private void send(int i, android.os.Bundle bundle) {
        try {
            this.mReceiver.send(i, bundle);
        } catch (android.os.DeadObjectException e) {
            android.util.Log.w(TAG, "Failed to send onSavedPasswordCountRequest result: " + e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }
}
