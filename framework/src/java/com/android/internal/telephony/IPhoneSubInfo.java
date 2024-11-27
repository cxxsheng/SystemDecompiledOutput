package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface IPhoneSubInfo extends android.os.IInterface {
    android.telephony.ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    @java.lang.Deprecated
    java.lang.String getDeviceId(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getDeviceIdForPhone(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getDeviceIdWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getDeviceSvn(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getDeviceSvnUsingSubId(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getGroupIdLevel1ForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getIccSerialNumber(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getIccSerialNumberForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getIccSerialNumberWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getIccSimChallengeResponse(int i, int i2, int i3, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    java.lang.String getImeiForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getImsPrivateUserIdentity(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.util.List<android.net.Uri> getImsPublicUserIdentities(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getIsimDomain(int i) throws android.os.RemoteException;

    java.lang.String getIsimImpi(int i) throws android.os.RemoteException;

    java.lang.String[] getIsimImpu(int i) throws android.os.RemoteException;

    java.lang.String getIsimIst(int i) throws android.os.RemoteException;

    java.lang.String[] getIsimPcscf(int i) throws android.os.RemoteException;

    java.lang.String getLine1AlphaTag(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getLine1AlphaTagForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getLine1Number(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getLine1NumberForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getMsisdn(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getMsisdnForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getNaiForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getSimServiceTable(int i, int i2) throws android.os.RemoteException;

    android.net.Uri getSmscIdentity(int i, int i2) throws android.os.RemoteException;

    @java.lang.Deprecated
    java.lang.String getSubscriberId(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getSubscriberIdForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getSubscriberIdWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getVoiceMailAlphaTag(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getVoiceMailAlphaTagForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getVoiceMailNumber(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getVoiceMailNumberForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void resetCarrierKeysForImsiEncryption(int i, java.lang.String str) throws android.os.RemoteException;

    void setCarrierInfoForImsiEncryption(int i, java.lang.String str, android.telephony.ImsiEncryptionInfo imsiEncryptionInfo) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.IPhoneSubInfo {
        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getDeviceId(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getDeviceIdWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getNaiForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getDeviceIdForPhone(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getImeiForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getDeviceSvn(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getDeviceSvnUsingSubId(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getSubscriberId(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getSubscriberIdWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getSubscriberIdForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getGroupIdLevel1ForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getIccSerialNumber(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getIccSerialNumberWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getIccSerialNumberForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getLine1Number(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getLine1NumberForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getLine1AlphaTag(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getLine1AlphaTagForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getMsisdn(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getMsisdnForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getVoiceMailNumber(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getVoiceMailNumberForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public android.telephony.ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public void setCarrierInfoForImsiEncryption(int i, java.lang.String str, android.telephony.ImsiEncryptionInfo imsiEncryptionInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public void resetCarrierKeysForImsiEncryption(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getVoiceMailAlphaTag(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getVoiceMailAlphaTagForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getIsimImpi(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getImsPrivateUserIdentity(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getIsimDomain(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String[] getIsimImpu(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.util.List<android.net.Uri> getImsPublicUserIdentities(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getIsimIst(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String[] getIsimPcscf(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getIccSimChallengeResponse(int i, int i2, int i3, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public android.net.Uri getSmscIdentity(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public java.lang.String getSimServiceTable(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.IPhoneSubInfo {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.IPhoneSubInfo";
        static final int TRANSACTION_getCarrierInfoForImsiEncryption = 23;
        static final int TRANSACTION_getDeviceId = 1;
        static final int TRANSACTION_getDeviceIdForPhone = 4;
        static final int TRANSACTION_getDeviceIdWithFeature = 2;
        static final int TRANSACTION_getDeviceSvn = 6;
        static final int TRANSACTION_getDeviceSvnUsingSubId = 7;
        static final int TRANSACTION_getGroupIdLevel1ForSubscriber = 11;
        static final int TRANSACTION_getIccSerialNumber = 12;
        static final int TRANSACTION_getIccSerialNumberForSubscriber = 14;
        static final int TRANSACTION_getIccSerialNumberWithFeature = 13;
        static final int TRANSACTION_getIccSimChallengeResponse = 35;
        static final int TRANSACTION_getImeiForSubscriber = 5;
        static final int TRANSACTION_getImsPrivateUserIdentity = 29;
        static final int TRANSACTION_getImsPublicUserIdentities = 32;
        static final int TRANSACTION_getIsimDomain = 30;
        static final int TRANSACTION_getIsimImpi = 28;
        static final int TRANSACTION_getIsimImpu = 31;
        static final int TRANSACTION_getIsimIst = 33;
        static final int TRANSACTION_getIsimPcscf = 34;
        static final int TRANSACTION_getLine1AlphaTag = 17;
        static final int TRANSACTION_getLine1AlphaTagForSubscriber = 18;
        static final int TRANSACTION_getLine1Number = 15;
        static final int TRANSACTION_getLine1NumberForSubscriber = 16;
        static final int TRANSACTION_getMsisdn = 19;
        static final int TRANSACTION_getMsisdnForSubscriber = 20;
        static final int TRANSACTION_getNaiForSubscriber = 3;
        static final int TRANSACTION_getSimServiceTable = 37;
        static final int TRANSACTION_getSmscIdentity = 36;
        static final int TRANSACTION_getSubscriberId = 8;
        static final int TRANSACTION_getSubscriberIdForSubscriber = 10;
        static final int TRANSACTION_getSubscriberIdWithFeature = 9;
        static final int TRANSACTION_getVoiceMailAlphaTag = 26;
        static final int TRANSACTION_getVoiceMailAlphaTagForSubscriber = 27;
        static final int TRANSACTION_getVoiceMailNumber = 21;
        static final int TRANSACTION_getVoiceMailNumberForSubscriber = 22;
        static final int TRANSACTION_resetCarrierKeysForImsiEncryption = 25;
        static final int TRANSACTION_setCarrierInfoForImsiEncryption = 24;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telephony.IPhoneSubInfo asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.IPhoneSubInfo)) {
                return (com.android.internal.telephony.IPhoneSubInfo) queryLocalInterface;
            }
            return new com.android.internal.telephony.IPhoneSubInfo.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDeviceId";
                case 2:
                    return "getDeviceIdWithFeature";
                case 3:
                    return "getNaiForSubscriber";
                case 4:
                    return "getDeviceIdForPhone";
                case 5:
                    return "getImeiForSubscriber";
                case 6:
                    return "getDeviceSvn";
                case 7:
                    return "getDeviceSvnUsingSubId";
                case 8:
                    return "getSubscriberId";
                case 9:
                    return "getSubscriberIdWithFeature";
                case 10:
                    return "getSubscriberIdForSubscriber";
                case 11:
                    return "getGroupIdLevel1ForSubscriber";
                case 12:
                    return "getIccSerialNumber";
                case 13:
                    return "getIccSerialNumberWithFeature";
                case 14:
                    return "getIccSerialNumberForSubscriber";
                case 15:
                    return "getLine1Number";
                case 16:
                    return "getLine1NumberForSubscriber";
                case 17:
                    return "getLine1AlphaTag";
                case 18:
                    return "getLine1AlphaTagForSubscriber";
                case 19:
                    return "getMsisdn";
                case 20:
                    return "getMsisdnForSubscriber";
                case 21:
                    return "getVoiceMailNumber";
                case 22:
                    return "getVoiceMailNumberForSubscriber";
                case 23:
                    return "getCarrierInfoForImsiEncryption";
                case 24:
                    return "setCarrierInfoForImsiEncryption";
                case 25:
                    return "resetCarrierKeysForImsiEncryption";
                case 26:
                    return "getVoiceMailAlphaTag";
                case 27:
                    return "getVoiceMailAlphaTagForSubscriber";
                case 28:
                    return "getIsimImpi";
                case 29:
                    return "getImsPrivateUserIdentity";
                case 30:
                    return "getIsimDomain";
                case 31:
                    return "getIsimImpu";
                case 32:
                    return "getImsPublicUserIdentities";
                case 33:
                    return "getIsimIst";
                case 34:
                    return "getIsimPcscf";
                case 35:
                    return "getIccSimChallengeResponse";
                case 36:
                    return "getSmscIdentity";
                case 37:
                    return "getSimServiceTable";
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
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String deviceId = getDeviceId(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String deviceIdWithFeature = getDeviceIdWithFeature(readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceIdWithFeature);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String naiForSubscriber = getNaiForSubscriber(readInt, readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeString(naiForSubscriber);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String deviceIdForPhone = getDeviceIdForPhone(readInt2, readString6, readString7);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceIdForPhone);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String imeiForSubscriber = getImeiForSubscriber(readInt3, readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeString(imeiForSubscriber);
                    return true;
                case 6:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String deviceSvn = getDeviceSvn(readString10, readString11);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceSvn);
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String deviceSvnUsingSubId = getDeviceSvnUsingSubId(readInt4, readString12, readString13);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceSvnUsingSubId);
                    return true;
                case 8:
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String subscriberId = getSubscriberId(readString14);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberId);
                    return true;
                case 9:
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String subscriberIdWithFeature = getSubscriberIdWithFeature(readString15, readString16);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberIdWithFeature);
                    return true;
                case 10:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String subscriberIdForSubscriber = getSubscriberIdForSubscriber(readInt5, readString17, readString18);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberIdForSubscriber);
                    return true;
                case 11:
                    int readInt6 = parcel.readInt();
                    java.lang.String readString19 = parcel.readString();
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String groupIdLevel1ForSubscriber = getGroupIdLevel1ForSubscriber(readInt6, readString19, readString20);
                    parcel2.writeNoException();
                    parcel2.writeString(groupIdLevel1ForSubscriber);
                    return true;
                case 12:
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String iccSerialNumber = getIccSerialNumber(readString21);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSerialNumber);
                    return true;
                case 13:
                    java.lang.String readString22 = parcel.readString();
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String iccSerialNumberWithFeature = getIccSerialNumberWithFeature(readString22, readString23);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSerialNumberWithFeature);
                    return true;
                case 14:
                    int readInt7 = parcel.readInt();
                    java.lang.String readString24 = parcel.readString();
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String iccSerialNumberForSubscriber = getIccSerialNumberForSubscriber(readInt7, readString24, readString25);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSerialNumberForSubscriber);
                    return true;
                case 15:
                    java.lang.String readString26 = parcel.readString();
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String line1Number = getLine1Number(readString26, readString27);
                    parcel2.writeNoException();
                    parcel2.writeString(line1Number);
                    return true;
                case 16:
                    int readInt8 = parcel.readInt();
                    java.lang.String readString28 = parcel.readString();
                    java.lang.String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String line1NumberForSubscriber = getLine1NumberForSubscriber(readInt8, readString28, readString29);
                    parcel2.writeNoException();
                    parcel2.writeString(line1NumberForSubscriber);
                    return true;
                case 17:
                    java.lang.String readString30 = parcel.readString();
                    java.lang.String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String line1AlphaTag = getLine1AlphaTag(readString30, readString31);
                    parcel2.writeNoException();
                    parcel2.writeString(line1AlphaTag);
                    return true;
                case 18:
                    int readInt9 = parcel.readInt();
                    java.lang.String readString32 = parcel.readString();
                    java.lang.String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String line1AlphaTagForSubscriber = getLine1AlphaTagForSubscriber(readInt9, readString32, readString33);
                    parcel2.writeNoException();
                    parcel2.writeString(line1AlphaTagForSubscriber);
                    return true;
                case 19:
                    java.lang.String readString34 = parcel.readString();
                    java.lang.String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String msisdn = getMsisdn(readString34, readString35);
                    parcel2.writeNoException();
                    parcel2.writeString(msisdn);
                    return true;
                case 20:
                    int readInt10 = parcel.readInt();
                    java.lang.String readString36 = parcel.readString();
                    java.lang.String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String msisdnForSubscriber = getMsisdnForSubscriber(readInt10, readString36, readString37);
                    parcel2.writeNoException();
                    parcel2.writeString(msisdnForSubscriber);
                    return true;
                case 21:
                    java.lang.String readString38 = parcel.readString();
                    java.lang.String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String voiceMailNumber = getVoiceMailNumber(readString38, readString39);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailNumber);
                    return true;
                case 22:
                    int readInt11 = parcel.readInt();
                    java.lang.String readString40 = parcel.readString();
                    java.lang.String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String voiceMailNumberForSubscriber = getVoiceMailNumberForSubscriber(readInt11, readString40, readString41);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailNumberForSubscriber);
                    return true;
                case 23:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    java.lang.String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telephony.ImsiEncryptionInfo carrierInfoForImsiEncryption = getCarrierInfoForImsiEncryption(readInt12, readInt13, readString42);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(carrierInfoForImsiEncryption, 1);
                    return true;
                case 24:
                    int readInt14 = parcel.readInt();
                    java.lang.String readString43 = parcel.readString();
                    android.telephony.ImsiEncryptionInfo imsiEncryptionInfo = (android.telephony.ImsiEncryptionInfo) parcel.readTypedObject(android.telephony.ImsiEncryptionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCarrierInfoForImsiEncryption(readInt14, readString43, imsiEncryptionInfo);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt15 = parcel.readInt();
                    java.lang.String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetCarrierKeysForImsiEncryption(readInt15, readString44);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString45 = parcel.readString();
                    java.lang.String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String voiceMailAlphaTag = getVoiceMailAlphaTag(readString45, readString46);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailAlphaTag);
                    return true;
                case 27:
                    int readInt16 = parcel.readInt();
                    java.lang.String readString47 = parcel.readString();
                    java.lang.String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String voiceMailAlphaTagForSubscriber = getVoiceMailAlphaTagForSubscriber(readInt16, readString47, readString48);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailAlphaTagForSubscriber);
                    return true;
                case 28:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String isimImpi = getIsimImpi(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeString(isimImpi);
                    return true;
                case 29:
                    int readInt18 = parcel.readInt();
                    java.lang.String readString49 = parcel.readString();
                    java.lang.String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String imsPrivateUserIdentity = getImsPrivateUserIdentity(readInt18, readString49, readString50);
                    parcel2.writeNoException();
                    parcel2.writeString(imsPrivateUserIdentity);
                    return true;
                case 30:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String isimDomain = getIsimDomain(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeString(isimDomain);
                    return true;
                case 31:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] isimImpu = getIsimImpu(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(isimImpu);
                    return true;
                case 32:
                    int readInt21 = parcel.readInt();
                    java.lang.String readString51 = parcel.readString();
                    java.lang.String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.net.Uri> imsPublicUserIdentities = getImsPublicUserIdentities(readInt21, readString51, readString52);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(imsPublicUserIdentities, 1);
                    return true;
                case 33:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String isimIst = getIsimIst(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeString(isimIst);
                    return true;
                case 34:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] isimPcscf = getIsimPcscf(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(isimPcscf);
                    return true;
                case 35:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    java.lang.String readString53 = parcel.readString();
                    java.lang.String readString54 = parcel.readString();
                    java.lang.String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String iccSimChallengeResponse = getIccSimChallengeResponse(readInt24, readInt25, readInt26, readString53, readString54, readString55);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSimChallengeResponse);
                    return true;
                case 36:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.net.Uri smscIdentity = getSmscIdentity(readInt27, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(smscIdentity, 1);
                    return true;
                case 37:
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String simServiceTable = getSimServiceTable(readInt29, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeString(simServiceTable);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.IPhoneSubInfo {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getDeviceId(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getDeviceIdWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getNaiForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getDeviceIdForPhone(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getImeiForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getDeviceSvn(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getDeviceSvnUsingSubId(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getSubscriberId(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getSubscriberIdWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getSubscriberIdForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getGroupIdLevel1ForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getIccSerialNumber(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getIccSerialNumberWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getIccSerialNumberForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getLine1Number(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getLine1NumberForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getLine1AlphaTag(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getLine1AlphaTagForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getMsisdn(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getMsisdnForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getVoiceMailNumber(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getVoiceMailNumberForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public android.telephony.ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ImsiEncryptionInfo) obtain2.readTypedObject(android.telephony.ImsiEncryptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public void setCarrierInfoForImsiEncryption(int i, java.lang.String str, android.telephony.ImsiEncryptionInfo imsiEncryptionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(imsiEncryptionInfo, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public void resetCarrierKeysForImsiEncryption(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getVoiceMailAlphaTag(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getVoiceMailAlphaTagForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getIsimImpi(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getImsPrivateUserIdentity(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getIsimDomain(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String[] getIsimImpu(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.util.List<android.net.Uri> getImsPublicUserIdentities(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.net.Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getIsimIst(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String[] getIsimPcscf(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getIccSimChallengeResponse(int i, int i2, int i3, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public android.net.Uri getSmscIdentity(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.Uri) obtain2.readTypedObject(android.net.Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public java.lang.String getSimServiceTable(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneSubInfo.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 36;
        }
    }
}
