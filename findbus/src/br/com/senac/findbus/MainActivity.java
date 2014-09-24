package br.com.senac.findbus;

import java.util.List;

import br.com.senac.findbus.dao.StopDAO;
import br.com.senac.findbus.model.StopED;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	StopDAO stopDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		stopDao = StopDAO.getInstance(getApplication());

		final TextView texto2 = (TextView) findViewById(R.id.texto2);
		Button botao = (Button) findViewById(R.id.botao);

		botao.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				texto2.setText("Ainda em desenvolvimento");

				try {
					if (android.os.Build.VERSION.SDK_INT > 9) {
						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
								.permitAll().build();
						StrictMode.setThreadPolicy(policy);
					}

					String resposta = new SynchronousHttpConnection()
							.get("http://ec2-54-200-87-69.us-west-2.compute.amazonaws.com:8080/findbusweb/webservice/r/stops");

					stopDao.importarListaJson(resposta);
					List<StopED> stops = stopDao.listarTodos();

					Mensagens.ExibeMensagemAlert(v.getContext(),
							"Tamanho da lista = " + stops.size());

				} catch (Exception e) {
					Mensagens.ExibeExceptionAlert(v.getContext(), e);
				}
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
