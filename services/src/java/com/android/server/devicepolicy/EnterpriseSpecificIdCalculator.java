package com.android.server.devicepolicy;

/* loaded from: classes.dex */
class EnterpriseSpecificIdCalculator {
    private static final int ESID_LENGTH = 16;
    private static final int PADDED_ENTERPRISE_ID_LENGTH = 64;
    private static final int PADDED_HW_ID_LENGTH = 16;
    private static final int PADDED_PROFILE_OWNER_LENGTH = 64;
    private final java.lang.String mImei;
    private final java.lang.String mMacAddress;
    private final java.lang.String mMeid;
    private final java.lang.String mSerialNumber;

    @com.android.internal.annotations.VisibleForTesting
    EnterpriseSpecificIdCalculator(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        this.mImei = str;
        this.mMeid = str2;
        this.mSerialNumber = str3;
        this.mMacAddress = str4;
    }

    EnterpriseSpecificIdCalculator(android.content.Context context) {
        java.lang.String str;
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class);
        com.android.internal.util.Preconditions.checkState(telephonyManager != null, "Unable to access telephony service");
        this.mImei = telephonyManager.getImei(0);
        try {
            str = telephonyManager.getMeid(0);
        } catch (java.lang.UnsupportedOperationException e) {
            str = null;
        }
        this.mMeid = str;
        this.mSerialNumber = android.os.Build.getSerial();
        android.net.wifi.WifiManager wifiManager = (android.net.wifi.WifiManager) context.getSystemService(android.net.wifi.WifiManager.class);
        com.android.internal.util.Preconditions.checkState(wifiManager != null, "Unable to access WiFi service");
        java.lang.String[] factoryMacAddresses = wifiManager.getFactoryMacAddresses();
        if (factoryMacAddresses == null || factoryMacAddresses.length == 0) {
            this.mMacAddress = "";
        } else {
            this.mMacAddress = factoryMacAddresses[0];
        }
    }

    private static java.lang.String getPaddedTruncatedString(java.lang.String str, int i) {
        return java.lang.String.format("%" + i + "s", str).substring(0, i);
    }

    private static java.lang.String getPaddedHardwareIdentifier(java.lang.String str) {
        if (str == null) {
            str = "";
        }
        return getPaddedTruncatedString(str, 16);
    }

    java.lang.String getPaddedImei() {
        return getPaddedHardwareIdentifier(this.mImei);
    }

    java.lang.String getPaddedMeid() {
        return getPaddedHardwareIdentifier(this.mMeid);
    }

    java.lang.String getPaddedSerialNumber() {
        return getPaddedHardwareIdentifier(this.mSerialNumber);
    }

    java.lang.String getPaddedProfileOwnerName(java.lang.String str) {
        return getPaddedTruncatedString(str, 64);
    }

    java.lang.String getPaddedEnterpriseId(java.lang.String str) {
        return getPaddedTruncatedString(str, 64);
    }

    public java.lang.String calculateEnterpriseId(java.lang.String str, java.lang.String str2) {
        boolean z = true;
        com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(str), "owner package must be specified.");
        if (str2 != null && str2.isEmpty()) {
            z = false;
        }
        com.android.internal.util.Preconditions.checkArgument(z, "enterprise ID must either be null or non-empty.");
        if (str2 == null) {
            str2 = "";
        }
        byte[] bytes = getPaddedSerialNumber().getBytes();
        byte[] bytes2 = getPaddedImei().getBytes();
        byte[] bytes3 = getPaddedMeid().getBytes();
        byte[] bytes4 = this.mMacAddress.getBytes();
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(bytes.length + bytes2.length + bytes3.length + bytes4.length);
        allocate.put(bytes);
        allocate.put(bytes2);
        allocate.put(bytes3);
        allocate.put(bytes4);
        byte[] bytes5 = getPaddedProfileOwnerName(str).getBytes();
        byte[] bytes6 = getPaddedEnterpriseId(str2).getBytes();
        java.nio.ByteBuffer allocate2 = java.nio.ByteBuffer.allocate(bytes5.length + bytes6.length);
        allocate2.put(bytes5);
        allocate2.put(bytes6);
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(android.security.identity.Util.computeHkdf("HMACSHA256", allocate.array(), (byte[]) null, allocate2.array(), 16));
        return new android.content.pm.VerifierDeviceIdentity(wrap.getLong()).toString() + new android.content.pm.VerifierDeviceIdentity(wrap.getLong()).toString();
    }
}
