package com.android.internal.view;

/* loaded from: classes5.dex */
public class BaseIWindow extends android.view.IWindow.Stub {
    private android.view.IWindowSession mSession;

    public void setSession(android.view.IWindowSession iWindowSession) {
        this.mSession = iWindowSession;
    }

    public void resized(android.window.ClientWindowFrames clientWindowFrames, boolean z, android.util.MergedConfiguration mergedConfiguration, android.view.InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) {
        if (z) {
            try {
                this.mSession.finishDrawing(this, null, i2);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // android.view.IWindow
    public void insetsControlChanged(android.view.InsetsState insetsState, android.view.InsetsSourceControl[] insetsSourceControlArr) {
    }

    @Override // android.view.IWindow
    public void showInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) {
    }

    @Override // android.view.IWindow
    public void hideInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) {
    }

    public void moved(int i, int i2) {
    }

    public void dispatchAppVisibility(boolean z) {
    }

    @Override // android.view.IWindow
    public void dispatchGetNewSurface() {
    }

    @Override // android.view.IWindow
    public void executeCommand(java.lang.String str, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.closeWithError("Unsupported command " + str);
            } catch (java.io.IOException e) {
            }
        }
    }

    @Override // android.view.IWindow
    public void closeSystemDialogs(java.lang.String str) {
    }

    public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) {
        if (z) {
            try {
                this.mSession.wallpaperOffsetsComplete(asBinder());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // android.view.IWindow
    public void dispatchDragEvent(android.view.DragEvent dragEvent) {
        if (dragEvent.getAction() == 3) {
            try {
                this.mSession.reportDropResult(this, false);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // android.view.IWindow
    public void updatePointerIcon(float f, float f2) {
        android.hardware.input.InputManagerGlobal.getInstance().setPointerIconType(1);
    }

    public void dispatchWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) {
        if (z) {
            try {
                this.mSession.wallpaperCommandComplete(asBinder(), null);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // android.view.IWindow
    public void dispatchWindowShown() {
    }

    @Override // android.view.IWindow
    public void requestAppKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) {
    }

    @Override // android.view.IWindow
    public void requestScrollCapture(android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        try {
            iScrollCaptureResponseListener.onScrollCaptureResponse(new android.view.ScrollCaptureResponse.Builder().setDescription("Not Implemented").build());
        } catch (android.os.RemoteException e) {
        }
    }
}
