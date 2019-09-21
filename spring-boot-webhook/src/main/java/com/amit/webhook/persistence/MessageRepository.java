package com.amit.webhook.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.amit.webhook.model.Destination;
import com.amit.webhook.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

	List<Message> findAllByDestinationOrderByIdAsc(Destination destination);

}
