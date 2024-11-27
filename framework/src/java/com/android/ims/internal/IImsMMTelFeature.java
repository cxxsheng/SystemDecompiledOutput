package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsMMTelFeature extends android.os.IInterface {
    void addRegistrationListener(com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException;

    android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2, int i3) throws android.os.RemoteException;

    com.android.ims.internal.IImsCallSession createCallSession(int i, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void endSession(int i) throws android.os.RemoteException;

    com.android.ims.internal.IImsConfig getConfigInterface() throws android.os.RemoteException;

    com.android.ims.internal.IImsEcbm getEcbmInterface() throws android.os.RemoteException;

    int getFeatureStatus() throws android.os.RemoteException;

    com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface() throws android.os.RemoteException;

    com.android.ims.internal.IImsCallSession getPendingCallSession(int i, java.lang.String str) throws android.os.RemoteException;

    com.android.ims.internal.IImsUt getUtInterface() throws android.os.RemoteException;

    boolean isConnected(int i, int i2) throws android.os.RemoteException;

    boolean isOpened() throws android.os.RemoteException;

    void removeRegistrationListener(com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException;

    void setUiTTYMode(int i, android.os.Message message) throws android.os.RemoteException;

    int startSession(android.app.PendingIntent pendingIntent, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException;

    void turnOffIms() throws android.os.RemoteException;

    void turnOnIms() throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsMMTelFeature {
        @Override // com.android.ims.internal.IImsMMTelFeature
        public int startSession(android.app.PendingIntent pendingIntent, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void endSession(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isConnected(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isOpened() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int getFeatureStatus() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void addRegistrationListener(com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void removeRegistrationListener(com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsCallSession createCallSession(int i, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsCallSession getPendingCallSession(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsUt getUtInterface() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsConfig getConfigInterface() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void turnOnIms() throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void turnOffIms() throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsEcbm getEcbmInterface() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setUiTTYMode(int i, android.os.Message message) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsMMTelFeature {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsMMTelFeature";
        static final int TRANSACTION_addRegistrationListener = 6;
        static final int TRANSACTION_createCallProfile = 8;
        static final int TRANSACTION_createCallSession = 9;
        static final int TRANSACTION_endSession = 2;
        static final int TRANSACTION_getConfigInterface = 12;
        static final int TRANSACTION_getEcbmInterface = 15;
        static final int TRANSACTION_getFeatureStatus = 5;
        static final int TRANSACTION_getMultiEndpointInterface = 17;
        static final int TRANSACTION_getPendingCallSession = 10;
        static final int TRANSACTION_getUtInterface = 11;
        static final int TRANSACTION_isConnected = 3;
        static final int TRANSACTION_isOpened = 4;
        static final int TRANSACTION_removeRegistrationListener = 7;
        static final int TRANSACTION_setUiTTYMode = 16;
        static final int TRANSACTION_startSession = 1;
        static final int TRANSACTION_turnOffIms = 14;
        static final int TRANSACTION_turnOnIms = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsMMTelFeature asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsMMTelFeature)) {
                return (com.android.ims.internal.IImsMMTelFeature) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsMMTelFeature.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startSession";
                case 2:
                    return "endSession";
                case 3:
                    return "isConnected";
                case 4:
                    return "isOpened";
                case 5:
                    return "getFeatureStatus";
                case 6:
                    return "addRegistrationListener";
                case 7:
                    return "removeRegistrationListener";
                case 8:
                    return "createCallProfile";
                case 9:
                    return "createCallSession";
                case 10:
                    return "getPendingCallSession";
                case 11:
                    return "getUtInterface";
                case 12:
                    return "getConfigInterface";
                case 13:
                    return "turnOnIms";
                case 14:
                    return "turnOffIms";
                case 15:
                    return "getEcbmInterface";
                case 16:
                    return "setUiTTYMode";
                case 17:
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
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    com.android.ims.internal.IImsRegistrationListener asInterface = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startSession = startSession(pendingIntent, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(startSession);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    endSession(readInt);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isConnected = isConnected(readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConnected);
                    return true;
                case 4:
                    boolean isOpened = isOpened();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOpened);
                    return true;
                case 5:
                    int featureStatus = getFeatureStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(featureStatus);
                    return true;
                case 6:
                    com.android.ims.internal.IImsRegistrationListener asInterface2 = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addRegistrationListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    com.android.ims.internal.IImsRegistrationListener asInterface3 = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRegistrationListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.ImsCallProfile createCallProfile = createCallProfile(readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createCallProfile, 1);
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    android.telephony.ims.ImsCallProfile imsCallProfile = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsCallSession createCallSession = createCallSession(readInt7, imsCallProfile);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createCallSession);
                    return true;
                case 10:
                    int readInt8 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsCallSession pendingCallSession = getPendingCallSession(readInt8, readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(pendingCallSession);
                    return true;
                case 11:
                    com.android.ims.internal.IImsUt utInterface = getUtInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(utInterface);
                    return true;
                case 12:
                    com.android.ims.internal.IImsConfig configInterface = getConfigInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(configInterface);
                    return true;
                case 13:
                    turnOnIms();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    turnOffIms();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    com.android.ims.internal.IImsEcbm ecbmInterface = getEcbmInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(ecbmInterface);
                    return true;
                case 16:
                    int readInt9 = parcel.readInt();
                    android.os.Message message = (android.os.Message) parcel.readTypedObject(android.os.Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiTTYMode(readInt9, message);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    com.android.ims.internal.IImsMultiEndpoint multiEndpointInterface = getMultiEndpointInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(multiEndpointInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsMMTelFeature {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int startSession(android.app.PendingIntent pendingIntent, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
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

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void endSession(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public boolean isConnected(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public boolean isOpened() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int getFeatureStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void addRegistrationListener(com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void removeRegistrationListener(com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.ImsCallProfile) obtain2.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public com.android.ims.internal.IImsCallSession createCallSession(int i, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public com.android.ims.internal.IImsCallSession getPendingCallSession(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public com.android.ims.internal.IImsUt getUtInterface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsUt.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public com.android.ims.internal.IImsConfig getConfigInterface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsConfig.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void turnOnIms() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void turnOffIms() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public com.android.ims.internal.IImsEcbm getEcbmInterface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsEcbm.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setUiTTYMode(int i, android.os.Message message) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsMMTelFeature.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
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
            return 16;
        }
    }
}
