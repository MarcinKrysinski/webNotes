package pl.krysinski.webnotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.krysinski.webnotes.model.Note;

@Repository
public interface NotesRepo extends JpaRepository <Note, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE notes SET notes.text= ?1, notes.creation_time=?2 WHERE notes.id= ?3", nativeQuery = true)
    void updateNote(String text, String creationTime, Long id);
}
