package io.github.spl21.microproject1.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.github.spl21.microproject1.entity.JournalEntry;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
    // basic CRUD methods
}
