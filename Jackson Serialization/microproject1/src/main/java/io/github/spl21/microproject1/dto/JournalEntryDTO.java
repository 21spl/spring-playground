package io.github.spl21.microproject1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JournalEntryDTO {

    @JsonProperty("id")
    private String entryId; //  serialized ObjectId to String using Mapper
    
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;
    
    @NotBlank(message= "Content is required")
    private String content;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String creationTime;   
}
