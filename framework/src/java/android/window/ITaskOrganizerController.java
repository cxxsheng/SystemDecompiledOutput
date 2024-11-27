package android.window;

/* loaded from: classes4.dex */
public interface ITaskOrganizerController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.ITaskOrganizerController";

    void createRootTask(int i, int i2, android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    boolean deleteRootTask(android.window.WindowContainerToken windowContainerToken) throws android.os.RemoteException;

    java.util.List<android.app.ActivityManager.RunningTaskInfo> getChildTasks(android.window.WindowContainerToken windowContainerToken, int[] iArr) throws android.os.RemoteException;

    android.window.WindowContainerToken getImeTarget(int i) throws android.os.RemoteException;

    java.util.List<android.app.ActivityManager.RunningTaskInfo> getRootTasks(int i, int[] iArr) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.window.TaskAppearedInfo> registerTaskOrganizer(android.window.ITaskOrganizer iTaskOrganizer) throws android.os.RemoteException;

    void restartTaskTopActivityProcessIfVisible(android.window.WindowContainerToken windowContainerToken) throws android.os.RemoteException;

    void setInterceptBackPressedOnTaskRoot(android.window.WindowContainerToken windowContainerToken, boolean z) throws android.os.RemoteException;

    void unregisterTaskOrganizer(android.window.ITaskOrganizer iTaskOrganizer) throws android.os.RemoteException;

    void updateCameraCompatControlState(android.window.WindowContainerToken windowContainerToken, int i) throws android.os.RemoteException;

    public static class Default implements android.window.ITaskOrganizerController {
        @Override // android.window.ITaskOrganizerController
        public android.content.pm.ParceledListSlice<android.window.TaskAppearedInfo> registerTaskOrganizer(android.window.ITaskOrganizer iTaskOrganizer) throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.ITaskOrganizerController
        public void unregisterTaskOrganizer(android.window.ITaskOrganizer iTaskOrganizer) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizerController
        public void createRootTask(int i, int i2, android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizerController
        public boolean deleteRootTask(android.window.WindowContainerToken windowContainerToken) throws android.os.RemoteException {
            return false;
        }

        @Override // android.window.ITaskOrganizerController
        public java.util.List<android.app.ActivityManager.RunningTaskInfo> getChildTasks(android.window.WindowContainerToken windowContainerToken, int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.ITaskOrganizerController
        public java.util.List<android.app.ActivityManager.RunningTaskInfo> getRootTasks(int i, int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.ITaskOrganizerController
        public android.window.WindowContainerToken getImeTarget(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.ITaskOrganizerController
        public void setInterceptBackPressedOnTaskRoot(android.window.WindowContainerToken windowContainerToken, boolean z) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizerController
        public void restartTaskTopActivityProcessIfVisible(android.window.WindowContainerToken windowContainerToken) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskOrganizerController
        public void updateCameraCompatControlState(android.window.WindowContainerToken windowContainerToken, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.ITaskOrganizerController {
        static final int TRANSACTION_createRootTask = 3;
        static final int TRANSACTION_deleteRootTask = 4;
        static final int TRANSACTION_getChildTasks = 5;
        static final int TRANSACTION_getImeTarget = 7;
        static final int TRANSACTION_getRootTasks = 6;
        static final int TRANSACTION_registerTaskOrganizer = 1;
        static final int TRANSACTION_restartTaskTopActivityProcessIfVisible = 9;
        static final int TRANSACTION_setInterceptBackPressedOnTaskRoot = 8;
        static final int TRANSACTION_unregisterTaskOrganizer = 2;
        static final int TRANSACTION_updateCameraCompatControlState = 10;

        public Stub() {
            attachInterface(this, android.window.ITaskOrganizerController.DESCRIPTOR);
        }

        public static android.window.ITaskOrganizerController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.ITaskOrganizerController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.ITaskOrganizerController)) {
                return (android.window.ITaskOrganizerController) queryLocalInterface;
            }
            return new android.window.ITaskOrganizerController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerTaskOrganizer";
                case 2:
                    return "unregisterTaskOrganizer";
                case 3:
                    return "createRootTask";
                case 4:
                    return "deleteRootTask";
                case 5:
                    return "getChildTasks";
                case 6:
                    return "getRootTasks";
                case 7:
                    return "getImeTarget";
                case 8:
                    return "setInterceptBackPressedOnTaskRoot";
                case 9:
                    return "restartTaskTopActivityProcessIfVisible";
                case 10:
                    return "updateCameraCompatControlState";
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
                parcel.enforceInterface(android.window.ITaskOrganizerController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.ITaskOrganizerController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.window.ITaskOrganizer asInterface = android.window.ITaskOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.window.TaskAppearedInfo> registerTaskOrganizer = registerTaskOrganizer(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerTaskOrganizer, 1);
                    return true;
                case 2:
                    android.window.ITaskOrganizer asInterface2 = android.window.ITaskOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskOrganizer(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    createRootTask(readInt, readInt2, readStrongBinder, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.window.WindowContainerToken windowContainerToken = (android.window.WindowContainerToken) parcel.readTypedObject(android.window.WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean deleteRootTask = deleteRootTask(windowContainerToken);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteRootTask);
                    return true;
                case 5:
                    android.window.WindowContainerToken windowContainerToken2 = (android.window.WindowContainerToken) parcel.readTypedObject(android.window.WindowContainerToken.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.ActivityManager.RunningTaskInfo> childTasks = getChildTasks(windowContainerToken2, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(childTasks, 1);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.ActivityManager.RunningTaskInfo> rootTasks = getRootTasks(readInt3, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(rootTasks, 1);
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.window.WindowContainerToken imeTarget = getImeTarget(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(imeTarget, 1);
                    return true;
                case 8:
                    android.window.WindowContainerToken windowContainerToken3 = (android.window.WindowContainerToken) parcel.readTypedObject(android.window.WindowContainerToken.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInterceptBackPressedOnTaskRoot(windowContainerToken3, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.window.WindowContainerToken windowContainerToken4 = (android.window.WindowContainerToken) parcel.readTypedObject(android.window.WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    restartTaskTopActivityProcessIfVisible(windowContainerToken4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.window.WindowContainerToken windowContainerToken5 = (android.window.WindowContainerToken) parcel.readTypedObject(android.window.WindowContainerToken.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCameraCompatControlState(windowContainerToken5, readInt5);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.ITaskOrganizerController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.ITaskOrganizerController.DESCRIPTOR;
            }

            @Override // android.window.ITaskOrganizerController
            public android.content.pm.ParceledListSlice<android.window.TaskAppearedInfo> registerTaskOrganizer(android.window.ITaskOrganizer iTaskOrganizer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskOrganizer);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void unregisterTaskOrganizer(android.window.ITaskOrganizer iTaskOrganizer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskOrganizer);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void createRootTask(int i, int i2, android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public boolean deleteRootTask(android.window.WindowContainerToken windowContainerToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public java.util.List<android.app.ActivityManager.RunningTaskInfo> getChildTasks(android.window.WindowContainerToken windowContainerToken, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public java.util.List<android.app.ActivityManager.RunningTaskInfo> getRootTasks(int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public android.window.WindowContainerToken getImeTarget(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.window.WindowContainerToken) obtain2.readTypedObject(android.window.WindowContainerToken.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void setInterceptBackPressedOnTaskRoot(android.window.WindowContainerToken windowContainerToken, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void restartTaskTopActivityProcessIfVisible(android.window.WindowContainerToken windowContainerToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void updateCameraCompatControlState(android.window.WindowContainerToken windowContainerToken, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
