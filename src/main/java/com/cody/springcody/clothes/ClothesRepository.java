package com.cody.springcody.clothes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Integer> {

}
