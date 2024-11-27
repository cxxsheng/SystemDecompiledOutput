package com.android.server.tv;

/* loaded from: classes2.dex */
final class PersistentDataStore {
    private static final java.lang.String ATTR_ENABLED = "enabled";
    private static final java.lang.String ATTR_STRING = "string";
    private static final java.lang.String TAG = "TvInputManagerService";
    private static final java.lang.String TAG_BLOCKED_RATINGS = "blocked-ratings";
    private static final java.lang.String TAG_PARENTAL_CONTROLS = "parental-controls";
    private static final java.lang.String TAG_RATING = "rating";
    private static final java.lang.String TAG_TV_INPUT_MANAGER_STATE = "tv-input-manager-state";
    private final android.util.AtomicFile mAtomicFile;
    private boolean mBlockedRatingsChanged;
    private final android.content.Context mContext;
    private boolean mLoaded;
    private boolean mParentalControlsEnabled;
    private boolean mParentalControlsEnabledChanged;
    private final android.os.Handler mHandler = new android.os.Handler();
    private final java.util.List<android.media.tv.TvContentRating> mBlockedRatings = java.util.Collections.synchronizedList(new java.util.ArrayList());
    private final java.lang.Runnable mSaveRunnable = new java.lang.Runnable() { // from class: com.android.server.tv.PersistentDataStore.1
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.tv.PersistentDataStore.this.save();
        }
    };

    public PersistentDataStore(android.content.Context context, int i) {
        this.mContext = context;
        java.io.File userSystemDirectory = android.os.Environment.getUserSystemDirectory(i);
        if (!userSystemDirectory.exists() && !userSystemDirectory.mkdirs()) {
            throw new java.lang.IllegalStateException("User dir cannot be created: " + userSystemDirectory);
        }
        this.mAtomicFile = new android.util.AtomicFile(new java.io.File(userSystemDirectory, "tv-input-manager-state.xml"), "tv-input-state");
    }

    public boolean isParentalControlsEnabled() {
        loadIfNeeded();
        return this.mParentalControlsEnabled;
    }

    public void setParentalControlsEnabled(boolean z) {
        loadIfNeeded();
        if (this.mParentalControlsEnabled != z) {
            this.mParentalControlsEnabled = z;
            this.mParentalControlsEnabledChanged = true;
            postSave();
        }
    }

    public boolean isRatingBlocked(android.media.tv.TvContentRating tvContentRating) {
        loadIfNeeded();
        synchronized (this.mBlockedRatings) {
            try {
                java.util.Iterator<android.media.tv.TvContentRating> it = this.mBlockedRatings.iterator();
                while (it.hasNext()) {
                    if (tvContentRating.contains(it.next())) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.media.tv.TvContentRating[] getBlockedRatings() {
        loadIfNeeded();
        return (android.media.tv.TvContentRating[]) this.mBlockedRatings.toArray(new android.media.tv.TvContentRating[this.mBlockedRatings.size()]);
    }

    public void addBlockedRating(android.media.tv.TvContentRating tvContentRating) {
        loadIfNeeded();
        if (tvContentRating != null && !this.mBlockedRatings.contains(tvContentRating)) {
            this.mBlockedRatings.add(tvContentRating);
            this.mBlockedRatingsChanged = true;
            postSave();
        }
    }

    public void removeBlockedRating(android.media.tv.TvContentRating tvContentRating) {
        loadIfNeeded();
        if (tvContentRating != null && this.mBlockedRatings.contains(tvContentRating)) {
            this.mBlockedRatings.remove(tvContentRating);
            this.mBlockedRatingsChanged = true;
            postSave();
        }
    }

    private void loadIfNeeded() {
        if (!this.mLoaded) {
            load();
            this.mLoaded = true;
        }
    }

    private void clearState() {
        this.mBlockedRatings.clear();
        this.mParentalControlsEnabled = false;
    }

    private void load() {
        clearState();
        try {
            java.io.FileInputStream openRead = this.mAtomicFile.openRead();
            try {
                try {
                    loadFromXml(android.util.Xml.resolvePullParser(openRead));
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Slog.w(TAG, "Failed to load tv input manager persistent store data.", e);
                    clearState();
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(openRead);
            }
        } catch (java.io.FileNotFoundException e2) {
        }
    }

    private void postSave() {
        this.mHandler.removeCallbacks(this.mSaveRunnable);
        this.mHandler.post(this.mSaveRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save() {
        try {
            java.io.FileOutputStream startWrite = this.mAtomicFile.startWrite();
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                saveToXml(resolveSerializer);
                resolveSerializer.flush();
                this.mAtomicFile.finishWrite(startWrite);
                broadcastChangesIfNeeded();
            } catch (java.lang.Throwable th) {
                this.mAtomicFile.failWrite(startWrite);
                throw th;
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed to save tv input manager persistent store data.", e);
        }
    }

    private void broadcastChangesIfNeeded() {
        if (this.mParentalControlsEnabledChanged) {
            this.mParentalControlsEnabledChanged = false;
            this.mContext.sendBroadcastAsUser(new android.content.Intent("android.media.tv.action.PARENTAL_CONTROLS_ENABLED_CHANGED"), android.os.UserHandle.ALL);
        }
        if (this.mBlockedRatingsChanged) {
            this.mBlockedRatingsChanged = false;
            this.mContext.sendBroadcastAsUser(new android.content.Intent("android.media.tv.action.BLOCKED_RATINGS_CHANGED"), android.os.UserHandle.ALL);
        }
    }

    private void loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.internal.util.XmlUtils.beginDocument(typedXmlPullParser, TAG_TV_INPUT_MANAGER_STATE);
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals(TAG_BLOCKED_RATINGS)) {
                loadBlockedRatingsFromXml(typedXmlPullParser);
            } else if (typedXmlPullParser.getName().equals(TAG_PARENTAL_CONTROLS)) {
                this.mParentalControlsEnabled = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "enabled");
            }
        }
    }

    private void loadBlockedRatingsFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals(TAG_RATING)) {
                java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_STRING);
                if (android.text.TextUtils.isEmpty(attributeValue)) {
                    throw new org.xmlpull.v1.XmlPullParserException("Missing string attribute on rating");
                }
                this.mBlockedRatings.add(android.media.tv.TvContentRating.unflattenFromString(attributeValue));
            }
        }
    }

    private void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startDocument((java.lang.String) null, true);
        typedXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        typedXmlSerializer.startTag((java.lang.String) null, TAG_TV_INPUT_MANAGER_STATE);
        typedXmlSerializer.startTag((java.lang.String) null, TAG_BLOCKED_RATINGS);
        synchronized (this.mBlockedRatings) {
            try {
                for (android.media.tv.TvContentRating tvContentRating : this.mBlockedRatings) {
                    typedXmlSerializer.startTag((java.lang.String) null, TAG_RATING);
                    typedXmlSerializer.attribute((java.lang.String) null, ATTR_STRING, tvContentRating.flattenToString());
                    typedXmlSerializer.endTag((java.lang.String) null, TAG_RATING);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_BLOCKED_RATINGS);
        typedXmlSerializer.startTag((java.lang.String) null, TAG_PARENTAL_CONTROLS);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, "enabled", this.mParentalControlsEnabled);
        typedXmlSerializer.endTag((java.lang.String) null, TAG_PARENTAL_CONTROLS);
        typedXmlSerializer.endTag((java.lang.String) null, TAG_TV_INPUT_MANAGER_STATE);
        typedXmlSerializer.endDocument();
    }
}
