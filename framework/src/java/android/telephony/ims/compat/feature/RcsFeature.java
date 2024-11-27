package android.telephony.ims.compat.feature;

/* loaded from: classes3.dex */
public class RcsFeature extends android.telephony.ims.compat.feature.ImsFeature {
    private final com.android.ims.internal.IImsRcsFeature mImsRcsBinder = new com.android.ims.internal.IImsRcsFeature.Stub() { // from class: android.telephony.ims.compat.feature.RcsFeature.1
    };

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public void onFeatureReady() {
    }

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public void onFeatureRemoved() {
    }

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public final com.android.ims.internal.IImsRcsFeature getBinder() {
        return this.mImsRcsBinder;
    }
}
