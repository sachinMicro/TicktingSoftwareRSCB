package com.rsc.bhopal.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.dtos.LogDTO;
import com.rsc.bhopal.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
	// @Query(name = "GET_LOGS_LATEST_ARRANGED", nativeQuery = false, value = "SELECT Log.message, Log.payload, Log.actionAt, Log.actionBy, Log.status FROM Log ORDER BY Log.actionAt DESC")
	// @Query(name = "GET_LOGS_LATEST_ARRANGED", nativeQuery = false, value = "SELECT l.message, l.payload, l.actionAt, l.actionBy, l.status FROM Log l ORDER BY l.actionAt DESC")
	@Query(name = "GET_LOGS_LATEST_ARRANGED", nativeQuery = false,
		value = "SELECT new com.rsc.bhopal.dtos.LogDTO(l.id, l.actionAt, l.actionBy, l.message, l.payload, l.status) FROM Log l ORDER BY l.actionAt DESC")
	List<LogDTO> getAllLogsDateSorted();
}
