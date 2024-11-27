package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public interface IPresenceService extends android.os.IInterface {
    com.android.ims.internal.uce.common.StatusCode addListener(int i, com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode getContactCap(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode getContactListCap(int i, java.lang.String[] strArr, int i2) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode getVersion(int i) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode publishMyCap(int i, com.android.ims.internal.uce.presence.PresCapInfo presCapInfo, int i2) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode reenableService(int i, int i2) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode removeListener(int i, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException;

    com.android.ims.internal.uce.common.StatusCode setNewFeatureTag(int i, java.lang.String str, com.android.ims.internal.uce.presence.PresServiceInfo presServiceInfo, int i2) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.uce.presence.IPresenceService {
        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public com.android.ims.internal.uce.common.StatusCode getVersion(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public com.android.ims.internal.uce.common.StatusCode addListener(int i, com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public com.android.ims.internal.uce.common.StatusCode removeListener(int i, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public com.android.ims.internal.uce.common.StatusCode reenableService(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public com.android.ims.internal.uce.common.StatusCode publishMyCap(int i, com.android.ims.internal.uce.presence.PresCapInfo presCapInfo, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public com.android.ims.internal.uce.common.StatusCode getContactCap(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public com.android.ims.internal.uce.common.StatusCode getContactListCap(int i, java.lang.String[] strArr, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public com.android.ims.internal.uce.common.StatusCode setNewFeatureTag(int i, java.lang.String str, com.android.ims.internal.uce.presence.PresServiceInfo presServiceInfo, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.uce.presence.IPresenceService {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.uce.presence.IPresenceService";
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_getContactCap = 6;
        static final int TRANSACTION_getContactListCap = 7;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_publishMyCap = 5;
        static final int TRANSACTION_reenableService = 4;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_setNewFeatureTag = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.uce.presence.IPresenceService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.uce.presence.IPresenceService)) {
                return (com.android.ims.internal.uce.presence.IPresenceService) queryLocalInterface;
            }
            return new com.android.ims.internal.uce.presence.IPresenceService.Stub.Proxy(iBinder);
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
                    return "reenableService";
                case 5:
                    return "publishMyCap";
                case 6:
                    return "getContactCap";
                case 7:
                    return "getContactListCap";
                case 8:
                    return "setNewFeatureTag";
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
                    com.android.ims.internal.uce.presence.IPresenceListener asInterface = com.android.ims.internal.uce.presence.IPresenceListener.Stub.asInterface(parcel.readStrongBinder());
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
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.common.StatusCode reenableService = reenableService(readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(reenableService, 1);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    com.android.ims.internal.uce.presence.PresCapInfo presCapInfo = (com.android.ims.internal.uce.presence.PresCapInfo) parcel.readTypedObject(com.android.ims.internal.uce.presence.PresCapInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.common.StatusCode publishMyCap = publishMyCap(readInt6, presCapInfo, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(publishMyCap, 1);
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
                    java.lang.String readString2 = parcel.readString();
                    com.android.ims.internal.uce.presence.PresServiceInfo presServiceInfo = (com.android.ims.internal.uce.presence.PresServiceInfo) parcel.readTypedObject(com.android.ims.internal.uce.presence.PresServiceInfo.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.common.StatusCode newFeatureTag = setNewFeatureTag(readInt12, readString2, presServiceInfo, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(newFeatureTag, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.uce.presence.IPresenceService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.uce.presence.IPresenceService.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public com.android.ims.internal.uce.common.StatusCode getVersion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public com.android.ims.internal.uce.common.StatusCode addListener(int i, com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPresenceListener);
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

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public com.android.ims.internal.uce.common.StatusCode removeListener(int i, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceService.Stub.DESCRIPTOR);
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

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public com.android.ims.internal.uce.common.StatusCode reenableService(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public com.android.ims.internal.uce.common.StatusCode publishMyCap(int i, com.android.ims.internal.uce.presence.PresCapInfo presCapInfo, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(presCapInfo, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.ims.internal.uce.common.StatusCode) obtain2.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public com.android.ims.internal.uce.common.StatusCode getContactCap(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceService.Stub.DESCRIPTOR);
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

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public com.android.ims.internal.uce.common.StatusCode getContactListCap(int i, java.lang.String[] strArr, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceService.Stub.DESCRIPTOR);
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

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public com.android.ims.internal.uce.common.StatusCode setNewFeatureTag(int i, java.lang.String str, com.android.ims.internal.uce.presence.PresServiceInfo presServiceInfo, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(presServiceInfo, 0);
                    obtain.writeInt(i2);
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
