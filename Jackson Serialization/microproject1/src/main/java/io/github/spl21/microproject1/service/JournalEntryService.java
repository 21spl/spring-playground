package io.github.spl21.microproject1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import io.github.spl21.microproject1.dto.JournalEntryDTO;
import io.github.spl21.microproject1.entity.JournalEntry;
import io.github.spl21.microproject1.mapper.JournalEntryDTOMapper;
import io.github.spl21.microproject1.repository.JournalEntryRepository;


@Service
public class JournalEntryService {
    
    private final JournalEntryRepository repository;
    private final JournalEntryDTOMapper mapper;
    // constructor injection
    public JournalEntryService(JournalEntryRepository repository, JournalEntryDTOMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // method to create entry
    public JournalEntryDTO createEntry(JournalEntryDTO entryDTO){
        JournalEntry entry = mapper.toEntity(entryDTO);
        entry = repository.save(entry);
        return mapper.toDTO(entry);
    }

    // method to get all entry
    public List<JournalEntryDTO> getAllEntries(){
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // method to getEntryById
    public JournalEntryDTO getEntryById(String id){
        return repository.findById(new ObjectId(id))
                .map(mapper::toDTO)
                .orElse(null);            
    }

    // method to update journal entry
    public JournalEntryDTO updateEntry(String id, JournalEntryDTO entryDTO)
    {
        // first check if the id is valid
        Optional<JournalEntry> existing = repository.findById(new ObjectId(id));
        if(existing.isEmpty()) return null;

        // update the entry
        JournalEntry entry = existing.get();
        entry.setTitle(entryDTO.getTitle());
        entry.setContent(entryDTO.getContent());
        entry = repository.save(entry);
        return mapper.toDTO(entry);
    }

    // method to delete entry
    public String deleteEntry(String id){
        // check if id is a valid id
        Optional<JournalEntry> entry = repository.findById(new ObjectId(id));
        if(entry.isEmpty()) return "No entry found with id " + id;
        repository.delete(entry.get());
        return "Entry with id " + id + " deleted";
    }

    

}
