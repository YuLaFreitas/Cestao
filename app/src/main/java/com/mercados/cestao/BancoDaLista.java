package com.mercados.cestao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDaLista extends SQLiteOpenHelper {
    private static final String nome_lista = "lista_de_compras";
    private static final String tabela = "tabela";
    private static final String rotulo = "rotulo";
    private static final String preco = "preco";
    private static final String quantidade = "quantidade";

    private static final int versao = 1;
    private SQLiteDatabase db;

    BancoDaLista(Context context) {super(context, nome_lista, null, versao);}


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + tabela +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        rotulo + " TEXT, " +
                        quantidade + " INTEGER, " +
                        preco + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + tabela);
        onCreate(db);
    }

    //----------------DA QUE EM DIANTE SÃO OPERAÇÕES EM REGISTROS REGISTROS
    void excluir(long id){
        db = getWritableDatabase();
        String[] whereArgs = {String.valueOf(id)};
        db.delete(tabela, " _id = ?", whereArgs);
    }

    void corrigir(ContentValues contentValues, String id){
        db = getWritableDatabase();
        db.update(tabela, contentValues, " _id = ?", new String[]{id});
    }


    public int contador(String tipo){
        String sql = null;
        int solv = 0;
        db = this.getWritableDatabase();
        Cursor c = null;
        switch (tipo){
            case "somar":
                sql = "SELECT SUM(" + preco + ") FROM " + tabela;
                assert c != null;
                if(c.moveToNext()) solv = c.getInt(0);
                break;
            case "contar:":
                sql = "SELECT COUNT(*) FROM " + tabela + " WHERE " + preco + " > 0 ;";
                break;        }

        c = db.rawQuery(sql, null);
        db.close();
        return solv;

    }

}
