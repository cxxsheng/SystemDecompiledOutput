package com.android.server.wm;

/* loaded from: classes3.dex */
class InsetsStateController {
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private int mForcedConsumingTypes;
    private final android.view.InsetsState mLastState = new android.view.InsetsState();
    private final android.view.InsetsState mState = new android.view.InsetsState();
    private final android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> mProviders = new android.util.SparseArray<>();
    private final android.util.ArrayMap<com.android.server.wm.InsetsControlTarget, java.util.ArrayList<com.android.server.wm.InsetsSourceProvider>> mControlTargetProvidersMap = new android.util.ArrayMap<>();
    private final android.util.SparseArray<com.android.server.wm.InsetsControlTarget> mIdControlTargetMap = new android.util.SparseArray<>();
    private final android.util.SparseArray<com.android.server.wm.InsetsControlTarget> mIdFakeControlTargetMap = new android.util.SparseArray<>();
    private final android.util.ArraySet<com.android.server.wm.InsetsControlTarget> mPendingControlChanged = new android.util.ArraySet<>();
    private final java.util.function.Consumer<com.android.server.wm.WindowState> mDispatchInsetsChanged = new java.util.function.Consumer() { // from class: com.android.server.wm.InsetsStateController$$ExternalSyntheticLambda2
        @Override // java.util.function.Consumer
        public final void accept(java.lang.Object obj) {
            com.android.server.wm.InsetsStateController.lambda$new$0((com.android.server.wm.WindowState) obj);
        }
    };
    private final com.android.server.wm.InsetsControlTarget mEmptyImeControlTarget = new com.android.server.wm.InsetsStateController.AnonymousClass1();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(com.android.server.wm.WindowState windowState) {
        if (windowState.isReadyToDispatchInsetsState()) {
            windowState.notifyInsetsChanged();
        }
    }

