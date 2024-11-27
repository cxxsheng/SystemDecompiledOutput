package android.service.chooser;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements android.service.chooser.FeatureFlags {
    @Override // android.service.chooser.FeatureFlags
    public boolean chooserAlbumText() {
        return false;
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean chooserPayloadToggling() {
        return false;
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean enableChooserResult() {
        return false;
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean enableSharesheetMetadataExtra() {
        return false;
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean legacyChooserPinningRemoval() {
        return true;
    }
}
