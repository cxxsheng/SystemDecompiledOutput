package android.view;

/* loaded from: classes4.dex */
public interface IRecentsAnimationController extends android.os.IInterface {
    void animateNavigationBarToApp(long j) throws android.os.RemoteException;

    void cleanupScreenshot() throws android.os.RemoteException;

    void detachNavigationBarFromApp(boolean z) throws android.os.RemoteException;

    void finish(boolean z, boolean z2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    boolean removeTask(int i) throws android.os.RemoteException;

    android.window.TaskSnapshot screenshotTask(int i) throws android.os.RemoteException;

    void setAnimationTargetsBehindSystemBars(boolean z) throws android.os.RemoteException;

    void setDeferCancelUntilNextTransition(boolean z, boolean z2) throws android.os.RemoteException;

    void setFinishTaskTransaction(int i, android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException;

    void setInputConsumerEnabled(boolean z) throws android.os.RemoteException;

    void setWillFinishToHome(boolean z) throws android.os.RemoteException;

    public static class Default implements android.view.IRecentsAnimationController {
        @Override // android.view.IRecentsAnimationController
        public android.window.TaskSnapshot screenshotTask(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IRecentsAnimationController
        public void setFinishTaskTransaction(int i, android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
        }

        @Override // android.view.IRecentsAnimationController
        public void finish(boolean z, boolean z2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.IRecentsAnimationController
        public void setInputConsumerEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IRecentsAnimationController
        public void setAnimationTargetsBehindSystemBars(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IRecentsAnimationController
        public void cleanupScreenshot() throws android.os.RemoteException {
        }

        @Override // android.view.IRecentsAnimationController
        public void setDeferCancelUntilNextTransition(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.view.IRecentsAnimationController
        public void setWillFinishToHome(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IRecentsAnimationController
        public boolean removeTask(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IRecentsAnimationController
        public void detachNavigationBarFromApp(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IRecentsAnimationController
        public void animateNavigationBarToApp(long j) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IRecentsAnimationController {
        public static final java.lang.String DESCRIPTOR = "android.view.IRecentsAnimationController";
        static final int TRANSACTION_animateNavigationBarToApp = 11;
        static final int TRANSACTION_cleanupScreenshot = 6;
        static final int TRANSACTION_detachNavigationBarFromApp = 10;
        static final int TRANSACTION_finish = 3;
        static final int TRANSACTION_removeTask = 9;
        static final int TRANSACTION_screenshotTask = 1;
        static final int TRANSACTION_setAnimationTargetsBehindSystemBars = 5;
        static final int TRANSACTION_setDeferCancelUntilNextTransition = 7;
        static final int TRANSACTION_setFinishTaskTransaction = 2;
        static final int TRANSACTION_setInputConsumerEnabled = 4;
        static final int TRANSACTION_setWillFinishToHome = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.IRecentsAnimationController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IRecentsAnimationController)) {
                return (android.view.IRecentsAnimationController) queryLocalInterface;
            }
            return new android.view.IRecentsAnimationController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "screenshotTask";
                case 2:
                    return "setFinishTaskTransaction";
                case 3:
                    return "finish";
                case 4:
                    return "setInputConsumerEnabled";
                case 5:
                    return "setAnimationTargetsBehindSystemBars";
                case 6:
                    return "cleanupScreenshot";
                case 7:
                    return "setDeferCancelUntilNextTransition";
                case 8:
                    return "setWillFinishToHome";
                case 9:
                    return "removeTask";
                case 10:
                    return "detachNavigationBarFromApp";
                case 11:
                    return "animateNavigationBarToApp";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.window.TaskSnapshot screenshotTask = screenshotTask(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(screenshotTask, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction = (android.window.PictureInPictureSurfaceTransaction) parcel.readTypedObject(android.window.PictureInPictureSurfaceTransaction.CREATOR);
                    android.view.SurfaceControl surfaceControl = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    setFinishTaskTransaction(readInt2, pictureInPictureSurfaceTransaction, surfaceControl);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    com.android.internal.os.IResultReceiver asInterface = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finish(readBoolean, readBoolean2, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInputConsumerEnabled(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAnimationTargetsBehindSystemBars(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    cleanupScreenshot();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean readBoolean5 = parcel.readBoolean();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeferCancelUntilNextTransition(readBoolean5, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setWillFinishToHome(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeTask = removeTask(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeTask);
                    return true;
                case 10:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    detachNavigationBarFromApp(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    animateNavigationBarToApp(readLong);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IRecentsAnimationController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IRecentsAnimationController.Stub.DESCRIPTOR;
            }

            @Override // android.view.IRecentsAnimationController
            public android.window.TaskSnapshot screenshotTask(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.window.TaskSnapshot) obtain2.readTypedObject(android.window.TaskSnapshot.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setFinishTaskTransaction(int i, android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pictureInPictureSurfaceTransaction, 0);
                    obtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void finish(boolean z, boolean z2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setInputConsumerEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setAnimationTargetsBehindSystemBars(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void cleanupScreenshot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setDeferCancelUntilNextTransition(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void setWillFinishToHome(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public boolean removeTask(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void detachNavigationBarFromApp(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationController
            public void animateNavigationBarToApp(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationController.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