    /* renamed from: com.android.server.wm.InsetsStateController$1, reason: invalid class name */
    class AnonymousClass1 implements com.android.server.wm.InsetsControlTarget {
        AnonymousClass1() {
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public void notifyInsetsControlChanged(final int i) {
            android.view.InsetsSourceControl[] controlsForDispatch = com.android.server.wm.InsetsStateController.this.getControlsForDispatch(this);
            if (controlsForDispatch == null) {
                return;
            }
            for (android.view.InsetsSourceControl insetsSourceControl : controlsForDispatch) {
                if (insetsSourceControl.getType() == android.view.WindowInsets.Type.ime()) {
                    com.android.server.wm.InsetsStateController.this.mDisplayContent.mWmService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.InsetsStateController$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.wm.InsetsStateController.AnonymousClass1.lambda$notifyInsetsControlChanged$0(i);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$notifyInsetsControlChanged$0(int i) {
            com.android.server.inputmethod.InputMethodManagerInternal.get().removeImeSurface(i);
        }
    }

    InsetsStateController(com.android.server.wm.DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
    }

    android.view.InsetsState getRawInsetsState() {
        return this.mState;
    }

    @android.annotation.Nullable
    android.view.InsetsSourceControl[] getControlsForDispatch(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        java.util.ArrayList<com.android.server.wm.InsetsSourceProvider> arrayList = this.mControlTargetProvidersMap.get(insetsControlTarget);
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        android.view.InsetsSourceControl[] insetsSourceControlArr = new android.view.InsetsSourceControl[size];
        for (int i = 0; i < size; i++) {
            insetsSourceControlArr[i] = arrayList.get(i).getControl(insetsControlTarget);
        }
        return insetsSourceControlArr;
    }

    android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> getSourceProviders() {
        return this.mProviders;
    }

    com.android.server.wm.InsetsSourceProvider getOrCreateSourceProvider(int i, int i2) {
        com.android.server.wm.InsetsSourceProvider insetsSourceProvider;
        int i3;
        com.android.server.wm.InsetsSourceProvider insetsSourceProvider2 = this.mProviders.get(i);
        if (insetsSourceProvider2 != null) {
            return insetsSourceProvider2;
        }
        android.view.InsetsSource orCreateSource = this.mState.getOrCreateSource(i, i2);
        if (i == android.view.InsetsSource.ID_IME) {
            insetsSourceProvider = new com.android.server.wm.ImeInsetsSourceProvider(orCreateSource, this, this.mDisplayContent);
        } else {
            insetsSourceProvider = new com.android.server.wm.InsetsSourceProvider(orCreateSource, this, this.mDisplayContent);
        }
        if ((i2 & this.mForcedConsumingTypes) != 0) {
            i3 = 4;
        } else {
            i3 = 0;
        }
        insetsSourceProvider.setFlags(i3, 4);
        this.mProviders.put(i, insetsSourceProvider);
        return insetsSourceProvider;
    }

    com.android.server.wm.ImeInsetsSourceProvider getImeSourceProvider() {
        return (com.android.server.wm.ImeInsetsSourceProvider) getOrCreateSourceProvider(android.view.InsetsSource.ID_IME, android.view.WindowInsets.Type.ime());
    }

    void removeSourceProvider(int i) {
        if (i != android.view.InsetsSource.ID_IME) {
            this.mState.removeSource(i);
            this.mProviders.remove(i);
        }
    }

    void setForcedConsumingTypes(int i) {
        int i2;
        if (this.mForcedConsumingTypes != i) {
            this.mForcedConsumingTypes = i;
            boolean z = false;
            for (int size = this.mProviders.size() - 1; size >= 0; size--) {
                com.android.server.wm.InsetsSourceProvider valueAt = this.mProviders.valueAt(size);
                if ((valueAt.getSource().getType() & i) != 0) {
                    i2 = 4;
                } else {
                    i2 = 0;
                }
                z |= valueAt.setFlags(i2, 4);
            }
            if (z) {
                notifyInsetsChanged();
            }
        }
    }

    void onPostLayout() {
        android.os.Trace.traceBegin(32L, "ISC.onPostLayout");
        for (int size = this.mProviders.size() - 1; size >= 0; size--) {
            this.mProviders.valueAt(size).onPostLayout();
        }
        if (!this.mLastState.equals(this.mState)) {
            this.mLastState.set(this.mState, true);
            notifyInsetsChanged();
        }
        android.os.Trace.traceEnd(32L);
    }

    void updateAboveInsetsState(boolean z) {
        android.view.InsetsState insetsState = new android.view.InsetsState();
        insetsState.set(this.mState, android.view.WindowInsets.Type.displayCutout() | android.view.WindowInsets.Type.systemGestures() | android.view.WindowInsets.Type.mandatorySystemGestures());
        android.util.SparseArray<android.view.InsetsSource> sparseArray = new android.util.SparseArray<>();
        android.util.ArraySet<com.android.server.wm.WindowState> arraySet = new android.util.ArraySet<>();
        this.mDisplayContent.updateAboveInsetsState(insetsState, sparseArray, arraySet);
        if (z) {
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                this.mDispatchInsetsChanged.accept(arraySet.valueAt(size));
            }
        }
    }

    void onDisplayFramesUpdated(boolean z) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        this.mDisplayContent.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.InsetsStateController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.InsetsStateController.this.lambda$onDisplayFramesUpdated$1(arrayList, (com.android.server.wm.WindowState) obj);
            }
        }, true);
        if (z) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                this.mDispatchInsetsChanged.accept((com.android.server.wm.WindowState) arrayList.get(size));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayFramesUpdated$1(java.util.ArrayList arrayList, com.android.server.wm.WindowState windowState) {
        windowState.mAboveInsetsState.set(this.mState, android.view.WindowInsets.Type.displayCutout());
        arrayList.add(windowState);
    }

    void onRequestedVisibleTypesChanged(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        boolean z = false;
        for (int size = this.mProviders.size() - 1; size >= 0; size--) {
            z |= this.mProviders.valueAt(size).updateClientVisibility(insetsControlTarget);
        }
        if (z) {
            notifyInsetsChanged();
            this.mDisplayContent.updateSystemGestureExclusion();
            this.mDisplayContent.updateKeepClearAreas();
            this.mDisplayContent.getDisplayPolicy().updateSystemBarAttributes();
        }
    }

    int getFakeControllingTypes(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        int i = 0;
        for (int size = this.mProviders.size() - 1; size >= 0; size--) {
            com.android.server.wm.InsetsSourceProvider valueAt = this.mProviders.valueAt(size);
            if (insetsControlTarget == valueAt.getFakeControlTarget()) {
                i |= valueAt.getSource().getType();
            }
        }
        return i;
    }

