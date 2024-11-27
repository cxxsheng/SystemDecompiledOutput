package com.android.server.wm;

/* loaded from: classes3.dex */
class TrustedOverlayHost {
    final java.util.ArrayList<android.view.SurfaceControlViewHost.SurfacePackage> mOverlays = new java.util.ArrayList<>();
    android.view.SurfaceControl mSurfaceControl;
    final com.android.server.wm.WindowManagerService mWmService;

    TrustedOverlayHost(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mWmService = windowManagerService;
    }

    void requireOverlaySurfaceControl() {
        if (this.mSurfaceControl == null) {
            this.mSurfaceControl = this.mWmService.makeSurfaceBuilder(null).setContainerLayer().setHidden(true).setName("Overlay Host Leash").build();
            this.mWmService.mTransactionFactory.get().setTrustedOverlay(this.mSurfaceControl, true).apply();
        }
    }

    void setParent(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        if (this.mSurfaceControl == null) {
            return;
        }
        transaction.reparent(this.mSurfaceControl, surfaceControl);
        if (surfaceControl != null) {
            transaction.show(this.mSurfaceControl);
        } else {
            transaction.hide(this.mSurfaceControl);
        }
    }

    void setLayer(android.view.SurfaceControl.Transaction transaction, int i) {
        if (this.mSurfaceControl != null) {
            transaction.setLayer(this.mSurfaceControl, i);
        }
    }

    void setVisibility(android.view.SurfaceControl.Transaction transaction, boolean z) {
        if (this.mSurfaceControl != null) {
            transaction.setVisibility(this.mSurfaceControl, z);
        }
    }

    void addOverlay(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, android.view.SurfaceControl surfaceControl) {
        requireOverlaySurfaceControl();
        boolean z = false;
        for (int size = this.mOverlays.size() - 1; size >= 0; size--) {
            if (this.mOverlays.get(size).getSurfaceControl().isSameSurface(surfacePackage.getSurfaceControl())) {
                z = true;
            }
        }
        if (!z) {
            this.mOverlays.add(surfacePackage);
        }
        android.view.SurfaceControl.Transaction transaction = this.mWmService.mTransactionFactory.get();
        transaction.reparent(surfacePackage.getSurfaceControl(), this.mSurfaceControl).show(surfacePackage.getSurfaceControl());
        setParent(transaction, surfaceControl);
        transaction.apply();
    }

    boolean removeOverlay(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
        android.view.SurfaceControl.Transaction transaction = this.mWmService.mTransactionFactory.get();
        for (int size = this.mOverlays.size() - 1; size >= 0; size--) {
            android.view.SurfaceControlViewHost.SurfacePackage surfacePackage2 = this.mOverlays.get(size);
            if (surfacePackage2.getSurfaceControl().isSameSurface(surfacePackage.getSurfaceControl())) {
                this.mOverlays.remove(size);
                transaction.reparent(surfacePackage2.getSurfaceControl(), null);
                surfacePackage2.release();
            }
        }
        transaction.apply();
        return this.mOverlays.size() > 0;
    }

    void dispatchConfigurationChanged(android.content.res.Configuration configuration) {
        for (int size = this.mOverlays.size() - 1; size >= 0; size--) {
            android.view.SurfaceControlViewHost.SurfacePackage surfacePackage = this.mOverlays.get(size);
            try {
                surfacePackage.getRemoteInterface().onConfigurationChanged(configuration);
            } catch (java.lang.Exception e) {
                removeOverlay(surfacePackage);
            }
        }
    }

    private void dispatchDetachedFromWindow() {
        for (int size = this.mOverlays.size() - 1; size >= 0; size--) {
            android.view.SurfaceControlViewHost.SurfacePackage surfacePackage = this.mOverlays.get(size);
            try {
                surfacePackage.getRemoteInterface().onDispatchDetachedFromWindow();
            } catch (java.lang.Exception e) {
            }
            surfacePackage.release();
        }
    }

    void dispatchInsetsChanged(android.view.InsetsState insetsState, android.graphics.Rect rect) {
        for (int size = this.mOverlays.size() - 1; size >= 0; size--) {
            try {
                this.mOverlays.get(size).getRemoteInterface().onInsetsChanged(insetsState, rect);
            } catch (java.lang.Exception e) {
            }
        }
    }

    void release() {
        dispatchDetachedFromWindow();
        this.mOverlays.clear();
        this.mWmService.mTransactionFactory.get().remove(this.mSurfaceControl).apply();
        this.mSurfaceControl = null;
    }
}
