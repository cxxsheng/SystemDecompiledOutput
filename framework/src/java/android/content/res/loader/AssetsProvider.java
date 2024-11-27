package android.content.res.loader;

/* loaded from: classes.dex */
public interface AssetsProvider {
    default android.content.res.AssetFileDescriptor loadAssetFd(java.lang.String str, int i) {
        return null;
    }
}
