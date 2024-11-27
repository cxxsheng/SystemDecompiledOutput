package android.widget;

/* loaded from: classes4.dex */
public class TextViewTranslationCallback implements android.view.translation.ViewTranslationCallback {
    private static final char COMPAT_PAD_CHARACTER = 8194;
    private static final boolean DEBUG = android.util.Log.isLoggable(android.view.translation.UiTranslationManager.LOG_TAG, 3);
    private static final java.lang.String TAG = "TextViewTranslationCb";
    private android.animation.ValueAnimator mAnimator;
    private java.lang.CharSequence mContentDescription;
    private java.lang.CharSequence mPaddedText;
    private android.text.method.TranslationTransformationMethod mTranslationTransformation;
    private boolean mIsShowingTranslation = false;
    private boolean mAnimationRunning = false;
    private boolean mIsTextPaddingEnabled = false;
    private boolean mOriginalIsTextSelectable = false;
    private int mOriginalFocusable = 0;
    private boolean mOriginalFocusableInTouchMode = false;
    private boolean mOriginalClickable = false;
    private boolean mOriginalLongClickable = false;
    private int mAnimationDurationMillis = 250;

    private void clearTranslationTransformation() {
        if (DEBUG) {
            android.util.Log.v(TAG, "clearTranslationTransformation: " + this.mTranslationTransformation);
        }
        this.mTranslationTransformation = null;
    }

