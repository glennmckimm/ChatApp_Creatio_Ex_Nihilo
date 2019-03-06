package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Glenn on 04/03/2019.
 */

public class ConversationsFragment extends Fragment {

    private static final String TAG = "ConversationsFragment";

    public ConversationsFragment() {
        // empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ConversationsFragment started");
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);

        return view;
    }
}
