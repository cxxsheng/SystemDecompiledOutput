package com.android.server.wm;

/* loaded from: classes3.dex */
final class ActivityCallerState {
    private static final java.lang.String ATTR_CALLER_IS_SHARE_ENABLED = "caller_is_share_enabled";
    private static final java.lang.String ATTR_CALLER_PACKAGE = "caller_package";
    private static final java.lang.String ATTR_CALLER_UID = "caller_uid";
    private static final java.lang.String ATTR_PREFIX = "prefix";
    private static final java.lang.String ATTR_SOURCE_USER_ID = "source_user_id";
    private static final java.lang.String ATTR_URI = "uri";
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final java.lang.String TAG_INACCESSIBLE_CONTENT_URI = "inaccessible_content_uri";
    private static final java.lang.String TAG_READABLE_CONTENT_URI = "readable_content_uri";
    private static final java.lang.String TAG_WRITABLE_CONTENT_URI = "writable_content_uri";
    final com.android.server.wm.ActivityTaskManagerService mAtmService;
    private final java.util.WeakHashMap<android.os.IBinder, com.android.server.wm.ActivityCallerState.CallerInfo> mCallerTokenInfoMap = new java.util.WeakHashMap<>();

    ActivityCallerState(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this.mAtmService = activityTaskManagerService;
    }

    com.android.server.wm.ActivityCallerState.CallerInfo getCallerInfoOrNull(android.os.IBinder iBinder) {
        return this.mCallerTokenInfoMap.getOrDefault(iBinder, null);
    }

    boolean hasCaller(android.os.IBinder iBinder) {
        return getCallerInfoOrNull(iBinder) != null;
    }

    int getUid(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityCallerState.CallerInfo callerInfoOrNull = getCallerInfoOrNull(iBinder);
        if (callerInfoOrNull != null) {
            return callerInfoOrNull.mUid;
        }
        return -1;
    }

    java.lang.String getPackage(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityCallerState.CallerInfo callerInfoOrNull = getCallerInfoOrNull(iBinder);
        if (callerInfoOrNull != null) {
            return callerInfoOrNull.mPackageName;
        }
        return null;
    }

    boolean isShareIdentityEnabled(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityCallerState.CallerInfo callerInfoOrNull = getCallerInfoOrNull(iBinder);
        if (callerInfoOrNull != null) {
            return callerInfoOrNull.mIsShareIdentityEnabled;
        }
        return false;
    }

    void add(android.os.IBinder iBinder, com.android.server.wm.ActivityCallerState.CallerInfo callerInfo) {
        this.mCallerTokenInfoMap.put(iBinder, callerInfo);
    }

    void computeCallerInfo(android.os.IBinder iBinder, android.content.Intent intent, int i, java.lang.String str, boolean z) {
        com.android.server.wm.ActivityCallerState.CallerInfo callerInfo = new com.android.server.wm.ActivityCallerState.CallerInfo(i, str, z);
        this.mCallerTokenInfoMap.put(iBinder, callerInfo);
        android.util.ArraySet<android.net.Uri> contentUrisFromIntent = getContentUrisFromIntent(intent);
        for (int size = contentUrisFromIntent.size() - 1; size >= 0; size--) {
            android.net.Uri valueAt = contentUrisFromIntent.valueAt(size);
            boolean addContentUriIfUidHasPermission = addContentUriIfUidHasPermission(valueAt, i, 1, callerInfo.mReadableContentUris);
            boolean addContentUriIfUidHasPermission2 = addContentUriIfUidHasPermission(valueAt, i, 2, callerInfo.mWritableContentUris);
            if (!addContentUriIfUidHasPermission && !addContentUriIfUidHasPermission2) {
                callerInfo.mInaccessibleContentUris.add(convertToGrantUri(valueAt, 0));
            }
        }
    }

