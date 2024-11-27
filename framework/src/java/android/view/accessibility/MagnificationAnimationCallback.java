package android.view.accessibility;

/* loaded from: classes4.dex */
public interface MagnificationAnimationCallback {
    public static final android.view.accessibility.MagnificationAnimationCallback STUB_ANIMATION_CALLBACK = new android.view.accessibility.MagnificationAnimationCallback() { // from class: android.view.accessibility.MagnificationAnimationCallback$$ExternalSyntheticLambda0
        @Override // android.view.accessibility.MagnificationAnimationCallback
        public final void onResult(boolean z) {
            android.view.accessibility.MagnificationAnimationCallback.lambda$static$0(z);
        }
    };

    void onResult(boolean z);

    static /* synthetic */ void lambda$static$0(boolean z) {
    }

    default void onResult(boolean z, android.view.MagnificationSpec magnificationSpec) {
        onResult(z);
    }
}
