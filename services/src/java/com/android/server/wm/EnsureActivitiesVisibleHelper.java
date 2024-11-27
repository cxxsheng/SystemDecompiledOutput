package com.android.server.wm;

/* loaded from: classes3.dex */
class EnsureActivitiesVisibleHelper {
    private boolean mAboveTop;
    private boolean mBehindFullyOccludedContainer;
    private boolean mContainerShouldBeVisible;
    private boolean mNotifyClients;
    private com.android.server.wm.ActivityRecord mStarting;
    private final com.android.server.wm.TaskFragment mTaskFragment;
    private com.android.server.wm.ActivityRecord mTopRunningActivity;

    EnsureActivitiesVisibleHelper(com.android.server.wm.TaskFragment taskFragment) {
        this.mTaskFragment = taskFragment;
    }

    void reset(com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        this.mStarting = activityRecord;
        this.mTopRunningActivity = this.mTaskFragment.topRunningActivity();
        this.mAboveTop = this.mTopRunningActivity != null;
        this.mContainerShouldBeVisible = this.mTaskFragment.shouldBeVisible(this.mStarting);
        this.mBehindFullyOccludedContainer = !this.mContainerShouldBeVisible;
        this.mNotifyClients = z;
    }

    void process(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        reset(activityRecord, z);
        if (this.mTopRunningActivity != null && this.mTaskFragment.asTask() != null) {
            this.mTaskFragment.asTask().checkTranslucentActivityWaiting(this.mTopRunningActivity);
        }
        boolean z2 = this.mTopRunningActivity != null && !this.mTopRunningActivity.mLaunchTaskBehind && this.mTaskFragment.canBeResumed(activityRecord) && (activityRecord == null || !activityRecord.isDescendantOf(this.mTaskFragment));
        java.util.ArrayList arrayList = null;
        for (int size = this.mTaskFragment.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mTaskFragment.mChildren.get(size);
            com.android.server.wm.TaskFragment asTaskFragment = windowContainer.asTaskFragment();
            if (asTaskFragment != null && asTaskFragment.getTopNonFinishingActivity() != null) {
                asTaskFragment.updateActivityVisibilities(activityRecord, z);
                this.mBehindFullyOccludedContainer |= asTaskFragment.getBounds().equals(this.mTaskFragment.getBounds()) && !asTaskFragment.isTranslucent(activityRecord);
                if (this.mAboveTop && this.mTopRunningActivity.getTaskFragment() == asTaskFragment) {
                    this.mAboveTop = false;
                }
                if (!this.mBehindFullyOccludedContainer) {
                    if (arrayList != null && arrayList.contains(asTaskFragment)) {
                        if (!asTaskFragment.isTranslucent(activityRecord) && !asTaskFragment.getAdjacentTaskFragment().isTranslucent(activityRecord)) {
                            this.mBehindFullyOccludedContainer = true;
                        }
                    } else {
                        com.android.server.wm.TaskFragment adjacentTaskFragment = asTaskFragment.getAdjacentTaskFragment();
                        if (adjacentTaskFragment != null) {
                            if (arrayList == null) {
                                arrayList = new java.util.ArrayList();
                            }
                            arrayList.add(adjacentTaskFragment);
                        }
                    }
                }
            } else if (windowContainer.asActivityRecord() != null) {
                setActivityVisibilityState(windowContainer.asActivityRecord(), activityRecord, z2);
            }
        }
    }

    private void setActivityVisibilityState(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, boolean z) {
        boolean z2 = false;
        boolean z3 = activityRecord == this.mTopRunningActivity;
        if (this.mAboveTop && !z3) {
            activityRecord.makeInvisible();
            return;
        }
        this.mAboveTop = false;
        activityRecord.updateVisibilityIgnoringKeyguard(this.mBehindFullyOccludedContainer);
        boolean shouldBeVisibleUnchecked = activityRecord.shouldBeVisibleUnchecked();
        if (activityRecord.visibleIgnoringKeyguard) {
            if (activityRecord.occludesParent()) {
                this.mBehindFullyOccludedContainer = true;
            } else {
                this.mBehindFullyOccludedContainer = false;
            }
        } else if (activityRecord.isState(com.android.server.wm.ActivityRecord.State.INITIALIZING)) {
            activityRecord.cancelInitializing();
        }
        if (shouldBeVisibleUnchecked) {
            if (activityRecord.finishing) {
                return;
            }
            if (activityRecord != this.mStarting && this.mNotifyClients) {
                activityRecord.ensureActivityConfiguration(true);
            }
            if (!activityRecord.attachedToProcess()) {
                com.android.server.wm.ActivityRecord activityRecord3 = this.mStarting;
                if (z && z3) {
                    z2 = true;
                }
                makeVisibleAndRestartIfNeeded(activityRecord3, z2, activityRecord);
            } else if (activityRecord.isVisibleRequested()) {
                if (activityRecord.mClientVisibilityDeferred && this.mNotifyClients) {
                    if (activityRecord.mClientVisibilityDeferred) {
                        activityRecord2 = null;
                    }
                    activityRecord.makeActiveIfNeeded(activityRecord2);
                    activityRecord.mClientVisibilityDeferred = false;
                }
                activityRecord.handleAlreadyVisible();
                if (this.mNotifyClients) {
                    activityRecord.makeActiveIfNeeded(this.mStarting);
                }
            } else {
                activityRecord.makeVisibleIfNeeded(this.mStarting, this.mNotifyClients);
            }
        } else {
            activityRecord.makeInvisible();
        }
        if (!this.mBehindFullyOccludedContainer && this.mTaskFragment.isActivityTypeHome() && activityRecord.isRootOfTask()) {
            this.mBehindFullyOccludedContainer = true;
        }
    }

    private void makeVisibleAndRestartIfNeeded(com.android.server.wm.ActivityRecord activityRecord, boolean z, com.android.server.wm.ActivityRecord activityRecord2) {
        if (!activityRecord2.isVisibleRequested() || activityRecord2.mLaunchTaskBehind) {
            activityRecord2.setVisibility(true);
        }
        if (activityRecord2 != activityRecord) {
            this.mTaskFragment.mTaskSupervisor.startSpecificActivity(activityRecord2, z, true);
        }
    }
}
