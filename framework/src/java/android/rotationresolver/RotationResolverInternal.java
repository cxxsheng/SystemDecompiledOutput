package android.rotationresolver;

/* loaded from: classes3.dex */
public abstract class RotationResolverInternal {

    public interface RotationResolverCallbackInternal {
        void onFailure(int i);

        void onSuccess(int i);
    }

    public abstract boolean isRotationResolverSupported();

    public abstract void resolveRotation(android.rotationresolver.RotationResolverInternal.RotationResolverCallbackInternal rotationResolverCallbackInternal, java.lang.String str, int i, int i2, long j, android.os.CancellationSignal cancellationSignal);
}
