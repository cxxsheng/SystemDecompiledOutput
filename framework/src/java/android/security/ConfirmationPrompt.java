package android.security;

/* loaded from: classes3.dex */
public class ConfirmationPrompt {
    private static final java.lang.String TAG = "ConfirmationPrompt";
    private android.security.ConfirmationCallback mCallback;
    private final android.security.apc.IConfirmationCallback mConfirmationCallback;
    private android.content.Context mContext;
    private java.util.concurrent.Executor mExecutor;
    private byte[] mExtraData;
    private java.lang.CharSequence mPromptText;
    private android.security.AndroidProtectedConfirmation mProtectedConfirmation;

    private android.security.AndroidProtectedConfirmation getService() {
        if (this.mProtectedConfirmation == null) {
            this.mProtectedConfirmation = new android.security.AndroidProtectedConfirmation();
        }
        return this.mProtectedConfirmation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCallback(int i, byte[] bArr, android.security.ConfirmationCallback confirmationCallback) {
        switch (i) {
            case 0:
                confirmationCallback.onConfirmed(bArr);
                break;
            case 1:
                confirmationCallback.onDismissed();
                break;
            case 2:
                confirmationCallback.onCanceled();
                break;
            case 3:
            case 4:
            default:
                confirmationCallback.onError(new java.lang.Exception("Unexpected responseCode=" + i + " from onConfirmtionPromptCompleted() callback."));
                break;
            case 5:
                confirmationCallback.onError(new java.lang.Exception("System error returned by ConfirmationUI."));
                break;
        }
    }

    public static final class Builder {
        private android.content.Context mContext;
        private byte[] mExtraData;
        private java.lang.CharSequence mPromptText;

        public Builder(android.content.Context context) {
            this.mContext = context;
        }

        public android.security.ConfirmationPrompt.Builder setPromptText(java.lang.CharSequence charSequence) {
            this.mPromptText = charSequence;
            return this;
        }

        public android.security.ConfirmationPrompt.Builder setExtraData(byte[] bArr) {
            this.mExtraData = bArr;
            return this;
        }

        public android.security.ConfirmationPrompt build() {
            if (android.text.TextUtils.isEmpty(this.mPromptText)) {
                throw new java.lang.IllegalArgumentException("prompt text must be set and non-empty");
            }
            if (this.mExtraData == null) {
                throw new java.lang.IllegalArgumentException("extraData must be set");
            }
            return new android.security.ConfirmationPrompt(this.mContext, this.mPromptText, this.mExtraData);
        }
    }

    private ConfirmationPrompt(android.content.Context context, java.lang.CharSequence charSequence, byte[] bArr) {
        this.mConfirmationCallback = new android.security.apc.IConfirmationCallback.Stub() { // from class: android.security.ConfirmationPrompt.1
            @Override // android.security.apc.IConfirmationCallback
            public void onCompleted(final int i, final byte[] bArr2) throws android.os.RemoteException {
                if (android.security.ConfirmationPrompt.this.mCallback != null) {
                    final android.security.ConfirmationCallback confirmationCallback = android.security.ConfirmationPrompt.this.mCallback;
                    java.util.concurrent.Executor executor = android.security.ConfirmationPrompt.this.mExecutor;
                    android.security.ConfirmationPrompt.this.mCallback = null;
                    android.security.ConfirmationPrompt.this.mExecutor = null;
                    if (executor == null) {
                        android.security.ConfirmationPrompt.this.doCallback(i, bArr2, confirmationCallback);
                    } else {
                        executor.execute(new java.lang.Runnable() { // from class: android.security.ConfirmationPrompt.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                android.security.ConfirmationPrompt.this.doCallback(i, bArr2, confirmationCallback);
                            }
                        });
                    }
                }
            }
        };
        this.mContext = context;
        this.mPromptText = charSequence;
        this.mExtraData = bArr;
    }

    private int getUiOptionsAsFlags() {
        int i = android.provider.Settings.Secure.getInt(this.mContext.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, 0) == 1 ? 1 : 0;
        if (android.provider.Settings.System.getFloat(r0, android.provider.Settings.System.FONT_SCALE, 1.0f) > 1.0d) {
            return i | 2;
        }
        return i;
    }

    private static boolean isAccessibilityServiceRunning(android.content.Context context) {
        try {
            if (android.provider.Settings.Secure.getInt(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED) != 1) {
                return false;
            }
            return true;
        } catch (android.provider.Settings.SettingNotFoundException e) {
            android.util.Log.w(TAG, "Unexpected SettingNotFoundException");
            e.printStackTrace();
            return false;
        }
    }

    public void presentPrompt(java.util.concurrent.Executor executor, android.security.ConfirmationCallback confirmationCallback) throws android.security.ConfirmationAlreadyPresentingException, android.security.ConfirmationNotAvailableException {
        if (this.mCallback != null) {
            throw new android.security.ConfirmationAlreadyPresentingException();
        }
        if (isAccessibilityServiceRunning(this.mContext)) {
            throw new android.security.ConfirmationNotAvailableException();
        }
        this.mCallback = confirmationCallback;
        this.mExecutor = executor;
        int presentConfirmationPrompt = getService().presentConfirmationPrompt(this.mConfirmationCallback, this.mPromptText.toString(), this.mExtraData, java.util.Locale.getDefault().toLanguageTag(), getUiOptionsAsFlags());
        switch (presentConfirmationPrompt) {
            case 0:
                return;
            case 3:
                throw new android.security.ConfirmationAlreadyPresentingException();
            case 6:
                throw new android.security.ConfirmationNotAvailableException();
            default:
                android.util.Log.w(TAG, "Unexpected responseCode=" + presentConfirmationPrompt + " from presentConfirmationPrompt() call.");
                throw new java.lang.IllegalArgumentException();
        }
    }

    public void cancelPrompt() {
        int cancelConfirmationPrompt = getService().cancelConfirmationPrompt(this.mConfirmationCallback);
        if (cancelConfirmationPrompt == 0) {
            return;
        }
        if (cancelConfirmationPrompt == 3) {
            throw new java.lang.IllegalStateException();
        }
        android.util.Log.w(TAG, "Unexpected responseCode=" + cancelConfirmationPrompt + " from cancelConfirmationPrompt() call.");
        throw new java.lang.IllegalStateException();
    }

    public static boolean isSupported(android.content.Context context) {
        if (isAccessibilityServiceRunning(context)) {
            return false;
        }
        return new android.security.AndroidProtectedConfirmation().isConfirmationPromptSupported();
    }
}
