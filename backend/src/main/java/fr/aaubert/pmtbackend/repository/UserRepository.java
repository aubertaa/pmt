package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //implement here queries not available in JpaRepository

/*
    @Query("""
                 FROM User
                 WHERE username = :username
            """)
    User findByUsername(String username);

    @Query("""
                 FROM User
                 WHERE email = :email
            """)
    User findByEmail(String email);
*/

}
