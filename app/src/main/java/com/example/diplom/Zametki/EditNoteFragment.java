package com.example.diplom.Zametki;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diplom.R;
import com.example.diplom.Room.Note;

public class EditNoteFragment extends Fragment {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Button buttonSave;

    private Button buttonDelete;
    private ZametkiViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_note_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextTitle = view.findViewById(R.id.edit_text_title);
        editTextDescription = view.findViewById(R.id.edit_text_description);
        buttonSave = view.findViewById(R.id.button_save);
        buttonDelete = view.findViewById(R.id.button_delete);
        viewModel = new ViewModelProvider(requireActivity()).get(ZametkiViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            String description = bundle.getString("description");

            editTextTitle.setText(title);
            editTextDescription.setText(description);
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });

    }

    private void saveNote() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            return;
        }

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("id")) {
            int noteId = bundle.getInt("id");
            Note editedNote = new Note(noteId, title, description);
            viewModel.update(editedNote);
        } else {
            Note newNote = new Note(title, description);
            viewModel.insert(newNote);
        }

        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void deleteNote() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("id")) {
            int noteId = bundle.getInt("id");
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            Note noteToDelete = new Note(noteId, title, description);
            viewModel.delete(noteToDelete);
        }
        requireActivity().getSupportFragmentManager().popBackStack();
    }

}