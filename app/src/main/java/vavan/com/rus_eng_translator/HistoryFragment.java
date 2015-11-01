package vavan.com.rus_eng_translator;

/**
 * Show the last 10 queries
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class HistoryFragment extends Fragment {

    Button btGetHistory;
    TextView tvHistory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        tvHistory = (TextView)view.findViewById(R.id.tvHistory);

        btGetHistory = (Button)view.findViewById(R.id.btGetHistory);
        btGetHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvHistory.setText(refreshDB());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvHistory.setText(refreshDB());
    }


    public String refreshDB(){
        String output = "";
        DBHelper db = new DBHelper(this.getActivity());

        if (db.getRecordCount()>0) {

            List<DBRecord> recordList = db.getAllRecord();
            while (recordList.size() > 10) {
                recordList.remove(0);
            }
            for (DBRecord record : recordList) {
                output = output + record.getLangFrom() + " - " + record.getLangTo() + "\n";
            }
        }
        return output;
    }
}
