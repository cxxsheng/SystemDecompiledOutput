package android.media;

/* loaded from: classes2.dex */
public class MediaInserter {
    private final int mBufferSizePerUri;
    private final android.content.ContentProviderClient mProvider;
    private final java.util.HashMap<android.net.Uri, java.util.List<android.content.ContentValues>> mRowMap = new java.util.HashMap<>();
    private final java.util.HashMap<android.net.Uri, java.util.List<android.content.ContentValues>> mPriorityRowMap = new java.util.HashMap<>();

    public MediaInserter(android.content.ContentProviderClient contentProviderClient, int i) {
        this.mProvider = contentProviderClient;
        this.mBufferSizePerUri = i;
    }

    public void insert(android.net.Uri uri, android.content.ContentValues contentValues) throws android.os.RemoteException {
        insert(uri, contentValues, false);
    }

    public void insertwithPriority(android.net.Uri uri, android.content.ContentValues contentValues) throws android.os.RemoteException {
        insert(uri, contentValues, true);
    }

    private void insert(android.net.Uri uri, android.content.ContentValues contentValues, boolean z) throws android.os.RemoteException {
        java.util.HashMap<android.net.Uri, java.util.List<android.content.ContentValues>> hashMap = z ? this.mPriorityRowMap : this.mRowMap;
        java.util.List<android.content.ContentValues> list = hashMap.get(uri);
        if (list == null) {
            list = new java.util.ArrayList<>();
            hashMap.put(uri, list);
        }
        list.add(new android.content.ContentValues(contentValues));
        if (list.size() >= this.mBufferSizePerUri) {
            flushAllPriority();
            flush(uri, list);
        }
    }

    public void flushAll() throws android.os.RemoteException {
        flushAllPriority();
        for (android.net.Uri uri : this.mRowMap.keySet()) {
            flush(uri, this.mRowMap.get(uri));
        }
        this.mRowMap.clear();
    }

    private void flushAllPriority() throws android.os.RemoteException {
        for (android.net.Uri uri : this.mPriorityRowMap.keySet()) {
            flush(uri, this.mPriorityRowMap.get(uri));
        }
        this.mPriorityRowMap.clear();
    }

    private void flush(android.net.Uri uri, java.util.List<android.content.ContentValues> list) throws android.os.RemoteException {
        if (!list.isEmpty()) {
            this.mProvider.bulkInsert(uri, (android.content.ContentValues[]) list.toArray(new android.content.ContentValues[list.size()]));
            list.clear();
        }
    }
}
