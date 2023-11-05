package com.cody.springcody.cody;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodyRepository extends JpaRepository<Cody, Integer> {

}
