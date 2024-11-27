package android.telephony.ims;

/* loaded from: classes3.dex */
public final class RcsConfig {
    private static final java.lang.String ATTRIBUTE_NAME = "name";
    private static final java.lang.String ATTRIBUTE_TYPE = "type";
    private static final java.lang.String ATTRIBUTE_VALUE = "value";
    private static final boolean DBG = android.os.Build.IS_ENG;
    private static final java.lang.String LOG_TAG = "RcsConfig";
    private static final java.lang.String PARM_SINGLE_REGISTRATION = "rcsVolteSingleRegistration";
    private static final java.lang.String TAG_CHARACTERISTIC = "characteristic";
    private static final java.lang.String TAG_PARM = "parm";
    private android.telephony.ims.RcsConfig.Characteristic mCurrent;
    private final byte[] mData;
    private final android.telephony.ims.RcsConfig.Characteristic mRoot;

    public static class Characteristic {
        private final android.telephony.ims.RcsConfig.Characteristic mParent;
        private final java.util.Map<java.lang.String, java.lang.String> mParms;
        private final java.util.Set<android.telephony.ims.RcsConfig.Characteristic> mSubs;
        private java.lang.String mType;

        private Characteristic(java.lang.String str, android.telephony.ims.RcsConfig.Characteristic characteristic) {
            this.mParms = new android.util.ArrayMap();
            this.mSubs = new android.util.ArraySet();
            this.mType = str;
            this.mParent = characteristic;
        }

        private java.lang.String getType() {
            return this.mType;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.util.Map<java.lang.String, java.lang.String> getParms() {
            return this.mParms;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.util.Set<android.telephony.ims.RcsConfig.Characteristic> getSubs() {
            return this.mSubs;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.telephony.ims.RcsConfig.Characteristic getParent() {
            return this.mParent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.telephony.ims.RcsConfig.Characteristic getSubByType(java.lang.String str) {
            if (android.text.TextUtils.equals(this.mType, str)) {
                return this;
            }
            java.util.Iterator<android.telephony.ims.RcsConfig.Characteristic> it = this.mSubs.iterator();
            android.telephony.ims.RcsConfig.Characteristic characteristic = null;
            while (it.hasNext() && (characteristic = it.next().getSubByType(str)) == null) {
            }
            return characteristic;
        }

        private boolean hasSubByType(java.lang.String str) {
            return getSubByType(str) != null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getParmValue(java.lang.String str) {
            java.lang.String str2 = this.mParms.get(str);
            if (str2 == null) {
                java.util.Iterator<android.telephony.ims.RcsConfig.Characteristic> it = this.mSubs.iterator();
                while (it.hasNext() && (str2 = it.next().getParmValue(str)) == null) {
                }
            }
            return str2;
        }

        boolean hasParm(java.lang.String str) {
            if (this.mParms.containsKey(str)) {
                return true;
            }
            java.util.Iterator<android.telephony.ims.RcsConfig.Characteristic> it = this.mSubs.iterator();
            while (it.hasNext()) {
                if (it.next().hasParm(str)) {
                    return true;
                }
            }
            return false;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + this.mType + "]: ");
            if (android.telephony.ims.RcsConfig.DBG) {
                sb.append(this.mParms);
            }
            for (android.telephony.ims.RcsConfig.Characteristic characteristic : this.mSubs) {
                sb.append("\n");
                sb.append(characteristic.toString().replace("\n", "\n\t"));
            }
            return sb.toString();
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.telephony.ims.RcsConfig.Characteristic)) {
                return false;
            }
            android.telephony.ims.RcsConfig.Characteristic characteristic = (android.telephony.ims.RcsConfig.Characteristic) obj;
            return android.text.TextUtils.equals(this.mType, characteristic.mType) && this.mParms.equals(characteristic.mParms) && this.mSubs.equals(characteristic.mSubs);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mType, this.mParms, this.mSubs);
        }
    }

