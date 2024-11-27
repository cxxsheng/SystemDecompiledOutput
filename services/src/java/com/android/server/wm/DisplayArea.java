package com.android.server.wm;

/* loaded from: classes3.dex */
public class DisplayArea<T extends com.android.server.wm.WindowContainer> extends com.android.server.wm.WindowContainer<T> {

    @com.android.internal.annotations.VisibleForTesting
    boolean mDisplayAreaAppearedSent;
    final int mFeatureId;
    private final java.lang.String mName;
    android.window.IDisplayAreaOrganizer mOrganizer;
    private final com.android.server.wm.DisplayAreaOrganizerController mOrganizerController;
    protected boolean mSetIgnoreOrientationRequest;
    private final android.content.res.Configuration mTmpConfiguration;
    protected final com.android.server.wm.DisplayArea.Type mType;

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ void commitPendingTransaction() {
        super.commitPendingTransaction();
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ int compareTo(com.android.server.wm.WindowContainer windowContainer) {
        return super.compareTo(windowContainer);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ android.view.SurfaceControl getAnimationLeash() {
        return super.getAnimationLeash();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ android.view.SurfaceControl getAnimationLeashParent() {
        return super.getAnimationLeashParent();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceFreezer.Freezable
    public /* bridge */ /* synthetic */ android.view.SurfaceControl getFreezeSnapshotTarget() {
        return super.getFreezeSnapshotTarget();
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ android.util.SparseArray getInsetsSourceProviders() {
        return super.getInsetsSourceProviders();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ android.view.SurfaceControl getParentSurfaceControl() {
        return super.getParentSurfaceControl();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ android.view.SurfaceControl.Transaction getPendingTransaction() {
        return super.getPendingTransaction();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ android.view.SurfaceControl getSurfaceControl() {
        return super.getSurfaceControl();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ int getSurfaceHeight() {
        return super.getSurfaceHeight();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ int getSurfaceWidth() {
        return super.getSurfaceWidth();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ android.view.SurfaceControl.Transaction getSyncTransaction() {
        return super.getSyncTransaction();
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ boolean hasInsetsSourceProvider() {
        return super.hasInsetsSourceProvider();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ android.view.SurfaceControl.Builder makeAnimationLeash() {
        return super.makeAnimationLeash();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ void onAnimationLeashCreated(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        super.onAnimationLeashCreated(transaction, surfaceControl);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ void onAnimationLeashLost(android.view.SurfaceControl.Transaction transaction) {
        super.onAnimationLeashLost(transaction);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public /* bridge */ /* synthetic */ void onRequestedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
        super.onRequestedOverrideConfigurationChanged(configuration);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceFreezer.Freezable
    public /* bridge */ /* synthetic */ void onUnfrozen() {
        super.onUnfrozen();
    }

    DisplayArea(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayArea.Type type, java.lang.String str) {
        this(windowManagerService, type, str, -1);
    }

    DisplayArea(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayArea.Type type, java.lang.String str, int i) {
        super(windowManagerService);
        this.mTmpConfiguration = new android.content.res.Configuration();
        setOverrideOrientation(-2);
        this.mType = type;
        this.mName = str;
        this.mFeatureId = i;
        this.mRemoteToken = new com.android.server.wm.WindowContainer.RemoteToken(this);
        this.mOrganizerController = windowManagerService.mAtmService.mWindowOrganizerController.mDisplayAreaOrganizerController;
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.wm.WindowContainer] */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.wm.WindowContainer] */
    @Override // com.android.server.wm.WindowContainer
    void onChildPositionChanged(com.android.server.wm.WindowContainer windowContainer) {
        super.onChildPositionChanged(windowContainer);
        com.android.server.wm.DisplayArea.Type.checkChild(this.mType, com.android.server.wm.DisplayArea.Type.typeOf(windowContainer));
        if (windowContainer instanceof com.android.server.wm.Task) {
            return;
        }
        for (int i = 1; i < getChildCount(); i++) {
            ?? childAt = getChildAt(i - 1);
            ?? childAt2 = getChildAt(i);
            if (windowContainer == childAt || windowContainer == childAt2) {
                com.android.server.wm.DisplayArea.Type.checkSiblings(com.android.server.wm.DisplayArea.Type.typeOf((com.android.server.wm.WindowContainer) childAt), com.android.server.wm.DisplayArea.Type.typeOf((com.android.server.wm.WindowContainer) childAt2));
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void positionChildAt(int i, T t, boolean z) {
        if (t.asDisplayArea() == null) {
            super.positionChildAt(i, t, z);
            return;
        }
        super.positionChildAt(findPositionForChildDisplayArea(i, t.asDisplayArea()), t, false);
        com.android.server.wm.WindowContainer parent = getParent();
        if (!z || parent == null) {
            return;
        }
        if (i == Integer.MAX_VALUE || i == Integer.MIN_VALUE) {
            parent.positionChildAt(i, this, true);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    int getOrientation(int i) {
        int orientation = super.getOrientation(i);
        if (shouldIgnoreOrientationRequest(orientation)) {
            this.mLastOrientationSource = null;
            return -2;
        }
        return orientation;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean handlesOrientationChangeFromDescendant(int i) {
        return !shouldIgnoreOrientationRequest(i) && super.handlesOrientationChangeFromDescendant(i);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean onDescendantOrientationChanged(@android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer) {
        int i;
        if (windowContainer != null) {
            i = windowContainer.getOverrideOrientation();
        } else {
            i = -2;
        }
        return !shouldIgnoreOrientationRequest(i) && super.onDescendantOrientationChanged(windowContainer);
    }

    boolean setIgnoreOrientationRequest(boolean z) {
        if (this.mSetIgnoreOrientationRequest == z) {
            return false;
        }
        this.mSetIgnoreOrientationRequest = z;
        if (this.mDisplayContent == null) {
            return false;
        }
        if (this.mDisplayContent.mFocusedApp != null) {
            this.mDisplayContent.onLastFocusedTaskDisplayAreaChanged(this.mDisplayContent.mFocusedApp.getDisplayArea());
        }
        if (!z) {
            return this.mDisplayContent.updateOrientation();
        }
        int lastOrientation = this.mDisplayContent.getLastOrientation();
        com.android.server.wm.WindowContainer lastOrientationSource = this.mDisplayContent.getLastOrientationSource();
        if (lastOrientation == -2 || lastOrientation == -1) {
            return false;
        }
        if (lastOrientationSource == null || lastOrientationSource.isDescendantOf(this)) {
            return this.mDisplayContent.updateOrientation();
        }
        return false;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void setAlwaysOnTop(boolean z) {
        if (isAlwaysOnTop() == z) {
            return;
        }
        super.setAlwaysOnTop(z);
        if (getParent().asDisplayArea() != null) {
            getParent().asDisplayArea().positionChildAt(Integer.MAX_VALUE, this, false);
        }
    }

    boolean shouldIgnoreOrientationRequest(int i) {
        return (i == 14 || i == 5 || !getIgnoreOrientationRequest() || shouldRespectOrientationRequestDueToPerAppOverride()) ? false : true;
    }

    private boolean shouldRespectOrientationRequestDueToPerAppOverride() {
        com.android.server.wm.ActivityRecord activityRecord;
        return (this.mDisplayContent == null || (activityRecord = this.mDisplayContent.topRunningActivity(true)) == null || activityRecord.getTaskFragment() == null || activityRecord.getTaskFragment().getWindowingMode() != 1 || !activityRecord.mLetterboxUiController.isOverrideRespectRequestedOrientationEnabled()) ? false : true;
    }

    boolean getIgnoreOrientationRequest() {
        return this.mSetIgnoreOrientationRequest && !this.mWmService.isIgnoreOrientationRequestDisabled();
    }

    private int findPositionForChildDisplayArea(int i, com.android.server.wm.DisplayArea displayArea) {
        if (displayArea.getParent() != this) {
            throw new java.lang.IllegalArgumentException("positionChildAt: container=" + displayArea.getName() + " is not a child of container=" + getName() + " current parent=" + displayArea.getParent());
        }
        int findMaxPositionForChildDisplayArea = findMaxPositionForChildDisplayArea(displayArea);
        int findMinPositionForChildDisplayArea = findMinPositionForChildDisplayArea(displayArea);
        int i2 = 0;
        for (int i3 = findMinPositionForChildDisplayArea; i3 <= findMaxPositionForChildDisplayArea; i3++) {
            if (((com.android.server.wm.WindowContainer) this.mChildren.get(i3)).isAlwaysOnTop()) {
                i2++;
            }
        }
        if (displayArea.isAlwaysOnTop()) {
            findMinPositionForChildDisplayArea = (findMaxPositionForChildDisplayArea - i2) + 1;
        } else {
            findMaxPositionForChildDisplayArea -= i2;
        }
        return java.lang.Math.max(java.lang.Math.min(i, findMaxPositionForChildDisplayArea), findMinPositionForChildDisplayArea);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.wm.WindowContainer] */
    private int findMaxPositionForChildDisplayArea(com.android.server.wm.DisplayArea displayArea) {
        com.android.server.wm.DisplayArea.Type typeOf = com.android.server.wm.DisplayArea.Type.typeOf(displayArea);
        for (int size = this.mChildren.size() - 1; size > 0; size--) {
            if (com.android.server.wm.DisplayArea.Type.typeOf((com.android.server.wm.WindowContainer) getChildAt(size)) == typeOf) {
                return size;
            }
        }
        return 0;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.wm.WindowContainer] */
    private int findMinPositionForChildDisplayArea(com.android.server.wm.DisplayArea displayArea) {
        com.android.server.wm.DisplayArea.Type typeOf = com.android.server.wm.DisplayArea.Type.typeOf(displayArea);
        for (int i = 0; i < this.mChildren.size(); i++) {
            if (com.android.server.wm.DisplayArea.Type.typeOf((com.android.server.wm.WindowContainer) getChildAt(i)) == typeOf) {
                return i;
            }
        }
        return this.mChildren.size() - 1;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean needsZBoost() {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean fillsParent() {
        return true;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String toString() {
        return this.mName + "@" + java.lang.System.identityHashCode(this);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        if (i == 2 && !isVisible()) {
            return;
        }
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L, i);
        protoOutputStream.write(1138166333442L, this.mName);
        protoOutputStream.write(1133871366148L, isTaskDisplayArea());
        protoOutputStream.write(1133871366149L, asRootDisplayArea() != null);
        protoOutputStream.write(1120986464262L, this.mFeatureId);
        protoOutputStream.write(1133871366151L, isOrganized());
        protoOutputStream.write(1133871366152L, getIgnoreOrientationRequest());
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.WindowContainer
    void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        super.dump(printWriter, str, z);
        if (this.mSetIgnoreOrientationRequest) {
            printWriter.println(str + "mSetIgnoreOrientationRequest=true");
        }
        if (hasRequestedOverrideConfiguration()) {
            printWriter.println(str + "overrideConfig=" + getRequestedOverrideConfiguration());
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.wm.WindowContainer] */
    void dumpChildDisplayArea(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        java.lang.String str2 = str + "  ";
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.DisplayArea asDisplayArea = getChildAt(childCount).asDisplayArea();
            if (asDisplayArea != null) {
                printWriter.print(str + "* " + asDisplayArea.getName());
                if (asDisplayArea.isOrganized()) {
                    printWriter.print(" (organized)");
                }
                printWriter.println();
                if (!asDisplayArea.isTaskDisplayArea()) {
                    asDisplayArea.dump(printWriter, str2, z);
                    asDisplayArea.dumpChildDisplayArea(printWriter, str2, z);
                }
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    long getProtoFieldId() {
        return 1146756268036L;
    }

    @Override // com.android.server.wm.WindowContainer
    final com.android.server.wm.DisplayArea asDisplayArea() {
        return this;
    }

    com.android.server.wm.DisplayArea.Tokens asTokens() {
        return null;
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.ActivityRecord getActivity(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, boolean z, com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mType == com.android.server.wm.DisplayArea.Type.ABOVE_TASKS) {
            return null;
        }
        return super.getActivity(predicate, z, activityRecord);
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.Task getTask(java.util.function.Predicate<com.android.server.wm.Task> predicate, boolean z) {
        if (this.mType == com.android.server.wm.DisplayArea.Type.ABOVE_TASKS) {
            return null;
        }
        return super.getTask(predicate, z);
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.Task getRootTask(java.util.function.Predicate<com.android.server.wm.Task> predicate, boolean z) {
        if (this.mType == com.android.server.wm.DisplayArea.Type.ABOVE_TASKS) {
            return null;
        }
        return super.getRootTask(predicate, z);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllActivities(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, boolean z) {
        if (this.mType == com.android.server.wm.DisplayArea.Type.ABOVE_TASKS) {
            return false;
        }
        return super.forAllActivities(predicate, z);
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllActivities(java.util.function.Consumer<com.android.server.wm.ActivityRecord> consumer, boolean z) {
        if (this.mType == com.android.server.wm.DisplayArea.Type.ABOVE_TASKS) {
            return;
        }
        super.forAllActivities(consumer, z);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllRootTasks(java.util.function.Predicate<com.android.server.wm.Task> predicate, boolean z) {
        if (this.mType == com.android.server.wm.DisplayArea.Type.ABOVE_TASKS) {
            return false;
        }
        return super.forAllRootTasks(predicate, z);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllTasks(java.util.function.Predicate<com.android.server.wm.Task> predicate) {
        if (this.mType == com.android.server.wm.DisplayArea.Type.ABOVE_TASKS) {
            return false;
        }
        return super.forAllTasks(predicate);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllLeafTasks(java.util.function.Predicate<com.android.server.wm.Task> predicate) {
        if (this.mType == com.android.server.wm.DisplayArea.Type.ABOVE_TASKS) {
            return false;
        }
        return super.forAllLeafTasks(predicate);
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllLeafTasks(java.util.function.Consumer<com.android.server.wm.Task> consumer, boolean z) {
        if (this.mType == com.android.server.wm.DisplayArea.Type.ABOVE_TASKS) {
            return;
        }
        super.forAllLeafTasks(consumer, z);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllLeafTaskFragments(java.util.function.Predicate<com.android.server.wm.TaskFragment> predicate) {
        if (this.mType == com.android.server.wm.DisplayArea.Type.ABOVE_TASKS) {
            return false;
        }
        return super.forAllLeafTaskFragments(predicate);
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllDisplayAreas(java.util.function.Consumer<com.android.server.wm.DisplayArea> consumer) {
        super.forAllDisplayAreas(consumer);
        consumer.accept(this);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllTaskDisplayAreas(java.util.function.Predicate<com.android.server.wm.TaskDisplayArea> predicate, boolean z) {
        if (this.mType != com.android.server.wm.DisplayArea.Type.ANY) {
            return false;
        }
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(i);
            int i2 = 1;
            if (windowContainer.asDisplayArea() != null && windowContainer.asDisplayArea().forAllTaskDisplayAreas(predicate, z)) {
                return true;
            }
            if (z) {
                i2 = -1;
            }
            i += i2;
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllTaskDisplayAreas(java.util.function.Consumer<com.android.server.wm.TaskDisplayArea> consumer, boolean z) {
        if (this.mType != com.android.server.wm.DisplayArea.Type.ANY) {
            return;
        }
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(i);
            if (windowContainer.asDisplayArea() != null) {
                windowContainer.asDisplayArea().forAllTaskDisplayAreas(consumer, z);
            }
            i += z ? -1 : 1;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    @android.annotation.Nullable
    <R> R reduceOnAllTaskDisplayAreas(java.util.function.BiFunction<com.android.server.wm.TaskDisplayArea, R, R> biFunction, @android.annotation.Nullable R r, boolean z) {
        if (this.mType != com.android.server.wm.DisplayArea.Type.ANY) {
            return r;
        }
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(i);
            if (windowContainer.asDisplayArea() != null) {
                r = (R) windowContainer.asDisplayArea().reduceOnAllTaskDisplayAreas(biFunction, r, z);
            }
            i += z ? -1 : 1;
        }
        return r;
    }

    @Override // com.android.server.wm.WindowContainer
    @android.annotation.Nullable
    <R> R getItemFromDisplayAreas(java.util.function.Function<com.android.server.wm.DisplayArea, R> function) {
        R r = (R) super.getItemFromDisplayAreas(function);
        return r != null ? r : function.apply(this);
    }

    @Override // com.android.server.wm.WindowContainer
    @android.annotation.Nullable
    <R> R getItemFromTaskDisplayAreas(java.util.function.Function<com.android.server.wm.TaskDisplayArea, R> function, boolean z) {
        R r;
        if (this.mType != com.android.server.wm.DisplayArea.Type.ANY) {
            return null;
        }
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(i);
            if (windowContainer.asDisplayArea() != null && (r = (R) windowContainer.asDisplayArea().getItemFromTaskDisplayAreas(function, z)) != null) {
                return r;
            }
            i += z ? -1 : 1;
        }
        return null;
    }

    void setOrganizer(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer) {
        setOrganizer(iDisplayAreaOrganizer, false);
    }

    void setOrganizer(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, boolean z) {
        if (this.mOrganizer == iDisplayAreaOrganizer) {
            return;
        }
        if (this.mDisplayContent == null || !this.mDisplayContent.isTrusted()) {
            throw new java.lang.IllegalStateException("Don't organize or trigger events for unavailable or untrusted display.");
        }
        android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer2 = this.mOrganizer;
        this.mOrganizer = iDisplayAreaOrganizer;
        sendDisplayAreaVanished(iDisplayAreaOrganizer2);
        if (!z) {
            sendDisplayAreaAppeared();
        } else if (iDisplayAreaOrganizer != null) {
            this.mDisplayAreaAppearedSent = true;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void sendDisplayAreaAppeared() {
        if (this.mOrganizer == null || this.mDisplayAreaAppearedSent) {
            return;
        }
        this.mOrganizerController.onDisplayAreaAppeared(this.mOrganizer, this);
        this.mDisplayAreaAppearedSent = true;
    }

    @com.android.internal.annotations.VisibleForTesting
    void sendDisplayAreaInfoChanged() {
        if (this.mOrganizer == null || !this.mDisplayAreaAppearedSent) {
            return;
        }
        this.mOrganizerController.onDisplayAreaInfoChanged(this.mOrganizer, this);
    }

    @com.android.internal.annotations.VisibleForTesting
    void sendDisplayAreaVanished(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer) {
        if (iDisplayAreaOrganizer == null || !this.mDisplayAreaAppearedSent) {
            return;
        }
        migrateToNewSurfaceControl(getSyncTransaction());
        this.mOrganizerController.onDisplayAreaVanished(iDisplayAreaOrganizer, this);
        this.mDisplayAreaAppearedSent = false;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        this.mTransitionController.collectForDisplayAreaChange(this);
        this.mTmpConfiguration.setTo(getConfiguration());
        super.onConfigurationChanged(configuration);
        if (this.mOrganizer != null && getConfiguration().diff(this.mTmpConfiguration) != 0) {
            sendDisplayAreaInfoChanged();
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    void resolveOverrideConfiguration(android.content.res.Configuration configuration) {
        super.resolveOverrideConfiguration(configuration);
        android.content.res.Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        android.graphics.Rect bounds = resolvedOverrideConfiguration.windowConfiguration.getBounds();
        android.graphics.Rect appBounds = resolvedOverrideConfiguration.windowConfiguration.getAppBounds();
        android.graphics.Rect appBounds2 = configuration.windowConfiguration.getAppBounds();
        if (!bounds.isEmpty()) {
            if ((appBounds == null || appBounds.isEmpty()) && appBounds2 != null && !appBounds2.isEmpty()) {
                android.graphics.Rect rect = new android.graphics.Rect(bounds);
                rect.intersect(appBounds2);
                resolvedOverrideConfiguration.windowConfiguration.setAppBounds(rect);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isOrganized() {
        return this.mOrganizer != null;
    }

    android.window.DisplayAreaInfo getDisplayAreaInfo() {
        android.window.DisplayAreaInfo displayAreaInfo = new android.window.DisplayAreaInfo(this.mRemoteToken.toWindowContainerToken(), getDisplayContent().getDisplayId(), this.mFeatureId);
        com.android.server.wm.DisplayArea rootDisplayArea = getRootDisplayArea();
        if (rootDisplayArea == null) {
            rootDisplayArea = getDisplayContent();
        }
        displayAreaInfo.rootDisplayAreaId = rootDisplayArea.mFeatureId;
        displayAreaInfo.configuration.setTo(getConfiguration());
        return displayAreaInfo;
    }

    void getStableRect(android.graphics.Rect rect) {
        if (this.mDisplayContent == null) {
            getBounds(rect);
        } else {
            this.mDisplayContent.getStableRect(rect);
            rect.intersect(getBounds());
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public boolean providesMaxBounds() {
        return true;
    }

    boolean isTaskDisplayArea() {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    void removeImmediately() {
        setOrganizer(null);
        super.removeImmediately();
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.DisplayArea getDisplayArea() {
        return this;
    }

    public static class Tokens extends com.android.server.wm.DisplayArea<com.android.server.wm.WindowToken> {
        private final java.util.function.Predicate<com.android.server.wm.WindowState> mGetOrientingWindow;
        int mLastKeyguardForcedOrientation;
        private final java.util.Comparator<com.android.server.wm.WindowToken> mWindowComparator;

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ void commitPendingTransaction() {
            super.commitPendingTransaction();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        public /* bridge */ /* synthetic */ int compareTo(com.android.server.wm.WindowContainer windowContainer) {
            return super.compareTo(windowContainer);
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ android.view.SurfaceControl getAnimationLeash() {
            return super.getAnimationLeash();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ android.view.SurfaceControl getAnimationLeashParent() {
            return super.getAnimationLeashParent();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceFreezer.Freezable
        public /* bridge */ /* synthetic */ android.view.SurfaceControl getFreezeSnapshotTarget() {
            return super.getFreezeSnapshotTarget();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        public /* bridge */ /* synthetic */ android.util.SparseArray getInsetsSourceProviders() {
            return super.getInsetsSourceProviders();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ android.view.SurfaceControl getParentSurfaceControl() {
            return super.getParentSurfaceControl();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ android.view.SurfaceControl.Transaction getPendingTransaction() {
            return super.getPendingTransaction();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ android.view.SurfaceControl getSurfaceControl() {
            return super.getSurfaceControl();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ int getSurfaceHeight() {
            return super.getSurfaceHeight();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ int getSurfaceWidth() {
            return super.getSurfaceWidth();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ android.view.SurfaceControl.Transaction getSyncTransaction() {
            return super.getSyncTransaction();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        public /* bridge */ /* synthetic */ boolean hasInsetsSourceProvider() {
            return super.hasInsetsSourceProvider();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ android.view.SurfaceControl.Builder makeAnimationLeash() {
            return super.makeAnimationLeash();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ void onAnimationLeashCreated(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
            super.onAnimationLeashCreated(transaction, surfaceControl);
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ void onAnimationLeashLost(android.view.SurfaceControl.Transaction transaction) {
            super.onAnimationLeashLost(transaction);
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
        public /* bridge */ /* synthetic */ void onRequestedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
            super.onRequestedOverrideConfigurationChanged(configuration);
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceFreezer.Freezable
        public /* bridge */ /* synthetic */ void onUnfrozen() {
            super.onUnfrozen();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$0(com.android.server.wm.WindowState windowState) {
            if (!windowState.isVisible() || !windowState.mLegacyPolicyVisibilityAfterAnim) {
                return false;
            }
            com.android.server.policy.WindowManagerPolicy windowManagerPolicy = this.mWmService.mPolicy;
            if (windowManagerPolicy.isKeyguardHostWindow(windowState.mAttrs)) {
                if (!this.mDisplayContent.isKeyguardLocked() && this.mDisplayContent.getDisplayPolicy().isAwake() && windowManagerPolicy.okToAnimate(true)) {
                    return false;
                }
                boolean z = this.mDisplayContent.mAppTransition.isUnoccluding() && this.mDisplayContent.mUnknownAppVisibilityController.allResolved();
                if (windowManagerPolicy.isKeyguardShowingAndNotOccluded() || z) {
                    return true;
                }
            }
            int i = windowState.mAttrs.screenOrientation;
            return (i == -1 || i == 3 || i == -2) ? false : true;
        }

        Tokens(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayArea.Type type, java.lang.String str) {
            this(windowManagerService, type, str, 2);
        }

        Tokens(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayArea.Type type, java.lang.String str, int i) {
            super(windowManagerService, type, str, i);
            this.mLastKeyguardForcedOrientation = -1;
            this.mWindowComparator = java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: com.android.server.wm.DisplayArea$Tokens$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    return ((com.android.server.wm.WindowToken) obj).getWindowLayerFromType();
                }
            });
            this.mGetOrientingWindow = new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayArea$Tokens$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$new$0;
                    lambda$new$0 = com.android.server.wm.DisplayArea.Tokens.this.lambda$new$0((com.android.server.wm.WindowState) obj);
                    return lambda$new$0;
                }
            };
        }

        void addChild(com.android.server.wm.WindowToken windowToken) {
            addChild((com.android.server.wm.DisplayArea.Tokens) windowToken, (java.util.Comparator<com.android.server.wm.DisplayArea.Tokens>) this.mWindowComparator);
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        int getOrientation(int i) {
            this.mLastOrientationSource = null;
            com.android.server.wm.WindowState window = getWindow(this.mGetOrientingWindow);
            if (window == null) {
                return i;
            }
            int i2 = window.mAttrs.screenOrientation;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 2230151187668089583L, 20, null, java.lang.String.valueOf(window), java.lang.Long.valueOf(i2), java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
            if (this.mWmService.mPolicy.isKeyguardHostWindow(window.mAttrs)) {
                if (i2 != -2 && i2 != -1) {
                    this.mLastKeyguardForcedOrientation = i2;
                } else {
                    i2 = this.mLastKeyguardForcedOrientation;
                }
            }
            this.mLastOrientationSource = window;
            return i2;
        }

        @Override // com.android.server.wm.DisplayArea
        final com.android.server.wm.DisplayArea.Tokens asTokens() {
            return this;
        }
    }

    static class Dimmable extends com.android.server.wm.DisplayArea<com.android.server.wm.DisplayArea> {
        private final com.android.server.wm.Dimmer mDimmer;

        Dimmable(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayArea.Type type, java.lang.String str, int i) {
            super(windowManagerService, type, str, i);
            this.mDimmer = com.android.server.wm.Dimmer.create(this);
        }

        @Override // com.android.server.wm.WindowContainer
        com.android.server.wm.Dimmer getDimmer() {
            return this.mDimmer;
        }

        @Override // com.android.server.wm.WindowContainer
        void prepareSurfaces() {
            this.mDimmer.resetDimStates();
            super.prepareSurfaces();
            android.graphics.Rect dimBounds = this.mDimmer.getDimBounds();
            if (dimBounds != null) {
                getBounds(dimBounds);
                dimBounds.offsetTo(0, 0);
            }
            if (!this.mTransitionController.isShellTransitionsEnabled() && forAllTasks(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayArea$Dimmable$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$prepareSurfaces$0;
                    lambda$prepareSurfaces$0 = com.android.server.wm.DisplayArea.Dimmable.lambda$prepareSurfaces$0((com.android.server.wm.Task) obj);
                    return lambda$prepareSurfaces$0;
                }
            })) {
                this.mDimmer.resetDimStates();
            }
            if (dimBounds != null && this.mDimmer.updateDims(getSyncTransaction())) {
                scheduleAnimation();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$prepareSurfaces$0(com.android.server.wm.Task task) {
            return !task.canAffectSystemUiFlags();
        }
    }

    enum Type {
        ABOVE_TASKS,
        BELOW_TASKS,
        ANY;

        static void checkSiblings(com.android.server.wm.DisplayArea.Type type, com.android.server.wm.DisplayArea.Type type2) {
            boolean z = true;
            com.android.internal.util.Preconditions.checkState(type == BELOW_TASKS || type2 != BELOW_TASKS, type + " must be above BELOW_TASKS");
            if (type == ABOVE_TASKS && type2 != ABOVE_TASKS) {
                z = false;
            }
            com.android.internal.util.Preconditions.checkState(z, type2 + " must be below ABOVE_TASKS");
        }

        static void checkChild(com.android.server.wm.DisplayArea.Type type, com.android.server.wm.DisplayArea.Type type2) {
            switch (com.android.server.wm.DisplayArea.AnonymousClass1.$SwitchMap$com$android$server$wm$DisplayArea$Type[type.ordinal()]) {
                case 1:
                    com.android.internal.util.Preconditions.checkState(type2 == ABOVE_TASKS, "ABOVE_TASKS can only contain ABOVE_TASKS");
                    break;
                case 2:
                    com.android.internal.util.Preconditions.checkState(type2 == BELOW_TASKS, "BELOW_TASKS can only contain BELOW_TASKS");
                    break;
            }
        }

        static com.android.server.wm.DisplayArea.Type typeOf(com.android.server.wm.WindowContainer windowContainer) {
            if (windowContainer.asDisplayArea() != null) {
                return ((com.android.server.wm.DisplayArea) windowContainer).mType;
            }
            if ((windowContainer instanceof com.android.server.wm.WindowToken) && !(windowContainer instanceof com.android.server.wm.ActivityRecord)) {
                return typeOf((com.android.server.wm.WindowToken) windowContainer);
            }
            if (windowContainer instanceof com.android.server.wm.Task) {
                return ANY;
            }
            throw new java.lang.IllegalArgumentException("Unknown container: " + windowContainer);
        }

        private static com.android.server.wm.DisplayArea.Type typeOf(com.android.server.wm.WindowToken windowToken) {
            return windowToken.getWindowLayerFromType() < 2 ? BELOW_TASKS : ABOVE_TASKS;
        }
    }

    /* renamed from: com.android.server.wm.DisplayArea$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$server$wm$DisplayArea$Type = new int[com.android.server.wm.DisplayArea.Type.values().length];

        static {
            try {
                $SwitchMap$com$android$server$wm$DisplayArea$Type[com.android.server.wm.DisplayArea.Type.ABOVE_TASKS.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$server$wm$DisplayArea$Type[com.android.server.wm.DisplayArea.Type.BELOW_TASKS.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
        }
    }
}
