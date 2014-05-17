package ale.juan.evento;

import ale.juan.ftrip.R;
import ale.juan.primerNivel.SwipeViewFtrip;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

public class nuevoEvento extends FragmentActivity {
	TextView  nombreEvento, detallesEvento, ubicacion;
	DatePicker fechaInicioEvento;
	Button crearEvento, seleccionarAmigos;
	
	//Variables de fb
	private UiLifecycleHelper uiHelper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.nuevo_evento);
		 
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		
		nombreEvento= (TextView)findViewById(R.id.nombreEvento);
		detallesEvento= (TextView)findViewById(R.id.detallesEvento);
		ubicacion= (TextView)findViewById(R.id.ubicacion);
		fechaInicioEvento= (DatePicker)findViewById(R.id.fechaInicioEvento);
		
		seleccionarAmigos= (Button)findViewById(R.id.seleccionarAmigos);
		crearEvento= (Button)findViewById(R.id.crearEvento);
		
		
		//Iniciar 
		//Iniciar la sesion de fb 
		//conectarme a fb
		//TODO Traer datis de los componentes
		//Traer el friend picker 
		
		seleccionarAmigos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startPickerActivity(PickerActivity.FRIEND_PICKER, 1);
			}
		});
		
	}

	 
	/*
	 * Parte del picker
	 * */
	
	private void startPickerActivity(Uri data, int requestCode) {
        Intent intent = new Intent();
        intent.setData(data);
        intent.setClass(this, PickerActivity.class);
        startActivityForResult(intent, requestCode);
    }
	
	
	/**
	  * Manejo de sessiones de fb
	  * 
	  * */

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
			 Intent intent = new Intent(this, SwipeViewFtrip.class);
			 this.startActivity(intent);
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
