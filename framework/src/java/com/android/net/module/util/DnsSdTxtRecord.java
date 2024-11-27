package com.android.net.module.util;

/* loaded from: classes5.dex */
public class DnsSdTxtRecord implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.net.module.util.DnsSdTxtRecord> CREATOR = new android.os.Parcelable.Creator<com.android.net.module.util.DnsSdTxtRecord>() { // from class: com.android.net.module.util.DnsSdTxtRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.net.module.util.DnsSdTxtRecord createFromParcel(android.os.Parcel parcel) {
            com.android.net.module.util.DnsSdTxtRecord dnsSdTxtRecord = new com.android.net.module.util.DnsSdTxtRecord();
            parcel.readByteArray(dnsSdTxtRecord.mData);
            return dnsSdTxtRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.net.module.util.DnsSdTxtRecord[] newArray(int i) {
            return new com.android.net.module.util.DnsSdTxtRecord[i];
        }
    };
    private static final byte mSeparator = 61;
    private byte[] mData;

    public DnsSdTxtRecord() {
        this.mData = new byte[0];
    }

    public DnsSdTxtRecord(byte[] bArr) {
        this.mData = (byte[]) bArr.clone();
    }

    public DnsSdTxtRecord(com.android.net.module.util.DnsSdTxtRecord dnsSdTxtRecord) {
        if (dnsSdTxtRecord != null && dnsSdTxtRecord.mData != null) {
            this.mData = (byte[]) dnsSdTxtRecord.mData.clone();
        }
    }

    public void set(java.lang.String str, java.lang.String str2) {
        byte[] bArr;
        int i;
        if (str2 != null) {
            bArr = str2.getBytes();
            i = bArr.length;
        } else {
            bArr = null;
            i = 0;
        }
        try {
            byte[] bytes = str.getBytes("US-ASCII");
            for (byte b : bytes) {
                if (b == 61) {
                    throw new java.lang.IllegalArgumentException("= is not a valid character in key");
                }
            }
            if (bytes.length + i >= 255) {
                throw new java.lang.IllegalArgumentException("Key and Value length cannot exceed 255 bytes");
            }
            int remove = remove(str);
            if (remove == -1) {
                remove = keyCount();
            }
            insert(bytes, bArr, remove);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.IllegalArgumentException("key should be US-ASCII");
        }
    }

    public java.lang.String get(java.lang.String str) {
        byte[] value = getValue(str);
        if (value != null) {
            return new java.lang.String(value);
        }
        return null;
    }

    public int remove(java.lang.String str) {
        int i = 0;
        int i2 = 0;
        while (i < this.mData.length) {
            int i3 = this.mData[i];
            if (str.length() <= i3 && ((str.length() == i3 || this.mData[str.length() + i + 1] == 61) && str.compareToIgnoreCase(new java.lang.String(this.mData, i + 1, str.length())) == 0)) {
                byte[] bArr = this.mData;
                this.mData = new byte[(bArr.length - i3) - 1];
                java.lang.System.arraycopy(bArr, 0, this.mData, 0, i);
                java.lang.System.arraycopy(bArr, i + i3 + 1, this.mData, i, ((bArr.length - i) - i3) - 1);
                return i2;
            }
            i += (i3 + 1) & 255;
            i2++;
        }
        return -1;
    }

    public int keyCount() {
        int i = 0;
        int i2 = 0;
        while (i < this.mData.length) {
            i += (this.mData[i] + 1) & 255;
            i2++;
        }
        return i2;
    }

    public boolean contains(java.lang.String str) {
        int i = 0;
        while (true) {
            java.lang.String key = getKey(i);
            if (key == null) {
                return false;
            }
            if (str.compareToIgnoreCase(key) == 0) {
                return true;
            }
            i++;
        }
    }

    public int size() {
        return this.mData.length;
    }

    public byte[] getRawData() {
        return (byte[]) this.mData.clone();
    }

    private void insert(byte[] bArr, byte[] bArr2, int i) {
        byte[] bArr3 = this.mData;
        int length = bArr2 != null ? bArr2.length : 0;
        int i2 = 0;
        for (int i3 = 0; i3 < i && i2 < this.mData.length; i3++) {
            i2 += (this.mData[i2] + 1) & 255;
        }
        int length2 = bArr.length + length + (bArr2 != null ? 1 : 0);
        int length3 = bArr3.length + length2 + 1;
        this.mData = new byte[length3];
        java.lang.System.arraycopy(bArr3, 0, this.mData, 0, i2);
        int length4 = bArr3.length - i2;
        java.lang.System.arraycopy(bArr3, i2, this.mData, length3 - length4, length4);
        this.mData[i2] = (byte) length2;
        int i4 = i2 + 1;
        java.lang.System.arraycopy(bArr, 0, this.mData, i4, bArr.length);
        if (bArr2 != null) {
            this.mData[i4 + bArr.length] = mSeparator;
            java.lang.System.arraycopy(bArr2, 0, this.mData, i2 + bArr.length + 2, length);
        }
    }

    private java.lang.String getKey(int i) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < i && i3 < this.mData.length; i4++) {
            i3 += this.mData[i3] + 1;
        }
        if (i3 < this.mData.length) {
            byte b = this.mData[i3];
            while (i2 < b && this.mData[i3 + i2 + 1] != 61) {
                i2++;
            }
            return new java.lang.String(this.mData, i3 + 1, i2);
        }
        return null;
    }

    private byte[] getValue(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i && i2 < this.mData.length; i3++) {
            i2 += this.mData[i2] + 1;
        }
        if (i2 < this.mData.length) {
            int i4 = this.mData[i2];
            for (int i5 = 0; i5 < i4; i5++) {
                int i6 = i2 + i5;
                if (this.mData[i6 + 1] == 61) {
                    int i7 = (i4 - i5) - 1;
                    byte[] bArr = new byte[i7];
                    java.lang.System.arraycopy(this.mData, i6 + 2, bArr, 0, i7);
                    return bArr;
                }
            }
        }
        return null;
    }

    private java.lang.String getValueAsString(int i) {
        byte[] value = getValue(i);
        if (value != null) {
            return new java.lang.String(value);
        }
        return null;
    }

    private byte[] getValue(java.lang.String str) {
        int i = 0;
        while (true) {
            java.lang.String key = getKey(i);
            if (key != null) {
                if (str.compareToIgnoreCase(key) != 0) {
                    i++;
                } else {
                    return getValue(i);
                }
            } else {
                return null;
            }
        }
    }

    public java.lang.String toString() {
        java.lang.String str = null;
        int i = 0;
        while (true) {
            java.lang.String key = getKey(i);
            if (key == null) {
                break;
            }
            java.lang.String str2 = "{" + key;
            java.lang.String valueAsString = getValueAsString(i);
            java.lang.String str3 = valueAsString != null ? str2 + "=" + valueAsString + "}" : str2 + "}";
            if (str == null) {
                str = str3;
            } else {
                str = str + ", " + str3;
            }
            i++;
        }
        return str != null ? str : "";
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.net.module.util.DnsSdTxtRecord)) {
            return false;
        }
        return java.util.Arrays.equals(((com.android.net.module.util.DnsSdTxtRecord) obj).mData, this.mData);
    }

    public int hashCode() {
        return java.util.Arrays.hashCode(this.mData);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByteArray(this.mData);
    }
}
