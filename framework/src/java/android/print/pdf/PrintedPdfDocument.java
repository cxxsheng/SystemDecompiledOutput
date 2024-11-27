package android.print.pdf;

/* loaded from: classes3.dex */
public class PrintedPdfDocument extends android.graphics.pdf.PdfDocument {
    private static final int MILS_PER_INCH = 1000;
    private static final int POINTS_IN_INCH = 72;
    private final android.graphics.Rect mContentRect;
    private final int mPageHeight;
    private final int mPageWidth;

    public PrintedPdfDocument(android.content.Context context, android.print.PrintAttributes printAttributes) {
        android.print.PrintAttributes.MediaSize mediaSize = printAttributes.getMediaSize();
        this.mPageWidth = (int) ((mediaSize.getWidthMils() / 1000.0f) * 72.0f);
        this.mPageHeight = (int) ((mediaSize.getHeightMils() / 1000.0f) * 72.0f);
        android.print.PrintAttributes.Margins minMargins = printAttributes.getMinMargins();
        this.mContentRect = new android.graphics.Rect((int) ((minMargins.getLeftMils() / 1000.0f) * 72.0f), (int) ((minMargins.getTopMils() / 1000.0f) * 72.0f), this.mPageWidth - ((int) ((minMargins.getRightMils() / 1000.0f) * 72.0f)), this.mPageHeight - ((int) ((minMargins.getBottomMils() / 1000.0f) * 72.0f)));
    }

    public android.graphics.pdf.PdfDocument.Page startPage(int i) {
        return startPage(new android.graphics.pdf.PdfDocument.PageInfo.Builder(this.mPageWidth, this.mPageHeight, i).setContentRect(this.mContentRect).create());
    }

    public int getPageWidth() {
        return this.mPageWidth;
    }

    public int getPageHeight() {
        return this.mPageHeight;
    }

    public android.graphics.Rect getPageContentRect() {
        return this.mContentRect;
    }
}
