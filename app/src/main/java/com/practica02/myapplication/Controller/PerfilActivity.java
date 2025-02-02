package com.practica02.myapplication.Controller;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.practica02.myapplication.Model.Entidades.Logica.LUsuario;
import com.practica02.myapplication.Model.Persistencia.UsuarioDAO;
import com.practica02.myapplication.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity {

    private CircleImageView imgPerfil;
    private ImageView imgPerfil2;
    private TextView txtNombre;
    private TextView txtCorreo;
    //private TextView txtContraseña;
    private TextView txtFechaDeNacimiento;
    private TextView txtGenero;
    private TextView txtPlaca;
    private TextView txtModelo;
    private TextView txtColor;

    //private Button btnModificar;

    private LUsuario usuarioTemporal;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        imgPerfil = findViewById(R.id.fotoPerfilPerfil);
        txtNombre = findViewById(R.id.idPerfilNombre);
        txtCorreo = findViewById(R.id.idPerfilCorreo);
        //txtContraseña = findViewById(R.id.idPerfilContraseña);
        txtFechaDeNacimiento = findViewById(R.id.txtPerfilFechaDeNacimiento);
        txtGenero = findViewById(R.id.txtPerfilGenero);
        txtPlaca = findViewById(R.id.txtPerfilPlaca);
        txtModelo = findViewById(R.id.txtPerfilModelo);
        txtColor = findViewById(R.id.txtPerfilColor);
        //btnModificar = findViewById(R.id.btnModificarPerfil);

        mAuth = FirebaseAuth.getInstance();

        UsuarioDAO.getInstancia().obtenerInformacionDeUsuarioPorLlave(UsuarioDAO.getInstancia().getKeyUsuario(), new UsuarioDAO.IDevolverUsuario() {
            @Override
            public void devolverUsuario(LUsuario lUsuario) {
                String url = lUsuario.getUsuario().getFotoPerfilURL();
                loadImageFromURL(url);
                txtNombre.setText(lUsuario.getUsuario().getNombre());
                txtCorreo.setText(lUsuario.getUsuario().getCorreo());
                txtFechaDeNacimiento.setText(LUsuario.obtenerFechaDeNacimiento(lUsuario.getUsuario().getFechaDeNacimiento()));
                txtGenero.setText(lUsuario.getUsuario().getGenero());
                txtPlaca.setText(lUsuario.getUsuario().getPlaca());
                txtModelo.setText(lUsuario.getUsuario().getPlaca());

            }
            @Override
            public void devolverError(String error) {
            }
        });

        /*btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this, ModificarPerfilActivity.class);
                startActivity(intent);
            }
        });*/

    }

    private void loadImageFromURL(String url){
        Picasso.with(this).load(url).into(imgPerfil,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError() {
                    }
                });
    }
}
