package android.hardware.radio.config;

/* loaded from: classes2.dex */
public interface IRadioConfigResponse extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$config$IRadioConfigResponse".replace('$', '.');
    public static final java.lang.String HASH = "1e3dcfffc1e90fc886cf5a22ecaa94601b115710";
    public static final int VERSION = 3;

    void getHalDeviceCapabilitiesResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getNumOfLiveModemsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, byte b) throws android.os.RemoteException;

    void getPhoneCapabilityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.config.PhoneCapability phoneCapability) throws android.os.RemoteException;

    void getSimSlotsStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.config.SimSlotStatus[] simSlotStatusArr) throws android.os.RemoteException;

    void getSimultaneousCallingSupportResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int[] iArr) throws android.os.RemoteException;

    void setNumOfLiveModemsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setPreferredDataModemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setSimSlotsMappingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.config.IRadioConfigResponse {
        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getHalDeviceCapabilitiesResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getNumOfLiveModemsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, byte b) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getPhoneCapabilityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.config.PhoneCapability phoneCapability) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getSimSlotsStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.config.SimSlotStatus[] simSlotStatusArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void setNumOfLiveModemsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void setPreferredDataModemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void setSimSlotsMappingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getSimultaneousCallingSupportResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.config.IRadioConfigResponse {
        static final int TRANSACTION_getHalDeviceCapabilitiesResponse = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNumOfLiveModemsResponse = 2;
        static final int TRANSACTION_getPhoneCapabilityResponse = 3;
        static final int TRANSACTION_getSimSlotsStatusResponse = 4;
        static final int TRANSACTION_getSimultaneousCallingSupportResponse = 8;
        static final int TRANSACTION_setNumOfLiveModemsResponse = 5;
        static final int TRANSACTION_setPreferredDataModemResponse = 6;
        static final int TRANSACTION_setSimSlotsMappingResponse = 7;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.config.IRadioConfigResponse asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.config.IRadioConfigResponse)) {
                return (android.hardware.radio.config.IRadioConfigResponse) queryLocalInterface;
            }
            return new android.hardware.radio.config.IRadioConfigResponse.Stub.Proxy(iBinder);
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
                    android.hardware.radio.RadioResponseInfo radioResponseInfo = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getHalDeviceCapabilitiesResponse(radioResponseInfo, readBoolean);
                    return true;
                case 2:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo2 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    getNumOfLiveModemsResponse(radioResponseInfo2, readByte);
                    return true;
                case 3:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo3 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.config.PhoneCapability phoneCapability = (android.hardware.radio.config.PhoneCapability) parcel.readTypedObject(android.hardware.radio.config.PhoneCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPhoneCapabilityResponse(radioResponseInfo3, phoneCapability);
                    return true;
                case 4:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo4 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.config.SimSlotStatus[] simSlotStatusArr = (android.hardware.radio.config.SimSlotStatus[]) parcel.createTypedArray(android.hardware.radio.config.SimSlotStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSimSlotsStatusResponse(radioResponseInfo4, simSlotStatusArr);
                    return true;
                case 5:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo5 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNumOfLiveModemsResponse(radioResponseInfo5);
                    return true;
                case 6:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo6 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferredDataModemResponse(radioResponseInfo6);
                    return true;
                case 7:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo7 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSimSlotsMappingResponse(radioResponseInfo7);
                    return true;
                case 8:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo8 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    getSimultaneousCallingSupportResponse(radioResponseInfo8, createIntArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.config.IRadioConfigResponse {
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

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getHalDeviceCapabilitiesResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getHalDeviceCapabilitiesResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getNumOfLiveModemsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getNumOfLiveModemsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getPhoneCapabilityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.config.PhoneCapability phoneCapability) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(phoneCapability, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getPhoneCapabilityResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getSimSlotsStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.config.SimSlotStatus[] simSlotStatusArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(simSlotStatusArr, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSimSlotsStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void setNumOfLiveModemsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNumOfLiveModemsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void setPreferredDataModemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setPreferredDataModemResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void setSimSlotsMappingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSimSlotsMappingResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getSimultaneousCallingSupportResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSimultaneousCallingSupportResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
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

            @Override // android.hardware.radio.config.IRadioConfigResponse
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
