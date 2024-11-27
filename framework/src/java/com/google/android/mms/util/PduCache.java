package com.google.android.mms.util;

/* loaded from: classes5.dex */
public final class PduCache extends com.google.android.mms.util.AbstractCache<android.net.Uri, com.google.android.mms.util.PduCacheEntry> {
    private static final boolean DEBUG = false;
    private static final boolean LOCAL_LOGV = false;
    private static final java.util.HashMap<java.lang.Integer, java.lang.Integer> MATCH_TO_MSGBOX_ID_MAP;
    private static final int MMS_ALL = 0;
    private static final int MMS_ALL_ID = 1;
    private static final int MMS_CONVERSATION = 10;
    private static final int MMS_CONVERSATION_ID = 11;
    private static final int MMS_DRAFTS = 6;
    private static final int MMS_DRAFTS_ID = 7;
    private static final int MMS_INBOX = 2;
    private static final int MMS_INBOX_ID = 3;
    private static final int MMS_OUTBOX = 8;
    private static final int MMS_OUTBOX_ID = 9;
    private static final int MMS_SENT = 4;
    private static final int MMS_SENT_ID = 5;
    private static final java.lang.String TAG = "PduCache";
    private static final android.content.UriMatcher URI_MATCHER = new android.content.UriMatcher(-1);
    private static com.google.android.mms.util.PduCache sInstance;
    private final java.util.HashMap<java.lang.Integer, java.util.HashSet<android.net.Uri>> mMessageBoxes = new java.util.HashMap<>();
    private final java.util.HashMap<java.lang.Long, java.util.HashSet<android.net.Uri>> mThreads = new java.util.HashMap<>();
    private final java.util.HashSet<android.net.Uri> mUpdating = new java.util.HashSet<>();

    static {
        URI_MATCHER.addURI("mms", null, 0);
        URI_MATCHER.addURI("mms", "#", 1);
        URI_MATCHER.addURI("mms", "inbox", 2);
        URI_MATCHER.addURI("mms", "inbox/#", 3);
        URI_MATCHER.addURI("mms", "sent", 4);
        URI_MATCHER.addURI("mms", "sent/#", 5);
        URI_MATCHER.addURI("mms", "drafts", 6);
        URI_MATCHER.addURI("mms", "drafts/#", 7);
        URI_MATCHER.addURI("mms", "outbox", 8);
        URI_MATCHER.addURI("mms", "outbox/#", 9);
        URI_MATCHER.addURI("mms-sms", "conversations", 10);
        URI_MATCHER.addURI("mms-sms", "conversations/#", 11);
        MATCH_TO_MSGBOX_ID_MAP = new java.util.HashMap<>();
        MATCH_TO_MSGBOX_ID_MAP.put(2, 1);
        MATCH_TO_MSGBOX_ID_MAP.put(4, 2);
        MATCH_TO_MSGBOX_ID_MAP.put(6, 3);
        MATCH_TO_MSGBOX_ID_MAP.put(8, 4);
    }

    private PduCache() {
    }

    public static final synchronized com.google.android.mms.util.PduCache getInstance() {
        com.google.android.mms.util.PduCache pduCache;
        synchronized (com.google.android.mms.util.PduCache.class) {
            if (sInstance == null) {
                sInstance = new com.google.android.mms.util.PduCache();
            }
            pduCache = sInstance;
        }
        return pduCache;
    }

    @Override // com.google.android.mms.util.AbstractCache
    public synchronized boolean put(android.net.Uri uri, com.google.android.mms.util.PduCacheEntry pduCacheEntry) {
        boolean put;
        int messageBox = pduCacheEntry.getMessageBox();
        java.util.HashSet<android.net.Uri> hashSet = this.mMessageBoxes.get(java.lang.Integer.valueOf(messageBox));
        if (hashSet == null) {
            hashSet = new java.util.HashSet<>();
            this.mMessageBoxes.put(java.lang.Integer.valueOf(messageBox), hashSet);
        }
        long threadId = pduCacheEntry.getThreadId();
        java.util.HashSet<android.net.Uri> hashSet2 = this.mThreads.get(java.lang.Long.valueOf(threadId));
        if (hashSet2 == null) {
            hashSet2 = new java.util.HashSet<>();
            this.mThreads.put(java.lang.Long.valueOf(threadId), hashSet2);
        }
        android.net.Uri normalizeKey = normalizeKey(uri);
        put = super.put((com.google.android.mms.util.PduCache) normalizeKey, (android.net.Uri) pduCacheEntry);
        if (put) {
            hashSet.add(normalizeKey);
            hashSet2.add(normalizeKey);
        }
        setUpdating(uri, false);
        return put;
    }

