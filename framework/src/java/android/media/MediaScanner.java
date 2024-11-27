package android.media;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public class MediaScanner implements java.lang.AutoCloseable {

    @java.lang.Deprecated
    private static final java.lang.String[] FILES_PRESCAN_PROJECTION = new java.lang.String[0];

    @java.lang.Deprecated
    private final android.net.Uri mAudioUri;

    @java.lang.Deprecated
    private final android.media.MediaScanner.MyMediaScannerClient mClient = new android.media.MediaScanner.MyMediaScannerClient();

    @java.lang.Deprecated
    private final android.content.Context mContext;

    @java.lang.Deprecated
    private java.lang.String mDefaultAlarmAlertFilename;

    @java.lang.Deprecated
    private java.lang.String mDefaultNotificationFilename;

    @java.lang.Deprecated
    private java.lang.String mDefaultRingtoneFilename;

    @java.lang.Deprecated
    private final android.net.Uri mFilesUri;

    @java.lang.Deprecated
    private android.media.MediaInserter mMediaInserter;

    @java.lang.Deprecated
    private final java.lang.String mPackageName;

    private static class FileEntry {

        @java.lang.Deprecated
        boolean mLastModifiedChanged;

        @java.lang.Deprecated
        long mRowId;

        @java.lang.Deprecated
        FileEntry(long j, java.lang.String str, long j2, int i) {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    @java.lang.Deprecated
    public MediaScanner(android.content.Context context, java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Deprecated
    private boolean isDrmEnabled() {
        throw new java.lang.UnsupportedOperationException();
    }

    private class MyMediaScannerClient implements android.media.MediaScannerClient {

        @java.lang.Deprecated
        private int mFileType;

        @java.lang.Deprecated
        private boolean mIsDrm;

        @java.lang.Deprecated
        private java.lang.String mMimeType;

        @java.lang.Deprecated
        private boolean mNoMedia;

        @java.lang.Deprecated
        private java.lang.String mPath;

        public MyMediaScannerClient() {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Deprecated
        public android.media.MediaScanner.FileEntry beginFile(java.lang.String str, java.lang.String str2, long j, long j2, boolean z, boolean z2) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // android.media.MediaScannerClient
        @java.lang.Deprecated
        public void scanFile(java.lang.String str, long j, long j2, boolean z, boolean z2) {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Deprecated
        public android.net.Uri doScanFile(java.lang.String str, java.lang.String str2, long j, long j2, boolean z, boolean z2, boolean z3) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // android.media.MediaScannerClient
        @java.lang.Deprecated
        public void handleStringTag(java.lang.String str, java.lang.String str2) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // android.media.MediaScannerClient
        @java.lang.Deprecated
        public void setMimeType(java.lang.String str) {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Deprecated
        private android.content.ContentValues toValues() {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Deprecated
        private android.net.Uri endFile(android.media.MediaScanner.FileEntry fileEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) throws android.os.RemoteException {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Deprecated
        private int getFileTypeFromDrm(java.lang.String str) {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    @java.lang.Deprecated
    private void prescan(java.lang.String str, boolean z) throws android.os.RemoteException {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Deprecated
    private void postscan(java.lang.String[] strArr) throws android.os.RemoteException {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Deprecated
    public android.net.Uri scanSingleFile(java.lang.String str, java.lang.String str2) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Deprecated
    public static boolean isNoMediaPath(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Deprecated
    android.media.MediaScanner.FileEntry makeEntryFor(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Deprecated
    private void setLocale(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        throw new java.lang.UnsupportedOperationException();
    }
}
