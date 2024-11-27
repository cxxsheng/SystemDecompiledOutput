package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class EncodedStringValue implements java.lang.Cloneable {
    private static final boolean DEBUG = false;
    private static final boolean LOCAL_LOGV = false;
    private static final java.lang.String TAG = "EncodedStringValue";
    private int mCharacterSet;
    private byte[] mData;

    public EncodedStringValue(int i, byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.NullPointerException("EncodedStringValue: Text-string is null.");
        }
        this.mCharacterSet = i;
        this.mData = new byte[bArr.length];
        java.lang.System.arraycopy(bArr, 0, this.mData, 0, bArr.length);
    }

    public EncodedStringValue(byte[] bArr) {
        this(106, bArr);
    }

    public EncodedStringValue(java.lang.String str) {
        try {
            this.mData = str.getBytes("utf-8");
            this.mCharacterSet = 106;
        } catch (java.io.UnsupportedEncodingException e) {
            android.util.Log.e(TAG, "Default encoding must be supported.", e);
        }
    }

    public int getCharacterSet() {
        return this.mCharacterSet;
    }

    public void setCharacterSet(int i) {
        this.mCharacterSet = i;
    }

    public byte[] getTextString() {
        byte[] bArr = new byte[this.mData.length];
        java.lang.System.arraycopy(this.mData, 0, bArr, 0, this.mData.length);
        return bArr;
    }

    public void setTextString(byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.NullPointerException("EncodedStringValue: Text-string is null.");
        }
        this.mData = new byte[bArr.length];
        java.lang.System.arraycopy(bArr, 0, this.mData, 0, bArr.length);
    }

    public java.lang.String getString() {
        if (this.mCharacterSet == 0) {
            return new java.lang.String(this.mData);
        }
        try {
            return new java.lang.String(this.mData, com.google.android.mms.pdu.CharacterSets.getMimeName(this.mCharacterSet));
        } catch (java.io.UnsupportedEncodingException e) {
            try {
                return new java.lang.String(this.mData, com.google.android.mms.pdu.CharacterSets.MIMENAME_ISO_8859_1);
            } catch (java.io.UnsupportedEncodingException e2) {
                return new java.lang.String(this.mData);
            }
        }
    }

    public void appendTextString(byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.NullPointerException("Text-string is null.");
        }
        if (this.mData == null) {
            this.mData = new byte[bArr.length];
            java.lang.System.arraycopy(bArr, 0, this.mData, 0, bArr.length);
            return;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(this.mData);
            byteArrayOutputStream.write(bArr);
            this.mData = byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            throw new java.lang.NullPointerException("appendTextString: failed when write a new Text-string");
        }
    }

    public java.lang.Object clone() throws java.lang.CloneNotSupportedException {
        int length = this.mData.length;
        byte[] bArr = new byte[length];
        java.lang.System.arraycopy(this.mData, 0, bArr, 0, length);
        try {
            return new com.google.android.mms.pdu.EncodedStringValue(this.mCharacterSet, bArr);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "failed to clone an EncodedStringValue: " + this);
            e.printStackTrace();
            throw new java.lang.CloneNotSupportedException(e.getMessage());
        }
    }

    public com.google.android.mms.pdu.EncodedStringValue[] split(java.lang.String str) {
        java.lang.String[] split = getString().split(str);
        int length = split.length;
        com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr = new com.google.android.mms.pdu.EncodedStringValue[length];
        for (int i = 0; i < length; i++) {
            try {
                encodedStringValueArr[i] = new com.google.android.mms.pdu.EncodedStringValue(this.mCharacterSet, split[i].getBytes());
            } catch (java.lang.NullPointerException e) {
                return null;
            }
        }
        return encodedStringValueArr;
    }

    public static com.google.android.mms.pdu.EncodedStringValue[] extract(java.lang.String str) {
        java.lang.String[] split = str.split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() > 0) {
                arrayList.add(new com.google.android.mms.pdu.EncodedStringValue(split[i]));
            }
        }
        int size = arrayList.size();
        if (size > 0) {
            return (com.google.android.mms.pdu.EncodedStringValue[]) arrayList.toArray(new com.google.android.mms.pdu.EncodedStringValue[size]);
        }
        return null;
    }

    public static java.lang.String concat(com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int length = encodedStringValueArr.length - 1;
        for (int i = 0; i <= length; i++) {
            sb.append(encodedStringValueArr[i].getString());
            if (i < length) {
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
            }
        }
        return sb.toString();
    }

    public static com.google.android.mms.pdu.EncodedStringValue copy(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        if (encodedStringValue == null) {
            return null;
        }
        return new com.google.android.mms.pdu.EncodedStringValue(encodedStringValue.mCharacterSet, encodedStringValue.mData);
    }

    public static com.google.android.mms.pdu.EncodedStringValue[] encodeStrings(java.lang.String[] strArr) {
        int length = strArr.length;
        if (length > 0) {
            com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr = new com.google.android.mms.pdu.EncodedStringValue[length];
            for (int i = 0; i < length; i++) {
                encodedStringValueArr[i] = new com.google.android.mms.pdu.EncodedStringValue(strArr[i]);
            }
            return encodedStringValueArr;
        }
        return null;
    }
}
