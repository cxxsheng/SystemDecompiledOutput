package org.apache.commons.math.exception.util;

/* loaded from: classes3.dex */
public class DummyLocalizable implements org.apache.commons.math.exception.util.Localizable {
    private static final long serialVersionUID = 8843275624471387299L;
    private final java.lang.String source;

    public DummyLocalizable(java.lang.String str) {
        this.source = str;
    }

    @Override // org.apache.commons.math.exception.util.Localizable
    public java.lang.String getSourceString() {
        return this.source;
    }

    @Override // org.apache.commons.math.exception.util.Localizable
    public java.lang.String getLocalizedString(java.util.Locale locale) {
        return this.source;
    }

    public java.lang.String toString() {
        return this.source;
    }
}
