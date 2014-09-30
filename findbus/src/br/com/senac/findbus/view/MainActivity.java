package br.com.senac.findbus.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.R;
import br.com.senac.findbus.dao.StopDAO;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final StopDAO stopDao = StopDAO.getInstance(getApplication());

		final TextView texto2 = (TextView) findViewById(R.id.texto2);
		Button botao = (Button) findViewById(R.id.botao);

		botao.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				texto2.setText("Ainda em desenvolvimento");

				stopDao.importarFromWS(v.getContext());
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

	public void btnChamaTelaExemplo1(View v) {
		try {
			startActivity(new Intent(this, ExemploTela1.class));
		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(v.getContext(), e);
		}

	}
	
	public void btnChamaTelaPesquisaStops(View v){
		startActivity(new Intent(this,PesquisaStops.class));
	}
}
