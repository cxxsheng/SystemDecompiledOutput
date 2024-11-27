package android.hardware.radio.ims;

/* loaded from: classes2.dex */
public interface IRadioIms extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$ims$IRadioIms".replace('$', '.');
    public static final java.lang.String HASH = "ec0dfedf764f3916783848c540ad312a74fa755d";
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void sendAnbrQuery(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void setResponseFunctions(android.hardware.radio.ims.IRadioImsResponse iRadioImsResponse, android.hardware.radio.ims.IRadioImsIndication iRadioImsIndication) throws android.os.RemoteException;

    void setSrvccCallInfo(int i, android.hardware.radio.ims.SrvccCall[] srvccCallArr) throws android.os.RemoteException;

    void startImsTraffic(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException;

    void stopImsTraffic(int i, int i2) throws android.os.RemoteException;

    void triggerEpsFallback(int i, int i2) throws android.os.RemoteException;

    void updateImsCallStatus(int i, android.hardware.radio.ims.ImsCall[] imsCallArr) throws android.os.RemoteException;

    void updateImsRegistrationInfo(int i, android.hardware.radio.ims.ImsRegistration imsRegistration) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.ims.IRadioIms {
        @Override // android.hardware.radio.ims.IRadioIms
        public void setSrvccCallInfo(int i, android.hardware.radio.ims.SrvccCall[] srvccCallArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void updateImsRegistrationInfo(int i, android.hardware.radio.ims.ImsRegistration imsRegistration) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void startImsTraffic(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void stopImsTraffic(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void triggerEpsFallback(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void setResponseFunctions(android.hardware.radio.ims.IRadioImsResponse iRadioImsResponse, android.hardware.radio.ims.IRadioImsIndication iRadioImsIndication) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void sendAnbrQuery(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void updateImsCallStatus(int i, android.hardware.radio.ims.ImsCall[] imsCallArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.ims.IRadioIms {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_sendAnbrQuery = 7;
        static final int TRANSACTION_setResponseFunctions = 6;
        static final int TRANSACTION_setSrvccCallInfo = 1;
        static final int TRANSACTION_startImsTraffic = 3;
        static final int TRANSACTION_stopImsTraffic = 4;
        static final int TRANSACTION_triggerEpsFallback = 5;
        static final int TRANSACTION_updateImsCallStatus = 8;
        static final int TRANSACTION_updateImsRegistrationInfo = 2;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.ims.IRadioIms asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.ims.IRadioIms)) {
                return (android.hardware.radio.ims.IRadioIms) queryLocalInterface;
            }
            return new android.hardware.radio.ims.IRadioIms.Stub.Proxy(iBinder);
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
                    android.hardware.radio.ims.SrvccCall[] srvccCallArr = (android.hardware.radio.ims.SrvccCall[]) parcel.createTypedArray(android.hardware.radio.ims.SrvccCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSrvccCallInfo(readInt, srvccCallArr);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.hardware.radio.ims.ImsRegistration imsRegistration = (android.hardware.radio.ims.ImsRegistration) parcel.readTypedObject(android.hardware.radio.ims.ImsRegistration.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateImsRegistrationInfo(readInt2, imsRegistration);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startImsTraffic(readInt3, readInt4, readInt5, readInt6, readInt7);
                    return true;
                case 4:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopImsTraffic(readInt8, readInt9);
                    return true;
                case 5:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerEpsFallback(readInt10, readInt11);
                    return true;
                case 6:
                    android.hardware.radio.ims.IRadioImsResponse asInterface = android.hardware.radio.ims.IRadioImsResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.radio.ims.IRadioImsIndication asInterface2 = android.hardware.radio.ims.IRadioImsIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    return true;
                case 7:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAnbrQuery(readInt12, readInt13, readInt14, readInt15);
                    return true;
                case 8:
                    int readInt16 = parcel.readInt();
                    android.hardware.radio.ims.ImsCall[] imsCallArr = (android.hardware.radio.ims.ImsCall[]) parcel.createTypedArray(android.hardware.radio.ims.ImsCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateImsCallStatus(readInt16, imsCallArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.ims.IRadioIms {
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

            @Override // android.hardware.radio.ims.IRadioIms
            public void setSrvccCallInfo(int i, android.hardware.radio.ims.SrvccCall[] srvccCallArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(srvccCallArr, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSrvccCallInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void updateImsRegistrationInfo(int i, android.hardware.radio.ims.ImsRegistration imsRegistration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsRegistration, 0);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method updateImsRegistrationInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void startImsTraffic(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startImsTraffic is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void stopImsTraffic(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stopImsTraffic is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void triggerEpsFallback(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method triggerEpsFallback is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void setResponseFunctions(android.hardware.radio.ims.IRadioImsResponse iRadioImsResponse, android.hardware.radio.ims.IRadioImsIndication iRadioImsIndication) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iRadioImsResponse);
                    obtain.writeStrongInterface(iRadioImsIndication);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void sendAnbrQuery(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendAnbrQuery is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void updateImsCallStatus(int i, android.hardware.radio.ims.ImsCall[] imsCallArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(imsCallArr, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method updateImsCallStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
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

            @Override // android.hardware.radio.ims.IRadioIms
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
