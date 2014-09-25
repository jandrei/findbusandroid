package br.com.senac.findbus.dao;

import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.StrictMode;
import br.com.senac.findbus.Constantes;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.SynchronousHttpConnection;
import br.com.senac.findbus.model.StopED;

import com.google.gson.Gson;

public class StopDAO extends CustomDAO<StopED> {

	protected static StopDAO singleton;

	public static StopDAO getInstance(Context ctx) {

		if (singleton == null)
			singleton = new StopDAO();

		if (singleton.db == null || !singleton.db.isOpen()) {
			singleton.db = new DbHelper(ctx).getWritableDatabase();
		}
		return singleton;
	}

	@Override
	protected ContentValues getContentValues(StopED obj) {
		ContentValues content = new ContentValues();
		content.put("sequence_android", obj.getSequenceAndroid());
		content.put("stop_id", obj.getStopId());
		content.put("stop_code", obj.getStopCode());
		content.put("stop_desc", obj.getStopDesc());
		content.put("stop_name", obj.getStopName());
		content.put("stop_lat", obj.getStopLat());
		content.put("stop_lon", obj.getStopLon());

		return content;
	}

	@Override
	protected String getNomeTabela() {
		return "stops";
	}

	@Override
	protected StopED fillObject(Cursor c) {
		StopED obj = new StopED();

		obj.setSequenceAndroid(c.getInt(c.getColumnIndexOrThrow("sequence_android")));
		obj.setStopId(c.getInt(c.getColumnIndexOrThrow("stop_id")));
		obj.setStopName(c.getString(c.getColumnIndexOrThrow("stop_name")));
		obj.setStopCode(c.getString(c.getColumnIndexOrThrow("stop_code")));
		obj.setStopDesc(c.getString(c.getColumnIndexOrThrow("stop_desc")));
		obj.setStopLat(c.getDouble(c.getColumnIndexOrThrow("stop_lat")));
		obj.setStopLon(c.getDouble(c.getColumnIndexOrThrow("stop_lon")));

		return obj;
	}

	public void importarFromWS(Context ctx) {
		try {
			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}

			String resposta = new SynchronousHttpConnection().get(Constantes.urlAmazon + Constantes.Stop.urlStop);

			List<StopED> stops = importarListaJson(resposta);

			Mensagens.ExibeMensagemAlert(ctx, "Tamanho da lista = " + stops.size());

		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(ctx, e);
		}
	}

	private List<StopED> importarListaJson(String json) {
		limparTabela();

		StopED[] listaStopEd = new Gson().fromJson(json, StopED[].class);
		for (StopED stopED : listaStopEd) {
			salvar(stopED);
		}

		return Arrays.asList(listaStopEd);
	}

}
