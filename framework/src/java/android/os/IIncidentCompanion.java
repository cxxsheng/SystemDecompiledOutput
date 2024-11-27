package android.os;

/* loaded from: classes3.dex */
public interface IIncidentCompanion extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IIncidentCompanion";

    void approveReport(java.lang.String str) throws android.os.RemoteException;

    void authorizeReport(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, android.os.IIncidentAuthListener iIncidentAuthListener) throws android.os.RemoteException;

    void cancelAuthorization(android.os.IIncidentAuthListener iIncidentAuthListener) throws android.os.RemoteException;

    void deleteAllIncidentReports(java.lang.String str) throws android.os.RemoteException;

    void deleteIncidentReports(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void denyReport(java.lang.String str) throws android.os.RemoteException;

    android.os.IncidentManager.IncidentReport getIncidentReport(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    java.util.List<java.lang.String> getIncidentReportList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.util.List<java.lang.String> getPendingReports() throws android.os.RemoteException;

    void sendReportReadyBroadcast(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements android.os.IIncidentCompanion {
        @Override // android.os.IIncidentCompanion
        public void authorizeReport(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, android.os.IIncidentAuthListener iIncidentAuthListener) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public void cancelAuthorization(android.os.IIncidentAuthListener iIncidentAuthListener) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public void sendReportReadyBroadcast(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public java.util.List<java.lang.String> getPendingReports() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IIncidentCompanion
        public void approveReport(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public void denyReport(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public java.util.List<java.lang.String> getIncidentReportList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IIncidentCompanion
        public android.os.IncidentManager.IncidentReport getIncidentReport(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IIncidentCompanion
        public void deleteIncidentReports(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public void deleteAllIncidentReports(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IIncidentCompanion {
        static final int TRANSACTION_approveReport = 5;
        static final int TRANSACTION_authorizeReport = 1;
        static final int TRANSACTION_cancelAuthorization = 2;
        static final int TRANSACTION_deleteAllIncidentReports = 10;
        static final int TRANSACTION_deleteIncidentReports = 9;
        static final int TRANSACTION_denyReport = 6;
        static final int TRANSACTION_getIncidentReport = 8;
        static final int TRANSACTION_getIncidentReportList = 7;
        static final int TRANSACTION_getPendingReports = 4;
        static final int TRANSACTION_sendReportReadyBroadcast = 3;

        public Stub() {
            attachInterface(this, android.os.IIncidentCompanion.DESCRIPTOR);
        }

        public static android.os.IIncidentCompanion asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IIncidentCompanion.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IIncidentCompanion)) {
                return (android.os.IIncidentCompanion) queryLocalInterface;
            }
            return new android.os.IIncidentCompanion.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "authorizeReport";
                case 2:
                    return "cancelAuthorization";
                case 3:
                    return "sendReportReadyBroadcast";
                case 4:
                    return "getPendingReports";
                case 5:
                    return "approveReport";
                case 6:
                    return "denyReport";
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
                parcel.enforceInterface(android.os.IIncidentCompanion.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IIncidentCompanion.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    android.os.IIncidentAuthListener asInterface = android.os.IIncidentAuthListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    authorizeReport(readInt, readString, readString2, readString3, readInt2, asInterface);
                    return true;
                case 2:
                    android.os.IIncidentAuthListener asInterface2 = android.os.IIncidentAuthListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelAuthorization(asInterface2);
                    return true;
                case 3:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendReportReadyBroadcast(readString4, readString5);
                    return true;
                case 4:
                    java.util.List<java.lang.String> pendingReports = getPendingReports();
                    parcel2.writeNoException();
                    parcel2.writeStringList(pendingReports);
                    return true;
                case 5:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    approveReport(readString6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    denyReport(readString7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> incidentReportList = getIncidentReportList(readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeStringList(incidentReportList);
                    return true;
                case 8:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.IncidentManager.IncidentReport incidentReport = getIncidentReport(readString10, readString11, readString12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(incidentReport, 1);
                    return true;
                case 9:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteIncidentReports(readString13, readString14, readString15);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteAllIncidentReports(readString16);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IIncidentCompanion {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IIncidentCompanion.DESCRIPTOR;
            }

            @Override // android.os.IIncidentCompanion
            public void authorizeReport(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, android.os.IIncidentAuthListener iIncidentAuthListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentCompanion.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iIncidentAuthListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public void cancelAuthorization(android.os.IIncidentAuthListener iIncidentAuthListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentCompanion.DESCRIPTOR);
                    obtain.writeStrongInterface(iIncidentAuthListener);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public void sendReportReadyBroadcast(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentCompanion.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public java.util.List<java.lang.String> getPendingReports() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentCompanion.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public void approveReport(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentCompanion.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public void denyReport(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentCompanion.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public java.util.List<java.lang.String> getIncidentReportList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentCompanion.DESCRIPTOR);
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

            @Override // android.os.IIncidentCompanion
            public android.os.IncidentManager.IncidentReport getIncidentReport(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentCompanion.DESCRIPTOR);
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

            @Override // android.os.IIncidentCompanion
            public void deleteIncidentReports(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentCompanion.DESCRIPTOR);
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

            @Override // android.os.IIncidentCompanion
            public void deleteAllIncidentReports(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentCompanion.DESCRIPTOR);
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
