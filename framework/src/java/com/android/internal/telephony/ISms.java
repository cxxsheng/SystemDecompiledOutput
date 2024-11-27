package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface ISms extends android.os.IInterface {
    int checkSmsShortCodeDestination(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException;

    void clearStorageMonitorMemoryStatusOverride(int i) throws android.os.RemoteException;

    boolean copyMessageToIccEfForSubscriber(int i, java.lang.String str, int i2, byte[] bArr, byte[] bArr2) throws android.os.RemoteException;

    java.lang.String createAppSpecificSmsToken(int i, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    java.lang.String createAppSpecificSmsTokenWithPackageInfo(int i, java.lang.String str, java.lang.String str2, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    boolean disableCellBroadcastForSubscriber(int i, int i2, int i3) throws android.os.RemoteException;

    boolean disableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    boolean enableCellBroadcastForSubscriber(int i, int i2, int i3) throws android.os.RemoteException;

    boolean enableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    java.util.List<com.android.internal.telephony.SmsRawData> getAllMessagesFromIccEfForSubscriber(int i, java.lang.String str) throws android.os.RemoteException;

    android.os.Bundle getCarrierConfigValuesForSubscriber(int i) throws android.os.RemoteException;

    java.lang.String getImsSmsFormatForSubscriber(int i) throws android.os.RemoteException;

    int getPreferredSmsSubscription() throws android.os.RemoteException;

    int getPremiumSmsPermission(java.lang.String str) throws android.os.RemoteException;

    int getPremiumSmsPermissionForSubscriber(int i, java.lang.String str) throws android.os.RemoteException;

    int getSmsCapacityOnIccForSubscriber(int i) throws android.os.RemoteException;

    java.lang.String getSmscAddressFromIccEfForSubscriber(int i, java.lang.String str) throws android.os.RemoteException;

    long getWapMessageSize(java.lang.String str) throws android.os.RemoteException;

    void injectSmsPduForSubscriber(int i, byte[] bArr, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    boolean isImsSmsSupportedForSubscriber(int i) throws android.os.RemoteException;

    boolean isSMSPromptEnabled() throws android.os.RemoteException;

    boolean isSmsSimPickActivityNeeded(int i) throws android.os.RemoteException;

    boolean resetAllCellBroadcastRanges(int i) throws android.os.RemoteException;

    void sendDataForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i2, byte[] bArr, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) throws android.os.RemoteException;

    void sendMultipartTextForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3, boolean z, long j) throws android.os.RemoteException;

    void sendMultipartTextForSubscriberWithOptions(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3, boolean z, int i2, boolean z2, int i3) throws android.os.RemoteException;

    void sendStoredMultipartText(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, java.lang.String str3, java.util.List<android.app.PendingIntent> list, java.util.List<android.app.PendingIntent> list2) throws android.os.RemoteException;

    void sendStoredText(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, java.lang.String str3, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) throws android.os.RemoteException;

    void sendTextForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, boolean z, long j) throws android.os.RemoteException;

    void sendTextForSubscriberWithOptions(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, boolean z, int i2, boolean z2, int i3) throws android.os.RemoteException;

    void setPremiumSmsPermission(java.lang.String str, int i) throws android.os.RemoteException;

    void setPremiumSmsPermissionForSubscriber(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    boolean setSmscAddressOnIccEfForSubscriber(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void setStorageMonitorMemoryStatusOverride(int i, boolean z) throws android.os.RemoteException;

    boolean updateMessageOnIccEfForSubscriber(int i, java.lang.String str, int i2, int i3, byte[] bArr) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.ISms {
        @Override // com.android.internal.telephony.ISms
        public java.util.List<com.android.internal.telephony.SmsRawData> getAllMessagesFromIccEfForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean updateMessageOnIccEfForSubscriber(int i, java.lang.String str, int i2, int i3, byte[] bArr) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean copyMessageToIccEfForSubscriber(int i, java.lang.String str, int i2, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public void sendDataForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i2, byte[] bArr, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, boolean z, long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextForSubscriberWithOptions(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, boolean z, int i2, boolean z2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void injectSmsPduForSubscriber(int i, byte[] bArr, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendMultipartTextForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3, boolean z, long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendMultipartTextForSubscriberWithOptions(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3, boolean z, int i2, boolean z2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public boolean enableCellBroadcastForSubscriber(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean disableCellBroadcastForSubscriber(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean enableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean disableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public int getPremiumSmsPermission(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public int getPremiumSmsPermissionForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public void setPremiumSmsPermission(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void setPremiumSmsPermissionForSubscriber(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public boolean isImsSmsSupportedForSubscriber(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean isSmsSimPickActivityNeeded(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public int getPreferredSmsSubscription() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public java.lang.String getImsSmsFormatForSubscriber(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean isSMSPromptEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public void sendStoredText(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, java.lang.String str3, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendStoredMultipartText(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, java.lang.String str3, java.util.List<android.app.PendingIntent> list, java.util.List<android.app.PendingIntent> list2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public android.os.Bundle getCarrierConfigValuesForSubscriber(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public java.lang.String createAppSpecificSmsToken(int i, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public java.lang.String createAppSpecificSmsTokenWithPackageInfo(int i, java.lang.String str, java.lang.String str2, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public void setStorageMonitorMemoryStatusOverride(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void clearStorageMonitorMemoryStatusOverride(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public int checkSmsShortCodeDestination(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public java.lang.String getSmscAddressFromIccEfForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean setSmscAddressOnIccEfForSubscriber(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public int getSmsCapacityOnIccForSubscriber(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean resetAllCellBroadcastRanges(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public long getWapMessageSize(java.lang.String str) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.ISms {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.ISms";
        static final int TRANSACTION_checkSmsShortCodeDestination = 30;
        static final int TRANSACTION_clearStorageMonitorMemoryStatusOverride = 29;
        static final int TRANSACTION_copyMessageToIccEfForSubscriber = 3;
        static final int TRANSACTION_createAppSpecificSmsToken = 26;
        static final int TRANSACTION_createAppSpecificSmsTokenWithPackageInfo = 27;
        static final int TRANSACTION_disableCellBroadcastForSubscriber = 11;
        static final int TRANSACTION_disableCellBroadcastRangeForSubscriber = 13;
        static final int TRANSACTION_enableCellBroadcastForSubscriber = 10;
        static final int TRANSACTION_enableCellBroadcastRangeForSubscriber = 12;
        static final int TRANSACTION_getAllMessagesFromIccEfForSubscriber = 1;
        static final int TRANSACTION_getCarrierConfigValuesForSubscriber = 25;
        static final int TRANSACTION_getImsSmsFormatForSubscriber = 21;
        static final int TRANSACTION_getPreferredSmsSubscription = 20;
        static final int TRANSACTION_getPremiumSmsPermission = 14;
        static final int TRANSACTION_getPremiumSmsPermissionForSubscriber = 15;
        static final int TRANSACTION_getSmsCapacityOnIccForSubscriber = 33;
        static final int TRANSACTION_getSmscAddressFromIccEfForSubscriber = 31;
        static final int TRANSACTION_getWapMessageSize = 35;
        static final int TRANSACTION_injectSmsPduForSubscriber = 7;
        static final int TRANSACTION_isImsSmsSupportedForSubscriber = 18;
        static final int TRANSACTION_isSMSPromptEnabled = 22;
        static final int TRANSACTION_isSmsSimPickActivityNeeded = 19;
        static final int TRANSACTION_resetAllCellBroadcastRanges = 34;
        static final int TRANSACTION_sendDataForSubscriber = 4;
        static final int TRANSACTION_sendMultipartTextForSubscriber = 8;
        static final int TRANSACTION_sendMultipartTextForSubscriberWithOptions = 9;
        static final int TRANSACTION_sendStoredMultipartText = 24;
        static final int TRANSACTION_sendStoredText = 23;
        static final int TRANSACTION_sendTextForSubscriber = 5;
        static final int TRANSACTION_sendTextForSubscriberWithOptions = 6;
        static final int TRANSACTION_setPremiumSmsPermission = 16;
        static final int TRANSACTION_setPremiumSmsPermissionForSubscriber = 17;
        static final int TRANSACTION_setSmscAddressOnIccEfForSubscriber = 32;
        static final int TRANSACTION_setStorageMonitorMemoryStatusOverride = 28;
        static final int TRANSACTION_updateMessageOnIccEfForSubscriber = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telephony.ISms asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.ISms)) {
                return (com.android.internal.telephony.ISms) queryLocalInterface;
            }
            return new com.android.internal.telephony.ISms.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAllMessagesFromIccEfForSubscriber";
                case 2:
                    return "updateMessageOnIccEfForSubscriber";
                case 3:
                    return "copyMessageToIccEfForSubscriber";
                case 4:
                    return "sendDataForSubscriber";
                case 5:
                    return "sendTextForSubscriber";
                case 6:
                    return "sendTextForSubscriberWithOptions";
                case 7:
                    return "injectSmsPduForSubscriber";
                case 8:
                    return "sendMultipartTextForSubscriber";
                case 9:
                    return "sendMultipartTextForSubscriberWithOptions";
                case 10:
                    return "enableCellBroadcastForSubscriber";
                case 11:
                    return "disableCellBroadcastForSubscriber";
                case 12:
                    return "enableCellBroadcastRangeForSubscriber";
                case 13:
                    return "disableCellBroadcastRangeForSubscriber";
                case 14:
                    return "getPremiumSmsPermission";
                case 15:
                    return "getPremiumSmsPermissionForSubscriber";
                case 16:
                    return "setPremiumSmsPermission";
                case 17:
                    return "setPremiumSmsPermissionForSubscriber";
                case 18:
                    return "isImsSmsSupportedForSubscriber";
                case 19:
                    return "isSmsSimPickActivityNeeded";
                case 20:
                    return "getPreferredSmsSubscription";
                case 21:
                    return "getImsSmsFormatForSubscriber";
                case 22:
                    return "isSMSPromptEnabled";
                case 23:
                    return "sendStoredText";
                case 24:
                    return "sendStoredMultipartText";
                case 25:
                    return "getCarrierConfigValuesForSubscriber";
                case 26:
                    return "createAppSpecificSmsToken";
                case 27:
                    return "createAppSpecificSmsTokenWithPackageInfo";
                case 28:
                    return "setStorageMonitorMemoryStatusOverride";
                case 29:
                    return "clearStorageMonitorMemoryStatusOverride";
                case 30:
                    return "checkSmsShortCodeDestination";
                case 31:
                    return "getSmscAddressFromIccEfForSubscriber";
                case 32:
                    return "setSmscAddressOnIccEfForSubscriber";
                case 33:
                    return "getSmsCapacityOnIccForSubscriber";
                case 34:
                    return "resetAllCellBroadcastRanges";
                case 35:
                    return "getWapMessageSize";
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
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<com.android.internal.telephony.SmsRawData> allMessagesFromIccEfForSubscriber = getAllMessagesFromIccEfForSubscriber(readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allMessagesFromIccEfForSubscriber, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean updateMessageOnIccEfForSubscriber = updateMessageOnIccEfForSubscriber(readInt2, readString2, readInt3, readInt4, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateMessageOnIccEfForSubscriber);
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean copyMessageToIccEfForSubscriber = copyMessageToIccEfForSubscriber(readInt5, readString3, readInt6, createByteArray2, createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(copyMessageToIccEfForSubscriber);
                    return true;
                case 4:
                    int readInt7 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    byte[] createByteArray4 = parcel.createByteArray();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDataForSubscriber(readInt7, readString4, readString5, readString6, readString7, readInt8, createByteArray4, pendingIntent, pendingIntent2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt9 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    android.app.PendingIntent pendingIntent3 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.app.PendingIntent pendingIntent4 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    sendTextForSubscriber(readInt9, readString8, readString9, readString10, readString11, readString12, pendingIntent3, pendingIntent4, readBoolean, readLong);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt10 = parcel.readInt();
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    android.app.PendingIntent pendingIntent5 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.app.PendingIntent pendingIntent6 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt11 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextForSubscriberWithOptions(readInt10, readString13, readString14, readString15, readString16, readString17, pendingIntent5, pendingIntent6, readBoolean2, readInt11, readBoolean3, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt13 = parcel.readInt();
                    byte[] createByteArray5 = parcel.createByteArray();
                    java.lang.String readString18 = parcel.readString();
                    android.app.PendingIntent pendingIntent7 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectSmsPduForSubscriber(readInt13, createByteArray5, readString18, pendingIntent7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt14 = parcel.readInt();
                    java.lang.String readString19 = parcel.readString();
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    java.lang.String readString22 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.app.PendingIntent.CREATOR);
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.app.PendingIntent.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    sendMultipartTextForSubscriber(readInt14, readString19, readString20, readString21, readString22, createStringArrayList, createTypedArrayList, createTypedArrayList2, readBoolean4, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt15 = parcel.readInt();
                    java.lang.String readString23 = parcel.readString();
                    java.lang.String readString24 = parcel.readString();
                    java.lang.String readString25 = parcel.readString();
                    java.lang.String readString26 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    java.util.ArrayList createTypedArrayList3 = parcel.createTypedArrayList(android.app.PendingIntent.CREATOR);
                    java.util.ArrayList createTypedArrayList4 = parcel.createTypedArrayList(android.app.PendingIntent.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt16 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendMultipartTextForSubscriberWithOptions(readInt15, readString23, readString24, readString25, readString26, createStringArrayList2, createTypedArrayList3, createTypedArrayList4, readBoolean5, readInt16, readBoolean6, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enableCellBroadcastForSubscriber = enableCellBroadcastForSubscriber(readInt18, readInt19, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableCellBroadcastForSubscriber);
                    return true;
                case 11:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean disableCellBroadcastForSubscriber = disableCellBroadcastForSubscriber(readInt21, readInt22, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableCellBroadcastForSubscriber);
                    return true;
                case 12:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enableCellBroadcastRangeForSubscriber = enableCellBroadcastRangeForSubscriber(readInt24, readInt25, readInt26, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableCellBroadcastRangeForSubscriber);
                    return true;
                case 13:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean disableCellBroadcastRangeForSubscriber = disableCellBroadcastRangeForSubscriber(readInt28, readInt29, readInt30, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableCellBroadcastRangeForSubscriber);
                    return true;
                case 14:
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int premiumSmsPermission = getPremiumSmsPermission(readString27);
                    parcel2.writeNoException();
                    parcel2.writeInt(premiumSmsPermission);
                    return true;
                case 15:
                    int readInt32 = parcel.readInt();
                    java.lang.String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int premiumSmsPermissionForSubscriber = getPremiumSmsPermissionForSubscriber(readInt32, readString28);
                    parcel2.writeNoException();
                    parcel2.writeInt(premiumSmsPermissionForSubscriber);
                    return true;
                case 16:
                    java.lang.String readString29 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPremiumSmsPermission(readString29, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt34 = parcel.readInt();
                    java.lang.String readString30 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPremiumSmsPermissionForSubscriber(readInt34, readString30, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isImsSmsSupportedForSubscriber = isImsSmsSupportedForSubscriber(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImsSmsSupportedForSubscriber);
                    return true;
                case 19:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSmsSimPickActivityNeeded = isSmsSimPickActivityNeeded(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSmsSimPickActivityNeeded);
                    return true;
                case 20:
                    int preferredSmsSubscription = getPreferredSmsSubscription();
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredSmsSubscription);
                    return true;
                case 21:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String imsSmsFormatForSubscriber = getImsSmsFormatForSubscriber(readInt38);
                    parcel2.writeNoException();
                    parcel2.writeString(imsSmsFormatForSubscriber);
                    return true;
                case 22:
                    boolean isSMSPromptEnabled = isSMSPromptEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSMSPromptEnabled);
                    return true;
                case 23:
                    int readInt39 = parcel.readInt();
                    java.lang.String readString31 = parcel.readString();
                    java.lang.String readString32 = parcel.readString();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.lang.String readString33 = parcel.readString();
                    android.app.PendingIntent pendingIntent8 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.app.PendingIntent pendingIntent9 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendStoredText(readInt39, readString31, readString32, uri, readString33, pendingIntent8, pendingIntent9);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt40 = parcel.readInt();
                    java.lang.String readString34 = parcel.readString();
                    java.lang.String readString35 = parcel.readString();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.lang.String readString36 = parcel.readString();
                    java.util.ArrayList createTypedArrayList5 = parcel.createTypedArrayList(android.app.PendingIntent.CREATOR);
                    java.util.ArrayList createTypedArrayList6 = parcel.createTypedArrayList(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendStoredMultipartText(readInt40, readString34, readString35, uri2, readString36, createTypedArrayList5, createTypedArrayList6);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle carrierConfigValuesForSubscriber = getCarrierConfigValuesForSubscriber(readInt41);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(carrierConfigValuesForSubscriber, 1);
                    return true;
                case 26:
                    int readInt42 = parcel.readInt();
                    java.lang.String readString37 = parcel.readString();
                    android.app.PendingIntent pendingIntent10 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String createAppSpecificSmsToken = createAppSpecificSmsToken(readInt42, readString37, pendingIntent10);
                    parcel2.writeNoException();
                    parcel2.writeString(createAppSpecificSmsToken);
                    return true;
                case 27:
                    int readInt43 = parcel.readInt();
                    java.lang.String readString38 = parcel.readString();
                    java.lang.String readString39 = parcel.readString();
                    android.app.PendingIntent pendingIntent11 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String createAppSpecificSmsTokenWithPackageInfo = createAppSpecificSmsTokenWithPackageInfo(readInt43, readString38, readString39, pendingIntent11);
                    parcel2.writeNoException();
                    parcel2.writeString(createAppSpecificSmsTokenWithPackageInfo);
                    return true;
                case 28:
                    int readInt44 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStorageMonitorMemoryStatusOverride(readInt44, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearStorageMonitorMemoryStatusOverride(readInt45);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt46 = parcel.readInt();
                    java.lang.String readString40 = parcel.readString();
                    java.lang.String readString41 = parcel.readString();
                    java.lang.String readString42 = parcel.readString();
                    java.lang.String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkSmsShortCodeDestination = checkSmsShortCodeDestination(readInt46, readString40, readString41, readString42, readString43);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkSmsShortCodeDestination);
                    return true;
                case 31:
                    int readInt47 = parcel.readInt();
                    java.lang.String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String smscAddressFromIccEfForSubscriber = getSmscAddressFromIccEfForSubscriber(readInt47, readString44);
                    parcel2.writeNoException();
                    parcel2.writeString(smscAddressFromIccEfForSubscriber);
                    return true;
                case 32:
                    java.lang.String readString45 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    java.lang.String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean smscAddressOnIccEfForSubscriber = setSmscAddressOnIccEfForSubscriber(readString45, readInt48, readString46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(smscAddressOnIccEfForSubscriber);
                    return true;
                case 33:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int smsCapacityOnIccForSubscriber = getSmsCapacityOnIccForSubscriber(readInt49);
                    parcel2.writeNoException();
                    parcel2.writeInt(smsCapacityOnIccForSubscriber);
                    return true;
                case 34:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resetAllCellBroadcastRanges = resetAllCellBroadcastRanges(readInt50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetAllCellBroadcastRanges);
                    return true;
                case 35:
                    java.lang.String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long wapMessageSize = getWapMessageSize(readString47);
                    parcel2.writeNoException();
                    parcel2.writeLong(wapMessageSize);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.ISms {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.ISms.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISms
            public java.util.List<com.android.internal.telephony.SmsRawData> getAllMessagesFromIccEfForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(com.android.internal.telephony.SmsRawData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean updateMessageOnIccEfForSubscriber(int i, java.lang.String str, int i2, int i3, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean copyMessageToIccEfForSubscriber(int i, java.lang.String str, int i2, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendDataForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i2, byte[] bArr, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, boolean z, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextForSubscriberWithOptions(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, boolean z, int i2, boolean z2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void injectSmsPduForSubscriber(int i, byte[] bArr, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3, boolean z, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeTypedList(list3, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextForSubscriberWithOptions(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3, boolean z, int i2, boolean z2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeTypedList(list3, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean enableCellBroadcastForSubscriber(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean disableCellBroadcastForSubscriber(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean enableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean disableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPremiumSmsPermission(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPremiumSmsPermissionForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setPremiumSmsPermission(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setPremiumSmsPermissionForSubscriber(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isImsSmsSupportedForSubscriber(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isSmsSimPickActivityNeeded(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPreferredSmsSubscription() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public java.lang.String getImsSmsFormatForSubscriber(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isSMSPromptEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendStoredText(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, java.lang.String str3, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendStoredMultipartText(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, java.lang.String str3, java.util.List<android.app.PendingIntent> list, java.util.List<android.app.PendingIntent> list2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str3);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public android.os.Bundle getCarrierConfigValuesForSubscriber(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public java.lang.String createAppSpecificSmsToken(int i, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public java.lang.String createAppSpecificSmsTokenWithPackageInfo(int i, java.lang.String str, java.lang.String str2, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setStorageMonitorMemoryStatusOverride(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void clearStorageMonitorMemoryStatusOverride(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int checkSmsShortCodeDestination(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public java.lang.String getSmscAddressFromIccEfForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean setSmscAddressOnIccEfForSubscriber(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getSmsCapacityOnIccForSubscriber(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean resetAllCellBroadcastRanges(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public long getWapMessageSize(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISms.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 34;
        }
    }
}
