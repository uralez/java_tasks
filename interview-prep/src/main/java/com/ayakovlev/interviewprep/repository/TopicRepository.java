package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.dto.TopicDto;
import com.ayakovlev.interviewprep.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("SELECT new com.ayakovlev.interviewprep.dto.TopicDto" +
            "(t.id, t.orderNumber, tt.name) " +
            "FROM Topic t " +
            "JOIN t.translations tt " +
            "WHERE tt.locale = :locale " +
            "ORDER BY t.orderNumber"
    )
    List<TopicDto> findAllWithTranslation(@Param("locale") String locale);
}
