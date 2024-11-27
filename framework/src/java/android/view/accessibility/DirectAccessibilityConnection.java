package android.view.accessibility;

/* loaded from: classes4.dex */
class DirectAccessibilityConnection extends android.accessibilityservice.IAccessibilityServiceConnection.Default {
    private static final int FETCH_FLAGS = 384;
    private static final android.graphics.Region INTERACTIVE_REGION = null;
    private final android.view.accessibility.IAccessibilityInteractionConnection mAccessibilityInteractionConnection;
    private final android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private final int mMyProcessId = android.os.Process.myPid();

    DirectAccessibilityConnection(android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection, android.view.accessibility.AccessibilityManager accessibilityManager) {
        this.mAccessibilityInteractionConnection = iAccessibilityInteractionConnection;
        this.mAccessibilityManager = accessibilityManager;
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public java.lang.String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, android.os.Bundle bundle) throws android.os.RemoteException {
        android.view.accessibility.IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = this.mAccessibilityManager.getWindowTransformationSpec(i);
        this.mAccessibilityInteractionConnection.findAccessibilityNodeInfoByAccessibilityId(j, INTERACTIVE_REGION, i2, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2, windowTransformationSpec.magnificationSpec, windowTransformationSpec.transformationMatrix, bundle);
        return new java.lang.String[0];
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public java.lang.String[] findAccessibilityNodeInfosByText(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        android.view.accessibility.IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = this.mAccessibilityManager.getWindowTransformationSpec(i);
        this.mAccessibilityInteractionConnection.findAccessibilityNodeInfosByText(j, str, INTERACTIVE_REGION, i2, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2, windowTransformationSpec.magnificationSpec, windowTransformationSpec.transformationMatrix);
        return new java.lang.String[0];
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public java.lang.String[] findAccessibilityNodeInfosByViewId(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        android.view.accessibility.IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = this.mAccessibilityManager.getWindowTransformationSpec(i);
        this.mAccessibilityInteractionConnection.findAccessibilityNodeInfosByViewId(j, str, INTERACTIVE_REGION, i2, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2, windowTransformationSpec.magnificationSpec, windowTransformationSpec.transformationMatrix);
        return new java.lang.String[0];
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public java.lang.String[] findFocus(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        android.view.accessibility.IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = this.mAccessibilityManager.getWindowTransformationSpec(i);
        this.mAccessibilityInteractionConnection.findFocus(j, i2, INTERACTIVE_REGION, i3, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2, windowTransformationSpec.magnificationSpec, windowTransformationSpec.transformationMatrix);
        return new java.lang.String[0];
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public java.lang.String[] focusSearch(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        android.view.accessibility.IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = this.mAccessibilityManager.getWindowTransformationSpec(i);
        this.mAccessibilityInteractionConnection.focusSearch(j, i2, INTERACTIVE_REGION, i3, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2, windowTransformationSpec.magnificationSpec, windowTransformationSpec.transformationMatrix);
        return new java.lang.String[0];
    }

    @Override // android.accessibilityservice.IAccessibilityServiceConnection.Default, android.accessibilityservice.IAccessibilityServiceConnection
    public boolean performAccessibilityAction(int i, long j, int i2, android.os.Bundle bundle, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        this.mAccessibilityInteractionConnection.performAccessibilityAction(j, i2, bundle, i3, iAccessibilityInteractionConnectionCallback, 384, this.mMyProcessId, j2);
        return true;
    }
}
