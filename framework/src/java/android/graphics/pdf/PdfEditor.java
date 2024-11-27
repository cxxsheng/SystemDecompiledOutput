package android.graphics.pdf;

/* loaded from: classes.dex */
public final class PdfEditor {
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private android.os.ParcelFileDescriptor mInput;
    private long mNativeDocument;
    private int mPageCount;

    private static native void nativeClose(long j);

    private static native int nativeGetPageCount(long j);

    private static native boolean nativeGetPageCropBox(long j, int i, android.graphics.Rect rect);

    private static native boolean nativeGetPageMediaBox(long j, int i, android.graphics.Rect rect);

    private static native void nativeGetPageSize(long j, int i, android.graphics.Point point);

    private static native long nativeOpen(int i, long j);

    private static native int nativeRemovePage(long j, int i);

    private static native boolean nativeScaleForPrinting(long j);

    private static native void nativeSetPageCropBox(long j, int i, android.graphics.Rect rect);

    private static native void nativeSetPageMediaBox(long j, int i, android.graphics.Rect rect);

    private static native void nativeSetTransformAndClip(long j, int i, long j2, int i2, int i3, int i4, int i5);

    private static native void nativeWrite(long j, int i);

    public PdfEditor(android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        if (parcelFileDescriptor == null) {
            throw new java.lang.NullPointerException("input cannot be null");
        }
        try {
            android.system.Os.lseek(parcelFileDescriptor.getFileDescriptor(), 0L, android.system.OsConstants.SEEK_SET);
            long j = android.system.Os.fstat(parcelFileDescriptor.getFileDescriptor()).st_size;
            this.mInput = parcelFileDescriptor;
            synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
                this.mNativeDocument = nativeOpen(this.mInput.getFd(), j);
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

    public int getPageCount() {
        throwIfClosed();
        return this.mPageCount;
    }

    public void removePage(int i) {
        throwIfClosed();
        throwIfPageNotInDocument(i);
        synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
            this.mPageCount = nativeRemovePage(this.mNativeDocument, i);
        }
    }

    public void setTransformAndClip(int i, android.graphics.Matrix matrix, android.graphics.Rect rect) {
        throwIfClosed();
        throwIfPageNotInDocument(i);
        throwIfNotNullAndNotAfine(matrix);
        if (matrix == null) {
            matrix = android.graphics.Matrix.IDENTITY_MATRIX;
        }
        if (rect == null) {
            android.graphics.Point point = new android.graphics.Point();
            getPageSize(i, point);
            synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
                nativeSetTransformAndClip(this.mNativeDocument, i, matrix.ni(), 0, 0, point.x, point.y);
            }
            return;
        }
        synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
            nativeSetTransformAndClip(this.mNativeDocument, i, matrix.ni(), rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public void getPageSize(int i, android.graphics.Point point) {
        throwIfClosed();
        throwIfOutSizeNull(point);
        throwIfPageNotInDocument(i);
        synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
            nativeGetPageSize(this.mNativeDocument, i, point);
        }
    }

    public boolean getPageMediaBox(int i, android.graphics.Rect rect) {
        boolean nativeGetPageMediaBox;
        throwIfClosed();
        throwIfOutMediaBoxNull(rect);
        throwIfPageNotInDocument(i);
        synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
            nativeGetPageMediaBox = nativeGetPageMediaBox(this.mNativeDocument, i, rect);
        }
        return nativeGetPageMediaBox;
    }

    public void setPageMediaBox(int i, android.graphics.Rect rect) {
        throwIfClosed();
        throwIfMediaBoxNull(rect);
        throwIfPageNotInDocument(i);
        synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
            nativeSetPageMediaBox(this.mNativeDocument, i, rect);
        }
    }

    public boolean getPageCropBox(int i, android.graphics.Rect rect) {
        boolean nativeGetPageCropBox;
        throwIfClosed();
        throwIfOutCropBoxNull(rect);
        throwIfPageNotInDocument(i);
        synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
            nativeGetPageCropBox = nativeGetPageCropBox(this.mNativeDocument, i, rect);
        }
        return nativeGetPageCropBox;
    }

    public void setPageCropBox(int i, android.graphics.Rect rect) {
        throwIfClosed();
        throwIfCropBoxNull(rect);
        throwIfPageNotInDocument(i);
        synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
            nativeSetPageCropBox(this.mNativeDocument, i, rect);
        }
    }

    public boolean shouldScaleForPrinting() {
        boolean nativeScaleForPrinting;
        throwIfClosed();
        synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
            nativeScaleForPrinting = nativeScaleForPrinting(this.mNativeDocument);
        }
        return nativeScaleForPrinting;
    }

    public void write(android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        try {
            throwIfClosed();
            synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
                nativeWrite(this.mNativeDocument, parcelFileDescriptor.getFd());
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
        }
    }

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
        if (this.mNativeDocument != 0) {
            synchronized (android.graphics.pdf.PdfRenderer.sPdfiumLock) {
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

    private void throwIfPageNotInDocument(int i) {
        if (i < 0 || i >= this.mPageCount) {
            throw new java.lang.IllegalArgumentException("Invalid page index");
        }
    }

    private void throwIfNotNullAndNotAfine(android.graphics.Matrix matrix) {
        if (matrix != null && !matrix.isAffine()) {
            throw new java.lang.IllegalStateException("Matrix must be afine");
        }
    }

    private void throwIfOutSizeNull(android.graphics.Point point) {
        if (point == null) {
            throw new java.lang.NullPointerException("outSize cannot be null");
        }
    }

    private void throwIfOutMediaBoxNull(android.graphics.Rect rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException("outMediaBox cannot be null");
        }
    }

    private void throwIfMediaBoxNull(android.graphics.Rect rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException("mediaBox cannot be null");
        }
    }

    private void throwIfOutCropBoxNull(android.graphics.Rect rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException("outCropBox cannot be null");
        }
    }

    private void throwIfCropBoxNull(android.graphics.Rect rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException("cropBox cannot be null");
        }
    }
}
