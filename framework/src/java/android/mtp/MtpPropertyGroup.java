package android.mtp;

/* loaded from: classes2.dex */
class MtpPropertyGroup {
    private static final java.lang.String PATH_WHERE = "_data=?";
    private static final java.lang.String TAG = android.mtp.MtpPropertyGroup.class.getSimpleName();
    private java.lang.String[] mColumns;
    private final android.mtp.MtpPropertyGroup.Property[] mProperties;

    private native java.lang.String format_date_time(long j);

    private class Property {
        int code;
        int column;
        int type;

        Property(int i, int i2, int i3) {
            this.code = i;
            this.type = i2;
            this.column = i3;
        }
    }

    public MtpPropertyGroup(int[] iArr) {
        int length = iArr.length;
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>(length);
        arrayList.add("_id");
        this.mProperties = new android.mtp.MtpPropertyGroup.Property[length];
        for (int i = 0; i < length; i++) {
            this.mProperties[i] = createProperty(iArr[i], arrayList);
        }
        int size = arrayList.size();
        this.mColumns = new java.lang.String[size];
        for (int i2 = 0; i2 < size; i2++) {
            this.mColumns[i2] = arrayList.get(i2);
        }
    }

    private android.mtp.MtpPropertyGroup.Property createProperty(int i, java.util.ArrayList<java.lang.String> arrayList) {
        int i2 = 4;
        java.lang.String str = null;
        switch (i) {
            case android.mtp.MtpConstants.PROPERTY_STORAGE_ID /* 56321 */:
                i2 = 6;
                break;
            case android.mtp.MtpConstants.PROPERTY_OBJECT_FORMAT /* 56322 */:
            case android.mtp.MtpConstants.PROPERTY_PROTECTION_STATUS /* 56323 */:
            case android.mtp.MtpConstants.PROPERTY_BITRATE_TYPE /* 56978 */:
            case android.mtp.MtpConstants.PROPERTY_NUMBER_OF_CHANNELS /* 56980 */:
                break;
            case android.mtp.MtpConstants.PROPERTY_OBJECT_SIZE /* 56324 */:
                i2 = 8;
                break;
            case android.mtp.MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_DATE_MODIFIED /* 56329 */:
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_PARENT_OBJECT /* 56331 */:
                i2 = 6;
                break;
            case android.mtp.MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                i2 = 10;
                break;
            case android.mtp.MtpConstants.PROPERTY_NAME /* 56388 */:
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_ARTIST /* 56390 */:
                str = "artist";
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_DESCRIPTION /* 56392 */:
                str = "description";
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_DATE_ADDED /* 56398 */:
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_DURATION /* 56457 */:
                str = "duration";
                i2 = 6;
                break;
            case android.mtp.MtpConstants.PROPERTY_TRACK /* 56459 */:
                str = "track";
                break;
            case android.mtp.MtpConstants.PROPERTY_GENRE /* 56460 */:
                str = "genre";
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_COMPOSER /* 56470 */:
                str = "composer";
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                str = "year";
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_ALBUM_NAME /* 56474 */:
                str = "album";
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_ALBUM_ARTIST /* 56475 */:
                str = "album_artist";
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_DISPLAY_NAME /* 56544 */:
                i2 = 65535;
                break;
            case android.mtp.MtpConstants.PROPERTY_SAMPLE_RATE /* 56979 */:
            case android.mtp.MtpConstants.PROPERTY_AUDIO_WAVE_CODEC /* 56985 */:
            case android.mtp.MtpConstants.PROPERTY_AUDIO_BITRATE /* 56986 */:
                i2 = 6;
                break;
            default:
                android.util.Log.e(TAG, "unsupported property " + i);
                i2 = 0;
                break;
        }
        if (str != null) {
            arrayList.add(str);
            return new android.mtp.MtpPropertyGroup.Property(i, i2, arrayList.size() - 1);
        }
        return new android.mtp.MtpPropertyGroup.Property(i, i2, -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getPropertyList(android.content.ContentProviderClient contentProviderClient, java.lang.String str, android.mtp.MtpStorageManager.MtpObject mtpObject, android.mtp.MtpPropertyList mtpPropertyList) {
        android.database.Cursor cursor;
        int i;
        int i2;
        java.lang.String str2;
        long j;
        int id = mtpObject.getId();
        java.lang.String path = mtpObject.getPath().toString();
        android.mtp.MtpPropertyGroup.Property[] propertyArr = this.mProperties;
        int length = propertyArr.length;
        android.database.Cursor cursor2 = null;
        int i3 = 0;
        while (i3 < length) {
            android.mtp.MtpPropertyGroup.Property property = propertyArr[i3];
            if (property.column != -1 && cursor2 == null) {
                try {
                    try {
                        try {
                            cursor2 = contentProviderClient.query(android.mtp.MtpDatabase.getObjectPropertiesUri(mtpObject.getFormat(), str), this.mColumns, PATH_WHERE, new java.lang.String[]{path}, null, null);
                            if (cursor2 != null && !cursor2.moveToNext()) {
                                cursor2.close();
                                cursor2 = null;
                            }
                            cursor = cursor2;
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(TAG, "Mediaprovider lookup failed");
                            cursor = cursor2;
                            switch (property.code) {
                                case android.mtp.MtpConstants.PROPERTY_STORAGE_ID /* 56321 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_OBJECT_FORMAT /* 56322 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_PROTECTION_STATUS /* 56323 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_OBJECT_SIZE /* 56324 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                                case android.mtp.MtpConstants.PROPERTY_NAME /* 56388 */:
                                case android.mtp.MtpConstants.PROPERTY_DISPLAY_NAME /* 56544 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_DATE_MODIFIED /* 56329 */:
                                case android.mtp.MtpConstants.PROPERTY_DATE_ADDED /* 56398 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_PARENT_OBJECT /* 56331 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_TRACK /* 56459 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_BITRATE_TYPE /* 56978 */:
                                case android.mtp.MtpConstants.PROPERTY_NUMBER_OF_CHANNELS /* 56980 */:
                                    break;
                                case android.mtp.MtpConstants.PROPERTY_SAMPLE_RATE /* 56979 */:
                                case android.mtp.MtpConstants.PROPERTY_AUDIO_WAVE_CODEC /* 56985 */:
                                case android.mtp.MtpConstants.PROPERTY_AUDIO_BITRATE /* 56986 */:
                                    break;
                            }
                            i3++;
                            cursor2 = cursor;
                        }
                    } catch (java.lang.IllegalArgumentException e2) {
                        return android.mtp.MtpConstants.RESPONSE_INVALID_OBJECT_PROP_CODE;
                    }
                } catch (android.os.RemoteException e3) {
                }
            } else {
                cursor = cursor2;
            }
            switch (property.code) {
                case android.mtp.MtpConstants.PROPERTY_STORAGE_ID /* 56321 */:
                    mtpPropertyList.append(id, property.code, property.type, mtpObject.getStorageId());
                    break;
                case android.mtp.MtpConstants.PROPERTY_OBJECT_FORMAT /* 56322 */:
                    mtpPropertyList.append(id, property.code, property.type, mtpObject.getFormat());
                    break;
                case android.mtp.MtpConstants.PROPERTY_PROTECTION_STATUS /* 56323 */:
                    mtpPropertyList.append(id, property.code, property.type, 0L);
                    break;
                case android.mtp.MtpConstants.PROPERTY_OBJECT_SIZE /* 56324 */:
                    mtpPropertyList.append(id, property.code, property.type, mtpObject.getSize());
                    break;
                case android.mtp.MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                case android.mtp.MtpConstants.PROPERTY_NAME /* 56388 */:
                case android.mtp.MtpConstants.PROPERTY_DISPLAY_NAME /* 56544 */:
                    mtpPropertyList.append(id, property.code, mtpObject.getName());
                    break;
                case android.mtp.MtpConstants.PROPERTY_DATE_MODIFIED /* 56329 */:
                case android.mtp.MtpConstants.PROPERTY_DATE_ADDED /* 56398 */:
                    mtpPropertyList.append(id, property.code, format_date_time(mtpObject.getModifiedTime()));
                    break;
                case android.mtp.MtpConstants.PROPERTY_PARENT_OBJECT /* 56331 */:
                    mtpPropertyList.append(id, property.code, property.type, mtpObject.getParent().isRoot() ? 0L : mtpObject.getParent().getId());
                    break;
                case android.mtp.MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                    mtpPropertyList.append(id, property.code, property.type, mtpObject.getModifiedTime() + (mtpObject.getPath().toString().hashCode() << 32));
                    break;
                case android.mtp.MtpConstants.PROPERTY_TRACK /* 56459 */:
                    if (cursor == null) {
                        i = 0;
                    } else {
                        i = cursor.getInt(property.column);
                    }
                    mtpPropertyList.append(id, property.code, 4, i % 1000);
                    break;
                case android.mtp.MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                    if (cursor == null) {
                        i2 = 0;
                    } else {
                        i2 = cursor.getInt(property.column);
                    }
                    mtpPropertyList.append(id, property.code, java.lang.Integer.toString(i2) + "0101T000000");
                    break;
                case android.mtp.MtpConstants.PROPERTY_BITRATE_TYPE /* 56978 */:
                case android.mtp.MtpConstants.PROPERTY_NUMBER_OF_CHANNELS /* 56980 */:
                    mtpPropertyList.append(id, property.code, 4, 0L);
                    break;
                case android.mtp.MtpConstants.PROPERTY_SAMPLE_RATE /* 56979 */:
                case android.mtp.MtpConstants.PROPERTY_AUDIO_WAVE_CODEC /* 56985 */:
                case android.mtp.MtpConstants.PROPERTY_AUDIO_BITRATE /* 56986 */:
                    mtpPropertyList.append(id, property.code, 6, 0L);
                    break;
                default:
                    switch (property.type) {
                        case 0:
                            mtpPropertyList.append(id, property.code, property.type, 0L);
                            break;
                        case 65535:
                            if (cursor == null) {
                                str2 = "";
                            } else {
                                str2 = cursor.getString(property.column);
                            }
                            mtpPropertyList.append(id, property.code, str2);
                            break;
                        default:
                            if (cursor == null) {
                                j = 0;
                            } else {
                                j = cursor.getLong(property.column);
                            }
                            mtpPropertyList.append(id, property.code, property.type, j);
                            break;
                    }
            }
            i3++;
            cursor2 = cursor;
        }
        if (cursor2 != null) {
            cursor2.close();
            return 8193;
        }
        return 8193;
    }
}
