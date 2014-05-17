package ale.juan.ftrip;


import java.util.Arrays;

import ale.juan.primerNivel.SwipeViewFtrip;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

public class MainFragment extends Fragment {

	private static final String TAG = "Nose";
	private static final String T = "Juan";
	private UiLifecycleHelper uiHelper;
	
	private ProfilePictureView profilePictureView;
	private TextView userNameView;
	
	
	private void makeMeRequest(final Session session) {
		Log.i(T,"makeMeRequest");
	    // Make an API call to get user data and define a 
	    // new callback to handle the response.
	    Request request = Request.newMeRequest(session, 
	            new Request.GraphUserCallback() {
	        @Override
	        public void onCompleted(GraphUser user, Response response) {
	            // If the response is successful
	        	Log.i(T,"onCompleted de make me request");
	        	if (session == Session.getActiveSession()) {
	                if (user != null) {
	                    // Set the id for the ProfilePictureView
	                    // view that in turn displays the profile picture.
	                    profilePictureView.setProfileId(user.getId());
	                    	                    // Set the Textview's text to the user's name.
	                    userNameView.setText(user.getName());
	                }
	            }
	            if (response.getError() != null) {
	                // Handle errors, will do so later.
	            }
	        }
	    });
	    request.executeAsync();
	} 
	
	
	// logic to listen for the changes
	
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
		 @Override
		    public void call(final Session session, final SessionState state, final Exception exception) {
		        onSessionStateChange(session, state, exception);
		        Log.i(T,"call del statusuCallback");
		    }
	};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	    Log.i(T,"onCreate");
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(T,"onCreateView");
		View view = inflater.inflate(R.layout.activity_login, container, false);
        LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setFragment(this);
		
		try {
			authButton.setReadPermissions(Arrays.asList("user_events", "basic_info","user_friends"));
		} catch (Exception e) {
		 Log.i("T", "Problema de permisos en el serReadPermission");
		}
		// Find the user's profile picture custom view
				profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
				profilePictureView.setCropped(true);

				// Find the user's name view
				userNameView = (TextView) view.findViewById(R.id.selection_user_name);
	    return view;
		
	}
	
	//private method that can control the UI
	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
		Log.i(T,"onSessionStateChanget");
		if (session != null && session.isOpened())  {
	    	// Get the user's data.
	        makeMeRequest(session);
	        Log.i(TAG, "Logged in...");
	        Intent intent = new Intent(getActivity(), SwipeViewFtrip.class);
	        getActivity().startActivity(intent);
	    } else if (session.isClosed()) {
	        Log.i(TAG, "Logged out...");
	    }
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
		Log.i(T,"onResume");
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(T,"onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		Log.i(T,"onPaise");
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		Log.i(T,"onDestroy");
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.i(T,"onSaveInstanceState");
		super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	
	

}

