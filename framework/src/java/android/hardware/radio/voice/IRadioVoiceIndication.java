package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public interface IRadioVoiceIndication extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$voice$IRadioVoiceIndication".replace('$', '.');
    public static final java.lang.String HASH = "78fb79bcb32590a868b3eb7affb39ab90e4ca782";
    public static final int VERSION = 3;

    void callRing(int i, boolean z, android.hardware.radio.voice.CdmaSignalInfoRecord cdmaSignalInfoRecord) throws android.os.RemoteException;

    void callStateChanged(int i) throws android.os.RemoteException;

    void cdmaCallWaiting(int i, android.hardware.radio.voice.CdmaCallWaiting cdmaCallWaiting) throws android.os.RemoteException;

    void cdmaInfoRec(int i, android.hardware.radio.voice.CdmaInformationRecord[] cdmaInformationRecordArr) throws android.os.RemoteException;

    void cdmaOtaProvisionStatus(int i, int i2) throws android.os.RemoteException;

    void currentEmergencyNumberList(int i, android.hardware.radio.voice.EmergencyNumber[] emergencyNumberArr) throws android.os.RemoteException;

    void enterEmergencyCallbackMode(int i) throws android.os.RemoteException;

    void exitEmergencyCallbackMode(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void indicateRingbackTone(int i, boolean z) throws android.os.RemoteException;

    void onSupplementaryServiceIndication(int i, android.hardware.radio.voice.StkCcUnsolSsResult stkCcUnsolSsResult) throws android.os.RemoteException;

    void onUssd(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void resendIncallMute(int i) throws android.os.RemoteException;

    void srvccStateNotify(int i, int i2) throws android.os.RemoteException;

    void stkCallControlAlphaNotify(int i, java.lang.String str) throws android.os.RemoteException;

    void stkCallSetup(int i, long j) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.voice.IRadioVoiceIndication {
        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void callRing(int i, boolean z, android.hardware.radio.voice.CdmaSignalInfoRecord cdmaSignalInfoRecord) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void callStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void cdmaCallWaiting(int i, android.hardware.radio.voice.CdmaCallWaiting cdmaCallWaiting) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void cdmaInfoRec(int i, android.hardware.radio.voice.CdmaInformationRecord[] cdmaInformationRecordArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void cdmaOtaProvisionStatus(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void currentEmergencyNumberList(int i, android.hardware.radio.voice.EmergencyNumber[] emergencyNumberArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void enterEmergencyCallbackMode(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void exitEmergencyCallbackMode(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void indicateRingbackTone(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void onSupplementaryServiceIndication(int i, android.hardware.radio.voice.StkCcUnsolSsResult stkCcUnsolSsResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void onUssd(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void resendIncallMute(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void srvccStateNotify(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void stkCallControlAlphaNotify(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void stkCallSetup(int i, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.voice.IRadioVoiceIndication {
        static final int TRANSACTION_callRing = 1;
        static final int TRANSACTION_callStateChanged = 2;
        static final int TRANSACTION_cdmaCallWaiting = 3;
        static final int TRANSACTION_cdmaInfoRec = 4;
        static final int TRANSACTION_cdmaOtaProvisionStatus = 5;
        static final int TRANSACTION_currentEmergencyNumberList = 6;
        static final int TRANSACTION_enterEmergencyCallbackMode = 7;
        static final int TRANSACTION_exitEmergencyCallbackMode = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_indicateRingbackTone = 9;
        static final int TRANSACTION_onSupplementaryServiceIndication = 10;
        static final int TRANSACTION_onUssd = 11;
        static final int TRANSACTION_resendIncallMute = 12;
        static final int TRANSACTION_srvccStateNotify = 13;
        static final int TRANSACTION_stkCallControlAlphaNotify = 14;
        static final int TRANSACTION_stkCallSetup = 15;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.voice.IRadioVoiceIndication asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.voice.IRadioVoiceIndication)) {
                return (android.hardware.radio.voice.IRadioVoiceIndication) queryLocalInterface;
            }
            return new android.hardware.radio.voice.IRadioVoiceIndication.Stub.Proxy(iBinder);
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
                    boolean readBoolean = parcel.readBoolean();
                    android.hardware.radio.voice.CdmaSignalInfoRecord cdmaSignalInfoRecord = (android.hardware.radio.voice.CdmaSignalInfoRecord) parcel.readTypedObject(android.hardware.radio.voice.CdmaSignalInfoRecord.CREATOR);
                    parcel.enforceNoDataAvail();
                    callRing(readInt, readBoolean, cdmaSignalInfoRecord);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callStateChanged(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    android.hardware.radio.voice.CdmaCallWaiting cdmaCallWaiting = (android.hardware.radio.voice.CdmaCallWaiting) parcel.readTypedObject(android.hardware.radio.voice.CdmaCallWaiting.CREATOR);
                    parcel.enforceNoDataAvail();
                    cdmaCallWaiting(readInt3, cdmaCallWaiting);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    android.hardware.radio.voice.CdmaInformationRecord[] cdmaInformationRecordArr = (android.hardware.radio.voice.CdmaInformationRecord[]) parcel.createTypedArray(android.hardware.radio.voice.CdmaInformationRecord.CREATOR);
                    parcel.enforceNoDataAvail();
                    cdmaInfoRec(readInt4, cdmaInformationRecordArr);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cdmaOtaProvisionStatus(readInt5, readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    android.hardware.radio.voice.EmergencyNumber[] emergencyNumberArr = (android.hardware.radio.voice.EmergencyNumber[]) parcel.createTypedArray(android.hardware.radio.voice.EmergencyNumber.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentEmergencyNumberList(readInt7, emergencyNumberArr);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enterEmergencyCallbackMode(readInt8);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    exitEmergencyCallbackMode(readInt9);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    indicateRingbackTone(readInt10, readBoolean2);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    android.hardware.radio.voice.StkCcUnsolSsResult stkCcUnsolSsResult = (android.hardware.radio.voice.StkCcUnsolSsResult) parcel.readTypedObject(android.hardware.radio.voice.StkCcUnsolSsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSupplementaryServiceIndication(readInt11, stkCcUnsolSsResult);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onUssd(readInt12, readInt13, readString);
                    return true;
                case 12:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resendIncallMute(readInt14);
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    srvccStateNotify(readInt15, readInt16);
                    return true;
                case 14:
                    int readInt17 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stkCallControlAlphaNotify(readInt17, readString2);
                    return true;
                case 15:
                    int readInt18 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    stkCallSetup(readInt18, readLong);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.voice.IRadioVoiceIndication {
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

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void callRing(int i, boolean z, android.hardware.radio.voice.CdmaSignalInfoRecord cdmaSignalInfoRecord) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(cdmaSignalInfoRecord, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method callRing is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void callStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method callStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void cdmaCallWaiting(int i, android.hardware.radio.voice.CdmaCallWaiting cdmaCallWaiting) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cdmaCallWaiting, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cdmaCallWaiting is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void cdmaInfoRec(int i, android.hardware.radio.voice.CdmaInformationRecord[] cdmaInformationRecordArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(cdmaInformationRecordArr, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cdmaInfoRec is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void cdmaOtaProvisionStatus(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cdmaOtaProvisionStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void currentEmergencyNumberList(int i, android.hardware.radio.voice.EmergencyNumber[] emergencyNumberArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(emergencyNumberArr, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method currentEmergencyNumberList is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void enterEmergencyCallbackMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method enterEmergencyCallbackMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void exitEmergencyCallbackMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method exitEmergencyCallbackMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void indicateRingbackTone(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method indicateRingbackTone is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void onSupplementaryServiceIndication(int i, android.hardware.radio.voice.StkCcUnsolSsResult stkCcUnsolSsResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(stkCcUnsolSsResult, 0);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onSupplementaryServiceIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void onUssd(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onUssd is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void resendIncallMute(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method resendIncallMute is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void srvccStateNotify(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method srvccStateNotify is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void stkCallControlAlphaNotify(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stkCallControlAlphaNotify is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void stkCallSetup(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stkCallSetup is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
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

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
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
