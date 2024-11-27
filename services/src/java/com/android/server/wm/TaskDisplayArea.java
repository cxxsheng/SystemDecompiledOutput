package com.android.server.wm;

/* loaded from: classes3.dex */
final class TaskDisplayArea extends com.android.server.wm.DisplayArea<com.android.server.wm.WindowContainer> {
    private com.android.server.wm.ActivityTaskManagerService mAtmService;
    private int mBackgroundColor;
    private final boolean mCanHostHomeTask;
    private int mColorLayerCounter;
    final boolean mCreatedByOrganizer;
    com.android.server.wm.DisplayContent mDisplayContent;
    com.android.server.wm.Task mLastFocusedRootTask;
    private int mLastLeafTaskToFrontId;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.Task mLaunchAdjacentFlagRootTask;
    private final java.util.ArrayList<com.android.server.wm.TaskDisplayArea.LaunchRootTaskDef> mLaunchRootTasks;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.Task mPreferredTopFocusableRootTask;
    private boolean mRemoved;
    private com.android.server.wm.Task mRootHomeTask;
    private com.android.server.wm.Task mRootPinnedTask;
    private java.util.ArrayList<com.android.server.wm.TaskDisplayArea.OnRootTaskOrderChangedListener> mRootTaskOrderChangedCallbacks;
    private com.android.server.wm.RootWindowContainer mRootWindowContainer;
    private final android.content.res.Configuration mTempConfiguration;
    private final java.util.ArrayList<com.android.server.wm.WindowContainer> mTmpAlwaysOnTopChildren;
    private final java.util.ArrayList<com.android.server.wm.WindowContainer> mTmpHomeChildren;
    private final android.util.IntArray mTmpNeedsZBoostIndexes;
    private final java.util.ArrayList<com.android.server.wm.WindowContainer> mTmpNormalChildren;
    private java.util.ArrayList<com.android.server.wm.Task> mTmpTasks;

    interface OnRootTaskOrderChangedListener {
        void onRootTaskOrderChanged(com.android.server.wm.Task task);
    }

    private static class LaunchRootTaskDef {
        int[] activityTypes;
        com.android.server.wm.Task task;
        int[] windowingModes;

        private LaunchRootTaskDef() {
        }

        boolean contains(int i, int i2) {
            return com.android.internal.util.ArrayUtils.contains(this.windowingModes, i) && com.android.internal.util.ArrayUtils.contains(this.activityTypes, i2);
        }
    }

    TaskDisplayArea(com.android.server.wm.DisplayContent displayContent, com.android.server.wm.WindowManagerService windowManagerService, java.lang.String str, int i) {
        this(displayContent, windowManagerService, str, i, false, true);
    }

    TaskDisplayArea(com.android.server.wm.DisplayContent displayContent, com.android.server.wm.WindowManagerService windowManagerService, java.lang.String str, int i, boolean z) {
        this(displayContent, windowManagerService, str, i, z, true);
    }

