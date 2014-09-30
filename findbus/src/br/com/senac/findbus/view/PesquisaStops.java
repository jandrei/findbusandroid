package br.com.senac.findbus.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.R;
import br.com.senac.findbus.dao.StopDAO;
import br.com.senac.findbus.model.StopED;

public class PesquisaStops extends Activity {

	RadioButton rbID;
	RadioButton rbNome;
	EditText txtPesquisa;

	ListView lista;

	StopDAO dao;

	List<StopED> stops = new ArrayList<StopED>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisa_stops);

		try {
			rbID = (RadioButton) findViewById(R.id.rbCodigo);
			rbNome = (RadioButton) findViewById(R.id.rbNome);
			txtPesquisa = (EditText) findViewById(R.id.edtPesquisar);
			lista = (ListView) findViewById(R.id.listaStops);
			dao = StopDAO.getInstance(this);

		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(this, e);
		}
	}

	public void btnPesquisar(View view) {
		try {

			StopED ed = new StopED();
			stops = new ArrayList<StopED>();

			if (rbID.isChecked()) {
				try {
					ed.setStopId(Integer.valueOf(txtPesquisa.getText().toString()));
				} catch (Exception e) {
					throw new Exception("Quando selecionar pesquisa por código, digite apenas números.");
				}

			} else {
				ed.setStopName(txtPesquisa.getText().toString().trim());
			}
			stops = dao.listar(ed);

			ArrayAdapter<String> fileList = null;
			ArrayList<String> result = new ArrayList<String>();

			for (StopED p : stops) {
				result.add(p.getStopId() + " - " + p.getStopName());
			}

			fileList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
			lista.setAdapter(fileList);
			lista.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					StopED ed = stops.get(position);

					try {
						Intent in = new Intent(PesquisaStops.this, ExemploTela1.class);
						in.putExtra("stopED", ed);
						startActivity(in);
					} catch (Exception e) {
						Mensagens.ExibeExceptionAlert(PesquisaStops.this, e);
					}
				}
			});

		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(this, e);
		}
	}

}
