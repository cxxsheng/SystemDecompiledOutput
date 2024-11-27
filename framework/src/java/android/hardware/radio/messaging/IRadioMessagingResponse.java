package android.hardware.radio.messaging;

/* loaded from: classes2.dex */
public interface IRadioMessagingResponse extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$messaging$IRadioMessagingResponse".replace('$', '.');
    public static final java.lang.String HASH = "30b0bc0e84679bc3b5ccb3a52da34c47cda6b7eb";
    public static final int VERSION = 3;

    void acknowledgeIncomingGsmSmsWithPduResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void acknowledgeLastIncomingCdmaSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void acknowledgeLastIncomingGsmSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void acknowledgeRequest(int i) throws android.os.RemoteException;

    void deleteSmsOnRuimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void deleteSmsOnSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void getCdmaBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws android.os.RemoteException;

    void getGsmBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getSmscAddressResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException;

    void reportSmsMemoryStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void sendCdmaSmsExpectMoreResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException;

    void sendCdmaSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException;

    void sendImsSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException;

    void sendSmsExpectMoreResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException;

    void sendSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException;

    void setCdmaBroadcastActivationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setCdmaBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setGsmBroadcastActivationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setGsmBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setSmscAddressResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void writeSmsToRuimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void writeSmsToSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.messaging.IRadioMessagingResponse {
        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void acknowledgeIncomingGsmSmsWithPduResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void acknowledgeLastIncomingCdmaSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void acknowledgeLastIncomingGsmSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void acknowledgeRequest(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void deleteSmsOnRuimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void deleteSmsOnSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void getCdmaBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void getGsmBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void getSmscAddressResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void reportSmsMemoryStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void sendCdmaSmsExpectMoreResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void sendCdmaSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void sendImsSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void sendSmsExpectMoreResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void sendSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void setCdmaBroadcastActivationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void setCdmaBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void setGsmBroadcastActivationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void setGsmBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void setSmscAddressResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void writeSmsToRuimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void writeSmsToSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.messaging.IRadioMessagingResponse {
        static final int TRANSACTION_acknowledgeIncomingGsmSmsWithPduResponse = 1;
        static final int TRANSACTION_acknowledgeLastIncomingCdmaSmsResponse = 2;
        static final int TRANSACTION_acknowledgeLastIncomingGsmSmsResponse = 3;
        static final int TRANSACTION_acknowledgeRequest = 4;
        static final int TRANSACTION_deleteSmsOnRuimResponse = 5;
        static final int TRANSACTION_deleteSmsOnSimResponse = 6;
        static final int TRANSACTION_getCdmaBroadcastConfigResponse = 7;
        static final int TRANSACTION_getGsmBroadcastConfigResponse = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSmscAddressResponse = 9;
        static final int TRANSACTION_reportSmsMemoryStatusResponse = 10;
        static final int TRANSACTION_sendCdmaSmsExpectMoreResponse = 11;
        static final int TRANSACTION_sendCdmaSmsResponse = 12;
        static final int TRANSACTION_sendImsSmsResponse = 13;
        static final int TRANSACTION_sendSmsExpectMoreResponse = 14;
        static final int TRANSACTION_sendSmsResponse = 15;
        static final int TRANSACTION_setCdmaBroadcastActivationResponse = 16;
        static final int TRANSACTION_setCdmaBroadcastConfigResponse = 17;
        static final int TRANSACTION_setGsmBroadcastActivationResponse = 18;
        static final int TRANSACTION_setGsmBroadcastConfigResponse = 19;
        static final int TRANSACTION_setSmscAddressResponse = 20;
        static final int TRANSACTION_writeSmsToRuimResponse = 21;
        static final int TRANSACTION_writeSmsToSimResponse = 22;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.messaging.IRadioMessagingResponse asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.messaging.IRadioMessagingResponse)) {
                return (android.hardware.radio.messaging.IRadioMessagingResponse) queryLocalInterface;
            }
            return new android.hardware.radio.messaging.IRadioMessagingResponse.Stub.Proxy(iBinder);
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
                    parcel.enforceNoDataAvail();
                    acknowledgeIncomingGsmSmsWithPduResponse(radioResponseInfo);
                    return true;
                case 2:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo2 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    acknowledgeLastIncomingCdmaSmsResponse(radioResponseInfo2);
                    return true;
                case 3:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo3 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    acknowledgeLastIncomingGsmSmsResponse(radioResponseInfo3);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeRequest(readInt);
                    return true;
                case 5:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo4 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteSmsOnRuimResponse(radioResponseInfo4);
                    return true;
                case 6:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo5 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteSmsOnSimResponse(radioResponseInfo5);
                    return true;
                case 7:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo6 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr = (android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[]) parcel.createTypedArray(android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCdmaBroadcastConfigResponse(radioResponseInfo6, cdmaBroadcastSmsConfigInfoArr);
                    return true;
                case 8:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo7 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr = (android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[]) parcel.createTypedArray(android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getGsmBroadcastConfigResponse(radioResponseInfo7, gsmBroadcastSmsConfigInfoArr);
                    return true;
                case 9:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo8 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getSmscAddressResponse(radioResponseInfo8, readString);
                    return true;
                case 10:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo9 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportSmsMemoryStatusResponse(radioResponseInfo9);
                    return true;
                case 11:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo10 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.messaging.SendSmsResult sendSmsResult = (android.hardware.radio.messaging.SendSmsResult) parcel.readTypedObject(android.hardware.radio.messaging.SendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSmsExpectMoreResponse(radioResponseInfo10, sendSmsResult);
                    return true;
                case 12:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo11 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.messaging.SendSmsResult sendSmsResult2 = (android.hardware.radio.messaging.SendSmsResult) parcel.readTypedObject(android.hardware.radio.messaging.SendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSmsResponse(radioResponseInfo11, sendSmsResult2);
                    return true;
                case 13:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo12 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.messaging.SendSmsResult sendSmsResult3 = (android.hardware.radio.messaging.SendSmsResult) parcel.readTypedObject(android.hardware.radio.messaging.SendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendImsSmsResponse(radioResponseInfo12, sendSmsResult3);
                    return true;
                case 14:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo13 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.messaging.SendSmsResult sendSmsResult4 = (android.hardware.radio.messaging.SendSmsResult) parcel.readTypedObject(android.hardware.radio.messaging.SendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSmsExpectMoreResponse(radioResponseInfo13, sendSmsResult4);
                    return true;
                case 15:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo14 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.messaging.SendSmsResult sendSmsResult5 = (android.hardware.radio.messaging.SendSmsResult) parcel.readTypedObject(android.hardware.radio.messaging.SendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSmsResponse(radioResponseInfo14, sendSmsResult5);
                    return true;
                case 16:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo15 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCdmaBroadcastActivationResponse(radioResponseInfo15);
                    return true;
                case 17:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo16 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCdmaBroadcastConfigResponse(radioResponseInfo16);
                    return true;
                case 18:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo17 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setGsmBroadcastActivationResponse(radioResponseInfo17);
                    return true;
                case 19:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo18 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setGsmBroadcastConfigResponse(radioResponseInfo18);
                    return true;
                case 20:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo19 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSmscAddressResponse(radioResponseInfo19);
                    return true;
                case 21:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo20 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    writeSmsToRuimResponse(radioResponseInfo20, readInt2);
                    return true;
                case 22:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo21 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    writeSmsToSimResponse(radioResponseInfo21, readInt3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.messaging.IRadioMessagingResponse {
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

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void acknowledgeIncomingGsmSmsWithPduResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeIncomingGsmSmsWithPduResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void acknowledgeLastIncomingCdmaSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeLastIncomingCdmaSmsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void acknowledgeLastIncomingGsmSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeLastIncomingGsmSmsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void acknowledgeRequest(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void deleteSmsOnRuimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method deleteSmsOnRuimResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void deleteSmsOnSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method deleteSmsOnSimResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void getCdmaBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(cdmaBroadcastSmsConfigInfoArr, 0);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCdmaBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void getGsmBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(gsmBroadcastSmsConfigInfoArr, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getGsmBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void getSmscAddressResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSmscAddressResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void reportSmsMemoryStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method reportSmsMemoryStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void sendCdmaSmsExpectMoreResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(sendSmsResult, 0);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendCdmaSmsExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void sendCdmaSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(sendSmsResult, 0);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendCdmaSmsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void sendImsSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(sendSmsResult, 0);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendImsSmsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void sendSmsExpectMoreResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(sendSmsResult, 0);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendSmsExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void sendSmsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.messaging.SendSmsResult sendSmsResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(sendSmsResult, 0);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendSmsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void setCdmaBroadcastActivationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCdmaBroadcastActivationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void setCdmaBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(17, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCdmaBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void setGsmBroadcastActivationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(18, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setGsmBroadcastActivationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void setGsmBroadcastConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(19, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setGsmBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void setSmscAddressResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(20, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSmscAddressResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void writeSmsToRuimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(21, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method writeSmsToRuimResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void writeSmsToSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(22, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method writeSmsToSimResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
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

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
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
