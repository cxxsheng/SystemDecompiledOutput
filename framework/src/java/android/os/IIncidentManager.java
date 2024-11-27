package android.os;

/* loaded from: classes3.dex */
public interface IIncidentManager extends android.os.IInterface {
    void deleteAllIncidentReports(java.lang.String str) throws android.os.RemoteException;

    void deleteIncidentReports(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    android.os.IncidentManager.IncidentReport getIncidentReport(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    java.util.List<java.lang.String> getIncidentReportList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void registerSection(int i, java.lang.String str, android.os.IIncidentDumpCallback iIncidentDumpCallback) throws android.os.RemoteException;

    void reportIncident(android.os.IncidentReportArgs incidentReportArgs) throws android.os.RemoteException;

    void reportIncidentToDumpstate(java.io.FileDescriptor fileDescriptor, android.os.IIncidentReportStatusListener iIncidentReportStatusListener) throws android.os.RemoteException;

    void reportIncidentToStream(android.os.IncidentReportArgs incidentReportArgs, android.os.IIncidentReportStatusListener iIncidentReportStatusListener, java.io.FileDescriptor fileDescriptor) throws android.os.RemoteException;

    void systemRunning() throws android.os.RemoteException;

    void unregisterSection(int i) throws android.os.RemoteException;

    public static class Default implements android.os.IIncidentManager {
        @Override // android.os.IIncidentManager
        public void reportIncident(android.os.IncidentReportArgs incidentReportArgs) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void reportIncidentToStream(android.os.IncidentReportArgs incidentReportArgs, android.os.IIncidentReportStatusListener iIncidentReportStatusListener, java.io.FileDescriptor fileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void reportIncidentToDumpstate(java.io.FileDescriptor fileDescriptor, android.os.IIncidentReportStatusListener iIncidentReportStatusListener) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void registerSection(int i, java.lang.String str, android.os.IIncidentDumpCallback iIncidentDumpCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void unregisterSection(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void systemRunning() throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentManager
        public java.util.List<java.lang.String> getIncidentReportList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IIncidentManager
        public android.os.IncidentManager.IncidentReport getIncidentReport(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IIncidentManager
        public void deleteIncidentReports(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void deleteAllIncidentReports(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IIncidentManager {
        public static final java.lang.String DESCRIPTOR = "android.os.IIncidentManager";
        static final int TRANSACTION_deleteAllIncidentReports = 10;
        static final int TRANSACTION_deleteIncidentReports = 9;
        static final int TRANSACTION_getIncidentReport = 8;
        static final int TRANSACTION_getIncidentReportList = 7;
        static final int TRANSACTION_registerSection = 4;
        static final int TRANSACTION_reportIncident = 1;
        static final int TRANSACTION_reportIncidentToDumpstate = 3;
        static final int TRANSACTION_reportIncidentToStream = 2;
        static final int TRANSACTION_systemRunning = 6;
        static final int TRANSACTION_unregisterSection = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IIncidentManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IIncidentManager)) {
                return (android.os.IIncidentManager) queryLocalInterface;
            }
            return new android.os.IIncidentManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reportIncident";
                case 2:
                    return "reportIncidentToStream";
                case 3:
                    return "reportIncidentToDumpstate";
                case 4:
                    return "registerSection";
                case 5:
                    return "unregisterSection";
                case 6:
                    return "systemRunning";
                case 7:
                    return "getIncidentReportList";
                case 8:
                    return "getIncidentReport";
                case 9:
                    return "deleteIncidentReports";
                case 10:
                    return "deleteAllIncidentReports";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IncidentReportArgs incidentReportArgs = (android.os.IncidentReportArgs) parcel.readTypedObject(android.os.IncidentReportArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportIncident(incidentReportArgs);
                    return true;
                case 2:
                    android.os.IncidentReportArgs incidentReportArgs2 = (android.os.IncidentReportArgs) parcel.readTypedObject(android.os.IncidentReportArgs.CREATOR);
                    android.os.IIncidentReportStatusListener asInterface = android.os.IIncidentReportStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    java.io.FileDescriptor readRawFileDescriptor = parcel.readRawFileDescriptor();
                    parcel.enforceNoDataAvail();
                    reportIncidentToStream(incidentReportArgs2, asInterface, readRawFileDescriptor);
                    return true;
                case 3:
                    java.io.FileDescriptor readRawFileDescriptor2 = parcel.readRawFileDescriptor();
                    android.os.IIncidentReportStatusListener asInterface2 = android.os.IIncidentReportStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    reportIncidentToDumpstate(readRawFileDescriptor2, asInterface2);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    android.os.IIncidentDumpCallback asInterface3 = android.os.IIncidentDumpCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSection(readInt, readString, asInterface3);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSection(readInt2);
                    return true;
                case 6:
                    systemRunning();
                    return true;
                case 7:
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> incidentReportList = getIncidentReportList(readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(incidentReportList);
                    return true;
                case 8:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.IncidentManager.IncidentReport incidentReport = getIncidentReport(readString4, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(incidentReport, 1);
                    return true;
                case 9:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteIncidentReports(readString7, readString8, readString9);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteAllIncidentReports(readString10);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IIncidentManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IIncidentManager.Stub.DESCRIPTOR;
            }

            @Override // android.os.IIncidentManager
            public void reportIncident(android.os.IncidentReportArgs incidentReportArgs) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(incidentReportArgs, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void reportIncidentToStream(android.os.IncidentReportArgs incidentReportArgs, android.os.IIncidentReportStatusListener iIncidentReportStatusListener, java.io.FileDescriptor fileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(incidentReportArgs, 0);
                    obtain.writeStrongInterface(iIncidentReportStatusListener);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void reportIncidentToDumpstate(java.io.FileDescriptor fileDescriptor, android.os.IIncidentReportStatusListener iIncidentReportStatusListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentManager.Stub.DESCRIPTOR);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    obtain.writeStrongInterface(iIncidentReportStatusListener);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void registerSection(int i, java.lang.String str, android.os.IIncidentDumpCallback iIncidentDumpCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIncidentDumpCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void unregisterSection(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void systemRunning() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public java.util.List<java.lang.String> getIncidentReportList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public android.os.IncidentManager.IncidentReport getIncidentReport(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.IncidentManager.IncidentReport) obtain2.readTypedObject(android.os.IncidentManager.IncidentReport.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void deleteIncidentReports(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void deleteAllIncidentReports(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
