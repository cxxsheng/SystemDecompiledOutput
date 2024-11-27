package android.graphics.pdf;

/* loaded from: classes.dex */
public final class PdfRenderer implements java.lang.AutoCloseable {
    static final java.lang.Object sPdfiumLock = new java.lang.Object();
    private android.graphics.pdf.PdfRenderer.Page mCurrentPage;
    private android.os.ParcelFileDescriptor mInput;
    private long mNativeDocument;
    private final int mPageCount;
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final android.graphics.Point mTempPoint = new android.graphics.Point();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RenderMode {
    }

    private static native void nativeClose(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeClosePage(long j);

    private static native long nativeCreate(int i, long j);

    private static native int nativeGetPageCount(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeOpenPageAndGetSize(long j, int i, android.graphics.Point point);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeRenderPage(long j, long j2, long j3, int i, int i2, int i3, int i4, long j4, int i5);

    private static native boolean nativeScaleForPrinting(long j);

    public PdfRenderer(android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        if (parcelFileDescriptor == null) {
            throw new java.lang.NullPointerException("input cannot be null");
        }
        try {
            android.system.Os.lseek(parcelFileDescriptor.getFileDescriptor(), 0L, android.system.OsConstants.SEEK_SET);
            long j = android.system.Os.fstat(parcelFileDescriptor.getFileDescriptor()).st_size;
            this.mInput = parcelFileDescriptor;
            synchronized (sPdfiumLock) {
                this.mNativeDocument = nativeCreate(this.mInput.getFd(), j);
                try {
                    this.mPageCount = nativeGetPageCount(this.mNativeDocument);
                } catch (java.lang.Throwable th) {
                    nativeClose(this.mNativeDocument);
                    this.mNativeDocument = 0L;
                    throw th;
                }
            }
            this.mCloseGuard.open("close");
        } catch (android.system.ErrnoException e) {
            throw new java.lang.IllegalArgumentException("file descriptor not seekable");
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        throwIfClosed();
        throwIfPageOpened();
        doClose();
    }

    public int getPageCount() {
        throwIfClosed();
        return this.mPageCount;
    }

    public boolean shouldScaleForPrinting() {
        boolean nativeScaleForPrinting;
        throwIfClosed();
        synchronized (sPdfiumLock) {
            nativeScaleForPrinting = nativeScaleForPrinting(this.mNativeDocument);
        }
        return nativeScaleForPrinting;
    }

    public android.graphics.pdf.PdfRenderer.Page openPage(int i) {
        throwIfClosed();
        throwIfPageOpened();
        throwIfPageNotInDocument(i);
        this.mCurrentPage = new android.graphics.pdf.PdfRenderer.Page(i);
        return this.mCurrentPage;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            doClose();
        } finally {
            super.finalize();
        }
    }

    private void doClose() {
        if (this.mCurrentPage != null) {
            this.mCurrentPage.close();
            this.mCurrentPage = null;
        }
        if (this.mNativeDocument != 0) {
            synchronized (sPdfiumLock) {
                nativeClose(this.mNativeDocument);
            }
            this.mNativeDocument = 0L;
        }
        if (this.mInput != null) {
            libcore.io.IoUtils.closeQuietly(this.mInput);
            this.mInput = null;
        }
        this.mCloseGuard.close();
    }

    private void throwIfClosed() {
        if (this.mInput == null) {
            throw new java.lang.IllegalStateException("Already closed");
        }
    }

    private void throwIfPageOpened() {
        if (this.mCurrentPage != null) {
            throw new java.lang.IllegalStateException("Current page not closed");
        }
    }

    private void throwIfPageNotInDocument(int i) {
        if (i < 0 || i >= this.mPageCount) {
            throw new java.lang.IllegalArgumentException("Invalid page index");
        }
    }

    public final class Page implements java.lang.AutoCloseable {
        public static final int RENDER_MODE_FOR_DISPLAY = 1;
        public static final int RENDER_MODE_FOR_PRINT = 2;
        private final dalvik.system.CloseGuard mCloseGuard;
        private final int mHeight;
        private final int mIndex;
        private long mNativePage;
        private final int mWidth;

        private Page(int i) {
            this.mCloseGuard = dalvik.system.CloseGuard.get();
            android.graphics.Point point = android.graphics.pdf.PdfRenderer.this.mTempPoint;
            synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
                this.mNativePage = android.graphics.pdf.PdfRenderer.nativeOpenPageAndGetSize(android.graphics.pdf.PdfRenderer.this.mNativeDocument, i, point);
            }
            this.mIndex = i;
            this.mWidth = point.x;
            this.mHeight = point.y;
            this.mCloseGuard.open("close");
        }

        public int getIndex() {
            return this.mIndex;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public void render(android.graphics.Bitmap bitmap, android.graphics.Rect rect, android.graphics.Matrix matrix, int i) {
            android.graphics.Matrix matrix2;
            if (this.mNativePage == 0) {
                throw new java.lang.NullPointerException();
            }
            android.graphics.Bitmap bitmap2 = (android.graphics.Bitmap) com.android.internal.util.Preconditions.checkNotNull(bitmap, "bitmap null");
            if (bitmap2.getConfig() != android.graphics.Bitmap.Config.ARGB_8888) {
                throw new java.lang.IllegalArgumentException("Unsupported pixel format");
            }
            if (rect != null && (rect.left < 0 || rect.top < 0 || rect.right > bitmap2.getWidth() || rect.bottom > bitmap2.getHeight())) {
                throw new java.lang.IllegalArgumentException("destBounds not in destination");
            }
            if (matrix != null && !matrix.isAffine()) {
                throw new java.lang.IllegalArgumentException("transform not affine");
            }
            if (i != 2 && i != 1) {
                throw new java.lang.IllegalArgumentException("Unsupported render mode");
            }
            if (i == 2 && i == 1) {
                throw new java.lang.IllegalArgumentException("Only single render mode supported");
            }
            int i2 = rect != null ? rect.left : 0;
            int i3 = rect != null ? rect.top : 0;
            int width = rect != null ? rect.right : bitmap2.getWidth();
            int height = rect != null ? rect.bottom : bitmap2.getHeight();
            if (matrix != null) {
                matrix2 = matrix;
            } else {
                matrix2 = new android.graphics.Matrix();
                matrix2.postScale((width - i2) / getWidth(), (height - i3) / getHeight());
                matrix2.postTranslate(i2, i3);
            }
            long ni = matrix2.ni();
            synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
                android.graphics.pdf.PdfRenderer.nativeRenderPage(android.graphics.pdf.PdfRenderer.this.mNativeDocument, this.mNativePage, bitmap2.getNativeInstance(), i2, i3, width, height, ni, i);
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            throwIfClosed();
            doClose();
        }

        protected void finalize() throws java.lang.Throwable {
            try {
                if (this.mCloseGuard != null) {
                    this.mCloseGuard.warnIfOpen();
                }
                doClose();
            } finally {
                super.finalize();
            }
        }

        private void doClose() {
            if (this.mNativePage != 0) {
                synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
                    android.graphics.pdf.PdfRenderer.nativeClosePage(this.mNativePage);
                }
                this.mNativePage = 0L;
            }
            this.mCloseGuard.close();
            android.graphics.pdf.PdfRenderer.this.mCurrentPage = null;
        }

        private void throwIfClosed() {
            if (this.mNativePage == 0) {
                throw new java.lang.IllegalStateException("Already closed");
            }
        }
    }
}
