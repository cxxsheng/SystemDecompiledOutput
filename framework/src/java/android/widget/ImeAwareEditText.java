package android.widget;

/* loaded from: classes4.dex */
public class ImeAwareEditText extends android.widget.EditText {
    private boolean mHasPendingShowSoftInputRequest;
    final java.lang.Runnable mRunShowSoftInputIfNecessary;

    public ImeAwareEditText(android.content.Context context) {
        super(context, null);
        this.mRunShowSoftInputIfNecessary = new java.lang.Runnable() { // from class: android.widget.ImeAwareEditText$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.ImeAwareEditText.this.lambda$new$0();
            }
        };
    }

    public ImeAwareEditText(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRunShowSoftInputIfNecessary = new java.lang.Runnable() { // from class: android.widget.ImeAwareEditText$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.ImeAwareEditText.this.lambda$new$0();
            }
        };
    }

    public ImeAwareEditText(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRunShowSoftInputIfNecessary = new java.lang.Runnable() { // from class: android.widget.ImeAwareEditText$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.ImeAwareEditText.this.lambda$new$0();
            }
        };
    }

    public ImeAwareEditText(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRunShowSoftInputIfNecessary = new java.lang.Runnable() { // from class: android.widget.ImeAwareEditText$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.ImeAwareEditText.this.lambda$new$0();
            }
        };
    }

    @Override // android.widget.TextView, android.view.View
    public android.view.inputmethod.InputConnection onCreateInputConnection(android.view.inputmethod.EditorInfo editorInfo) {
        android.view.inputmethod.InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        if (this.mHasPendingShowSoftInputRequest) {
            removeCallbacks(this.mRunShowSoftInputIfNecessary);
            post(this.mRunShowSoftInputIfNecessary);
        }
        return onCreateInputConnection;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showSoftInputIfNecessary, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        if (this.mHasPendingShowSoftInputRequest) {
            ((android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class)).showSoftInput(this, 0);
            this.mHasPendingShowSoftInputRequest = false;
        }
    }

    public void scheduleShowSoftInput() {
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class);
        if (inputMethodManager.hasActiveInputConnection(this)) {
            this.mHasPendingShowSoftInputRequest = false;
            removeCallbacks(this.mRunShowSoftInputIfNecessary);
            inputMethodManager.showSoftInput(this, 0);
            return;
        }
        this.mHasPendingShowSoftInputRequest = true;
    }
}
