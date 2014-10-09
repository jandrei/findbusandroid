package br.com.senac.findbus.view;

import java.util.List;

import android.app.Dialog;
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

			if (status == ConnectionResult.SUCCESS) {

				StopED ed = (StopED) getIntent().getSerializableExtra("stopED");

				if (ed != null) {
					GoogleMap map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
					LatLng latLng = new LatLng(ed.getStopLat(), ed.getStopLon());
					configuraPosicao(map, latLng, ed);

					Mensagens.ExibeMensagemAlert(ExemploTela1.this, "Sucess!! = " + ed.getStopId());

				} else {
					StopDAO dao = StopDAO.getInstance(getBaseContext());
					GoogleMap map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
					ed =new StopED();
					ed.setStopName("belas");
					List<StopED> stops = dao.listar(ed);
					
					PolylineOptions options = new PolylineOptions();
					for (StopED stopED : stops) {
						options.add(new LatLng(stopED.getStopLat(), stopED.getStopLon()));
					}
					
					map.addPolyline(options);
					
					
					Mensagens.ExibeMensagemAlert(ExemploTela1.this, "Sucess!!");

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
