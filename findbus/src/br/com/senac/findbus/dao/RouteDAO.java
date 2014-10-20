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
import br.com.senac.findbus.model.RouteED;

import com.google.gson.Gson;

public class RouteDAO extends CustomDAO<RouteED> {

	protected static RouteDAO singleton;

	public static RouteDAO getInstance(Context ctx) {

		if (singleton == null)
			singleton = new RouteDAO();

		if (singleton.db == null || !singleton.db.isOpen()) {
			singleton.db = new DbHelper(ctx).getWritableDatabase();
		}
		return singleton;
	}

	@Override
	protected ContentValues getContentValues(RouteED obj) {
		ContentValues content = new ContentValues();

		content.put("sequence_android", obj.getSequenceAndroid());
		content.put("route_id", obj.getRouteId());
		content.put("agency_id", obj.getAgencyId());
		content.put("route_short_name", obj.getRouteShortName());
		content.put("route_long_name", obj.getRouteLongName());
		content.put("route_desc", obj.getRouteDesc());
		content.put("route_type", obj.getRouteType());
		content.put("route_url", obj.getRouteUrl());
		content.put("route_color", obj.getRouteColor());
		content.put("route_text_color", obj.getRouteTextColor());

		return content;
	}

	@Override
	protected String getNomeTabela() {
		return "routes";
	}

	@Override
	protected RouteED fillObject(Cursor c) {
		RouteED obj = new RouteED();
		
		obj.setSequenceAndroid(c.getInt(c.getColumnIndexOrThrow("sequence_android")));
		obj.setRouteId(c.getString(c.getColumnIndexOrThrow("route_id")));
		obj.setAgencyId(c.getString(c.getColumnIndexOrThrow("agency_id")));
		obj.setRouteShortName(c.getString(c.getColumnIndexOrThrow("route_short_name")));
		obj.setRouteLongName(c.getString(c.getColumnIndexOrThrow("route_long_name")));
		obj.setRouteDesc(c.getString(c.getColumnIndexOrThrow("route_desc")));
		obj.setRouteType(c.getInt(c.getColumnIndexOrThrow("route_type")));
		obj.setRouteUrl(c.getString(c.getColumnIndexOrThrow("route_url")));
		obj.setRouteColor(c.getString(c.getColumnIndexOrThrow("route_color")));
		obj.setRouteTextColor(c.getString(c.getColumnIndexOrThrow("route_text_color")));

		return obj;
	}

	@Override
	public String getFiltroListar(RouteED obj) {
		String filtro = "";
		if (obj.getRouteId() != null) {
			filtro += " and route_id = " + obj.getRouteId();
		}
		if (obj.getRouteShortName() != null) {
			filtro += " and lower(route_short_name) like '%" + obj.getRouteShortName().toLowerCase()+"%'";
		}

		return filtro;
	}
	
	@Override
	public String getOrderBy() {
		return "route_long_name";
	}

	public void importarFromWS(Context ctx) {
		try {
			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}

			RouteED pesqStop = new RouteED();
			List<RouteED> lista = listar(pesqStop);

			if (lista == null || lista.isEmpty()) {
				String resposta = new SynchronousHttpConnection().get(Constantes.urlAmazon + Constantes.Route.urlRoute);
				lista = importarListaJson(resposta);
			}

			String mensagem = "";

			for (int i = 0; i < 10 && i < lista.size(); i++) {
				RouteED ed = lista.get(i);
				mensagem += ed.getRouteId() + "\n";
			}

			Mensagens.ExibeMensagemAlert(ctx, "Tamanho da lista = " + lista.size() + "\n" + mensagem);

		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(ctx, e);
		}
	}

	private List<RouteED> importarListaJson(String json) {
		limparTabela();

		RouteED[] lista = new Gson().fromJson(json, RouteED[].class);
		for (RouteED stopED : lista) {
			salvar(stopED);
		}

		return Arrays.asList(lista);
	}

}
