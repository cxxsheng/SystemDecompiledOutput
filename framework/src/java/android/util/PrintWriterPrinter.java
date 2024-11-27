package android.util;

/* loaded from: classes3.dex */
public class PrintWriterPrinter implements android.util.Printer {
    private final java.io.PrintWriter mPW;

    public PrintWriterPrinter(java.io.PrintWriter printWriter) {
        this.mPW = printWriter;
    }

    @Override // android.util.Printer
    public void println(java.lang.String str) {
        this.mPW.println(str);
    }
}
