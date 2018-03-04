package crys.com.contatslist.Utils;

import android.Manifest;

/**
 * Created by cryst on 14/01/2018.
 */

public class Init {

    public Init() {
    }

    public static final String[] PHONE_PERMISSIONS = {Manifest.permission.CALL_PHONE};

    public static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    public static final int CAMERA_REQUEST_CODE = 5;
}
