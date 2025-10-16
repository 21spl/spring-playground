package io.github.spl21.microproject2.repository;

import io.github.spl21.microproject2.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Integer>{
}
