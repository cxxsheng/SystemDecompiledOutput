package android.view;

/* loaded from: classes4.dex */
public interface AttachedSurfaceControl {

    public interface OnBufferTransformHintChangedListener {
        void onBufferTransformHintChanged(int i);
    }

    boolean applyTransactionOnDraw(android.view.SurfaceControl.Transaction transaction);

    android.view.SurfaceControl.Transaction buildReparentTransaction(android.view.SurfaceControl surfaceControl);

    default int getBufferTransformHint() {
        return 0;
    }

    default void addOnBufferTransformHintChangedListener(android.view.AttachedSurfaceControl.OnBufferTransformHintChangedListener onBufferTransformHintChangedListener) {
    }

    default void removeOnBufferTransformHintChangedListener(android.view.AttachedSurfaceControl.OnBufferTransformHintChangedListener onBufferTransformHintChangedListener) {
    }

    default void setTouchableRegion(android.graphics.Region region) {
    }

    default android.window.SurfaceSyncGroup getOrCreateSurfaceSyncGroup() {
        return null;
    }

    default void setChildBoundingInsets(android.graphics.Rect rect) {
    }

    default android.window.InputTransferToken getInputTransferToken() {
        throw new java.lang.UnsupportedOperationException("The getInputTransferToken needs to be implemented before making this call.");
    }
}
