package nobugs.team.shopping.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nobugs.team.shopping.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OrderFinishedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFinishedFragment extends Fragment {
    public static OrderFinishedFragment newInstance() {
        OrderFinishedFragment fragment = new OrderFinishedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public OrderFinishedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_finished, container, false);
    }
}
