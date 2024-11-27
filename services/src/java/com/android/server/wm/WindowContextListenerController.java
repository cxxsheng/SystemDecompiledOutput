package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowContextListenerController {

    @com.android.internal.annotations.VisibleForTesting
    final android.util.ArrayMap<android.os.IBinder, com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl> mListeners = new android.util.ArrayMap<>();

    WindowContextListenerController() {
    }

    void registerWindowContainerListener(@android.annotation.NonNull com.android.server.wm.WindowProcessController windowProcessController, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer, int i, @android.annotation.Nullable android.os.Bundle bundle) {
        registerWindowContainerListener(windowProcessController, iBinder, windowContainer, i, bundle, true);
    }

    void registerWindowContainerListener(@android.annotation.NonNull com.android.server.wm.WindowProcessController windowProcessController, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer, int i, @android.annotation.Nullable android.os.Bundle bundle, boolean z) {
        if (this.mListeners.get(iBinder) == null) {
            new com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl(windowProcessController, iBinder, windowContainer, i, bundle).register(z);
        } else {
            updateContainerForWindowContextListener(iBinder, windowContainer);
        }
    }

    void updateContainerForWindowContextListener(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer) {
        com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl = this.mListeners.get(iBinder);
        if (windowContextListenerImpl == null) {
            throw new java.lang.IllegalArgumentException("Can't find listener for " + iBinder);
        }
        windowContextListenerImpl.updateContainer(windowContainer);
    }

    void unregisterWindowContainerListener(android.os.IBinder iBinder) {
        com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl = this.mListeners.get(iBinder);
        if (windowContextListenerImpl == null) {
            return;
        }
        windowContextListenerImpl.unregister();
        if (windowContextListenerImpl.mDeathRecipient != null) {
            windowContextListenerImpl.mDeathRecipient.unlinkToDeath();
        }
    }

    void dispatchPendingConfigurationIfNeeded(int i) {
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl valueAt = this.mListeners.valueAt(size);
            if (valueAt.getWindowContainer().getDisplayContent().getDisplayId() == i && valueAt.mHasPendingConfiguration) {
                valueAt.dispatchWindowContextInfoChange();
            }
        }
    }

    boolean assertCallerCanModifyListener(android.os.IBinder iBinder, boolean z, int i) {
        com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl = this.mListeners.get(iBinder);
        if (windowContextListenerImpl == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 2163930285157267092L, 0, null, null);
            return false;
        }
        if (z || i == windowContextListenerImpl.getUid()) {
            return true;
        }
        throw new java.lang.UnsupportedOperationException("Uid mismatch. Caller uid is " + i + ", while the listener's owner is from " + windowContextListenerImpl.getUid());
    }

    boolean hasListener(android.os.IBinder iBinder) {
        return this.mListeners.containsKey(iBinder);
    }

    int getWindowType(android.os.IBinder iBinder) {
        com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl = this.mListeners.get(iBinder);
        if (windowContextListenerImpl != null) {
            return windowContextListenerImpl.mType;
        }
        return -1;
    }

    @android.annotation.Nullable
    android.os.Bundle getOptions(android.os.IBinder iBinder) {
        com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl = this.mListeners.get(iBinder);
        if (windowContextListenerImpl != null) {
            return windowContextListenerImpl.mOptions;
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.WindowContainer<?> getContainer(android.os.IBinder iBinder) {
        com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl = this.mListeners.get(iBinder);
        if (windowContextListenerImpl != null) {
            return windowContextListenerImpl.mContainer;
        }
        return null;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("WindowContextListenerController{");
        sb.append("mListeners=[");
        int size = this.mListeners.values().size();
        for (int i = 0; i < size; i++) {
            sb.append(this.mListeners.valueAt(i));
            if (i != size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    @com.android.internal.annotations.VisibleForTesting
    class WindowContextListenerImpl implements com.android.server.wm.WindowContainerListener {

        @android.annotation.NonNull
        private final android.os.IBinder mClientToken;

        @android.annotation.NonNull
        private com.android.server.wm.WindowContainer<?> mContainer;
        private com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl.DeathRecipient mDeathRecipient;
        private boolean mHasPendingConfiguration;
        private android.content.res.Configuration mLastReportedConfig;
        private int mLastReportedDisplay;

        @android.annotation.Nullable
        private final android.os.Bundle mOptions;
        private final int mType;

        @android.annotation.NonNull
        private final com.android.server.wm.WindowProcessController mWpc;

        private WindowContextListenerImpl(@android.annotation.NonNull com.android.server.wm.WindowProcessController windowProcessController, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer, int i, @android.annotation.Nullable android.os.Bundle bundle) {
            this.mLastReportedDisplay = -1;
            java.util.Objects.requireNonNull(windowProcessController);
            this.mWpc = windowProcessController;
            this.mClientToken = iBinder;
            java.util.Objects.requireNonNull(windowContainer);
            this.mContainer = windowContainer;
            this.mType = i;
            this.mOptions = bundle;
            com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl.DeathRecipient deathRecipient = new com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl.DeathRecipient();
            try {
                deathRecipient.linkToDeath();
                this.mDeathRecipient = deathRecipient;
            } catch (android.os.RemoteException e) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 6139364662459841509L, 0, "Could not register window container listener token=%s, container=%s", java.lang.String.valueOf(iBinder), java.lang.String.valueOf(this.mContainer));
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        com.android.server.wm.WindowContainer<?> getWindowContainer() {
            return this.mContainer;
        }

        int getUid() {
            return this.mWpc.mUid;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateContainer(@android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer) {
            java.util.Objects.requireNonNull(windowContainer);
            if (this.mContainer.equals(windowContainer)) {
                return;
            }
            this.mContainer.unregisterWindowContainerListener(this);
            this.mContainer = windowContainer;
            clear();
            register();
        }

        private void register() {
            register(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void register(boolean z) {
            android.os.IBinder iBinder = this.mClientToken;
            if (this.mDeathRecipient == null) {
                throw new java.lang.IllegalStateException("Invalid client token: " + iBinder);
            }
            com.android.server.wm.WindowContextListenerController.this.mListeners.putIfAbsent(iBinder, this);
            this.mContainer.registerWindowContainerListener(this, z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unregister() {
            this.mContainer.unregisterWindowContainerListener(this);
            com.android.server.wm.WindowContextListenerController.this.mListeners.remove(this.mClientToken);
        }

        private void clear() {
            this.mLastReportedConfig = null;
            this.mLastReportedDisplay = -1;
        }

        @Override // com.android.server.wm.ConfigurationContainerListener
        public void onMergedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
            dispatchWindowContextInfoChange();
        }

        @Override // com.android.server.wm.WindowContainerListener
        public void onDisplayChanged(com.android.server.wm.DisplayContent displayContent) {
            dispatchWindowContextInfoChange();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchWindowContextInfoChange() {
            if (this.mDeathRecipient == null) {
                throw new java.lang.IllegalStateException("Invalid client token: " + this.mClientToken);
            }
            com.android.server.wm.DisplayContent displayContent = this.mContainer.getDisplayContent();
            if (!displayContent.isReady()) {
                return;
            }
            if (!android.window.WindowProviderService.isWindowProviderService(this.mOptions) && android.view.Display.isSuspendedState(displayContent.getDisplayInfo().state)) {
                this.mHasPendingConfiguration = true;
                return;
            }
            android.content.res.Configuration configuration = this.mContainer.getConfiguration();
            int displayId = displayContent.getDisplayId();
            if (this.mLastReportedConfig == null) {
                this.mLastReportedConfig = new android.content.res.Configuration();
            }
            if (configuration.equals(this.mLastReportedConfig) && displayId == this.mLastReportedDisplay) {
                return;
            }
            this.mLastReportedConfig.setTo(configuration);
            this.mLastReportedDisplay = displayId;
            this.mWpc.scheduleClientTransactionItem(android.app.servertransaction.WindowContextInfoChangeItem.obtain(this.mClientToken, configuration, displayId));
            this.mHasPendingConfiguration = false;
        }

        @Override // com.android.server.wm.WindowContainerListener
        public void onRemoved() {
            com.android.server.wm.DisplayContent displayContent;
            if (this.mDeathRecipient == null) {
                throw new java.lang.IllegalStateException("Invalid client token: " + this.mClientToken);
            }
            com.android.server.wm.WindowToken asWindowToken = this.mContainer.asWindowToken();
            if (asWindowToken != null && asWindowToken.isFromClient() && (displayContent = asWindowToken.mWmService.mRoot.getDisplayContent(this.mLastReportedDisplay)) != null) {
                updateContainer(displayContent.findAreaForToken(asWindowToken));
                return;
            }
            this.mDeathRecipient.unlinkToDeath();
            this.mWpc.scheduleClientTransactionItem(android.app.servertransaction.WindowContextWindowRemovalItem.obtain(this.mClientToken));
            unregister();
        }

        public java.lang.String toString() {
            return "WindowContextListenerImpl{clientToken=" + this.mClientToken + ", container=" + this.mContainer + "}";
        }

        private class DeathRecipient implements android.os.IBinder.DeathRecipient {
            private DeathRecipient() {
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl.this.mContainer.mWmService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl.this.mDeathRecipient = null;
                        com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl.this.unregister();
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }

            void linkToDeath() throws android.os.RemoteException {
                com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl.this.mClientToken.linkToDeath(this, 0);
            }

            void unlinkToDeath() {
                com.android.server.wm.WindowContextListenerController.WindowContextListenerImpl.this.mClientToken.unlinkToDeath(this, 0);
            }
        }
    }
}
