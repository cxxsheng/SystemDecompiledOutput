package com.android.internal.vibrator.persistence;

@java.lang.FunctionalInterface
/* loaded from: classes5.dex */
public interface XmlSerializer<T> {
    com.android.internal.vibrator.persistence.XmlSerializedVibration<T> serialize(T t) throws com.android.internal.vibrator.persistence.XmlSerializerException;
}
