package android.media;

/* loaded from: classes2.dex */
public interface AudioMetadataReadMap {
    <T> boolean containsKey(android.media.AudioMetadata.Key<T> key);

    android.media.AudioMetadataMap dup();

    <T> T get(android.media.AudioMetadata.Key<T> key);

    java.util.Set<android.media.AudioMetadata.Key<?>> keySet();

    int size();
}
