package android.net.wifi.nl80211;

/* loaded from: classes2.dex */
public interface IClientInterface extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.wifi.nl80211.IClientInterface";

    void SendMgmtFrame(byte[] bArr, android.net.wifi.nl80211.ISendMgmtFrameEvent iSendMgmtFrameEvent, int i) throws android.os.RemoteException;

    java.lang.String getInterfaceName() throws android.os.RemoteException;

    byte[] getMacAddress() throws android.os.RemoteException;

    int[] getPacketCounters() throws android.os.RemoteException;

    android.net.wifi.nl80211.IWifiScannerImpl getWifiScannerImpl() throws android.os.RemoteException;

    int[] signalPoll() throws android.os.RemoteException;

    public static class Default implements android.net.wifi.nl80211.IClientInterface {
        @Override // android.net.wifi.nl80211.IClientInterface
        public int[] getPacketCounters() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IClientInterface
        public int[] signalPoll() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IClientInterface
        public byte[] getMacAddress() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IClientInterface
        public java.lang.String getInterfaceName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IClientInterface
        public android.net.wifi.nl80211.IWifiScannerImpl getWifiScannerImpl() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IClientInterface
        public void SendMgmtFrame(byte[] bArr, android.net.wifi.nl80211.ISendMgmtFrameEvent iSendMgmtFrameEvent, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.wifi.nl80211.IClientInterface {
        static final int TRANSACTION_SendMgmtFrame = 6;
        static final int TRANSACTION_getInterfaceName = 4;
        static final int TRANSACTION_getMacAddress = 3;
        static final int TRANSACTION_getPacketCounters = 1;
        static final int TRANSACTION_getWifiScannerImpl = 5;
        static final int TRANSACTION_signalPoll = 2;

        public Stub() {
            attachInterface(this, android.net.wifi.nl80211.IClientInterface.DESCRIPTOR);
        }

        public static android.net.wifi.nl80211.IClientInterface asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.wifi.nl80211.IClientInterface.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.wifi.nl80211.IClientInterface)) {
                return (android.net.wifi.nl80211.IClientInterface) queryLocalInterface;
            }
            return new android.net.wifi.nl80211.IClientInterface.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPacketCounters";
                case 2:
                    return "signalPoll";
                case 3:
                    return "getMacAddress";
                case 4:
                    return "getInterfaceName";
                case 5:
                    return "getWifiScannerImpl";
                case 6:
                    return "SendMgmtFrame";
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
                parcel.enforceInterface(android.net.wifi.nl80211.IClientInterface.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.wifi.nl80211.IClientInterface.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] packetCounters = getPacketCounters();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(packetCounters);
                    return true;
                case 2:
                    int[] signalPoll = signalPoll();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(signalPoll);
                    return true;
                case 3:
                    byte[] macAddress = getMacAddress();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(macAddress);
                    return true;
                case 4:
                    java.lang.String interfaceName = getInterfaceName();
                    parcel2.writeNoException();
                    parcel2.writeString(interfaceName);
                    return true;
                case 5:
                    android.net.wifi.nl80211.IWifiScannerImpl wifiScannerImpl = getWifiScannerImpl();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(wifiScannerImpl);
                    return true;
                case 6:
                    byte[] createByteArray = parcel.createByteArray();
                    android.net.wifi.nl80211.ISendMgmtFrameEvent asInterface = android.net.wifi.nl80211.ISendMgmtFrameEvent.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SendMgmtFrame(createByteArray, asInterface, readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.wifi.nl80211.IClientInterface {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.wifi.nl80211.IClientInterface.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public int[] getPacketCounters() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IClientInterface.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public int[] signalPoll() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IClientInterface.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public byte[] getMacAddress() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IClientInterface.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public java.lang.String getInterfaceName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IClientInterface.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public android.net.wifi.nl80211.IWifiScannerImpl getWifiScannerImpl() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IClientInterface.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.net.wifi.nl80211.IWifiScannerImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IClientInterface
            public void SendMgmtFrame(byte[] bArr, android.net.wifi.nl80211.ISendMgmtFrameEvent iSendMgmtFrameEvent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IClientInterface.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iSendMgmtFrameEvent);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
