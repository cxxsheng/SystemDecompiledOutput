package android.view;

/* loaded from: classes4.dex */
public class WindowlessWindowManager implements android.view.IWindowSession {
    private static final java.lang.String TAG = "WindowlessWindowManager";
    private final android.content.res.Configuration mConfiguration;
    final android.window.InputTransferToken mHostInputTransferToken;
    private android.view.InsetsState mInsetsState;
    private android.view.ISurfaceControlViewHostParent mParentInterface;
    protected final android.view.SurfaceControl mRootSurface;
    final java.util.HashMap<android.os.IBinder, android.view.WindowlessWindowManager.State> mStateForWindow = new java.util.HashMap<>();
    final java.util.HashMap<android.os.IBinder, android.view.WindowlessWindowManager.ResizeCompleteCallback> mResizeCompletionForWindow = new java.util.HashMap<>();
    private final android.view.SurfaceSession mSurfaceSession = new android.view.SurfaceSession();
    private final android.window.InputTransferToken mInputTransferToken = new android.window.InputTransferToken();
    private final android.window.ClientWindowFrames mTmpFrames = new android.window.ClientWindowFrames();
    private final android.util.MergedConfiguration mTmpConfig = new android.util.MergedConfiguration();
    private final android.view.WindowlessWindowLayout mLayout = new android.view.WindowlessWindowLayout();
    private final android.view.IWindowSession mRealWm = android.view.WindowManagerGlobal.getWindowSession();

    public interface ResizeCompleteCallback {
        void finished(android.view.SurfaceControl.Transaction transaction);
    }

    private class State {
        android.graphics.Rect mAttachedFrame;
        android.view.IWindow mClient;
        int mDisplayId;
        android.graphics.Rect mFrame;
        android.os.IBinder mInputChannelToken;
        android.graphics.Region mInputRegion;
        android.window.InputTransferToken mInputTransferToken;
        android.view.SurfaceControl mLeash;
        android.view.SurfaceControl mSurfaceControl;
        final android.view.WindowManager.LayoutParams mParams = new android.view.WindowManager.LayoutParams();
        final android.view.WindowManager.LayoutParams mLastReportedParams = new android.view.WindowManager.LayoutParams();

        State(android.view.SurfaceControl surfaceControl, android.view.WindowManager.LayoutParams layoutParams, int i, android.view.IWindow iWindow, android.view.SurfaceControl surfaceControl2, android.graphics.Rect rect) {
            this.mSurfaceControl = surfaceControl;
            this.mParams.copyFrom(layoutParams);
            this.mDisplayId = i;
            this.mClient = iWindow;
            this.mLeash = surfaceControl2;
            this.mFrame = rect;
        }
    }

    public WindowlessWindowManager(android.content.res.Configuration configuration, android.view.SurfaceControl surfaceControl, android.window.InputTransferToken inputTransferToken) {
        this.mRootSurface = surfaceControl;
        this.mConfiguration = new android.content.res.Configuration(configuration);
        this.mHostInputTransferToken = inputTransferToken;
    }

    public void setConfiguration(android.content.res.Configuration configuration) {
        this.mConfiguration.setTo(configuration);
    }

    android.window.InputTransferToken getInputTransferToken(android.os.IBinder iBinder) {
        synchronized (this) {
            if (this.mStateForWindow.isEmpty()) {
                return this.mInputTransferToken;
            }
            android.view.WindowlessWindowManager.State state = this.mStateForWindow.get(iBinder);
            if (state != null) {
                return state.mInputTransferToken;
            }
            android.util.Log.w(TAG, "Failed to get focusGrantToken. Returning null token");
            return null;
        }
    }

    void setCompletionCallback(android.os.IBinder iBinder, android.view.WindowlessWindowManager.ResizeCompleteCallback resizeCompleteCallback) {
        if (this.mResizeCompletionForWindow.get(iBinder) != null) {
            android.util.Log.w(TAG, "Unsupported overlapping resizes");
        }
        this.mResizeCompletionForWindow.put(iBinder, resizeCompleteCallback);
    }

