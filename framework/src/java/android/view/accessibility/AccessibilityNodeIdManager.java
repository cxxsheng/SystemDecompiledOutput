package android.view.accessibility;

/* loaded from: classes4.dex */
public final class AccessibilityNodeIdManager {
    private static android.view.accessibility.AccessibilityNodeIdManager sIdManager;
    private android.view.accessibility.WeakSparseArray<android.view.View> mIdsToViews = new android.view.accessibility.WeakSparseArray<>();

    public static synchronized android.view.accessibility.AccessibilityNodeIdManager getInstance() {
        android.view.accessibility.AccessibilityNodeIdManager accessibilityNodeIdManager;
        synchronized (android.view.accessibility.AccessibilityNodeIdManager.class) {
            if (sIdManager == null) {
                sIdManager = new android.view.accessibility.AccessibilityNodeIdManager();
            }
            accessibilityNodeIdManager = sIdManager;
        }
        return accessibilityNodeIdManager;
    }

    private AccessibilityNodeIdManager() {
    }

    public void registerViewWithId(android.view.View view, int i) {
        synchronized (this.mIdsToViews) {
            this.mIdsToViews.append(i, view);
        }
    }

    public void unregisterViewWithId(int i) {
        synchronized (this.mIdsToViews) {
            this.mIdsToViews.remove(i);
        }
    }

    public android.view.View findView(int i) {
        android.view.View view;
        synchronized (this.mIdsToViews) {
            view = this.mIdsToViews.get(i);
        }
        if (view == null || !view.includeForAccessibility()) {
            return null;
        }
        return view;
    }
}
