package org.example.student_volunteer_restapi.repository;

import org.example.student_volunteer_restapi.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByUser_IdAndMarkedDelete(Long id, boolean markedDelete);

    List<Bid> findByUser_Id(Long id);

}
