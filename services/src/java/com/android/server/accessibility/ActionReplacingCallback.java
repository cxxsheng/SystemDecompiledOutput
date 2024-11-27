package com.android.server.accessibility;

/* loaded from: classes.dex */
public class ActionReplacingCallback extends android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub {
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = "ActionReplacingCallback";
    private final android.view.accessibility.IAccessibilityInteractionConnection mConnectionWithReplacementActions;
    private final int mInteractionId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    android.view.accessibility.AccessibilityNodeInfo mNodeFromOriginalWindow;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    android.view.accessibility.AccessibilityNodeInfo mNodeWithReplacementActions;
    private final int mNodeWithReplacementActionsInteractionId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    java.util.List<android.view.accessibility.AccessibilityNodeInfo> mNodesFromOriginalWindow;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    java.util.List<android.view.accessibility.AccessibilityNodeInfo> mPrefetchedNodesFromOriginalWindow;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mReplacementNodeIsReadyOrFailed;
    private final android.view.accessibility.IAccessibilityInteractionConnectionCallback mServiceCallback;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mSetFindNodeFromOriginalWindowCalled = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mSetFindNodesFromOriginalWindowCalled = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mSetPrefetchFromOriginalWindowCalled = false;

    public ActionReplacingCallback(android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection, int i, int i2, long j) {
        this.mServiceCallback = iAccessibilityInteractionConnectionCallback;
        this.mConnectionWithReplacementActions = iAccessibilityInteractionConnection;
        this.mInteractionId = i;
        this.mNodeWithReplacementActionsInteractionId = i + 1;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mConnectionWithReplacementActions.findAccessibilityNodeInfoByAccessibilityId(android.view.accessibility.AccessibilityNodeInfo.ROOT_NODE_ID, (android.graphics.Region) null, this.mNodeWithReplacementActionsInteractionId, this, 0, i2, j, (android.view.MagnificationSpec) null, (float[]) null, (android.os.Bundle) null);
            } catch (android.os.RemoteException e) {
                this.mReplacementNodeIsReadyOrFailed = true;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setFindAccessibilityNodeInfoResult(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        synchronized (this.mLock) {
            try {
                if (i == this.mInteractionId) {
                    this.mNodeFromOriginalWindow = accessibilityNodeInfo;
                    this.mSetFindNodeFromOriginalWindowCalled = true;
                } else if (i == this.mNodeWithReplacementActionsInteractionId) {
                    this.mNodeWithReplacementActions = accessibilityNodeInfo;
                    this.mReplacementNodeIsReadyOrFailed = true;
                } else {
                    android.util.Slog.e(LOG_TAG, "Callback with unexpected interactionId");
                    return;
                }
                replaceInfoActionsAndCallServiceIfReady();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setFindAccessibilityNodeInfosResult(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, int i) {
        synchronized (this.mLock) {
            try {
                if (i == this.mInteractionId) {
                    this.mNodesFromOriginalWindow = list;
                    this.mSetFindNodesFromOriginalWindowCalled = true;
                } else if (i == this.mNodeWithReplacementActionsInteractionId) {
                    setNodeWithReplacementActionsFromList(list);
                    this.mReplacementNodeIsReadyOrFailed = true;
                } else {
                    android.util.Slog.e(LOG_TAG, "Callback with unexpected interactionId");
                    return;
                }
                replaceInfoActionsAndCallServiceIfReady();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setPrefetchAccessibilityNodeInfoResult(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            if (i == this.mInteractionId) {
                this.mPrefetchedNodesFromOriginalWindow = list;
                this.mSetPrefetchFromOriginalWindowCalled = true;
                replaceInfoActionsAndCallServiceIfReady();
                return;
            }
            android.util.Slog.e(LOG_TAG, "Callback with unexpected interactionId");
        }
    }

    private void replaceInfoActionsAndCallServiceIfReady() {
        replaceInfoActionsAndCallService();
        replaceInfosActionsAndCallService();
        replacePrefetchInfosActionsAndCallService();
    }

    private void setNodeWithReplacementActionsFromList(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = list.get(i);
            if (accessibilityNodeInfo.getSourceNodeId() == android.view.accessibility.AccessibilityNodeInfo.ROOT_NODE_ID) {
                this.mNodeWithReplacementActions = accessibilityNodeInfo;
            }
        }
    }

    public void setPerformAccessibilityActionResult(boolean z, int i) throws android.os.RemoteException {
        this.mServiceCallback.setPerformAccessibilityActionResult(z, i);
    }

    public void sendTakeScreenshotOfWindowError(int i, int i2) throws android.os.RemoteException {
        this.mServiceCallback.sendTakeScreenshotOfWindowError(i, i2);
    }

    private void replaceInfoActionsAndCallService() {
        boolean z;
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo;
        synchronized (this.mLock) {
            try {
                z = this.mReplacementNodeIsReadyOrFailed && this.mSetFindNodeFromOriginalWindowCalled;
                if (z && this.mNodeFromOriginalWindow != null) {
                    replaceActionsOnInfoLocked(this.mNodeFromOriginalWindow);
                    this.mSetFindNodeFromOriginalWindowCalled = false;
                }
                accessibilityNodeInfo = this.mNodeFromOriginalWindow;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            try {
                this.mServiceCallback.setFindAccessibilityNodeInfoResult(accessibilityNodeInfo, this.mInteractionId);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void replaceInfosActionsAndCallService() {
        boolean z;
        java.util.List<android.view.accessibility.AccessibilityNodeInfo> list;
        synchronized (this.mLock) {
            try {
                z = this.mReplacementNodeIsReadyOrFailed && this.mSetFindNodesFromOriginalWindowCalled;
                if (!z) {
                    list = null;
                } else {
                    list = replaceActionsLocked(this.mNodesFromOriginalWindow);
                    this.mSetFindNodesFromOriginalWindowCalled = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            try {
                this.mServiceCallback.setFindAccessibilityNodeInfosResult(list, this.mInteractionId);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void replacePrefetchInfosActionsAndCallService() {
        boolean z;
        java.util.List<android.view.accessibility.AccessibilityNodeInfo> list;
        synchronized (this.mLock) {
            try {
                z = this.mReplacementNodeIsReadyOrFailed && this.mSetPrefetchFromOriginalWindowCalled;
                if (!z) {
                    list = null;
                } else {
                    list = replaceActionsLocked(this.mPrefetchedNodesFromOriginalWindow);
                    this.mSetPrefetchFromOriginalWindowCalled = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            try {
                this.mServiceCallback.setPrefetchAccessibilityNodeInfoResult(list, this.mInteractionId);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<android.view.accessibility.AccessibilityNodeInfo> replaceActionsLocked(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                replaceActionsOnInfoLocked(list.get(i));
            }
        }
        if (list == null) {
            return null;
        }
        return new java.util.ArrayList(list);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void replaceActionsOnInfoLocked(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        accessibilityNodeInfo.removeAllActions();
        accessibilityNodeInfo.setClickable(false);
        accessibilityNodeInfo.setFocusable(false);
        accessibilityNodeInfo.setContextClickable(false);
        accessibilityNodeInfo.setScrollable(false);
        accessibilityNodeInfo.setLongClickable(false);
        accessibilityNodeInfo.setDismissable(false);
        if (accessibilityNodeInfo.getSourceNodeId() == android.view.accessibility.AccessibilityNodeInfo.ROOT_NODE_ID && this.mNodeWithReplacementActions != null) {
            java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> actionList = this.mNodeWithReplacementActions.getActionList();
            if (actionList != null) {
                for (int i = 0; i < actionList.size(); i++) {
                    accessibilityNodeInfo.addAction(actionList.get(i));
                }
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
            }
            accessibilityNodeInfo.setClickable(this.mNodeWithReplacementActions.isClickable());
            accessibilityNodeInfo.setFocusable(this.mNodeWithReplacementActions.isFocusable());
            accessibilityNodeInfo.setContextClickable(this.mNodeWithReplacementActions.isContextClickable());
            accessibilityNodeInfo.setScrollable(this.mNodeWithReplacementActions.isScrollable());
            accessibilityNodeInfo.setLongClickable(this.mNodeWithReplacementActions.isLongClickable());
            accessibilityNodeInfo.setDismissable(this.mNodeWithReplacementActions.isDismissable());
        }
    }

    public void sendAttachOverlayResult(int i, int i2) throws android.os.RemoteException {
        this.mServiceCallback.sendAttachOverlayResult(i, i2);
    }
}