    public RcsConfig(byte[] bArr) throws java.lang.IllegalArgumentException {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        if (bArr == null || bArr.length == 0) {
            throw new java.lang.IllegalArgumentException("Empty data");
        }
        byte b = 0;
        this.mRoot = new android.telephony.ims.RcsConfig.Characteristic(null, 0 == true ? 1 : 0);
        this.mCurrent = this.mRoot;
        this.mData = bArr;
        android.telephony.ims.RcsConfig.Characteristic characteristic = this.mRoot;
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
        try {
            try {
                org.xmlpull.v1.XmlPullParserFactory newInstance = org.xmlpull.v1.XmlPullParserFactory.newInstance();
                newInstance.setNamespaceAware(true);
                org.xmlpull.v1.XmlPullParser newPullParser = newInstance.newPullParser();
                newPullParser.setInput(byteArrayInputStream, null);
                for (int eventType = newPullParser.getEventType(); eventType != 1 && characteristic != null; eventType = newPullParser.next()) {
                    if (eventType == 2) {
                        java.lang.String lowerCase = newPullParser.getName().trim().toLowerCase(java.util.Locale.ROOT);
                        int i = 0;
                        if (TAG_CHARACTERISTIC.equals(lowerCase)) {
                            int attributeCount = newPullParser.getAttributeCount();
                            if (attributeCount > 0) {
                                while (i < attributeCount) {
                                    java.lang.String lowerCase2 = newPullParser.getAttributeName(i).trim().toLowerCase(java.util.Locale.ROOT);
                                    if (!"type".equals(lowerCase2)) {
                                        i++;
                                    } else {
                                        str3 = newPullParser.getAttributeValue(newPullParser.getAttributeNamespace(i), lowerCase2).trim().toLowerCase(java.util.Locale.ROOT);
                                        break;
                                    }
                                }
                            }
                            str3 = null;
                            android.telephony.ims.RcsConfig.Characteristic characteristic2 = new android.telephony.ims.RcsConfig.Characteristic(str3, characteristic);
                            characteristic.getSubs().add(characteristic2);
                            characteristic = characteristic2;
                        } else if (TAG_PARM.equals(lowerCase)) {
                            int attributeCount2 = newPullParser.getAttributeCount();
                            if (attributeCount2 <= 1) {
                                str = null;
                                str2 = null;
                            } else {
                                str = null;
                                str2 = null;
                                while (i < attributeCount2) {
                                    java.lang.String lowerCase3 = newPullParser.getAttributeName(i).trim().toLowerCase(java.util.Locale.ROOT);
                                    if ("name".equals(lowerCase3)) {
                                        str = newPullParser.getAttributeValue(newPullParser.getAttributeNamespace(i), lowerCase3).trim().toLowerCase(java.util.Locale.ROOT);
                                    } else if ("value".equals(lowerCase3)) {
                                        str2 = newPullParser.getAttributeValue(newPullParser.getAttributeNamespace(i), lowerCase3).trim();
                                    }
                                    i++;
                                }
                            }
                            if (str != null && str2 != null) {
                                characteristic.getParms().put(str, str2);
                            }
                        }
                    } else if (eventType == 3 && TAG_CHARACTERISTIC.equals(newPullParser.getName().trim().toLowerCase(java.util.Locale.ROOT))) {
                        characteristic = characteristic.getParent();
                    }
                }
                try {
                    byteArrayInputStream.close();
                } catch (java.io.IOException e) {
                    loge("error to close input stream, skip.");
                }
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
                throw new java.lang.IllegalArgumentException(e2);
            }
        } catch (java.lang.Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (java.io.IOException e3) {
                loge("error to close input stream, skip.");
            }
            throw th;
        }
    }

    public java.lang.String getString(java.lang.String str, java.lang.String str2) {
        java.lang.String parmValue = this.mCurrent.getParmValue(str.trim().toLowerCase(java.util.Locale.ROOT));
        return parmValue != null ? parmValue : str2;
    }

    public int getInteger(java.lang.String str, int i) {
        try {
            return java.lang.Integer.parseInt(getString(str, null));
        } catch (java.lang.NumberFormatException e) {
            logd("error to getInteger for " + str + " due to " + e);
            return i;
        }
    }

    public boolean getBoolean(java.lang.String str, boolean z) {
        java.lang.String string = getString(str, null);
        return string != null ? java.lang.Boolean.parseBoolean(string) : z;
    }

    public boolean hasConfig(java.lang.String str) {
        return this.mCurrent.hasParm(str.trim().toLowerCase(java.util.Locale.ROOT));
    }

