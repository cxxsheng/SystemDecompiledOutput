package com.android.server.notification;

/* loaded from: classes2.dex */
public class ValidateNotificationPeople implements com.android.server.notification.NotificationSignalExtractor {
    private static final boolean ENABLE_PEOPLE_VALIDATOR = true;
    private static final int MAX_PEOPLE = 10;
    static final float NONE = 0.0f;
    private static final int PEOPLE_CACHE_SIZE = 200;
    private static final java.lang.String SETTING_ENABLE_PEOPLE_VALIDATOR = "validate_notification_people_enabled";
    static final float STARRED_CONTACT = 1.0f;
    static final float VALID_CONTACT = 0.5f;
    private android.content.Context mBaseContext;
    protected boolean mEnabled;
    private int mEvictionCount;
    private android.os.Handler mHandler;
    private android.database.ContentObserver mObserver;
    private android.util.LruCache<java.lang.String, com.android.server.notification.ValidateNotificationPeople.LookupResult> mPeopleCache;
    private com.android.server.notification.NotificationUsageStats mUsageStats;
    private java.util.Map<java.lang.Integer, android.content.Context> mUserToContextMap;
    private static final java.lang.String TAG = "ValidateNoPeople";
    private static final boolean VERBOSE = android.util.Log.isLoggable(TAG, 2);
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.lang.String[] LOOKUP_PROJECTION = {"_id", "lookup", "starred", "has_phone_number"};

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String[] PHONE_LOOKUP_PROJECTION = {"data4", "data1"};

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void initialize(android.content.Context context, com.android.server.notification.NotificationUsageStats notificationUsageStats) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Initializing  " + getClass().getSimpleName() + ".");
        }
        this.mUserToContextMap = new android.util.ArrayMap();
        this.mBaseContext = context;
        this.mUsageStats = notificationUsageStats;
        this.mPeopleCache = new android.util.LruCache<>(200);
        this.mEnabled = 1 == android.provider.Settings.Global.getInt(this.mBaseContext.getContentResolver(), SETTING_ENABLE_PEOPLE_VALIDATOR, 1);
        if (this.mEnabled) {
            this.mHandler = new android.os.Handler();
            this.mObserver = new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.notification.ValidateNotificationPeople.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z, android.net.Uri uri, int i) {
                    super.onChange(z, uri, i);
                    if ((com.android.server.notification.ValidateNotificationPeople.DEBUG || com.android.server.notification.ValidateNotificationPeople.this.mEvictionCount % 100 == 0) && com.android.server.notification.ValidateNotificationPeople.VERBOSE) {
                        android.util.Slog.i(com.android.server.notification.ValidateNotificationPeople.TAG, "mEvictionCount: " + com.android.server.notification.ValidateNotificationPeople.this.mEvictionCount);
                    }
                    com.android.server.notification.ValidateNotificationPeople.this.mPeopleCache.evictAll();
                    com.android.server.notification.ValidateNotificationPeople.this.mEvictionCount++;
                }
            };
            this.mBaseContext.getContentResolver().registerContentObserver(android.provider.ContactsContract.Contacts.CONTENT_URI, true, this.mObserver, -1);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void initForTests(android.content.Context context, com.android.server.notification.NotificationUsageStats notificationUsageStats, android.util.LruCache<java.lang.String, com.android.server.notification.ValidateNotificationPeople.LookupResult> lruCache) {
        this.mUserToContextMap = new android.util.ArrayMap();
        this.mBaseContext = context;
        this.mUsageStats = notificationUsageStats;
        this.mPeopleCache = lruCache;
        this.mEnabled = true;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public com.android.server.notification.RankingReconsideration process(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!this.mEnabled) {
            if (VERBOSE) {
                android.util.Slog.i(TAG, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
            }
            return null;
        }
        if (notificationRecord == null || notificationRecord.getNotification() == null) {
            if (VERBOSE) {
                android.util.Slog.i(TAG, "skipping empty notification");
            }
            return null;
        }
        if (notificationRecord.getUserId() == -1) {
            if (VERBOSE) {
                android.util.Slog.i(TAG, "skipping global notification");
            }
            return null;
        }
        android.content.Context contextAsUser = getContextAsUser(notificationRecord.getUser());
        if (contextAsUser == null) {
            if (VERBOSE) {
                android.util.Slog.i(TAG, "skipping notification that lacks a context");
            }
            return null;
        }
        return validatePeople(contextAsUser, notificationRecord);
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setConfig(com.android.server.notification.RankingConfig rankingConfig) {
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setZenHelper(com.android.server.notification.ZenModeHelper zenModeHelper) {
    }

    public float getContactAffinity(android.os.UserHandle userHandle, android.os.Bundle bundle, int i, float f) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "checking affinity for " + userHandle);
        }
        if (bundle == null) {
            return 0.0f;
        }
        java.lang.String l = java.lang.Long.toString(java.lang.System.nanoTime());
        float[] fArr = new float[1];
        android.content.Context contextAsUser = getContextAsUser(userHandle);
        if (contextAsUser == null) {
            return 0.0f;
        }
        final com.android.server.notification.ValidateNotificationPeople.PeopleRankingReconsideration validatePeople = validatePeople(contextAsUser, l, bundle, null, fArr, null);
        float f2 = fArr[0];
        if (validatePeople != null) {
            final java.util.concurrent.Semaphore semaphore = new java.util.concurrent.Semaphore(0);
            android.os.AsyncTask.THREAD_POOL_EXECUTOR.execute(new java.lang.Runnable() { // from class: com.android.server.notification.ValidateNotificationPeople.2
                @Override // java.lang.Runnable
                public void run() {
                    validatePeople.work();
                    semaphore.release();
                }
            });
            try {
                if (!semaphore.tryAcquire(i, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                    android.util.Slog.w(TAG, "Timeout while waiting for affinity: " + l + ". Returning timeoutAffinity=" + f);
                    return f;
                }
                return java.lang.Math.max(validatePeople.getContactAffinity(), f2);
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.w(TAG, "InterruptedException while waiting for affinity: " + l + ". Returning affinity=" + f2, e);
                return f2;
            }
        }
        return f2;
    }

    private android.content.Context getContextAsUser(android.os.UserHandle userHandle) {
        android.content.Context context = this.mUserToContextMap.get(java.lang.Integer.valueOf(userHandle.getIdentifier()));
        if (context == null) {
            try {
                context = this.mBaseContext.createPackageContextAsUser(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, 0, userHandle);
                this.mUserToContextMap.put(java.lang.Integer.valueOf(userHandle.getIdentifier()), context);
                return context;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.e(TAG, "failed to create package context for lookups", e);
                return context;
            }
        }
        return context;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.notification.RankingReconsideration validatePeople(android.content.Context context, com.android.server.notification.NotificationRecord notificationRecord) {
        boolean z;
        java.lang.String key = notificationRecord.getKey();
        android.os.Bundle bundle = notificationRecord.getNotification().extras;
        float[] fArr = new float[1];
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        com.android.server.notification.ValidateNotificationPeople.PeopleRankingReconsideration validatePeople = validatePeople(context, key, bundle, notificationRecord.getPeopleOverride(), fArr, arraySet);
        boolean z2 = false;
        float f = fArr[0];
        notificationRecord.setContactAffinity(f);
        if (arraySet.size() > 0) {
            notificationRecord.mergePhoneNumbers(arraySet);
        }
        if (validatePeople == null) {
            com.android.server.notification.NotificationUsageStats notificationUsageStats = this.mUsageStats;
            if (f > 0.0f) {
                z = true;
            } else {
                z = false;
            }
            if (f == 1.0f) {
                z2 = true;
            }
            notificationUsageStats.registerPeopleAffinity(notificationRecord, z, z2, true);
        } else {
            validatePeople.setRecord(notificationRecord);
        }
        return validatePeople;
    }

    private com.android.server.notification.ValidateNotificationPeople.PeopleRankingReconsideration validatePeople(android.content.Context context, java.lang.String str, android.os.Bundle bundle, java.util.List<java.lang.String> list, float[] fArr, android.util.ArraySet<java.lang.String> arraySet) {
        android.util.ArraySet<java.lang.String> phoneNumbers;
        if (bundle == null) {
            return null;
        }
        android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet(list);
        java.lang.String[] extraPeople = getExtraPeople(bundle);
        if (extraPeople != null) {
            arraySet2.addAll(java.util.Arrays.asList(extraPeople));
        }
        if (VERBOSE) {
            android.util.Slog.i(TAG, "Validating: " + str + " for " + context.getUserId());
        }
        java.util.LinkedList linkedList = new java.util.LinkedList();
        float f = 0.0f;
        int i = 0;
        for (java.lang.String str2 : arraySet2) {
            if (!android.text.TextUtils.isEmpty(str2)) {
                synchronized (this.mPeopleCache) {
                    try {
                        com.android.server.notification.ValidateNotificationPeople.LookupResult lookupResult = this.mPeopleCache.get(getCacheKey(context.getUserId(), str2));
                        if (lookupResult == null || lookupResult.isExpired()) {
                            linkedList.add(str2);
                        } else if (DEBUG) {
                            android.util.Slog.d(TAG, "using cached lookupResult");
                        }
                        if (lookupResult != null) {
                            f = java.lang.Math.max(f, lookupResult.getAffinity());
                            if (arraySet != null && (phoneNumbers = lookupResult.getPhoneNumbers()) != null && phoneNumbers.size() > 0) {
                                arraySet.addAll((android.util.ArraySet<? extends java.lang.String>) phoneNumbers);
                            }
                        }
                    } finally {
                    }
                }
                i++;
                if (i == 10) {
                }
            }
        }
        fArr[0] = f;
        if (linkedList.isEmpty()) {
            if (VERBOSE) {
                android.util.Slog.i(TAG, "final affinity: " + f);
            }
            return null;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Pending: future work scheduled for: " + str);
        }
        return new com.android.server.notification.ValidateNotificationPeople.PeopleRankingReconsideration(context, str, linkedList);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected static java.lang.String getCacheKey(int i, java.lang.String str) {
        return java.lang.Integer.toString(i) + ":" + str;
    }

    public static java.lang.String[] getExtraPeople(android.os.Bundle bundle) {
        return combineLists(getExtraPeopleForKey(bundle, "android.people"), getExtraPeopleForKey(bundle, "android.people.list"));
    }

    private static java.lang.String[] combineLists(java.lang.String[] strArr, java.lang.String[] strArr2) {
        if (strArr == null) {
            return strArr2;
        }
        if (strArr2 == null) {
            return strArr;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(strArr.length + strArr2.length);
        for (java.lang.String str : strArr) {
            arraySet.add(str);
        }
        for (java.lang.String str2 : strArr2) {
            arraySet.add(str2);
        }
        return (java.lang.String[]) arraySet.toArray(libcore.util.EmptyArray.STRING);
    }

    @android.annotation.Nullable
    private static java.lang.String[] getExtraPeopleForKey(android.os.Bundle bundle, java.lang.String str) {
        java.lang.Object obj = bundle.get(str);
        if (obj instanceof java.lang.String[]) {
            return (java.lang.String[]) obj;
        }
        int i = 0;
        if (obj instanceof java.util.ArrayList) {
            java.util.ArrayList arrayList = (java.util.ArrayList) obj;
            if (arrayList.isEmpty()) {
                return null;
            }
            if (arrayList.get(0) instanceof java.lang.String) {
                return (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
            }
            if (arrayList.get(0) instanceof java.lang.CharSequence) {
                int size = arrayList.size();
                java.lang.String[] strArr = new java.lang.String[size];
                while (i < size) {
                    strArr[i] = ((java.lang.CharSequence) arrayList.get(i)).toString();
                    i++;
                }
                return strArr;
            }
            if (!(arrayList.get(0) instanceof android.app.Person)) {
                return null;
            }
            int size2 = arrayList.size();
            java.lang.String[] strArr2 = new java.lang.String[size2];
            while (i < size2) {
                strArr2[i] = ((android.app.Person) arrayList.get(i)).resolveToLegacyUri();
                i++;
            }
            return strArr2;
        }
        if (obj instanceof java.lang.String) {
            return new java.lang.String[]{(java.lang.String) obj};
        }
        if (obj instanceof char[]) {
            return new java.lang.String[]{new java.lang.String((char[]) obj)};
        }
        if (obj instanceof java.lang.CharSequence) {
            return new java.lang.String[]{((java.lang.CharSequence) obj).toString()};
        }
        if (!(obj instanceof java.lang.CharSequence[])) {
            return null;
        }
        java.lang.CharSequence[] charSequenceArr = (java.lang.CharSequence[]) obj;
        int length = charSequenceArr.length;
        java.lang.String[] strArr3 = new java.lang.String[length];
        while (i < length) {
            strArr3[i] = charSequenceArr[i].toString();
            i++;
        }
        return strArr3;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected static class LookupResult {
        private static final long CONTACT_REFRESH_MILLIS = 3600000;
        private float mAffinity = 0.0f;
        private boolean mHasPhone = false;
        private java.lang.String mPhoneLookupKey = null;
        private android.util.ArraySet<java.lang.String> mPhoneNumbers = new android.util.ArraySet<>();
        private final long mExpireMillis = java.lang.System.currentTimeMillis() + 3600000;

        public void mergeContact(android.database.Cursor cursor) {
            this.mAffinity = java.lang.Math.max(this.mAffinity, 0.5f);
            int columnIndex = cursor.getColumnIndex("_id");
            if (columnIndex < 0) {
                android.util.Slog.i(com.android.server.notification.ValidateNotificationPeople.TAG, "invalid cursor: no _ID");
            } else {
                int i = cursor.getInt(columnIndex);
                if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                    android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "contact _ID is: " + i);
                }
            }
            int columnIndex2 = cursor.getColumnIndex("lookup");
            if (columnIndex2 >= 0) {
                this.mPhoneLookupKey = cursor.getString(columnIndex2);
                if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                    android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "contact LOOKUP_KEY is: " + this.mPhoneLookupKey);
                }
            } else if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "invalid cursor: no LOOKUP_KEY");
            }
            int columnIndex3 = cursor.getColumnIndex("starred");
            if (columnIndex3 >= 0) {
                boolean z = cursor.getInt(columnIndex3) != 0;
                if (z) {
                    this.mAffinity = java.lang.Math.max(this.mAffinity, 1.0f);
                }
                if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                    android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "contact STARRED is: " + z);
                }
            } else if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "invalid cursor: no STARRED");
            }
            int columnIndex4 = cursor.getColumnIndex("has_phone_number");
            if (columnIndex4 >= 0) {
                this.mHasPhone = cursor.getInt(columnIndex4) != 0;
                if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                    android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "contact HAS_PHONE_NUMBER is: " + this.mHasPhone);
                    return;
                }
                return;
            }
            if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "invalid cursor: no HAS_PHONE_NUMBER");
            }
        }

        public java.lang.String getPhoneLookupKey() {
            if (!this.mHasPhone) {
                return null;
            }
            return this.mPhoneLookupKey;
        }

        public void mergePhoneNumber(android.database.Cursor cursor) {
            int columnIndex = cursor.getColumnIndex("data4");
            if (columnIndex >= 0) {
                this.mPhoneNumbers.add(cursor.getString(columnIndex));
            } else if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "cursor data not found: no NORMALIZED_NUMBER");
            }
            int columnIndex2 = cursor.getColumnIndex("data1");
            if (columnIndex2 >= 0) {
                this.mPhoneNumbers.add(cursor.getString(columnIndex2));
            } else if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "cursor data not found: no NUMBER");
            }
        }

        public android.util.ArraySet<java.lang.String> getPhoneNumbers() {
            return this.mPhoneNumbers;
        }

        @com.android.internal.annotations.VisibleForTesting
        protected boolean isExpired() {
            return this.mExpireMillis < java.lang.System.currentTimeMillis();
        }

        private boolean isInvalid() {
            return this.mAffinity == 0.0f || isExpired();
        }

        public float getAffinity() {
            if (isInvalid()) {
                return 0.0f;
            }
            return this.mAffinity;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class PeopleRankingReconsideration extends com.android.server.notification.RankingReconsideration {
        private float mContactAffinity;
        private final android.content.Context mContext;
        private final java.util.LinkedList<java.lang.String> mPendingLookups;
        private android.util.ArraySet<java.lang.String> mPhoneNumbers;
        private com.android.server.notification.NotificationRecord mRecord;

        private PeopleRankingReconsideration(android.content.Context context, java.lang.String str, java.util.LinkedList<java.lang.String> linkedList) {
            super(str);
            this.mContactAffinity = 0.0f;
            this.mPhoneNumbers = null;
            this.mContext = context;
            this.mPendingLookups = linkedList;
        }

        @Override // com.android.server.notification.RankingReconsideration
        public void work() {
            com.android.server.notification.ValidateNotificationPeople.LookupResult lookupResult;
            if (com.android.server.notification.ValidateNotificationPeople.VERBOSE) {
                android.util.Slog.i(com.android.server.notification.ValidateNotificationPeople.TAG, "Executing: validation for: " + this.mKey);
            }
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            java.util.Iterator<java.lang.String> it = this.mPendingLookups.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                java.lang.String next = it.next();
                java.lang.String cacheKey = com.android.server.notification.ValidateNotificationPeople.getCacheKey(this.mContext.getUserId(), next);
                synchronized (com.android.server.notification.ValidateNotificationPeople.this.mPeopleCache) {
                    try {
                        lookupResult = (com.android.server.notification.ValidateNotificationPeople.LookupResult) com.android.server.notification.ValidateNotificationPeople.this.mPeopleCache.get(cacheKey);
                        if (lookupResult == null || lookupResult.isExpired()) {
                            r4 = false;
                        }
                    } finally {
                    }
                }
                if (!r4) {
                    android.net.Uri parse = android.net.Uri.parse(next);
                    if ("tel".equals(parse.getScheme())) {
                        if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                            android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "checking telephone URI: " + next);
                        }
                        lookupResult = resolvePhoneContact(this.mContext, parse.getSchemeSpecificPart());
                    } else if ("mailto".equals(parse.getScheme())) {
                        if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                            android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "checking mailto URI: " + next);
                        }
                        lookupResult = resolveEmailContact(this.mContext, parse.getSchemeSpecificPart());
                    } else if (next.startsWith(android.provider.ContactsContract.Contacts.CONTENT_LOOKUP_URI.toString())) {
                        if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                            android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "checking lookup URI: " + next);
                        }
                        lookupResult = searchContactsAndLookupNumbers(this.mContext, parse);
                    } else {
                        lookupResult = new com.android.server.notification.ValidateNotificationPeople.LookupResult();
                        if (!"name".equals(parse.getScheme())) {
                            android.util.Slog.w(com.android.server.notification.ValidateNotificationPeople.TAG, "unsupported URI " + next);
                        }
                    }
                }
                if (lookupResult != null) {
                    if (!r4) {
                        synchronized (com.android.server.notification.ValidateNotificationPeople.this.mPeopleCache) {
                            com.android.server.notification.ValidateNotificationPeople.this.mPeopleCache.put(cacheKey, lookupResult);
                        }
                    }
                    if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                        android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "lookup contactAffinity is " + lookupResult.getAffinity());
                    }
                    this.mContactAffinity = java.lang.Math.max(this.mContactAffinity, lookupResult.getAffinity());
                    if (lookupResult.getPhoneNumbers() != null) {
                        if (this.mPhoneNumbers == null) {
                            this.mPhoneNumbers = new android.util.ArraySet<>();
                        }
                        this.mPhoneNumbers.addAll((android.util.ArraySet<? extends java.lang.String>) lookupResult.getPhoneNumbers());
                    }
                } else if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                    android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "lookupResult is null");
                }
            }
            if (com.android.server.notification.ValidateNotificationPeople.DEBUG) {
                android.util.Slog.d(com.android.server.notification.ValidateNotificationPeople.TAG, "Validation finished in " + (java.lang.System.currentTimeMillis() - currentTimeMillis) + "ms");
            }
            if (this.mRecord != null) {
                com.android.server.notification.ValidateNotificationPeople.this.mUsageStats.registerPeopleAffinity(this.mRecord, this.mContactAffinity > 0.0f, this.mContactAffinity == 1.0f, false);
            }
        }

        private static com.android.server.notification.ValidateNotificationPeople.LookupResult resolvePhoneContact(android.content.Context context, java.lang.String str) {
            return searchContacts(context, android.net.Uri.withAppendedPath(android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI, android.net.Uri.encode(str)));
        }

        private static com.android.server.notification.ValidateNotificationPeople.LookupResult resolveEmailContact(android.content.Context context, java.lang.String str) {
            return searchContacts(context, android.net.Uri.withAppendedPath(android.provider.ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, android.net.Uri.encode(str)));
        }

        @com.android.internal.annotations.VisibleForTesting
        static com.android.server.notification.ValidateNotificationPeople.LookupResult searchContacts(android.content.Context context, android.net.Uri uri) {
            com.android.server.notification.ValidateNotificationPeople.LookupResult lookupResult = new com.android.server.notification.ValidateNotificationPeople.LookupResult();
            android.net.Uri createCorpLookupUriFromEnterpriseLookupUri = android.provider.ContactsContract.Contacts.createCorpLookupUriFromEnterpriseLookupUri(uri);
            if (createCorpLookupUriFromEnterpriseLookupUri == null) {
                addContacts(lookupResult, context, uri);
            } else {
                addWorkContacts(lookupResult, context, createCorpLookupUriFromEnterpriseLookupUri);
            }
            return lookupResult;
        }

        @com.android.internal.annotations.VisibleForTesting
        static com.android.server.notification.ValidateNotificationPeople.LookupResult searchContactsAndLookupNumbers(android.content.Context context, android.net.Uri uri) {
            com.android.server.notification.ValidateNotificationPeople.LookupResult searchContacts = searchContacts(context, uri);
            java.lang.String phoneLookupKey = searchContacts.getPhoneLookupKey();
            if (phoneLookupKey != null) {
                try {
                    android.database.Cursor query = context.getContentResolver().query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, com.android.server.notification.ValidateNotificationPeople.PHONE_LOOKUP_PROJECTION, "lookup = ?", new java.lang.String[]{phoneLookupKey}, null);
                    try {
                        if (query == null) {
                            android.util.Slog.w(com.android.server.notification.ValidateNotificationPeople.TAG, "Cursor is null when querying contact phone number.");
                            if (query != null) {
                                query.close();
                            }
                            return searchContacts;
                        }
                        while (query.moveToNext()) {
                            searchContacts.mergePhoneNumber(query);
                        }
                        query.close();
                    } finally {
                    }
                } catch (java.lang.Throwable th) {
                    android.util.Slog.w(com.android.server.notification.ValidateNotificationPeople.TAG, "Problem getting content resolver or querying phone numbers.", th);
                }
            }
            return searchContacts;
        }

        private static void addWorkContacts(com.android.server.notification.ValidateNotificationPeople.LookupResult lookupResult, android.content.Context context, android.net.Uri uri) {
            int findWorkUserId = findWorkUserId(context);
            if (findWorkUserId == -1) {
                android.util.Slog.w(com.android.server.notification.ValidateNotificationPeople.TAG, "Work profile user ID not found for work contact: " + uri);
                return;
            }
            addContacts(lookupResult, context, android.content.ContentProvider.maybeAddUserId(uri, findWorkUserId));
        }

        private static int findWorkUserId(android.content.Context context) {
            android.os.UserManager userManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
            for (int i : userManager.getProfileIds(context.getUserId(), true)) {
                if (userManager.isManagedProfile(i)) {
                    return i;
                }
            }
            return -1;
        }

        private static void addContacts(com.android.server.notification.ValidateNotificationPeople.LookupResult lookupResult, android.content.Context context, android.net.Uri uri) {
            try {
                android.database.Cursor query = context.getContentResolver().query(uri, com.android.server.notification.ValidateNotificationPeople.LOOKUP_PROJECTION, null, null, null);
                try {
                    if (query == null) {
                        android.util.Slog.w(com.android.server.notification.ValidateNotificationPeople.TAG, "Null cursor from contacts query.");
                        if (query != null) {
                            query.close();
                            return;
                        }
                        return;
                    }
                    while (query.moveToNext()) {
                        lookupResult.mergeContact(query);
                    }
                    query.close();
                } finally {
                }
            } catch (java.lang.Throwable th) {
                android.util.Slog.w(com.android.server.notification.ValidateNotificationPeople.TAG, "Problem getting content resolver or performing contacts query.", th);
            }
        }

        @Override // com.android.server.notification.RankingReconsideration
        public void applyChangesLocked(com.android.server.notification.NotificationRecord notificationRecord) {
            notificationRecord.setContactAffinity(java.lang.Math.max(this.mContactAffinity, notificationRecord.getContactAffinity()));
            if (com.android.server.notification.ValidateNotificationPeople.VERBOSE) {
                android.util.Slog.i(com.android.server.notification.ValidateNotificationPeople.TAG, "final affinity: " + notificationRecord.getContactAffinity());
            }
            notificationRecord.mergePhoneNumbers(this.mPhoneNumbers);
        }

        public float getContactAffinity() {
            return this.mContactAffinity;
        }

        public void setRecord(com.android.server.notification.NotificationRecord notificationRecord) {
            this.mRecord = notificationRecord;
        }
    }
}
