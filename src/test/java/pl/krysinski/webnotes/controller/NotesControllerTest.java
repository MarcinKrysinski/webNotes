package pl.krysinski.webnotes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.krysinski.webnotes.model.Note;
import pl.krysinski.webnotes.repository.NotesRepo;
import pl.krysinski.webnotes.service.NotesService;
import pl.krysinski.webnotes.service.NotesServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class NotesControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NotesRepo notesRepo;


    private List<Note> prepareMockData() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1L, "Lorem ipsum"));
        notes.add(new Note(2L, "Something to do for example nothing"));
        notes.add(new Note(3L, ""));
        notes.add(new Note(4L, "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."));
        notes.add(new Note(5L, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));

        return notes;
    }

    @Test
    void should_get_all_notes() throws Exception {
        //given
        given(notesRepo.findAll()).willReturn(prepareMockData());

        //then
        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("Lorem ipsum"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].text").value(""));
    }

    @Test
    void should_get_note_by_id() throws Exception {
        //given
        long noteId = 2L;
        String noteText = "Something to do for example nothing";
        given(notesRepo.findById(noteId)).willReturn(Optional.of(prepareMockData().get(Math.toIntExact(noteId-1))));

        //then
        mockMvc.perform(get("/notes/{id}", noteId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(noteText));
    }

    @Test
    void should_update_note() throws Exception {
        //given
        long noteId = 1L;
        String noteText = "New text!!!";
        Note objectNote = new Note(noteId, noteText);
        String note = mapper.writeValueAsString(objectNote);
        given(notesRepo.findById(noteId)).willReturn(Optional.of(prepareMockData().get(Math.toIntExact(noteId))));



        //then
        mockMvc.perform(put("/notes/{id}", noteId).contentType(MediaType.APPLICATION_JSON).content(note))
                .andExpect(status().isOk());
    }

    @Test
    void should_add_new_note() throws Exception {
        //given
        long noteId = 6L;
        String noteText = "The sixth note text!!!";
        Note objectNote = new Note(noteId, noteText);
        String note = mapper.writeValueAsString(objectNote);
        given(notesRepo.save(objectNote)).willReturn(objectNote);



        //then
        mockMvc.perform(post("/notes").contentType(MediaType.APPLICATION_JSON).content(note))
                .andExpect(status().isCreated());
    }

    @Test
    void should_delete_note() throws Exception {
        //given
        long noteId = 3L;
        given(notesRepo.findById(noteId)).willReturn(Optional.of(prepareMockData().get(Math.toIntExact(noteId))));

        //then
        mockMvc.perform(delete("/notes/{id}", noteId))
                .andExpect(status().isOk());
    }
}
