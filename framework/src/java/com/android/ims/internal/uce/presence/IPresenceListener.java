package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public interface IPresenceListener extends android.os.IInterface {
    void capInfoReceived(java.lang.String str, com.android.ims.internal.uce.presence.PresTupleInfo[] presTupleInfoArr) throws android.os.RemoteException;

    void cmdStatus(com.android.ims.internal.uce.presence.PresCmdStatus presCmdStatus) throws android.os.RemoteException;

    void getVersionCb(java.lang.String str) throws android.os.RemoteException;

    void listCapInfoReceived(com.android.ims.internal.uce.presence.PresRlmiInfo presRlmiInfo, com.android.ims.internal.uce.presence.PresResInfo[] presResInfoArr) throws android.os.RemoteException;

    void publishTriggering(com.android.ims.internal.uce.presence.PresPublishTriggerType presPublishTriggerType) throws android.os.RemoteException;

    void serviceAvailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException;

    void serviceUnAvailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException;

    void sipResponseReceived(com.android.ims.internal.uce.presence.PresSipResponse presSipResponse) throws android.os.RemoteException;

    void unpublishMessageSent() throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.uce.presence.IPresenceListener {
        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void getVersionCb(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void serviceAvailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void serviceUnAvailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void publishTriggering(com.android.ims.internal.uce.presence.PresPublishTriggerType presPublishTriggerType) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void cmdStatus(com.android.ims.internal.uce.presence.PresCmdStatus presCmdStatus) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void sipResponseReceived(com.android.ims.internal.uce.presence.PresSipResponse presSipResponse) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void capInfoReceived(java.lang.String str, com.android.ims.internal.uce.presence.PresTupleInfo[] presTupleInfoArr) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void listCapInfoReceived(com.android.ims.internal.uce.presence.PresRlmiInfo presRlmiInfo, com.android.ims.internal.uce.presence.PresResInfo[] presResInfoArr) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void unpublishMessageSent() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.uce.presence.IPresenceListener {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.uce.presence.IPresenceListener";
        static final int TRANSACTION_capInfoReceived = 7;
        static final int TRANSACTION_cmdStatus = 5;
        static final int TRANSACTION_getVersionCb = 1;
        static final int TRANSACTION_listCapInfoReceived = 8;
        static final int TRANSACTION_publishTriggering = 4;
        static final int TRANSACTION_serviceAvailable = 2;
        static final int TRANSACTION_serviceUnAvailable = 3;
        static final int TRANSACTION_sipResponseReceived = 6;
        static final int TRANSACTION_unpublishMessageSent = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.uce.presence.IPresenceListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.uce.presence.IPresenceListener)) {
                return (com.android.ims.internal.uce.presence.IPresenceListener) queryLocalInterface;
            }
            return new com.android.ims.internal.uce.presence.IPresenceListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVersionCb";
                case 2:
                    return "serviceAvailable";
                case 3:
                    return "serviceUnAvailable";
                case 4:
                    return "publishTriggering";
                case 5:
                    return "cmdStatus";
                case 6:
                    return "sipResponseReceived";
                case 7:
                    return "capInfoReceived";
                case 8:
                    return "listCapInfoReceived";
                case 9:
                    return "unpublishMessageSent";
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
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getVersionCb(readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    com.android.ims.internal.uce.common.StatusCode statusCode = (com.android.ims.internal.uce.common.StatusCode) parcel.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    serviceAvailable(statusCode);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    com.android.ims.internal.uce.common.StatusCode statusCode2 = (com.android.ims.internal.uce.common.StatusCode) parcel.readTypedObject(com.android.ims.internal.uce.common.StatusCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    serviceUnAvailable(statusCode2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    com.android.ims.internal.uce.presence.PresPublishTriggerType presPublishTriggerType = (com.android.ims.internal.uce.presence.PresPublishTriggerType) parcel.readTypedObject(com.android.ims.internal.uce.presence.PresPublishTriggerType.CREATOR);
                    parcel.enforceNoDataAvail();
                    publishTriggering(presPublishTriggerType);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    com.android.ims.internal.uce.presence.PresCmdStatus presCmdStatus = (com.android.ims.internal.uce.presence.PresCmdStatus) parcel.readTypedObject(com.android.ims.internal.uce.presence.PresCmdStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    cmdStatus(presCmdStatus);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    com.android.ims.internal.uce.presence.PresSipResponse presSipResponse = (com.android.ims.internal.uce.presence.PresSipResponse) parcel.readTypedObject(com.android.ims.internal.uce.presence.PresSipResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    sipResponseReceived(presSipResponse);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString2 = parcel.readString();
                    com.android.ims.internal.uce.presence.PresTupleInfo[] presTupleInfoArr = (com.android.ims.internal.uce.presence.PresTupleInfo[]) parcel.createTypedArray(com.android.ims.internal.uce.presence.PresTupleInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    capInfoReceived(readString2, presTupleInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    com.android.ims.internal.uce.presence.PresRlmiInfo presRlmiInfo = (com.android.ims.internal.uce.presence.PresRlmiInfo) parcel.readTypedObject(com.android.ims.internal.uce.presence.PresRlmiInfo.CREATOR);
                    com.android.ims.internal.uce.presence.PresResInfo[] presResInfoArr = (com.android.ims.internal.uce.presence.PresResInfo[]) parcel.createTypedArray(com.android.ims.internal.uce.presence.PresResInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    listCapInfoReceived(presRlmiInfo, presResInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    unpublishMessageSent();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.uce.presence.IPresenceListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.uce.presence.IPresenceListener.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void getVersionCb(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void serviceAvailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(statusCode, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void serviceUnAvailable(com.android.ims.internal.uce.common.StatusCode statusCode) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(statusCode, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void publishTriggering(com.android.ims.internal.uce.presence.PresPublishTriggerType presPublishTriggerType) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(presPublishTriggerType, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void cmdStatus(com.android.ims.internal.uce.presence.PresCmdStatus presCmdStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(presCmdStatus, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void sipResponseReceived(com.android.ims.internal.uce.presence.PresSipResponse presSipResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(presSipResponse, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void capInfoReceived(java.lang.String str, com.android.ims.internal.uce.presence.PresTupleInfo[] presTupleInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedArray(presTupleInfoArr, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void listCapInfoReceived(com.android.ims.internal.uce.presence.PresRlmiInfo presRlmiInfo, com.android.ims.internal.uce.presence.PresResInfo[] presResInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(presRlmiInfo, 0);
                    obtain.writeTypedArray(presResInfoArr, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void unpublishMessageSent() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.presence.IPresenceListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
