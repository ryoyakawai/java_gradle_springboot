package helloworld;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesampleRepository extends JpaRepository<Messagesample, Long> {}