    TaskDisplayArea(com.android.server.wm.DisplayContent displayContent, com.android.server.wm.WindowManagerService windowManagerService, java.lang.String str, int i, boolean z, boolean z2) {
        super(windowManagerService, com.android.server.wm.DisplayArea.Type.ANY, str, i);
        this.mBackgroundColor = 0;
        this.mColorLayerCounter = 0;
        this.mTmpAlwaysOnTopChildren = new java.util.ArrayList<>();
        this.mTmpNormalChildren = new java.util.ArrayList<>();
        this.mTmpHomeChildren = new java.util.ArrayList<>();
        this.mTmpNeedsZBoostIndexes = new android.util.IntArray();
        this.mTmpTasks = new java.util.ArrayList<>();
        this.mLaunchRootTasks = new java.util.ArrayList<>();
        this.mRootTaskOrderChangedCallbacks = new java.util.ArrayList<>();
        this.mTempConfiguration = new android.content.res.Configuration();
        this.mDisplayContent = displayContent;
        this.mRootWindowContainer = windowManagerService.mRoot;
        this.mAtmService = windowManagerService.mAtmService;
        this.mCreatedByOrganizer = z;
        this.mCanHostHomeTask = z2;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getRootTask(final int i, final int i2) {
        if (i2 == 2) {
            return this.mRootHomeTask;
        }
        if (i == 2) {
            return this.mRootPinnedTask;
        }
        return getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getRootTask$0;
                lambda$getRootTask$0 = com.android.server.wm.TaskDisplayArea.lambda$getRootTask$0(i2, i, (com.android.server.wm.Task) obj);
                return lambda$getRootTask$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getRootTask$0(int i, int i2, com.android.server.wm.Task task) {
        if (i == 0 && i2 == task.getWindowingMode()) {
            return true;
        }
        return task.isCompatible(i2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopRootTask$1(com.android.server.wm.Task task) {
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.Task getTopRootTask() {
        return getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopRootTask$1;
                lambda$getTopRootTask$1 = com.android.server.wm.TaskDisplayArea.lambda$getTopRootTask$1((com.android.server.wm.Task) obj);
                return lambda$getTopRootTask$1;
            }
        });
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getRootHomeTask() {
        return this.mRootHomeTask;
    }

    com.android.server.wm.Task getRootPinnedTask() {
        return this.mRootPinnedTask;
    }

    java.util.ArrayList<com.android.server.wm.Task> getVisibleTasks() {
        final java.util.ArrayList<com.android.server.wm.Task> arrayList = new java.util.ArrayList<>();
        forAllTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TaskDisplayArea.lambda$getVisibleTasks$2(arrayList, (com.android.server.wm.Task) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getVisibleTasks$2(java.util.ArrayList arrayList, com.android.server.wm.Task task) {
        if (task.isLeafTask() && task.isVisible()) {
            arrayList.add(task);
        }
    }

    void onRootTaskWindowingModeChanged(com.android.server.wm.Task task) {
        removeRootTaskReferenceIfNeeded(task);
        addRootTaskReferenceIfNeeded(task);
        if (task == this.mRootPinnedTask && getTopRootTask() != task) {
            positionChildAt(Integer.MAX_VALUE, task, false);
        }
    }

    void addRootTaskReferenceIfNeeded(com.android.server.wm.Task task) {
        if (task.isActivityTypeHome()) {
            if (this.mRootHomeTask != null) {
                if (!task.isDescendantOf(this.mRootHomeTask)) {
                    throw new java.lang.IllegalArgumentException("addRootTaskReferenceIfNeeded: root home task=" + this.mRootHomeTask + " already exist on display=" + this + " rootTask=" + task);
                }
            } else {
                this.mRootHomeTask = task;
            }
        }
        if (task.isRootTask() && task.getWindowingMode() == 2) {
            if (this.mRootPinnedTask != null) {
                throw new java.lang.IllegalArgumentException("addRootTaskReferenceIfNeeded: root pinned task=" + this.mRootPinnedTask + " already exist on display=" + this + " rootTask=" + task);
            }
            this.mRootPinnedTask = task;
        }
    }

    void removeRootTaskReferenceIfNeeded(com.android.server.wm.Task task) {
        if (task == this.mRootHomeTask) {
            this.mRootHomeTask = null;
        } else if (task == this.mRootPinnedTask) {
            this.mRootPinnedTask = null;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void setInitialSurfaceControlProperties(android.view.SurfaceControl.Builder builder) {
        builder.setEffectLayer();
        super.setInitialSurfaceControlProperties(builder);
    }

    @Override // com.android.server.wm.WindowContainer
    void addChild(com.android.server.wm.WindowContainer windowContainer, int i) {
        if (windowContainer.asTaskDisplayArea() != null) {
            super.addChild((com.android.server.wm.TaskDisplayArea) windowContainer, i);
        } else {
            if (windowContainer.asTask() != null) {
                addChildTask(windowContainer.asTask(), i);
                return;
            }
            throw new java.lang.IllegalArgumentException("TaskDisplayArea can only add Task and TaskDisplayArea, but found " + windowContainer);
        }
    }

    private void addChildTask(com.android.server.wm.Task task, int i) {
        addRootTaskReferenceIfNeeded(task);
        super.addChild((com.android.server.wm.TaskDisplayArea) task, findPositionForRootTask(i, task, true));
        if (this.mPreferredTopFocusableRootTask != null && task.isFocusable() && this.mPreferredTopFocusableRootTask.compareTo((com.android.server.wm.WindowContainer) task) < 0) {
            this.mPreferredTopFocusableRootTask = null;
        }
        this.mAtmService.mTaskSupervisor.updateTopResumedActivityIfNeeded("addChildTask");
        this.mAtmService.updateSleepIfNeededLocked();
        onRootTaskOrderChanged(task);
    }

    @Override // com.android.server.wm.WindowContainer
    protected void removeChild(com.android.server.wm.WindowContainer windowContainer) {
        if (windowContainer.asTaskDisplayArea() != null) {
            super.removeChild(windowContainer);
        } else {
            if (windowContainer.asTask() != null) {
                removeChildTask(windowContainer.asTask());
                return;
            }
            throw new java.lang.IllegalArgumentException("TaskDisplayArea can only remove Task and TaskDisplayArea, but found " + windowContainer);
        }
    }

    private void removeChildTask(com.android.server.wm.Task task) {
        super.removeChild(task);
        onRootTaskRemoved(task);
        this.mAtmService.updateSleepIfNeededLocked();
        removeRootTaskReferenceIfNeeded(task);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isOnTop() {
        return true;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    void positionChildAt(int i, com.android.server.wm.WindowContainer windowContainer, boolean z) {
        if (windowContainer.asTaskDisplayArea() != null) {
            super.positionChildAt(i, windowContainer, z);
        } else {
            if (windowContainer.asTask() != null) {
                positionChildTaskAt(i, windowContainer.asTask(), z);
                return;
            }
            throw new java.lang.IllegalArgumentException("TaskDisplayArea can only position Task and TaskDisplayArea, but found " + windowContainer);
        }
    }

    private void positionChildTaskAt(int i, com.android.server.wm.Task task, boolean z) {
        boolean z2 = i >= getChildCount() - 1;
        boolean z3 = i <= 0;
        int indexOf = this.mChildren.indexOf(task);
        if (task.isAlwaysOnTop() && !z2) {
            android.util.Slog.w("WindowManager", "Ignoring move of always-on-top root task=" + this + " to bottom");
            super.positionChildAt(indexOf, task, false);
            return;
        }
        if ((!this.mDisplayContent.isTrusted() || this.mDisplayContent.mDontMoveToTop) && !getParent().isOnTop()) {
            z = false;
        }
        int findPositionForRootTask = findPositionForRootTask(i, task, false);
        super.positionChildAt(findPositionForRootTask, task, false);
        if (z && getParent() != null && (z2 || z3)) {
            getParent().positionChildAt(z2 ? Integer.MAX_VALUE : Integer.MIN_VALUE, this, true);
        }
        task.updateTaskMovement(z2, z3, findPositionForRootTask);
        if (z2 && task != this.mRootPinnedTask && task.isTopActivityFocusable()) {
            this.mPreferredTopFocusableRootTask = task.shouldBeVisible(null) ? task : null;
        } else if (this.mPreferredTopFocusableRootTask == task) {
            this.mPreferredTopFocusableRootTask = null;
        }
        this.mAtmService.mTaskSupervisor.updateTopResumedActivityIfNeeded("positionChildTaskAt");
        if (this.mChildren.indexOf(task) != indexOf) {
            onRootTaskOrderChanged(task);
        }
    }

    void onLeafTaskRemoved(int i) {
        if (this.mLastLeafTaskToFrontId == i) {
            this.mLastLeafTaskToFrontId = -1;
        }
    }

    void onLeafTaskMoved(com.android.server.wm.Task task, boolean z, boolean z2) {
        if (z2) {
            this.mAtmService.getTaskChangeNotificationController().notifyTaskMovedToBack(task.getTaskInfo());
        }
        if (!z) {
            if (task.mTaskId == this.mLastLeafTaskToFrontId) {
                this.mLastLeafTaskToFrontId = -1;
                com.android.server.wm.ActivityRecord topMostActivity = getTopMostActivity();
                if (topMostActivity != null) {
                    this.mAtmService.getTaskChangeNotificationController().notifyTaskMovedToFront(topMostActivity.getTask().getTaskInfo());
                    return;
                }
                return;
            }
            return;
        }
        if (task.mTaskId == this.mLastLeafTaskToFrontId || task.topRunningActivityLocked() == null) {
            return;
        }
        this.mLastLeafTaskToFrontId = task.mTaskId;
        com.android.server.wm.EventLogTags.writeWmTaskToFront(task.mUserId, task.mTaskId, getDisplayId());
        this.mAtmService.getTaskChangeNotificationController().notifyTaskMovedToFront(task.getTaskInfo());
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    void onChildPositionChanged(com.android.server.wm.WindowContainer windowContainer) {
        super.onChildPositionChanged(windowContainer);
        this.mRootWindowContainer.invalidateTaskLayers();
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    boolean forAllTaskDisplayAreas(java.util.function.Predicate<com.android.server.wm.TaskDisplayArea> predicate, boolean z) {
        return z ? super.forAllTaskDisplayAreas(predicate, z) || predicate.test(this) : predicate.test(this) || super.forAllTaskDisplayAreas(predicate, z);
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    void forAllTaskDisplayAreas(java.util.function.Consumer<com.android.server.wm.TaskDisplayArea> consumer, boolean z) {
        if (z) {
            super.forAllTaskDisplayAreas(consumer, z);
            consumer.accept(this);
        } else {
            consumer.accept(this);
            super.forAllTaskDisplayAreas(consumer, z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    @android.annotation.Nullable
    <R> R reduceOnAllTaskDisplayAreas(java.util.function.BiFunction<com.android.server.wm.TaskDisplayArea, R, R> biFunction, @android.annotation.Nullable R r, boolean z) {
        if (z) {
            return (R) biFunction.apply(this, super.reduceOnAllTaskDisplayAreas(biFunction, r, z));
        }
        return (R) super.reduceOnAllTaskDisplayAreas(biFunction, biFunction.apply(this, r), z);
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    @android.annotation.Nullable
    <R> R getItemFromTaskDisplayAreas(java.util.function.Function<com.android.server.wm.TaskDisplayArea, R> function, boolean z) {
        if (z) {
            R r = (R) super.getItemFromTaskDisplayAreas(function, z);
            return r != null ? r : function.apply(this);
        }
        R apply = function.apply(this);
        if (apply != null) {
            return apply;
        }
        return (R) super.getItemFromTaskDisplayAreas(function, z);
    }

    private int getPriority(com.android.server.wm.WindowContainer windowContainer) {
        com.android.server.wm.TaskDisplayArea asTaskDisplayArea = windowContainer.asTaskDisplayArea();
        if (asTaskDisplayArea != null) {
            return asTaskDisplayArea.getPriority(asTaskDisplayArea.getTopChild());
        }
        com.android.server.wm.Task asTask = windowContainer.asTask();
        if (this.mWmService.mAssistantOnTopOfDream && asTask.isActivityTypeAssistant()) {
            return 4;
        }
        if (asTask.isActivityTypeDream()) {
            return 3;
        }
        if (asTask.inPinnedWindowingMode()) {
            return 2;
        }
        return asTask.isAlwaysOnTop() ? 1 : 0;
    }

    private int findMinPositionForRootTask(com.android.server.wm.Task task) {
        int i;
        int indexOf;
        int i2 = Integer.MIN_VALUE;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            i = i2;
            i2 = i4;
            if (i2 >= this.mChildren.size() || getPriority((com.android.server.wm.WindowContainer) this.mChildren.get(i2)) >= getPriority(task)) {
                break;
            }
            i3 = i2 + 1;
        }
        if (task.isAlwaysOnTop() && (indexOf = this.mChildren.indexOf(task)) > i) {
            return indexOf;
        }
        return i;
    }

    private int findMaxPositionForRootTask(com.android.server.wm.Task task) {
        int size = this.mChildren.size() - 1;
        while (true) {
            if (size < 0) {
                return 0;
            }
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(size);
            boolean z = windowContainer == task;
            if (getPriority(windowContainer) > getPriority(task) || z) {
                size--;
            } else {
                return size;
            }
        }
    }

    private int findPositionForRootTask(int i, com.android.server.wm.Task task, boolean z) {
        int findMaxPositionForRootTask = findMaxPositionForRootTask(task);
        int findMinPositionForRootTask = findMinPositionForRootTask(task);
        if (i == Integer.MAX_VALUE) {
            i = this.mChildren.size();
        } else if (i == Integer.MIN_VALUE) {
            i = 0;
        }
        int max = java.lang.Math.max(java.lang.Math.min(i, findMaxPositionForRootTask), findMinPositionForRootTask);
        int indexOf = this.mChildren.indexOf(task);
        if (max == i) {
            return max;
        }
        if (z || max < indexOf) {
            return max + 1;
        }
        return max;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    int getOrientation(final int i) {
        int orientation = super.getOrientation(i);
        if (!canSpecifyOrientation(orientation)) {
            this.mLastOrientationSource = null;
            return ((java.lang.Integer) reduceOnAllTaskDisplayAreas(new java.util.function.BiFunction() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda5
                @Override // java.util.function.BiFunction
                public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                    java.lang.Integer lambda$getOrientation$3;
                    lambda$getOrientation$3 = com.android.server.wm.TaskDisplayArea.this.lambda$getOrientation$3(i, (com.android.server.wm.TaskDisplayArea) obj, (java.lang.Integer) obj2);
                    return lambda$getOrientation$3;
                }
            }, -2)).intValue();
        }
        if (orientation != -2 && orientation != 3) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 7378236902389922467L, 5, null, java.lang.Long.valueOf(orientation), java.lang.Long.valueOf(this.mDisplayContent.mDisplayId));
            return orientation;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 2005499548343677845L, 5, null, java.lang.Long.valueOf(this.mDisplayContent.getLastOrientation()), java.lang.Long.valueOf(this.mDisplayContent.mDisplayId));
        return this.mDisplayContent.getLastOrientation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$getOrientation$3(int i, com.android.server.wm.TaskDisplayArea taskDisplayArea, java.lang.Integer num) {
        if (taskDisplayArea == this || num.intValue() != -2) {
            return num;
        }
        return java.lang.Integer.valueOf(taskDisplayArea.getOrientation(i));
    }

    @Override // com.android.server.wm.WindowContainer
    void assignChildLayers(android.view.SurfaceControl.Transaction transaction) {
        assignRootTaskOrdering(transaction);
        for (int i = 0; i < this.mChildren.size(); i++) {
            ((com.android.server.wm.WindowContainer) this.mChildren.get(i)).assignChildLayers(transaction);
        }
    }

    void assignRootTaskOrdering(android.view.SurfaceControl.Transaction transaction) {
        if (getParent() == null) {
            return;
        }
        this.mTmpAlwaysOnTopChildren.clear();
        this.mTmpHomeChildren.clear();
        this.mTmpNormalChildren.clear();
        for (int i = 0; i < this.mChildren.size(); i++) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(i);
            com.android.server.wm.TaskDisplayArea asTaskDisplayArea = windowContainer.asTaskDisplayArea();
            if (asTaskDisplayArea != null) {
                com.android.server.wm.Task topRootTask = asTaskDisplayArea.getTopRootTask();
                if (topRootTask == null) {
                    this.mTmpNormalChildren.add(asTaskDisplayArea);
                } else if (topRootTask.isAlwaysOnTop()) {
                    this.mTmpAlwaysOnTopChildren.add(asTaskDisplayArea);
                } else if (topRootTask.isActivityTypeHome()) {
                    this.mTmpHomeChildren.add(asTaskDisplayArea);
                } else {
                    this.mTmpNormalChildren.add(asTaskDisplayArea);
                }
            } else {
                com.android.server.wm.Task asTask = windowContainer.asTask();
                if (asTask.isAlwaysOnTop()) {
                    this.mTmpAlwaysOnTopChildren.add(asTask);
                } else if (asTask.isActivityTypeHome()) {
                    this.mTmpHomeChildren.add(asTask);
                } else {
                    this.mTmpNormalChildren.add(asTask);
                }
            }
        }
        adjustRootTaskLayer(transaction, this.mTmpAlwaysOnTopChildren, adjustRootTaskLayer(transaction, this.mTmpNormalChildren, adjustRootTaskLayer(transaction, this.mTmpHomeChildren, 0)));
    }

    private int adjustRootTaskLayer(android.view.SurfaceControl.Transaction transaction, java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList, int i) {
        boolean needsZBoost;
        this.mTmpNeedsZBoostIndexes.clear();
        int size = arrayList.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.wm.WindowContainer windowContainer = arrayList.get(i3);
            com.android.server.wm.TaskDisplayArea asTaskDisplayArea = windowContainer.asTaskDisplayArea();
            if (asTaskDisplayArea != null) {
                needsZBoost = asTaskDisplayArea.childrenNeedZBoost();
            } else {
                needsZBoost = windowContainer.needsZBoost();
            }
            if (needsZBoost) {
                this.mTmpNeedsZBoostIndexes.add(i3);
            } else {
                windowContainer.assignLayer(transaction, i);
                i++;
            }
        }
        int size2 = this.mTmpNeedsZBoostIndexes.size();
        while (i2 < size2) {
            arrayList.get(this.mTmpNeedsZBoostIndexes.get(i2)).assignLayer(transaction, i);
            i2++;
            i++;
        }
        return i;
    }

    private boolean childrenNeedZBoost() {
        final boolean[] zArr = new boolean[1];
        forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TaskDisplayArea.lambda$childrenNeedZBoost$4(zArr, (com.android.server.wm.Task) obj);
            }
        });
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$childrenNeedZBoost$4(boolean[] zArr, com.android.server.wm.Task task) {
        zArr[0] = task.needsZBoost() | zArr[0];
    }

    @Override // com.android.server.wm.WindowContainer
    android.view.RemoteAnimationTarget createRemoteAnimationTarget(com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        com.android.server.wm.ActivityRecord topMostActivity = getTopMostActivity();
        if (topMostActivity != null) {
            return topMostActivity.createRemoteAnimationTarget(remoteAnimationRecord);
        }
        return null;
    }

    void setBackgroundColor(int i) {
        setBackgroundColor(i, false);
    }

    void setBackgroundColor(int i, boolean z) {
        this.mBackgroundColor = i;
        android.graphics.Color valueOf = android.graphics.Color.valueOf(i);
        if (!z) {
            this.mColorLayerCounter++;
        }
        if (this.mSurfaceControl != null) {
            getPendingTransaction().setColor(this.mSurfaceControl, new float[]{valueOf.red(), valueOf.green(), valueOf.blue()});
            scheduleAnimation();
        }
    }

    void clearBackgroundColor() {
        this.mColorLayerCounter--;
        if (this.mColorLayerCounter == 0 && this.mSurfaceControl != null) {
            getPendingTransaction().unsetColor(this.mSurfaceControl);
            scheduleAnimation();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void migrateToNewSurfaceControl(android.view.SurfaceControl.Transaction transaction) {
        super.migrateToNewSurfaceControl(transaction);
        if (this.mColorLayerCounter > 0) {
            setBackgroundColor(this.mBackgroundColor, true);
        }
        reassignLayer(transaction);
        scheduleAnimation();
    }

    void onRootTaskRemoved(com.android.server.wm.Task task) {
        if (this.mPreferredTopFocusableRootTask == task) {
            this.mPreferredTopFocusableRootTask = null;
        }
        if (this.mLaunchAdjacentFlagRootTask == task) {
            this.mLaunchAdjacentFlagRootTask = null;
        }
        this.mDisplayContent.releaseSelfIfNeeded();
        onRootTaskOrderChanged(task);
    }

    void positionTaskBehindHome(com.android.server.wm.Task task) {
        com.android.server.wm.WindowContainer parent = getOrCreateRootHomeTask().getParent();
        com.android.server.wm.Task asTask = parent != null ? parent.asTask() : null;
        if (asTask == null) {
            if (task.getParent() == this) {
                positionChildAt(Integer.MIN_VALUE, task, false);
                return;
            } else {
                task.reparent(this, false);
                return;
            }
        }
        if (asTask == task.getParent()) {
            asTask.positionChildAtBottom(task);
        } else {
            task.reparent(asTask, false, 2, false, false, "positionTaskBehindHome");
        }
    }

    com.android.server.wm.Task getOrCreateRootTask(int i, int i2, boolean z) {
        return getOrCreateRootTask(i, i2, z, null, null, null, 0);
    }

    com.android.server.wm.Task getOrCreateRootTask(int i, int i2, boolean z, @android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable com.android.server.wm.Task task2, @android.annotation.Nullable android.app.ActivityOptions activityOptions, int i3) {
        int windowingMode = i == 0 ? getWindowingMode() : i;
        if (!com.android.server.wm.DisplayContent.alwaysCreateRootTask(windowingMode, i2)) {
            com.android.server.wm.Task rootTask = getRootTask(windowingMode, i2);
            if (rootTask != null) {
                return rootTask;
            }
        } else if (task != null) {
            int i4 = z ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            com.android.server.wm.Task launchRootTask = getLaunchRootTask(windowingMode, i2, activityOptions, task2, i3, task);
            if (launchRootTask != null) {
                if (task.getParent() == null) {
                    launchRootTask.addChild(task, i4);
                } else if (task.getParent() != launchRootTask) {
                    task.reparent(launchRootTask, i4);
                }
            } else if (task.getDisplayArea() != this || task.getRootTask().mReparentLeafTaskIfRelaunch) {
                if (task.getParent() == null) {
                    addChild(task, i4);
                } else {
                    task.reparent(this, z);
                }
            }
            if (i != 0 && task.isRootTask() && task.getWindowingMode() != i) {
                task.mTransitionController.collect(task);
                task.setWindowingMode(i);
            }
            return task.getRootTask();
        }
        return new com.android.server.wm.Task.Builder(this.mAtmService).setWindowingMode(i).setActivityType(i2).setOnTop(z).setParent(this).setSourceTask(task2).setActivityOptions(activityOptions).setLaunchFlags(i3).build();
    }

    com.android.server.wm.Task getOrCreateRootTask(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable com.android.server.wm.Task task2, @android.annotation.Nullable com.android.server.wm.LaunchParamsController.LaunchParams launchParams, int i, int i2, boolean z) {
        int i3;
        if (launchParams != null) {
            i3 = launchParams.mWindowingMode;
        } else if (activityOptions == null) {
            i3 = 0;
        } else {
            i3 = activityOptions.getLaunchWindowingMode();
        }
        return getOrCreateRootTask(validateWindowingMode(i3, activityRecord, task), i2, z, task, task2, activityOptions, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    int getNextRootTaskId() {
        return this.mAtmService.mTaskSupervisor.getNextTaskIdForUser();
    }

    com.android.server.wm.Task createRootTask(int i, int i2, boolean z) {
        return createRootTask(i, i2, z, null);
    }

    com.android.server.wm.Task createRootTask(int i, int i2, boolean z, android.app.ActivityOptions activityOptions) {
        return new com.android.server.wm.Task.Builder(this.mAtmService).setWindowingMode(i).setActivityType(i2).setParent(this).setOnTop(z).setActivityOptions(activityOptions).build();
    }

    void setLaunchRootTask(com.android.server.wm.Task task, int[] iArr, int[] iArr2) {
        if (!task.mCreatedByOrganizer) {
            throw new java.lang.IllegalArgumentException("Can't set not mCreatedByOrganizer as launch root tr=" + task);
        }
        com.android.server.wm.TaskDisplayArea.LaunchRootTaskDef launchRootTaskDef = getLaunchRootTaskDef(task);
        if (launchRootTaskDef != null) {
            this.mLaunchRootTasks.remove(launchRootTaskDef);
        } else {
            launchRootTaskDef = new com.android.server.wm.TaskDisplayArea.LaunchRootTaskDef();
            launchRootTaskDef.task = task;
        }
        launchRootTaskDef.activityTypes = iArr2;
        launchRootTaskDef.windowingModes = iArr;
        if (!com.android.internal.util.ArrayUtils.isEmpty(iArr) || !com.android.internal.util.ArrayUtils.isEmpty(iArr2)) {
            this.mLaunchRootTasks.add(launchRootTaskDef);
        }
    }

    void removeLaunchRootTask(com.android.server.wm.Task task) {
        com.android.server.wm.TaskDisplayArea.LaunchRootTaskDef launchRootTaskDef = getLaunchRootTaskDef(task);
        if (launchRootTaskDef != null) {
            this.mLaunchRootTasks.remove(launchRootTaskDef);
        }
    }

    void setLaunchAdjacentFlagRootTask(@android.annotation.Nullable com.android.server.wm.Task task) {
        if (task != null) {
            if (!task.mCreatedByOrganizer) {
                throw new java.lang.IllegalArgumentException("Can't set not mCreatedByOrganizer as launch adjacent flag root tr=" + task);
            }
            if (task.getAdjacentTaskFragment() == null) {
                throw new java.lang.UnsupportedOperationException("Can't set non-adjacent root as launch adjacent flag root tr=" + task);
            }
        }
        this.mLaunchAdjacentFlagRootTask = task;
    }

    @android.annotation.Nullable
    private com.android.server.wm.TaskDisplayArea.LaunchRootTaskDef getLaunchRootTaskDef(com.android.server.wm.Task task) {
        for (int size = this.mLaunchRootTasks.size() - 1; size >= 0; size--) {
            if (this.mLaunchRootTasks.get(size).task.mTaskId == task.mTaskId) {
                return this.mLaunchRootTasks.get(size);
            }
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getLaunchRootTask(int i, int i2, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.Task task, int i3) {
        return getLaunchRootTask(i, i2, activityOptions, task, i3, null);
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getLaunchRootTask(int i, int i2, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.Task task, int i3, @android.annotation.Nullable com.android.server.wm.Task task2) {
        com.android.server.wm.Task adjacentTask;
        com.android.server.wm.Task fromWindowContainerToken;
        if (activityOptions != null && (fromWindowContainerToken = com.android.server.wm.Task.fromWindowContainerToken(activityOptions.getLaunchRootTask())) != null && fromWindowContainerToken.mCreatedByOrganizer) {
            return fromWindowContainerToken;
        }
        if ((i3 & 4096) != 0 && this.mLaunchAdjacentFlagRootTask != null && (task == null || task != task2)) {
            if (task != null && this.mLaunchAdjacentFlagRootTask.getAdjacentTask() != null && (task == this.mLaunchAdjacentFlagRootTask || task.isDescendantOf(this.mLaunchAdjacentFlagRootTask))) {
                return this.mLaunchAdjacentFlagRootTask.getAdjacentTask();
            }
            return this.mLaunchAdjacentFlagRootTask;
        }
        int size = this.mLaunchRootTasks.size();
        do {
            size--;
            if (size < 0) {
                if (task == null || ((task2 != null && task2.getWindowingMode() == 2) || (adjacentTask = task.getAdjacentTask()) == null)) {
                    return null;
                }
                if (task2 != null && (task2 == adjacentTask || task2.isDescendantOf(adjacentTask))) {
                    return adjacentTask;
                }
                return task.getCreatedByOrganizerTask();
            }
        } while (!this.mLaunchRootTasks.get(size).contains(i, i2));
        com.android.server.wm.Task task3 = this.mLaunchRootTasks.get(size).task;
        com.android.server.wm.Task adjacentTask2 = task3 != null ? task3.getAdjacentTask() : null;
        if (task != null && adjacentTask2 != null && (task == adjacentTask2 || task.isDescendantOf(adjacentTask2))) {
            return adjacentTask2;
        }
        return task3;
    }

    com.android.server.wm.Task getFocusedRootTask() {
        if (this.mPreferredTopFocusableRootTask != null) {
            return this.mPreferredTopFocusableRootTask;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(size);
            if (windowContainer.asTaskDisplayArea() != null) {
                com.android.server.wm.Task focusedRootTask = windowContainer.asTaskDisplayArea().getFocusedRootTask();
                if (focusedRootTask != null) {
                    return focusedRootTask;
                }
            } else {
                com.android.server.wm.Task asTask = ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTask();
                if (asTask.isFocusableAndVisible()) {
                    return asTask;
                }
            }
        }
        return null;
    }

    com.android.server.wm.Task getNextFocusableRootTask(com.android.server.wm.Task task, boolean z) {
        if (task != null) {
            task.getWindowingMode();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(size);
            if (windowContainer.asTaskDisplayArea() != null) {
                com.android.server.wm.Task nextFocusableRootTask = windowContainer.asTaskDisplayArea().getNextFocusableRootTask(task, z);
                if (nextFocusableRootTask != null) {
                    return nextFocusableRootTask;
                }
            } else {
                com.android.server.wm.Task asTask = ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTask();
                if ((!z || asTask != task) && asTask.isFocusableAndVisible()) {
                    return asTask;
                }
            }
        }
        return null;
    }

    com.android.server.wm.ActivityRecord getFocusedActivity() {
        com.android.server.wm.Task focusedRootTask = getFocusedRootTask();
        if (focusedRootTask == null) {
            return null;
        }
        com.android.server.wm.ActivityRecord topResumedActivity = focusedRootTask.getTopResumedActivity();
        if (topResumedActivity == null || topResumedActivity.app == null) {
            com.android.server.wm.ActivityRecord topPausingActivity = focusedRootTask.getTopPausingActivity();
            if (topPausingActivity == null || topPausingActivity.app == null) {
                return focusedRootTask.topRunningActivity(true);
            }
            return topPausingActivity;
        }
        return topResumedActivity;
    }

    com.android.server.wm.Task getLastFocusedRootTask() {
        return this.mLastFocusedRootTask;
    }

    void updateLastFocusedRootTask(com.android.server.wm.Task task, java.lang.String str) {
        com.android.server.wm.Task focusedRootTask;
        if (str == null || (focusedRootTask = getFocusedRootTask()) == task) {
            return;
        }
        if (this.mDisplayContent.isSleeping() && focusedRootTask != null) {
            focusedRootTask.clearLastPausedActivity();
        }
        this.mLastFocusedRootTask = task;
        com.android.server.wm.EventLogTags.writeWmFocusedRootTask(this.mRootWindowContainer.mCurrentUser, this.mDisplayContent.mDisplayId, focusedRootTask == null ? -1 : focusedRootTask.getRootTaskId(), this.mLastFocusedRootTask != null ? this.mLastFocusedRootTask.getRootTaskId() : -1, str);
    }

    boolean allResumedActivitiesComplete() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(size);
            if (windowContainer.asTaskDisplayArea() != null) {
                if (!windowContainer.asTaskDisplayArea().allResumedActivitiesComplete()) {
                    return false;
                }
            } else {
                com.android.server.wm.ActivityRecord topResumedActivity = ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTask().getTopResumedActivity();
                if (topResumedActivity != null && !topResumedActivity.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
                    return false;
                }
            }
        }
        this.mLastFocusedRootTask = getFocusedRootTask();
        return true;
    }

    boolean pauseBackTasks(final com.android.server.wm.ActivityRecord activityRecord) {
        final int[] iArr = {0};
        forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TaskDisplayArea.lambda$pauseBackTasks$5(com.android.server.wm.ActivityRecord.this, iArr, (com.android.server.wm.Task) obj);
            }
        }, true);
        return iArr[0] > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pauseBackTasks$5(com.android.server.wm.ActivityRecord activityRecord, int[] iArr, com.android.server.wm.Task task) {
        if (task.pauseActivityIfNeeded(activityRecord, "pauseBackTasks")) {
            iArr[0] = iArr[0] + 1;
        }
    }

    static boolean isWindowingModeSupported(int i, boolean z, boolean z2, boolean z3) {
        if (i == 0 || i == 1) {
            return true;
        }
        if (!z) {
            return false;
        }
        if (i == 6) {
            return true;
        }
        if (!z2 && i == 5) {
            return false;
        }
        if (z3 || i != 2) {
            return true;
        }
        return false;
    }

    int resolveWindowingMode(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.Task task) {
        int launchWindowingMode = activityOptions != null ? activityOptions.getLaunchWindowingMode() : 0;
        if (launchWindowingMode == 0) {
            if (task != null) {
                launchWindowingMode = task.getWindowingMode();
            }
            if (launchWindowingMode == 0 && activityRecord != null) {
                launchWindowingMode = activityRecord.getWindowingMode();
            }
            if (launchWindowingMode == 0) {
                launchWindowingMode = getWindowingMode();
            }
        }
        int validateWindowingMode = validateWindowingMode(launchWindowingMode, activityRecord, task);
        if (validateWindowingMode != 0) {
            return validateWindowingMode;
        }
        return 1;
    }

    boolean isValidWindowingMode(int i, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.Task task) {
        boolean z = this.mAtmService.mSupportsMultiWindow;
        boolean z2 = this.mAtmService.mSupportsFreeformWindowManagement;
        boolean z3 = this.mAtmService.mSupportsPictureInPicture;
        if (z) {
            if (task != null) {
                z2 = task.supportsFreeformInDisplayArea(this);
                z = task.supportsMultiWindowInDisplayArea(this) || (i == 2 && z3);
            } else if (activityRecord != null) {
                z2 = activityRecord.supportsFreeformInDisplayArea(this);
                z3 = activityRecord.supportsPictureInPicture();
                z = activityRecord.supportsMultiWindowInDisplayArea(this);
            }
        }
        return i != 0 && isWindowingModeSupported(i, z, z2, z3);
    }

    int validateWindowingMode(int i, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.Task task) {
        if (!isValidWindowingMode(i, activityRecord, task)) {
            return 0;
        }
        return i;
    }

    boolean supportsNonResizableMultiWindow() {
        int i = this.mAtmService.mSupportsNonResizableMultiWindow;
        if (this.mAtmService.mDevEnableNonResizableMultiWindow || i == 1) {
            return true;
        }
        if (i == -1) {
            return false;
        }
        return isLargeEnoughForMultiWindow();
    }

    boolean supportsActivityMinWidthHeightMultiWindow(int i, int i2, @android.annotation.Nullable android.content.pm.ActivityInfo activityInfo) {
        int i3;
        if (activityInfo != null && !activityInfo.shouldCheckMinWidthHeightForMultiWindow()) {
            return true;
        }
        if ((i <= 0 && i2 <= 0) || (i3 = this.mAtmService.mRespectsActivityMinWidthHeightMultiWindow) == -1) {
            return true;
        }
        if (i3 == 0 && isLargeEnoughForMultiWindow()) {
            return true;
        }
        android.content.res.Configuration configuration = getConfiguration();
        return configuration.orientation == 2 ? i <= ((int) ((this.mAtmService.mMinPercentageMultiWindowSupportWidth * ((float) configuration.screenWidthDp)) * this.mDisplayContent.getDisplayMetrics().density)) : i2 <= ((int) ((this.mAtmService.mMinPercentageMultiWindowSupportHeight * ((float) configuration.screenHeightDp)) * this.mDisplayContent.getDisplayMetrics().density));
    }

    private boolean isLargeEnoughForMultiWindow() {
        return getConfiguration().smallestScreenWidthDp >= 600;
    }

    boolean isTopRootTask(com.android.server.wm.Task task) {
        return task == getTopRootTask();
    }

    com.android.server.wm.ActivityRecord topRunningActivity() {
        return topRunningActivity(false);
    }

    com.android.server.wm.ActivityRecord topRunningActivity(boolean z) {
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.Task focusedRootTask = getFocusedRootTask();
        if (focusedRootTask == null) {
            activityRecord = null;
        } else {
            activityRecord = focusedRootTask.topRunningActivity();
        }
        if (activityRecord == null) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(size);
                if (windowContainer.asTaskDisplayArea() != null) {
                    activityRecord = windowContainer.asTaskDisplayArea().topRunningActivity(z);
                    if (activityRecord != null) {
                        break;
                    }
                } else {
                    com.android.server.wm.Task asTask = ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTask();
                    if (asTask != focusedRootTask && asTask.isTopActivityFocusable() && (activityRecord = asTask.topRunningActivity()) != null) {
                        break;
                    }
                }
            }
        }
        if (activityRecord != null && z && this.mRootWindowContainer.mTaskSupervisor.getKeyguardController().isKeyguardLocked(activityRecord.getDisplayId()) && !activityRecord.canShowWhenLocked()) {
            return null;
        }
        return activityRecord;
    }

    protected int getRootTaskCount() {
        final int[] iArr = new int[1];
        forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TaskDisplayArea.lambda$getRootTaskCount$6(iArr, (com.android.server.wm.Task) obj);
            }
        });
        return iArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getRootTaskCount$6(int[] iArr, com.android.server.wm.Task task) {
        iArr[0] = iArr[0] + 1;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getOrCreateRootHomeTask() {
        return getOrCreateRootHomeTask(false);
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getOrCreateRootHomeTask(boolean z) {
        com.android.server.wm.Task rootHomeTask = getRootHomeTask();
        if (rootHomeTask == null && canHostHomeTask()) {
            return createRootTask(0, 2, z);
        }
        return rootHomeTask;
    }

    com.android.server.wm.Task getTopRootTaskInWindowingMode(int i) {
        return getRootTask(i, 0);
    }

    void moveHomeRootTaskToFront(java.lang.String str) {
        com.android.server.wm.Task orCreateRootHomeTask = getOrCreateRootHomeTask();
        if (orCreateRootHomeTask != null) {
            orCreateRootHomeTask.moveToFront(str);
        }
    }

    void moveHomeActivityToTop(java.lang.String str) {
        com.android.server.wm.ActivityRecord homeActivity = getHomeActivity();
        if (homeActivity == null) {
            moveHomeRootTaskToFront(str);
        } else {
            homeActivity.moveFocusableActivityToTop(str);
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getHomeActivity() {
        return getHomeActivityForUser(this.mRootWindowContainer.mCurrentUser);
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getHomeActivityForUser(int i) {
        com.android.server.wm.Task rootHomeTask = getRootHomeTask();
        if (rootHomeTask == null) {
            return null;
        }
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new java.util.function.BiPredicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda2
            @Override // java.util.function.BiPredicate
            public final boolean test(java.lang.Object obj, java.lang.Object obj2) {
                boolean isHomeActivityForUser;
                isHomeActivityForUser = com.android.server.wm.TaskDisplayArea.isHomeActivityForUser((com.android.server.wm.ActivityRecord) obj, ((java.lang.Integer) obj2).intValue());
                return isHomeActivityForUser;
            }
        }, com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), java.lang.Integer.valueOf(i));
        com.android.server.wm.ActivityRecord activity = rootHomeTask.getActivity(obtainPredicate);
        obtainPredicate.recycle();
        return activity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isHomeActivityForUser(com.android.server.wm.ActivityRecord activityRecord, int i) {
        return activityRecord.isActivityTypeHome() && (i == -1 || activityRecord.mUserId == i);
    }

    void moveRootTaskBehindBottomMostVisibleRootTask(com.android.server.wm.Task task) {
        com.android.server.wm.Task asTask;
        if (task.shouldBeVisible(null)) {
            return;
        }
        task.getParent().positionChildAt(Integer.MIN_VALUE, task, false);
        boolean isRootTask = task.isRootTask();
        int size = isRootTask ? this.mChildren.size() : task.getParent().getChildCount();
        for (int i = 0; i < size; i++) {
            if (isRootTask) {
                com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(i);
                if (windowContainer.asTaskDisplayArea() != null) {
                    asTask = windowContainer.asTaskDisplayArea().getBottomMostVisibleRootTask(task);
                } else {
                    asTask = windowContainer.asTask();
                }
            } else {
                asTask = task.getParent().getChildAt(i).asTask();
            }
            if (asTask != task && asTask != null) {
                boolean z = asTask.getWindowingMode() == 1;
                if (asTask.shouldBeVisible(null) && z) {
                    task.getParent().positionChildAt(java.lang.Math.max(0, i - 1), task, false);
                    return;
                }
            }
        }
    }

    @android.annotation.Nullable
    private com.android.server.wm.Task getBottomMostVisibleRootTask(com.android.server.wm.Task task) {
        return getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getBottomMostVisibleRootTask$7;
                lambda$getBottomMostVisibleRootTask$7 = com.android.server.wm.TaskDisplayArea.lambda$getBottomMostVisibleRootTask$7((com.android.server.wm.Task) obj);
                return lambda$getBottomMostVisibleRootTask$7;
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getBottomMostVisibleRootTask$7(com.android.server.wm.Task task) {
        return task.shouldBeVisible(null) && (task.getWindowingMode() == 1);
    }

    void moveRootTaskBehindRootTask(com.android.server.wm.Task task, com.android.server.wm.Task task2) {
        com.android.server.wm.WindowContainer parent;
        if (task2 == null || task2 == task || (parent = task.getParent()) == null || parent != task2.getParent()) {
            return;
        }
        int indexOf = parent.mChildren.indexOf(task);
        int indexOf2 = parent.mChildren.indexOf(task2);
        if (indexOf <= indexOf2) {
            indexOf2--;
        }
        parent.positionChildAt(java.lang.Math.max(0, indexOf2), task, false);
    }

    boolean hasPinnedTask() {
        return getRootPinnedTask() != null;
    }

    static com.android.server.wm.Task getRootTaskAbove(com.android.server.wm.Task task) {
        com.android.server.wm.WindowContainer parent = task.getParent();
        int indexOf = parent.mChildren.indexOf(task) + 1;
        if (indexOf < parent.mChildren.size()) {
            return (com.android.server.wm.Task) parent.mChildren.get(indexOf);
        }
        return null;
    }

    boolean isRootTaskVisible(int i) {
        com.android.server.wm.Task topRootTaskInWindowingMode = getTopRootTaskInWindowingMode(i);
        return topRootTaskInWindowingMode != null && topRootTaskInWindowingMode.isVisible();
    }

    void removeRootTask(com.android.server.wm.Task task) {
        removeChild(task);
    }

    int getDisplayId() {
        return this.mDisplayContent.getDisplayId();
    }

    boolean isRemoved() {
        return this.mRemoved;
    }

    void registerRootTaskOrderChangedListener(com.android.server.wm.TaskDisplayArea.OnRootTaskOrderChangedListener onRootTaskOrderChangedListener) {
        if (!this.mRootTaskOrderChangedCallbacks.contains(onRootTaskOrderChangedListener)) {
            this.mRootTaskOrderChangedCallbacks.add(onRootTaskOrderChangedListener);
        }
    }

    void unregisterRootTaskOrderChangedListener(com.android.server.wm.TaskDisplayArea.OnRootTaskOrderChangedListener onRootTaskOrderChangedListener) {
        this.mRootTaskOrderChangedCallbacks.remove(onRootTaskOrderChangedListener);
    }

    void onRootTaskOrderChanged(com.android.server.wm.Task task) {
        for (int size = this.mRootTaskOrderChangedCallbacks.size() - 1; size >= 0; size--) {
            this.mRootTaskOrderChangedCallbacks.get(size).onRootTaskOrderChanged(task);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean canCreateRemoteAnimationTarget() {
        return com.android.server.wm.WindowManagerService.sEnableShellTransitions;
    }

    boolean canHostHomeTask() {
        return this.mDisplayContent.isHomeSupported() && this.mCanHostHomeTask;
    }

    void ensureActivitiesVisible(final com.android.server.wm.ActivityRecord activityRecord, final boolean z) {
        this.mAtmService.mTaskSupervisor.beginActivityVisibilityUpdate();
        try {
            forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.wm.Task) obj).ensureActivitiesVisible(com.android.server.wm.ActivityRecord.this, z);
                }
            });
        } finally {
            this.mAtmService.mTaskSupervisor.endActivityVisibilityUpdate();
        }
    }

    com.android.server.wm.Task remove() {
        com.android.server.wm.Task task = null;
        this.mPreferredTopFocusableRootTask = null;
        boolean shouldDestroyContentOnRemove = this.mDisplayContent.shouldDestroyContentOnRemove();
        com.android.server.wm.TaskDisplayArea defaultTaskDisplayArea = this.mRootWindowContainer.getDefaultTaskDisplayArea();
        int size = this.mChildren.size();
        int i = 0;
        while (i < size) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(i);
            if (windowContainer.asTaskDisplayArea() != null) {
                task = windowContainer.asTaskDisplayArea().remove();
            } else {
                com.android.server.wm.Task asTask = ((com.android.server.wm.WindowContainer) this.mChildren.get(i)).asTask();
                if (shouldDestroyContentOnRemove || !asTask.isActivityTypeStandardOrUndefined() || asTask.mCreatedByOrganizer) {
                    asTask.remove(false, "removeTaskDisplayArea");
                } else {
                    com.android.server.wm.Task launchRootTask = defaultTaskDisplayArea.getLaunchRootTask(asTask.getWindowingMode(), asTask.getActivityType(), null, null, 0);
                    asTask.reparent(launchRootTask == null ? defaultTaskDisplayArea : launchRootTask, Integer.MAX_VALUE);
                    if (!(launchRootTask == null && asTask.getRequestedOverrideWindowingMode() == 1 && defaultTaskDisplayArea.getWindowingMode() != 1)) {
                        asTask.setWindowingMode(0);
                    }
                    task = asTask;
                }
                i -= size - this.mChildren.size();
                size = this.mChildren.size();
            }
            i++;
        }
        if (task != null && !task.isRootTask()) {
            task.getRootTask().moveToFront("display-removed");
        }
        this.mRemoved = true;
        return task;
    }

    boolean canSpecifyOrientation(int i) {
        return this.mDisplayContent.getOrientationRequestingTaskDisplayArea() == this && !shouldIgnoreOrientationRequest(i);
    }

    void clearPreferredTopFocusableRootTask() {
        this.mPreferredTopFocusableRootTask = null;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void setWindowingMode(int i) {
        this.mTempConfiguration.setTo(getRequestedOverrideConfiguration());
        android.app.WindowConfiguration windowConfiguration = this.mTempConfiguration.windowConfiguration;
        windowConfiguration.setWindowingMode(i);
        windowConfiguration.setDisplayWindowingMode(i);
        onRequestedOverrideConfigurationChanged(this.mTempConfiguration);
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.TaskDisplayArea getTaskDisplayArea() {
        return this;
    }

    @Override // com.android.server.wm.DisplayArea
    boolean isTaskDisplayArea() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.TaskDisplayArea asTaskDisplayArea() {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.server.wm.WindowContainer] */
    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        printWriter.println(str + "TaskDisplayArea " + getName());
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("  ");
        java.lang.String sb2 = sb.toString();
        super.dump(printWriter, sb2, z);
        if (this.mPreferredTopFocusableRootTask != null) {
            printWriter.println(sb2 + "mPreferredTopFocusableRootTask=" + this.mPreferredTopFocusableRootTask);
        }
        if (this.mLastFocusedRootTask != null) {
            printWriter.println(sb2 + "mLastFocusedRootTask=" + this.mLastFocusedRootTask);
        }
        java.lang.String str2 = sb2 + "  ";
        if (this.mLaunchRootTasks.size() > 0) {
            printWriter.println(sb2 + "mLaunchRootTasks:");
            for (int size = this.mLaunchRootTasks.size() + (-1); size >= 0; size += -1) {
                com.android.server.wm.TaskDisplayArea.LaunchRootTaskDef launchRootTaskDef = this.mLaunchRootTasks.get(size);
                printWriter.println(str2 + java.util.Arrays.toString(launchRootTaskDef.activityTypes) + " " + java.util.Arrays.toString(launchRootTaskDef.windowingModes) + "  task=" + launchRootTaskDef.task);
            }
        }
        printWriter.println(sb2 + "Application tokens in top down Z order:");
        for (int childCount = getChildCount() + (-1); childCount >= 0; childCount--) {
            ?? childAt = getChildAt(childCount);
            if (childAt.asTaskDisplayArea() != null) {
                childAt.dump(printWriter, sb2, z);
            } else {
                com.android.server.wm.Task asTask = childAt.asTask();
                printWriter.println(sb2 + "* " + asTask.toFullString());
                asTask.dump(printWriter, str2, z);
            }
        }
    }
}
