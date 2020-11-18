package pl.krysinski.webnotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krysinski.webnotes.model.Note;
import pl.krysinski.webnotes.repository.NotesRepo;

import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {

    private NotesRepo notesRepo;

    @Autowired
    public NotesServiceImpl(NotesRepo notesRepo) {
        this.notesRepo = notesRepo;
    }


    @Override
    public List<Note> findAllNotes() {
        return notesRepo.findAll();
    }

    @Override
    public void saveNote(Note note) {
        notesRepo.save(note);
    }

    @Override
    public boolean updateNote(Note newNote) {
        newNote.setCreationTime();
        notesRepo.updateNote(newNote.getText(), newNote.getCreationTime(), newNote.getId());
        return true;
    }
    
    @Override
    public Optional<Note> findNoteById(Long id) {
        return notesRepo.findById(id);
    }

    @Override
    public void deleteNote(Long id) {
        notesRepo.deleteById(id);
    }


}