    protected void setTouchRegion(android.os.IBinder iBinder, android.graphics.Region region) {
        synchronized (this) {
            android.view.WindowlessWindowManager.State state = this.mStateForWindow.get(iBinder);
            if (state == null) {
                return;
            }
            if (java.util.Objects.equals(region, state.mInputRegion)) {
                return;
            }
            state.mInputRegion = region != null ? new android.graphics.Region(region) : null;
            if (state.mInputChannelToken != null) {
                try {
                    this.mRealWm.updateInputChannel(state.mInputChannelToken, state.mDisplayId, state.mSurfaceControl, state.mParams.flags, state.mParams.privateFlags, state.mParams.inputFeatures, state.mInputRegion);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Failed to update surface input channel: ", e);
                }
            }
        }
    }

    protected android.view.SurfaceControl getParentSurface(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams) {
        synchronized (this) {
            if (this.mStateForWindow.isEmpty()) {
                return this.mRootSurface;
            }
            return this.mStateForWindow.get(layoutParams.token).mLeash;
        }
    }

    @Override // android.view.IWindowSession
    public int addToDisplay(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) {
        android.view.WindowlessWindowManager.State state;
        android.view.SurfaceControl build = new android.view.SurfaceControl.Builder(this.mSurfaceSession).setName(layoutParams.getTitle().toString() + "Leash").setCallsite("WindowlessWindowManager.addToDisplay").setParent(getParentSurface(iWindow, layoutParams)).build();
        android.view.SurfaceControl build2 = new android.view.SurfaceControl.Builder(this.mSurfaceSession).setFormat(layoutParams.format).setBLASTLayer().setName(layoutParams.getTitle().toString()).setCallsite("WindowlessWindowManager.addToDisplay").setHidden(false).setParent(build).build();
        android.view.WindowlessWindowManager.State state2 = new android.view.WindowlessWindowManager.State(build2, layoutParams, i2, iWindow, build, new android.graphics.Rect());
        synchronized (this) {
            android.view.WindowlessWindowManager.State state3 = this.mStateForWindow.get(layoutParams.token);
            if (state3 != null) {
                state2.mAttachedFrame = state3.mFrame;
            }
            if (this.mStateForWindow.isEmpty()) {
                state2.mInputTransferToken = this.mInputTransferToken;
            } else {
                state2.mInputTransferToken = new android.window.InputTransferToken();
            }
            this.mStateForWindow.put(iWindow.asBinder(), state2);
        }
        if (state2.mAttachedFrame == null) {
            rect.set(0, 0, -1, -1);
        } else {
            rect.set(state2.mAttachedFrame);
        }
        fArr[0] = 1.0f;
        if ((layoutParams.inputFeatures & 1) == 0) {
            try {
                if (this.mRealWm instanceof android.view.IWindowSession.Stub) {
                    this.mRealWm.grantInputChannel(i2, new android.view.SurfaceControl(build2, "WindowlessWindowManager.addToDisplay"), iWindow.asBinder(), this.mHostInputTransferToken, layoutParams.flags, layoutParams.privateFlags, layoutParams.inputFeatures, layoutParams.type, layoutParams.token, state2.mInputTransferToken, layoutParams.getTitle().toString(), inputChannel);
                    state = state2;
                } else {
                    state = state2;
                    this.mRealWm.grantInputChannel(i2, build2, iWindow.asBinder(), this.mHostInputTransferToken, layoutParams.flags, layoutParams.privateFlags, layoutParams.inputFeatures, layoutParams.type, layoutParams.token, state2.mInputTransferToken, layoutParams.getTitle().toString(), inputChannel);
                }
                state.mInputChannelToken = inputChannel != null ? inputChannel.getToken() : null;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to grant input to surface: ", e);
            }
        }
        sendLayoutParamsToParent();
        return isInTouchModeInternal(i2) ? 3 : 2;
    }

    @Override // android.view.IWindowSession
    public int addToDisplayAsUser(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) {
        return addToDisplay(iWindow, layoutParams, i, i2, i4, inputChannel, insetsState, array, rect, fArr);
    }

    @Override // android.view.IWindowSession
    public int addToDisplayWithoutInputChannel(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, android.view.InsetsState insetsState, android.graphics.Rect rect, float[] fArr) {
        return 0;
    }

    @Override // android.view.IWindowSession
    public void remove(android.os.IBinder iBinder) throws android.os.RemoteException {
        android.view.WindowlessWindowManager.State remove;
        this.mRealWm.remove(iBinder);
        synchronized (this) {
            remove = this.mStateForWindow.remove(iBinder);
        }
        if (remove == null) {
            throw new java.lang.IllegalArgumentException("Invalid window token (never added or removed already)");
        }
        removeSurface(remove.mSurfaceControl);
        removeSurface(remove.mLeash);
    }

