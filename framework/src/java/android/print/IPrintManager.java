package android.print;

/* loaded from: classes3.dex */
public interface IPrintManager extends android.os.IInterface {
    void addPrintJobStateChangeListener(android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener, int i, int i2) throws android.os.RemoteException;

    void addPrintServiceRecommendationsChangeListener(android.printservice.recommendation.IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws android.os.RemoteException;

    void addPrintServicesChangeListener(android.print.IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws android.os.RemoteException;

    void cancelPrintJob(android.print.PrintJobId printJobId, int i, int i2) throws android.os.RemoteException;

    void createPrinterDiscoverySession(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws android.os.RemoteException;

    void destroyPrinterDiscoverySession(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws android.os.RemoteException;

    boolean getBindInstantServiceAllowed(int i) throws android.os.RemoteException;

    android.graphics.drawable.Icon getCustomPrinterIcon(android.print.PrinterId printerId, int i) throws android.os.RemoteException;

    android.print.PrintJobInfo getPrintJobInfo(android.print.PrintJobId printJobId, int i, int i2) throws android.os.RemoteException;

    java.util.List<android.print.PrintJobInfo> getPrintJobInfos(int i, int i2) throws android.os.RemoteException;

    java.util.List<android.printservice.recommendation.RecommendationInfo> getPrintServiceRecommendations(int i) throws android.os.RemoteException;

    java.util.List<android.printservice.PrintServiceInfo> getPrintServices(int i, int i2) throws android.os.RemoteException;

    boolean isPrintServiceEnabled(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    android.os.Bundle print(java.lang.String str, android.print.IPrintDocumentAdapter iPrintDocumentAdapter, android.print.PrintAttributes printAttributes, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void removePrintJobStateChangeListener(android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) throws android.os.RemoteException;

    void removePrintServiceRecommendationsChangeListener(android.printservice.recommendation.IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws android.os.RemoteException;

    void removePrintServicesChangeListener(android.print.IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws android.os.RemoteException;

    void restartPrintJob(android.print.PrintJobId printJobId, int i, int i2) throws android.os.RemoteException;

    void setBindInstantServiceAllowed(int i, boolean z) throws android.os.RemoteException;

    void setPrintServiceEnabled(android.content.ComponentName componentName, boolean z, int i) throws android.os.RemoteException;

    void startPrinterDiscovery(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, java.util.List<android.print.PrinterId> list, int i) throws android.os.RemoteException;

    void startPrinterStateTracking(android.print.PrinterId printerId, int i) throws android.os.RemoteException;

    void stopPrinterDiscovery(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws android.os.RemoteException;

    void stopPrinterStateTracking(android.print.PrinterId printerId, int i) throws android.os.RemoteException;

    void validatePrinters(java.util.List<android.print.PrinterId> list, int i) throws android.os.RemoteException;

    public static class Default implements android.print.IPrintManager {
        @Override // android.print.IPrintManager
        public java.util.List<android.print.PrintJobInfo> getPrintJobInfos(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public android.print.PrintJobInfo getPrintJobInfo(android.print.PrintJobId printJobId, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public android.os.Bundle print(java.lang.String str, android.print.IPrintDocumentAdapter iPrintDocumentAdapter, android.print.PrintAttributes printAttributes, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public void cancelPrintJob(android.print.PrintJobId printJobId, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void restartPrintJob(android.print.PrintJobId printJobId, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void addPrintJobStateChangeListener(android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void removePrintJobStateChangeListener(android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void addPrintServicesChangeListener(android.print.IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void removePrintServicesChangeListener(android.print.IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public java.util.List<android.printservice.PrintServiceInfo> getPrintServices(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public void setPrintServiceEnabled(android.content.ComponentName componentName, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public boolean isPrintServiceEnabled(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.print.IPrintManager
        public void addPrintServiceRecommendationsChangeListener(android.printservice.recommendation.IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void removePrintServiceRecommendationsChangeListener(android.printservice.recommendation.IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public java.util.List<android.printservice.recommendation.RecommendationInfo> getPrintServiceRecommendations(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public void createPrinterDiscoverySession(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void startPrinterDiscovery(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, java.util.List<android.print.PrinterId> list, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void stopPrinterDiscovery(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void validatePrinters(java.util.List<android.print.PrinterId> list, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void startPrinterStateTracking(android.print.PrinterId printerId, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public android.graphics.drawable.Icon getCustomPrinterIcon(android.print.PrinterId printerId, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public void stopPrinterStateTracking(android.print.PrinterId printerId, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public void destroyPrinterDiscoverySession(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintManager
        public boolean getBindInstantServiceAllowed(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.print.IPrintManager
        public void setBindInstantServiceAllowed(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.print.IPrintManager {
        public static final java.lang.String DESCRIPTOR = "android.print.IPrintManager";
        static final int TRANSACTION_addPrintJobStateChangeListener = 6;
        static final int TRANSACTION_addPrintServiceRecommendationsChangeListener = 13;
        static final int TRANSACTION_addPrintServicesChangeListener = 8;
        static final int TRANSACTION_cancelPrintJob = 4;
        static final int TRANSACTION_createPrinterDiscoverySession = 16;
        static final int TRANSACTION_destroyPrinterDiscoverySession = 23;
        static final int TRANSACTION_getBindInstantServiceAllowed = 24;
        static final int TRANSACTION_getCustomPrinterIcon = 21;
        static final int TRANSACTION_getPrintJobInfo = 2;
        static final int TRANSACTION_getPrintJobInfos = 1;
        static final int TRANSACTION_getPrintServiceRecommendations = 15;
        static final int TRANSACTION_getPrintServices = 10;
        static final int TRANSACTION_isPrintServiceEnabled = 12;
        static final int TRANSACTION_print = 3;
        static final int TRANSACTION_removePrintJobStateChangeListener = 7;
        static final int TRANSACTION_removePrintServiceRecommendationsChangeListener = 14;
        static final int TRANSACTION_removePrintServicesChangeListener = 9;
        static final int TRANSACTION_restartPrintJob = 5;
        static final int TRANSACTION_setBindInstantServiceAllowed = 25;
        static final int TRANSACTION_setPrintServiceEnabled = 11;
        static final int TRANSACTION_startPrinterDiscovery = 17;
        static final int TRANSACTION_startPrinterStateTracking = 20;
        static final int TRANSACTION_stopPrinterDiscovery = 18;
        static final int TRANSACTION_stopPrinterStateTracking = 22;
        static final int TRANSACTION_validatePrinters = 19;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.print.IPrintManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.print.IPrintManager)) {
                return (android.print.IPrintManager) queryLocalInterface;
            }
            return new android.print.IPrintManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPrintJobInfos";
                case 2:
                    return "getPrintJobInfo";
                case 3:
                    return android.content.Context.PRINT_SERVICE;
                case 4:
                    return "cancelPrintJob";
                case 5:
                    return "restartPrintJob";
                case 6:
                    return "addPrintJobStateChangeListener";
                case 7:
                    return "removePrintJobStateChangeListener";
                case 8:
                    return "addPrintServicesChangeListener";
                case 9:
                    return "removePrintServicesChangeListener";
                case 10:
                    return "getPrintServices";
                case 11:
                    return "setPrintServiceEnabled";
                case 12:
                    return "isPrintServiceEnabled";
                case 13:
                    return "addPrintServiceRecommendationsChangeListener";
                case 14:
                    return "removePrintServiceRecommendationsChangeListener";
                case 15:
                    return "getPrintServiceRecommendations";
                case 16:
                    return "createPrinterDiscoverySession";
                case 17:
                    return "startPrinterDiscovery";
                case 18:
                    return "stopPrinterDiscovery";
                case 19:
                    return "validatePrinters";
                case 20:
                    return "startPrinterStateTracking";
                case 21:
                    return "getCustomPrinterIcon";
                case 22:
                    return "stopPrinterStateTracking";
                case 23:
                    return "destroyPrinterDiscoverySession";
                case 24:
                    return "getBindInstantServiceAllowed";
                case 25:
                    return "setBindInstantServiceAllowed";
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.print.PrintJobInfo> printJobInfos = getPrintJobInfos(readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printJobInfos, 1);
                    return true;
                case 2:
                    android.print.PrintJobId printJobId = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.print.PrintJobInfo printJobInfo = getPrintJobInfo(printJobId, readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(printJobInfo, 1);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    android.print.IPrintDocumentAdapter asInterface = android.print.IPrintDocumentAdapter.Stub.asInterface(parcel.readStrongBinder());
                    android.print.PrintAttributes printAttributes = (android.print.PrintAttributes) parcel.readTypedObject(android.print.PrintAttributes.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle print = print(readString, asInterface, printAttributes, readString2, readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(print, 1);
                    return true;
                case 4:
                    android.print.PrintJobId printJobId2 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelPrintJob(printJobId2, readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.print.PrintJobId printJobId3 = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restartPrintJob(printJobId3, readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.print.IPrintJobStateChangeListener asInterface2 = android.print.IPrintJobStateChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrintJobStateChangeListener(asInterface2, readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.print.IPrintJobStateChangeListener asInterface3 = android.print.IPrintJobStateChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrintJobStateChangeListener(asInterface3, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.print.IPrintServicesChangeListener asInterface4 = android.print.IPrintServicesChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrintServicesChangeListener(asInterface4, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.print.IPrintServicesChangeListener asInterface5 = android.print.IPrintServicesChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrintServicesChangeListener(asInterface5, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.printservice.PrintServiceInfo> printServices = getPrintServices(readInt16, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printServices, 1);
                    return true;
                case 11:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrintServiceEnabled(componentName, readBoolean, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPrintServiceEnabled = isPrintServiceEnabled(componentName2, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPrintServiceEnabled);
                    return true;
                case 13:
                    android.printservice.recommendation.IRecommendationsChangeListener asInterface6 = android.printservice.recommendation.IRecommendationsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrintServiceRecommendationsChangeListener(asInterface6, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.printservice.recommendation.IRecommendationsChangeListener asInterface7 = android.printservice.recommendation.IRecommendationsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrintServiceRecommendationsChangeListener(asInterface7, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.printservice.recommendation.RecommendationInfo> printServiceRecommendations = getPrintServiceRecommendations(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printServiceRecommendations, 1);
                    return true;
                case 16:
                    android.print.IPrinterDiscoveryObserver asInterface8 = android.print.IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createPrinterDiscoverySession(asInterface8, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.print.IPrinterDiscoveryObserver asInterface9 = android.print.IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.print.PrinterId.CREATOR);
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPrinterDiscovery(asInterface9, createTypedArrayList, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.print.IPrinterDiscoveryObserver asInterface10 = android.print.IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPrinterDiscovery(asInterface10, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.print.PrinterId.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    validatePrinters(createTypedArrayList2, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.print.PrinterId printerId = (android.print.PrinterId) parcel.readTypedObject(android.print.PrinterId.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPrinterStateTracking(printerId, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.print.PrinterId printerId2 = (android.print.PrinterId) parcel.readTypedObject(android.print.PrinterId.CREATOR);
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.graphics.drawable.Icon customPrinterIcon = getCustomPrinterIcon(printerId2, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(customPrinterIcon, 1);
                    return true;
                case 22:
                    android.print.PrinterId printerId3 = (android.print.PrinterId) parcel.readTypedObject(android.print.PrinterId.CREATOR);
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPrinterStateTracking(printerId3, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    android.print.IPrinterDiscoveryObserver asInterface11 = android.print.IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyPrinterDiscoverySession(asInterface11, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bindInstantServiceAllowed = getBindInstantServiceAllowed(readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindInstantServiceAllowed);
                    return true;
                case 25:
                    int readInt32 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBindInstantServiceAllowed(readInt32, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.print.IPrintManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.print.IPrintManager.Stub.DESCRIPTOR;
            }

            @Override // android.print.IPrintManager
            public java.util.List<android.print.PrintJobInfo> getPrintJobInfos(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.print.PrintJobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public android.print.PrintJobInfo getPrintJobInfo(android.print.PrintJobId printJobId, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.print.PrintJobInfo) obtain2.readTypedObject(android.print.PrintJobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public android.os.Bundle print(java.lang.String str, android.print.IPrintDocumentAdapter iPrintDocumentAdapter, android.print.PrintAttributes printAttributes, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPrintDocumentAdapter);
                    obtain.writeTypedObject(printAttributes, 0);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void cancelPrintJob(android.print.PrintJobId printJobId, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void restartPrintJob(android.print.PrintJobId printJobId, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void addPrintJobStateChangeListener(android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintJobStateChangeListener);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void removePrintJobStateChangeListener(android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintJobStateChangeListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void addPrintServicesChangeListener(android.print.IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintServicesChangeListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void removePrintServicesChangeListener(android.print.IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintServicesChangeListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public java.util.List<android.printservice.PrintServiceInfo> getPrintServices(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.printservice.PrintServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void setPrintServiceEnabled(android.content.ComponentName componentName, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public boolean isPrintServiceEnabled(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void addPrintServiceRecommendationsChangeListener(android.printservice.recommendation.IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecommendationsChangeListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void removePrintServiceRecommendationsChangeListener(android.printservice.recommendation.IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecommendationsChangeListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public java.util.List<android.printservice.recommendation.RecommendationInfo> getPrintServiceRecommendations(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.printservice.recommendation.RecommendationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void createPrinterDiscoverySession(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void startPrinterDiscovery(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, java.util.List<android.print.PrinterId> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void stopPrinterDiscovery(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void validatePrinters(java.util.List<android.print.PrinterId> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void startPrinterStateTracking(android.print.PrinterId printerId, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public android.graphics.drawable.Icon getCustomPrinterIcon(android.print.PrinterId printerId, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.drawable.Icon) obtain2.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void stopPrinterStateTracking(android.print.PrinterId printerId, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void destroyPrinterDiscoverySession(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public boolean getBindInstantServiceAllowed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void setBindInstantServiceAllowed(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.print.IPrintManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }
    }
}
