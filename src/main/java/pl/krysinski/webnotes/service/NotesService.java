package pl.krysinski.webnotes.service;

import pl.krysinski.webnotes.model.Note;

import java.util.List;
import java.util.Optional;

public interface NotesService {

    List<Note> findAllNotes();

    Note saveNote(Note note);

    boolean updateNote(Note newNote);

    Optional<Note> findNoteById(Long id);

    boolean deleteNote(Long id);
}
