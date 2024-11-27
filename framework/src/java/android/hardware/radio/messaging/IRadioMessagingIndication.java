package android.hardware.radio.messaging;

/* loaded from: classes2.dex */
public interface IRadioMessagingIndication extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$messaging$IRadioMessagingIndication".replace('$', '.');
    public static final java.lang.String HASH = "30b0bc0e84679bc3b5ccb3a52da34c47cda6b7eb";
    public static final int VERSION = 3;

    void cdmaNewSms(int i, android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage) throws android.os.RemoteException;

    void cdmaRuimSmsStorageFull(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void newBroadcastSms(int i, byte[] bArr) throws android.os.RemoteException;

    void newSms(int i, byte[] bArr) throws android.os.RemoteException;

    void newSmsOnSim(int i, int i2) throws android.os.RemoteException;

    void newSmsStatusReport(int i, byte[] bArr) throws android.os.RemoteException;

    void simSmsStorageFull(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.messaging.IRadioMessagingIndication {
        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void cdmaNewSms(int i, android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void cdmaRuimSmsStorageFull(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void newBroadcastSms(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void newSms(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void newSmsOnSim(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void newSmsStatusReport(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void simSmsStorageFull(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.messaging.IRadioMessagingIndication {
        static final int TRANSACTION_cdmaNewSms = 1;
        static final int TRANSACTION_cdmaRuimSmsStorageFull = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_newBroadcastSms = 3;
        static final int TRANSACTION_newSms = 4;
        static final int TRANSACTION_newSmsOnSim = 5;
        static final int TRANSACTION_newSmsStatusReport = 6;
        static final int TRANSACTION_simSmsStorageFull = 7;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.messaging.IRadioMessagingIndication asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.messaging.IRadioMessagingIndication)) {
                return (android.hardware.radio.messaging.IRadioMessagingIndication) queryLocalInterface;
            }
            return new android.hardware.radio.messaging.IRadioMessagingIndication.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage = (android.hardware.radio.messaging.CdmaSmsMessage) parcel.readTypedObject(android.hardware.radio.messaging.CdmaSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    cdmaNewSms(readInt, cdmaSmsMessage);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cdmaRuimSmsStorageFull(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    newBroadcastSms(readInt3, createByteArray);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    newSms(readInt4, createByteArray2);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    newSmsOnSim(readInt5, readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    newSmsStatusReport(readInt7, createByteArray3);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simSmsStorageFull(readInt8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.messaging.IRadioMessagingIndication {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void cdmaNewSms(int i, android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cdmaSmsMessage, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cdmaNewSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void cdmaRuimSmsStorageFull(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cdmaRuimSmsStorageFull is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void newBroadcastSms(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method newBroadcastSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void newSms(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method newSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void newSmsOnSim(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method newSmsOnSim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void newSmsStatusReport(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method newSmsStatusReport is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void simSmsStorageFull(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method simSmsStorageFull is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
