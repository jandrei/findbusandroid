package br.com.senac.findbus.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.R;
import br.com.senac.findbus.dao.RouteDAO;
import br.com.senac.findbus.model.RouteED;

public class PesquisaRoutes extends Activity {

	EditText txtPesquisa;
	ListView lista;

	RouteDAO dao;

	List<RouteED> stops = new ArrayList<RouteED>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisa_stops);

		try {
			txtPesquisa = (EditText) findViewById(R.id.edtPesquisar);
			lista = (ListView) findViewById(R.id.listaStops);
			dao = RouteDAO.getInstance(this);

		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(this, e);
		}
	}

	public void btnPesquisar(View view) {
		try {

			RouteED ed = new RouteED();
			stops = new ArrayList<RouteED>();
			ed.setRouteShortName(txtPesquisa.getText().toString().trim());
			stops = dao.listar(ed);

			ArrayAdapter<String> fileList = null;
			ArrayList<String> result = new ArrayList<String>();

			for (RouteED p : stops) {
				result.add(p.getRouteId() + " - " + p.getRouteLongName());
			}

			fileList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
			lista.setAdapter(fileList);

		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(this, e);
		}
	}

}
