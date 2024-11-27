package com.android.server.accessibility;

/* loaded from: classes.dex */
public class ProxyManager {
    private static final boolean DEBUG;
    private static final java.lang.String LOG_TAG = "ProxyManager";
    static final java.lang.String PROXY_COMPONENT_CLASS_NAME = "ProxyClass";
    static final java.lang.String PROXY_COMPONENT_PACKAGE_NAME = "ProxyPackage";
    private com.android.server.accessibility.AccessibilityInputFilter mA11yInputFilter;
    private final com.android.server.accessibility.AccessibilityWindowManager mA11yWindowManager;
    private com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener mAppsOnVirtualDeviceListener;
    private final android.content.Context mContext;
    private final java.lang.Object mLock;
    private final android.os.Handler mMainHandler;
    private final com.android.server.accessibility.ProxyManager.SystemSupport mSystemSupport;
    private final com.android.server.accessibility.UiAutomationManager mUiAutomationManager;
    private android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener mVirtualDeviceListener;
    private final android.util.SparseIntArray mLastStates = new android.util.SparseIntArray();
    private final android.util.SparseArray<com.android.server.accessibility.ProxyAccessibilityServiceConnection> mProxyA11yServiceConnections = new android.util.SparseArray<>();
    private com.android.server.companion.virtual.VirtualDeviceManagerInternal mLocalVdm = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);

    public interface SystemSupport {
        @android.annotation.NonNull
        android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> getCurrentUserClientsLocked();

        @android.annotation.NonNull
        android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> getGlobalClientsLocked();

        void notifyClearAccessibilityCacheLocked();

        void removeDeviceIdLocked(int i);

        void updateWindowsForAccessibilityCallbackLocked();
    }

    static {
        DEBUG = android.util.Log.isLoggable(LOG_TAG, 3) && android.os.Build.IS_DEBUGGABLE;
    }

    public ProxyManager(java.lang.Object obj, com.android.server.accessibility.AccessibilityWindowManager accessibilityWindowManager, android.content.Context context, android.os.Handler handler, com.android.server.accessibility.UiAutomationManager uiAutomationManager, com.android.server.accessibility.ProxyManager.SystemSupport systemSupport) {
        this.mLock = obj;
        this.mA11yWindowManager = accessibilityWindowManager;
        this.mContext = context;
        this.mMainHandler = handler;
        this.mUiAutomationManager = uiAutomationManager;
        this.mSystemSupport = systemSupport;
    }

    public void registerProxy(final android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, final int i, int i2, com.android.server.accessibility.AccessibilitySecurityPolicy accessibilitySecurityPolicy, com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport systemSupport, android.accessibilityservice.AccessibilityTrace accessibilityTrace, com.android.server.wm.WindowManagerInternal windowManagerInternal) throws android.os.RemoteException {
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "Register proxy for display id: " + i);
        }
        android.companion.virtual.VirtualDeviceManager virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) this.mContext.getSystemService(android.companion.virtual.VirtualDeviceManager.class);
        if (virtualDeviceManager == null) {
            return;
        }
        int deviceIdForDisplayId = virtualDeviceManager.getDeviceIdForDisplayId(i);
        android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = new android.accessibilityservice.AccessibilityServiceInfo();
        accessibilityServiceInfo.setCapabilities(3);
        accessibilityServiceInfo.setComponentName(new android.content.ComponentName(PROXY_COMPONENT_PACKAGE_NAME, PROXY_COMPONENT_CLASS_NAME + i));
        com.android.server.accessibility.ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection = new com.android.server.accessibility.ProxyAccessibilityServiceConnection(this.mContext, accessibilityServiceInfo.getComponentName(), accessibilityServiceInfo, i2, this.mMainHandler, this.mLock, accessibilitySecurityPolicy, systemSupport, accessibilityTrace, windowManagerInternal, this.mA11yWindowManager, i, deviceIdForDisplayId);
        synchronized (this.mLock) {
            try {
                this.mProxyA11yServiceConnections.put(i, proxyAccessibilityServiceConnection);
                com.android.server.accessibility.Flags.proxyUseAppsOnVirtualDeviceListener();
                if (this.mAppsOnVirtualDeviceListener == null) {
                    this.mAppsOnVirtualDeviceListener = new com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda2
                        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener
                        public final void onAppsOnAnyVirtualDeviceChanged(java.util.Set set) {
                            com.android.server.accessibility.ProxyManager.this.lambda$registerProxy$0(set);
                        }
                    };
                    com.android.server.companion.virtual.VirtualDeviceManagerInternal localVdm = getLocalVdm();
                    if (localVdm != null) {
                        localVdm.registerAppsOnVirtualDeviceListener(this.mAppsOnVirtualDeviceListener);
                    }
                }
                if (this.mProxyA11yServiceConnections.size() == 1) {
                    registerVirtualDeviceListener();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        iAccessibilityServiceClient.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.accessibility.ProxyManager.1
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                iAccessibilityServiceClient.asBinder().unlinkToDeath(this, 0);
                com.android.server.accessibility.ProxyManager.this.clearConnectionAndUpdateState(i);
            }
        }, 0);
        this.mMainHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.ProxyManager.this.lambda$registerProxy$1(i);
            }
        });
        proxyAccessibilityServiceConnection.initializeServiceInterface(iAccessibilityServiceClient);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerProxy$1(int i) {
        if (this.mA11yInputFilter != null) {
            this.mA11yInputFilter.disableFeaturesForDisplayIfInstalled(i);
        }
    }

    private void registerVirtualDeviceListener() {
        android.companion.virtual.VirtualDeviceManager virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) this.mContext.getSystemService(android.companion.virtual.VirtualDeviceManager.class);
        if (virtualDeviceManager == null || !android.companion.virtual.flags.Flags.vdmPublicApis()) {
            return;
        }
        if (this.mVirtualDeviceListener == null) {
            this.mVirtualDeviceListener = new android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener() { // from class: com.android.server.accessibility.ProxyManager.2
                public void onVirtualDeviceClosed(int i) {
                    com.android.server.accessibility.ProxyManager.this.clearConnections(i);
                }
            };
        }
        virtualDeviceManager.registerVirtualDeviceListener(this.mContext.getMainExecutor(), this.mVirtualDeviceListener);
    }

    private void unregisterVirtualDeviceListener() {
        android.companion.virtual.VirtualDeviceManager virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) this.mContext.getSystemService(android.companion.virtual.VirtualDeviceManager.class);
        if (virtualDeviceManager == null || !android.companion.virtual.flags.Flags.vdmPublicApis()) {
            return;
        }
        virtualDeviceManager.unregisterVirtualDeviceListener(this.mVirtualDeviceListener);
    }

    public boolean unregisterProxy(int i) {
        return clearConnectionAndUpdateState(i);
    }

    public void clearConnections(int i) {
        int i2;
        android.util.IntArray intArray = new android.util.IntArray();
        synchronized (this.mLock) {
            for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
                try {
                    com.android.server.accessibility.ProxyAccessibilityServiceConnection valueAt = this.mProxyA11yServiceConnections.valueAt(i3);
                    if (valueAt != null && valueAt.getDeviceId() == i) {
                        intArray.add(valueAt.getDisplayId());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        for (i2 = 0; i2 < intArray.size(); i2++) {
            clearConnectionAndUpdateState(intArray.get(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean clearConnectionAndUpdateState(int i) {
        boolean z;
        int i2;
        synchronized (this.mLock) {
            try {
                if (!this.mProxyA11yServiceConnections.contains(i)) {
                    z = false;
                    i2 = -1;
                } else {
                    i2 = this.mProxyA11yServiceConnections.get(i).getDeviceId();
                    this.mProxyA11yServiceConnections.remove(i);
                    if (this.mProxyA11yServiceConnections.size() == 0) {
                        unregisterVirtualDeviceListener();
                    }
                    z = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            updateStateForRemovedDisplay(i, i2);
        }
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "Unregistered proxy for display id " + i + ": " + z);
        }
        return z;
    }

    private void updateStateForRemovedDisplay(final int i, int i2) {
        com.android.server.companion.virtual.VirtualDeviceManagerInternal localVdm;
        this.mA11yWindowManager.stopTrackingDisplayProxy(i);
        this.mMainHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.ProxyManager.this.lambda$updateStateForRemovedDisplay$2(i);
            }
        });
        if (!isProxyedDeviceId(i2)) {
            synchronized (this.mLock) {
                try {
                    com.android.server.accessibility.Flags.proxyUseAppsOnVirtualDeviceListener();
                    if (this.mProxyA11yServiceConnections.size() == 0 && (localVdm = getLocalVdm()) != null && this.mAppsOnVirtualDeviceListener != null) {
                        localVdm.unregisterAppsOnVirtualDeviceListener(this.mAppsOnVirtualDeviceListener);
                        this.mAppsOnVirtualDeviceListener = null;
                    }
                    this.mSystemSupport.removeDeviceIdLocked(i2);
                    this.mLastStates.delete(i2);
                } finally {
                }
            }
            return;
        }
        onProxyChanged(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateStateForRemovedDisplay$2(int i) {
        android.view.Display display;
        if (this.mA11yInputFilter != null && (display = ((android.hardware.display.DisplayManager) this.mContext.getSystemService("display")).getDisplay(i)) != null) {
            this.mA11yInputFilter.enableFeaturesForDisplayIfInstalled(display);
        }
    }

    public boolean isProxyedDisplay(int i) {
        boolean contains;
        synchronized (this.mLock) {
            try {
                contains = this.mProxyA11yServiceConnections.contains(i);
                if (DEBUG) {
                    android.util.Slog.v(LOG_TAG, "Tracking proxy display " + i + " : " + contains);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return contains;
    }

    public boolean isProxyedDeviceId(int i) {
        boolean z;
        if (i == 0 || i == -1) {
            return false;
        }
        synchronized (this.mLock) {
            z = getFirstProxyForDeviceIdLocked(i) != null;
        }
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "Tracking device " + i + " : " + z);
        }
        return z;
    }

    public boolean displayBelongsToCaller(int i, int i2) {
        android.companion.virtual.VirtualDeviceManager virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) this.mContext.getSystemService(android.companion.virtual.VirtualDeviceManager.class);
        com.android.server.companion.virtual.VirtualDeviceManagerInternal localVdm = getLocalVdm();
        if (virtualDeviceManager == null || localVdm == null) {
            return false;
        }
        for (android.companion.virtual.VirtualDevice virtualDevice : virtualDeviceManager.getVirtualDevices()) {
            if (localVdm.getDisplayIdsForDevice(virtualDevice.getDeviceId()).contains(java.lang.Integer.valueOf(i2)) && i == localVdm.getDeviceOwnerUid(virtualDevice.getDeviceId())) {
                return true;
            }
        }
        return false;
    }

    public void sendAccessibilityEventLocked(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        com.android.server.accessibility.ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection = this.mProxyA11yServiceConnections.get(accessibilityEvent.getDisplayId());
        if (proxyAccessibilityServiceConnection != null) {
            if (DEBUG) {
                android.util.Slog.v(LOG_TAG, "Send proxy event " + accessibilityEvent + " for display id " + accessibilityEvent.getDisplayId());
            }
            proxyAccessibilityServiceConnection.notifyAccessibilityEvent(accessibilityEvent);
        }
    }

    public boolean canRetrieveInteractiveWindowsLocked() {
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= this.mProxyA11yServiceConnections.size()) {
                break;
            }
            if (!this.mProxyA11yServiceConnections.valueAt(i).mRetrieveInteractiveWindows) {
                i++;
            } else {
                z = true;
                break;
            }
        }
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "At least one proxy can retrieve windows: " + z);
        }
        return z;
    }

    public int getStateLocked(int i) {
        int i2;
        if (!this.mUiAutomationManager.canIntrospect()) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
            com.android.server.accessibility.ProxyAccessibilityServiceConnection valueAt = this.mProxyA11yServiceConnections.valueAt(i3);
            if (valueAt != null && valueAt.getDeviceId() == i) {
                i2 |= getStateForDisplayIdLocked(valueAt);
            }
        }
        if (DEBUG) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("For device id ");
            sb.append(i);
            sb.append(" a11y is enabled: ");
            sb.append((i2 & 1) != 0);
            android.util.Slog.v(LOG_TAG, sb.toString());
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append("For device id ");
            sb2.append(i);
            sb2.append(" touch exploration is enabled: ");
            sb2.append((i2 & 2) != 0);
            android.util.Slog.v(LOG_TAG, sb2.toString());
        }
        return i2;
    }

    private int getStateForDisplayIdLocked(com.android.server.accessibility.ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection) {
        int i;
        if (proxyAccessibilityServiceConnection == null) {
            i = 0;
        } else if (!proxyAccessibilityServiceConnection.mRequestTouchExplorationMode) {
            i = 1;
        } else {
            i = 3;
        }
        if (DEBUG) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Accessibility is enabled for all proxies: ");
            sb.append((i & 1) != 0);
            android.util.Slog.v(LOG_TAG, sb.toString());
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append("Touch exploration is enabled for all proxies: ");
            sb2.append((i & 2) != 0);
            android.util.Slog.v(LOG_TAG, sb2.toString());
        }
        return i;
    }

    private int getLastSentStateLocked(int i) {
        return this.mLastStates.get(i, 0);
    }

    private void setLastStateLocked(int i, int i2) {
        this.mLastStates.put(i, i2);
    }

    private void updateRelevantEventTypesLocked(final int i) {
        if (!isProxyedDeviceId(i)) {
            return;
        }
        this.mMainHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.ProxyManager.this.lambda$updateRelevantEventTypesLocked$4(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRelevantEventTypesLocked$4(final int i) {
        synchronized (this.mLock) {
            broadcastToClientsLocked(com.android.internal.util.FunctionalUtils.ignoreRemoteException(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda8
                public final void acceptOrThrow(java.lang.Object obj) {
                    com.android.server.accessibility.ProxyManager.this.lambda$updateRelevantEventTypesLocked$3(i, (com.android.server.accessibility.AccessibilityManagerService.Client) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRelevantEventTypesLocked$3(int i, com.android.server.accessibility.AccessibilityManagerService.Client client) throws android.os.RemoteException {
        int computeRelevantEventTypesLocked;
        if (client.mDeviceId == i && client.mLastSentRelevantEventTypes != (computeRelevantEventTypesLocked = computeRelevantEventTypesLocked(client))) {
            client.mLastSentRelevantEventTypes = computeRelevantEventTypesLocked;
            client.mCallback.setRelevantEventTypes(computeRelevantEventTypesLocked);
        }
    }

    public int computeRelevantEventTypesLocked(com.android.server.accessibility.AccessibilityManagerService.Client client) {
        int i;
        int i2 = 0;
        for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
            com.android.server.accessibility.ProxyAccessibilityServiceConnection valueAt = this.mProxyA11yServiceConnections.valueAt(i3);
            if (valueAt != null && valueAt.getDeviceId() == client.mDeviceId) {
                int relevantEventTypes = i2 | valueAt.getRelevantEventTypes();
                if (com.android.server.accessibility.AccessibilityManagerService.isClientInPackageAllowlist(this.mUiAutomationManager.getServiceInfo(), client)) {
                    i = this.mUiAutomationManager.getRelevantEventTypes();
                } else {
                    i = 0;
                }
                i2 = relevantEventTypes | i;
            }
        }
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "Relevant event types for device id " + client.mDeviceId + ": " + android.view.accessibility.AccessibilityEvent.eventTypeToString(i2));
        }
        return i2;
    }

    public void addServiceInterfacesLocked(@android.annotation.NonNull java.util.List<android.accessibilityservice.IAccessibilityServiceClient> list, int i) {
        for (int i2 = 0; i2 < this.mProxyA11yServiceConnections.size(); i2++) {
            com.android.server.accessibility.ProxyAccessibilityServiceConnection valueAt = this.mProxyA11yServiceConnections.valueAt(i2);
            if (valueAt != null && valueAt.getDeviceId() == i) {
                android.os.IBinder iBinder = valueAt.mService;
                android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient = valueAt.mServiceInterface;
                if (iBinder != null && iAccessibilityServiceClient != null) {
                    list.add(iAccessibilityServiceClient);
                }
            }
        }
    }

    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAndEnabledServiceInfosLocked(int i, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
            com.android.server.accessibility.ProxyAccessibilityServiceConnection valueAt = this.mProxyA11yServiceConnections.valueAt(i3);
            if (valueAt != null && valueAt.getDeviceId() == i2) {
                if (i == -1) {
                    arrayList.addAll(valueAt.getInstalledAndEnabledServices());
                } else if ((valueAt.mFeedbackType & i) != 0) {
                    for (android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo : valueAt.getInstalledAndEnabledServices()) {
                        if ((accessibilityServiceInfo.feedbackType & i) != 0) {
                            arrayList.add(accessibilityServiceInfo);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private void onProxyChanged(int i, boolean z) {
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "onProxyChanged called for deviceId: " + i);
        }
        synchronized (this.mLock) {
            updateDeviceIdsIfNeededLocked(i);
            this.mSystemSupport.updateWindowsForAccessibilityCallbackLocked();
            updateRelevantEventTypesLocked(i);
            scheduleUpdateProxyClientsIfNeededLocked(i, z);
            scheduleNotifyProxyClientsOfServicesStateChangeLocked(i);
            updateFocusAppearanceLocked(i);
            this.mSystemSupport.notifyClearAccessibilityCacheLocked();
        }
    }

    public void onProxyChanged(int i) {
        onProxyChanged(i, false);
    }

    private void scheduleUpdateProxyClientsIfNeededLocked(final int i, boolean z) {
        final int stateLocked = getStateLocked(i);
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "State for device id " + i + " is " + stateLocked);
            android.util.Slog.v(LOG_TAG, "Last state for device id " + i + " is " + getLastSentStateLocked(i));
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("force update: ");
            sb.append(z);
            android.util.Slog.v(LOG_TAG, sb.toString());
        }
        if (getLastSentStateLocked(i) == stateLocked) {
            com.android.server.accessibility.Flags.proxyUseAppsOnVirtualDeviceListener();
            if (!z) {
                return;
            }
        }
        setLastStateLocked(i, stateLocked);
        this.mMainHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.ProxyManager.this.lambda$scheduleUpdateProxyClientsIfNeededLocked$6(i, stateLocked);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleUpdateProxyClientsIfNeededLocked$6(final int i, final int i2) {
        synchronized (this.mLock) {
            broadcastToClientsLocked(com.android.internal.util.FunctionalUtils.ignoreRemoteException(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda0
                public final void acceptOrThrow(java.lang.Object obj) {
                    com.android.server.accessibility.ProxyManager.lambda$scheduleUpdateProxyClientsIfNeededLocked$5(i, i2, (com.android.server.accessibility.AccessibilityManagerService.Client) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$scheduleUpdateProxyClientsIfNeededLocked$5(int i, int i2, com.android.server.accessibility.AccessibilityManagerService.Client client) throws android.os.RemoteException {
        if (client.mDeviceId == i) {
            client.mCallback.setState(i2);
        }
    }

    private void scheduleNotifyProxyClientsOfServicesStateChangeLocked(final int i) {
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "Notify services state change at device id " + i);
        }
        this.mMainHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.ProxyManager.this.lambda$scheduleNotifyProxyClientsOfServicesStateChangeLocked$8(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleNotifyProxyClientsOfServicesStateChangeLocked$8(final int i) {
        broadcastToClientsLocked(com.android.internal.util.FunctionalUtils.ignoreRemoteException(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda7
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.accessibility.ProxyManager.this.lambda$scheduleNotifyProxyClientsOfServicesStateChangeLocked$7(i, (com.android.server.accessibility.AccessibilityManagerService.Client) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleNotifyProxyClientsOfServicesStateChangeLocked$7(int i, com.android.server.accessibility.AccessibilityManagerService.Client client) throws android.os.RemoteException {
        if (client.mDeviceId == i) {
            synchronized (this.mLock) {
                client.mCallback.notifyServicesStateChanged(getRecommendedTimeoutMillisLocked(i));
            }
        }
    }

    private void updateFocusAppearanceLocked(int i) {
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "Update proxy focus appearance at device id " + i);
        }
        final com.android.server.accessibility.ProxyAccessibilityServiceConnection firstProxyForDeviceIdLocked = getFirstProxyForDeviceIdLocked(i);
        if (firstProxyForDeviceIdLocked != null) {
            this.mMainHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.accessibility.ProxyManager.this.lambda$updateFocusAppearanceLocked$10(firstProxyForDeviceIdLocked);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFocusAppearanceLocked$10(final com.android.server.accessibility.ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection) {
        broadcastToClientsLocked(com.android.internal.util.FunctionalUtils.ignoreRemoteException(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda10
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.accessibility.ProxyManager.lambda$updateFocusAppearanceLocked$9(com.android.server.accessibility.ProxyAccessibilityServiceConnection.this, (com.android.server.accessibility.AccessibilityManagerService.Client) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateFocusAppearanceLocked$9(com.android.server.accessibility.ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection, com.android.server.accessibility.AccessibilityManagerService.Client client) throws android.os.RemoteException {
        if (client.mDeviceId == proxyAccessibilityServiceConnection.getDeviceId()) {
            client.mCallback.setFocusAppearance(proxyAccessibilityServiceConnection.getFocusStrokeWidthLocked(), proxyAccessibilityServiceConnection.getFocusColorLocked());
        }
    }

    private com.android.server.accessibility.ProxyAccessibilityServiceConnection getFirstProxyForDeviceIdLocked(int i) {
        for (int i2 = 0; i2 < this.mProxyA11yServiceConnections.size(); i2++) {
            com.android.server.accessibility.ProxyAccessibilityServiceConnection valueAt = this.mProxyA11yServiceConnections.valueAt(i2);
            if (valueAt != null && valueAt.getDeviceId() == i) {
                return valueAt;
            }
        }
        return null;
    }

    private void broadcastToClientsLocked(@android.annotation.NonNull java.util.function.Consumer<com.android.server.accessibility.AccessibilityManagerService.Client> consumer) {
        android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> currentUserClientsLocked = this.mSystemSupport.getCurrentUserClientsLocked();
        android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> globalClientsLocked = this.mSystemSupport.getGlobalClientsLocked();
        currentUserClientsLocked.broadcastForEachCookie(consumer);
        globalClientsLocked.broadcastForEachCookie(consumer);
    }

    public void updateTimeoutsIfNeeded(int i, int i2) {
        synchronized (this.mLock) {
            for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
                try {
                    com.android.server.accessibility.ProxyAccessibilityServiceConnection valueAt = this.mProxyA11yServiceConnections.valueAt(i3);
                    if (valueAt != null && valueAt.updateTimeouts(i, i2)) {
                        scheduleNotifyProxyClientsOfServicesStateChangeLocked(valueAt.getDeviceId());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public long getRecommendedTimeoutMillisLocked(int i) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.mProxyA11yServiceConnections.size(); i4++) {
            com.android.server.accessibility.ProxyAccessibilityServiceConnection valueAt = this.mProxyA11yServiceConnections.valueAt(i4);
            if (valueAt != null && valueAt.getDeviceId() == i) {
                int interactiveTimeout = valueAt.getInteractiveTimeout();
                int nonInteractiveTimeout = valueAt.getNonInteractiveTimeout();
                i2 = java.lang.Math.max(interactiveTimeout, i2);
                i3 = java.lang.Math.max(nonInteractiveTimeout, i3);
            }
        }
        return com.android.internal.util.IntPair.of(i2, i3);
    }

    public int getFocusStrokeWidthLocked(int i) {
        com.android.server.accessibility.ProxyAccessibilityServiceConnection firstProxyForDeviceIdLocked = getFirstProxyForDeviceIdLocked(i);
        if (firstProxyForDeviceIdLocked != null) {
            return firstProxyForDeviceIdLocked.getFocusStrokeWidthLocked();
        }
        return 0;
    }

    public int getFocusColorLocked(int i) {
        com.android.server.accessibility.ProxyAccessibilityServiceConnection firstProxyForDeviceIdLocked = getFirstProxyForDeviceIdLocked(i);
        if (firstProxyForDeviceIdLocked != null) {
            return firstProxyForDeviceIdLocked.getFocusColorLocked();
        }
        return 0;
    }

    public int getFirstDeviceIdForUidLocked(int i) {
        com.android.server.companion.virtual.VirtualDeviceManagerInternal localVdm = getLocalVdm();
        if (localVdm == null) {
            return -1;
        }
        for (java.lang.Integer num : localVdm.getDeviceIdsForUid(i)) {
            if (num.intValue() != 0 && num.intValue() != -1) {
                return num.intValue();
            }
        }
        return -1;
    }

    private void updateDeviceIdsIfNeededLocked(int i) {
        android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> currentUserClientsLocked = this.mSystemSupport.getCurrentUserClientsLocked();
        android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> globalClientsLocked = this.mSystemSupport.getGlobalClientsLocked();
        updateDeviceIdsIfNeededLocked(i, currentUserClientsLocked);
        updateDeviceIdsIfNeededLocked(i, globalClientsLocked);
    }

    private void updateDeviceIdsIfNeededLocked(int i, @android.annotation.NonNull android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> remoteCallbackList) {
        com.android.server.companion.virtual.VirtualDeviceManagerInternal localVdm = getLocalVdm();
        if (localVdm == null) {
            return;
        }
        for (int i2 = 0; i2 < remoteCallbackList.getRegisteredCallbackCount(); i2++) {
            com.android.server.accessibility.AccessibilityManagerService.Client client = (com.android.server.accessibility.AccessibilityManagerService.Client) remoteCallbackList.getRegisteredCallbackCookie(i2);
            com.android.server.accessibility.Flags.proxyUseAppsOnVirtualDeviceListener();
            if (i != 0 && i != -1) {
                boolean contains = localVdm.getDeviceIdsForUid(client.mUid).contains(java.lang.Integer.valueOf(i));
                if (client.mDeviceId != i && contains) {
                    if (DEBUG) {
                        android.util.Slog.v(LOG_TAG, "Packages moved to device id " + i + " are " + java.util.Arrays.toString(client.mPackageNames));
                    }
                    client.mDeviceId = i;
                } else if (client.mDeviceId == i && !contains) {
                    client.mDeviceId = 0;
                    if (DEBUG) {
                        android.util.Slog.v(LOG_TAG, "Packages moved to the default device from device id " + i + " are " + java.util.Arrays.toString(client.mPackageNames));
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.VisibleForTesting
    /* renamed from: notifyProxyOfRunningAppsChange, reason: merged with bridge method [inline-methods] */
    public void lambda$registerProxy$0(java.util.Set<java.lang.Integer> set) {
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "notifyProxyOfRunningAppsChange: " + set);
        }
        synchronized (this.mLock) {
            try {
                if (this.mProxyA11yServiceConnections.size() == 0) {
                    return;
                }
                com.android.server.companion.virtual.VirtualDeviceManagerInternal localVdm = getLocalVdm();
                if (localVdm == null) {
                    return;
                }
                android.util.ArraySet arraySet = new android.util.ArraySet();
                for (int i = 0; i < this.mProxyA11yServiceConnections.size(); i++) {
                    com.android.server.accessibility.ProxyAccessibilityServiceConnection valueAt = this.mProxyA11yServiceConnections.valueAt(i);
                    if (valueAt != null) {
                        int deviceId = valueAt.getDeviceId();
                        java.util.Iterator<java.lang.Integer> it = set.iterator();
                        while (it.hasNext()) {
                            if (localVdm.getDeviceIdsForUid(it.next().intValue()).contains(java.lang.Integer.valueOf(deviceId))) {
                                arraySet.add(java.lang.Integer.valueOf(deviceId));
                            }
                        }
                    }
                }
                java.util.Iterator it2 = arraySet.iterator();
                while (it2.hasNext()) {
                    onProxyChanged(((java.lang.Integer) it2.next()).intValue(), true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void clearCacheLocked() {
        for (int i = 0; i < this.mProxyA11yServiceConnections.size(); i++) {
            this.mProxyA11yServiceConnections.valueAt(i).notifyClearAccessibilityNodeInfoCache();
        }
    }

    public void setAccessibilityInputFilter(com.android.server.accessibility.AccessibilityInputFilter accessibilityInputFilter) {
        if (DEBUG) {
            android.util.Slog.v(LOG_TAG, "Set proxy input filter to " + accessibilityInputFilter);
        }
        this.mA11yInputFilter = accessibilityInputFilter;
    }

    private com.android.server.companion.virtual.VirtualDeviceManagerInternal getLocalVdm() {
        if (this.mLocalVdm == null) {
            this.mLocalVdm = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
        }
        return this.mLocalVdm;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setLocalVirtualDeviceManager(com.android.server.companion.virtual.VirtualDeviceManagerInternal virtualDeviceManagerInternal) {
        this.mLocalVdm = virtualDeviceManagerInternal;
    }

    void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        synchronized (this.mLock) {
            try {
                printWriter.println();
                printWriter.println("Proxy manager state:");
                printWriter.println("    Number of proxy connections: " + this.mProxyA11yServiceConnections.size());
                printWriter.println("    Registered proxy connections:");
                android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> currentUserClientsLocked = this.mSystemSupport.getCurrentUserClientsLocked();
                android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> globalClientsLocked = this.mSystemSupport.getGlobalClientsLocked();
                for (int i = 0; i < this.mProxyA11yServiceConnections.size(); i++) {
                    com.android.server.accessibility.ProxyAccessibilityServiceConnection valueAt = this.mProxyA11yServiceConnections.valueAt(i);
                    if (valueAt != null) {
                        valueAt.dump(fileDescriptor, printWriter, strArr);
                    }
                    printWriter.println();
                    printWriter.println("        User clients for proxy's virtual device id");
                    printClientsForDeviceId(printWriter, currentUserClientsLocked, valueAt.getDeviceId());
                    printWriter.println();
                    printWriter.println("        Global clients for proxy's virtual device id");
                    printClientsForDeviceId(printWriter, globalClientsLocked, valueAt.getDeviceId());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void printClientsForDeviceId(java.io.PrintWriter printWriter, android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> remoteCallbackList, int i) {
        if (remoteCallbackList != null) {
            for (int i2 = 0; i2 < remoteCallbackList.getRegisteredCallbackCount(); i2++) {
                com.android.server.accessibility.AccessibilityManagerService.Client client = (com.android.server.accessibility.AccessibilityManagerService.Client) remoteCallbackList.getRegisteredCallbackCookie(i2);
                if (client.mDeviceId == i) {
                    printWriter.println("            " + java.util.Arrays.toString(client.mPackageNames) + "\n");
                }
            }
        }
    }
}