    protected void removeSurface(android.view.SurfaceControl surfaceControl) {
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        try {
            transaction.remove(surfaceControl).apply();
            transaction.close();
        } catch (java.lang.Throwable th) {
            try {
                transaction.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private boolean isOpaque(android.view.WindowManager.LayoutParams layoutParams) {
        if ((layoutParams.surfaceInsets != null && layoutParams.surfaceInsets.left != 0) || layoutParams.surfaceInsets.top != 0 || layoutParams.surfaceInsets.right != 0 || layoutParams.surfaceInsets.bottom != 0) {
            return false;
        }
        return !android.graphics.PixelFormat.formatHasAlpha(layoutParams.format);
    }

    private boolean isInTouchModeInternal(int i) {
        try {
            return android.view.WindowManagerGlobal.getWindowManagerService().isInTouchMode(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to check if the window is in touch mode", e);
            return false;
        }
    }

    protected android.os.IBinder getWindowBinder(android.view.View view) {
        android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (viewRootImpl == null) {
            return null;
        }
        return viewRootImpl.mWindow.asBinder();
    }

    protected android.view.SurfaceControl getSurfaceControl(android.view.View view) {
        android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (viewRootImpl == null) {
            return null;
        }
        return getSurfaceControl(viewRootImpl.mWindow);
    }

    protected android.view.SurfaceControl getSurfaceControl(android.view.IWindow iWindow) {
        android.view.WindowlessWindowManager.State state = this.mStateForWindow.get(iWindow.asBinder());
        if (state == null) {
            return null;
        }
        return state.mSurfaceControl;
    }

    @Override // android.view.IWindowSession
    public int relayout(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, android.window.ClientWindowFrames clientWindowFrames, android.util.MergedConfiguration mergedConfiguration, android.view.SurfaceControl surfaceControl, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.os.Bundle bundle) {
        android.view.WindowlessWindowManager.State state;
        int i7;
        synchronized (this) {
            state = this.mStateForWindow.get(iWindow.asBinder());
        }
        if (state == null) {
            throw new java.lang.IllegalArgumentException("Invalid window token (never added or removed already)");
        }
        android.view.SurfaceControl surfaceControl2 = state.mSurfaceControl;
        android.view.SurfaceControl surfaceControl3 = state.mLeash;
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        if (layoutParams == null) {
            i7 = 0;
        } else {
            i7 = state.mParams.copyFrom(layoutParams);
        }
        android.view.WindowManager.LayoutParams layoutParams2 = state.mParams;
        android.window.ClientWindowFrames clientWindowFrames2 = new android.window.ClientWindowFrames();
        clientWindowFrames2.attachedFrame = state.mAttachedFrame;
        this.mLayout.computeFrames(layoutParams2, null, null, null, 0, i, i2, 0, 0.0f, clientWindowFrames2);
        state.mFrame.set(clientWindowFrames2.frame);
        if (clientWindowFrames != null) {
            clientWindowFrames.frame.set(clientWindowFrames2.frame);
            clientWindowFrames.parentFrame.set(clientWindowFrames2.parentFrame);
            clientWindowFrames.displayFrame.set(clientWindowFrames2.displayFrame);
        }
        transaction.setPosition(surfaceControl3, clientWindowFrames2.frame.left, clientWindowFrames2.frame.top);
        if (i3 == 0) {
            transaction.setOpaque(surfaceControl2, isOpaque(layoutParams2)).show(surfaceControl3).apply();
            if (surfaceControl != null) {
                surfaceControl.copyFrom(surfaceControl2, "WindowlessWindowManager.relayout");
            }
        } else {
            transaction.hide(surfaceControl3).apply();
            if (surfaceControl != null) {
                surfaceControl.release();
            }
        }
        if (mergedConfiguration != null) {
            mergedConfiguration.setConfiguration(this.mConfiguration, this.mConfiguration);
        }
        if ((i7 & 65540) != 0 && state.mInputChannelToken != null) {
            try {
                if (this.mRealWm instanceof android.view.IWindowSession.Stub) {
                    this.mRealWm.updateInputChannel(state.mInputChannelToken, state.mDisplayId, new android.view.SurfaceControl(surfaceControl2, "WindowlessWindowManager.relayout"), layoutParams2.flags, layoutParams2.privateFlags, layoutParams2.inputFeatures, state.mInputRegion);
                } else {
                    this.mRealWm.updateInputChannel(state.mInputChannelToken, state.mDisplayId, surfaceControl2, layoutParams2.flags, layoutParams2.privateFlags, layoutParams2.inputFeatures, state.mInputRegion);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to update surface input channel: ", e);
            }
        }
        if (insetsState != null && this.mInsetsState != null) {
            insetsState.set(this.mInsetsState);
        }
        sendLayoutParamsToParent();
        return 0;
    }

    @Override // android.view.IWindowSession
    public void relayoutAsync(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6) {
        relayout(iWindow, layoutParams, i, i2, i3, i4, i5, i6, null, null, null, null, null, null);
    }

    @Override // android.view.IWindowSession
    public boolean outOfMemory(android.view.IWindow iWindow) {
        return false;
    }

    @Override // android.view.IWindowSession
    public void setInsets(android.view.IWindow iWindow, int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Region region) {
        setTouchRegion(iWindow.asBinder(), region);
    }

    @Override // android.view.IWindowSession
    public void clearTouchableRegion(android.view.IWindow iWindow) {
        setTouchRegion(iWindow.asBinder(), null);
    }

    @Override // android.view.IWindowSession
    public void finishDrawing(android.view.IWindow iWindow, android.view.SurfaceControl.Transaction transaction, int i) {
        synchronized (this) {
            android.view.WindowlessWindowManager.ResizeCompleteCallback resizeCompleteCallback = this.mResizeCompletionForWindow.get(iWindow.asBinder());
            if (resizeCompleteCallback == null) {
                transaction.apply();
            } else {
                resizeCompleteCallback.finished(transaction);
                this.mResizeCompletionForWindow.remove(iWindow.asBinder());
            }
        }
    }

    @Override // android.view.IWindowSession
    public boolean performHapticFeedback(int i, boolean z, boolean z2) {
        return false;
    }

    @Override // android.view.IWindowSession
    public void performHapticFeedbackAsync(int i, boolean z, boolean z2) {
        performHapticFeedback(i, z, z2);
    }

    @Override // android.view.IWindowSession
    public android.os.IBinder performDrag(android.view.IWindow iWindow, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, android.content.ClipData clipData) {
        return null;
    }

    @Override // android.view.IWindowSession
    public void reportDropResult(android.view.IWindow iWindow, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void cancelDragAndDrop(android.os.IBinder iBinder, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void dragRecipientEntered(android.view.IWindow iWindow) {
    }

    @Override // android.view.IWindowSession
    public void dragRecipientExited(android.view.IWindow iWindow) {
    }

    @Override // android.view.IWindowSession
    public void setWallpaperPosition(android.os.IBinder iBinder, float f, float f2, float f3, float f4) {
    }

    @Override // android.view.IWindowSession
    public void setWallpaperZoomOut(android.os.IBinder iBinder, float f) {
    }

    @Override // android.view.IWindowSession
    public void setShouldZoomOutWallpaper(android.os.IBinder iBinder, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void wallpaperOffsetsComplete(android.os.IBinder iBinder) {
    }

    @Override // android.view.IWindowSession
    public void setWallpaperDisplayOffset(android.os.IBinder iBinder, int i, int i2) {
    }

    @Override // android.view.IWindowSession
    public void sendWallpaperCommand(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void wallpaperCommandComplete(android.os.IBinder iBinder, android.os.Bundle bundle) {
    }

    @Override // android.view.IWindowSession
    public void onRectangleOnScreenRequested(android.os.IBinder iBinder, android.graphics.Rect rect) {
    }

    @Override // android.view.IWindowSession
    public android.view.IWindowId getWindowId(android.os.IBinder iBinder) {
        return null;
    }

    @Override // android.view.IWindowSession
    public void pokeDrawLock(android.os.IBinder iBinder) {
    }

    @Override // android.view.IWindowSession
    public boolean startMovingTask(android.view.IWindow iWindow, float f, float f2) {
        return false;
    }

    @Override // android.view.IWindowSession
    public void finishMovingTask(android.view.IWindow iWindow) {
    }

    @Override // android.view.IWindowSession
    public void updatePointerIcon(android.view.IWindow iWindow) {
    }

    @Override // android.view.IWindowSession
    public void updateTapExcludeRegion(android.view.IWindow iWindow, android.graphics.Region region) {
    }

    @Override // android.view.IWindowSession
    public void updateRequestedVisibleTypes(android.view.IWindow iWindow, int i) {
    }

    @Override // android.view.IWindowSession
    public void reportSystemGestureExclusionChanged(android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list) {
    }

    @Override // android.view.IWindowSession
    public void reportDecorViewGestureInterceptionChanged(android.view.IWindow iWindow, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void reportKeepClearAreasChanged(android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) {
    }

    @Override // android.view.IWindowSession
    public void grantInputChannel(int i, android.view.SurfaceControl surfaceControl, android.os.IBinder iBinder, android.window.InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, android.os.IBinder iBinder2, android.window.InputTransferToken inputTransferToken2, java.lang.String str, android.view.InputChannel inputChannel) {
    }

    @Override // android.view.IWindowSession
    public void updateInputChannel(android.os.IBinder iBinder, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, android.graphics.Region region) {
    }

    @Override // android.os.IInterface
    public android.os.IBinder asBinder() {
        return null;
    }

    @Override // android.view.IWindowSession
    public void grantEmbeddedWindowFocus(android.view.IWindow iWindow, android.window.InputTransferToken inputTransferToken, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void generateDisplayHash(android.view.IWindow iWindow, android.graphics.Rect rect, java.lang.String str, android.os.RemoteCallback remoteCallback) {
    }

    @Override // android.view.IWindowSession
    public void setOnBackInvokedCallbackInfo(android.view.IWindow iWindow, android.window.OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) throws android.os.RemoteException {
    }

    @Override // android.view.IWindowSession
    public boolean dropForAccessibility(android.view.IWindow iWindow, int i, int i2) {
        return false;
    }

    public void setInsetsState(android.view.InsetsState insetsState) {
        this.mInsetsState = insetsState;
        for (android.view.WindowlessWindowManager.State state : this.mStateForWindow.values()) {
            try {
                this.mTmpFrames.frame.set(0, 0, state.mParams.width, state.mParams.height);
                this.mTmpFrames.displayFrame.set(this.mTmpFrames.frame);
                this.mTmpConfig.setConfiguration(this.mConfiguration, this.mConfiguration);
                state.mClient.resized(this.mTmpFrames, false, this.mTmpConfig, insetsState, false, false, state.mDisplayId, Integer.MAX_VALUE, false);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // android.view.IWindowSession
    public boolean cancelDraw(android.view.IWindow iWindow) {
        return false;
    }

    @Override // android.view.IWindowSession
    public boolean moveFocusToAdjacentWindow(android.view.IWindow iWindow, int i) {
        android.util.Log.e(TAG, "Received request to moveFocusToAdjacentWindow on WindowlessWindowManager. We shouldn't get here!");
        return false;
    }

    void setParentInterface(android.view.ISurfaceControlViewHostParent iSurfaceControlViewHostParent) {
        if ((this.mParentInterface == null ? null : this.mParentInterface.asBinder()) != (iSurfaceControlViewHostParent != null ? iSurfaceControlViewHostParent.asBinder() : null)) {
            clearLastReportedParams();
        }
        this.mParentInterface = iSurfaceControlViewHostParent;
        sendLayoutParamsToParent();
    }

    private void clearLastReportedParams() {
        android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams();
        java.util.Iterator<android.view.WindowlessWindowManager.State> it = this.mStateForWindow.values().iterator();
        while (it.hasNext()) {
            it.next().mLastReportedParams.copyFrom(layoutParams);
        }
    }

    private void sendLayoutParamsToParent() {
        if (this.mParentInterface == null) {
            return;
        }
        android.view.WindowManager.LayoutParams[] layoutParamsArr = new android.view.WindowManager.LayoutParams[this.mStateForWindow.size()];
        boolean z = false;
        int i = 0;
        for (android.view.WindowlessWindowManager.State state : this.mStateForWindow.values()) {
            z |= state.mLastReportedParams.copyFrom(state.mParams) != 0;
            layoutParamsArr[i] = state.mParams;
            i++;
        }
        if (z) {
            try {
                this.mParentInterface.updateParams(layoutParamsArr);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    boolean forwardBackKeyToParent(android.view.KeyEvent keyEvent) {
        if (this.mParentInterface == null) {
            return false;
        }
        try {
            this.mParentInterface.forwardBackKeyToParent(keyEvent);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to forward back key To Parent: ", e);
            return false;
        }
    }
}
