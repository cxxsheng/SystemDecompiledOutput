package android.app;

/* loaded from: classes.dex */
public interface IAppTask extends android.os.IInterface {
    void finishAndRemoveTask() throws android.os.RemoteException;

    android.app.ActivityManager.RecentTaskInfo getTaskInfo() throws android.os.RemoteException;

    void moveToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str) throws android.os.RemoteException;

    void setExcludeFromRecents(boolean z) throws android.os.RemoteException;

    int startActivity(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.app.IAppTask {
        @Override // android.app.IAppTask
        public void finishAndRemoveTask() throws android.os.RemoteException {
        }

        @Override // android.app.IAppTask
        public android.app.ActivityManager.RecentTaskInfo getTaskInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IAppTask
        public void moveToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IAppTask
        public int startActivity(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IAppTask
        public void setExcludeFromRecents(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IAppTask {
        public static final java.lang.String DESCRIPTOR = "android.app.IAppTask";
        static final int TRANSACTION_finishAndRemoveTask = 1;
        static final int TRANSACTION_getTaskInfo = 2;
        static final int TRANSACTION_moveToFront = 3;
        static final int TRANSACTION_setExcludeFromRecents = 5;
        static final int TRANSACTION_startActivity = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IAppTask asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IAppTask)) {
                return (android.app.IAppTask) queryLocalInterface;
            }
            return new android.app.IAppTask.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "finishAndRemoveTask";
                case 2:
                    return "getTaskInfo";
                case 3:
                    return "moveToFront";
                case 4:
                    return "startActivity";
                case 5:
                    return "setExcludeFromRecents";
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
                    finishAndRemoveTask();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.app.ActivityManager.RecentTaskInfo taskInfo = getTaskInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskInfo, 1);
                    return true;
                case 3:
                    android.app.IApplicationThread asInterface = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    moveToFront(asInterface, readString);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString4 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivity = startActivity(readStrongBinder, readString2, readString3, intent, readString4, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivity);
                    return true;
                case 5:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setExcludeFromRecents(readBoolean);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IAppTask {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IAppTask.Stub.DESCRIPTOR;
            }

            @Override // android.app.IAppTask
            public void finishAndRemoveTask() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAppTask.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAppTask
            public android.app.ActivityManager.RecentTaskInfo getTaskInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAppTask.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.ActivityManager.RecentTaskInfo) obtain2.readTypedObject(android.app.ActivityManager.RecentTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAppTask
            public void moveToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAppTask.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAppTask
            public int startActivity(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAppTask.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAppTask
            public void setExcludeFromRecents(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAppTask.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
