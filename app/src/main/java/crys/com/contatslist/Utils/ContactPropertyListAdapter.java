package crys.com.contatslist.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import crys.com.contatslist.MainActivity;
import crys.com.contatslist.R;
import crys.com.contatslist.models.Contact;

/**
 * Created by cryst on 14/01/2018.
 */

public class ContactPropertyListAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater;
    private List<String> mProperties = null;
    private int layoutResource;
    private Context mContext;
    private String mAppend;

    private static final String TAG = "ContactPropertyListAdap";

    public ContactPropertyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> properties) {
        super(context, resource, properties);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
    }
    //-------------------Stuff to change--------------------------------------------------//
    private static class ViewHolder{
        TextView property;
        ImageView rightIcon;
        ImageView leftIcon;

    }
    //------------------------------------------------------------------------------------//
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //ViewHolder build pattern start
        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            //-------------------Stuff to change--------------------------------------------------//
            holder.property = (TextView) convertView.findViewById(R.id.tvMiddleCardView);
            holder.rightIcon = (ImageView) convertView.findViewById(R.id.iconRightCardView);
            holder.leftIcon = (ImageView) convertView.findViewById(R.id.iconLeftCardView);
            //------------------------------------------------------------------------------------//



            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //-------------------Stuff to change--------------------------------------------------//
        final String property = getItem(position);
        holder.property.setText(property);

        //Check if it's an email or a phone number
        //email
        if(property.contains("@")){
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_email", null, mContext.getPackageName()));
        }else if((property.length()) != 0){
            //Phone call
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_phone", null, mContext.getPackageName()));
            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((MainActivity)mContext).checkPermission(Init.PHONE_PERMISSIONS)){
                        Log.d(TAG, "onClick: initiating phone call...");

                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", property, null));
                        mContext.startActivity(callIntent);
                    }else {
                        ((MainActivity)mContext).verifyPermissions(Init.PHONE_PERMISSIONS);
                    }
                }
            });

            holder.rightIcon .setImageResource(mContext.getResources().getIdentifier("@drawable/ic_message", null, mContext.getPackageName()));
        }

        //-----------------------------------------------------------------------------------//

        return convertView;
    }
}
