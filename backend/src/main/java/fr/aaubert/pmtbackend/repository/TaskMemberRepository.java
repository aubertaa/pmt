package fr.aaubert.pmtbackend.repository;

import com.mysql.cj.MysqlConnection;
import fr.aaubert.pmtbackend.model.Task;
import fr.aaubert.pmtbackend.model.TaskMember;
import fr.aaubert.pmtbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMemberRepository extends JpaRepository<TaskMember, Long> {

    //get userId from user selected from task_member where task.task_id = ?
    @Query("SELECT tm.user.userId FROM TaskMember tm WHERE tm.task.id = ?1")
    Long getMemberUserIdByTaskId(Long taskId);

    @Query("SELECT tm FROM TaskMember tm WHERE tm.task.id = ?1 AND tm.user.userId = ?2")
    TaskMember findByTaskIdAndUserId(Long taskId, Long userId);

    @Query("SELECT tm FROM TaskMember tm WHERE tm.task.id = ?1")
    TaskMember findByTaskId(Long taskId);
}
