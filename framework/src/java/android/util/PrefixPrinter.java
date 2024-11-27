package android.util;

/* loaded from: classes3.dex */
public class PrefixPrinter implements android.util.Printer {
    private final java.lang.String mPrefix;
    private final android.util.Printer mPrinter;

    public static android.util.Printer create(android.util.Printer printer, java.lang.String str) {
        if (str == null || str.equals("")) {
            return printer;
        }
        return new android.util.PrefixPrinter(printer, str);
    }

    private PrefixPrinter(android.util.Printer printer, java.lang.String str) {
        this.mPrinter = printer;
        this.mPrefix = str;
    }

    @Override // android.util.Printer
    public void println(java.lang.String str) {
        this.mPrinter.println(this.mPrefix + str);
    }
}
