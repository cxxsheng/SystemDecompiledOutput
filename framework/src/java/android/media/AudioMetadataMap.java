package android.media;

/* loaded from: classes2.dex */
public interface AudioMetadataMap extends android.media.AudioMetadataReadMap {
    <T> T remove(android.media.AudioMetadata.Key<T> key);

    <T> T set(android.media.AudioMetadata.Key<T> key, T t);
}
