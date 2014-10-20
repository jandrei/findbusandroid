package br.com.senac.findbus.view;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.R;
import br.com.senac.findbus.dao.StopDAO;
import br.com.senac.findbus.model.StopED;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExemploTela1 extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exemplo_tela1);
		try {
			int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

			if (status == ConnectionResult.SUCCESS) {
				GoogleMap map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
				map.setMyLocationEnabled(true);
				
				StopED ed = (StopED) getIntent().getSerializableExtra("stopED");

				if (ed != null) {
					
					LatLng latLng = new LatLng(ed.getStopLat(), ed.getStopLon());
					configuraPosicao(map, latLng, ed);

				} else {
					StopDAO dao = StopDAO.getInstance(getBaseContext());
					List<StopED> stops = null;
//					int conta = 5;
//					
//					while(conta >= 0){
//						if (map.getMyLocation() != null){
//							break;
//						}
//						conta--;
//						Thread.sleep(1000);
//					}
					LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
					stops = dao.listarProximos(locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
					LatLng ltn = null;
					
					for (StopED stopED : stops) {
						ltn = new LatLng(stopED.getStopLat(), stopED.getStopLon());
						map.addMarker(new MarkerOptions().position(ltn)
								.title(stopED.getStopName())
								.icon( BitmapDescriptorFactory.fromResource(R.drawable.bus)));
					}
					if (stops.size()>0){
						map.animateCamera(CameraUpdateFactory.newLatLngZoom(ltn, 17.0f));
					}
				}
			} else {
				int requestCode = 10;
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
				dialog.show();
			}
		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(ExemploTela1.this, e);
		}
	}

	private void configuraPosicao(GoogleMap map, LatLng latLng, StopED ed) {
		map.addMarker(new MarkerOptions().position(latLng).title(ed.getStopId() + "-" + ed.getStopName() + " (" + ed.getStopLat() + "," + ed.getStopLon() + ")").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
	}
}
