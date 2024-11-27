package android.telecom;

/* loaded from: classes3.dex */
public class CallerInfo {
    private static final java.lang.String TAG = "CallerInfo";
    public static final long USER_TYPE_CURRENT = 0;
    public static final long USER_TYPE_WORK = 1;
    private static final boolean VDBG = android.telecom.Log.VERBOSE;
    public android.graphics.drawable.Drawable cachedPhoto;
    public android.graphics.Bitmap cachedPhotoIcon;
    public java.lang.String cnapName;
    private android.net.Uri contactDisplayPhotoUri;
    public boolean contactExists;
    private long contactIdOrZero;
    public android.net.Uri contactRefUri;
    public android.net.Uri contactRingtoneUri;
    public java.lang.String geoDescription;
    public boolean isCachedPhotoCurrent;
    public java.lang.String lookupKey;
    private java.lang.String name;
    public int namePresentation;
    public boolean needUpdate;
    public java.lang.String normalizedNumber;
    public java.lang.String numberLabel;
    public int numberPresentation;
    public int numberType;
    public java.lang.String phoneLabel;
    private java.lang.String phoneNumber;
    public int photoResource;
    public android.content.ComponentName preferredPhoneAccountComponent;
    public java.lang.String preferredPhoneAccountId;
    public boolean shouldSendToVoicemail;
    private boolean mIsEmergency = false;
    private boolean mIsVoiceMail = false;
    public long userType = 0;

