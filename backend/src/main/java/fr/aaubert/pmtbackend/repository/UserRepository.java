package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
                 FROM User
                 WHERE userName = :userName
            """)
    User findByUsername(String userName);
/*
    @Query("""
                 FROM User
                 WHERE email = :email
            """)
    User findByEmail(String email);*/

}
