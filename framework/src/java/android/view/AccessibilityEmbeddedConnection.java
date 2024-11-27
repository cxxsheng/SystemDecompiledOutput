package android.view;

/* loaded from: classes4.dex */
final class AccessibilityEmbeddedConnection extends android.view.accessibility.IAccessibilityEmbeddedConnection.Stub {
    private final android.graphics.Matrix mTmpWindowMatrix = new android.graphics.Matrix();
    private final java.lang.ref.WeakReference<android.view.ViewRootImpl> mViewRootImpl;

    AccessibilityEmbeddedConnection(android.view.ViewRootImpl viewRootImpl) {
        this.mViewRootImpl = new java.lang.ref.WeakReference<>(viewRootImpl);
    }

    @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
    public android.os.IBinder associateEmbeddedHierarchy(android.os.IBinder iBinder, int i) {
        android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
        if (viewRootImpl != null) {
            android.view.accessibility.AccessibilityManager accessibilityManager = android.view.accessibility.AccessibilityManager.getInstance(viewRootImpl.mContext);
            viewRootImpl.mAttachInfo.mLeashedParentToken = iBinder;
            viewRootImpl.mAttachInfo.mLeashedParentAccessibilityViewId = i;
            if (accessibilityManager.isEnabled()) {
                accessibilityManager.associateEmbeddedHierarchy(iBinder, viewRootImpl.mLeashToken);
            }
            return viewRootImpl.mLeashToken;
        }
        return null;
    }

    @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
    public void disassociateEmbeddedHierarchy() {
        android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
        if (viewRootImpl != null) {
            android.view.accessibility.AccessibilityManager accessibilityManager = android.view.accessibility.AccessibilityManager.getInstance(viewRootImpl.mContext);
            viewRootImpl.mAttachInfo.mLeashedParentToken = null;
            viewRootImpl.mAttachInfo.mLeashedParentAccessibilityViewId = -1;
            if (accessibilityManager.isEnabled()) {
                accessibilityManager.disassociateEmbeddedHierarchy(viewRootImpl.mLeashToken);
            }
        }
    }

    @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
    public void setWindowMatrix(float[] fArr) {
        android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
        if (viewRootImpl != null) {
            this.mTmpWindowMatrix.setValues(fArr);
            if (viewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy == null) {
                viewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy = new android.graphics.Matrix();
            }
            viewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy.set(this.mTmpWindowMatrix);
        }
    }
}