    public android.telephony.ims.RcsConfig.Characteristic getCharacteristic(java.lang.String str) {
        return this.mCurrent.getSubByType(str.trim().toLowerCase(java.util.Locale.ROOT));
    }

    public boolean hasCharacteristic(java.lang.String str) {
        return this.mCurrent.getSubByType(str.trim().toLowerCase(java.util.Locale.ROOT)) != null;
    }

    public void setCurrentCharacteristic(android.telephony.ims.RcsConfig.Characteristic characteristic) {
        if (characteristic != null) {
            this.mCurrent = characteristic;
        }
    }

    public boolean moveToParent() {
        if (this.mCurrent.getParent() == null) {
            return false;
        }
        this.mCurrent = this.mCurrent.getParent();
        return true;
    }

    public void moveToRoot() {
        this.mCurrent = this.mRoot;
    }

    public android.telephony.ims.RcsConfig.Characteristic getRoot() {
        return this.mRoot;
    }

    public android.telephony.ims.RcsConfig.Characteristic getCurrentCharacteristic() {
        return this.mCurrent;
    }

    public boolean isRcsVolteSingleRegistrationSupported(boolean z) {
        int integer = getInteger(PARM_SINGLE_REGISTRATION, 1);
        if (z) {
            if (integer == 1) {
                return true;
            }
        } else if (integer > 0) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[RCS Config]");
        if (DBG) {
            sb.append("=== Root ===\n");
            sb.append(this.mRoot);
            sb.append("=== Current ===\n");
            sb.append(this.mCurrent);
        }
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.ims.RcsConfig)) {
            return false;
        }
        android.telephony.ims.RcsConfig rcsConfig = (android.telephony.ims.RcsConfig) obj;
        return this.mRoot.equals(rcsConfig.mRoot) && this.mCurrent.equals(rcsConfig.mCurrent);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mRoot, this.mCurrent);
    }

    public static byte[] compressGzip(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        byte[] bArr2 = null;
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(bArr.length);
            java.util.zip.GZIPOutputStream gZIPOutputStream = new java.util.zip.GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bArr2;
        } catch (java.io.IOException e) {
            loge("Error to compressGzip due to " + e);
            return bArr2;
        }
    }

    public static byte[] decompressGzip(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        byte[] bArr2 = null;
        try {
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            java.util.zip.GZIPInputStream gZIPInputStream = new java.util.zip.GZIPInputStream(byteArrayInputStream);
            byte[] bArr3 = new byte[1024];
            for (int read = gZIPInputStream.read(bArr3); read >= 0; read = gZIPInputStream.read(bArr3)) {
                byteArrayOutputStream.write(bArr3, 0, read);
            }
            gZIPInputStream.close();
            byteArrayInputStream.close();
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bArr2;
        } catch (java.io.IOException e) {
            loge("Error to decompressGzip due to " + e);
            return bArr2;
        }
    }

    public static void updateConfigForSub(android.content.Context context, int i, byte[] bArr, boolean z) {
        if (!z) {
            bArr = compressGzip(bArr);
        }
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put(android.provider.Telephony.SimInfo.COLUMN_RCS_CONFIG, bArr);
        context.getContentResolver().update(android.provider.Telephony.SimInfo.CONTENT_URI, contentValues, "_id=" + i, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x005b, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0059, code lost:
    
        if (r6 == null) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x0065, code lost:
    
        if (r6 != null) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0068, code lost:
    
        if (r8 == false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x006f, code lost:
    
        return decompressGzip(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:?, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] loadRcsConfigForSub(android.content.Context context, int i, boolean z) {
        android.database.Cursor query = context.getContentResolver().query(android.provider.Telephony.SimInfo.CONTENT_URI, null, "_id=" + i, null, null);
        byte[] bArr = null;
        try {
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        bArr = query.getBlob(query.getColumnIndexOrThrow(android.provider.Telephony.SimInfo.COLUMN_RCS_CONFIG));
                    }
                } catch (java.lang.Exception e) {
                    loge("error to load rcs config for sub:" + i + " due to " + e);
                }
            }
        } catch (java.lang.Throwable th) {
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    private static void logd(java.lang.String str) {
        com.android.telephony.Rlog.d(LOG_TAG, str);
    }

    private static void loge(java.lang.String str) {
        com.android.telephony.Rlog.e(LOG_TAG, str);
    }
}
