package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class ISmsImplBase extends com.android.internal.telephony.ISms.Stub {
    @Override // com.android.internal.telephony.ISms
    public java.util.List<com.android.internal.telephony.SmsRawData> getAllMessagesFromIccEfForSubscriber(int i, java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean updateMessageOnIccEfForSubscriber(int i, java.lang.String str, int i2, int i3, byte[] bArr) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean copyMessageToIccEfForSubscriber(int i, java.lang.String str, int i2, byte[] bArr, byte[] bArr2) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendDataForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i2, byte[] bArr, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendTextForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, boolean z, long j) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendTextForSubscriberWithOptions(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, boolean z, int i2, boolean z2, int i3) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void injectSmsPduForSubscriber(int i, byte[] bArr, java.lang.String str, android.app.PendingIntent pendingIntent) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendMultipartTextForSubscriber(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3, boolean z, long j) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendMultipartTextForSubscriberWithOptions(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3, boolean z, int i2, boolean z2, int i3) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean enableCellBroadcastForSubscriber(int i, int i2, int i3) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean disableCellBroadcastForSubscriber(int i, int i2, int i3) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean enableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean disableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public int getPremiumSmsPermission(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public int getPremiumSmsPermissionForSubscriber(int i, java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void setPremiumSmsPermission(java.lang.String str, int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void setPremiumSmsPermissionForSubscriber(int i, java.lang.String str, int i2) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean isImsSmsSupportedForSubscriber(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean isSmsSimPickActivityNeeded(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public int getPreferredSmsSubscription() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public java.lang.String getImsSmsFormatForSubscriber(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean isSMSPromptEnabled() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendStoredText(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, java.lang.String str3, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendStoredMultipartText(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, java.lang.String str3, java.util.List<android.app.PendingIntent> list, java.util.List<android.app.PendingIntent> list2) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public android.os.Bundle getCarrierConfigValuesForSubscriber(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public java.lang.String createAppSpecificSmsToken(int i, java.lang.String str, android.app.PendingIntent pendingIntent) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public java.lang.String createAppSpecificSmsTokenWithPackageInfo(int i, java.lang.String str, java.lang.String str2, android.app.PendingIntent pendingIntent) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void setStorageMonitorMemoryStatusOverride(int i, boolean z) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void clearStorageMonitorMemoryStatusOverride(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public int checkSmsShortCodeDestination(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public java.lang.String getSmscAddressFromIccEfForSubscriber(int i, java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean setSmscAddressOnIccEfForSubscriber(java.lang.String str, int i, java.lang.String str2) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public int getSmsCapacityOnIccForSubscriber(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean resetAllCellBroadcastRanges(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public long getWapMessageSize(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }
}
