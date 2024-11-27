package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class LanguageTaggedString extends co.nstant.in.cbor.model.Array {
    public LanguageTaggedString(java.lang.String str, java.lang.String str2) {
        this(new co.nstant.in.cbor.model.UnicodeString(str), new co.nstant.in.cbor.model.UnicodeString(str2));
    }

    public LanguageTaggedString(co.nstant.in.cbor.model.UnicodeString unicodeString, co.nstant.in.cbor.model.UnicodeString unicodeString2) {
        setTag(38);
        java.util.Objects.requireNonNull(unicodeString);
        add(unicodeString);
        java.util.Objects.requireNonNull(unicodeString2);
        add(unicodeString2);
    }

    public co.nstant.in.cbor.model.UnicodeString getLanguage() {
        return (co.nstant.in.cbor.model.UnicodeString) getDataItems().get(0);
    }

    public co.nstant.in.cbor.model.UnicodeString getString() {
        return (co.nstant.in.cbor.model.UnicodeString) getDataItems().get(1);
    }
}
