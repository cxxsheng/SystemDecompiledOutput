package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsService extends android.os.IInterface {
    void addRegistrationListener(int i, int i2, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException;

    void close(int i) throws android.os.RemoteException;

    android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2, int i3) throws android.os.RemoteException;

    com.android.ims.internal.IImsCallSession createCallSession(int i, android.telephony.ims.ImsCallProfile imsCallProfile, com.android.ims.internal.IImsCallSessionListener iImsCallSessionListener) throws android.os.RemoteException;

    com.android.ims.internal.IImsConfig getConfigInterface(int i) throws android.os.RemoteException;

    com.android.ims.internal.IImsEcbm getEcbmInterface(int i) throws android.os.RemoteException;

    com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface(int i) throws android.os.RemoteException;

    com.android.ims.internal.IImsCallSession getPendingCallSession(int i, java.lang.String str) throws android.os.RemoteException;

    com.android.ims.internal.IImsUt getUtInterface(int i) throws android.os.RemoteException;

    boolean isConnected(int i, int i2, int i3) throws android.os.RemoteException;

    boolean isOpened(int i) throws android.os.RemoteException;

    int open(int i, int i2, android.app.PendingIntent pendingIntent, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException;

    void setRegistrationListener(int i, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException;

    void setUiTTYMode(int i, int i2, android.os.Message message) throws android.os.RemoteException;

    void turnOffIms(int i) throws android.os.RemoteException;

    void turnOnIms(int i) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsService {
        @Override // com.android.ims.internal.IImsService
        public int open(int i, int i2, android.app.PendingIntent pendingIntent, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsService
        public void close(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public boolean isConnected(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsService
        public boolean isOpened(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsService
        public void setRegistrationListener(int i, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void addRegistrationListener(int i, int i2, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public com.android.ims.internal.IImsCallSession createCallSession(int i, android.telephony.ims.ImsCallProfile imsCallProfile, com.android.ims.internal.IImsCallSessionListener iImsCallSessionListener) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public com.android.ims.internal.IImsCallSession getPendingCallSession(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public com.android.ims.internal.IImsUt getUtInterface(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public com.android.ims.internal.IImsConfig getConfigInterface(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public void turnOnIms(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void turnOffIms(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public com.android.ims.internal.IImsEcbm getEcbmInterface(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public void setUiTTYMode(int i, int i2, android.os.Message message) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsService {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsService";
        static final int TRANSACTION_addRegistrationListener = 6;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_createCallProfile = 7;
        static final int TRANSACTION_createCallSession = 8;
        static final int TRANSACTION_getConfigInterface = 11;
        static final int TRANSACTION_getEcbmInterface = 14;
        static final int TRANSACTION_getMultiEndpointInterface = 16;
        static final int TRANSACTION_getPendingCallSession = 9;
        static final int TRANSACTION_getUtInterface = 10;
        static final int TRANSACTION_isConnected = 3;
        static final int TRANSACTION_isOpened = 4;
        static final int TRANSACTION_open = 1;
        static final int TRANSACTION_setRegistrationListener = 5;
        static final int TRANSACTION_setUiTTYMode = 15;
        static final int TRANSACTION_turnOffIms = 13;
        static final int TRANSACTION_turnOnIms = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsService)) {
                return (com.android.ims.internal.IImsService) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.telephony.ims.RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN;
                case 2:
                    return "close";
                case 3:
                    return "isConnected";
                case 4:
                    return "isOpened";
                case 5:
                    return "setRegistrationListener";
                case 6:
                    return "addRegistrationListener";
                case 7:
                    return "createCallProfile";
                case 8:
                    return "createCallSession";
                case 9:
                    return "getPendingCallSession";
                case 10:
                    return "getUtInterface";
                case 11:
                    return "getConfigInterface";
                case 12:
                    return "turnOnIms";
                case 13:
                    return "turnOffIms";
                case 14:
                    return "getEcbmInterface";
                case 15:
                    return "setUiTTYMode";
                case 16:
                    return "getMultiEndpointInterface";
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
                    int readInt2 = parcel.readInt();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    com.android.ims.internal.IImsRegistrationListener asInterface = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int open = open(readInt, readInt2, pendingIntent, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(open);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    close(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isConnected = isConnected(readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConnected);
                    return true;
                case 4:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isOpened = isOpened(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOpened);
                    return true;
                case 5:
                    int readInt8 = parcel.readInt();
                    com.android.ims.internal.IImsRegistrationListener asInterface2 = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setRegistrationListener(readInt8, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    com.android.ims.internal.IImsRegistrationListener asInterface3 = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addRegistrationListener(readInt9, readInt10, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.ImsCallProfile createCallProfile = createCallProfile(readInt11, readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createCallProfile, 1);
                    return true;
                case 8:
                    int readInt14 = parcel.readInt();
                    android.telephony.ims.ImsCallProfile imsCallProfile = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    com.android.ims.internal.IImsCallSessionListener asInterface4 = com.android.ims.internal.IImsCallSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsCallSession createCallSession = createCallSession(readInt14, imsCallProfile, asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createCallSession);
                    return true;
                case 9:
                    int readInt15 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsCallSession pendingCallSession = getPendingCallSession(readInt15, readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(pendingCallSession);
                    return true;
                case 10:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsUt utInterface = getUtInterface(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(utInterface);
                    return true;
                case 11:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsConfig configInterface = getConfigInterface(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(configInterface);
                    return true;
                case 12:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    turnOnIms(readInt18);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    turnOffIms(readInt19);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsEcbm ecbmInterface = getEcbmInterface(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(ecbmInterface);
                    return true;
                case 15:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    android.os.Message message = (android.os.Message) parcel.readTypedObject(android.os.Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiTTYMode(readInt21, readInt22, message);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsMultiEndpoint multiEndpointInterface = getMultiEndpointInterface(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(multiEndpointInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsService.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsService
            public int open(int i, int i2, android.app.PendingIntent pendingIntent, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void close(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public boolean isConnected(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public boolean isOpened(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void setRegistrationListener(int i, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void addRegistrationListener(int i, int i2, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.ImsCallProfile) obtain2.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public com.android.ims.internal.IImsCallSession createCallSession(int i, android.telephony.ims.ImsCallProfile imsCallProfile, com.android.ims.internal.IImsCallSessionListener iImsCallSessionListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    obtain.writeStrongInterface(iImsCallSessionListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public com.android.ims.internal.IImsCallSession getPendingCallSession(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public com.android.ims.internal.IImsUt getUtInterface(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsUt.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public com.android.ims.internal.IImsConfig getConfigInterface(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsConfig.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void turnOnIms(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void turnOffIms(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public com.android.ims.internal.IImsEcbm getEcbmInterface(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsEcbm.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void setUiTTYMode(int i, int i2, android.os.Message message) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsMultiEndpoint.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }
    }
}
