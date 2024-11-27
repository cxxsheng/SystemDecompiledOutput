package com.android.server.wm;

/* loaded from: classes3.dex */
public class DisplayAreaOrganizerController extends android.window.IDisplayAreaOrganizerController.Stub {
    private static final java.lang.String TAG = "DisplayAreaOrganizerController";
    private final com.android.server.wm.WindowManagerGlobalLock mGlobalLock;
    private int mNextTaskDisplayAreaFeatureId = 20002;
    private final java.util.HashMap<java.lang.Integer, com.android.server.wm.DisplayAreaOrganizerController.DisplayAreaOrganizerState> mOrganizersByFeatureIds = new java.util.HashMap<>();
    final com.android.server.wm.ActivityTaskManagerService mService;

    private class DeathRecipient implements android.os.IBinder.DeathRecipient {
        int mFeature;
        android.window.IDisplayAreaOrganizer mOrganizer;

        DeathRecipient(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) {
            this.mOrganizer = iDisplayAreaOrganizer;
            this.mFeature = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.DisplayAreaOrganizerController.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    android.window.IDisplayAreaOrganizer organizerByFeature = com.android.server.wm.DisplayAreaOrganizerController.this.getOrganizerByFeature(this.mFeature);
                    if (organizerByFeature != null) {
                        android.os.IBinder asBinder = organizerByFeature.asBinder();
                        if (!asBinder.equals(this.mOrganizer.asBinder()) && asBinder.isBinderAlive()) {
                            android.util.Slog.d(com.android.server.wm.DisplayAreaOrganizerController.TAG, "Dead organizer replaced for feature=" + this.mFeature);
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        ((com.android.server.wm.DisplayAreaOrganizerController.DisplayAreaOrganizerState) com.android.server.wm.DisplayAreaOrganizerController.this.mOrganizersByFeatureIds.remove(java.lang.Integer.valueOf(this.mFeature))).destroy();
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DisplayAreaOrganizerState {
        private final com.android.server.wm.DisplayAreaOrganizerController.DeathRecipient mDeathRecipient;
        private final android.window.IDisplayAreaOrganizer mOrganizer;

        DisplayAreaOrganizerState(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) {
            this.mOrganizer = iDisplayAreaOrganizer;
            this.mDeathRecipient = com.android.server.wm.DisplayAreaOrganizerController.this.new DeathRecipient(iDisplayAreaOrganizer, i);
            try {
                iDisplayAreaOrganizer.asBinder().linkToDeath(this.mDeathRecipient, 0);
            } catch (android.os.RemoteException e) {
            }
        }

        void destroy() {
            final android.os.IBinder asBinder = this.mOrganizer.asBinder();
            com.android.server.wm.DisplayAreaOrganizerController.this.mService.mRootWindowContainer.forAllDisplayAreas(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayAreaOrganizerController$DisplayAreaOrganizerState$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.DisplayAreaOrganizerController.DisplayAreaOrganizerState.this.lambda$destroy$0(asBinder, (com.android.server.wm.DisplayArea) obj);
                }
            });
            asBinder.unlinkToDeath(this.mDeathRecipient, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$destroy$0(android.os.IBinder iBinder, com.android.server.wm.DisplayArea displayArea) {
            if (displayArea.mOrganizer != null && displayArea.mOrganizer.asBinder().equals(iBinder)) {
                if (displayArea.isTaskDisplayArea() && displayArea.asTaskDisplayArea().mCreatedByOrganizer) {
                    com.android.server.wm.DisplayAreaOrganizerController.this.deleteTaskDisplayArea(displayArea.asTaskDisplayArea());
                } else {
                    displayArea.setOrganizer(null);
                }
            }
        }
    }

    DisplayAreaOrganizerController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this.mService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    private void enforceTaskPermission(java.lang.String str) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission(str);
    }

    @android.annotation.Nullable
    android.window.IDisplayAreaOrganizer getOrganizerByFeature(int i) {
        com.android.server.wm.DisplayAreaOrganizerController.DisplayAreaOrganizerState displayAreaOrganizerState = this.mOrganizersByFeatureIds.get(java.lang.Integer.valueOf(i));
        if (displayAreaOrganizerState != null) {
            return displayAreaOrganizerState.mOrganizer;
        }
        return null;
    }

    public android.content.pm.ParceledListSlice<android.window.DisplayAreaAppearedInfo> registerOrganizer(final android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, final int i) {
        android.content.pm.ParceledListSlice<android.window.DisplayAreaAppearedInfo> parceledListSlice;
        enforceTaskPermission("registerOrganizer()");
        long callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 3968604152682328317L, 4, null, java.lang.String.valueOf(iDisplayAreaOrganizer.asBinder()), java.lang.Long.valueOf(callingUid));
                    if (this.mOrganizersByFeatureIds.get(java.lang.Integer.valueOf(i)) != null) {
                        this.mOrganizersByFeatureIds.remove(java.lang.Integer.valueOf(i)).destroy();
                        android.util.Slog.d(TAG, "Replacing dead organizer for feature=" + i);
                    }
                    com.android.server.wm.DisplayAreaOrganizerController.DisplayAreaOrganizerState displayAreaOrganizerState = new com.android.server.wm.DisplayAreaOrganizerController.DisplayAreaOrganizerState(iDisplayAreaOrganizer, i);
                    final java.util.ArrayList arrayList = new java.util.ArrayList();
                    this.mService.mRootWindowContainer.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayAreaOrganizerController$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.DisplayAreaOrganizerController.this.lambda$registerOrganizer$1(i, arrayList, iDisplayAreaOrganizer, (com.android.server.wm.DisplayContent) obj);
                        }
                    });
                    this.mOrganizersByFeatureIds.put(java.lang.Integer.valueOf(i), displayAreaOrganizerState);
                    parceledListSlice = new android.content.pm.ParceledListSlice<>(arrayList);
                } finally {
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return parceledListSlice;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerOrganizer$1(final int i, final java.util.List list, final android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, com.android.server.wm.DisplayContent displayContent) {
        if (!displayContent.isTrusted()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -3066370283926570943L, 1, null, java.lang.Long.valueOf(displayContent.getDisplayId()));
        } else {
            displayContent.forAllDisplayAreas(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayAreaOrganizerController$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.DisplayAreaOrganizerController.this.lambda$registerOrganizer$0(i, list, iDisplayAreaOrganizer, (com.android.server.wm.DisplayArea) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerOrganizer$0(int i, java.util.List list, android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, com.android.server.wm.DisplayArea displayArea) {
        if (displayArea.mFeatureId != i) {
            return;
        }
        list.add(organizeDisplayArea(iDisplayAreaOrganizer, displayArea, "DisplayAreaOrganizerController.registerOrganizer"));
    }

    public void unregisterOrganizer(final android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer) {
        enforceTaskPermission("unregisterTaskOrganizer()");
        long callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -943497726140336963L, 4, null, java.lang.String.valueOf(iDisplayAreaOrganizer.asBinder()), java.lang.Long.valueOf(callingUid));
                    this.mOrganizersByFeatureIds.entrySet().removeIf(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayAreaOrganizerController$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$unregisterOrganizer$2;
                            lambda$unregisterOrganizer$2 = com.android.server.wm.DisplayAreaOrganizerController.lambda$unregisterOrganizer$2(iDisplayAreaOrganizer, (java.util.Map.Entry) obj);
                            return lambda$unregisterOrganizer$2;
                        }
                    });
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$unregisterOrganizer$2(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, java.util.Map.Entry entry) {
        boolean equals = ((com.android.server.wm.DisplayAreaOrganizerController.DisplayAreaOrganizerState) entry.getValue()).mOrganizer.asBinder().equals(iDisplayAreaOrganizer.asBinder());
        if (equals) {
            ((com.android.server.wm.DisplayAreaOrganizerController.DisplayAreaOrganizerState) entry.getValue()).destroy();
        }
        return equals;
    }

    public android.window.DisplayAreaAppearedInfo createTaskDisplayArea(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, int i, final int i2, java.lang.String str) {
        com.android.server.wm.TaskDisplayArea taskDisplayArea;
        com.android.server.wm.TaskDisplayArea createTaskDisplayArea;
        android.window.DisplayAreaAppearedInfo organizeDisplayArea;
        enforceTaskPermission("createTaskDisplayArea()");
        long callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 5147103403966149923L, 1, null, java.lang.Long.valueOf(callingUid));
                    com.android.server.wm.DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent == null) {
                        throw new java.lang.IllegalArgumentException("createTaskDisplayArea unknown displayId=" + i);
                    }
                    if (!displayContent.isTrusted()) {
                        throw new java.lang.IllegalArgumentException("createTaskDisplayArea untrusted displayId=" + i);
                    }
                    com.android.server.wm.RootDisplayArea rootDisplayArea = (com.android.server.wm.RootDisplayArea) displayContent.getItemFromDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.DisplayAreaOrganizerController$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            com.android.server.wm.RootDisplayArea lambda$createTaskDisplayArea$3;
                            lambda$createTaskDisplayArea$3 = com.android.server.wm.DisplayAreaOrganizerController.lambda$createTaskDisplayArea$3(i2, (com.android.server.wm.DisplayArea) obj);
                            return lambda$createTaskDisplayArea$3;
                        }
                    });
                    if (rootDisplayArea == null) {
                        taskDisplayArea = (com.android.server.wm.TaskDisplayArea) displayContent.getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.DisplayAreaOrganizerController$$ExternalSyntheticLambda4
                            @Override // java.util.function.Function
                            public final java.lang.Object apply(java.lang.Object obj) {
                                com.android.server.wm.TaskDisplayArea lambda$createTaskDisplayArea$4;
                                lambda$createTaskDisplayArea$4 = com.android.server.wm.DisplayAreaOrganizerController.lambda$createTaskDisplayArea$4(i2, (com.android.server.wm.TaskDisplayArea) obj);
                                return lambda$createTaskDisplayArea$4;
                            }
                        });
                    } else {
                        taskDisplayArea = null;
                    }
                    if (rootDisplayArea == null && taskDisplayArea == null) {
                        throw new java.lang.IllegalArgumentException("Can't find a parent DisplayArea with featureId=" + i2);
                    }
                    int i3 = this.mNextTaskDisplayAreaFeatureId;
                    this.mNextTaskDisplayAreaFeatureId = i3 + 1;
                    com.android.server.wm.DisplayAreaOrganizerController.DisplayAreaOrganizerState displayAreaOrganizerState = new com.android.server.wm.DisplayAreaOrganizerController.DisplayAreaOrganizerState(iDisplayAreaOrganizer, i3);
                    if (rootDisplayArea != null) {
                        createTaskDisplayArea = createTaskDisplayArea(rootDisplayArea, str, i3);
                    } else {
                        createTaskDisplayArea = createTaskDisplayArea(taskDisplayArea, str, i3);
                    }
                    organizeDisplayArea = organizeDisplayArea(iDisplayAreaOrganizer, createTaskDisplayArea, "DisplayAreaOrganizerController.createTaskDisplayArea");
                    this.mOrganizersByFeatureIds.put(java.lang.Integer.valueOf(i3), displayAreaOrganizerState);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return organizeDisplayArea;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.wm.RootDisplayArea lambda$createTaskDisplayArea$3(int i, com.android.server.wm.DisplayArea displayArea) {
        if (displayArea.asRootDisplayArea() != null && displayArea.mFeatureId == i) {
            return displayArea.asRootDisplayArea();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.wm.TaskDisplayArea lambda$createTaskDisplayArea$4(int i, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        if (taskDisplayArea.mFeatureId == i) {
            return taskDisplayArea;
        }
        return null;
    }

    public void deleteTaskDisplayArea(android.window.WindowContainerToken windowContainerToken) {
        enforceTaskPermission("deleteTaskDisplayArea()");
        long callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -1659480097203667175L, 1, null, java.lang.Long.valueOf(callingUid));
                    com.android.server.wm.WindowContainer fromBinder = com.android.server.wm.WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null || fromBinder.asTaskDisplayArea() == null) {
                        throw new java.lang.IllegalArgumentException("Can't resolve TaskDisplayArea from token");
                    }
                    com.android.server.wm.TaskDisplayArea asTaskDisplayArea = fromBinder.asTaskDisplayArea();
                    if (!asTaskDisplayArea.mCreatedByOrganizer) {
                        throw new java.lang.IllegalArgumentException("Attempt to delete TaskDisplayArea not created by organizer TaskDisplayArea=" + asTaskDisplayArea);
                    }
                    this.mOrganizersByFeatureIds.remove(java.lang.Integer.valueOf(asTaskDisplayArea.mFeatureId)).destroy();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void onDisplayAreaAppeared(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, com.android.server.wm.DisplayArea displayArea) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -4514772405648277945L, 0, null, java.lang.String.valueOf(displayArea.getName()));
        try {
            iDisplayAreaOrganizer.onDisplayAreaAppeared(displayArea.getDisplayAreaInfo(), new android.view.SurfaceControl(displayArea.getSurfaceControl(), "DisplayAreaOrganizerController.onDisplayAreaAppeared"));
        } catch (android.os.RemoteException e) {
        }
    }

    void onDisplayAreaVanished(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, com.android.server.wm.DisplayArea displayArea) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 995846188225477231L, 0, null, java.lang.String.valueOf(displayArea.getName()));
        if (!iDisplayAreaOrganizer.asBinder().isBinderAlive()) {
            android.util.Slog.d(TAG, "Organizer died before sending onDisplayAreaVanished");
        } else {
            try {
                iDisplayAreaOrganizer.onDisplayAreaVanished(displayArea.getDisplayAreaInfo());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void onDisplayAreaInfoChanged(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, com.android.server.wm.DisplayArea displayArea) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -1007032390526684388L, 0, null, java.lang.String.valueOf(displayArea.getName()));
        try {
            iDisplayAreaOrganizer.onDisplayAreaInfoChanged(displayArea.getDisplayAreaInfo());
        } catch (android.os.RemoteException e) {
        }
    }

    private android.window.DisplayAreaAppearedInfo organizeDisplayArea(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, com.android.server.wm.DisplayArea displayArea, java.lang.String str) {
        displayArea.setOrganizer(iDisplayAreaOrganizer, true);
        return new android.window.DisplayAreaAppearedInfo(displayArea.getDisplayAreaInfo(), new android.view.SurfaceControl(displayArea.getSurfaceControl(), str));
    }

    private com.android.server.wm.TaskDisplayArea createTaskDisplayArea(final com.android.server.wm.RootDisplayArea rootDisplayArea, java.lang.String str, int i) {
        com.android.server.wm.TaskDisplayArea taskDisplayArea = new com.android.server.wm.TaskDisplayArea(rootDisplayArea.mDisplayContent, rootDisplayArea.mWmService, str, i, true);
        com.android.server.wm.DisplayArea displayArea = (com.android.server.wm.DisplayArea) rootDisplayArea.getItemFromDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.DisplayAreaOrganizerController$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.wm.DisplayArea lambda$createTaskDisplayArea$5;
                lambda$createTaskDisplayArea$5 = com.android.server.wm.DisplayAreaOrganizerController.lambda$createTaskDisplayArea$5(com.android.server.wm.RootDisplayArea.this, (com.android.server.wm.DisplayArea) obj);
                return lambda$createTaskDisplayArea$5;
            }
        });
        if (displayArea == null) {
            throw new java.lang.IllegalStateException("Root must either contain TDA or DAG root=" + rootDisplayArea);
        }
        com.android.server.wm.WindowContainer parent = displayArea.getParent();
        parent.addChild(taskDisplayArea, parent.mChildren.indexOf(displayArea) + 1);
        return taskDisplayArea;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.wm.DisplayArea lambda$createTaskDisplayArea$5(com.android.server.wm.RootDisplayArea rootDisplayArea, com.android.server.wm.DisplayArea displayArea) {
        if (displayArea.mType != com.android.server.wm.DisplayArea.Type.ANY) {
            return null;
        }
        com.android.server.wm.RootDisplayArea rootDisplayArea2 = displayArea.getRootDisplayArea();
        if (rootDisplayArea2 == rootDisplayArea || rootDisplayArea2 == displayArea) {
            return displayArea;
        }
        return null;
    }

    private com.android.server.wm.TaskDisplayArea createTaskDisplayArea(com.android.server.wm.TaskDisplayArea taskDisplayArea, java.lang.String str, int i) {
        com.android.server.wm.TaskDisplayArea taskDisplayArea2 = new com.android.server.wm.TaskDisplayArea(taskDisplayArea.mDisplayContent, taskDisplayArea.mWmService, str, i, true);
        taskDisplayArea.addChild(taskDisplayArea2, Integer.MAX_VALUE);
        return taskDisplayArea2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteTaskDisplayArea(com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        taskDisplayArea.setOrganizer(null);
        this.mService.mRootWindowContainer.mTaskSupervisor.beginDeferResume();
        try {
            com.android.server.wm.Task remove = taskDisplayArea.remove();
            this.mService.mRootWindowContainer.mTaskSupervisor.endDeferResume();
            taskDisplayArea.removeImmediately();
            if (remove != null) {
                remove.resumeNextFocusAfterReparent();
            }
        } catch (java.lang.Throwable th) {
            this.mService.mRootWindowContainer.mTaskSupervisor.endDeferResume();
            throw th;
        }
    }
}
