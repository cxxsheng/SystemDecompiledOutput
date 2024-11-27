package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface ICallDiagnosticService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.ICallDiagnosticService";

    void callQualityChanged(java.lang.String str, android.telephony.CallQuality callQuality) throws android.os.RemoteException;

    void initializeDiagnosticCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException;

    void notifyCallDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause) throws android.os.RemoteException;

    void receiveBluetoothCallQualityReport(android.telecom.BluetoothCallQualityReport bluetoothCallQualityReport) throws android.os.RemoteException;

    void receiveDeviceToDeviceMessage(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void removeDiagnosticCall(java.lang.String str) throws android.os.RemoteException;

    void setAdapter(com.android.internal.telecom.ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapter) throws android.os.RemoteException;

    void updateCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException;

    void updateCallAudioState(android.telecom.CallAudioState callAudioState) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.ICallDiagnosticService {
        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void setAdapter(com.android.internal.telecom.ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapter) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void initializeDiagnosticCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void updateCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void updateCallAudioState(android.telecom.CallAudioState callAudioState) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void removeDiagnosticCall(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void receiveDeviceToDeviceMessage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void callQualityChanged(java.lang.String str, android.telephony.CallQuality callQuality) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void receiveBluetoothCallQualityReport(android.telecom.BluetoothCallQualityReport bluetoothCallQualityReport) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void notifyCallDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.ICallDiagnosticService {
        static final int TRANSACTION_callQualityChanged = 7;
        static final int TRANSACTION_initializeDiagnosticCall = 2;
        static final int TRANSACTION_notifyCallDisconnected = 9;
        static final int TRANSACTION_receiveBluetoothCallQualityReport = 8;
        static final int TRANSACTION_receiveDeviceToDeviceMessage = 6;
        static final int TRANSACTION_removeDiagnosticCall = 5;
        static final int TRANSACTION_setAdapter = 1;
        static final int TRANSACTION_updateCall = 3;
        static final int TRANSACTION_updateCallAudioState = 4;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
        }

        public static com.android.internal.telecom.ICallDiagnosticService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.ICallDiagnosticService)) {
                return (com.android.internal.telecom.ICallDiagnosticService) queryLocalInterface;
            }
            return new com.android.internal.telecom.ICallDiagnosticService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setAdapter";
                case 2:
                    return "initializeDiagnosticCall";
                case 3:
                    return "updateCall";
                case 4:
                    return "updateCallAudioState";
                case 5:
                    return "removeDiagnosticCall";
                case 6:
                    return "receiveDeviceToDeviceMessage";
                case 7:
                    return "callQualityChanged";
                case 8:
                    return "receiveBluetoothCallQualityReport";
                case 9:
                    return "notifyCallDisconnected";
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
                parcel.enforceInterface(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.telecom.ICallDiagnosticServiceAdapter asInterface = com.android.internal.telecom.ICallDiagnosticServiceAdapter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setAdapter(asInterface);
                    return true;
                case 2:
                    android.telecom.ParcelableCall parcelableCall = (android.telecom.ParcelableCall) parcel.readTypedObject(android.telecom.ParcelableCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    initializeDiagnosticCall(parcelableCall);
                    return true;
                case 3:
                    android.telecom.ParcelableCall parcelableCall2 = (android.telecom.ParcelableCall) parcel.readTypedObject(android.telecom.ParcelableCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCall(parcelableCall2);
                    return true;
                case 4:
                    android.telecom.CallAudioState callAudioState = (android.telecom.CallAudioState) parcel.readTypedObject(android.telecom.CallAudioState.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCallAudioState(callAudioState);
                    return true;
                case 5:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeDiagnosticCall(readString);
                    return true;
                case 6:
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    receiveDeviceToDeviceMessage(readString2, readInt, readInt2);
                    return true;
                case 7:
                    java.lang.String readString3 = parcel.readString();
                    android.telephony.CallQuality callQuality = (android.telephony.CallQuality) parcel.readTypedObject(android.telephony.CallQuality.CREATOR);
                    parcel.enforceNoDataAvail();
                    callQualityChanged(readString3, callQuality);
                    return true;
                case 8:
                    android.telecom.BluetoothCallQualityReport bluetoothCallQualityReport = (android.telecom.BluetoothCallQualityReport) parcel.readTypedObject(android.telecom.BluetoothCallQualityReport.CREATOR);
                    parcel.enforceNoDataAvail();
                    receiveBluetoothCallQualityReport(bluetoothCallQualityReport);
                    return true;
                case 9:
                    java.lang.String readString4 = parcel.readString();
                    android.telecom.DisconnectCause disconnectCause = (android.telecom.DisconnectCause) parcel.readTypedObject(android.telecom.DisconnectCause.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCallDisconnected(readString4, disconnectCause);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.ICallDiagnosticService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void setAdapter(com.android.internal.telecom.ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCallDiagnosticServiceAdapter);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void initializeDiagnosticCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelableCall, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void updateCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelableCall, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void updateCallAudioState(android.telecom.CallAudioState callAudioState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
                    obtain.writeTypedObject(callAudioState, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void removeDiagnosticCall(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void receiveDeviceToDeviceMessage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void callQualityChanged(java.lang.String str, android.telephony.CallQuality callQuality) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callQuality, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void receiveBluetoothCallQualityReport(android.telecom.BluetoothCallQualityReport bluetoothCallQualityReport) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
                    obtain.writeTypedObject(bluetoothCallQualityReport, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void notifyCallDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallDiagnosticService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(disconnectCause, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
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
