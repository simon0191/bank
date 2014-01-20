package com.payulatam.samples.bank.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.service.ITransactionDao;

@Controller
@RequestMapping(value = "/transactions")
public class TransactionController {

	@Autowired
	ITransactionDao transactionDao;

//	@ResponseBody
//	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseStatus(value = HttpStatus.CREATED)
//	public Transaction create(@RequestBody Map<String,Object> params) {
//		Transaction t = new Transaction();
//		t.setAccountId((String)params.get("accountId"));
//		t.setType(TransactionType.valueOf((String)params.get("type")));
//		t.setValue(new BigDecimal((String)params.get("value")));
//		
//		Transaction result = transactionDao.create(t.getAccountId(),
//				t.getType(), t.getValue());
//		return result;
//	}
	
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Transaction create(@RequestBody Transaction t) {
		Transaction result = transactionDao.create(t.getAccountId(),
				t.getType(), t.getValue());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/{transactionNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Transaction searchById(@PathVariable String transactionNumber) {
		Transaction result = transactionDao.searchByNumber(transactionNumber);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/searchByClient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Transaction> searchByClientId(@PathVariable String id) {
		List<Transaction> result = transactionDao.searchByClientId(id);
		return result;
	}
	
	@ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Exception resolveBindingException ( Exception e )
    {
        return e;
    }
	
//	@ResponseBody
//	@RequestMapping(value = "/searchByClient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<Transaction> searchByClientId(@PathVariable String id) {
//		List<Transaction> result = transactionDao.searchByDateBetweenAndAccount(startDate, endDate, accountId);
//		return result;
//	}

}
