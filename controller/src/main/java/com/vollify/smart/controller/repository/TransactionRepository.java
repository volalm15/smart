package com.vollify.smart.controller.repository;

import com.vollify.smart.controller.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
