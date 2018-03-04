package crys.com.contatslist.Utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import crys.com.contatslist.R;

/**
 * Created by cryst on 03/03/2018.
 */

public class ChangePhotoDialog extends DialogFragment{

    private static final String TAG = "ChangePhotoDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_changephoto, container, false);

        return view;
    }
}
