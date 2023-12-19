package com.celeb.security.jwt;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TokenCacheRepository extends CrudRepository<Token, String> {

}
