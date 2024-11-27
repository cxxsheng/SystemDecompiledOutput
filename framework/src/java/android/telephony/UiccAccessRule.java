package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class UiccAccessRule implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.UiccAccessRule> CREATOR = new android.os.Parcelable.Creator<android.telephony.UiccAccessRule>() { // from class: android.telephony.UiccAccessRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UiccAccessRule createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.UiccAccessRule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UiccAccessRule[] newArray(int i) {
            return new android.telephony.UiccAccessRule[i];
        }
    };
    private static final java.lang.String DELIMITER_CERTIFICATE_HASH_PACKAGE_NAMES = ":";
    private static final java.lang.String DELIMITER_INDIVIDUAL_PACKAGE_NAMES = ",";
    private static final int ENCODING_VERSION = 1;
    private static final java.lang.String TAG = "UiccAccessRule";
    private final long mAccessType;
    private final byte[] mCertificateHash;
    private final java.lang.String mPackageName;

    public static byte[] encodeRules(android.telephony.UiccAccessRule[] uiccAccessRuleArr) {
        if (uiccAccessRuleArr == null) {
            return null;
        }
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(1);
            dataOutputStream.writeInt(uiccAccessRuleArr.length);
            for (android.telephony.UiccAccessRule uiccAccessRule : uiccAccessRuleArr) {
                dataOutputStream.writeInt(uiccAccessRule.mCertificateHash.length);
                dataOutputStream.write(uiccAccessRule.mCertificateHash);
                if (uiccAccessRule.mPackageName != null) {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeUTF(uiccAccessRule.mPackageName);
                } else {
                    dataOutputStream.writeBoolean(false);
                }
                dataOutputStream.writeLong(uiccAccessRule.mAccessType);
            }
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("ByteArrayOutputStream should never lead to an IOException", e);
        }
    }

    public static android.telephony.UiccAccessRule[] decodeRulesFromCarrierConfig(java.lang.String[] strArr) {
        if (strArr == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : strArr) {
            java.lang.String[] split = str.split(":");
            byte[] hexStringToBytes = com.android.internal.telephony.uicc.IccUtils.hexStringToBytes(split[0]);
            if (split.length == 1) {
                arrayList.add(new android.telephony.UiccAccessRule(hexStringToBytes, null, 0L));
            } else {
                for (java.lang.String str2 : split[1].split(",")) {
                    arrayList.add(new android.telephony.UiccAccessRule(hexStringToBytes, str2, 0L));
                }
            }
        }
        return (android.telephony.UiccAccessRule[]) arrayList.toArray(new android.telephony.UiccAccessRule[arrayList.size()]);
    }

    public static android.telephony.UiccAccessRule[] decodeRules(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
            try {
                dataInputStream.readInt();
                int readInt = dataInputStream.readInt();
                android.telephony.UiccAccessRule[] uiccAccessRuleArr = new android.telephony.UiccAccessRule[readInt];
                for (int i = 0; i < readInt; i++) {
                    byte[] bArr2 = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(bArr2);
                    uiccAccessRuleArr[i] = new android.telephony.UiccAccessRule(bArr2, dataInputStream.readBoolean() ? dataInputStream.readUTF() : null, dataInputStream.readLong());
                }
                dataInputStream.close();
                dataInputStream.close();
                return uiccAccessRuleArr;
            } finally {
            }
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("ByteArrayInputStream should never lead to an IOException", e);
        }
    }

    public UiccAccessRule(byte[] bArr, java.lang.String str, long j) {
        this.mCertificateHash = bArr;
        this.mPackageName = str;
        this.mAccessType = j;
    }

    UiccAccessRule(android.os.Parcel parcel) {
        this.mCertificateHash = parcel.createByteArray();
        this.mPackageName = parcel.readString();
        this.mAccessType = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByteArray(this.mCertificateHash);
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mAccessType);
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getCertificateHexString() {
        return com.android.internal.telephony.uicc.IccUtils.bytesToHexString(this.mCertificateHash);
    }

    public int getCarrierPrivilegeStatus(android.content.pm.PackageInfo packageInfo) {
        java.util.List<android.content.pm.Signature> signatures = getSignatures(packageInfo);
        if (signatures.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Must use GET_SIGNING_CERTIFICATES when looking up package info");
        }
        java.util.Iterator<android.content.pm.Signature> it = signatures.iterator();
        while (it.hasNext()) {
            int carrierPrivilegeStatus = getCarrierPrivilegeStatus(it.next(), packageInfo.packageName);
            if (carrierPrivilegeStatus != 0) {
                return carrierPrivilegeStatus;
            }
        }
        return 0;
    }

    public int getCarrierPrivilegeStatus(android.content.pm.Signature signature, java.lang.String str) {
        if (matches(getCertHash(signature, "SHA-256"), str)) {
            return 1;
        }
        return (this.mCertificateHash.length == 20 && matches(getCertHash(signature, "SHA-1"), str)) ? 1 : 0;
    }

    public boolean matches(java.lang.String str, java.lang.String str2) {
        return matches(com.android.internal.telephony.uicc.IccUtils.hexStringToBytes(str), str2);
    }

    private boolean matches(byte[] bArr, java.lang.String str) {
        return bArr != null && java.util.Arrays.equals(this.mCertificateHash, bArr) && (android.text.TextUtils.isEmpty(this.mPackageName) || this.mPackageName.equals(str));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.UiccAccessRule uiccAccessRule = (android.telephony.UiccAccessRule) obj;
        if (java.util.Arrays.equals(this.mCertificateHash, uiccAccessRule.mCertificateHash) && java.util.Objects.equals(this.mPackageName, uiccAccessRule.mPackageName) && this.mAccessType == uiccAccessRule.mAccessType) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Arrays.hashCode(this.mCertificateHash) + 31) * 31) + java.util.Objects.hashCode(this.mPackageName)) * 31) + java.util.Objects.hashCode(java.lang.Long.valueOf(this.mAccessType));
    }

    public java.lang.String toString() {
        return "cert: " + com.android.internal.telephony.uicc.IccUtils.bytesToHexString(this.mCertificateHash) + " pkg: " + this.mPackageName + " access: " + this.mAccessType;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static java.util.List<android.content.pm.Signature> getSignatures(android.content.pm.PackageInfo packageInfo) {
        android.content.pm.Signature[] signatureArr = packageInfo.signatures;
        android.content.pm.SigningInfo signingInfo = packageInfo.signingInfo;
        if (signingInfo != null) {
            signatureArr = signingInfo.getSigningCertificateHistory();
            if (signingInfo.hasMultipleSigners()) {
                signatureArr = signingInfo.getApkContentsSigners();
            }
        }
        return signatureArr == null ? java.util.Collections.EMPTY_LIST : java.util.Arrays.asList(signatureArr);
    }

    public static byte[] getCertHash(android.content.pm.Signature signature, java.lang.String str) {
        try {
            return java.security.MessageDigest.getInstance(str).digest(signature.toByteArray());
        } catch (java.security.NoSuchAlgorithmException e) {
            com.android.telephony.Rlog.e(TAG, "NoSuchAlgorithmException: " + e);
            return null;
        }
    }
}
