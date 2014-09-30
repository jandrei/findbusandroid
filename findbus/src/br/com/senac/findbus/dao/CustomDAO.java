package br.com.senac.findbus.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.senac.findbus.model.CustomED;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class CustomDAO<T extends CustomED> {

	protected SQLiteDatabase db;

	public void salvar(T obj) {
		if (obj.getSequenceAndroid() == null) {
			inserir(obj);
		} else {
			atualizar(obj);
		}
	}

	protected abstract ContentValues getContentValues(T obj);

	protected abstract String getNomeTabela();

	protected abstract T fillObject(Cursor c);

	public void inserir(T obj) {
		long id = db
				.insertOrThrow(getNomeTabela(), null, getContentValues(obj));

		obj.setSequenceAndroid((int) id);
	}

	public void atualizar(T obj) {
		db.update(getNomeTabela(), getContentValues(obj), "sequence_android=" + obj.getSequenceAndroid(),
				null);
	}

	public void excluir(T obj) {
		db.delete(getNomeTabela(), "sequence_android=" + obj.getSequenceAndroid(), null);
	}
	
	public void limparTabela(){
		db.delete(getNomeTabela(), null, null);
	}

	public T buscaUmPorID(int id) {
		Cursor c = db.query(getNomeTabela(), null, "sequence_android=" + id, null, null,
				null, null);

		try {
			if (c.getCount() == 0)
				return null;

			c.moveToFirst();
			T obj = fillObject(c);
			return obj;
		} finally {
			c.close();
		}
	}

	public List<T> listarTodos() {
		Cursor c = db
				.query(getNomeTabela(), null, null, null, null, null, null);
		try {

			List<T> lista = new ArrayList<T>();

			// Se a consulta n�o retornou nenhum registro
			if (c.getCount() == 0)
				return lista; // retorno a lista vazia

			// Enquanto consigo mover o cursor para o proximo registro
			while (c.moveToNext()) {
				T t = fillObject(c);
			
				lista.add(t); // /adiciono o objeto na lista
			}

			return lista; // retorna a lista

		} finally {// Sempre � executado, mesmo se tiver um return antes
			c.close(); // fecho o cursor
		}
	}
	
	public String getFiltroListar(T obj){
		return "";
	}
	
	public String getOrderBy(){
		return null;
	}

	public List<T> listar(T obj) {
		
		String filtro = " 1=1 ";
		if (obj != null ){
			if (obj.getSequenceAndroid() != null){
				filtro = " and sequence_android="+obj.getSequenceAndroid();
			}
			filtro += getFiltroListar(obj);
		}
		
		Cursor c = db.query(getNomeTabela(), null, filtro, null, null, null, getOrderBy());
		try {

			List<T> lista = new ArrayList<T>();

			// Se a consulta n�o retornou nenhum registro
			if (c.getCount() == 0)
				return lista; // retorno a lista vazia

			// Enquanto consigo mover o cursor para o proximo registro
			while (c.moveToNext()) {
				T t = fillObject(c);
			
				lista.add(t); // /adiciono o objeto na lista
			}

			return lista; // retorna a lista

		} finally {// Sempre � executado, mesmo se tiver um return antes
			c.close(); // fecho o cursor
		}
	}

}
