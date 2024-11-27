package com.android.server.wm;

/* loaded from: classes3.dex */
public class BlackFrame {
    private final com.android.server.wm.BlackFrame.BlackSurface[] mBlackSurfaces = new com.android.server.wm.BlackFrame.BlackSurface[4];
    private final android.graphics.Rect mInnerRect;
    private final android.graphics.Rect mOuterRect;
    private final java.util.function.Supplier<android.view.SurfaceControl.Transaction> mTransactionFactory;

    static class BlackSurface {
        final int layer;
        final int left;
        final android.view.SurfaceControl surface;
        final int top;

        BlackSurface(android.view.SurfaceControl.Transaction transaction, int i, int i2, int i3, int i4, int i5, com.android.server.wm.DisplayContent displayContent, android.view.SurfaceControl surfaceControl) throws android.view.Surface.OutOfResourcesException {
            this.left = i2;
            this.top = i3;
            this.layer = i;
            this.surface = displayContent.makeOverlay().setName("BlackSurface").setColorLayer().setParent(surfaceControl).setCallsite("BlackSurface").build();
            transaction.setWindowCrop(this.surface, i4 - i2, i5 - i3);
            transaction.setAlpha(this.surface, 1.0f);
            transaction.setLayer(this.surface, i);
            transaction.setPosition(this.surface, this.left, this.top);
            transaction.show(this.surface);
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, -2963535976860666511L, 4, null, java.lang.String.valueOf(this.surface), java.lang.Long.valueOf(i));
        }
    }

    public void printTo(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("Outer: ");
        this.mOuterRect.printShortString(printWriter);
        printWriter.print(" / Inner: ");
        this.mInnerRect.printShortString(printWriter);
        printWriter.println();
        for (int i = 0; i < this.mBlackSurfaces.length; i++) {
            com.android.server.wm.BlackFrame.BlackSurface blackSurface = this.mBlackSurfaces[i];
            printWriter.print(str);
            printWriter.print("#");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.print(blackSurface.surface);
            printWriter.print(" left=");
            printWriter.print(blackSurface.left);
            printWriter.print(" top=");
            printWriter.println(blackSurface.top);
        }
    }

    public BlackFrame(java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier, android.view.SurfaceControl.Transaction transaction, android.graphics.Rect rect, android.graphics.Rect rect2, int i, com.android.server.wm.DisplayContent displayContent, boolean z, android.view.SurfaceControl surfaceControl) throws android.view.Surface.OutOfResourcesException {
        this.mTransactionFactory = supplier;
        this.mOuterRect = new android.graphics.Rect(rect);
        this.mInnerRect = new android.graphics.Rect(rect2);
        try {
            if (rect.top < rect2.top) {
                this.mBlackSurfaces[0] = new com.android.server.wm.BlackFrame.BlackSurface(transaction, i, rect.left, rect.top, rect2.right, rect2.top, displayContent, surfaceControl);
            }
            if (rect.left < rect2.left) {
                this.mBlackSurfaces[1] = new com.android.server.wm.BlackFrame.BlackSurface(transaction, i, rect.left, rect2.top, rect2.left, rect.bottom, displayContent, surfaceControl);
            }
            if (rect.bottom > rect2.bottom) {
                this.mBlackSurfaces[2] = new com.android.server.wm.BlackFrame.BlackSurface(transaction, i, rect2.left, rect2.bottom, rect.right, rect.bottom, displayContent, surfaceControl);
            }
            if (rect.right > rect2.right) {
                this.mBlackSurfaces[3] = new com.android.server.wm.BlackFrame.BlackSurface(transaction, i, rect2.right, rect.top, rect.right, rect2.bottom, displayContent, surfaceControl);
            }
        } catch (java.lang.Throwable th) {
            kill();
            throw th;
        }
    }

    public void kill() {
        android.view.SurfaceControl.Transaction transaction = this.mTransactionFactory.get();
        for (int i = 0; i < this.mBlackSurfaces.length; i++) {
            if (this.mBlackSurfaces[i] != null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, -5633771912572750947L, 0, null, java.lang.String.valueOf(this.mBlackSurfaces[i].surface));
                transaction.remove(this.mBlackSurfaces[i].surface);
                this.mBlackSurfaces[i] = null;
            }
        }
        transaction.apply();
    }
}
