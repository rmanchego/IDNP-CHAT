package com.practica02.myapplication.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.practica02.myapplication.Activity.LoginActivity;
import com.practica02.myapplication.Activity.MensajeriaActivity;
import com.practica02.myapplication.Activity.ModificarPerfilActivity;
import com.practica02.myapplication.Activity.VistaInicialActivity;
import com.practica02.myapplication.Entidades.Firebase.Mensaje;
import com.practica02.myapplication.Entidades.Firebase.UltimoMensaje;
import com.practica02.myapplication.Entidades.Firebase.Usuario;
import com.practica02.myapplication.Entidades.Logica.LUsuario;
import com.practica02.myapplication.Holder.ChatsViewHolder;
import com.practica02.myapplication.Holder.UsuarioViewHolder;
import com.practica02.myapplication.Persistencia.UsuarioDAO;
import com.practica02.myapplication.R;
import com.practica02.myapplication.Utilidades.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chat extends Fragment {

    private RecyclerView rvChats;
    private FirebaseRecyclerAdapter adapter;
    private FirebaseAuth mAuth;

    public Chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        final FragmentActivity c = getActivity();
        super.onActivityCreated(savedInstanceState);
        rvChats = getView().findViewById(R.id.rvChats2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        rvChats.setLayoutManager(linearLayoutManager);

        Query query = FirebaseDatabase.getInstance() //consulta
                .getReference()
                .child(Constantes.NODO_ULTIMO_MENSAJE).child(UsuarioDAO.getInstancia().getKeyUsuario());

        FirebaseRecyclerOptions<UltimoMensaje> options =
                new FirebaseRecyclerOptions.Builder<UltimoMensaje>()
                        .setQuery(query, UltimoMensaje.class)
                        .build();
//----------------------------
        adapter = new FirebaseRecyclerAdapter<UltimoMensaje, ChatsViewHolder>(options){
            @Override
            public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_chats, parent, false);
                return new ChatsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ChatsViewHolder holder, final int position, UltimoMensaje model) {
                Glide.with(Chat.this).load(model.getUrlFotoUsuario()).into(holder.getCivFotoPerfil());
                holder.getTxtNombre().setText(model.getNombreUsuario());
                holder.getTxtMensaje().setText(model.getMensaje());
                //Obtiene toda la data de la lista //  Key en getSpanpshot 2

                holder.getLayaoutPrincipal().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), MensajeriaActivity.class);
                        intent.putExtra("key_receptor", getSnapshots().getSnapshot(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        rvChats.setAdapter(adapter);
    }



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
