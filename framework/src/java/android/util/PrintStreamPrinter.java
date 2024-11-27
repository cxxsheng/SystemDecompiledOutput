package android.util;

/* loaded from: classes3.dex */
public class PrintStreamPrinter implements android.util.Printer {
    private final java.io.PrintStream mPS;

    public PrintStreamPrinter(java.io.PrintStream printStream) {
        this.mPS = printStream;
    }

    @Override // android.util.Printer
    public void println(java.lang.String str) {
        this.mPS.println(str);
    }
}
