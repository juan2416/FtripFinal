package ale.juan.evento;


import ale.juan.ftrip.R;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;
/**
 * The PickerActivity enhances the Friend or Place Picker by adding a title
 * and a Done button. The selection results are saved in the ScrumptiousApplication
 * instance.
 */
public class PickerActivity extends FragmentActivity {

	private FriendPickerFragment friendPickerFragment;
	 public static final Uri FRIEND_PICKER = Uri.parse("picker://friend");	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.pickers);
        
        Bundle args = getIntent().getExtras();
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragmentToShow = null;
        Uri intentUri = getIntent().getData();
        
        if (savedInstanceState == null) {
            friendPickerFragment = new FriendPickerFragment(args);
        } else {
            friendPickerFragment = (FriendPickerFragment) manager.findFragmentById(R.id.picker_fragment);;
        }
        friendPickerFragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
            @Override
            public void onError(PickerFragment<?> fragment, FacebookException error) {
                PickerActivity.this.onError(error);
            }
        });
        friendPickerFragment.setOnDoneButtonClickedListener(new PickerFragment.OnDoneButtonClickedListener() {
            @Override
            public void onDoneButtonClicked(PickerFragment<?> fragment) {
                finishActivity();
            }
        });
        fragmentToShow = friendPickerFragment;
        manager.beginTransaction().replace(R.id.picker_fragment, fragmentToShow).commit();

	}
	
	@Override
    protected void onStart() {
        super.onStart();
            try {
                friendPickerFragment.loadData(false);
            } catch (Exception ex) {
            	Log.d("error",ex.getCause().toString());
            }
	}
	
	private void onError(Exception error) {
        String text = "Excepcion"+ error.getMessage();
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void onError(String error, final boolean finishActivity) {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error_dialog_title).
                setMessage(error).
                setPositiveButton(R.string.error_dialog_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (finishActivity) {
                            finishActivity();
                        }
                    }
                });
        builder.show();*/
    }

    private void finishActivity() {
        contenedorAmigos app = (contenedorAmigos) getApplication();
        if (FRIEND_PICKER.equals(getIntent().getData())) {
            if (friendPickerFragment != null) {
                app.setSelectedUsers(friendPickerFragment.getSelection());
            }
            setResult(RESULT_OK, null);
            finish();
        }
      }

      
}