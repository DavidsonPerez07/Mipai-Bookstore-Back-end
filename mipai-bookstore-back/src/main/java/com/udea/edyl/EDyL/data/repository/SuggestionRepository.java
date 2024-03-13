package com.udea.edyl.EDyL.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udea.edyl.EDyL.data.entity.Suggestion;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

}
