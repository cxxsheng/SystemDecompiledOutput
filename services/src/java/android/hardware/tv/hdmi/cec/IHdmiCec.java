package android.hardware.tv.hdmi.cec;

/* loaded from: classes.dex */
public interface IHdmiCec extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$hdmi$cec$IHdmiCec".replace('$', '.');
    public static final java.lang.String HASH = "cd956e3a0c2e6ade71693c85e9f0aeffa221ea26";
    public static final int VERSION = 1;

    byte addLogicalAddress(byte b) throws android.os.RemoteException;

    void clearLogicalAddress() throws android.os.RemoteException;

    void enableAudioReturnChannel(int i, boolean z) throws android.os.RemoteException;

    void enableCec(boolean z) throws android.os.RemoteException;

    void enableSystemCecControl(boolean z) throws android.os.RemoteException;

    void enableWakeupByOtp(boolean z) throws android.os.RemoteException;

    int getCecVersion() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    int getPhysicalAddress() throws android.os.RemoteException;

    int getVendorId() throws android.os.RemoteException;

    byte sendMessage(android.hardware.tv.hdmi.cec.CecMessage cecMessage) throws android.os.RemoteException;

    void setCallback(android.hardware.tv.hdmi.cec.IHdmiCecCallback iHdmiCecCallback) throws android.os.RemoteException;

    void setLanguage(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.hdmi.cec.IHdmiCec {
        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public byte addLogicalAddress(byte b) throws android.os.RemoteException {
            return (byte) 0;
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public void clearLogicalAddress() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public void enableAudioReturnChannel(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public int getCecVersion() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public int getPhysicalAddress() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public int getVendorId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public byte sendMessage(android.hardware.tv.hdmi.cec.CecMessage cecMessage) throws android.os.RemoteException {
            return (byte) 0;
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public void setCallback(android.hardware.tv.hdmi.cec.IHdmiCecCallback iHdmiCecCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public void setLanguage(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public void enableWakeupByOtp(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public void enableCec(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public void enableSystemCecControl(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCec
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.hdmi.cec.IHdmiCec {
        static final int TRANSACTION_addLogicalAddress = 1;
        static final int TRANSACTION_clearLogicalAddress = 2;
        static final int TRANSACTION_enableAudioReturnChannel = 3;
        static final int TRANSACTION_enableCec = 11;
        static final int TRANSACTION_enableSystemCecControl = 12;
        static final int TRANSACTION_enableWakeupByOtp = 10;
        static final int TRANSACTION_getCecVersion = 4;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPhysicalAddress = 5;
        static final int TRANSACTION_getVendorId = 6;
        static final int TRANSACTION_sendMessage = 7;
        static final int TRANSACTION_setCallback = 8;
        static final int TRANSACTION_setLanguage = 9;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
        }

        public static android.hardware.tv.hdmi.cec.IHdmiCec asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.hdmi.cec.IHdmiCec)) {
                return (android.hardware.tv.hdmi.cec.IHdmiCec) queryLocalInterface;
            }
            return new android.hardware.tv.hdmi.cec.IHdmiCec.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR;
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
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    byte addLogicalAddress = addLogicalAddress(readByte);
                    parcel2.writeNoException();
                    parcel2.writeByte(addLogicalAddress);
                    return true;
                case 2:
                    clearLogicalAddress();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableAudioReturnChannel(readInt, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int cecVersion = getCecVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(cecVersion);
                    return true;
                case 5:
                    int physicalAddress = getPhysicalAddress();
                    parcel2.writeNoException();
                    parcel2.writeInt(physicalAddress);
                    return true;
                case 6:
                    int vendorId = getVendorId();
                    parcel2.writeNoException();
                    parcel2.writeInt(vendorId);
                    return true;
                case 7:
                    android.hardware.tv.hdmi.cec.CecMessage cecMessage = (android.hardware.tv.hdmi.cec.CecMessage) parcel.readTypedObject(android.hardware.tv.hdmi.cec.CecMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte sendMessage = sendMessage(cecMessage);
                    parcel2.writeNoException();
                    parcel2.writeByte(sendMessage);
                    return true;
                case 8:
                    android.hardware.tv.hdmi.cec.IHdmiCecCallback asInterface = android.hardware.tv.hdmi.cec.IHdmiCecCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setLanguage(readString);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableWakeupByOtp(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableCec(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableSystemCecControl(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.tv.hdmi.cec.IHdmiCec {
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
                return android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR;
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public byte addLogicalAddress(byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method addLogicalAddress is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public void clearLogicalAddress() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method clearLogicalAddress is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public void enableAudioReturnChannel(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enableAudioReturnChannel is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public int getCecVersion() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCecVersion is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public int getPhysicalAddress() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getPhysicalAddress is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public int getVendorId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getVendorId is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public byte sendMessage(android.hardware.tv.hdmi.cec.CecMessage cecMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    obtain.writeTypedObject(cecMessage, 0);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method sendMessage is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public void setCallback(android.hardware.tv.hdmi.cec.IHdmiCecCallback iHdmiCecCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiCecCallback);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public void setLanguage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setLanguage is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public void enableWakeupByOtp(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enableWakeupByOtp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public void enableCec(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enableCec is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public void enableSystemCecControl(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enableSystemCecControl is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
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

            @Override // android.hardware.tv.hdmi.cec.IHdmiCec
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.tv.hdmi.cec.IHdmiCec.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
