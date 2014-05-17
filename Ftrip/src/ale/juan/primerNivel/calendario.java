package ale.juan.primerNivel;

import ale.juan.evento.evento;
import ale.juan.ftrip.R;
import ale.juan.util.CustomAgendaAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class calendario extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendario_fragment, container, false);
      

        return rootView;
    }

}
