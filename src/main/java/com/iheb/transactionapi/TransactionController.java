package com.iheb.transactionapi;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository repository;

    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

    // GET /transactions  -> list all transactions
    @GetMapping
    public List<Transaction> getAll() {
        return repository.findAll();
    }

    // GET /transactions/balance -> total of all amounts
    @GetMapping("/balance")
    public double balance() {
        return repository.findAll().stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    // GET /transactions/{id} -> get one transaction by id
    @GetMapping("/{id}")
    public Transaction getOne(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // POST /transactions -> add a new transaction
    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        return repository.save(transaction);
    }

    // DELETE /transactions/{id} -> remove a transaction
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
