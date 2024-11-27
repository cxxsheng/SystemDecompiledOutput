package android.print;

/* loaded from: classes3.dex */
public interface IPrintSpooler extends android.os.IInterface {
    void clearCustomPrinterIconCache(android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException;

    void createPrintJob(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException;

    void getCustomPrinterIcon(android.print.PrinterId printerId, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException;

    void getPrintJobInfo(android.print.PrintJobId printJobId, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i, int i2) throws android.os.RemoteException;

    void getPrintJobInfos(android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, android.content.ComponentName componentName, int i, int i2, int i3) throws android.os.RemoteException;

    void onCustomPrinterIconLoaded(android.print.PrinterId printerId, android.graphics.drawable.Icon icon, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException;

    void pruneApprovedPrintServices(java.util.List<android.content.ComponentName> list) throws android.os.RemoteException;

    void removeObsoletePrintJobs() throws android.os.RemoteException;

    void setClient(android.print.IPrintSpoolerClient iPrintSpoolerClient) throws android.os.RemoteException;

    void setPrintJobCancelling(android.print.PrintJobId printJobId, boolean z) throws android.os.RemoteException;

    void setPrintJobState(android.print.PrintJobId printJobId, int i, java.lang.String str, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i2) throws android.os.RemoteException;

    void setPrintJobTag(android.print.PrintJobId printJobId, java.lang.String str, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException;

    void setProgress(android.print.PrintJobId printJobId, float f) throws android.os.RemoteException;

    void setStatus(android.print.PrintJobId printJobId, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void setStatusRes(android.print.PrintJobId printJobId, int i, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void writePrintJobData(android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.PrintJobId printJobId) throws android.os.RemoteException;

    public static class Default implements android.print.IPrintSpooler {
        @Override // android.print.IPrintSpooler
        public void removeObsoletePrintJobs() throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void getPrintJobInfos(android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, android.content.ComponentName componentName, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void getPrintJobInfo(android.print.PrintJobId printJobId, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void createPrintJob(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setPrintJobState(android.print.PrintJobId printJobId, int i, java.lang.String str, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i2) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setProgress(android.print.PrintJobId printJobId, float f) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setStatus(android.print.PrintJobId printJobId, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setStatusRes(android.print.PrintJobId printJobId, int i, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void onCustomPrinterIconLoaded(android.print.PrinterId printerId, android.graphics.drawable.Icon icon, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void getCustomPrinterIcon(android.print.PrinterId printerId, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void clearCustomPrinterIconCache(android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setPrintJobTag(android.print.PrintJobId printJobId, java.lang.String str, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void writePrintJobData(android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.PrintJobId printJobId) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setClient(android.print.IPrintSpoolerClient iPrintSpoolerClient) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setPrintJobCancelling(android.print.PrintJobId printJobId, boolean z) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void pruneApprovedPrintServices(java.util.List<android.content.ComponentName> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.print.IPrintSpooler {
        public static final java.lang.String DESCRIPTOR = "android.print.IPrintSpooler";
        static final int TRANSACTION_clearCustomPrinterIconCache = 11;
        static final int TRANSACTION_createPrintJob = 4;
        static final int TRANSACTION_getCustomPrinterIcon = 10;
        static final int TRANSACTION_getPrintJobInfo = 3;
        static final int TRANSACTION_getPrintJobInfos = 2;
        static final int TRANSACTION_onCustomPrinterIconLoaded = 9;
        static final int TRANSACTION_pruneApprovedPrintServices = 16;
        static final int TRANSACTION_removeObsoletePrintJobs = 1;
        static final int TRANSACTION_setClient = 14;
        static final int TRANSACTION_setPrintJobCancelling = 15;
        static final int TRANSACTION_setPrintJobState = 5;
        static final int TRANSACTION_setPrintJobTag = 12;
        static final int TRANSACTION_setProgress = 6;
        static final int TRANSACTION_setStatus = 7;
        static final int TRANSACTION_setStatusRes = 8;
        static final int TRANSACTION_writePrintJobData = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.print.IPrintSpooler asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.print.IPrintSpooler)) {
                return (android.print.IPrintSpooler) queryLocalInterface;
            }
            return new android.print.IPrintSpooler.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "removeObsoletePrintJobs";
                case 2:
                    return "getPrintJobInfos";
                case 3:
                    return "getPrintJobInfo";
                case 4:
                    return "createPrintJob";
                case 5:
                    return "setPrintJobState";
                case 6:
                    return "setProgress";
                case 7:
                    return "setStatus";
                case 8:
                    return "setStatusRes";
                case 9:
                    return "onCustomPrinterIconLoaded";
                case 10:
                    return "getCustomPrinterIcon";
                case 11:
                    return "clearCustomPrinterIconCache";
                case 12:
                    return "setPrintJobTag";
                case 13:
                    return "writePrintJobData";
                case 14:
                    return "setClient";
                case 15:
                    return "setPrintJobCancelling";
                case 16:
                    return "pruneApprovedPrintServices";
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
                    removeObsoletePrintJobs();
                    return true;
                case 2:
                    android.print.IPrintSpoolerCallbacks asInterface = android.print.IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPrintJobInfos(asInterface, componentName, readInt, readInt2, readInt3);
                    return true;
                case 3:
                    android.print.PrintJobId printJobId = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    android.print.IPrintSpoolerCallbacks asInterface2 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPrintJobInfo(printJobId, asInterface2, readInt4, readInt5);
                    return true;
                case 4:
                    android.print.PrintJobInfo printJobInfo = (android.print.PrintJobInfo) parcel.readTypedObject(android.print.PrintJobInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    createPrintJob(printJobInfo);
                    return true;
                case 5:
                    android.print.PrintJobId printJobId2 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    int readInt6 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    android.print.IPrintSpoolerCallbacks asInterface3 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrintJobState(printJobId2, readInt6, readString, asInterface3, readInt7);
                    return true;
                case 6:
                    android.print.PrintJobId printJobId3 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setProgress(printJobId3, readFloat);
                    return true;
                case 7:
                    android.print.PrintJobId printJobId4 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatus(printJobId4, charSequence);
                    return true;
                case 8:
                    android.print.PrintJobId printJobId5 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    int readInt8 = parcel.readInt();
                    java.lang.CharSequence charSequence2 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatusRes(printJobId5, readInt8, charSequence2);
                    return true;
                case 9:
                    android.print.PrinterId printerId = (android.print.PrinterId) parcel.readTypedObject(android.print.PrinterId.CREATOR);
                    android.graphics.drawable.Icon icon = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                    android.print.IPrintSpoolerCallbacks asInterface4 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCustomPrinterIconLoaded(printerId, icon, asInterface4, readInt9);
                    return true;
                case 10:
                    android.print.PrinterId printerId2 = (android.print.PrinterId) parcel.readTypedObject(android.print.PrinterId.CREATOR);
                    android.print.IPrintSpoolerCallbacks asInterface5 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCustomPrinterIcon(printerId2, asInterface5, readInt10);
                    return true;
                case 11:
                    android.print.IPrintSpoolerCallbacks asInterface6 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearCustomPrinterIconCache(asInterface6, readInt11);
                    return true;
                case 12:
                    android.print.PrintJobId printJobId6 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    android.print.IPrintSpoolerCallbacks asInterface7 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrintJobTag(printJobId6, readString2, asInterface7, readInt12);
                    return true;
                case 13:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.print.PrintJobId printJobId7 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    parcel.enforceNoDataAvail();
                    writePrintJobData(parcelFileDescriptor, printJobId7);
                    return true;
                case 14:
                    android.print.IPrintSpoolerClient asInterface8 = android.print.IPrintSpoolerClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setClient(asInterface8);
                    return true;
                case 15:
                    android.print.PrintJobId printJobId8 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPrintJobCancelling(printJobId8, readBoolean);
                    return true;
                case 16:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    pruneApprovedPrintServices(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.print.IPrintSpooler {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.print.IPrintSpooler.Stub.DESCRIPTOR;
            }

            @Override // android.print.IPrintSpooler
            public void removeObsoletePrintJobs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void getPrintJobInfos(android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, android.content.ComponentName componentName, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void getPrintJobInfo(android.print.PrintJobId printJobId, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void createPrintJob(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setPrintJobState(android.print.PrintJobId printJobId, int i, java.lang.String str, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setProgress(android.print.PrintJobId printJobId, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeFloat(f);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setStatus(android.print.PrintJobId printJobId, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setStatusRes(android.print.PrintJobId printJobId, int i, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void onCustomPrinterIconLoaded(android.print.PrinterId printerId, android.graphics.drawable.Icon icon, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeTypedObject(icon, 0);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void getCustomPrinterIcon(android.print.PrinterId printerId, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void clearCustomPrinterIconCache(android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setPrintJobTag(android.print.PrintJobId printJobId, java.lang.String str, android.print.IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void writePrintJobData(android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.PrintJobId printJobId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(printJobId, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setClient(android.print.IPrintSpoolerClient iPrintSpoolerClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintSpoolerClient);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setPrintJobCancelling(android.print.PrintJobId printJobId, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void pruneApprovedPrintServices(java.util.List<android.content.ComponentName> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpooler.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }
    }
}