    boolean checkContentUriPermission(android.os.IBinder iBinder, com.android.server.uri.GrantUri grantUri, int i) {
        if (!android.content.Intent.isAccessUriMode(i)) {
            throw new java.lang.IllegalArgumentException("Mode flags are not access URI mode flags: " + i);
        }
        com.android.server.wm.ActivityCallerState.CallerInfo orDefault = this.mCallerTokenInfoMap.getOrDefault(iBinder, null);
        if (orDefault == null) {
            android.util.Slog.e(TAG, "Caller not found for checkContentUriPermission of: " + grantUri.uri.toSafeString());
            return false;
        }
        if (orDefault.mInaccessibleContentUris.contains(grantUri)) {
            return false;
        }
        boolean contains = orDefault.mReadableContentUris.contains(grantUri);
        boolean contains2 = orDefault.mWritableContentUris.contains(grantUri);
        if (!contains && !contains2) {
            throw new java.lang.IllegalArgumentException("The supplied URI wasn't passed at launch in #getData, #EXTRA_STREAM, nor #getClipData: " + grantUri.uri.toSafeString());
        }
        if (!((i & 1) != 0) || contains) {
            return !((i & 2) != 0) || contains2;
        }
        return false;
    }

    private boolean addContentUriIfUidHasPermission(android.net.Uri uri, int i, int i2, android.util.ArraySet<com.android.server.uri.GrantUri> arraySet) {
        com.android.server.uri.GrantUri convertToGrantUri = convertToGrantUri(uri, i2);
        if (this.mAtmService.mUgmInternal.checkUriPermission(convertToGrantUri, i, i2, true)) {
            arraySet.add(convertToGrantUri);
            return true;
        }
        return false;
    }

