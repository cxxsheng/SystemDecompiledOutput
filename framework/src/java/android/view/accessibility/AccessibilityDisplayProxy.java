package android.view.accessibility;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public abstract class AccessibilityDisplayProxy {
    private static final int INVALID_CONNECTION_ID = -1;
    private static final java.lang.String LOG_TAG = "AccessibilityDisplayProxy";
    private int mConnectionId = -1;
    private int mDisplayId;
    private java.util.concurrent.Executor mExecutor;
    private java.util.List<android.accessibilityservice.AccessibilityServiceInfo> mInstalledAndEnabledServices;
    android.accessibilityservice.IAccessibilityServiceClient mServiceClient;

    public AccessibilityDisplayProxy(int i, java.util.concurrent.Executor executor, java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list) {
        this.mDisplayId = i;
        this.mExecutor = executor;
        this.mServiceClient = new android.view.accessibility.AccessibilityDisplayProxy.IAccessibilityServiceClientImpl(null, this.mExecutor);
        this.mInstalledAndEnabledServices = list;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
    }

    public void onProxyConnected() {
    }

    public void interrupt() {
    }

    public android.view.accessibility.AccessibilityNodeInfo findFocus(int i) {
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, -2, android.view.accessibility.AccessibilityNodeInfo.ROOT_NODE_ID, i);
    }

    public java.util.List<android.view.accessibility.AccessibilityWindowInfo> getWindows() {
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().getWindowsOnDisplay(this.mConnectionId, this.mDisplayId);
    }

    public void setInstalledAndEnabledServices(java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list) {
        this.mInstalledAndEnabledServices = list;
        sendServiceInfos();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendServiceInfos() {
        android.view.accessibility.AccessibilityInteractionClient.getInstance();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (this.mInstalledAndEnabledServices != null && this.mInstalledAndEnabledServices.size() > 0 && connection != null) {
            try {
                connection.setInstalledAndEnabledServices(this.mInstalledAndEnabledServices);
                android.view.accessibility.AccessibilityInteractionClient.getInstance().clearCache(this.mConnectionId);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(LOG_TAG, "Error while setting AccessibilityServiceInfos", e);
                e.rethrowFromSystemServer();
            }
        }
        this.mInstalledAndEnabledServices = null;
    }

    public final java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAndEnabledServices() {
        android.view.accessibility.AccessibilityInteractionClient.getInstance();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                return connection.getInstalledAndEnabledServices();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(LOG_TAG, "Error while getting AccessibilityServiceInfo", e);
                e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptyList();
    }

    public void setAccessibilityFocusAppearance(int i, int i2) {
        android.view.accessibility.AccessibilityInteractionClient.getInstance();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setFocusAppearance(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(LOG_TAG, "Error while setting the strokeWidth and color of the accessibility focus rectangle", e);
                e.rethrowFromSystemServer();
            }
        }
    }

    private class IAccessibilityServiceClientImpl extends android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper {
        IAccessibilityServiceClientImpl(android.content.Context context, java.util.concurrent.Executor executor) {
            super(context, executor, new android.accessibilityservice.AccessibilityService.Callbacks() { // from class: android.view.accessibility.AccessibilityDisplayProxy.IAccessibilityServiceClientImpl.1
                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
                    android.view.accessibility.AccessibilityDisplayProxy.this.onAccessibilityEvent(accessibilityEvent);
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onInterrupt() {
                    android.view.accessibility.AccessibilityDisplayProxy.this.interrupt();
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onServiceConnected() {
                    android.view.accessibility.AccessibilityDisplayProxy.this.sendServiceInfos();
                    android.view.accessibility.AccessibilityDisplayProxy.this.onProxyConnected();
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void init(int i, android.os.IBinder iBinder) {
                    android.view.accessibility.AccessibilityDisplayProxy.this.mConnectionId = i;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public boolean onGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
                    return false;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public boolean onKeyEvent(android.view.KeyEvent keyEvent) {
                    return false;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onMagnificationChanged(int i, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onMotionEvent(android.view.MotionEvent motionEvent) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onTouchStateChanged(int i, int i2) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onSoftKeyboardShowModeChanged(int i) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onPerformGestureResult(int i, boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onFingerprintCapturingGesturesChanged(boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onFingerprintGesture(int i) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityButtonClicked(int i) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityButtonAvailabilityChanged(boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onSystemActionsChanged() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void createImeSession(com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void startInput(com.android.internal.inputmethod.RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
                }
            });
        }
    }
}
