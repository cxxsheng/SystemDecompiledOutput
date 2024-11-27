package android.os;

/* loaded from: classes3.dex */
public interface IDumpstate extends android.os.IInterface {
    public static final int BUGREPORT_FLAG_DEFER_CONSENT = 2;
    public static final int BUGREPORT_FLAG_KEEP_BUGREPORT_ON_RETRIEVAL = 4;
    public static final int BUGREPORT_FLAG_USE_PREDUMPED_UI_DATA = 1;
    public static final int BUGREPORT_MODE_DEFAULT = 6;
    public static final int BUGREPORT_MODE_FULL = 0;
    public static final int BUGREPORT_MODE_INTERACTIVE = 1;
    public static final int BUGREPORT_MODE_ONBOARDING = 7;
    public static final int BUGREPORT_MODE_REMOTE = 2;
    public static final int BUGREPORT_MODE_TELEPHONY = 4;
    public static final int BUGREPORT_MODE_WEAR = 3;
    public static final int BUGREPORT_MODE_WIFI = 5;
    public static final java.lang.String DESCRIPTOR = "android.os.IDumpstate";

    void cancelBugreport(int i, java.lang.String str) throws android.os.RemoteException;

    void preDumpUiData(java.lang.String str) throws android.os.RemoteException;

    void retrieveBugreport(int i, java.lang.String str, int i2, java.io.FileDescriptor fileDescriptor, java.lang.String str2, boolean z, android.os.IDumpstateListener iDumpstateListener) throws android.os.RemoteException;

    void startBugreport(int i, java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, int i2, int i3, android.os.IDumpstateListener iDumpstateListener, boolean z) throws android.os.RemoteException;

    public static class Default implements android.os.IDumpstate {
        @Override // android.os.IDumpstate
        public void preDumpUiData(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IDumpstate
        public void startBugreport(int i, java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, int i2, int i3, android.os.IDumpstateListener iDumpstateListener, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IDumpstate
        public void cancelBugreport(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IDumpstate
        public void retrieveBugreport(int i, java.lang.String str, int i2, java.io.FileDescriptor fileDescriptor, java.lang.String str2, boolean z, android.os.IDumpstateListener iDumpstateListener) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IDumpstate {
        static final int TRANSACTION_cancelBugreport = 3;
        static final int TRANSACTION_preDumpUiData = 1;
        static final int TRANSACTION_retrieveBugreport = 4;
        static final int TRANSACTION_startBugreport = 2;

        public Stub() {
            attachInterface(this, android.os.IDumpstate.DESCRIPTOR);
        }

        public static android.os.IDumpstate asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IDumpstate.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IDumpstate)) {
                return (android.os.IDumpstate) queryLocalInterface;
            }
            return new android.os.IDumpstate.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "preDumpUiData";
                case 2:
                    return "startBugreport";
                case 3:
                    return "cancelBugreport";
                case 4:
                    return "retrieveBugreport";
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
                parcel.enforceInterface(android.os.IDumpstate.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IDumpstate.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    preDumpUiData(readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    java.io.FileDescriptor readRawFileDescriptor = parcel.readRawFileDescriptor();
                    java.io.FileDescriptor readRawFileDescriptor2 = parcel.readRawFileDescriptor();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    android.os.IDumpstateListener asInterface = android.os.IDumpstateListener.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startBugreport(readInt, readString2, readRawFileDescriptor, readRawFileDescriptor2, readInt2, readInt3, asInterface, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelBugreport(readInt4, readString3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    java.io.FileDescriptor readRawFileDescriptor3 = parcel.readRawFileDescriptor();
                    java.lang.String readString5 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    android.os.IDumpstateListener asInterface2 = android.os.IDumpstateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    retrieveBugreport(readInt5, readString4, readInt6, readRawFileDescriptor3, readString5, readBoolean2, asInterface2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IDumpstate {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IDumpstate.DESCRIPTOR;
            }

            @Override // android.os.IDumpstate
            public void preDumpUiData(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IDumpstate.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public void startBugreport(int i, java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, int i2, int i3, android.os.IDumpstateListener iDumpstateListener, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IDumpstate.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    obtain.writeRawFileDescriptor(fileDescriptor2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iDumpstateListener);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public void cancelBugreport(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IDumpstate.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDumpstate
            public void retrieveBugreport(int i, java.lang.String str, int i2, java.io.FileDescriptor fileDescriptor, java.lang.String str2, boolean z, android.os.IDumpstateListener iDumpstateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IDumpstate.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iDumpstateListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
