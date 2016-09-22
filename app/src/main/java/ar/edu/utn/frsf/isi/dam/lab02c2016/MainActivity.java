package ar.edu.utn.frsf.isi.dam.lab02c2016;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.R.layout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnCheckedChangeListener{
    DecimalFormat f = new DecimalFormat("##.00");
    private final static String[] horarios = {"20.00", "20.30", "21.00", "21.30", "22.00"};
    private Spinner spHorarios;
    private ListView lv;
    private ArrayAdapter<ElementoMenu> listAdapter1;
    private RadioGroup radioGroup;
    private RadioButton radioPlato;
    private RadioButton radioPostre;
    private RadioButton radioBebida;
    private TextView pedido;
    private Button btAgregar;
    private Button btConfirmar;
    private Button btReiniciar;
    Boolean PedidoConfirmado;
    private ArrayList<ElementoMenu> elementosPedidos;
    Double total=0.0;
    ElementoMenu eActual;
    ElementoMenu[] listaBebidas;
    ElementoMenu[] listaPlatos;
    ElementoMenu[] listaPostre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarListas();
        pedido.setMovementMethod(new ScrollingMovementMethod());
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, horarios);
        spHorarios = (Spinner) findViewById(R.id.spHorarios);
        spHorarios.setAdapter(adapter);
        pedido = (TextView) findViewById(R.id.txPedido);
        radioGroup = (RadioGroup) findViewById(R.id.opciones_pedido);
        radioPlato = (RadioButton) findViewById(R.id.btplato);
        radioPostre = (RadioButton) findViewById(R.id.btpostre);
        radioBebida = (RadioButton) findViewById(R.id.btbebida);
        btAgregar = (Button) findViewById(R.id.btagregar);
        btConfirmar = (Button) findViewById(R.id.btconfirmar);
        btReiniciar = (Button) findViewById(R.id.btreiniciar);
        PedidoConfirmado=false;
        elementosPedidos = new ArrayList<ElementoMenu>();
        lv = (ListView) findViewById(R.id.listView);
        listAdapter1 = new ArrayAdapter<ElementoMenu>(this,android.R.layout.simple_list_item_single_choice);
        lv.setAdapter(listAdapter1);
        lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);

        radioGroup.setOnCheckedChangeListener(this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eActual = (ElementoMenu) lv.getItemAtPosition(i);

            }
        });

        btAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!radioBebida.isChecked() && !radioPostre.isChecked() && !radioPlato.isChecked()){
                    Toast.makeText(MainActivity.this,"Seleccione una opción",Toast.LENGTH_SHORT).show();
                }
               // else if(lv.getItemsCanFocus())
                else if(PedidoConfirmado)
                    Toast.makeText(MainActivity.this,"Error. No puede agregar el elemento. Ya confirmó el pedido",Toast.LENGTH_SHORT).show();
                else{
                    pedido.setText(pedido.getText() + "\n" + eActual.toString());
                    elementosPedidos.add(eActual);
                    total += eActual.getPrecio();
                }

            }
        });

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(!PedidoConfirmado) {
                    PedidoConfirmado = true;
                    pedido.setText(pedido.getText() + "\nTotal: $" + f.format(total));
                }
            }
        });

        btReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapter1.clear();
                total=0.0;
                elementosPedidos.clear();
                pedido.setText("");
                PedidoConfirmado = false;
            }
        });


    }

    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        switch (i){
            case -1:
                break;
            case R.id.btplato:
                listAdapter1.clear();
                listAdapter1.addAll(Arrays.asList(listaPlatos));
                break;
            case R.id.btbebida:
                listAdapter1.clear();
                listAdapter1.addAll(Arrays.asList(listaBebidas));
                break;
            case R.id.btpostre:
                listAdapter1.clear();
                listAdapter1.addAll(Arrays.asList(listaPostre));
                break;
            default:
                break;
        }
    }



    class ElementoMenu {
        private Integer id;
        private String nombre;
        private Double precio;

        public ElementoMenu() {
        }

        public ElementoMenu(Integer i, String n, Double p) {
            this.setId(i);
            this.setNombre(n);
            this.setPrecio(p);
        }

        public ElementoMenu(Integer i, String n) {
            this(i,n,0.0);
            Random r = new Random();
            this.precio= (r.nextInt(3)+1)*((r.nextDouble()*100));
        }


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }

        @Override
        public String toString() {
            return this.nombre+ "( "+f.format(this.precio)+")";
        }
    }


    private void iniciarListas() {
        // inicia lista de bebidas
        listaBebidas = new ElementoMenu[7];
        listaBebidas[0] = new ElementoMenu(1, "Coca");
        listaBebidas[1] = new ElementoMenu(4, "Jugo");
        listaBebidas[2] = new ElementoMenu(6, "Agua");
        listaBebidas[3] = new ElementoMenu(8, "Soda");
        listaBebidas[4] = new ElementoMenu(9, "Fernet");
        listaBebidas[5] = new ElementoMenu(10, "Vino");
        listaBebidas[6] = new ElementoMenu(11, "Cerveza");
        // inicia lista de platos
        listaPlatos = new ElementoMenu[14];
        listaPlatos[0] = new ElementoMenu(1, "Ravioles");
        listaPlatos[1] = new ElementoMenu(2, "Gnocchi");
        listaPlatos[2] = new ElementoMenu(3, "Tallarines");
        listaPlatos[3] = new ElementoMenu(4, "Lomo");
        listaPlatos[4] = new ElementoMenu(5, "Entrecot");
        listaPlatos[5] = new ElementoMenu(6, "Pollo");
        listaPlatos[6] = new ElementoMenu(7, "Pechuga");
        listaPlatos[7] = new ElementoMenu(8, "Pizza");
        listaPlatos[8] = new ElementoMenu(9, "Empanadas");
        listaPlatos[9] = new ElementoMenu(10, "Milanesas");
        listaPlatos[10] = new ElementoMenu(11, "Picada 1");
        listaPlatos[11] = new ElementoMenu(12, "Picada 2");
        listaPlatos[12] = new ElementoMenu(13, "Hamburguesa");
        listaPlatos[12] = new ElementoMenu(14, "Calamares");
        // inicia lista de postres
        listaPostre = new ElementoMenu[15];
        listaPostre[0] = new ElementoMenu(1, "Helado");
        listaPostre[1] = new ElementoMenu(2, "Ensalada de Frutas");
        listaPostre[2] = new ElementoMenu(3, "Macedonia");
        listaPostre[3] = new ElementoMenu(4, "Brownie");
        listaPostre[4] = new ElementoMenu(5, "Cheescake");
        listaPostre[5] = new ElementoMenu(6, "Tiramisu");
        listaPostre[6] = new ElementoMenu(7, "Mousse");
        listaPostre[7] = new ElementoMenu(8, "Fondue");
        listaPostre[8] = new ElementoMenu(9, "Profiterol");
        listaPostre[9] = new ElementoMenu(10, "Selva Negra");
        listaPostre[10] = new ElementoMenu(11, "Lemon Pie");
        listaPostre[11] = new ElementoMenu(12, "KitKat");
        listaPostre[12] = new ElementoMenu(13, "IceCreamSandwich");
        listaPostre[13] = new ElementoMenu(14, "Frozen Yougurth");
        listaPostre[14] = new ElementoMenu(15, "Queso y Batata");

    }


}

