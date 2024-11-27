package android.window;

/* loaded from: classes4.dex */
public final class TaskFragmentTransaction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TaskFragmentTransaction> CREATOR = new android.os.Parcelable.Creator<android.window.TaskFragmentTransaction>() { // from class: android.window.TaskFragmentTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentTransaction createFromParcel(android.os.Parcel parcel) {
            return new android.window.TaskFragmentTransaction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentTransaction[] newArray(int i) {
            return new android.window.TaskFragmentTransaction[i];
        }
    };
    public static final int TYPE_ACTIVITY_REPARENTED_TO_TASK = 6;
    public static final int TYPE_TASK_FRAGMENT_APPEARED = 1;
    public static final int TYPE_TASK_FRAGMENT_ERROR = 5;
    public static final int TYPE_TASK_FRAGMENT_INFO_CHANGED = 2;
    public static final int TYPE_TASK_FRAGMENT_PARENT_INFO_CHANGED = 4;
    public static final int TYPE_TASK_FRAGMENT_VANISHED = 3;
    private final java.util.ArrayList<android.window.TaskFragmentTransaction.Change> mChanges;
    private final android.os.IBinder mTransactionToken;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ChangeType {
    }

    public TaskFragmentTransaction() {
        this.mChanges = new java.util.ArrayList<>();
        this.mTransactionToken = new android.os.Binder();
    }

    private TaskFragmentTransaction(android.os.Parcel parcel) {
        this.mChanges = new java.util.ArrayList<>();
        this.mTransactionToken = parcel.readStrongBinder();
        parcel.readTypedList(this.mChanges, android.window.TaskFragmentTransaction.Change.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mTransactionToken);
        parcel.writeTypedList(this.mChanges);
    }

    public android.os.IBinder getTransactionToken() {
        return this.mTransactionToken;
    }

    public void addChange(android.window.TaskFragmentTransaction.Change change) {
        if (change != null) {
            this.mChanges.add(change);
        }
    }

    public boolean isEmpty() {
        return this.mChanges.isEmpty();
    }

    public java.util.List<android.window.TaskFragmentTransaction.Change> getChanges() {
        return this.mChanges;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("TaskFragmentTransaction{token=");
        sb.append(this.mTransactionToken);
        sb.append(" changes=[");
        for (int i = 0; i < this.mChanges.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(this.mChanges.get(i));
        }
        sb.append("]}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Change implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.window.TaskFragmentTransaction.Change> CREATOR = new android.os.Parcelable.Creator<android.window.TaskFragmentTransaction.Change>() { // from class: android.window.TaskFragmentTransaction.Change.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TaskFragmentTransaction.Change createFromParcel(android.os.Parcel parcel) {
                return new android.window.TaskFragmentTransaction.Change(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TaskFragmentTransaction.Change[] newArray(int i) {
                return new android.window.TaskFragmentTransaction.Change[i];
            }
        };
        private android.content.Intent mActivityIntent;
        private android.os.IBinder mActivityToken;
        private android.os.Bundle mErrorBundle;
        private android.os.IBinder mErrorCallbackToken;
        private android.view.SurfaceControl mSurfaceControl;
        private android.window.TaskFragmentInfo mTaskFragmentInfo;
        private android.window.TaskFragmentParentInfo mTaskFragmentParentInfo;
        private android.os.IBinder mTaskFragmentToken;
        private int mTaskId;
        private final int mType;

        public Change(int i) {
            this.mType = i;
        }

        private Change(android.os.Parcel parcel) {
            this.mType = parcel.readInt();
            this.mTaskFragmentToken = parcel.readStrongBinder();
            this.mTaskFragmentInfo = (android.window.TaskFragmentInfo) parcel.readTypedObject(android.window.TaskFragmentInfo.CREATOR);
            this.mTaskId = parcel.readInt();
            this.mErrorCallbackToken = parcel.readStrongBinder();
            this.mErrorBundle = parcel.readBundle(android.window.TaskFragmentTransaction.class.getClassLoader());
            this.mActivityIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
            this.mActivityToken = parcel.readStrongBinder();
            this.mTaskFragmentParentInfo = (android.window.TaskFragmentParentInfo) parcel.readTypedObject(android.window.TaskFragmentParentInfo.CREATOR);
            this.mSurfaceControl = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mType);
            parcel.writeStrongBinder(this.mTaskFragmentToken);
            parcel.writeTypedObject(this.mTaskFragmentInfo, i);
            parcel.writeInt(this.mTaskId);
            parcel.writeStrongBinder(this.mErrorCallbackToken);
            parcel.writeBundle(this.mErrorBundle);
            parcel.writeTypedObject(this.mActivityIntent, i);
            parcel.writeStrongBinder(this.mActivityToken);
            parcel.writeTypedObject(this.mTaskFragmentParentInfo, i);
            parcel.writeTypedObject(this.mSurfaceControl, i);
        }

        public android.window.TaskFragmentTransaction.Change setTaskFragmentToken(android.os.IBinder iBinder) {
            this.mTaskFragmentToken = (android.os.IBinder) java.util.Objects.requireNonNull(iBinder);
            return this;
        }

        public android.window.TaskFragmentTransaction.Change setTaskFragmentInfo(android.window.TaskFragmentInfo taskFragmentInfo) {
            this.mTaskFragmentInfo = (android.window.TaskFragmentInfo) java.util.Objects.requireNonNull(taskFragmentInfo);
            return this;
        }

        public android.window.TaskFragmentTransaction.Change setTaskId(int i) {
            this.mTaskId = i;
            return this;
        }

        public android.window.TaskFragmentTransaction.Change setTaskConfiguration(android.content.res.Configuration configuration) {
            return this;
        }

        public android.window.TaskFragmentTransaction.Change setErrorCallbackToken(android.os.IBinder iBinder) {
            this.mErrorCallbackToken = iBinder;
            return this;
        }

        public android.window.TaskFragmentTransaction.Change setErrorBundle(android.os.Bundle bundle) {
            this.mErrorBundle = (android.os.Bundle) java.util.Objects.requireNonNull(bundle);
            return this;
        }

        public android.window.TaskFragmentTransaction.Change setActivityIntent(android.content.Intent intent) {
            this.mActivityIntent = (android.content.Intent) java.util.Objects.requireNonNull(intent);
            return this;
        }

        public android.window.TaskFragmentTransaction.Change setActivityToken(android.os.IBinder iBinder) {
            this.mActivityToken = (android.os.IBinder) java.util.Objects.requireNonNull(iBinder);
            return this;
        }

        public android.window.TaskFragmentTransaction.Change setTaskFragmentParentInfo(android.window.TaskFragmentParentInfo taskFragmentParentInfo) {
            this.mTaskFragmentParentInfo = (android.window.TaskFragmentParentInfo) java.util.Objects.requireNonNull(taskFragmentParentInfo);
            return this;
        }

        public android.window.TaskFragmentTransaction.Change setTaskFragmentSurfaceControl(android.view.SurfaceControl surfaceControl) {
            this.mSurfaceControl = surfaceControl;
            return this;
        }

        public int getType() {
            return this.mType;
        }

        public android.os.IBinder getTaskFragmentToken() {
            return this.mTaskFragmentToken;
        }

        public android.window.TaskFragmentInfo getTaskFragmentInfo() {
            return this.mTaskFragmentInfo;
        }

        public int getTaskId() {
            return this.mTaskId;
        }

        public android.content.res.Configuration getTaskConfiguration() {
            return this.mTaskFragmentParentInfo.getConfiguration();
        }

        public android.os.IBinder getErrorCallbackToken() {
            return this.mErrorCallbackToken;
        }

        public android.os.Bundle getErrorBundle() {
            return this.mErrorBundle != null ? this.mErrorBundle : android.os.Bundle.EMPTY;
        }

        public android.content.Intent getActivityIntent() {
            return this.mActivityIntent;
        }

        public android.os.IBinder getActivityToken() {
            return this.mActivityToken;
        }

        public android.window.TaskFragmentParentInfo getTaskFragmentParentInfo() {
            return this.mTaskFragmentParentInfo;
        }

        public android.view.SurfaceControl getTaskFragmentSurfaceControl() {
            return this.mSurfaceControl;
        }

        public java.lang.String toString() {
            return "Change{ type=" + this.mType + " }";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
