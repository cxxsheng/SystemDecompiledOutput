package android.hardware.radio.sim;

/* loaded from: classes2.dex */
public interface IRadioSimIndication extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$sim$IRadioSimIndication".replace('$', '.');
    public static final java.lang.String HASH = "ea7be3035be8d4869237a6478d2e0bb0efcc1e87";
    public static final int VERSION = 3;

    void carrierInfoForImsiEncryption(int i) throws android.os.RemoteException;

    void cdmaSubscriptionSourceChanged(int i, int i2) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void simPhonebookChanged(int i) throws android.os.RemoteException;

    void simPhonebookRecordsReceived(int i, byte b, android.hardware.radio.sim.PhonebookRecordInfo[] phonebookRecordInfoArr) throws android.os.RemoteException;

    void simRefresh(int i, android.hardware.radio.sim.SimRefreshResult simRefreshResult) throws android.os.RemoteException;

    void simStatusChanged(int i) throws android.os.RemoteException;

    void stkEventNotify(int i, java.lang.String str) throws android.os.RemoteException;

    void stkProactiveCommand(int i, java.lang.String str) throws android.os.RemoteException;

    void stkSessionEnd(int i) throws android.os.RemoteException;

    void subscriptionStatusChanged(int i, boolean z) throws android.os.RemoteException;

    void uiccApplicationsEnablementChanged(int i, boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.sim.IRadioSimIndication {
        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void carrierInfoForImsiEncryption(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void cdmaSubscriptionSourceChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void simPhonebookChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void simPhonebookRecordsReceived(int i, byte b, android.hardware.radio.sim.PhonebookRecordInfo[] phonebookRecordInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void simRefresh(int i, android.hardware.radio.sim.SimRefreshResult simRefreshResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void simStatusChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void stkEventNotify(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void stkProactiveCommand(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void stkSessionEnd(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void subscriptionStatusChanged(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void uiccApplicationsEnablementChanged(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.sim.IRadioSimIndication {
        static final int TRANSACTION_carrierInfoForImsiEncryption = 1;
        static final int TRANSACTION_cdmaSubscriptionSourceChanged = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_simPhonebookChanged = 3;
        static final int TRANSACTION_simPhonebookRecordsReceived = 4;
        static final int TRANSACTION_simRefresh = 5;
        static final int TRANSACTION_simStatusChanged = 6;
        static final int TRANSACTION_stkEventNotify = 7;
        static final int TRANSACTION_stkProactiveCommand = 8;
        static final int TRANSACTION_stkSessionEnd = 9;
        static final int TRANSACTION_subscriptionStatusChanged = 10;
        static final int TRANSACTION_uiccApplicationsEnablementChanged = 11;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.sim.IRadioSimIndication asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.sim.IRadioSimIndication)) {
                return (android.hardware.radio.sim.IRadioSimIndication) queryLocalInterface;
            }
            return new android.hardware.radio.sim.IRadioSimIndication.Stub.Proxy(iBinder);
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
                    parcel.enforceNoDataAvail();
                    carrierInfoForImsiEncryption(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cdmaSubscriptionSourceChanged(readInt2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simPhonebookChanged(readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    android.hardware.radio.sim.PhonebookRecordInfo[] phonebookRecordInfoArr = (android.hardware.radio.sim.PhonebookRecordInfo[]) parcel.createTypedArray(android.hardware.radio.sim.PhonebookRecordInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    simPhonebookRecordsReceived(readInt5, readByte, phonebookRecordInfoArr);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    android.hardware.radio.sim.SimRefreshResult simRefreshResult = (android.hardware.radio.sim.SimRefreshResult) parcel.readTypedObject(android.hardware.radio.sim.SimRefreshResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    simRefresh(readInt6, simRefreshResult);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simStatusChanged(readInt7);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stkEventNotify(readInt8, readString);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stkProactiveCommand(readInt9, readString2);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stkSessionEnd(readInt10);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    subscriptionStatusChanged(readInt11, readBoolean);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    uiccApplicationsEnablementChanged(readInt12, readBoolean2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.sim.IRadioSimIndication {
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

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void carrierInfoForImsiEncryption(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method carrierInfoForImsiEncryption is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void cdmaSubscriptionSourceChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cdmaSubscriptionSourceChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simPhonebookChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method simPhonebookChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simPhonebookRecordsReceived(int i, byte b, android.hardware.radio.sim.PhonebookRecordInfo[] phonebookRecordInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    obtain.writeTypedArray(phonebookRecordInfoArr, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method simPhonebookRecordsReceived is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simRefresh(int i, android.hardware.radio.sim.SimRefreshResult simRefreshResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(simRefreshResult, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method simRefresh is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simStatusChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method simStatusChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void stkEventNotify(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stkEventNotify is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void stkProactiveCommand(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stkProactiveCommand is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void stkSessionEnd(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stkSessionEnd is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void subscriptionStatusChanged(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method subscriptionStatusChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void uiccApplicationsEnablementChanged(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method uiccApplicationsEnablementChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
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

            @Override // android.hardware.radio.sim.IRadioSimIndication
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
