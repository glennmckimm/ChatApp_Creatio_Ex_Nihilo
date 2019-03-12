package uk.ac.tees.t7014713.exnihilo_chatapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import uk.ac.tees.t7014713.exnihilo_chatapplication.Model.Message;
import uk.ac.tees.t7014713.exnihilo_chatapplication.Pop_up.PopupActivity;
import uk.ac.tees.t7014713.exnihilo_chatapplication.R;

/**
 * Created by Glenn on 09/03/2019.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Message> mMessage;

    FirebaseUser fUser;

    private StorageReference storageReference;

    public MessageAdapter(Context mContext, List<Message> mMessage) {
        this.mMessage = mMessage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    /**
     * Update this so that the message TextView can have reactions
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.ViewHolder holder, int position) {
        final Message msg = mMessage.get(position);
        holder.showMessage.setText(msg.getMessage());
        holder.showMessage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mContext.startActivity(new Intent(mContext, PopupActivity.class));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView showMessage;
        public ImageView profileImage;

        public ViewHolder(View view) {
            super(view);

            showMessage = view.findViewById(R.id.showMessage);
            profileImage = view.findViewById(R.id.profileImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        if(mMessage.get(position).getSender().equals(fUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
