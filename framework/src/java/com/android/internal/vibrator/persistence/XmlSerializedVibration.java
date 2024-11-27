package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
public interface XmlSerializedVibration<T> {
    T deserialize();

    void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException;

    void writeContent(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException;
}
