
# Journal Entry Module - Design Overview

## 1. JournalEntry - Entity

 **Annotation:** @Document(collection = "journal_entries")

| Field Name     | Type             | Description                      |
|----------------|------------------|----------------------------------|
| `entryId`      | `ObjectId`       | MongoDB ID for the entry         |
| `title`        | `String`         | Journal entry title              |
| `content`      | `String`         | Journal entry body               |
| `creationTime` | `LocalDateTime`  | Time when the entry was created  |

## 2. JournalEntryDTO - DTO class


| Field Name     | Type          | Description                        |
|----------------|---------------|------------------------------------|
| `entryId`           | `String`      | Hex string representation of ID    |
| `title`        | `String`      | Title of the journal entry         |
| `content`      | `String`      | Main content of the entry          |
| `creationTime` | `String`      | Formatted date-time (ISO string)   |

## 3. JournalEntryDTOMapper - Mapper class

Annotation: @Component
| Method Name       | Return Type          | Parameter Type        | Description                                |
|--------------------|----------------------|------------------------|--------------------------------------------|
| `toDTO()`          | `JournalEntryDTO`    | `JournalEntry`         | Converts Entity → DTO                      |
| `toEntity()`       | `JournalEntry`       | `JournalEntryDTO`      | Converts DTO → Entity                      |

## 4. JournalEntryRepository - Repository Interface

- Implements MongoRepository<JournalEntry, ObjectId>

## 5. JournalEntryService - Service class

| Method Name | Return Type | Parameter  | Description | Repository method used |
|--------------|-------------|----------------|--------------|----------------------|
| `createEntry()`   | `JournalEntryDTO` | `JournalEntryDTO` DTO | Creates a new journal entry | `save()`|
| `getAllEntries()`   | `List<JournalEntryDTO>` | — | Fetches all entries | `findAll()` |
| `getEntryById()`  | `JournalEntryDTO` | `String` entryId | Finds entry by ID | `findById()` |
| `updateEntry()`   | `JournalEntryDTO` | `String` entryId, `JournalEntryDTO` dto | Updates entry details | `update()` |
| `deleteEntry ()`   | `String` | `String` entryId | Deletes entry by ID | `delete()` |

## 6. JournalEntryController - Controller class



