package android.service.carrier;

/* loaded from: classes3.dex */
public class CarrierMessagingClientService extends android.app.Service {
    private final android.service.carrier.CarrierMessagingClientService.ICarrierMessagingClientServiceImpl mImpl = new android.service.carrier.CarrierMessagingClientService.ICarrierMessagingClientServiceImpl();

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return this.mImpl.asBinder();
    }

    private class ICarrierMessagingClientServiceImpl extends android.service.carrier.ICarrierMessagingClientService.Stub {
        private ICarrierMessagingClientServiceImpl() {
        }
    }
}
