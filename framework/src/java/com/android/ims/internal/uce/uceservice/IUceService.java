package com.android.ims.internal.uce.uceservice;

/* loaded from: classes4.dex */
public interface IUceService extends android.os.IInterface {
    @java.lang.Deprecated
    int createOptionsService(com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException;

    int createOptionsServiceForSubscription(com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong, java.lang.String str) throws android.os.RemoteException;

    @java.lang.Deprecated
    int createPresenceService(com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException;

    int createPresenceServiceForSubscription(com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong, java.lang.String str) throws android.os.RemoteException;

    void destroyOptionsService(int i) throws android.os.RemoteException;

    void destroyPresenceService(int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    com.android.ims.internal.uce.options.IOptionsService getOptionsService() throws android.os.RemoteException;

    com.android.ims.internal.uce.options.IOptionsService getOptionsServiceForSubscription(java.lang.String str) throws android.os.RemoteException;

    @java.lang.Deprecated
    com.android.ims.internal.uce.presence.IPresenceService getPresenceService() throws android.os.RemoteException;

    com.android.ims.internal.uce.presence.IPresenceService getPresenceServiceForSubscription(java.lang.String str) throws android.os.RemoteException;

    boolean getServiceStatus() throws android.os.RemoteException;

    boolean isServiceStarted() throws android.os.RemoteException;

    boolean startService(com.android.ims.internal.uce.uceservice.IUceListener iUceListener) throws android.os.RemoteException;

    boolean stopService() throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.uce.uceservice.IUceService {
        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean startService(com.android.ims.internal.uce.uceservice.IUceListener iUceListener) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean stopService() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean isServiceStarted() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createOptionsService(com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createOptionsServiceForSubscription(com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public void destroyOptionsService(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createPresenceService(com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createPresenceServiceForSubscription(com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public void destroyPresenceService(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean getServiceStatus() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public com.android.ims.internal.uce.presence.IPresenceService getPresenceService() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public com.android.ims.internal.uce.presence.IPresenceService getPresenceServiceForSubscription(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public com.android.ims.internal.uce.options.IOptionsService getOptionsService() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public com.android.ims.internal.uce.options.IOptionsService getOptionsServiceForSubscription(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.uce.uceservice.IUceService {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.uce.uceservice.IUceService";
        static final int TRANSACTION_createOptionsService = 4;
        static final int TRANSACTION_createOptionsServiceForSubscription = 5;
        static final int TRANSACTION_createPresenceService = 7;
        static final int TRANSACTION_createPresenceServiceForSubscription = 8;
        static final int TRANSACTION_destroyOptionsService = 6;
        static final int TRANSACTION_destroyPresenceService = 9;
        static final int TRANSACTION_getOptionsService = 13;
        static final int TRANSACTION_getOptionsServiceForSubscription = 14;
        static final int TRANSACTION_getPresenceService = 11;
        static final int TRANSACTION_getPresenceServiceForSubscription = 12;
        static final int TRANSACTION_getServiceStatus = 10;
        static final int TRANSACTION_isServiceStarted = 3;
        static final int TRANSACTION_startService = 1;
        static final int TRANSACTION_stopService = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.uce.uceservice.IUceService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.uce.uceservice.IUceService)) {
                return (com.android.ims.internal.uce.uceservice.IUceService) queryLocalInterface;
            }
            return new com.android.ims.internal.uce.uceservice.IUceService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startService";
                case 2:
                    return "stopService";
                case 3:
                    return "isServiceStarted";
                case 4:
                    return "createOptionsService";
                case 5:
                    return "createOptionsServiceForSubscription";
                case 6:
                    return "destroyOptionsService";
                case 7:
                    return "createPresenceService";
                case 8:
                    return "createPresenceServiceForSubscription";
                case 9:
                    return "destroyPresenceService";
                case 10:
                    return "getServiceStatus";
                case 11:
                    return "getPresenceService";
                case 12:
                    return "getPresenceServiceForSubscription";
                case 13:
                    return "getOptionsService";
                case 14:
                    return "getOptionsServiceForSubscription";
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
                    com.android.ims.internal.uce.uceservice.IUceListener asInterface = com.android.ims.internal.uce.uceservice.IUceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean startService = startService(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startService);
                    return true;
                case 2:
                    boolean stopService = stopService();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopService);
                    return true;
                case 3:
                    boolean isServiceStarted = isServiceStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isServiceStarted);
                    return true;
                case 4:
                    com.android.ims.internal.uce.options.IOptionsListener asInterface2 = com.android.ims.internal.uce.options.IOptionsListener.Stub.asInterface(parcel.readStrongBinder());
                    com.android.ims.internal.uce.common.UceLong uceLong = (com.android.ims.internal.uce.common.UceLong) parcel.readTypedObject(com.android.ims.internal.uce.common.UceLong.CREATOR);
                    parcel.enforceNoDataAvail();
                    int createOptionsService = createOptionsService(asInterface2, uceLong);
                    parcel2.writeNoException();
                    parcel2.writeInt(createOptionsService);
                    parcel2.writeTypedObject(uceLong, 1);
                    return true;
                case 5:
                    com.android.ims.internal.uce.options.IOptionsListener asInterface3 = com.android.ims.internal.uce.options.IOptionsListener.Stub.asInterface(parcel.readStrongBinder());
                    com.android.ims.internal.uce.common.UceLong uceLong2 = (com.android.ims.internal.uce.common.UceLong) parcel.readTypedObject(com.android.ims.internal.uce.common.UceLong.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int createOptionsServiceForSubscription = createOptionsServiceForSubscription(asInterface3, uceLong2, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(createOptionsServiceForSubscription);
                    parcel2.writeTypedObject(uceLong2, 1);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyOptionsService(readInt);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    com.android.ims.internal.uce.presence.IPresenceListener asInterface4 = com.android.ims.internal.uce.presence.IPresenceListener.Stub.asInterface(parcel.readStrongBinder());
                    com.android.ims.internal.uce.common.UceLong uceLong3 = (com.android.ims.internal.uce.common.UceLong) parcel.readTypedObject(com.android.ims.internal.uce.common.UceLong.CREATOR);
                    parcel.enforceNoDataAvail();
                    int createPresenceService = createPresenceService(asInterface4, uceLong3);
                    parcel2.writeNoException();
                    parcel2.writeInt(createPresenceService);
                    parcel2.writeTypedObject(uceLong3, 1);
                    return true;
                case 8:
                    com.android.ims.internal.uce.presence.IPresenceListener asInterface5 = com.android.ims.internal.uce.presence.IPresenceListener.Stub.asInterface(parcel.readStrongBinder());
                    com.android.ims.internal.uce.common.UceLong uceLong4 = (com.android.ims.internal.uce.common.UceLong) parcel.readTypedObject(com.android.ims.internal.uce.common.UceLong.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int createPresenceServiceForSubscription = createPresenceServiceForSubscription(asInterface5, uceLong4, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(createPresenceServiceForSubscription);
                    parcel2.writeTypedObject(uceLong4, 1);
                    return true;
                case 9:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyPresenceService(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean serviceStatus = getServiceStatus();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(serviceStatus);
                    return true;
                case 11:
                    com.android.ims.internal.uce.presence.IPresenceService presenceService = getPresenceService();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(presenceService);
                    return true;
                case 12:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.presence.IPresenceService presenceServiceForSubscription = getPresenceServiceForSubscription(readString3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(presenceServiceForSubscription);
                    return true;
                case 13:
                    com.android.ims.internal.uce.options.IOptionsService optionsService = getOptionsService();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(optionsService);
                    return true;
                case 14:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.uce.options.IOptionsService optionsServiceForSubscription = getOptionsServiceForSubscription(readString4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(optionsServiceForSubscription);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.uce.uceservice.IUceService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public boolean startService(com.android.ims.internal.uce.uceservice.IUceListener iUceListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUceListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public boolean stopService() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public boolean isServiceStarted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public int createOptionsService(com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOptionsListener);
                    obtain.writeTypedObject(uceLong, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        uceLong.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public int createOptionsServiceForSubscription(com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOptionsListener);
                    obtain.writeTypedObject(uceLong, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        uceLong.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public void destroyOptionsService(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public int createPresenceService(com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPresenceListener);
                    obtain.writeTypedObject(uceLong, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        uceLong.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public int createPresenceServiceForSubscription(com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPresenceListener);
                    obtain.writeTypedObject(uceLong, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        uceLong.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public void destroyPresenceService(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public boolean getServiceStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public com.android.ims.internal.uce.presence.IPresenceService getPresenceService() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.uce.presence.IPresenceService.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public com.android.ims.internal.uce.presence.IPresenceService getPresenceServiceForSubscription(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.uce.presence.IPresenceService.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public com.android.ims.internal.uce.options.IOptionsService getOptionsService() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.uce.options.IOptionsService.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public com.android.ims.internal.uce.options.IOptionsService getOptionsServiceForSubscription(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.uce.uceservice.IUceService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.uce.options.IOptionsService.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }
    }
}
