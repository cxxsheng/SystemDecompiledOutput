package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IImsRegistration extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IImsRegistration";

    void addEmergencyRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException;

    void addRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException;

    int getRegistrationTechnology() throws android.os.RemoteException;

    void removeEmergencyRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException;

    void removeRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException;

    void triggerDeregistration(int i) throws android.os.RemoteException;

    void triggerFullNetworkRegistration(int i, java.lang.String str) throws android.os.RemoteException;

    void triggerSipDelegateDeregistration() throws android.os.RemoteException;

    void triggerUpdateSipDelegateRegistration() throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IImsRegistration {
        @Override // android.telephony.ims.aidl.IImsRegistration
        public int getRegistrationTechnology() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addEmergencyRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeEmergencyRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerFullNetworkRegistration(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerUpdateSipDelegateRegistration() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerSipDelegateDeregistration() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerDeregistration(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IImsRegistration {
        static final int TRANSACTION_addEmergencyRegistrationCallback = 4;
        static final int TRANSACTION_addRegistrationCallback = 2;
        static final int TRANSACTION_getRegistrationTechnology = 1;
        static final int TRANSACTION_removeEmergencyRegistrationCallback = 5;
        static final int TRANSACTION_removeRegistrationCallback = 3;
        static final int TRANSACTION_triggerDeregistration = 9;
        static final int TRANSACTION_triggerFullNetworkRegistration = 6;
        static final int TRANSACTION_triggerSipDelegateDeregistration = 8;
        static final int TRANSACTION_triggerUpdateSipDelegateRegistration = 7;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IImsRegistration asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IImsRegistration)) {
                return (android.telephony.ims.aidl.IImsRegistration) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IImsRegistration.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getRegistrationTechnology";
                case 2:
                    return "addRegistrationCallback";
                case 3:
                    return "removeRegistrationCallback";
                case 4:
                    return "addEmergencyRegistrationCallback";
                case 5:
                    return "removeEmergencyRegistrationCallback";
                case 6:
                    return "triggerFullNetworkRegistration";
                case 7:
                    return "triggerUpdateSipDelegateRegistration";
                case 8:
                    return "triggerSipDelegateDeregistration";
                case 9:
                    return "triggerDeregistration";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int registrationTechnology = getRegistrationTechnology();
                    parcel2.writeNoException();
                    parcel2.writeInt(registrationTechnology);
                    return true;
                case 2:
                    android.telephony.ims.aidl.IImsRegistrationCallback asInterface = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addRegistrationCallback(asInterface);
                    return true;
                case 3:
                    android.telephony.ims.aidl.IImsRegistrationCallback asInterface2 = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRegistrationCallback(asInterface2);
                    return true;
                case 4:
                    android.telephony.ims.aidl.IImsRegistrationCallback asInterface3 = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addEmergencyRegistrationCallback(asInterface3);
                    return true;
                case 5:
                    android.telephony.ims.aidl.IImsRegistrationCallback asInterface4 = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeEmergencyRegistrationCallback(asInterface4);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    triggerFullNetworkRegistration(readInt, readString);
                    return true;
                case 7:
                    triggerUpdateSipDelegateRegistration();
                    return true;
                case 8:
                    triggerSipDelegateDeregistration();
                    return true;
                case 9:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerDeregistration(readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IImsRegistration {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public int getRegistrationTechnology() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void addRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void removeRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void addEmergencyRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void removeEmergencyRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerFullNetworkRegistration(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerUpdateSipDelegateRegistration() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerSipDelegateDeregistration() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerDeregistration(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistration.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
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
