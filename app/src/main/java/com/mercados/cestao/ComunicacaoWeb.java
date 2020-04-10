package com.mercados.cestao;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ComunicacaoWeb{

    private String dadoStr;
    private float valor;
    private int quant;

    private Context context;

    public ComunicacaoWeb(Context c){context = c;}

    @JavascriptInterface
    public void passar(String descricao, String quant, String preco){

        setDadoStr(descricao);
        setQuant(Integer.parseInt(quant));
        setValor(Float.parseFloat(preco));

        getAddRegistro();
    }
    @SuppressLint("SetTextI18n")
    private void getAddRegistro(){

        BancoDaLista bl = new BancoDaLista(context);
        SQLiteDatabase sq = bl.getWritableDatabase();
        AlertDialog.Builder aviso = new AlertDialog.Builder(context);
        aviso.create();

        try{
            ContentValues cv = new ContentValues();
            cv.put("rotulo", getDadoStr());
            cv.put("quantidade", "" + getQuant());
            cv.put("preco", "" +  getValor());

            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            TextView textView = new TextView(context);
            textView.setTextSize(20);

            sq.insert("tabela", null, cv);

                textView.setText("Produto adicionado: "
                        + getDadoStr()
                        + "\n quantidade: "
                        + getQuant()
                        + "\n valor: "
                        + getValor());

            aviso.setView(textView);

            aviso.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }catch (Exception e){
            aviso.setMessage(e.getMessage());
        }
        aviso.show();
    }

    private String getDadoStr() {return dadoStr;}
    private void setDadoStr(String dadoStr) {this.dadoStr = dadoStr;}
    private float getValor() { return valor; }
    private void setValor(float valor) { this.valor = valor; }
    private void setQuant(int quant) {this.quant = quant;    }
    private int getQuant() {return quant;}
}
