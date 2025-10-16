package io.github.spl21.microproject1.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import io.github.spl21.microproject1.dto.JournalEntryDTO;
import io.github.spl21.microproject1.entity.JournalEntry;



@Component
public class JournalEntryDTOMapper {
    
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    public JournalEntryDTO toDTO(JournalEntry entry)
    {
        if(entry==null)
        {
            return null;
        }

        JournalEntryDTO dto = new JournalEntryDTO();
        dto.setEntryId(entry.getEntryId().toHexString());
        dto.setTitle(entry.getTitle());
        dto.setContent(entry.getContent());
        dto.setCreationTime(entry.getCreationTime().format(dateTimeFormatter));


        return dto;
    }


    // method to convert  dto to entity
    public JournalEntry toEntity(JournalEntryDTO dto)
    {
        if(dto==null) return null;

        JournalEntry entry = new JournalEntry();
        // only set ObjectId if dto entryId is not null or empty (for update operations)
        if(dto.getEntryId()!=null && !dto.getEntryId().isEmpty())
        {
            entry.setEntryId(new ObjectId(dto.getEntryId()));
        }


        entry.setContent(dto.getContent());
        entry.setTitle(dto.getTitle());
        // creationTime is handled automatically by mongodb auditing

        return entry;
    }

}
