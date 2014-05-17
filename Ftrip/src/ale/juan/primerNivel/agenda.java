package ale.juan.primerNivel;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ale.juan.evento.evento;
import ale.juan.ftrip.R;
import ale.juan.util.CustomAgendaAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class agenda extends Fragment {

	private UiLifecycleHelper uiHelper;
	private ProfilePictureView profilePictureView;
	String TAG="Info";
	ListView listaView;
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.agenda_fragment, container, false);

		profilePictureView = (ProfilePictureView) rootView.findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);


		Session session=Session.getActiveSession();
		makeMeRequest(session);
		infoEventos();


		return rootView;
	}



	/*
	 * Obtener datos de eventos.
	 * 
	 * */
	 private void infoEventos() {


		 String fqlQuery = "SELECT name,description, pic_small, eid from event WHERE eid in (SELECT eid FROM event_member WHERE uid = me()) or creator = me() ";
		 Bundle params = new Bundle();
		 params.putString("q", fqlQuery);
		 Session session = Session.getActiveSession();
		 Request request = new Request(session,
				 "/fql",                         
				 params,                         
				 HttpMethod.GET,                
				 new Request.Callback(){         
			 ArrayList <evento> eventos = new ArrayList<>();
			 public void onCompleted(Response response) {
				 GraphObject graphObject = response.getGraphObject();
				 JSONArray arreglo;
				 try {
					 arreglo= graphObject.getInnerJSONObject().getJSONArray("data");
					 for (int i = 0; i < arreglo.length(); i++) {
						 JSONObject c = arreglo.getJSONObject(i);
						 evento e = new evento();
						 e.setName(c.getString("name"));
						 e.setDescription( c.getString("description"));
						 e.setEid(c.getLong("eid"));
						 e.setPic_square(c.getString("pic_square"));
						/* e.setPrivacy(c.getString("privacy"));
						 e.setStart_time(c.getString("start_time"));*/
						 eventos.add(e);
					 }
				 } catch (JSONException e) {
					 e.printStackTrace();

				 }finally{

					 llenarLista(eventos);
				 }
			 }
		 }); 
		 try {
			 Request.executeBatchAsync(request);	
		 } catch (Exception e) {
			 Log.i("Final", "Result1: " );
		 }
	 }

	  private void llenarLista(ArrayList<evento> eventos2) {
	  listaView=(ListView)rootView.findViewById(R.id.listaView);
	 ArrayAdapter<evento> adapter = new CustomAgendaAdapter(getActivity(),
		       eventos2);

	 listaView.setAdapter(adapter);
 }	



	 /**
	  * Manejo de sessiones de fb
	  * 
	  * */
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 uiHelper = new UiLifecycleHelper(getActivity(), callback);
		 uiHelper.onCreate(savedInstanceState);
	 }



	 private Session.StatusCallback callback = new Session.StatusCallback() {
		 @Override
		 public void call(final Session session, final SessionState state, final Exception exception) {
			 onSessionStateChange(session, state, exception);
			 //   Log.i(T,"call del statusuCallback");
		 }
	 };


	 private void onSessionStateChange(Session session, SessionState state,
			 Exception exception) {
		 if (session != null && session.isOpened())  {
			 // Get the user's data.
			 makeMeRequest(session);
			 //  Log.i(TAG, "Logged in...");
			 Intent intent = new Intent(getActivity(), SwipeViewFtrip.class);
			 getActivity().startActivity(intent);
		 } else if (session.isClosed()) {
			 //Log.i(TAG, "Logged out...");
		 }

	 }

	 private void makeMeRequest(final Session session) {
		 Request request = Request.newMeRequest(session, 
				 new Request.GraphUserCallback() {
			 @Override
			 public void onCompleted(GraphUser user, Response response) {
				 // If the response is successful
				 //Log.i(T,"onCompleted de make me request");
				 if (session == Session.getActiveSession()) {
					 if (user != null) {
						 // Set the id for the ProfilePictureView
						 // view that in turn displays the profile picture.
						 profilePictureView.setProfileId(user.getId());
					 }
				 }
				 if (response.getError() != null) {
					 // Handle errors, will do so later.
				 }
			 }
		 });
		 request.executeAsync();
	 } 

}






