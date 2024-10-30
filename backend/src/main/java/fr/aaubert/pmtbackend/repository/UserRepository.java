package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
                 FROM User
                 WHERE userName = :userName
            """)
    User findByUsername(String userName);


    @Transactional
    @Modifying
    @Query("""
             UPDATE User
             SET notifications = :notificationsActive
             WHERE userId = :userId
        """)
    void setNotificationStatusForUserId(Long userId, Boolean notificationsActive);

/*
    @Query("""
                 FROM User
                 WHERE email = :email
            """)
    User findByEmail(String email);*/

}
