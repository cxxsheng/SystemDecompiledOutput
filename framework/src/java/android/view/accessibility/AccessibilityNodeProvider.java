package android.view.accessibility;

/* loaded from: classes4.dex */
public abstract class AccessibilityNodeProvider {
    public static final int HOST_VIEW_ID = -1;

    public android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
        return null;
    }

    public void addExtraDataToAccessibilityNodeInfo(int i, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, java.lang.String str, android.os.Bundle bundle) {
    }

    public boolean performAction(int i, int i2, android.os.Bundle bundle) {
        return false;
    }

    public java.util.List<android.view.accessibility.AccessibilityNodeInfo> findAccessibilityNodeInfosByText(java.lang.String str, int i) {
        return null;
    }

    public android.view.accessibility.AccessibilityNodeInfo findFocus(int i) {
        return null;
    }
}
