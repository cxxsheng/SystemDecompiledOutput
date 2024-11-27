package com.android.server.wm;

/* loaded from: classes3.dex */
public class SeamlessRotator {
    private final boolean mApplyFixedTransformHint;
    private final int mFixedTransformHint;
    private final int mNewRotation;
    private final int mOldRotation;
    private final android.graphics.Matrix mTransform = new android.graphics.Matrix();
    private final float[] mFloat9 = new float[9];

    public SeamlessRotator(int i, int i2, android.view.DisplayInfo displayInfo, boolean z) {
        this.mOldRotation = i;
        this.mNewRotation = i2;
        this.mApplyFixedTransformHint = z;
        this.mFixedTransformHint = i;
        boolean z2 = true;
        if (displayInfo.rotation != 1 && displayInfo.rotation != 3) {
            z2 = false;
        }
        int i3 = z2 ? displayInfo.logicalWidth : displayInfo.logicalHeight;
        int i4 = z2 ? displayInfo.logicalHeight : displayInfo.logicalWidth;
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        com.android.server.wm.utils.CoordinateTransforms.transformLogicalToPhysicalCoordinates(i, i4, i3, this.mTransform);
        com.android.server.wm.utils.CoordinateTransforms.transformPhysicalToLogicalCoordinates(i2, i4, i3, matrix);
        this.mTransform.postConcat(matrix);
    }

    public void unrotate(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.WindowContainer windowContainer) {
        applyTransform(transaction, windowContainer.getSurfaceControl());
        float[] fArr = {windowContainer.mLastSurfacePosition.x, windowContainer.mLastSurfacePosition.y};
        this.mTransform.mapPoints(fArr);
        transaction.setPosition(windowContainer.getSurfaceControl(), fArr[0], fArr[1]);
        if (this.mApplyFixedTransformHint) {
            transaction.setFixedTransformHint(windowContainer.mSurfaceControl, this.mFixedTransformHint);
        }
    }

    void applyTransform(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        transaction.setMatrix(surfaceControl, this.mTransform, this.mFloat9);
    }

    public int getOldRotation() {
        return this.mOldRotation;
    }

    void finish(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.WindowContainer windowContainer) {
        if (windowContainer.mSurfaceControl == null || !windowContainer.mSurfaceControl.isValid()) {
            return;
        }
        setIdentityMatrix(transaction, windowContainer.mSurfaceControl);
        transaction.setPosition(windowContainer.mSurfaceControl, windowContainer.mLastSurfacePosition.x, windowContainer.mLastSurfacePosition.y);
        if (this.mApplyFixedTransformHint) {
            transaction.unsetFixedTransformHint(windowContainer.mSurfaceControl);
        }
    }

    void setIdentityMatrix(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        transaction.setMatrix(surfaceControl, android.graphics.Matrix.IDENTITY_MATRIX, this.mFloat9);
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.print("{old=");
        printWriter.print(this.mOldRotation);
        printWriter.print(", new=");
        printWriter.print(this.mNewRotation);
        printWriter.print("}");
    }

    public java.lang.String toString() {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        dump(new java.io.PrintWriter(stringWriter));
        return "ForcedSeamlessRotator" + stringWriter.toString();
    }
}
