package crys.com.contatslist;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import crys.com.contatslist.Utils.ContactPropertyListAdapter;
import crys.com.contatslist.Utils.DatabaseHelper;
import crys.com.contatslist.Utils.UniversalImageLoader;
import crys.com.contatslist.models.Contact;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cryst on 06/01/2018.
 */

public class ContactFragment extends Fragment{

    private static final String TAG = "ContactFragment";

    public interface OnEditContactListener{
        public void onEditContactSelected(Contact contact);

    }

    OnEditContactListener mOnEditContactListener;

    //This will evade the nullpointer exception whena adding to a new bundle from MainActivity
    public ContactFragment(){
        super();
        setArguments(new Bundle());
    }
    private Toolbar toolbar;
    private Contact mContact;
    private TextView mContactName;
    private CircleImageView mContactImage;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.contactToolbar);
        mContactName = (TextView) view.findViewById(R.id.tvName);
        mContactImage = (CircleImageView) view.findViewById(R.id.contactImage);
        mListView = (ListView) view.findViewById(R.id.lvContactProperties);
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

                mOnEditContactListener.onEditContactSelected(mContact);

            }
        });

        return view;
    }

    private void init(){
        mContactName.setText(mContact.getName());
        UniversalImageLoader.setImage(mContact.getProfileImage(), mContactImage, null, "");

        ArrayList<String> properties = new ArrayList<>();
        properties.add(mContact.getPhonenumber());
        properties.add(mContact.getEmail());
        ContactPropertyListAdapter adapter = new ContactPropertyListAdapter(getActivity(), R.layout.layout_cardview, properties);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
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
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                Cursor cursor = databaseHelper.getContactId(mContact);

                int contactID = -1;
                while (cursor.moveToNext()){
                    contactID = cursor.getInt(0);
                }
                if(contactID > -1){
                    if(databaseHelper.deteleContact(contactID) > 0){
                        Toast.makeText(getActivity(), "Contact Deleted", Toast.LENGTH_LONG).show();

                        //Clear teh arguments ont he current bundle since the contact is deleted
                        this.getArguments().clear();
                        //remove previous fragment from the backstack (therefore navigating back)
                        getActivity().getSupportFragmentManager().popBackStack();
                    }else {
                        Toast.makeText(getActivity(), "Contact Not Was Deleted, Database Error", Toast.LENGTH_LONG).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        Cursor cursor = databaseHelper.getContactId(mContact);
        int contactID = -1;
        while (cursor.moveToNext()){
            contactID = cursor.getInt(0);
        }
        if(contactID > -1){// If the contact doesn't exists then navigate back by popping the stack
            init();
        }else {
            this.getArguments().clear();//optional clear arguments but not necessary
            getActivity().getSupportFragmentManager().popBackStack();
        }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mOnEditContactListener = (OnEditContactListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: "+ e.getMessage() );
        }
    }
}
