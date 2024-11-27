package android.app;

/* loaded from: classes.dex */
public class GrammaticalInflectionManager {
    public static final java.util.Set<java.lang.Integer> VALID_GRAMMATICAL_GENDER_VALUES = java.util.Collections.unmodifiableSet(new java.util.HashSet(java.util.Arrays.asList(0, 1, 2, 3)));
    private final android.content.Context mContext;
    private final android.app.IGrammaticalInflectionManager mService;

    public GrammaticalInflectionManager(android.content.Context context, android.app.IGrammaticalInflectionManager iGrammaticalInflectionManager) {
        this.mContext = context;
        this.mService = iGrammaticalInflectionManager;
    }

    public int getApplicationGrammaticalGender() {
        return this.mContext.getApplicationContext().getResources().getConfiguration().getGrammaticalGender();
    }

    public void setRequestedApplicationGrammaticalGender(int i) {
        if (!VALID_GRAMMATICAL_GENDER_VALUES.contains(java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException("Unknown grammatical gender");
        }
        try {
            this.mService.setRequestedApplicationGrammaticalGender(this.mContext.getPackageName(), this.mContext.getUserId(), i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSystemWideGrammaticalGender(int i) {
        if (!VALID_GRAMMATICAL_GENDER_VALUES.contains(java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException("Unknown grammatical gender");
        }
        try {
            this.mService.setSystemWideGrammaticalGender(i, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSystemGrammaticalGender() {
        try {
            return this.mService.getSystemGrammaticalGender(this.mContext.getAttributionSource(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
