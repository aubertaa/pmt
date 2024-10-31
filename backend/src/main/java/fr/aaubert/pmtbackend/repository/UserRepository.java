package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


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


    @Query("""
             SELECT email
             FROM User
             WHERE notifications = true
        """)
    List<String> getAllUsersEmailHavingNotificationsTrue();

}
