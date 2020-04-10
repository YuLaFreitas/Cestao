package com.mercados.cestao;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class Configuracao extends AppCompatActivity {
    ComunicacaoWeb comWeb;
    SharedPreferences spNome, spTel, spEnd, spRef;
    SharedPreferences.Editor altNome, altEnd, altRef, altTel;

    Intent intent;
    EditText moeda, nome, telefone, endereco, referencia;
    RadioButton debito, credito;
    AlertDialog.Builder alerta;

    LinearLayout pagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        nome = findViewById(R.id.labNome);
        telefone = findViewById(R.id.labTel);
        endereco = findViewById(R.id.labEndereco);
        referencia = findViewById(R.id.labRef);

        spNome = getSharedPreferences(getString(R.string.nome), Context.MODE_PRIVATE);
        spTel = getSharedPreferences(getString(R.string.labTel), Context.MODE_PRIVATE);
        spEnd = getSharedPreferences(getString(R.string.labEndereco), Context.MODE_PRIVATE);
        spRef = getSharedPreferences(getString(R.string.labRef), Context.MODE_PRIVATE);

        nome.setText(spNome.getString(getString(R.string.nome), ""));
        telefone.setText(spTel.getString(getString(R.string.labTel), ""));
        endereco.setText(spEnd.getString(getString(R.string.labEndereco), ""));
        referencia.setText(spRef.getString(getString(R.string.labRef), ""));
    }


    public void onClick(final View view) {
        final StringBuffer texto = new StringBuffer();

        comWeb = new ComunicacaoWeb(this);
        intent  = new Intent(this, MainActivity.class);

        alerta = new AlertDialog.Builder(this);
        pagamento = findViewById(R.id.pagamento);

        switch (view.getId()) {

            case R.id.fechar:

                alerta.create();
                alerta.setTitle("ALERTA!");
                alerta.setMessage("SALVOU TUDO?");

               alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                });
               alerta.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                   }
               });
                alerta.show();
                break;
            case R.id.labCartao:
                credito = new RadioButton(this);
                debito = new RadioButton(this);
                pagamento.removeAllViews();

                debito.setText(getString(R.string.debito));
                debito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        credito.setChecked(false);
                        texto.append("\nCARTÃO")
                                .append(" debito\n");
                    }
                });

                credito.setText(getString(R.string.credito));
                credito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        debito.setChecked(false);
                        texto.append("\nCARTÃO")
                                .append(" credito\n");
                    }
                });


                pagamento.addView(debito);
                pagamento.addView(credito);
                break;

            case R.id.labDinheiro:
                pagamento.removeAllViews();

                moeda = new EditText(this);

                moeda.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                moeda.setRawInputType(InputType.TYPE_CLASS_NUMBER);

                moeda.setHint("DINHEIRO EM MÃOS");

                texto.append("\nVALOR EM MÃOS\n R$ ");

                moeda.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        texto.append(s.charAt(start));
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

                pagamento.addView(moeda);
                break;

            case R.id.salvar:
                boolean teste = true;

                    if (nome.length() > 0) {
                        altNome = spNome.edit();
                        altNome.putString(getString(R.string.nome),
                                nome.getText().toString());
                        altNome.apply();
                        texto
                                .append("\nNome: ")
                                .append(spNome.getString(getString(R.string.nome), "null"));

                    }else {
                        ausenciadeDados(getResources().getString(R.string.nome), view);
                        teste = false;
                    }
                        if(telefone.length() >0){

                                texto.append("\nTelefone: ")
                                    .append(spTel.getString(getString(R.string.labTel), "null"));

                        altTel = spTel.edit();
                        altTel.putString(getString(R.string.labTel),
                                telefone.getText().toString());
                        altTel.apply();}
                        else {
                            ausenciadeDados( getResources().getString(R.string.labTel),view);
                            teste = false;
                        }

                        if(endereco.length() > 0) {
                            texto.append("\nEndereço: ")
                                    .append(spEnd.getString(getString(R.string.labEndereco), "null"))
                                    .append("\nLISTA DE COMPRAS:\n");

                            altEnd = spEnd.edit();
                            altEnd.putString(getString(R.string.labEndereco), endereco.getText().toString());
                            altEnd.apply();
                        }
                        else {
                            ausenciadeDados(getResources().getString(R.string.labEndereco), view);
                            teste = false;
                        }
                        if(referencia.length()>0) {
                            altRef = spRef.edit();
                            altRef.putString(getString(R.string.labRef),
                                    referencia.getText().toString());
                            altRef.apply();
                            texto.append("\nReferência: ")
                                    .append(spRef.getString(getString(R.string.labRef), "null"));
                        }

                        if(teste) preEnvio(texto);
               break;
        }
    }
    public void ausenciadeDados(String campo, View v){
        Snackbar snackbar = Snackbar
                .make(v,"falta " + campo, Snackbar.LENGTH_LONG);
        snackbar.show();
}

    public void preEnvio(final StringBuffer texto){
        alerta.create();


        alerta.setMessage(texto);
        alerta.setView(listView(this));

        alerta.setPositiveButton("ENVIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Implementar um fragment que apresenta a lista antes de enviar os dados
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.setType(Intent.CATEGORY_ALTERNATIVE);
                intent1.putExtra(Intent.EXTRA_REFERRER_NAME, texto.toString());

                Intent compartilhar = Intent.createChooser(intent1, null);

                intent1.setType("text/plain");
                startActivity(compartilhar);
            }
        });

        alerta.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alerta.show();
    }

    ListView compras;
    BancoDaLista bl;
    SQLiteDatabase database;
    CursorAdapter cursorAdapter;
    Cursor c;
    @SuppressLint("SetTextI18n")
    public View listView(Context context){

        try {
            compras = new ListView(context);

            bl = new BancoDaLista(context);
            database = bl.getWritableDatabase();
            c = database.rawQuery("SELECT * FROM tabela", null);

            if (c.getCount() > 0) {

                cursorAdapter = new SimpleCursorAdapter(context, R.layout.linha, c,
                        new String[]{"rotulo", "quantidade", "preco", "_id"},
                        new int[]{R.id.produto, R.id.quantidade, R.id.preco});
            } else {
                Toast.makeText(context, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
            }
            compras.setAdapter(cursorAdapter);
            compras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                    parent.setEmptyView(view);
                    bl.excluir(id);
                        }
                    });

        }catch (Exception e){
            TextView img = new TextView(context);
            img.setText("Erro:\n" + e.getMessage() + "\n");
            return img;
        }
        return compras;
    }
}