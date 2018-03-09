package crys.com.contatslist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import crys.com.contatslist.Utils.ContactListAdapter;
import crys.com.contatslist.models.Contact;

/**
 * Created by cryst on 02/01/2018.
 */

public class ViewContactsFragment extends Fragment {

    private static final String TAG = "ViewContactsFragment";

    //Mangekyou Sharingan - testImageURL = "i.pinimg.com/736x/36/14/7e/36147e5fd8754d737c14db5805f65ee7--mangekyou-sharingan-you-from.jpg";
    private String testImageURL = "i.pinimg.com/originals/84/93/d5/8493d5aa8bddfb3736250c40fca9dacc.jpg";

    public interface OnContactSelectedListener{
        public void OnContactSelected(Contact con);
    }
    OnContactSelectedListener mContactSelectedListener;

    public interface OnAddContactListener{
        public void onAddContact();
    }
    OnAddContactListener mOnAddContact;

    //Variables and widgets
    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;
    private int mAppBarState;

    private AppBarLayout viewContactsBar, searchBar;

    private ContactListAdapter adapter;

    private ListView contactsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_viewcontacts, container, false);
        viewContactsBar = (AppBarLayout) view.findViewById(R.id.viewContactsToolbar);
        searchBar = (AppBarLayout) view.findViewById(R.id.searchToolbar);
        contactsList = (ListView) view.findViewById(R.id.contactsList);
        Log.d(TAG, "onCreateView: started.");

        setAppBarState(STANDARD_APPBAR);

        setupContactsList();

        //Navigate to add contacts fragments
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked fab.");
                mOnAddContact.onAddContact();
            }
        });

        ImageView ivSearchContact = (ImageView) view.findViewById(R.id.ivSearchIcon);
        ivSearchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked search icon.");
                toggleTooBarState();
            }
        });

        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked back arrow.");
                toggleTooBarState();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mContactSelectedListener = (OnContactSelectedListener) getActivity();
            mOnAddContact = (OnAddContactListener) getActivity();
        }catch (ClassCastException e){
            Log.d(TAG, "onAttach: ClassCastException: "+ e.getMessage());
        }
    }

    //https://
    private void setupContactsList(){
        final ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Crystyano Almeida","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Uchiha","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Almeida","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Uchiha","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Almeida","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Uchiha","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Almeida","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Uchiha","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Almeida","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Uchiha","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Almeida","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Uchiha","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Almeida","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Uchiha","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Almeida","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Uchiha","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Almeida","(85) 997992233","Mobile","crys@crys.ca",testImageURL));
        contacts.add(new Contact("Crystyano Uchiha","(85) 997992233","Mobile","crys@crys.ca",testImageURL));

        adapter = new ContactListAdapter(getActivity(), R.layout.layout_contactlistitem, contacts, "https://");
        contactsList.setAdapter(adapter);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /* Testing the fragment open
                ContactFragment fragment = new ContactFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //Replace whatever is in the fragment_container view with this fragment,
                //and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(getString(R.string.contact_fragment));
                transaction.commit();
                 */
                //pass the contact to the interface and send it to MainActivity
                mContactSelectedListener.OnContactSelected(contacts.get(position));

            }
        });
    }

    /**
     * Initiates the appbar state toggle
     */

    private void toggleTooBarState() {
        Log.d(TAG, "toggleTooBarState: toggling AppBarState.");
        if (mAppBarState == STANDARD_APPBAR){
            setAppBarState(SEARCH_APPBAR);

        }else {
            setAppBarState(STANDARD_APPBAR);
        }
    }

    /**
     * Sets the appbar state for either the search 'mode' or 'standard' mode
     * @param state
     */

    private void setAppBarState(int state) {
        Log.d(TAG, "setAppBarState: changing app bar state to:"+state);

        mAppBarState = state;

        if(mAppBarState == STANDARD_APPBAR){
            searchBar.setVisibility(View.GONE);
            viewContactsBar.setVisibility(View.VISIBLE);
            //hide the keyboard
            View view = getView();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try{
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }catch (NullPointerException e){
                Log.d(TAG, "setAppBarState: NullPointerException: "+e.getMessage());
            }
        } else if(mAppBarState == SEARCH_APPBAR){
            viewContactsBar.setVisibility(View.GONE);
            searchBar.setVisibility(View.VISIBLE);

            //open the keyboard
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setAppBarState(STANDARD_APPBAR);
    }
}
