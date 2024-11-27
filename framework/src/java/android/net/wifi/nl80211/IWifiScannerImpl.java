package android.net.wifi.nl80211;

/* loaded from: classes2.dex */
public interface IWifiScannerImpl extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.wifi.nl80211.IWifiScannerImpl";
    public static final int SCAN_STATUS_FAILED_ABORT = 3;
    public static final int SCAN_STATUS_FAILED_BUSY = 2;
    public static final int SCAN_STATUS_FAILED_GENERIC = 1;
    public static final int SCAN_STATUS_FAILED_INVALID_ARGS = 5;
    public static final int SCAN_STATUS_FAILED_NODEV = 4;
    public static final int SCAN_STATUS_SUCCESS = 0;
    public static final int SCAN_TYPE_DEFAULT = -1;
    public static final int SCAN_TYPE_HIGH_ACCURACY = 2;
    public static final int SCAN_TYPE_LOW_POWER = 1;
    public static final int SCAN_TYPE_LOW_SPAN = 0;

    void abortScan() throws android.os.RemoteException;

    int getMaxSsidsPerScan() throws android.os.RemoteException;

    android.net.wifi.nl80211.NativeScanResult[] getPnoScanResults() throws android.os.RemoteException;

    android.net.wifi.nl80211.NativeScanResult[] getScanResults() throws android.os.RemoteException;

    boolean scan(android.net.wifi.nl80211.SingleScanSettings singleScanSettings) throws android.os.RemoteException;

    int scanRequest(android.net.wifi.nl80211.SingleScanSettings singleScanSettings) throws android.os.RemoteException;

    boolean startPnoScan(android.net.wifi.nl80211.PnoSettings pnoSettings) throws android.os.RemoteException;

    boolean stopPnoScan() throws android.os.RemoteException;

    void subscribePnoScanEvents(android.net.wifi.nl80211.IPnoScanEvent iPnoScanEvent) throws android.os.RemoteException;

    void subscribeScanEvents(android.net.wifi.nl80211.IScanEvent iScanEvent) throws android.os.RemoteException;

    void unsubscribePnoScanEvents() throws android.os.RemoteException;

    void unsubscribeScanEvents() throws android.os.RemoteException;

    public static class Default implements android.net.wifi.nl80211.IWifiScannerImpl {
        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public android.net.wifi.nl80211.NativeScanResult[] getScanResults() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public android.net.wifi.nl80211.NativeScanResult[] getPnoScanResults() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public int getMaxSsidsPerScan() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public boolean scan(android.net.wifi.nl80211.SingleScanSettings singleScanSettings) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public int scanRequest(android.net.wifi.nl80211.SingleScanSettings singleScanSettings) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void subscribeScanEvents(android.net.wifi.nl80211.IScanEvent iScanEvent) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void unsubscribeScanEvents() throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void subscribePnoScanEvents(android.net.wifi.nl80211.IPnoScanEvent iPnoScanEvent) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void unsubscribePnoScanEvents() throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public boolean startPnoScan(android.net.wifi.nl80211.PnoSettings pnoSettings) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public boolean stopPnoScan() throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IWifiScannerImpl
        public void abortScan() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.wifi.nl80211.IWifiScannerImpl {
        static final int TRANSACTION_abortScan = 12;
        static final int TRANSACTION_getMaxSsidsPerScan = 3;
        static final int TRANSACTION_getPnoScanResults = 2;
        static final int TRANSACTION_getScanResults = 1;
        static final int TRANSACTION_scan = 4;
        static final int TRANSACTION_scanRequest = 5;
        static final int TRANSACTION_startPnoScan = 10;
        static final int TRANSACTION_stopPnoScan = 11;
        static final int TRANSACTION_subscribePnoScanEvents = 8;
        static final int TRANSACTION_subscribeScanEvents = 6;
        static final int TRANSACTION_unsubscribePnoScanEvents = 9;
        static final int TRANSACTION_unsubscribeScanEvents = 7;

        public Stub() {
            attachInterface(this, android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
        }

        public static android.net.wifi.nl80211.IWifiScannerImpl asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.wifi.nl80211.IWifiScannerImpl)) {
                return (android.net.wifi.nl80211.IWifiScannerImpl) queryLocalInterface;
            }
            return new android.net.wifi.nl80211.IWifiScannerImpl.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getScanResults";
                case 2:
                    return "getPnoScanResults";
                case 3:
                    return "getMaxSsidsPerScan";
                case 4:
                    return "scan";
                case 5:
                    return "scanRequest";
                case 6:
                    return "subscribeScanEvents";
                case 7:
                    return "unsubscribeScanEvents";
                case 8:
                    return "subscribePnoScanEvents";
                case 9:
                    return "unsubscribePnoScanEvents";
                case 10:
                    return "startPnoScan";
                case 11:
                    return "stopPnoScan";
                case 12:
                    return "abortScan";
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
                parcel.enforceInterface(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.net.wifi.nl80211.NativeScanResult[] scanResults = getScanResults();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(scanResults, 1);
                    return true;
                case 2:
                    android.net.wifi.nl80211.NativeScanResult[] pnoScanResults = getPnoScanResults();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(pnoScanResults, 1);
                    return true;
                case 3:
                    int maxSsidsPerScan = getMaxSsidsPerScan();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxSsidsPerScan);
                    return true;
                case 4:
                    android.net.wifi.nl80211.SingleScanSettings singleScanSettings = (android.net.wifi.nl80211.SingleScanSettings) parcel.readTypedObject(android.net.wifi.nl80211.SingleScanSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean scan = scan(singleScanSettings);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(scan);
                    return true;
                case 5:
                    android.net.wifi.nl80211.SingleScanSettings singleScanSettings2 = (android.net.wifi.nl80211.SingleScanSettings) parcel.readTypedObject(android.net.wifi.nl80211.SingleScanSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    int scanRequest = scanRequest(singleScanSettings2);
                    parcel2.writeNoException();
                    parcel2.writeInt(scanRequest);
                    return true;
                case 6:
                    android.net.wifi.nl80211.IScanEvent asInterface = android.net.wifi.nl80211.IScanEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribeScanEvents(asInterface);
                    return true;
                case 7:
                    unsubscribeScanEvents();
                    return true;
                case 8:
                    android.net.wifi.nl80211.IPnoScanEvent asInterface2 = android.net.wifi.nl80211.IPnoScanEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribePnoScanEvents(asInterface2);
                    return true;
                case 9:
                    unsubscribePnoScanEvents();
                    return true;
                case 10:
                    android.net.wifi.nl80211.PnoSettings pnoSettings = (android.net.wifi.nl80211.PnoSettings) parcel.readTypedObject(android.net.wifi.nl80211.PnoSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean startPnoScan = startPnoScan(pnoSettings);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startPnoScan);
                    return true;
                case 11:
                    boolean stopPnoScan = stopPnoScan();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopPnoScan);
                    return true;
                case 12:
                    abortScan();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.wifi.nl80211.IWifiScannerImpl {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public android.net.wifi.nl80211.NativeScanResult[] getScanResults() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.wifi.nl80211.NativeScanResult[]) obtain2.createTypedArray(android.net.wifi.nl80211.NativeScanResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public android.net.wifi.nl80211.NativeScanResult[] getPnoScanResults() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.wifi.nl80211.NativeScanResult[]) obtain2.createTypedArray(android.net.wifi.nl80211.NativeScanResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public int getMaxSsidsPerScan() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public boolean scan(android.net.wifi.nl80211.SingleScanSettings singleScanSettings) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    obtain.writeTypedObject(singleScanSettings, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public int scanRequest(android.net.wifi.nl80211.SingleScanSettings singleScanSettings) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    obtain.writeTypedObject(singleScanSettings, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void subscribeScanEvents(android.net.wifi.nl80211.IScanEvent iScanEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    obtain.writeStrongInterface(iScanEvent);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void unsubscribeScanEvents() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void subscribePnoScanEvents(android.net.wifi.nl80211.IPnoScanEvent iPnoScanEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    obtain.writeStrongInterface(iPnoScanEvent);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void unsubscribePnoScanEvents() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public boolean startPnoScan(android.net.wifi.nl80211.PnoSettings pnoSettings) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    obtain.writeTypedObject(pnoSettings, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public boolean stopPnoScan() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWifiScannerImpl
            public void abortScan() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWifiScannerImpl.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
