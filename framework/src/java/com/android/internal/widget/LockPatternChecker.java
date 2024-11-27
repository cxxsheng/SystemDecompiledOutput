package com.android.internal.widget;

/* loaded from: classes5.dex */
public final class LockPatternChecker {

    public interface OnVerifyCallback {
        void onVerified(com.android.internal.widget.VerifyCredentialResponse verifyCredentialResponse, int i);
    }

    public interface OnCheckCallback {
        void onChecked(boolean z, int i);

        default void onEarlyMatched() {
        }

        default void onCancelled() {
        }
    }

    public static android.os.AsyncTask<?, ?, ?> verifyCredential(final com.android.internal.widget.LockPatternUtils lockPatternUtils, com.android.internal.widget.LockscreenCredential lockscreenCredential, final int i, final int i2, final com.android.internal.widget.LockPatternChecker.OnVerifyCallback onVerifyCallback) {
        final com.android.internal.widget.LockscreenCredential duplicate = lockscreenCredential.duplicate();
        android.os.AsyncTask<java.lang.Void, java.lang.Void, com.android.internal.widget.VerifyCredentialResponse> asyncTask = new android.os.AsyncTask<java.lang.Void, java.lang.Void, com.android.internal.widget.VerifyCredentialResponse>() { // from class: com.android.internal.widget.LockPatternChecker.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public com.android.internal.widget.VerifyCredentialResponse doInBackground(java.lang.Void... voidArr) {
                return com.android.internal.widget.LockPatternUtils.this.verifyCredential(duplicate, i, i2);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(com.android.internal.widget.VerifyCredentialResponse verifyCredentialResponse) {
                onVerifyCallback.onVerified(verifyCredentialResponse, verifyCredentialResponse.getTimeout());
                duplicate.zeroize();
            }

            @Override // android.os.AsyncTask
            protected void onCancelled() {
                duplicate.zeroize();
            }
        };
        asyncTask.execute(new java.lang.Void[0]);
        return asyncTask;
    }

    public static android.os.AsyncTask<?, ?, ?> checkCredential(final com.android.internal.widget.LockPatternUtils lockPatternUtils, com.android.internal.widget.LockscreenCredential lockscreenCredential, final int i, final com.android.internal.widget.LockPatternChecker.OnCheckCallback onCheckCallback) {
        final com.android.internal.widget.LockscreenCredential duplicate = lockscreenCredential.duplicate();
        android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Boolean> asyncTask = new android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Boolean>() { // from class: com.android.internal.widget.LockPatternChecker.2
            private int mThrottleTimeout;

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public java.lang.Boolean doInBackground(java.lang.Void... voidArr) {
                try {
                    com.android.internal.widget.LockPatternUtils lockPatternUtils2 = com.android.internal.widget.LockPatternUtils.this;
                    com.android.internal.widget.LockscreenCredential lockscreenCredential2 = duplicate;
                    int i2 = i;
                    final com.android.internal.widget.LockPatternChecker.OnCheckCallback onCheckCallback2 = onCheckCallback;
                    java.util.Objects.requireNonNull(onCheckCallback2);
                    return java.lang.Boolean.valueOf(lockPatternUtils2.checkCredential(lockscreenCredential2, i2, new com.android.internal.widget.LockPatternUtils.CheckCredentialProgressCallback() { // from class: com.android.internal.widget.LockPatternChecker$2$$ExternalSyntheticLambda0
                        @Override // com.android.internal.widget.LockPatternUtils.CheckCredentialProgressCallback
                        public final void onEarlyMatched() {
                            com.android.internal.widget.LockPatternChecker.OnCheckCallback.this.onEarlyMatched();
                        }
                    }));
                } catch (com.android.internal.widget.LockPatternUtils.RequestThrottledException e) {
                    this.mThrottleTimeout = e.getTimeoutMs();
                    return false;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(java.lang.Boolean bool) {
                onCheckCallback.onChecked(bool.booleanValue(), this.mThrottleTimeout);
                duplicate.zeroize();
            }

            @Override // android.os.AsyncTask
            protected void onCancelled() {
                onCheckCallback.onCancelled();
                duplicate.zeroize();
            }
        };
        asyncTask.execute(new java.lang.Void[0]);
        return asyncTask;
    }

    public static android.os.AsyncTask<?, ?, ?> verifyTiedProfileChallenge(final com.android.internal.widget.LockPatternUtils lockPatternUtils, com.android.internal.widget.LockscreenCredential lockscreenCredential, final int i, final int i2, final com.android.internal.widget.LockPatternChecker.OnVerifyCallback onVerifyCallback) {
        final com.android.internal.widget.LockscreenCredential duplicate = lockscreenCredential.duplicate();
        android.os.AsyncTask<java.lang.Void, java.lang.Void, com.android.internal.widget.VerifyCredentialResponse> asyncTask = new android.os.AsyncTask<java.lang.Void, java.lang.Void, com.android.internal.widget.VerifyCredentialResponse>() { // from class: com.android.internal.widget.LockPatternChecker.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public com.android.internal.widget.VerifyCredentialResponse doInBackground(java.lang.Void... voidArr) {
                return com.android.internal.widget.LockPatternUtils.this.verifyTiedProfileChallenge(duplicate, i, i2);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(com.android.internal.widget.VerifyCredentialResponse verifyCredentialResponse) {
                onVerifyCallback.onVerified(verifyCredentialResponse, verifyCredentialResponse.getTimeout());
                duplicate.zeroize();
            }

            @Override // android.os.AsyncTask
            protected void onCancelled() {
                duplicate.zeroize();
            }
        };
        asyncTask.execute(new java.lang.Void[0]);
        return asyncTask;
    }
}
