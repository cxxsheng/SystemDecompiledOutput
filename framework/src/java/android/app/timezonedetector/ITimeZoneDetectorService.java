package android.app.timezonedetector;

/* loaded from: classes.dex */
public interface ITimeZoneDetectorService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.timezonedetector.ITimeZoneDetectorService";

    void addListener(android.app.time.ITimeZoneDetectorListener iTimeZoneDetectorListener) throws android.os.RemoteException;

    boolean confirmTimeZone(java.lang.String str) throws android.os.RemoteException;

    android.app.time.TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig() throws android.os.RemoteException;

    android.app.time.TimeZoneState getTimeZoneState() throws android.os.RemoteException;

    void removeListener(android.app.time.ITimeZoneDetectorListener iTimeZoneDetectorListener) throws android.os.RemoteException;

    boolean setManualTimeZone(android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws android.os.RemoteException;

    boolean suggestManualTimeZone(android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws android.os.RemoteException;

    void suggestTelephonyTimeZone(android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) throws android.os.RemoteException;

    boolean updateConfiguration(android.app.time.TimeZoneConfiguration timeZoneConfiguration) throws android.os.RemoteException;

    public static class Default implements android.app.timezonedetector.ITimeZoneDetectorService {
        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public android.app.time.TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public void addListener(android.app.time.ITimeZoneDetectorListener iTimeZoneDetectorListener) throws android.os.RemoteException {
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public void removeListener(android.app.time.ITimeZoneDetectorListener iTimeZoneDetectorListener) throws android.os.RemoteException {
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public boolean updateConfiguration(android.app.time.TimeZoneConfiguration timeZoneConfiguration) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public android.app.time.TimeZoneState getTimeZoneState() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public boolean confirmTimeZone(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public boolean setManualTimeZone(android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public boolean suggestManualTimeZone(android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public void suggestTelephonyTimeZone(android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.timezonedetector.ITimeZoneDetectorService {
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_confirmTimeZone = 6;
        static final int TRANSACTION_getCapabilitiesAndConfig = 1;
        static final int TRANSACTION_getTimeZoneState = 5;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_setManualTimeZone = 7;
        static final int TRANSACTION_suggestManualTimeZone = 8;
        static final int TRANSACTION_suggestTelephonyTimeZone = 9;
        static final int TRANSACTION_updateConfiguration = 4;

        public Stub() {
            attachInterface(this, android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
        }

        public static android.app.timezonedetector.ITimeZoneDetectorService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.timezonedetector.ITimeZoneDetectorService)) {
                return (android.app.timezonedetector.ITimeZoneDetectorService) queryLocalInterface;
            }
            return new android.app.timezonedetector.ITimeZoneDetectorService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCapabilitiesAndConfig";
                case 2:
                    return "addListener";
                case 3:
                    return "removeListener";
                case 4:
                    return "updateConfiguration";
                case 5:
                    return "getTimeZoneState";
                case 6:
                    return "confirmTimeZone";
                case 7:
                    return "setManualTimeZone";
                case 8:
                    return "suggestManualTimeZone";
                case 9:
                    return "suggestTelephonyTimeZone";
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
                parcel.enforceInterface(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.time.TimeZoneCapabilitiesAndConfig capabilitiesAndConfig = getCapabilitiesAndConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesAndConfig, 1);
                    return true;
                case 2:
                    android.app.time.ITimeZoneDetectorListener asInterface = android.app.time.ITimeZoneDetectorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.app.time.ITimeZoneDetectorListener asInterface2 = android.app.time.ITimeZoneDetectorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.app.time.TimeZoneConfiguration timeZoneConfiguration = (android.app.time.TimeZoneConfiguration) parcel.readTypedObject(android.app.time.TimeZoneConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateConfiguration = updateConfiguration(timeZoneConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateConfiguration);
                    return true;
                case 5:
                    android.app.time.TimeZoneState timeZoneState = getTimeZoneState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(timeZoneState, 1);
                    return true;
                case 6:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean confirmTimeZone = confirmTimeZone(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(confirmTimeZone);
                    return true;
                case 7:
                    android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion = (android.app.timezonedetector.ManualTimeZoneSuggestion) parcel.readTypedObject(android.app.timezonedetector.ManualTimeZoneSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean manualTimeZone = setManualTimeZone(manualTimeZoneSuggestion);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(manualTimeZone);
                    return true;
                case 8:
                    android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion2 = (android.app.timezonedetector.ManualTimeZoneSuggestion) parcel.readTypedObject(android.app.timezonedetector.ManualTimeZoneSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean suggestManualTimeZone = suggestManualTimeZone(manualTimeZoneSuggestion2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(suggestManualTimeZone);
                    return true;
                case 9:
                    android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion = (android.app.timezonedetector.TelephonyTimeZoneSuggestion) parcel.readTypedObject(android.app.timezonedetector.TelephonyTimeZoneSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    suggestTelephonyTimeZone(telephonyTimeZoneSuggestion);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.timezonedetector.ITimeZoneDetectorService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR;
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public android.app.time.TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.time.TimeZoneCapabilitiesAndConfig) obtain2.readTypedObject(android.app.time.TimeZoneCapabilitiesAndConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public void addListener(android.app.time.ITimeZoneDetectorListener iTimeZoneDetectorListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
                    obtain.writeStrongInterface(iTimeZoneDetectorListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public void removeListener(android.app.time.ITimeZoneDetectorListener iTimeZoneDetectorListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
                    obtain.writeStrongInterface(iTimeZoneDetectorListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public boolean updateConfiguration(android.app.time.TimeZoneConfiguration timeZoneConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(timeZoneConfiguration, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public android.app.time.TimeZoneState getTimeZoneState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.time.TimeZoneState) obtain2.readTypedObject(android.app.time.TimeZoneState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public boolean confirmTimeZone(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public boolean setManualTimeZone(android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(manualTimeZoneSuggestion, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public boolean suggestManualTimeZone(android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(manualTimeZoneSuggestion, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public void suggestTelephonyTimeZone(android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timezonedetector.ITimeZoneDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(telephonyTimeZoneSuggestion, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