    public synchronized void setUpdating(android.net.Uri uri, boolean z) {
        if (z) {
            this.mUpdating.add(uri);
        } else {
            this.mUpdating.remove(uri);
        }
    }

    public synchronized boolean isUpdating(android.net.Uri uri) {
        return this.mUpdating.contains(uri);
    }

    @Override // com.google.android.mms.util.AbstractCache
    public synchronized com.google.android.mms.util.PduCacheEntry purge(android.net.Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case 0:
            case 10:
                purgeAll();
                return null;
            case 1:
                return purgeSingleEntry(uri);
            case 2:
            case 4:
            case 6:
            case 8:
                purgeByMessageBox(MATCH_TO_MSGBOX_ID_MAP.get(java.lang.Integer.valueOf(match)));
                return null;
            case 3:
            case 5:
            case 7:
            case 9:
                return purgeSingleEntry(android.net.Uri.withAppendedPath(android.provider.Telephony.Mms.CONTENT_URI, uri.getLastPathSegment()));
            case 11:
                purgeByThreadId(android.content.ContentUris.parseId(uri));
                return null;
            default:
                return null;
        }
    }

    private com.google.android.mms.util.PduCacheEntry purgeSingleEntry(android.net.Uri uri) {
        this.mUpdating.remove(uri);
        com.google.android.mms.util.PduCacheEntry pduCacheEntry = (com.google.android.mms.util.PduCacheEntry) super.purge((com.google.android.mms.util.PduCache) uri);
        if (pduCacheEntry != null) {
            removeFromThreads(uri, pduCacheEntry);
            removeFromMessageBoxes(uri, pduCacheEntry);
            return pduCacheEntry;
        }
        return null;
    }

    @Override // com.google.android.mms.util.AbstractCache
    public synchronized void purgeAll() {
        super.purgeAll();
        this.mMessageBoxes.clear();
        this.mThreads.clear();
        this.mUpdating.clear();
    }

    private android.net.Uri normalizeKey(android.net.Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case 1:
                return uri;
            case 2:
            case 4:
            case 6:
            case 8:
            default:
                return null;
            case 3:
            case 5:
            case 7:
            case 9:
                return android.net.Uri.withAppendedPath(android.provider.Telephony.Mms.CONTENT_URI, uri.getLastPathSegment());
        }
    }

    private void purgeByMessageBox(java.lang.Integer num) {
        java.util.HashSet<android.net.Uri> remove;
        if (num != null && (remove = this.mMessageBoxes.remove(num)) != null) {
            java.util.Iterator<android.net.Uri> it = remove.iterator();
            while (it.hasNext()) {
                android.net.Uri next = it.next();
                this.mUpdating.remove(next);
                com.google.android.mms.util.PduCacheEntry pduCacheEntry = (com.google.android.mms.util.PduCacheEntry) super.purge((com.google.android.mms.util.PduCache) next);
                if (pduCacheEntry != null) {
                    removeFromThreads(next, pduCacheEntry);
                }
            }
        }
    }

    private void removeFromThreads(android.net.Uri uri, com.google.android.mms.util.PduCacheEntry pduCacheEntry) {
        java.util.HashSet<android.net.Uri> hashSet = this.mThreads.get(java.lang.Long.valueOf(pduCacheEntry.getThreadId()));
        if (hashSet != null) {
            hashSet.remove(uri);
        }
    }

    private void purgeByThreadId(long j) {
        java.util.HashSet<android.net.Uri> remove = this.mThreads.remove(java.lang.Long.valueOf(j));
        if (remove != null) {
            java.util.Iterator<android.net.Uri> it = remove.iterator();
            while (it.hasNext()) {
                android.net.Uri next = it.next();
                this.mUpdating.remove(next);
                com.google.android.mms.util.PduCacheEntry pduCacheEntry = (com.google.android.mms.util.PduCacheEntry) super.purge((com.google.android.mms.util.PduCache) next);
                if (pduCacheEntry != null) {
                    removeFromMessageBoxes(next, pduCacheEntry);
                }
            }
        }
    }

    private void removeFromMessageBoxes(android.net.Uri uri, com.google.android.mms.util.PduCacheEntry pduCacheEntry) {
        java.util.HashSet<android.net.Uri> hashSet = this.mThreads.get(java.lang.Long.valueOf(pduCacheEntry.getMessageBox()));
        if (hashSet != null) {
            hashSet.remove(uri);
        }
    }
}