    void onImeControlTargetChanged(@android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        if (insetsControlTarget == null) {
            insetsControlTarget = this.mEmptyImeControlTarget;
        }
        onControlTargetChanged(getImeSourceProvider(), insetsControlTarget, false);
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, -6684172224226118673L, 0, null, java.lang.String.valueOf(insetsControlTarget != null ? insetsControlTarget.getWindow() : "null"));
        notifyPendingInsetsControlChanged();
    }

    void onBarControlTargetChanged(@android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget, @android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget2, @android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget3, @android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget4) {
        for (int size = this.mProviders.size() - 1; size >= 0; size--) {
            com.android.server.wm.InsetsSourceProvider valueAt = this.mProviders.valueAt(size);
            int type = valueAt.getSource().getType();
            if (type == android.view.WindowInsets.Type.statusBars()) {
                onControlTargetChanged(valueAt, insetsControlTarget, false);
                onControlTargetChanged(valueAt, insetsControlTarget2, true);
            } else if (type == android.view.WindowInsets.Type.navigationBars()) {
                onControlTargetChanged(valueAt, insetsControlTarget3, false);
                onControlTargetChanged(valueAt, insetsControlTarget4, true);
            }
        }
        notifyPendingInsetsControlChanged();
    }

    void notifyControlTargetChanged(@android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget, com.android.server.wm.InsetsSourceProvider insetsSourceProvider) {
        onControlTargetChanged(insetsSourceProvider, insetsControlTarget, false);
        notifyPendingInsetsControlChanged();
    }

    void notifyControlRevoked(@android.annotation.NonNull com.android.server.wm.InsetsControlTarget insetsControlTarget, com.android.server.wm.InsetsSourceProvider insetsSourceProvider) {
        removeFromControlMaps(insetsControlTarget, insetsSourceProvider, false);
    }

    private void onControlTargetChanged(com.android.server.wm.InsetsSourceProvider insetsSourceProvider, @android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget, boolean z) {
        com.android.server.wm.InsetsControlTarget insetsControlTarget2;
        if (z) {
            insetsControlTarget2 = this.mIdFakeControlTargetMap.get(insetsSourceProvider.getSource().getId());
        } else {
            insetsControlTarget2 = this.mIdControlTargetMap.get(insetsSourceProvider.getSource().getId());
        }
        if (insetsControlTarget == insetsControlTarget2 || !insetsSourceProvider.isControllable()) {
            return;
        }
        if (z) {
            insetsSourceProvider.updateFakeControlTarget(insetsControlTarget);
        } else {
            insetsSourceProvider.updateControlForTarget(insetsControlTarget, false);
            insetsControlTarget = insetsSourceProvider.getControlTarget();
            if (insetsControlTarget == insetsControlTarget2) {
                return;
            }
        }
        if (insetsControlTarget2 != null) {
            removeFromControlMaps(insetsControlTarget2, insetsSourceProvider, z);
            this.mPendingControlChanged.add(insetsControlTarget2);
        }
        if (insetsControlTarget != null) {
            addToControlMaps(insetsControlTarget, insetsSourceProvider, z);
            this.mPendingControlChanged.add(insetsControlTarget);
        }
    }

    private void removeFromControlMaps(@android.annotation.NonNull com.android.server.wm.InsetsControlTarget insetsControlTarget, com.android.server.wm.InsetsSourceProvider insetsSourceProvider, boolean z) {
        java.util.ArrayList<com.android.server.wm.InsetsSourceProvider> arrayList = this.mControlTargetProvidersMap.get(insetsControlTarget);
        if (arrayList == null) {
            return;
        }
        arrayList.remove(insetsSourceProvider);
        if (arrayList.isEmpty()) {
            this.mControlTargetProvidersMap.remove(insetsControlTarget);
        }
        if (z) {
            this.mIdFakeControlTargetMap.remove(insetsSourceProvider.getSource().getId());
        } else {
            this.mIdControlTargetMap.remove(insetsSourceProvider.getSource().getId());
        }
    }

    private void addToControlMaps(@android.annotation.NonNull com.android.server.wm.InsetsControlTarget insetsControlTarget, com.android.server.wm.InsetsSourceProvider insetsSourceProvider, boolean z) {
        this.mControlTargetProvidersMap.computeIfAbsent(insetsControlTarget, new java.util.function.Function() { // from class: com.android.server.wm.InsetsStateController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.ArrayList lambda$addToControlMaps$2;
                lambda$addToControlMaps$2 = com.android.server.wm.InsetsStateController.lambda$addToControlMaps$2((com.android.server.wm.InsetsControlTarget) obj);
                return lambda$addToControlMaps$2;
            }
        }).add(insetsSourceProvider);
        if (z) {
            this.mIdFakeControlTargetMap.put(insetsSourceProvider.getSource().getId(), insetsControlTarget);
        } else {
            this.mIdControlTargetMap.put(insetsSourceProvider.getSource().getId(), insetsControlTarget);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.ArrayList lambda$addToControlMaps$2(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        return new java.util.ArrayList();
    }

    void notifyControlChanged(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        this.mPendingControlChanged.add(insetsControlTarget);
        notifyPendingInsetsControlChanged();
    }

    private void notifyPendingInsetsControlChanged() {
        if (this.mPendingControlChanged.isEmpty()) {
            return;
        }
        this.mDisplayContent.mWmService.mAnimator.addAfterPrepareSurfacesRunnable(new java.lang.Runnable() { // from class: com.android.server.wm.InsetsStateController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.InsetsStateController.this.lambda$notifyPendingInsetsControlChanged$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPendingInsetsControlChanged$3() {
        for (int size = this.mProviders.size() - 1; size >= 0; size--) {
            this.mProviders.valueAt(size).onSurfaceTransactionApplied();
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int displayId = this.mDisplayContent.getDisplayId();
        for (int size2 = this.mPendingControlChanged.size() - 1; size2 >= 0; size2--) {
            com.android.server.wm.InsetsControlTarget valueAt = this.mPendingControlChanged.valueAt(size2);
            valueAt.notifyInsetsControlChanged(displayId);
            if (this.mControlTargetProvidersMap.containsKey(valueAt)) {
                arraySet.add(valueAt);
            }
        }
        this.mPendingControlChanged.clear();
        for (int size3 = arraySet.size() - 1; size3 >= 0; size3--) {
            onRequestedVisibleTypesChanged((com.android.server.wm.InsetsControlTarget) arraySet.valueAt(size3));
        }
        arraySet.clear();
    }

    void notifyInsetsChanged() {
        this.mDisplayContent.notifyInsetsChanged(this.mDispatchInsetsChanged);
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + "WindowInsetsStateController");
        java.lang.String str2 = str + "  ";
        this.mState.dump(str2, printWriter);
        printWriter.println(str2 + "Control map:");
        for (int size = this.mControlTargetProvidersMap.size() + (-1); size >= 0; size--) {
            com.android.server.wm.InsetsControlTarget keyAt = this.mControlTargetProvidersMap.keyAt(size);
            printWriter.print(str2 + "  ");
            printWriter.print(keyAt);
            printWriter.println(":");
            java.util.ArrayList<com.android.server.wm.InsetsSourceProvider> valueAt = this.mControlTargetProvidersMap.valueAt(size);
            for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                com.android.server.wm.InsetsSourceProvider insetsSourceProvider = valueAt.get(size2);
                if (insetsSourceProvider != null) {
                    printWriter.print(str2 + "    ");
                    if (keyAt == insetsSourceProvider.getFakeControlTarget()) {
                        printWriter.print("(fake) ");
                    }
                    printWriter.println(insetsSourceProvider.getControl(keyAt));
                }
            }
        }
        if (this.mControlTargetProvidersMap.isEmpty()) {
            printWriter.print(str2 + "  none");
        }
        printWriter.println(str2 + "InsetsSourceProviders:");
        for (int size3 = this.mProviders.size() + (-1); size3 >= 0; size3 += -1) {
            this.mProviders.valueAt(size3).dump(printWriter, str2 + "  ");
        }
        if (this.mForcedConsumingTypes != 0) {
            printWriter.println(str2 + "mForcedConsumingTypes=" + android.view.WindowInsets.Type.toString(this.mForcedConsumingTypes));
        }
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, int i) {
        long j;
        for (int size = this.mProviders.size() - 1; size >= 0; size--) {
            com.android.server.wm.InsetsSourceProvider valueAt = this.mProviders.valueAt(size);
            if (valueAt.getSource().getType() == android.view.WindowInsets.Type.ime()) {
                j = 1146756268063L;
            } else {
                j = 2246267895843L;
            }
            valueAt.dumpDebug(protoOutputStream, j, i);
        }
    }
}
