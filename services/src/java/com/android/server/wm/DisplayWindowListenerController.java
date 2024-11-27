package com.android.server.wm;

/* loaded from: classes3.dex */
class DisplayWindowListenerController {
    android.os.RemoteCallbackList<android.view.IDisplayWindowListener> mDisplayListeners = new android.os.RemoteCallbackList<>();
    private final com.android.server.wm.WindowManagerService mService;

    DisplayWindowListenerController(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    int[] registerListener(android.view.IDisplayWindowListener iDisplayWindowListener) {
        int[] array;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDisplayListeners.register(iDisplayWindowListener);
                final android.util.IntArray intArray = new android.util.IntArray();
                this.mService.mAtmService.mRootWindowContainer.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayWindowListenerController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.DisplayWindowListenerController.lambda$registerListener$0(intArray, (com.android.server.wm.DisplayContent) obj);
                    }
                });
                array = intArray.toArray();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return array;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$registerListener$0(android.util.IntArray intArray, com.android.server.wm.DisplayContent displayContent) {
        intArray.add(displayContent.mDisplayId);
    }

    void unregisterListener(android.view.IDisplayWindowListener iDisplayWindowListener) {
        this.mDisplayListeners.unregister(iDisplayWindowListener);
    }

    void dispatchDisplayAdded(com.android.server.wm.DisplayContent displayContent) {
        int beginBroadcast = this.mDisplayListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mDisplayListeners.getBroadcastItem(i).onDisplayAdded(displayContent.mDisplayId);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mDisplayListeners.finishBroadcast();
    }

    void dispatchDisplayChanged(com.android.server.wm.DisplayContent displayContent, android.content.res.Configuration configuration) {
        boolean z = false;
        for (int i = 0; i < displayContent.getParent().getChildCount(); i++) {
            if (displayContent.getParent().getChildAt(i) == displayContent) {
                z = true;
            }
        }
        if (!z) {
            return;
        }
        int beginBroadcast = this.mDisplayListeners.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mDisplayListeners.getBroadcastItem(i2).onDisplayConfigurationChanged(displayContent.getDisplayId(), configuration);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mDisplayListeners.finishBroadcast();
    }

    void dispatchDisplayRemoved(com.android.server.wm.DisplayContent displayContent) {
        int beginBroadcast = this.mDisplayListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mDisplayListeners.getBroadcastItem(i).onDisplayRemoved(displayContent.mDisplayId);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mDisplayListeners.finishBroadcast();
    }

    void dispatchFixedRotationStarted(com.android.server.wm.DisplayContent displayContent, int i) {
        int beginBroadcast = this.mDisplayListeners.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mDisplayListeners.getBroadcastItem(i2).onFixedRotationStarted(displayContent.mDisplayId, i);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mDisplayListeners.finishBroadcast();
    }

    void dispatchFixedRotationFinished(com.android.server.wm.DisplayContent displayContent) {
        int beginBroadcast = this.mDisplayListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mDisplayListeners.getBroadcastItem(i).onFixedRotationFinished(displayContent.mDisplayId);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mDisplayListeners.finishBroadcast();
    }

    void dispatchKeepClearAreasChanged(com.android.server.wm.DisplayContent displayContent, java.util.Set<android.graphics.Rect> set, java.util.Set<android.graphics.Rect> set2) {
        int beginBroadcast = this.mDisplayListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mDisplayListeners.getBroadcastItem(i).onKeepClearAreasChanged(displayContent.mDisplayId, new java.util.ArrayList(set), new java.util.ArrayList(set2));
            } catch (android.os.RemoteException e) {
            }
        }
        this.mDisplayListeners.finishBroadcast();
    }
}
