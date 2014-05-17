package ale.juan.util;

import ale.juan.primerNivel.agenda;
import ale.juan.primerNivel.calendario;
import ale.juan.primerNivel.notificaciones;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
/**
 * Es el adaptador implementado apra retor
 * 
 * */
public class CustomPagerAdapter extends FragmentPagerAdapter{

	 public CustomPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
		@Override
		public Fragment getItem(int arg0) {
		     //arg0 = 0;
		  switch (arg0) {
		   case 0: {
		    return new agenda();
		   }
		   case 1: {
			   return new calendario();
		   }
		   case 2: {
			   return new notificaciones();
		   }
		   
		  }
		return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}
		
		@Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }
	}
