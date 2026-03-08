package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
