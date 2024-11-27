package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class TextClassificationManager {
    private static final java.lang.String LOG_TAG = "androidtc";
    private static final android.view.textclassifier.TextClassificationConstants sDefaultSettings = new android.view.textclassifier.TextClassificationConstants();
    private final android.content.Context mContext;
    private android.view.textclassifier.TextClassifier mCustomTextClassifier;
    private android.view.textclassifier.TextClassificationConstants mSettings;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.view.textclassifier.TextClassificationSessionFactory mDefaultSessionFactory = new android.view.textclassifier.TextClassificationSessionFactory() { // from class: android.view.textclassifier.TextClassificationManager$$ExternalSyntheticLambda0
        @Override // android.view.textclassifier.TextClassificationSessionFactory
        public final android.view.textclassifier.TextClassifier createTextClassificationSession(android.view.textclassifier.TextClassificationContext textClassificationContext) {
            android.view.textclassifier.TextClassifier lambda$new$0;
            lambda$new$0 = android.view.textclassifier.TextClassificationManager.this.lambda$new$0(textClassificationContext);
            return lambda$new$0;
        }
    };
    private android.view.textclassifier.TextClassificationSessionFactory mSessionFactory = this.mDefaultSessionFactory;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.textclassifier.TextClassifier lambda$new$0(android.view.textclassifier.TextClassificationContext textClassificationContext) {
        return new android.view.textclassifier.TextClassificationSession(textClassificationContext, getTextClassifier());
    }

    public TextClassificationManager(android.content.Context context) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
    }

    public android.view.textclassifier.TextClassifier getTextClassifier() {
        synchronized (this.mLock) {
            if (this.mCustomTextClassifier != null) {
                return this.mCustomTextClassifier;
            }
            if (getSettings().isSystemTextClassifierEnabled()) {
                return getSystemTextClassifier(1);
            }
            return getLocalTextClassifier();
        }
    }

    public void setTextClassifier(android.view.textclassifier.TextClassifier textClassifier) {
        synchronized (this.mLock) {
            this.mCustomTextClassifier = textClassifier;
        }
    }

    public android.view.textclassifier.TextClassifier getTextClassifier(int i) {
        switch (i) {
            case 0:
                return getLocalTextClassifier();
            default:
                return getSystemTextClassifier(i);
        }
    }

    private android.view.textclassifier.TextClassificationConstants getSettings() {
        android.view.textclassifier.TextClassificationConstants textClassificationConstants;
        synchronized (this.mLock) {
            if (this.mSettings == null) {
                this.mSettings = new android.view.textclassifier.TextClassificationConstants();
            }
            textClassificationConstants = this.mSettings;
        }
        return textClassificationConstants;
    }

    public android.view.textclassifier.TextClassifier createTextClassificationSession(android.view.textclassifier.TextClassificationContext textClassificationContext) {
        java.util.Objects.requireNonNull(textClassificationContext);
        android.view.textclassifier.TextClassifier createTextClassificationSession = this.mSessionFactory.createTextClassificationSession(textClassificationContext);
        java.util.Objects.requireNonNull(createTextClassificationSession, "Session Factory should never return null");
        return createTextClassificationSession;
    }

    public android.view.textclassifier.TextClassifier createTextClassificationSession(android.view.textclassifier.TextClassificationContext textClassificationContext, android.view.textclassifier.TextClassifier textClassifier) {
        java.util.Objects.requireNonNull(textClassificationContext);
        java.util.Objects.requireNonNull(textClassifier);
        return new android.view.textclassifier.TextClassificationSession(textClassificationContext, textClassifier);
    }

    public void setTextClassificationSessionFactory(android.view.textclassifier.TextClassificationSessionFactory textClassificationSessionFactory) {
        synchronized (this.mLock) {
            if (textClassificationSessionFactory != null) {
                this.mSessionFactory = textClassificationSessionFactory;
            } else {
                this.mSessionFactory = this.mDefaultSessionFactory;
            }
        }
    }

    private android.view.textclassifier.TextClassifier getSystemTextClassifier(int i) {
        synchronized (this.mLock) {
            if (getSettings().isSystemTextClassifierEnabled()) {
                try {
                    android.view.textclassifier.Log.d("androidtc", "Initializing SystemTextClassifier, type = " + android.view.textclassifier.TextClassifier.typeToString(i));
                    return new android.view.textclassifier.SystemTextClassifier(this.mContext, getSettings(), i == 2);
                } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                    android.view.textclassifier.Log.e("androidtc", "Could not initialize SystemTextClassifier", e);
                }
            }
            return android.view.textclassifier.TextClassifier.NO_OP;
        }
    }

    private android.view.textclassifier.TextClassifier getLocalTextClassifier() {
        android.view.textclassifier.Log.d("androidtc", "Local text-classifier not supported. Returning a no-op text-classifier.");
        return android.view.textclassifier.TextClassifier.NO_OP;
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        getSystemTextClassifier(2).dump(indentingPrintWriter);
        getSystemTextClassifier(1).dump(indentingPrintWriter);
        getSettings().dump(indentingPrintWriter);
    }

    public static android.view.textclassifier.TextClassificationConstants getSettings(android.content.Context context) {
        java.util.Objects.requireNonNull(context);
        android.view.textclassifier.TextClassificationManager textClassificationManager = (android.view.textclassifier.TextClassificationManager) context.getSystemService(android.view.textclassifier.TextClassificationManager.class);
        if (textClassificationManager != null) {
            return textClassificationManager.getSettings();
        }
        return sDefaultSettings;
    }
}
