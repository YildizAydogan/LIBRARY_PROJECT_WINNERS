package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {


}
