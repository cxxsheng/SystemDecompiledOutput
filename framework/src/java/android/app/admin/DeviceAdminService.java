package android.app.admin;

/* loaded from: classes.dex */
public class DeviceAdminService extends android.app.Service {
    private final android.app.admin.DeviceAdminService.IDeviceAdminServiceImpl mImpl = new android.app.admin.DeviceAdminService.IDeviceAdminServiceImpl();

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return this.mImpl.asBinder();
    }

    private class IDeviceAdminServiceImpl extends android.app.admin.IDeviceAdminService.Stub {
        private IDeviceAdminServiceImpl() {
        }
    }
}
