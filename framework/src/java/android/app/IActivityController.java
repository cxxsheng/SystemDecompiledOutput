package android.app;

/* loaded from: classes.dex */
public interface IActivityController extends android.os.IInterface {
    boolean activityResuming(java.lang.String str) throws android.os.RemoteException;

    boolean activityStarting(android.content.Intent intent, java.lang.String str) throws android.os.RemoteException;

    boolean appCrashed(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, long j, java.lang.String str4) throws android.os.RemoteException;

    int appEarlyNotResponding(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    int appNotResponding(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    int systemNotResponding(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.app.IActivityController {
        @Override // android.app.IActivityController
        public boolean activityStarting(android.content.Intent intent, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityController
        public boolean activityResuming(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityController
        public boolean appCrashed(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, long j, java.lang.String str4) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityController
        public int appEarlyNotResponding(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityController
        public int appNotResponding(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityController
        public int systemNotResponding(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IActivityController {
        public static final java.lang.String DESCRIPTOR = "android.app.IActivityController";
        static final int TRANSACTION_activityResuming = 2;
        static final int TRANSACTION_activityStarting = 1;
        static final int TRANSACTION_appCrashed = 3;
        static final int TRANSACTION_appEarlyNotResponding = 4;
        static final int TRANSACTION_appNotResponding = 5;
        static final int TRANSACTION_systemNotResponding = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IActivityController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IActivityController)) {
                return (android.app.IActivityController) queryLocalInterface;
            }
            return new android.app.IActivityController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "activityStarting";
                case 2:
                    return "activityResuming";
                case 3:
                    return "appCrashed";
                case 4:
                    return "appEarlyNotResponding";
                case 5:
                    return "appNotResponding";
                case 6:
                    return "systemNotResponding";
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
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean activityStarting = activityStarting(intent, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activityStarting);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean activityResuming = activityResuming(readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activityResuming);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    long readLong = parcel.readLong();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean appCrashed = appCrashed(readString3, readInt, readString4, readString5, readLong, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appCrashed);
                    return true;
                case 4:
                    java.lang.String readString7 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int appEarlyNotResponding = appEarlyNotResponding(readString7, readInt2, readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(appEarlyNotResponding);
                    return true;
                case 5:
                    java.lang.String readString9 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int appNotResponding = appNotResponding(readString9, readInt3, readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(appNotResponding);
                    return true;
                case 6:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int systemNotResponding = systemNotResponding(readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(systemNotResponding);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IActivityController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IActivityController.Stub.DESCRIPTOR;
            }

            @Override // android.app.IActivityController
            public boolean activityStarting(android.content.Intent intent, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityController.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public boolean activityResuming(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public boolean appCrashed(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, long j, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeLong(j);
                    obtain.writeString(str4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public int appEarlyNotResponding(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public int appNotResponding(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityController
            public int systemNotResponding(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
