package com.android.internal.appwidget;

/* loaded from: classes4.dex */
public interface IAppWidgetService extends android.os.IInterface {
    int allocateAppWidgetId(java.lang.String str, int i) throws android.os.RemoteException;

    boolean bindAppWidgetId(java.lang.String str, int i, int i2, android.content.ComponentName componentName, android.os.Bundle bundle) throws android.os.RemoteException;

    boolean bindRemoteViewsService(java.lang.String str, int i, android.content.Intent intent, android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.app.IServiceConnection iServiceConnection, long j) throws android.os.RemoteException;

    android.content.IntentSender createAppWidgetConfigIntentSender(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void deleteAllHosts() throws android.os.RemoteException;

    void deleteAppWidgetId(java.lang.String str, int i) throws android.os.RemoteException;

    void deleteHost(java.lang.String str, int i) throws android.os.RemoteException;

    int[] getAppWidgetIds(android.content.ComponentName componentName) throws android.os.RemoteException;

    int[] getAppWidgetIdsForHost(java.lang.String str, int i) throws android.os.RemoteException;

    android.appwidget.AppWidgetProviderInfo getAppWidgetInfo(java.lang.String str, int i) throws android.os.RemoteException;

    android.os.Bundle getAppWidgetOptions(java.lang.String str, int i) throws android.os.RemoteException;

    android.widget.RemoteViews getAppWidgetViews(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getInstalledProvidersForProfile(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    android.widget.RemoteViews getWidgetPreview(java.lang.String str, android.content.ComponentName componentName, int i, int i2) throws android.os.RemoteException;

    boolean hasBindAppWidgetPermission(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isBoundWidgetPackage(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isRequestPinAppWidgetSupported() throws android.os.RemoteException;

    void noteAppWidgetTapped(java.lang.String str, int i) throws android.os.RemoteException;

    void notifyAppWidgetViewDataChanged(java.lang.String str, int[] iArr, int i) throws android.os.RemoteException;

    void notifyProviderInheritance(android.content.ComponentName[] componentNameArr) throws android.os.RemoteException;

    void partiallyUpdateAppWidgetIds(java.lang.String str, int[] iArr, android.widget.RemoteViews remoteViews) throws android.os.RemoteException;

    void removeWidgetPreview(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    boolean requestPinAppWidget(java.lang.String str, android.content.ComponentName componentName, android.os.Bundle bundle, android.content.IntentSender intentSender) throws android.os.RemoteException;

    void setAppWidgetHidden(java.lang.String str, int i) throws android.os.RemoteException;

    void setBindAppWidgetPermission(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setWidgetPreview(android.content.ComponentName componentName, int i, android.widget.RemoteViews remoteViews) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice startListening(com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost, java.lang.String str, int i, int[] iArr) throws android.os.RemoteException;

    void stopListening(java.lang.String str, int i) throws android.os.RemoteException;

    void updateAppWidgetIds(java.lang.String str, int[] iArr, android.widget.RemoteViews remoteViews) throws android.os.RemoteException;

    void updateAppWidgetOptions(java.lang.String str, int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void updateAppWidgetProvider(android.content.ComponentName componentName, android.widget.RemoteViews remoteViews) throws android.os.RemoteException;

    void updateAppWidgetProviderInfo(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.appwidget.IAppWidgetService {
        @Override // com.android.internal.appwidget.IAppWidgetService
        public android.content.pm.ParceledListSlice startListening(com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost, java.lang.String str, int i, int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void stopListening(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public int allocateAppWidgetId(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void deleteAppWidgetId(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void deleteHost(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void deleteAllHosts() throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public android.widget.RemoteViews getAppWidgetViews(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public int[] getAppWidgetIdsForHost(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void setAppWidgetHidden(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public android.content.IntentSender createAppWidgetConfigIntentSender(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetIds(java.lang.String str, int[] iArr, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetOptions(java.lang.String str, int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public android.os.Bundle getAppWidgetOptions(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void partiallyUpdateAppWidgetIds(java.lang.String str, int[] iArr, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetProvider(android.content.ComponentName componentName, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetProviderInfo(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void notifyAppWidgetViewDataChanged(java.lang.String str, int[] iArr, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public android.content.pm.ParceledListSlice getInstalledProvidersForProfile(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public android.appwidget.AppWidgetProviderInfo getAppWidgetInfo(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean hasBindAppWidgetPermission(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void setBindAppWidgetPermission(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean bindAppWidgetId(java.lang.String str, int i, int i2, android.content.ComponentName componentName, android.os.Bundle bundle) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean bindRemoteViewsService(java.lang.String str, int i, android.content.Intent intent, android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.app.IServiceConnection iServiceConnection, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void notifyProviderInheritance(android.content.ComponentName[] componentNameArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public int[] getAppWidgetIds(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean isBoundWidgetPackage(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean requestPinAppWidget(java.lang.String str, android.content.ComponentName componentName, android.os.Bundle bundle, android.content.IntentSender intentSender) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean isRequestPinAppWidgetSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void noteAppWidgetTapped(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void setWidgetPreview(android.content.ComponentName componentName, int i, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public android.widget.RemoteViews getWidgetPreview(java.lang.String str, android.content.ComponentName componentName, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void removeWidgetPreview(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.appwidget.IAppWidgetService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.appwidget.IAppWidgetService";
        static final int TRANSACTION_allocateAppWidgetId = 3;
        static final int TRANSACTION_bindAppWidgetId = 22;
        static final int TRANSACTION_bindRemoteViewsService = 23;
        static final int TRANSACTION_createAppWidgetConfigIntentSender = 10;
        static final int TRANSACTION_deleteAllHosts = 6;
        static final int TRANSACTION_deleteAppWidgetId = 4;
        static final int TRANSACTION_deleteHost = 5;
        static final int TRANSACTION_getAppWidgetIds = 25;
        static final int TRANSACTION_getAppWidgetIdsForHost = 8;
        static final int TRANSACTION_getAppWidgetInfo = 19;
        static final int TRANSACTION_getAppWidgetOptions = 13;
        static final int TRANSACTION_getAppWidgetViews = 7;
        static final int TRANSACTION_getInstalledProvidersForProfile = 18;
        static final int TRANSACTION_getWidgetPreview = 31;
        static final int TRANSACTION_hasBindAppWidgetPermission = 20;
        static final int TRANSACTION_isBoundWidgetPackage = 26;
        static final int TRANSACTION_isRequestPinAppWidgetSupported = 28;
        static final int TRANSACTION_noteAppWidgetTapped = 29;
        static final int TRANSACTION_notifyAppWidgetViewDataChanged = 17;
        static final int TRANSACTION_notifyProviderInheritance = 24;
        static final int TRANSACTION_partiallyUpdateAppWidgetIds = 14;
        static final int TRANSACTION_removeWidgetPreview = 32;
        static final int TRANSACTION_requestPinAppWidget = 27;
        static final int TRANSACTION_setAppWidgetHidden = 9;
        static final int TRANSACTION_setBindAppWidgetPermission = 21;
        static final int TRANSACTION_setWidgetPreview = 30;
        static final int TRANSACTION_startListening = 1;
        static final int TRANSACTION_stopListening = 2;
        static final int TRANSACTION_updateAppWidgetIds = 11;
        static final int TRANSACTION_updateAppWidgetOptions = 12;
        static final int TRANSACTION_updateAppWidgetProvider = 15;
        static final int TRANSACTION_updateAppWidgetProviderInfo = 16;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.appwidget.IAppWidgetService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.appwidget.IAppWidgetService)) {
                return (com.android.internal.appwidget.IAppWidgetService) queryLocalInterface;
            }
            return new com.android.internal.appwidget.IAppWidgetService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startListening";
                case 2:
                    return "stopListening";
                case 3:
                    return "allocateAppWidgetId";
                case 4:
                    return "deleteAppWidgetId";
                case 5:
                    return "deleteHost";
                case 6:
                    return "deleteAllHosts";
                case 7:
                    return "getAppWidgetViews";
                case 8:
                    return "getAppWidgetIdsForHost";
                case 9:
                    return "setAppWidgetHidden";
                case 10:
                    return "createAppWidgetConfigIntentSender";
                case 11:
                    return "updateAppWidgetIds";
                case 12:
                    return "updateAppWidgetOptions";
                case 13:
                    return "getAppWidgetOptions";
                case 14:
                    return "partiallyUpdateAppWidgetIds";
                case 15:
                    return "updateAppWidgetProvider";
                case 16:
                    return "updateAppWidgetProviderInfo";
                case 17:
                    return "notifyAppWidgetViewDataChanged";
                case 18:
                    return "getInstalledProvidersForProfile";
                case 19:
                    return "getAppWidgetInfo";
                case 20:
                    return "hasBindAppWidgetPermission";
                case 21:
                    return "setBindAppWidgetPermission";
                case 22:
                    return "bindAppWidgetId";
                case 23:
                    return "bindRemoteViewsService";
                case 24:
                    return "notifyProviderInheritance";
                case 25:
                    return "getAppWidgetIds";
                case 26:
                    return "isBoundWidgetPackage";
                case 27:
                    return "requestPinAppWidget";
                case 28:
                    return "isRequestPinAppWidgetSupported";
                case 29:
                    return "noteAppWidgetTapped";
                case 30:
                    return "setWidgetPreview";
                case 31:
                    return "getWidgetPreview";
                case 32:
                    return "removeWidgetPreview";
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
                    com.android.internal.appwidget.IAppWidgetHost asInterface = com.android.internal.appwidget.IAppWidgetHost.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice startListening = startListening(asInterface, readString, readInt, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startListening, 1);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopListening(readString2, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int allocateAppWidgetId = allocateAppWidgetId(readString3, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(allocateAppWidgetId);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteAppWidgetId(readString4, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteHost(readString5, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    deleteAllHosts();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString6 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.widget.RemoteViews appWidgetViews = getAppWidgetViews(readString6, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appWidgetViews, 1);
                    return true;
                case 8:
                    java.lang.String readString7 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] appWidgetIdsForHost = getAppWidgetIdsForHost(readString7, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(appWidgetIdsForHost);
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppWidgetHidden(readString8, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString9 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.IntentSender createAppWidgetConfigIntentSender = createAppWidgetConfigIntentSender(readString9, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createAppWidgetConfigIntentSender, 1);
                    return true;
                case 11:
                    java.lang.String readString10 = parcel.readString();
                    int[] createIntArray2 = parcel.createIntArray();
                    android.widget.RemoteViews remoteViews = (android.widget.RemoteViews) parcel.readTypedObject(android.widget.RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAppWidgetIds(readString10, createIntArray2, remoteViews);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    java.lang.String readString11 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAppWidgetOptions(readString11, readInt11, bundle);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String readString12 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle appWidgetOptions = getAppWidgetOptions(readString12, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appWidgetOptions, 1);
                    return true;
                case 14:
                    java.lang.String readString13 = parcel.readString();
                    int[] createIntArray3 = parcel.createIntArray();
                    android.widget.RemoteViews remoteViews2 = (android.widget.RemoteViews) parcel.readTypedObject(android.widget.RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    partiallyUpdateAppWidgetIds(readString13, createIntArray3, remoteViews2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.widget.RemoteViews remoteViews3 = (android.widget.RemoteViews) parcel.readTypedObject(android.widget.RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAppWidgetProvider(componentName, remoteViews3);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateAppWidgetProviderInfo(componentName2, readString14);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    java.lang.String readString15 = parcel.readString();
                    int[] createIntArray4 = parcel.createIntArray();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAppWidgetViewDataChanged(readString15, createIntArray4, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice installedProvidersForProfile = getInstalledProvidersForProfile(readInt14, readInt15, readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedProvidersForProfile, 1);
                    return true;
                case 19:
                    java.lang.String readString17 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.appwidget.AppWidgetProviderInfo appWidgetInfo = getAppWidgetInfo(readString17, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appWidgetInfo, 1);
                    return true;
                case 20:
                    java.lang.String readString18 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasBindAppWidgetPermission = hasBindAppWidgetPermission(readString18, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasBindAppWidgetPermission);
                    return true;
                case 21:
                    java.lang.String readString19 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBindAppWidgetPermission(readString19, readInt18, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    java.lang.String readString20 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    android.content.ComponentName componentName3 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean bindAppWidgetId = bindAppWidgetId(readString20, readInt19, readInt20, componentName3, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindAppWidgetId);
                    return true;
                case 23:
                    java.lang.String readString21 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    android.app.IApplicationThread asInterface2 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.app.IServiceConnection asInterface3 = android.app.IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean bindRemoteViewsService = bindRemoteViewsService(readString21, readInt21, intent, asInterface2, readStrongBinder, asInterface3, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindRemoteViewsService);
                    return true;
                case 24:
                    android.content.ComponentName[] componentNameArr = (android.content.ComponentName[]) parcel.createTypedArray(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyProviderInheritance(componentNameArr);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    android.content.ComponentName componentName4 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int[] appWidgetIds = getAppWidgetIds(componentName4);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(appWidgetIds);
                    return true;
                case 26:
                    java.lang.String readString22 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBoundWidgetPackage = isBoundWidgetPackage(readString22, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBoundWidgetPackage);
                    return true;
                case 27:
                    java.lang.String readString23 = parcel.readString();
                    android.content.ComponentName componentName5 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestPinAppWidget = requestPinAppWidget(readString23, componentName5, bundle3, intentSender);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestPinAppWidget);
                    return true;
                case 28:
                    boolean isRequestPinAppWidgetSupported = isRequestPinAppWidgetSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRequestPinAppWidgetSupported);
                    return true;
                case 29:
                    java.lang.String readString24 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteAppWidgetTapped(readString24, readInt23);
                    return true;
                case 30:
                    android.content.ComponentName componentName6 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt24 = parcel.readInt();
                    android.widget.RemoteViews remoteViews4 = (android.widget.RemoteViews) parcel.readTypedObject(android.widget.RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    setWidgetPreview(componentName6, readInt24, remoteViews4);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    java.lang.String readString25 = parcel.readString();
                    android.content.ComponentName componentName7 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.widget.RemoteViews widgetPreview = getWidgetPreview(readString25, componentName7, readInt25, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(widgetPreview, 1);
                    return true;
                case 32:
                    android.content.ComponentName componentName8 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeWidgetPreview(componentName8, readInt27);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.appwidget.IAppWidgetService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public android.content.pm.ParceledListSlice startListening(com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost, java.lang.String str, int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppWidgetHost);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void stopListening(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int allocateAppWidgetId(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteAppWidgetId(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteHost(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteAllHosts() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public android.widget.RemoteViews getAppWidgetViews(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.widget.RemoteViews) obtain2.readTypedObject(android.widget.RemoteViews.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int[] getAppWidgetIdsForHost(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void setAppWidgetHidden(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public android.content.IntentSender createAppWidgetConfigIntentSender(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.IntentSender) obtain2.readTypedObject(android.content.IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetIds(java.lang.String str, int[] iArr, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetOptions(java.lang.String str, int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public android.os.Bundle getAppWidgetOptions(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void partiallyUpdateAppWidgetIds(java.lang.String str, int[] iArr, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetProvider(android.content.ComponentName componentName, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetProviderInfo(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void notifyAppWidgetViewDataChanged(java.lang.String str, int[] iArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public android.content.pm.ParceledListSlice getInstalledProvidersForProfile(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public android.appwidget.AppWidgetProviderInfo getAppWidgetInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.appwidget.AppWidgetProviderInfo) obtain2.readTypedObject(android.appwidget.AppWidgetProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean hasBindAppWidgetPermission(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void setBindAppWidgetPermission(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean bindAppWidgetId(java.lang.String str, int i, int i2, android.content.ComponentName componentName, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean bindRemoteViewsService(java.lang.String str, int i, android.content.Intent intent, android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.app.IServiceConnection iServiceConnection, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeLong(j);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void notifyProviderInheritance(android.content.ComponentName[] componentNameArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(componentNameArr, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int[] getAppWidgetIds(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isBoundWidgetPackage(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean requestPinAppWidget(java.lang.String str, android.content.ComponentName componentName, android.os.Bundle bundle, android.content.IntentSender intentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isRequestPinAppWidgetSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void noteAppWidgetTapped(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void setWidgetPreview(android.content.ComponentName componentName, int i, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public android.widget.RemoteViews getWidgetPreview(java.lang.String str, android.content.ComponentName componentName, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.widget.RemoteViews) obtain2.readTypedObject(android.widget.RemoteViews.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void removeWidgetPreview(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 31;
        }
    }
}
