package android.util;

/* loaded from: classes3.dex */
public class StringBuilderPrinter implements android.util.Printer {
    private final java.lang.StringBuilder mBuilder;

    public StringBuilderPrinter(java.lang.StringBuilder sb) {
        this.mBuilder = sb;
    }

    @Override // android.util.Printer
    public void println(java.lang.String str) {
        this.mBuilder.append(str);
        int length = str.length();
        if (length <= 0 || str.charAt(length - 1) != '\n') {
            this.mBuilder.append('\n');
        }
    }
}
