package com.rsc.bhopal.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.ActivityLog;

@Repository
public interface ActivityLogRepo extends CrudRepository<ActivityLog, Long>{

}
