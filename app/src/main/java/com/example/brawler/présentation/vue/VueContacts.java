package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PrésenteurContacts;
import com.example.brawler.présentation.vue.adapter.ContactsAdapter;

public class VueContacts extends Fragment {
    private PrésenteurContacts _presenteur;
    private RecyclerView rvContacts;
    private ContactsAdapter contactsAdapter;

    public void setPresenteur(PrésenteurContacts presenteur){_presenteur = presenteur;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voir_contact,container,false);

        /**
         * Création du recyclerview et de l'adapteur. Fontion pour initer la liste et vue.
         */
        rvContacts = (RecyclerView)view.findViewById(R.id.listeContacts);
        contactsAdapter = new ContactsAdapter(this.getContext());
        contactsAdapter.setPrésenteur(_presenteur);
        rvContacts.setAdapter(contactsAdapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this.getContext()));
        /**
         * Fonction future
         * new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvContacts);
         */
        return view;
    }

    /**
     * Set la liste d'utilisateurs et rafraichit la vue
     */
    public void afficherContacts(){
        contactsAdapter.setListUtilisateurs();
        rafraichirVue();
    }

    /**
     * Rafraichit la vue si changement est nécessaire
     */
    public void rafraichirVue(){
        if(contactsAdapter!=null)
            contactsAdapter.notifyDataSetChanged();
    }

    /**
     * Fonction de delete contact non implémenté
     *
     ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();


            switch (direction) {
                case ItemTouchHelper.LEFT:
                contactsAdapter.removeContact(position);
                contactsAdapter.notifyDataSetChanged();
                contactsAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), contactsAdapter.getItemCount());
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorJeter))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };**/
}
