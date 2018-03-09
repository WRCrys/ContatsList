package crys.com.contatslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cryst on 08/03/2018.
 */

public class AddContactFragment extends Fragment {

    private static final String TAG = "AddContactFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addcontact, container, false);
        Log.d(TAG, "onCreateView: started");
        return view;
    }
}
