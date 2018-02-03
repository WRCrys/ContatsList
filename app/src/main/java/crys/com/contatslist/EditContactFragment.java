package crys.com.contatslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import crys.com.contatslist.Utils.UniversalImageLoader;
import crys.com.contatslist.models.Contact;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cryst on 06/01/2018.
 */

public class EditContactFragment extends Fragment{

    private static final String TAG = "EditContactFragment";

    //This will evade the nullpointer exception whena adding to a new bundle from MainActivity
    public EditContactFragment(){
        super();
        setArguments(new Bundle());
    }

    private Contact mContact;
    private EditText mPhoneNumber, mName, mEmail;
    private CircleImageView mContactImage;
    private Spinner mSelectDevice;
    private Toolbar toolbar;
    private String mSelectedImagePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editcontact, container, false);
        Log.d(TAG, "onCreateView: started");

        //EditTexts
        mPhoneNumber = (EditText) view.findViewById(R.id.etContactPhone);
        mName = (EditText) view.findViewById(R.id.etContactName);
        mEmail = (EditText) view.findViewById(R.id.etContactEmail);
        //CircleImage
        mContactImage = (CircleImageView) view.findViewById(R.id.contactImage);
        //Spinner
        mSelectDevice = (Spinner) view.findViewById(R.id.selectDevice);
        //Toobar
        toolbar = (Toolbar) view.findViewById(R.id.editContactToolbar);

        //get the contact from the bundle
        mContact = getContactFromBundle();

        if(mContact != null){
            init();
        }

        return view;
    }

    private void init(){
        mPhoneNumber.setText(mContact.getPhonenumber());
        mName.setText(mContact.getName());
        mEmail.setText(mContact.getEmail());
        UniversalImageLoader.setImage(mContact.getProfileImage(), mContactImage, null, "http://");
        //Setting the selected device to the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.device_options,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectDevice.setAdapter(adapter);
        int position = adapter.getPosition(mContact.getDevice());
        mSelectDevice.setSelection(position);
    }

    /**
     * Retrieves the selected contacts from the bundle (coming from MainActivity)
     * @return
     */
    private Contact getContactFromBundle(){
        Log.d(TAG, "getContactFromBundle: arguments: "+getArguments());

        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getParcelable(getString(R.string.contact));
        }else {
            return null;
        }
    }
}
