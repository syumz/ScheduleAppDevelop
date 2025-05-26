package org.example.scheduleappdevelop.schedule.repository;

import org.example.scheduleappdevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