    private static com.android.server.uri.GrantUri convertToGrantUri(android.net.Uri uri, int i) {
        return new com.android.server.uri.GrantUri(android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getCallingUserId()), android.content.ContentProvider.getUriWithoutUserId(uri), i);
    }

    private static android.util.ArraySet<android.net.Uri> getContentUrisFromIntent(android.content.Intent intent) {
        android.util.ArraySet<android.net.Uri> arraySet = new android.util.ArraySet<>();
        if (intent == null) {
            return arraySet;
        }
        addUriIfContentUri(intent.getData(), arraySet);
        if (intent.hasExtra("android.intent.extra.STREAM")) {
            java.util.ArrayList<android.net.Uri> tryToUnparcelArrayListExtraStreamsUri = tryToUnparcelArrayListExtraStreamsUri(intent);
            if (tryToUnparcelArrayListExtraStreamsUri == null) {
                addUriIfContentUri(tryToUnparcelExtraStreamUri(intent), arraySet);
            } else {
                for (int size = tryToUnparcelArrayListExtraStreamsUri.size() - 1; size >= 0; size--) {
                    addUriIfContentUri(tryToUnparcelArrayListExtraStreamsUri.get(size), arraySet);
                }
            }
        }
        android.content.ClipData clipData = intent.getClipData();
        if (clipData == null) {
            return arraySet;
        }
        for (int i = 0; i < clipData.getItemCount(); i++) {
            android.content.ClipData.Item itemAt = clipData.getItemAt(i);
            addUriIfContentUri(itemAt.getUri(), arraySet);
            arraySet.addAll((android.util.ArraySet<? extends android.net.Uri>) getContentUrisFromIntent(itemAt.getIntent()));
        }
        return arraySet;
    }

    private static android.net.Uri tryToUnparcelExtraStreamUri(android.content.Intent intent) {
        try {
            return (android.net.Uri) intent.getParcelableExtra("android.intent.extra.STREAM", android.net.Uri.class);
        } catch (android.os.BadParcelableException e) {
            android.util.Slog.w(TAG, "Failed to unparcel an URI in EXTRA_STREAM, returning null: " + e);
            return null;
        }
    }

    private static java.util.ArrayList<android.net.Uri> tryToUnparcelArrayListExtraStreamsUri(android.content.Intent intent) {
        try {
            return intent.getParcelableArrayListExtra("android.intent.extra.STREAM", android.net.Uri.class);
        } catch (android.os.BadParcelableException e) {
            android.util.Slog.w(TAG, "Failed to unparcel an ArrayList of URIs in EXTRA_STREAM, returning null: " + e);
            return null;
        }
    }

    private static void addUriIfContentUri(android.net.Uri uri, android.util.ArraySet<android.net.Uri> arraySet) {
        if (uri != null && com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
            arraySet.add(uri);
        }
    }

    public static final class CallerInfo {
        final boolean mIsShareIdentityEnabled;
        final java.lang.String mPackageName;
        final int mUid;
        final android.util.ArraySet<com.android.server.uri.GrantUri> mReadableContentUris = new android.util.ArraySet<>();
        final android.util.ArraySet<com.android.server.uri.GrantUri> mWritableContentUris = new android.util.ArraySet<>();
        final android.util.ArraySet<com.android.server.uri.GrantUri> mInaccessibleContentUris = new android.util.ArraySet<>();

        CallerInfo(int i, java.lang.String str, boolean z) {
            this.mUid = i;
            this.mPackageName = str;
            this.mIsShareIdentityEnabled = z;
        }

        public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_CALLER_UID, this.mUid);
            if (this.mPackageName != null) {
                typedXmlSerializer.attribute((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_CALLER_PACKAGE, this.mPackageName);
            }
            typedXmlSerializer.attributeBoolean((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_CALLER_IS_SHARE_ENABLED, this.mIsShareIdentityEnabled);
            for (int size = this.mReadableContentUris.size() - 1; size >= 0; size--) {
                saveGrantUriToXml(typedXmlSerializer, this.mReadableContentUris.valueAt(size), com.android.server.wm.ActivityCallerState.TAG_READABLE_CONTENT_URI);
            }
            for (int size2 = this.mWritableContentUris.size() - 1; size2 >= 0; size2--) {
                saveGrantUriToXml(typedXmlSerializer, this.mWritableContentUris.valueAt(size2), com.android.server.wm.ActivityCallerState.TAG_WRITABLE_CONTENT_URI);
            }
            for (int size3 = this.mInaccessibleContentUris.size() - 1; size3 >= 0; size3--) {
                saveGrantUriToXml(typedXmlSerializer, this.mInaccessibleContentUris.valueAt(size3), com.android.server.wm.ActivityCallerState.TAG_INACCESSIBLE_CONTENT_URI);
            }
        }

        public static com.android.server.wm.ActivityCallerState.CallerInfo restoreFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            com.android.server.wm.ActivityCallerState.CallerInfo callerInfo = new com.android.server.wm.ActivityCallerState.CallerInfo(typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_CALLER_UID, 0), typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_CALLER_PACKAGE), typedXmlPullParser.getAttributeBoolean((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_CALLER_IS_SHARE_ENABLED, false));
            int depth = typedXmlPullParser.getDepth();
            while (true) {
                int next = typedXmlPullParser.next();
                if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() < depth)) {
                    break;
                }
                if (next == 2) {
                    java.lang.String name = typedXmlPullParser.getName();
                    if (com.android.server.wm.ActivityCallerState.TAG_READABLE_CONTENT_URI.equals(name)) {
                        callerInfo.mReadableContentUris.add(restoreGrantUriFromXml(typedXmlPullParser));
                    } else if (com.android.server.wm.ActivityCallerState.TAG_WRITABLE_CONTENT_URI.equals(name)) {
                        callerInfo.mWritableContentUris.add(restoreGrantUriFromXml(typedXmlPullParser));
                    } else if (com.android.server.wm.ActivityCallerState.TAG_INACCESSIBLE_CONTENT_URI.equals(name)) {
                        callerInfo.mInaccessibleContentUris.add(restoreGrantUriFromXml(typedXmlPullParser));
                    } else {
                        android.util.Slog.w(com.android.server.wm.ActivityCallerState.TAG, "restoreActivity: unexpected name=" + name);
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            }
            return callerInfo;
        }

        private void saveGrantUriToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.uri.GrantUri grantUri, java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            typedXmlSerializer.startTag((java.lang.String) null, str);
            typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_SOURCE_USER_ID, grantUri.sourceUserId);
            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_URI, java.lang.String.valueOf(grantUri.uri));
            typedXmlSerializer.attributeBoolean((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_PREFIX, grantUri.prefix);
            typedXmlSerializer.endTag((java.lang.String) null, str);
        }

        private static com.android.server.uri.GrantUri restoreGrantUriFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            return new com.android.server.uri.GrantUri(typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_SOURCE_USER_ID, 0), android.net.Uri.parse(typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_URI)), typedXmlPullParser.getAttributeBoolean((java.lang.String) null, com.android.server.wm.ActivityCallerState.ATTR_PREFIX, false) ? 128 : 0);
        }
    }
}