    public static android.telecom.CallerInfo getCallerInfo(android.content.Context context, android.net.Uri uri, android.database.Cursor cursor) {
        boolean z;
        int columnIndex;
        android.telecom.CallerInfo callerInfo = new android.telecom.CallerInfo();
        callerInfo.photoResource = 0;
        callerInfo.phoneLabel = null;
        callerInfo.numberType = 0;
        callerInfo.numberLabel = null;
        callerInfo.cachedPhoto = null;
        callerInfo.isCachedPhotoCurrent = false;
        callerInfo.contactExists = false;
        callerInfo.userType = 0L;
        if (VDBG) {
            android.telecom.Log.v(TAG, "getCallerInfo() based on cursor...", new java.lang.Object[0]);
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex2 = cursor.getColumnIndex("display_name");
                if (columnIndex2 != -1) {
                    callerInfo.name = cursor.getString(columnIndex2);
                }
                int columnIndex3 = cursor.getColumnIndex("number");
                if (columnIndex3 != -1) {
                    callerInfo.phoneNumber = cursor.getString(columnIndex3);
                }
                int columnIndex4 = cursor.getColumnIndex("normalized_number");
                if (columnIndex4 != -1) {
                    callerInfo.normalizedNumber = cursor.getString(columnIndex4);
                }
                int columnIndex5 = cursor.getColumnIndex("label");
                if (columnIndex5 != -1 && (columnIndex = cursor.getColumnIndex("type")) != -1) {
                    callerInfo.numberType = cursor.getInt(columnIndex);
                    callerInfo.numberLabel = cursor.getString(columnIndex5);
                    callerInfo.phoneLabel = android.provider.ContactsContract.CommonDataKinds.Phone.getDisplayLabel(context, callerInfo.numberType, callerInfo.numberLabel).toString();
                }
                int columnIndexForPersonId = getColumnIndexForPersonId(uri, cursor);
                if (columnIndexForPersonId == -1) {
                    android.telecom.Log.w(TAG, "Couldn't find contact_id column for " + uri, new java.lang.Object[0]);
                } else {
                    long j = cursor.getLong(columnIndexForPersonId);
                    if (j != 0 && !android.provider.ContactsContract.Contacts.isEnterpriseContactId(j)) {
                        callerInfo.contactIdOrZero = j;
                        if (VDBG) {
                            android.telecom.Log.v(TAG, "==> got info.contactIdOrZero: " + callerInfo.contactIdOrZero, new java.lang.Object[0]);
                        }
                    }
                    if (android.provider.ContactsContract.Contacts.isEnterpriseContactId(j)) {
                        callerInfo.userType = 1L;
                    }
                }
                int columnIndex6 = cursor.getColumnIndex("lookup");
                if (columnIndex6 != -1) {
                    callerInfo.lookupKey = cursor.getString(columnIndex6);
                }
                int columnIndex7 = cursor.getColumnIndex("photo_uri");
                if (columnIndex7 != -1 && cursor.getString(columnIndex7) != null) {
                    callerInfo.contactDisplayPhotoUri = android.net.Uri.parse(cursor.getString(columnIndex7));
                } else {
                    callerInfo.contactDisplayPhotoUri = null;
                }
                int columnIndex8 = cursor.getColumnIndex(android.provider.ContactsContract.DataColumns.PREFERRED_PHONE_ACCOUNT_COMPONENT_NAME);
                if (columnIndex8 != -1 && cursor.getString(columnIndex8) != null) {
                    callerInfo.preferredPhoneAccountComponent = android.content.ComponentName.unflattenFromString(cursor.getString(columnIndex8));
                }
                int columnIndex9 = cursor.getColumnIndex(android.provider.ContactsContract.DataColumns.PREFERRED_PHONE_ACCOUNT_ID);
                if (columnIndex9 != -1 && cursor.getString(columnIndex9) != null) {
                    callerInfo.preferredPhoneAccountId = cursor.getString(columnIndex9);
                }
                int columnIndex10 = cursor.getColumnIndex("custom_ringtone");
                if (columnIndex10 != -1 && cursor.getString(columnIndex10) != null) {
                    if (android.text.TextUtils.isEmpty(cursor.getString(columnIndex10))) {
                        callerInfo.contactRingtoneUri = android.net.Uri.EMPTY;
                    } else {
                        callerInfo.contactRingtoneUri = android.net.Uri.parse(cursor.getString(columnIndex10));
                    }
                } else {
                    callerInfo.contactRingtoneUri = null;
                }
                int columnIndex11 = cursor.getColumnIndex("send_to_voicemail");
                if (columnIndex11 == -1 || cursor.getInt(columnIndex11) != 1) {
                    z = false;
                } else {
                    z = true;
                }
                callerInfo.shouldSendToVoicemail = z;
                callerInfo.contactExists = true;
            }
            cursor.close();
        }
        callerInfo.needUpdate = false;
        callerInfo.name = normalize(callerInfo.name);
        callerInfo.contactRefUri = uri;
        return callerInfo;
    }

    public static android.telecom.CallerInfo getCallerInfo(android.content.Context context, android.net.Uri uri) {
        android.content.ContentResolver currentProfileContentResolver = android.telecom.CallerInfoAsyncQuery.getCurrentProfileContentResolver(context);
        if (currentProfileContentResolver != null) {
            try {
                return getCallerInfo(context, uri, currentProfileContentResolver.query(uri, null, null, null, null));
            } catch (java.lang.RuntimeException e) {
                android.telecom.Log.e(TAG, (java.lang.Throwable) e, "Error getting caller info.", new java.lang.Object[0]);
            }
        }
        return null;
    }

    public static android.telecom.CallerInfo getCallerInfo(android.content.Context context, java.lang.String str) {
        if (VDBG) {
            android.telecom.Log.v(TAG, "getCallerInfo() based on number...", new java.lang.Object[0]);
        }
        return getCallerInfo(context, str, android.telephony.SubscriptionManager.getDefaultSubscriptionId());
    }

    public static android.telecom.CallerInfo getCallerInfo(android.content.Context context, java.lang.String str, int i) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        if (((android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class)).isEmergencyNumber(str)) {
            return new android.telecom.CallerInfo().markAsEmergency(context);
        }
        if (android.telephony.PhoneNumberUtils.isVoiceMailNumber(null, i, str)) {
            return new android.telecom.CallerInfo().markAsVoiceMail(context, i);
        }
        android.telecom.CallerInfo doSecondaryLookupIfNecessary = doSecondaryLookupIfNecessary(context, str, getCallerInfo(context, android.net.Uri.withAppendedPath(android.provider.ContactsContract.PhoneLookup.ENTERPRISE_CONTENT_FILTER_URI, android.net.Uri.encode(str))));
        if (android.text.TextUtils.isEmpty(doSecondaryLookupIfNecessary.phoneNumber)) {
            doSecondaryLookupIfNecessary.phoneNumber = str;
        }
        return doSecondaryLookupIfNecessary;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String str) {
        this.name = str;
    }

    public java.lang.String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(java.lang.String str) {
        this.phoneNumber = str;
    }

    public long getContactId() {
        return this.contactIdOrZero;
    }

    public android.net.Uri getContactDisplayPhotoUri() {
        return this.contactDisplayPhotoUri;
    }

    public void SetContactDisplayPhotoUri(android.net.Uri uri) {
        this.contactDisplayPhotoUri = uri;
    }

    static android.telecom.CallerInfo doSecondaryLookupIfNecessary(android.content.Context context, java.lang.String str, android.telecom.CallerInfo callerInfo) {
        if (!callerInfo.contactExists && android.telephony.PhoneNumberUtils.isUriNumber(str)) {
            java.lang.String usernameFromUriNumber = android.telephony.PhoneNumberUtils.getUsernameFromUriNumber(str);
            if (android.telephony.PhoneNumberUtils.isGlobalPhoneNumber(usernameFromUriNumber)) {
                return getCallerInfo(context, android.net.Uri.withAppendedPath(android.provider.ContactsContract.PhoneLookup.ENTERPRISE_CONTENT_FILTER_URI, android.net.Uri.encode(usernameFromUriNumber)));
            }
            return callerInfo;
        }
        return callerInfo;
    }

    public boolean isEmergencyNumber() {
        return this.mIsEmergency;
    }

    public boolean isVoiceMailNumber() {
        return this.mIsVoiceMail;
    }

    android.telecom.CallerInfo markAsEmergency(android.content.Context context) {
        this.phoneNumber = context.getString(com.android.internal.R.string.emergency_call_dialog_number_for_display);
        this.photoResource = com.android.internal.R.drawable.picture_emergency;
        this.mIsEmergency = true;
        return this;
    }

    android.telecom.CallerInfo markAsVoiceMail(android.content.Context context, int i) {
        this.mIsVoiceMail = true;
        try {
            this.phoneNumber = ((android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class)).createForSubscriptionId(i).getVoiceMailAlphaTag();
        } catch (java.lang.SecurityException e) {
            android.telecom.Log.e(TAG, (java.lang.Throwable) e, "Cannot access VoiceMail.", new java.lang.Object[0]);
        }
        return this;
    }

    private static java.lang.String normalize(java.lang.String str) {
        if (str == null || str.length() > 0) {
            return str;
        }
        return null;
    }

    private static int getColumnIndexForPersonId(android.net.Uri uri, android.database.Cursor cursor) {
        if (VDBG) {
            android.telecom.Log.v(TAG, "- getColumnIndexForPersonId: contactRef URI = '" + uri + "'...", new java.lang.Object[0]);
        }
        java.lang.String uri2 = uri.toString();
        java.lang.String str = "contact_id";
        if (uri2.startsWith("content://com.android.contacts/data/phones")) {
            if (VDBG) {
                android.telecom.Log.v(TAG, "'data/phones' URI; using RawContacts.CONTACT_ID", new java.lang.Object[0]);
            }
        } else if (uri2.startsWith("content://com.android.contacts/data")) {
            if (VDBG) {
                android.telecom.Log.v(TAG, "'data' URI; using Data.CONTACT_ID", new java.lang.Object[0]);
            }
        } else if (uri2.startsWith("content://com.android.contacts/phone_lookup")) {
            if (VDBG) {
                android.telecom.Log.v(TAG, "'phone_lookup' URI; using PhoneLookup._ID", new java.lang.Object[0]);
            }
            str = "_id";
        } else {
            android.telecom.Log.w(TAG, "Unexpected prefix for contactRef '" + uri2 + "'", new java.lang.Object[0]);
            str = null;
        }
        int columnIndex = str != null ? cursor.getColumnIndex(str) : -1;
        if (VDBG) {
            android.telecom.Log.v(TAG, "==> Using column '" + str + "' (columnIndex = " + columnIndex + ") for person_id lookup...", new java.lang.Object[0]);
        }
        return columnIndex;
    }

    public void updateGeoDescription(android.content.Context context, java.lang.String str) {
        if (!android.text.TextUtils.isEmpty(this.phoneNumber)) {
            str = this.phoneNumber;
        }
        this.geoDescription = getGeoDescription(context, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00df A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static java.lang.String getGeoDescription(android.content.Context context, java.lang.String str) {
        com.android.i18n.phonenumbers.Phonenumber.PhoneNumber phoneNumber;
        if (VDBG) {
            android.telecom.Log.v(TAG, "getGeoDescription('" + str + "')...", new java.lang.Object[0]);
        }
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        com.android.i18n.phonenumbers.PhoneNumberUtil phoneNumberUtil = com.android.i18n.phonenumbers.PhoneNumberUtil.getInstance();
        com.android.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder = com.android.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder.getInstance();
        java.util.Locale locale = context.getResources().getConfiguration().locale;
        java.lang.String currentCountryIso = getCurrentCountryIso(context, locale);
        try {
            if (VDBG) {
                android.telecom.Log.v(TAG, "parsing '" + str + "' for countryIso '" + currentCountryIso + "'...", new java.lang.Object[0]);
            }
            phoneNumber = phoneNumberUtil.parse(str, currentCountryIso);
            try {
                if (VDBG) {
                    android.telecom.Log.v(TAG, "- parsed number: " + phoneNumber, new java.lang.Object[0]);
                }
            } catch (com.android.i18n.phonenumbers.NumberParseException e) {
                android.telecom.Log.w(TAG, "getGeoDescription: NumberParseException for incoming number '" + android.telecom.Log.pii(str) + "'", new java.lang.Object[0]);
                if (phoneNumber != null) {
                }
            }
        } catch (com.android.i18n.phonenumbers.NumberParseException e2) {
            phoneNumber = null;
        }
        if (phoneNumber != null) {
            return null;
        }
        java.lang.String descriptionForNumber = phoneNumberOfflineGeocoder.getDescriptionForNumber(phoneNumber, locale);
        if (VDBG) {
            android.telecom.Log.v(TAG, "- got description: '" + descriptionForNumber + "'", new java.lang.Object[0]);
        }
        return descriptionForNumber;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static java.lang.String getCurrentCountryIso(android.content.Context context, java.util.Locale locale) {
        java.lang.String str;
        android.location.CountryDetector countryDetector = (android.location.CountryDetector) context.getSystemService(android.content.Context.COUNTRY_DETECTOR);
        if (countryDetector != null) {
            android.location.Country detectCountry = countryDetector.detectCountry();
            if (detectCountry == null) {
                android.telecom.Log.e(TAG, (java.lang.Throwable) new java.lang.Exception(), "CountryDetector.detectCountry() returned null.", new java.lang.Object[0]);
            } else {
                str = detectCountry.getCountryIso();
                if (str != null) {
                    java.lang.String country = locale.getCountry();
                    android.telecom.Log.w(TAG, "No CountryDetector; falling back to countryIso based on locale: " + country, new java.lang.Object[0]);
                    return country;
                }
                return str;
            }
        }
        str = null;
        if (str != null) {
        }
    }

    protected static java.lang.String getCurrentCountryIso(android.content.Context context) {
        return getCurrentCountryIso(context, java.util.Locale.getDefault());
    }

    public java.lang.String toString() {
        return new java.lang.StringBuilder(128).append(super.toString() + " { ").append("name " + (this.name == null ? "null" : "non-null")).append(", phoneNumber " + (this.phoneNumber != null ? "non-null" : "null")).append(" }").toString();
    }
}
