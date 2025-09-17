package com.pedro.UniLar.profile.user.repositories;

import com.pedro.UniLar.profile.user.entities.Condomino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondominoRepository extends JpaRepository<Condomino, Long> {
}
