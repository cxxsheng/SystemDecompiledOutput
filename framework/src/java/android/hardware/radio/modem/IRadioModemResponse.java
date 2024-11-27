package android.hardware.radio.modem;

/* loaded from: classes2.dex */
public interface IRadioModemResponse extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$modem$IRadioModemResponse".replace('$', '.');
    public static final java.lang.String HASH = "8586a5528f0085c15cff4b6628f1b8153aca29ad";
    public static final int VERSION = 3;

    void acknowledgeRequest(int i) throws android.os.RemoteException;

    void enableModemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void getBasebandVersionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException;

    @java.lang.Deprecated
    void getDeviceIdentityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException;

    void getHardwareConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.HardwareConfig[] hardwareConfigArr) throws android.os.RemoteException;

    void getImeiResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.ImeiInfo imeiInfo) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getModemActivityInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.ActivityStatsInfo activityStatsInfo) throws android.os.RemoteException;

    void getModemStackStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void getRadioCapabilityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.RadioCapability radioCapability) throws android.os.RemoteException;

    @java.lang.Deprecated
    void nvReadItemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException;

    void nvResetConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    @java.lang.Deprecated
    void nvWriteCdmaPrlResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    @java.lang.Deprecated
    void nvWriteItemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void requestShutdownResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void sendDeviceStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setRadioCapabilityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.RadioCapability radioCapability) throws android.os.RemoteException;

    void setRadioPowerResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.modem.IRadioModemResponse {
        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void acknowledgeRequest(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void enableModemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getBasebandVersionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getDeviceIdentityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getHardwareConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.HardwareConfig[] hardwareConfigArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getModemActivityInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.ActivityStatsInfo activityStatsInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getModemStackStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getRadioCapabilityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.RadioCapability radioCapability) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void nvReadItemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void nvResetConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void nvWriteCdmaPrlResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void nvWriteItemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void requestShutdownResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void sendDeviceStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void setRadioCapabilityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.RadioCapability radioCapability) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void setRadioPowerResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getImeiResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.ImeiInfo imeiInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.modem.IRadioModemResponse {
        static final int TRANSACTION_acknowledgeRequest = 1;
        static final int TRANSACTION_enableModemResponse = 2;
        static final int TRANSACTION_getBasebandVersionResponse = 3;
        static final int TRANSACTION_getDeviceIdentityResponse = 4;
        static final int TRANSACTION_getHardwareConfigResponse = 5;
        static final int TRANSACTION_getImeiResponse = 17;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getModemActivityInfoResponse = 6;
        static final int TRANSACTION_getModemStackStatusResponse = 7;
        static final int TRANSACTION_getRadioCapabilityResponse = 8;
        static final int TRANSACTION_nvReadItemResponse = 9;
        static final int TRANSACTION_nvResetConfigResponse = 10;
        static final int TRANSACTION_nvWriteCdmaPrlResponse = 11;
        static final int TRANSACTION_nvWriteItemResponse = 12;
        static final int TRANSACTION_requestShutdownResponse = 13;
        static final int TRANSACTION_sendDeviceStateResponse = 14;
        static final int TRANSACTION_setRadioCapabilityResponse = 15;
        static final int TRANSACTION_setRadioPowerResponse = 16;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.modem.IRadioModemResponse asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.modem.IRadioModemResponse)) {
                return (android.hardware.radio.modem.IRadioModemResponse) queryLocalInterface;
            }
            return new android.hardware.radio.modem.IRadioModemResponse.Stub.Proxy(iBinder);
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
                    acknowledgeRequest(readInt);
                    return true;
                case 2:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    enableModemResponse(radioResponseInfo);
                    return true;
                case 3:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo2 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getBasebandVersionResponse(radioResponseInfo2, readString);
                    return true;
                case 4:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo3 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getDeviceIdentityResponse(radioResponseInfo3, readString2, readString3, readString4, readString5);
                    return true;
                case 5:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo4 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.modem.HardwareConfig[] hardwareConfigArr = (android.hardware.radio.modem.HardwareConfig[]) parcel.createTypedArray(android.hardware.radio.modem.HardwareConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    getHardwareConfigResponse(radioResponseInfo4, hardwareConfigArr);
                    return true;
                case 6:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo5 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.modem.ActivityStatsInfo activityStatsInfo = (android.hardware.radio.modem.ActivityStatsInfo) parcel.readTypedObject(android.hardware.radio.modem.ActivityStatsInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getModemActivityInfoResponse(radioResponseInfo5, activityStatsInfo);
                    return true;
                case 7:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo6 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getModemStackStatusResponse(radioResponseInfo6, readBoolean);
                    return true;
                case 8:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo7 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.modem.RadioCapability radioCapability = (android.hardware.radio.modem.RadioCapability) parcel.readTypedObject(android.hardware.radio.modem.RadioCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    getRadioCapabilityResponse(radioResponseInfo7, radioCapability);
                    return true;
                case 9:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo8 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    nvReadItemResponse(radioResponseInfo8, readString6);
                    return true;
                case 10:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo9 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    nvResetConfigResponse(radioResponseInfo9);
                    return true;
                case 11:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo10 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    nvWriteCdmaPrlResponse(radioResponseInfo10);
                    return true;
                case 12:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo11 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    nvWriteItemResponse(radioResponseInfo11);
                    return true;
                case 13:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo12 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestShutdownResponse(radioResponseInfo12);
                    return true;
                case 14:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo13 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDeviceStateResponse(radioResponseInfo13);
                    return true;
                case 15:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo14 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.modem.RadioCapability radioCapability2 = (android.hardware.radio.modem.RadioCapability) parcel.readTypedObject(android.hardware.radio.modem.RadioCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRadioCapabilityResponse(radioResponseInfo14, radioCapability2);
                    return true;
                case 16:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo15 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRadioPowerResponse(radioResponseInfo15);
                    return true;
                case 17:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo16 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.modem.ImeiInfo imeiInfo = (android.hardware.radio.modem.ImeiInfo) parcel.readTypedObject(android.hardware.radio.modem.ImeiInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getImeiResponse(radioResponseInfo16, imeiInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.modem.IRadioModemResponse {
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

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void acknowledgeRequest(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void enableModemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method enableModemResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getBasebandVersionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getBasebandVersionResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getDeviceIdentityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getDeviceIdentityResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getHardwareConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.HardwareConfig[] hardwareConfigArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(hardwareConfigArr, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getHardwareConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getModemActivityInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.ActivityStatsInfo activityStatsInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(activityStatsInfo, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getModemActivityInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getModemStackStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getModemStackStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getRadioCapabilityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.RadioCapability radioCapability) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(radioCapability, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getRadioCapabilityResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvReadItemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method nvReadItemResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvResetConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method nvResetConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvWriteCdmaPrlResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method nvWriteCdmaPrlResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvWriteItemResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method nvWriteItemResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void requestShutdownResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method requestShutdownResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void sendDeviceStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendDeviceStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void setRadioCapabilityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.RadioCapability radioCapability) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(radioCapability, 0);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setRadioCapabilityResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void setRadioPowerResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setRadioPowerResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getImeiResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.modem.ImeiInfo imeiInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(imeiInfo, 0);
                    if (!this.mRemote.transact(17, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getImeiResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
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

            @Override // android.hardware.radio.modem.IRadioModemResponse
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
