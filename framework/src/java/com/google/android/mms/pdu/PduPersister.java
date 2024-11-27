package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class PduPersister {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final java.util.HashMap<java.lang.Integer, java.lang.Integer> CHARSET_COLUMN_INDEX_MAP;
    private static final java.util.HashMap<java.lang.Integer, java.lang.String> CHARSET_COLUMN_NAME_MAP;
    private static final boolean DEBUG = false;
    private static final java.util.HashMap<java.lang.Integer, java.lang.Integer> ENCODED_STRING_COLUMN_INDEX_MAP;
    private static final java.util.HashMap<java.lang.Integer, java.lang.String> ENCODED_STRING_COLUMN_NAME_MAP;
    private static final boolean LOCAL_LOGV = false;
    private static final java.util.HashMap<java.lang.Integer, java.lang.Integer> LONG_COLUMN_INDEX_MAP;
    private static final java.util.HashMap<java.lang.Integer, java.lang.String> LONG_COLUMN_NAME_MAP;
    private static final java.util.HashMap<java.lang.Integer, java.lang.Integer> OCTET_COLUMN_INDEX_MAP;
    private static final java.util.HashMap<java.lang.Integer, java.lang.String> OCTET_COLUMN_NAME_MAP;
    private static final int PART_COLUMN_CHARSET = 1;
    private static final int PART_COLUMN_CONTENT_DISPOSITION = 2;
    private static final int PART_COLUMN_CONTENT_ID = 3;
    private static final int PART_COLUMN_CONTENT_LOCATION = 4;
    private static final int PART_COLUMN_CONTENT_TYPE = 5;
    private static final int PART_COLUMN_FILENAME = 6;
    private static final int PART_COLUMN_ID = 0;
    private static final int PART_COLUMN_NAME = 7;
    private static final int PART_COLUMN_TEXT = 8;
    private static final com.google.android.mms.util.PduCache PDU_CACHE_INSTANCE;
    private static final int PDU_COLUMN_CONTENT_CLASS = 11;
    private static final int PDU_COLUMN_CONTENT_LOCATION = 5;
    private static final int PDU_COLUMN_CONTENT_TYPE = 6;
    private static final int PDU_COLUMN_DATE = 21;
    private static final int PDU_COLUMN_DELIVERY_REPORT = 12;
    private static final int PDU_COLUMN_DELIVERY_TIME = 22;
    private static final int PDU_COLUMN_EXPIRY = 23;
    private static final int PDU_COLUMN_ID = 0;
    private static final int PDU_COLUMN_MESSAGE_BOX = 1;
    private static final int PDU_COLUMN_MESSAGE_CLASS = 7;
    private static final int PDU_COLUMN_MESSAGE_ID = 8;
    private static final int PDU_COLUMN_MESSAGE_SIZE = 24;
    private static final int PDU_COLUMN_MESSAGE_TYPE = 13;
    private static final int PDU_COLUMN_MMS_VERSION = 14;
    private static final int PDU_COLUMN_PRIORITY = 15;
    private static final int PDU_COLUMN_READ_REPORT = 16;
    private static final int PDU_COLUMN_READ_STATUS = 17;
    private static final int PDU_COLUMN_REPORT_ALLOWED = 18;
    private static final int PDU_COLUMN_RESPONSE_TEXT = 9;
    private static final int PDU_COLUMN_RETRIEVE_STATUS = 19;
    private static final int PDU_COLUMN_RETRIEVE_TEXT = 3;
    private static final int PDU_COLUMN_RETRIEVE_TEXT_CHARSET = 26;
    private static final int PDU_COLUMN_STATUS = 20;
    private static final int PDU_COLUMN_SUBJECT = 4;
    private static final int PDU_COLUMN_SUBJECT_CHARSET = 25;
    private static final int PDU_COLUMN_THREAD_ID = 2;
    private static final int PDU_COLUMN_TRANSACTION_ID = 10;
    private static final long PLACEHOLDER_THREAD_ID = Long.MAX_VALUE;
    public static final int PROC_STATUS_COMPLETED = 3;
    public static final int PROC_STATUS_PERMANENTLY_FAILURE = 2;
    public static final int PROC_STATUS_TRANSIENT_FAILURE = 1;
    private static final java.lang.String TAG = "PduPersister";
    public static final java.lang.String TEMPORARY_DRM_OBJECT_URI = "content://mms/9223372036854775807/part";
    private static final java.util.HashMap<java.lang.Integer, java.lang.Integer> TEXT_STRING_COLUMN_INDEX_MAP;
    private static final java.util.HashMap<java.lang.Integer, java.lang.String> TEXT_STRING_COLUMN_NAME_MAP;
    private static com.google.android.mms.pdu.PduPersister sPersister;
    private final android.content.ContentResolver mContentResolver;
    private final android.content.Context mContext;
    private final android.drm.DrmManagerClient mDrmManagerClient;
    private static final int[] ADDRESS_FIELDS = {129, 130, 137, 151};
    private static final java.lang.String[] PDU_PROJECTION = {"_id", android.provider.Telephony.BaseMmsColumns.MESSAGE_BOX, "thread_id", android.provider.Telephony.BaseMmsColumns.RETRIEVE_TEXT, android.provider.Telephony.BaseMmsColumns.SUBJECT, android.provider.Telephony.BaseMmsColumns.CONTENT_LOCATION, android.provider.Telephony.BaseMmsColumns.CONTENT_TYPE, android.provider.Telephony.BaseMmsColumns.MESSAGE_CLASS, android.provider.Telephony.BaseMmsColumns.MESSAGE_ID, android.provider.Telephony.BaseMmsColumns.RESPONSE_TEXT, android.provider.Telephony.BaseMmsColumns.TRANSACTION_ID, android.provider.Telephony.BaseMmsColumns.CONTENT_CLASS, android.provider.Telephony.BaseMmsColumns.DELIVERY_REPORT, android.provider.Telephony.BaseMmsColumns.MESSAGE_TYPE, "v", android.provider.Telephony.BaseMmsColumns.PRIORITY, android.provider.Telephony.BaseMmsColumns.READ_REPORT, android.provider.Telephony.BaseMmsColumns.READ_STATUS, android.provider.Telephony.BaseMmsColumns.REPORT_ALLOWED, android.provider.Telephony.BaseMmsColumns.RETRIEVE_STATUS, android.provider.Telephony.BaseMmsColumns.STATUS, "date", android.provider.Telephony.BaseMmsColumns.DELIVERY_TIME, android.provider.Telephony.BaseMmsColumns.EXPIRY, android.provider.Telephony.BaseMmsColumns.MESSAGE_SIZE, android.provider.Telephony.BaseMmsColumns.SUBJECT_CHARSET, android.provider.Telephony.BaseMmsColumns.RETRIEVE_TEXT_CHARSET};
    private static final java.lang.String[] PART_PROJECTION = {"_id", android.provider.Telephony.Mms.Part.CHARSET, android.provider.Telephony.Mms.Part.CONTENT_DISPOSITION, "cid", android.provider.Telephony.Mms.Part.CONTENT_LOCATION, "ct", android.provider.Telephony.Mms.Part.FILENAME, "name", "text"};
    private static final java.util.HashMap<android.net.Uri, java.lang.Integer> MESSAGE_BOX_MAP = new java.util.HashMap<>();

    static {
        MESSAGE_BOX_MAP.put(android.provider.Telephony.Mms.Inbox.CONTENT_URI, 1);
        MESSAGE_BOX_MAP.put(android.provider.Telephony.Mms.Sent.CONTENT_URI, 2);
        MESSAGE_BOX_MAP.put(android.provider.Telephony.Mms.Draft.CONTENT_URI, 3);
        MESSAGE_BOX_MAP.put(android.provider.Telephony.Mms.Outbox.CONTENT_URI, 4);
        CHARSET_COLUMN_INDEX_MAP = new java.util.HashMap<>();
        CHARSET_COLUMN_INDEX_MAP.put(150, 25);
        CHARSET_COLUMN_INDEX_MAP.put(154, 26);
        CHARSET_COLUMN_NAME_MAP = new java.util.HashMap<>();
        CHARSET_COLUMN_NAME_MAP.put(150, android.provider.Telephony.BaseMmsColumns.SUBJECT_CHARSET);
        CHARSET_COLUMN_NAME_MAP.put(154, android.provider.Telephony.BaseMmsColumns.RETRIEVE_TEXT_CHARSET);
        ENCODED_STRING_COLUMN_INDEX_MAP = new java.util.HashMap<>();
        ENCODED_STRING_COLUMN_INDEX_MAP.put(154, 3);
        ENCODED_STRING_COLUMN_INDEX_MAP.put(150, 4);
        ENCODED_STRING_COLUMN_NAME_MAP = new java.util.HashMap<>();
        ENCODED_STRING_COLUMN_NAME_MAP.put(154, android.provider.Telephony.BaseMmsColumns.RETRIEVE_TEXT);
        ENCODED_STRING_COLUMN_NAME_MAP.put(150, android.provider.Telephony.BaseMmsColumns.SUBJECT);
        TEXT_STRING_COLUMN_INDEX_MAP = new java.util.HashMap<>();
        TEXT_STRING_COLUMN_INDEX_MAP.put(131, 5);
        TEXT_STRING_COLUMN_INDEX_MAP.put(132, 6);
        TEXT_STRING_COLUMN_INDEX_MAP.put(138, 7);
        TEXT_STRING_COLUMN_INDEX_MAP.put(139, 8);
        TEXT_STRING_COLUMN_INDEX_MAP.put(147, 9);
        TEXT_STRING_COLUMN_INDEX_MAP.put(152, 10);
        TEXT_STRING_COLUMN_NAME_MAP = new java.util.HashMap<>();
        TEXT_STRING_COLUMN_NAME_MAP.put(131, android.provider.Telephony.BaseMmsColumns.CONTENT_LOCATION);
        TEXT_STRING_COLUMN_NAME_MAP.put(132, android.provider.Telephony.BaseMmsColumns.CONTENT_TYPE);
        TEXT_STRING_COLUMN_NAME_MAP.put(138, android.provider.Telephony.BaseMmsColumns.MESSAGE_CLASS);
        TEXT_STRING_COLUMN_NAME_MAP.put(139, android.provider.Telephony.BaseMmsColumns.MESSAGE_ID);
        TEXT_STRING_COLUMN_NAME_MAP.put(147, android.provider.Telephony.BaseMmsColumns.RESPONSE_TEXT);
        TEXT_STRING_COLUMN_NAME_MAP.put(152, android.provider.Telephony.BaseMmsColumns.TRANSACTION_ID);
        OCTET_COLUMN_INDEX_MAP = new java.util.HashMap<>();
        OCTET_COLUMN_INDEX_MAP.put(186, 11);
        OCTET_COLUMN_INDEX_MAP.put(134, 12);
        OCTET_COLUMN_INDEX_MAP.put(140, 13);
        OCTET_COLUMN_INDEX_MAP.put(141, 14);
        OCTET_COLUMN_INDEX_MAP.put(143, 15);
        OCTET_COLUMN_INDEX_MAP.put(144, 16);
        OCTET_COLUMN_INDEX_MAP.put(155, 17);
        OCTET_COLUMN_INDEX_MAP.put(145, 18);
        OCTET_COLUMN_INDEX_MAP.put(153, 19);
        OCTET_COLUMN_INDEX_MAP.put(149, 20);
        OCTET_COLUMN_NAME_MAP = new java.util.HashMap<>();
        OCTET_COLUMN_NAME_MAP.put(186, android.provider.Telephony.BaseMmsColumns.CONTENT_CLASS);
        OCTET_COLUMN_NAME_MAP.put(134, android.provider.Telephony.BaseMmsColumns.DELIVERY_REPORT);
        OCTET_COLUMN_NAME_MAP.put(140, android.provider.Telephony.BaseMmsColumns.MESSAGE_TYPE);
        OCTET_COLUMN_NAME_MAP.put(141, "v");
        OCTET_COLUMN_NAME_MAP.put(143, android.provider.Telephony.BaseMmsColumns.PRIORITY);
        OCTET_COLUMN_NAME_MAP.put(144, android.provider.Telephony.BaseMmsColumns.READ_REPORT);
        OCTET_COLUMN_NAME_MAP.put(155, android.provider.Telephony.BaseMmsColumns.READ_STATUS);
        OCTET_COLUMN_NAME_MAP.put(145, android.provider.Telephony.BaseMmsColumns.REPORT_ALLOWED);
        OCTET_COLUMN_NAME_MAP.put(153, android.provider.Telephony.BaseMmsColumns.RETRIEVE_STATUS);
        OCTET_COLUMN_NAME_MAP.put(149, android.provider.Telephony.BaseMmsColumns.STATUS);
        LONG_COLUMN_INDEX_MAP = new java.util.HashMap<>();
        LONG_COLUMN_INDEX_MAP.put(133, 21);
        LONG_COLUMN_INDEX_MAP.put(135, 22);
        LONG_COLUMN_INDEX_MAP.put(136, 23);
        LONG_COLUMN_INDEX_MAP.put(142, 24);
        LONG_COLUMN_NAME_MAP = new java.util.HashMap<>();
        LONG_COLUMN_NAME_MAP.put(133, "date");
        LONG_COLUMN_NAME_MAP.put(135, android.provider.Telephony.BaseMmsColumns.DELIVERY_TIME);
        LONG_COLUMN_NAME_MAP.put(136, android.provider.Telephony.BaseMmsColumns.EXPIRY);
        LONG_COLUMN_NAME_MAP.put(142, android.provider.Telephony.BaseMmsColumns.MESSAGE_SIZE);
        PDU_CACHE_INSTANCE = com.google.android.mms.util.PduCache.getInstance();
    }

    private PduPersister(android.content.Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mDrmManagerClient = new android.drm.DrmManagerClient(context);
    }

    public static com.google.android.mms.pdu.PduPersister getPduPersister(android.content.Context context) {
        if (sPersister == null) {
            sPersister = new com.google.android.mms.pdu.PduPersister(context);
        } else if (!context.equals(sPersister.mContext)) {
            sPersister.release();
            sPersister = new com.google.android.mms.pdu.PduPersister(context);
        }
        return sPersister;
    }

    private void setEncodedStringValueToHeaders(android.database.Cursor cursor, int i, com.google.android.mms.pdu.PduHeaders pduHeaders, int i2) {
        java.lang.String string = cursor.getString(i);
        if (string != null && string.length() > 0) {
            pduHeaders.setEncodedStringValue(new com.google.android.mms.pdu.EncodedStringValue(cursor.getInt(CHARSET_COLUMN_INDEX_MAP.get(java.lang.Integer.valueOf(i2)).intValue()), getBytes(string)), i2);
        }
    }

    private void setTextStringToHeaders(android.database.Cursor cursor, int i, com.google.android.mms.pdu.PduHeaders pduHeaders, int i2) {
        java.lang.String string = cursor.getString(i);
        if (string != null) {
            pduHeaders.setTextString(getBytes(string), i2);
        }
    }

    private void setOctetToHeaders(android.database.Cursor cursor, int i, com.google.android.mms.pdu.PduHeaders pduHeaders, int i2) throws com.google.android.mms.InvalidHeaderValueException {
        if (!cursor.isNull(i)) {
            pduHeaders.setOctet(cursor.getInt(i), i2);
        }
    }

    private void setLongToHeaders(android.database.Cursor cursor, int i, com.google.android.mms.pdu.PduHeaders pduHeaders, int i2) {
        if (!cursor.isNull(i)) {
            pduHeaders.setLongInteger(cursor.getLong(i), i2);
        }
    }

    private java.lang.Integer getIntegerFromPartColumn(android.database.Cursor cursor, int i) {
        if (!cursor.isNull(i)) {
            return java.lang.Integer.valueOf(cursor.getInt(i));
        }
        return null;
    }

    private byte[] getByteArrayFromPartColumn(android.database.Cursor cursor, int i) {
        if (!cursor.isNull(i)) {
            return getBytes(cursor.getString(i));
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x0129 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.google.android.mms.pdu.PduPart[] loadParts(long j) throws com.google.android.mms.MmsException {
        java.io.InputStream inputStream;
        java.lang.Throwable th;
        java.io.IOException e;
        android.database.Cursor query = com.google.android.mms.util.SqliteWrapper.query(this.mContext, this.mContentResolver, android.net.Uri.parse("content://mms/" + j + "/part"), PART_PROJECTION, null, null, null);
        if (query != null) {
            try {
                if (query.getCount() != 0) {
                    com.google.android.mms.pdu.PduPart[] pduPartArr = new com.google.android.mms.pdu.PduPart[query.getCount()];
                    int i = 0;
                    while (query.moveToNext()) {
                        com.google.android.mms.pdu.PduPart pduPart = new com.google.android.mms.pdu.PduPart();
                        java.lang.Integer integerFromPartColumn = getIntegerFromPartColumn(query, 1);
                        if (integerFromPartColumn != null) {
                            pduPart.setCharset(integerFromPartColumn.intValue());
                        }
                        byte[] byteArrayFromPartColumn = getByteArrayFromPartColumn(query, 2);
                        if (byteArrayFromPartColumn != null) {
                            pduPart.setContentDisposition(byteArrayFromPartColumn);
                        }
                        byte[] byteArrayFromPartColumn2 = getByteArrayFromPartColumn(query, 3);
                        if (byteArrayFromPartColumn2 != null) {
                            pduPart.setContentId(byteArrayFromPartColumn2);
                        }
                        byte[] byteArrayFromPartColumn3 = getByteArrayFromPartColumn(query, 4);
                        if (byteArrayFromPartColumn3 != null) {
                            pduPart.setContentLocation(byteArrayFromPartColumn3);
                        }
                        byte[] byteArrayFromPartColumn4 = getByteArrayFromPartColumn(query, 5);
                        if (byteArrayFromPartColumn4 == null) {
                            throw new com.google.android.mms.MmsException("Content-Type must be set.");
                        }
                        pduPart.setContentType(byteArrayFromPartColumn4);
                        byte[] byteArrayFromPartColumn5 = getByteArrayFromPartColumn(query, 6);
                        if (byteArrayFromPartColumn5 != null) {
                            pduPart.setFilename(byteArrayFromPartColumn5);
                        }
                        byte[] byteArrayFromPartColumn6 = getByteArrayFromPartColumn(query, 7);
                        if (byteArrayFromPartColumn6 != null) {
                            pduPart.setName(byteArrayFromPartColumn6);
                        }
                        android.net.Uri parse = android.net.Uri.parse("content://mms/part/" + query.getLong(0));
                        pduPart.setDataUri(parse);
                        java.lang.String isoString = toIsoString(byteArrayFromPartColumn4);
                        if (!com.google.android.mms.ContentType.isImageType(isoString) && !com.google.android.mms.ContentType.isAudioType(isoString) && !com.google.android.mms.ContentType.isVideoType(isoString)) {
                            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                            if ("text/plain".equals(isoString) || com.google.android.mms.ContentType.APP_SMIL.equals(isoString) || "text/html".equals(isoString)) {
                                java.lang.String string = query.getString(8);
                                if (string == null) {
                                    string = "";
                                }
                                byte[] textString = new com.google.android.mms.pdu.EncodedStringValue(string).getTextString();
                                byteArrayOutputStream.write(textString, 0, textString.length);
                            } else {
                                try {
                                    inputStream = this.mContentResolver.openInputStream(parse);
                                } catch (java.io.IOException e2) {
                                    inputStream = null;
                                    e = e2;
                                } catch (java.lang.Throwable th2) {
                                    inputStream = null;
                                    th = th2;
                                    if (inputStream != null) {
                                    }
                                    throw th;
                                }
                                try {
                                    try {
                                        byte[] bArr = new byte[256];
                                        for (int read = inputStream.read(bArr); read >= 0; read = inputStream.read(bArr)) {
                                            byteArrayOutputStream.write(bArr, 0, read);
                                        }
                                        if (inputStream != null) {
                                            try {
                                                inputStream.close();
                                            } catch (java.io.IOException e3) {
                                                android.util.Log.e(TAG, "Failed to close stream", e3);
                                            }
                                        }
                                    } catch (java.lang.Throwable th3) {
                                        th = th3;
                                        if (inputStream != null) {
                                            try {
                                                inputStream.close();
                                            } catch (java.io.IOException e4) {
                                                android.util.Log.e(TAG, "Failed to close stream", e4);
                                            }
                                        }
                                        throw th;
                                    }
                                } catch (java.io.IOException e5) {
                                    e = e5;
                                    android.util.Log.e(TAG, "Failed to load part data", e);
                                    query.close();
                                    throw new com.google.android.mms.MmsException(e);
                                }
                            }
                            pduPart.setData(byteArrayOutputStream.toByteArray());
                        }
                        pduPartArr[i] = pduPart;
                        i++;
                    }
                    return pduPartArr;
                }
            } finally {
                if (query != null) {
                    query.close();
                }
            }
        }
        if (query != null) {
            query.close();
        }
        return null;
    }

    private void loadAddress(long j, com.google.android.mms.pdu.PduHeaders pduHeaders) {
        android.database.Cursor query = com.google.android.mms.util.SqliteWrapper.query(this.mContext, this.mContentResolver, android.net.Uri.parse("content://mms/" + j + "/addr"), new java.lang.String[]{"address", android.provider.Telephony.Mms.Addr.CHARSET, "type"}, null, null, null);
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    java.lang.String string = query.getString(0);
                    if (!android.text.TextUtils.isEmpty(string)) {
                        int i = query.getInt(2);
                        switch (i) {
                            case 129:
                            case 130:
                            case 151:
                                pduHeaders.appendEncodedStringValue(new com.google.android.mms.pdu.EncodedStringValue(query.getInt(1), getBytes(string)), i);
                                break;
                            case 137:
                                pduHeaders.setEncodedStringValue(new com.google.android.mms.pdu.EncodedStringValue(query.getInt(1), getBytes(string)), i);
                                break;
                            default:
                                android.util.Log.e(TAG, "Unknown address type: " + i);
                                break;
                        }
                    }
                } finally {
                    query.close();
                }
            }
        }
    }

    public com.google.android.mms.pdu.GenericPdu load(android.net.Uri uri) throws com.google.android.mms.MmsException {
        com.google.android.mms.pdu.PduPart[] loadParts;
        com.google.android.mms.pdu.GenericPdu sendReq;
        try {
            synchronized (PDU_CACHE_INSTANCE) {
                if (PDU_CACHE_INSTANCE.isUpdating(uri)) {
                    try {
                        PDU_CACHE_INSTANCE.wait();
                    } catch (java.lang.InterruptedException e) {
                        android.util.Log.e(TAG, "load: ", e);
                    }
                    com.google.android.mms.util.PduCacheEntry pduCacheEntry = PDU_CACHE_INSTANCE.get(uri);
                    if (pduCacheEntry != null) {
                        com.google.android.mms.pdu.GenericPdu pdu = pduCacheEntry.getPdu();
                        synchronized (PDU_CACHE_INSTANCE) {
                            PDU_CACHE_INSTANCE.setUpdating(uri, false);
                            PDU_CACHE_INSTANCE.notifyAll();
                        }
                        return pdu;
                    }
                }
                PDU_CACHE_INSTANCE.setUpdating(uri, true);
                android.database.Cursor query = com.google.android.mms.util.SqliteWrapper.query(this.mContext, this.mContentResolver, uri, PDU_PROJECTION, null, null, null);
                com.google.android.mms.pdu.PduHeaders pduHeaders = new com.google.android.mms.pdu.PduHeaders();
                long parseId = android.content.ContentUris.parseId(uri);
                if (query != null) {
                    try {
                        if (query.getCount() == 1 && query.moveToFirst()) {
                            int i = query.getInt(1);
                            long j = query.getLong(2);
                            for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry : ENCODED_STRING_COLUMN_INDEX_MAP.entrySet()) {
                                setEncodedStringValueToHeaders(query, entry.getValue().intValue(), pduHeaders, entry.getKey().intValue());
                            }
                            for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry2 : TEXT_STRING_COLUMN_INDEX_MAP.entrySet()) {
                                setTextStringToHeaders(query, entry2.getValue().intValue(), pduHeaders, entry2.getKey().intValue());
                            }
                            for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry3 : OCTET_COLUMN_INDEX_MAP.entrySet()) {
                                setOctetToHeaders(query, entry3.getValue().intValue(), pduHeaders, entry3.getKey().intValue());
                            }
                            for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry4 : LONG_COLUMN_INDEX_MAP.entrySet()) {
                                setLongToHeaders(query, entry4.getValue().intValue(), pduHeaders, entry4.getKey().intValue());
                            }
                            if (parseId == -1) {
                                throw new com.google.android.mms.MmsException("Error! ID of the message: -1.");
                            }
                            loadAddress(parseId, pduHeaders);
                            int octet = pduHeaders.getOctet(140);
                            com.google.android.mms.pdu.PduBody pduBody = new com.google.android.mms.pdu.PduBody();
                            if ((octet == 132 || octet == 128) && (loadParts = loadParts(parseId)) != null) {
                                for (com.google.android.mms.pdu.PduPart pduPart : loadParts) {
                                    pduBody.addPart(pduPart);
                                }
                            }
                            switch (octet) {
                                case 128:
                                    sendReq = new com.google.android.mms.pdu.SendReq(pduHeaders, pduBody);
                                    break;
                                case 129:
                                case 137:
                                case 138:
                                case 139:
                                case 140:
                                case 141:
                                case 142:
                                case 143:
                                case 144:
                                case 145:
                                case 146:
                                case 147:
                                case 148:
                                case 149:
                                case 150:
                                case 151:
                                    throw new com.google.android.mms.MmsException("Unsupported PDU type: " + java.lang.Integer.toHexString(octet));
                                case 130:
                                    sendReq = new com.google.android.mms.pdu.NotificationInd(pduHeaders);
                                    break;
                                case 131:
                                    sendReq = new com.google.android.mms.pdu.NotifyRespInd(pduHeaders);
                                    break;
                                case 132:
                                    sendReq = new com.google.android.mms.pdu.RetrieveConf(pduHeaders, pduBody);
                                    break;
                                case 133:
                                    sendReq = new com.google.android.mms.pdu.AcknowledgeInd(pduHeaders);
                                    break;
                                case 134:
                                    sendReq = new com.google.android.mms.pdu.DeliveryInd(pduHeaders);
                                    break;
                                case 135:
                                    sendReq = new com.google.android.mms.pdu.ReadRecInd(pduHeaders);
                                    break;
                                case 136:
                                    sendReq = new com.google.android.mms.pdu.ReadOrigInd(pduHeaders);
                                    break;
                                default:
                                    throw new com.google.android.mms.MmsException("Unrecognized PDU type: " + java.lang.Integer.toHexString(octet));
                            }
                            synchronized (PDU_CACHE_INSTANCE) {
                                PDU_CACHE_INSTANCE.put(uri, new com.google.android.mms.util.PduCacheEntry(sendReq, i, j));
                                PDU_CACHE_INSTANCE.setUpdating(uri, false);
                                PDU_CACHE_INSTANCE.notifyAll();
                            }
                            return sendReq;
                        }
                    } finally {
                        if (query != null) {
                            query.close();
                        }
                    }
                }
                throw new com.google.android.mms.MmsException("Bad uri: " + uri);
            }
        } catch (java.lang.Throwable th) {
            synchronized (PDU_CACHE_INSTANCE) {
                PDU_CACHE_INSTANCE.setUpdating(uri, false);
                PDU_CACHE_INSTANCE.notifyAll();
                throw th;
            }
        }
    }

    private void persistAddress(long j, int i, com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr) {
        android.content.ContentValues contentValues = new android.content.ContentValues(3);
        for (com.google.android.mms.pdu.EncodedStringValue encodedStringValue : encodedStringValueArr) {
            contentValues.clear();
            contentValues.put("address", toIsoString(encodedStringValue.getTextString()));
            contentValues.put(android.provider.Telephony.Mms.Addr.CHARSET, java.lang.Integer.valueOf(encodedStringValue.getCharacterSet()));
            contentValues.put("type", java.lang.Integer.valueOf(i));
            com.google.android.mms.util.SqliteWrapper.insert(this.mContext, this.mContentResolver, android.net.Uri.parse("content://mms/" + j + "/addr"), contentValues);
        }
    }

    private static java.lang.String getPartContentType(com.google.android.mms.pdu.PduPart pduPart) {
        if (pduPart.getContentType() == null) {
            return null;
        }
        return toIsoString(pduPart.getContentType());
    }

    public android.net.Uri persistPart(com.google.android.mms.pdu.PduPart pduPart, long j, java.util.HashMap<android.net.Uri, java.io.InputStream> hashMap) throws com.google.android.mms.MmsException {
        android.net.Uri parse = android.net.Uri.parse("content://mms/" + j + "/part");
        android.content.ContentValues contentValues = new android.content.ContentValues(8);
        int charset = pduPart.getCharset();
        if (charset != 0) {
            contentValues.put(android.provider.Telephony.Mms.Part.CHARSET, java.lang.Integer.valueOf(charset));
        }
        java.lang.String partContentType = getPartContentType(pduPart);
        if (partContentType != null) {
            if (com.google.android.mms.ContentType.IMAGE_JPG.equals(partContentType)) {
                partContentType = com.google.android.mms.ContentType.IMAGE_JPEG;
            }
            contentValues.put("ct", partContentType);
            if (com.google.android.mms.ContentType.APP_SMIL.equals(partContentType)) {
                contentValues.put("seq", (java.lang.Integer) (-1));
            }
            if (pduPart.getFilename() != null) {
                contentValues.put(android.provider.Telephony.Mms.Part.FILENAME, new java.lang.String(pduPart.getFilename()));
            }
            if (pduPart.getName() != null) {
                contentValues.put("name", new java.lang.String(pduPart.getName()));
            }
            if (pduPart.getContentDisposition() != null) {
                contentValues.put(android.provider.Telephony.Mms.Part.CONTENT_DISPOSITION, toIsoString(pduPart.getContentDisposition()));
            }
            if (pduPart.getContentId() != null) {
                contentValues.put("cid", toIsoString(pduPart.getContentId()));
            }
            if (pduPart.getContentLocation() != null) {
                contentValues.put(android.provider.Telephony.Mms.Part.CONTENT_LOCATION, toIsoString(pduPart.getContentLocation()));
            }
            android.net.Uri insert = com.google.android.mms.util.SqliteWrapper.insert(this.mContext, this.mContentResolver, parse, contentValues);
            if (insert == null) {
                throw new com.google.android.mms.MmsException("Failed to persist part, return null.");
            }
            persistData(pduPart, insert, partContentType, hashMap);
            pduPart.setDataUri(insert);
            return insert;
        }
        throw new com.google.android.mms.MmsException("MIME type of the part must be set.");
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00fe, code lost:
    
        throw new com.google.android.mms.MmsException("Error converting drm data.");
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01fa  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01de A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v11, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v34 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.InputStream, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.OutputStream, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void persistData(com.google.android.mms.pdu.PduPart pduPart, android.net.Uri uri, java.lang.String str, java.util.HashMap<android.net.Uri, java.io.InputStream> hashMap) throws com.google.android.mms.MmsException {
        java.lang.Throwable th;
        ?? r2;
        ?? r3;
        com.google.android.mms.util.DrmConvertSession drmConvertSession;
        java.io.InputStream inputStream;
        java.io.OutputStream outputStream;
        android.net.Uri uri2 = uri;
        java.lang.String str2 = str;
        try {
            try {
                byte[] data = pduPart.getData();
                if (!"text/plain".equals(str2) && !com.google.android.mms.ContentType.APP_SMIL.equals(str2) && !"text/html".equals(str2)) {
                    boolean isDrmConvertNeeded = com.google.android.mms.util.DownloadDrmHelper.isDrmConvertNeeded(str);
                    try {
                        if (isDrmConvertNeeded) {
                            if (uri2 != null) {
                                try {
                                    android.os.ParcelFileDescriptor openFileDescriptor = this.mContentResolver.openFileDescriptor(uri2, "r");
                                    try {
                                        if (openFileDescriptor.getStatSize() > 0) {
                                            if (openFileDescriptor != null) {
                                                openFileDescriptor.close();
                                                return;
                                            }
                                            return;
                                        } else if (openFileDescriptor != null) {
                                            openFileDescriptor.close();
                                        }
                                    } finally {
                                    }
                                } catch (java.lang.Exception e) {
                                    android.util.Log.e(TAG, "Can't get file info for: " + pduPart.getDataUri(), e);
                                }
                            }
                            drmConvertSession = com.google.android.mms.util.DrmConvertSession.open(this.mContext, str2);
                            if (drmConvertSession == null) {
                                throw new com.google.android.mms.MmsException("Mimetype " + str2 + " can not be converted.");
                            }
                        } else {
                            drmConvertSession = null;
                        }
                        outputStream = this.mContentResolver.openOutputStream(uri2);
                    } catch (java.io.FileNotFoundException e2) {
                        e = e2;
                    } catch (java.io.IOException e3) {
                        e = e3;
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        r2 = 0;
                        r3 = 0;
                    }
                    try {
                        if (data == null) {
                            android.net.Uri dataUri = pduPart.getDataUri();
                            if (dataUri != null && !dataUri.equals(uri2)) {
                                inputStream = (hashMap == null || !hashMap.containsKey(dataUri)) ? null : hashMap.get(dataUri);
                                if (inputStream == null) {
                                    try {
                                        inputStream = this.mContentResolver.openInputStream(dataUri);
                                    } catch (java.io.FileNotFoundException e4) {
                                        e = e4;
                                        android.util.Log.e(TAG, "Failed to open Input/Output stream.", e);
                                        throw new com.google.android.mms.MmsException(e);
                                    } catch (java.io.IOException e5) {
                                        e = e5;
                                        android.util.Log.e(TAG, "Failed to read/write data.", e);
                                        throw new com.google.android.mms.MmsException(e);
                                    }
                                }
                                byte[] bArr = new byte[8192];
                                while (true) {
                                    int read = inputStream.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    if (isDrmConvertNeeded) {
                                        byte[] convert = drmConvertSession.convert(bArr, read);
                                        if (convert == null) {
                                            break;
                                        } else {
                                            outputStream.write(convert, 0, convert.length);
                                        }
                                    } else {
                                        outputStream.write(bArr, 0, read);
                                    }
                                }
                            }
                            android.util.Log.w(TAG, "Can't find data for this part.");
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (java.io.IOException e6) {
                                    android.util.Log.e(TAG, "IOException while closing: " + outputStream, e6);
                                }
                            }
                            if (drmConvertSession != null) {
                                drmConvertSession.close(null);
                                com.google.android.mms.util.SqliteWrapper.update(this.mContext, this.mContentResolver, android.net.Uri.parse("content://mms/resetFilePerm/" + new java.io.File((java.lang.String) null).getName()), new android.content.ContentValues(0), null, null);
                                return;
                            }
                            return;
                        }
                        if (isDrmConvertNeeded) {
                            byte[] convert2 = drmConvertSession.convert(data, data.length);
                            if (convert2 == null) {
                                throw new com.google.android.mms.MmsException("Error converting drm data.");
                            }
                            outputStream.write(convert2, 0, convert2.length);
                        } else {
                            outputStream.write(data);
                        }
                        inputStream = null;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (java.io.IOException e7) {
                                android.util.Log.e(TAG, "IOException while closing: " + outputStream, e7);
                            }
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (java.io.IOException e8) {
                                android.util.Log.e(TAG, "IOException while closing: " + inputStream, e8);
                            }
                        }
                        if (drmConvertSession == null) {
                            drmConvertSession.close(null);
                            com.google.android.mms.util.SqliteWrapper.update(this.mContext, this.mContentResolver, android.net.Uri.parse("content://mms/resetFilePerm/" + new java.io.File((java.lang.String) null).getName()), new android.content.ContentValues(0), null, null);
                            return;
                        }
                        return;
                    } catch (java.io.FileNotFoundException e9) {
                        e = e9;
                    } catch (java.io.IOException e10) {
                        e = e10;
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        r2 = 0;
                        r3 = outputStream;
                        if (r3 != 0) {
                            try {
                                r3.close();
                            } catch (java.io.IOException e11) {
                                android.util.Log.e(TAG, "IOException while closing: " + r3, e11);
                            }
                        }
                        if (r2 != 0) {
                            try {
                                r2.close();
                            } catch (java.io.IOException e12) {
                                android.util.Log.e(TAG, "IOException while closing: " + r2, e12);
                            }
                        }
                        if (drmConvertSession == null) {
                            throw th;
                        }
                        drmConvertSession.close(null);
                        com.google.android.mms.util.SqliteWrapper.update(this.mContext, this.mContentResolver, android.net.Uri.parse("content://mms/resetFilePerm/" + new java.io.File((java.lang.String) null).getName()), new android.content.ContentValues(0), null, null);
                        throw th;
                    }
                }
                android.content.ContentValues contentValues = new android.content.ContentValues();
                if (data == null) {
                    data = new java.lang.String("").getBytes("utf-8");
                }
                contentValues.put("text", new com.google.android.mms.pdu.EncodedStringValue(data).getString());
                if (this.mContentResolver.update(uri2, contentValues, null, null) != 1) {
                    throw new com.google.android.mms.MmsException("unable to update " + uri.toString());
                }
                inputStream = null;
                outputStream = null;
                drmConvertSession = null;
                if (outputStream != null) {
                }
                if (inputStream != null) {
                }
                if (drmConvertSession == null) {
                }
            } catch (java.io.FileNotFoundException e13) {
                e = e13;
            } catch (java.io.IOException e14) {
                e = e14;
            } catch (java.lang.Throwable th4) {
                th = th4;
                r2 = 0;
                r3 = 0;
                drmConvertSession = null;
            }
        } catch (java.lang.Throwable th5) {
            th = th5;
            r2 = uri2;
            r3 = str2;
        }
    }

    private void updateAddress(long j, int i, com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr) {
        com.google.android.mms.util.SqliteWrapper.delete(this.mContext, this.mContentResolver, android.net.Uri.parse("content://mms/" + j + "/addr"), "type=" + i, null);
        persistAddress(j, i, encodedStringValueArr);
    }

    public void updateHeaders(android.net.Uri uri, com.google.android.mms.pdu.SendReq sendReq) {
        com.google.android.mms.pdu.EncodedStringValue[] encodedStringValues;
        synchronized (PDU_CACHE_INSTANCE) {
            if (PDU_CACHE_INSTANCE.isUpdating(uri)) {
                try {
                    PDU_CACHE_INSTANCE.wait();
                } catch (java.lang.InterruptedException e) {
                    android.util.Log.e(TAG, "updateHeaders: ", e);
                }
            }
        }
        PDU_CACHE_INSTANCE.purge(uri);
        android.content.ContentValues contentValues = new android.content.ContentValues(10);
        byte[] contentType = sendReq.getContentType();
        if (contentType != null) {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.CONTENT_TYPE, toIsoString(contentType));
        }
        long date = sendReq.getDate();
        if (date != -1) {
            contentValues.put("date", java.lang.Long.valueOf(date));
        }
        int deliveryReport = sendReq.getDeliveryReport();
        if (deliveryReport != 0) {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.DELIVERY_REPORT, java.lang.Integer.valueOf(deliveryReport));
        }
        long expiry = sendReq.getExpiry();
        if (expiry != -1) {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.EXPIRY, java.lang.Long.valueOf(expiry));
        }
        byte[] messageClass = sendReq.getMessageClass();
        if (messageClass != null) {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.MESSAGE_CLASS, toIsoString(messageClass));
        }
        int priority = sendReq.getPriority();
        if (priority != 0) {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.PRIORITY, java.lang.Integer.valueOf(priority));
        }
        int readReport = sendReq.getReadReport();
        if (readReport != 0) {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.READ_REPORT, java.lang.Integer.valueOf(readReport));
        }
        byte[] transactionId = sendReq.getTransactionId();
        if (transactionId != null) {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.TRANSACTION_ID, toIsoString(transactionId));
        }
        com.google.android.mms.pdu.EncodedStringValue subject = sendReq.getSubject();
        if (subject != null) {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.SUBJECT, toIsoString(subject.getTextString()));
            contentValues.put(android.provider.Telephony.BaseMmsColumns.SUBJECT_CHARSET, java.lang.Integer.valueOf(subject.getCharacterSet()));
        } else {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.SUBJECT, "");
        }
        long messageSize = sendReq.getMessageSize();
        if (messageSize > 0) {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.MESSAGE_SIZE, java.lang.Long.valueOf(messageSize));
        }
        com.google.android.mms.pdu.PduHeaders pduHeaders = sendReq.getPduHeaders();
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i : ADDRESS_FIELDS) {
            if (i == 137) {
                com.google.android.mms.pdu.EncodedStringValue encodedStringValue = pduHeaders.getEncodedStringValue(i);
                if (encodedStringValue == null) {
                    encodedStringValues = null;
                } else {
                    encodedStringValues = new com.google.android.mms.pdu.EncodedStringValue[]{encodedStringValue};
                }
            } else {
                encodedStringValues = pduHeaders.getEncodedStringValues(i);
            }
            if (encodedStringValues != null) {
                updateAddress(android.content.ContentUris.parseId(uri), i, encodedStringValues);
                if (i == 151) {
                    for (com.google.android.mms.pdu.EncodedStringValue encodedStringValue2 : encodedStringValues) {
                        if (encodedStringValue2 != null) {
                            hashSet.add(encodedStringValue2.getString());
                        }
                    }
                }
            }
        }
        if (!hashSet.isEmpty()) {
            contentValues.put("thread_id", java.lang.Long.valueOf(android.provider.Telephony.Threads.getOrCreateThreadId(this.mContext, hashSet)));
        }
        com.google.android.mms.util.SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
    }

    private void updatePart(android.net.Uri uri, com.google.android.mms.pdu.PduPart pduPart, java.util.HashMap<android.net.Uri, java.io.InputStream> hashMap) throws com.google.android.mms.MmsException {
        android.content.ContentValues contentValues = new android.content.ContentValues(7);
        int charset = pduPart.getCharset();
        if (charset != 0) {
            contentValues.put(android.provider.Telephony.Mms.Part.CHARSET, java.lang.Integer.valueOf(charset));
        }
        if (pduPart.getContentType() != null) {
            java.lang.String isoString = toIsoString(pduPart.getContentType());
            contentValues.put("ct", isoString);
            if (pduPart.getFilename() != null) {
                contentValues.put(android.provider.Telephony.Mms.Part.FILENAME, new java.lang.String(pduPart.getFilename()));
            }
            if (pduPart.getName() != null) {
                contentValues.put("name", new java.lang.String(pduPart.getName()));
            }
            if (pduPart.getContentDisposition() != null) {
                contentValues.put(android.provider.Telephony.Mms.Part.CONTENT_DISPOSITION, toIsoString(pduPart.getContentDisposition()));
            }
            if (pduPart.getContentId() != null) {
                contentValues.put("cid", toIsoString(pduPart.getContentId()));
            }
            if (pduPart.getContentLocation() != null) {
                contentValues.put(android.provider.Telephony.Mms.Part.CONTENT_LOCATION, toIsoString(pduPart.getContentLocation()));
            }
            com.google.android.mms.util.SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
            if (pduPart.getData() != null || !uri.equals(pduPart.getDataUri())) {
                persistData(pduPart, uri, isoString, hashMap);
                return;
            }
            return;
        }
        throw new com.google.android.mms.MmsException("MIME type of the part must be set.");
    }

    public void updateParts(android.net.Uri uri, com.google.android.mms.pdu.PduBody pduBody, java.util.HashMap<android.net.Uri, java.io.InputStream> hashMap) throws com.google.android.mms.MmsException {
        try {
            synchronized (PDU_CACHE_INSTANCE) {
                if (PDU_CACHE_INSTANCE.isUpdating(uri)) {
                    try {
                        PDU_CACHE_INSTANCE.wait();
                    } catch (java.lang.InterruptedException e) {
                        android.util.Log.e(TAG, "updateParts: ", e);
                    }
                    com.google.android.mms.util.PduCacheEntry pduCacheEntry = PDU_CACHE_INSTANCE.get(uri);
                    if (pduCacheEntry != null) {
                        ((com.google.android.mms.pdu.MultimediaMessagePdu) pduCacheEntry.getPdu()).setBody(pduBody);
                    }
                }
                PDU_CACHE_INSTANCE.setUpdating(uri, true);
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.HashMap hashMap2 = new java.util.HashMap();
            int partsNum = pduBody.getPartsNum();
            java.lang.StringBuilder append = new java.lang.StringBuilder().append('(');
            for (int i = 0; i < partsNum; i++) {
                com.google.android.mms.pdu.PduPart part = pduBody.getPart(i);
                android.net.Uri dataUri = part.getDataUri();
                if (dataUri != null && !android.text.TextUtils.isEmpty(dataUri.getAuthority()) && dataUri.getAuthority().startsWith("mms")) {
                    hashMap2.put(dataUri, part);
                    if (append.length() > 1) {
                        append.append(" AND ");
                    }
                    append.append("_id");
                    append.append("!=");
                    android.database.DatabaseUtils.appendEscapedSQLString(append, dataUri.getLastPathSegment());
                }
                arrayList.add(part);
            }
            append.append(')');
            long parseId = android.content.ContentUris.parseId(uri);
            com.google.android.mms.util.SqliteWrapper.delete(this.mContext, this.mContentResolver, android.net.Uri.parse(android.provider.Telephony.Mms.CONTENT_URI + "/" + parseId + "/part"), append.length() > 2 ? append.toString() : null, null);
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                persistPart((com.google.android.mms.pdu.PduPart) it.next(), parseId, hashMap);
            }
            for (java.util.Map.Entry entry : hashMap2.entrySet()) {
                updatePart((android.net.Uri) entry.getKey(), (com.google.android.mms.pdu.PduPart) entry.getValue(), hashMap);
            }
            synchronized (PDU_CACHE_INSTANCE) {
                PDU_CACHE_INSTANCE.setUpdating(uri, false);
                PDU_CACHE_INSTANCE.notifyAll();
            }
        } catch (java.lang.Throwable th) {
            synchronized (PDU_CACHE_INSTANCE) {
                PDU_CACHE_INSTANCE.setUpdating(uri, false);
                PDU_CACHE_INSTANCE.notifyAll();
                throw th;
            }
        }
    }

    public android.net.Uri persist(com.google.android.mms.pdu.GenericPdu genericPdu, android.net.Uri uri, boolean z, boolean z2, java.util.HashMap<android.net.Uri, java.io.InputStream> hashMap) throws com.google.android.mms.MmsException {
        long j;
        int i;
        long j2;
        int i2;
        int i3;
        long j3;
        android.net.Uri insert;
        com.google.android.mms.pdu.PduBody body;
        com.google.android.mms.pdu.EncodedStringValue[] encodedStringValues;
        if (uri == null) {
            throw new com.google.android.mms.MmsException("Uri may not be null.");
        }
        try {
            j = android.content.ContentUris.parseId(uri);
        } catch (java.lang.NumberFormatException e) {
            j = -1;
        }
        boolean z3 = j != -1;
        if (!z3 && MESSAGE_BOX_MAP.get(uri) == null) {
            throw new com.google.android.mms.MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        synchronized (PDU_CACHE_INSTANCE) {
            if (PDU_CACHE_INSTANCE.isUpdating(uri)) {
                try {
                    PDU_CACHE_INSTANCE.wait();
                } catch (java.lang.InterruptedException e2) {
                    android.util.Log.e(TAG, "persist1: ", e2);
                }
            }
        }
        PDU_CACHE_INSTANCE.purge(uri);
        com.google.android.mms.pdu.PduHeaders pduHeaders = genericPdu.getPduHeaders();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry : ENCODED_STRING_COLUMN_NAME_MAP.entrySet()) {
            int intValue = entry.getKey().intValue();
            com.google.android.mms.pdu.EncodedStringValue encodedStringValue = pduHeaders.getEncodedStringValue(intValue);
            if (encodedStringValue != null) {
                java.lang.String str = CHARSET_COLUMN_NAME_MAP.get(java.lang.Integer.valueOf(intValue));
                contentValues.put(entry.getValue(), toIsoString(encodedStringValue.getTextString()));
                contentValues.put(str, java.lang.Integer.valueOf(encodedStringValue.getCharacterSet()));
            }
        }
        for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry2 : TEXT_STRING_COLUMN_NAME_MAP.entrySet()) {
            byte[] textString = pduHeaders.getTextString(entry2.getKey().intValue());
            if (textString != null) {
                contentValues.put(entry2.getValue(), toIsoString(textString));
            }
        }
        for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry3 : OCTET_COLUMN_NAME_MAP.entrySet()) {
            int octet = pduHeaders.getOctet(entry3.getKey().intValue());
            if (octet != 0) {
                contentValues.put(entry3.getValue(), java.lang.Integer.valueOf(octet));
            }
        }
        for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry4 : LONG_COLUMN_NAME_MAP.entrySet()) {
            long longInteger = pduHeaders.getLongInteger(entry4.getKey().intValue());
            if (longInteger != -1) {
                contentValues.put(entry4.getValue(), java.lang.Long.valueOf(longInteger));
            }
        }
        java.util.HashMap<java.lang.Integer, com.google.android.mms.pdu.EncodedStringValue[]> hashMap2 = new java.util.HashMap<>(ADDRESS_FIELDS.length);
        for (int i4 : ADDRESS_FIELDS) {
            if (i4 == 137) {
                com.google.android.mms.pdu.EncodedStringValue encodedStringValue2 = pduHeaders.getEncodedStringValue(i4);
                if (encodedStringValue2 == null) {
                    encodedStringValues = null;
                } else {
                    encodedStringValues = new com.google.android.mms.pdu.EncodedStringValue[]{encodedStringValue2};
                }
            } else {
                encodedStringValues = pduHeaders.getEncodedStringValues(i4);
            }
            hashMap2.put(java.lang.Integer.valueOf(i4), encodedStringValues);
        }
        java.util.HashSet<java.lang.String> hashSet = new java.util.HashSet<>();
        int messageType = genericPdu.getMessageType();
        if (messageType == 130 || messageType == 132 || messageType == 128) {
            switch (messageType) {
                case 128:
                    i = 0;
                    loadRecipients(151, hashSet, hashMap2, false);
                    break;
                case 129:
                case 131:
                default:
                    i = 0;
                    break;
                case 130:
                case 132:
                    i = 0;
                    loadRecipients(137, hashSet, hashMap2, false);
                    if (z2) {
                        loadRecipients(151, hashSet, hashMap2, true);
                        loadRecipients(130, hashSet, hashMap2, true);
                        break;
                    }
                    break;
            }
            if (z && !hashSet.isEmpty()) {
                j2 = android.provider.Telephony.Threads.getOrCreateThreadId(this.mContext, hashSet);
            } else {
                j2 = 0;
            }
            contentValues.put("thread_id", java.lang.Long.valueOf(j2));
        } else {
            i = 0;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if ((genericPdu instanceof com.google.android.mms.pdu.MultimediaMessagePdu) && (body = ((com.google.android.mms.pdu.MultimediaMessagePdu) genericPdu).getBody()) != null) {
            int partsNum = body.getPartsNum();
            if (partsNum <= 2) {
                i3 = 1;
            } else {
                i3 = i;
            }
            int i5 = i;
            i2 = i5;
            while (i5 < partsNum) {
                com.google.android.mms.pdu.PduPart part = body.getPart(i5);
                i2 += part.getDataLength();
                persistPart(part, currentTimeMillis, hashMap);
                java.lang.String partContentType = getPartContentType(part);
                if (partContentType != null && !com.google.android.mms.ContentType.APP_SMIL.equals(partContentType) && !"text/plain".equals(partContentType)) {
                    i3 = 0;
                }
                i5++;
            }
        } else {
            i2 = 0;
            i3 = 1;
        }
        contentValues.put(android.provider.Telephony.BaseMmsColumns.TEXT_ONLY, java.lang.Integer.valueOf(i3));
        if (contentValues.getAsInteger(android.provider.Telephony.BaseMmsColumns.MESSAGE_SIZE) == null) {
            contentValues.put(android.provider.Telephony.BaseMmsColumns.MESSAGE_SIZE, java.lang.Integer.valueOf(i2));
        }
        if (z3) {
            j3 = currentTimeMillis;
            com.google.android.mms.util.SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
            insert = uri;
        } else {
            j3 = currentTimeMillis;
            insert = com.google.android.mms.util.SqliteWrapper.insert(this.mContext, this.mContentResolver, uri, contentValues);
            if (insert == null) {
                throw new com.google.android.mms.MmsException("persist() failed: return null.");
            }
            j = android.content.ContentUris.parseId(insert);
        }
        android.content.ContentValues contentValues2 = new android.content.ContentValues(1);
        contentValues2.put(android.provider.Telephony.Mms.Part.MSG_ID, java.lang.Long.valueOf(j));
        com.google.android.mms.util.SqliteWrapper.update(this.mContext, this.mContentResolver, android.net.Uri.parse("content://mms/" + j3 + "/part"), contentValues2, null, null);
        if (!z3) {
            insert = android.net.Uri.parse(uri + "/" + j);
        }
        for (int i6 : ADDRESS_FIELDS) {
            com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr = hashMap2.get(java.lang.Integer.valueOf(i6));
            if (encodedStringValueArr != null) {
                persistAddress(j, i6, encodedStringValueArr);
            }
        }
        return insert;
    }

    private void loadRecipients(int i, java.util.HashSet<java.lang.String> hashSet, java.util.HashMap<java.lang.Integer, com.google.android.mms.pdu.EncodedStringValue[]> hashMap, boolean z) {
        com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr = hashMap.get(java.lang.Integer.valueOf(i));
        if (encodedStringValueArr == null) {
            return;
        }
        if (z && encodedStringValueArr.length == 1) {
            return;
        }
        android.telephony.SubscriptionManager from = android.telephony.SubscriptionManager.from(this.mContext);
        java.util.HashSet hashSet2 = new java.util.HashSet();
        if (z) {
            java.util.Iterator<android.telephony.SubscriptionInfo> it = from.getActiveSubscriptionInfoList().iterator();
            while (it.hasNext()) {
                java.lang.String line1Number = ((android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class)).createForSubscriptionId(it.next().getSubscriptionId()).getLine1Number();
                if (line1Number != null) {
                    hashSet2.add(line1Number);
                }
            }
        }
        for (com.google.android.mms.pdu.EncodedStringValue encodedStringValue : encodedStringValueArr) {
            if (encodedStringValue != null) {
                java.lang.String string = encodedStringValue.getString();
                if (z) {
                    java.util.Iterator it2 = hashSet2.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (!android.telephony.PhoneNumberUtils.compare(string, (java.lang.String) it2.next()) && !hashSet.contains(string)) {
                            hashSet.add(string);
                            break;
                        }
                    }
                } else if (!hashSet.contains(string)) {
                    hashSet.add(string);
                }
            }
        }
    }

    public android.net.Uri move(android.net.Uri uri, android.net.Uri uri2) throws com.google.android.mms.MmsException {
        long parseId = android.content.ContentUris.parseId(uri);
        if (parseId == -1) {
            throw new com.google.android.mms.MmsException("Error! ID of the message: -1.");
        }
        java.lang.Integer num = MESSAGE_BOX_MAP.get(uri2);
        if (num == null) {
            throw new com.google.android.mms.MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        android.content.ContentValues contentValues = new android.content.ContentValues(1);
        contentValues.put(android.provider.Telephony.BaseMmsColumns.MESSAGE_BOX, num);
        com.google.android.mms.util.SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
        return android.content.ContentUris.withAppendedId(uri2, parseId);
    }

    public static java.lang.String toIsoString(byte[] bArr) {
        try {
            return new java.lang.String(bArr, com.google.android.mms.pdu.CharacterSets.MIMENAME_ISO_8859_1);
        } catch (java.io.UnsupportedEncodingException e) {
            android.util.Log.e(TAG, "ISO_8859_1 must be supported!", e);
            return "";
        }
    }

    public static byte[] getBytes(java.lang.String str) {
        try {
            return str.getBytes(com.google.android.mms.pdu.CharacterSets.MIMENAME_ISO_8859_1);
        } catch (java.io.UnsupportedEncodingException e) {
            android.util.Log.e(TAG, "ISO_8859_1 must be supported!", e);
            return new byte[0];
        }
    }

    public void release() {
        com.google.android.mms.util.SqliteWrapper.delete(this.mContext, this.mContentResolver, android.net.Uri.parse(TEMPORARY_DRM_OBJECT_URI), null, null);
        this.mDrmManagerClient.release();
    }

    public android.database.Cursor getPendingMessages(long j) {
        android.net.Uri.Builder buildUpon = android.provider.Telephony.MmsSms.PendingMessages.CONTENT_URI.buildUpon();
        buildUpon.appendQueryParameter("protocol", "mms");
        return com.google.android.mms.util.SqliteWrapper.query(this.mContext, this.mContentResolver, buildUpon.build(), null, "err_type < ? AND due_time <= ?", new java.lang.String[]{java.lang.String.valueOf(10), java.lang.String.valueOf(j)}, android.provider.Telephony.MmsSms.PendingMessages.DUE_TIME);
    }
}
