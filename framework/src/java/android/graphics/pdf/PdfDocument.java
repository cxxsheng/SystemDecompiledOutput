package android.graphics.pdf;

/* loaded from: classes.dex */
public class PdfDocument {
    private android.graphics.pdf.PdfDocument.Page mCurrentPage;
    private final byte[] mChunk = new byte[4096];
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final java.util.List<android.graphics.pdf.PdfDocument.PageInfo> mPages = new java.util.ArrayList();
    private long mNativeDocument = nativeCreateDocument();

    private native void nativeClose(long j);

    private native long nativeCreateDocument();

    private native void nativeFinishPage(long j);

    private static native long nativeStartPage(long j, int i, int i2, int i3, int i4, int i5, int i6);

    private native void nativeWriteTo(long j, java.io.OutputStream outputStream, byte[] bArr);

    public PdfDocument() {
        this.mCloseGuard.open("close");
    }

    public android.graphics.pdf.PdfDocument.Page startPage(android.graphics.pdf.PdfDocument.PageInfo pageInfo) {
        throwIfClosed();
        throwIfCurrentPageNotFinished();
        if (pageInfo == null) {
            throw new java.lang.IllegalArgumentException("page cannot be null");
        }
        this.mCurrentPage = new android.graphics.pdf.PdfDocument.Page(new android.graphics.pdf.PdfDocument.PdfCanvas(nativeStartPage(this.mNativeDocument, pageInfo.mPageWidth, pageInfo.mPageHeight, pageInfo.mContentRect.left, pageInfo.mContentRect.top, pageInfo.mContentRect.right, pageInfo.mContentRect.bottom)), pageInfo);
        return this.mCurrentPage;
    }

    public void finishPage(android.graphics.pdf.PdfDocument.Page page) {
        throwIfClosed();
        if (page == null) {
            throw new java.lang.IllegalArgumentException("page cannot be null");
        }
        if (page != this.mCurrentPage) {
            throw new java.lang.IllegalStateException("invalid page");
        }
        if (page.isFinished()) {
            throw new java.lang.IllegalStateException("page already finished");
        }
        this.mPages.add(page.getInfo());
        this.mCurrentPage = null;
        nativeFinishPage(this.mNativeDocument);
        page.finish();
    }

    public void writeTo(java.io.OutputStream outputStream) throws java.io.IOException {
        throwIfClosed();
        throwIfCurrentPageNotFinished();
        if (outputStream == null) {
            throw new java.lang.IllegalArgumentException("out cannot be null!");
        }
        nativeWriteTo(this.mNativeDocument, outputStream, this.mChunk);
    }

    public java.util.List<android.graphics.pdf.PdfDocument.PageInfo> getPages() {
        return java.util.Collections.unmodifiableList(this.mPages);
    }

    public void close() {
        throwIfCurrentPageNotFinished();
        dispose();
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            dispose();
        } finally {
            super.finalize();
        }
    }

    private void dispose() {
        if (this.mNativeDocument != 0) {
            nativeClose(this.mNativeDocument);
            this.mCloseGuard.close();
            this.mNativeDocument = 0L;
        }
    }

    private void throwIfClosed() {
        if (this.mNativeDocument == 0) {
            throw new java.lang.IllegalStateException("document is closed!");
        }
    }

    private void throwIfCurrentPageNotFinished() {
        if (this.mCurrentPage != null) {
            throw new java.lang.IllegalStateException("Current page not finished!");
        }
    }

    private final class PdfCanvas extends android.graphics.Canvas {
        public PdfCanvas(long j) {
            super(j);
        }

        @Override // android.graphics.Canvas
        public void setBitmap(android.graphics.Bitmap bitmap) {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static final class PageInfo {
        private android.graphics.Rect mContentRect;
        private int mPageHeight;
        private int mPageNumber;
        private int mPageWidth;

        private PageInfo() {
        }

        public int getPageWidth() {
            return this.mPageWidth;
        }

        public int getPageHeight() {
            return this.mPageHeight;
        }

        public android.graphics.Rect getContentRect() {
            return this.mContentRect;
        }

        public int getPageNumber() {
            return this.mPageNumber;
        }

        public static final class Builder {
            private final android.graphics.pdf.PdfDocument.PageInfo mPageInfo = new android.graphics.pdf.PdfDocument.PageInfo();

            public Builder(int i, int i2, int i3) {
                if (i <= 0) {
                    throw new java.lang.IllegalArgumentException("page width must be positive");
                }
                if (i2 <= 0) {
                    throw new java.lang.IllegalArgumentException("page width must be positive");
                }
                if (i3 < 0) {
                    throw new java.lang.IllegalArgumentException("pageNumber must be non negative");
                }
                this.mPageInfo.mPageWidth = i;
                this.mPageInfo.mPageHeight = i2;
                this.mPageInfo.mPageNumber = i3;
            }

            public android.graphics.pdf.PdfDocument.PageInfo.Builder setContentRect(android.graphics.Rect rect) {
                if (rect != null && (rect.left < 0 || rect.top < 0 || rect.right > this.mPageInfo.mPageWidth || rect.bottom > this.mPageInfo.mPageHeight)) {
                    throw new java.lang.IllegalArgumentException("contentRect does not fit the page");
                }
                this.mPageInfo.mContentRect = rect;
                return this;
            }

            public android.graphics.pdf.PdfDocument.PageInfo create() {
                if (this.mPageInfo.mContentRect == null) {
                    this.mPageInfo.mContentRect = new android.graphics.Rect(0, 0, this.mPageInfo.mPageWidth, this.mPageInfo.mPageHeight);
                }
                return this.mPageInfo;
            }
        }
    }

    public static final class Page {
        private android.graphics.Canvas mCanvas;
        private final android.graphics.pdf.PdfDocument.PageInfo mPageInfo;

        private Page(android.graphics.Canvas canvas, android.graphics.pdf.PdfDocument.PageInfo pageInfo) {
            this.mCanvas = canvas;
            this.mPageInfo = pageInfo;
        }

        public android.graphics.Canvas getCanvas() {
            return this.mCanvas;
        }

        public android.graphics.pdf.PdfDocument.PageInfo getInfo() {
            return this.mPageInfo;
        }

        boolean isFinished() {
            return this.mCanvas == null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void finish() {
            if (this.mCanvas != null) {
                this.mCanvas.release();
                this.mCanvas = null;
            }
        }
    }
}
