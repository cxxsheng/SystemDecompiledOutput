package android.telephony.satellite.stub;

/* loaded from: classes3.dex */
public interface ISatellite extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.satellite.stub.ISatellite";

    void abortSendingSatelliteDatagrams(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void deprovisionSatelliteService(java.lang.String str, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void enableCellularModemWhileSatelliteModeIsOn(boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void pollPendingSatelliteDatagrams(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void provisionSatelliteService(java.lang.String str, byte[] bArr, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void requestIsSatelliteCommunicationAllowedForCurrentLocation(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException;

    void requestIsSatelliteEnabled(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException;

    void requestIsSatelliteEnabledForCarrier(int i, android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException;

    void requestIsSatelliteProvisioned(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException;

    void requestIsSatelliteSupported(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException;

    void requestSatelliteCapabilities(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) throws android.os.RemoteException;

    void requestSatelliteEnabled(boolean z, boolean z2, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void requestSatelliteListeningEnabled(boolean z, int i, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void requestSatelliteModemState(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IIntegerConsumer iIntegerConsumer2) throws android.os.RemoteException;

    void requestSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.stub.INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) throws android.os.RemoteException;

    void requestTimeForNextSatelliteVisibility(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IIntegerConsumer iIntegerConsumer2) throws android.os.RemoteException;

    void sendSatelliteDatagram(android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram, boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void setSatelliteEnabledForCarrier(int i, boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void setSatelliteListener(android.telephony.satellite.stub.ISatelliteListener iSatelliteListener) throws android.os.RemoteException;

    void setSatellitePlmn(int i, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void startSendingNtnSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void startSendingSatellitePointingInfo(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void stopSendingNtnSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void stopSendingSatellitePointingInfo(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    public static class Default implements android.telephony.satellite.stub.ISatellite {
        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatelliteListener(android.telephony.satellite.stub.ISatelliteListener iSatelliteListener) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteListeningEnabled(boolean z, int i, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void enableCellularModemWhileSatelliteModeIsOn(boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteEnabled(boolean z, boolean z2, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteEnabled(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteSupported(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteCapabilities(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void startSendingSatellitePointingInfo(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void stopSendingSatellitePointingInfo(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void provisionSatelliteService(java.lang.String str, byte[] bArr, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void deprovisionSatelliteService(java.lang.String str, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteProvisioned(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void pollPendingSatelliteDatagrams(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void sendSatelliteDatagram(android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram, boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteModemState(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IIntegerConsumer iIntegerConsumer2) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteCommunicationAllowedForCurrentLocation(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestTimeForNextSatelliteVisibility(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IIntegerConsumer iIntegerConsumer2) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatellitePlmn(int i, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatelliteEnabledForCarrier(int i, boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteEnabledForCarrier(int i, android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.stub.INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void startSendingNtnSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void stopSendingNtnSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void abortSendingSatelliteDatagrams(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.satellite.stub.ISatellite {
        static final int TRANSACTION_abortSendingSatelliteDatagrams = 24;
        static final int TRANSACTION_deprovisionSatelliteService = 11;
        static final int TRANSACTION_enableCellularModemWhileSatelliteModeIsOn = 3;
        static final int TRANSACTION_pollPendingSatelliteDatagrams = 13;
        static final int TRANSACTION_provisionSatelliteService = 10;
        static final int TRANSACTION_requestIsSatelliteCommunicationAllowedForCurrentLocation = 16;
        static final int TRANSACTION_requestIsSatelliteEnabled = 5;
        static final int TRANSACTION_requestIsSatelliteEnabledForCarrier = 20;
        static final int TRANSACTION_requestIsSatelliteProvisioned = 12;
        static final int TRANSACTION_requestIsSatelliteSupported = 6;
        static final int TRANSACTION_requestSatelliteCapabilities = 7;
        static final int TRANSACTION_requestSatelliteEnabled = 4;
        static final int TRANSACTION_requestSatelliteListeningEnabled = 2;
        static final int TRANSACTION_requestSatelliteModemState = 15;
        static final int TRANSACTION_requestSignalStrength = 21;
        static final int TRANSACTION_requestTimeForNextSatelliteVisibility = 17;
        static final int TRANSACTION_sendSatelliteDatagram = 14;
        static final int TRANSACTION_setSatelliteEnabledForCarrier = 19;
        static final int TRANSACTION_setSatelliteListener = 1;
        static final int TRANSACTION_setSatellitePlmn = 18;
        static final int TRANSACTION_startSendingNtnSignalStrength = 22;
        static final int TRANSACTION_startSendingSatellitePointingInfo = 8;
        static final int TRANSACTION_stopSendingNtnSignalStrength = 23;
        static final int TRANSACTION_stopSendingSatellitePointingInfo = 9;

        public Stub() {
            attachInterface(this, android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
        }

        public static android.telephony.satellite.stub.ISatellite asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.satellite.stub.ISatellite)) {
                return (android.telephony.satellite.stub.ISatellite) queryLocalInterface;
            }
            return new android.telephony.satellite.stub.ISatellite.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setSatelliteListener";
                case 2:
                    return "requestSatelliteListeningEnabled";
                case 3:
                    return "enableCellularModemWhileSatelliteModeIsOn";
                case 4:
                    return "requestSatelliteEnabled";
                case 5:
                    return "requestIsSatelliteEnabled";
                case 6:
                    return "requestIsSatelliteSupported";
                case 7:
                    return "requestSatelliteCapabilities";
                case 8:
                    return "startSendingSatellitePointingInfo";
                case 9:
                    return "stopSendingSatellitePointingInfo";
                case 10:
                    return "provisionSatelliteService";
                case 11:
                    return "deprovisionSatelliteService";
                case 12:
                    return "requestIsSatelliteProvisioned";
                case 13:
                    return "pollPendingSatelliteDatagrams";
                case 14:
                    return "sendSatelliteDatagram";
                case 15:
                    return "requestSatelliteModemState";
                case 16:
                    return "requestIsSatelliteCommunicationAllowedForCurrentLocation";
                case 17:
                    return "requestTimeForNextSatelliteVisibility";
                case 18:
                    return "setSatellitePlmn";
                case 19:
                    return "setSatelliteEnabledForCarrier";
                case 20:
                    return "requestIsSatelliteEnabledForCarrier";
                case 21:
                    return "requestSignalStrength";
                case 22:
                    return "startSendingNtnSignalStrength";
                case 23:
                    return "stopSendingNtnSignalStrength";
                case 24:
                    return "abortSendingSatelliteDatagrams";
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
                parcel.enforceInterface(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.satellite.stub.ISatelliteListener asInterface = android.telephony.satellite.stub.ISatelliteListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSatelliteListener(asInterface);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    android.telephony.IIntegerConsumer asInterface2 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteListeningEnabled(readBoolean, readInt, asInterface2);
                    return true;
                case 3:
                    boolean readBoolean2 = parcel.readBoolean();
                    android.telephony.IIntegerConsumer asInterface3 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableCellularModemWhileSatelliteModeIsOn(readBoolean2, asInterface3);
                    return true;
                case 4:
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    android.telephony.IIntegerConsumer asInterface4 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteEnabled(readBoolean3, readBoolean4, asInterface4);
                    return true;
                case 5:
                    android.telephony.IIntegerConsumer asInterface5 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.IBooleanConsumer asInterface6 = android.telephony.IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteEnabled(asInterface5, asInterface6);
                    return true;
                case 6:
                    android.telephony.IIntegerConsumer asInterface7 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.IBooleanConsumer asInterface8 = android.telephony.IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteSupported(asInterface7, asInterface8);
                    return true;
                case 7:
                    android.telephony.IIntegerConsumer asInterface9 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer asInterface10 = android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteCapabilities(asInterface9, asInterface10);
                    return true;
                case 8:
                    android.telephony.IIntegerConsumer asInterface11 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSendingSatellitePointingInfo(asInterface11);
                    return true;
                case 9:
                    android.telephony.IIntegerConsumer asInterface12 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopSendingSatellitePointingInfo(asInterface12);
                    return true;
                case 10:
                    java.lang.String readString = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    android.telephony.IIntegerConsumer asInterface13 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    provisionSatelliteService(readString, createByteArray, asInterface13);
                    return true;
                case 11:
                    java.lang.String readString2 = parcel.readString();
                    android.telephony.IIntegerConsumer asInterface14 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deprovisionSatelliteService(readString2, asInterface14);
                    return true;
                case 12:
                    android.telephony.IIntegerConsumer asInterface15 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.IBooleanConsumer asInterface16 = android.telephony.IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteProvisioned(asInterface15, asInterface16);
                    return true;
                case 13:
                    android.telephony.IIntegerConsumer asInterface17 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    pollPendingSatelliteDatagrams(asInterface17);
                    return true;
                case 14:
                    android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram = (android.telephony.satellite.stub.SatelliteDatagram) parcel.readTypedObject(android.telephony.satellite.stub.SatelliteDatagram.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    android.telephony.IIntegerConsumer asInterface18 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendSatelliteDatagram(satelliteDatagram, readBoolean5, asInterface18);
                    return true;
                case 15:
                    android.telephony.IIntegerConsumer asInterface19 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.IIntegerConsumer asInterface20 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteModemState(asInterface19, asInterface20);
                    return true;
                case 16:
                    android.telephony.IIntegerConsumer asInterface21 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.IBooleanConsumer asInterface22 = android.telephony.IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteCommunicationAllowedForCurrentLocation(asInterface21, asInterface22);
                    return true;
                case 17:
                    android.telephony.IIntegerConsumer asInterface23 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.IIntegerConsumer asInterface24 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestTimeForNextSatelliteVisibility(asInterface23, asInterface24);
                    return true;
                case 18:
                    int readInt2 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    android.telephony.IIntegerConsumer asInterface25 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSatellitePlmn(readInt2, createStringArrayList, createStringArrayList2, asInterface25);
                    return true;
                case 19:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    android.telephony.IIntegerConsumer asInterface26 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSatelliteEnabledForCarrier(readInt3, readBoolean6, asInterface26);
                    return true;
                case 20:
                    int readInt4 = parcel.readInt();
                    android.telephony.IIntegerConsumer asInterface27 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.IBooleanConsumer asInterface28 = android.telephony.IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteEnabledForCarrier(readInt4, asInterface27, asInterface28);
                    return true;
                case 21:
                    android.telephony.IIntegerConsumer asInterface29 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.satellite.stub.INtnSignalStrengthConsumer asInterface30 = android.telephony.satellite.stub.INtnSignalStrengthConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSignalStrength(asInterface29, asInterface30);
                    return true;
                case 22:
                    android.telephony.IIntegerConsumer asInterface31 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSendingNtnSignalStrength(asInterface31);
                    return true;
                case 23:
                    android.telephony.IIntegerConsumer asInterface32 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopSendingNtnSignalStrength(asInterface32);
                    return true;
                case 24:
                    android.telephony.IIntegerConsumer asInterface33 = android.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    abortSendingSatelliteDatagrams(asInterface33);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.satellite.stub.ISatellite {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.satellite.stub.ISatellite.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void setSatelliteListener(android.telephony.satellite.stub.ISatelliteListener iSatelliteListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iSatelliteListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteListeningEnabled(boolean z, int i, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void enableCellularModemWhileSatelliteModeIsOn(boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteEnabled(boolean z, boolean z2, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteEnabled(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteSupported(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteCapabilities(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iSatelliteCapabilitiesConsumer);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void startSendingSatellitePointingInfo(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void stopSendingSatellitePointingInfo(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void provisionSatelliteService(java.lang.String str, byte[] bArr, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void deprovisionSatelliteService(java.lang.String str, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteProvisioned(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void pollPendingSatelliteDatagrams(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void sendSatelliteDatagram(android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram, boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeTypedObject(satelliteDatagram, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteModemState(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IIntegerConsumer iIntegerConsumer2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iIntegerConsumer2);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteCommunicationAllowedForCurrentLocation(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestTimeForNextSatelliteVisibility(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IIntegerConsumer iIntegerConsumer2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iIntegerConsumer2);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void setSatellitePlmn(int i, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void setSatelliteEnabledForCarrier(int i, boolean z, android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteEnabledForCarrier(int i, android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.stub.INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iNtnSignalStrengthConsumer);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void startSendingNtnSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void stopSendingNtnSignalStrength(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void abortSendingSatelliteDatagrams(android.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 23;
        }
    }
}