    @Override // android.view.translation.ViewTranslationCallback
    public boolean onShowTranslation(android.view.View view) {
        if (this.mIsShowingTranslation) {
            if (DEBUG) {
                android.util.Log.d(TAG, view + " is already showing translated text.");
            }
            return false;
        }
        android.view.translation.ViewTranslationResponse viewTranslationResponse = view.getViewTranslationResponse();
        if (viewTranslationResponse == null) {
            android.util.Log.e(TAG, "onShowTranslation() shouldn't be called before onViewTranslationResponse().");
            return false;
        }
        android.widget.TextView textView = (android.widget.TextView) view;
        if (this.mTranslationTransformation == null || !viewTranslationResponse.equals(this.mTranslationTransformation.getViewTranslationResponse())) {
            this.mTranslationTransformation = new android.text.method.TranslationTransformationMethod(viewTranslationResponse, textView.getTransformationMethod());
        }
        final android.text.method.TranslationTransformationMethod translationTransformationMethod = this.mTranslationTransformation;
        final java.lang.ref.WeakReference weakReference = new java.lang.ref.WeakReference(textView);
        runChangeTextWithAnimationIfNeeded(textView, new java.lang.Runnable() { // from class: android.widget.TextViewTranslationCallback$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.TextViewTranslationCallback.this.lambda$onShowTranslation$0(weakReference, translationTransformationMethod);
            }
        });
        if (viewTranslationResponse.getKeys().contains(android.view.translation.ViewTranslationRequest.ID_CONTENT_DESCRIPTION)) {
            java.lang.CharSequence text = viewTranslationResponse.getValue(android.view.translation.ViewTranslationRequest.ID_CONTENT_DESCRIPTION).getText();
            if (!android.text.TextUtils.isEmpty(text)) {
                this.mContentDescription = view.getContentDescription();
                view.setContentDescription(text);
                return true;
            }
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onShowTranslation$0(java.lang.ref.WeakReference weakReference, android.text.method.TransformationMethod transformationMethod) {
        this.mIsShowingTranslation = true;
        this.mAnimationRunning = false;
        android.widget.TextView textView = (android.widget.TextView) weakReference.get();
        if (textView == null) {
            return;
        }
        this.mOriginalIsTextSelectable = textView.isTextSelectable();
        if (this.mOriginalIsTextSelectable) {
            this.mOriginalFocusableInTouchMode = textView.isFocusableInTouchMode();
            this.mOriginalFocusable = textView.getFocusable();
            this.mOriginalClickable = textView.isClickable();
            this.mOriginalLongClickable = textView.isLongClickable();
            textView.setTextIsSelectable(false);
        }
        textView.setTransformationMethod(transformationMethod);
    }

    @Override // android.view.translation.ViewTranslationCallback
    public boolean onHideTranslation(android.view.View view) {
        if (view.getViewTranslationResponse() == null) {
            android.util.Log.e(TAG, "onHideTranslation() shouldn't be called before onViewTranslationResponse().");
            return false;
        }
        if (this.mTranslationTransformation != null) {
            final android.text.method.TransformationMethod originalTransformationMethod = this.mTranslationTransformation.getOriginalTransformationMethod();
            android.widget.TextView textView = (android.widget.TextView) view;
            final java.lang.ref.WeakReference weakReference = new java.lang.ref.WeakReference(textView);
            runChangeTextWithAnimationIfNeeded(textView, new java.lang.Runnable() { // from class: android.widget.TextViewTranslationCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.TextViewTranslationCallback.this.lambda$onHideTranslation$1(weakReference, originalTransformationMethod);
                }
            });
            if (!android.text.TextUtils.isEmpty(this.mContentDescription)) {
                view.setContentDescription(this.mContentDescription);
                return true;
            }
            return true;
        }
        if (DEBUG) {
            android.util.Log.w(TAG, "onHideTranslation(): no translated text.");
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHideTranslation$1(java.lang.ref.WeakReference weakReference, android.text.method.TransformationMethod transformationMethod) {
        this.mIsShowingTranslation = false;
        this.mAnimationRunning = false;
        android.widget.TextView textView = (android.widget.TextView) weakReference.get();
        if (textView == null) {
            return;
        }
        textView.setTransformationMethod(transformationMethod);
        if (this.mOriginalIsTextSelectable && !textView.isTextSelectable()) {
            textView.setTextIsSelectable(true);
            textView.setFocusableInTouchMode(this.mOriginalFocusableInTouchMode);
            textView.setFocusable(this.mOriginalFocusable);
            textView.setClickable(this.mOriginalClickable);
            textView.setLongClickable(this.mOriginalLongClickable);
        }
    }

    @Override // android.view.translation.ViewTranslationCallback
    public boolean onClearTranslation(android.view.View view) {
        if (this.mTranslationTransformation != null) {
            onHideTranslation(view);
            clearTranslationTransformation();
            this.mPaddedText = null;
            this.mContentDescription = null;
            return true;
        }
        if (DEBUG) {
            android.util.Log.w(TAG, "onClearTranslation(): no translated text.");
            return false;
        }
        return false;
    }

    public boolean isShowingTranslation() {
        return this.mIsShowingTranslation;
    }

    public boolean isAnimationRunning() {
        return this.mAnimationRunning;
    }

    @Override // android.view.translation.ViewTranslationCallback
    public void enableContentPadding() {
        this.mIsTextPaddingEnabled = true;
    }

    boolean isTextPaddingEnabled() {
        return this.mIsTextPaddingEnabled;
    }

    java.lang.CharSequence getPaddedText(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        if (charSequence == null) {
            return null;
        }
        if (this.mPaddedText == null) {
            this.mPaddedText = computePaddedText(charSequence, charSequence2);
        }
        return this.mPaddedText;
    }

    private java.lang.CharSequence computePaddedText(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        if (charSequence2 == null) {
            return charSequence;
        }
        int length = charSequence2.length();
        if (length <= charSequence.length()) {
            return charSequence;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(length);
        sb.append(charSequence);
        for (int length2 = charSequence.length(); length2 < length; length2++) {
            sb.append(COMPAT_PAD_CHARACTER);
        }
        return sb;
    }

    @Override // android.view.translation.ViewTranslationCallback
    public void setAnimationDurationMillis(int i) {
        this.mAnimationDurationMillis = i;
    }

    private void runChangeTextWithAnimationIfNeeded(final android.widget.TextView textView, final java.lang.Runnable runnable) {
        if (!android.animation.ValueAnimator.areAnimatorsEnabled()) {
            runnable.run();
            return;
        }
        if (this.mAnimator != null) {
            this.mAnimator.end();
        }
        this.mAnimationRunning = true;
        this.mAnimator = android.animation.ValueAnimator.ofArgb(textView.getCurrentTextColor(), colorWithAlpha(textView.getCurrentTextColor(), 0));
        this.mAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.TextViewTranslationCallback$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                android.widget.TextView.this.setTextColor(((java.lang.Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        this.mAnimator.setRepeatMode(2);
        this.mAnimator.setRepeatCount(1);
        this.mAnimator.setDuration(this.mAnimationDurationMillis);
        final android.content.res.ColorStateList textColors = textView.getTextColors();
        final java.lang.ref.WeakReference weakReference = new java.lang.ref.WeakReference(textView);
        this.mAnimator.addListener(new android.animation.Animator.AnimatorListener() { // from class: android.widget.TextViewTranslationCallback.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(android.animation.Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                android.widget.TextView textView2 = (android.widget.TextView) weakReference.get();
                if (textView2 != null) {
                    textView2.setTextColor(textColors);
                }
                android.widget.TextViewTranslationCallback.this.mAnimator = null;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(android.animation.Animator animator) {
                runnable.run();
            }
        });
        this.mAnimator.start();
    }

    private static int colorWithAlpha(int i, int i2) {
        return android.graphics.Color.argb(i2, android.graphics.Color.red(i), android.graphics.Color.green(i), android.graphics.Color.blue(i));
    }
}
