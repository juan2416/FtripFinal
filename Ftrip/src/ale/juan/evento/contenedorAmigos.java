package ale.juan.evento;

import java.util.List;

import android.app.Application;

import com.facebook.model.GraphUser;

public class contenedorAmigos extends Application {
	private List<GraphUser> selectedUsers;

	/**
	 * @return the selectedUsers
	 */
	public List<GraphUser> getSelectedUsers() {
		return selectedUsers;
	}

	/**
	 * @param selectedUsers the selectedUsers to set
	 */
	public void setSelectedUsers(List<GraphUser> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}
	
}
