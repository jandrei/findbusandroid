package br.com.senac.findbus.view;

import android.app.Dialog;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.R;
import br.com.senac.findbus.model.StopED;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class ExemploTela1 extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exemplo_tela1);
		try {
			int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

			if (status != ConnectionResult.SUCCESS) {
				int requestCode = 10;
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
				dialog.show();
			} else {
				StopED ed = (StopED) getIntent().getSerializableExtra("stopED");
				GoogleMap map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
				map.setMyLocationEnabled(true);
				if (ed != null) {
					LatLng latLng = new LatLng(ed.getStopLat(), ed.getStopLon());
					configuraPosicao(map, latLng, ed);
				}
			}
		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(ExemploTela1.this, e);
		}
	}

	private void configuraPosicao(GoogleMap map, LatLng latLng, StopED ed) {
		map.addMarker(new MarkerOptions().position(latLng).title(ed.getStopId() + "-" + ed.getStopName() + " (" + ed.getStopLat() + "," + ed.getStopLon() + ")").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
	}
}
