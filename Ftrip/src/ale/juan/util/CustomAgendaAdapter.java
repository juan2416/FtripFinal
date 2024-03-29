package ale.juan.util;

import java.io.InputStream;
import java.util.ArrayList;

import ale.juan.evento.evento;
import ale.juan.ftrip.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAgendaAdapter extends ArrayAdapter<evento>{
private final Context context;
 final ArrayList<evento> listaEventos;
	
	public CustomAgendaAdapter(Context context,  ArrayList<evento> eventos) {
		super(context, R.layout.fila_agenda_fragment, eventos);
	    this.context = context;
	    this.listaEventos= eventos;
	}


	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		 LayoutInflater inflater = (LayoutInflater) context
		 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
		 View rowView = inflater.inflate(R.layout.fila_agenda_fragment, parent, false);
		 TextView titulo= (TextView)rowView.findViewById(R.id.titulo);
		 TextView descripcion= (TextView)rowView.findViewById(R.id.descripcion);
		 ImageView imagenEvento=(ImageView)rowView.findViewById(R.id.imagen);
		 
		 titulo.setText(listaEventos.get(position).getName());
		 descripcion.setText(listaEventos.get(position).getDescription());
		 
		 new DownloadImageTask((ImageView) rowView.findViewById(R.id.imagen))
         .execute(listaEventos.get(position).getPic_square());
		 
		 return rowView;
	}
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }	 
	}
	
}

