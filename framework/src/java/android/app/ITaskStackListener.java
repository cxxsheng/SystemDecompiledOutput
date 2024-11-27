package android.app;

/* loaded from: classes.dex */
public interface ITaskStackListener extends android.os.IInterface {
    public static final int FORCED_RESIZEABLE_REASON_SECONDARY_DISPLAY = 2;
    public static final int FORCED_RESIZEABLE_REASON_SPLIT_SCREEN = 1;

    void onActivityDismissingDockedTask() throws android.os.RemoteException;

    void onActivityForcedResizable(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void onActivityLaunchOnSecondaryDisplayFailed(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException;

    void onActivityLaunchOnSecondaryDisplayRerouted(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException;

    void onActivityPinned(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void onActivityRequestedOrientationChanged(int i, int i2) throws android.os.RemoteException;

    void onActivityRestartAttempt(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) throws android.os.RemoteException;

    void onActivityRotation(int i) throws android.os.RemoteException;

    void onActivityUnpinned() throws android.os.RemoteException;

    void onBackPressedOnTaskRoot(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException;

    void onLockTaskModeChanged(int i) throws android.os.RemoteException;

    void onRecentTaskListFrozenChanged(boolean z) throws android.os.RemoteException;

    void onRecentTaskListUpdated() throws android.os.RemoteException;

    void onTaskCreated(int i, android.content.ComponentName componentName) throws android.os.RemoteException;

    void onTaskDescriptionChanged(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException;

    void onTaskDisplayChanged(int i, int i2) throws android.os.RemoteException;

    void onTaskFocusChanged(int i, boolean z) throws android.os.RemoteException;

    void onTaskMovedToBack(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException;

    void onTaskMovedToFront(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException;

    void onTaskProfileLocked(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException;

    void onTaskRemovalStarted(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException;

    void onTaskRemoved(int i) throws android.os.RemoteException;

    void onTaskRequestedOrientationChanged(int i, int i2) throws android.os.RemoteException;

    void onTaskSnapshotChanged(int i, android.window.TaskSnapshot taskSnapshot) throws android.os.RemoteException;

    void onTaskStackChanged() throws android.os.RemoteException;

    public static class Default implements android.app.ITaskStackListener {
        @Override // android.app.ITaskStackListener
        public void onTaskStackChanged() throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityPinned(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityUnpinned() throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityRestartAttempt(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityForcedResizable(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityDismissingDockedTask() throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityLaunchOnSecondaryDisplayFailed(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityLaunchOnSecondaryDisplayRerouted(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskCreated(int i, android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskRemoved(int i) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskMovedToFront(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskDescriptionChanged(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityRequestedOrientationChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskRemovalStarted(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskProfileLocked(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskSnapshotChanged(int i, android.window.TaskSnapshot taskSnapshot) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onBackPressedOnTaskRoot(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskDisplayChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onRecentTaskListUpdated() throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onRecentTaskListFrozenChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskFocusChanged(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskRequestedOrientationChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityRotation(int i) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskMovedToBack(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onLockTaskModeChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ITaskStackListener {
        public static final java.lang.String DESCRIPTOR = "android.app.ITaskStackListener";
        static final int TRANSACTION_onActivityDismissingDockedTask = 6;
        static final int TRANSACTION_onActivityForcedResizable = 5;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayFailed = 7;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayRerouted = 8;
        static final int TRANSACTION_onActivityPinned = 2;
        static final int TRANSACTION_onActivityRequestedOrientationChanged = 13;
        static final int TRANSACTION_onActivityRestartAttempt = 4;
        static final int TRANSACTION_onActivityRotation = 23;
        static final int TRANSACTION_onActivityUnpinned = 3;
        static final int TRANSACTION_onBackPressedOnTaskRoot = 17;
        static final int TRANSACTION_onLockTaskModeChanged = 25;
        static final int TRANSACTION_onRecentTaskListFrozenChanged = 20;
        static final int TRANSACTION_onRecentTaskListUpdated = 19;
        static final int TRANSACTION_onTaskCreated = 9;
        static final int TRANSACTION_onTaskDescriptionChanged = 12;
        static final int TRANSACTION_onTaskDisplayChanged = 18;
        static final int TRANSACTION_onTaskFocusChanged = 21;
        static final int TRANSACTION_onTaskMovedToBack = 24;
        static final int TRANSACTION_onTaskMovedToFront = 11;
        static final int TRANSACTION_onTaskProfileLocked = 15;
        static final int TRANSACTION_onTaskRemovalStarted = 14;
        static final int TRANSACTION_onTaskRemoved = 10;
        static final int TRANSACTION_onTaskRequestedOrientationChanged = 22;
        static final int TRANSACTION_onTaskSnapshotChanged = 16;
        static final int TRANSACTION_onTaskStackChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.ITaskStackListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ITaskStackListener)) {
                return (android.app.ITaskStackListener) queryLocalInterface;
            }
            return new android.app.ITaskStackListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTaskStackChanged";
                case 2:
                    return "onActivityPinned";
                case 3:
                    return "onActivityUnpinned";
                case 4:
                    return "onActivityRestartAttempt";
                case 5:
                    return "onActivityForcedResizable";
                case 6:
                    return "onActivityDismissingDockedTask";
                case 7:
                    return "onActivityLaunchOnSecondaryDisplayFailed";
                case 8:
                    return "onActivityLaunchOnSecondaryDisplayRerouted";
                case 9:
                    return "onTaskCreated";
                case 10:
                    return "onTaskRemoved";
                case 11:
                    return "onTaskMovedToFront";
                case 12:
                    return "onTaskDescriptionChanged";
                case 13:
                    return "onActivityRequestedOrientationChanged";
                case 14:
                    return "onTaskRemovalStarted";
                case 15:
                    return "onTaskProfileLocked";
                case 16:
                    return "onTaskSnapshotChanged";
                case 17:
                    return "onBackPressedOnTaskRoot";
                case 18:
                    return "onTaskDisplayChanged";
                case 19:
                    return "onRecentTaskListUpdated";
                case 20:
                    return "onRecentTaskListFrozenChanged";
                case 21:
                    return "onTaskFocusChanged";
                case 22:
                    return "onTaskRequestedOrientationChanged";
                case 23:
                    return "onActivityRotation";
                case 24:
                    return "onTaskMovedToBack";
                case 25:
                    return "onLockTaskModeChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onTaskStackChanged();
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityPinned(readString, readInt, readInt2, readInt3);
                    return true;
                case 3:
                    onActivityUnpinned();
                    return true;
                case 4:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onActivityRestartAttempt(runningTaskInfo, readBoolean, readBoolean2, readBoolean3);
                    return true;
                case 5:
                    java.lang.String readString2 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityForcedResizable(readString2, readInt4, readInt5);
                    return true;
                case 6:
                    onActivityDismissingDockedTask();
                    return true;
                case 7:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo2 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityLaunchOnSecondaryDisplayFailed(runningTaskInfo2, readInt6);
                    return true;
                case 8:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo3 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityLaunchOnSecondaryDisplayRerouted(runningTaskInfo3, readInt7);
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskCreated(readInt8, componentName);
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskRemoved(readInt9);
                    return true;
                case 11:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo4 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskMovedToFront(runningTaskInfo4);
                    return true;
                case 12:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo5 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskDescriptionChanged(runningTaskInfo5);
                    return true;
                case 13:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityRequestedOrientationChanged(readInt10, readInt11);
                    return true;
                case 14:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo6 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskRemovalStarted(runningTaskInfo6);
                    return true;
                case 15:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo7 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskProfileLocked(runningTaskInfo7, readInt12);
                    return true;
                case 16:
                    int readInt13 = parcel.readInt();
                    android.window.TaskSnapshot taskSnapshot = (android.window.TaskSnapshot) parcel.readTypedObject(android.window.TaskSnapshot.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskSnapshotChanged(readInt13, taskSnapshot);
                    return true;
                case 17:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo8 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBackPressedOnTaskRoot(runningTaskInfo8);
                    return true;
                case 18:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskDisplayChanged(readInt14, readInt15);
                    return true;
                case 19:
                    onRecentTaskListUpdated();
                    return true;
                case 20:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onRecentTaskListFrozenChanged(readBoolean4);
                    return true;
                case 21:
                    int readInt16 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTaskFocusChanged(readInt16, readBoolean5);
                    return true;
                case 22:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskRequestedOrientationChanged(readInt17, readInt18);
                    return true;
                case 23:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityRotation(readInt19);
                    return true;
                case 24:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo9 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskMovedToBack(runningTaskInfo9);
                    return true;
                case 25:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLockTaskModeChanged(readInt20);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ITaskStackListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ITaskStackListener.Stub.DESCRIPTOR;
            }

            @Override // android.app.ITaskStackListener
            public void onTaskStackChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityPinned(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityUnpinned() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRestartAttempt(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityForcedResizable(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityDismissingDockedTask() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityLaunchOnSecondaryDisplayFailed(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityLaunchOnSecondaryDisplayRerouted(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskCreated(int i, android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRemoved(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskMovedToFront(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskDescriptionChanged(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRequestedOrientationChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRemovalStarted(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskProfileLocked(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskSnapshotChanged(int i, android.window.TaskSnapshot taskSnapshot) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(taskSnapshot, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onBackPressedOnTaskRoot(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskDisplayChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskListUpdated() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskListFrozenChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskFocusChanged(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRequestedOrientationChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRotation(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskMovedToBack(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onLockTaskModeChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITaskStackListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }
    }
}
