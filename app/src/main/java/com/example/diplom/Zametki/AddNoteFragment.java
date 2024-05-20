package com.example.diplom.Zametki;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diplom.R;
import com.example.diplom.Room.Note;

public class AddNoteFragment extends Fragment {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Button buttonSave;
    private ZametkiViewModel noteViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        editTextTitle = view.findViewById(R.id.edit_text_title);
        editTextDescription = view.findViewById(R.id.edit_text_description);
        buttonSave = view.findViewById(R.id.button_save);

        noteViewModel = new ViewModelProvider(this).get(ZametkiViewModel.class);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        return view;
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            return;
        }
        Note note = new Note(title, description);
        noteViewModel.insert(note);

        getActivity().getSupportFragmentManager().popBackStack();
    }
}
