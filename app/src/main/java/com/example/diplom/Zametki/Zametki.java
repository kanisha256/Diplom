package com.example.diplom.Zametki;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.Naviagtion.Navigator;
import com.example.diplom.R;
import com.example.diplom.Room.Note;

import java.util.List;

public class Zametki extends Fragment {

    private Navigator navigator;
    private ZametkiViewModel noteViewModel;
    private Adapter noteAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zametki_fragment_layout, container, false);
        navigator = new Navigator(getFragmentManager(), R.id.main_container);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        noteAdapter = new Adapter();
        recyclerView.setAdapter(noteAdapter);

        noteAdapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                navigateToEditNoteFragment(note);
            }
        });

        noteViewModel = new ViewModelProvider(this).get(ZametkiViewModel.class);
        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });

        ImageButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddNoteFragment();
            }
        });

        return view;
    }

    public void navigateToAddNoteFragment() {
        navigator.navigateTo(new AddNoteFragment(), true);
    }

    public void navigateToEditNoteFragment(Note note) {
        Bundle bundle = new Bundle();
        bundle.putString("title", note.getTitle());
        bundle.putString("description", note.getDescription());
        bundle.putInt("id", note.getId());
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        editNoteFragment.setArguments(bundle);
        navigator.navigateTo(editNoteFragment, true);
    }

}