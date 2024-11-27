package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SmsCbMessage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.SmsCbMessage> CREATOR = new android.os.Parcelable.Creator<android.telephony.SmsCbMessage>() { // from class: android.telephony.SmsCbMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SmsCbMessage createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.SmsCbMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SmsCbMessage[] newArray(int i) {
            return new android.telephony.SmsCbMessage[i];
        }
    };
    public static final int GEOGRAPHICAL_SCOPE_CELL_WIDE = 3;
    public static final int GEOGRAPHICAL_SCOPE_CELL_WIDE_IMMEDIATE = 0;
    public static final int GEOGRAPHICAL_SCOPE_LOCATION_AREA_WIDE = 2;
    public static final int GEOGRAPHICAL_SCOPE_PLMN_WIDE = 1;
    public static final java.lang.String LOG_TAG = "SMSCB";
    public static final int MAXIMUM_WAIT_TIME_NOT_SET = 255;
    public static final int MESSAGE_FORMAT_3GPP = 1;
    public static final int MESSAGE_FORMAT_3GPP2 = 2;
    public static final int MESSAGE_PRIORITY_EMERGENCY = 3;
    public static final int MESSAGE_PRIORITY_INTERACTIVE = 1;
    public static final int MESSAGE_PRIORITY_NORMAL = 0;
    public static final int MESSAGE_PRIORITY_URGENT = 2;
    private final java.lang.String mBody;
    private final android.telephony.SmsCbCmasInfo mCmasWarningInfo;
    private final int mDataCodingScheme;
    private final android.telephony.SmsCbEtwsInfo mEtwsWarningInfo;
    private final int mGeographicalScope;
    private final java.util.List<android.telephony.CbGeoUtils.Geometry> mGeometries;
    private final java.lang.String mLanguage;
    private final android.telephony.SmsCbLocation mLocation;
    private final int mMaximumWaitTimeSec;
    private final int mMessageFormat;
    private final int mPriority;
    private final long mReceivedTimeMillis;
    private final int mSerialNumber;
    private final int mServiceCategory;
    private final int mSlotIndex;
    private final int mSubId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GeographicalScope {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MessageFormat {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MessagePriority {
    }

    public SmsCbMessage(int i, int i2, int i3, android.telephony.SmsCbLocation smsCbLocation, int i4, java.lang.String str, java.lang.String str2, int i5, android.telephony.SmsCbEtwsInfo smsCbEtwsInfo, android.telephony.SmsCbCmasInfo smsCbCmasInfo, int i6, int i7) {
        this(i, i2, i3, smsCbLocation, i4, str, 0, str2, i5, smsCbEtwsInfo, smsCbCmasInfo, 0, null, java.lang.System.currentTimeMillis(), i6, i7);
    }

    public SmsCbMessage(int i, int i2, int i3, android.telephony.SmsCbLocation smsCbLocation, int i4, java.lang.String str, int i5, java.lang.String str2, int i6, android.telephony.SmsCbEtwsInfo smsCbEtwsInfo, android.telephony.SmsCbCmasInfo smsCbCmasInfo, int i7, java.util.List<android.telephony.CbGeoUtils.Geometry> list, long j, int i8, int i9) {
        this.mMessageFormat = i;
        this.mGeographicalScope = i2;
        this.mSerialNumber = i3;
        this.mLocation = smsCbLocation;
        this.mServiceCategory = i4;
        this.mLanguage = str;
        this.mDataCodingScheme = i5;
        this.mBody = str2;
        this.mPriority = i6;
        this.mEtwsWarningInfo = smsCbEtwsInfo;
        this.mCmasWarningInfo = smsCbCmasInfo;
        this.mReceivedTimeMillis = j;
        this.mGeometries = list;
        this.mMaximumWaitTimeSec = i7;
        this.mSlotIndex = i8;
        this.mSubId = i9;
    }

    public SmsCbMessage(android.os.Parcel parcel) {
        this.mMessageFormat = parcel.readInt();
        this.mGeographicalScope = parcel.readInt();
        this.mSerialNumber = parcel.readInt();
        this.mLocation = new android.telephony.SmsCbLocation(parcel);
        this.mServiceCategory = parcel.readInt();
        this.mLanguage = parcel.readString();
        this.mDataCodingScheme = parcel.readInt();
        this.mBody = parcel.readString();
        this.mPriority = parcel.readInt();
        switch (parcel.readInt()) {
            case 67:
                this.mEtwsWarningInfo = null;
                this.mCmasWarningInfo = new android.telephony.SmsCbCmasInfo(parcel);
                break;
            case 68:
            default:
                this.mEtwsWarningInfo = null;
                this.mCmasWarningInfo = null;
                break;
            case 69:
                this.mEtwsWarningInfo = new android.telephony.SmsCbEtwsInfo(parcel);
                this.mCmasWarningInfo = null;
                break;
        }
        this.mReceivedTimeMillis = parcel.readLong();
        java.lang.String readString = parcel.readString();
        this.mGeometries = readString != null ? android.telephony.CbGeoUtils.parseGeometriesFromString(readString) : null;
        this.mMaximumWaitTimeSec = parcel.readInt();
        this.mSlotIndex = parcel.readInt();
        this.mSubId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mMessageFormat);
        parcel.writeInt(this.mGeographicalScope);
        parcel.writeInt(this.mSerialNumber);
        this.mLocation.writeToParcel(parcel, i);
        parcel.writeInt(this.mServiceCategory);
        parcel.writeString(this.mLanguage);
        parcel.writeInt(this.mDataCodingScheme);
        parcel.writeString(this.mBody);
        parcel.writeInt(this.mPriority);
        if (this.mEtwsWarningInfo != null) {
            parcel.writeInt(69);
            this.mEtwsWarningInfo.writeToParcel(parcel, i);
        } else if (this.mCmasWarningInfo != null) {
            parcel.writeInt(67);
            this.mCmasWarningInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(48);
        }
        parcel.writeLong(this.mReceivedTimeMillis);
        parcel.writeString(this.mGeometries != null ? android.telephony.CbGeoUtils.encodeGeometriesToString(this.mGeometries) : null);
        parcel.writeInt(this.mMaximumWaitTimeSec);
        parcel.writeInt(this.mSlotIndex);
        parcel.writeInt(this.mSubId);
    }

    public int getGeographicalScope() {
        return this.mGeographicalScope;
    }

    public int getSerialNumber() {
        return this.mSerialNumber;
    }

    public android.telephony.SmsCbLocation getLocation() {
        return this.mLocation;
    }

    public int getServiceCategory() {
        return this.mServiceCategory;
    }

    public java.lang.String getLanguageCode() {
        return this.mLanguage;
    }

    public int getDataCodingScheme() {
        return this.mDataCodingScheme;
    }

    public java.lang.String getMessageBody() {
        return this.mBody;
    }

    @android.annotation.SystemApi
    public java.util.List<android.telephony.CbGeoUtils.Geometry> getGeometries() {
        if (this.mGeometries == null) {
            return new java.util.ArrayList();
        }
        return this.mGeometries;
    }

    public int getMaximumWaitingDuration() {
        return this.mMaximumWaitTimeSec;
    }

    public long getReceivedTime() {
        return this.mReceivedTimeMillis;
    }

    public int getSlotIndex() {
        return this.mSlotIndex;
    }

    public int getSubscriptionId() {
        return this.mSubId;
    }

    public int getMessageFormat() {
        return this.mMessageFormat;
    }

    public int getMessagePriority() {
        return this.mPriority;
    }

    public android.telephony.SmsCbEtwsInfo getEtwsWarningInfo() {
        return this.mEtwsWarningInfo;
    }

    public android.telephony.SmsCbCmasInfo getCmasWarningInfo() {
        return this.mCmasWarningInfo;
    }

    public boolean isEmergencyMessage() {
        return this.mPriority == 3;
    }

    public boolean isEtwsMessage() {
        return this.mEtwsWarningInfo != null;
    }

    public boolean isCmasMessage() {
        return this.mCmasWarningInfo != null;
    }

    public java.lang.String toString() {
        return "SmsCbMessage{geographicalScope=" + this.mGeographicalScope + ", serialNumber=" + this.mSerialNumber + ", location=" + this.mLocation + ", serviceCategory=" + this.mServiceCategory + ", language=" + this.mLanguage + ", body=" + this.mBody + ", priority=" + this.mPriority + (this.mEtwsWarningInfo != null ? ", " + this.mEtwsWarningInfo.toString() : "") + (this.mCmasWarningInfo != null ? ", " + this.mCmasWarningInfo.toString() : "") + ", maximumWaitingTime=" + this.mMaximumWaitTimeSec + ", received time=" + this.mReceivedTimeMillis + ", slotIndex = " + this.mSlotIndex + ", geo=" + (this.mGeometries != null ? android.telephony.CbGeoUtils.encodeGeometriesToString(this.mGeometries) : "null") + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.content.ContentValues getContentValues() {
        android.content.ContentValues contentValues = new android.content.ContentValues(16);
        contentValues.put("slot_index", java.lang.Integer.valueOf(this.mSlotIndex));
        contentValues.put("sub_id", java.lang.Integer.valueOf(this.mSubId));
        contentValues.put(android.provider.Telephony.CellBroadcasts.GEOGRAPHICAL_SCOPE, java.lang.Integer.valueOf(this.mGeographicalScope));
        if (this.mLocation.getPlmn() != null) {
            contentValues.put("plmn", this.mLocation.getPlmn());
        }
        if (this.mLocation.getLac() != -1) {
            contentValues.put(android.provider.Telephony.CellBroadcasts.LAC, java.lang.Integer.valueOf(this.mLocation.getLac()));
        }
        if (this.mLocation.getCid() != -1) {
            contentValues.put("cid", java.lang.Integer.valueOf(this.mLocation.getCid()));
        }
        contentValues.put("serial_number", java.lang.Integer.valueOf(getSerialNumber()));
        contentValues.put(android.provider.Telephony.CellBroadcasts.SERVICE_CATEGORY, java.lang.Integer.valueOf(getServiceCategory()));
        contentValues.put("language", getLanguageCode());
        contentValues.put(android.provider.Telephony.CellBroadcasts.DATA_CODING_SCHEME, java.lang.Integer.valueOf(getDataCodingScheme()));
        contentValues.put("body", getMessageBody());
        contentValues.put(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT, java.lang.Integer.valueOf(getMessageFormat()));
        contentValues.put("priority", java.lang.Integer.valueOf(getMessagePriority()));
        android.telephony.SmsCbEtwsInfo etwsWarningInfo = getEtwsWarningInfo();
        if (etwsWarningInfo != null) {
            contentValues.put(android.provider.Telephony.CellBroadcasts.ETWS_WARNING_TYPE, java.lang.Integer.valueOf(etwsWarningInfo.getWarningType()));
            contentValues.put(android.provider.Telephony.CellBroadcasts.ETWS_IS_PRIMARY, java.lang.Boolean.valueOf(etwsWarningInfo.isPrimary()));
        }
        android.telephony.SmsCbCmasInfo cmasWarningInfo = getCmasWarningInfo();
        if (cmasWarningInfo != null) {
            contentValues.put(android.provider.Telephony.CellBroadcasts.CMAS_MESSAGE_CLASS, java.lang.Integer.valueOf(cmasWarningInfo.getMessageClass()));
            contentValues.put(android.provider.Telephony.CellBroadcasts.CMAS_CATEGORY, java.lang.Integer.valueOf(cmasWarningInfo.getCategory()));
            contentValues.put(android.provider.Telephony.CellBroadcasts.CMAS_RESPONSE_TYPE, java.lang.Integer.valueOf(cmasWarningInfo.getResponseType()));
            contentValues.put(android.provider.Telephony.CellBroadcasts.CMAS_SEVERITY, java.lang.Integer.valueOf(cmasWarningInfo.getSeverity()));
            contentValues.put(android.provider.Telephony.CellBroadcasts.CMAS_URGENCY, java.lang.Integer.valueOf(cmasWarningInfo.getUrgency()));
            contentValues.put(android.provider.Telephony.CellBroadcasts.CMAS_CERTAINTY, java.lang.Integer.valueOf(cmasWarningInfo.getCertainty()));
        }
        contentValues.put(android.provider.Telephony.CellBroadcasts.RECEIVED_TIME, java.lang.Long.valueOf(this.mReceivedTimeMillis));
        if (this.mGeometries != null) {
            contentValues.put(android.provider.Telephony.CellBroadcasts.GEOMETRIES, android.telephony.CbGeoUtils.encodeGeometriesToString(this.mGeometries));
        } else {
            contentValues.put(android.provider.Telephony.CellBroadcasts.GEOMETRIES, (java.lang.String) null);
        }
        contentValues.put(android.provider.Telephony.CellBroadcasts.MAXIMUM_WAIT_TIME, java.lang.Integer.valueOf(this.mMaximumWaitTimeSec));
        return contentValues;
    }

    public static android.telephony.SmsCbMessage createFromCursor(android.database.Cursor cursor) {
        java.lang.String str;
        int i;
        int i2;
        android.telephony.SmsCbEtwsInfo smsCbEtwsInfo;
        android.telephony.SmsCbCmasInfo smsCbCmasInfo;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.CellBroadcasts.GEOGRAPHICAL_SCOPE));
        int i9 = cursor.getInt(cursor.getColumnIndexOrThrow("serial_number"));
        int i10 = cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.CellBroadcasts.SERVICE_CATEGORY));
        java.lang.String string = cursor.getString(cursor.getColumnIndexOrThrow("language"));
        java.lang.String string2 = cursor.getString(cursor.getColumnIndexOrThrow("body"));
        int i11 = cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT));
        int i12 = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
        int i13 = cursor.getInt(cursor.getColumnIndexOrThrow("slot_index"));
        int i14 = cursor.getInt(cursor.getColumnIndexOrThrow("sub_id"));
        int columnIndex = cursor.getColumnIndex("plmn");
        if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
            str = cursor.getString(columnIndex);
        } else {
            str = null;
        }
        int columnIndex2 = cursor.getColumnIndex(android.provider.Telephony.CellBroadcasts.LAC);
        if (columnIndex2 != -1 && !cursor.isNull(columnIndex2)) {
            i = cursor.getInt(columnIndex2);
        } else {
            i = -1;
        }
        int columnIndex3 = cursor.getColumnIndex("cid");
        if (columnIndex3 != -1 && !cursor.isNull(columnIndex3)) {
            i2 = cursor.getInt(columnIndex3);
        } else {
            i2 = -1;
        }
        android.telephony.SmsCbLocation smsCbLocation = new android.telephony.SmsCbLocation(str, i, i2);
        int columnIndex4 = cursor.getColumnIndex(android.provider.Telephony.CellBroadcasts.ETWS_WARNING_TYPE);
        int columnIndex5 = cursor.getColumnIndex(android.provider.Telephony.CellBroadcasts.ETWS_IS_PRIMARY);
        if (columnIndex4 != -1 && !cursor.isNull(columnIndex4) && columnIndex5 != -1 && !cursor.isNull(columnIndex5)) {
            smsCbEtwsInfo = new android.telephony.SmsCbEtwsInfo(cursor.getInt(columnIndex4), false, false, cursor.getInt(columnIndex5) != 0, null);
        } else {
            smsCbEtwsInfo = null;
        }
        int columnIndex6 = cursor.getColumnIndex(android.provider.Telephony.CellBroadcasts.CMAS_MESSAGE_CLASS);
        if (columnIndex6 != -1 && !cursor.isNull(columnIndex6)) {
            int i15 = cursor.getInt(columnIndex6);
            int columnIndex7 = cursor.getColumnIndex(android.provider.Telephony.CellBroadcasts.CMAS_CATEGORY);
            if (columnIndex7 != -1 && !cursor.isNull(columnIndex7)) {
                i3 = cursor.getInt(columnIndex7);
            } else {
                i3 = -1;
            }
            int columnIndex8 = cursor.getColumnIndex(android.provider.Telephony.CellBroadcasts.CMAS_RESPONSE_TYPE);
            if (columnIndex8 != -1 && !cursor.isNull(columnIndex8)) {
                i4 = cursor.getInt(columnIndex8);
            } else {
                i4 = -1;
            }
            int columnIndex9 = cursor.getColumnIndex(android.provider.Telephony.CellBroadcasts.CMAS_SEVERITY);
            if (columnIndex9 != -1 && !cursor.isNull(columnIndex9)) {
                i5 = cursor.getInt(columnIndex9);
            } else {
                i5 = -1;
            }
            int columnIndex10 = cursor.getColumnIndex(android.provider.Telephony.CellBroadcasts.CMAS_URGENCY);
            if (columnIndex10 != -1 && !cursor.isNull(columnIndex10)) {
                i6 = cursor.getInt(columnIndex10);
            } else {
                i6 = -1;
            }
            int columnIndex11 = cursor.getColumnIndex(android.provider.Telephony.CellBroadcasts.CMAS_CERTAINTY);
            if (columnIndex11 != -1 && !cursor.isNull(columnIndex11)) {
                i7 = cursor.getInt(columnIndex11);
            } else {
                i7 = -1;
            }
            smsCbCmasInfo = new android.telephony.SmsCbCmasInfo(i15, i3, i4, i5, i6, i7);
        } else {
            smsCbCmasInfo = null;
        }
        java.lang.String string3 = cursor.getString(cursor.getColumnIndex(android.provider.Telephony.CellBroadcasts.GEOMETRIES));
        return new android.telephony.SmsCbMessage(i11, i8, i9, smsCbLocation, i10, string, 0, string2, i12, smsCbEtwsInfo, smsCbCmasInfo, cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.CellBroadcasts.MAXIMUM_WAIT_TIME)), string3 != null ? android.telephony.CbGeoUtils.parseGeometriesFromString(string3) : null, cursor.getLong(cursor.getColumnIndexOrThrow(android.provider.Telephony.CellBroadcasts.RECEIVED_TIME)), i13, i14);
    }

    public boolean needGeoFencingCheck() {
        return (this.mMaximumWaitTimeSec <= 0 || this.mGeometries == null || this.mGeometries.isEmpty()) ? false : true;
    }
}
