package com.android.ims.internal.uce.options;

/* loaded from: classes4.dex */
public interface IOptionsService extends android.os.IInterface {
    com.android.ims.internal.uce.common.StatusCode addListener(int i, com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode getContactCap(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode getContactListCap(int i, java.lang.String[] strArr, int i2) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode getMyInfo(int i, int i2) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode getVersion(int i) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode removeListener(int i, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode responseIncomingOptions(int i, int i2, int i3, java.lang.String str, com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo, boolean z) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode setMyInfo(int i, com.android.ims.internal.uce.common.CapInfo capInfo, int i2) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.uce.options.IOptionsService {
        @Override // com.android.ims.internal.uce.options.IOptionsService
        public com.android.ims.internal.uce.common.StatusCode getVersion(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public com.android.ims.internal.uce.common.StatusCode addListener(int i, com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public com.android.ims.internal.uce.common.StatusCode removeListener(int i, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public com.android.ims.internal.uce.common.StatusCode setMyInfo(int i, com.android.ims.internal.uce.common.CapInfo capInfo, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public com.android.ims.internal.uce.common.StatusCode getMyInfo(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public com.android.ims.internal.uce.common.StatusCode getContactCap(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public com.android.ims.internal.uce.common.StatusCode getContactListCap(int i, java.lang.String[] strArr, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public com.android.ims.internal.uce.common.StatusCode responseIncomingOptions(int i, int i2, int i3, java.lang.String str, com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.uce.options.IOptionsService {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.uce.options.IOptionsService";
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_getContactCap = 6;
        static final int TRANSACTION_getContactListCap = 7;
        static final int TRANSACTION_getMyInfo = 5;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_responseIncomingOptions = 8;
        static final int TRANSACTION_setMyInfo = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.uce.options.IOptionsService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.uce.options.IOptionsService)) {
                return (com.android.ims.internal.uce.options.IOptionsService) queryLocalInterface;
            }
            return new com.android.ims.internal.uce.options.IOptionsService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVersion";
                case 2:
                    return "addListener";
                case 3:
                    return "removeListener";
                case 4:
                    return "setMyInfo";
                case 5:
                    return "getMyInfo";
                case 6:
                    return "getContactCap";
                case 7:
                    return "getContactListCap";
                case 8:
                    return "responseIncomingOptions";
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
                    com.android.ims.internal.uce.common.StatusCode version = getVersion(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(version, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    com.android.ims.internal.uce.options.IOptionsListener asInterface = com.android.ims.internal.uce.options.IOptionsListener.Stub.asInterface(parcel.readStrongBinder());
                    com.android.ims.internal.uce.common.UceLong uceLong = (com.android.ims.internal.uce.common.UceLong) parcel.readTypedObject(com.android.ims.internal.uce.common.UceLong.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.common.StatusCode addListener = addListener(readInt2, asInterface, uceLong);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addListener, 1);
                    parcel2.writeTypedObject(uceLong, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    com.android.ims.internal.uce.common.UceLong uceLong2 = (com.android.ims.internal.uce.common.UceLong) parcel.readTypedObject(com.android.ims.internal.uce.common.UceLong.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.common.StatusCode removeListener = removeListener(readInt3, uceLong2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(removeListener, 1);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    com.android.ims.internal.uce.common.CapInfo capInfo = (com.android.ims.internal.uce.common.CapInfo) parcel.readTypedObject(com.android.ims.internal.uce.common.CapInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.common.StatusCode myInfo = setMyInfo(readInt4, capInfo, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(myInfo, 1);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.common.StatusCode myInfo2 = getMyInfo(readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(myInfo2, 1);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.common.StatusCode contactCap = getContactCap(readInt8, readString, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contactCap, 1);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.common.StatusCode contactListCap = getContactListCap(readInt10, createStringArray, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contactListCap, 1);
                    return true;
                case 8:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo = (com.android.ims.internal.uce.options.OptionsCapInfo) parcel.readTypedObject(com.android.ims.internal.uce.options.OptionsCapInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.common.StatusCode responseIncomingOptions = responseIncomingOptions(readInt12, readInt13, readInt14, readString2, optionsCapInfo, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(responseIncomingOptions, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.uce.options.IOptionsService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.uce.options.IOptionsService.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public com.android.ims.internal.uce.common.StatusCode getVersion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public com.android.ims.internal.uce.common.StatusCode addListener(int i, com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iOptionsListener);
                    obtain.writeTypedObject(uceLong, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    com.android.ims.internal.uce.common.StatusCode statusCode = (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                    if (obtain2.readInt() != 0) {
                        uceLong.readFromParcel(obtain2);
                    }
                    return statusCode;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public com.android.ims.internal.uce.common.StatusCode removeListener(int i, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uceLong, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public com.android.ims.internal.uce.common.StatusCode setMyInfo(int i, com.android.ims.internal.uce.common.CapInfo capInfo, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(capInfo, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public com.android.ims.internal.uce.common.StatusCode getMyInfo(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public com.android.ims.internal.uce.common.StatusCode getContactCap(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public com.android.ims.internal.uce.common.StatusCode getContactListCap(int i, java.lang.String[] strArr, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public com.android.ims.internal.uce.common.StatusCode responseIncomingOptions(int i, int i2, int i3, java.lang.String str, com.android.ims.internal.uce.options.OptionsCapInfo optionsCapInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.options.IOptionsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeTypedObject(optionsCapInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
