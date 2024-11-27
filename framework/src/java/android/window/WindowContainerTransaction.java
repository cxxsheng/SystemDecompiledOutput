package android.window;

/* loaded from: classes4.dex */
public final class WindowContainerTransaction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.WindowContainerTransaction> CREATOR = new android.os.Parcelable.Creator<android.window.WindowContainerTransaction>() { // from class: android.window.WindowContainerTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.WindowContainerTransaction createFromParcel(android.os.Parcel parcel) {
            return new android.window.WindowContainerTransaction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.WindowContainerTransaction[] newArray(int i) {
            return new android.window.WindowContainerTransaction[i];
        }
    };
    private final android.util.ArrayMap<android.os.IBinder, android.window.WindowContainerTransaction.Change> mChanges;
    private android.os.IBinder mErrorCallbackToken;
    private final java.util.ArrayList<android.window.WindowContainerTransaction.HierarchyOp> mHierarchyOps;
    private android.window.ITaskFragmentOrganizer mTaskFragmentOrganizer;

    public WindowContainerTransaction() {
        this.mChanges = new android.util.ArrayMap<>();
        this.mHierarchyOps = new java.util.ArrayList<>();
    }

    private WindowContainerTransaction(android.os.Parcel parcel) {
        this.mChanges = new android.util.ArrayMap<>();
        this.mHierarchyOps = new java.util.ArrayList<>();
        parcel.readMap(this.mChanges, null);
        parcel.readTypedList(this.mHierarchyOps, android.window.WindowContainerTransaction.HierarchyOp.CREATOR);
        this.mErrorCallbackToken = parcel.readStrongBinder();
        this.mTaskFragmentOrganizer = android.window.ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
    }

    private android.window.WindowContainerTransaction.Change getOrCreateChange(android.os.IBinder iBinder) {
        android.window.WindowContainerTransaction.Change change = this.mChanges.get(iBinder);
        if (change == null) {
            android.window.WindowContainerTransaction.Change change2 = new android.window.WindowContainerTransaction.Change();
            this.mChanges.put(iBinder, change2);
            return change2;
        }
        return change;
    }

    public android.window.WindowContainerTransaction setBounds(android.window.WindowContainerToken windowContainerToken, android.graphics.Rect rect) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.windowConfiguration.setBounds(rect);
        orCreateChange.mConfigSetMask |= 536870912;
        orCreateChange.mWindowSetMask |= 1;
        return this;
    }

    public android.window.WindowContainerTransaction setAppBounds(android.window.WindowContainerToken windowContainerToken, android.graphics.Rect rect) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.windowConfiguration.setAppBounds(rect);
        orCreateChange.mConfigSetMask |= 536870912;
        orCreateChange.mWindowSetMask |= 2;
        return this;
    }

    public android.window.WindowContainerTransaction setScreenSizeDp(android.window.WindowContainerToken windowContainerToken, int i, int i2) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.screenWidthDp = i;
        orCreateChange.mConfiguration.screenHeightDp = i2;
        orCreateChange.mConfigSetMask |= 1024;
        return this;
    }

    public android.window.WindowContainerTransaction setDensityDpi(android.window.WindowContainerToken windowContainerToken, int i) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.densityDpi = i;
        orCreateChange.mConfigSetMask |= 4096;
        return this;
    }

    public android.window.WindowContainerTransaction scheduleFinishEnterPip(android.window.WindowContainerToken windowContainerToken, android.graphics.Rect rect) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mPinnedBounds = new android.graphics.Rect(rect);
        orCreateChange.mChangeMask |= 4;
        return this;
    }

    public android.window.WindowContainerTransaction setBoundsChangeTransaction(android.window.WindowContainerToken windowContainerToken, android.view.SurfaceControl.Transaction transaction) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mBoundsChangeTransaction = transaction;
        orCreateChange.mChangeMask |= 2;
        return this;
    }

    public android.window.WindowContainerTransaction setBoundsChangeTransaction(android.window.WindowContainerToken windowContainerToken, android.graphics.Rect rect) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        if (orCreateChange.mBoundsChangeSurfaceBounds == null) {
            orCreateChange.mBoundsChangeSurfaceBounds = new android.graphics.Rect();
        }
        orCreateChange.mBoundsChangeSurfaceBounds.set(rect);
        orCreateChange.mChangeMask |= 16;
        return this;
    }

    public android.window.WindowContainerTransaction setActivityWindowingMode(android.window.WindowContainerToken windowContainerToken, int i) {
        getOrCreateChange(windowContainerToken.asBinder()).mActivityWindowingMode = i;
        return this;
    }

    public android.window.WindowContainerTransaction setWindowingMode(android.window.WindowContainerToken windowContainerToken, int i) {
        getOrCreateChange(windowContainerToken.asBinder()).mWindowingMode = i;
        return this;
    }

    public android.window.WindowContainerTransaction setFocusable(android.window.WindowContainerToken windowContainerToken, boolean z) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mFocusable = z;
        orCreateChange.mChangeMask |= 1;
        return this;
    }

    public android.window.WindowContainerTransaction setHidden(android.window.WindowContainerToken windowContainerToken, boolean z) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mHidden = z;
        orCreateChange.mChangeMask |= 8;
        return this;
    }

    public android.window.WindowContainerTransaction setSmallestScreenWidthDp(android.window.WindowContainerToken windowContainerToken, int i) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.smallestScreenWidthDp = i;
        orCreateChange.mConfigSetMask |= 2048;
        return this;
    }

    public android.window.WindowContainerTransaction setIgnoreOrientationRequest(android.window.WindowContainerToken windowContainerToken, boolean z) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mIgnoreOrientationRequest = z;
        orCreateChange.mChangeMask |= 32;
        return this;
    }

    public android.window.WindowContainerTransaction setForceTranslucent(android.window.WindowContainerToken windowContainerToken, boolean z) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mForceTranslucent = z;
        orCreateChange.mChangeMask |= 128;
        return this;
    }

    public android.window.WindowContainerTransaction setDoNotPip(android.window.WindowContainerToken windowContainerToken) {
        getOrCreateChange(windowContainerToken.asBinder()).mChangeMask |= 64;
        return this;
    }

    public android.window.WindowContainerTransaction setRelativeBounds(android.window.WindowContainerToken windowContainerToken, android.graphics.Rect rect) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        if (orCreateChange.mRelativeBounds == null) {
            orCreateChange.mRelativeBounds = new android.graphics.Rect();
        }
        orCreateChange.mRelativeBounds.set(rect);
        orCreateChange.mChangeMask |= 512;
        orCreateChange.mConfigSetMask |= 536870912;
        orCreateChange.mWindowSetMask |= 1;
        return this;
    }

    public android.window.WindowContainerTransaction reparent(android.window.WindowContainerToken windowContainerToken, android.window.WindowContainerToken windowContainerToken2, boolean z) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForReparent(windowContainerToken.asBinder(), windowContainerToken2 == null ? null : windowContainerToken2.asBinder(), z));
        return this;
    }

    public android.window.WindowContainerTransaction reorder(android.window.WindowContainerToken windowContainerToken, boolean z) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForReorder(windowContainerToken.asBinder(), z));
        return this;
    }

    public android.window.WindowContainerTransaction reparentTasks(android.window.WindowContainerToken windowContainerToken, android.window.WindowContainerToken windowContainerToken2, int[] iArr, int[] iArr2, boolean z, boolean z2) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForChildrenTasksReparent(windowContainerToken != null ? windowContainerToken.asBinder() : null, windowContainerToken2 != null ? windowContainerToken2.asBinder() : null, iArr, iArr2, z, z2));
        return this;
    }

    public android.window.WindowContainerTransaction reparentTasks(android.window.WindowContainerToken windowContainerToken, android.window.WindowContainerToken windowContainerToken2, int[] iArr, int[] iArr2, boolean z) {
        return reparentTasks(windowContainerToken, windowContainerToken2, iArr, iArr2, z, false);
    }

    public android.window.WindowContainerTransaction setLaunchRoot(android.window.WindowContainerToken windowContainerToken, int[] iArr, int[] iArr2) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForSetLaunchRoot(windowContainerToken.asBinder(), iArr, iArr2));
        return this;
    }

    public android.window.WindowContainerTransaction setAdjacentRoots(android.window.WindowContainerToken windowContainerToken, android.window.WindowContainerToken windowContainerToken2) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForAdjacentRoots(windowContainerToken.asBinder(), windowContainerToken2.asBinder()));
        return this;
    }

    public android.window.WindowContainerTransaction setLaunchAdjacentFlagRoot(android.window.WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForSetLaunchAdjacentFlagRoot(windowContainerToken.asBinder(), false));
        return this;
    }

    public android.window.WindowContainerTransaction clearLaunchAdjacentFlagRoot(android.window.WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForSetLaunchAdjacentFlagRoot(windowContainerToken.asBinder(), true));
        return this;
    }

    public android.window.WindowContainerTransaction startTask(int i, android.os.Bundle bundle) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForTaskLaunch(i, bundle));
        return this;
    }

    public android.window.WindowContainerTransaction removeTask(android.window.WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForRemoveTask(windowContainerToken.asBinder()));
        return this;
    }

    public android.window.WindowContainerTransaction setDragResizing(android.window.WindowContainerToken windowContainerToken, boolean z) {
        android.window.WindowContainerTransaction.Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mChangeMask |= 256;
        orCreateChange.mDragResizing = z;
        return this;
    }

    public android.window.WindowContainerTransaction sendPendingIntent(android.app.PendingIntent pendingIntent, android.content.Intent intent, android.os.Bundle bundle) {
        this.mHierarchyOps.add(new android.window.WindowContainerTransaction.HierarchyOp.Builder(7).setLaunchOptions(bundle).setPendingIntent(pendingIntent).setActivityIntent(intent).build());
        return this;
    }

    public android.window.WindowContainerTransaction startShortcut(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, android.os.Bundle bundle) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForStartShortcut(str, shortcutInfo, bundle));
        return this;
    }

    public android.window.WindowContainerTransaction createTaskFragment(android.window.TaskFragmentCreationParams taskFragmentCreationParams) {
        return addTaskFragmentOperation(taskFragmentCreationParams.getFragmentToken(), new android.window.TaskFragmentOperation.Builder(0).setTaskFragmentCreationParams(taskFragmentCreationParams).build());
    }

    public android.window.WindowContainerTransaction deleteTaskFragment(android.os.IBinder iBinder) {
        return addTaskFragmentOperation(iBinder, new android.window.TaskFragmentOperation.Builder(1).build());
    }

    public android.window.WindowContainerTransaction startActivityInTaskFragment(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.content.Intent intent, android.os.Bundle bundle) {
        return addTaskFragmentOperation(iBinder, new android.window.TaskFragmentOperation.Builder(2).setActivityToken(iBinder2).setActivityIntent(intent).setBundle(bundle).build());
    }

    public android.window.WindowContainerTransaction reparentActivityToTaskFragment(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        return addTaskFragmentOperation(iBinder, new android.window.TaskFragmentOperation.Builder(3).setActivityToken(iBinder2).build());
    }

    public android.window.WindowContainerTransaction setAdjacentTaskFragments(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.window.WindowContainerTransaction.TaskFragmentAdjacentParams taskFragmentAdjacentParams) {
        return addTaskFragmentOperation(iBinder, new android.window.TaskFragmentOperation.Builder(4).setSecondaryFragmentToken(iBinder2).setBundle(taskFragmentAdjacentParams != null ? taskFragmentAdjacentParams.toBundle() : null).build());
    }

    public android.window.WindowContainerTransaction clearAdjacentTaskFragments(android.os.IBinder iBinder) {
        return addTaskFragmentOperation(iBinder, new android.window.TaskFragmentOperation.Builder(5).build());
    }

    public android.window.WindowContainerTransaction restoreTransientOrder(android.window.WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(new android.window.WindowContainerTransaction.HierarchyOp.Builder(9).setContainer(windowContainerToken.asBinder()).build());
        return this;
    }

    public android.window.WindowContainerTransaction addInsetsSource(android.window.WindowContainerToken windowContainerToken, android.os.IBinder iBinder, int i, int i2, android.graphics.Rect rect, android.graphics.Rect[] rectArr) {
        this.mHierarchyOps.add(new android.window.WindowContainerTransaction.HierarchyOp.Builder(10).setContainer(windowContainerToken.asBinder()).setInsetsFrameProvider(new android.view.InsetsFrameProvider(iBinder, i, i2).setSource(3).setArbitraryRectangle(rect).setBoundingRects(rectArr)).setInsetsFrameOwner(iBinder).build());
        return this;
    }

    public android.window.WindowContainerTransaction removeInsetsSource(android.window.WindowContainerToken windowContainerToken, android.os.IBinder iBinder, int i, int i2) {
        this.mHierarchyOps.add(new android.window.WindowContainerTransaction.HierarchyOp.Builder(11).setContainer(windowContainerToken.asBinder()).setInsetsFrameProvider(new android.view.InsetsFrameProvider(iBinder, i, i2)).setInsetsFrameOwner(iBinder).build());
        return this;
    }

    public android.window.WindowContainerTransaction requestFocusOnTaskFragment(android.os.IBinder iBinder) {
        return addTaskFragmentOperation(iBinder, new android.window.TaskFragmentOperation.Builder(6).build());
    }

    public android.window.WindowContainerTransaction finishActivity(android.os.IBinder iBinder) {
        this.mHierarchyOps.add(new android.window.WindowContainerTransaction.HierarchyOp.Builder(14).setContainer(iBinder).build());
        return this;
    }

    public android.window.WindowContainerTransaction setCompanionTaskFragment(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        return addTaskFragmentOperation(iBinder, new android.window.TaskFragmentOperation.Builder(7).setSecondaryFragmentToken(iBinder2).build());
    }

    public android.window.WindowContainerTransaction addTaskFragmentOperation(android.os.IBinder iBinder, android.window.TaskFragmentOperation taskFragmentOperation) {
        java.util.Objects.requireNonNull(iBinder);
        java.util.Objects.requireNonNull(taskFragmentOperation);
        this.mHierarchyOps.add(new android.window.WindowContainerTransaction.HierarchyOp.Builder(17).setContainer(iBinder).setTaskFragmentOperation(taskFragmentOperation).build());
        return this;
    }

    public android.window.WindowContainerTransaction setAlwaysOnTop(android.window.WindowContainerToken windowContainerToken, boolean z) {
        this.mHierarchyOps.add(new android.window.WindowContainerTransaction.HierarchyOp.Builder(12).setContainer(windowContainerToken.asBinder()).setAlwaysOnTop(z).build());
        return this;
    }

    public android.window.WindowContainerTransaction setErrorCallbackToken(android.os.IBinder iBinder) {
        if (this.mErrorCallbackToken != null) {
            throw new java.lang.IllegalStateException("Can't set multiple error token for one transaction.");
        }
        this.mErrorCallbackToken = iBinder;
        return this;
    }

    public android.window.WindowContainerTransaction setTaskFragmentOrganizer(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        this.mTaskFragmentOrganizer = iTaskFragmentOrganizer;
        return this;
    }

    public android.window.WindowContainerTransaction clearAdjacentRoots(android.window.WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(android.window.WindowContainerTransaction.HierarchyOp.createForClearAdjacentRoots(windowContainerToken.asBinder()));
        return this;
    }

    public android.window.WindowContainerTransaction setReparentLeafTaskIfRelaunch(android.window.WindowContainerToken windowContainerToken, boolean z) {
        this.mHierarchyOps.add(new android.window.WindowContainerTransaction.HierarchyOp.Builder(16).setContainer(windowContainerToken.asBinder()).setReparentLeafTaskIfRelaunch(z).build());
        return this;
    }

    public android.window.WindowContainerTransaction movePipActivityToPinnedRootTask(android.window.WindowContainerToken windowContainerToken, android.graphics.Rect rect) {
        this.mHierarchyOps.add(new android.window.WindowContainerTransaction.HierarchyOp.Builder(18).setContainer(windowContainerToken.asBinder()).setBounds(rect).build());
        return this;
    }

    public android.window.WindowContainerTransaction deferConfigToTransitionEnd(android.window.WindowContainerToken windowContainerToken) {
        getOrCreateChange(windowContainerToken.asBinder()).mConfigAtTransitionEnd = true;
        return this;
    }

    public void merge(android.window.WindowContainerTransaction windowContainerTransaction, boolean z) {
        android.os.IBinder iBinder;
        android.os.IBinder iBinder2;
        int size = windowContainerTransaction.mChanges.size();
        for (int i = 0; i < size; i++) {
            android.os.IBinder keyAt = windowContainerTransaction.mChanges.keyAt(i);
            android.window.WindowContainerTransaction.Change change = this.mChanges.get(keyAt);
            if (change == null) {
                change = new android.window.WindowContainerTransaction.Change();
                this.mChanges.put(keyAt, change);
            }
            change.merge(windowContainerTransaction.mChanges.valueAt(i), z);
        }
        int size2 = windowContainerTransaction.mHierarchyOps.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mHierarchyOps.add(z ? windowContainerTransaction.mHierarchyOps.get(i2) : new android.window.WindowContainerTransaction.HierarchyOp(windowContainerTransaction.mHierarchyOps.get(i2)));
        }
        if (this.mErrorCallbackToken != null && windowContainerTransaction.mErrorCallbackToken != null && this.mErrorCallbackToken != windowContainerTransaction.mErrorCallbackToken) {
            throw new java.lang.IllegalArgumentException("Can't merge two WCTs with different error token");
        }
        if (this.mTaskFragmentOrganizer != null) {
            iBinder = this.mTaskFragmentOrganizer.asBinder();
        } else {
            iBinder = null;
        }
        if (!java.util.Objects.equals(iBinder, windowContainerTransaction.mTaskFragmentOrganizer != null ? windowContainerTransaction.mTaskFragmentOrganizer.asBinder() : null)) {
            throw new java.lang.IllegalArgumentException("Can't merge two WCTs from different TaskFragmentOrganizers");
        }
        if (this.mErrorCallbackToken != null) {
            iBinder2 = this.mErrorCallbackToken;
        } else {
            iBinder2 = windowContainerTransaction.mErrorCallbackToken;
        }
        this.mErrorCallbackToken = iBinder2;
    }

    public boolean isEmpty() {
        return this.mChanges.isEmpty() && this.mHierarchyOps.isEmpty();
    }

    public java.util.Map<android.os.IBinder, android.window.WindowContainerTransaction.Change> getChanges() {
        return this.mChanges;
    }

    public java.util.List<android.window.WindowContainerTransaction.HierarchyOp> getHierarchyOps() {
        return this.mHierarchyOps;
    }

    public android.os.IBinder getErrorCallbackToken() {
        return this.mErrorCallbackToken;
    }

    public android.window.ITaskFragmentOrganizer getTaskFragmentOrganizer() {
        return this.mTaskFragmentOrganizer;
    }

    public java.lang.String toString() {
        return "WindowContainerTransaction { changes = " + this.mChanges + " hops = " + this.mHierarchyOps + " errorCallbackToken=" + this.mErrorCallbackToken + " taskFragmentOrganizer=" + this.mTaskFragmentOrganizer + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeMap(this.mChanges);
        parcel.writeTypedList(this.mHierarchyOps);
        parcel.writeStrongBinder(this.mErrorCallbackToken);
        parcel.writeStrongInterface(this.mTaskFragmentOrganizer);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Change implements android.os.Parcelable {
        public static final int CHANGE_BOUNDS_TRANSACTION = 2;
        public static final int CHANGE_BOUNDS_TRANSACTION_RECT = 16;
        public static final int CHANGE_DRAG_RESIZING = 256;
        public static final int CHANGE_FOCUSABLE = 1;
        public static final int CHANGE_FORCE_NO_PIP = 64;
        public static final int CHANGE_FORCE_TRANSLUCENT = 128;
        public static final int CHANGE_HIDDEN = 8;
        public static final int CHANGE_IGNORE_ORIENTATION_REQUEST = 32;
        public static final int CHANGE_PIP_CALLBACK = 4;
        public static final int CHANGE_RELATIVE_BOUNDS = 512;
        public static final android.os.Parcelable.Creator<android.window.WindowContainerTransaction.Change> CREATOR = new android.os.Parcelable.Creator<android.window.WindowContainerTransaction.Change>() { // from class: android.window.WindowContainerTransaction.Change.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.WindowContainerTransaction.Change createFromParcel(android.os.Parcel parcel) {
                return new android.window.WindowContainerTransaction.Change(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.WindowContainerTransaction.Change[] newArray(int i) {
                return new android.window.WindowContainerTransaction.Change[i];
            }
        };
        private int mActivityWindowingMode;
        private android.graphics.Rect mBoundsChangeSurfaceBounds;
        private android.view.SurfaceControl.Transaction mBoundsChangeTransaction;
        private int mChangeMask;
        private boolean mConfigAtTransitionEnd;
        private int mConfigSetMask;
        private final android.content.res.Configuration mConfiguration;
        private boolean mDragResizing;
        private boolean mFocusable;
        private boolean mForceTranslucent;
        private boolean mHidden;
        private boolean mIgnoreOrientationRequest;
        private android.graphics.Rect mPinnedBounds;
        private android.graphics.Rect mRelativeBounds;
        private int mWindowSetMask;
        private int mWindowingMode;

        public Change() {
            this.mConfiguration = new android.content.res.Configuration();
            this.mFocusable = true;
            this.mHidden = false;
            this.mIgnoreOrientationRequest = false;
            this.mForceTranslucent = false;
            this.mDragResizing = false;
            this.mChangeMask = 0;
            this.mConfigSetMask = 0;
            this.mWindowSetMask = 0;
            this.mPinnedBounds = null;
            this.mBoundsChangeTransaction = null;
            this.mBoundsChangeSurfaceBounds = null;
            this.mRelativeBounds = null;
            this.mConfigAtTransitionEnd = false;
            this.mActivityWindowingMode = -1;
            this.mWindowingMode = -1;
        }

        protected Change(android.os.Parcel parcel) {
            this.mConfiguration = new android.content.res.Configuration();
            this.mFocusable = true;
            this.mHidden = false;
            this.mIgnoreOrientationRequest = false;
            this.mForceTranslucent = false;
            this.mDragResizing = false;
            this.mChangeMask = 0;
            this.mConfigSetMask = 0;
            this.mWindowSetMask = 0;
            this.mPinnedBounds = null;
            this.mBoundsChangeTransaction = null;
            this.mBoundsChangeSurfaceBounds = null;
            this.mRelativeBounds = null;
            this.mConfigAtTransitionEnd = false;
            this.mActivityWindowingMode = -1;
            this.mWindowingMode = -1;
            this.mConfiguration.readFromParcel(parcel);
            this.mFocusable = parcel.readBoolean();
            this.mHidden = parcel.readBoolean();
            this.mIgnoreOrientationRequest = parcel.readBoolean();
            this.mForceTranslucent = parcel.readBoolean();
            this.mDragResizing = parcel.readBoolean();
            this.mChangeMask = parcel.readInt();
            this.mConfigSetMask = parcel.readInt();
            this.mWindowSetMask = parcel.readInt();
            if ((this.mChangeMask & 4) != 0) {
                this.mPinnedBounds = new android.graphics.Rect();
                this.mPinnedBounds.readFromParcel(parcel);
            }
            if ((this.mChangeMask & 2) != 0) {
                this.mBoundsChangeTransaction = android.view.SurfaceControl.Transaction.CREATOR.createFromParcel(parcel);
            }
            if ((this.mChangeMask & 16) != 0) {
                this.mBoundsChangeSurfaceBounds = new android.graphics.Rect();
                this.mBoundsChangeSurfaceBounds.readFromParcel(parcel);
            }
            if ((this.mChangeMask & 512) != 0) {
                this.mRelativeBounds = new android.graphics.Rect();
                this.mRelativeBounds.readFromParcel(parcel);
            }
            this.mConfigAtTransitionEnd = parcel.readBoolean();
            this.mWindowingMode = parcel.readInt();
            this.mActivityWindowingMode = parcel.readInt();
        }

        public void merge(android.window.WindowContainerTransaction.Change change, boolean z) {
            android.graphics.Rect rect;
            this.mConfiguration.setTo(change.mConfiguration, change.mConfigSetMask, change.mWindowSetMask);
            this.mConfigSetMask |= change.mConfigSetMask;
            this.mWindowSetMask |= change.mWindowSetMask;
            boolean z2 = true;
            if ((change.mChangeMask & 1) != 0) {
                this.mFocusable = change.mFocusable;
            }
            if (z && (change.mChangeMask & 2) != 0) {
                this.mBoundsChangeTransaction = change.mBoundsChangeTransaction;
                change.mBoundsChangeTransaction = null;
            }
            if ((change.mChangeMask & 4) != 0) {
                this.mPinnedBounds = z ? change.mPinnedBounds : new android.graphics.Rect(change.mPinnedBounds);
            }
            if ((change.mChangeMask & 8) != 0) {
                this.mHidden = change.mHidden;
            }
            if ((change.mChangeMask & 32) != 0) {
                this.mIgnoreOrientationRequest = change.mIgnoreOrientationRequest;
            }
            if ((change.mChangeMask & 128) != 0) {
                this.mForceTranslucent = change.mForceTranslucent;
            }
            if ((change.mChangeMask & 256) != 0) {
                this.mDragResizing = change.mDragResizing;
            }
            this.mChangeMask |= change.mChangeMask;
            if (change.mActivityWindowingMode >= 0) {
                this.mActivityWindowingMode = change.mActivityWindowingMode;
            }
            if (change.mWindowingMode >= 0) {
                this.mWindowingMode = change.mWindowingMode;
            }
            if (change.mBoundsChangeSurfaceBounds != null) {
                this.mBoundsChangeSurfaceBounds = z ? change.mBoundsChangeSurfaceBounds : new android.graphics.Rect(change.mBoundsChangeSurfaceBounds);
            }
            if (change.mRelativeBounds != null) {
                if (z) {
                    rect = change.mRelativeBounds;
                } else {
                    rect = new android.graphics.Rect(change.mRelativeBounds);
                }
                this.mRelativeBounds = rect;
            }
            if (!this.mConfigAtTransitionEnd && !change.mConfigAtTransitionEnd) {
                z2 = false;
            }
            this.mConfigAtTransitionEnd = z2;
        }

        public int getWindowingMode() {
            return this.mWindowingMode;
        }

        public int getActivityWindowingMode() {
            return this.mActivityWindowingMode;
        }

        public android.content.res.Configuration getConfiguration() {
            return this.mConfiguration;
        }

        public boolean getFocusable() {
            if ((this.mChangeMask & 1) == 0) {
                throw new java.lang.RuntimeException("Focusable not set. check CHANGE_FOCUSABLE first");
            }
            return this.mFocusable;
        }

        public boolean getHidden() {
            if ((this.mChangeMask & 8) == 0) {
                throw new java.lang.RuntimeException("Hidden not set. check CHANGE_HIDDEN first");
            }
            return this.mHidden;
        }

        public boolean getIgnoreOrientationRequest() {
            if ((this.mChangeMask & 32) == 0) {
                throw new java.lang.RuntimeException("IgnoreOrientationRequest not set. Check CHANGE_IGNORE_ORIENTATION_REQUEST first");
            }
            return this.mIgnoreOrientationRequest;
        }

        public boolean getForceTranslucent() {
            if ((this.mChangeMask & 128) == 0) {
                throw new java.lang.RuntimeException("Force translucent not set. Check CHANGE_FORCE_TRANSLUCENT first");
            }
            return this.mForceTranslucent;
        }

        public boolean getDragResizing() {
            if ((this.mChangeMask & 256) == 0) {
                throw new java.lang.RuntimeException("Drag resizing not set. Check CHANGE_DRAG_RESIZING first");
            }
            return this.mDragResizing;
        }

        public boolean getConfigAtTransitionEnd() {
            return this.mConfigAtTransitionEnd;
        }

        public int getChangeMask() {
            return this.mChangeMask;
        }

        public int getConfigSetMask() {
            return this.mConfigSetMask;
        }

        public int getWindowSetMask() {
            return this.mWindowSetMask;
        }

        public android.graphics.Rect getEnterPipBounds() {
            return this.mPinnedBounds;
        }

        public android.view.SurfaceControl.Transaction getBoundsChangeTransaction() {
            return this.mBoundsChangeTransaction;
        }

        public android.graphics.Rect getBoundsChangeSurfaceBounds() {
            return this.mBoundsChangeSurfaceBounds;
        }

        public android.graphics.Rect getRelativeBounds() {
            return this.mRelativeBounds;
        }

        public java.lang.String toString() {
            boolean z = ((this.mConfigSetMask & 536870912) == 0 || (this.mWindowSetMask & 1) == 0) ? false : true;
            boolean z2 = ((536870912 & this.mConfigSetMask) == 0 || (this.mWindowSetMask & 2) == 0) ? false : true;
            boolean z3 = (this.mConfigSetMask & 1024) != 0;
            boolean z4 = (this.mConfigSetMask & 2048) != 0;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append('{');
            if (z) {
                sb.append("bounds:" + this.mConfiguration.windowConfiguration.getBounds() + ",");
            }
            if (z2) {
                sb.append("appbounds:" + this.mConfiguration.windowConfiguration.getAppBounds() + ",");
            }
            if (z4) {
                sb.append("ssw:" + this.mConfiguration.smallestScreenWidthDp + ",");
            }
            if (z3) {
                sb.append("sw/h:" + this.mConfiguration.screenWidthDp + "x" + this.mConfiguration.screenHeightDp + ",");
            }
            if ((this.mChangeMask & 1) != 0) {
                sb.append("focusable:" + this.mFocusable + ",");
            }
            if ((this.mChangeMask & 256) != 0) {
                sb.append("dragResizing:" + this.mDragResizing + ",");
            }
            if (this.mBoundsChangeTransaction != null) {
                sb.append("hasBoundsTransaction,");
            }
            if ((this.mChangeMask & 32) != 0) {
                sb.append("ignoreOrientationRequest:" + this.mIgnoreOrientationRequest + ",");
            }
            if ((this.mChangeMask & 512) != 0) {
                sb.append("relativeBounds:").append(this.mRelativeBounds).append(",");
            }
            if (this.mConfigAtTransitionEnd) {
                sb.append("configAtTransitionEnd").append(",");
            }
            sb.append("}");
            return sb.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            this.mConfiguration.writeToParcel(parcel, i);
            parcel.writeBoolean(this.mFocusable);
            parcel.writeBoolean(this.mHidden);
            parcel.writeBoolean(this.mIgnoreOrientationRequest);
            parcel.writeBoolean(this.mForceTranslucent);
            parcel.writeBoolean(this.mDragResizing);
            parcel.writeInt(this.mChangeMask);
            parcel.writeInt(this.mConfigSetMask);
            parcel.writeInt(this.mWindowSetMask);
            if (this.mPinnedBounds != null) {
                this.mPinnedBounds.writeToParcel(parcel, i);
            }
            if (this.mBoundsChangeTransaction != null) {
                this.mBoundsChangeTransaction.writeToParcel(parcel, i);
            }
            if (this.mBoundsChangeSurfaceBounds != null) {
                this.mBoundsChangeSurfaceBounds.writeToParcel(parcel, i);
            }
            if (this.mRelativeBounds != null) {
                this.mRelativeBounds.writeToParcel(parcel, i);
            }
            parcel.writeBoolean(this.mConfigAtTransitionEnd);
            parcel.writeInt(this.mWindowingMode);
            parcel.writeInt(this.mActivityWindowingMode);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static final class HierarchyOp implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.window.WindowContainerTransaction.HierarchyOp> CREATOR = new android.os.Parcelable.Creator<android.window.WindowContainerTransaction.HierarchyOp>() { // from class: android.window.WindowContainerTransaction.HierarchyOp.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.WindowContainerTransaction.HierarchyOp createFromParcel(android.os.Parcel parcel) {
                return new android.window.WindowContainerTransaction.HierarchyOp(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.WindowContainerTransaction.HierarchyOp[] newArray(int i) {
                return new android.window.WindowContainerTransaction.HierarchyOp[i];
            }
        };
        public static final int HIERARCHY_OP_TYPE_ADD_INSETS_FRAME_PROVIDER = 10;
        public static final int HIERARCHY_OP_TYPE_ADD_TASK_FRAGMENT_OPERATION = 17;
        public static final int HIERARCHY_OP_TYPE_CHILDREN_TASKS_REPARENT = 2;
        public static final int HIERARCHY_OP_TYPE_CLEAR_ADJACENT_ROOTS = 15;
        public static final int HIERARCHY_OP_TYPE_FINISH_ACTIVITY = 14;
        public static final int HIERARCHY_OP_TYPE_LAUNCH_TASK = 5;
        public static final int HIERARCHY_OP_TYPE_MOVE_PIP_ACTIVITY_TO_PINNED_TASK = 18;
        public static final int HIERARCHY_OP_TYPE_PENDING_INTENT = 7;
        public static final int HIERARCHY_OP_TYPE_REMOVE_INSETS_FRAME_PROVIDER = 11;
        public static final int HIERARCHY_OP_TYPE_REMOVE_TASK = 13;
        public static final int HIERARCHY_OP_TYPE_REORDER = 1;
        public static final int HIERARCHY_OP_TYPE_REPARENT = 0;
        public static final int HIERARCHY_OP_TYPE_RESTORE_TRANSIENT_ORDER = 9;
        public static final int HIERARCHY_OP_TYPE_SET_ADJACENT_ROOTS = 4;
        public static final int HIERARCHY_OP_TYPE_SET_ALWAYS_ON_TOP = 12;
        public static final int HIERARCHY_OP_TYPE_SET_LAUNCH_ADJACENT_FLAG_ROOT = 6;
        public static final int HIERARCHY_OP_TYPE_SET_LAUNCH_ROOT = 3;
        public static final int HIERARCHY_OP_TYPE_SET_REPARENT_LEAF_TASK_IF_RELAUNCH = 16;
        public static final int HIERARCHY_OP_TYPE_START_SHORTCUT = 8;
        public static final java.lang.String LAUNCH_KEY_SHORTCUT_CALLING_PACKAGE = "android:transaction.hop.shortcut_calling_package";
        public static final java.lang.String LAUNCH_KEY_TASK_ID = "android:transaction.hop.taskId";
        private android.content.Intent mActivityIntent;
        private int[] mActivityTypes;
        private boolean mAlwaysOnTop;
        private android.graphics.Rect mBounds;
        private android.os.IBinder mContainer;
        private android.os.IBinder mInsetsFrameOwner;
        private android.view.InsetsFrameProvider mInsetsFrameProvider;
        private android.os.Bundle mLaunchOptions;
        private android.app.PendingIntent mPendingIntent;
        private android.os.IBinder mReparent;
        private boolean mReparentLeafTaskIfRelaunch;
        private boolean mReparentTopOnly;
        private android.content.pm.ShortcutInfo mShortcutInfo;
        private android.window.TaskFragmentOperation mTaskFragmentOperation;
        private boolean mToTop;
        private final int mType;
        private int[] mWindowingModes;

        public static android.window.WindowContainerTransaction.HierarchyOp createForReparent(android.os.IBinder iBinder, android.os.IBinder iBinder2, boolean z) {
            return new android.window.WindowContainerTransaction.HierarchyOp.Builder(0).setContainer(iBinder).setReparentContainer(iBinder2).setToTop(z).build();
        }

        public static android.window.WindowContainerTransaction.HierarchyOp createForReorder(android.os.IBinder iBinder, boolean z) {
            return new android.window.WindowContainerTransaction.HierarchyOp.Builder(1).setContainer(iBinder).setReparentContainer(iBinder).setToTop(z).build();
        }

        public static android.window.WindowContainerTransaction.HierarchyOp createForChildrenTasksReparent(android.os.IBinder iBinder, android.os.IBinder iBinder2, int[] iArr, int[] iArr2, boolean z, boolean z2) {
            return new android.window.WindowContainerTransaction.HierarchyOp.Builder(2).setContainer(iBinder).setReparentContainer(iBinder2).setWindowingModes(iArr).setActivityTypes(iArr2).setToTop(z).setReparentTopOnly(z2).build();
        }

        public static android.window.WindowContainerTransaction.HierarchyOp createForSetLaunchRoot(android.os.IBinder iBinder, int[] iArr, int[] iArr2) {
            return new android.window.WindowContainerTransaction.HierarchyOp.Builder(3).setContainer(iBinder).setWindowingModes(iArr).setActivityTypes(iArr2).build();
        }

        public static android.window.WindowContainerTransaction.HierarchyOp createForAdjacentRoots(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
            return new android.window.WindowContainerTransaction.HierarchyOp.Builder(4).setContainer(iBinder).setReparentContainer(iBinder2).build();
        }

        public static android.window.WindowContainerTransaction.HierarchyOp createForTaskLaunch(int i, android.os.Bundle bundle) {
            if (bundle == null) {
                bundle = new android.os.Bundle();
            }
            bundle.putInt(LAUNCH_KEY_TASK_ID, i);
            return new android.window.WindowContainerTransaction.HierarchyOp.Builder(5).setToTop(true).setLaunchOptions(bundle).build();
        }

        public static android.window.WindowContainerTransaction.HierarchyOp createForStartShortcut(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, android.os.Bundle bundle) {
            if (bundle == null) {
                bundle = new android.os.Bundle();
            }
            bundle.putString(LAUNCH_KEY_SHORTCUT_CALLING_PACKAGE, str);
            return new android.window.WindowContainerTransaction.HierarchyOp.Builder(8).setShortcutInfo(shortcutInfo).setLaunchOptions(bundle).build();
        }

        public static android.window.WindowContainerTransaction.HierarchyOp createForSetLaunchAdjacentFlagRoot(android.os.IBinder iBinder, boolean z) {
            return new android.window.WindowContainerTransaction.HierarchyOp.Builder(6).setContainer(iBinder).setToTop(z).build();
        }

        public static android.window.WindowContainerTransaction.HierarchyOp createForRemoveTask(android.os.IBinder iBinder) {
            return new android.window.WindowContainerTransaction.HierarchyOp.Builder(13).setContainer(iBinder).build();
        }

        public static android.window.WindowContainerTransaction.HierarchyOp createForClearAdjacentRoots(android.os.IBinder iBinder) {
            return new android.window.WindowContainerTransaction.HierarchyOp.Builder(15).setContainer(iBinder).build();
        }

        private HierarchyOp(int i) {
            this.mType = i;
        }

        public HierarchyOp(android.window.WindowContainerTransaction.HierarchyOp hierarchyOp) {
            this.mType = hierarchyOp.mType;
            this.mContainer = hierarchyOp.mContainer;
            this.mBounds = hierarchyOp.mBounds;
            this.mReparent = hierarchyOp.mReparent;
            this.mInsetsFrameProvider = hierarchyOp.mInsetsFrameProvider;
            this.mInsetsFrameOwner = hierarchyOp.mInsetsFrameOwner;
            this.mToTop = hierarchyOp.mToTop;
            this.mReparentTopOnly = hierarchyOp.mReparentTopOnly;
            this.mWindowingModes = hierarchyOp.mWindowingModes;
            this.mActivityTypes = hierarchyOp.mActivityTypes;
            this.mLaunchOptions = hierarchyOp.mLaunchOptions;
            this.mActivityIntent = hierarchyOp.mActivityIntent;
            this.mTaskFragmentOperation = hierarchyOp.mTaskFragmentOperation;
            this.mPendingIntent = hierarchyOp.mPendingIntent;
            this.mShortcutInfo = hierarchyOp.mShortcutInfo;
            this.mAlwaysOnTop = hierarchyOp.mAlwaysOnTop;
            this.mReparentLeafTaskIfRelaunch = hierarchyOp.mReparentLeafTaskIfRelaunch;
        }

        protected HierarchyOp(android.os.Parcel parcel) {
            this.mType = parcel.readInt();
            this.mContainer = parcel.readStrongBinder();
            this.mBounds = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
            this.mReparent = parcel.readStrongBinder();
            this.mInsetsFrameProvider = (android.view.InsetsFrameProvider) parcel.readTypedObject(android.view.InsetsFrameProvider.CREATOR);
            this.mInsetsFrameOwner = parcel.readStrongBinder();
            this.mToTop = parcel.readBoolean();
            this.mReparentTopOnly = parcel.readBoolean();
            this.mWindowingModes = parcel.createIntArray();
            this.mActivityTypes = parcel.createIntArray();
            this.mLaunchOptions = parcel.readBundle();
            this.mActivityIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
            this.mTaskFragmentOperation = (android.window.TaskFragmentOperation) parcel.readTypedObject(android.window.TaskFragmentOperation.CREATOR);
            this.mPendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
            this.mShortcutInfo = (android.content.pm.ShortcutInfo) parcel.readTypedObject(android.content.pm.ShortcutInfo.CREATOR);
            this.mAlwaysOnTop = parcel.readBoolean();
            this.mReparentLeafTaskIfRelaunch = parcel.readBoolean();
        }

        public int getType() {
            return this.mType;
        }

        public boolean isReparent() {
            return this.mType == 0;
        }

        public android.os.IBinder getNewParent() {
            return this.mReparent;
        }

        public android.view.InsetsFrameProvider getInsetsFrameProvider() {
            return this.mInsetsFrameProvider;
        }

        public android.os.IBinder getInsetsFrameOwner() {
            return this.mInsetsFrameOwner;
        }

        public android.os.IBinder getContainer() {
            return this.mContainer;
        }

        public android.os.IBinder getAdjacentRoot() {
            return this.mReparent;
        }

        public boolean getToTop() {
            return this.mToTop;
        }

        public boolean getReparentTopOnly() {
            return this.mReparentTopOnly;
        }

        public int[] getWindowingModes() {
            return this.mWindowingModes;
        }

        public int[] getActivityTypes() {
            return this.mActivityTypes;
        }

        public android.os.Bundle getLaunchOptions() {
            return this.mLaunchOptions;
        }

        public android.content.Intent getActivityIntent() {
            return this.mActivityIntent;
        }

        public boolean isAlwaysOnTop() {
            return this.mAlwaysOnTop;
        }

        public boolean isReparentLeafTaskIfRelaunch() {
            return this.mReparentLeafTaskIfRelaunch;
        }

        public android.window.TaskFragmentOperation getTaskFragmentOperation() {
            return this.mTaskFragmentOperation;
        }

        public android.app.PendingIntent getPendingIntent() {
            return this.mPendingIntent;
        }

        public android.content.pm.ShortcutInfo getShortcutInfo() {
            return this.mShortcutInfo;
        }

        public android.graphics.Rect getBounds() {
            return this.mBounds;
        }

        public static java.lang.String hopToString(int i) {
            switch (i) {
                case 0:
                    return "reparent";
                case 1:
                    return "reorder";
                case 2:
                    return "ChildrenTasksReparent";
                case 3:
                    return "SetLaunchRoot";
                case 4:
                    return "SetAdjacentRoot";
                case 5:
                    return "LaunchTask";
                case 6:
                    return "SetAdjacentFlagRoot";
                case 7:
                    return "PendingIntent";
                case 8:
                    return "StartShortcut";
                case 9:
                default:
                    return "HOP(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
                case 10:
                    return "addInsetsFrameProvider";
                case 11:
                    return "removeInsetsFrameProvider";
                case 12:
                    return "setAlwaysOnTop";
                case 13:
                    return "RemoveTask";
                case 14:
                    return "finishActivity";
                case 15:
                    return "ClearAdjacentRoot";
                case 16:
                    return "setReparentLeafTaskIfRelaunch";
                case 17:
                    return "addTaskFragmentOperation";
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{").append(hopToString(this.mType)).append(": ");
            switch (this.mType) {
                case 0:
                    sb.append(this.mContainer).append(" to ").append(this.mToTop ? "top of " : "bottom of ").append(this.mReparent);
                    break;
                case 1:
                    sb.append(this.mContainer).append(" to ").append(this.mToTop ? "top" : "bottom");
                    break;
                case 2:
                    sb.append("from=").append(this.mContainer).append(" to=").append(this.mReparent).append(" mToTop=").append(this.mToTop).append(" mReparentTopOnly=").append(this.mReparentTopOnly).append(" mWindowingMode=").append(java.util.Arrays.toString(this.mWindowingModes)).append(" mActivityType=").append(java.util.Arrays.toString(this.mActivityTypes));
                    break;
                case 3:
                    sb.append("container=").append(this.mContainer).append(" mWindowingMode=").append(java.util.Arrays.toString(this.mWindowingModes)).append(" mActivityType=").append(java.util.Arrays.toString(this.mActivityTypes));
                    break;
                case 4:
                    sb.append("container=").append(this.mContainer).append(" adjacentRoot=").append(this.mReparent);
                    break;
                case 5:
                    sb.append(this.mLaunchOptions);
                    break;
                case 6:
                    sb.append("container=").append(this.mContainer).append(" clearRoot=").append(this.mToTop);
                    break;
                case 7:
                    sb.append("options=").append(this.mLaunchOptions);
                    break;
                case 8:
                    sb.append("options=").append(this.mLaunchOptions).append(" info=").append(this.mShortcutInfo);
                    break;
                case 9:
                default:
                    sb.append("container=").append(this.mContainer).append(" reparent=").append(this.mReparent).append(" mToTop=").append(this.mToTop).append(" mWindowingMode=").append(java.util.Arrays.toString(this.mWindowingModes)).append(" mActivityType=").append(java.util.Arrays.toString(this.mActivityTypes));
                    break;
                case 10:
                case 11:
                    sb.append("container=").append(this.mContainer).append(" provider=").append(this.mInsetsFrameProvider).append(" owner=").append(this.mInsetsFrameOwner);
                    break;
                case 12:
                    sb.append("container=").append(this.mContainer).append(" alwaysOnTop=").append(this.mAlwaysOnTop);
                    break;
                case 13:
                    sb.append("task=").append(this.mContainer);
                    break;
                case 14:
                    sb.append("activity=").append(this.mContainer);
                    break;
                case 15:
                    sb.append("container=").append(this.mContainer);
                    break;
                case 16:
                    sb.append("container= ").append(this.mContainer).append(" reparentLeafTaskIfRelaunch= ").append(this.mReparentLeafTaskIfRelaunch);
                    break;
                case 17:
                    sb.append("fragmentToken= ").append(this.mContainer).append(" operation= ").append(this.mTaskFragmentOperation);
                    break;
            }
            return sb.append("}").toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mType);
            parcel.writeStrongBinder(this.mContainer);
            parcel.writeTypedObject(this.mBounds, i);
            parcel.writeStrongBinder(this.mReparent);
            parcel.writeTypedObject(this.mInsetsFrameProvider, i);
            parcel.writeStrongBinder(this.mInsetsFrameOwner);
            parcel.writeBoolean(this.mToTop);
            parcel.writeBoolean(this.mReparentTopOnly);
            parcel.writeIntArray(this.mWindowingModes);
            parcel.writeIntArray(this.mActivityTypes);
            parcel.writeBundle(this.mLaunchOptions);
            parcel.writeTypedObject(this.mActivityIntent, i);
            parcel.writeTypedObject(this.mTaskFragmentOperation, i);
            parcel.writeTypedObject(this.mPendingIntent, i);
            parcel.writeTypedObject(this.mShortcutInfo, i);
            parcel.writeBoolean(this.mAlwaysOnTop);
            parcel.writeBoolean(this.mReparentLeafTaskIfRelaunch);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private static class Builder {
            private android.content.Intent mActivityIntent;
            private int[] mActivityTypes;
            private boolean mAlwaysOnTop;
            private android.graphics.Rect mBounds;
            private android.os.IBinder mContainer;
            private android.os.IBinder mInsetsFrameOwner;
            private android.view.InsetsFrameProvider mInsetsFrameProvider;
            private android.os.Bundle mLaunchOptions;
            private android.app.PendingIntent mPendingIntent;
            private android.os.IBinder mReparent;
            private boolean mReparentLeafTaskIfRelaunch;
            private boolean mReparentTopOnly;
            private android.content.pm.ShortcutInfo mShortcutInfo;
            private android.window.TaskFragmentOperation mTaskFragmentOperation;
            private boolean mToTop;
            private final int mType;
            private int[] mWindowingModes;

            Builder(int i) {
                this.mType = i;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setContainer(android.os.IBinder iBinder) {
                this.mContainer = iBinder;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setReparentContainer(android.os.IBinder iBinder) {
                this.mReparent = iBinder;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setInsetsFrameProvider(android.view.InsetsFrameProvider insetsFrameProvider) {
                this.mInsetsFrameProvider = insetsFrameProvider;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setInsetsFrameOwner(android.os.IBinder iBinder) {
                this.mInsetsFrameOwner = iBinder;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setToTop(boolean z) {
                this.mToTop = z;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setReparentTopOnly(boolean z) {
                this.mReparentTopOnly = z;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setWindowingModes(int[] iArr) {
                this.mWindowingModes = iArr;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setActivityTypes(int[] iArr) {
                this.mActivityTypes = iArr;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setLaunchOptions(android.os.Bundle bundle) {
                this.mLaunchOptions = bundle;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setActivityIntent(android.content.Intent intent) {
                this.mActivityIntent = intent;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setPendingIntent(android.app.PendingIntent pendingIntent) {
                this.mPendingIntent = pendingIntent;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setAlwaysOnTop(boolean z) {
                this.mAlwaysOnTop = z;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setTaskFragmentOperation(android.window.TaskFragmentOperation taskFragmentOperation) {
                this.mTaskFragmentOperation = taskFragmentOperation;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setReparentLeafTaskIfRelaunch(boolean z) {
                this.mReparentLeafTaskIfRelaunch = z;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setShortcutInfo(android.content.pm.ShortcutInfo shortcutInfo) {
                this.mShortcutInfo = shortcutInfo;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp.Builder setBounds(android.graphics.Rect rect) {
                this.mBounds = rect;
                return this;
            }

            android.window.WindowContainerTransaction.HierarchyOp build() {
                int[] iArr;
                android.window.WindowContainerTransaction.HierarchyOp hierarchyOp = new android.window.WindowContainerTransaction.HierarchyOp(this.mType);
                hierarchyOp.mContainer = this.mContainer;
                hierarchyOp.mReparent = this.mReparent;
                if (this.mWindowingModes != null) {
                    iArr = java.util.Arrays.copyOf(this.mWindowingModes, this.mWindowingModes.length);
                } else {
                    iArr = null;
                }
                hierarchyOp.mWindowingModes = iArr;
                hierarchyOp.mActivityTypes = this.mActivityTypes != null ? java.util.Arrays.copyOf(this.mActivityTypes, this.mActivityTypes.length) : null;
                hierarchyOp.mInsetsFrameProvider = this.mInsetsFrameProvider;
                hierarchyOp.mInsetsFrameOwner = this.mInsetsFrameOwner;
                hierarchyOp.mToTop = this.mToTop;
                hierarchyOp.mReparentTopOnly = this.mReparentTopOnly;
                hierarchyOp.mLaunchOptions = this.mLaunchOptions;
                hierarchyOp.mActivityIntent = this.mActivityIntent;
                hierarchyOp.mPendingIntent = this.mPendingIntent;
                hierarchyOp.mAlwaysOnTop = this.mAlwaysOnTop;
                hierarchyOp.mTaskFragmentOperation = this.mTaskFragmentOperation;
                hierarchyOp.mShortcutInfo = this.mShortcutInfo;
                hierarchyOp.mBounds = this.mBounds;
                hierarchyOp.mReparentLeafTaskIfRelaunch = this.mReparentLeafTaskIfRelaunch;
                return hierarchyOp;
            }
        }
    }

    public static class TaskFragmentAdjacentParams {
        private static final java.lang.String DELAY_PRIMARY_LAST_ACTIVITY_REMOVAL = "android:transaction.adjacent.option.delay_primary_removal";
        private static final java.lang.String DELAY_SECONDARY_LAST_ACTIVITY_REMOVAL = "android:transaction.adjacent.option.delay_secondary_removal";
        private boolean mDelayPrimaryLastActivityRemoval;
        private boolean mDelaySecondaryLastActivityRemoval;

        public TaskFragmentAdjacentParams() {
        }

        public TaskFragmentAdjacentParams(android.os.Bundle bundle) {
            this.mDelayPrimaryLastActivityRemoval = bundle.getBoolean(DELAY_PRIMARY_LAST_ACTIVITY_REMOVAL);
            this.mDelaySecondaryLastActivityRemoval = bundle.getBoolean(DELAY_SECONDARY_LAST_ACTIVITY_REMOVAL);
        }

        public void setShouldDelayPrimaryLastActivityRemoval(boolean z) {
            this.mDelayPrimaryLastActivityRemoval = z;
        }

        public void setShouldDelaySecondaryLastActivityRemoval(boolean z) {
            this.mDelaySecondaryLastActivityRemoval = z;
        }

        public boolean shouldDelayPrimaryLastActivityRemoval() {
            return this.mDelayPrimaryLastActivityRemoval;
        }

        public boolean shouldDelaySecondaryLastActivityRemoval() {
            return this.mDelaySecondaryLastActivityRemoval;
        }

        android.os.Bundle toBundle() {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putBoolean(DELAY_PRIMARY_LAST_ACTIVITY_REMOVAL, this.mDelayPrimaryLastActivityRemoval);
            bundle.putBoolean(DELAY_SECONDARY_LAST_ACTIVITY_REMOVAL, this.mDelaySecondaryLastActivityRemoval);
            return bundle;
        }
    }
}
