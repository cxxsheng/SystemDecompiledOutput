package android.gesture;

/* loaded from: classes.dex */
public final class GestureLibraries {
    private GestureLibraries() {
    }

    public static android.gesture.GestureLibrary fromFile(java.lang.String str) {
        return fromFile(new java.io.File(str));
    }

    public static android.gesture.GestureLibrary fromFile(java.io.File file) {
        return new android.gesture.GestureLibraries.FileGestureLibrary(file);
    }

    public static android.gesture.GestureLibrary fromFileDescriptor(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        return new android.gesture.GestureLibraries.FileGestureLibrary(parcelFileDescriptor.getFileDescriptor());
    }

    public static android.gesture.GestureLibrary fromPrivateFile(android.content.Context context, java.lang.String str) {
        return fromFile(context.getFileStreamPath(str));
    }

    public static android.gesture.GestureLibrary fromRawResource(android.content.Context context, int i) {
        return new android.gesture.GestureLibraries.ResourceGestureLibrary(context, i);
    }

    private static class FileGestureLibrary extends android.gesture.GestureLibrary {
        private final java.io.FileDescriptor mFd;
        private final java.io.File mPath;

        public FileGestureLibrary(java.io.File file) {
            this.mPath = file;
            this.mFd = null;
        }

        public FileGestureLibrary(java.io.FileDescriptor fileDescriptor) {
            this.mPath = null;
            this.mFd = fileDescriptor;
        }

        @Override // android.gesture.GestureLibrary
        public boolean isReadOnly() {
            if (this.mPath != null) {
                return !this.mPath.canWrite();
            }
            return false;
        }

        @Override // android.gesture.GestureLibrary
        public boolean save() {
            if (!this.mStore.hasChanged()) {
                return true;
            }
            if (this.mPath != null) {
                java.io.File file = this.mPath;
                java.io.File parentFile = file.getParentFile();
                if (!parentFile.exists() && !parentFile.mkdirs()) {
                    return false;
                }
                try {
                    file.createNewFile();
                    this.mStore.save(new java.io.FileOutputStream(file), true);
                    return true;
                } catch (java.io.IOException e) {
                    android.util.Log.d(android.gesture.GestureConstants.LOG_TAG, "Could not save the gesture library in " + this.mPath, e);
                    return false;
                }
            }
            try {
                this.mStore.save(new java.io.FileOutputStream(this.mFd), true);
                return true;
            } catch (java.io.IOException e2) {
                android.util.Log.d(android.gesture.GestureConstants.LOG_TAG, "Could not save the gesture library", e2);
                return false;
            }
        }

        @Override // android.gesture.GestureLibrary
        public boolean load() {
            if (this.mPath != null) {
                java.io.File file = this.mPath;
                if (file.exists() && file.canRead()) {
                    try {
                        this.mStore.load(new java.io.FileInputStream(file), true);
                        return true;
                    } catch (java.io.IOException e) {
                        android.util.Log.d(android.gesture.GestureConstants.LOG_TAG, "Could not load the gesture library from " + this.mPath, e);
                    }
                }
                return false;
            }
            try {
                this.mStore.load(new java.io.FileInputStream(this.mFd), true);
                return true;
            } catch (java.io.IOException e2) {
                android.util.Log.d(android.gesture.GestureConstants.LOG_TAG, "Could not load the gesture library", e2);
                return false;
            }
        }
    }

    private static class ResourceGestureLibrary extends android.gesture.GestureLibrary {
        private final java.lang.ref.WeakReference<android.content.Context> mContext;
        private final int mResourceId;

        public ResourceGestureLibrary(android.content.Context context, int i) {
            this.mContext = new java.lang.ref.WeakReference<>(context);
            this.mResourceId = i;
        }

        @Override // android.gesture.GestureLibrary
        public boolean isReadOnly() {
            return true;
        }

        @Override // android.gesture.GestureLibrary
        public boolean save() {
            return false;
        }

        @Override // android.gesture.GestureLibrary
        public boolean load() {
            android.content.Context context = this.mContext.get();
            if (context != null) {
                try {
                    this.mStore.load(context.getResources().openRawResource(this.mResourceId), true);
                    return true;
                } catch (java.io.IOException e) {
                    android.util.Log.d(android.gesture.GestureConstants.LOG_TAG, "Could not load the gesture library from raw resource " + context.getResources().getResourceName(this.mResourceId), e);
                }
            }
            return false;
        }
    }
}
