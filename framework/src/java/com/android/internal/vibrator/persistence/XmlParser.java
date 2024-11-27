package com.android.internal.vibrator.persistence;

@java.lang.FunctionalInterface
/* loaded from: classes5.dex */
public interface XmlParser<T> {
    com.android.internal.vibrator.persistence.XmlSerializedVibration<T> parseTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException;
}
