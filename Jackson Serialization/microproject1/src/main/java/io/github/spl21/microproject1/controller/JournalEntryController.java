package io.github.spl21.microproject1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.spl21.microproject1.dto.JournalEntryDTO;
import io.github.spl21.microproject1.service.JournalEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/entry")
@RequiredArgsConstructor
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    

    @PostMapping
    public ResponseEntity<JournalEntryDTO> createEntry(@Valid @RequestBody JournalEntryDTO dto) {
        return ResponseEntity.ok(journalEntryService.createEntry(dto));
    }

    @GetMapping
    public ResponseEntity<List<JournalEntryDTO>> getAllEntries() {
        return ResponseEntity.ok(journalEntryService.getAllEntries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntryDTO> getEntry(@PathVariable String id) {
        JournalEntryDTO dto = journalEntryService.getEntryById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntryDTO> updateEntry(@PathVariable String id, @Valid @RequestBody JournalEntryDTO dto) {
        JournalEntryDTO updated = journalEntryService.updateEntry(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable String id) {
        journalEntryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }
}
