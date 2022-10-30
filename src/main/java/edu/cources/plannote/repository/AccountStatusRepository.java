package edu.cources.plannote.repository;

import edu.cources.plannote.entity.AccountStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatusEntity, String> {

}
