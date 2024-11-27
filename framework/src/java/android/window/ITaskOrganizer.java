package android.window;

/* loaded from: classes4.dex */
public interface ITaskOrganizer extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.ITaskOrganizer";

    void addStartingWindow(android.window.StartingWindowInfo startingWindowInfo) throws android.os.RemoteException;

    void copySplashScreenView(int i) throws android.os.RemoteException;

    void onAppSplashScreenViewRemoved(int i) throws android.os.RemoteException;

    void onBackPressedOnTaskRoot(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException;

    void onImeDrawnOnTask(int i) throws android.os.RemoteException;

    void onTaskAppeared(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException;

    void onTaskInfoChanged(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException;

    void onTaskVanished(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException;

    void removeStartingWindow(android.window.StartingWindowRemovalInfo startingWindowRemovalInfo) throws android.os.RemoteException;

    public static class Default implements android.window.ITaskOrganizer {
        @Override // android.window.ITaskOrganizer
        public void addStartingWindow(android.window.StartingWindowInfo startingWindowInfo) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void removeStartingWindow(android.window.StartingWindowRemovalInfo startingWindowRemovalInfo) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void copySplashScreenView(int i) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onAppSplashScreenViewRemoved(int i) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskAppeared(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskVanished(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskInfoChanged(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onBackPressedOnTaskRoot(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onImeDrawnOnTask(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.ITaskOrganizer {
        static final int TRANSACTION_addStartingWindow = 1;
        static final int TRANSACTION_copySplashScreenView = 3;
        static final int TRANSACTION_onAppSplashScreenViewRemoved = 4;
        static final int TRANSACTION_onBackPressedOnTaskRoot = 8;
        static final int TRANSACTION_onImeDrawnOnTask = 9;
        static final int TRANSACTION_onTaskAppeared = 5;
        static final int TRANSACTION_onTaskInfoChanged = 7;
        static final int TRANSACTION_onTaskVanished = 6;
        static final int TRANSACTION_removeStartingWindow = 2;

        public Stub() {
            attachInterface(this, android.window.ITaskOrganizer.DESCRIPTOR);
        }

        public static android.window.ITaskOrganizer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.ITaskOrganizer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.ITaskOrganizer)) {
                return (android.window.ITaskOrganizer) queryLocalInterface;
            }
            return new android.window.ITaskOrganizer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addStartingWindow";
                case 2:
                    return "removeStartingWindow";
                case 3:
                    return "copySplashScreenView";
                case 4:
                    return "onAppSplashScreenViewRemoved";
                case 5:
                    return "onTaskAppeared";
                case 6:
                    return "onTaskVanished";
                case 7:
                    return "onTaskInfoChanged";
                case 8:
                    return "onBackPressedOnTaskRoot";
                case 9:
                    return "onImeDrawnOnTask";
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
                parcel.enforceInterface(android.window.ITaskOrganizer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.ITaskOrganizer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.window.StartingWindowInfo startingWindowInfo = (android.window.StartingWindowInfo) parcel.readTypedObject(android.window.StartingWindowInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    addStartingWindow(startingWindowInfo);
                    return true;
                case 2:
                    android.window.StartingWindowRemovalInfo startingWindowRemovalInfo = (android.window.StartingWindowRemovalInfo) parcel.readTypedObject(android.window.StartingWindowRemovalInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeStartingWindow(startingWindowRemovalInfo);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    copySplashScreenView(readInt);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAppSplashScreenViewRemoved(readInt2);
                    return true;
                case 5:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    android.view.SurfaceControl surfaceControl = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskAppeared(runningTaskInfo, surfaceControl);
                    return true;
                case 6:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo2 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskVanished(runningTaskInfo2);
                    return true;
                case 7:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo3 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskInfoChanged(runningTaskInfo3);
                    return true;
                case 8:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo4 = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBackPressedOnTaskRoot(runningTaskInfo4);
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onImeDrawnOnTask(readInt3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.ITaskOrganizer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.ITaskOrganizer.DESCRIPTOR;
            }

            @Override // android.window.ITaskOrganizer
            public void addStartingWindow(android.window.StartingWindowInfo startingWindowInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(startingWindowInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void removeStartingWindow(android.window.StartingWindowRemovalInfo startingWindowRemovalInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(startingWindowRemovalInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void copySplashScreenView(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizer.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onAppSplashScreenViewRemoved(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizer.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onTaskAppeared(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    obtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onTaskVanished(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onTaskInfoChanged(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onBackPressedOnTaskRoot(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onImeDrawnOnTask(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizer.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
