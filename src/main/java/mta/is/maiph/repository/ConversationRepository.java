/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.repository;

import mta.is.maiph.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author MaiPH
 */
public interface ConversationRepository extends MongoRepository<Conversation, String> {
       
}
