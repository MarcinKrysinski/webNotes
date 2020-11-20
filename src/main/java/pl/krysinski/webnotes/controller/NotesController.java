package pl.krysinski.webnotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krysinski.webnotes.model.Note;
import pl.krysinski.webnotes.service.NotesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/notes", produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "http://localhost:4200")
public class NotesController {

    private NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return new ResponseEntity<>(notesService.findAllNotes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> noteById = notesService.findNoteById(id);
        if (noteById.isPresent()) {
            return new ResponseEntity<>(noteById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateNote(@RequestBody Note newNote, @PathVariable Long id) {
        Optional<Note> noteById = notesService.findNoteById(id);
        if (noteById.isPresent()) {
            return new ResponseEntity<>(notesService.updateNote(newNote), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNote(@PathVariable Long id) {
        boolean isDelete = notesService.deleteNote(id);
        if (isDelete) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
