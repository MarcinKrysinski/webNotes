package pl.krysinski.webnotes.service;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import pl.krysinski.webnotes.model.Note;
import pl.krysinski.webnotes.repository.NotesRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class NotesServiceImplTest {

    @Test
    void should_find_all_notes() {
        //given
        NotesServiceImpl notesService = mock(NotesServiceImpl.class);
        //when
        when(notesService.findAllNotes()).thenReturn(prepareMockData());
        //then
        MatcherAssert.assertThat(notesService.findAllNotes(), Matchers.hasSize(5));
    }

    @Test
    void should_save_note() {
        //given
        Note note = new Note(5L, "Some text");
        NotesServiceImpl notesService = mock(NotesServiceImpl.class);
        //when
        when(notesService.saveNote(note)).thenReturn(mockSaveNote(note));
        notesService.saveNote(note);
        //then
        verify(notesService, times(1)).saveNote(note);
    }

    @Test
    void should_update_note() {
        //given
        Note newNote = new Note(1L, "New text");
        NotesServiceImpl notesService = mock(NotesServiceImpl.class);
        //when
        when(notesService.updateNote(newNote)).thenReturn(true);
        notesService.updateNote(newNote);
        //then
        verify(notesService, times(1)).updateNote(any());
    }

    @Test
    void should_find_note_by_id() {
        //given
        long noteId = 4L;
        NotesServiceImpl notesService = mock(NotesServiceImpl.class);
        //when
        when(notesService.findNoteById(noteId)).thenReturn(Optional.of(prepareMockData().get(Math.toIntExact(noteId - 1))));
        //then
        MatcherAssert.assertThat(notesService.findNoteById(noteId).get().getText(), Matchers.hasToString("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."));
    }

    @Test
    void should_delete_note() {
        //given
        long noteId = 1L;
        NotesRepo notesRepo = mock(NotesRepo.class);
        Note note = new Note(1L, "Lorem ipsum");
        given(notesRepo.findById(noteId)).willReturn(Optional.of(note));


        //when
        when(notesRepo.findById(noteId)).thenReturn(mockFindById(noteId));
        notesRepo.deleteById(3L);

        //then
        MatcherAssert.assertThat(notesRepo.findById(noteId).get().getText(), Matchers.hasToString("Lorem ipsum"));
        verify(notesRepo, times(1)).deleteById(anyLong());

    }


    List<Note> notes = new ArrayList<>();

    private List<Note> prepareMockData() {
        notes.add(new Note(1L, "Lorem ipsum"));
        notes.add(new Note(2L, "Something to do for example nothing"));
        notes.add(new Note(3L, ""));
        notes.add(new Note(4L, "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."));
        notes.add(new Note(5L, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));

        return notes;
    }


    private Optional<Note> mockFindById(Long id) {
        prepareMockData();
        return Optional.ofNullable(notes.get(Integer.parseInt(String.valueOf(id - 1))));
    }

    private Note mockSaveNote(Note note) {
        return note;
    }
}
