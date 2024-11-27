package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface IRadioNetworkIndication extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$network$IRadioNetworkIndication".replace('$', '.');
    public static final java.lang.String HASH = "c45c122528c07c449ea08f6eacaace17bb7abc38";
    public static final int VERSION = 3;

    void barringInfoChanged(int i, android.hardware.radio.network.CellIdentity cellIdentity, android.hardware.radio.network.BarringInfo[] barringInfoArr) throws android.os.RemoteException;

    void cdmaPrlChanged(int i, int i2) throws android.os.RemoteException;

    void cellInfoList(int i, android.hardware.radio.network.CellInfo[] cellInfoArr) throws android.os.RemoteException;

    void cellularIdentifierDisclosed(int i, android.hardware.radio.network.CellularIdentifierDisclosure cellularIdentifierDisclosure) throws android.os.RemoteException;

    void currentLinkCapacityEstimate(int i, android.hardware.radio.network.LinkCapacityEstimate linkCapacityEstimate) throws android.os.RemoteException;

    void currentPhysicalChannelConfigs(int i, android.hardware.radio.network.PhysicalChannelConfig[] physicalChannelConfigArr) throws android.os.RemoteException;

    void currentSignalStrength(int i, android.hardware.radio.network.SignalStrength signalStrength) throws android.os.RemoteException;

    void emergencyNetworkScanResult(int i, android.hardware.radio.network.EmergencyRegResult emergencyRegResult) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void imsNetworkStateChanged(int i) throws android.os.RemoteException;

    void networkScanResult(int i, android.hardware.radio.network.NetworkScanResult networkScanResult) throws android.os.RemoteException;

    void networkStateChanged(int i) throws android.os.RemoteException;

    void nitzTimeReceived(int i, java.lang.String str, long j, long j2) throws android.os.RemoteException;

    void registrationFailed(int i, android.hardware.radio.network.CellIdentity cellIdentity, java.lang.String str, int i2, int i3, int i4) throws android.os.RemoteException;

    void restrictedStateChanged(int i, int i2) throws android.os.RemoteException;

    void securityAlgorithmsUpdated(int i, android.hardware.radio.network.SecurityAlgorithmUpdate securityAlgorithmUpdate) throws android.os.RemoteException;

    void suppSvcNotify(int i, android.hardware.radio.network.SuppSvcNotification suppSvcNotification) throws android.os.RemoteException;

    void voiceRadioTechChanged(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.network.IRadioNetworkIndication {
        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void barringInfoChanged(int i, android.hardware.radio.network.CellIdentity cellIdentity, android.hardware.radio.network.BarringInfo[] barringInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void cdmaPrlChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void cellInfoList(int i, android.hardware.radio.network.CellInfo[] cellInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void currentLinkCapacityEstimate(int i, android.hardware.radio.network.LinkCapacityEstimate linkCapacityEstimate) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void currentPhysicalChannelConfigs(int i, android.hardware.radio.network.PhysicalChannelConfig[] physicalChannelConfigArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void currentSignalStrength(int i, android.hardware.radio.network.SignalStrength signalStrength) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void imsNetworkStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void networkScanResult(int i, android.hardware.radio.network.NetworkScanResult networkScanResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void networkStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void nitzTimeReceived(int i, java.lang.String str, long j, long j2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void registrationFailed(int i, android.hardware.radio.network.CellIdentity cellIdentity, java.lang.String str, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void restrictedStateChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void suppSvcNotify(int i, android.hardware.radio.network.SuppSvcNotification suppSvcNotification) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void voiceRadioTechChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void emergencyNetworkScanResult(int i, android.hardware.radio.network.EmergencyRegResult emergencyRegResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void cellularIdentifierDisclosed(int i, android.hardware.radio.network.CellularIdentifierDisclosure cellularIdentifierDisclosure) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void securityAlgorithmsUpdated(int i, android.hardware.radio.network.SecurityAlgorithmUpdate securityAlgorithmUpdate) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.network.IRadioNetworkIndication {
        static final int TRANSACTION_barringInfoChanged = 1;
        static final int TRANSACTION_cdmaPrlChanged = 2;
        static final int TRANSACTION_cellInfoList = 3;
        static final int TRANSACTION_cellularIdentifierDisclosed = 16;
        static final int TRANSACTION_currentLinkCapacityEstimate = 4;
        static final int TRANSACTION_currentPhysicalChannelConfigs = 5;
        static final int TRANSACTION_currentSignalStrength = 6;
        static final int TRANSACTION_emergencyNetworkScanResult = 15;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_imsNetworkStateChanged = 7;
        static final int TRANSACTION_networkScanResult = 8;
        static final int TRANSACTION_networkStateChanged = 9;
        static final int TRANSACTION_nitzTimeReceived = 10;
        static final int TRANSACTION_registrationFailed = 11;
        static final int TRANSACTION_restrictedStateChanged = 12;
        static final int TRANSACTION_securityAlgorithmsUpdated = 17;
        static final int TRANSACTION_suppSvcNotify = 13;
        static final int TRANSACTION_voiceRadioTechChanged = 14;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.network.IRadioNetworkIndication asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.network.IRadioNetworkIndication)) {
                return (android.hardware.radio.network.IRadioNetworkIndication) queryLocalInterface;
            }
            return new android.hardware.radio.network.IRadioNetworkIndication.Stub.Proxy(iBinder);
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
                    android.hardware.radio.network.CellIdentity cellIdentity = (android.hardware.radio.network.CellIdentity) parcel.readTypedObject(android.hardware.radio.network.CellIdentity.CREATOR);
                    android.hardware.radio.network.BarringInfo[] barringInfoArr = (android.hardware.radio.network.BarringInfo[]) parcel.createTypedArray(android.hardware.radio.network.BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    barringInfoChanged(readInt, cellIdentity, barringInfoArr);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cdmaPrlChanged(readInt2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    android.hardware.radio.network.CellInfo[] cellInfoArr = (android.hardware.radio.network.CellInfo[]) parcel.createTypedArray(android.hardware.radio.network.CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cellInfoList(readInt4, cellInfoArr);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    android.hardware.radio.network.LinkCapacityEstimate linkCapacityEstimate = (android.hardware.radio.network.LinkCapacityEstimate) parcel.readTypedObject(android.hardware.radio.network.LinkCapacityEstimate.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentLinkCapacityEstimate(readInt5, linkCapacityEstimate);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    android.hardware.radio.network.PhysicalChannelConfig[] physicalChannelConfigArr = (android.hardware.radio.network.PhysicalChannelConfig[]) parcel.createTypedArray(android.hardware.radio.network.PhysicalChannelConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentPhysicalChannelConfigs(readInt6, physicalChannelConfigArr);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    android.hardware.radio.network.SignalStrength signalStrength = (android.hardware.radio.network.SignalStrength) parcel.readTypedObject(android.hardware.radio.network.SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentSignalStrength(readInt7, signalStrength);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    imsNetworkStateChanged(readInt8);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    android.hardware.radio.network.NetworkScanResult networkScanResult = (android.hardware.radio.network.NetworkScanResult) parcel.readTypedObject(android.hardware.radio.network.NetworkScanResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    networkScanResult(readInt9, networkScanResult);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    networkStateChanged(readInt10);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    nitzTimeReceived(readInt11, readString, readLong, readLong2);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    android.hardware.radio.network.CellIdentity cellIdentity2 = (android.hardware.radio.network.CellIdentity) parcel.readTypedObject(android.hardware.radio.network.CellIdentity.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registrationFailed(readInt12, cellIdentity2, readString2, readInt13, readInt14, readInt15);
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restrictedStateChanged(readInt16, readInt17);
                    return true;
                case 13:
                    int readInt18 = parcel.readInt();
                    android.hardware.radio.network.SuppSvcNotification suppSvcNotification = (android.hardware.radio.network.SuppSvcNotification) parcel.readTypedObject(android.hardware.radio.network.SuppSvcNotification.CREATOR);
                    parcel.enforceNoDataAvail();
                    suppSvcNotify(readInt18, suppSvcNotification);
                    return true;
                case 14:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    voiceRadioTechChanged(readInt19, readInt20);
                    return true;
                case 15:
                    int readInt21 = parcel.readInt();
                    android.hardware.radio.network.EmergencyRegResult emergencyRegResult = (android.hardware.radio.network.EmergencyRegResult) parcel.readTypedObject(android.hardware.radio.network.EmergencyRegResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    emergencyNetworkScanResult(readInt21, emergencyRegResult);
                    return true;
                case 16:
                    int readInt22 = parcel.readInt();
                    android.hardware.radio.network.CellularIdentifierDisclosure cellularIdentifierDisclosure = (android.hardware.radio.network.CellularIdentifierDisclosure) parcel.readTypedObject(android.hardware.radio.network.CellularIdentifierDisclosure.CREATOR);
                    parcel.enforceNoDataAvail();
                    cellularIdentifierDisclosed(readInt22, cellularIdentifierDisclosure);
                    return true;
                case 17:
                    int readInt23 = parcel.readInt();
                    android.hardware.radio.network.SecurityAlgorithmUpdate securityAlgorithmUpdate = (android.hardware.radio.network.SecurityAlgorithmUpdate) parcel.readTypedObject(android.hardware.radio.network.SecurityAlgorithmUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    securityAlgorithmsUpdated(readInt23, securityAlgorithmUpdate);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.network.IRadioNetworkIndication {
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

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void barringInfoChanged(int i, android.hardware.radio.network.CellIdentity cellIdentity, android.hardware.radio.network.BarringInfo[] barringInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cellIdentity, 0);
                    obtain.writeTypedArray(barringInfoArr, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method barringInfoChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cdmaPrlChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cdmaPrlChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cellInfoList(int i, android.hardware.radio.network.CellInfo[] cellInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(cellInfoArr, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cellInfoList is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentLinkCapacityEstimate(int i, android.hardware.radio.network.LinkCapacityEstimate linkCapacityEstimate) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(linkCapacityEstimate, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method currentLinkCapacityEstimate is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentPhysicalChannelConfigs(int i, android.hardware.radio.network.PhysicalChannelConfig[] physicalChannelConfigArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(physicalChannelConfigArr, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method currentPhysicalChannelConfigs is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentSignalStrength(int i, android.hardware.radio.network.SignalStrength signalStrength) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(signalStrength, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method currentSignalStrength is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void imsNetworkStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method imsNetworkStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void networkScanResult(int i, android.hardware.radio.network.NetworkScanResult networkScanResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(networkScanResult, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method networkScanResult is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void networkStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method networkStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void nitzTimeReceived(int i, java.lang.String str, long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method nitzTimeReceived is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void registrationFailed(int i, android.hardware.radio.network.CellIdentity cellIdentity, java.lang.String str, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cellIdentity, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method registrationFailed is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void restrictedStateChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method restrictedStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void suppSvcNotify(int i, android.hardware.radio.network.SuppSvcNotification suppSvcNotification) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(suppSvcNotification, 0);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method suppSvcNotify is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void voiceRadioTechChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method voiceRadioTechChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void emergencyNetworkScanResult(int i, android.hardware.radio.network.EmergencyRegResult emergencyRegResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(emergencyRegResult, 0);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method emergencyNetworkScanResult is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cellularIdentifierDisclosed(int i, android.hardware.radio.network.CellularIdentifierDisclosure cellularIdentifierDisclosure) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cellularIdentifierDisclosure, 0);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cellularIdentifierDisclosed is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void securityAlgorithmsUpdated(int i, android.hardware.radio.network.SecurityAlgorithmUpdate securityAlgorithmUpdate) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(securityAlgorithmUpdate, 0);
                    if (!this.mRemote.transact(17, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method securityAlgorithmsUpdated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
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

            @Override // android.hardware.radio.network.IRadioNetworkIndication
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
