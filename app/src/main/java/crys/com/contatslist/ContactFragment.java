package crys.com.contatslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import crys.com.contatslist.Utils.UniversalImageLoader;
import crys.com.contatslist.models.Contact;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cryst on 06/01/2018.
 */

public class ContactFragment extends Fragment{

    private static final String TAG = "ContactFragment";
    //This will evade the nullpointer exception whena adding to a new bundle from MainActivity
    public ContactFragment(){
        super();
        setArguments(new Bundle());
    }
    private Toolbar toolbar;
    private Contact mContact;
    private TextView mContactName;
    private CircleImageView mContactImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.contactToolbar);
        mContactName = (TextView) view.findViewById(R.id.tvName);
        mContactImage = (CircleImageView) view.findViewById(R.id.contactImage);
        Log.d(TAG, "onCreateView: started");
        mContact = getContactFromBundle();



        //required for setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        init();

        //navigation for the backarrow
        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked back arrow.");
                //remove previous fragment from the backstack(therefore navigation back )
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        //navigate to the edit contact fragment to edit the contact selected
        ImageView ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked the edit icon");
                EditContactFragment fragment = new EditContactFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //Replace whatever is in the fragment_container view with this fragment,
                //and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(getString(R.string.edit_contact_fragment));
                Log.d(TAG, "onClick: fragment: " + getString(R.string.edit_contact_fragment));
                transaction.commit();
            }
        });

        return view;
    }

    private void init(){
        mContactName.setText(mContact.getName());
        UniversalImageLoader.setImage(mContact.getProfileImage(), mContactImage, null, "https://");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_delete:
                Log.d(TAG, "onOptionsItemSelected: deleting contacts");
        }
        return super.onOptionsItemSelected(item);
    }

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
