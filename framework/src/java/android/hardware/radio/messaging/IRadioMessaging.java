package android.hardware.radio.messaging;

/* loaded from: classes2.dex */
public interface IRadioMessaging extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$messaging$IRadioMessaging".replace('$', '.');
    public static final java.lang.String HASH = "30b0bc0e84679bc3b5ccb3a52da34c47cda6b7eb";
    public static final int VERSION = 3;

    void acknowledgeIncomingGsmSmsWithPdu(int i, boolean z, java.lang.String str) throws android.os.RemoteException;

    void acknowledgeLastIncomingCdmaSms(int i, android.hardware.radio.messaging.CdmaSmsAck cdmaSmsAck) throws android.os.RemoteException;

    void acknowledgeLastIncomingGsmSms(int i, boolean z, int i2) throws android.os.RemoteException;

    void deleteSmsOnRuim(int i, int i2) throws android.os.RemoteException;

    void deleteSmsOnSim(int i, int i2) throws android.os.RemoteException;

    void getCdmaBroadcastConfig(int i) throws android.os.RemoteException;

    void getGsmBroadcastConfig(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getSmscAddress(int i) throws android.os.RemoteException;

    void reportSmsMemoryStatus(int i, boolean z) throws android.os.RemoteException;

    void responseAcknowledgement() throws android.os.RemoteException;

    void sendCdmaSms(int i, android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage) throws android.os.RemoteException;

    void sendCdmaSmsExpectMore(int i, android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage) throws android.os.RemoteException;

    void sendImsSms(int i, android.hardware.radio.messaging.ImsSmsMessage imsSmsMessage) throws android.os.RemoteException;

    void sendSms(int i, android.hardware.radio.messaging.GsmSmsMessage gsmSmsMessage) throws android.os.RemoteException;

    void sendSmsExpectMore(int i, android.hardware.radio.messaging.GsmSmsMessage gsmSmsMessage) throws android.os.RemoteException;

    void setCdmaBroadcastActivation(int i, boolean z) throws android.os.RemoteException;

    void setCdmaBroadcastConfig(int i, android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws android.os.RemoteException;

    void setGsmBroadcastActivation(int i, boolean z) throws android.os.RemoteException;

    void setGsmBroadcastConfig(int i, android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws android.os.RemoteException;

    void setResponseFunctions(android.hardware.radio.messaging.IRadioMessagingResponse iRadioMessagingResponse, android.hardware.radio.messaging.IRadioMessagingIndication iRadioMessagingIndication) throws android.os.RemoteException;

    void setSmscAddress(int i, java.lang.String str) throws android.os.RemoteException;

    void writeSmsToRuim(int i, android.hardware.radio.messaging.CdmaSmsWriteArgs cdmaSmsWriteArgs) throws android.os.RemoteException;

    void writeSmsToSim(int i, android.hardware.radio.messaging.SmsWriteArgs smsWriteArgs) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.messaging.IRadioMessaging {
        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void acknowledgeIncomingGsmSmsWithPdu(int i, boolean z, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void acknowledgeLastIncomingCdmaSms(int i, android.hardware.radio.messaging.CdmaSmsAck cdmaSmsAck) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void acknowledgeLastIncomingGsmSms(int i, boolean z, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void deleteSmsOnRuim(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void deleteSmsOnSim(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void getCdmaBroadcastConfig(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void getGsmBroadcastConfig(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void getSmscAddress(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void reportSmsMemoryStatus(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void responseAcknowledgement() throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void sendCdmaSms(int i, android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void sendCdmaSmsExpectMore(int i, android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void sendImsSms(int i, android.hardware.radio.messaging.ImsSmsMessage imsSmsMessage) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void sendSms(int i, android.hardware.radio.messaging.GsmSmsMessage gsmSmsMessage) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void sendSmsExpectMore(int i, android.hardware.radio.messaging.GsmSmsMessage gsmSmsMessage) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setCdmaBroadcastActivation(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setCdmaBroadcastConfig(int i, android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setGsmBroadcastActivation(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setGsmBroadcastConfig(int i, android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setResponseFunctions(android.hardware.radio.messaging.IRadioMessagingResponse iRadioMessagingResponse, android.hardware.radio.messaging.IRadioMessagingIndication iRadioMessagingIndication) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setSmscAddress(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void writeSmsToRuim(int i, android.hardware.radio.messaging.CdmaSmsWriteArgs cdmaSmsWriteArgs) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void writeSmsToSim(int i, android.hardware.radio.messaging.SmsWriteArgs smsWriteArgs) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.messaging.IRadioMessaging {
        static final int TRANSACTION_acknowledgeIncomingGsmSmsWithPdu = 1;
        static final int TRANSACTION_acknowledgeLastIncomingCdmaSms = 2;
        static final int TRANSACTION_acknowledgeLastIncomingGsmSms = 3;
        static final int TRANSACTION_deleteSmsOnRuim = 4;
        static final int TRANSACTION_deleteSmsOnSim = 5;
        static final int TRANSACTION_getCdmaBroadcastConfig = 6;
        static final int TRANSACTION_getGsmBroadcastConfig = 7;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSmscAddress = 8;
        static final int TRANSACTION_reportSmsMemoryStatus = 9;
        static final int TRANSACTION_responseAcknowledgement = 10;
        static final int TRANSACTION_sendCdmaSms = 11;
        static final int TRANSACTION_sendCdmaSmsExpectMore = 12;
        static final int TRANSACTION_sendImsSms = 13;
        static final int TRANSACTION_sendSms = 14;
        static final int TRANSACTION_sendSmsExpectMore = 15;
        static final int TRANSACTION_setCdmaBroadcastActivation = 16;
        static final int TRANSACTION_setCdmaBroadcastConfig = 17;
        static final int TRANSACTION_setGsmBroadcastActivation = 18;
        static final int TRANSACTION_setGsmBroadcastConfig = 19;
        static final int TRANSACTION_setResponseFunctions = 20;
        static final int TRANSACTION_setSmscAddress = 21;
        static final int TRANSACTION_writeSmsToRuim = 22;
        static final int TRANSACTION_writeSmsToSim = 23;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.messaging.IRadioMessaging asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.messaging.IRadioMessaging)) {
                return (android.hardware.radio.messaging.IRadioMessaging) queryLocalInterface;
            }
            return new android.hardware.radio.messaging.IRadioMessaging.Stub.Proxy(iBinder);
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
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acknowledgeIncomingGsmSmsWithPdu(readInt, readBoolean, readString);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.hardware.radio.messaging.CdmaSmsAck cdmaSmsAck = (android.hardware.radio.messaging.CdmaSmsAck) parcel.readTypedObject(android.hardware.radio.messaging.CdmaSmsAck.CREATOR);
                    parcel.enforceNoDataAvail();
                    acknowledgeLastIncomingCdmaSms(readInt2, cdmaSmsAck);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeLastIncomingGsmSms(readInt3, readBoolean2, readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteSmsOnRuim(readInt5, readInt6);
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteSmsOnSim(readInt7, readInt8);
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaBroadcastConfig(readInt9);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getGsmBroadcastConfig(readInt10);
                    return true;
                case 8:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSmscAddress(readInt11);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportSmsMemoryStatus(readInt12, readBoolean3);
                    return true;
                case 10:
                    responseAcknowledgement();
                    return true;
                case 11:
                    int readInt13 = parcel.readInt();
                    android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage = (android.hardware.radio.messaging.CdmaSmsMessage) parcel.readTypedObject(android.hardware.radio.messaging.CdmaSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSms(readInt13, cdmaSmsMessage);
                    return true;
                case 12:
                    int readInt14 = parcel.readInt();
                    android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage2 = (android.hardware.radio.messaging.CdmaSmsMessage) parcel.readTypedObject(android.hardware.radio.messaging.CdmaSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSmsExpectMore(readInt14, cdmaSmsMessage2);
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    android.hardware.radio.messaging.ImsSmsMessage imsSmsMessage = (android.hardware.radio.messaging.ImsSmsMessage) parcel.readTypedObject(android.hardware.radio.messaging.ImsSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendImsSms(readInt15, imsSmsMessage);
                    return true;
                case 14:
                    int readInt16 = parcel.readInt();
                    android.hardware.radio.messaging.GsmSmsMessage gsmSmsMessage = (android.hardware.radio.messaging.GsmSmsMessage) parcel.readTypedObject(android.hardware.radio.messaging.GsmSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSms(readInt16, gsmSmsMessage);
                    return true;
                case 15:
                    int readInt17 = parcel.readInt();
                    android.hardware.radio.messaging.GsmSmsMessage gsmSmsMessage2 = (android.hardware.radio.messaging.GsmSmsMessage) parcel.readTypedObject(android.hardware.radio.messaging.GsmSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSmsExpectMore(readInt17, gsmSmsMessage2);
                    return true;
                case 16:
                    int readInt18 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCdmaBroadcastActivation(readInt18, readBoolean4);
                    return true;
                case 17:
                    int readInt19 = parcel.readInt();
                    android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr = (android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[]) parcel.createTypedArray(android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCdmaBroadcastConfig(readInt19, cdmaBroadcastSmsConfigInfoArr);
                    return true;
                case 18:
                    int readInt20 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setGsmBroadcastActivation(readInt20, readBoolean5);
                    return true;
                case 19:
                    int readInt21 = parcel.readInt();
                    android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr = (android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[]) parcel.createTypedArray(android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setGsmBroadcastConfig(readInt21, gsmBroadcastSmsConfigInfoArr);
                    return true;
                case 20:
                    android.hardware.radio.messaging.IRadioMessagingResponse asInterface = android.hardware.radio.messaging.IRadioMessagingResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.radio.messaging.IRadioMessagingIndication asInterface2 = android.hardware.radio.messaging.IRadioMessagingIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    return true;
                case 21:
                    int readInt22 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSmscAddress(readInt22, readString2);
                    return true;
                case 22:
                    int readInt23 = parcel.readInt();
                    android.hardware.radio.messaging.CdmaSmsWriteArgs cdmaSmsWriteArgs = (android.hardware.radio.messaging.CdmaSmsWriteArgs) parcel.readTypedObject(android.hardware.radio.messaging.CdmaSmsWriteArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    writeSmsToRuim(readInt23, cdmaSmsWriteArgs);
                    return true;
                case 23:
                    int readInt24 = parcel.readInt();
                    android.hardware.radio.messaging.SmsWriteArgs smsWriteArgs = (android.hardware.radio.messaging.SmsWriteArgs) parcel.readTypedObject(android.hardware.radio.messaging.SmsWriteArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    writeSmsToSim(readInt24, smsWriteArgs);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.messaging.IRadioMessaging {
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

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void acknowledgeIncomingGsmSmsWithPdu(int i, boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeIncomingGsmSmsWithPdu is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void acknowledgeLastIncomingCdmaSms(int i, android.hardware.radio.messaging.CdmaSmsAck cdmaSmsAck) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cdmaSmsAck, 0);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeLastIncomingCdmaSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void acknowledgeLastIncomingGsmSms(int i, boolean z, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeLastIncomingGsmSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void deleteSmsOnRuim(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method deleteSmsOnRuim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void deleteSmsOnSim(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method deleteSmsOnSim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void getCdmaBroadcastConfig(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCdmaBroadcastConfig is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void getGsmBroadcastConfig(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getGsmBroadcastConfig is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void getSmscAddress(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSmscAddress is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void reportSmsMemoryStatus(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method reportSmsMemoryStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void responseAcknowledgement() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void sendCdmaSms(int i, android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cdmaSmsMessage, 0);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendCdmaSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void sendCdmaSmsExpectMore(int i, android.hardware.radio.messaging.CdmaSmsMessage cdmaSmsMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cdmaSmsMessage, 0);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendCdmaSmsExpectMore is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void sendImsSms(int i, android.hardware.radio.messaging.ImsSmsMessage imsSmsMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsSmsMessage, 0);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendImsSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void sendSms(int i, android.hardware.radio.messaging.GsmSmsMessage gsmSmsMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(gsmSmsMessage, 0);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void sendSmsExpectMore(int i, android.hardware.radio.messaging.GsmSmsMessage gsmSmsMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(gsmSmsMessage, 0);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendSmsExpectMore is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setCdmaBroadcastActivation(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCdmaBroadcastActivation is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setCdmaBroadcastConfig(int i, android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(cdmaBroadcastSmsConfigInfoArr, 0);
                    if (!this.mRemote.transact(17, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCdmaBroadcastConfig is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setGsmBroadcastActivation(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(18, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setGsmBroadcastActivation is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setGsmBroadcastConfig(int i, android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(gsmBroadcastSmsConfigInfoArr, 0);
                    if (!this.mRemote.transact(19, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setGsmBroadcastConfig is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setResponseFunctions(android.hardware.radio.messaging.IRadioMessagingResponse iRadioMessagingResponse, android.hardware.radio.messaging.IRadioMessagingIndication iRadioMessagingIndication) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iRadioMessagingResponse);
                    obtain.writeStrongInterface(iRadioMessagingIndication);
                    if (!this.mRemote.transact(20, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setSmscAddress(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(21, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSmscAddress is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void writeSmsToRuim(int i, android.hardware.radio.messaging.CdmaSmsWriteArgs cdmaSmsWriteArgs) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cdmaSmsWriteArgs, 0);
                    if (!this.mRemote.transact(22, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method writeSmsToRuim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void writeSmsToSim(int i, android.hardware.radio.messaging.SmsWriteArgs smsWriteArgs) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(smsWriteArgs, 0);
                    if (!this.mRemote.transact(23, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method writeSmsToSim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
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

            @Override // android.hardware.radio.messaging.IRadioMessaging
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
