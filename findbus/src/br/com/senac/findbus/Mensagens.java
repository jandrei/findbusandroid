package br.com.senac.findbus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class Mensagens {

	public void metodoNaoEstatico() {

	}

	/**
	 * Exibe Alert Dialog com T�tulo e texto passado por par�metro
	 * 
	 * @param contexto
	 * @param texto
	 *            - Texto a ser exibido no alert Dialog
	 */
	public static void ExibeMensagemAlert(Context contexto, String titulo,
			String texto) {
		AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
		builder.setTitle(titulo);
		builder.setMessage(texto);
		builder.setCancelable(false);
		builder.setPositiveButton("OK", null);
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Exibe Alert Dialog com T�tulo "Aten��o" e o texto passado por par�metro
	 * 
	 * @param contexto
	 * @param texto
	 *            - Texto a ser exibido no alert Dialog
	 */
	public static void ExibeMensagemAlert(Context contexto, String texto) {
		ExibeMensagemAlert(contexto, "Atenção", texto);
	}

	/**
	 * Exibe em um AlertDialog os detalhes da Exception passada por par�metro
	 * 
	 * OBS: � exibido o tipo da exce��o e a mensagem da exception.
	 * 
	 * @param contexto
	 * @param ex
	 */
	public static void ExibeExceptionAlert(Context contexto, Exception e) {
		// Se for uma Exception de campo obrigat�rio
		ExibeMensagemAlert(contexto, "Ocorreu o seguinte erro", String.format(
				"Classe do erro: %s \n Mensagem: %s1", e.getClass().getName(),
				e.getMessage()));
	}

	/**
	 * Exibe uma mensagem do tipo Toast na Tela, ela fica vis�vel por
	 * determinado tempo e some.
	 * 
	 * OBS: � poss�vel fazer customiza��es com a classe Toast, veja mais em:
	 * 
	 * http://developer.android.com/guide/topics/ui/notifiers/toasts.html
	 * 
	 * @param mensagem
	 *            Mensagem que ser� exibida
	 */
	public static void ExibeMensagemToast(Context contexto, String mensagem) {
		Toast toast = Toast.makeText(contexto, mensagem, Toast.LENGTH_LONG);
		toast.show();
	}

	/**
	 * Exibe janela com bot�o "Sim" e "N�o".
	 * 
	 * @param titulo
	 *            - T�tulo da janela que � exibida
	 * @param pergunta
	 *            - Textp/pergunta que exibido na janela
	 * @param eventoSim
	 *            - Evento que � disparado ao clicar no bot�o "Sim"
	 * @param eventoNao
	 *            - Evento que � disparado ao clicar no bot�o "N�o"
	 */
	public static void EfetuaPergunta(Context contexto, String titulo,
			String pergunta, DialogInterface.OnClickListener eventoSim,
			DialogInterface.OnClickListener eventoNao) {

		AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
		builder.setTitle(titulo);
		builder.setMessage(pergunta);
		builder.setCancelable(false);
		builder.setPositiveButton("Sim", eventoSim);
		builder.setNegativeButton("Não", eventoNao);
		AlertDialog alert = builder.create();
		alert.show();
	}
}
