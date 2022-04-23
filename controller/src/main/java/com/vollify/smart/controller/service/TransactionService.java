package com.vollify.smart.controller.service;

import com.vollify.smart.controller.model.Transaction;
import com.vollify.smart.controller.repository.TransactionRepository;
import com.vollify.smart.controller.service.common.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService extends AbstractService<Transaction> {

    private final TransactionRepository transactionRepository;

    @Override
    protected MongoRepository<Transaction, String> getDao() {
        return transactionRepository;
    }

}