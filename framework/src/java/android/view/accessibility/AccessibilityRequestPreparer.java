package android.view.accessibility;

/* loaded from: classes4.dex */
public abstract class AccessibilityRequestPreparer {
    public static final int REQUEST_TYPE_EXTRA_DATA = 1;
    private final int mAccessibilityViewId;
    private final int mRequestTypes;
    private final java.lang.ref.WeakReference<android.view.View> mViewRef;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestTypes {
    }

    public abstract void onPrepareExtraData(int i, java.lang.String str, android.os.Bundle bundle, android.os.Message message);

    public AccessibilityRequestPreparer(android.view.View view, int i) {
        if (!view.isAttachedToWindow()) {
            throw new java.lang.IllegalStateException("View must be attached to a window");
        }
        this.mViewRef = new java.lang.ref.WeakReference<>(view);
        this.mAccessibilityViewId = view.getAccessibilityViewId();
        this.mRequestTypes = i;
        view.addOnAttachStateChangeListener(new android.view.accessibility.AccessibilityRequestPreparer.ViewAttachStateListener());
    }

    public android.view.View getView() {
        return this.mViewRef.get();
    }

    private class ViewAttachStateListener implements android.view.View.OnAttachStateChangeListener {
        private ViewAttachStateListener() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
            android.content.Context context = view.getContext();
            if (context != null) {
                ((android.view.accessibility.AccessibilityManager) context.getSystemService(android.view.accessibility.AccessibilityManager.class)).removeAccessibilityRequestPreparer(android.view.accessibility.AccessibilityRequestPreparer.this);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    }

    int getAccessibilityViewId() {
        return this.mAccessibilityViewId;
    }
}
