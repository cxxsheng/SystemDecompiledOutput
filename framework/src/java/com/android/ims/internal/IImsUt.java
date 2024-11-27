package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsUt extends android.os.IInterface {
    void close() throws android.os.RemoteException;

    int queryCLIP() throws android.os.RemoteException;

    int queryCLIR() throws android.os.RemoteException;

    int queryCOLP() throws android.os.RemoteException;

    int queryCOLR() throws android.os.RemoteException;

    int queryCallBarring(int i) throws android.os.RemoteException;

    int queryCallBarringForServiceClass(int i, int i2) throws android.os.RemoteException;

    int queryCallForward(int i, java.lang.String str) throws android.os.RemoteException;

    int queryCallWaiting() throws android.os.RemoteException;

    void setListener(com.android.ims.internal.IImsUtListener iImsUtListener) throws android.os.RemoteException;

    int transact(android.os.Bundle bundle) throws android.os.RemoteException;

    int updateCLIP(boolean z) throws android.os.RemoteException;

    int updateCLIR(int i) throws android.os.RemoteException;

    int updateCOLP(boolean z) throws android.os.RemoteException;

    int updateCOLR(int i) throws android.os.RemoteException;

    int updateCallBarring(int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException;

    int updateCallBarringForServiceClass(int i, int i2, java.lang.String[] strArr, int i3) throws android.os.RemoteException;

    int updateCallBarringWithPassword(int i, int i2, java.lang.String[] strArr, int i3, java.lang.String str) throws android.os.RemoteException;

    int updateCallForward(int i, int i2, java.lang.String str, int i3, int i4) throws android.os.RemoteException;

    int updateCallWaiting(boolean z, int i) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsUt {
        @Override // com.android.ims.internal.IImsUt
        public void close() throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallBarring(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallForward(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallWaiting() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCLIR() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCLIP() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCOLR() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCOLP() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int transact(android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallBarring(int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallForward(int i, int i2, java.lang.String str, int i3, int i4) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallWaiting(boolean z, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCLIR(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCLIP(boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCOLR(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCOLP(boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public void setListener(com.android.ims.internal.IImsUtListener iImsUtListener) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallBarringForServiceClass(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallBarringForServiceClass(int i, int i2, java.lang.String[] strArr, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallBarringWithPassword(int i, int i2, java.lang.String[] strArr, int i3, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsUt {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsUt";
        static final int TRANSACTION_close = 1;
        static final int TRANSACTION_queryCLIP = 6;
        static final int TRANSACTION_queryCLIR = 5;
        static final int TRANSACTION_queryCOLP = 8;
        static final int TRANSACTION_queryCOLR = 7;
        static final int TRANSACTION_queryCallBarring = 2;
        static final int TRANSACTION_queryCallBarringForServiceClass = 18;
        static final int TRANSACTION_queryCallForward = 3;
        static final int TRANSACTION_queryCallWaiting = 4;
        static final int TRANSACTION_setListener = 17;
        static final int TRANSACTION_transact = 9;
        static final int TRANSACTION_updateCLIP = 14;
        static final int TRANSACTION_updateCLIR = 13;
        static final int TRANSACTION_updateCOLP = 16;
        static final int TRANSACTION_updateCOLR = 15;
        static final int TRANSACTION_updateCallBarring = 10;
        static final int TRANSACTION_updateCallBarringForServiceClass = 19;
        static final int TRANSACTION_updateCallBarringWithPassword = 20;
        static final int TRANSACTION_updateCallForward = 11;
        static final int TRANSACTION_updateCallWaiting = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsUt asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsUt)) {
                return (com.android.ims.internal.IImsUt) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsUt.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "close";
                case 2:
                    return "queryCallBarring";
                case 3:
                    return "queryCallForward";
                case 4:
                    return "queryCallWaiting";
                case 5:
                    return "queryCLIR";
                case 6:
                    return "queryCLIP";
                case 7:
                    return "queryCOLR";
                case 8:
                    return "queryCOLP";
                case 9:
                    return "transact";
                case 10:
                    return "updateCallBarring";
                case 11:
                    return "updateCallForward";
                case 12:
                    return "updateCallWaiting";
                case 13:
                    return "updateCLIR";
                case 14:
                    return "updateCLIP";
                case 15:
                    return "updateCOLR";
                case 16:
                    return "updateCOLP";
                case 17:
                    return "setListener";
                case 18:
                    return "queryCallBarringForServiceClass";
                case 19:
                    return "updateCallBarringForServiceClass";
                case 20:
                    return "updateCallBarringWithPassword";
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
                    close();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int queryCallBarring = queryCallBarring(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCallBarring);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int queryCallForward = queryCallForward(readInt2, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCallForward);
                    return true;
                case 4:
                    int queryCallWaiting = queryCallWaiting();
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCallWaiting);
                    return true;
                case 5:
                    int queryCLIR = queryCLIR();
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCLIR);
                    return true;
                case 6:
                    int queryCLIP = queryCLIP();
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCLIP);
                    return true;
                case 7:
                    int queryCOLR = queryCOLR();
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCOLR);
                    return true;
                case 8:
                    int queryCOLP = queryCOLP();
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCOLP);
                    return true;
                case 9:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int transact = transact(bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(transact);
                    return true;
                case 10:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int updateCallBarring = updateCallBarring(readInt3, readInt4, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCallBarring);
                    return true;
                case 11:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateCallForward = updateCallForward(readInt5, readInt6, readString2, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCallForward);
                    return true;
                case 12:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateCallWaiting = updateCallWaiting(readBoolean, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCallWaiting);
                    return true;
                case 13:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateCLIR = updateCLIR(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCLIR);
                    return true;
                case 14:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int updateCLIP = updateCLIP(readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCLIP);
                    return true;
                case 15:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateCOLR = updateCOLR(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCOLR);
                    return true;
                case 16:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int updateCOLP = updateCOLP(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCOLP);
                    return true;
                case 17:
                    com.android.ims.internal.IImsUtListener asInterface = com.android.ims.internal.IImsUtListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int queryCallBarringForServiceClass = queryCallBarringForServiceClass(readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCallBarringForServiceClass);
                    return true;
                case 19:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateCallBarringForServiceClass = updateCallBarringForServiceClass(readInt14, readInt15, createStringArray2, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCallBarringForServiceClass);
                    return true;
                case 20:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    int readInt19 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int updateCallBarringWithPassword = updateCallBarringWithPassword(readInt17, readInt18, createStringArray3, readInt19, readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCallBarringWithPassword);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsUt {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsUt.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsUt
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int queryCallBarring(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int queryCallForward(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int queryCallWaiting() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int queryCLIR() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int queryCLIP() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int queryCOLR() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int queryCOLP() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int transact(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int updateCallBarring(int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int updateCallForward(int i, int i2, java.lang.String str, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int updateCallWaiting(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int updateCLIR(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int updateCLIP(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int updateCOLR(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int updateCOLP(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public void setListener(com.android.ims.internal.IImsUtListener iImsUtListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsUtListener);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int queryCallBarringForServiceClass(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int updateCallBarringForServiceClass(int i, int i2, java.lang.String[] strArr, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i3);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUt
            public int updateCallBarringWithPassword(int i, int i2, java.lang.String[] strArr, int i3, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsUt.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
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
            return 19;
        }
    }
}
