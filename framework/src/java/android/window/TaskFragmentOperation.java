package android.window;

/* loaded from: classes4.dex */
public final class TaskFragmentOperation implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TaskFragmentOperation> CREATOR = new android.os.Parcelable.Creator<android.window.TaskFragmentOperation>() { // from class: android.window.TaskFragmentOperation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentOperation createFromParcel(android.os.Parcel parcel) {
            return new android.window.TaskFragmentOperation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentOperation[] newArray(int i) {
            return new android.window.TaskFragmentOperation[i];
        }
    };
    public static final int OP_TYPE_CLEAR_ADJACENT_TASK_FRAGMENTS = 5;
    public static final int OP_TYPE_CREATE_TASK_FRAGMENT = 0;
    public static final int OP_TYPE_CREATE_TASK_FRAGMENT_DECOR_SURFACE = 14;
    public static final int OP_TYPE_DELETE_TASK_FRAGMENT = 1;
    public static final int OP_TYPE_REMOVE_TASK_FRAGMENT_DECOR_SURFACE = 15;
    public static final int OP_TYPE_REORDER_TO_BOTTOM_OF_TASK = 12;
    public static final int OP_TYPE_REORDER_TO_FRONT = 10;
    public static final int OP_TYPE_REORDER_TO_TOP_OF_TASK = 13;
    public static final int OP_TYPE_REPARENT_ACTIVITY_TO_TASK_FRAGMENT = 3;
    public static final int OP_TYPE_REQUEST_FOCUS_ON_TASK_FRAGMENT = 6;
    public static final int OP_TYPE_SET_ADJACENT_TASK_FRAGMENTS = 4;
    public static final int OP_TYPE_SET_ANIMATION_PARAMS = 8;
    public static final int OP_TYPE_SET_COMPANION_TASK_FRAGMENT = 7;
    public static final int OP_TYPE_SET_DIM_ON_TASK = 16;
    public static final int OP_TYPE_SET_ISOLATED_NAVIGATION = 11;
    public static final int OP_TYPE_SET_MOVE_TO_BOTTOM_IF_CLEAR_WHEN_LAUNCH = 17;
    public static final int OP_TYPE_SET_RELATIVE_BOUNDS = 9;
    public static final int OP_TYPE_START_ACTIVITY_IN_TASK_FRAGMENT = 2;
    public static final int OP_TYPE_UNKNOWN = -1;
    private final android.content.Intent mActivityIntent;
    private final android.os.IBinder mActivityToken;
    private final android.window.TaskFragmentAnimationParams mAnimationParams;
    private final android.os.Bundle mBundle;
    private final boolean mDimOnTask;
    private final boolean mIsolatedNav;
    private final boolean mMoveToBottomIfClearWhenLaunch;
    private final int mOpType;
    private final android.os.IBinder mSecondaryFragmentToken;
    private final android.window.TaskFragmentCreationParams mTaskFragmentCreationParams;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OperationType {
    }

    private TaskFragmentOperation(int i, android.window.TaskFragmentCreationParams taskFragmentCreationParams, android.os.IBinder iBinder, android.content.Intent intent, android.os.Bundle bundle, android.os.IBinder iBinder2, android.window.TaskFragmentAnimationParams taskFragmentAnimationParams, boolean z, boolean z2, boolean z3) {
        this.mOpType = i;
        this.mTaskFragmentCreationParams = taskFragmentCreationParams;
        this.mActivityToken = iBinder;
        this.mActivityIntent = intent;
        this.mBundle = bundle;
        this.mSecondaryFragmentToken = iBinder2;
        this.mAnimationParams = taskFragmentAnimationParams;
        this.mIsolatedNav = z;
        this.mDimOnTask = z2;
        this.mMoveToBottomIfClearWhenLaunch = z3;
    }

    private TaskFragmentOperation(android.os.Parcel parcel) {
        this.mOpType = parcel.readInt();
        this.mTaskFragmentCreationParams = (android.window.TaskFragmentCreationParams) parcel.readTypedObject(android.window.TaskFragmentCreationParams.CREATOR);
        this.mActivityToken = parcel.readStrongBinder();
        this.mActivityIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
        this.mBundle = parcel.readBundle(getClass().getClassLoader());
        this.mSecondaryFragmentToken = parcel.readStrongBinder();
        this.mAnimationParams = (android.window.TaskFragmentAnimationParams) parcel.readTypedObject(android.window.TaskFragmentAnimationParams.CREATOR);
        this.mIsolatedNav = parcel.readBoolean();
        this.mDimOnTask = parcel.readBoolean();
        this.mMoveToBottomIfClearWhenLaunch = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mOpType);
        parcel.writeTypedObject(this.mTaskFragmentCreationParams, i);
        parcel.writeStrongBinder(this.mActivityToken);
        parcel.writeTypedObject(this.mActivityIntent, i);
        parcel.writeBundle(this.mBundle);
        parcel.writeStrongBinder(this.mSecondaryFragmentToken);
        parcel.writeTypedObject(this.mAnimationParams, i);
        parcel.writeBoolean(this.mIsolatedNav);
        parcel.writeBoolean(this.mDimOnTask);
        parcel.writeBoolean(this.mMoveToBottomIfClearWhenLaunch);
    }

    public int getOpType() {
        return this.mOpType;
    }

    public android.window.TaskFragmentCreationParams getTaskFragmentCreationParams() {
        return this.mTaskFragmentCreationParams;
    }

    public android.os.IBinder getActivityToken() {
        return this.mActivityToken;
    }

    public android.content.Intent getActivityIntent() {
        return this.mActivityIntent;
    }

    public android.os.Bundle getBundle() {
        return this.mBundle;
    }

    public android.os.IBinder getSecondaryFragmentToken() {
        return this.mSecondaryFragmentToken;
    }

    public android.window.TaskFragmentAnimationParams getAnimationParams() {
        return this.mAnimationParams;
    }

    public boolean isIsolatedNav() {
        return this.mIsolatedNav;
    }

    public boolean isDimOnTask() {
        return this.mDimOnTask;
    }

    public boolean isMoveToBottomIfClearWhenLaunch() {
        return this.mMoveToBottomIfClearWhenLaunch;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("TaskFragmentOperation{ opType=").append(this.mOpType);
        if (this.mTaskFragmentCreationParams != null) {
            sb.append(", taskFragmentCreationParams=").append(this.mTaskFragmentCreationParams);
        }
        if (this.mActivityToken != null) {
            sb.append(", activityToken=").append(this.mActivityToken);
        }
        if (this.mActivityIntent != null) {
            sb.append(", activityIntent=").append(this.mActivityIntent);
        }
        if (this.mBundle != null) {
            sb.append(", bundle=").append(this.mBundle);
        }
        if (this.mSecondaryFragmentToken != null) {
            sb.append(", secondaryFragmentToken=").append(this.mSecondaryFragmentToken);
        }
        if (this.mAnimationParams != null) {
            sb.append(", animationParams=").append(this.mAnimationParams);
        }
        sb.append(", isolatedNav=").append(this.mIsolatedNav);
        sb.append(", dimOnTask=").append(this.mDimOnTask);
        sb.append(", moveToBottomIfClearWhenLaunch=").append(this.mMoveToBottomIfClearWhenLaunch);
        sb.append('}');
        return sb.toString();
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mOpType), this.mTaskFragmentCreationParams, this.mActivityToken, this.mActivityIntent, this.mBundle, this.mSecondaryFragmentToken, this.mAnimationParams, java.lang.Boolean.valueOf(this.mIsolatedNav), java.lang.Boolean.valueOf(this.mDimOnTask), java.lang.Boolean.valueOf(this.mMoveToBottomIfClearWhenLaunch));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.window.TaskFragmentOperation)) {
            return false;
        }
        android.window.TaskFragmentOperation taskFragmentOperation = (android.window.TaskFragmentOperation) obj;
        return this.mOpType == taskFragmentOperation.mOpType && java.util.Objects.equals(this.mTaskFragmentCreationParams, taskFragmentOperation.mTaskFragmentCreationParams) && java.util.Objects.equals(this.mActivityToken, taskFragmentOperation.mActivityToken) && java.util.Objects.equals(this.mActivityIntent, taskFragmentOperation.mActivityIntent) && java.util.Objects.equals(this.mBundle, taskFragmentOperation.mBundle) && java.util.Objects.equals(this.mSecondaryFragmentToken, taskFragmentOperation.mSecondaryFragmentToken) && java.util.Objects.equals(this.mAnimationParams, taskFragmentOperation.mAnimationParams) && this.mIsolatedNav == taskFragmentOperation.mIsolatedNav && this.mDimOnTask == taskFragmentOperation.mDimOnTask && this.mMoveToBottomIfClearWhenLaunch == taskFragmentOperation.mMoveToBottomIfClearWhenLaunch;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private android.content.Intent mActivityIntent;
        private android.os.IBinder mActivityToken;
        private android.window.TaskFragmentAnimationParams mAnimationParams;
        private android.os.Bundle mBundle;
        private boolean mDimOnTask;
        private boolean mIsolatedNav;
        private boolean mMoveToBottomIfClearWhenLaunch;
        private final int mOpType;
        private android.os.IBinder mSecondaryFragmentToken;
        private android.window.TaskFragmentCreationParams mTaskFragmentCreationParams;

        public Builder(int i) {
            this.mOpType = i;
        }

        public android.window.TaskFragmentOperation.Builder setTaskFragmentCreationParams(android.window.TaskFragmentCreationParams taskFragmentCreationParams) {
            this.mTaskFragmentCreationParams = taskFragmentCreationParams;
            return this;
        }

        public android.window.TaskFragmentOperation.Builder setActivityToken(android.os.IBinder iBinder) {
            this.mActivityToken = iBinder;
            return this;
        }

        public android.window.TaskFragmentOperation.Builder setActivityIntent(android.content.Intent intent) {
            this.mActivityIntent = intent;
            return this;
        }

        public android.window.TaskFragmentOperation.Builder setBundle(android.os.Bundle bundle) {
            this.mBundle = bundle;
            return this;
        }

        public android.window.TaskFragmentOperation.Builder setSecondaryFragmentToken(android.os.IBinder iBinder) {
            this.mSecondaryFragmentToken = iBinder;
            return this;
        }

        public android.window.TaskFragmentOperation.Builder setAnimationParams(android.window.TaskFragmentAnimationParams taskFragmentAnimationParams) {
            this.mAnimationParams = taskFragmentAnimationParams;
            return this;
        }

        public android.window.TaskFragmentOperation.Builder setIsolatedNav(boolean z) {
            this.mIsolatedNav = z;
            return this;
        }

        public android.window.TaskFragmentOperation.Builder setDimOnTask(boolean z) {
            this.mDimOnTask = z;
            return this;
        }

        public android.window.TaskFragmentOperation.Builder setMoveToBottomIfClearWhenLaunch(boolean z) {
            this.mMoveToBottomIfClearWhenLaunch = z;
            return this;
        }

        public android.window.TaskFragmentOperation build() {
            return new android.window.TaskFragmentOperation(this.mOpType, this.mTaskFragmentCreationParams, this.mActivityToken, this.mActivityIntent, this.mBundle, this.mSecondaryFragmentToken, this.mAnimationParams, this.mIsolatedNav, this.mDimOnTask, this.mMoveToBottomIfClearWhenLaunch);
        }
    }
}
