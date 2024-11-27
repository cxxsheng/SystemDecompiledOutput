package android.os;

/* loaded from: classes3.dex */
public class DropBoxManager {
    public static final java.lang.String ACTION_DROPBOX_ENTRY_ADDED = "android.intent.action.DROPBOX_ENTRY_ADDED";
    public static final java.lang.String EXTRA_DROPPED_COUNT = "android.os.extra.DROPPED_COUNT";
    public static final java.lang.String EXTRA_TAG = "tag";
    public static final java.lang.String EXTRA_TIME = "time";
    private static final int HAS_BYTE_ARRAY = 8;
    public static final int IS_EMPTY = 1;
    public static final int IS_GZIPPED = 4;
    public static final int IS_TEXT = 2;
    private static final java.lang.String TAG = "DropBoxManager";
    private final android.content.Context mContext;
    private final com.android.internal.os.IDropBoxManagerService mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static class Entry implements android.os.Parcelable, java.io.Closeable {
        public static final android.os.Parcelable.Creator<android.os.DropBoxManager.Entry> CREATOR = new android.os.Parcelable.Creator() { // from class: android.os.DropBoxManager.Entry.1
            @Override // android.os.Parcelable.Creator
            public android.os.DropBoxManager.Entry[] newArray(int i) {
                return new android.os.DropBoxManager.Entry[i];
            }

            @Override // android.os.Parcelable.Creator
            public android.os.DropBoxManager.Entry createFromParcel(android.os.Parcel parcel) {
                java.lang.String readString = parcel.readString();
                long readLong = parcel.readLong();
                int readInt = parcel.readInt();
                if ((readInt & 8) != 0) {
                    return new android.os.DropBoxManager.Entry(readString, readLong, parcel.createByteArray(), readInt & (-9));
                }
                return new android.os.DropBoxManager.Entry(readString, readLong, android.os.ParcelFileDescriptor.CREATOR.createFromParcel(parcel), readInt);
            }
        };
        private final byte[] mData;
        private final android.os.ParcelFileDescriptor mFileDescriptor;
        private final int mFlags;
        private final java.lang.String mTag;
        private final long mTimeMillis;

        public Entry(java.lang.String str, long j) {
            if (str == null) {
                throw new java.lang.NullPointerException("tag == null");
            }
            this.mTag = str;
            this.mTimeMillis = j;
            this.mData = null;
            this.mFileDescriptor = null;
            this.mFlags = 1;
        }

        public Entry(java.lang.String str, long j, java.lang.String str2) {
            if (str == null) {
                throw new java.lang.NullPointerException("tag == null");
            }
            if (str2 == null) {
                throw new java.lang.NullPointerException("text == null");
            }
            this.mTag = str;
            this.mTimeMillis = j;
            this.mData = str2.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            this.mFileDescriptor = null;
            this.mFlags = 2;
        }

        public Entry(java.lang.String str, long j, byte[] bArr, int i) {
            if (str == null) {
                throw new java.lang.NullPointerException("tag == null");
            }
            if (((i & 1) != 0) != (bArr == null)) {
                throw new java.lang.IllegalArgumentException("Bad flags: " + i);
            }
            this.mTag = str;
            this.mTimeMillis = j;
            this.mData = bArr;
            this.mFileDescriptor = null;
            this.mFlags = i;
        }

        public Entry(java.lang.String str, long j, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) {
            if (str == null) {
                throw new java.lang.NullPointerException("tag == null");
            }
            if (((i & 1) != 0) != (parcelFileDescriptor == null)) {
                throw new java.lang.IllegalArgumentException("Bad flags: " + i);
            }
            this.mTag = str;
            this.mTimeMillis = j;
            this.mData = null;
            this.mFileDescriptor = parcelFileDescriptor;
            this.mFlags = i;
        }

        public Entry(java.lang.String str, long j, java.io.File file, int i) throws java.io.IOException {
            if (str == null) {
                throw new java.lang.NullPointerException("tag == null");
            }
            if ((i & 1) != 0) {
                throw new java.lang.IllegalArgumentException("Bad flags: " + i);
            }
            this.mTag = str;
            this.mTimeMillis = j;
            this.mData = null;
            this.mFileDescriptor = android.os.ParcelFileDescriptor.open(file, 268435456);
            this.mFlags = i;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            try {
                if (this.mFileDescriptor != null) {
                    this.mFileDescriptor.close();
                }
            } catch (java.io.IOException e) {
            }
        }

        public java.lang.String getTag() {
            return this.mTag;
        }

        public long getTimeMillis() {
            return this.mTimeMillis;
        }

        public int getFlags() {
            return this.mFlags & (-5);
        }

        public java.lang.String getText(int i) {
            java.io.InputStream inputStream;
            java.io.InputStream inputStream2 = null;
            if ((this.mFlags & 2) == 0) {
                return null;
            }
            if (this.mData != null) {
                return new java.lang.String(this.mData, 0, java.lang.Math.min(i, this.mData.length));
            }
            try {
                inputStream = getInputStream();
                if (inputStream == null) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (java.io.IOException e) {
                        }
                    }
                    return null;
                }
                try {
                    byte[] bArr = new byte[i];
                    int i2 = 0;
                    int i3 = 0;
                    while (i2 >= 0) {
                        i3 += i2;
                        if (i3 >= i) {
                            break;
                        }
                        i2 = inputStream.read(bArr, i3, i - i3);
                    }
                    java.lang.String str = new java.lang.String(bArr, 0, i3);
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (java.io.IOException e2) {
                        }
                    }
                    return str;
                } catch (java.io.IOException e3) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (java.io.IOException e4) {
                        }
                    }
                    return null;
                } catch (java.lang.Throwable th) {
                    th = th;
                    inputStream2 = inputStream;
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (java.io.IOException e5) {
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e6) {
                inputStream = null;
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        }

        public java.io.InputStream getInputStream() throws java.io.IOException {
            java.io.InputStream autoCloseInputStream;
            if (this.mData != null) {
                autoCloseInputStream = new java.io.ByteArrayInputStream(this.mData);
            } else if (this.mFileDescriptor != null) {
                autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(this.mFileDescriptor);
            } else {
                return null;
            }
            if ((this.mFlags & 4) == 0) {
                return autoCloseInputStream;
            }
            return new java.util.zip.GZIPInputStream(new java.io.BufferedInputStream(autoCloseInputStream));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return this.mFileDescriptor != null ? 1 : 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mTag);
            parcel.writeLong(this.mTimeMillis);
            if (this.mFileDescriptor != null) {
                parcel.writeInt(this.mFlags & (-9));
                this.mFileDescriptor.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(this.mFlags | 8);
                parcel.writeByteArray(this.mData);
            }
        }
    }

    public DropBoxManager(android.content.Context context, com.android.internal.os.IDropBoxManagerService iDropBoxManagerService) {
        this.mContext = context;
        this.mService = iDropBoxManagerService;
    }

    protected DropBoxManager() {
        this.mContext = null;
        this.mService = null;
    }

    public void addText(java.lang.String str, java.lang.String str2) {
        addData(str, str2.getBytes(java.nio.charset.StandardCharsets.UTF_8), 2);
    }

    public void addData(java.lang.String str, byte[] bArr, int i) {
        if (bArr == null) {
            throw new java.lang.NullPointerException("data == null");
        }
        try {
            this.mService.addData(str, bArr, i);
        } catch (android.os.RemoteException e) {
            if ((e instanceof android.os.TransactionTooLargeException) && this.mContext.getApplicationInfo().targetSdkVersion < 24) {
                android.util.Log.e(TAG, "App sent too much data, so it was ignored", e);
                return;
            }
            throw e.rethrowFromSystemServer();
        }
    }

    public void addFile(java.lang.String str, java.io.File file, int i) throws java.io.IOException {
        if (file == null) {
            throw new java.lang.NullPointerException("file == null");
        }
        try {
            android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, 268435456);
            try {
                this.mService.addFile(str, open, i);
                if (open != null) {
                    open.close();
                }
            } finally {
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isTagEnabled(java.lang.String str) {
        try {
            return this.mService.isTagEnabled(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.DropBoxManager.Entry getNextEntry(java.lang.String str, long j) {
        try {
            return this.mService.getNextEntryWithAttribution(str, j, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.SecurityException e2) {
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                throw e2;
            }
            android.util.Log.w(TAG, e2.getMessage());
            return null;
        }
    }
}
